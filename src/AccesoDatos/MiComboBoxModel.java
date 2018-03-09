/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Breyner
 */
public class MiComboBoxModel extends AbstractListModel implements ComboBoxModel {
    
    ArrayList lista=new ArrayList();
    Bd bd=new Bd();
    
    public MiComboBoxModel(){
        lista.add("casa");
        lista.add("carro");
        lista.add("beca");
    }
    
    String selection = "2010";
    
    public Object getElementAt(int index){
        return lista.get(index);
    }
    
    public int getSize(){
        return lista.size();
    }
    
    public void setSelectedItem(Object anItem){
        selection = (String) anItem; // to select and register an
    } //item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    public Object getSelectedItem() {
    return selection; // to add the selection to the combo box
    }
}//new MiComboBoxModel()
//jComboBox2.setModel(new MiComboBoxModel());
    

