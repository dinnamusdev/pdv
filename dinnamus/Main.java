/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus;

import MetodosDeNegocio.Venda.ManutencaoBancoLocal;
import br.com.ui.MetodosUI_Auxiliares_1;
import dinnamus.infraestrutura.inicializacao.InicializarAPP;
import dinnamus.ui.InteracaoUsuario.Seguranca.frmLogin;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

 
 

/**
 *
 * @author desenvolvedor
 */
public class Main  {

    /**
     * @param args the command line arguments
     */
   //alteração do arquivo
    
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
       try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
           //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
           
        } catch (ClassNotFoundException ex) {
          //  Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
           // Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
           // Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
       
       if(!InicializarAPP.Iniciar())
          System.exit(0); 
       else{                   
          ManutencaoBancoLocal.LimparMovimento();             
          frmLogin l=(frmLogin) MetodosUI_Auxiliares_1.CentrarFrame(new frmLogin(),Toolkit.getDefaultToolkit() );
          l.setVisible(true);            
       }
    }

}
