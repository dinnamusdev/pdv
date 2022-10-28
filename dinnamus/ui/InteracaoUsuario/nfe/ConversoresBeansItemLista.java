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
public class ConversoresBeansItemLista<T> {

        
    
    Map<T, ItemLista> hmitenslistas;
    public ConversoresBeansItemLista(HashMap<T,ItemLista>  hmitenslistas) {
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
                public T convertReverse(ItemLista value) {
                    T ret = null;
                   
                    if (value != null) {
                            
                        ret =  (T) value.getIndice().toString();
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
