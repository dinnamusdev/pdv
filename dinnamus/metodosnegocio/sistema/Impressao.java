/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.metodosnegocio.sistema;



import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.util.NamedParameter;
import MetodosDeNegocio.Venda.pdvgerenciar;
import java.sql.ResultSet;
import javax.naming.NameParser;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 *
 * @author desenvolvedor
 */
public class Impressao {

        private static PrintService[] ps=null;
        
        public static PrintService[] ListaImpressorasInstaladas() {
               try {          
                ps = PrintServiceLookup.lookupPrintServices(null, null);
               
               } catch (Exception exception) {
                  ps=null;  
                  LogDinnamus.Log(exception);
               }
               return ps;   
        }
        public static ResultSet ImpressorasDefinidas(String cFinalidade){
            ResultSet rs = null;
            try {
                    rs = DAO_RepositorioLocal.GerarResultSet("SELECT off_impressoras.CodigoImpressora as cod, off_impressoras.NomeImpressora as Impressora, off_finalidadecomprovantes.FinalidadeComprovante as Finalidade , off_impressoras.PORTA " +
                            "FROM off_impressoras INNER JOIN "  +
                      "off_finalidadecomprovantes ON off_impressoras.CodigoTipoComprovante = off_finalidadecomprovantes.CodigoTipoComprovante" +
                      (cFinalidade.toString().length()>0 ? " where off_finalidadecomprovantes.FinalidadeComprovante='"+ cFinalidade +"'" : "")
                      ,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
            } catch (Exception e) {
                LogDinnamus.Log(e);
            }
            return rs;
        }
        public static ResultSet ImpressorasDefinidas(){            
            return ImpressorasDefinidas("");
        }
        public static boolean  Excluir_ImpressorasDefinidas(Integer nCodigo){

            try {
                 return DAO_RepositorioLocal.ExecutarComandoSimples("delete from off_impressoras where codigoimpressora=" + nCodigo.toString());
            } catch (Exception e) {
                LogDinnamus.Log(e);
                return false;
            }

        }
        public static ResultSet FinalidadesComprovantes(){
            ResultSet rs = null;
            try {
                    rs = DAO_RepositorioLocal.GerarResultSet("select * from off_finalidadecomprovantes");
            } catch (Exception e) {
                LogDinnamus.Log(e);
            }
            return rs;
        }
        public static boolean IncluirImpressora(String cNomeImpressora, String cPorta, int nCodigoTipoComprovante ){
            boolean bRet=false;
            try {


                String cSql = "insert into off_impressoras(CodigoImpressora,NomeImpressora,porta,CodigoTipoComprovante)"+
                         " values (:codigoimpressora,:nomeimpressora,:porta,:codigotipocomprovante)".toLowerCase();

                NamedParameter np = new NamedParameter(DAO_RepositorioLocal.getCnRepLocal(), cSql);
                np.setInt("codigoimpressora", DAO_RepositorioLocal.NovoValorIdentidade("off_impressoras", Sistema.getLojaAtual(),pdvgerenciar.CodigoPDV()).intValue() );
                np.setString("nomeimpressora", cNomeImpressora);
                np.setString("porta", cPorta);
                np.setInt("codigotipocomprovante", nCodigoTipoComprovante);
                np.execute();
                DAO_RepositorioLocal.Commitar_Statment();
                bRet=true;

            } catch (Exception e) {
                LogDinnamus.Log(e);
            }
            return bRet;
        }
        
        
        
}
