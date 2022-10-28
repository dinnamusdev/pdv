/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.metodosnegocio.publicidade;

import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import dinnamus.ui.InteracaoUsuario.publicidade.frmPublicidade;
import static java.lang.Thread.sleep;
import java.sql.ResultSet;

/**
 *
 * @author Fernando
 */
public class Publicidade {
    private Thread TarefaPublicidade = null; 
    public boolean Executa = true;
    public String TeclasLidas ="";
    public Long HostEmAtividade=0l;
    
    private boolean AcionarPublicidade(){
        try {
            TeclasLidas="";
            frmPublicidade publicidade = new frmPublicidade(null, true);
            publicidade.setVisible(true);
            TeclasLidas = frmPublicidade.TeclasLidas;
            //btFechar2ActionPerformed(null);
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
   public boolean IniciarThreadPublicidade(){
        try {
            
            TarefaPublicidade = new Thread("ThreadPublicidade"){
                        public void run() {
                                System.out.println("INICIANDO: IniciarThreadPublicidade");
              
                                while(Executa){                                    
                                    System.out.println("EXECUTANDO: IniciarThreadPublicidade");
                                    if(System.currentTimeMillis()-HostEmAtividade>10000){
                                       AcionarPublicidade();
                                       Executa=false;
                                    }else{
                                        try {
                                            sleep(100);
                                        } catch (InterruptedException ex) {
                                            LogDinnamus.Log(ex, false);
                                        }
                                    }
                                }
                                System.out.println("TERMINANDO: IniciarThreadPublicidade");
                        
                        }
            };
            TarefaPublicidade.start();
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public static ResultSet ListarDeImagens(){
        ResultSet rsPublicidadeImagens = null;
        try {
          
            
            rsPublicidadeImagens = DAO_RepositorioLocal.GerarResultSet("select * from off_publicidade where ativo=1 order by ordem", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            
            return rsPublicidadeImagens;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return null;
        }
    }
    
    public static int ContarImagens(){
        int nTotalImagens =0;
        try {
          
            
            ResultSet rs = DAO_RepositorioLocal.GerarResultSet("select count(*) TotalImagens from off_publicidade where ativo=1 ordet by ordem", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if(rs.next()){
                nTotalImagens= rs.getInt("TotalImagens");
            
            }
            
            
            return nTotalImagens;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return 0;
        }
    }
}
