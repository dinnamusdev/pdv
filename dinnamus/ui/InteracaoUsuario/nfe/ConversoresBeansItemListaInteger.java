/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfe;

import br.com.log.LogDinnamus;
import br.com.ui.ItemLista;
import java.util.HashMap;
import java.util.Map;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Fernando
 */
public class ConversoresBeansItemListaInteger {

        
    
    Map<Integer, ItemLista> hmitenslistas;
    public ConversoresBeansItemListaInteger(HashMap<Integer,ItemLista>  hmitenslistas) {
        this.hmitenslistas = hmitenslistas;
    }

    
    public Converter<Object, ItemLista> converterItemLista() {
        Converter<Object, ItemLista> Ret = null;
        try {
            Ret = new Converter<Object, ItemLista>() {
                @Override
                public ItemLista convertForward(Object value) {
                    ItemLista ret = null;

                    if (value != null && hmitenslistas!=null) {
                        ret = hmitenslistas.get(value.toString());
                    }

                    return ret;
                }

                @Override
                public Integer convertReverse(ItemLista value) {
                    Integer ret = null;
                   
                    if (value != null) {
                            
                        ret =  Integer.valueOf(value.getIndice().toString());
                    }

                    return ret;
                }
            };


        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
}
