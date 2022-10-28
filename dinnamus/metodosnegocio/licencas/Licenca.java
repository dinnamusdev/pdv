/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.metodosnegocio.licencas;

import br.String.ManipulacaoString;
import br.TratamentoNulo.TratamentoNulos;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.servidor.Dao_Jdbc_1;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.data.ManipularData;
import br.data.formatar.DataFormatar;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.manipulararquivos.ManipulacaoZIPUNZIP;
import br.seguranca.criptografia;
import MetodosDeNegocio.RepositorioLocal.DAO_RepositorioLocal_Inicializar;
import MetodosDeNegocio.Venda.pdvgerenciar;
import com.sun.rowset.CachedRowSetImpl;
import java.io.File;
import java.sql.Blob;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 *
 * @author Fernando
 */
public class Licenca {
    private static boolean LicencaTestada=false;
    private static Thread TarefeLicencas=null;
    private static boolean DataErrada=false;
    private static String DiretorioTemp = "";
    private static String IDContrato = "";
    private static String MSGErro ="";
    private static String MSGAviso ="";
    private static String MSGInfo ="";
    
    public static ResultSet ContratoRetaguarda(){
        ResultSet Ret = null;
        try {
            
             Ret = Dao_Jdbc_1.getConexao().GerarResultSet("select * from licenca_aplicacao order by ultimamodificacao desc");
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean AtualizarDataUltimoAcesso(){
        boolean Ret = false;
        try {
            ResultSet rsContratoRetaguarda=null;
            if(Sistema.isOnline()){
                 rsContratoRetaguarda =ContratoRetaguarda();
            }
            else{                
                rsContratoRetaguarda =DadosLicenca();
            }
            if(rsContratoRetaguarda.next()){
                String IDContratoRet = rsContratoRetaguarda.getString("idcontrato");
                String IDContratoRetNormal = criptografia.DesencriptRC4(IDContratoRet, "111177180");
                Ret = AtualizarDataUltimoAcesso(IDContratoRetNormal);
                crsDadosLicUltimoAcesso=null;
            }
            
                
              
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean AtualizarContratoPDV(){
        boolean Ret = false;
        try {
             if(pdvgerenciar.PdvOK()){
                  if(Sistema.isOnline()){
                      ResultSet rsContratoRetaguarda =ContratoRetaguarda();
                      ResultSet rsContratoPDV =DadosLicenca();
                      String IDContratoRet ="";
                      if(rsContratoRetaguarda.next()){
                            IDContratoRet = rsContratoRetaguarda.getString("idcontrato");                                            
                            String IDContratoRetNormal = criptografia.DesencriptRC4(IDContratoRet, "111177180");
                            if(rsContratoPDV.next()){
                                 String IDContratoPDV = rsContratoPDV.getString("idcontrato");
                                // if(!IDContratoRet.equalsIgnoreCase(IDContratoPDV)){
                                     DAO_RepositorioLocal.ExecutarComandoSimples("DELETE FROM LICENCA_APLICACAO");
                                     if(DAO_RepositorioLocal_Inicializar.AtualizarOnline("IDCONTRATO", "LICENCA_APLICACAO", true, null, null)>0){
                                         crsDadosLic=null; 
                                         rsContratoPDV =DadosLicenca();
                                          if(rsContratoPDV.next()){
                                             String NovoIDContratoPDV =  rsContratoPDV.getString("idcontrato");
                                             if(!NovoIDContratoPDV.equalsIgnoreCase(IDContratoPDV)){
                                                 AtualizarDataUltimoAcesso(IDContratoRetNormal); 
                                                 
                                             }
                                          }
                                     }                               
                                 //}E                                                  
                            }else{
                                  if(DAO_RepositorioLocal_Inicializar.AtualizarOnline("IDCONTRATO", "LICENCA_APLICACAO", true, null, null)>0){
                                      Ret = AtualizarDataUltimoAcesso(IDContratoRetNormal);
                                      
                                      //Ret=true;
                                  }
                            }
                      }                      
                  }
             }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        crsDadosLic=null;
        return Ret;
    }
    public static boolean ValidarInstalacao(){
        setLicencaTestada(false);  
        boolean ret = ValidarInstalacao_Acao();        
        setLicencaTestada(true);  
        if(ret){
           getTarefeLicencas();
        }
        
        return ret;
    }
    public static boolean ValidarInstalacao_Acao(){
        boolean bRet = false;
        try {
            if(DadosLicenca().next()){
               if(ValidarContrato()){
                   setIDContrato(AbrirDadosLicenca());
                   if(ValidarLiberacao()){
                       if(Sistema.isOnline()){
                          if(ExcedeuPontosPDV()){
                             setMSGErro("FOI EXCEDIDO O NÚMERO DE LICENÇAS DISPONÍVEIS PARA PDV [ "+ LicencasPDVContrato() +" ]");
                             return false;
                          }
                       }
                       if(!VerificarDataDoSistema()){
                           return false;
                       }else{
                           if(!AtualizarDataUltimoAcesso()){
                              return false; 
                           }
                       }
                       return true;
                   }               
               }
            }else{
                setMSGErro("O CONTRATO DE LICENCA NÃO FOI LOCALIZADO");
            }            

        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return bRet;
    }
    private static int LicencasPDVContrato(){
        int LicPDV =0;
        try {
            ArrayList<HashMap<String,String>> contrato = ObterDadosLicenca("DADOS_CONTRATO",getIDContrato());
            if(contrato.get(0).get("licpdv").equalsIgnoreCase("")){
                LicPDV=0;
            }else{
                LicPDV = Integer.parseInt(contrato.get(0).get("licpdv"));
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return LicPDV;
    }
    private static CachedRowSetImpl DadosLicenca(){
        return DadosLicenca(false);
    }
    private static CachedRowSetImpl crsDadosLic=null; 
    
    private static CachedRowSetImpl DadosLicenca(boolean  Posiciona){
        //ResultSet rsDadosLic = null;
        try {
            //if(Sistema.isOnline()){
            //    rs = Dao_Jdbc.getConexao().GerarResultSet("select * from licenca_aplicacao", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //}else{5
            if(crsDadosLic==null){
                ResultSet rsDadosLic = DAO_RepositorioLocal.GerarResultSet("select * from licenca_aplicacao ORDER BY ULTIMAMODIFICACAO DESC", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                crsDadosLic = new CachedRowSetImpl();
                crsDadosLic.populate(rsDadosLic);              
                
            }else{
                crsDadosLic.beforeFirst();
            }    
            //}
            if(Posiciona){
                crsDadosLic.next();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        return crsDadosLic;
    }   
    private static boolean ValidarContrato(){
        boolean bRet = false;
        try {
            String IDContrato = null;
            ResultSet rs = DadosLicenca();
            if(rs.next()){
                IDContrato = rs.getString("idcontrato");
                if(IDContrato==null){
                    setMSGErro("CODIGO DA LICENÇA FOI CORROMPIDO");
                }else{
                    String IDContratoNormal = criptografia.DesencriptRC4(IDContrato, "111177180");
                    String StatusContrato = rs.getString("licenca_status");
                    String StatusContratoNormal = criptografia.DesencriptRC4(StatusContrato, IDContratoNormal);
                    if(StatusContratoNormal.equalsIgnoreCase("valido")){
                        bRet=true;
                    }else{
                         setMSGErro("O CONTRATO NÃO É VALIDO");
                    }
                }
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return bRet;
    }
    
    public static Date DataExpiracao(){
        Date DataRetorno = new Date();
        String Retorno ="";
        try {
            ResultSet rsDadosLicenca = DadosLicenca(true);
                
            String LiberacaoAtual = TratamentoNulos.getTratarString().Tratar(rsDadosLicenca.getString("validade"),"");
            
            String Liberacao="";
            ArrayList<HashMap<String,String>> liberacoes = ObterDadosLicenca("LIBERACAO",getIDContrato());
            for (int i = 0; i < liberacoes.size(); i++) {
                if(LiberacaoAtual.equalsIgnoreCase("")){
                  Retorno = liberacoes.get(0).get("data_expirar");
                  break;
                }else{
                    Liberacao =liberacoes.get(i).get("liberacao");
                    String Parte1Liberacao = Liberacao.substring(0, 3);
                    String Parte2Liberacao = Liberacao.substring(Liberacao.length()-3, Liberacao.length());
                    if((Parte1Liberacao+Parte2Liberacao).equalsIgnoreCase(LiberacaoAtual)){
                        if(i<liberacoes.size()-1){
                            Retorno = liberacoes.get(i+1).get("data_expirar");
                        }else{                            
                            //na ultima liberacao, o vencimento é o termino do contrato
                            ArrayList<HashMap<String,String>> contrato = ObterDadosLicenca("DADOS_CONTRATO",IDContrato);
                            if(contrato.size()>0){
                               Retorno =contrato.get(0).get("termimnocontrato");
                            }
                        }
                        break;
                    }
                }
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        if(!Retorno.equalsIgnoreCase("")){
           DataRetorno = DataHora.getStringToData(Retorno, DataHora.FormatDataPadraoDAO);
        }else{
            DataRetorno=null;
        }
        
        
        return DataRetorno;
    }
    public static String CNPJContrato(){
        String Ret ="";
        try {
            
            ArrayList<HashMap<String,String>> contrato = ObterDadosLicenca("DADOS_CONTRATANTE",IDContrato);
            if(contrato.size()>0){
               Ret = ManipulacaoString.DeixarSomenteNumeros(contrato.get(0).get("cnpj"));
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static int PontosPDVEmUso(){
        int Pontos=0;
        try {
            ArrayList<String> conexoespdv = new ArrayList<String>();
            String BancoRetaguarda = Dao_Jdbc_1.getConexao().getCNX().getCatalog();
            String NomeHost ="";
            String NomeAPP="";
            String BancoAPP="";
            String SPID = "";
            ResultSet rsConexoesAoBanco = Dao_Jdbc_1.getConexao().GerarResultSet("sp_who2");
            while(rsConexoesAoBanco.next()){
                NomeHost =  TratamentoNulos.getTratarString().Tratar(rsConexoesAoBanco.getString("hostname"),"");
                NomeAPP =  TratamentoNulos.getTratarString().Tratar(rsConexoesAoBanco.getString("programName"),"");
                BancoAPP =  TratamentoNulos.getTratarString().Tratar(rsConexoesAoBanco.getString("dbname"),"");
                SPID =  TratamentoNulos.getTratarString().Tratar(rsConexoesAoBanco.getString("spid"),"");
                if(NomeAPP.trim().equalsIgnoreCase("jtds") && BancoAPP.trim().equalsIgnoreCase(BancoRetaguarda) ){
                    if(!conexoespdv.contains(SPID)){
                        conexoespdv.add(SPID);                        
                    }
                }
            }
            Pontos = conexoespdv.size();
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Pontos;
    }
    public static boolean ExcedeuPontosPDV(){
        try {
            int PontosPDVContrato = LicencasPDVContrato(); 
            int PontosPDVEmUso = PontosPDVEmUso();
            
            if(PontosPDVEmUso+1>PontosPDVContrato){
                return true;
            }
            
        } catch (Exception e) {
           LogDinnamus.Log(e, true);            
        }
        return false;
    }
    public static Long DiasExpirar(){
        Long DiasExpirar =0l;
        try {
                Date DataExpirar = DataExpiracao();

                if(DataExpirar==null){            
                    return 99999l;
                }
                Date DataUltimoAcesso = DataServidor();                
                if(DataUltimoAcesso==null){
                    return 99999l;
                }

                DiasExpirar = ManipularData.Diferenca(DataExpirar, DataUltimoAcesso);
                
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return DiasExpirar;
    }
    private static boolean ValidarLiberacao(){
        boolean bRet =false;
        try {            
            if(!getIDContrato().equalsIgnoreCase("")){                            
                
                Long DiasExpirar = DiasExpirar();
                if(DiasExpirar==9999l){
                   setMSGErro("A DATA DE VALIDADE DA LICENÇA É INVÁLIDA");
                   return false;
                }
                
                Date DataExpirar =  DataExpiracao();
                
                DataExpirar=(DataExpirar==null ? new Date() : DataExpirar);
                
                if(DiasExpirar>=0){
                    if(DiasExpirar>=0 && DiasExpirar <=5){
                       if(DiasExpirar==0){
                           setMSGAviso("O SISTEMA EXPIRA HOJE ÀS 23:59");                  
                       }else{
                          setMSGAviso("O SISTEMA VAI EXPIRA EM [ "+ DiasExpirar +" ] DIA(S) [ "+ DataHora.getCampoFormatado(DataHora.FormatDataPadrao, DataExpirar) +" ] ");                  
                       }
                    }else{
                        
                       setMSGInfo("O SISTEMA VAI EXPIRA EM [ "+ DiasExpirar +" ] DIA(S) [ "+ DataHora.getCampoFormatado(DataHora.FormatDataPadrao, DataExpirar) +" ] ");                       
                       
                    }
                    bRet=true;
                }else if(DiasExpirar<0){
                    setMSGErro("O SISTEMA EXPIROU HÁ [ " + DiasExpirar*-1 +" ] DIA(S) [ "+ DataHora.getCampoFormatado(DataHora.FormatDataPadrao, DataExpirar)  +" ]");
                    AtualizarDataUltimoAcesso();
                }else{
                    bRet=true;
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        return bRet;
                
    }
    public static Date DataServidor(){
        Date d = null;
        try {
            if(Sistema.isOnline()){
                /*ResultSet rsInfoServidor = DAO_RepositorioLocal.GerarResultSet("select CURRENT_DATE as DataServidor FROM LOJAS", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
                if(rsInfoServidor.next()){
                   d = new Date(rsInfoServidor.getDate("DataServidor").getTime());
                }*/
                d= new Date();
            }else{                
                d =DateFormat.getInstance().parse(DataHora.getData(DataHora.FormatDataPadrao , new Date()) + " 0:0 PM, PDT");    
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return d;
    }
    private static ArrayList<HashMap<String,String>> MontarDadosLicenca(String[] Dados, String Campos){
        ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String, String>>();
        try {
            String[] arCampos = Campos.replace("\"", Campos).split(";");
            //String[] arDados = Campos.replace("\"", Campos).split(";");
            String[] arItemDados=null;
            HashMap<String,String> item = new HashMap<String, String>();
            String ItemDados="";
            String Campo="";
            String Dado="";
                    
            for (int i = 0; i < Dados.length; i++) {
                ItemDados = Dados[i];
                arItemDados = ItemDados.split(";");
                item =new HashMap<String, String>();
                for (int j = 0; j < arCampos.length; j++) {
                    if(j < arItemDados.length){
                        Dado = arItemDados[j];
                    }else{
                        Dado="";
                    }
                    Campo = arCampos[j];                    
                    item.put(Campo.toLowerCase(), Dado);                    
                }
                al.add(item);                
            }                      
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return al;
    }
    private static ArrayList<HashMap<String,String>> ObterDadosLicenca(String Dados, String IDContrato){
         ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String, String>>();
        try {
                String NomeArquivoDados = getDiretorioTemp() +"contrato_"+ IDContrato +"_LICENCA_"+ Dados +".DATA";
                String NomeArquivoColunas = getDiretorioTemp() + "contrato_"+ IDContrato +"_LICENCA_"+ Dados +".COLUNAS";
                String ArquivoDados =  ManipulacaoArquivo2.LerArquivo(NomeArquivoDados, false);
                String ArquivoColunas = ManipulacaoArquivo2.LerArquivo(NomeArquivoColunas, false);
                String[] ArquivoDadosLinhas = null;
                if(ArquivoDados.length()>0){
                    ArquivoDadosLinhas = ArquivoDados.split("\n");
                    ArquivoDados="";
                    for (int i = 0; i < ArquivoDadosLinhas.length; i++) {
                        ArquivoDadosLinhas[i] = criptografia.DesencriptRC4( ArquivoDadosLinhas[i],"111177180").replace("%", "");                        
                    }
                }
                if(ArquivoColunas.length()>0){
                    ArquivoColunas= criptografia.DesencriptRC4(ArquivoColunas.split("\n")[0], "111177180");
                }
                al = MontarDadosLicenca(ArquivoDadosLinhas,ArquivoColunas);
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return al;
    }
    
    public static boolean AtualizarDataUltimoAcesso(String IdContratoSistema){
        try {
            Date dDataServidor = DataServidor();
            
            String Data = DataFormatar.Formatar_Data_Em_String_DDMMYYYY(dDataServidor).replace("/", "");

            String DataEncriptada = criptografia.EncriptRC4( Data,IdContratoSistema.equalsIgnoreCase("") ? getIDContrato() : IdContratoSistema);
            
            DAO_RepositorioLocal.ExecutarComandoSimples("update off_configuracaoestacao set lda='"+ DataEncriptada +"'", true);
            
            
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public static boolean  VerificarDataDoSistema(){
        try {
         
            Date dDataUltimoAcesso = DataUltimoAcesso(getIDContrato());
            
            if(dDataUltimoAcesso==null){
                setMSGErro("DATA DO ÚLTIMO ACESSO CORROMPIDA");
                return false;
            }
            Date dDataServidor  = DataServidor();
            
            if(dDataUltimoAcesso.getTime()>dDataServidor.getTime()){
                setMSGAviso("A DATA DO ULTIMO ACESSO FOI < "+ DataHora.getData(DataHora.FormatDataPadrao, dDataUltimoAcesso)  +
                          " > E A DATA DO COMPUTADOR É < " + DataHora.getData(DataHora.FormatDataPadrao, dDataServidor) + " >" );
                
                setDataErrada(true);
                return false;                
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return true;
    } 
    
    public static ArrayList<String> GerarSenha_E_ContraSenha(){
        
        ArrayList<String> ar = new ArrayList<String>();
        try {
            
            Integer Aleatorio = (new Random()).nextInt();
            if(Aleatorio<0){
                Aleatorio*=-1;
            }
            String ContraSenha = criptografia.EncriptRC4(Aleatorio.toString().substring(0, 3), getIDContrato());
            
            ar.add(Aleatorio.toString().substring(0, 3));
            
            ar.add(ContraSenha);
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return ar;
    
    }
    private static CachedRowSetImpl  crsDadosLicUltimoAcesso =null;
    public static Date DataUltimoAcesso(String IDContrato){
        Date RetornoData = new Date();
        try {
            String DataUltimoAcessoArmazenada ="";
            if(crsDadosLicUltimoAcesso==null){
                ResultSet rsDadosLicUltimoAcesso = DAO_RepositorioLocal.GerarResultSet("select lda from off_configuracaoestacao",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                crsDadosLicUltimoAcesso = new CachedRowSetImpl();
                crsDadosLicUltimoAcesso.populate(rsDadosLicUltimoAcesso);
            }else{
                crsDadosLicUltimoAcesso.beforeFirst();
            }                
            
            if(crsDadosLicUltimoAcesso.next()){
               String DataUltimoAcesso = crsDadosLicUltimoAcesso.getString("lda");
               if(DataUltimoAcesso == null){                   
                   RetornoData =  ManipularData.GerarData( 01, 01,2001);                   
               }else{
                   DataUltimoAcessoArmazenada = criptografia.DesencriptRC4(DataUltimoAcesso,IDContrato);                 
                   RetornoData = DataFormatar.Formatar_String_DDMMYYYY_Em_Data_2(DataUltimoAcessoArmazenada);
               }
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return RetornoData;
        
    }
    private static String AbrirDadosLicenca(){              
        try {
            CachedRowSetImpl rsDadosContrato  = DadosLicenca();
            
  //          Collection<?> toCollection = crsDadosContrato.toCollection();
            
//            ResultSet rsDadosContrato = crsDadosContrato.ge
            
            if(rsDadosContrato.next()){
                
                if(!ManipulacaoArquivo2.DiretorioExiste(getDiretorioTemp())){
                    ManipulacaoArquivo2.CriarDiretorio(getDiretorioTemp());
                }else{
                    ManipulacaoArquivo2.ExcluirTodosArquivoDoDiretorio(getDiretorioTemp());
                }
                
                String IDContratoNormal = criptografia.DesencriptRC4(rsDadosContrato.getString("idcontrato"), "111177180");
                
                String NomeArquivoZIP = getDiretorioTemp() + "contrato_" + IDContratoNormal + ".zip";
                //Object object = rsDadosContrato.getObject("licenca");
                
                Blob blob = rsDadosContrato.getBlob("licenca");
                Long tam = blob.length();
                byte[] ArquivoTZip =  blob.getBytes(1,tam.intValue());
                
                //byte[] teste = null;
                                 
                if(ArquivoTZip!=null){
                    if(ManipulacaoArquivo2.CriarArquivo(NomeArquivoZIP, ArquivoTZip)){
                        ManipulacaoZIPUNZIP licenca = new ManipulacaoZIPUNZIP();
                        File ArquivoLicenca = new File(NomeArquivoZIP);
                        List ConteudoLicencaZIP = licenca.listarEntradasZip(ArquivoLicenca);
                        if( ConteudoLicencaZIP.size()>0){
                            licenca.extrairZip(new File(getDiretorioTemp()));
                            return IDContratoNormal;
                        }                    
                    }
                }else{
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL OBTER OS DADOS DA LICENÇA DE USO", "AbrirDadosLicenca");
                }
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return "";
    }

    /**
     * @return the DiretorioTemp
     */
    public static String getDiretorioTemp() {
        if(DiretorioTemp.equalsIgnoreCase("")){
            DiretorioTemp=ManipulacaoArquivo2.DiretorioDeTrabalho() + ManipulacaoArquivo2.getSeparadorDiretorio() + "Temp" + ManipulacaoArquivo2.getSeparadorDiretorio();
        }
        return DiretorioTemp;
    }

    /**
     * @return the IDContrato
     */
    public static String getIDContrato() {
        return IDContrato;
    }

    /**
     * @param aIDContrato the IDContrato to set
     */
    public static void setIDContrato(String aIDContrato) {
        IDContrato = aIDContrato;
    }

    /**
     * @return the MSGErro
     */
    public static String getMSGErro() {
        return MSGErro;
    }

    /**
     * @param aMSGErro the MSGErro to set
     */
    public static void setMSGErro(String aMSGErro) {
        MSGErro = aMSGErro;
    }

    /**
     * @return the MSGAviso
     */
    public static String getMSGAviso() {
        return MSGAviso;
    }

    /**
     * @param aMSGAviso the MSGAviso to set
     */
    public static void setMSGAviso(String aMSGAviso) {
        MSGAviso = aMSGAviso;
    }

    /**
     * @return the MSGInfo
     */
    public static String getMSGInfo() {
        return MSGInfo;
    }

    /**
     * @param aMSGInfo the MSGInfo to set
     */
    public static void setMSGInfo(String aMSGInfo) {
        MSGInfo = aMSGInfo;
    }

    /**
     * @return the DataErrada
     */
    public static boolean isDataErrada() {
        return DataErrada;
    }

    /**
     * @param aDataErrada the DataErrada to set
     */
    public static void setDataErrada(boolean aDataErrada) {
        DataErrada = aDataErrada;
    }

    /**
     * @return the TarefeLicencas
     */
    public static Thread getTarefeLicencas() {
        if(TarefeLicencas==null && isLicencaTestada()){
            TarefeLicencas = new Thread("Gerenciador de Licenca"){
                public void run(){                    
                    while(true){
                        try {
                            Thread.sleep(10000);
                            
                            if(!ValidarLiberacao()){                                
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO( getMSGErro(), "LICENÇA INVÁLIDA");
                                System.exit(1);
                            }
                            
                            if(!VerificarDataDoSistema()){
                                //AtualizarDataUltimoAcesso();
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO( getMSGAviso(), "DATA INVÁLIDA");
                                System.exit(1);
                            }
                        } catch (Exception e) {
                            LogDinnamus.Log(e, true);
                        }
                    }
                }
            };
            TarefeLicencas.start();
        }
        
        return TarefeLicencas;
    }

    /**
     * @param aTarefeLicencas the TarefeLicencas to set
     */
    public static void setTarefeLicencas(Thread aTarefeLicencas) {
        TarefeLicencas = aTarefeLicencas;
    }

    /**
     * @return the LicencaTestada
     */
    public static boolean isLicencaTestada() {
        return LicencaTestada;
    }

    /**
     * @param aLicencaTestada the LicencaTestada to set
     */
    public static void setLicencaTestada(boolean aLicencaTestada) {
        LicencaTestada = aLicencaTestada;
    }

    /**
     * @param DiretorioTemp the DiretorioTemp to set
     */
   
    
}
