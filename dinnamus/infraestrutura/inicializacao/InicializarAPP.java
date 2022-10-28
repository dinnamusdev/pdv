/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.infraestrutura.inicializacao;


import br.infraestrurura.inicializacao.VerificarArquivoCFG;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.servidor.Dao_Jdbc_1;
import br.com.ui.MetodosUI_Auxiliares_1;
import MetodosDeNegocio.RepositorioLocal.DAO_RepositorioLocal_Inicializar;
import br.manipulararquivos.ManipulacaoArquivo2;
import MetodosDeNegocio.Sincronismo.Sincronismo;
import MetodosDeNegocio.Sincronismo.VerificarStatusServidor;
import dinnamus.metodosnegocio.licencas.Licenca;
import dinnamus.metodosnegocio.sistema.ConfiguracaoHost;
import MetodosDeNegocio.Venda.pdvgerenciar;
import br.TratamentoNulo.TratamentoNulos;
import static dinnamus.metodosnegocio.licencas.Licenca.getTarefeLicencas;

import dinnamus.ui.InteracaoUsuario.frmApresentacao;
import dinnamus.ui.infraestrutura.frmGerarCFGDinnamuS;
import dinnamus.ui.infraestrutura.frmSelecionarLojaAtual;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author desenvolvedor
 */

