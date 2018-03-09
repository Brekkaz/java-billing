/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Breyner
 */
public class Bd {
    
    final String DRIVER="oracle.jdbc.driver.OracleDriver"/*"com.mysql.jdbc.Driver"*//*"org.gjt.mm.mysql.Driver"*/;
    String bd="facturacionvi";
    String user=/*"root"*/"breyner";
    String password=/*"12345"*/"oracle";
    String host=/*"jdbc:mysql://127.0.0.1/"+bd*/"jdbc:oracle:thin:@localhost:1521:xe"/*"127.0.0.1"*/;
    
    Connection con;
    Statement cmd;
    PreparedStatement cmd2;
    
    void Conectar(){
        try {
            Class.forName(DRIVER);
        }
        catch(ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "no se encuentra el driver de MySql");
        }

        try{
            con = DriverManager.getConnection(host,user,password);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error al realizar la conexion");
        }
        
        try{
            cmd=con.createStatement();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error al crear el objeto sentencia");
        }
    } 
    
    public boolean EjecutarComando(String vSql){
        boolean sw=true;
        //JOptionPane.showInputDialog(null, "lol",vSql);
        Conectar();
        try{
            cmd.execute(vSql);
        }catch(Exception e){
            sw=false;
        }
        Desconectar();
        return sw;
    }
    
    public DefaultTableModel EjecutarConsulta(String vSql){
        DefaultTableModel modelotabla=null;
        //JOptionPane.showInputDialog(null,"jaja",vSql);
        Conectar();
        try{
            ResultSet resultado = cmd.executeQuery(vSql);
            ResultSetMetaData metaData = resultado.getMetaData();
            
            int nCampos = metaData.getColumnCount();
            String[] lCampos = new String[nCampos];
            Object datos[]=new Object[nCampos];
            
            for(int i=0;i<nCampos;i++){
                lCampos[i]=metaData.getColumnName(i+1);
            }
            
            //se sobreescribe el metodo iscelleditable para que no se puedan editar las celdas
            modelotabla = new DefaultTableModel(null,lCampos){public boolean isCellEditable(int rowIndex, int columIndex){return false;}};;
            
            while(resultado.next()){
                for(int i=0;i<nCampos;i++){                 
                    datos[i]=resultado.getObject(i+1);
                }
                modelotabla.addRow(datos);
            }
            resultado.close();
        } catch (SQLException ex) {}
        Desconectar();
        
        return modelotabla;
    }
    
    void Desconectar() {
        try {
            cmd.close();
            con.close();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al desconectar la base de datos");
        }
    }
        
    // <editor-fold defaultstate="collapsed" desc="Comando Con Parametros">
    public void CrearComando(String vSql){
        Conectar();
        try {
            cmd2 = con.prepareStatement(vSql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Al Crear El Comando","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void AsignarParametro(int vIndex, String vValue) {
        try {
            cmd2.setString(vIndex, vValue);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Al Asignar Parametro","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void AsignarParametro(int vIndex, int vValue) {
        try {
            cmd2.setInt(vIndex, vValue);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Al Asignar Parametro","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean EjecutarComando(){
        boolean sw=true;
        try {
            cmd2.executeUpdate();
        } catch (SQLException ex) {
            sw=false;
        }
        Desconectar();
        return sw;
    }
    // </editor-fold>
}
