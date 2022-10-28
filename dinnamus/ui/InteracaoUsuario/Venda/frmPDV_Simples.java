
/*
 * frmPDV.java
 *
 * Created on 26/05/2010, 17:54:58
 */

package dinnamus.ui.InteracaoUsuario.Venda;


import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensorc;
import dinnamus.entidades.recursos.Recursos;
import br.diversos.ControlarDigitacao;
import br.com.FormatarNumeros;
import br.com.ui.ItemLista;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.valor.formatar.FormatarNumero;
import br.String.ManipulacaoString;
import br.data.ManipularData;
import br.arredondar.NumeroArredondar;
import br.TratamentoNulo.TratamentoNulos;
import br.com.ui.BloquearTela;
import br.com.ui.InteracaoDuranteProcessamento;
import br.com.ui.ProcessamentoComProgresso;
import MetodosDeNegocio.Sincronismo.SincronizarMovimento;
import MetodosDeNegocio.Sincronismo.VerificarStatusServidor;

import MetodosDeNegocio.Fachada.cadproduto;
import MetodosDeNegocio.Fachada.clientes;
import MetodosDeNegocio.Fachada.tabeladepreco;
import MetodosDeNegocio.Seguranca.UsuarioAuditar;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import dinnamus.metodosnegocio.venda.caixa.ComprovanteNaoFiscal;
import MetodosDeNegocio.Venda.GerenciarCaixa;
import dinnamus.metodosnegocio.venda.caixa.PDVComprovante;
import MetodosDeNegocio.Venda.FormatadorDeTexto;
import MetodosDeNegocio.Venda.TratarCodigoBalanca;
import MetodosDeNegocio.Venda.Venda;
import MetodosDeNegocio.Venda.VendaEmEdicao;
import MetodosDeNegocio.Venda.pdvgerenciar;
import dinnamus.ui.InteracaoUsuario.Estoque.frmPesquisarProduto;
import UI.Seguranca.ValidarAcessoAoProcesso;

//import br.com.ui.controleteclas;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.tef.Padrao.TefPadrao;
import br.tef.Padrao.ui.TEFInteracao;
import br.transformacao.TransformacaoDados;
import br.ui.teclas.DefinirAtalhos2;
import br.ui.teclas.controleteclas;
import MetodosDeNegocio.RepositorioLocal.DAO_RepositorioLocal_Inicializar;
import MetodosDeNegocio.Entidades.Itensdevolvidos;
import MetodosDeNegocio.Sincronismo.Sincronismo;
import MetodosDeNegocio.Venda.PreVenda;
import dinnamus.metodosnegocio.venda.caixa.PDVComprovanteFiscal;
import static dinnamus.metodosnegocio.venda.caixa.PDVComprovanteFiscal.getEcfDinnmus;
import MetodosDeNegocio.ecf.ECFAtual;
import MetodosDeNegocio.Venda.Concorrencia;
import MetodosDeNegocio.Crediario.Crediario;
import MetodosDeNegocio.Crediario.entidade.BaixarConta;
import MetodosDeNegocio.Venda.DadosorcRN;
import MetodosDeNegocio.Venda.ItensDevolvidosRN;
import MetodosDeNegocio.Venda.ItensorcRN;
import MetodosDeNegocio.Venda.PreVendasSelecionadas;
import MetodosDeNegocio.Venda.PagtoorcRN;
import MetodosDeNegocio.Venda.ParametrosGlobais;
import MetodosDeNegocio.TEF.TEFVenda;
import MetodosDeNegocio.Venda.Estoque;
import MetodosDeNegocio.Venda.Troca;
import br.com.servidor.Dao_Jdbc_1;
import br.com.ui.ImagemTratamento;
import br.com.ui.SwingUtilidade;
import br.com.xml.XStream_Api;
import com.nfce.cancelamento.NFCE_DesfazerNFCE;
import com.nfce.config.NFCE_Configurar;
import com.nfce.config.NFCE_Contingencia;
import com.nfce.consultar.NFCE_ConsultarStatus;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import com.nfce.envio.NFCe_ConfiguracaoAmbiente;
import dinnamus.metodosnegocio.licencas.Licenca;
import dinnamus.rel.RelatorioJasperXML;

/**
 *
 * @author dti
 */

public class frmPDV_Simples extends javax.swing.JDialog {

    /**
     * @return the ProdutFotoWidth
     */
    public static int getProdutFotoWidth() {
        if (ProdutFotoWidth==0){
            ProdutFotoWidth=imagemProduto.getWidth();                         
        }
        return ProdutFotoWidth;
    }

    /**
     * @param aProdutFotoWidth the ProdutFotoWidth to set
     */
    public static void setProdutFotoWidth(int aProdutFotoWidth) {
        ProdutFotoWidth = aProdutFotoWidth;
    }

    /**
     * @return the ProdutFotoHeight
     */
    public static int getProdutFotoHeight() {
        if (ProdutFotoHeight==0){
            ProdutFotoHeight=imagemProduto.getHeight();                         
        }
        return ProdutFotoHeight;
    }
    public static void setProdutFotoHeight(int aProdutFotoHeight) {
        ProdutFotoHeight = aProdutFotoHeight;
    }


    /** Creates new form frmPDV */
    private ArrayList<String> ModosTrabalhoDisponiveis= null; 
    private boolean NFCe_OK=false;
    private boolean IniciouOK  = false;
    private boolean TEF_Ativo = false;
    public String TipoComprovanteAtual ="fiscal";
    private boolean TeclasFuncao = true;
    private int StatusTarefaCaixaLivre=0;
    private boolean ECFAtivo = false;
    private boolean ECFDisponivel = false;
    public  int MomentoDaVendaTEFInterrompida_PDV = 0;
    private boolean InterromperCapturaDeTeclas =false;
    private boolean ExecutarTarefaCaixaLivre=true;
    private boolean ForcarExibicaoDescanso=false;
    private Float TrocoUltimaVenda =0f;
    private boolean bInterroperTarefaCaixaLivre=false;
    private boolean ExecutarTarefaSincronismo=false;
    private boolean InterromperTarefaSincronismo=false;
    private boolean ExecutandoTarefaSincronismo=false;
    private boolean ModoDeRecebimento=false;
    private static String ControleCX="";
    private static Dadosorc dadosorc=null;
    private static Itensorc itensorc=null;
    private static ArrayList<Itensorc> arItensorc=new ArrayList<Itensorc>();
    private static Integer nCodigoFilial=0;
    private static Integer nCodigoObjetoCaixa=0;
    private static Integer nCodigoOperadorCX=0;
    //public static ECFDinnamuS EcfDinnmus = new ECFDinnamuS();
    private static boolean VendaRecuperada=false;
    private static String NomeImpressoraComprovante="";
    private String VendedorCodigo ="";
    private String VendedorNome = "";
    //private static boolean bParaThread =false;
    //private static boolean bAtualizacaoPedente =false;
    //private static ResultSet rsDescontoAtacado = null;
    private static int ProdutFotoWidth=0;
    private static int ProdutFotoHeight=0;    
    //private frmPDV_SimplesPagtos frmOpcoesPagto = new frmPDV_SimplesPagtos(null, true);
    private Long HostEmAtividade = 0l;
    //private boolean EnviarVenda =false;
    public String NomeCaixa = "";
    private static boolean CarregandoForm = true;
    private boolean AbrirFechamentoAutomaticamente=false;
    private int MomentoVendaInterrompida = 0;
    private BloquearTela bloq =null;
    private String TipoComprovantePDV ="CUPOM FISCAL.";
    private Thread ThreadSincronismo = null;
    private boolean MenuF1Ativo;
    public Integer Pagto_ViasCompVDaCrediario=0;
    public boolean Pagto_ComprovanteIReport;
    private RelatorioJasperXML jasperNFce;
            
    /**
     * @return the nDescontoLiberado
     */
    public static Float getnDescontoLiberado() {
        
        return nDescontoLiberado;

    }

    /**
     * @param anDescontoLiberado the nDescontoLiberado to set
     */
    public static void setnDescontoLiberado(Float anDescontoLiberado) {
        nDescontoLiberado = anDescontoLiberado;
    }

    /**
     * @return the ControleCX
     */
    public static String getControleCX() {
        return ControleCX;
    }

    /**
     * @param aControleCX the ControleCX to set
     */
    public static void setControleCX(String aControleCX) {
        ControleCX = aControleCX;
    }


    /**
     * @return the NomeImpressoraComprovante
     */
    public static String getNomeImpressoraComprovante() {
        return NomeImpressoraComprovante;
    }

    /**
     * @param aNomeImpressoraComprovante the NomeImpressoraComprovante to set
     */
    public static void setNomeImpressoraComprovante(String aNomeImpressoraComprovante) {
        NomeImpressoraComprovante = aNomeImpressoraComprovante;
    }
    
    private frmPDVItensVendidos itensVendidos=null;
    