public class InicializarAPP {
       private static frmApresentacao  _frmApresentacao=new frmApresentacao();
       private static boolean bConectouServidor=false;       
       public static boolean Iniciar(){

          boolean bRetorno=false; 
         _frmApresentacao=(frmApresentacao) MetodosUI_Auxiliares_1.CentrarFrame(_frmApresentacao,Toolkit.getDefaultToolkit());  
         _frmApresentacao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         _frmApresentacao.setVisible(true);
         
         if (!LogDinnamus.Iniciar())
             JOptionPane.showConfirmDialog(_frmApresentacao, "O sistema de Log não pode ser iniciado. ", "Problema no sistema de Log",1);         
         
                  
         while(!bRetorno)
         {           
            bRetorno=VerificarArquivoCFG.Executar();
            if(bRetorno)
            {
                System.out.print("Arquivo Encontrado e verificado com sucesso");
               
            }
            else
            {   
                String MSGACfg = VerificarArquivoCFG.getMensagemAOUsuario();
                if(MSGACfg.length()>0){
                    if(!MSGACfg.toLowerCase().contains("não localizado")){
                        JOptionPane.showConfirmDialog(_frmApresentacao, VerificarArquivoCFG.getMensagemAOUsuario() + "\nCONTATE O SUPORTE", "PROBLEMAS NA CONFIGURAÇÃO",2);
                        System.exit(0);
                    }
                }
                int nRetorno=JOptionPane.showConfirmDialog(_frmApresentacao, "Deseja iniciar o módulo de configuração?", "INSTALAÇÃO DO PDV DINNAMUS 2.0",2);
                if(nRetorno==0)
                {
                    System.out.print(VerificarArquivoCFG.getMensagemAOUsuario());                 
                    frmGerarCFGDinnamuS _frmGerarCFGDinnamuS=new frmGerarCFGDinnamuS(_frmApresentacao, true);
                    _frmGerarCFGDinnamuS.setLocationRelativeTo(null);                    
                    _frmGerarCFGDinnamuS.setVisible(true);            
                }
                else
                    System.exit(0);
            }
         }
         if(bRetorno)
         {
            Map<String,String> hmServidor=VerificarArquivoCFG.getHmServidores().get("Servidor0");
            bConectouServidor=Dao_Jdbc_1.getConexao().AbrirCnx(hmServidor,true);
            Sistema.setOnline(bConectouServidor);
            
            //Map<String,String> hmServidor=VerificarArquivoCFG.getHmServidores().get("Servidor0");
           
            bRetorno=true;
            (new Thread(new VerificarStatusServidor() ,"VerificarStatusServidor")).start();            
            
            if (!DAO_RepositorioLocal_Inicializar.VerificarRepositorioLocal(VerificarArquivoCFG.getHmRepositorioLocal(),bConectouServidor) )
            {
               JOptionPane.showMessageDialog(_frmApresentacao,"Não foi possível conectar o repositório Local");
               bRetorno=false;
               _frmApresentacao.dispose();
               return false;
            }
            else
            {
               
               if(hmServidor.get("TipoInstalacao").equalsIgnoreCase("PDV"))
               {
                   Sistema.setDataUltimoSinc(DAO_RepositorioLocal_Inicializar.getUltimoSinc());
                   //boolean bBulkInsert=false;
                   String cNomeArquivoCarga="";

                   if(Sistema.isInicializacaoDoPdv()){
                       String cRet=MetodosUI_Auxiliares_1.InputBox(_frmApresentacao, "Foi identificado que esta é A carga inicial do PDV\nInforme o procedimento que será usado para iniciar o PDV\n(1)-Online\n(2)-Arquivo de Carga\n(3)-Carga Automatica", "Inicialização do PDV", "INFO",new Object[]{"(1)-Online","(2)-Arquivo de Carga","(3) - Carga Automatica"},"(1)-Online");
                       if(cRet==null){
                           return false;
                       }
                       if(cRet.equalsIgnoreCase("(2)-Arquivo de Carga") || cRet.equalsIgnoreCase("(3) - Carga Automatica"))
                       {
                          if(cRet.equalsIgnoreCase("(2)-Arquivo de Carga")){ 
                             cNomeArquivoCarga= MetodosUI_Auxiliares_1.ListarArquivos(_frmApresentacao, ManipulacaoArquivo2.DiretorioDeTrabalho(), "*.zip", "Arquivo de Carga");
                          }else{
                              if(!GerarCargaInicial.Gerar(true, true)){
                                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(_frmApresentacao, "Não foi possível gerar o arquivo de carga direto pelo pdv", "Falha na Carga PDV Automática", "ERROR");
                                 _frmApresentacao.dispose();
                                 return false;
                              }else{
                                  cNomeArquivoCarga = GerarCargaInicial.ArquivoGerado;
                              }
                          }
                          if(ManipulacaoArquivo2.ArquivoExiste(cNomeArquivoCarga, false))
                          {
                              bRetorno=true;
                          }
                          else
                          {
                               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(_frmApresentacao, "Não foi possível localizar o arquivo informado", "Falha na Carga PDV", "ERROR");
                               _frmApresentacao.dispose();
                               return false;
                          }

                       }
                   }
                   
                   if(bRetorno )
                   {
                       if(!pdvgerenciar.PdvOK() && Sistema.isInicializacaoDoPdv() ){
                            bRetorno=InicializarServidor.Inicializar(Sistema.getDataUltimoSinc(),cNomeArquivoCarga);
                            if(!bRetorno){
                               br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível realizar o sincronismo inicial com o servidor","INICIALIZAÇÃO DO PDV");
                               _frmApresentacao.dispose();
                               return false;
                            }                                                        
                       }                      
                       Sistema.setInicializacaoDoPdv(false);                           
      
                   }
               }
            }
            bRetorno=false;
            int nLojaAtual=0;
            while (!bRetorno)
            {
                nLojaAtual=ConfiguracaoHost.Verificar();
                if(nLojaAtual<0)
                   break;
                else if(nLojaAtual==0)
                {
                    
                  if(!Sistema.isOnline()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O PDV ESTÁ EM INICIALIZAÇÃO E PRECISA COMUNICAR COM O SERVIDOR.", "PDV SEM COMUNICAÇÃO COM O SERVIDOR");
                    return false;
                  }  
                  int nRetorno=JOptionPane.showConfirmDialog(_frmApresentacao, "Para iniciar o DINNAMUS, você precisa realizar a customização do ambiente de trabalho. Deseja fazer isso agora ?", "Customização do Ambiente",2);
                  if(nRetorno==0)
                  {
                    frmSelecionarLojaAtual workStation=new frmSelecionarLojaAtual(_frmApresentacao, true);
                    workStation.setLocationRelativeTo(null);
                    workStation.setVisible(true);
                    //if(DAO_RepositorioLocal_Inicializar.AtualizarOnline("IDCONTRATO", "LICENCA_APLICACAO", true, null, null)<=0){
                    //    MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível carregar a licença de uso", "LICENÇA NÃO CARREGADA");
                    //    return false;
                    //}         
                  }
                  else
                  {
                    bRetorno=false;
                    break;
                  }
                }
                else
                {
                    Sistema.setNLojaAtual(nLojaAtual);
                    bRetorno=true;
                }
            }
     }
     else
     {
        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(_frmApresentacao, "Não foi possível estabelecer comunicação com o servidor", "Falha na conexão", "AVISO");
     }
        if (pdvgerenciar.PdvOK()) {
            if (Sistema.isOnline()) {
                if (!Sistema.isInicializacaoDoPdv()) {     
                    int TotalRegistro=0;
                    HashMap<String,Integer> hmTotalRegistro = DAO_RepositorioLocal_Inicializar.VerificarPendenciasSincronismo_Cadastro(Sistema.getDataUltimoSinc(), "", false, false);
                   
                    for (Iterator it =  hmTotalRegistro.values().iterator(); it.hasNext();) {
                       TotalRegistro =TotalRegistro+  Integer.valueOf(it.next().toString());

                    }
                   
                    if (TotalRegistro > 0d) {
                        Object[] op = null;
                        if (TotalRegistro > 1000) {
                            op = new Object[]{"(1)-Online", "(2)-Carga Automatica"};

                            String cRet = MetodosUI_Auxiliares_1.InputBox(_frmApresentacao, "Foi(ram) identificado(s) [" + TotalRegistro + "] registros modificados no servidor\n\nInforme o procedimento que deseja usar para atualizar o PDV\n(1)-Online\n(2)-Carga Automatica", "Atualização do PDV", "INFO", new Object[]{"(1)-Online", "(2)-Carga Automatica"}, "(2)-Carga Automatica");

                            cRet = TratamentoNulos.getTratarString().Tratar(cRet, "");

                            if (cRet.equalsIgnoreCase("(1)-Online")) {

                                bRetorno = InicializarServidor.Inicializar(Sistema.getDataUltimoSinc(), "");
                                if (!bRetorno) {
                                    br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível realizar o sincronismo ONLINE com o servidor", "ATUALIZAÇÃO DO PDV");
                                    return false;
                                }
                            } else if (cRet.equalsIgnoreCase("(2)-Carga Automatica")) {
                                if (!GerarCargaInicial.Gerar(true,hmTotalRegistro,true)) {
                                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível gerar o arquivo de carga direto pelo pdv", "ATUALIZAÇÃO DO PDV");
                                    //_frmApresentacao.dispose();
                                    return false;
                                } else {
                                    String cNomeArquivoCarga = GerarCargaInicial.ArquivoGerado;
                                    if (ManipulacaoArquivo2.ArquivoExiste(cNomeArquivoCarga, false)) {
                                        bRetorno = InicializarServidor.Inicializar(Sistema.getDataUltimoSinc(), cNomeArquivoCarga);
                                    }
                                    
                                    if (!bRetorno) {
                                        br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível realizar o sincronismo automático", "ATUALIZAÇÃO DO PDV");
                                        return false;
                                    }
                                }
                            }
                        } else {
                            bRetorno = InicializarServidor.Inicializar(Sistema.getDataUltimoSinc(), "");
                            if (!bRetorno) {
                                br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível realizar o sincronismo ONLINE com o servidor", "ATUALIZAÇÃO DO PDV");
                                return false;
                            }
                        }
                    }
                }
            }
        }        
        
     Licenca.AtualizarContratoPDV();     
     ValidarInstalacao();
    _frmApresentacao.dispose();
     return bRetorno;
  }    /*  
   public boolean CorrigirNFCEIDDuplicados(){
       boolean Ret = false;
       try {
           
           ResultSet rsPesquisa = DAO_RepositorioLocal.GerarResultSet("select * from off_scripts where id=4 and script='select * from off_nfce_pdv_notas where 1<>1'");
           if(!rsPesquisa.next()){
               ResultSet rsNFECE  = Dao_Jdbc.getConexao().GerarResultSet("select * from off_nfce_pdv_notas where ")
           }
           
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
       }
       return Ret;
   }*/
   public static void ValidarInstalacao(){
        try {
            if(!Licenca.ValidarInstalacao()){             
                if(Licenca.isDataErrada()){
                   String opcao  =br.com.ui.MetodosUI_Auxiliares_1.InputBox(null, Licenca.getMSGAviso() + "\n\nO QUE DESEJA FAZER ?", "Licença de Uso - Data Errada", "AVISO", new Object[]{"AJUSTAR DATA","LIBERAR ACESSO"}, "AJUSTAR DATA");
                   if(opcao!=null){
                       if(opcao.equalsIgnoreCase("AJUSTAR DATA")){
                           br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O SISTEMA SERÁ ENCERRADO AGORA. \n\nAJUSTE A DATA DO COMPUTADOR E RETORNE AO PDV", "AJUSTAR DATA");
                           System.exit(0);                            
                       }else{
                           ArrayList<String> codigos = new ArrayList<String>();

                           codigos = Licenca.GerarSenha_E_ContraSenha();
                           if(codigos.size()>0){
                               String RetornoLiberacao  =br.com.ui.MetodosUI_Auxiliares_1.InputBox(null, "INFORME ESTE CÓDIGO [ "+  codigos.get(1) +" ] \n\nAO SUPORTE E RECEBA A LIBERAÇÃO", "LIBERAR ACESSO AO PDV", "INFO");

                               if(RetornoLiberacao==null){
                                   br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O SISTEMA SERÁ ENCERRADO AGORA", "DATA ERRADA");
                                   System.exit(0);                            
                               }else{
                                   if(!RetornoLiberacao.trim().equalsIgnoreCase(codigos.get(0).trim())){
                                       br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "CÓDIGO DE LIBERAÇÃO INVÁLIDO \n\nO SISTEMA SERÁ ENCERRADO AGORA", "DATA ERRADA");
                                           System.exit(0);
                                   }else{
                                       if(Licenca.AtualizarDataUltimoAcesso()){
                                          br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "DATA AJUSTADA COM SUCESSO\n\nBOM TRABALHO!!!", "LIBERAÇÃO OK");
                                          getTarefeLicencas();
                                       }else{
                                          br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL AJUSTA A DATA\n\nO SISTEMA SERÁ ENCERRADO AGORA", "DATA ERRADA");
                                          System.exit(0);
                                       }                                        
                                   }
                               }                                
                           }    
                       }
                   }else{
                       br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O SISTEMA SERÁ ENCERRADO AGORA", "DATA ERRADA");
                       System.exit(0);                                                
                   }
                   }else{
                       String MSG = Licenca.getMSGErro();
                       if(MSG.trim().length()>0){
                        br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, Licenca.getMSGErro(), "Licença de Uso");
                       }
                       System.exit(0);  
                    }

             }else{
                if(Licenca.getMSGAviso().length()>0){
                      br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, Licenca.getMSGAviso(), "Licença de Uso");
                }
            }   
            //return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            //return false;
        }
    }
}
