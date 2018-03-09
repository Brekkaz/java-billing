/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import AccesoDatos.Bd;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
//import AccesoDatos.Bd;

/**
 *
 * @author Breyner
 */
public class Usuario extends ModeloBase{

    public Usuario(String Tabla[], ArrayList<String[]> InnerJoin) {
        super(Tabla, InnerJoin);
    }
    
    public String testSuficiente(String vInventario){
        String vCant;
        Bd bd=new Bd();
        DefaultTableModel dtm= bd.EjecutarConsulta("select i.id, (i.cantidad - NVL((select SUM(cantidad) FROM detfactura d where i.id = d.idinventario),0)) unidadesdisponibles from inventario i where i.id= '"+vInventario+"'");
        vCant=String.valueOf(dtm.getValueAt(0, 1));
        return vCant;
    }
    
    public DefaultTableModel detaFactura(String idFactura){
        Bd bd=new Bd();
        DefaultTableModel dtm;
        return dtm= bd.EjecutarConsulta("SELECT i.codproducto, p.nombre ,d.cantidad, i.precioout FROM detfactura d INNER JOIN inventario i On d.idinventario=i.id INNER JOIN producto p On i.codproducto= p.codigo WHERE d.idfactura='"+idFactura+"'");
    }
    
    public String insertFactura(String vidusuario, String vidcliente){
        Bd bd=new Bd();
        bd.EjecutarComando("INSERT INTO factura (id, idusuario, idcliente, fecha) VALUES (null,'"+vidusuario+"','"+vidcliente+"','"+super.getFechaActual()+"')");
        DefaultTableModel dtm= bd.EjecutarConsulta("select max(id) from usuario");
        return dtm.getValueAt(0, 0).toString();
    }
    
}
