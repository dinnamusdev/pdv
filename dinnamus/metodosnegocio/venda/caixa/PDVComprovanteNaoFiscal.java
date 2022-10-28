/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.metodosnegocio.venda.caixa;

import br.impressao.EscPos;
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
import br.impressao.ImpressoraCompravante;
import br.impressao.Perifericos;
import br.matriz.Matrizes;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author dti
 */
public class PDVComprovanteNaoFiscal {

    //private static GerenciarPorta porta;
    
    private static int CodigoLoja = 0, CodigoUsuarioLogado = 0;
    private static ImpressoraCompravante impressora=null;
    private static byte[] FormatacaoPadraoComprovante = Matrizes.UnirMatrizes(EscPos.CodigoTextoCompactado(),EscPos.CodigoAlinhar_Centralizado());
   
    public static boolean ConcederDesconto(Integer nSEQ, Float nDesconto){
        try {
            
            
            getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante, ("desconto item " + nSEQ.toString() + "  R$ " +  FormatarNumero.FormatarNumero(nDesconto ,"##0.00")).getBytes());
            
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
                    
        }
    
    
    }
    public static boolean Troca_AbrirCupom( ResultSet rsDadosEmpresa, Long CodigoTroca, boolean ImprimirDadosLoja) {
        boolean bRet = false;
        try {
            if (getImpressora()!=null){

               //bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante, EscPos.CodigoTextoCompactado() );
                //8bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante, EscPos.CodigoAlinhar_Centralizado() );
                bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.Troca_CabecaNota_Conteudo(rsDadosEmpresa, true, ImprimirDadosLoja, CodigoTroca).getBytes());
                bRet=true;
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível abrir cupom de troca.", "Abrir Cupom de Troca", "AVISO");
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    
    public static boolean AbrirCupom(int nCodigoLoja, int nCodigoUsuarioLogado,Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, ArrayList<Long> PreVenda) {
        boolean bRet = false;
        try {
            if (getImpressora()!=null){
                setCodigoLoja(nCodigoLoja);
                setCodigoUsuarioLogado(nCodigoUsuarioLogado);
                
                bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.CabecaNota_Conteudo(d, rsDadosEmpresa, cTipoNota,true,PreVenda).getBytes());

                bRet=true;
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
             Ret=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,Dados.getBytes());
             
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public static boolean Recebimento_AbrirComprovante(int nCodigoLoja, int nCodigoUsuarioLogado, String Dados) {
        boolean bRet = false;
        try {
            if (getImpressora()!=null){
                setCodigoLoja(nCodigoLoja);
                setCodigoUsuarioLogado(nCodigoUsuarioLogado);
           
                byte[] codigo = new byte[]{27,33,1};                
                //bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,codigo );
                bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,Dados.getBytes()/*ComprovanteNaoFiscal.CabecaNota_Conteudo(d, rsDadosEmpresa, cTipoNota,true,PreVenda)*/);
                bRet=true;
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível abrir o comprovante de recebimento ", "Abrir Recebimento", "AVISO");
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    
    
     public static boolean CrediarioExtrato_Imprimir(  String Dados) {
        boolean bRet = false;
        try {
            if (getImpressora()!=null){
                byte[] codigo = new byte[]{27,33,1};                
                //bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,codigo );
                bRet=getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,Dados.getBytes()/*ComprovanteNaoFiscal.CabecaNota_Conteudo(d, rsDadosEmpresa, cTipoNota,true,PreVenda)*/);
                bRet=true;
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível abrir o extrato do crediário ", "Abrir Extrato", "AVISO");
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
            
            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.SubTotalizaCupom(d).getBytes());

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean TerminaFechamento(Float nDinheiro, Float nTroco, String cMensagem, Long nCodigoCliente, Long nCodigoVenda, String cImpNaoFiscal, String CodigoVendedor,String NomeVendedor){
        boolean bRet=false;
        try {
            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.TerminaFechamento(nDinheiro, nTroco, cMensagem,nCodigoCliente,nCodigoVenda,CodigoVendedor,NomeVendedor).getBytes());
            
            //if(bRet){               
                //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, cImpNaoFiscal, "", "");
               //getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,PDVComprovanteNaoFiscal.AcionarGuilhotina(cImpNaoFiscal));
            //}
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    public static boolean Recebimento_TerminaFechamento(String DadosFechamento){
        boolean bRet=false;
        try {
            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,DadosFechamento.getBytes());            
         
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    public static boolean EfetuarFormaPagto(String cConta,String cNomeForma, Double nValor){
        boolean bRet=false;
        try {
            
            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,ComprovanteNaoFiscal.EfetuarFormaPagto(cConta,cNomeForma, nValor).getBytes());

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean CancelarCupom(String cNomeOperador, String ModeloImp){
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

            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());
            bRet =getImpressora().Imprimir_Texto(Perifericos.BuscarCodigoGuilhotina(ModeloImp));

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

            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

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

            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

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
            
            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public static boolean ImprimirItem(Itensorc i) {
        boolean bRet = false;        
        String cDados="";
        String cDescricao,  cCodigo, Cor, Tam;
        Double nPreco,  nQt,    nSubTotal;
        Float nDesc;

        try {

            cDescricao=TratamentoNulos.getTratarString().Tratar(i.getDescricao(),"");
            cCodigo=TratamentoNulos.getTratarString().Tratar(i.getRef(),"");
            Cor=TratamentoNulos.getTratarString().Tratar(i.getNomecor(),"");
            Tam=TratamentoNulos.getTratarString().Tratar(i.getCodtam(),"");
            nPreco=i.getPreco().doubleValue();
            nQt=i.getQuantidade().doubleValue();
            nDesc= TratamentoNulos.getTratarBigDecimal().Tratar(i.getDescv(),BigDecimal.ZERO).floatValue();
            nSubTotal=i.getTotal().doubleValue();
                        
            cDados=ComprovanteNaoFiscal.RegistrarItem_Imprimir_Itens(i.getSeq().toString(),cDescricao, cCodigo, nPreco, nQt,  nDesc, nSubTotal,true,i.isFracionado(),Cor,Tam);

            bRet =getImpressora().Imprimir_Texto(FormatacaoPadraoComprovante,cDados.getBytes());

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public static boolean Fechar(){
        boolean bRet=false;
        try {

            
            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    /**
     * @return the porta
     */
    
    
    /**
     * @param aPorta the porta to set
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
    public static String BuscarCodigoGuilhotina(String modelo){
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
    public static char[] Converte_Para_HEX(String Texto){
        char[] Ret =null;
        try {
            String[] split = Texto.split("<");
            if(split.length>0){
                Ret = new char[split.length-1];
                String Campo ="";
                Integer  Valor =0;
                for (int i = 0; i < split.length; i++) {
                    if(split[i].length()>0){
                        Campo = split[i].replace(">", "");
                        Valor = (new Integer(Campo));
                        //Campo =  Integer.toHexString(Valor).toString();
                        if(Campo.length()<4){
                          Ret[i-1] = (char) new Integer(Valor).intValue();
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
            byte[]dados = Perifericos.BuscarCodigoGuilhotina(cTipoImpComprovante);
            if(dados.length>0){                
                bRet=getImpressora().Imprimir_Texto( dados);
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
            
            byte[]dados = Perifericos.BuscarCodigoGuilhotina(cImpressora);
            
            if(dados.length>0){
              getImpressora().Imprimir_Texto(dados,("." + ManipulacaoArquivo2.newline).getBytes());
                
            }
            
            
        } catch (Exception e) {
            
            LogDinnamus.Log(e);
            
        }
        return cRetorno;
                
    }

    /**
     * @return the impressora
     */
    public static ImpressoraCompravante getImpressora() {
        return impressora;
    }

    /**
     * @param impressora the impressora to set
     */
    public static void setImpressora(ImpressoraCompravante impressora) {
        PDVComprovanteNaoFiscal.impressora = impressora;
    }
}