    private static Float nDescontoLiberado=0f;
    //private static JFrame framebasico=null;
    frmClienteListagem formTabela;
    public boolean InicializarForm()
    {
        
         if(!UI_ValidarResolucao()){
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "As resoluções de vídeo compatíveis com o pdv são 800x600 e 1024x768. \n\nAjuste a resolução e tente novamente", "Problema com a Resolução", "AVISO");
            return false;
         }
        Sincronismo.SincronismoCadastroAuto(false,false);   
        SincronizarMovimento.Iniciar(false, false);  
        VerificarStatusServidor.setVerificarServidor(true);
        setModoRecebimento(false);
        
         
        if(!InicializarUI())
        {   
           return false;
        }
        else
        {
            //formTabela = new frmClienteListagem(null, true);
            controleteclas.SetarTodosOsBotoes(this.getContentPane());
            //controleteclas.UsarTeclaNaTrocaDeCampos(PainelSubtotal5, KeyEvent.VK_ENTER);
            if(UsuarioSistema.getIdCaixaAtual()==null){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O operador não possui um caixa aberto", "Pdv DinnamuS", "AVISO");
                //this.dispose();
                return false;
            }
            else
            {
            //ResultSet rs = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(), 0, UsuarioSistema.getIdCaixaAtual());
            ResultSet rs = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(), 0, pdvgerenciar.CaixaVinculado());
            try {
                if (rs.next()) {
                        setControleCX(rs.getString("controlecx"));
                        Pagto_ViasCompVDaCrediario =  TratamentoNulos.getTratarInt().Tratar(rs.getInt("viascompvdacrediario"),1);
                        Pagto_ComprovanteIReport =  rs.getBoolean("comprovantesireport");
                }
            } catch (SQLException ex) {
                LogDinnamus.Log(ex);
            }
            
            if(Pagto_ComprovanteIReport){
                String DirRel =  ManipulacaoArquivo2.DiretorioDeTrabalho()+ManipulacaoArquivo2.getSeparadorDiretorio() +"relatorios"+ ManipulacaoArquivo2.getSeparadorDiretorio() ;
            
                jasperNFce = new RelatorioJasperXML(DirRel+"DanfeNFCe-1.jasper");
            }
           
            this.setLocationRelativeTo(null);
            
            
            //this.setVisible(true);
            
            } 
            return true;
        }
    }
    
    
    
    
    public frmPDV_Simples(java.awt.Frame parent, boolean modal) {        
        this.setUndecorated(true);
        initComponents();        
        final Toolkit tk = Toolkit.getDefaultToolkit();           
        Dimension screenSize = tk.getScreenSize();  
        this.setSize(screenSize.width, screenSize.height); 
        lblECF.setVisible(false);        
        this.setLocationRelativeTo(null);                          
        lblStatusTEF.setVisible(false); 
        
        new Thread("Iniciar frmPDV_Simples"){
            public void run(){
                CarregandoForm=true;
                //MetodosUI_Auxiliares.BloquearDesbloquearComponentes(PainelCorpo, false);
                PainelCorpo.setVisible(false);
                PainelTopo.setVisible(false);

                //lblLogoCarregando.setIcon());           
                ImageIcon img =  new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/logo-dinnamus.png"));//  (ImageIcon)lblLogoCarregando.getIcon(); 
                //img.getImage().
                img.setImage( img.getImage().getScaledInstance((int)tk.getScreenSize().getWidth(),(int) tk.getScreenSize().getHeight(), 100));                //ImagemTratamento.AjustarTamanho(   img, (int) , (int) );
                lblLogoCarregando.setIcon(img);
                System.out.println("Tamanho Logo :  " + img.getIconHeight() + " " +  img.getIconWidth());
                
                PainelCarregando.setVisible(true);                
                
                IniciouOK =InicializarForm();                
                PainelCorpo.setVisible(true);
                PainelTopo.setVisible(true);
                PainelCarregando.setVisible(false);
                CarregandoForm=false;
                MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelCorpo, true);
            }
        }.start();        
        lblStafusSincronismo.setVisible(false);
        System.out.println("Retorno inicializar form " + CarregandoForm);
    }


    private void LimparCampoValoresProdutos() {
        txtSubTotal.setText("");
        txtQuantidade.setText("");
        txtPreco.setText("");
        txtNomeProduto.setText("");
    }

    private void LimparCamposClientes() {
        
        txtNomeCliente.setText("");
    }
     
    public Long RegistraItem_RegistrarEntidades_Dadosorc(boolean RegistroNovo)
    {
        Long nRet=Long.valueOf(0);
        Date dDataAbertura ;
        Integer nCodigoVendedor, nCodigoCliente,  nCodigoOperador;
        String cControleCX="";
        String cNomeVendedor,cNomeCliente,cNomeOperador;
        try {
            dDataAbertura=  DataHora.getStringToData(DataHora.getData(),"dd/MM/yyyy");
            nCodigoVendedor= Integer.parseInt(VendedorCodigo);
            cNomeVendedor=VendedorNome;
            nCodigoOperador=UsuarioSistema.getIdUsuarioLogado();
            cNomeOperador=UsuarioSistema.getNomeUsuario();
            nCodigoCliente= 0;//Integer.valueOf(getDadosorc().getCodcliente()) ;
            cNomeCliente="";            
            nRet=RegistraItem_RegistrarEntidades_Dadosorc_Persistencia( dDataAbertura, nCodigoVendedor,cNomeVendedor, nCodigoCliente, cNomeCliente, nCodigoOperador, cNomeOperador,Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(), UsuarioSistema.getIdCaixaAtual(), getControleCX(), pdvgerenciar.CodigoPDV(),RegistroNovo);
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nRet;
    }
    public Long RegistraItem_RegistrarEntidades_Dadosorc_Persistencia(Date dDataAbertura,Integer nCodigoVendedor,String cNomeVendedor, Integer nCodigoCliente, String cNomeCliente, Integer nCodigoOperador, String cNomeOperador, Integer nLoja, Integer nCodigoOpCaixa, Integer nCodigoObjetoCaixa, String cControleCX, int nCodigoPDV , boolean RegistroNovo)
    {
        Long nRetorno=Long.valueOf(0);
        try {
            if(RegistroNovo){
                getDadosorc().setCodigo(DAO_RepositorioLocal.NovoValorIdentidade("dadosorc", Sistema.getLojaAtual(),nCodigoPDV));
                getDadosorc().setData(dDataAbertura);            
                getDadosorc().setHora(Timestamp.valueOf(DataHora.getDataHoraAtual()));
                getDadosorc().setLoja(nLoja);
                getDadosorc().setFilial(nCodigoFilial);
                getDadosorc().setCodoperador(nCodigoOperador.toString());
                getDadosorc().setOperador(cNomeOperador);            
                getDadosorc().setControleCx("");
                getDadosorc().setObjetoCaixa(0);
                getDadosorc().setCodcaixa(0);
                getDadosorc().setRecebido("N");
                getDadosorc().setFeito("S");
                getDadosorc().setPdv(nCodigoPDV);
            }
            String CodVendedor = TratamentoNulos.getTratarString().Tratar(getDadosorc().getCodvendedor(),"");
            if(CodVendedor.equalsIgnoreCase("")){
                getDadosorc().setVendedor(cNomeVendedor.toString());
                getDadosorc().setCodvendedor(nCodigoVendedor.toString());
            }            
            nRetorno= DadosorcRN.Dadosorc_Incluir(DAO_RepositorioLocal.getCnRepLocal(), getDadosorc(), 0,RegistroNovo,true);
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nRetorno;
    }
    
    private SimpleAttributeSet FormatoDoCorpo(){
        return FormatoDoCorpo(0);
    }
            
    private SimpleAttributeSet FormatoDoCorpo(int TamanhoFonteAdicional)
    {
        //int TamanhoFonte = TamanhoDaFonte();
        SimpleAttributeSet atr;
        atr=FormatadorDeTexto.FonteNome("Courier New");
        atr.addAttributes(FormatadorDeTexto.FonteTamanho(TamanhoDaFonteItensRegistrados() + TamanhoFonteAdicional));
        return atr;
    }
    private SimpleAttributeSet FormatoDoCabacalho()
    {
        SimpleAttributeSet atr;
        atr=FormatadorDeTexto.FonteNome("Courier New");
        atr.addAttributes(FormatadorDeTexto.FonteTamanho(TamanhoDaFonte()+15));
        atr.addAttributes(FormatadorDeTexto.FonteNegrito());
        atr.addAttributes(FormatadorDeTexto.Alinhamento(FormatadorDeTexto.ALIGN_CENTER));
        return atr;
    }
     public int TamanhoDaFonteItensRegistrados(){
        int Fonte =0;
        try {
            
              Toolkit tk = Toolkit.getDefaultToolkit();         
              double Height= tk.getScreenSize().getSize().getHeight();
              double Width= tk.getScreenSize().getSize().getWidth();
              if (Width==800) {
                 Fonte = 10;
              }else  if (Width==1024) {
                    Fonte = 14;
              }else  if (Width==1280) {
                   Fonte = 16;
              }else  if (Width>=1360) {
                   Fonte = 16;                   
              }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
       return Fonte;
    }
    public int TamanhoDaFonte(){
        int Fonte =0;
        try {
            
              Toolkit tk = Toolkit.getDefaultToolkit();         
              double Height= tk.getScreenSize().getSize().getHeight();
              double Width= tk.getScreenSize().getSize().getWidth();
              if (Width==800) {
                 Fonte = 10;
              }else  if (Width==1024) {
                    Fonte = 14;
              }else  if (Width==1280) {
                   Fonte = 16;
              }else  if (Width>=1360) {
                   Fonte = 18;                   
              }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
       return Fonte;
    }
    public boolean AjustarFontes_UI(){
        try {
           int nTamanhoFonte = TamanhoDaFonte_Campos_DadosProduto();
           txtPreco.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonte));
           txtNomeProduto.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonte));
           txtQuantidade.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonte));
           txtSubTotal.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonte));
           int nTamanhoFonteTotal = TamanhoDaFonte_Campos_Totais();
           
           txtTotalItens.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal-10));
           lblQtItens.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal-10));
           //lblQtItensDevolvidos.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal-10));
           //txtQtItensDevolvidos.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal-10));
           
           //lblVendaBrutaDevolvido.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal));
           //txtVendaBrutaDevolvido.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal));
           //lblValorCreditoDevolvido.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal));
           //txtValorCreditoDevolvido.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal));
           lblTotal.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal-5));
           txtTotal.setFont(new Font("Tahoma",Font.BOLD,nTamanhoFonteTotal+5));
           
           
           
           
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    
    }
        public boolean UI_ValidarResolucao(){
        
        try {
            
              Toolkit tk = Toolkit.getDefaultToolkit();                       
              double Width= tk.getScreenSize().getSize().getWidth();
              //if (Width==800 || Width==1024) {
                 return true;
              //}
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
     
    }
    public int TamanhoDaFonte_Campos_DadosProduto(){
        int Fonte =0;
        try {
            
              Toolkit tk = Toolkit.getDefaultToolkit();         
              double Height= tk.getScreenSize().getSize().getHeight();
              double Width= tk.getScreenSize().getSize().getWidth();
              if (Width==800) {
                 Fonte = 20;
              }else  if (Width==1024) {
                    Fonte = 36;
              }else  if (Width==1280) {
                   Fonte = 38;
              }else  if (Width>=1360) {
                   Fonte =40;                   
              }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
       return Fonte;
    }
    
    public int TamanhoDaFonte_Campos_Totais(){
        int Fonte =0;
        try {
            
              Toolkit tk = Toolkit.getDefaultToolkit();         
              double Height= tk.getScreenSize().getSize().getHeight();
              double Width= tk.getScreenSize().getSize().getWidth();
              if (Width==800) {
                 Fonte = 22;
              }else  if (Width==1024) {
                    Fonte = 24;
              }else  if (Width==1280) {
                   Fonte = 26;
              }else  if (Width>=1360) {
                   Fonte = 28;                   
              }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
       return Fonte;
    }    
    
   
    public boolean  RegistrarItem_Tela_CabecaNota(Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean LimparCabecalho, ArrayList<Long> Notas  ){
     
        boolean bRet=false;
        String cDados="";
        try {            
            cDados = ComprovanteNaoFiscal.CabecaNota_Conteudo( d,  rsDadosEmpresa,  cTipoNota,true,Notas );
            String[] LinhaCabecalho = cDados.split(ManipulacaoArquivo2.newline);
            if(LimparCabecalho){
                txtCabecaNota.setText("");
            }
            String Cliente="", Vendedor=""; 
            for (int i = 0; i < LinhaCabecalho.length; i++) {
                if(i==0){ // linha do cabecalho com o nome do comprovante
                   lblNomeComprovante.setText(LinhaCabecalho[i]);
                   lblNomeComprovante.setFont(new Font("Courie New", Font.BOLD, TamanhoDaFonte()+15));
                   bRet=true;                   
                }else {
                    if(i==1){ // segunda linha

                        String Texto= "";
                        /*if(d.getCliente().length()>0 && d.getCliente().indexOf("** Consumidor **")<0){                            
                            Cliente = d.getCodcliente() + " - " + d.getCliente();
                            Cliente = "CLIENTE  : " + ManipulacaoString.Left(Cliente, ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora().length()-11);                            
                            EscreverNaTela_Cabecalho(Cliente + (i<LinhaCabecalho.length-1 ? ManipulacaoArquivo2.newline : ""), FormatoDoCorpo());                            
                        } */                   
                        if(d.getCodvendedor()!=null){
                            if(d.getCodvendedor().trim().length()>0){
                                if( !d.getCodvendedor().equalsIgnoreCase("0")){
                                    Vendedor = d.getCodvendedor() + " - " + d.getVendedor();
                                    if(d.getCodvendedor().indexOf(UsuarioSistema.getCodVendedorVinculadoAoCaixa()) <0){
                                        EscreverNaTela_Cabecalho("VENDEDOR : "+ Vendedor + (i<LinhaCabecalho.length-1 ? ManipulacaoArquivo2.newline : ""), FormatoDoCorpo());
                                    }
                                }
                            }
                        }
                        /*if(Cliente.length()>0 || Vendedor.length() >0){
                           EscreverNaTela_Cabecalho( ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()  + (i<LinhaCabecalho.length-1 ? ManipulacaoArquivo2.newline : ""), FormatoDoCorpo());
                        }*/
                    }
                    bRet=EscreverNaTela_Cabecalho(LinhaCabecalho[i] +  (i<LinhaCabecalho.length-1 ? ManipulacaoArquivo2.newline : ""), FormatoDoCorpo());
                    //txtCabecaNota.update(txtCabecaNota.getGraphics());
                }                
                if(!bRet){
                    break;
                }
            }
            //txtCabecaNota.update(txtCabecaNota.getGraphics());
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return bRet;
    }
     public boolean  RegistrarItem_Tela_CabecaNota_Comprovante_Crediario(Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean LimparCabecalho){
     
        boolean bRet=false;
        String cDados="";
        try {            
            cDados = ComprovanteNaoFiscal.ComprovanteCrediario_Cabeca_Conteudo( d,  rsDadosEmpresa,  cTipoNota,false,false/*,true,Contas ,false,false*/);
            String[] LinhaCabecalho = cDados.split(ManipulacaoArquivo2.newline);
            if(LimparCabecalho){
                txtCabecaNota.setText("");
            }
            String Cliente="", Vendedor=""; 
            for (int i = 0; i < LinhaCabecalho.length; i++) {
                if(i==0){ // linha do cabecalho com o nome do comprovante
                   lblNomeComprovante.setText(LinhaCabecalho[i]);
                   lblNomeComprovante.setFont(new Font("Courie New", Font.BOLD, TamanhoDaFonte()+15));
                   bRet=true;                   
                }else {
                    if(i==1){ // segunda linha
                        String Texto= "";
                        if(d.getCliente().length()>0 && d.getCliente().indexOf("** Consumidor **")<0){                            
                            Cliente = d.getCodcliente() + " - " + d.getCliente();
                            Cliente = "CLIENTE  : " + ManipulacaoString.Left(Cliente, ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora().length()-11);                            
                            EscreverNaTela_Cabecalho(Cliente + (i<LinhaCabecalho.length-1 ? ManipulacaoArquivo2.newline : ""), FormatoDoCorpo());                            
                        }                    
                        if(d.getCodvendedor()!=null){
                            if(d.getCodvendedor().trim().length()>0){
                                if( !d.getCodvendedor().equalsIgnoreCase("0")){
                                    Vendedor = d.getCodvendedor() + " - " + d.getVendedor();
                                    if(d.getCodvendedor().indexOf(UsuarioSistema.getCodVendedorVinculadoAoCaixa()) <0){
                                        EscreverNaTela_Cabecalho("VENDEDOR : "+ Vendedor + (i<LinhaCabecalho.length-1 ? ManipulacaoArquivo2.newline : ""), FormatoDoCorpo());
                                    }
                                }
                            }
                        }                        
                    }
                    bRet=EscreverNaTela_Cabecalho(LinhaCabecalho[i] +  (i<LinhaCabecalho.length-1 ? ManipulacaoArquivo2.newline : ""), FormatoDoCorpo());
                    //txtCabecaNota.update(txtCabecaNota.getGraphics());
                }                
                if(!bRet){
                    break;
                
                }
            }
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public boolean ModoRecebimento(boolean  ExibirMSG){
        boolean Ret = false;
        try {
            
            Ret = isModoRecebimento();
            if(Ret && ExibirMSG){                
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Operação não permitida com o sistema em MODO RECEBIMENTO ", "SISTEMA EM MODO RECEBIMENTO");                 
            }           
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public boolean EscreverNaTela(String cTexto)
    {
        
        return EscreverNaTela( cTexto, null);
    }
    public synchronized boolean EscreverNaTela_Cabecalho(final String cTexto, final SimpleAttributeSet attributeSet)
    {
        try {                                
     
                txtCabecaNota.getStyledDocument().insertString(txtCabecaNota.getStyledDocument().getLength(), cTexto , attributeSet);                            
     
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return false;
    
    }
    public synchronized boolean EscreverNaTela(final String cTexto, final SimpleAttributeSet attributeSet)
    {
        try { 
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {                        
                        try {                       
                            txtItens.getStyledDocument().insertString(txtItens.getStyledDocument().getLength(), cTexto , attributeSet);                            
                        } catch (BadLocationException ex) {
                            LogDinnamus.Log(ex, true);
                        }
                        if(txtItens.getStyledDocument().getLength()-1>=0){
                            txtItens.setCaretPosition(txtItens.getStyledDocument().getLength()-1);                                                                         
                        }
                    }
                });                
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return false;
    
    }
   
    public boolean PainelTotais_Crediario_Somar(Double nValorTotal, Integer nQTTotal)
    {
        boolean bRet=false;
        try {
            
            String Itens = ManipulacaoString.FormataPADL(3, nQTTotal.toString(), "0");
            txtTotalItens.setText(Itens);
                   
            txtTotal.setText("R$ " + FormatarNumeros.FormatarParaMoeda(nValorTotal.doubleValue()));
            
            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public boolean RegistrarITem_SomarTotalNotas()
    {
        boolean bRet=false;
        try {
            Long CodigoVenda = getDadosorc().getCodigo();
            Double nValorTotal=ItensorcRN.Itensorc_Somar(CodigoVenda);            
            Double nValorDevolvido=Troca.ValorTotalCreditoTroca_PorVenda(CodigoVenda);    
            Integer nQTTotal=ItensorcRN.Itensorc_SomarQuantidades(CodigoVenda).intValue();           
            
            if(PreVendasSelecionadas.getNotasSelecionadas().size()>0){
                nValorTotal -= PreVenda.SomarDescontoPreVenda(PreVendasSelecionadas.getNotasSelecionadas());
            }
            
            String Itens = ManipulacaoString.FormataPADL(3, nQTTotal.toString(), "0");
            txtTotalItens.setText(Itens);
            Double ValorFinal = NumeroArredondar.Arredondar_Double(nValorTotal - nValorDevolvido,2);       
            txtTotal.setText("R$ " + FormatarNumeros.FormatarParaMoeda(ValorFinal.doubleValue()));
            
            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public boolean RefazerNota()
    {
        boolean bRet=false;
        txtItens.setText("");
        if(ReEscreverItensNota())
            if(RegistrarITem_SomarTotalNotas())
                bRet=true;

        return bRet;
    }
    public static Itensorc SetarItensorc( Dadosorc dDadosorc,int nSeq , Long nItensorc_Idunico ,Long nIG_ChaveUnica, String cCodigoProduto,String cNomeProduto, Double nQuantidade, Double nPreco, Double nSubTotal, String cSituacaoTributaria, String cAliquota, Float nPercentualIcms, int nCodigoLoja, String cTabela, Long nCodigoReexibicao, int nCodigoPDV, boolean  bFracionado, String cCodMov, String Unidade, Float Custo, String NCM ,Integer CodigoVendedor, String SequenciaOriginal, Long CodigoOriginal, Double Descv, Double Descp,String CodCor,String CodTam)
    {
        Itensorc i=null;
        try {
            i=new Itensorc();
            //i.setIdunico(DAO_RepositorioLocal.NovoValorIdentidade("itensorc", Sistema.getLojaAtual(),nCodigoPDV));
            i.setCodigo(dDadosorc);
            i.setCodprod(nIG_ChaveUnica);
            i.setRef(cCodigoProduto);
            i.setNomeImpresso(cNomeProduto);
            i.setDescricao(cNomeProduto);
            i.setCodaliquota(cAliquota);
            i.setIcms(nPercentualIcms.toString());
            i.setPreco(new BigDecimal(nPreco.toString()));
            i.setPrecooriginal(i.getPreco());
            i.setQuantidade(new BigDecimal(nQuantidade.toString()));
            i.setData(ManipularData.DataAtual());
            i.setTotal(new BigDecimal(nSubTotal.toString()));
            i.setLiquido(new BigDecimal(nSubTotal.toString()));
            i.setDescv(new BigDecimal(Descv.toString()));
            i.setDescp(new BigDecimal(Descp.toString()));
            i.setTabela(cTabela);
            i.setSt(cSituacaoTributaria);
            i.setLoja(nCodigoLoja);
            i.setFracionado(bFracionado);
            i.setCodmov(cCodMov);
            i.setUnidade(Unidade);
            i.setCusto(new BigDecimal(Custo.toString()));
            i.setNcm(NCM);
            i.setCodigoVendedor(CodigoVendedor);
            i.setCodigoOriginal(CodigoOriginal);
            i.setSequenciaOriginal(SequenciaOriginal);
            i.setCodcor(CodCor);
            i.setNomecor(CodCor);
            i.setCodtam(CodTam);
            if(nCodigoReexibicao==0){
               i=Venda.RegistraItem_RegistrarEntidades_Itensorc_Persistencia(i,nCodigoReexibicao);
            }else{
                i.setSeq(nSeq);
                i.setIdunico(nItensorc_Idunico);
            }
            

            if(i.getIdunico()==0){
               return null;
            }

        } catch (Exception e) {
            i=null;
            LogDinnamus.Log(e);
        }
        return i;
    }
    public boolean ReEscreverItensNota()
    {

        boolean bRet=false;
        try {

            Itensorc itensorc=null;
            bRet=RegistrarItem_Tela_CabecaNota (getDadosorc(),Sistema.getDadosLojaAtualSistema(),TipoComprovantePDV,false,PreVendasSelecionadas.getNotasSelecionadas());
            for (int i = 0; i < arItensorc.size(); i++) {
                itensorc = arItensorc.get(i);
                if(!EscreverNaTela(ComprovanteNaoFiscal.RegistrarItem_Tela_Itens(itensorc.getSeq().toString(),
                        itensorc.getDescricao(),
                        itensorc.getRef(),
                        itensorc.getPreco().doubleValue(),
                        itensorc.getQuantidade().doubleValue(),
                        (itensorc.getDescv()==null ? 0 : itensorc.getDescv().doubleValue()), itensorc.getTotal().doubleValue(),itensorc.getCodcor(),itensorc.getCodtam()),FormatoDoCorpo()))
                        return false;
            }  
            bRet=true;
        }
        catch (Exception e) {
            LogDinnamus.Log(e);
        }


        return bRet;
    }
    public boolean ImpressaoLiberada(boolean PreVenda, boolean Concomitante){
        try {
            if(btImprimirComprovante.isSelected()){
               if(PreVenda){
                  return Concomitante;
               }else{
                  return true; 
               } 
            }else{
                return false;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    //public boolean RegistraItem(Integer nSeq , Long nItensorc_Idunico,Long nIG_ChaveUnica, String cCodigoProduto,String cNomeProduto, Float nQuantidade, Float nPreco,Float nDesc ,Float nSubTotal, String cSituacaoTributaria, String cAliquota, Float nPercentualIcms, String cTipoComprovante, String cTabela, Long nCodigoVendaReexibir, String cTipoComprovanteReexibido, boolean bContinuarImpressao, int nCodigoPDV, boolean bFracionado, boolean bItemCancelado,String cCodMov,String Unidade, Float Custo, boolean Recomecando, String NCM, boolean ImportandoPreVenda, boolean Concomitante)
    public boolean IniciarOperacaoComercial(Long CodigoOperacao){
        boolean Ret = false;
        try {            
            if(CodigoOperacao==0l){
              CodigoOperacao=RegistraItem_RegistrarEntidades_Dadosorc(true);
            }
            if(CodigoOperacao!=0){
                getDadosorc().setCodigo(CodigoOperacao);
                getDadosorc().setPdv(pdvgerenciar.CodigoPDV());
                getDadosorc().setObjetoCaixa(nCodigoObjetoCaixa);
                getDadosorc().setLoja(Sistema.getLojaAtual());
                getDadosorc().setCodoperador(UsuarioSistema.getIdUsuarioLogado().toString());
                getDadosorc().setOperador(UsuarioSistema.getNomeUsuario());                                              
                Ret=true;
            }else{
                return false;
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
        public boolean AbrirComprovanteCrediario(Long CodigoOperacao , BaixarConta b, boolean ReExibir){
        boolean bRet=false;
        ArrayList<Long> Contas = b.getDuplicatas_Codigo();
        
        ///Double DuplicatasTotal = b.getDuplicatas_Total();
        Integer DuplicatasQT = b.getDuplicatas_qt();
        Double ValorAReceberPelasContas = b.getDuplicatas_A_Receber();
      
        try {                               
            txtItens.setText("");            
            if(CodigoOperacao==0l || ReExibir){                                
                bRet = IniciarOperacaoComercial(CodigoOperacao);                                
                if(bRet){
                    bRet= RegistrarItem_Tela_CabecaNota_Comprovante_Crediario(getDadosorc(),Sistema.getDadosLojaAtualSistema(),"",false);                                               
                    if(!bRet){ return false;}
                }
            }
            String cDados = ComprovanteNaoFiscal.ComprovanteCrediario_Contas_Conteudo(getDadosorc(),  b);
            bRet = EscreverNaTela(cDados,FormatoDoCorpo());
            if(bRet){
                bRet = PainelTotais_Crediario_Somar(ValorAReceberPelasContas, DuplicatasQT);
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public boolean AbrirNota(String cTipoComprovante){
        boolean bRet=false;
        String cTipoComprovantePDV="";          
        String cDadosCabecaNota="";
        boolean PreVenda =false;
        try {
            if(getDadosorc().getCodigo()==null)
            {                          
                if(cTipoComprovante.equalsIgnoreCase("CUPOM FISCAL.")){
                    cTipoComprovantePDV="fiscal";
                    PDVComprovante.setTipoComprovante(TipoComprovanteAtual);
                    if(!ECFDisponivel){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ECF NÃO DISPONÍVEL", "ABRIR VENDA");
                       return false;
                    }
                }else if(!cTipoComprovante.equalsIgnoreCase("") && !cTipoComprovante.equalsIgnoreCase("nfce")){
                    cTipoComprovantePDV="nfiscal";
                    PDVComprovante.setTipoComprovante(TipoComprovanteAtual);
                    //if(!PDVComprovanteNaoFiscal.Iniciar()){
                     //   return false;
                   // }
                }else{
                    btImprimirComprovante.setSelected(false);
                }   
                txtItens.setText("");
                Long nCodigoVenda = Long.valueOf(0);
                
                if(lblNomeComprovante.getText().trim().length()==0){
                   nCodigoVenda=RegistraItem_RegistrarEntidades_Dadosorc(true);
                }else{
                   nCodigoVenda = VendaEmEdicao.CodigoVendaEmEdicao();
                }                
                if(nCodigoVenda!=0){
                    bRet=true;
                    if(bRet){
                        getDadosorc().setCodigo(nCodigoVenda);
                        getDadosorc().setPdv(pdvgerenciar.CodigoPDV());
                        getDadosorc().setObjetoCaixa(nCodigoObjetoCaixa);
                        getDadosorc().setCodoperador(UsuarioSistema.getIdUsuarioLogado().toString());
                        getDadosorc().setOperador(UsuarioSistema.getNomeUsuario());
                        
                        //txtSequencia1.setText(nCodigoVenda.toString());
                        if(lblNomeComprovante.getText().trim().length()==0){                          
                            bRet=RegistrarItem_Tela_CabecaNota (getDadosorc(),Sistema.getDadosLojaAtualSistema(),cTipoComprovante,false,PreVendasSelecionadas.getNotasSelecionadas());
                        }
                    }
                }else{
                    return false;
                }
            }
            else
            {
                bRet=true;
            }
            if(bRet){
                //bRet=false;               
                //bRet=true;
                if(getArItensorc().size()==0 ||  !PDVComprovante.isIniciou()){
                                   
                    if(TipoComprovanteAtual.equalsIgnoreCase("fiscal")){
                        if(!isECFDisponivel()){
                           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "A FUNÇÃO FISCAL NÃO ESTA DISPONÍVEL\n\nPARA MAIORES DETALHES CONSULTA O STATUS DO ECF [ F12\\CAIXA-[3]\\F5 ]", "ECF NÃO DISPONÍVEL");
                           return false;                        
                        }
                    }
                    if(!TipoComprovanteAtual.equalsIgnoreCase("nimp") && !TipoComprovanteAtual.equalsIgnoreCase("nfce") ){
                        if(PDVComprovante.Iniciar(TipoComprovanteAtual,getEcfDinnmus(),getNomeImpressoraComprovante())){                              
                            if(!PDVComprovante.AbrirCupom( "",getDadosorc(),Sistema.getDadosLojaAtualSistema(),TipoComprovanteAtual,false,PreVendasSelecionadas.getNotasSelecionadas())){
                               ApagarNota(true);
                               return false;
                            }
                        }else{                        
                             ApagarNota(true);
                             return false;
                        }
                    }
                    String COO="";
                    if(TipoComprovanteAtual.equalsIgnoreCase("fiscal") && btImprimirComprovante.isSelected()){
                       COO = getEcfDinnmus().UltimoCupom();
                    }
                    if(!PreVenda){
                        if(getArItensorc().size()==0){
                            if(VendaEmEdicao.CodigoVendaEmEdicao()==0){
                                VendaEmEdicao.RegistrarVendaEmEdicao(getDadosorc().getCodigo(),( btImprimirComprovante.getText().equalsIgnoreCase("Imprimir Comprovante - [ OFF ]") ? "" : cTipoComprovantePDV ),COO,PreVenda);
                            }
                        }
                    }else{                                    
                        VendaEmEdicao.VendaEmEdicao_AtualizarTipoComprovante(TipoComprovanteAtual, COO);
                    }
                }
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public boolean RegistraItem(Integer nSeq , Long nItensorc_Idunico,Long nIG_ChaveUnica, String cCodigoProduto,String cNomeProduto, Double nQuantidade, Double nPreco ,Double nSubTotal, String cSituacaoTributaria, String cAliquota, Float nPercentualIcms, String cTipoComprovante, String cTabela, Long nCodigoVendaReexibir, String cTipoComprovanteReexibido, boolean bContinuarImpressao, int nCodigoPDV, boolean bFracionado, boolean bItemCancelado,String cCodMov,String Unidade, Float Custo, boolean Recomecando, String NCM, boolean ImportandoPreVenda, boolean Concomitante, ArrayList<Long> PreVendas , String Cor, String Tam){
        return RegistraItem(nSeq, nItensorc_Idunico, nIG_ChaveUnica, cCodigoProduto, cNomeProduto, nQuantidade, nPreco, nSubTotal, cSituacaoTributaria, cAliquota, nPercentualIcms, cTipoComprovante, cTabela, nCodigoVendaReexibir, cTipoComprovanteReexibido, bContinuarImpressao, nCodigoPDV, bFracionado, bItemCancelado, cCodMov, Unidade, Custo, Recomecando, NCM, ImportandoPreVenda, Concomitante, PreVendas, 0, "", 0l,0d,0d,Cor,Tam);
    }
    public boolean RegistraItem(Integer nSeq , Long nItensorc_Idunico,Long nIG_ChaveUnica, String cCodigoProduto,String cNomeProduto, Double nQuantidade, Double nPreco,Double nSubTotal, String cSituacaoTributaria, String cAliquota, Float nPercentualIcms, String cTipoComprovante, String cTabela, Long nCodigoVendaReexibir, String cTipoComprovanteReexibido, boolean bContinuarImpressao, int nCodigoPDV, boolean bFracionado, boolean bItemCancelado,String cCodMov,String Unidade, Float Custo, boolean Recomecando, String NCM, boolean ImportandoPreVenda, boolean Concomitante, ArrayList<Long> PreVendas,Integer CodigoVendedor, String SequenciaOriginal, Long CodigoOriginal , Double Descv, Double Descp, String Cor, String Tam)
    {
        boolean bRet=false;
        //String cDadosCabecaNota="";
        boolean PreVenda =false;
        try {
            
            
            int nCodigoUsuario = ValidarAcessoAoProcesso.Verificar(null, UsuarioSistema.getIdUsuarioLogado(), "PDV_VDA", Sistema.getLojaAtual(), true, "REALIZAR VENDA");

            if (nCodigoUsuario == 0) {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "OPERADOR NÃO AUTORIZADO REALIZAR VENDA", "NÃO AUTORIZADO");
                return false;
            }
            
            if(PreVendas!=null){
                PreVenda = (PreVendas.size()>0l ? true : false);
            }
            
            if(arItensorc.size()==0 && getDadosorc().getCodigo()==null )
            {
                txtItens.setText("");
                Long nCodigoVenda = Long.valueOf(0);
                if(nCodigoVendaReexibir!=0){
                    nCodigoVenda = nCodigoVendaReexibir;
                }else{
                    if(lblNomeComprovante.getText().trim().length()==0){
                       nCodigoVenda=RegistraItem_RegistrarEntidades_Dadosorc(true);
                    }else{
                       nCodigoVenda = VendaEmEdicao.CodigoVendaEmEdicao();
                    }
                }
                if(nCodigoVenda!=0){
                    bRet=true;
                    if(bRet){
                        getDadosorc().setCodigo(nCodigoVenda);
                        getDadosorc().setPdv(pdvgerenciar.CodigoPDV());
                        getDadosorc().setObjetoCaixa(nCodigoObjetoCaixa);
                        getDadosorc().setCodoperador(UsuarioSistema.getIdUsuarioLogado().toString());
                        getDadosorc().setOperador(UsuarioSistema.getNomeUsuario());
                        String CodVendedor =TratamentoNulos.getTratarString().Tratar(getDadosorc().getCodvendedor(),"");
                        if(CodVendedor.equalsIgnoreCase("")){
                            getDadosorc().setCodvendedor(UsuarioSistema.getCodVendedorVinculadoAoCaixa());
                            getDadosorc().setVendedor(UsuarioSistema.getNomeVendedorVinculadoAoCaixa());
                        }
                        //txtSequencia1.setText(nCodigoVenda.toString());
                        
                        if(lblNomeComprovante.getText().trim().length()==0){
                            bRet=RegistrarItem_Tela_CabecaNota (getDadosorc(),Sistema.getDadosLojaAtualSistema(),cTipoComprovante,false,PreVendas);
                        }
                    }
                }else{
                    return false;
                }
            }
            else
            {
                if(PreVendas!=null){
                    if(PreVendas.size()>1 && arItensorc.size()==0){
                        //PreVenda = true;
                        if(lblNomeComprovante.getText().trim().length()==0){
                          bRet=RegistrarItem_Tela_CabecaNota (getDadosorc(),Sistema.getDadosLojaAtualSistema(),cTipoComprovante,false,PreVendas);
                        } 
                    }
                }
                bRet=true;
            }
            if(bRet){
                bRet=false;
                if(nPreco==0f){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O produto não pode ser registrado sem preco", "Produto sem Preço", "AVISO");
                    return false;
                }
                if(cCodigoProduto.trim()==""){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O produto não pode ser registrado sem um codigo valido", "Produto sem Código", "AVISO");
                    return false;
                }
                if(!ImportandoPreVenda){                 
                    if(PreVendasSelecionadas.getNotasSelecionadas().size()==1){
                        //PreVenda=true;
                        //Integer nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "BloqEdicaoNota", Sistema.getLojaAtual(), true, "EDIÇÃO PRÉ-VENDA");    // TODO add your handling code here:
                        //if(nCodigoUsuario==0){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL INCLUIR ITENS EM UMA OPERAÇÃO DE MESCLAGEM/PREVENDA", "MESCLAGEM/PREVENDA NÃO EDITÁVEL");
                        return false;
                        //}                    
                    }                   
                }                
                
                String cTipoComprovantePDV="";
                if(cTipoComprovanteReexibido.equalsIgnoreCase("")){
                    if(cTipoComprovante.equalsIgnoreCase("fiscal")){
                        cTipoComprovantePDV="fiscal";
                     }else if(cTipoComprovante.equalsIgnoreCase("nfce")){
                           cTipoComprovantePDV="nfce";
                    }else if(!cTipoComprovante.equalsIgnoreCase("")){
                        cTipoComprovantePDV="nfiscal";
                    }else{
                        btImprimirComprovante.setSelected(false);
                    }
                }else{
                    cTipoComprovantePDV = cTipoComprovanteReexibido;
                }
                System.out.println("Tipo de Comprovante : " +  cTipoComprovantePDV);    
                
                Itensorc i = SetarItensorc(getDadosorc(), nSeq,nItensorc_Idunico,nIG_ChaveUnica,  cCodigoProduto, cNomeProduto,  nQuantidade,  nPreco,  nSubTotal,  cSituacaoTributaria,  cAliquota, nPercentualIcms, Sistema.getLojaAtual() , cTabela, nCodigoVendaReexibir,nCodigoPDV,bFracionado, cCodMov,Unidade, Custo,NCM, CodigoVendedor, SequenciaOriginal, CodigoOriginal,Descv,Descp,Cor,Tam);
                if(i!=null){
                    if(i.getIdunico()!=0){
                        bRet=true;
                        if(getArItensorc().size()==0 ||  !PDVComprovante.isIniciou()){
                           
                            if(cTipoComprovantePDV.equalsIgnoreCase("fiscal")){
                                if(!isECFDisponivel()){
                                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "A FUNÇÃO FISCAL NÃO ESTA DISPONÍVEL\n\nPARA MAIORES DETALHES CONSULTA O STATUS DO ECF [ F12\\CAIXA-[3]\\F5 ]", "ECF NÃO DISPONÍVEL");
                                   return false;                        
                                }
                            }
                            PDVComprovante.setTipoComprovante(cTipoComprovante); 
                            if(ImpressaoLiberada(PreVenda , Concomitante )){
                              
                              if(PDVComprovante.Iniciar(cTipoComprovantePDV,getEcfDinnmus(),getNomeImpressoraComprovante())){
                                  
                                 if(!bContinuarImpressao && !PDVComprovante.isImpressaoIniciada() ){
                                    cTipoComprovantePDV=cTipoComprovante; 
                                     if(!VendaRecuperada){
                                         
                                         
                                        if(!PDVComprovante.AbrirCupom( "",getDadosorc(),Sistema.getDadosLojaAtualSistema(),cTipoComprovante,Recomecando,PreVendasSelecionadas.getNotasSelecionadas())){
                                          ApagarNota(true);
                                          return false;
                                       }
                                     }
                                }else{
                                    PDVComprovante.setTipoComprovante(cTipoComprovante);
                                    cTipoComprovantePDV=cTipoComprovante;
                                }
                              }else{
                                   // Nao inicio o comprovante
                                   ApagarNota(true);
                                   return false;
                              }
                            }
                            if(nCodigoVendaReexibir==0){
                                String COO="";
                                if(cTipoComprovantePDV.equalsIgnoreCase("fiscal") && btImprimirComprovante.isSelected()){
                                   COO = getEcfDinnmus().UltimoCupom();
                                }                                
                                if(!PreVenda){
                                    if(getArItensorc().size()==0){
                                        if(VendaEmEdicao.CodigoVendaEmEdicao()==0){
                                            VendaEmEdicao.RegistrarVendaEmEdicao(getDadosorc().getCodigo(),(!cTipoComprovantePDV.equalsIgnoreCase("nfce") && btImprimirComprovante.getText().equalsIgnoreCase("Imprimir Comprovante - [ OFF ]") ? "" : cTipoComprovantePDV ),COO,PreVenda);
                                        }
                                    }
                                }else{                                    
                                    VendaEmEdicao.VendaEmEdicao_AtualizarTipoComprovante(cTipoComprovante, COO);
                                }
                                    
                            }
                        }
                        PDVComprovante.setTipoComprovante(cTipoComprovantePDV);
                        if(ImpressaoLiberada(PreVenda , Concomitante )){
                            if(!bContinuarImpressao){
                                if(!bItemCancelado){
                                    if(!PDVComprovante.ImprimirItem(i)){
                                       ItensorcRN.Itensorc_Excluir(i.getIdunico(), i.getCodigo().getCodigo());                                                                              
                                       return false;
                                    }
                                }
                            }
                        }

                        if(!EscreverNaTela(ComprovanteNaoFiscal.RegistrarItem_Tela_Itens(i.getSeq().toString() ,cNomeProduto, cCodigoProduto, nPreco, nQuantidade, Descv, nSubTotal,false,bFracionado,Cor,Tam),FormatoDoCorpo())){
                           return false;
                        }
                        //txtItens.setCaretPosition(txtItens.getText().length()-1) ;

                        if(bItemCancelado){
                            if(!EscreverNaTela(ComprovanteNaoFiscal.RegistrarItem_Tela_CancelarItens(i,i.getSeq(), false),FormatoDoCorpo()) ){
                                return false;
                            }
                        }
                        getArItensorc().add(i);

                        if(!ImportandoPreVenda){
                            if(!RegistrarITem_SomarTotalNotas()){
                              getArItensorc().remove(arItensorc.size()-1);
                               return false;
                            }
                        }
                        
                        if(nCodigoVendaReexibir==0){
                            if(ImpressaoLiberada(PreVenda , Concomitante )){
                                VendaEmEdicao.RegistrarItemImpresso(getDadosorc().getCodigo(), i.getSeq());
                            }
                        }   
                        if(PreVenda &&  getArItensorc().size()==1){
                            PDVComprovante.setIniciou(true);
                        } 
                    
                    }
                }
            }            
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }


        return bRet;
    }
    private boolean RegistrarItensDaNota(ResultSet rs, Long nCodigoVendaReexibir, String cTipoComprovante, boolean bContinuarImpressao,int nCodigoPDV, boolean bItemCancelado, boolean ImportandoPreVenda, boolean Concomitante, boolean Recomecando, ArrayList<Long> PreVendas){
        boolean bRet=false;
        try {
                //Itensorc i=new Itensorc();
                Long nIG_ChaveUnica;
                String cCodigoProduto,Unidade="";
                String cNomeProduto;
                Double nQuantidade;
                Double nPreco;
                Double  DescV, DescP;
                Float Custo=0f;
                boolean bFracionado=false;
                Double nSubTotal;
                String cSituacaoTributaria;
                String cAliquota,NCM="",Cor ="",Tam="";
                Float nPercentualIcms;
                Integer  CodVendedor =TratamentoNulos.getTratarInt().Tratar(rs.getInt("codvendedor"), 0);
                String SequenciaOriginal = TratamentoNulos.getTratarString().Tratar(rs.getString("sequenciaoriginal"),"");;
                Long CodigoOriginal=TratamentoNulos.getTratarLong().Tratar(rs.getLong("codigooriginal"), 0l);
                //String cTipoComprovante;
                String cTabela;
                nIG_ChaveUnica = rs.getLong("codprod");
                cCodigoProduto = TratamentoNulos.getTratarString().Tratar(rs.getString("ref"),"");
                cNomeProduto = TratamentoNulos.getTratarString().Tratar(rs.getString("descricao"),"");
                nQuantidade = rs.getDouble("quantidade");
                nPreco = rs.getDouble("preco");
                DescV = rs.getDouble("descv");
                DescP = rs.getDouble("descp");
                
                nSubTotal =  rs.getDouble("total");
                bFracionado=rs.getBoolean("fracionado");
                Custo =rs.getFloat("custo");
                NCM=  TratamentoNulos.getTratarString().Tratar(rs.getString("ncm"),"");
                Unidade=TratamentoNulos.getTratarString().Tratar(rs.getString("unidade"),"UN");
                String cCodMov= TratamentoNulos.getTratarString().Tratar( rs.getString("codmov"),"");
                cSituacaoTributaria = TratamentoNulos.getTratarString().Tratar(rs.getString("st"),"").trim();
                cAliquota = TratamentoNulos.getTratarString().Tratar(rs.getString("codaliquota"),"");
                nPercentualIcms = rs.getFloat("icms");
                cTabela=TratamentoNulos.getTratarString().Tratar(rs.getString("tabela"),"");
                Cor =TratamentoNulos.getTratarString().Tratar(rs.getString("nomecor"),"");
                Tam =TratamentoNulos.getTratarString().Tratar(rs.getString("codtam"),"");
                bRet = RegistraItem(rs.getInt("seq"),rs.getLong("idunico"), nIG_ChaveUnica,  cCodigoProduto,cNomeProduto,  nQuantidade,  nPreco,   nSubTotal,  cSituacaoTributaria,  cAliquota,  nPercentualIcms,  cTipoComprovante,  cTabela, nCodigoVendaReexibir,cTipoComprovante,bContinuarImpressao,nCodigoPDV,bFracionado, bItemCancelado,cCodMov,Unidade,Custo,Recomecando,NCM,ImportandoPreVenda,Concomitante,PreVendas, CodVendedor, SequenciaOriginal, CodigoOriginal,DescV,DescP,Cor,Tam);
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private boolean ReExibirNotaInterrompida(Long nCodigoVenda, String cTipoComprovante, boolean bContinuarImpressao, int nCodigoPDV,int MomentoVendaInterrompida, ArrayList<Long> PreVenda, boolean ImportandoPreVenda, boolean Concomitante, String IDVendedor, String IdCliente){
        boolean bRet=false;
        try {
            ResultSet rsDadosorc = DadosorcRN.Dadosorc_Listar(nCodigoVenda);
            if(rsDadosorc.next()){
                Long nSeq = rsDadosorc.getLong("codigo");
                String cCodVendedor =  "";
                String NomeVendedor = "";
                String cCodCliente ="";
                String cNomeCliente="";
                if(!IDVendedor.equalsIgnoreCase("")){
                    ResultSet rsVendedor = Venda.Vendedor(new Long(IDVendedor), Sistema.getLojaAtual());
                    if(rsVendedor.next()){
                       cCodVendedor =  TratamentoNulos.getTratarString().Tratar(rsVendedor.getString("codigo"),"");
                       NomeVendedor =  TratamentoNulos.getTratarString().Tratar(rsVendedor.getString("nome"),"");
                    }
                }                
                if(!IdCliente.equalsIgnoreCase("")){
                    ResultSet rsCliente = Crediario.LocalizarCliente("codigo", IdCliente);
                    if(rsCliente.next()){
                       cCodCliente =   TratamentoNulos.getTratarString().Tratar(rsCliente.getString("codigo"),"");
                       cNomeCliente= TratamentoNulos.getTratarString().Tratar(rsCliente.getString("nome"),"");
                    }
                }
                Long CodigoOrcamento =0l,CodigoCotacao =0l;
                CodigoOrcamento= TratamentoNulos.getTratarLong().Tratar(rsDadosorc.getLong("codigoorcamento"),0l);
                CodigoCotacao= TratamentoNulos.getTratarLong().Tratar(rsDadosorc.getLong("codigocotacao"),0l);
                //MetodosUI_Auxiliares.SetarOpcaoCombo(cbVendedor,cCodVendedor);                
                //txtNomeCliente.setText(cCodCliente  + " - " + cNomeCliente);
                VendedorCodigo =  cCodVendedor;
                VendedorNome = NomeVendedor;
                ResultSet rsItens = ItensorcRN.Itensorc_Listar(nCodigoVenda);
                getDadosorc().setCodcliente(cCodCliente);
                getDadosorc().setCliente(cNomeCliente);
                getDadosorc().setData(rsDadosorc.getDate("data"));
                getDadosorc().setHora(rsDadosorc.getTimestamp("hora"));
                getDadosorc().setPdv(rsDadosorc.getInt("pdv"));
                getDadosorc().setOperador(rsDadosorc.getString("operador"));
                getDadosorc().setNotaNome(TratamentoNulos.getTratarString().Tratar( rsDadosorc.getString("nota_nome"),""));
                getDadosorc().setVendedor(NomeVendedor);
                getDadosorc().setCodvendedor(cCodVendedor);
                //DataHora.getStringToData(lblData.getText(),"dd/MM/yyyy");
                getDadosorc().setLoja(rsDadosorc.getInt("loja"));
                getDadosorc().setFilial(rsDadosorc.getInt("filial"));
                getDadosorc().setCodoperador( TratamentoNulos.getTratarString().Tratar( rsDadosorc.getString("codoperador"),""));
                getDadosorc().setOperador(TratamentoNulos.getTratarString().Tratar( rsDadosorc.getString("operador"),""));
                getDadosorc().setControleCx("");
                getDadosorc().setObjetoCaixa(0);
                getDadosorc().setCodcaixa(0);
                getDadosorc().setRecebido("N");
                getDadosorc().setFeito("S");
                getDadosorc().setCodigoorcamento(CodigoOrcamento);
                getDadosorc().setCodigocotacao(CodigoCotacao);
                int Itens =0;
                boolean bItemCancelado=false;
               
                
                while(rsItens.next()){
                    bItemCancelado = TratamentoNulos.getTratarString().Tratar(rsItens.getString("codmov"),"").equalsIgnoreCase("apagar") ? true : false;
                    if(!RegistrarItensDaNota(rsItens,nSeq,cTipoComprovante,bContinuarImpressao,nCodigoPDV, bItemCancelado,ImportandoPreVenda,Concomitante,true ,PreVenda)){
                        return false;
                    }                    
                }
                
               
                
                if(cTipoComprovante.equalsIgnoreCase("fiscal")){
                    AtivarModoFiscal();
                }else if(cTipoComprovante.equalsIgnoreCase("nfiscal")){
                    AtivarModoNaoFiscal(false);
                }else if(cTipoComprovante.equalsIgnoreCase("nfce")){
                    AtivarModoSemImpressao_NFCe();
                }else{                   
                      AtivarModoSemImpressao();                   
                }
                
                if(MomentoVendaInterrompida>=2 ){
                   AbrirFechamentoAutomaticamente=true; //PrepararFechamento(MomentoVendaInterrompida);
                }
               
                RegistrarITem_SomarTotalNotas();
                
                bRet=true;

            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    
    private boolean TerminarVenda(Double Troco){
        try {   
            if(Troco!=null){
              TrocoUltimaVenda = Troco.floatValue();
            }else{
              TrocoUltimaVenda=0f;  
            }
            ApagarNota(false);
            //frmDesconto.ValorVendaPDV=0f;
            //rsDescontoAtacado = null;           
            //if(TrocoUltimaVenda>0f){
            ForcarExibicaoDescanso=true;
            //}            
            //ExibirLogoDinnamuS();
            //ChamarFormCaixaLivre(TrocoUltimaVenda);
            SwingUtilidade.RequestFocus(txtProcurar);   
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean DefinirVendedor(){
        try {
             if(cbVendedor.getSelectedIndex()>=0){
                 ItemLista i =(ItemLista)cbVendedor.getSelectedItem();
                if (i.getIndice()==null) {
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "NAO EXISTE UM VENDEDOR VINCULADO AO CAIXA", "PDV", "AVISO");
                   return false;
                }
                
                if (i.getDescricao()==null) {
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "NAO EXISTE UM VENDEDOR VINCULADO AO CAIXA", "PDV", "AVISO");
                   return false;
                }
                
                getDadosorc().setCodvendedor(i.getIndice().toString());
                    
                getDadosorc().setVendedor(i.getDescricao().toString());                    
                
            }
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    
    private boolean  ProcurarProduto() {
        boolean Ret = false;
        try {
            InterromperTarefaSincronismo=true;
            Ret = ProcurarProduto_Acao();
            InterromperTarefaSincronismo=false;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean  ProcurarProduto_Acao() {
        ArrayList<String> arRetorno=null;
        boolean bRet=false;
        boolean bProdutoBalanca=false;
        try {

            if(ModoRecebimento(true))  {               
                return false;
             }
            if(ParametrosGlobais.getPreVenda_Codigo().size()>1){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EDIÇÃO NÃO PERMITIDA", "MESCLAGEM EM ANDAMENTO");
               return false;
            }
            ItemLista TabelaAtual =  (ItemLista) cbTbPreco.getSelectedItem();
            
            if(TabelaAtual==null){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "VERIFICAR TABELA DE PREÇO (F12 OPÇÃO : 6)", "PDV");
                return false;
            }
            
            int nTabelaPreco = Integer.parseInt(TabelaAtual.getIndice().toString());

            txtProcurar.setText(ManipulacaoString.LimparStringDeCaracteresInvalidos(txtProcurar.getText().toString().trim()));
            String CodigoALocalizar = txtProcurar.getText();
            String cCodigo = txtProcurar.getText().substring(0,1);
            arRetorno= new ArrayList<String>();                    
            if(cCodigo.equals("2") && CodigoALocalizar.length()==13){
                arRetorno = TratarCodigoBalanca.Tratar(txtProcurar.getText());
                if(arRetorno.size()>0){                    
                    if(!arRetorno.get(0).equalsIgnoreCase("0")){
                        bProdutoBalanca=true;
                        txtProcurar.setText(arRetorno.get(0));
                        if(arRetorno.get(1).equals("Peso")){
                            txtQuantidade.setText(arRetorno.get(2));
                        }                    
                    }
                }
            }
            ResultSet rs = cadproduto.Pesquisar(txtProcurar.getText().toString().trim(), Sistema.getCodigoLojaMatriz(), true, nTabelaPreco, 0, 0, 0f, true, true, 1);
            if(rs!=null){
                if(rs.getRow()<=0){                   
                   if(arRetorno.size()>0){
                      arRetorno=null;
                      bProdutoBalanca=false;
                      cCodigo = CodigoALocalizar;
                      
                      rs = cadproduto.Pesquisar(CodigoALocalizar.trim(), Sistema.getCodigoLojaMatriz(), true, nTabelaPreco, 0, 0, 0f, true, true, 1);                                            
                      txtProcurar.setText(CodigoALocalizar);  
                   } 
                }            
            }
            if (rs != null) {
                if(rs.isFirst()){
                    
                    try {
                        boolean bPrecoLivre = rs.getBoolean("precolivre");
                        Float  nPercentualDeIcms=0f, nCusto=0f;
                        Double  nPrecoProduto=0d, nSubTotal = 0d,nQuantidade = 0d;
                        if (bPrecoLivre) {
                            String cRetornoPrecoLivre= MetodosUI_Auxiliares_1.InputBox(null,"Digite o Valor" , "Produto com Preço Livre", "AVISO");
                            
                            try {
                                cRetornoPrecoLivre= cRetornoPrecoLivre.replaceAll(",", ".");
                                nPrecoProduto = Double.parseDouble(cRetornoPrecoLivre);
                                if(nPrecoProduto<=0 || nPrecoProduto >999){
                                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O valor informado está fora do limite aceitavel [0-999]", "Preço Livre Inválido", "AVISO"); 
                                   return false;
                                }       
                            } catch (Exception e) {
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Digite um preço válido", "Preço Livre", "AVISO");
                                return false;
                            }
                            
                        }else{
                            nPrecoProduto = rs.getDouble("ITP_PrecoVenda");
                        }
                        nPrecoProduto = NumeroArredondar.Arredondar_Double(nPrecoProduto, 2);
                                
                        boolean bFracionado =false;
                        String cTributacaoIcms="",cAliquota="", cTabela="",Unidade="",NCM="",Cor="",Tam="";
                        //rs.first();

                        String Arredondamento_truncamento  = Sistema.getDadosLojaAtualSistema().getString("ArredondamentoOuTrucamento")==null ? "Arredondamento" : Sistema.getDadosLojaAtualSistema().getString("ArredondamentoOuTrucamento");

                        txtNomeProduto.setText(rs.getString("CP_Nome"));
                        if (txtQuantidade.getText().trim().length() == 0) {
                            txtQuantidade.setText("1");
                        }
                        cTabela=cbTbPreco.getSelectedItem().toString();
                        nQuantidade = Double.parseDouble(txtQuantidade.getText());
                        if(Arredondamento_truncamento.equalsIgnoreCase("Arredondamento")){
                              
                             nSubTotal = NumeroArredondar.Arredondar_Double(nQuantidade * nPrecoProduto,2);
                        }else{
                            nSubTotal = nQuantidade * nPrecoProduto;
                            nSubTotal = NumeroArredondar.truncar(nSubTotal.toString(),2).doubleValue();
                        
                        }
                        
                        
                        
                        
                        bFracionado = rs.getBoolean("fracionado");
                        Unidade = TratamentoNulos.getTratarString().Tratar( rs.getString("unidade"),"UN");
                        nCusto = rs.getFloat("ultimopreco");
                        NCM = TratamentoNulos.getTratarString().Tratar(rs.getString("codigonbn"),"");
                        Tam =TratamentoNulos.getTratarString().Tratar(rs.getString("tamanho"),"");
                        Cor =TratamentoNulos.getTratarString().Tratar(rs.getString("cor"),"");
                        
                        if(bProdutoBalanca && !bFracionado){
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O produto não foi identificado como pesavel.", "Produto Pesável", "AVISO");
                            txtNomeProduto.setText("");
                            txtProcurar.setText("");
                            txtQuantidade.setText("");
                            LimparCampoValoresProdutos();
                            return false;
                        }
                        if(bProdutoBalanca){
                           if(arRetorno.get(1).equals("Valor")){
                              nQuantidade = NumeroArredondar.Arredondar_Double(Double.valueOf(arRetorno.get(2))/nPrecoProduto,3);
                              txtQuantidade.setText(nQuantidade.toString());
                              nSubTotal= (nQuantidade*nPrecoProduto);
                              //String Arredondamento_truncamento  = Sistema.getDadosLojaAtualSistema().getString("ArredondamentoOuTrucamento")==null ? "Arredondamento" : Sistema.getDadosLojaAtualSistema().getString("ArredondamentoOuTrucamento");

                              if(Arredondamento_truncamento.equalsIgnoreCase("Arredondamento")){
                                nSubTotal =  NumeroArredondar.Arredondar_Double(nSubTotal,2).doubleValue();  //  Float.valueOf(arRetorno.get(2));
                            
                              }else{
                                nSubTotal =  NumeroArredondar.truncar(nSubTotal.toString(),2).doubleValue();  //  Float.valueOf(arRetorno.get(2));
                            
                              }
                                  
                            }
                        }else{
                            
                        }       
                        
                        Boolean BloqueiaProdutoZerado =  Sistema.getDadosLojaAtualSistema().getBoolean("BloqueiaProdutoZerado");
                        if(BloqueiaProdutoZerado && Sistema.isOnline()){
                            Double EstoqueDisponivel = Estoque.QuantidadeDisponivel(Sistema.getLojaAtual(), Sistema.CodigoDaFilial_LojaAtual(), rs.getLong("IG_Chaveunica"));
                           
                            Double QuantidadeVendidaPDV = 0d;
                            if(getDadosorc().getCodigo()!=null){
                               QuantidadeVendidaPDV = Estoque.QuantidadeVendidaPDV(getDadosorc().getCodigo(), rs.getLong("IG_Chaveunica"));
                            }
                            Double QtUltrapassaEstoque = EstoqueDisponivel - QuantidadeVendidaPDV - nQuantidade;
                            if(QtUltrapassaEstoque <0){
                                StringBuilder sb = new StringBuilder();
                                sb.append("")
                                        .append(txtNomeProduto.getText())
                                        .append("\n\n")
                                        .append("ESTOQUE ATUAL  : ")
                                        .append(EstoqueDisponivel.toString());
                                         
                                        //.append(Unidade)
                                        //.append(" em estoque "); 
                                        if(QuantidadeVendidaPDV>0){
                                            sb.append("\nQUANTIDADE JA VENDIDA : ")
                                            .append(QuantidadeVendidaPDV.toString())
                                            .append("\nESTOQUE DISPONIVEL : " )
                                            .append( EstoqueDisponivel - QuantidadeVendidaPDV);
                                                     
                                        }
                                        sb.append("\n")
                                        .append("\nNÃO SERÁ POSSÍVEL REGISTRAR O ITEM");
                                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples( new JFrame(), sb.toString(), "Estoque INSUFICIENTE!!", "AVISO");
                               
                                txtNomeProduto.setText("");
                                txtProcurar.setText("");
                                txtQuantidade.setText("");
                                LimparCampoValoresProdutos();
                                return false;
                            }
                        
                        }
                        cTributacaoIcms=(rs.getString("Tributaçãoicms")==null ? "1" : rs.getString("Tributaçãoicms")) ;
                        cAliquota=(  rs.getString("Codaliquota")==null ? "01"  : rs.getString("Codaliquota"));
                        nPercentualDeIcms= (rs.getFloat("Percentualdeicms")==0f ?  17f : rs.getFloat("Percentualdeicms"));

                        Blob Foto = rs.getBlob("foto");
                        byte[] Imagem = null; 
                        if(Foto!=null){
                          if(Foto.length()>0){
                              Long Tamanho = Foto.length();
                              Imagem = Foto.getBytes(1, Tamanho.intValue());
                          }  
                        }
                        txtPreco.setValue(nPrecoProduto);
                        txtPreco.commitEdit();
                        txtSubTotal.setValue(nSubTotal);                        
                        txtSubTotal.commitEdit();
                        if(btModoVenda.getText().contains("VENDENDO")){
                            bRet= RegistraItem(0,Long.valueOf(0),rs.getLong("IG_Chaveunica"), txtProcurar.getText(), txtNomeProduto.getText(),  nQuantidade, nPrecoProduto ,nSubTotal,cTributacaoIcms,cAliquota,nPercentualDeIcms,TipoComprovanteAtual,cTabela,Long.valueOf(0),"",false,pdvgerenciar.CodigoPDV(),bFracionado,false,"",Unidade, nCusto,false,NCM,false,false,null,Cor,Tam);
                            if(!bRet)
                            {
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples( new JFrame(), "O item não pode ser registrado", "Registro de Item", "AVISO");
                                LimparCampoValoresProdutos();
                            }else{                            
                                  
                                  ExibirFotoProduto(Imagem);
                            }
                        }
                        //txtProcurar.setValue(null);
                        txtProcurar.setText("");
                       txtQuantidade.setText("");
                    } catch (SQLException ex) {
                        LogDinnamus.Log(ex);

                    }
                }
                else{
                     txtNomeProduto.setText("");
                    //txtProcurar.setText(null);
                    String cCodigoNaoLocalizado=txtProcurar.getText();
                    txtProcurar.setText("");
                    txtQuantidade.setText("");
                    LimparCampoValoresProdutos();
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Produto não localizado [ " + cCodigoNaoLocalizado + " ]", "Pesquisar Produto", "AVISO");
                }

            } else {
                    ProcurarProduto();
            }            
        } catch (Exception e) {
                    LogDinnamus.Log(e);
        }

        return bRet;
    }
    public ImageIcon redimensionar(JLabel jLabel, int xLargura, int yAltura){
       
        ImageIcon img = new ImageIcon (jLabel.getIcon().toString());  
        img.setImage(img.getImage().getScaledInstance(xLargura, yAltura, 100));
       
        return img;
    }
   private boolean ExibirLogoStatusConexao(boolean Status){
        try {
           if(Status) {
               lblStatusConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/redeonline.png")));           
               lblStatusConexao.setText("ONLINE");
           }else{
               lblStatusConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/redeoffline.png")));           
               lblStatusConexao.setText("OFFLINE");
           }
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean ExibirLogoDinnamuS(){
        try {
            
           imagemProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/DinnamuS.jpg")));           
           ImageIcon img = (ImageIcon)imagemProduto.getIcon(); 
           ImagemTratamento.AjustarTamanho(img, getProdutFotoWidth(), getProdutFotoHeight());                             
           imagemProduto.setIcon(img);
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean ExibirFotoProduto(final byte[] FotoProduto){
        try {                                
            if(FotoProduto!=null){
                if(FotoProduto.length>0){                    
                     if (getProdutFotoWidth()==0 && getProdutFotoHeight()==0){
                         setProdutFotoWidth(imagemProduto.getWidth());
                         setProdutFotoHeight(imagemProduto.getHeight());
                          //ProdutFotoHeight=;                     
                     }
                     new Thread("Exibir Foto Produto") {
                     @Override
                     public void run() {                                                  
                         try {                              
                             ImageIcon img = ImagemTratamento.AjustarTamanho(FotoProduto, getProdutFotoWidth(), getProdutFotoHeight());                             
                             imagemProduto.setIcon(img);
                             
                         } catch (Exception ex) {
                             LogDinnamus.Log(ex, true);
                         }                         
                     }                     
                     }.start();
                     return true; 
                  }                  
              }
              imagemProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/sem imagem 4.jpg")));
              return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void LiberarObjetos()
    {
        try {
            setDadosorc(null);
            setItensorc(null);
            setArItensorc(null);
            ParametrosGlobais.setBaixarConta(null);
            ParametrosGlobais.setPreVenda_Codigo(null);
            if(PDVComprovante.getImpressoraCompravante()!=null){
                PDVComprovante.getImpressoraCompravante().Fechar();
            }
            
            //parent=null;

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btGaveta = new javax.swing.JToggleButton();
        txtECF = new javax.swing.JTextField();
        btImprimirComprovante = new javax.swing.JToggleButton();
        cbTbPreco = new javax.swing.JComboBox();
        cbVendedor = new javax.swing.JComboBox();
        txtNomeCliente = new javax.swing.JTextField();
        txtNomeVendedor = new javax.swing.JTextField();
        PainelCorpo = new javax.swing.JPanel();
        PainelProduto = new javax.swing.JPanel();
        PainelPasseProduto1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNomeProduto = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        imagemProduto = new javax.swing.JLabel();
        PainelItensAtendimento = new javax.swing.JPanel();
        PainelTotalGeralNota = new javax.swing.JPanel();
        PainelItens = new javax.swing.JPanel();
        lblQtItens = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        PainelCamposTotal = new javax.swing.JPanel();
        txtTotalItens = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        PainelItensRegistrados = new javax.swing.JPanel();
        txtCabecaNota = new javax.swing.JTextPane();
        PainelItensRolavel = new javax.swing.JScrollPane();
        txtItens = new javax.swing.JTextPane();
        lblNomeComprovante = new javax.swing.JLabel();
        PainelVenda = new javax.swing.JPanel();
        PainelBotoes = new javax.swing.JPanel();
        btModoVenda = new javax.swing.JButton();
        btMenuPDV = new javax.swing.JButton();
        btCliente = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btVendedor = new javax.swing.JButton();
        btOcultarBarra = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btReimpressao = new javax.swing.JButton();
        painelLogoCliente = new javax.swing.JPanel();
        LogoCliente = new javax.swing.JLabel();
        PainelQuantidade = new javax.swing.JPanel();
        txtQuantidade = new javax.swing.JTextField();
        lblQt = new javax.swing.JLabel();
        PainelSubtotal = new javax.swing.JPanel();
        txtSubTotal = new javax.swing.JFormattedTextField();
        lblPreco1 = new javax.swing.JLabel();
        PainelPreco = new javax.swing.JPanel();
        txtPreco = new javax.swing.JFormattedTextField();
        lblPreco = new javax.swing.JLabel();
        PainelProcurar = new javax.swing.JPanel();
        txtProcurar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        PainelRodape = new javax.swing.JPanel();
        lblStatusConexao = new javax.swing.JLabel();
        lblStatusTEF = new javax.swing.JLabel();
        lblECF = new javax.swing.JLabel();
        lblStafusSincronismo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblRecepcao = new javax.swing.JLabel();
        lblEnvio = new javax.swing.JLabel();
        lblUltimoSincCadastro = new javax.swing.JLabel();
        lblNFCe = new javax.swing.JLabel();
        lblNFCe_Contigencia = new javax.swing.JLabel();
        PainelTopo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btCliente1 = new javax.swing.JButton();
        lbltitulo = new javax.swing.JLabel();
        PainelCarregando = new javax.swing.JPanel();
        lblCarregandoPDV = new javax.swing.JLabel();
        lblLogoCarregando = new javax.swing.JLabel();

        btGaveta.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btGaveta.setMnemonic('g');
        btGaveta.setSelected(true);
        btGaveta.setText("Gaveta - On");
        btGaveta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGavetaActionPerformed(evt);
            }
        });

        txtECF.setEditable(false);
        txtECF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        btImprimirComprovante.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btImprimirComprovante.setMnemonic('P');
        btImprimirComprovante.setSelected(true);
        btImprimirComprovante.setText("Imprimir Comprovante - [ ON ]");
        btImprimirComprovante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirComprovanteActionPerformed(evt);
            }
        });

        cbTbPreco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTbPreco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTbPrecoKeyPressed(evt);
            }
        });

        cbVendedor.setBackground(new java.awt.Color(233, 237, 238));
        cbVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVendedorActionPerformed(evt);
            }
        });
        cbVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbVendedorKeyPressed(evt);
            }
        });

        txtNomeCliente.setEditable(false);
        txtNomeCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtNomeCliente.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtNomeCliente.setBorder(null);
        txtNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeClienteActionPerformed(evt);
            }
        });
        txtNomeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeClienteKeyPressed(evt);
            }
        });

        txtNomeVendedor.setEditable(false);
        txtNomeVendedor.setBackground(new java.awt.Color(255, 255, 255));
        txtNomeVendedor.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtNomeVendedor.setBorder(null);
        txtNomeVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeVendedorActionPerformed(evt);
            }
        });
        txtNomeVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeVendedorKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("DINNAMUS - CHECKOUT");
        setBackground(new java.awt.Color(153, 153, 153));
        setModal(true);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelCorpo.setBackground(new java.awt.Color(0, 0, 0));
        PainelCorpo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        PainelProduto.setBackground(new java.awt.Color(0, 0, 0));
        PainelProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PainelProduto.setAlignmentX(0.0F);
        PainelProduto.setAlignmentY(0.0F);
        PainelProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PainelProduto.setPreferredSize(new java.awt.Dimension(410, 433));
        PainelProduto.setLayout(new java.awt.GridBagLayout());

        PainelPasseProduto1.setBackground(new java.awt.Color(0, 0, 0));
        PainelPasseProduto1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelPasseProduto1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PainelPasseProduto1.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setBackground(new java.awt.Color(0, 0, 0));

        txtNomeProduto.setEditable(false);
        txtNomeProduto.setBackground(new java.awt.Color(0, 0, 0));
        txtNomeProduto.setColumns(20);
        txtNomeProduto.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtNomeProduto.setForeground(new java.awt.Color(255, 255, 255));
        txtNomeProduto.setLineWrap(true);
        txtNomeProduto.setRows(3);
        txtNomeProduto.setToolTipText("");
        txtNomeProduto.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtNomeProduto);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        PainelPasseProduto1.add(jScrollPane2, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        imagemProduto.setBackground(new java.awt.Color(0, 0, 0));
        imagemProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagemProduto.setMaximumSize(new java.awt.Dimension(259, 194));
        imagemProduto.setMinimumSize(new java.awt.Dimension(259, 194));
        imagemProduto.setOpaque(true);
        imagemProduto.setPreferredSize(new java.awt.Dimension(259, 194));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imagemProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 158, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(imagemProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPasseProduto1.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 20;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelProduto.add(PainelPasseProduto1, gridBagConstraints);

        PainelItensAtendimento.setBackground(new java.awt.Color(0, 0, 0));
        PainelItensAtendimento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        PainelItensAtendimento.setForeground(new java.awt.Color(255, 255, 255));
        PainelItensAtendimento.setPreferredSize(new java.awt.Dimension(400, 433));
        PainelItensAtendimento.setLayout(new java.awt.GridBagLayout());

        PainelTotalGeralNota.setBackground(new java.awt.Color(255, 255, 204));
        PainelTotalGeralNota.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelTotalGeralNota.setLayout(new java.awt.GridBagLayout());

        PainelItens.setBackground(new java.awt.Color(255, 255, 204));
        PainelItens.setLayout(new java.awt.GridBagLayout());

        lblQtItens.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblQtItens.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/cart_put.png"))); // NOI18N
        lblQtItens.setText("QUANTIDADE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelItens.add(lblQtItens, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotal.setText("TOTAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelItens.add(lblTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        PainelTotalGeralNota.add(PainelItens, gridBagConstraints);

        PainelCamposTotal.setBackground(new java.awt.Color(255, 255, 204));
        PainelCamposTotal.setLayout(new java.awt.GridBagLayout());

        txtTotalItens.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalItens.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalItens.setText("000");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelCamposTotal.add(txtTotalItens, gridBagConstraints);

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotal.setText("R$  0,00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelCamposTotal.add(txtTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelTotalGeralNota.add(PainelCamposTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelItensAtendimento.add(PainelTotalGeralNota, gridBagConstraints);

        PainelItensRegistrados.setBackground(new java.awt.Color(0, 0, 0));
        PainelItensRegistrados.setForeground(new java.awt.Color(255, 255, 255));
        PainelItensRegistrados.setOpaque(false);
        PainelItensRegistrados.setLayout(new java.awt.GridBagLayout());

        txtCabecaNota.setEditable(false);
        txtCabecaNota.setBackground(new java.awt.Color(0, 0, 0));
        txtCabecaNota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtCabecaNota.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        PainelItensRegistrados.add(txtCabecaNota, gridBagConstraints);

        PainelItensRolavel.setBackground(new java.awt.Color(255, 255, 204));
        PainelItensRolavel.setBorder(null);
        PainelItensRolavel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        PainelItensRolavel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtItens.setEditable(false);
        txtItens.setBackground(new java.awt.Color(0, 0, 0));
        txtItens.setBorder(null);
        txtItens.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtItens.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        PainelItensRolavel.setViewportView(txtItens);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.3;
        PainelItensRegistrados.add(PainelItensRolavel, gridBagConstraints);

        lblNomeComprovante.setBackground(new java.awt.Color(255, 255, 255));
        lblNomeComprovante.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        lblNomeComprovante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNomeComprovante.setToolTipText("");
        lblNomeComprovante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 0));
        lblNomeComprovante.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelItensRegistrados.add(lblNomeComprovante, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.6;
        PainelItensAtendimento.add(PainelItensRegistrados, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 19;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 7, 6, 10);
        PainelProduto.add(PainelItensAtendimento, gridBagConstraints);

        PainelVenda.setOpaque(false);
        PainelVenda.setLayout(new java.awt.GridBagLayout());

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));
        PainelBotoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        btModoVenda.setBackground(new java.awt.Color(0, 0, 0));
        btModoVenda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btModoVenda.setForeground(new java.awt.Color(255, 255, 255));
        btModoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO VERDE.png"))); // NOI18N
        btModoVenda.setText("[F2] VENDENDO");
        btModoVenda.setBorderPainted(false);
        btModoVenda.setMargin(new java.awt.Insets(0, 0, 0, 4));
        btModoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btModoVendaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelBotoes.add(btModoVenda, gridBagConstraints);

        btMenuPDV.setBackground(new java.awt.Color(0, 0, 0));
        btMenuPDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btMenuPDV.setForeground(new java.awt.Color(255, 255, 255));
        btMenuPDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/gear.png"))); // NOI18N
        btMenuPDV.setText("[F1] MENU ");
        btMenuPDV.setBorderPainted(false);
        btMenuPDV.setMargin(new java.awt.Insets(0, 0, 0, 4));
        btMenuPDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMenuPDVActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelBotoes.add(btMenuPDV, gridBagConstraints);

        btCliente.setBackground(new java.awt.Color(0, 0, 0));
        btCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btCliente.setForeground(new java.awt.Color(255, 255, 255));
        btCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/User group.png"))); // NOI18N
        btCliente.setMnemonic('c');
        btCliente.setText("[F3] CLIENTE");
        btCliente.setBorderPainted(false);
        btCliente.setMargin(new java.awt.Insets(0, 0, 0, 4));
        btCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelBotoes.add(btCliente, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        btVendedor.setBackground(new java.awt.Color(0, 0, 0));
        btVendedor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btVendedor.setForeground(new java.awt.Color(255, 255, 255));
        btVendedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Boss.png"))); // NOI18N
        btVendedor.setMnemonic('c');
        btVendedor.setText("[F5] VENDEDOR");
        btVendedor.setBorderPainted(false);
        btVendedor.setMargin(new java.awt.Insets(0, 0, 0, 4));
        btVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVendedorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(btVendedor, gridBagConstraints);

        btOcultarBarra.setBackground(new java.awt.Color(0, 0, 0));
        btOcultarBarra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btOcultarBarra.setForeground(new java.awt.Color(255, 255, 255));
        btOcultarBarra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/delete.png"))); // NOI18N
        btOcultarBarra.setText("[F11]  BARRA");
        btOcultarBarra.setBorderPainted(false);
        btOcultarBarra.setMargin(new java.awt.Insets(0, 0, 0, 4));
        btOcultarBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOcultarBarraActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(btOcultarBarra, gridBagConstraints);

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/printer.png"))); // NOI18N
        jButton2.setText("[F12] PV");
        jButton2.setBorderPainted(false);
        jButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(jButton2, gridBagConstraints);

        btReimpressao.setBackground(new java.awt.Color(0, 0, 0));
        btReimpressao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btReimpressao.setForeground(new java.awt.Color(255, 255, 255));
        btReimpressao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/ecfonline.png"))); // NOI18N
        btReimpressao.setMnemonic('c');
        btReimpressao.setText("[F9] Reimp");
        btReimpressao.setBorderPainted(false);
        btReimpressao.setMargin(new java.awt.Insets(0, 0, 0, 4));
        btReimpressao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReimpressaoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(btReimpressao, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.30000000000000004;
        gridBagConstraints.weighty = 0.1;
        PainelBotoes.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelVenda.add(PainelBotoes, gridBagConstraints);

        painelLogoCliente.setBackground(new java.awt.Color(0, 0, 0));
        painelLogoCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelLogoCliente.setLayout(new java.awt.GridBagLayout());

        LogoCliente.setBackground(new java.awt.Color(0, 0, 0));
        LogoCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/logo-dinnamus.png"))); // NOI18N
        LogoCliente.setMaximumSize(new java.awt.Dimension(50, 50));
        LogoCliente.setMinimumSize(new java.awt.Dimension(50, 50));
        LogoCliente.setOpaque(true);
        LogoCliente.setPreferredSize(new java.awt.Dimension(308, 80));
        LogoCliente.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                LogoClienteComponentResized(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        painelLogoCliente.add(LogoCliente, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        PainelVenda.add(painelLogoCliente, gridBagConstraints);

        PainelQuantidade.setBackground(new java.awt.Color(255, 255, 255));
        PainelQuantidade.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PainelQuantidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PainelQuantidade.setOpaque(false);
        PainelQuantidade.setLayout(new java.awt.GridBagLayout());

        txtQuantidade.setEditable(false);
        txtQuantidade.setBackground(new java.awt.Color(0, 0, 0));
        txtQuantidade.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtQuantidade.setForeground(new java.awt.Color(255, 255, 255));
        txtQuantidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuantidade.setBorder(null);
        txtQuantidade.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        PainelQuantidade.add(txtQuantidade, gridBagConstraints);

        lblQt.setBackground(new java.awt.Color(255, 255, 204));
        lblQt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblQt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQt.setText("QUANT  ");
        lblQt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        lblQt.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelQuantidade.add(lblQt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 5);
        PainelVenda.add(PainelQuantidade, gridBagConstraints);

        PainelSubtotal.setBackground(new java.awt.Color(255, 255, 255));
        PainelSubtotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PainelSubtotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PainelSubtotal.setPreferredSize(new java.awt.Dimension(1000, 84));
        PainelSubtotal.setLayout(new java.awt.GridBagLayout());

        txtSubTotal.setEditable(false);
        txtSubTotal.setBackground(new java.awt.Color(0, 0, 0));
        txtSubTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        txtSubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubTotal.setDisabledTextColor(new java.awt.Color(255, 255, 204));
        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.3;
        PainelSubtotal.add(txtSubTotal, gridBagConstraints);

        lblPreco1.setBackground(new java.awt.Color(255, 255, 204));
        lblPreco1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPreco1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPreco1.setText("SUBTOTAL  ");
        lblPreco1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelSubtotal.add(lblPreco1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 5);
        PainelVenda.add(PainelSubtotal, gridBagConstraints);

        PainelPreco.setBackground(new java.awt.Color(255, 255, 255));
        PainelPreco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PainelPreco.setToolTipText("");
        PainelPreco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PainelPreco.setOpaque(false);
        PainelPreco.setLayout(new java.awt.GridBagLayout());

        txtPreco.setEditable(false);
        txtPreco.setBackground(new java.awt.Color(0, 0, 0));
        txtPreco.setBorder(null);
        txtPreco.setForeground(new java.awt.Color(255, 255, 255));
        txtPreco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtPreco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPreco.setToolTipText("");
        txtPreco.setCaretColor(new java.awt.Color(255, 255, 255));
        txtPreco.setDisabledTextColor(new java.awt.Color(255, 255, 204));
        txtPreco.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtPreco.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        PainelPreco.add(txtPreco, gridBagConstraints);

        lblPreco.setBackground(new java.awt.Color(255, 255, 204));
        lblPreco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPreco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPreco.setText("PREÇO  ");
        lblPreco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        lblPreco.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPreco.add(lblPreco, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 5);
        PainelVenda.add(PainelPreco, gridBagConstraints);

        PainelProcurar.setLayout(new java.awt.GridBagLayout());

        txtProcurar.setBackground(new java.awt.Color(0, 0, 0));
        txtProcurar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtProcurar.setForeground(new java.awt.Color(255, 255, 255));
        txtProcurar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProcurar.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        txtProcurar.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtProcurarAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        txtProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProcurarActionPerformed(evt);
            }
        });
        txtProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProcurarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProcurarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProcurarKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelProcurar.add(txtProcurar, gridBagConstraints);

        jLabel3.setBackground(new java.awt.Color(255, 255, 204));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PROCURAR - [F4]");
        jLabel3.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelProcurar.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 5);
        PainelVenda.add(PainelProcurar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        PainelProduto.add(PainelVenda, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelCorpo.add(PainelProduto, gridBagConstraints);

        PainelRodape.setBackground(new java.awt.Color(0, 0, 0));
        PainelRodape.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));
        PainelRodape.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                PainelRodapeComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                PainelRodapeComponentShown(evt);
            }
        });
        PainelRodape.setLayout(new java.awt.GridBagLayout());

        lblStatusConexao.setBackground(new java.awt.Color(255, 255, 255));
        lblStatusConexao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblStatusConexao.setForeground(new java.awt.Color(255, 255, 255));
        lblStatusConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/redeonline.png"))); // NOI18N
        lblStatusConexao.setText("ONLINE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblStatusConexao, gridBagConstraints);

        lblStatusTEF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatusTEF.setForeground(new java.awt.Color(255, 255, 255));
        lblStatusTEF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/TEFONLINE.png"))); // NOI18N
        lblStatusTEF.setText("TEF OK   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblStatusTEF, gridBagConstraints);

        lblECF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblECF.setForeground(new java.awt.Color(255, 255, 255));
        lblECF.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblECF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/ecfonline.png"))); // NOI18N
        lblECF.setText("ECF OK  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblECF, gridBagConstraints);

        lblStafusSincronismo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStafusSincronismo.setForeground(new java.awt.Color(255, 255, 255));
        lblStafusSincronismo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Sync.gif"))); // NOI18N
        lblStafusSincronismo.setText("SINC    ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblStafusSincronismo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        PainelRodape.add(jLabel1, gridBagConstraints);

        lblRecepcao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblRecepcao.setForeground(new java.awt.Color(255, 255, 255));
        lblRecepcao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Download.png"))); // NOI18N
        lblRecepcao.setText("RECEPÇÃO OK     ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblRecepcao, gridBagConstraints);

        lblEnvio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEnvio.setForeground(new java.awt.Color(255, 255, 255));
        lblEnvio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Upload.png"))); // NOI18N
        lblEnvio.setText("ENVIO OK");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblEnvio, gridBagConstraints);

        lblUltimoSincCadastro.setForeground(new java.awt.Color(255, 255, 255));
        lblUltimoSincCadastro.setText("...");
        lblUltimoSincCadastro.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.7;
        PainelRodape.add(lblUltimoSincCadastro, gridBagConstraints);

        lblNFCe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNFCe.setForeground(new java.awt.Color(255, 255, 255));
        lblNFCe.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNFCe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logo-nfce-xs.png"))); // NOI18N
        lblNFCe.setText("NFCe OK  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblNFCe, gridBagConstraints);

        lblNFCe_Contigencia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNFCe_Contigencia.setForeground(new java.awt.Color(255, 255, 255));
        lblNFCe_Contigencia.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNFCe_Contigencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logo-nfc-offline.png"))); // NOI18N
        lblNFCe_Contigencia.setText("Contingência ");
        lblNFCe_Contigencia.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelRodape.add(lblNFCe_Contigencia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelCorpo.add(PainelRodape, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        getContentPane().add(PainelCorpo, gridBagConstraints);

        PainelTopo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTopo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), null));
        PainelTopo.setForeground(new java.awt.Color(255, 255, 255));
        PainelTopo.setLayout(new java.awt.GridBagLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 204));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/barra logo dinnamus.JPG"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        PainelTopo.add(jLabel2, gridBagConstraints);

        btCliente1.setBackground(new java.awt.Color(0, 0, 0));
        btCliente1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btCliente1.setForeground(new java.awt.Color(255, 255, 255));
        btCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btCliente1.setMnemonic('F');
        btCliente1.setText("FECHAR");
        btCliente1.setBorderPainted(false);
        btCliente1.setMargin(new java.awt.Insets(0, 0, 0, 4));
        btCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCliente1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.1;
        PainelTopo.add(btCliente1, gridBagConstraints);

        lbltitulo.setBackground(new java.awt.Color(255, 255, 204));
        lbltitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbltitulo.setText("AUTOMAÇÃO - [ PONTO DE VENDA ]");
        lbltitulo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 204), 1, true));
        lbltitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelTopo.add(lbltitulo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(PainelTopo, gridBagConstraints);

        PainelCarregando.setBackground(new java.awt.Color(0, 0, 0));
        PainelCarregando.setLayout(new java.awt.GridBagLayout());

        lblCarregandoPDV.setBackground(new java.awt.Color(0, 0, 0));
        lblCarregandoPDV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCarregandoPDV.setForeground(new java.awt.Color(255, 255, 255));
        lblCarregandoPDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/javafx-loading-100x100.gif"))); // NOI18N
        lblCarregandoPDV.setText("CARREGANDO PDV...AGUARDE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        PainelCarregando.add(lblCarregandoPDV, gridBagConstraints);

        lblLogoCarregando.setText("texto");
        lblLogoCarregando.setMaximumSize(new java.awt.Dimension(10, 10));
        lblLogoCarregando.setMinimumSize(new java.awt.Dimension(10, 10));
        lblLogoCarregando.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelCarregando.add(lblLogoCarregando, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelCarregando, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void ESCAPE_Sair(){
       try {
            boolean bSair = false;
            
            if (arItensorc==null){
                bSair=true;
            }else{
                if (arItensorc.size()==0){
                    bSair=true;
                }
            }            
            if(!bSair)
            {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Existe uma nota em edição. Operação de saida cancelada", "Fechamento do Checkout", "INFO");
            }
            else
                if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Confirma a saida do checkout?", "Saida do Checkout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {
                    LiberarObjetos();                    
                    ExecutarTarefaCaixaLivre=false;
                    setProdutFotoHeight(0);
                    setProdutFotoWidth(0);
                    this.dispose();                    
                    return;
                }
            txtProcurar.requestFocus();
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

    }    
    public void MenuPDV_TipoComprovante(){
        try {            
            if(arItensorc.size()==0)
            {
                MudarBotaoTipoComprovante();
            }
            txtProcurar.requestFocus();
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }    
    private void MudarBotaoTipoComprovante() {
        if(TipoComprovantePDV.matches("CUPOM VENDA"))
           TipoComprovantePDV="CUPOM FISCAL.";    
        else
            TipoComprovantePDV="CUPOM VENDA";
    }    
    public void MenuPDV_F2_Apagar(){
            MenuPDV_F2_Apagar(0);
    }
    public void MenuPDV_F2_Apagar(int nUsuario){
        try {
            if(PreVendasSelecionadas.getNotasSelecionadas().size()>0){
                ApagarNota(true);
            }else{
                Long CodigoVenda = TratamentoNulos.getTratarLong().Tratar(getDadosorc().getCodigo(),0l);
                
                if(CodigoVenda>0){
                    Integer nCodigoUsuario=0;
                    if(nUsuario==0){
                       nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "ExcluirCotacao", Sistema.getLojaAtual(), true, "Cancelamento de Nota");
                    }else{
                        nCodigoUsuario =nUsuario;
                    }
                    String TipoComprovante = VendaEmEdicao.VerificarVendaInterrompida(true).getString("tipocomprovante");
                    Long nCodigoSequencia = CodigoVenda ; //Long.valueOf( txtSequencia1.getText());
                    boolean ModoVenda = ModoVenda();
                    if(nCodigoUsuario>0){
                        if(ApagarNota(true)){                        
                            VendaEmEdicao.FinalizarNota(nCodigoSequencia);
                            if(ModoVenda){
                                UsuarioAuditar.Auditar(nCodigoUsuario, "VENDAS","Excluiu a cotação");
                            }
                            if(btImprimirComprovante.isSelected()){                            
                                PDVComprovante.CancelarComprovante(TipoComprovante,getNomeImpressoraComprovante());
                            }
                        }
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Usuário não autorizado", "Apaga Nota", "AVISO");
                    }
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }
    private void txtNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtNomeClienteActionPerformed
    public boolean MenuPDV_F4_ExcluirItem(){
        try {
            
           if(ModoRecebimento(true)){
               return false;
           }
           if(arItensorc.size()>0)
             {
                 
               if(PreVendasSelecionadas.getNotasSelecionadas().size()==1){
                  MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL REMOVER ITENS EM UMA OPERAÇÃO DE MESCLAGEM/PREVENDA", "MESCLAGEM/PREVENDA NÃO EDITÁVEL");
                  return false;
               }  
               itensVendidos=new frmPDVItensVendidos(null, true);
               itensVendidos.Inicializar(getDadosorc().getCodigo(), arItensorc.size());
               itensVendidos.setVisible(true);
               Object[] objLinha = itensVendidos.getObjLinha();
               if(objLinha!=null)
               {
                   if(objLinha.length>0) {                       
                      int nSeq=Integer.parseInt(objLinha[0].toString());
                      String Nome =objLinha[1].toString();
                      Long nIDUnico=Long.parseLong(objLinha[5].toString());                         
                      Integer nCodigoUsuario =ValidarAcessoAoProcesso.Verificar( null , UsuarioSistema.getIdUsuarioLogado(), "CancelarItem", Sistema.getLojaAtual(),  true,"Cancelamento de ITEM ["+ nSeq +"] - " + Nome);
                      if(nCodigoUsuario>0){                         
                         Itensorc item_escolhido=arItensorc.get(nSeq-1);
                         if(btImprimirComprovante.isSelected()){
                             item_escolhido.setSeq(nSeq);    
                             if(!PDVComprovante.CancelarItem(item_escolhido)){
                                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível realizar o cancelamento do item", "Cancelar Item", "AVISO");
                                 txtProcurar.requestFocus();
                                 return false;
                             }
                         }
                         if(ItensorcRN.Itensorc_Cancelar(item_escolhido)){                      
                           arItensorc.get(nSeq-1).setCodmov("Apagar");
                           UsuarioAuditar.Auditar(nCodigoUsuario, "VENDAS","Cancelamento do item no PDV" + pdvgerenciar.CodigoPDV());
                           RegistrarITem_SomarTotalNotas();
                           EscreverNaTela(ComprovanteNaoFiscal.RegistrarItem_Tela_CancelarItens(item_escolhido,nSeq, false),FormatoDoCorpo());                        
                         }
                     }
                     else
                     {
                         MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Usuário não autorizado a cancelar item", "Acesso Negado", "AVISO");
                     }
                   }
               }
             }
             txtProcurar.requestFocus();
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
   
    private String TipoComprovante(){
    
    return TipoComprovanteAtual;

}

private boolean Venda_PodeIniciarFechamento(){
    boolean Ret = false;
    try {
        if(arItensorc.size()>0 || FormatarNumero.FormatarNumero(txtTotal.getText()) >0f)
        {
            Ret=true;
        }
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
private boolean Crediario_PodeIniciarFechamento(){
    boolean Ret = false;
    try {
       Ret = isModoRecebimento();
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
private void ProcurarProduto_KeyPressed(java.awt.event.KeyEvent evt)
{
         if(!isTeclasFuncao()){
                return ;
         }   
        if(evt.getKeyChar()=='\n'){
            if(txtProcurar.getText().toString().trim().length()>0)
            {
               ProcurarProduto();
               
            }
            else
            {
                if(Venda_PodeIniciarFechamento() || Crediario_PodeIniciarFechamento())
                {
                    if(PrepararFechamento(0))
                    {
                        evt.consume();                        
                    }
                }
            }

        }
        else if(evt.getKeyChar()=='*')
        {
            evt.consume();
            
            if(ModoRecebimento(true))  {                
                return ;
             }
            
            if(txtProcurar.getText()!=null)
            {
                String cConteudoQt=txtProcurar.getText();
                //cConteudoQt=cConteudoQt.replaceAll("\\*", "");
                boolean bValidarFracionado=false;
                int nTamanhoMaximo =4;
                
                if(cConteudoQt.matches("^[0-9]*$") || cConteudoQt.contains(".") )
                {                   
                    
                    txtProcurar.setText("");
                    if(cConteudoQt.length()<=8)
                    {                        
                        try {
                            
                             Float nQtDigitada = Float.parseFloat((cConteudoQt));
                             int nPosPonto = cConteudoQt.indexOf(".");    
                             if (nPosPonto>=0) {
                                
                                String CasasDecimais = cConteudoQt.substring(nPosPonto+1) ;
                                 if (CasasDecimais.length()>3) {
                                     MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Informação digitada Inválida(9999.999)", "Quantidade Livre", "AVISO");                                
                                     txtProcurar.requestFocus();
                                     return;                                                                      
                                 }                                        
                             }                                      
                             
                             String ParteInteira = "";
                             if(nPosPonto>0){                               
                               ParteInteira = cConteudoQt.substring(0,nPosPonto);
                             }else{
                                 ParteInteira=cConteudoQt;
                             }
                                     
                             if (ParteInteira.length()>4) {  
                                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Informação digitada Inválida(1-9999)", "Quantidade Livre", "AVISO");

                             }else{
                                    txtQuantidade.setText(cConteudoQt);
                             }                           
                             
                             
                             
                        } catch (Exception e) {
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Informação digitada Inválida", "Quantidade Livre", "AVISO");
                        }     
                       
                    }
                    else
                    {
                        LimparCampoValoresProdutos();
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Informação digitada acima do limite aceitavel", "Quantidade Livre", "AVISO");
                    }
                }
            }
        }
        txtProcurar.requestFocus();

}
private void txtProcurarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcurarKeyPressed
        try {            
            //setbInterroperTarefaCaixaLivre(true);
            CaixaLivrePausar();
            ProcurarProduto_KeyPressed(evt);
            HostEmAtividade = System.currentTimeMillis();
            CaixaLivreAtivar();
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
}//GEN-LAST:event_txtProcurarKeyPressed
public void MenuPDV_F3_ExtornarVenda(){
    MenuPDV_F3_ExtornarVenda(false);
}
public void MenuPDV_F3_ExtornarVenda(boolean  Reimprimir){
    try {
        if(arItensorc.isEmpty())
        {    
            if(ModoRecebimento(true)){
                return; 
            }
            InterromperTarefaSincronismo=true;
            frmExtornarVenda extornarVenda = new frmExtornarVenda(null, true,nCodigoFilial,Sistema.getLojaAtual(),pdvgerenciar.CodigoPDV(),getControleCX(),ECFAtual.getECF(),getNomeImpressoraComprovante(),jasperNFce,Reimprimir);
            extornarVenda.setLocationRelativeTo(null);
            extornarVenda.setVisible(true);
            InterromperTarefaSincronismo=false;
            
        }
        
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
private void cbVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVendedorActionPerformed
    // TODO add your handling code here:
    
}//GEN-LAST:event_cbVendedorActionPerformed

private void cbVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbVendedorKeyPressed

    // TODO add your handling code here:
    if(evt.getKeyChar()=='\n')
    {
        txtProcurar.requestFocus();
        
    }
}//GEN-LAST:event_cbVendedorKeyPressed

private void cbTbPrecoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTbPrecoKeyPressed
    // TODO add your handling code here:
   if(evt.getKeyChar()=='\n')
    {
        txtProcurar.requestFocus();
    }
}//GEN-LAST:event_cbTbPrecoKeyPressed

private void txtProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProcurarActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_txtProcurarActionPerformed


private boolean AlterarTroca(Double ValorCreditoTroca, Long CodigoCreditoTroca)
{
    try {
                
        VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(getDadosorc().getCodigo(), null, null,null,null,null, ValorCreditoTroca, CodigoCreditoTroca,null,null,null,null);

        return true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
        return false;
        
    }
}
private boolean AlterarDescontos(Double nPercDesconto, Double nValorDesconto, Double nValorDescontoAtacado ,Double nValorVenda, Double ValorCreditoTroca, Long CodigoCreditoTroca, Double AcrescimoPrc, Double AcrescimoVLr, Double AcrescimoPagto)
{
    try {
        
        Double PercTotalDesconto =0d,ValorTotalDesconto =0d;
        
        ValorTotalDesconto  = nValorDesconto + nValorDescontoAtacado ;
      
        PercTotalDesconto = NumeroArredondar.Arredondar_Double(ValorTotalDesconto/nValorVenda,2);
        
        getDadosorc().setDesconto(ValorTotalDesconto);
        getDadosorc().setPercdesc(BigDecimal.valueOf(PercTotalDesconto));
        
        VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(getDadosorc().getCodigo(), null, nValorDesconto,nValorDescontoAtacado,null,null, ValorCreditoTroca, CodigoCreditoTroca,nPercDesconto,AcrescimoPrc,AcrescimoVLr,AcrescimoPagto);

        return true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
        return false;
        
    }
}

     

public boolean TEF_VendaNoCartao_NaoAutorizada(){
    try {
        
        ResultSet rs = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo());
        String FormaCartao ="";
        String TefStatus="";
        while(rs.next()){
            FormaCartao =  TratamentoNulos.getTratarString().Tratar(rs.getString("tef"),"N");
            if(FormaCartao.equalsIgnoreCase("S")){
               TefStatus =  TratamentoNulos.getTratarString().Tratar(rs.getString("tefstatus"),""); 
               if(TefStatus.equalsIgnoreCase("")){
                  return true;                 
               }
            }
        }
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        
    }
    return false;
}
public boolean TEF_VendaNoCartao_VendaCancelada(){
    try {
        
        ResultSet rs = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo());
        String FormaCartao ="";
        String TefStatus="";
        while(rs.next()){
            FormaCartao =  TratamentoNulos.getTratarString().Tratar(rs.getString("tef"),"N");
            if(FormaCartao.equalsIgnoreCase("S")){
               TefStatus =  TratamentoNulos.getTratarString().Tratar(rs.getString("tefcomandoatual"),""); 
               if(TefStatus.equalsIgnoreCase("CNC")){
                  return true;                 
               }
            }
        }
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        
    }
    return false;
}

public String ChamarFormCaixaLivre(Float Troco){
    String StringFechamento="";
    String RetornoCodigoLidoEmEspera="";
    try {
        
        frmPDVSimples_StatusPDV statuspdv = new frmPDVSimples_StatusPDV(null,true);
        statuspdv.lblMsgStatusTitulo.setText("CAIXA LIVRE");        
        StringFechamento= "OBRIGADO PELA PREFERÊNCIA";
        
        if(Troco>0f){
            //FormatarNumeros.FormatarParaMoeda(Troco)
            statuspdv.lblMsgRodape.setText("\n\nTROCO DA ÚLTIMA VENDA R$ " +  FormatarNumeros.FormatarParaMoeda(Troco));
        }
        
        statuspdv.lblMsgStatus.setText(StringFechamento);
        
        statuspdv.setVisible(true);
        
        RetornoCodigoLidoEmEspera = statuspdv.TeclasLidas ;
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return  RetornoCodigoLidoEmEspera;
}
private boolean DefinirCliente(String CodCliente , String NomeCliente){
    boolean Ret = false;
    try {
        if (getDadosorc() != null) {
            getDadosorc().setCodcliente(CodCliente);
            getDadosorc().setCliente(NomeCliente);
        }
        txtNomeCliente.setVisible(true);
        txtNomeCliente.setText("CLIENTE : " + CodCliente + " - " + NomeCliente);
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
private void ClientesAcionar(){
     try {
         
            formTabela = new frmClienteListagem(null, true,false,ModoVenda());
            formTabela.Pagto_TipoComprovante =TipoComprovante();
            formTabela.Pagto_ECFDisponivel = isECFDisponivel();
            formTabela.Pagto_ImpressoraComprovantes = getNomeImpressoraComprovante();
            formTabela.Pagto_ecf = getEcfDinnmus();       
            formTabela.Pagto_nCodigoObjetoCaixa = nCodigoObjetoCaixa;
         
            formTabela.setVisible(true);
            Long CodigoCliente = formTabela.ClienteSelecionado;           
            //String Cliente ="";

            if(CodigoCliente>0) {                
                DefinirCliente(formTabela.ClienteSelecionado.toString(), formTabela.ClienteSelecionadoNome);
                if(ParametrosGlobais.getBaixarConta().getDuplicatas_Codigo().size()>0){
                    Long CodigoOperacao = VendaEmEdicao.CodigoVendaEmEdicao();
                    boolean Ret = AbrirComprovanteCrediario(CodigoOperacao,ParametrosGlobais.getBaixarConta(),false);
                    if(Ret){
                        if(CodigoOperacao==0){
                             Ret =VendaEmEdicao.RegistrarVendaEmEdicao_Recebimento(getDadosorc().getCodigo(),TipoComprovantePDV,ParametrosGlobais.getBaixarConta(),Sistema.getLojaAtual(),pdvgerenciar.CodigoPDV() );
                            if(Ret){
                               setModoRecebimento(true);
                            }
                            
                        }
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível iniciar o baixa do crediário", "OPERAÇÃO NÃO INICIADA");
                    }
                }else{
                    
                    if(getDadosorc().getCodigo()==null && ParametrosGlobais.getPreVenda_Codigo().size()==0){
                        int nCodigoUsuario = ValidarAcessoAoProcesso.Verificar(null, UsuarioSistema.getIdUsuarioLogado(), "PDV_VDA", Sistema.getLojaAtual(), true, "REALIZAR VENDA");

                        if (nCodigoUsuario == 0) {
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "OPERADOR NÃO AUTORIZADO REALIZAR VENDA", "NÃO AUTORIZADO");
                            return ;
                        }
                       AbrirNota(TipoComprovantePDV);
                   }else{
                       RegistraItem_RegistrarEntidades_Dadosorc(false);
                       RegistrarItem_Tela_CabecaNota (getDadosorc(),Sistema.getDadosLojaAtualSistema(),TipoComprovantePDV,true,ParametrosGlobais.getPreVenda_Codigo());
                   }                   
                   VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(getDadosorc().getCodigo(), null, null, null, getDadosorc().getCodcliente(), null, null, null, null, null, null, null, null, true);
                }
                
            }else{
                if(isModoRecebimento()){
                    if(ParametrosGlobais.getBaixarConta().getDuplicatas_Codigo().size()==0){
                        ApagarNota(true);
                    }
                }
            }   
           
          
            txtProcurar.requestFocus();
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
private void btClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btClienteActionPerformed
    // TODO add your handling code here:
   CaixaLivrePausar();
   ClientesAcionar();
   CaixaLivreAtivar();

}//GEN-LAST:event_btClienteActionPerformed
public void MenuPDV_ResumoCaixa(){
    if(ModoRecebimento(true)){
        return;
    }
   (new frmResumoCaixa(null, true,Sistema.getLojaAtual(),UsuarioSistema.getIdCaixaAtual(), getControleCX(), UsuarioSistema.getIdUsuarioLogadoCaixa(),false)).setVisible(true);
}
public void MenuPDV_LeituraX(){
    if(ECFDisponivel){
        int nRet = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Essa é a 1ª Abertura do Dia ?", "Leitura X", JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE);
        if(nRet==JOptionPane.YES_OPTION){                
            if(getEcfDinnmus().VerificaDiaAberto(pdvgerenciar.CodigoPDV())==1){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O DIA FISCAL JÁ ESTA ABERTO", "DIA FISCAL");
            }else{
                getEcfDinnmus().AberturaDoDia("0", "");
            }       
        }else if(nRet==JOptionPane.NO_OPTION){
              getEcfDinnmus().LeituraX();
        }
    }else{
        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("ECF NÃO ESTÁ DISPONÍVEL", "LEITURA X");
    }
}
public void MenuPDV_ReducaoZ(){
    try {
        if(ECFDisponivel){
            if(ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "ReduçãoZ", Sistema.getLojaAtual(), true, "Encerramento do Movimento Fiscal - Redução Z")>0){
                if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Confirma o encerramento do Dia?", "Redução Z", JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){

                    getEcfDinnmus().FechamentoDoDia();

                    if(getEcfDinnmus().VerificaZJaEmitida().equalsIgnoreCase("")){
                        if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "DESEJA INICIAR O DIA FISCAL ?", "Leitura X", JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                            getEcfDinnmus().AberturaDoDia("", "");
                        }                
                    }
                    setECFDisponivel(getEcfDinnmus().VerificaDiaAberto(pdvgerenciar.CodigoPDV())==1 ? true : false);
                    //setECFDisponivel(getEcfDinnmus());
                }
            }
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("ECF NÃO ESTÁ DISPONÍVEL", "REDUÇÃO Z");
        }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
public void MenuPDV_AcionarGP(){
    try {
        if(getArItensorc().size()==0){
            if(!ECFDisponivel && !isNFCe_OK()){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O TEF NÃO PODE SER ACIONADO QUANDO ECF/NFCe NÃO ESTA DISPONÍVEL","ECF/NFCe NÃO DISPONÍVEL");
                return;
            }            
            InterromperCapturaDeTeclas=true;            
            setStatusTarefaCaixaLivre(0);          
            if(isNFCe_OK()){
                if(PDVComprovante.getImpressoraCompravante().isOK()){
                   if(!PDVComprovante.ImpressoraDeComprovante_Iniciar()){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "IMPRESSÃO DOS COMPROVANTES NÃO DISPONÍVEL","Impressora de Comprovante não disponível");
                       return;
                   }
                }   
            }
            TefPadrao.setImpressora_comprovante(PDVComprovante.getImpressoraCompravante());
            TefPadrao.IniciarAdmTEF(getEcfDinnmus(),TipoComprovanteAtual);
            InterromperCapturaDeTeclas=false;                   
            HostEmAtividade_Atualizar();
            setStatusTarefaCaixaLivre(2);                                                
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL INICIAR A ADMINISTRAÇÃO TEF\nCOM UMA NOTA ABERTA", "ADMINISTRAÇÃO TEF");
        } 
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
}
public void MenuPDV_StatusECF(){
     try {
        if(ECFDisponivel){
            ArrayList<String> alMsg = ECFAtual.getECF().PalavraStatus(true);
            String cMsg = "";
            for(int i=0;i<alMsg.size();i++){
               cMsg += alMsg.get(i) + "\n";
            }
            if(cMsg.length()>0){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, cMsg, "Status da Impressora", "AVISO");
            }
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("ECF NÃO ESTÁ DISPONÍVEL", "STATUS ECF");
        }
        txtProcurar.requestFocus();

    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
public void MenuPDV_CancelarCupom(){
    try {
        if(ECFDisponivel){
            if(ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "CancelarCupom", Sistema.getLojaAtual(), true, "Cancelar Cupom Fiscal")>0){
                if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Confirma o cancelamento do cupom fiscal","Cancelar Cupom Fiscal", JOptionPane.YES_NO_OPTION ,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                    getEcfDinnmus().CancelarUltimoCupom();
                }
            }
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("ECF NÃO ESTÁ DISPONÍVEL", "CANCELAR ÚLTIMO CUPOM");
        }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }

}
private void AtivarDesativarImpressao(){
      try {

            if(btImprimirComprovante.getText().equalsIgnoreCase("Imprimir Comprovante - [ ON ]")){
                btImprimirComprovante.setText("Imprimir Comprovante - [ OFF ]");
                btImprimirComprovante.setSelected(false);
            }else{
                btImprimirComprovante.setText("Imprimir Comprovante - [ ON ]");
                btImprimirComprovante.setSelected(true);
            }

    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
public void MenuPDV_ImprimirComprovante(){
      try {
        if (arItensorc.size()==0) {
            AtivarDesativarImpressao();
          }
        else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Existe uma nota aberta. Operação não realizada","Desativar Impressão", "AVISO");
        }
        txtProcurar.requestFocus();
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
private void txtNomeClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeClienteKeyPressed
    // TODO add your handling code here:
    try {
        if(evt.getKeyCode() == KeyEvent.VK_DELETE || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            InicializarCampoClientePadrao();
            txtProcurar.requestFocus();
        }

    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}//GEN-LAST:event_txtNomeClienteKeyPressed
public void MenuPDV_F2_StatusGaveta(){
    try {
        if(btGaveta.isSelected()){
            btGaveta.setText("Gaveta - On");
        }else{
            btGaveta.setText("Gaveta - Off");
        }
        txtProcurar.requestFocus();
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
private void btGavetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGavetaActionPerformed
    // TODO add your handling code here:
    try {
        if(btGaveta.isSelected()){
            btGaveta.setText("Gaveta - On");
        }else{
            btGaveta.setText("Gaveta - Off");
        }
        txtProcurar.requestFocus();
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}//GEN-LAST:event_btGavetaActionPerformed
public void MenuPDV_F3_AbrirGaveta(){
    try {
        Integer nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "AbrirGaveta", Sistema.getLojaAtual(), true, "Abertura da Gaveta");    // TODO add your handling code here:
        if(nCodigoUsuario>0){             
             
             if(PDVComprovante.AbrirGaveta(TipoComprovante(),getNomeImpressoraComprovante())){
                 UsuarioAuditar.Auditar(nCodigoUsuario, "VENDAS","Abriu Gaveta");
             }
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Usuário não autorizado", "Abertura de Gaveta", "AVISO");
        }
        txtProcurar.requestFocus();
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }

}
private String PesquisarProduto(){
    String Retorno ="";
    try {
        frmPesquisarProduto pesq  =new frmPesquisarProduto( null,true,nCodigoFilial);
        pesq.setVisible(true);
        Retorno = pesq.getCodigoProduto();
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Retorno;        
}
private void F4_PesquisarProduto(){
        try {
        String CodigoProduto = PesquisarProduto();
        if(CodigoProduto.length()>0){
           txtProcurar.setText(CodigoProduto);
           ProcurarProduto();
        }
        txtProcurar.requestFocus();
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
}        
private void txtProcurarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcurarKeyTyped
    // TODO add your handling code here:
    /*try {
        String cLetra = String.valueOf(evt.getKeyChar()).toLowerCase();
        if(cLetra.equals("s") && evt.isAltDown()){
          btPesquisarProdutoActionPerformed(null);
        }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    */
}//GEN-LAST:event_txtProcurarKeyTyped

private void txtProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcurarKeyReleased

    
    // TODO add your handling code here:
}//GEN-LAST:event_txtProcurarKeyReleased
public void MenuPDV_ModoTrabalho(){
    try {
        Object[] Opcoes =new Object[] {(Sistema.isOnline() ? "OFF-Line" : "On-Line"),"Automático"};
        String cRet= MetodosUI_Auxiliares_1.InputBox(null, "Alteração do modo de trabalho \n\nEscolha uma opção Abaixo:\n", "Modo de Trabalho", "AVISO",Opcoes,"");
        if(cRet.equalsIgnoreCase("OFF-Line")){
           if(ParametrosGlobais.getPreVenda_Codigo().size()>=1){
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("NÃO SERÁ POSSÍVEL ENTRAR EM MODO OFF-LINE DURANTE UMA OPERAÇÃO DE PRÉ-VENDA/MESCLAGEM", "PRE-VENDA/MESCLAGEM EM ANDAMENTO");
              return ;
           }
           VerificarStatusServidor.setVerificarServidor(false);
           Sistema.setForcaOFFLine(true);
           Sistema.setOnline(false);
        }else if(cRet.equalsIgnoreCase("On-Line")){                
            VerificarStatusServidor.setVerificarServidor(true);
            Sistema.setForcaOFFLine(false);
            Sistema.setOnline(true);
        }else{
            VerificarStatusServidor.setVerificarServidor(true);
            Sistema.setForcaOFFLine(false);
        }
        txtProcurar.requestFocus();

    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
}
private void txtProcurarAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtProcurarAncestorAdded
// TODO add your handling code here:
}//GEN-LAST:event_txtProcurarAncestorAdded

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        final frmPDV_Simples p = this;
        new Thread("FrmPDV_Simples_formWindowOpened"){
            public void run(){
                    try {
                        while(CarregandoForm){                        
                             Thread.sleep(500);                     
                        }
                        if(!IniciouOK){
                           dispose();
                        }else{
                            IniciarLogoLoja();
                            AjustaTamanhoLogoCliente();
                            
                            if(AbrirFechamentoAutomaticamente){
                                PrepararFechamento(MomentoVendaInterrompida);
                                
                            }
                            setTeclasFuncao(true);
                            p.requestFocusInWindow();
                            p.requestFocus();
                            txtProcurar.requestFocus();                            
                        }                       
                    } catch (InterruptedException ex) {
                         LogDinnamus.Log(ex, true);
                    } 
            }            
        }.start();      
    }//GEN-LAST:event_formWindowOpened
       
    public boolean MenuPDV_TEF_Ativo(){
        
        boolean Ret =  ECFDisponivel ? TEF_Ativo() : false ; 
        try {
            LogoStatusTEF(Ret);
            TEF_Ativo = Ret;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);           
        }
         return Ret;
    }
    public boolean TEF_Ativo(){
        try {
             if(Venda.PDV_TEF_Habilitado()){
                 if(TefPadrao.VerificarGP()){        
                    return true; 
                 }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
    public boolean TEF_PDV_Venda(Long nIdUnico){
        try {
            if(!Venda.PDV_TEF_Habilitado()){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Pdv não esta habilitado para operar com TEF", "TEF", "AVISO");      
               return false;
            }
            if(TipoComprovante().equalsIgnoreCase("fiscal") && btImprimirComprovante.isSelected()){                                       
                    ResultSet rsPagtoOrc = PagtoorcRN.PagtoOrc_Listar(dadosorc.getCodigo(), nIdUnico);                
                    if(rsPagtoOrc.next()){     
                        if(rsPagtoOrc.getString("TEF").equalsIgnoreCase("S"))
                        {
                            String StatusTEF =TratamentoNulos.getTratarString().Tratar(rsPagtoOrc.getString("TEFSTATUS"),"");
                            if(!StatusTEF.equalsIgnoreCase("")){
                               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "A Forma de Pagamento já esta Aprovada pela Administradora", "TEF - Forma Pagto", "AVISO");      
                               return false;
                            }else{
                                if(!TefPadrao.VerificarGP()){                     
                                    TEFInteracao.MensagemTEF("TEF DinnamuS", "O TEF NÃO ESTA ATIVADO", 5000);
                                    return false;
                                }
                                if(!TEFVenda.TEF_PDV_ConfirmarVendaPendente(getDadosorc().getCodigo(),0l)){
                                    return false;
                                }
                                String COO = getEcfDinnmus().UltimoCupom();
                                Double ValorVendaTEF = rsPagtoOrc.getDouble("valor");
                                String ControleTEF = rsPagtoOrc.getString("controle");
                                HashMap<String,String> TEF_Retorno_Venda = TefPadrao.IniciarVendaTEF(COO, ValorVendaTEF, getDadosorc().getCodigo(),false,ControleTEF );                                
                                String SituacaoVenda = TEF_Retorno_Venda.get("situacaovenda");
                                String MsgTEF = TEF_Retorno_Venda.get("msg");
                                //SituacaoVenda = TratamentoNulos.getTratarString().Tratar(SituacaoVenda, "");
                                if(TEF_Retorno_Venda.size()>0)                                {
                                    if(SituacaoVenda.equalsIgnoreCase("aprovada")){                                    
                                        TEF_Retorno_Venda.put("coo", COO);
                                        String Rede = TEF_Retorno_Venda.get("rede");
                                        String NSU = TEF_Retorno_Venda.get("nsu");
                                        String Finalizacao = TEF_Retorno_Venda.get("finalizacao");              
                                        String Identificacao = TEF_Retorno_Venda.get("identificacao");   
                                        String InfoTef =  TransformacaoDados.TransformarColecaoEmString(TEF_Retorno_Venda,"-dti-","<=>");//TEF_Retorno_Venda.toString(); //Rede +":"+ NSU +":"+ Finalizacao +":"+ COO + ":" + DataOp + ":" + HoraOP + ":" + ValorOP ;
                                        PagtoorcRN.Pagtoorc_AtualizarInfoTEF(nIdUnico,MsgTEF + " : " + Rede , InfoTef,"",Identificacao);                                    
                                        TEFInteracao.MensagemTEF("TEF DinnamuS", MsgTEF, 5000);
                                        //FormasDePagto_AtualizarGrid((getDadosorc().getCodigo()==null ? 0 :getDadosorc().getCodigo()));                                          
                                        //Evento_F7();
                                    }else if(!SituacaoVenda.equalsIgnoreCase("inativo")){                                        
                                        TEFInteracao.MensagemTEF("TEF DinnamuS", MsgTEF, 5000);
                                        
                                    }
                                } 
                            }
                        }
                        else
                        {
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Formas de Pagto não possibilita TEF", "TEF - Forma Pagto", "AVISO");
                        }
                    }
                
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O Modo de trabalho atual não permite esta operação", "TEF - Forma Pagto", "AVISO");
            }                
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void AjustaTamanhoLogoCliente(){
        try {
            
            ImageIcon img = (ImageIcon) LogoCliente.getIcon();
            img.setImage( img.getImage().getScaledInstance((int)painelLogoCliente.getWidth(),(int) painelLogoCliente.getHeight(), 100));                //ImagemTratamento.AjustarTamanho(   img, (int) , (int) );
            LogoCliente.setIcon(img);
            LogoCliente.setVisible(true);
           this.repaint();
            
            //Pain.update(Pain.getGraphics());
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private void LogoClienteComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_LogoClienteComponentResized
        // TODO add your handling code here:]
        
       // MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO(null, "teste", "teste");
    }//GEN-LAST:event_LogoClienteComponentResized

    private void btOcultarBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOcultarBarraActionPerformed
        // TODO add your handling code here:
        TeclaAtalho_Acoes_2("F11");
        
    }//GEN-LAST:event_btOcultarBarraActionPerformed

    private void btModoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btModoVendaActionPerformed
        // TODO add your handling code here:
        TeclaAtalho_Acoes_2("F2");
    }//GEN-LAST:event_btModoVendaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        TeclaAtalho_Acoes_2("F12");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btMenuPDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMenuPDVActionPerformed
        // TODO add your handling code here:
        TeclaAtalho_Acoes_2("F1");
    }//GEN-LAST:event_btMenuPDVActionPerformed

    private void PainelRodapeComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PainelRodapeComponentHidden
        // TODO add your handling code here:
        //AjustaTamanhoLogoCliente();
    }//GEN-LAST:event_PainelRodapeComponentHidden

    private void PainelRodapeComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PainelRodapeComponentShown
        // TODO add your handling code here:
        //AjustaTamanhoLogoCliente();
    }//GEN-LAST:event_PainelRodapeComponentShown
    private void Sair(){
         try {
            boolean bSair = false;
            String MSG_Saida ="";
            if(ModoRecebimento(false))  {
               MSG_Saida="Existe um RECEBIMENTO em andamento. Operação de Saida Cancelada";
               bSair=false;
            }else{            
                MSG_Saida="Existe uma nota em edição. Operação de saida cancelada";
                if (arItensorc==null){
                    bSair=true;
                }else{
                    if (arItensorc.size()==0){
                        bSair=true;
                    }
                }
            
            }              
            if(!bSair)
            {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, MSG_Saida, "Fechamento do Checkout", "INFO");
            }
            else{
                if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Confirma a saida do pdv?", "Saida do PDV", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {
                    if(SincronizarMovimento.EnviandoMovimento){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Aguarde a transmissão do movimento para poder fechar o pdv", "TRANSMITINDO MOVIMENTO...AGUARDE");
                        return ;
                    }
                    LiberarObjetos();
                    ExecutarTarefaCaixaLivre=false;
                    setProdutFotoHeight(0);
                    setProdutFotoWidth(0);                
                    VerificarStatusServidor.setVerificarServidor(true);
                    Concorrencia.LiberarNota();                
                    ExecutarTarefaSincronismo=false;
                    ExecutarTarefaCaixaLivre=false;
                    this.dispose();
                    return;
                }
            }
            txtProcurar.requestFocus();
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }
    private void btImprimirComprovanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirComprovanteActionPerformed
        // TODO add your handling code here:

        try {
            if (arItensorc.size()==0) {
                AtivarDesativarImpressao();
            }
            else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Existe uma nota aberta. Operação não realizada","Desativar Impressão", "AVISO");
            }
            txtProcurar.requestFocus();
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_btImprimirComprovanteActionPerformed

    private void txtNomeVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeVendedorActionPerformed

    private void txtNomeVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeVendedorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeVendedorKeyPressed

    private void btVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVendedorActionPerformed
        // TODO add your handling code here:
        try {
            if(ModoRecebimento(true))  {
                return ;
             }
            if(ParametrosGlobais.getPreVenda_Codigo().size()>0){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ESTÁ FUNÇÃO ESTA BLOQUEADA \nDURANTE UMA OPERAÇÃO DE MESCLAGEM","MESCLAGEM EM ANDAMENTO");
                return;
            }
        
            frmPDV_SimplesVendedor vendedor = new frmPDV_SimplesVendedor(null, true);
            vendedor.setVisible(true);
            if(vendedor.VendedorCodigo>0l){
                VendedorCodigo = vendedor.VendedorCodigo.toString();
                VendedorNome = Venda.VendedorNome(vendedor.VendedorCodigo, Sistema.getLojaAtual());
                if(getDadosorc().getCodigo()==null){
                    int nCodigoUsuario = ValidarAcessoAoProcesso.Verificar(null, UsuarioSistema.getIdUsuarioLogado(), "PDV_VDA", Sistema.getLojaAtual(), true, "REALIZAR VENDA");

                    if (nCodigoUsuario == 0) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "OPERADOR NÃO AUTORIZADO REALIZAR VENDA", "NÃO AUTORIZADO");
                        return ;
                    }
                    AbrirNota(TipoComprovantePDV);
                }else{
                    RegistraItem_RegistrarEntidades_Dadosorc(false);     
                    getDadosorc().setVendedor(VendedorNome);
                    getDadosorc().setCodvendedor(VendedorCodigo);
                    RegistrarItem_Tela_CabecaNota(getDadosorc(),Sistema.getDadosLojaAtualSistema(),TipoComprovantePDV,true,PreVendasSelecionadas.getNotasSelecionadas());
                }
                VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(getDadosorc().getCodigo(), null, null, null, null, null, null, null, null, null, null, null, VendedorCodigo, true);                
            }
            txtProcurar.requestFocus();
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }//GEN-LAST:event_btVendedorActionPerformed

    private void btCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCliente1ActionPerformed
        // TODO add your handling code here:
        Sair();
    }//GEN-LAST:event_btCliente1ActionPerformed

    private void btReimpressaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReimpressaoActionPerformed
        // TODO add your handling code here: 
        try {
            if(PodeChamarReimpressao()){
              MenuPDV_F3_ExtornarVenda(true);
            }
        } catch (Exception e) {
             LogDinnamus.Log(e, true);
        }
       

    }//GEN-LAST:event_btReimpressaoActionPerformed
    public void filtrarNomeNaTabela(String text) {
   /* AbstractTableModel tabela_clientes = (AbstractTableModel) dbgOpcoesPagto.getjTable().getModel();
    final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dbgOpcoesPagto.getjTable().getModel());
    dbgOpcoesPagto.getjTable().setRowSorter(sorter);
    //String text = jText_Procura.getText().toUpperCase();

    if (text.length() == 0)
    {
         sorter.setRowFilter(null);
    }
    else
    {
         try
         {
             RowFilter<TableModel, Object> rf = null;
              try {
                     rf = RowFilter.regexFilter(text, 1);
              } catch (java.util.regex.PatternSyntaxException e) {
                  return;
              }

              sorter.setRowFilter(rf);
              
         }
         catch (PatternSyntaxException pse)  {
               System.err.println("Erro");
         }
    }*/


}
protected boolean VerificarCaixaAberto()
{
    boolean bRet=false;
    ResultSet rs=null;
    try {

        rs = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(),0,pdvgerenciar.CodigoPDV(),false);
        if(rs.next())
        {
          if(rs.getString("status").equalsIgnoreCase("F")){
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Caixa atual esta FECHADO", "CAIXA FECHADO", "AVISO");
          }else{
              NomeCaixa = rs.getString("nome");
              //txtCaixa.setText(rs.getString("nome"));
              ECFAtivo = rs.getBoolean("ecf_ativo");
              nCodigoFilial = rs.getInt("filial");
              nCodigoObjetoCaixa = rs.getInt("Codigo");
              nCodigoOperadorCX = rs.getInt("operadorcx");
              if(!GerenciarCaixa.VerificarSeCaixaOFF(Sistema.getLojaAtual(), nCodigoObjetoCaixa)){
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O caixa selecionado não é um caixa off line", "Abertura de Caixa", "AVISO");
                   return false;
               }
               int nCodigoPDV_VinculadoCaixa =GerenciarCaixa.VerificarPDV_VinculadoAoCaixa(Sistema.getLojaAtual(), nCodigoObjetoCaixa);

               if(nCodigoPDV_VinculadoCaixa!=0 && nCodigoPDV_VinculadoCaixa!=pdvgerenciar.CodigoPDV()){
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O caixa ja foi aberto em outro pdv", "Abertura de Caixa", "AVISO");
                   return false;
               } 
             
              setNomeImpressoraComprovante(TratamentoNulos.getTratarString().Tratar( rs.getString("impressoranaofiscal"),""));
              String cECF = TratamentoNulos.getTratarString().Tratar(rs.getString("IMPRESSORAFISCAL"),"NAO CONFIGURADO");
              String cPorta = TratamentoNulos.getTratarString().Tratar(rs.getString("porta"),"COM1");
              if(!cECF.equalsIgnoreCase("NAO CONFIGURADO")){
                //EcfDinnmus.setTipoECF(cECF,cPorta);
                PDVComprovante.setEcfatual(ECFAtual.getECF());
              }
              txtECF.setText(cECF);
              if(nCodigoFilial>0){
                bRet=true;
              }else{
                  MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Nenhum local de estoque foi vinculado ao caixa atual", "Caixa não vinculado", "AVISO");
              }
          }
        }
        else
        {
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O operador não possui um caixa aberto", "Pdv DinnamuS", "AVISO");
        }                        
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }

    return bRet;
}

    /**
    * @param args the command line arguments
    */
    public boolean ApagarNotaRegistros(Long nCodigoVenda, boolean bRemoverRegistroNotaEdicao){
        boolean bRet=false;
        try {
            if(nCodigoVenda!=0){
               bRet= DadosorcRN.Dadosorc_Excluir(nCodigoVenda,bRemoverRegistroNotaEdicao);              
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    public boolean ApagarNotaRegistros_Itens(Long nCodigoVenda, boolean bRemoverRegistroNotaEdicao){
        boolean bRet=false;
        try {
            if(nCodigoVenda!=0){
               bRet= ItensorcRN.Itensorc_Excluir(nCodigoVenda);              
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    public boolean ApagarNota(boolean bApagarRegistros)
    {
        boolean bRet=false;
        try {
            if(bApagarRegistros){
                
                Long nCodigoVenda= TratamentoNulos.getTratarLong().Tratar( getDadosorc().getCodigo(),0l);
                if(nCodigoVenda>0){
                    nCodigoVenda= nCodigoVenda ; //Long.valueOf(txtSequencia1.getText());
                    if(!ApagarNotaRegistros(nCodigoVenda,true)){
                        return false;
                    }
                }
            }
            VendaRecuperada=false;
            ParametrosGlobais.setPreVenda_Codigo(null);
            ParametrosGlobais.setBaixarConta(null);
            setModoRecebimento(false);
            //txtSequencia1.setText("");
            LimparCampoValoresProdutos();
            txtTotal.setText("R$  0,00");
            getArItensorc().clear();
            setnDescontoLiberado((Float) 0f);
            setDadosorc(new Dadosorc());
            txtProcurar.requestFocus();
         
            txtQuantidade.setText("");
            txtSubTotal.setText("");
            PDVComprovante.setImpressaoIniciada(false);
            PDVComprovante.setIniciou(false);
            imagemProduto.setIcon(null);
            txtTotalItens.setText("000");
         
            if(InicializarCampoClientePadrao()){
                bRet=true;
            }
         
            Concorrencia.LiberarNota();
         
            //tbInteracao.setSelectedIndex(0);
            txtPreco.setValue(null);
            LimpaEspelhoVenda();
           IniciarVendedorPadrao();
            
            PreVendasSelecionadas.LimparNotasSelecionadas();
            //MesclarVenda_PrepararInterface(false);
            //btPendencias.setVisible(false);
            this.MomentoVendaInterrompida=0;
            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public void IniciarVendedorPadrao(){
        
            VendedorCodigo=UsuarioSistema.getCodVendedorVinculadoAoCaixa();
            VendedorNome=UsuarioSistema.getNomeVendedorVinculadoAoCaixa();
    }   
            
    public boolean LimparNota(boolean bApagarRegistros)
    {
        boolean bRet=false;
        try {
            if(bApagarRegistros){                
                Long nCodigoVenda= TratamentoNulos.getTratarLong().Tratar( getDadosorc().getCodigo(),0l);
                if(nCodigoVenda>0){
                    //nCodigoVenda= nCodigoVenda ; //Long.valueOf(txtSequencia1.getText());
                    if(!ApagarNotaRegistros_Itens(nCodigoVenda,true)){
                        return false;
                    }
                }
            }            
            LimparCampoValoresProdutos();
            txtTotal.setText("R$  0,00");
            getArItensorc().clear();
            setnDescontoLiberado((Float) 0f);            
            txtProcurar.requestFocus();                     
            PDVComprovante.setImpressaoIniciada(false);
            PDVComprovante.setIniciou(false);
            imagemProduto.setIcon(null);
            txtTotalItens.setText("000");
            txtPreco.setValue(null);
            //setDadosorc(new Dadosorc());
            LimpaEspelhoVenda();
            VendedorCodigo="0";
            VendedorNome="";
           
            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public void LimpaEspelhoVenda(){
        try {
            txtItens.setText("");
            txtCabecaNota.setText("");
            lblNomeComprovante.setText(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora(" "));
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    
    public void ExibirLogoEnvio(){
        
        try {
            boolean envio = pdvgerenciar.EnviarMovimento();
            if(envio && Sistema.isOnline()){
              lblEnvio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Upload.png")));
              lblEnvio.setText("ENVIO OK");
            }else{
              lblEnvio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Uploadoffline.png")));
              lblEnvio.setText("SEM ENVIO");  
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }
    public void ExibirUltimoSincCad(){
        //boolean Ret = false;
        try {
           Date dUltimoSinc =  DAO_RepositorioLocal_Inicializar.getUltimoSinc();
           if(dUltimoSinc!=null){
               lblUltimoSincCadastro.setText( "Versão Banco Local : " + DataHora.getCampoFormatado("dd/MM/yyyy HH:mm:ss",dUltimoSinc));                     
           }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        //return Ret;
    }
    public void ExibirLogorecepcao(){
        
        try {
            boolean recepcao = pdvgerenciar.ReceberCadastros();
            if(recepcao && Sistema.isOnline()){
              lblRecepcao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Download.png")));
              lblRecepcao.setText("RECEPÇÃO OK");
            }else{
              lblRecepcao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Downloadoffline.png")));
              lblRecepcao.setText("SEM RECEPÇÃO");  
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }
    public  boolean VerificarNFCe(int Loja,int PDV, boolean ExibirMSG_ServidorOK){
        boolean Ret = false;
        try {
           // if (Sistema.isOnline()) {
                boolean RetConfig = NFCE_Configurar.NFCE_Configuracao_OK(Loja,PDV,NFCE_Configurar.NFCE_MODELO);
                if (RetConfig) {
                    // PDV = pdvgerenciar.CodigoPDV();                            
                    Icon ico = new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logo-nfce-xs.png"));
                    InteracaoDuranteProcessamento.Mensagem_Iniciar("NFCe ATIVADA", "PREPARANDO USO DA NFCE...AGUARDE", false, ico);                    
                    boolean RetConfigNFCe = NFCe_ConfiguracaoAmbiente.Configurar(Loja, PDV, Licenca.DataServidor());
                    InteracaoDuranteProcessamento.Mensagem_Terminar();
                    if(RetConfigNFCe){
                        String MSG_Retorno = (new NFCE_ConsultarStatus()).Consultar_NFCe(false, pdvgerenciar.CodigoPDV());
                        if(MSG_Retorno.length()==0){
                            
                            if(!NFCE_Contingencia.Contingencia(PDV)){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("Não foi possível VERIFICAR OS SERVIDORES DA SEFAZ\n\nO SISTEMA ENTRARA EM CONTIGÊNCIA", "NFC-e sem comunicação");
                                if(!NFCE_Contingencia.EntrarEmContingencia("AUTOMATICA", Loja, PDV)){
                                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não é possivel iniciar a NFCe no modo CONTINGÊNCIA", "NFCe - Sistema OFF-Line");
                                }else{
                                    Ret=true;
                                }
                            }else{
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("Não foi possível VERIFICAR OS SERVIDORES DA SEFAZ\n\nO SISTEMA ESTA OPERANDO EM CONTIGÊNCIA", "NFC-e sem comunicação");
                                Ret=true;
                            }
                        }else{
                             if(NFCE_Contingencia.Contingencia(PDV)){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("O sistema está saindo da contingência", "NFC-e ONLINE");
                                if(!NFCE_Contingencia.SaidaDaContingencia("AUTOMATICA", Loja, PDV)){
                                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não é possivel sair do modo CONTINGÊNCIA", "NFCe - Sistema em CONTINGÊNCIA");
                                }else{
                                    Ret=true;
                                }
                             }else{
                                 if(ExibirMSG_ServidorOK){
                                     MetodosUI_Auxiliares_1.MensagemAoOperadorTextual("NFC-e OK", "Retorno Sefaz", MSG_Retorno, "INFO", new Font("Courier New", Font.PLAIN, 14));                        
                                 }
                                Ret=true;
                             }
                        }
                    }
                   
                } 
           /* }else {
                MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não é possivel iniciar a NFCe no modo offline", "NFCe - Sistema OFF-Line");
            }*/

            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;        
    }
    public boolean InicializarUI()
    {
        PDVComprovante.setImpressaoIniciada(false);
        boolean bRet=false;
        LogoCliente.setVisible(false);
        dadosorc=new Dadosorc();
        itensorc=new Itensorc();
        arItensorc=new ArrayList<Itensorc>();
        AbrirFechamentoAutomaticamente=false;
        lblNFCe_Contigencia.setVisible(false);  
        lblNFCe.setVisible(false);
        setTeclasFuncao(false);
        if(ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "Check-Out", Sistema.getLojaAtual(), true, "Check-Out")>0){         
           XStream_Api.getX();
           if(VerificarCaixaAberto()){          
            if(DefinirControleTeclasFuncoesCheckout()){           
             if(InicializarCamposComControleDeDigitacao()){            
                if(InicializarUI_Combos()){                               
                    if(InicializarCamposBases()){                      
                        if(InicializarSincronismo()){                            
                            ExibirLogoDinnamuS();
                            ExibirLogoEnvio();
                            ExibirLogorecepcao();
                            ExibirUltimoSincCad();
                            if(ECFAtivo ){
                               // if(MetodosUI_Auxiliares.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "DESEJA REALIZAR A VERIFICAÇÃO DO ECF ?","VERIFICAR ECF")==MetodosUI_Auxiliares.Sim() ){                                    
                               //br.com.ui.MetodosUI_Auxiliares.MensagemAoOperadorTextual("PDV DINNAMUS","VERIFICANDO ECF ... AGUARDE","","AVISO",null,false,null);     
                                    
                                //this.repaint();
                               InteracaoDuranteProcessamento.Mensagem_Iniciar("CARREGANDO PDV", "VERIFICANDO ECF...AGUARDE");
                               setECFDisponivel(VerificarECF()); 
                               InteracaoDuranteProcessamento.Mensagem_Terminar();
                               //br.com.ui.MetodosUI_Auxiliares.MensagemAoOperadorTextual_Terminar();
                               
                              
                            }else{
                               lblECF.setVisible(false);                                   
                               
                               //}        
                            }
                            setNFCe_OK(VerificarNFCe(Sistema.getLojaAtual(),pdvgerenciar.CodigoPDV(),false ));
                            
                             if (ECFAtivo || isNFCe_OK()) {
                                if (ECFDisponivel || isNFCe_OK()) {
                                    TEF_Ativo = TEF_Ativo();
                                }
                            }
                            LogoStatusTEF(TEF_Ativo);
                            DefinirModoTrabalhoNFCe();
                            IniciarVendedorPadrao();    
                            DefinirModosTrabalho();
                            DefinirModoDeTrabalho();
                            
                            String RetTEF = "";
                            if(TEF_Ativo){
                                RetTEF = VerificarTransacaoTEFInterrompida();
                                if(RetTEF.equalsIgnoreCase("Erro") || RetTEF.equalsIgnoreCase("")){                                  
                                    return false;
                                }             
                            }
                            lblNomeComprovante.setText(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora(" "));
                            VendaRecuperada=false;
                            if(VerificarOperacaoInterrompida(RetTEF)){
                              
                               System.out.println("OK");    
                               bRet=true;
                               txtProcurar.requestFocus();                                                                     
                               HostEmAtividade=System.currentTimeMillis();
                               InicializarStatusCaixaLivre();
                               CaixaLivreAtivar();
                              
                            } else{
                                return false;
                            }      
                        }
                        }                    
                }
             }             
            }
           }
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, Recursos.getMensagens().getString("acesso.negado"), "Acesso Negado", "AVISO");
        }
        return bRet;

    }
    public boolean DefinirModoTrabalhoNFCe(){
        boolean Ret = false;
        try {
            lblNFCe.setVisible(isNFCe_OK());               
            if(lblNFCe.isVisible()){
                ResultSet rs = NFCE_Configurar.ListarConfiguracaoPDV_PorPDV(pdvgerenciar.CodigoPDV(),NFCE_Configurar.NFCE_MODELO);
                if(rs.next()){
                   boolean Contingencia = rs.getBoolean("contigencia");
                   if(Contingencia){
                       lblNFCe_Contigencia.setVisible(true);  
                       lblNFCe.setVisible(false);
                   }else{
                       lblNFCe_Contigencia.setVisible(false);  
                   }
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private void DefinirModoDeTrabalho(){
        try {
            if (ModosTrabalhoDisponiveis.contains("nfce")) {
                AtivarModoSemImpressao_NFCe();
            } else if(ModosTrabalhoDisponiveis.contains("fiscal")) {
                    AtivarModoFiscal();
            } else if(ModosTrabalhoDisponiveis.contains("nfiscal")) {
                    AtivarModoNaoFiscal(true);
            }else{
                   AtivarModoSemImpressao();
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private void LogoModoOperacao(){
        try {           
            if(btModoVenda.getText().contains("CONSULTANDO")){
                btModoVenda.setText("[F2] VENDENDO");
                if(TipoComprovanteAtual.equalsIgnoreCase("fiscal")){
                   AtivarModoFiscal();
                }else if(TipoComprovanteAtual.equalsIgnoreCase("nfiscal")){                                            
                         AtivarModoNaoFiscal(true);
                }else if(TipoComprovanteAtual.equalsIgnoreCase("nimp")){
                        AtivarModoSemImpressao();                            
                }                 
            }else{
                btModoVenda.setText("[F2] CONSULTANDO");                
                btModoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Find.png")));
            } 
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private void LogoStatusECF(boolean Status){
        try {
            lblECF.setVisible(true);
            if(Status){
                lblECF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/ecfonline.png")));           
                lblECF.setText("ECF OK  ");
            }else{
                lblECF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/ecfoffline.png")));           
                lblECF.setText("ECF BLOQUEADO  ");
            }
            
        } catch (Exception e) {
        }
    }
    private void LogoStatusTEF(boolean Status){
        try {
            lblStatusTEF.setVisible(true);
            
            if(Status){
                
                lblStatusTEF.setText("TEF ATIVO  ");
                lblStatusTEF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/TEFONLINE.png")));           
            }else{
               lblStatusTEF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/TEFOFFLINE.png")));           
                lblStatusTEF.setText("TEF INATIVO  ");
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    public boolean IniciarLogoLoja(){
        try {
            Blob Logo = Sistema.getDadosLojaAtualSistema().getBlob("logo");
            
            if(Logo!=null){
                if(Logo.length()>0){
                   
                   ImageIcon i = new ImageIcon(Logo.getBytes(1, (int)Logo.length() ) );
                   LogoCliente.setIcon(i);                                      
                }
            }
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
   
   
    public boolean  InicializarCamposComControleDeDigitacao()
    {
        boolean bRet=false;

        try {

            txtProcurar.setDocument(new ControlarDigitacao("ALFANUMERICO",true ));
            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

        return bRet;
    }
    public boolean InicializarCampoClientePadrao(){
        boolean bRet=false;
        try {
            Integer nCodigoClientePadrao = TratamentoNulos.getTratarInt().Tratar(Sistema.getDadosLojaAtualSistema().getInt("clientepadrao"),0);
            if(nCodigoClientePadrao>0){
                ResultSet rs = clientes.Listar(Sistema.getLojaAtual(),nCodigoClientePadrao.toString());
                if(rs.next()){
                   String cNomeCliente =TratamentoNulos.getTratarString().Tratar(rs.getString("nome"),"");
                   getDadosorc().setCodcliente(nCodigoClientePadrao.toString());
                   getDadosorc().setCliente(cNomeCliente);
                   txtNomeCliente.setVisible(true);
                   txtNomeCliente.setText(nCodigoClientePadrao.toString() + " - " + cNomeCliente);
                }else{
                    txtNomeCliente.setVisible(false);
                    getDadosorc().setCodcliente("0");
                    getDadosorc().setCliente("** Consumidor **");
                    //txtNomeCliente.setText("** Consumidor **");
                }
            }else{
                txtNomeCliente.setVisible(false);
                getDadosorc().setCodcliente("0");
                getDadosorc().setCliente("** Consumidor **");
                //txtNomeCliente.setText("** Consumidor **");
            }
            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public boolean  InicializarCamposBases()
    {
        
        try {
                        
            AjustarFontes_UI();
            
            //txtPDV.setText( String.valueOf(pdvgerenciar.DadosPdv().getInt("CodigoPDV")));
            if(InicializarCampoClientePadrao()){
                return true;
            }
            
        } catch (Exception ex) {
            LogDinnamus.Log(ex, true);
        }
        return false;
    }
    public void  InicializarStatusCaixaLivre(){
            new Thread( new TarefaCaixaLivre(),"InicializarStatusCaixaLivre").start();
    }
    public boolean  InicializarSincronismo()
    {
        try {
            InterromperTarefaSincronismo=false;
            ExecutarTarefaSincronismo=true;
            ThreadSincronismo = new Thread(new TarefaSincronismo(), "InicializarSincronismo");
            ThreadSincronismo.setDaemon(true);
            ThreadSincronismo.start();
            
            return true;
        } catch (Exception e) {
                LogDinnamus.Log(e);
        }
        return false;

    }
    
    public boolean MenuPDV_VerificarECF(){
           boolean Ret = VerificarECF();
           setECFDisponivel(Ret);
           return Ret;
    }
    public boolean VerificarECF(){
        boolean Retorno = false;
        
        try {
            Integer CodigoPDv = pdvgerenciar.CodigoPDV();
           //if(!ECFAtual.isECFValido(false)){  
               Retorno = ECFAtual.Verificar(CodigoPDv,false,false,false,false,true);
          // }else{
          ///     Retorno = ECFAtual.getECF().VerificacaoInicial(CodigoPDv, false,false,false);               
          // }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);

        }
        return Retorno;
    }
    
    public boolean AtualizarDadosVendedor(){
        try {
            VendedorCodigo =  UsuarioSistema.getCodVendedorVinculadoAoCaixa();   
            ResultSet rs =  Venda.Vendedor(Long.valueOf(VendedorCodigo), Sistema.getLojaAtual());
            if(rs.next()){
                VendedorNome = rs.getString("nome");
            }            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean InicializarUI_Combos()
    {
        boolean bRet=false;
        bRet= MetodosUI_Auxiliares_1.PreencherCombo(cbTbPreco, tabeladepreco.Listar(Sistema.getCodigoLojaMatriz()), "Descricao", "Codigo", true);
        if(bRet)
        {             
           bRet = AtualizarDadosVendedor();         
           if(bRet)
              bRet=IniciarCamposTexto();
        }

        return bRet;

    }
    public String  VerificarTransacaoTEFInterrompida(){
        String DadosRetornoTransacaoTEFInterrompida ="Erro";
        try {         
                if(Venda.PDV_TEF_Habilitado()){                  
                   String Retorno  =TEFVenda.VerificarTransacaoTEFInterrompida();
                   DadosRetornoTransacaoTEFInterrompida = Retorno;                 
                }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return DadosRetornoTransacaoTEFInterrompida;
    }
    
     public boolean VerificarNotaInterrompida_Prevenda(Long CodigoUltimaVenda){
        boolean Ret = false;
        try {            
            ResultSet rsPreVendasEmEdicao = VendaEmEdicao.PreVendas_EmEdicao(CodigoUltimaVenda);            
            PreVendasSelecionadas.LimparNotasSelecionadas();            
            Long CodigoVendaOnline=0l;
         
            while( rsPreVendasEmEdicao.next()){
                CodigoVendaOnline =rsPreVendasEmEdicao.getLong("codigovendaonline");
                if(Sistema.isOnline()){                                                            
                    String MsgConcorrencia = Concorrencia.VerificarNota(CodigoVendaOnline);
                    if(!MsgConcorrencia.equalsIgnoreCase("")){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, MsgConcorrencia, "NOTA INTERROMPIDA - PRÉ VENDA BLOQUEADA");                    
                        return false;
                    }
                } 
                PreVendasSelecionadas.getNotasSelecionadas().add(CodigoVendaOnline);
            }    
            if(PreVendasSelecionadas.getNotasSelecionadas().size()>0){
                  if (!Sistema.isOnline()) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("EXISTE UMA PRÉ-VENDA INTERROMPIDA MAS O SISTEMA ESTA OFF-LINE\n\nOPERAÇÃO NAO PERMITIDA", "SISTEMA OFF-LINE");
                    return false;
                }
                if (Sistema.isOnline()) {
                    Ret = Concorrencia.BloquearNota(PreVendasSelecionadas.getNotasSelecionadas());
                }else{
                   Ret=true;
                }
            }else{
                Ret=true;
            }
            if(Ret){
               ParametrosGlobais.setPreVenda_Codigo(PreVendasSelecionadas.getNotasSelecionadas());
            }
            return Ret;
                           
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
    public boolean VerificarOperacaoInterrompida(String RetTEF){
        boolean Ret = false;
        try {
             ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
             if(rs.next()){
                 boolean recebimento = rs.getBoolean("recebimento");
                 if(!recebimento){
                     Ret = VerificarNotaInterrompida(RetTEF, rs);
                 }else{
                     Ret = VerificarRecebimentoInterrompida(RetTEF,rs);                    
                     setModoRecebimento(Ret);  
                     
                     
                 }
             }else{
                 Ret=true;
             }
             
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public boolean VerificarNotaInterrompida(String RetTEF,ResultSet rs ){
         String cRet="0";
         
         try {
            
                                       
                String cTipoComprovante=rs.getString("TipoComprovante");
                String NFCE_ChaveDeAcesso= TratamentoNulos.getTratarString().Tratar(rs.getString("nfce_chavedeacesso"),"");
                String cCOO=rs.getString("coo"),  cCOOAtual="" ;
                String IDVendedor ="", IDCliente ="";
                
                if(!NFCE_ChaveDeAcesso.equalsIgnoreCase("")){
                  cTipoComprovante="nfce";
                }
                if(isECFDisponivel()){
                   cCOOAtual=  getEcfDinnmus().UltimoCupom().trim();
                }
                Long nCodigoVenda = rs.getLong("CodigoUltimaVenda");                
               
                //Sem Itens para reexibir
               
                if(ItensorcRN.Itensorc_Local_Contar(nCodigoVenda)==0){
                    VendaEmEdicao.FinalizarNota(nCodigoVenda);
                    return true;
                }
                
                if(!VerificarNotaInterrompida_Prevenda(nCodigoVenda)){
                    return false;

                }
                
                IDCliente =  TratamentoNulos.getTratarString().Tratar( rs.getString("idcliente"),"");
                IDVendedor = TratamentoNulos.getTratarString().Tratar( rs.getString("vendedor"),"");
                
                if(cTipoComprovante.equalsIgnoreCase("fiscal")){
                    if(!isECFDisponivel()){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "FOI IDENTIFICADO UMA VENDA FISCAL INTERROMPIDA, PORÉM O ECF NÃO ESTA DISPONÍVEL\n\nREALIZE A VERIFICAÇÃO INICIAL DO ECF", "ECF NÃO DISPONÍVEL");
                        return false;                        
                    }
                }                         
                this.MomentoVendaInterrompida = VendaEmEdicao.MomentoVendaInterrompida(nCodigoVenda);                
                
                Object[] Opcoes=null;
                String MensagemAoOperador="";
                Boolean ReiniciarComprovante=false;
                if(MomentoVendaInterrompida==1 || MomentoVendaInterrompida==2  ){
                    if(!cCOO.equalsIgnoreCase("")){
                        int CupomAberto=0;
                        
                        CupomAberto = getEcfDinnmus().VerificarStatusCupomAberto();
                        
                        if(CupomAberto==0){
                            MensagemAoOperador= "Foi Identificado uma Venda Fiscal interrompida numero ["+ cCOO +"]\nPorém não existe nenhum cupom fiscal aberto no ECF\n\nUM NOVO CUPOM FISCAL PODERÁ SER INICIADO A PARTIR DO 1ª ITEM";
                            ReiniciarComprovante=true;
                            Opcoes=new Object[] { "Reiniciar do 1º Item"};
                        }else{ 
                            ReiniciarComprovante=true;
                            if(!cCOO.equalsIgnoreCase(cCOOAtual)){                               
                               MensagemAoOperador="Foi Identificado uma Venda Fiscal interrompida numero ["+ cCOO +"]\nPorém o cupom fiscal aberto no ECF É ["+ cCOOAtual +"]\n\nUM NOVO CUPOM FISCAL PODERÁ SER INICIADO A PARTIR DO 1ª ITEM";                               
                               Opcoes=new Object[] { "Reiniciar do 1º Item","Ignorar Nota"};
                            }else{
                               Opcoes=new Object[] { "Continuar nota","Ignorar Nota"};
                            }
                        }
                    }
                }         
                if(Opcoes==null){
                   Opcoes=new Object[] { "Continuar nota","Ignorar Nota"};                                         
                }  
                if(MensagemAoOperador.equalsIgnoreCase("")){
                   MensagemAoOperador ="Foi identificado um nota interrompida\n\nEscolha uma opção Abaixo:\n";
                }

                cRet= MetodosUI_Auxiliares_1.InputBox(null,MensagemAoOperador , "Nota interrompida", "AVISO",Opcoes,"Continuar Nota");
                
                cRet = TratamentoNulos.getTratarString().Tratar(cRet, "").trim();
                 
                if(cRet.equalsIgnoreCase("Continuar Nota") || cRet.equalsIgnoreCase("Reiniciar do 1º Item") || cRet.equalsIgnoreCase("Ignorar Nota") ){                    
                    LimpaEspelhoVenda();
                    if( cRet.equalsIgnoreCase("Continuar Nota")){  
                         VendaRecuperada=true;
                        return ReExibirNotaInterrompida(nCodigoVenda,cTipoComprovante,(cRet.equalsIgnoreCase("Continuar Nota") ?  true : false ),pdvgerenciar.CodigoPDV(),MomentoVendaInterrompida,PreVendasSelecionadas.getNotasSelecionadas(),true,false, IDVendedor, IDCliente);
                    }else if(cRet.equalsIgnoreCase("Reiniciar do 1º Item")){                          
                            if(!cTipoComprovante.equalsIgnoreCase("")){
                               int nRet=0;
                               if(!ReiniciarComprovante){
                                     nRet= MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Deseja utilizar o mesmo tipo de compravante ?","Tipo de Comprovante",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);                              
                                     if(nRet==JOptionPane.NO_OPTION){
                                       if(cTipoComprovante.equalsIgnoreCase("nfiscal")){
                                           cTipoComprovante="fiscal";
                                       }else{
                                           cTipoComprovante="CUPOM VENDA";
                                       }
                                     }else if(nRet==JOptionPane.CANCEL_OPTION){
                                           this.dispose();
                                           return false;
                                     }
                               }
                            }
                            
                            boolean RetornoNotaInterrompida = ReExibirNotaInterrompida(nCodigoVenda,cTipoComprovante,(cRet.equalsIgnoreCase("Continuar Nota") ?  true : false ),pdvgerenciar.CodigoPDV(),MomentoVendaInterrompida,PreVendasSelecionadas.getNotasSelecionadas(),true,false, IDVendedor, IDCliente);
                            if(RetornoNotaInterrompida){
                                VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(nCodigoVenda, getEcfDinnmus().UltimoCupom().trim(), null, null, null, "",null,null,null,null,null,null);
                                //RetornoNotaInterrompida=VendaEmEdicao.RegistrarVendaEmEdicao(dadosorc.getCodigo(), cTipoComprovante, cCOOAtual);
                                VendaRecuperada=true;
                            }
                            return RetornoNotaInterrompida;
                    }
                    else if(cRet.equalsIgnoreCase("Ignorar Nota")){
                        
                       if(!RetTEF.equalsIgnoreCase("")){ 
                           int nRetPergTeF = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Foi solicitado o CANCELAMENTO DA VENDA, porém existe uma transação TEF em Andamento.\n\nDESEJA REALMENTE CANCELAR A VENDA TEF ?", "TRANSAÇÃO TEF EM ANDAMENTO");
                           if(nRetPergTeF!=MetodosUI_Auxiliares_1.Sim()){
                               return false;
                           }
                       }
                       
                       
                       Concorrencia.LiberarNota();
                       PreVendasSelecionadas.LimparNotasSelecionadas();
                       Integer nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "extornar", Sistema.getLojaAtual(), true, "Extornar Venda");    // TODO add your handling code here:
                       if(nCodigoUsuario>0){
                           int CodigoPDV = pdvgerenciar.CodigoPDV();
                           if(!NFCE_ChaveDeAcesso.equalsIgnoreCase("")){
                               String versao = NFCe_ConfiguracaoAmbiente.getVersaoNFCe(CodigoPDV);
                               
                               DesfazerNFCE(nCodigoVenda,Sistema.getLojaAtual(),CodigoPDV,versao);
                           }
                           UsuarioAuditar.Auditar(nCodigoUsuario, "VENDAS","Extornar Venda" + nCodigoVenda.toString()); 
                           VendaEmEdicao.FinalizarNota(nCodigoVenda,true,true);                        
                       }else{
                           return false;
                       }
                    }
                    return true;
                }else{
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, "Opção inválida", "Nota interrompida", "AVISO");
                    return false;
                }
           
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

        return true;
    }
    private boolean DesfazerNFCE(Long CodigoVenda, int Loja , int PDV, String versao ){
    boolean Ret = false;
    try {
        
        String ChaveDeAcesso="",XML;        
        byte[] _XML;
        ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
        if(rs.next()){
            _XML = rs.getBytes("nfce_xml");
            ChaveDeAcesso = rs.getString("nfce_chavedeacesso");
             XML = new String(_XML);
            NFCE_DesfazerNFCE nfce_desfazer = new NFCE_DesfazerNFCE();
            boolean RetDefazer = nfce_desfazer.Desfazer(CodigoVenda, ChaveDeAcesso, XML, 
                    NFCe_ConfiguracaoAmbiente.getConfig().getCUF(), Loja, PDV,versao);
            if (!RetDefazer) {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível desfazer a nfce", "NFCe - aberta");
            } else {
                VendaEmEdicao.RegistrarVendaEmEdicao_NFCe(CodigoVenda, "");
                return true;
            }
        }
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
    public boolean VerificarRecebimentoInterrompida(String RetTEF,  ResultSet rs){
         String cRet="0";
         
         try {         
                String cTipoComprovante=rs.getString("TipoComprovante");
                Long nCodigoVenda = rs.getLong("CodigoUltimaVenda");               
               
                if(cTipoComprovante.equalsIgnoreCase("fiscal")){
                    if(!isECFDisponivel()){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "FOI IDENTIFICADO UM RECEBIMENTO FISCAL INTERROMPIDA, PORÉM O ECF NÃO ESTA DISPONÍVEL\n\nREALIZE A VERIFICAÇÃO INICIAL DO ECF", "ECF NÃO DISPONÍVEL");
                        return false;                        
                    }
                }                         
                this.MomentoVendaInterrompida = VendaEmEdicao.MomentoVendaInterrompida(nCodigoVenda);                               
                Object[] Opcoes=null;
                String MensagemAoOperador="";
                //Boolean ReiniciarComprovante=false;
                Opcoes=new Object[] { "Continuar Recebimento","Ignorar Recebimento"};                       
                if(MensagemAoOperador.equalsIgnoreCase("")){
                   MensagemAoOperador ="Foi identificado um recebimento interrompida\n\nEscolha uma opção Abaixo:\n";
                }

                
                cRet= MetodosUI_Auxiliares_1.InputBox(null,MensagemAoOperador , "Recebimento interrompido", "AVISO",Opcoes,"Continuar Recebimento");
                
                cRet = TratamentoNulos.getTratarString().Tratar(cRet, "").trim();


                
                if(cRet.equalsIgnoreCase("Continuar Recebimento") || cRet.equalsIgnoreCase("Ignorar Recebimento") ){                    
                    LimpaEspelhoVenda();
                    if( cRet.equalsIgnoreCase("Continuar Recebimento")){                          
                        if (!Sistema.isOnline()) {
                            Concorrencia.LiberarNota();
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não é possível continuar o recebimento com o sistema OFF-LINE", "SISTEMA OFF-LINE");                            
                            return true;
                        }
                        return ReExibirRecebimento(nCodigoVenda);
                    }
                    else if(cRet.equalsIgnoreCase("Ignorar Recebimento")){
                        
                       if(!RetTEF.equalsIgnoreCase("")){ 
                           int nRetPergTeF = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Foi solicitado o CANCELAMENTO DO RECEBIMENTO, porém existe uma transação TEF em Andamento.\n\nDESEJA REALMENTE CANCELAR O RECEBIMENTO TEF ?", "TRANSAÇÃO TEF EM ANDAMENTO");
                           if(nRetPergTeF!=MetodosUI_Auxiliares_1.Sim()){
                               return false;
                           }
                       }
                       Concorrencia.LiberarNota();                       
                       Integer nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "extornar", Sistema.getLojaAtual(), true, "Extornar Recebimento");    // TODO add your handling code here:
                       if(nCodigoUsuario>0){
                           UsuarioAuditar.Auditar(nCodigoUsuario, "Recebimento","Extornar Recebimento" + nCodigoVenda.toString()); 
                           VendaEmEdicao.FinalizarNota(nCodigoVenda,true,true);                        
                       }else{
                           return false;
                       }
                    }
                    return true;
                }else{                    
                    return false;
                }
           
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

        return true;
    }
    public boolean ReExibirRecebimento(Long CodigoOperacao){
        boolean Ret = false;
        try {
            String DadosRecebimento="";
            ResultSet rs = DAO_RepositorioLocal.GerarResultSet("select dadosrecebimento from off_dadosultimavenda_recebimento  where codigoultimavenda="+ CodigoOperacao);
            if(rs.next()){
                DadosRecebimento = rs.getString("dadosrecebimento");
                BaixarConta b = (BaixarConta)XStream_Api.ConverterXmlParaObjeto(DadosRecebimento);                
                ParametrosGlobais.setBaixarConta(b);
                DefinirCliente(b.getDuplicatas_ClienteCodigo().toString(), b.getDuplicatas_ClienteNome());
                Ret = AbrirComprovanteCrediario(CodigoOperacao,b,true);
                if(Ret){
                  this.MomentoVendaInterrompida = VendaEmEdicao.MomentoVendaInterrompida(CodigoOperacao);                                  
                  if(MomentoVendaInterrompida>=2 ){
                      AbrirFechamentoAutomaticamente=true; //PrepararFechamento(MomentoVendaInterrompida);
                  }
                }
                
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
            
    public boolean IniciarCamposTexto()
    {
        boolean bRet=false;
        try {
            try {
                    ResultSet rs = Sistema.getClientePadrao(Sistema.getLojaAtual(), Sistema.getDadosLoja(Sistema.getLojaAtual(),true).getInt("ClientePadrao"));
                    if(rs!=null){
                        txtNomeCliente.setText(String.valueOf(rs.getInt("Codigo")) + " - " + rs.getString("nome"));
                        getDadosorc().setCodcliente(String.valueOf(rs.getInt("Codigo")));
                        getDadosorc().setCliente(rs.getString("nome"));
                        txtNomeCliente.setVisible(true);
                    }else{
                        //txtNomeCliente.setText("0 - ** Consumidor **");
                        txtNomeCliente.setVisible(false);
                        getDadosorc().setCodcliente("0");
                        getDadosorc().setCliente("");
                    }
                } catch (SQLException ex) {
                    LogDinnamus.Log(ex, true);
                }
                //txtOperador.setText(UsuarioSistema.getNomeUsuario());
                
                bRet=true;
        } catch (Exception e) {
           LogDinnamus.Log(e);
        }
        return bRet;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LogoCliente;
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelCamposTotal;
    private javax.swing.JPanel PainelCarregando;
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelItens;
    private javax.swing.JPanel PainelItensAtendimento;
    private javax.swing.JPanel PainelItensRegistrados;
    private javax.swing.JScrollPane PainelItensRolavel;
    private javax.swing.JPanel PainelPasseProduto1;
    private javax.swing.JPanel PainelPreco;
    private javax.swing.JPanel PainelProcurar;
    private javax.swing.JPanel PainelProduto;
    private javax.swing.JPanel PainelQuantidade;
    private javax.swing.JPanel PainelRodape;
    private javax.swing.JPanel PainelSubtotal;
    private javax.swing.JPanel PainelTopo;
    private javax.swing.JPanel PainelTotalGeralNota;
    private javax.swing.JPanel PainelVenda;
    private javax.swing.JButton btCliente;
    private javax.swing.JButton btCliente1;
    private javax.swing.JToggleButton btGaveta;
    public javax.swing.JToggleButton btImprimirComprovante;
    private javax.swing.JButton btMenuPDV;
    private javax.swing.JButton btModoVenda;
    private javax.swing.JButton btOcultarBarra;
    private javax.swing.JButton btReimpressao;
    private javax.swing.JButton btVendedor;
    public javax.swing.JComboBox cbTbPreco;
    private javax.swing.JComboBox cbVendedor;
    private static javax.swing.JLabel imagemProduto;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCarregandoPDV;
    private javax.swing.JLabel lblECF;
    javax.swing.JLabel lblEnvio;
    private javax.swing.JLabel lblLogoCarregando;
    private javax.swing.JLabel lblNFCe;
    private javax.swing.JLabel lblNFCe_Contigencia;
    private javax.swing.JLabel lblNomeComprovante;
    private javax.swing.JLabel lblPreco;
    private javax.swing.JLabel lblPreco1;
    private javax.swing.JLabel lblQt;
    private javax.swing.JLabel lblQtItens;
    javax.swing.JLabel lblRecepcao;
    javax.swing.JLabel lblStafusSincronismo;
    private javax.swing.JLabel lblStatusConexao;
    javax.swing.JLabel lblStatusTEF;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUltimoSincCadastro;
    private javax.swing.JLabel lbltitulo;
    private javax.swing.JPanel painelLogoCliente;
    private javax.swing.JTextPane txtCabecaNota;
    public javax.swing.JTextField txtECF;
    private javax.swing.JTextPane txtItens;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextArea txtNomeProduto;
    private javax.swing.JTextField txtNomeVendedor;
    private javax.swing.JFormattedTextField txtPreco;
    private javax.swing.JTextField txtProcurar;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JFormattedTextField txtSubTotal;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtTotalItens;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the dadosorc
     */
    public Dadosorc getDadosorc() {
        if(dadosorc==null)
            dadosorc=new Dadosorc();

        return dadosorc;
    }

    /**
     * @param dadosorc the dadosorc to set
     */
    public void setDadosorc(Dadosorc dadosorc) {

        this.dadosorc = dadosorc;
    }

    /**
     * @return the itensorc
     */
    public Itensorc getItensorc() {
        if(itensorc==null)
            itensorc=new Itensorc();
        return itensorc;
    }

    /**
     * @param itensorc the itensorc to set
     */
    public void setItensorc(Itensorc itensorc) {
        this.itensorc = itensorc;
    }

    /**
     * @return the arItensorc
     */
    public ArrayList<Itensorc> getArItensorc() {
        if(arItensorc==null)
            arItensorc=new ArrayList<Itensorc>();
        return arItensorc;
    }

    /**
     * @param arItensorc the arItensorc to set
     */
    public void setArItensorc(ArrayList<Itensorc> arItensorc) {
        this.arItensorc = arItensorc;
    }
    private void HostEmAtividade_Atualizar(){
        HostEmAtividade=System.currentTimeMillis();
    }

    /**
     * @return the ECFDisponivel
     */
    public boolean isECFDisponivel() {
        return ECFDisponivel;
    }

    /**
     * @param ECFDisponivel the ECFDisponivel to set
     */
    public void setECFDisponivel(boolean ECFDisponivel) {
        this.ECFDisponivel = ECFDisponivel;
        PDVComprovanteFiscal.setEcfDinnmus(ECFAtual.getECF());
        LogoStatusECF(isECFDisponivel());                                    
    }

    /**
     * @return the bInterroperTarefaCaixaLivre
     */
    public boolean isbInterroperTarefaCaixaLivre() {
        return bInterroperTarefaCaixaLivre;
    }

    /**
     * @param bInterroperTarefaCaixaLivre the bInterroperTarefaCaixaLivre to set
     */
    public void setbInterroperTarefaCaixaLivre(boolean bInterroperTarefaCaixaLivre) {
        this.bInterroperTarefaCaixaLivre = bInterroperTarefaCaixaLivre;
    }

    /**
     * @return the StatusTarefaCaixaLivre
     */
    public int getStatusTarefaCaixaLivre() {
        return StatusTarefaCaixaLivre;
    }

    /**
     * @param StatusTarefaCaixaLivre the StatusTarefaCaixaLivre to set
     */
    public void setStatusTarefaCaixaLivre(int StatusTarefaCaixaLivre) {
        /*
         0 - pausado
         1 - parado
         2 - ativo         
         */
        this.StatusTarefaCaixaLivre = StatusTarefaCaixaLivre;
    }

    /**
     * @return the TeclasFuncao
     */
    public boolean isTeclasFuncao() {
        return TeclasFuncao;
    }

    /**
     * @param TeclasFuncao the TeclasFuncao to set
     */
    public void setTeclasFuncao(boolean TeclasFuncao) {
        this.TeclasFuncao = TeclasFuncao;
    }

    /**
     * @return the ModoDeRecebimento
     */
    public boolean isModoRecebimento() {
        return ModoDeRecebimento;
    }

    /**
     * @param ModoDeRecebimento the ModoDeRecebimento to set
     */
    public void setModoRecebimento(boolean ModoRecebimento) {
        this.ModoDeRecebimento = ModoRecebimento;
    }
    public boolean ModoVenda(){
        boolean Ret = false;
        try {
             if((arItensorc!=null ? arItensorc.size() : 0 )>0 || PreVendasSelecionadas.getNotasSelecionadas().size()>0){
                 Ret=true;
             }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    /**
     * @return the NFCe_OK
     */
    public boolean isNFCe_OK() {
        return NFCe_OK;
    }

    /**
     * @param NFCe_OK the NFCe_OK to set
     */
    public void setNFCe_OK(boolean NFCe_OK) {
        this.NFCe_OK = NFCe_OK;
    }

    /**
     * @return the bloq
     */
    public BloquearTela getBloq() {
        if(bloq==null){
          bloq = new BloquearTela();
        }
        return bloq;
    }

    /**
     * @param bloq the bloq to set
     */
    public void setBloq(BloquearTela bloq) {
        this.bloq = bloq;
    }

    /**
     * @return the MenuF1Ativo
     */
    public boolean isMenuF1Ativo() {
        return MenuF1Ativo;
    }

    /**
     * @param MenuF1Ativo the MenuF1Ativo to set
     */
    public void setMenuF1Ativo(boolean MenuF1Ativo) {
        this.MenuF1Ativo = MenuF1Ativo;
    }
    private class TarefaCaixaLivre implements Runnable {
        
        public void run() {
              
              while(ExecutarTarefaCaixaLivre){
                if(getStatusTarefaCaixaLivre()==2/* ativo */ || ForcarExibicaoDescanso)  {
                    if(/*(System.currentTimeMillis()-HostEmAtividade>80000 && PreVendasSelecionadas.getNotasSelecionadas().size()==0 && !ModoRecebimento(false) &&  (arItensorc!=null ? arItensorc.size() : 0 )==0) ||*/ TrocoUltimaVenda>0f || ForcarExibicaoDescanso ){
                       String RetornoCodigoLidoEmEspera= ChamarFormCaixaLivre(TrocoUltimaVenda);
                       HostEmAtividade=System.currentTimeMillis();
                       TrocoUltimaVenda=0f;                       
                       ForcarExibicaoDescanso=false;
                       if(RetornoCodigoLidoEmEspera.length()>0){
                           txtProcurar.setText(RetornoCodigoLidoEmEspera);
                           txtProcurar.requestFocus();
                           try {
                               new Robot().keyPress(KeyEvent.VK_ENTER);
                           } catch (AWTException ex) {
                              LogDinnamus.Log(ex, true);
                           }
                       }else{
                           txtProcurar.requestFocus();
                       }
                       
                    }
                }
                  try {
                      Thread.sleep(10);
                  } catch (InterruptedException ex) {
                      LogDinnamus.Log(ex, true);
                  }
                //System.out.println("Executando Tarefa de Tela Caixa Livre " + new GregorianCalendar().getTime().toString());
              }
        }
    }
    public void SincronizarMovimentoPendente(){
        try {
            CaixaLivrePausar();                        
            BloquearTela B1 = new BloquearTela();
            B1.Tela_Bloquear(this, 0.5f);
            SincronizarMovimento.Iniciar(false, false);  
            B1.Tela_DesBloquear();
            CaixaLivreAtivar();
            setTeclasFuncao(true);
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private class TarefaSincronismo implements Runnable {
        public void run() {
            try {
                boolean VerificarPendencias = false;
                ExecutandoTarefaSincronismo=false;
                while (ExecutarTarefaSincronismo) {                      
                    if(!InterromperTarefaSincronismo){
                        ExecutandoTarefaSincronismo=true;
                        //System.out.println("Executando TaferaSincronismo " + ManipularDatas.DataAtual());
                        String StatusSalvo = lblStatusConexao.getText();
                        
                        boolean modotrabalho = Sistema.isOnline();//Dao_Jdbc_1.getConexao().TestarConexao();
                        
                        ExibirLogoStatusConexao(modotrabalho);   
                        ExibirLogoEnvio();
                        ExibirLogorecepcao();
                        VerificarPendencias=false;
                        
                        if((arItensorc!=null ? arItensorc.size()==0 : false) && !SincronizarMovimento.EnviandoMovimento && PreVendasSelecionadas.getNotasSelecionadas().size()==0 && modotrabalho ){
                           int nTotalVendasPendentes =  SincronizarMovimento.VerificarVendasPendentesDeSincronismo();
                           if(nTotalVendasPendentes>0 && !isMenuF1Ativo()){
                              setTeclasFuncao(false);
                              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "FORAM DETECTADAS VENDAS PENDENTES DE SINCRONISMO\n\nO SINCRONISMO SERÁ REALIZADO AGORA", "SISTEMA RETORNANDO A CONEXÃO ONLINE");                                   
                              SincronizarMovimentoPendente();
                              SincronizarMovimento.EnviarDadosCaixa(false, false);
                              VerificarPendencias=false;
                           }
                        }
                        ExecutandoTarefaSincronismo=false;
                    }
                    Thread.sleep(1000);
                    //System.out.println("Executando Tarefa de sincronismo " + new GregorianCalendar().getTime().toString());
                }
            }
            catch (InterruptedException e) {
                LogDinnamus.Log(e);
            }
        }
    }
    public void CaixaLivrePausar(){
         if(getStatusTarefaCaixaLivre()!=1){
            AlterarStatusCaixaLivre(0);  // pausa
         }
    }
    public void CaixaLivreAtivar(){
            if(getStatusTarefaCaixaLivre()!=1){
               HostEmAtividade=System.currentTimeMillis();   
               AlterarStatusCaixaLivre(2);  // ativo 
            }      
    }
    public void Evento_F1(){
        try {                      
            if(SincronizarMovimento.EnviandoMovimento){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Aguarde o envio do movimento para acionar o Menu PDV", "Enviando Movimento");
            }else{
                CaixaLivrePausar();                
                getBloq().Tela_Bloquear(this, 0.5f, Color.BLACK);
                setMenuF1Ativo(true);
                new frmPDV_Simples_Menu(null, true, this).setVisible(true);
                setMenuF1Ativo(false);
                ExibirUltimoSincCad();
                getBloq().Tela_DesBloquear();
                CaixaLivreAtivar();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    public boolean PodeChamarReimpressao(){
        try {
            
              if(ModosTrabalhoDisponiveis!=null && ModosTrabalhoDisponiveis.size()==1){
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO ESTA DISPONÍVEL A IMPRESSÃO DE COMPROVANTES", "REIMPRESSÃO");
                    return false;
              }
              if(PreVendasSelecionadas.getNotasSelecionadas().size()==1){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EXISTE UMA PRÉ-VENDA EM ABERTO. FINALIZE OU CANCELE", "REIMPRESSÃO");
                    return false;
                }
                
                if(PreVendasSelecionadas.getNotasSelecionadas().size()==0){
                    if(arItensorc.size()>0){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EXISTE UMA VENDA EM ABERTO. FINALIZE OU CANCELE", "REIMPRESSÃO");
                        return false;
                    }
                }
                    
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    
    public boolean PodeChamarPreVenda(){
        try {
                if(!Sistema.isOnline()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O SISTEMA ESTÁ EM MODO OFF-LINE\nNÃO É POSSÍVEL IMPORTAR A PRÉ-VENDA", "PRÉ-VENDA");
                    return false;
                }
                int nCodigoUsuario = ValidarAcessoAoProcesso.Verificar(null, UsuarioSistema.getIdUsuarioLogado(), "PDV_VDA", Sistema.getLojaAtual(), true, "REALIZAR VENDA");

                if (nCodigoUsuario == 0) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "OPERADOR NÃO AUTORIZADO REALIZAR VENDA", "NÃO AUTORIZADO");
                    return false;
                }
                if(!pdvgerenciar.EnviarMovimento()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O SISTEMA ESTÁ COM O ENVIO DE MOVIMENTO DESATIVADO\nNÃO É POSSÍVEL IMPORTAR A PRÉ-VENDA", "PRÉ-VENDA");
                    return false;
                }
                if(!btModoVenda.getText().contains("VENDENDO")){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O SISTEMA ESTÁ EM MODO DE CONSULTA[F2]\nNÃO É POSSÍVEL IMPORTAR A PRÉ-VENDA", "PRÉ-VENDA");
                    return false;
                }
                if(PreVendasSelecionadas.getNotasSelecionadas().size()==1){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EXISTE UMA PRÉ-VENDA EM ABERTO. FINALIZE OU CANCELE", "PRÉ-VENDA");
                    return false;
                }
                
                if(PreVendasSelecionadas.getNotasSelecionadas().size()==0){
                    if(arItensorc.size()>0){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EXISTE UMA VENDA EM ABERTO. FINALIZE OU CANCELE", "PRÉ-VENDA");
                        return false;
                    }
                }
                    
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    
    private boolean AcionarPreVenda(){
        try {
                if(ModoRecebimento(true))  {                  
                    return false;
                 }
                if(!PodeChamarPreVenda()){
                    return false;
                }
                ParametrosGlobais.setPreVenda_Codigo(new ArrayList<Long>());
                ParametrosGlobais.setPreVenda_Concomitante(false);
                AlterarStatusCaixaLivre(0); // pausa
                //bloq.Tela_Bloquear(this, 0.5f);
                new frmPDV_Simples_ImportarPrevenda(null, true).setVisible(true);
                //bloq.Tela_DesBloqueartr
                
                if(ParametrosGlobais.getPreVenda_Codigo().size()>0){
                    final ArrayList<Long> CodigoPreVenda = ParametrosGlobais.getPreVenda_Codigo();                                       
                    final boolean Concomitante = ParametrosGlobais.isPreVenda_Concomitante();
                    if(CodigoPreVenda.size()>1){
                        if(!ParametrosGlobais.isMesclagem_Alterada()){
                           return false; 
                        }else{
                          Concorrencia.LiberarNota();
                          VendaEmEdicao.FinalizarNota(getDadosorc().getCodigo()); 
                          PreVendasSelecionadas.LimparNotasSelecionadas();
                          LimparNota(true);
                        }
                    }
                    String VerificarNota = Concorrencia.VerificarNota(CodigoPreVenda);
                    
                    if(VerificarNota.equalsIgnoreCase("erro")){ return false;}
                    
                    if(VerificarNota.length()!=0 ){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, VerificarNota, "NOTA BLOQUEADA");
                        PreVendasSelecionadas.LimparNotasSelecionadas();
                        LimparNota(true);
                        return false;
                    }                 
                    
                    if(getArItensorc().size()>0 && CodigoPreVenda.size()==1){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSIVEL IMPORTAR A PRE-VENDA. \n\nEXISTE UMA VENDA EM ANDAMENTO", "PRÉ-VENDA");     
                        return false; 
                     }          
                     //BloquearTela
                     Ativar_Desativar_PDV(false);
                     new Thread("AcionarPreVenda"){
                         public void run(){
                            if(!ImportarPrevenda(CodigoPreVenda,Sistema.getLojaAtual(),Sistema.getFilial(),pdvgerenciar.CodigoPDV(), Concomitante )){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL IMPORTAR PRÉ-VENDA", "PRÉ-VENDA");
                                ApagarNota(true);                        
                            }else{                                    
                                Concorrencia.BloquearNota(CodigoPreVenda);                                    
                                txtItens.update(txtItens.getGraphics());
                            }
                            Ativar_Desativar_PDV(true);
                            PreVendasSelecionadas.getNotasSelecionadas().size();
                            txtProcurar.requestFocus();
                         }
                     }.start();
                    
                }else{
                    ApagarNota(true);
                }
                
                AlterarStatusCaixaLivre(2);
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public void Evento_F4(){
        try {
            
                 if(ModoRecebimento(true))  {                    
                    return ;
                 }
                 if(txtProcurar.isFocusOwner()){
                    CaixaLivrePausar(); 
                    F4_PesquisarProduto();                    
                    CaixaLivreAtivar();
                 }
                 
         
                 
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
      public void Evento_F5(){
        try {
          
                 
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    public void Evento_F2(){
        try {         
            if(ModoRecebimento(true))  {
               // MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Operação não permitida com o sistema em MODO RECEBIMENTO ", "SISTEMA EM MODO RECEBIMENTO");
               return ;
             }
            LogoModoOperacao();            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    public void Evento_F7(){
        try {
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private void AlterarStatusCaixaLivre(int Status){

        if(Status==2){            
             HostEmAtividade=System.currentTimeMillis();            
        }
        setStatusTarefaCaixaLivre(Status);
    }
    private void Evento_F12(){
        try {
             //if(tbInteracao.getSelectedIndex()==0){   
               AcionarPreVenda();          
             //}
            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }
   
    private void Evento_ESC(){
        try {
            // if(tbInteracao.getSelectedIndex()==0)
               Sair();
            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }
    private boolean AjustarTipoDeComprovante(){
        try {
                ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
                if(rs.next()){
                    String cTipoComprovante = rs.getString("tipocomprovante");
                    String PreVenda = TratamentoNulos.getTratarString().Tratar(rs.getString("prevenda"),"");

                    if(cTipoComprovante.equalsIgnoreCase("fiscal")){
                        AtivarModoFiscal();
                    }else if(cTipoComprovante.equalsIgnoreCase("nfiscal")){
                        AtivarModoSemImpressao_NFCe();
                    }else if(cTipoComprovante.equalsIgnoreCase("nfce")){   
                          AtivarModoNaoFiscal(false);
                    }else{
                        if(PreVenda.equalsIgnoreCase("")){
                            AtivarModoSemImpressao();
                        }else{
                            AtivarModoFiscal();
                        }
                    }
                }
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
            
    private boolean AtivarModoFiscal(){
        try {
            if(ECFDisponivel){
                TipoComprovantePDV="CUPOM FISCAL.";
                btImprimirComprovante.setText("Imprimir Comprovante - [ ON ]");
                btImprimirComprovante.setSelected(true);
                btModoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO VERDE.png")));
                TipoComprovanteAtual="fiscal";              
                //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO(null, "ECF NÃO DISPONÍVEL", "PDV - MODO DE TRABALHO");               
                return true;
            }          
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
    
    private boolean AtivarModoNaoFiscal(boolean ModoSilencioso){
        try {
            if(PDVComprovante.getImpressoraCompravante()!=null){
                TipoComprovanteAtual="nfiscal";
                TipoComprovantePDV ="CUPOM VENDA";
                btImprimirComprovante.setText("Imprimir Comprovante - [ ON ]");
                btImprimirComprovante.setSelected(true);
                btModoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO AZUL.png")));            
                return true;
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
   private boolean AtivarModoSemImpressao_NFCe(){
        try {
            if(isNFCe_OK()){
                btModoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO BRANCO.png")));
                btImprimirComprovante.setText("Imprimir Comprovante - [ OFF ]");
                TipoComprovantePDV = "nfce";
                btImprimirComprovante.setSelected(false);
                TipoComprovanteAtual="nfce";                                
                return true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
           
        }
         return false;
    }
    
    private boolean AtivarModoSemImpressao(){
        try {
            
            btModoVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO VERMELHO.png")));
            btImprimirComprovante.setText("Imprimir Comprovante - [ OFF ]");
            TipoComprovantePDV = "";
            btImprimirComprovante.setSelected(false);
            TipoComprovanteAtual="nimp";                                
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    private void TrocarTipoComprovante(){
        try {
            if(ModoRecebimento(true))  {
               ///MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Operação não permitida com o sistema em MODO RECEBIMENTO ", "SISTEMA EM MODO RECEBIMENTO");
               return ;
             }
            
            if(getArItensorc().size()>0){
                boolean isPrevenda_Concomitante=false;
                //String Prevenda = "";
                String TipoComprovante = "";
                ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
                if(rs.next()){
                    //Prevenda = TratamentoNulos.getTratarString().Tratar(rs.getString("prevenda"),"");
                    isPrevenda_Concomitante = rs.getBoolean("prevenda_concomitante");
                    TipoComprovante =  TratamentoNulos.getTratarString().Tratar(rs.getString("tipocomprovante"),"");
                    
                }
                if(!TipoComprovante.equalsIgnoreCase("") || isPrevenda_Concomitante ){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ESTA FUNÇÃO NÃO ESTA DISPONÍVEL\nQUANDO EXISTEM ITENS JÁ IMPRESSOS", "MODO OPERACIONAL(F10)");
                    return;
                }else{
                 //   if(tpPrincipal.getSelectedIndex()==0){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EXISTEM ITENS REGISTRADOS.\n\nO MODO DE TRABALHO PODERA SER ALTERADO APENAS NO FECHAMENTO DA VENDA", "MODO OPERACIONAL(F10)");
                        return;
                   // }
                   
                }
            }
            
            if(getDadosorc().getCodigo()!=null){
                if(PagtoorcRN.Pagtoorc_VendaTEF(getDadosorc().getCodigo())){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSIVEL ALTERAR O MODO OPERACIONAL\n\nExistem venda(s) aprovada(s) no cartão de crédito", "MODO OPERACIONAL(F10)");
                    return;                    
                }
            }
            btModoVenda.setText("[F2] VENDENDO");
            int Indice  =ModosTrabalhoDisponiveis.indexOf(TipoComprovanteAtual);
            String ProximoModo = "";
            if(Indice<ModosTrabalhoDisponiveis.size()-1){
                ProximoModo=ModosTrabalhoDisponiveis.get(Indice+1);
            }else{
                ProximoModo=ModosTrabalhoDisponiveis.get(0);
            }
            if(ProximoModo.equalsIgnoreCase("fiscal")){             
                 AtivarModoFiscal();                
            }else if(ProximoModo.equalsIgnoreCase("nfiscal")){                                            
                    AtivarModoNaoFiscal(true);
            }else if(ProximoModo.equalsIgnoreCase("NFCe")){                                            
                    AtivarModoSemImpressao_NFCe();             
            }else if(ProximoModo.equalsIgnoreCase("nimp")){                     
                   AtivarModoSemImpressao(); 
                           
            }            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
public boolean DefinirModosTrabalho(){
    boolean Ret = false;
    try {
        ModosTrabalhoDisponiveis=new ArrayList<String>();
        ModosTrabalhoDisponiveis.add("nimp");
        if( isNFCe_OK()){
            ModosTrabalhoDisponiveis.add("nfce");
        }
        if(ECFDisponivel){
            ModosTrabalhoDisponiveis.add("fiscal");
        }
        if(PDVComprovante.ImpressoraDeComprovante_Iniciar()){
            
           
            
            ModosTrabalhoDisponiveis.add("nfiscal");
             
        }else{
            
            if(ModosTrabalhoDisponiveis.contains("nfce")){
                
                
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("A NFCe está habilitada, mas não poderá ser utilizada\n\nO IMPRESSORA DE COMPROVANTE NÃO ESTA DISPONÍVEL", "NFCe - NÃO DISPONÍVEL");
                ModosTrabalhoDisponiveis.remove("nfce");
                setNFCe_OK(false);
                DefinirModoTrabalhoNFCe();
                
            }
        }
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
public boolean Ativar_Desativar_PDV(boolean status){
    boolean Ret = false;
    try {
        
       MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelBotoes, status);
       setTeclasFuncao(status);
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
public void TeclaAtalho_Acoes_2(String Fonte){
    TeclaAtalho_Acoes(Fonte);
    txtProcurar.requestFocus();
}
public void TeclaAtalho_Acoes(String Fonte){
        try {
             if(!isTeclasFuncao()){
                return ;
             }            
             if(Fonte.equalsIgnoreCase("F12")){
                Evento_F12();
               
             }      
             
             else if(Fonte.equalsIgnoreCase("F11")){           
                    PainelRodape.setVisible(!PainelRodape.isVisible());                    
                    txtProcurar.requestFocus();           
             }
             else if(Fonte.equalsIgnoreCase("F1")){ 
                  Evento_F1();
             }
             else if(Fonte.equalsIgnoreCase("F10")){                                              
                  TrocarTipoComprovante();             
             }
             else if(Fonte.equalsIgnoreCase("F2")){                                
                  Evento_F2();
             }
             else if(Fonte.equalsIgnoreCase("F3")){                                                  
                  btClienteActionPerformed(null);
             }
             else if(Fonte.equalsIgnoreCase("F4")){                                
                  Evento_F4();
             }
             else if(Fonte.equalsIgnoreCase("F5")){                                
                  btVendedorActionPerformed(null);
             }
             else if(Fonte.equalsIgnoreCase("F6")){
               //  btMesclagemActionPerformed(null);
             }
             else if(Fonte.equalsIgnoreCase("F7")){}
             else if(Fonte.equalsIgnoreCase("F8")){}
             else if(Fonte.equalsIgnoreCase("F9")){
                 btReimpressaoActionPerformed(null);
             }
             else if(Fonte.equalsIgnoreCase("ESCAPE")){    
                   Evento_ESC();
             }
            else if(Fonte.equalsIgnoreCase("UP")){ 
               if(txtProcurar.isFocusOwner()){
                 PainelItensRolavel.getVerticalScrollBar().setValue(PainelItensRolavel.getVerticalScrollBar().getValue()-10);
               }
            }
            else if(Fonte.equalsIgnoreCase("DOWN")){                 
               if(txtProcurar.isFocusOwner()){
                 PainelItensRolavel.getVerticalScrollBar().setValue(PainelItensRolavel.getVerticalScrollBar().getValue()+10);
               }
             }
        } catch (Exception ex) {
            LogDinnamus.Log(ex,true);
        }
    }    
    private boolean TeclasAtalhos_UI(){
        try {  
             AbstractAction TeclaAtalhos  = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                       String Fonte = e.getSource().toString();                
                       TeclaAtalho_Acoes_2(Fonte);
                       //txtProcurar.requestFocus();
                }
            };            
             
            String Teclas[] ={"UP","DOWN","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","ESCAPE"};  
            DefinirAtalhos2.Definir(PainelCorpo, Teclas, TeclaAtalhos);
            //DefinirAtalhos.Definir(PainelCorpo, TeclaAtalhos);
            return true;
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
           return false;
       }
   }

    private boolean DefinirControleTeclasFuncoesCheckout()
    {   
        return TeclasAtalhos_UI();       
    }

    
    private boolean ImportarPrevenda( ArrayList<Long>  CodigoVendaOnline, Integer Loja, Integer Filial, Integer PDV, boolean Concomitante){
        Boolean Retorno = false;
        try {           
            
            
            ProcessamentoComProgresso progresso = new ProcessamentoComProgresso();
            
            int TotalItens = ItensorcRN.Itensorc_Contar(CodigoVendaOnline);
            
            Retorno=ImportarPrevenda_Acao(CodigoVendaOnline, Loja, Filial, PDV, TotalItens, Concomitante, progresso);                         

            if(Retorno){
               DAO_RepositorioLocal.Commitar_Statment();
            }else{
                DAO_RepositorioLocal.RollBack_Statment();
            }
            
            progresso.Fechar();
            return Retorno;
                     
           
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            Retorno = false;
        }
        return Retorno;
    }
    
private boolean ImportarPrevenda_Acao(ArrayList<Long> CodigoVendaOnline, int Loja,int Filial, int PDV,int Total,boolean Concomitante ,ProcessamentoComProgresso progresso){
        boolean bRetorno = false;
        try {
            
            if(CodigoVendaOnline.size()>1){
                for (int i = 0; i < CodigoVendaOnline.size(); i++) {
                    if (!ImportarPrevenda_ImportarCotacaoParaOPdv(CodigoVendaOnline.get(i), Loja, Filial, PDV)){
                       return false; 
                    }
                }
            }
            ResultSet rsDadosPrevenda = PreVenda.ListarPreVendas(CodigoVendaOnline.get(0), Loja);
            ResultSet rsDadosPrevenda_itens = PreVenda.ListarPreVendas_Itens(CodigoVendaOnline, Loja,false);
            
            if(rsDadosPrevenda.next()){
               String ClienteCodigo = rsDadosPrevenda.getString("codcliente");
               String ClienteNome = rsDadosPrevenda.getString("cliente");
               String VendedorCodigoPre = rsDadosPrevenda.getString("codvendedor");
               String VendedorNomePre = rsDadosPrevenda.getString("vendedor");
               Long CodigoCotacao = rsDadosPrevenda.getLong("codigocotacao");
               Long CodigoOrcamento = rsDadosPrevenda.getLong("codigoorcamento");     
                if(CodigoVendaOnline.size()==1){   
                    if(!DadosorcRN.Dadosorc_Excluir_PrevendaLocal(CodigoCotacao, CodigoOrcamento, false)){
                       return false;
                    }               
                }
               getDadosorc().setCodcliente(ClienteCodigo);
               getDadosorc().setCliente(ClienteNome);
               if(CodigoVendaOnline.size()==1){
                  getDadosorc().setCodigocotacao(CodigoCotacao);
                  getDadosorc().setCodigoorcamento(CodigoOrcamento);                  
                  MetodosUI_Auxiliares_1.SetarOpcaoCombo_Model(cbVendedor, VendedorCodigoPre);
                  getDadosorc().setVendedor(VendedorNomePre);
                  getDadosorc().setCodvendedor(VendedorCodigoPre);                  
               }
               txtNomeCliente.setText(ClienteNome);
               //txtPreVenda.setText(CodigoCotacao.toString() + "-" + CodigoOrcamento.toString());
               bRetorno = ImportarPrevenda_RegistrarItens(rsDadosPrevenda_itens,Total,Concomitante,progresso,CodigoVendaOnline);
               if(bRetorno){                   
                   int TotalItensImportados = ItensorcRN.Itensorc_Local_Contar(getDadosorc().getCodigo());                   
                   if(TotalItensImportados!=Total){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FORAM IMPORTADOS TODOS OS ITENS DESTA PRE-VENDA", "PRÉ-VENDA NÃO CONCLUIDA ");
                       return false;
                   }                   
                   String TipoComprovante = "";
                   String COO ="";
                   if(Concomitante){
                      TipoComprovante = TipoComprovante();
                      if(TipoComprovante.equalsIgnoreCase("fiscal")){
                         COO = getEcfDinnmus().UltimoCupom();                       
                      }                  
                   }
                   Long CodigoTroca = ImportarPrevenda_RegistrarItensDevolvidos(CodigoVendaOnline,getDadosorc().getCodigo() ,Loja,PDV);
                           
                   if(CodigoTroca<0l){
                       return false;
                   }
                   if(!RegistrarITem_SomarTotalNotas()){
                       return false;
                   }                           
                   
                   bRetorno= VendaEmEdicao.RegistrarVendaEmEdicao(getDadosorc().getCodigo(), TipoComprovante,  COO, CodigoVendaOnline,  Concomitante );
                   
                    if(CodigoTroca>0l){
                      DAO_RepositorioLocal.Commitar_Statment();  
                      Double ValorCredito = Troca.ValorTotalCreditoTroca_PorVenda(getDadosorc().getCodigo());
                      if(!VendaEmEdicao.RegistrarVendaEmEdicao_VincularTroca(getDadosorc().getCodigo(),CodigoTroca,ValorCredito)){
                          return false;
                      }
                   }
                   
               }               
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return bRetorno;
    }   


private Long ImportarPrevenda_RegistrarItensDevolvidos(ArrayList<Long> CodigoVendaOnline, Long CodigoVendaPDV, int Loja,int PDV){
    try {
        
        ResultSet rsItensDevolvidos = ItensDevolvidosRN.ItensDevolvidos_Listar(CodigoVendaOnline, true,true);
        
        if(!rsItensDevolvidos.next()){
            return 0l;
        }else{
            rsItensDevolvidos.beforeFirst();
        }        
        
        Long PKTrocaItens = Troca.Registrar_Troca(CodigoVendaPDV,Loja,PDV);
        if(PKTrocaItens==0l){
            return -1l;
        }
        while(rsItensDevolvidos.next()){
             if(CodigoVendaOnline.size()>1){
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "TROCA NÃO ESTÁ DISPONÍVEL EM OPERAÇÃO DE MESCLAGEM","TROCA NÃO PERMITIDA");
                 return -1l;
             }
             Itensdevolvidos itens = new Itensdevolvidos();
             itens.setSeq(rsItensDevolvidos.getInt("seq"));
             itens.setCodigo(CodigoVendaPDV);
             itens.setCodprod(rsItensDevolvidos.getLong("codprod"));
             itens.setDescricao(rsItensDevolvidos.getString("descricao"));
             itens.setQuantidade(rsItensDevolvidos.getBigDecimal("quantidade"));
             itens.setPreco(rsItensDevolvidos.getBigDecimal("preco"));
             itens.setDescv(rsItensDevolvidos.getBigDecimal("descv"));
             itens.setDescp(rsItensDevolvidos.getBigDecimal("descp"));
             itens.setTotal(rsItensDevolvidos.getBigDecimal("total"));
             itens.setNomemov(rsItensDevolvidos.getString("nomemov"));
             itens.setNomecor(rsItensDevolvidos.getString("nomecor"));
             itens.setRef(rsItensDevolvidos.getString("ref"));
             itens.setCodtam(rsItensDevolvidos.getString("codtam"));
             itens.setNomeImpresso(rsItensDevolvidos.getString("nome_impresso"));
             if(!Troca.Registrar_Item(PKTrocaItens, itens, Loja, PDV)){  return -1l; }                         
        }                
        return PKTrocaItens;
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        return -1l;
    }
}
private boolean ImportarPrevenda_RegistrarItens(ResultSet rsDadosPrevenda_itens, int Total,boolean Concomitante, ProcessamentoComProgresso progresso, ArrayList<Long> PreVendas){
        boolean bRetorno = false;
        try {
            int Registro =1;
            while(rsDadosPrevenda_itens.next()){                
                
                bRetorno =  RegistrarItensDaNota(rsDadosPrevenda_itens, 0l,TipoComprovante(),false,pdvgerenciar.CodigoPDV(),false,true,Concomitante,false,PreVendas);
                if(!bRetorno){
                    break;
                }else{
                    Bloquear_Desbloquear_TelaProdutos(false);
                    if(Registro==1){
                        progresso.Iniciar("REGISTRANDO PRÉ-VENDA",Total) ;
                    }
                    progresso.AtualizarProgresso("REGISTRANDO ITEM " + Registro + "/" + Total, Registro);
                }                     
                Registro++;                               
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        Bloquear_Desbloquear_TelaProdutos(true);
        return bRetorno;
    }
    public boolean ImportarPrevenda_ImportarCotacaoParaOPdv(Long CodigoPreVenda, Integer Loja, Integer Filial, Integer PDV){
    boolean bRet = false;
    try {
                ResultSet rsPrevenda = PreVenda.ListarPreVendas(CodigoPreVenda); 
                
                Dadosorc dPreVenda = DadosorcRN.setDadosorc(rsPrevenda);                        
                
                if(!DadosorcRN.Dadosorc_Excluir_PrevendaLocal(dPreVenda.getCodigocotacao(), dPreVenda.getCodigoorcamento(), false)){
                    return false;
                }
                
                dPreVenda.setCodigo(DAO_RepositorioLocal.NovoValorIdentidade("dadosorc", Loja, PDV));
                dPreVenda.setNaosinc(false);                        
                dPreVenda.setLoja(Loja);
                dPreVenda.setFilial(Filial);
                dPreVenda.setMesclagem_nota(CodigoPreVenda);
                dPreVenda.setUltimamodificacao(new Timestamp(new GregorianCalendar().getTime().getTime()));
                Long Ret = DadosorcRN.Dadosorc_Incluir(DAO_RepositorioLocal.getCnRepLocal(),dPreVenda,0,true,true);                        
                if(Ret !=0l){
                    bRet=true;
                }             
                
    } catch (Exception e) {
        LogDinnamus.Log(e, true);

    }
    return bRet;
}
    
    private void Bloquear_Desbloquear_TelaProdutos(boolean status){
        try {
          txtProcurar.setEnabled(status);
          MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelBotoes, status);
         // tpPrincipal.setEnabled(status);
          setTeclasFuncao(status);
          
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
   // public boolean PrepararFechamento(){
        //return PrepararFechamento(0);
    //}
    public boolean PrepararFechamento(int MomentoVendaInterrompida ){
        boolean Ret = false;
        try {
              
             InterromperTarefaSincronismo=true;
             getBloq().Tela_Bloquear(this, 1f, Color.BLACK);
             Ret = PrepararFechamento_Acao(MomentoVendaInterrompida);           
             InterromperTarefaSincronismo=false;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }finally{
             getBloq().Tela_DesBloquear();
        }
        return Ret;
             
    }
    public boolean PrepararFechamento_Acao(int MomentoVendaInterrompida ){
            try {
            
                if(!DefinirVendedor()){
                    return false;
                }
                //this.repaint();
                frmPDV_SimplesFechamento fechamento = new frmPDV_SimplesFechamento(null, true);
                fechamento.Pagto_dadosorc  = getDadosorc();
                fechamento.Pagto_Itensorc  = getArItensorc();
                fechamento.Pagto_ecf = getEcfDinnmus();
                fechamento.Pagto_TEF_Ativo = TEF_Ativo;     
                fechamento.Pagto_ControleCX = getControleCX();
                fechamento.Pagto_TipoComprovante =TipoComprovante();
                fechamento.Pagto_ImprimirComprovante=btImprimirComprovante.isSelected();
                fechamento.Pagto_MomentoDaVendaTEFInterrompida_PDV = MomentoDaVendaTEFInterrompida_PDV;
                fechamento.Pagto_ECFDisponivel = isECFDisponivel();
                fechamento.Pagto_nCodigoOperadorCX=nCodigoOperadorCX;
                fechamento.Pagto_nCodigoObjetoCaixa=nCodigoObjetoCaixa;
                fechamento.Pagto_nCodigoFilial=nCodigoFilial;  
                fechamento.Pagto_Recebimento_Crediario= isModoRecebimento();
                fechamento.Pagto_ImpressoraComprovantes = getNomeImpressoraComprovante();
                fechamento.Pagto_NFCe_OK = isNFCe_OK();
                fechamento.ModosTrabalhoDisponiveis = ModosTrabalhoDisponiveis;
                fechamento.Pagto_ViasCompVDaCrediario = Pagto_ViasCompVDaCrediario;
                //fechamento.Pagto_ComprovanteIReport = Pagto_ComprovanteIReport;
                fechamento.jasperNFce   = jasperNFce;
                        
                //fechamento.Pagto_PreVendaPendenciasParaECF = (PreVendaPendenciasParaECF == null ? false : true);
                if(fechamento.IniciarUI(MomentoVendaInterrompida)){               
                    fechamento.setVisible(true);
                    Long CodigoVenda = getDadosorc().getCodigo();
                    Long CodigoTroca = fechamento.Pagto_CodigoTrocaVenda;
                    if(fechamento.Pagto_OK){                          
                        TerminarVenda(fechamento.Pagto_Troco);                        
                    }else{
                        if(CodigoVenda!=null){
                            boolean Efetivada = DAO_RepositorioLocal.GerarResultSet("select 1 from dadosorc where recebido='S' and codigo=" + CodigoVenda).next();
                            if(Efetivada){
                               ApagarNota(false);
                               SwingUtilidade.RequestFocus(txtProcurar);   
                            }
                        }else{
                             ApagarNota(false);
                             SwingUtilidade.RequestFocus(txtProcurar);
                        }
                    }
                   DefinirModoTrabalhoNFCe();
                }
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
}