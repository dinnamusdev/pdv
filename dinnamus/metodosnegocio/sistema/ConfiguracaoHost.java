/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.metodosnegocio.sistema;

import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.util.NamedParameter;
//import dinnamus.dao.servidor.DAO_Servidor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desenvolvedor
 */
public class ConfiguracaoHost {

    public static List getLojasDisponiveis() {
        return LojasDisponiveis;
    }

    
    private static void setLojasDisponiveis(List aLojasDisponiveis) {
        LojasDisponiveis = aLojasDisponiveis;
    }

    private static List LojasDisponiveis;
    public static int Verificar()
    {
               
        return  VerificarSituacaoConfHost();
        
    }

    /*public static List ListarLojas()
    {
        List lsLojas=null;
        
        lsLojas=DAO_Servidor.Pesquisa_Nomeada("lojas.findByAllSimples");
        
        setLojasDisponiveis(lsLojas); 
        
        return lsLojas;
        
    }*/
    public static ResultSet DadosPDV()
    {
        ResultSet rs=DAO_RepositorioLocal.GerarResultSet("select *  from off_ConfiguracaoEstacao");
        try {
            if (!rs.next()) {
                rs = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracaoHost.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;

    }
    public static boolean ConfigurarPDV(String cHost, int nLoja, int nCodigoCaixaPdv, int nCodigoOperadorCaixa , int nModoTrabalho)
    {
        boolean bRetorno=false;
        
        try {
        
        NamedParameter np= DAO_RepositorioLocal.CriarNamedStatment("insert into dinnamus.off_ConfiguracaoEstacao (Host,LojaAtual) values (:Host,:LojaAtual)");
        
        np.setString("Host", cHost);
        
        np.setInt("LojaAtual", nLoja);
        
        np.execute();
           
        bRetorno = DAO_RepositorioLocal.Commitar_Statment(); 
         
        } catch (Exception exception) {
            LogDinnamus.Log(exception);
            
        }

        return bRetorno;
    }
    public static int VerificarSituacaoConfHost()
    {
        
        int nRetorno = 0;
        
        ResultSet rsConf = DAO_RepositorioLocal.GerarResultSet("select LojaAtual from dinnamus.off_ConfiguracaoEstacao");
        
        try {   
            
            if(rsConf.next())
                nRetorno=rsConf.getInt("LojaAtual");                        
                
        
        } catch (SQLException ex) {
            LogDinnamus.Log(ex);
            nRetorno=-1;
        }
        return nRetorno;
    }
}

