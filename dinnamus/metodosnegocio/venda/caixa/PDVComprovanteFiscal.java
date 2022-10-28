/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.metodosnegocio.venda.caixa;


import br.String.ManipulacaoString;
import br.TratamentoNulo.TratamentoNulos;
import br.com.ecf.ECFDinnamuS;
import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.valor.formatar.FormatarNumero;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensdevolvidos;
import MetodosDeNegocio.Entidades.Itensorc;
import MetodosDeNegocio.Venda.VendaEmEdicao;
import java.math.BigDecimal;
import java.sql.ResultSet;


import javax.swing.JOptionPane;

/**
 *
 * @author dti
 */
public class PDVComprovanteFiscal {
        private static ECFDinnamuS EcfDinnmus;
        private static String Erro="";

        public static boolean  Iniciar(ECFDinnamuS EcfDinnmus){
            setEcfDinnmus(EcfDinnmus);
            return true;
        }
       public static boolean  Recebimento_Iniciar(ECFDinnamuS EcfDinnmus){
           getEcfDinnmus().CancelarQualquerComprovanteAberto();
           if(EcfDinnmus.ComprovanteNaoFiscal_Abrir()==1) {
              setEcfDinnmus(EcfDinnmus);            
              return true;
           }else{
               return false;
           }
        }
        public static boolean Troca_AbrirCupom( ResultSet rsDadosEmpresa, Long CodigoTroca, boolean ImprimirDadosLoja){
            try {                
                String Dados = ComprovanteNaoFiscal.Troca_CabecaNota_Conteudo(rsDadosEmpresa, true, ImprimirDadosLoja, CodigoTroca);
                if(getEcfDinnmus().ComprovanteNaoFiscal_Abrir()==1){
                    return getEcfDinnmus().ComprovanteNaoFiscal_Usar(Dados)==1 ? true : false;
                }                
            } catch (Exception e) {
                LogDinnamus.Log(e, true);
                
            }
            return false;
        }
    public static boolean Troca_ImprimirItem(Itensdevolvidos i) {
        boolean bRet = false;        
        String cDados="";
        String cDescricao,  cCodigo;
        Float nPreco,  nQt,  nDesc,  nSubTotal;
        String Complemento="";
        try {

            cDescricao=TratamentoNulos.getTratarString().Tratar(i.getDescricao(),"");
            cCodigo=TratamentoNulos.getTratarString().Tratar(i.getRef(),"");
            nPreco=i.getPreco().floatValue();
            nQt=i.getQuantidade().floatValue();
            nDesc= TratamentoNulos.getTratarBigDecimal().Tratar(i.getDescv(),BigDecimal.ZERO).floatValue();
            nSubTotal=i.getTotal().floatValue();
            Complemento =(i.getNomecor() + " " + i.getCodtam()).trim();
            cDados=ComprovanteNaoFiscal.Troca_Dados_Itens(i.getSeq().toString(),cDescricao, cCodigo, nPreco, nQt,  nDesc, nSubTotal,true,i.isFracionado(),Complemento);
            
             return getEcfDinnmus().ComprovanteNaoFiscal_Usar(cDados)==1 ? true : false;
            //bRet =getPorta().Escrever(cDados);

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return false;
    }
       public static boolean Troca_Fechamento(Double ValorCredito, Double Quantidade, boolean EjetarPapel){
        String cDados="";
        try {
           
            cDados+= ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            cDados+=ManipulacaoString.PrepararStringEmLinha("VALOR CREDITO :",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(ValorCredito ,"##0.00"),9) + ManipulacaoArquivo2.newline;
            cDados+=ManipulacaoString.PrepararStringEmLinha("QTD DEVOLVIDA :",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(Quantidade ,"000"),9) + ManipulacaoArquivo2.newline;                
          
            cDados+= ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            if(EjetarPapel){
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
            }
            getEcfDinnmus().ComprovanteNaoFiscal_Usar(cDados);
            
            getEcfDinnmus().ComprovanteNaoFiscal_Fechar();
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return false;
    }
   public static boolean ImprimirCNFV(String cTexto){
            try {
                getEcfDinnmus().ComprovanteNaoFiscal_Abrir();
               
                getEcfDinnmus().ComprovanteNaoFiscal_Usar(cTexto);
                //getEcfDinnmus().ComprovanteNaoFiscal_Usar(cTexto);
                
                getEcfDinnmus().ComprovanteNaoFiscal_Fechar();
                
                
                return true;
            } catch (Exception e) {
                LogDinnamus.Log(e, true);
                return false;
            }
        }
        public static boolean ImprimirItemFiscal(Itensorc i){
            try {
                int nRetorno=0;
                while(true){
                    String cod,  nome,  aliq="",  tipoqt,tpdesc,valdesc,unidade="",Det="";
                    Double quant, unit,desconto;
                    int casas;
                    cod=i.getRef();
                    Det = i.getNomecor()+ " " + i.getCodtam();
                    
                    if(Det.trim().length()>0){
                      nome= i.getDescricao() + " " + Det;                        
                    }else{
                      nome=i.getDescricao();
                    }
                    
                    nome = ManipulacaoString.Left(nome, 200);
                   
                    
                    if(i.getSt().trim().equalsIgnoreCase("1")){
                        if ( i.getCodaliquota().trim().equalsIgnoreCase("") || i.getCodaliquota().trim().equalsIgnoreCase("01") || i.getCodaliquota().trim().equalsIgnoreCase("17") ) {
                            aliq="1700";
                            //aliq=aliq + "00";
                        }else{  
                            
                            aliq= i.getCodaliquota();
                        }       
                        
                    }else{
                        if(i.getSt().trim().equalsIgnoreCase("2")){
                           aliq="II"; 
                        }else if(i.getSt().trim().equalsIgnoreCase("3")){
                            aliq="NN";
                        }else if(i.getSt().trim().equalsIgnoreCase("4")){
                            aliq="FF";
                        }
                    }
                    
                    
                    unidade=i.getUnidade();
                    tipoqt = (i.isFracionado() ? "F" : "I");
                    quant =i.getQuantidade().doubleValue();
                    unit =  i.getPreco().doubleValue();
                    
                    if(i.getDescv()!=null){
                        desconto = i.getDescv().doubleValue();
                    }else{
                        desconto =0d;    
                    }
                    if(aliq.trim().equalsIgnoreCase("")){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ALIQUOTA INVÁLIDA", "REGISTRAR ITEM");
                        return false;
                    }
                    
                    
                    nRetorno=getEcfDinnmus().VendeItem(cod, nome,aliq,tipoqt,quant, 2,unit,"$", desconto,unidade);
                    if(nRetorno==1){
                        return true;
                    }else{                        

                        if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, (!getEcfDinnmus().getError().equalsIgnoreCase("") ? getEcfDinnmus().getError() +"\n" :"") + "Não foi possível imprimir o item no cupom fiscal. Deseja Tentar Novamente?", "Imprimindo Item", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION){                          
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                LogDinnamus.Log(e);
                return false;
            }
        }
        
        public static boolean AbrirCupomFiscal(String Str){
            boolean bRet=false;
            try {
                
                getEcfDinnmus().CancelarQualquerComprovanteAberto();
                
                while(true){
                    if(getEcfDinnmus().AbreCupom(Str)==1){
                        bRet=true;
                        break;
                    }else{
                        if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Não foi possível Abrir o cupom fiscal. Deseja Tentar Novamente?", "Imprimindo Item", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION){
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar o cupom fiscal", "Abertura de Cupom fiscal", "AVISO");
                            break;
                        }
                    }
                }               
            } catch (Exception e) {
                LogDinnamus.Log(e);
                bRet= false;
            }
            return bRet;
        }
         public static boolean Recebimento_AbrirComprovante(String Str){
            boolean bRet=false;
            try {
                
                //getEcfDinnmus().CancelarQualquerComprovanteAberto();
                
                while(true){
                    if(getEcfDinnmus().ComprovanteNaoFiscal_Usar(Str)==1){
                        bRet=true;
                        break;
                    }else{
                        if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Não foi possível Abrir o RECEBIMENTO. Deseja Tentar Novamente?", "Imprimindo Recebimento", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION){
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar a impressão do RECEBIMENTO", "Abertura de RECEBIMENTO", "AVISO");
                            break;
                        }
                    }
                }               
            } catch (Exception e) {
                LogDinnamus.Log(e);
                bRet= false;
            }
            return bRet;
        }
        public static boolean CancelarCupomFiscal(){
            boolean bRet=false;
            try {

                if(getEcfDinnmus().VerificarStatusCupomAberto()==1){
                    getEcfDinnmus().CancelarUltimoCupom();
                }

            } catch (Exception e) {
                LogDinnamus.Log(e);
                bRet= false;
            }
            return bRet;
        
        }
       
        public static boolean ConcederDescontoItemCupomFiscal(Integer Seq, Float nValorDesconto){
            try {
                
                return (getEcfDinnmus().ConcederDescontoItem(Seq, nValorDesconto)==1 ? true : false);
                                
            } catch (Exception e) {
                LogDinnamus.Log(e, true);
                return false;
            }
        }
        public static boolean CancelarItemCupomFiscal(Integer nSeq){
            boolean bRet=false;
            try {
                
                bRet = (getEcfDinnmus().CancelarItem(nSeq)==1 ? true : false);

            } catch (Exception e) {
                LogDinnamus.Log(e);
                bRet= false;
            }
            return bRet;
        }

        public static boolean IniciaFechamentoCupom(String cAcredDesc, String cTipoAcredDesc, Double cValAcredDesc ){
            try {
                while(true){
                    boolean bIgnorarImpressao = false;
                    String[] MomentoImpressaoCupomFiscal = VendaEmEdicao.MomentoImpressaoCupomFiscal();
                    //if(MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("descontoitem"))
                    
                    if(!MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("descontoitem") ){
                       if(!MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("")){
                            bIgnorarImpressao=true;
                       }
                    }
                    
                    if(!bIgnorarImpressao){
                        if(getEcfDinnmus().IniciaFechamentoCupom( cAcredDesc, cTipoAcredDesc, cValAcredDesc )==1){
                            VendaEmEdicao.RegistrarMomentoImpressaoCupomFiscal("iniciarfechamento:0");
                            return true;
                        }else{
                            if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Não foi possível iniciar o fechamento do cupom. Deseja Tentar Novamente?", "Fechamento do Cupom", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar o fechamento cupom fiscal", "Fechamento do Cupom fiscal", "AVISO");
                                return false;
                            }
                        }
                    }else{
                        return true;
                    }
                }
            } catch (Exception e) {
                LogDinnamus.Log(e);
                return false;
            }
        }
        public static boolean EfetuarFormaPagto( String FormaPag,Double ValorFormaPag ){
            try {
                while(true){
                    if(getEcfDinnmus().EfetuarFormaPagto(FormaPag, ValorFormaPag )==1){
                        return true;
                    }else{
                        if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, (getEcfDinnmus().getError().length()>0 ?  getEcfDinnmus().getError() +"\n" : "" ) + "Não foi possível iniciar o fechamento do cupom. Deseja Tentar Novamente?", "Fechamento do Cupom", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION){
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar o fechamento cupom fiscal", "Fechamento do Cupom fiscal", "AVISO");
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                LogDinnamus.Log(e);
                return false;
            }
        }
        /*public static boolean ComprovanteNaoFiscal(cTexto){
        
        }*/
        public static void FecharPorta(){
            getEcfDinnmus().FecharPorta();
        }
        public static boolean TerminaFechamento(Dadosorc d , String cMsg){
            try {

                while(true){
                    Float Dinheiro =d.getDinheiro()==null ? 0f : d.getDinheiro().floatValue();
                    Float Troco =  d.getTroco()==null ? 0f : d.getTroco().floatValue();                    
                    
                    if(getEcfDinnmus().TerminaFechamento(Troco>0f ? Dinheiro : 0f , cMsg)==1){
                        return true; 
                    }else{
                        if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Não foi possível finalizar o cupom. Deseja Tentar Novamente?", "Fechamento do Cupom", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION){
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível finalizar cupom fiscal", "Fechamento do Cupom fiscal", "AVISO");
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                LogDinnamus.Log(e);
                return false;
            }

        }


    /**
     * @return the EcfDinnmus
     */
    public static ECFDinnamuS getEcfDinnmus() {
        return EcfDinnmus;
    }

    /**
     * @param aEcfDinnmus the EcfDinnmus to set
     */
    public static void setEcfDinnmus(ECFDinnamuS aEcfDinnmus) {
        EcfDinnmus = aEcfDinnmus;
    }

    /**
     * @return the Erro
     */
    public static String getErro() {
        return Erro;
    }

    /**
     * @param aErro the Erro to set
     */
    public static void setErro(String aErro) {
        Erro = aErro;
    }
}
