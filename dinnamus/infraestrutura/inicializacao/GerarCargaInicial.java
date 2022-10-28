/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.infraestrutura.inicializacao;

import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.manipulararquivos.ManipulacaoZIPUNZIP;
import br.com.ui.frmMonitorProgresso;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Fernando
 */
public class GerarCargaInicial {
    private static String cDiretorioCarga = ManipulacaoArquivo2.DiretorioDeTrabalho() + ManipulacaoArquivo2.getSeparadorDiretorio() + "EnviarPDV";
    private static frmMonitorProgresso progresso= null;
    public static String ArquivoGerado="";
    public static boolean bret =false;
    public static int i =0;
    public static String cNomeTabela="";
    public static boolean Gerar(boolean silencioso) {    
        return Gerar(silencioso, false);
    }
    
    public static boolean Gerar(boolean silencioso, boolean incluircoluna) {      
        return Gerar(silencioso,null,incluircoluna);
    }
    
     public static boolean Gerar(boolean silencioso, HashMap<String,Integer> tabelas) {    
         return Gerar(silencioso, tabelas, false);
     }  
    public static boolean Gerar(boolean silencioso, HashMap<String,Integer> tabelas, boolean incluircolunas) {      
        
               bret= Gerar_Acao(silencioso,tabelas,incluircolunas);
               progresso.dispose();
               if(bret){
                   if(!silencioso){
                      MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Arquivo de carga gerado["+ GerarCargaInicial.ArquivoGerado +"] com sucesso ", "Carga do PDV", "INFO");
                   }
               }else{
                   if(!silencioso){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível gerar arquivo de carga gerado", "Carga do PDV", "INFO");
                   }
               } 
     
        
        
        return bret;
        
    }
    
    
    private static boolean Gerar_Acao(boolean silencioso, HashMap<String,Integer> hmtabelas, boolean incluircolunas) {                                        
        // TODO add your handling code here:
         boolean bRetorno=false;
          try {
              
            ArquivoGerado="";  
            LogDinnamus.Informacao( "----> Iniciando geração de carga inicial do pdv" );
            //bExecutarThread=true;
            if(!ManipulacaoArquivo2.DiretorioExiste(cDiretorioCarga)){
               ManipulacaoArquivo2.CriarDiretorio(cDiretorioCarga);
            }
            
            String data = DataHora.getData("ddMMyyyy");
            String hora = DataHora.getHora("HHmmss");
            
           
            ArquivoCargaPDV.Iniciar(cDiretorioCarga,data,hora);
             cNomeTabela="";
            
            ArrayList<String> arTabela = new ArrayList<String>();
            if(hmtabelas!=null){
                for (Iterator<String> it = hmtabelas.keySet().iterator(); it.hasNext();) {
                    arTabela.add(it.next());                    
                }                
            }else{                    
                  arTabela = ArquivoCargaPDV.ProcessarArquivo_ListaTabelas();
            }
            
            progresso =new frmMonitorProgresso("Gerando Carga Inicial", 1, arTabela.size());    
            progresso.setVisible(true);
            progresso.InicializarBarraProgresso(1, arTabela.size());
 
                    
            
            for ( i = 0; i < arTabela.size(); i++) {
                cNomeTabela = arTabela.get(i);
 
                    new Thread("GerarCargaInicial"){
                        @Override
                        public void run(){
                                progresso.Atualizar(i+1, "Processando Tabela " + cNomeTabela);
                        }
                    }.start();
       
                
                LogDinnamus.Informacao( "---------- Processando Tabela ...." + cNomeTabela);
                
                if(ArquivoCargaPDV.ProcessarTabela(cNomeTabela,incluircolunas))
                    LogDinnamus.Informacao( "OK");
                else
                {
                   LogDinnamus.Informacao("FALHA");
                   bRetorno=true;
                   break;
                }
            }
            if(!bRetorno)
            {                
                String cNomeArquivoZIP=cDiretorioCarga + ManipulacaoArquivo2.getSeparadorDiretorio() + "CargaPDV" +
                    DataHora.getCampoFormatado( data.replaceAll("/",""),  new Date())+
                    DataHora.getCampoFormatado( hora.replaceAll(":",""), new Date())+ 
                    ".zip";
                LogDinnamus.Informacao("---------- Iniciando compactação do arquivo de carga "+ cNomeArquivoZIP  +"\n");
                ManipulacaoZIPUNZIP zIP=new ManipulacaoZIPUNZIP();
                if(zIP.CompactarArquivosDinnamuS(cNomeArquivoZIP, ArquivoCargaPDV.getArquivosCarga()))
                {
                    LogDinnamus.Informacao("---------- Arquivo compactado com sucesso\n" );
                    LogDinnamus.Informacao("---------- Removendo arquivos temporarios..." );
                    if(ManipulacaoArquivo2.ExcluirArquivo(ArquivoCargaPDV.getArquivosCarga()))
                    {
                        LogDinnamus.Informacao("OK\n"  );
                        LogDinnamus.Informacao("---------- Processo concluido com sucesso\n"  );
                        //progresso..setString("Processo concluido com sucesso");
                        ArquivoGerado = cNomeArquivoZIP;
                        bRetorno=true;
                    }
                    else
                        LogDinnamus.Informacao("Falha\n"  );
                }
                else
                    LogDinnamus.Informacao("---------- Falha na compactação do arquivo\n" );
            }            
        } catch (Exception e) {
                LogDinnamus.Log(e);
                
        }
        return bRetorno;

    }                                       
}
