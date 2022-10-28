/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfe;

import br.com.log.LogDinnamus;
import br.com.ui.ItemLista;
import java.util.HashMap;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Fernando
 */
public class ConversoresBeansBind {
   
    
   public Converter<Short,Boolean> converterShortBoolean(){
        Converter<Short, Boolean> Ret=null;
       try {
               Ret = new Converter<Short, Boolean>() {
               @Override
               public Boolean convertForward(Short value) {
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   if (value == null) {
                       return false;
                   } else if (value == 1) {
                       return true;
                   } else {
                       return false;
                   }
               }

               @Override
               public Short convertReverse(Boolean value) {
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   Short ret=0;
                   
                   if(value){
                       ret= 0;
                   }else{
                       ret=1;
                   }
                   return ret;
               }
           };


       } catch (Exception e) {
           LogDinnamus.Log(e, true);
       }
       return Ret;
   }    
   
  
   
   public Converter<Object,ItemLista> converterItemLista(){
        Converter<Object,ItemLista> Ret=null;
       try {
               Ret = new Converter<Object,ItemLista>() {
               @Override
               public ItemLista convertForward(Object value) {
                   ItemLista ret=null;
                       
                   if(value!=null){
                       ret = new ItemLista();
                       ret.setIndice(value.toString());
                   }
                   
                   return ret;
               }

               @Override
               public String convertReverse(ItemLista value) {
                  String ret=null;
                   
                  if(value!=null){
                     
                      ret  = value.getIndice().toString();
                     
                      
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
