/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.metodosnegocio.venda.caixa;

import MetodosDeNegocio.Crediario.Crediario;
import MetodosDeNegocio.Crediario.entidade.BaixarConta;
import br.TratamentoNulo.TratamentoNulos;
import br.arredondar.NumeroArredondar;
import br.com.FormatarNumeros;
import br.com.ecf.ECFDinnamuS;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.data.ManipularData;
import br.digitoverificador.DigitoVerificadores;
import br.tef.Padrao.TefPadrao;
import br.transformacao.TransformacaoDados;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensdevolvidos;
import MetodosDeNegocio.Entidades.Itensorc;
import MetodosDeNegocio.Fachada.TiposdePagamentos;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.DadosorcRN;
import MetodosDeNegocio.Venda.ImpostoNaNota;
import MetodosDeNegocio.Venda.ItensorcRN;
import MetodosDeNegocio.Venda.PagtoorcRN;
import MetodosDeNegocio.Venda.ParametrosGlobais;
import MetodosDeNegocio.Venda.Troca;
import MetodosDeNegocio.Venda.Venda;
import MetodosDeNegocio.Venda.VendaEmEdicao;
import MetodosDeNegocio.TEF.TEFVenda;
import br.String.ManipulacaoString;
import com.nfce.config.NFCE_Configurar;
import br.impressao.ImpressaoDireta;
import br.impressao.ImpressaoDiretaDR700;
import br.impressao.ImpressaoNoSpooler;
import br.impressao.ImpressoraCompravante;
import br.impressao.Perifericos;
import br.manipulararquivos.ManipulacaoArquivo2;
import dinnamus.metodosnegocio.sistema.Impressao;
import static dinnamus.metodosnegocio.venda.caixa.PDVComprovanteFiscal.getEcfDinnmus;
import dinnamus.ui.InteracaoUsuario.nfce.danfe.GerarDanfeNFCE;
import dinnamus.rel.RelatorioJasperXML;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author dti
 */
public class PDVComprovante {

    private static ImpressoraCompravante ImpressoraCompravante = null;
    private static boolean ImpressaoIniciada = false;
    private static String TipoComprovante = "";
    private static boolean Iniciou = false;
    private static ECFDinnamuS ecfatual;
    private static String cNomeImpNaoFiscal = "";
    private static String CPF_Ou_CNPJ = "";
   //private GerarDanfeNFCE danfeNFCE= 
    //private static GerenciarPorta porta=null;

