/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.dao.servidor;

/**
 *
 * @author desenvolvedor
 */
public class DAO_Servidor {

/*    private static EntityManagerFactory efDinnamuS;    
    private static EntityManager emDinnamus;
    private static EntityTransaction txDinnamus;
    private static Query qryDinnamuS;
    
    public static boolean Iniciar()
    {
        boolean bRetorno=true;
        
        try {
            
            efDinnamuS=Persistence.createEntityManagerFactory("DinnamuSPU",getConfiguracaoServidor());
             
            bRetorno=isConectaded();
            
            Sistema.setOnline(bRetorno);
            
        } catch (Exception exception) {
            //exception.printStackTrace();
            LogDinnamus.Log(exception);

            bRetorno=false;            
        }             
        return bRetorno;
    }

    public static boolean isConectaded()
    {
            Boolean bRetorno=true;
            try {

                AbrirEntityManager().createNativeQuery("select 1").getResultList();
                
            } catch (Exception exception) {
                
                   bRetorno=false;
                   LogDinnamus.Log(exception);
            }
    
            return bRetorno;
            
    }
    public static Map getConfiguracaoServidor(){   
  
        Map prop = new HashMap();   
        try {        
            Map<String,String> hmServidor=VerificarArquivoCFG.getHmServidores().get("Servidor0");
            prop.put("hibernate.connection.url", "jdbc:jtds:sqlserver://"+ hmServidor.get("Host")  + "/" + hmServidor.get("Banco"));   
            prop.put("hibernate.connection.username",hmServidor.get("Usuario"));
            prop.put("hibernate.connection.password",hmServidor.get("Senha"));
        } catch (Exception exception) {
                       
             LogDinnamus.Log(exception);


             
        }
        return prop;   
    }
    public static  List Pesquisa_Nomeada(String cNomePesquisa, DAO_Parametro_Generico<?>[] dpParametros )
    {
        List _list=null;
        try {
        
            emDinnamus=AbrirEntityManager();

            qryDinnamuS = emDinnamus.createNamedQuery(cNomePesquisa);
            
            if(dpParametros!=null)
            {
                for (int i = 1; i <= dpParametros.length; i++) {

                    qryDinnamuS.setParameter(dpParametros[i-1].getNomeParamentro(), dpParametros[i-1].getGValorParamentro() );
                }
            }
            _list = qryDinnamuS.getResultList();
            
            FecharEntityManager();
        }
        catch (Exception exception) {
             LogDinnamus.Log(exception);

        }
                      
        return _list;
        
    }
    public static List PesquisarSQLNativo(String cQuery , Class cl)
    {
        List ls=null;
        try {
               
            emDinnamus=AbrirEntityManager();
            ls = emDinnamus.createNativeQuery(cQuery, cl ).getResultList();
            FecharEntityManager();
            
        } catch (Exception exception) {
                LogDinnamus.Log(exception);
        }        
        return ls;
    }
    public static List PesquisarSQLNativo(String cQuery)
    {
        List ls=null;
        try {

            emDinnamus=AbrirEntityManager();
            ls = emDinnamus.createNativeQuery(cQuery).getResultList();
            FecharEntityManager();

        } catch (Exception exception) {
                LogDinnamus.Log(exception);
        }
        return ls;
    }
    public static  List Pesquisa_Nomeada(String cNomePesquisa)
    {
       DAO_Parametro_Generico<?>[] dpParametros=null;        
        
       return Pesquisa_Nomeada(cNomePesquisa, dpParametros );
        
    }
            
    public static boolean Persist(Object obj)
    {
        boolean bRetorno=true;
        
        try {

            emDinnamus=AbrirEntityManager();
            
            txDinnamus=AbrirTransacao(emDinnamus);        
            
            txDinnamus.begin();
            
            emDinnamus.persist(obj);
            
            txDinnamus.commit();
            
            FecharEntityManager();
            
        } catch (Exception exception) {
                LogDinnamus.Log(exception);

                bRetorno=false;
                try {
                       txDinnamus.rollback();
                       
                } catch (Exception exception1) {
                        LogDinnamus.Log(exception1);

                }

                
                        
        }

        return bRetorno;
    }
    private static EntityManager AbrirEntityManager()
    {
        EntityManager _em;
        
        try {

            _em=efDinnamuS.createEntityManager();
            
        } catch (Exception exception) {
                LogDinnamus.Log(exception);
                _em=null;
        }
        
        return _em;
        
    }
    private static EntityTransaction AbrirTransacao(EntityManager em)
    {
    
        EntityTransaction _tx;
        try {
                _tx=em.getTransaction();
            
        } catch (Exception exception) {
                 LogDinnamus.Log(exception);
                _tx=null;
        }
        
        return _tx;
        
        
        
        
    }
    
    private static boolean FecharEntityManager()
    {
        boolean bRetorno=true;
        
        try {

            emDinnamus.close();
            
        } catch (Exception exception) {
               LogDinnamus.Log(exception);

                bRetorno=false;
        }
        
        return bRetorno;
        
    }
    public static void Terminar()
    {    
        try {
            efDinnamuS.close();

        } catch (Exception exception) {
                 LogDinnamus.Log(exception);

        }

         
    }

    public static Map<String,DAO_Parametro_Generico<?>> ConverterObjetoEmColecao(Object obj)
        {   
                Field[] _Field=obj.getClass().getDeclaredFields();
                Map<String,DAO_Parametro_Generico<?>> hmCamposClasse=new HashMap<String, DAO_Parametro_Generico<?>>();
                Object objValorCampo=new Object();
                String cTipoCampo="",cNomeCampo="";
                try {            
                    for (Field field : _Field) {                                                           
                        try {
                            
                            cNomeCampo=field.getName().toUpperCase();
                            if(!cNomeCampo.equals("SERIALVERSIONUID"))
                            {
                                 field.setAccessible(true);
                                 

                                 if(field.get(obj)!=null)                             
                                    objValorCampo = field.get(obj);   
                                 else       
                                 { 
                                    cTipoCampo=field.getType().getName().toUpperCase();

                                    if (cTipoCampo.equals("JAVA.MATH.BIGDECIMAL"))                                
                                        objValorCampo=new BigDecimal(0);                                
                                    else if(cTipoCampo.equals("JAVA.LANG.BOOLEAN"))                                
                                         objValorCampo= new Boolean(false);
                                    else if(cTipoCampo.equals("JAVA.LANG.SHORT"))                                
                                         objValorCampo= new Short("0");
                                    else if(cTipoCampo.equals("JAVA.LANG.DOUBLE"))                                
                                         objValorCampo= new Double(0);                                
                                    else if(cTipoCampo.equals("JAVA.LANG.INTEGER"))                                
                                         objValorCampo= new Integer(0);
                                    else if(cTipoCampo.equals("JAVA.LANG.FLOAT"))                                
                                         objValorCampo= new Float(0);                                    
                                    else if(cTipoCampo.equals("JAVA.UTIL.DATE"))                                
                                         objValorCampo= new Date(0);                                
                                    else
                                           objValorCampo = field.getType().newInstance();
                                  }

                                  hmCamposClasse.put(
                                         cNomeCampo ,
                                         new DAO_Parametro_Generico<Object>(field.getName(), objValorCampo)
                                         );

                                field.setAccessible(false);
                            }

                        } catch (IllegalArgumentException ex) {
                            LogDinnamus.Log(ex);
                            hmCamposClasse=null;
                        } catch (IllegalAccessException ex) {
                             LogDinnamus.Log(ex);
                            hmCamposClasse=null;
                        }
                    }
                } catch (Exception exception) {
                     LogDinnamus.Log(exception);
                    hmCamposClasse=null;
                }

                return hmCamposClasse;
        }

*/
}
