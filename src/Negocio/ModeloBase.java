/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.Bd;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Breyner
 */

public class ModeloBase {
    Bd bd = new Bd();
    String Tabla, Object, InnerJoin;
    String[] Campos;
    
    public ModeloBase(String Tabla[], ArrayList<String[]> InnerJoin) {
        this.Tabla = Tabla[0];
        if(Tabla.length>1){
            this.Object = Tabla[1];
        }
        this.Campos=getNombreCampos(this.Tabla);
        if(InnerJoin!=null){
            this.InnerJoin=getInnerJoin(this.Tabla, InnerJoin);
        }
    }
    
    public String getFechaActual(){
        Date date= new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
    
    String getInnerJoin(String vTabla, ArrayList<String[]> vTablas){
        String sql=vTabla+" "+vTablas.get(0)[0]+" ";
            for(int i=1;i<=vTablas.size()-1;i++){
                sql+="INNER JOIN "+vTablas.get(i)[0]+" "+vTablas.get(i)[1]+" ON "+vTablas.get(0)[0]+"."+vTablas.get(i)[2]+"="+vTablas.get(i)[1]+"."+vTablas.get(i)[3]+" ";       
            }
        return sql;
    }
    
    String[] getNombreCampos(String vTabla){
        String[] campos;
        DefaultTableModel dtm=bd.EjecutarConsulta("SELECT * FROM "+vTabla);
        campos=new String[dtm.getColumnCount()];
        for(int i=0;i<=dtm.getColumnCount()-1;i++){
            campos[i]=dtm.getColumnName(i);
        }
        return campos;
    }
    
    public String Create(String[] Values){
        //creamos la sentencia sql
        String sql="INSERT INTO "+Tabla+" (";
        for(int i=0;i<=Campos.length-1;i++){
            if(i==Campos.length-1){
                if(Values[0]==null){
                    sql+=Campos[i]+") VALUES (null,";
                }else{
                    sql+=Campos[i]+") VALUES (";
                }
            }else{
                sql+=Campos[i]+", ";
            }
        }
        for(int i=0;i<=Values.length-1;i++){
            if(!(Values[i]==null)){
                if(i==Values.length-1){
                    sql+="'"+Values[i]+"')";
                }else{
                    sql+="'"+Values[i]+"', ";
                }
            }
        }
        
        //ejecutamos
        String msg;
        if(bd.EjecutarComando(sql)){
            msg= Object+" Se A Creado Correctamente";
        }else{
            msg="No Se A Podido Crear "+Object;
        }
        return msg;
    }
    
    public String Update(String[] Values, String[] vCampos, String[] Values2){
        //creamos la sentencia sql
        String sql="UPDATE "+Tabla+" SET ";
        for(int i=0;i<=Campos.length-1;i++){
            if(!(Values[i]==null)){
                if(i==Values.length-1){
                    sql+=Campos[i]+"='"+Values[i]+"' WHERE ";
                }else{
                    sql+=Campos[i]+"='"+Values[i]+"', ";
                }
            }
        }
        
        for(int i=0;i<=vCampos.length-1;i++){
            if(i==vCampos.length-1){
                sql+= vCampos[i]+" = '"+Values2[i]+"'";
            }else{
                sql+= vCampos[i]+" = '"+Values2[i]+"' AND ";
            }
        }
        
        //ejecutamos
        String msg;
        if(bd.EjecutarComando(sql)){
            msg= Object+" Se A Actualizado Correctamente";
        }else{
            msg="No Se A Podido Actualizar "+Object;
        }
        return msg;
    }
    
    public String Delete(String[] vCampos, String[] Values){
        //creamos la sentencia sql
        String sql="DELETE FROM "+Tabla+" WHERE ";
        for(int i=0;i<=vCampos.length-1;i++){
            if(i==Values.length-1){
                sql+=vCampos[i]+"='"+Values[i]+"'";
            }else{
                sql+=vCampos[i]+"='"+Values[i]+"' AND ";
            }
        }
        
        //ejecutamos
        String msg;
        if(bd.EjecutarComando(sql)){
            msg= Object+" Se A Eliminado Correctamente";
        }else{
            msg="No Se A Podido Eliminar "+Object;
        }
        return msg;
    }
    
    public DefaultTableModel ReadAll(String[] vCampos){
        String sql="SELECT ";
        for(int i=0;i<=vCampos.length-1;i++){
            if(i==vCampos.length-1){
                sql+=vCampos[i]+" FROM "+this.Tabla;
            }else{
                sql+=vCampos[i]+", ";
            }
        }
        return bd.EjecutarConsulta(sql);
    }
    
    public DefaultTableModel ReadAllInner(String[] vCampos){
        String sql="SELECT ";
        for(int i=0;i<=vCampos.length-1;i++){
            if(i==vCampos.length-1){
                sql+=vCampos[i]+" FROM "+this.InnerJoin;
            }else{
                sql+=vCampos[i]+", ";
            }
        }
        return bd.EjecutarConsulta(sql);
    }
    
    public DefaultTableModel ReadOnes(String[] vCampos1, String[] vCampos2, String[] Values){
        String sql="SELECT ";
        for(int i=0;i<=vCampos1.length-1;i++){
            if(i==vCampos1.length-1){
                sql+=vCampos1[i]+" FROM "+this.Tabla+" WHERE ";
            }else{
                sql+=vCampos1[i]+", ";
            }
        }
        for(int i=0;i<=vCampos2.length-1;i++){
            if(i==vCampos2.length-1){
                sql+= vCampos2[i]+" = '"+Values[i]+"'";
            }else{
                sql+= vCampos2[i]+" = '"+Values[i]+"' AND ";
            }
        }
        return bd.EjecutarConsulta(sql);
    }
    
    public String[] getRegistro(String vCampo,String vId){
        String[] registro=null;
        DefaultTableModel dtm = ReadOnes(new String[]{"*"},new String[]{vCampo},new String[]{vId});
        if(dtm!=null && dtm.getRowCount()>0){
            registro =new String[dtm.getColumnCount()];
            for(int i=0;i<=dtm.getColumnCount()-1;i++){
                registro[i]=String.valueOf(dtm.getValueAt(0, i));
            }
        }
        return registro;
    }
    
    public DefaultTableModel ReadOnesInner(String[] vCampos1, String[] vCampos2, String[] Values){
        String sql="SELECT ";
        for(int i=0;i<=vCampos1.length-1;i++){
            if(i==vCampos1.length-1){
                sql+=vCampos1[i]+" FROM "+this.InnerJoin+" WHERE ";
            }else{
                sql+=vCampos1[i]+", ";
            }
        }
        for(int i=0;i<=vCampos2.length-1;i++){
            if(i==vCampos2.length-1){
                sql+= vCampos2[i]+" = '"+Values[i]+"'";
            }else{
                sql+= vCampos2[i]+" = '"+Values[i]+"' AND ";
            }
        }
        return bd.EjecutarConsulta(sql);
    }
    
    public DefaultTableModel Search(String[] vCampos1, String[] vCampos2, String valor){
        String sql="SELECT ";
        for(int i=0;i<=vCampos1.length-1;i++){
            if(i==vCampos1.length-1){
                sql+=vCampos1[i]+" FROM "+this.Tabla+" WHERE ";
            }else{
                sql+=vCampos1[i]+", ";
            }
        }
        for(int i=0;i<=vCampos2.length-1;i++){
            if(i==vCampos2.length-1){
                sql+= vCampos2[i]+" LIKE '%"+valor+"%'";
            }else{
                sql+= vCampos2[i]+" LIKE '%"+valor+"%' OR ";
            }
        }
        return bd.EjecutarConsulta(sql);
    }
    
    public DefaultTableModel SearchInner(String[] vCampos1, String[] vCampos2, String valor){
        String sql="SELECT ";
        for(int i=0;i<=vCampos1.length-1;i++){
            if(i==vCampos1.length-1){
                sql+=vCampos1[i]+" FROM "+this.InnerJoin+" WHERE ";
            }else{
                sql+=vCampos1[i]+", ";
            }
        }
        for(int i=0;i<=vCampos2.length-1;i++){
            if(i==vCampos2.length-1){
                sql+= vCampos2[i]+" LIKE '%"+valor+"%'";
            }else{
                sql+= vCampos2[i]+" LIKE '%"+valor+"%' OR ";
            }
        }
        return bd.EjecutarConsulta(sql);
    }
    
    public boolean TestRegistro(String[] Campos, String[] Values){
        boolean sw;
        //creamos la sentencia sql
        String sql="SELECT * FROM "+Tabla+" WHERE ";
        for(int i=0;i<=Campos.length-1;i++){
            if(i==Campos.length-1){
                sql+=Campos[i]+"='"+Values[i]+"'";
            }else{
                sql+=Campos[i]+"='"+Values[i]+"' AND ";
            }
        }
        
        //ejecutamos
        DefaultTableModel dtm=bd.EjecutarConsulta(sql);;
        if(dtm!=null && dtm.getRowCount()>0){
            sw=true;
        }else{
            sw=false;
        }
        return sw;
    }
    
    public boolean TestRegistroUpdate(String[] Campos, String[] Values){
        boolean sw;
        //creamos la sentencia sql
        String sql="SELECT * FROM "+Tabla+" WHERE ";
        for(int i=0;i<=Campos.length-1;i++){
            if(i==Campos.length-1){
                sql+=Campos[i]+"='"+Values[i]+"'";
            }else if(i==0){
                sql+=Campos[i]+"<>'"+Values[i]+"' AND ";
            }else{
                sql+=Campos[i]+"='"+Values[i]+"' AND ";
            }
        }
        JOptionPane.showMessageDialog(null, sql);
        //ejecutamos
        DefaultTableModel dtm=bd.EjecutarConsulta(sql);
        
        if(dtm!=null && dtm.getRowCount()>0){
            sw=true;
        }else{
            sw=false;
        }
        return sw;
    }
    
    public DefaultComboBoxModel LlenarCombo(String vTabla, int vIndexValue, int vIndexDisplay){
        DefaultComboBoxModel cmb= new DefaultComboBoxModel();
        DefaultTableModel dtm=bd.EjecutarConsulta("SELECT * FROM "+vTabla);;
        for (int i=0;i<=dtm.getRowCount()-1;i++){
            itemCombo ic=new itemCombo(String.valueOf(dtm.getValueAt(i,vIndexValue)), String.valueOf(dtm.getValueAt(i,vIndexDisplay)));
            cmb.addElement(ic);
        }
        return cmb;
    }
    
    public DefaultComboBoxModel LlenarCombo(String vTabla, int vIndexValue, int vIndexDisplay,String vCampo,String value){
        DefaultComboBoxModel cmb= new DefaultComboBoxModel();
        DefaultTableModel dtm=bd.EjecutarConsulta("SELECT * FROM "+vTabla+" WHERE "+vCampo+"="+value);
        for (int i=0;i<=dtm.getRowCount()-1;i++){
            itemCombo ic=new itemCombo(String.valueOf(dtm.getValueAt(i,vIndexValue)), String.valueOf(dtm.getValueAt(i,vIndexDisplay)));
            cmb.addElement(ic);
        }
        return cmb;
    }
    
    public DefaultComboBoxModel LlenarComboPre(String vTabla, int vIndexValue, int vIndexDisplay,String[] vCampos2,String[] Values){
        DefaultComboBoxModel cmb= new DefaultComboBoxModel();
        String sql="";
        for(int i=0;i<=vCampos2.length-1;i++){
            if(i==vCampos2.length-1){
                sql+= vCampos2[i]+" > '"+Values[i]+"'";
            }else{
                sql+= vCampos2[i]+" = '"+Values[i]+"' AND ";
            }
        }
        DefaultTableModel dtm=bd.EjecutarConsulta("SELECT * FROM "+vTabla+" WHERE "+sql);
        for (int i=0;i<=dtm.getRowCount()-1;i++){
            itemCombo ic=new itemCombo(String.valueOf(dtm.getValueAt(i,vIndexValue)), String.valueOf(dtm.getValueAt(i,vIndexDisplay)));
            cmb.addElement(ic);
        }
        return cmb;
    }
    
}
