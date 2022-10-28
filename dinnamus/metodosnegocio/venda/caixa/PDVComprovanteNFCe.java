/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.metodosnegocio.venda.caixa;

import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensorc;
import br.data.DataHora;
import br.valor.formatar.FormatarNumero;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.TratamentoNulo.TratamentoNulos;
import br.com.repositorio.DAO_RepositorioLocal;
import MetodosDeNegocio.Entidades.Itensdevolvidos;
import MetodosDeNegocio.Venda.pdvgerenciar;
import br.String.ManipulacaoString;
import com.nfce.config.NFCE_Configurar;
import static br.impressao.ESCPOSApi.GerarQRCode;
import br.impressao.EscPos;
import br.impressao.ImpressoraCompravante;
import br.impressao.Perifericos;
import br.matriz.Matrizes;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author dti
 */
public class PDVComprovanteNFCe {

    //private static GerenciarPorta porta;
    private static int CodigoLoja = 0, CodigoUsuarioLogado = 0;
    private static ImpressoraCompravante _impressora_comprovante=null;
    private static byte[] FormatacaoPadraoComprovante = Matrizes.UnirMatrizes(EscPos.CodigoTextoCompactado(),EscPos.CodigoAlinhar_Centralizado());
    public static boolean DadosFechamentoNFCe(Long Codigo){
        boolean Ret = false;
        try {
            String Texto = ComprovanteNFCE.DadosFechamentoNFCe(Codigo);
                        
            //getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,EscPos.CodigoTextoCompactado() );                        
                getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,Texto.getBytes());
            Ret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean ConcederDesconto(Integer nSEQ, Float nDesconto){
        try {
            
            getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante, ("desconto item " + nSEQ.toString() + "  R$ " +  FormatarNumero.FormatarNumero(nDesconto ,"##0.00")).getBytes());
            
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
                    
        }
    
    
    }
    public static boolean Troca_AbrirCupom( ResultSet rsDadosEmpresa, Long CodigoTroca, boolean ImprimirDadosLoja) {
        boolean bRet = false;
        try {
            if (getImpressora_comprovante()!=null){

                bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.Troca_CabecaNota_Conteudo(rsDadosEmpresa, true, ImprimirDadosLoja, CodigoTroca).getBytes());
                bRet=true;
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível abrir cupom de troca.", "Abrir Cupom de Troca", "AVISO");
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    public static boolean ImprimirLogoNFCe(){
        boolean Ret = false;
        try {
            URL resource = PDVComprovanteNFCe.class.getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logonfce_grande.jpg");
            String Imagem = resource.getFile();
            //GerenciarPorta porta = PDVComprovante.getPorta();
            //porta.AbrirPorta();     
            
            //ESCPOSApi.ImprimirImagem(Imagem, porta.getFs(),"jpg");
            //porta.Fechar();
            getImpressora_comprovante().Imprimir_Imagem(Imagem, 50,90,85);
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean AbrirCupom(int nCodigoLoja, int nCodigoUsuarioLogado,Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, ArrayList<Long> PreVenda) {
        boolean bRet = false;
        try {
            if (getImpressora_comprovante()!=null){
                setCodigoLoja(nCodigoLoja);
                setCodigoUsuarioLogado(nCodigoUsuarioLogado);
                //char codigo = 77;
                //bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante, new String(char));
                //byte[] codigo = new byte[]{27,33,1};
                
                //ImprimirLogoNFCe();
                bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,  ComprovanteNFCE.CabecaNota_Conteudo(d, rsDadosEmpresa, cTipoNota,true,PreVenda).getBytes());
               // bRet=true;
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível abrir cupom fiscal.", "Abrir Cupom Fiscal", "AVISO");
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean Recebimento_SubTotaliza(String Dados){
        boolean Ret = false;
        try {
             Ret=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,Dados.getBytes());
             
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean Recebimento_AbrirComprovante(int nCodigoLoja, int nCodigoUsuarioLogado, String Dados) {
        boolean bRet = false;
        try {
            if (getImpressora_comprovante()!=null){
                setCodigoLoja(nCodigoLoja);
                setCodigoUsuarioLogado(nCodigoUsuarioLogado);
           
                byte[] codigo = new byte[]{27,33,1};                
                //bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,codigo );
                bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,Dados.getBytes()/*ComprovanteNaoFiscal.CabecaNota_Conteudo(d, rsDadosEmpresa, cTipoNota,true,PreVenda)*/);
                bRet=true;
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível abrir o comprovante de recebimento ", "Abrir Recebimento", "AVISO");
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    public static boolean FecharVenda(Dadosorc d){
        boolean bRet=false;
        try {


        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean IniciaFechamentoCupom(Dadosorc d){
        boolean bRet=false;
        try {
            
            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.SubTotalizaCupom(d).getBytes());

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean TerminaFechamento(Float nDinheiro, Float nTroco, String cMensagem, Long nCodigoCliente, Long nCodigoVenda, String cImpNaoFiscal,String IDCliente, String CodigoVendedor,String NomeVendedor){
        boolean bRet=false;
        try {
            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNFCE.TerminaFechamento(nDinheiro, nTroco, cMensagem,nCodigoCliente,nCodigoVenda,IDCliente,CodigoVendedor,NomeVendedor).getBytes());
            
            //if(bRet){               
                //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, cImpNaoFiscal, "", "");
               //getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,PDVComprovanteNaoFiscal.AcionarGuilhotina(cImpNaoFiscal));
            //}
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    public static boolean Recebimento_TerminaFechamento(String DadosFechamento){
        boolean bRet=false;
        try {
            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,DadosFechamento.getBytes());            
         
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    public static boolean EfetuarFormaPagto(String cConta,String cNomeForma, Double nValor){
        boolean bRet=false;
        try {
            
            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.EfetuarFormaPagto(cConta,cNomeForma, nValor).getBytes());

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean CancelarCupom(String cNomeOperador){
        boolean bRet=false;
        try {

            String cDados ="";
            cDados =  "***********************************" + ManipulacaoArquivo2.newline;
            cDados += "******** CUPOM CANCELADO **********" + ManipulacaoArquivo2.newline;
            cDados += "OPERADOR : " + cNomeOperador + ManipulacaoArquivo2.newline;
            cDados += DataHora.getData() + " - "  + DataHora.getHora() + ManipulacaoArquivo2.newline;
            cDados +=  "**********************************" + ManipulacaoArquivo2.newline;
            cDados +=  ManipulacaoArquivo2.newline;
            cDados +=  ManipulacaoArquivo2.newline;
            cDados +=  ManipulacaoArquivo2.newline;

            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return bRet;
    }
    
    public static boolean CancelarItem(Itensorc i){
        boolean bRet=false;
        try {
            String cDados="";

            cDados = ComprovanteNaoFiscal.RegistrarItem_Tela_CancelarItens(i,i.getSeq(),false);

            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return bRet;
    }
  public static boolean Troca_ImprimirItem(Itensdevolvidos i) {
        boolean bRet = false;        
        String cDados="";
        String cDescricao,  cCodigo;
        String Complemento ="";
        Float nPreco,  nQt,  nDesc,  nSubTotal;

        try {

            cDescricao=TratamentoNulos.getTratarString().Tratar(i.getDescricao(),"");
            cCodigo=TratamentoNulos.getTratarString().Tratar(i.getRef(),"");
            nPreco=i.getPreco().floatValue();
            nQt=i.getQuantidade().floatValue();
            nDesc= TratamentoNulos.getTratarBigDecimal().Tratar(i.getDescv(),BigDecimal.ZERO).floatValue();
            nSubTotal=i.getTotal().floatValue();
            Complemento =(i.getNomecor() + " " + i.getCodtam()).trim();

            cDados=ComprovanteNaoFiscal.Troca_Dados_Itens(i.getSeq().toString(),cDescricao, cCodigo, nPreco, nQt,  nDesc, nSubTotal,true,i.isFracionado(),Complemento);

            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean Troca_Fechamento(Double ValorCredito, Double Quantidade, boolean EjetarPapel){
        try {
            boolean bRet = false;        
            
            String cDados="";
            
            cDados = ComprovanteNaoFiscal.Troca_Fechamento(ValorCredito, Quantidade, EjetarPapel);
            
            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }

    }
    
    public static boolean ImprimirQRCode(Long CodigoVenda,String QRCode){
        boolean Ret = false;
        try {
            String DiretorioTemp = ManipulacaoArquivo2.DiretorioDeTrabalho()+ManipulacaoArquivo2.getSeparadorDiretorio()+"temp";
            String ArqTmp ="";
            if(!ManipulacaoArquivo2.DiretorioExiste(DiretorioTemp)){
                if(ManipulacaoArquivo2.CriarDiretorio(DiretorioTemp)){return false;}
            }
            ArqTmp =DiretorioTemp + ManipulacaoArquivo2.getSeparadorDiretorio() + CodigoVenda.toString()+"qrcode.png";
            if(ManipulacaoArquivo2.ArquivoExiste(ArqTmp, false)){
               ManipulacaoArquivo2.ExcluirArquivo(ArqTmp);
            }   
            //getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante, EscPos.CodigoAlinhar_Centralizado());
            QRCode = TratamentoNulos.getTratarString().Tratar(QRCode,"");
            if(QRCode.equalsIgnoreCase("")){
               ResultSet rsNotaEnviada= NFCE_Configurar.NotaEnviada(CodigoVenda, pdvgerenciar.CodigoPDV()); 
               if(rsNotaEnviada.next()){
                   QRCode=TratamentoNulos.getTratarString().Tratar(rsNotaEnviada.getString("dadosqrcode"),"");
               }
            }
            if(QRCode.trim().length()>0){
                getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,("Consulta via QRCode" + ManipulacaoArquivo2.newline).getBytes());
                                 
                
                /*GerenciarPorta porta = PDVComprovante.getPorta();
                porta.AbrirPorta();            
                Ret = ESCPOSApi.ImprimirQRCode(QRCode, ArqTmp,porta.getFs());
                porta.Fechar();*/
                
                if(!GerarQRCode(QRCode, ArqTmp)){
                    return false;
                }else{
                    Ret = getImpressora_comprovante().Imprimir_Imagem(ArqTmp, 42,120,120);
                }

                
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean ImprimirItem(Itensorc i) {
        boolean bRet = false;        
        String cDados="";
        String cDescricao,  cCodigo,Cor,Tam;
        Double nPreco,  nQt,    nSubTotal;
        Float nDesc;

        try {

            cDescricao= ManipulacaoString.Left( TratamentoNulos.getTratarString().Tratar(i.getDescricao(),""),40);
            cCodigo=TratamentoNulos.getTratarString().Tratar(i.getRef(),"");
            Cor=TratamentoNulos.getTratarString().Tratar(i.getNomecor(),"");
            Tam=TratamentoNulos.getTratarString().Tratar(i.getCodtam(),"");
            
            nPreco=i.getPreco().doubleValue();
            nQt=i.getQuantidade().doubleValue();
            nDesc= TratamentoNulos.getTratarBigDecimal().Tratar(i.getDescv(),BigDecimal.ZERO).floatValue();
            nSubTotal=i.getTotal().doubleValue();
            
            cDados=ComprovanteNFCE.RegistrarItem_Imprimir_Itens(i.getSeq().toString(),cDescricao, cCodigo, nPreco, nQt,  nDesc, nSubTotal,true,i.isFracionado(),Cor,Tam);
           // bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante, EscPos.Codigo_IniciarImpressora() );
           // bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante, EscPos.CodigoTextoCompactado() );  
            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean ImprimirItens(ArrayList<Itensorc> iList) {
        boolean bRet = false;        
        String cDados="",Cor,Tam;
        String cDescricao,  cCodigo;
        Double nPreco,  nQt,    nSubTotal;
        Float nDesc;

        try {

            for (int j = 0; j < iList.size(); j++) {
                Itensorc i = iList.get(j);
            
                cDescricao= ManipulacaoString.Left( TratamentoNulos.getTratarString().Tratar(i.getDescricao(),""),40);
                cCodigo=TratamentoNulos.getTratarString().Tratar(i.getRef(),"");
                Cor=TratamentoNulos.getTratarString().Tratar(i.getNomecor(),"");
                Tam=TratamentoNulos.getTratarString().Tratar(i.getCodtam(),"");
                nPreco=i.getPreco().doubleValue();
                nQt=i.getQuantidade().doubleValue();
                nDesc= TratamentoNulos.getTratarBigDecimal().Tratar(i.getDescv(),BigDecimal.ZERO).floatValue();
                nSubTotal=i.getTotal().doubleValue();
                
                cDados=cDados + ComprovanteNFCE.RegistrarItem_Imprimir_Itens(i.getSeq().toString(),cDescricao, cCodigo, nPreco, nQt,  nDesc, nSubTotal,true,i.isFracionado(),Cor,Tam);
                String SituacaoItem = TratamentoNulos.getTratarString().Tratar(i.getCodmov(),"");
                if(SituacaoItem.equalsIgnoreCase("apagar")){
                   cDados  =  cDados+ ComprovanteNaoFiscal.RegistrarItem_Tela_CancelarItens(i,i.getSeq(),false);                                    
                }
            }
           // bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante, EscPos.Codigo_IniciarImpressora() );
           // bRet=getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante, EscPos.CodigoTextoCompactado() );  
            bRet =getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    

    /**
     * @return the porta
     */
    

     

    /**
     * @return the CodigoLoja
     */
    public static int getCodigoLoja() {
        return CodigoLoja;
    }

    /**
     * @param aCodigoLoja the CodigoLoja to set
     */
    public static void setCodigoLoja(int aCodigoLoja) {
        CodigoLoja = aCodigoLoja;
    }

    /**
     * @return the CodigoUsuarioLogado
     */
    public static int getCodigoUsuarioLogado() {
        return CodigoUsuarioLogado;
    }

    /**
     * @param aCodigoUsuarioLogado the CodigoUsuarioLogado to set
     */
    public static void setCodigoUsuarioLogado(int aCodigoUsuarioLogado) {
        CodigoUsuarioLogado = aCodigoUsuarioLogado;
    }
    private static String BuscarCodigoGaveta(String modelo){
        String Ret  ="";
        try {
            
            ResultSet rs = DAO_RepositorioLocal.GerarResultSet("select * from off_modeloimpressoranaofiscal where modeloimp='"+ modelo +"'");
            if(rs.next()){
                Ret =  TratamentoNulos.getTratarString().Tratar(rs.getString("codigoacionargaveta"),"");
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    } 
    private static String BuscarCodigoGuilhotina(String modelo){
        String Ret  ="";
        try {
            
            ResultSet rs = DAO_RepositorioLocal.GerarResultSet("select * from off_modeloimpressoranaofiscal where modeloimp='"+ modelo +"'");
            if(rs.next()){
                Ret =  TratamentoNulos.getTratarString().Tratar(rs.getString("codigoacionarguilhotina"),"");
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    } 
    public static byte[] Converte_Para_HEX(String Texto){
        byte[] Ret =null;
        try {
            String[] split = Texto.split("<");
            if(split.length>0){
                Ret = new byte[split.length-1];
                String Campo ="";
                Integer  Valor =0;
                for (int i = 0; i < split.length; i++) {
                    if(split[i].length()>0){
                        Campo = split[i].replace(">", "");
                        Valor = (new Integer(Campo));
                        //Campo =  Integer.toHexString(Valor).toString();
                        if(Campo.length()<4){
                          Ret[i-1] = (byte) new Integer(Valor).intValue();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
     public static byte[] Converte_Para_HEX_byte(String Texto){
        byte[] Ret =null;
        try {
            String[] split = Texto.split("<");
            if(split.length>0){
                Ret = new byte[split.length-1];
                String Campo ="";
                Integer  Valor =0;
                for (int i = 0; i < split.length; i++) {
                    if(split[i].length()>0){
                        Campo = split[i].replace(">", "");
                        Valor = (new Integer(Campo));
                        //Campo =  Integer.toHexString(Valor).toString();
                        if(Campo.length()<4){
                          Ret[i-1] = new Integer(Valor).byteValue();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean AbrirGaveta(String cTipoImpComprovante){
        boolean bRet=false;
        try {
            //String cDados="";
            byte[] cDadosHEX=null;
            
            cDadosHEX = Perifericos.BuscarCodigoGaveta(cTipoImpComprovante);
            /*
            if(cDados.length()>0){
                cDadosHEX = Converte_Para_HEX(cDados);
            }
            
            System.out.println("Codigo de Acionamento Gaveta ASCII : " + cDados);
            System.out.println("Codigo de Acionamento Gaveta HEX   : " + cDadosHEX);
            
            if(cTipoImpComprovante.equalsIgnoreCase("Daruma")){
                cDados =  "\u001b\u0070";
            }else if(cTipoImpComprovante.equalsIgnoreCase("Bematech")){
                cDados="\u001B\u0076\u008C";
            }else if(cTipoImpComprovante.equalsIgnoreCase("Sweda")){
                cDados="\u001B" + "&012";
            }else if (cTipoImpComprovante.equalsIgnoreCase("Bematech MP4200-TH")) {
               
            }*/
            
             //cDados = "\\u001B\\u0076\\u008C";                          
            if(cDadosHEX.length>0){                
                bRet=getImpressora_comprovante().Imprimir_Texto(new String(cDadosHEX ));
            }else{
                bRet=true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static String AcionarGuilhotina(String cImpressora)
    {
        String cRetorno="";
        try {
            
            byte[] Dados = Perifericos.BuscarCodigoGuilhotina(cImpressora);
            if(Dados.length>0){
                
                    System.out.println("Codigo de Acionamento Guilhotina ASCII : " + Dados);
                    //System.out.println("Codigo de Acionamento Guilhotina HEX   : " + DadosHEX);//               
                         
                    //char[] cDadosHEX = Converte_Para_HEX(Dados);
                    
                    /*
                    if (cImpressora.equalsIgnoreCase("sweda")) {
                        cRetorno = "\u0011" ;
                    }else if (cImpressora.equalsIgnoreCase("Bematech")) {
                        cRetorno = "\u006d"  ;
                    }else if (cImpressora.equalsIgnoreCase("Bematech MP4200-TH")) {
                        cRetorno = "\u001B\u0077"  ;                
                    }*/
                   
                    getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,Dados);
                    getImpressora_comprovante().Imprimir_Texto(FormatacaoPadraoComprovante,("." + ManipulacaoArquivo2.newline).getBytes());
                
            }
            
            
        } catch (Exception e) {
            
            LogDinnamus.Log(e);
            
        }
        return cRetorno;
                
    }

    /**
     * @return the _impressora_comprovante
     */
    public static ImpressoraCompravante getImpressora_comprovante() {
        return _impressora_comprovante;
    }

    /**
     * @param aImpressora_comprovante the _impressora_comprovante to set
     */
    public static void setImpressora_comprovante(ImpressoraCompravante aImpressora_comprovante) {
        _impressora_comprovante = aImpressora_comprovante;
    }
}
