/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.entidades.recursos;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author dti
 */
public class Recursos {
    static private Locale ptBR = null;
    static private ResourceBundle Mensagens = null;

    /**
     * @return the Mensagens
     */
    public static ResourceBundle getMensagens() {
        if(Mensagens==null){
            ptBR=new Locale("pt","BR");
            Mensagens = ResourceBundle.getBundle("messages", ptBR);
        }
        return Mensagens;
    }

    /**
     * @param aMensagens the Mensagens to set
     */
    public static void setMensagens(ResourceBundle aMensagens) {
        Mensagens = aMensagens;
    }


}
