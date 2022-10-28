/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.menu;

import br.com.log.LogDinnamus;
import java.sql.ResultSet;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Fernando
 */
public class Menu {
    
        public static DefaultMutableTreeNode MontarMenu(ResultSet rsMenu, DefaultMutableTreeNode noPai, String Pai){
            
          
            
            try {
                String NomeMenu ="";
                String PaiItemMenu ="";
                String IDMenu ="";
                while(rsMenu.next()){
                    IDMenu = rsMenu.getString("menu");                    
                    
                    NomeMenu = rsMenu.getString("DescricaoMenu");                    
                    
                    PaiItemMenu = rsMenu.getString("Pai");                    
                    
                    if("".equals(PaiItemMenu)){
                         noPai = new DefaultMutableTreeNode(NomeMenu);
                         MontarMenu(rsMenu, noPai, IDMenu);
                     }else{
                        if(PaiItemMenu.equalsIgnoreCase(Pai)){
                            noPai.add(new DefaultMutableTreeNode(NomeMenu));                            
                        }
                    }
                }                
            } catch (Exception e) {
                LogDinnamus.Log(e, true);
            }
            return noPai;
        }
}