    /**
     * @return the TipoComprovante
     */
    // public static GerenciarPorta getPorta()  {
    //     return porta;
    // }
    public static boolean FecharPorta() {
        boolean bRet = false;
        try {


            bRet = true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    public static String getImpressoraDeComprovante() {
        String Ret = "";
        try {
            ResultSet rs = Impressao.ImpressorasDefinidas("PDV");
            if (rs != null) {
                try {
                    if (rs.next()) {
                        String cNomePorta = TratamentoNulos.getTratarString().Tratar(rs.getString("porta"), "");

                        String NomeImpressora = TratamentoNulos.getTratarString().Tratar(rs.getString("impressora"), "");
                        if (!NomeImpressora.equalsIgnoreCase("")) {

                            Ret = NomeImpressora;
                        } else if (!cNomePorta.equalsIgnoreCase("")) {
                            Ret = cNomePorta;
                        }

                    }
                } catch (SQLException ex) {
                    LogDinnamus.Log(ex);
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public static boolean ImpressoraDeComprovante_Iniciar() {
        boolean Ret = false;
        try {
            ResultSet rs = Impressao.ImpressorasDefinidas("PDV");
            if (rs != null) {
                try {
                    if (rs.next()) {
                        String cNomePorta = TratamentoNulos.getTratarString().Tratar(rs.getString("porta"), "");
                        String NomeImpressora = TratamentoNulos.getTratarString().Tratar(rs.getString("impressora"), "");
                        if (!cNomePorta.equalsIgnoreCase("")) {
                            String ModeloImp = Perifericos.ModeloImpressoraNaoFiscal();
                            if (ModeloImp.equalsIgnoreCase("Daruma DR-700")) {
                                setImpressoraCompravante(new ImpressaoDiretaDR700());
                            } else {
                                setImpressoraCompravante(new ImpressaoDireta());
                            }
                            Ret = getImpressoraCompravante().Abrir(cNomePorta);
                        } else if (!NomeImpressora.equalsIgnoreCase("")) {
                            setImpressoraCompravante(new ImpressaoNoSpooler());
                            Ret = getImpressoraCompravante().Abrir(NomeImpressora);
                        }
                        if (!Ret) {
                            setImpressoraCompravante(null);
                        }
                        if (getImpressoraCompravante() != null) {
                            Ret = true;
                        }
                    }
                } catch (SQLException ex) {
                    LogDinnamus.Log(ex);
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    /*public static GerenciarPorta AbrirPorta(boolean bFechaPorta)  {
     if (porta==null) {
     String cNomePorta = "",NomeImpressora ="";
     ResultSet rs =Impressao.ImpressorasDefinidas("PDV");
     if (rs != null) {
     try {
     if (rs.next()) {
     cNomePorta = rs.getString("porta");
     NomeImpressora=rs.getString("impressora");
     //if(NomeImpressora!=null)
     porta = new GerenciarPorta( cNomePorta,true);
     if(!porta.isOK()){
     porta=null;
     }
     }
     } catch (SQLException ex) {
     LogDinnamus.Log(ex);
     }
     }
     }
     return porta;
     }*/

    public static Itensorc SetarItensorc(ResultSet rsItens) {
        Itensorc i = new Itensorc();
        try {
            i.setCodprod(rsItens.getLong("codprod"));
            i.setRef(rsItens.getString("ref"));
            // i.setNomeImpresso(rsItens.getString("nome_impresso"));
            i.setDescricao(rsItens.getString("descricao"));
            i.setCodaliquota(rsItens.getString("codaliquota"));
            i.setIcms(rsItens.getString("icms"));
            i.setPreco(new BigDecimal(rsItens.getFloat("preco")));
            i.setPrecooriginal(i.getPreco());
            i.setQuantidade(new BigDecimal(rsItens.getFloat("quantidade")));
            i.setData(ManipularData.DataAtual());
            i.setTotal(new BigDecimal(rsItens.getFloat("total")));
            i.setLiquido(new BigDecimal(rsItens.getFloat("liquido")));
            i.setTabela(rsItens.getString("tabela"));
            i.setSt(rsItens.getString("st"));
            i.setLoja(rsItens.getInt("loja"));
            i.setFracionado(rsItens.getBoolean("fracionado"));
            i.setCodmov(rsItens.getString("codmov"));
            i.setUnidade(TratamentoNulos.getTratarString().Tratar(rsItens.getString("unidade"), "UN"));
            i.setCusto(new BigDecimal(rsItens.getFloat("custo")));
            Double Descv = rsItens.getDouble("descv");
            Double Descp = rsItens.getDouble("descp");
            i.setDescv(new BigDecimal(Descv));
            i.setDescp(new BigDecimal(Descp));
            i.setSeq(rsItens.getInt("seq"));
            i.setCodcor(rsItens.getString("codcor"));
            i.setNomecor(rsItens.getString("nomecor"));
            i.setCodtam(rsItens.getString("codtam"));
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return i;
    }

    public static Itensdevolvidos SetarItensDevolvidos(ResultSet rsItens) {
        Itensdevolvidos i = new Itensdevolvidos();
        try {
            i.setCodprod(rsItens.getLong("codprod"));
            i.setRef(rsItens.getString("ref"));
            i.setSeq(rsItens.getInt("seq"));
            i.setDescricao(rsItens.getString("descricao"));
            i.setCodaliquota(rsItens.getString("codaliquota"));
            i.setIcms(rsItens.getString("icms"));
            i.setPreco(new BigDecimal(rsItens.getFloat("preco")));
            i.setPrecooriginal(i.getPreco());
            i.setQuantidade(new BigDecimal(rsItens.getFloat("quantidade")));
            i.setData(ManipularData.DataAtual());
            i.setTotal(new BigDecimal(rsItens.getFloat("total")));
            i.setLiquido(new BigDecimal(rsItens.getFloat("liquido")));
            i.setTabela(rsItens.getString("tabela"));
            i.setSt(rsItens.getString("st"));
            i.setLoja(rsItens.getInt("loja"));
            i.setFracionado(false);
            i.setCodtam(TratamentoNulos.getTratarString().Tratar(rsItens.getString("codtam"), ""));
            i.setNomecor(TratamentoNulos.getTratarString().Tratar(rsItens.getString("Nomecor"), ""));
            i.setCodmov(rsItens.getString("codmov"));
            i.setUnidade(TratamentoNulos.getTratarString().Tratar(rsItens.getString("unidade"), "UN"));
            //i.setCusto(new BigDecimal(rsItens.getFloat("custo")));
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return i;
    }

    public static Dadosorc SetarDadosOrc(Long CodigoVenda) {
        Dadosorc d = new Dadosorc();
        try {
            ResultSet rs = DadosorcRN.Dadosorc_Listar(CodigoVenda);
            if (rs.next()) {
                d.setCodigo(rs.getLong("codigo"));
                d.setData(rs.getDate("data"));
                d.setHora(Timestamp.valueOf(DataHora.getDataHoraAtual()));
                d.setLoja(rs.getInt("loja"));
                d.setFilial(rs.getInt("filial"));
                d.setCodoperador(rs.getString("codoperador"));
                d.setOperador(rs.getString("operador"));
                d.setVendedor(rs.getString("vendedor"));
                d.setCodvendedor(rs.getString("codvendedor"));
                d.setControleCx(rs.getString("controlecx"));
                d.setObjetoCaixa(rs.getInt("objetocaixa"));
                d.setCodcaixa(rs.getInt("codcaixa"));
                d.setRecebido(rs.getString("recebido"));
                d.setFeito(rs.getString("feito"));
                d.setPdv(rs.getInt("pdv"));
                d.setCliente(rs.getString("cliente"));
                d.setCodcliente(rs.getString("codcliente"));
                d.setValor(rs.getBigDecimal("valor"));
                d.setTotalbruto(rs.getBigDecimal("totalbruto"));
                d.setNotaNome(TratamentoNulos.getTratarString().Tratar(rs.getString("nota_nome"), ""));
                //new BigDecimal( NumeroArredondar.Arredondar(rs.getFloat("desconto"), 2));
                d.setCodigocotacao(rs.getLong("codigocotacao"));
                d.setCodigoorcamento(rs.getLong("codigoorcamento"));
                d.setTroco(new BigDecimal(NumeroArredondar.Arredondar(TratamentoNulos.getTratarFloat().Tratar(rs.getFloat("troco"), 0f), 2)));
                d.setDinheiro(new BigDecimal(NumeroArredondar.Arredondar(TratamentoNulos.getTratarFloat().Tratar(rs.getFloat("dinheiro"), 0f), 2)));
                d.setDesconto(NumeroArredondar.Arredondar_Double(TratamentoNulos.getTratarDouble().Tratar(rs.getDouble("desconto"), 0d), 2));
                d.setAcrescimo(NumeroArredondar.Arredondar_Double(TratamentoNulos.getTratarDouble().Tratar(rs.getDouble("acrescimo"), 0d), 2));
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return d;
    }

    public static boolean Reimprimir(String cTipoComprovantePDV, ECFDinnamuS ecf, Long CodigoVenda, String NomeImpNaoFiscal, RelatorioJasperXML jasperNFce) {
        try {

            Dadosorc d = SetarDadosOrc(CodigoVenda);
            ResultSet rsItens = ItensorcRN.Itensorc_Listar(CodigoVenda);

            if(jasperNFce!=null && cTipoComprovantePDV.equalsIgnoreCase("nfce")){
               // new GerarDanfeNFCE().Imprimir_NfceDanfe(CodigoVenda, jasperNFce, NomeImpNaoFiscal, Sistema.isOnline(),PDVComprovante.getImpressoraDeComprovante());
            
               Long CodigoTroca = Troca.RetornaTrocas_PorVenda(Sistema.isOnline(), CodigoVenda);
               return PDVComprovante.FecharCupomVenda_Acao_Padrao_IReport(d, 1, TipoComprovante, CodigoTroca, true, NomeImpNaoFiscal, jasperNFce);///;FecharCupomVenda_Acao_Padrao_IReport(d,0,NomeImpNaoFiscal,0l,true,)
            
            }else{
            if (PDVComprovante.Iniciar(cTipoComprovantePDV, ecf, NomeImpNaoFiscal)) {
                if (PDVComprovante.AbrirCupom("", d, Sistema.getDadosLojaAtualSistema(), cTipoComprovantePDV, false, null)) {
                    if (cTipoComprovantePDV.equalsIgnoreCase("nfce")) {
                        ArrayList<Itensorc> itens = new ArrayList<>();

                        while (rsItens.next()) {
                            Itensorc i = SetarItensorc(rsItens);
                            i.setCodigo(d);
                            itens.add(i);
                        }
                        if (!PDVComprovanteNFCe.ImprimirItens(itens)) {
                            return false;
                        }
                    } else {
                        while (rsItens.next()) {
                            Itensorc i = SetarItensorc(rsItens);
                            i.setCodigo(d);
                            if (!PDVComprovante.ImprimirItem(i)) {
                                return false;
                            }
                        }
                    }

                    Long CodigoTroca = 0l;
                    //if(Sistema.isOnline()){
                    CodigoTroca = Troca.RetornaTrocas_PorVenda(true, CodigoVenda);
                    //}
                   // ResultSet rsDescontoAtacado = Venda.DescontoAtacado_ListarItem(CodigoVenda,sis);
                    
                    if (!PDVComprovante.FecharCupomVenda(d,  1, cTipoComprovantePDV, ecf, true, CodigoTroca, true, null, "", NomeImpNaoFiscal, jasperNFce)) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            }

            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    public static boolean Iniciar(String TipoComprovante, ECFDinnamuS ecf, String NomeImpNaoFiscal) {

        setTipoComprovante(TipoComprovante);
        setIniciou(false);
        setcNomeImpNaoFiscal(NomeImpNaoFiscal);
        //System.out.println(ecf.getTipoECF());
        if (TipoComprovante.equalsIgnoreCase("fiscal")) {
            setIniciou(PDVComprovanteFiscal.Iniciar(ecf));
            setEcfatual(ecf);
        } else if (TipoComprovante.equalsIgnoreCase("nfce") || TipoComprovante.equalsIgnoreCase("nfiscal")) {
            if (!getImpressoraCompravante().isOK()) {
                if (ImpressoraDeComprovante_Iniciar()) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível comunicar com a impressora configurada\n\nVerifique as configurações do pdv", "Impressora Não configurada");
                    PDVComprovanteNFCe.setImpressora_comprovante(null);
                    PDVComprovanteNaoFiscal.setImpressora(null);
                    return false;
                }
            }
            setIniciou(true);
            PDVComprovanteNFCe.setImpressora_comprovante(getImpressoraCompravante());
            PDVComprovanteNaoFiscal.setImpressora(getImpressoraCompravante());
        }
        return isIniciou();
    }

    public static boolean Recebimento_Iniciar(String TipoComprovante, ECFDinnamuS ecf, String NomeImpNaoFiscal) {

        setTipoComprovante(TipoComprovante);
        setIniciou(false);
        setcNomeImpNaoFiscal(NomeImpNaoFiscal);
        //System.out.println(ecf.getTipoECF());
        if (TipoComprovante.equalsIgnoreCase("fiscal")) {
            setIniciou(PDVComprovanteFiscal.Recebimento_Iniciar(ecf));
            setEcfatual(ecf);
        } else {
            if (!getImpressoraCompravante().isOK()) {
                ImpressoraDeComprovante_Iniciar();
                if (!getImpressoraCompravante().isOK()) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível comunicar com a impressora configurada\n\nVerifique as configurações do pdv", "Impressora Não configurada");
                    return false;
                }
            }
            setIniciou(getImpressoraCompravante().isOK());
        }
        return isIniciou();
    }

    public static String getTipoComprovante() {
        return TipoComprovante;
    }

    /**
     * @param aTipoComprovante the TipoComprovante to set
     */
    public static void setTipoComprovante(String aTipoComprovante) {
        TipoComprovante = aTipoComprovante;
    }

    public static boolean ImprimirItem(Itensorc i) {
        if (getTipoComprovante().toLowerCase().equalsIgnoreCase("fiscal")) {
            return PDVComprovanteFiscal.ImprimirItemFiscal(i);
        } else if (getTipoComprovante().toLowerCase().equalsIgnoreCase("nfce")) {
            return PDVComprovanteNFCe.ImprimirItem(i);
        } else {
            return PDVComprovanteNaoFiscal.ImprimirItem(i);
        }
    }
    /*  public static boolean FecharPreVenda(Long CodigoVenda, String cTipoComprovantePDV,ECFDinnamuS ecf,ArrayList<Long> PreVenda){
     return FecharPreVenda(CodigoVenda, cTipoComprovantePDV, ecf, PreVenda, false,"");
     }*/

    public static boolean FecharPreVenda(Long CodigoVenda, String cTipoComprovantePDV, ECFDinnamuS ecf, ArrayList<Long> PreVenda, boolean Recomecar, String NomeImpressoraComprovante) {
        boolean Retorno = false;
        try {
            Dadosorc d = SetarDadosOrc(CodigoVenda);
            ResultSet rsItens = ItensorcRN.Itensorc_Listar(CodigoVenda);
            if (PDVComprovante.Iniciar(cTipoComprovantePDV, ecf, NomeImpressoraComprovante)) {
                if (PDVComprovante.AbrirCupom("", d, Sistema.getDadosLojaAtualSistema(), cTipoComprovantePDV, Recomecar, PreVenda)) {
                    Integer SeqUltimoItemImpresso = TratamentoNulos.getTratarInt().Tratar(VendaEmEdicao.VerificarVendaInterrompida(true).getInt("CodigoUltimoItemImpresso"), 0);
                    Integer SeqItem = 0;
                    while (rsItens.next()) {
                        SeqItem = rsItens.getInt("seq");
                        if (SeqItem > SeqUltimoItemImpresso) {
                            Itensorc i = SetarItensorc(rsItens);
                            i.setCodigo(d);
                            i.setSeq(SeqItem);
                            String SituacaoItem = TratamentoNulos.getTratarString().Tratar(i.getCodmov(), "");

                            if (!PDVComprovante.ImprimirItem(i)) {
                                return false;
                            }
                            if (SituacaoItem.equalsIgnoreCase("apagar")) {
                                if (!PDVComprovante.CancelarItem(i)) {
                                    return false;
                                }
                            }
                            if (!VendaEmEdicao.RegistrarItemImpresso(CodigoVenda, i.getSeq())) {
                                return false;
                            }
                        }

                    }
                    Retorno = true;
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Retorno;
    }

    public static boolean FecharPreVenda_nfce(Long CodigoVenda, String cTipoComprovantePDV, ECFDinnamuS ecf, ArrayList<Long> PreVenda, boolean Recomecar, String NomeImpressoraComprovante) {
        boolean Retorno = false;
        try {
            Dadosorc d = SetarDadosOrc(CodigoVenda);
            ResultSet rsItens = ItensorcRN.Itensorc_Listar(CodigoVenda);
            if (PDVComprovante.Iniciar(cTipoComprovantePDV, ecf, NomeImpressoraComprovante)) {
                if (PDVComprovante.AbrirCupom("", d, Sistema.getDadosLojaAtualSistema(), cTipoComprovantePDV, Recomecar, PreVenda)) {
                    Integer SeqUltimoItemImpresso = TratamentoNulos.getTratarInt().Tratar(VendaEmEdicao.VerificarVendaInterrompida(true).getInt("CodigoUltimoItemImpresso"), 0);
                    Integer SeqItem = 0;
                    String Itens = "";
                    ArrayList<Itensorc> itens = new ArrayList<>();
                    while (rsItens.next()) {
                        SeqItem = rsItens.getInt("seq");
                        if (SeqItem > SeqUltimoItemImpresso) {
                            Itensorc i = SetarItensorc(rsItens);
                            i.setCodigo(d);
                            i.setSeq(SeqItem);
                            //String SituacaoItem = TratamentoNulos.getTratarString().Tratar(i.getCodmov(),"");
                            //PDVComprovanteNFCe.ImprimirItem(i);
                            //PDVComprovanteNFCe.ImprimirItem(i); 
                            itens.add(i);

                            /* if(!VendaEmEdicao.RegistrarItemImpresso(CodigoVenda, i.getSeq())){
                             return false;
                             } */
                        }

                    }
                    Retorno = PDVComprovanteNFCe.ImprimirItens(itens);
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Retorno;
    }

    public static boolean CrediarioExtrato(Long nCodigoCliente, int Loja, int ObjetoCaixa, String NomeImpressoraComprovante) {
        try {
            ResultSet DuplicatasAbertas = Crediario.DuplicatasEmAberto_ComJuros(nCodigoCliente, Sistema.getLojaAtual(), false, "", "");
            if (PDVComprovante.CrediarioExtrato_Iniciar("nfiscal", NomeImpressoraComprovante)) {
                if (PDVComprovante.CrediarioExtrato_AbrirCupom_Acao(Loja, ObjetoCaixa, DuplicatasAbertas,nCodigoCliente)) {
                    return true;
                }
            }


        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        } finally {
            return true;
        }
    }

    private static boolean CrediarioExtrato_Iniciar(String TipoComprovante, String NomeImpNaoFiscal) {

        setTipoComprovante(TipoComprovante);
        setIniciou(false);
        setcNomeImpNaoFiscal(NomeImpNaoFiscal);

        if (!getImpressoraCompravante().isOK()) {
            ImpressoraDeComprovante_Iniciar();
            if (!getImpressoraCompravante().isOK()) {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível comunicar com a impressora configurada\n\nVerifique as configurações do pdv", "Impressora Não configurada");
                return false;
            }
        }
        setIniciou(getImpressoraCompravante().isOK());

        return isIniciou();
    }

    public static boolean Recebimento_IniciarComprovante(Long CodigoVenda, String cTipoComprovantePDV, ECFDinnamuS ecf, BaixarConta b, String NomeImpressoraComprovante) {
        boolean Retorno = false;
        try {
            Dadosorc d = SetarDadosOrc(CodigoVenda);
            //ResultSet rsItens = ItensorcRN.Itensorc_Listar(CodigoVenda);
            if (PDVComprovante.Recebimento_Iniciar(cTipoComprovantePDV, ecf, NomeImpressoraComprovante)) {
                if (PDVComprovante.Recebimento_AbrirCupom("", d, Sistema.getDadosLojaAtualSistema(), cTipoComprovantePDV, false, b)) {
                    Retorno = true;
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Retorno;
    }

    public static boolean Troca_AbrirCupom(ResultSet rsDadosEmpresa, Long CodigoTroca, boolean ImprimirDadosLoja) {
        boolean bRet = false;
        try {
            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                bRet = PDVComprovanteFiscal.Troca_AbrirCupom(rsDadosEmpresa, CodigoTroca, ImprimirDadosLoja);
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                bRet = PDVComprovanteNFCe.Troca_AbrirCupom(rsDadosEmpresa, CodigoTroca, ImprimirDadosLoja);
            } else {
                bRet = PDVComprovanteNaoFiscal.Troca_AbrirCupom(rsDadosEmpresa, CodigoTroca, ImprimirDadosLoja);
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            // return false;
        }
        return bRet;
    }

    public static boolean Troca_ImprimirItem(Itensdevolvidos i) {
        boolean bRet = false;
        try {
            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                bRet = PDVComprovanteFiscal.Troca_ImprimirItem(i);
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                bRet = PDVComprovanteNFCe.Troca_ImprimirItem(i);
            } else {
                bRet = PDVComprovanteNaoFiscal.Troca_ImprimirItem(i);
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            // return false;
        }
        return bRet;
    }

    public static boolean Troca_Fechamento(Double ValorCredito, Double Quantidade, boolean EjetarPapel) {
        boolean bRet = false;
        try {
            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                bRet = PDVComprovanteFiscal.Troca_Fechamento(ValorCredito, Quantidade, false);
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                bRet = PDVComprovanteNFCe.Troca_Fechamento(ValorCredito, Quantidade, EjetarPapel);

                PDVComprovanteNaoFiscal.AcionarGuilhotina(getcNomeImpNaoFiscal());
            } else {
                bRet = PDVComprovanteNaoFiscal.Troca_Fechamento(ValorCredito, Quantidade, EjetarPapel);

                PDVComprovanteNaoFiscal.AcionarGuilhotina(getcNomeImpNaoFiscal());
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            // return false;
        }
        return bRet;
    }

    public static boolean AbrirCupom(String cDadosCupom, Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean RecomecarNota, ArrayList<Long> PreVenda) {

        boolean ret = AbrirCupom_Acao(cDadosCupom, d, rsDadosEmpresa, cTipoNota, RecomecarNota, PreVenda);

        setImpressaoIniciada(ret);

        return ret;

    }

    public static boolean Recebimento_AbrirCupom(String cDadosCupom, Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean RecomecarNota, BaixarConta b/*,ArrayList<Long> Contas,Double ValorDaContas,Double ValorAReceberPelasContas, Double ValorRestante*/) {

        boolean ret = Recebimento_AbrirCupom_Acao(cDadosCupom, d, rsDadosEmpresa, cTipoNota, RecomecarNota, b);

        setImpressaoIniciada(ret);

        return ret;

    }

    public static boolean CrediarioExtrato_AbrirCupom_Acao(Integer Loja, Integer ObjetoCaixa, ResultSet dadoscontas, Long nCodigoCliente) {
        boolean bRet = false;
        try {
            String DadosCabeca = ComprovanteNaoFiscal.ComprovanteCrediarioExtrato_CabecaNota_Conteudo(Loja, ObjetoCaixa);
             
            String cDadosCliente = ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DadosCliente(nCodigoCliente,false);            
            
            String DadosConta = ComprovanteNaoFiscal.ComprovanteCrediarioExtrato_Contas_Conteudo(dadoscontas);

            String Dados = DadosCabeca  + DadosConta + cDadosCliente;

            if (PDVComprovanteNaoFiscal.getImpressora() == null) {
                PDVComprovanteNaoFiscal.setImpressora(getImpressoraCompravante());
            }
            bRet = PDVComprovanteNaoFiscal.CrediarioExtrato_Imprimir(Dados);

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }

    public static boolean Recebimento_AbrirCupom_Acao(String cDadosCupom, Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean RecomecarNota, BaixarConta b/* ArrayList<Long> Contas,Double ValorDaContas,Double ValorAReceberPelasContas, Double ValorRestante*/) {
        boolean bRet = false;
        try {
            String DadosCabeca = ComprovanteNaoFiscal.ComprovanteCrediario_Cabeca_Conteudo(d, rsDadosEmpresa, cTipoNota, true, true);
            String DadosConta = ComprovanteNaoFiscal.ComprovanteCrediario_Contas_Conteudo(d, b);
            String Dados = DadosCabeca + DadosConta;

            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                bRet = PDVComprovanteFiscal.Recebimento_AbrirComprovante(Dados);
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                bRet = PDVComprovanteNFCe.Recebimento_AbrirComprovante(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(), Dados);
            } else {
                if (PDVComprovanteNaoFiscal.getImpressora() == null) {
                    PDVComprovanteNaoFiscal.setImpressora(getImpressoraCompravante());
                }
                bRet = PDVComprovanteNaoFiscal.Recebimento_AbrirComprovante(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(), Dados);
                //return true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }

    public static String capturarCPF_Ou_CNPJ() {

        String cDadosCupom = "";
        try {

            if (Sistema.getDadosLojaAtualSistema().getBoolean("NFC_Ativar")) {
                //String IDCliente = "";
                String CPF = null;

                CPF = MetodosUI_Auxiliares_1.InputBox(null, "Digite o CPF/CNPJ\n[Enter]-Prossegue [Esc]-Cancela", "Identificação do Consumidor", "AVISO");

                if (CPF != null) {
                    CPF = ManipulacaoString.DeixarSomenteNumeros(CPF);
                    if (CPF.trim().length() == 0) {
                        cDadosCupom = "prosseguir";
                    } else {
                        if (CPF.trim().length() != 11 && CPF.trim().length() != 14) {
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Este campo aceita apenas CPF(11 Dígitos) ou CNPJ(14 Dígitos)", "CPF/CNPJ Inválido");
                            cDadosCupom = "invalido";
                        } else {
                            if (CPF.trim().length() == 11) {
                                if (!DigitoVerificadores.CPF(CPF)) {
                                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "CPF inválido", "Identificação do Consumidor", "AVISO");
                                    cDadosCupom = "invalido";
                                } else {
                                    cDadosCupom = CPF;
                                }
                            } else {
                                if (!DigitoVerificadores.isCnpjValido(CPF)) {
                                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "CNPJ inválido", "Identificação do Consumidor", "AVISO");
                                    cDadosCupom = "invalido";
                                } else {
                                    cDadosCupom = CPF;
                                }
                            }
                        }
                    }
                } else {
                    cDadosCupom = "ignorar";
                }
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return cDadosCupom;
    }

    public static boolean AbrirCupom_Acao(String cDadosCupom, Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean RecomecarNota, ArrayList<Long> PreVenda) {
        boolean bRet = false;
        try {
            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                if (Sistema.getDadosLojaAtualSistema().getBoolean("NFC_Ativar")) {
                    String IDCliente = "";
                    String CPF = null;
                    if (RecomecarNota) {
                        ResultSet rsUltimaVenda = VendaEmEdicao.VerificarVendaInterrompida();
                        if (rsUltimaVenda.next()) {
                            CPF = TratamentoNulos.getTratarString().Tratar(rsUltimaVenda.getString("idcliente"), "");
                            if (CPF.equalsIgnoreCase("")) {
                                CPF = null;
                            }
                        }
                    } else {
                        cDadosCupom = d.getNotaNome();
                        String cCNPJ_Ou_CPF = "";
                        int cupomaberto = PDVComprovanteFiscal.getEcfDinnmus().VerificarStatusCupomAberto();
                        if (cupomaberto == 0) {
                            cCNPJ_Ou_CPF = PDVComprovante.capturarCPF_Ou_CNPJ();
                            if (cCNPJ_Ou_CPF.equalsIgnoreCase("ignorar") || cCNPJ_Ou_CPF.equalsIgnoreCase("invalido")) {
                                return false;
                            } else if (cCNPJ_Ou_CPF.equalsIgnoreCase("prosseguir")) {
                                cCNPJ_Ou_CPF = "";
                            }
                        }
                        cDadosCupom = cCNPJ_Ou_CPF;
                        VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(d.getCodigo(), getEcfatual().UltimoCupom(), null, null, cDadosCupom, null, null, null, null, null, null, null);
                    }
                }
                bRet = PDVComprovanteFiscal.AbrirCupomFiscal(cDadosCupom);
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                bRet = PDVComprovanteNFCe.AbrirCupom(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(), d, rsDadosEmpresa, cTipoNota, PreVenda);
            } else {
                bRet = PDVComprovanteNaoFiscal.AbrirCupom(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(), d, rsDadosEmpresa, cTipoNota, PreVenda);
                //return true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }

    public static boolean EfetuarFormaPagto(String FormaPag, Double ValorFormaPag) {
        if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
            return PDVComprovanteFiscal.EfetuarFormaPagto(FormaPag, ValorFormaPag);
        } else {
            return true;
        }
    }

    public static boolean IniciaFechamentoCupom(String cAcredDesc, String cTipoAcredDesc, Double cValAcredDesc) {
        if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
            return PDVComprovanteFiscal.IniciaFechamentoCupom(cAcredDesc, cTipoAcredDesc, cValAcredDesc);
        } else {
            return true;
        }
    }

    public static boolean TerminaFechamento2(Dadosorc d) {
        boolean bRet = false;
        if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
            bRet = PDVComprovanteFiscal.TerminaFechamento(d, "");
        } else {
            bRet = true;
        }
        setIniciou(!bRet);
        return bRet;
    }

    public static Float FechaCupomVenda_ConcederDesconto(ResultSet rs) {
        Float DescontoAtacado = 0f;
        try {

            int nSeq = 0;
            Float nDesconto = 0f;
            //Float TotalDesconto =0f;
            while (rs.next()) {
                nSeq = rs.getInt("seq");
                nDesconto = rs.getFloat("desconto");
                //TotalDesconto+=nDesconto;
                if (nDesconto > 0f) {
                    if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                        boolean bIgnorarImpressao = false;
                        String[] MomentoImpressaoCupomFiscal = VendaEmEdicao.MomentoImpressaoCupomFiscal();
                        if (MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("") || MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("descontoitem")) {
                            if (MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("descontoitem") && !MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("")) {
                                int Item = Integer.valueOf(MomentoImpressaoCupomFiscal[1]);
                                if (Item == nSeq) {
                                    bIgnorarImpressao = true;
                                }
                            }
                        } else {
                            bIgnorarImpressao = true;
                        }
                        if (!bIgnorarImpressao) {
                            if (!PDVComprovanteFiscal.ConcederDescontoItemCupomFiscal(nSeq, nDesconto)) {
                                DescontoAtacado = -1f;
                                break;
                            } else {
                                VendaEmEdicao.RegistrarMomentoImpressaoCupomFiscal("descontoitem:" + nSeq);
                            }
                        }
                    } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                        if (!PDVComprovanteNFCe.ConcederDesconto(nSeq, nDesconto)) {
                            DescontoAtacado = -1f;
                            break;
                        }

                    } else {
                        if (!PDVComprovanteNaoFiscal.ConcederDesconto(nSeq, nDesconto)) {
                            DescontoAtacado = -1f;
                            break;
                        }
                    }
                }
                DescontoAtacado += nDesconto;
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            DescontoAtacado = -1f;
        }
        return DescontoAtacado;
    }
    /*
     public static boolean FecharCupomVenda(Dadosorc d) {
     return FecharCupomVenda(d, null,0);
     }*/

    public static ArrayList<HashMap<String, String>> CupomTEF_PrepararDados(ArrayList<String> VendasTEF) {
        ArrayList<HashMap<String, String>> RetornoVendas = new ArrayList<HashMap<String, String>>();
        try {

            /*String[] VendaTef = null;
             String[] VendaTef_Dados = null;
             String[] VendaTef_Dados_Item = null;
             String Key ="";
             String Valor= "";*/
            for (Integer i = 0; i < VendasTEF.size(); i++) {
                //VendaTef_Dados = VendasTEF.get(i).split("-dti-");
                HashMap<String, String> RetornoVenda = TransformacaoDados.TransformaStringEmHashmap(VendasTEF.get(i), "-dti-", "<=>"); // new HashMap<String, String>();
                if (RetornoVenda.size() == 0) {
                    break;
                }
                /*for (int j = 0; j < VendaTef_Dados.length; j++) {                    
                 VendaTef_Dados_Item= VendaTef_Dados[j].split("<=>");
                 Key = VendaTef_Dados_Item[0];
                 Valor = VendaTef_Dados_Item[1];
                 RetornoVenda.put(Key, Valor);
                 }*/
                RetornoVendas.add(RetornoVenda);

            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return RetornoVendas;

    }

    private static boolean CupomTEF(ArrayList<HashMap<String, Double>> Valores, ArrayList<String> VendasTEF, String COO, int MomentoVenda, int TipoComprovante, String TipoComprovanteImpresso) {
        try {
            ArrayList<HashMap<String, String>> RetornoVendas = CupomTEF_PrepararDados(VendasTEF);
            if (RetornoVendas.size() > 0) {

                TefPadrao.setImpressora_comprovante(getImpressoraCompravante());
                if (TefPadrao.ImprimirComprovanteTEF(Valores, RetornoVendas, getEcfatual(), COO, (TipoComprovante == 0 ? (MomentoVenda == 4 ? 1 : 0) : TipoComprovante), TipoComprovanteImpresso) == 1) {
                    return true;
                }

            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);

        }
        return false;
    }
    //private static ProcessamentoComProgresso processo_impressao = new ProcessamentoComProgresso();

    public static boolean FecharCupomRecebimento(Dadosorc d, ResultSet rsDescontoConcedido, int MomentoVenda, String TipoComprovante, ECFDinnamuS ecf, boolean ECFDiscponivel, Long CodigoTroca, boolean Reimp, BaixarConta b, String NomeImpressoraComprovante) {
        boolean Ret = false;
        try {
            //processo_impressao.Iniciar("FINALIZANDO COMPROVANTE...AGUARDE", 7);

            Ret = FecharCupomRecebimento_Acao(d, rsDescontoConcedido, MomentoVenda, TipoComprovante, ecf, ECFDiscponivel, CodigoTroca, Reimp, b, NomeImpressoraComprovante);

            // processo_impressao.Fechar();

        } catch (Exception e) {
            LogDinnamus.Log(e, true);

        }
        return Ret;
    }

    public static boolean Recebimento_SubTotaliza(Double ValorDuplicatas, int QtDuplicatas, Double ValorRecebidoCliente, Double Desconto, Double Acrescimo) {
        boolean Ret = false;
        try {

            String DadosSubtotalizacao = ComprovanteNaoFiscal.Recebimento_SubTotaliza(ValorDuplicatas, QtDuplicatas, ValorRecebidoCliente, Desconto, Acrescimo);
            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                if (getEcfDinnmus().ComprovanteNaoFiscal_Usar(DadosSubtotalizacao) == 1) {
                    Ret = true;
                }
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                Ret = PDVComprovanteNFCe.Recebimento_SubTotaliza(DadosSubtotalizacao);
            } else {
                Ret = PDVComprovanteNaoFiscal.Recebimento_SubTotaliza(DadosSubtotalizacao);
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public static boolean Recebimento_EfetuarPagto(String cConta, String cNomeForma, Double nValor) {
        boolean Ret = false;
        try {
            String DadosPagto = ComprovanteNaoFiscal.EfetuarFormaPagto(cConta, cNomeForma, nValor);

            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                if (getEcfDinnmus().ComprovanteNaoFiscal_Usar(DadosPagto) == 1) {
                    return true;
                }
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                if (PDVComprovanteNFCe.EfetuarFormaPagto(cConta, cNomeForma, nValor)) {
                    return true;
                }
            } else {
                if (PDVComprovanteNaoFiscal.EfetuarFormaPagto(cConta, cNomeForma, nValor)) {
                    return true;
                }
            }


        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public static boolean Recebimento_Finalizacao(Double nDinheiro, Double nTroco, String cMensagem, Long nCodigoCliente, Long nCodigoVenda) {
        boolean Ret = false;
        try {
            String DadosFinalizacao = ComprovanteNaoFiscal.Recebimento_TerminaFechamento(nDinheiro, nTroco, cMensagem, nCodigoCliente, nCodigoVenda);

            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                if (getEcfDinnmus().ComprovanteNaoFiscal_Usar(DadosFinalizacao) == 1) {
                    getEcfDinnmus().ComprovanteNaoFiscal_Fechar();
                    return true;
                }
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                if (PDVComprovanteNFCe.Recebimento_TerminaFechamento(DadosFinalizacao)) {
                    PDVComprovanteNFCe.AcionarGuilhotina(getcNomeImpNaoFiscal());
                    return true;
                }
            } else {
                if (PDVComprovanteNaoFiscal.Recebimento_TerminaFechamento(DadosFinalizacao)) {
                    PDVComprovanteNaoFiscal.AcionarGuilhotina(getcNomeImpNaoFiscal());
                    return true;
                }
            }


        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public static boolean FecharCupomRecebimento_Acao(Dadosorc d, ResultSet rsDescontoConcedido, int MomentoVenda, String TipoComprovante, ECFDinnamuS ecf, boolean ECFDiscponivel, Long CodigoTroca, boolean Reimp, BaixarConta b, String NomeImpressoraComprovante) {
        boolean bRet = false;
        boolean ImprimiuCupomFiscal = false;
        try {
            boolean PreVenda = false;
            String COO = "";
            ArrayList<HashMap<String, Double>> Valores = new ArrayList<HashMap<String, Double>>();
            ArrayList<String> InfoTEF = new ArrayList<String>();
            Float DescontoAtacado = 0f;
            ResultSet rsUltimaVenda = VendaEmEdicao.VerificarVendaInterrompida();
            String TipoComprovanteAtual = "";
            if (rsUltimaVenda.next()) {
                TipoComprovanteAtual = rsUltimaVenda.getString("tipocomprovante");
            } else {
                TipoComprovanteAtual = TipoComprovante;
            }

            setTipoComprovante(TipoComprovante);

            if (Reimp) {
                MomentoVenda = 2;
            }

            if (!PDVComprovante.Recebimento_IniciarComprovante(d.getCodigo(), TipoComprovante, ecf, b, NomeImpressoraComprovante)) {
                return false;
            }

            String cMsg = ""; //TratamentoNulos.getTratarString().Tratar( Sistema.getDadosLoja(Sistema.getLojaAtual(),true).getString("Mensagem"),"DinnamuS Tec.").trim();

            Double ValorTotalFormas = PagtoorcRN.Pagtorc_SomarValores(d.getCodigo());

            if (!Recebimento_SubTotaliza(b.getDuplicatas_Total(), b.getDuplicatas_Codigo().size(), ValorTotalFormas, d.getDesconto(), d.getAcrescimo())) {
                return false;
            }
            //processo_impressao.AtualizarProgresso("FINALIZANDO COMPROVANTE...AGUARDE", 3);
            ResultSet rs = PagtoorcRN.PagtoOrc_Listar(d.getCodigo());
            String cForma = "", cLetra = "";
            int nTotalParcelas = PagtoorcRN.PagtoOrc_Contar(d.getCodigo());

            Double nValorForma = 0d;
            boolean bUsarCNFV = false;
            // Long Idunico=0l;
            //boolean bIgnorarImpressao = false;
            //String[] MomentoImpressaoCupomFiscal =  null;
            while (rs.next()) {// Forma de Pagamento
                // Idunico = rs.getLong("idunico");
                cLetra = TratamentoNulos.getTratarString().Tratar(rs.getString("letra"), "").trim();
                if (cLetra.equalsIgnoreCase("")) {
                    cForma = rs.getString("grupoforma");
                } else {
                    cForma = TiposdePagamentos.NomeFormaPagamentoNoEcf(cLetra, d.getObjetoCaixa().toString());
                }
                if (cForma.length() == 0) {
                    cForma = rs.getString("grupoforma");
                }
                if (!bUsarCNFV) {
                    bUsarCNFV = rs.getBoolean("usarcnfv");
                }

                if (rs.getString("destino").equalsIgnoreCase("caixa") && d.getDinheiro().floatValue() > 0f && nTotalParcelas == 1) {
                    nValorForma = d.getDinheiro().doubleValue();
                } else {
                    nValorForma = rs.getDouble("valor");
                }
                String TEFStatus = rs.getString("tefstatus");

                if (TEFStatus != null) {
                    HashMap<String, Double> Valor = new HashMap<String, Double>();
                    Valor.put(cForma, nValorForma);
                    Valores.add(Valor);
                    String DadosInfoTef = rs.getString("tefinfo");
                    InfoTEF.add(DadosInfoTef);
                }

                if (!PDVComprovante.Recebimento_EfetuarPagto(TratamentoNulos.getTratarString().Tratar(rs.getString("forma"), ""), cForma, rs.getDouble("valor"))) {
                    return false;
                }
            }

            if (!PDVComprovante.Recebimento_Finalizacao(d.getDinheiro().doubleValue(), d.getTroco().doubleValue(), cMsg, new Long(d.getCodcliente()), d.getCodigo())) {
                return false;
            }

            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {// Termina o cupom
                if (Valores.size() > 0) {
                    System.out.println("INICIANDO IMPRESSAO TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                    // processo_impressao.Ocultar();
                    boolean bRetornoCupomTEF = CupomTEF(Valores, InfoTEF, COO, MomentoVenda, (ImprimiuCupomFiscal ? 0 : 1), getTipoComprovante());
                    if (!bRetornoCupomTEF) {
                        return false;
                    }
                    //processo_impressao.Exibir();
                    System.out.println("INICIANDO CNF TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                    TEFVenda.TEF_PDV_ConfirmarVendaPendente(d.getCodigo(), 0l);
                    System.out.println("TERMINANDO CNF TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                }
            }

            if (bUsarCNFV) {
                Long nCodigoCliente = Long.valueOf(d.getCodcliente());
                Long nCodigoVenda = d.getCodigo();
                int nLoja = d.getLoja();

                String cTextoCNFV = ComprovanteNaoFiscal.ComprovanteFiado(nCodigoVenda, nCodigoCliente, nLoja, d.getData());

                if (getTipoComprovante().equalsIgnoreCase("fiscal")) {// Termina o cupom
                    PDVComprovanteFiscal.ImprimirCNFV(cTextoCNFV);
                    PDVComprovanteFiscal.ImprimirCNFV(cTextoCNFV);
                } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {// Termina o cupom    
                    getImpressoraCompravante().Imprimir_Texto(cTextoCNFV.getBytes());
                } else {
                    getImpressoraCompravante().Imprimir_Texto(cTextoCNFV.getBytes());

                }
            }

            bRet = true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    public static boolean FecharCupomVenda(Dadosorc d, int MomentoVenda, String TipoComprovante, ECFDinnamuS ecf, boolean ECFDiscponivel, Long CodigoTroca, boolean Reimp, ArrayList<Long> PreVenda, String QRCode, String NomeImpressoraComprovante, RelatorioJasperXML jasperNFce) {
        boolean Ret = false;
        try {

            if (jasperNFce != null && TipoComprovante.equalsIgnoreCase("nfce")) {
                Ret = FecharCupomVenda_Acao_Padrao_IReport(d, MomentoVenda, TipoComprovante, CodigoTroca, Reimp, NomeImpressoraComprovante, jasperNFce);
            } else {
                Ret = FecharCupomVenda_Acao_Padrao(d, MomentoVenda, TipoComprovante, ecf, ECFDiscponivel, CodigoTroca, Reimp, PreVenda, QRCode, NomeImpressoraComprovante);
            }


        } catch (Exception e) {
            LogDinnamus.Log(e, true);

        }
        return Ret;
    }

    public static boolean FecharCupomVenda_Acao_Padrao_IReport(Dadosorc d,  int MomentoVenda, String TipoComprovante, Long CodigoTroca, boolean Reimp,  String NomeImpressoraComprovante, RelatorioJasperXML jasperNFce) {
        boolean Ret = false;
        try {

            String Nomeimpressora = getImpressoraDeComprovante();

            if (!jasperNFce.IsIniciado()) {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("A NFCe está habilitada para comprovantes iReport, mas o arquivo ireport não esta disponível", "IMPRESSÃO NÃO DISPONÍVEL");
                Ret = false;
            } else {
                if (!Nomeimpressora.equalsIgnoreCase("")) {

                    Double CreditoTroca = 0d;
                    String DadosDescontoCreditoTroca;
                    String cMsg = TratamentoNulos.getTratarString().Tratar(Sistema.getDadosLoja(Sistema.getLojaAtual(), true).getString("Mensagem"), "DinnamuS Tec.").trim();

                    if (CodigoTroca > 0l) {
                        CreditoTroca = Troca.ValorTotalCreditoTroca_PorID(CodigoTroca);
                        DadosDescontoCreditoTroca = "CRED. TROCA : R$ " + FormatarNumeros.FormatarParaMoeda(CreditoTroca);
                        if (!Reimp) {
                            d.setDesconto(d.getDesconto() + CreditoTroca.floatValue());
                        }
                    }
                    ResultSet rs = PagtoorcRN.PagtoOrc_Listar(d.getCodigo());
                    String cForma = "";
                    boolean bUsarCNFV = false;
                    Double nValorForma = 0d, ValorTotalFormas = 0d;
                    ArrayList<HashMap<String, Double>> Valores = new ArrayList<HashMap<String, Double>>();
                    ArrayList<String> InfoTEF = new ArrayList<String>();

                    while (rs.next()) {// Forma de Pagamento

                        if (!bUsarCNFV) {
                            bUsarCNFV = rs.getBoolean("usarcnfv");
                        }

                        nValorForma = rs.getDouble("valor");

                        ValorTotalFormas += nValorForma;


                        String TEFStatus = rs.getString("tefstatus");
                        if (TEFStatus != null) {
                            HashMap<String, Double> Valor = new HashMap<String, Double>();
                            Valor.put(cForma, nValorForma);
                            Valores.add(Valor);
                            String DadosInfoTef = rs.getString("tefinfo");
                            InfoTEF.add(DadosInfoTef);
                        }


                    }
                    ValorTotalFormas = NumeroArredondar.Arredondar_Double(ValorTotalFormas, 2);

                    String DadosImpostoAproximado = DadosImpostoAproximado(d.getCodigo(), ValorTotalFormas);

                    if (DadosImpostoAproximado.length() > 0) {
                        cMsg += "\n" + DadosImpostoAproximado;
                    }

                    Ret = new GerarDanfeNFCE().Imprimir_NfceDanfe(d.getCodigo(), jasperNFce, Nomeimpressora, false,PDVComprovante.getImpressoraDeComprovante());

                    if (Ret) {
                        if (Valores.size() == 0) {
                            PDVComprovanteNFCe.AcionarGuilhotina(NomeImpressoraComprovante);
                        } else {

                            System.out.println("INICIANDO IMPRESSAO TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                            // processo_impressao.Ocultar();
                            boolean bRetornoCupomTEF = CupomTEF(Valores, InfoTEF, "", MomentoVenda, 1, getTipoComprovante());
                            if (!bRetornoCupomTEF) {
                                return false;
                            }
                            //processo_impressao.Exibir();
                            System.out.println("INICIANDO CNF TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                            TEFVenda.TEF_PDV_ConfirmarVendaPendente(d.getCodigo(), 0l);
                            System.out.println("TERMINANDO CNF TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));

                        }

                        if (bUsarCNFV) {
                            Long nCodigoCliente = Long.valueOf(d.getCodcliente());
                            Long nCodigoVenda = d.getCodigo();
                            int nLoja = d.getLoja();

                            String cTextoCNFV = ComprovanteNaoFiscal.ComprovanteFiado(nCodigoVenda, nCodigoCliente, nLoja, d.getData());

                            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {// Termina o cupom
                                PDVComprovanteFiscal.ImprimirCNFV(cTextoCNFV);
                                PDVComprovanteFiscal.ImprimirCNFV(cTextoCNFV);
                            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {// Termina o cupom
                                getImpressoraCompravante().Imprimir_Texto(cTextoCNFV.getBytes());

                            } else {
                                getImpressoraCompravante().Imprimir_Texto(cTextoCNFV.getBytes());
                                //PDVComprovanteNaoFiscal.AcionarGuilhotina(cNomeImpNaoFiscal);   
                            }
                        }

                        if (CodigoTroca > 0l) {
                            // if(Sistema.isOnline()){
                            ImprimirCupomTroca(CodigoTroca, false);
                            //  }
                        }

                    }


                }
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public static boolean FecharCupomVenda_Acao_Padrao(Dadosorc d, int MomentoVenda, String TipoComprovante, ECFDinnamuS ecf, boolean ECFDiscponivel, Long CodigoTroca, boolean Reimp, ArrayList<Long> PreVendas, String QRCode, String NomeImpressoraComprovante) {
        boolean bRet = false;
        boolean ImprimiuCupomFiscal = false;
        try {
            PDVComprovante.setCPF_Ou_CNPJ("");
            boolean PreVenda = false;
            String COO = "";
            ArrayList<HashMap<String, Double>> Valores = new ArrayList<HashMap<String, Double>>();
            ArrayList<String> InfoTEF = new ArrayList<String>();
            Float DescontoAtacado = 0f;
            ResultSet rsUltimaVenda = VendaEmEdicao.VerificarVendaInterrompida();
            String TipoComprovanteAtual = "";
            boolean CupomFiscalFechado = false;
            if (rsUltimaVenda.next()) {
                TipoComprovanteAtual = rsUltimaVenda.getString("tipocomprovante");
                if (TipoComprovanteAtual.equalsIgnoreCase("fiscal") && MomentoVenda >= 3) {
                    if (ecf.VerificarStatusCupomAberto() == 1) {
                        ecf.CancelarUltimoCupom();
                        VendaEmEdicao.RegistrarItemImpresso(d.getCodigo(), 0);
                        if (!PDVComprovante.FecharPreVenda(d.getCodigo(), TipoComprovante, ecf, PreVendas, true, NomeImpressoraComprovante)) {
                            return false;
                        }

                    } else {
                        CupomFiscalFechado = true;
                    }
                }
            } else {
                TipoComprovanteAtual = TipoComprovante;
            }

            setTipoComprovante(TipoComprovante);

            if (Reimp) {
                MomentoVenda = 2;
                if (TipoComprovante.equalsIgnoreCase("nfce")) {
                    ResultSet rs = NFCE_Configurar.NotaEnviada(d.getCodigo(), d.getPdv());
                    if (rs.next()) {
                        QRCode = TratamentoNulos.getTratarString().Tratar(rs.getString("dadosqrcode"), "");
                    } else {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível obter os dados da nfce", "NFCe - Reimprimir");
                        return false;
                    }
                }
            }
            //processo_impressao.AtualizarProgresso("FINALIZANDO COMPROVANTE...AGUARDE", 1);

            if (ParametrosGlobais.getPreVenda_Codigo().size() > 0 || TipoComprovante.equalsIgnoreCase("nfce")) {
                if (TipoComprovante.equalsIgnoreCase("nfce")) {
                    if (!Reimp) {
                        if (!PDVComprovante.FecharPreVenda_nfce(d.getCodigo(), TipoComprovante, ecf, PreVendas, Reimp, NomeImpressoraComprovante)) {
                            return false;
                        }
                    }
                    // PDVComprovante.setCPF_Ou_CNPJ(cCNPJ_Ou_CPF);
                } else {

                    PreVenda = true;
                    boolean Concomitante = VendaEmEdicao.VerificarVendaInterrompida(true).getBoolean("prevenda_concomitante");
                    //getTipoComprovante()               
                    if (!Concomitante) {
                        if (!PDVComprovante.FecharPreVenda(d.getCodigo(), TipoComprovante, ecf, PreVendas, Reimp, NomeImpressoraComprovante)) {
                            return false;
                        }
                    }
                }
            } else {
                if (!TipoComprovanteAtual.equalsIgnoreCase(TipoComprovante)) {
                    if (!PDVComprovante.FecharPreVenda(d.getCodigo(), getTipoComprovante(), ecf, PreVendas, Reimp, NomeImpressoraComprovante)) {
                        return false;
                    } else {
                        if (TipoComprovante.equalsIgnoreCase("fiscal") && ECFDiscponivel) {
                            COO = ecf.UltimoCupom();
                        }
                        VendaEmEdicao.VendaEmEdicao_AtualizarTipoComprovante(TipoComprovante, COO);
                    }
                }
            }

            String cMsg = TratamentoNulos.getTratarString().Tratar(Sistema.getDadosLoja(Sistema.getLojaAtual(), true).getString("Mensagem"), "DinnamuS Tec.").trim();
            String DadosDescontoAtacado = "";
            String DadosDescontoCreditoTroca = "";
            if (DescontoAtacado > 0f) {
                DadosDescontoAtacado = "DESC ATAC : R$ " + FormatarNumeros.FormatarParaMoeda(DescontoAtacado);
            }
            Double CreditoTroca = 0d;
            if (CodigoTroca > 0l) {
                CreditoTroca = Troca.ValorTotalCreditoTroca_PorID(CodigoTroca);
                DadosDescontoCreditoTroca = "CRED. TROCA : R$ " + FormatarNumeros.FormatarParaMoeda(CreditoTroca);
               /* if (!Reimp) {
                    d.setDesconto(d.getDesconto() + CreditoTroca.floatValue());
                }*/
            }

            if (DadosDescontoAtacado.length() + DadosDescontoCreditoTroca.length() > 0) {
                cMsg += "\n" + DadosDescontoAtacado + (DadosDescontoAtacado.length() > 0 ? " " : "") + DadosDescontoCreditoTroca;
            }

            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                COO = getEcfatual().UltimoCupom();
                Double nAcrescimentoDesconto = 0d;
                String cTipo = "";
                if (d.getDesconto() < d.getAcrescimo()) { // Inicia fechamento
                    nAcrescimentoDesconto = d.getAcrescimo() - d.getDesconto();
                    cTipo = "A";
                } else {
                    nAcrescimentoDesconto = d.getDesconto() + d.getAcrescimo() - DescontoAtacado;
                    cTipo = "D";
                }
                if (MomentoVenda <= 3 && !CupomFiscalFechado) {
                    if (!PDVComprovanteFiscal.IniciaFechamentoCupom(cTipo, "$", nAcrescimentoDesconto)) {
                        return false;
                    }
                }
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                if (!PDVComprovanteNFCe.IniciaFechamentoCupom(d)) {
                    return false;
                }
            } else {
                if (!PDVComprovanteNaoFiscal.IniciaFechamentoCupom(d)) {
                    return false;
                }
            }
            //processo_impressao.AtualizarProgresso("FINALIZANDO COMPROVANTE...AGUARDE", 3);
            ResultSet rs = PagtoorcRN.PagtoOrc_Listar(d.getCodigo());
            String cForma = "", cLetra = "";
            int nTotalParcelas = PagtoorcRN.PagtoOrc_Contar(d.getCodigo());
            Double ValorTotalFormas = PagtoorcRN.Pagtorc_SomarValores(d.getCodigo());
            Double nValorForma = 0d;
            boolean bUsarCNFV = false;
            Long Idunico = 0l;
            boolean bIgnorarImpressao = false;
            String[] MomentoImpressaoCupomFiscal = null;
            while (rs.next()) {// Forma de Pagamento
                Idunico = rs.getLong("idunico");
                cLetra = TratamentoNulos.getTratarString().Tratar(rs.getString("letra"), "").trim();
                if (cLetra.equalsIgnoreCase("")) {
                    cForma = rs.getString("grupoforma");
                } else {
                    cForma = TiposdePagamentos.NomeFormaPagamentoNoEcf(cLetra, d.getObjetoCaixa().toString());
                }
                if (cForma.length() == 0) {
                    cForma = rs.getString("grupoforma");
                }
                if (!bUsarCNFV) {
                    bUsarCNFV = rs.getBoolean("usarcnfv");
                }

                nValorForma = rs.getDouble("valor");

                if (getTipoComprovante().equalsIgnoreCase("nfce") || getTipoComprovante().equalsIgnoreCase("fiscal")) {
                    String TEFStatus = rs.getString("tefstatus");
                    if (TEFStatus != null) {
                        HashMap<String, Double> Valor = new HashMap<String, Double>();
                        Valor.put(cForma, nValorForma);
                        Valores.add(Valor);
                        String DadosInfoTef = rs.getString("tefinfo");
                        InfoTEF.add(DadosInfoTef);
                    }
                }
                if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                    if (rs.getString("destino").equalsIgnoreCase("caixa") && d.getDinheiro().floatValue() > 0f && nTotalParcelas == 1) {
                        nValorForma = d.getDinheiro().doubleValue();
                    } else {
                        nValorForma = rs.getDouble("valor");
                    }
                    if (MomentoVenda <= 3) {
                        bIgnorarImpressao = false;

                        MomentoImpressaoCupomFiscal = VendaEmEdicao.MomentoImpressaoCupomFiscal();
                        System.out.println("Passo 7 " + MomentoImpressaoCupomFiscal);
                        if (!Reimp) {
                            if (MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("efetuarformapagto")) {
                                Long UltimoIdUnicoImpresso = Long.valueOf(MomentoImpressaoCupomFiscal[1]);
                                if (Idunico.longValue() <= UltimoIdUnicoImpresso.longValue()) {
                                    bIgnorarImpressao = true;
                                }
                            } else {
                                if (!MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("iniciarfechamento")) {
                                    bIgnorarImpressao = true;
                                }
                            }
                        }
                        if (!bIgnorarImpressao) {
                            if (!CupomFiscalFechado) {
                                if (!PDVComprovanteFiscal.EfetuarFormaPagto(cForma, nValorForma)) {
                                    return false;
                                } else {
                                    VendaEmEdicao.RegistrarMomentoImpressaoCupomFiscal("efetuarformapagto:" + Idunico);
                                }
                            }
                        }
                    }

                } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {

                    if (!PDVComprovanteNFCe.EfetuarFormaPagto(TratamentoNulos.getTratarString().Tratar(rs.getString("forma"), ""), cForma, rs.getDouble("valor"))) {
                        return false;
                    }
                } else {

                    if (!PDVComprovanteNaoFiscal.EfetuarFormaPagto(TratamentoNulos.getTratarString().Tratar(rs.getString("forma"), ""), cForma, rs.getDouble("valor"))) {
                        return false;
                    }

                }
            }
            //processo_impressao.AtualizarProgresso("FINALIZANDO COMPROVANTE...AGUARDE", 4);
            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {// Termina o cupom
                if (MomentoVenda <= 3) {
                    String DadosImpostoAproximado = DadosImpostoAproximado(d.getCodigo(), ValorTotalFormas);
                    if (DadosImpostoAproximado.length() > 0) {
                        cMsg += "\n" + DadosImpostoAproximado;
                    }
                    bIgnorarImpressao = false;
                    if (!Reimp) {
                        MomentoImpressaoCupomFiscal = VendaEmEdicao.MomentoImpressaoCupomFiscal();
                        if (!MomentoImpressaoCupomFiscal[0].equalsIgnoreCase("efetuarformapagto")) {
                            bIgnorarImpressao = true;
                        }
                    }
                    if (!CupomFiscalFechado) {
                        if (!bIgnorarImpressao) {
                            if (!PDVComprovanteFiscal.TerminaFechamento(d, ComprovanteNaoFiscal.CabecaNota_Conteudo_DadosPDV(d, getTipoComprovante(), false, PreVendas, false) + cMsg)) {
                                return false;
                            } else {
                                ImprimiuCupomFiscal = true;
                                VendaEmEdicao.RegistrarMomentoImpressaoCupomFiscal("terminarfechamento:0");
                            }
                        }
                    }
                }
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                String DadosImpostoAproximado = DadosImpostoAproximado(d.getCodigo(), ValorTotalFormas);
                if (DadosImpostoAproximado.length() > 0) {
                    cMsg += "\n" + DadosImpostoAproximado;
                }
                if (!PDVComprovanteNFCe.TerminaFechamento(d.getDinheiro().floatValue(), d.getTroco().floatValue(), cMsg, Long.valueOf(d.getCodcliente().equalsIgnoreCase("") ? "0" : d.getCodcliente()), d.getCodigo(), cNomeImpNaoFiscal, d.getNotaNome(), d.getCodvendedor(), d.getVendedor())) {
                    return false;
                }

                PDVComprovanteNFCe.ImprimirQRCode(d.getCodigo(), QRCode);
                PDVComprovanteNFCe.DadosFechamentoNFCe(d.getCodigo());
                if (Valores.size() == 0) {
                    PDVComprovanteNFCe.AcionarGuilhotina(getcNomeImpNaoFiscal());
                }
            } else {
                if (!PDVComprovanteNaoFiscal.TerminaFechamento(d.getDinheiro().floatValue(), d.getTroco().floatValue(), cMsg, Long.valueOf(d.getCodcliente().equalsIgnoreCase("") ? "0" : d.getCodcliente()), d.getCodigo(), cNomeImpNaoFiscal, d.getCodvendedor(), d.getVendedor())) {
                    return false;
                }
                PDVComprovanteNaoFiscal.AcionarGuilhotina(getcNomeImpNaoFiscal());
            }
            //processo_impressao.AtualizarProgresso("FINALIZANDO COMPROVANTE...AGUARDE",5);
            if (getTipoComprovante().equalsIgnoreCase("fiscal") || getTipoComprovante().equalsIgnoreCase("nfce")) {// Termina o cupom
                if (Valores.size() > 0) {
                    System.out.println("INICIANDO IMPRESSAO TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                    // processo_impressao.Ocultar();
                    boolean bRetornoCupomTEF = CupomTEF(Valores, InfoTEF, COO, MomentoVenda, (ImprimiuCupomFiscal ? 0 : 1), getTipoComprovante());
                    if (!bRetornoCupomTEF) {
                        return false;
                    }
                    //processo_impressao.Exibir();
                    System.out.println("INICIANDO CNF TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                    TEFVenda.TEF_PDV_ConfirmarVendaPendente(d.getCodigo(), 0l);
                    System.out.println("TERMINANDO CNF TEF " + DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                }
            }
            //processo_impressao.AtualizarProgresso("FINALIZANDO COMPROVANTE...AGUARDE",6);
            if (bUsarCNFV) {
                Long nCodigoCliente = Long.valueOf(d.getCodcliente());
                Long nCodigoVenda = d.getCodigo();
                int nLoja = d.getLoja();

                String cTextoCNFV = ComprovanteNaoFiscal.ComprovanteFiado(nCodigoVenda, nCodigoCliente, nLoja, d.getData());

                if (getTipoComprovante().equalsIgnoreCase("fiscal")) {// Termina o cupom
                    PDVComprovanteFiscal.ImprimirCNFV(cTextoCNFV);
                    PDVComprovanteFiscal.ImprimirCNFV(cTextoCNFV);
                } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {// Termina o cupom
                    getImpressoraCompravante().Imprimir_Texto(cTextoCNFV.getBytes());

                } else {
                    getImpressoraCompravante().Imprimir_Texto(cTextoCNFV.getBytes());
                    //PDVComprovanteNaoFiscal.AcionarGuilhotina(cNomeImpNaoFiscal);   
                }
            }

            if (CodigoTroca > 0l) {
                // if(Sistema.isOnline()){
                ImprimirCupomTroca(CodigoTroca, false);
                //  }
            }
            //processo_impressao.AtualizarProgresso("FINALIZANDO COMPROVANTE...AGUARDE",7);
            bRet = true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    public static boolean ImprimirCupomTroca(Long CodigoTroca, boolean ImprimirDadosLoja) {
        try {
            ResultSet rsDadosEmpresa = Sistema.getDadosLojaAtualSistema();
            if (Troca_AbrirCupom(rsDadosEmpresa, CodigoTroca, ImprimirDadosLoja)) {
                ResultSet rs = Troca.TrocasRealizadas_Itens(CodigoTroca, true, true, false);
                while (rs.next()) {
                    Itensdevolvidos i = new Itensdevolvidos();
                    i = SetarItensDevolvidos(rs);
                    if (!Troca_ImprimirItem(i)) {
                        return false;
                    }
                }
                Double ValorCredito = Troca.ValorTotalCreditoTroca_PorID(CodigoTroca);
                Double Quantidade = Troca.QuantidadeTotalCreditoTroca_PorID(CodigoTroca);
                if (!Troca_Fechamento(ValorCredito, Quantidade, true)) {
                    return false;
                }



                return true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return false;
    }

    public static String DadosImpostoAproximado(Long Codigo, Double ValorTotal) {
        String Retorno = "";
        try {

            Double ImpostoAprox = ImpostoNaNota.CalcularImposto(Codigo);
            Double PercImpostoAprox = NumeroArredondar.Arredondar_Double((ImpostoAprox / ValorTotal) * 100d, 2);
            Retorno = "VAL APROX TRIB R$ " + FormatarNumeros.FormatarParaMoeda(ImpostoAprox) + " (" + FormatarNumeros.FormatarParaMoeda(PercImpostoAprox) + "%) Fonte : IBPT";

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Retorno;
    }

    /**
     * @return the Iniciou
     */
    public static boolean isIniciou() {
        return Iniciou;
    }

    /**
     * @param aIniciou the Iniciou to set
     */
    public static void setIniciou(boolean aIniciou) {
        Iniciou = aIniciou;
    }

    /**
     * @return the EcfDinnmus
     */
    public static boolean CancelarComprovante() {
        return CancelarComprovante("", "");
    }

    public static boolean CancelarComprovante(String TipoComprovante, String ModeloImp) {
        boolean bRet = false;
        try {
            if (TipoComprovante.equalsIgnoreCase("")) {
                TipoComprovante = getTipoComprovante();
            }
            if (TipoComprovante.equalsIgnoreCase("fiscal")) {
                bRet = PDVComprovanteFiscal.CancelarCupomFiscal();
            } else if (TipoComprovante.equalsIgnoreCase("nfiscal") || (TipoComprovante.equalsIgnoreCase("nfce"))) {
                if (!PDVComprovante.getImpressoraCompravante().isOK()) {
                    PDVComprovante.ImpressoraDeComprovante_Iniciar();
                }
                if (TipoComprovante.equalsIgnoreCase("nfiscal")) {
                    bRet = PDVComprovanteNaoFiscal.CancelarCupom(UsuarioSistema.getNomeUsuario(), ModeloImp);
                } else {
                    bRet = PDVComprovanteNFCe.CancelarCupom(UsuarioSistema.getNomeUsuario());
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    public static boolean CancelarItem(Itensorc i) {
        boolean bRet = false;
        try {
            if (getTipoComprovante().equalsIgnoreCase("fiscal")) {
                bRet = PDVComprovanteFiscal.CancelarItemCupomFiscal(i.getSeq());
            } else if (getTipoComprovante().equalsIgnoreCase("nfce")) {
                bRet = PDVComprovanteNFCe.CancelarItem(i);
            } else {
                bRet = PDVComprovanteNaoFiscal.CancelarItem(i);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    public static boolean AbrirGaveta(String cTipoComprovante, String cNomeImp) {
        boolean bRet = false;
        try {
            if (cTipoComprovante.equalsIgnoreCase("fiscal")) {
                bRet = getEcfatual().AbrirGaveta();
            } else if (cTipoComprovante.equalsIgnoreCase("nfce")) {
                bRet = PDVComprovanteNFCe.AbrirGaveta(cNomeImp);
            } else {
                bRet = PDVComprovanteNaoFiscal.AbrirGaveta(cNomeImp);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }

    /**
     * @return the ecfatual
     */
    public static ECFDinnamuS getEcfatual() {
        return ecfatual;
    }

    /**
     * @param aEcfatual the ecfatual to set
     */
    public static void setEcfatual(ECFDinnamuS aEcfatual) {
        ecfatual = aEcfatual;
    }

    /**
     * @return the ImpressaoIniciada
     */
    public static boolean isImpressaoIniciada() {
        return ImpressaoIniciada;
    }

    /**
     * @param aImpressaoIniciada the ImpressaoIniciada to set
     */
    public static void setImpressaoIniciada(boolean aImpressaoIniciada) {
        ImpressaoIniciada = aImpressaoIniciada;
    }

    /**
     * @return the cNomeImpNaoFiscal
     */
    public static String getcNomeImpNaoFiscal() {
        return cNomeImpNaoFiscal;
    }

    /**
     * @param acNomeImpNaoFiscal the cNomeImpNaoFiscal to set
     */
    public static void setcNomeImpNaoFiscal(String acNomeImpNaoFiscal) {
        cNomeImpNaoFiscal = acNomeImpNaoFiscal;
    }

    /**
     * @return the CPF_Ou_CNPJ
     */
    public static String getCPF_Ou_CNPJ() {
        return CPF_Ou_CNPJ;
    }

    /**
     * @param aCPF_Ou_CNPJ the CPF_Ou_CNPJ to set
     */
    public static void setCPF_Ou_CNPJ(String aCPF_Ou_CNPJ) {
        CPF_Ou_CNPJ = aCPF_Ou_CNPJ;
    }

    /**
     * @return the ImpressoraCompravante
     */
    public static ImpressoraCompravante getImpressoraCompravante() {
        return ImpressoraCompravante;
    }

    /**
     * @param aImpressoraCompravante the ImpressoraCompravante to set
     */
    public static void setImpressoraCompravante(ImpressoraCompravante aImpressoraCompravante) {
        ImpressoraCompravante = aImpressoraCompravante;
    }
}
