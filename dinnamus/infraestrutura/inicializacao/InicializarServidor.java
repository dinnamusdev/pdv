/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.infraestrutura.inicializacao;

import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.util.DAO_Parametro_Generico;
import MetodosDeNegocio.RepositorioLocal.DAO_RepositorioLocal_Inicializar;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author desenvolvedor
 */
public class InicializarServidor {

        public static boolean Inicializar(Timestamp tsUltimoSinc, String cNomeArquivoCarga)
        {
            return Inicializar(tsUltimoSinc, cNomeArquivoCarga,false);
        }
        public static boolean Inicializar(Timestamp tsUltimoSinc, String cNomeArquivoCarga, boolean  bForcaAtualizacao)
        {           
           boolean bRetorno=true;
                      
           if(Sistema.isOnline())
                bRetorno=DAO_RepositorioLocal_Inicializar.Executar(tsUltimoSinc,cNomeArquivoCarga,false,bForcaAtualizacao);
             
            return bRetorno; 
           
        }  
        public static Map<String,DAO_Parametro_Generico<?>> ConverterObjetoEmColecao(Object obj)
        {              
            
                Field[] _Field=obj.getClass().getDeclaredFields();
                Map<String,DAO_Parametro_Generico<?>> hmCamposClasse=new HashMap<String, DAO_Parametro_Generico<?>>();
                try {            
                    for (Field field : _Field) {                                                           
                        try {

                            field.setAccessible(true);

                             hmCamposClasse.put(
                                     field.getName() ,
                                     new DAO_Parametro_Generico<Object>(field.getName(), field.get(obj))
                                     );
                            field.setAccessible(false);

                        } catch (IllegalArgumentException ex) {
                                LogDinnamus.Log(ex);
                        } catch (IllegalAccessException ex) {
                                LogDinnamus.Log(ex);
                          
                        }
                    }
                } catch (Exception exception) {
                     LogDinnamus.Log(exception);
                    
                }

                return hmCamposClasse;
        }
                
}
