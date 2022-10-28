/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfce;

import MetodosDeNegocio.RepositorioLocal.DAO_RepositorioLocal_Inicializar;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.servidor.Dao_Jdbc_1;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.com.ui.frmMonitorProgresso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 *
 * @author Fernando
 */
public class NFCE_RepositorioLocal {
    private frmMonitorProgresso monitorProgresso;
    public  boolean PreparaRepositorioLocal_NFCe(int PDV, int Loja,boolean Forcar){
       boolean Ret = false;
       try {
                       
           monitorProgresso=new frmMonitorProgresso("Preparando Repositório Local de NFC-e",1,1,true);
           monitorProgresso.setVisible(true);
           monitorProgresso.setLocationRelativeTo(null);            
           monitorProgresso.setAlwaysOnTop(true);
           monitorProgresso.InicializarBarraProgresso(0,8);
           Savepoint psSincNFCe = DAO_RepositorioLocal.CriarPontoDeSalvamento("SincronizarNFCE");
           Ret = PreparaRepositorioLocal_NFCe_1(PDV, Loja, Forcar);
           if(Ret){
               DAO_RepositorioLocal.Commitar_Statment(psSincNFCe);
           }else{
               DAO_RepositorioLocal.RollBack_Statment(psSincNFCe);
           }
           monitorProgresso.dispose();           
           if(Ret){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("Repositório local de NFC-e Montado com sucesso", "NFC-e OK");
           }else{
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível montar o Repositório local de NFC-e", "Procedimento não realizado");
           }
         
           
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
       }
       return Ret;
   } 
    private boolean AjustarValorPK(String Tabela,String PK, Integer PDV, Integer Loja){
        boolean Ret = false;
        try {
            
            Long MaiorPK =0l;
            ResultSet rstmp =  DAO_RepositorioLocal.GerarResultSet("select max(" + PK +  " ) as MaiorPK from " + Tabela);
            
            if(rstmp.next()){
                MaiorPK = rstmp.getLong("MaiorPK");
                Long NovaPk = 0l;
                if(MaiorPK>0){
                    String PrimeiraPartePK = Loja.toString()  + "0"+PDV.toString() +"0";
                    String SegundaPartePK = MaiorPK.toString().substring(PrimeiraPartePK.length());
                    NovaPk = new Long(SegundaPartePK);
                    if(!DAO_RepositorioLocal.GerarResultSet("select 1 from SEQUENCIALTABELAS where nometabela ='" + Tabela + "'").next()){
                        Ret = DAO_RepositorioLocal.ExecutarComandoSimples("insert into SEQUENCIALTABELAS(nometabela,valor) values ('" + Tabela.toUpperCase() + "'," +  NovaPk +  ")");                    
                    }else{                        
                        Ret = DAO_RepositorioLocal.ExecutarComandoSimples("update SEQUENCIALTABELAS set valor="+NovaPk +" where nometabela='"+ Tabela +"'");                    
                    }
                    if(!Ret) {return false;}
                }
                Ret  = true;
            }            
        } catch (SQLException | NumberFormatException e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean PreparaRepositorioLocal_NFCe_1(int PDV, int Loja,boolean Forcar){
        boolean Ret = false;
        int reg =0;
        try {
            //1
            if(Forcar){
                DAO_RepositorioLocal.ExecutarComandoSimples("delete from off_nfce_config");
            }
            monitorProgresso.AdicionarValorPrincipal(1, "Importando Config NFCe-Lojas");         
            if(!DAO_RepositorioLocal.GerarResultSet("select 1 from off_nfce_config").next()){                
                reg=DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_config", true, null, monitorProgresso);
                if(reg<0){return false;}
            }
            if(!AjustarValorPK("off_nfce_config", "id", PDV, Loja)){return false;}           
            
            //2
            if(Forcar){
                DAO_RepositorioLocal.ExecutarComandoSimples("delete from off_nfce_pdv");
            }
            monitorProgresso.AdicionarValorPrincipal(1,"Importando Config NFCe-PDV");            
            if(!DAO_RepositorioLocal.GerarResultSet("select 1 from off_nfce_pdv").next()){
                reg =DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_pdv", true, null, monitorProgresso);
                if(reg<0){return false;}
            }
            if(!AjustarValorPK("off_nfce_pdv", "id", PDV, Loja)){return false;}
            //3
            if(Forcar){
                DAO_RepositorioLocal.ExecutarComandoSimples("delete from off_nfce_pdv_notas");
            }                        
            monitorProgresso.AdicionarValorPrincipal(1,"Importando NFC-e");
            if(!DAO_RepositorioLocal.GerarResultSet("SELECT 1 FROM off_nfce_pdv_notas notas INNER JOIN off_nfce_pdv pdv ON pdv.id=notas.id_nfce_pdv WHERE notas.pdv=" + PDV).next()){
                ResultSet rsNotas = Dao_Jdbc_1.getConexao().GerarResultSet("SELECT notas.* FROM off_nfce_pdv_notas notas INNER JOIN off_nfce_pdv pdv ON pdv.id=notas.id_nfce_pdv WHERE notas.pdv=" + PDV, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                reg= DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_pdv_notas", true, null, monitorProgresso,false,rsNotas);
                if(reg<0){return false;}
            }
            if(!AjustarValorPK("off_nfce_pdv_notas", "id", PDV, Loja)){return false;}
            // 4
            if(Forcar){
                DAO_RepositorioLocal.ExecutarComandoSimples("delete from off_nfce_pdv_notas_canc");
            }                        
            monitorProgresso.AdicionarValorPrincipal(1,"Importando NFC-e Canceladas");
            if(!DAO_RepositorioLocal.GerarResultSet("SELECT 1 FROM off_nfce_pdv_notas_canc canc INNER JOIN off_nfce_pdv_notas notas ON notas.id=canc.id_nfce_pdv_notas INNER JOIN off_nfce_pdv pdv ON pdv.id=notas.id_nfce_pdv WHERE notas.pdv=" + PDV).next()){
                ResultSet rsNotas = Dao_Jdbc_1.getConexao().GerarResultSet("SELECT canc.* FROM off_nfce_pdv_notas_canc canc INNER JOIN off_nfce_pdv_notas notas ON notas.id=canc.id_nfce_pdv_notas INNER JOIN off_nfce_pdv pdv ON pdv.id=notas.id_nfce_pdv WHERE notas.pdv=" + PDV, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                reg=DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_pdv_notas_canc", true, null, monitorProgresso,false,rsNotas);
                if(reg<0){return false;}
            }
            if(!AjustarValorPK("off_nfce_pdv_notas_canc", "id", PDV, Loja)){return false;}
            // 5
            if(Forcar){
                DAO_RepositorioLocal.ExecutarComandoSimples("delete from off_nfce_pdv_notas_cpl");
            }                        
            monitorProgresso.AdicionarValorPrincipal(1,"Importando NFC-e dados complementares");
            if(!DAO_RepositorioLocal.GerarResultSet("SELECT 1 FROM off_nfce_pdv_notas_cpl compl INNER JOIN off_nfce_pdv_notas notas ON notas.id=compl.id_nfce_pdv_notas INNER JOIN off_nfce_pdv pdv ON pdv.id=notas.id_nfce_pdv WHERE notas.pdv=" + PDV).next()){
                ResultSet rsNotas = Dao_Jdbc_1.getConexao().GerarResultSet("SELECT compl.* FROM off_nfce_pdv_notas_cpl compl INNER JOIN off_nfce_pdv_notas notas ON notas.id=compl.id_nfce_pdv_notas INNER JOIN off_nfce_pdv pdv ON pdv.id=notas.id_nfce_pdv WHERE notas.pdv=" + PDV, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                reg=DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_pdv_notas_cpl", true, null, monitorProgresso,false,rsNotas);
                if(reg<0){return false;}
            }
            if(!AjustarValorPK("off_nfce_pdv_notas_cpl", "id", PDV, Loja)){return false;}
             //6
            if(Forcar){
                DAO_RepositorioLocal.ExecutarComandoSimples("delete from off_nfce_inutilizar where pdv=" + PDV);
            }
            monitorProgresso.AdicionarValorPrincipal(1,"Importando NFCe-Inutilizadas");            
            if(!DAO_RepositorioLocal.GerarResultSet("SELECT 1 FROM off_nfce_inutilizar inutil INNER JOIN  off_nfce_pdv_notas notas ON notas.id=inutil.id_off_pdv_nota  where inutil.pdv=" + PDV).next()){
                ResultSet rsInutilizar =Dao_Jdbc_1.getConexao().GerarResultSet("SELECT inutil.* FROM off_nfce_inutilizar inutil INNER JOIN  off_nfce_pdv_notas notas ON notas.id=inutil.id_off_pdv_nota  where notas.pdv=" + PDV,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                reg=DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_inutilizar", true, null, monitorProgresso,false,rsInutilizar);
                if(reg<0){return false;}
            }
             if(!AjustarValorPK("off_nfce_inutilizar", "id", PDV, Loja)){return false;}
            
            //7
            monitorProgresso.AdicionarValorPrincipal(1,"Importando Histórico de Contingências");            
            if(!DAO_RepositorioLocal.GerarResultSet("select 1 from off_nfce_contigencia where pdv=" + PDV).next()){
                ResultSet rscontigencia =Dao_Jdbc_1.getConexao().GerarResultSet("select * from off_nfce_contigencia where pdv=" + PDV,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                reg=DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_contigencia", true, null, monitorProgresso,false,rscontigencia);
                if(reg<0){return false;}
            }
            if(!AjustarValorPK("off_nfce_contigencia", "id", PDV, Loja)){return false;}
            /*
            if(!DAO_RepositorioLocal.GerarResultSet("select 1 from off_nfce_pdv_notas_canc").next()){
                DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_pdv_notas_canc", true, null, monitorProgresso);
            }
              if(!DAO_RepositorioLocal.GerarResultSet("select 1 from off_nfce_pdv_notas_cpl").next()){
                DAO_RepositorioLocal_Inicializar.AtualizarOnline("id", "off_nfce_pdv_notas_cpl", true, null, monitorProgresso);
            }
            * 
            * 
*/
            monitorProgresso.AdicionarValorPrincipal(1,"Concluido");
            
            Ret=true;
   
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
}
