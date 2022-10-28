/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.Venda;

import br.TratamentoNulo.TratamentoNulos;
import br.arredondar.NumeroArredondar;
import br.com.FormatarNumeros;
import br.com.ecf.ECFDinnamuS;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.BloquearTela;
import br.com.ui.InteracaoDuranteProcessamento;
import br.com.ui.ItemLista;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.data.ManipularData;
import br.tef.Padrao.TefPadrao;
import br.transformacao.TransformacaoDados;
import br.ui.teclas.DefinirAtalhos;
import br.ui.teclas.controleteclas;
import br.valor.formatar.FormatarNumero;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensorc;
import MetodosDeNegocio.Sincronismo.SincronizarMovimento;
import MetodosDeNegocio.Sincronismo.VerificarStatusServidor;
import MetodosDeNegocio.Venda.PreVenda;
import MetodosDeNegocio.Seguranca.UsuarioAuditar;
import MetodosDeNegocio.Seguranca.UsuarioPermissoes;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.GerenciarCaixa;
import dinnamus.metodosnegocio.venda.caixa.PDVComprovante;
import MetodosDeNegocio.Crediario.Crediario;
import MetodosDeNegocio.Crediario.entidade.BaixarConta;
import MetodosDeNegocio.Fachada.clientes;
import MetodosDeNegocio.Venda.ItensorcRN;
import MetodosDeNegocio.Venda.PreVendasSelecionadas;
import MetodosDeNegocio.Venda.PagtoorcRN;
import MetodosDeNegocio.Venda.ParametrosGlobais;
import MetodosDeNegocio.Venda.Troca;
import MetodosDeNegocio.Venda.Venda;
import MetodosDeNegocio.Venda.VendaEmEdicao;
import MetodosDeNegocio.Venda.pdvgerenciar;
import MetodosDeNegocio.TEF.TEFVenda;
import MetodosDeNegocio.Venda.ImpostoNaNota;
import UI.Seguranca.IdentificarUsuario;
import UI.Seguranca.ValidarAcessoAoProcesso;
import br.String.ManipulacaoString;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.servidor.Dao_Jdbc_1;
import br.com.ui.SwingUtilidade;
import com.fincatto.nfe310.classes.NFProtocolo;
import com.fincatto.nfe310.classes.lote.envio.NFLoteEnvio;
import com.fincatto.nfe310.classes.nota.consulta.NFNotaConsultaRetorno;
import com.fincatto.nfe310.parsers.NotaParser;
import com.nfce.cancelamento.NFCE_DesfazerNFCE;
import com.nfce.config.NFCE_Configurar;
import com.nfce.config.NFCE_Contingencia;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.text.DefaultFormatterFactory;
import com.nfce.envio.EnviarNFCe;
import com.nfce.envio.NFCe_ConfiguracaoAmbiente;
import dinnamus.rel.RelatorioJasperXML;
import dinnamus.ui.InteracaoUsuario.nfce.frmEnviarNFCE_PDV;
import java.awt.Color;
import java.awt.Point;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Fernando
 */
public class frmPDV_SimplesFechamento extends javax.swing.JDialog {

    /**
     * Creates new form frmPDV_SimplesFechamento
     */
    public boolean Pagto_OK =false;
    public Dadosorc Pagto_dadosorc  = null;
    public ArrayList<Itensorc> Pagto_Itensorc  = null;
    public ECFDinnamuS Pagto_ecf = null;
    public boolean Pagto_TEF_Ativo = false;
    public Double Pagto_Troco = 0d;
    public String Pagto_TipoComprovante ="";
    public boolean Pagto_ImprimirComprovante=false;
    public int Pagto_MomentoDaVendaTEFInterrompida_PDV = 0;
    public boolean Pagto_ECFDisponivel = false;
    public int Pagto_nCodigoOperadorCX=0;
    public int Pagto_nCodigoObjetoCaixa=0;
    public int Pagto_nCodigoFilial=0;
    public String Pagto_ControleCX = "";
    public Long Pagto_CodigoTrocaVenda = 0l;
    public boolean Pagto_PreVendaPendenciasParaECF = false;
    public boolean Pagto_Recebimento_Crediario = false;
    public String Pagto_ImpressoraComprovantes ="";
    public boolean Pagto_NFCe_OK=false;
    public Integer Pagto_ViasCompVDaCrediario=0;
    public ArrayList<String> ModosTrabalhoDisponiveis = null;
   // public boolean Pagto_ComprovanteIReport;
    public  RelatorioJasperXML jasperNFce;
    
    private boolean NFCeEmFechamento = false;
    private boolean InterrompeLeituraTecla = false;
    //private frmPDV_SimplesDesconto frmDesconto = null;
    private frmPDV_SimplesSelecionarTroca frmTroca = null ; 
    private static ResultSet rsDescontoAtacado = null;
    private boolean IniciarUI=false;
    private BloquearTela bloq = new BloquearTela(); 
    private Long CodigoTroca=0l;
    private Double CreditoTroca =0d;    
    private Long CodigoPreVenda = 0l;
    private boolean Mesclagem=false;
    public frmPDV_SimplesFechamento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Pagto_OK=false;        
        setLocationRelativeTo(null);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        PainelFormasPagtoVenda = new javax.swing.JPanel();
        PainelTopo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btFechar5 = new javax.swing.JButton();
        PainelFormasPagtoVenda1 = new javax.swing.JPanel();
        PainelBotoes = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        dbgFormasPagto = new br.com.ui.JTableDinnamuS();
        txtValorFaltaReceber = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        btRemoverForma = new javax.swing.JButton();
        btEditarForma = new javax.swing.JButton();
        btListaForma = new javax.swing.JButton();
        PainelRodapePagto = new javax.swing.JPanel();
        btGravarVenda = new javax.swing.JButton();
        PainelCorpoPagto = new javax.swing.JPanel();
        lblDesconto = new javax.swing.JLabel();
        txtValorVenda = new javax.swing.JFormattedTextField();
        txtDescEAcrescVal = new javax.swing.JFormattedTextField();
        lblValorFinal = new javax.swing.JFormattedTextField();
        lblSubTitulo = new javax.swing.JLabel();
        lblTituloCampoValorFinal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblSinalMenosDesconto = new javax.swing.JLabel();
        lblSinalMenosAcrescimo = new javax.swing.JLabel();
        lblSinalIgual = new javax.swing.JLabel();
        PainelDescontos = new javax.swing.JPanel();
        txtDescontoAvulsoVLR = new javax.swing.JFormattedTextField();
        txtDescontoAvulsoPerc = new javax.swing.JFormattedTextField();
        txtDescontoAtacado = new javax.swing.JFormattedTextField();
        lblDescontoNormal = new javax.swing.JLabel();
        lblDescontoAtacado = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDescontoPreVenda = new javax.swing.JFormattedTextField();
        lblDEscontoPreVenda = new javax.swing.JLabel();
        PainelAcrescimo = new javax.swing.JPanel();
        txtAcrescimoAvulsoVLR = new javax.swing.JFormattedTextField();
        lblAcrescimoAvulso = new javax.swing.JLabel();
        txtAcrescimoPagto = new javax.swing.JFormattedTextField();
        lblAcrescimoPagto = new javax.swing.JLabel();
        txtAcrescimoAvulsoPerc = new javax.swing.JFormattedTextField();
        lblAcrescimoAvulso1 = new javax.swing.JLabel();
        txtAcrescimo = new javax.swing.JFormattedTextField();
        lblAcrescimoTotal = new javax.swing.JLabel();
        PainelTroca = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtValorCreditoTroca = new javax.swing.JFormattedTextField();
        lblTroca = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(530, 570));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelFormasPagtoVenda.setBackground(new java.awt.Color(255, 255, 255));
        PainelFormasPagtoVenda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelFormasPagtoVenda.setMinimumSize(new java.awt.Dimension(530, 570));
        PainelFormasPagtoVenda.setPreferredSize(new java.awt.Dimension(530, 570));
        PainelFormasPagtoVenda.setLayout(new java.awt.GridBagLayout());

        PainelTopo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTopo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setBackground(new java.awt.Color(0, 0, 0));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO VERDE.png"))); // NOI18N
        lblTitulo.setText("FECHAMENTO DA VENDA");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelTopo.add(lblTitulo, gridBagConstraints);

        btFechar5.setBackground(new java.awt.Color(0, 0, 0));
        btFechar5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btFechar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btFechar5.setMnemonic('x');
        btFechar5.setToolTipText("");
        btFechar5.setBorderPainted(false);
        btFechar5.setOpaque(false);
        btFechar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFechar5ActionPerformed(evt);
            }
        });
        btFechar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btFechar5KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelTopo.add(btFechar5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        PainelFormasPagtoVenda.add(PainelTopo, gridBagConstraints);

        PainelFormasPagtoVenda1.setBackground(new java.awt.Color(255, 255, 255));
        PainelFormasPagtoVenda1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelFormasPagtoVenda1.setLayout(new java.awt.GridBagLayout());

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("PAGAMENTO REGISTRADO  - [F8]");
        jLabel12.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        PainelBotoes.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        PainelFormasPagtoVenda1.add(PainelBotoes, gridBagConstraints);

        dbgFormasPagto.setBackground(new java.awt.Color(255, 255, 255));
        dbgFormasPagto.setExibirBarra(false);
        dbgFormasPagto.setPreferredSize(new java.awt.Dimension(700, 251));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        PainelFormasPagtoVenda1.add(dbgFormasPagto, gridBagConstraints);

        txtValorFaltaReceber.setEditable(false);
        txtValorFaltaReceber.setBackground(new java.awt.Color(255, 255, 255));
        txtValorFaltaReceber.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtValorFaltaReceber.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValorFaltaReceber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorFaltaReceber.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        PainelFormasPagtoVenda1.add(txtValorFaltaReceber, gridBagConstraints);

        jLabel14.setBackground(new java.awt.Color(255, 255, 204));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText(" FALTA RECEBER  ");
        jLabel14.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelFormasPagtoVenda1.add(jLabel14, gridBagConstraints);

        btRemoverForma.setBackground(new java.awt.Color(0, 0, 0));
        btRemoverForma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btRemoverForma.setForeground(new java.awt.Color(255, 255, 255));
        btRemoverForma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money_delete.png"))); // NOI18N
        btRemoverForma.setMnemonic('r');
        btRemoverForma.setText("[DEL] REMOVER PAGTO");
        btRemoverForma.setBorderPainted(false);
        btRemoverForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverFormaActionPerformed(evt);
            }
        });
        btRemoverForma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btRemoverFormaKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelFormasPagtoVenda1.add(btRemoverForma, gridBagConstraints);

        btEditarForma.setBackground(new java.awt.Color(0, 0, 0));
        btEditarForma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btEditarForma.setForeground(new java.awt.Color(255, 255, 255));
        btEditarForma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/calculator_edit.png"))); // NOI18N
        btEditarForma.setMnemonic('e');
        btEditarForma.setText("[F2] EDITAR PAGTO");
        btEditarForma.setBorderPainted(false);
        btEditarForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarFormaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelFormasPagtoVenda1.add(btEditarForma, gridBagConstraints);

        btListaForma.setBackground(new java.awt.Color(0, 0, 0));
        btListaForma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btListaForma.setForeground(new java.awt.Color(255, 255, 255));
        btListaForma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money_add.png"))); // NOI18N
        btListaForma.setMnemonic('r');
        btListaForma.setText("[F7] PAGAR");
        btListaForma.setBorderPainted(false);
        btListaForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btListaFormaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelFormasPagtoVenda1.add(btListaForma, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        PainelFormasPagtoVenda.add(PainelFormasPagtoVenda1, gridBagConstraints);

        PainelRodapePagto.setBackground(new java.awt.Color(255, 255, 204));
        PainelRodapePagto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelRodapePagto.setPreferredSize(new java.awt.Dimension(470, 520));
        PainelRodapePagto.setLayout(new java.awt.GridBagLayout());

        btGravarVenda.setBackground(new java.awt.Color(0, 0, 0));
        btGravarVenda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btGravarVenda.setForeground(new java.awt.Color(255, 255, 255));
        btGravarVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/Venda/money.png"))); // NOI18N
        btGravarVenda.setText("  GRAVAR - [F3] ");
        btGravarVenda.setBorderPainted(false);
        btGravarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGravarVendaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 11, 11);
        PainelRodapePagto.add(btGravarVenda, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelFormasPagtoVenda.add(PainelRodapePagto, gridBagConstraints);

        PainelCorpoPagto.setBackground(new java.awt.Color(255, 255, 255));
        PainelCorpoPagto.setLayout(new java.awt.GridBagLayout());

        lblDesconto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDesconto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDesconto.setText("  DESCONTO [F6]  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(lblDesconto, gridBagConstraints);

        txtValorVenda.setEditable(false);
        txtValorVenda.setBackground(new java.awt.Color(255, 255, 255));
        txtValorVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtValorVenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtValorVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorVenda.setToolTipText("");
        txtValorVenda.setCaretColor(new java.awt.Color(255, 255, 255));
        txtValorVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValorVenda.setEnabled(false);
        txtValorVenda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtValorVenda.setValue(new Float(0f));
        txtValorVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorVendaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(txtValorVenda, gridBagConstraints);

        txtDescEAcrescVal.setEditable(false);
        txtDescEAcrescVal.setBackground(new java.awt.Color(255, 255, 255));
        txtDescEAcrescVal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescEAcrescVal.setForeground(new java.awt.Color(255, 0, 0));
        txtDescEAcrescVal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtDescEAcrescVal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescEAcrescVal.setText("0,00");
        txtDescEAcrescVal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescEAcrescVal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDescEAcrescVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescEAcrescValActionPerformed(evt);
            }
        });
        txtDescEAcrescVal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDescEAcrescValPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(txtDescEAcrescVal, gridBagConstraints);

        lblValorFinal.setEditable(false);
        lblValorFinal.setBackground(new java.awt.Color(255, 255, 255));
        lblValorFinal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblValorFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###0.00"))));
        lblValorFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblValorFinal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lblValorFinal.setEnabled(false);
        lblValorFinal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblValorFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblValorFinalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(lblValorFinal, gridBagConstraints);

        lblSubTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblSubTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSubTitulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSubTitulo.setText("  VLR DA VENDA  ");
        lblSubTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(lblSubTitulo, gridBagConstraints);

        lblTituloCampoValorFinal.setBackground(new java.awt.Color(255, 255, 204));
        lblTituloCampoValorFinal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTituloCampoValorFinal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloCampoValorFinal.setText("  VALOR FINAL  ");
        lblTituloCampoValorFinal.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(lblTituloCampoValorFinal, gridBagConstraints);

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(" (=)");
        jLabel1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelCorpoPagto.add(jLabel1, gridBagConstraints);

        lblSinalMenosDesconto.setText(" ( - )");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        PainelCorpoPagto.add(lblSinalMenosDesconto, gridBagConstraints);

        lblSinalMenosAcrescimo.setBackground(new java.awt.Color(255, 255, 204));
        lblSinalMenosAcrescimo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSinalMenosAcrescimo.setText(" (+)");
        lblSinalMenosAcrescimo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelCorpoPagto.add(lblSinalMenosAcrescimo, gridBagConstraints);

        lblSinalIgual.setBackground(new java.awt.Color(255, 255, 204));
        lblSinalIgual.setText(" (=)");
        lblSinalIgual.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelCorpoPagto.add(lblSinalIgual, gridBagConstraints);

        PainelDescontos.setOpaque(false);
        PainelDescontos.setLayout(new java.awt.GridBagLayout());

        txtDescontoAvulsoVLR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescontoAvulsoVLR.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtDescontoAvulsoVLR.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescontoAvulsoVLR.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescontoAvulsoVLR.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDescontoAvulsoVLR.setValue(0f);
        txtDescontoAvulsoVLR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoAvulsoVLRActionPerformed(evt);
            }
        });
        txtDescontoAvulsoVLR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoAvulsoVLRFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoAvulsoVLRFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelDescontos.add(txtDescontoAvulsoVLR, gridBagConstraints);

        txtDescontoAvulsoPerc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescontoAvulsoPerc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtDescontoAvulsoPerc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescontoAvulsoPerc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescontoAvulsoPerc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDescontoAvulsoPerc.setValue(0f);
        txtDescontoAvulsoPerc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoAvulsoPercActionPerformed(evt);
            }
        });
        txtDescontoAvulsoPerc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoAvulsoPercFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoAvulsoPercFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelDescontos.add(txtDescontoAvulsoPerc, gridBagConstraints);

        txtDescontoAtacado.setEditable(false);
        txtDescontoAtacado.setBackground(new java.awt.Color(234, 232, 232));
        txtDescontoAtacado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescontoAtacado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtDescontoAtacado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescontoAtacado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescontoAtacado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDescontoAtacado.setValue(0f);
        txtDescontoAtacado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoAtacadoActionPerformed(evt);
            }
        });
        txtDescontoAtacado.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDescontoAtacadoPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelDescontos.add(txtDescontoAtacado, gridBagConstraints);

        lblDescontoNormal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDescontoNormal.setText("      DESCONTO NORMAL ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        PainelDescontos.add(lblDescontoNormal, gridBagConstraints);

        lblDescontoAtacado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDescontoAtacado.setText("      DESCONTO ATACADO ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        PainelDescontos.add(lblDescontoAtacado, gridBagConstraints);

        jLabel10.setText(" % ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        PainelDescontos.add(jLabel10, gridBagConstraints);

        txtDescontoPreVenda.setEditable(false);
        txtDescontoPreVenda.setBackground(new java.awt.Color(234, 232, 232));
        txtDescontoPreVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDescontoPreVenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtDescontoPreVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescontoPreVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescontoPreVenda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDescontoPreVenda.setValue(0f);
        txtDescontoPreVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoPreVendaActionPerformed(evt);
            }
        });
        txtDescontoPreVenda.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDescontoPreVendaPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelDescontos.add(txtDescontoPreVenda, gridBagConstraints);

        lblDEscontoPreVenda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDEscontoPreVenda.setText("      DESCONTO PRÉ-VENDA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        PainelDescontos.add(lblDEscontoPreVenda, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelCorpoPagto.add(PainelDescontos, gridBagConstraints);

        PainelAcrescimo.setBackground(new java.awt.Color(255, 255, 204));
        PainelAcrescimo.setLayout(new java.awt.GridBagLayout());

        txtAcrescimoAvulsoVLR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAcrescimoAvulsoVLR.setForeground(new java.awt.Color(0, 0, 255));
        txtAcrescimoAvulsoVLR.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtAcrescimoAvulsoVLR.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcrescimoAvulsoVLR.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAcrescimoAvulsoVLR.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtAcrescimoAvulsoVLR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAcrescimoAvulsoVLRActionPerformed(evt);
            }
        });
        txtAcrescimoAvulsoVLR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAcrescimoAvulsoVLRFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAcrescimoAvulsoVLRFocusLost(evt);
            }
        });
        txtAcrescimoAvulsoVLR.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAcrescimoAvulsoVLRPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelAcrescimo.add(txtAcrescimoAvulsoVLR, gridBagConstraints);

        lblAcrescimoAvulso.setBackground(new java.awt.Color(255, 255, 204));
        lblAcrescimoAvulso.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAcrescimoAvulso.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAcrescimoAvulso.setText("%");
        lblAcrescimoAvulso.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelAcrescimo.add(lblAcrescimoAvulso, gridBagConstraints);

        txtAcrescimoPagto.setEditable(false);
        txtAcrescimoPagto.setBackground(new java.awt.Color(234, 232, 232));
        txtAcrescimoPagto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAcrescimoPagto.setForeground(new java.awt.Color(0, 0, 255));
        txtAcrescimoPagto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtAcrescimoPagto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcrescimoPagto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAcrescimoPagto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtAcrescimoPagto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAcrescimoPagtoActionPerformed(evt);
            }
        });
        txtAcrescimoPagto.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAcrescimoPagtoPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelAcrescimo.add(txtAcrescimoPagto, gridBagConstraints);

        lblAcrescimoPagto.setBackground(new java.awt.Color(255, 255, 204));
        lblAcrescimoPagto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAcrescimoPagto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAcrescimoPagto.setText("      ACRÉSCIMO PAGTO");
        lblAcrescimoPagto.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.2;
        PainelAcrescimo.add(lblAcrescimoPagto, gridBagConstraints);

        txtAcrescimoAvulsoPerc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAcrescimoAvulsoPerc.setForeground(new java.awt.Color(0, 0, 255));
        txtAcrescimoAvulsoPerc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtAcrescimoAvulsoPerc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcrescimoAvulsoPerc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAcrescimoAvulsoPerc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtAcrescimoAvulsoPerc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAcrescimoAvulsoPercActionPerformed(evt);
            }
        });
        txtAcrescimoAvulsoPerc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAcrescimoAvulsoPercFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAcrescimoAvulsoPercFocusLost(evt);
            }
        });
        txtAcrescimoAvulsoPerc.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAcrescimoAvulsoPercPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelAcrescimo.add(txtAcrescimoAvulsoPerc, gridBagConstraints);

        lblAcrescimoAvulso1.setBackground(new java.awt.Color(255, 255, 204));
        lblAcrescimoAvulso1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAcrescimoAvulso1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAcrescimoAvulso1.setText("      ACRÉSCIMO AVULSO");
        lblAcrescimoAvulso1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.2;
        PainelAcrescimo.add(lblAcrescimoAvulso1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelCorpoPagto.add(PainelAcrescimo, gridBagConstraints);

        txtAcrescimo.setEditable(false);
        txtAcrescimo.setBackground(new java.awt.Color(255, 255, 255));
        txtAcrescimo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtAcrescimo.setForeground(new java.awt.Color(0, 0, 255));
        txtAcrescimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtAcrescimo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcrescimo.setText("0,00");
        txtAcrescimo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtAcrescimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtAcrescimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAcrescimoActionPerformed(evt);
            }
        });
        txtAcrescimo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAcrescimoPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(txtAcrescimo, gridBagConstraints);

        lblAcrescimoTotal.setBackground(new java.awt.Color(255, 255, 204));
        lblAcrescimoTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAcrescimoTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAcrescimoTotal.setText("  ACRÉSCIMO [F5]");
        lblAcrescimoTotal.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpoPagto.add(lblAcrescimoTotal, gridBagConstraints);

        PainelTroca.setOpaque(false);
        PainelTroca.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText(" ( - )");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        PainelTroca.add(jLabel5, gridBagConstraints);

        txtValorCreditoTroca.setEditable(false);
        txtValorCreditoTroca.setBackground(new java.awt.Color(255, 255, 255));
        txtValorCreditoTroca.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtValorCreditoTroca.setForeground(new java.awt.Color(255, 0, 0));
        txtValorCreditoTroca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValorCreditoTroca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorCreditoTroca.setText("0,00");
        txtValorCreditoTroca.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelTroca.add(txtValorCreditoTroca, gridBagConstraints);

        lblTroca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTroca.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTroca.setText("  TROCA [F4]      ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelTroca.add(lblTroca, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelCorpoPagto.add(PainelTroca, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelFormasPagtoVenda.add(PainelCorpoPagto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        getContentPane().add(PainelFormasPagtoVenda, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public boolean IniciarUI(int MomentoVendaInterrompida){
        IniciarUI=IniciarUI_Acao(MomentoVendaInterrompida);
        return IniciarUI;
    }
    public boolean IniciarUI_Acao(int MomentoVendaInterrompida){
        try {

            PainelDescontos.setVisible(false);
            PainelAcrescimo.setVisible(false);
            
            setPreVenda(false);
            
            controleteclas.UsarTeclaNaTrocaDeCampos(PainelFormasPagtoVenda, KeyEvent.VK_ENTER);
            //controleteclas.
            //PermitirFechamentoF3=false;
            if(!TeclasAtalhos_UI()){ return false;}

            DefinirTipoComprovante(Pagto_TipoComprovante);
            boolean Ret=false;
            if(Pagto_Recebimento_Crediario){
               Ret = PrepararFechamentoCrediario(MomentoVendaInterrompida, ParametrosGlobais.getBaixarConta().getDuplicatas_A_Receber());
            }else{
               Ret  =PrepararFechamento(MomentoVendaInterrompida);
            }
            
            
            return Ret;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void btFechar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar5ActionPerformed
        try {
            if(PreparaParaSairDoFechamento()){
                Pagto_OK=false;
                dispose();
            }                  
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btFechar5ActionPerformed

    private void btFechar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFechar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btFechar5KeyPressed

    private void btRemoverFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverFormaActionPerformed
        // TODO add your handling code here:
        try {
            if(dbgFormasPagto.getjTable().getRowCount()==0){
               return; 
            }
            Float Valor=0f;
            String FormaPagto = "";
            Long nIdUnico=0l;
            int LinhaAtual = dbgFormasPagto.getjTable().getSelectedRow();
            if(LinhaAtual<0){
               LinhaAtual=0; 
            }
            nIdUnico = dbgFormasPagto.getTbDinnamuS().getValorCelulaLong("idunico", LinhaAtual);
            FormaPagto =dbgFormasPagto.getTbDinnamuS().getValorCelulaString("grupoforma", LinhaAtual);
            Valor =dbgFormasPagto.getTbDinnamuS().getValorCelulaFloat("valor", LinhaAtual);            
            if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "CONFIRMA A EXCLUSÃO DA FORMA DE PAGAMENTO?\n\n" + FormaPagto + " R$ "+ FormatarNumeros.FormatarParaMoeda(Valor) ,  "EXCLUSÃO DO PAGTO")!=MetodosUI_Auxiliares_1.Sim()){
               return ;
            }                 
            if(Venda.VendaEmFechamento()){
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "A VENDA ESTÁ COM O CUPOM FISCAL FECHADO. FAVOR ENCERRAR A NOTA", "EXCLUSÃO NÃO PERMITIDA");
                 return ;
            }
            
            
            if(btGravarVenda.isEnabled()){
                final Long IDUnico_Final = nIdUnico;
                new Thread("frmPDV_SimplesFechamento_btRemoverFormaActionPerformed"){
                    @Override
                    public void run(){
                       RemoverPagto(IDUnico_Final);
                       if(Pagto_TipoComprovante.equalsIgnoreCase("nfce")){
                          VendaEmEdicao.RegistrarVendaEmEdicao_NFCe(Pagto_dadosorc.getCodigo(), "");
                       }
                       Bloquear_Liberar_CamposEditaveis(VerificarPossibilidadeEdicaoCampos());                    //}
                    }
                }.start();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }//GEN-LAST:event_btRemoverFormaActionPerformed
    private boolean RemoverPagto(Long nIdUnico){
        try {
                boolean bPermiteCancelar =true;
                ResultSet rsPagtoOrc = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo(), nIdUnico);
                if(rsPagtoOrc.next()){
                    String TEFStatus = TratamentoNulos.getTratarString().Tratar(rsPagtoOrc.getString("tefstatus"),"");
                    if(!TEFStatus.equalsIgnoreCase("[ F9 ]- REPETIR OPERAÇÃO") && !TEFStatus.equalsIgnoreCase("") ){
                        bPermiteCancelar =false;
                        int nRetorno= MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "A Forma de Pagamento já esta Aprovada pela Administradora\n\nCONFIRMA ESTA OPERAÇÃO ?", "Exclusão da Forma Pagto");
                        if(nRetorno==MetodosUI_Auxiliares_1.Sim()){
                            int nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "ExcluirCotacao", Sistema.getLojaAtual(), true, "Exclusão de Forma Pagto");
                            if(nCodigoUsuario>0){
                               bPermiteCancelar = TEFVenda.DesfazerVendaTEF(nCodigoUsuario,nIdUnico,rsPagtoOrc.getString("tefcomandoatual"),getDadosorc().getCodigo(),getEcf());
                            }else{
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "OPERADOR NÃO AUTORIZADO", "Exclusão de Forma Pagto");
                            }
                        }
                    }
                }
                if(!bPermiteCancelar){
                    return false;
                }
                if(!PagtoorcRN.PagtoOrc_Excluir(getDadosorc().getCodigo(),nIdUnico,true)){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível excluir a forma de pagto", "Exclusão de Forma Pagto", "AVISO");
                }else{
                    IniciarGridPagtos_Atualizar();
                }               
            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return false;
    }
    public void EditarPagto(){
            try {
            int nPosGridFormasPagto = dbgFormasPagto.LinhaAtualModel();
            //int nPosGridOpcoesPagto = dbgOpcoesPagto.LinhaAtualModel();
            
            int LinhaAtual = dbgFormasPagto.getjTable().getSelectedRow();
            if(LinhaAtual<0){
               LinhaAtual=0; 
            }            
                
            Long nIdUnico = dbgFormasPagto.getTbDinnamuS().getValorCelulaLong("idunico", LinhaAtual);
            ResultSet rsForma = PagtoorcRN.PagtoOrc_Listar( getDadosorc().getCodigo(), nIdUnico);
            if(rsForma.next()){
                String Destino = rsForma.getString("destino");
                Float Valor=rsForma.getFloat("valor");
                if(Destino.equalsIgnoreCase("A Receber & Crediario")){
                    String IdUnicoPagto=rsForma.getString("idunico"),  GrupoForma=rsForma.getString("grupoforma");
                    FormEdicaoParcelas(IdUnicoPagto, GrupoForma, Valor);
                }else  if(Destino.equalsIgnoreCase("Cheques Recebidos")){
                    String Controle = rsForma.getString("controle");
                    CadastroDeCheques(getDadosorc(),Valor,Controle);
                }else{
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ESTE PAGAMENTO NÃO PERMITE EDIÇÃO", "EDITAR PAGTO");
                }
            }
            IniciarGridPagtos_Atualizar();
            dbgFormasPagto.getjTable().setRowSelectionInterval(nPosGridFormasPagto, nPosGridFormasPagto);
            
        } catch (Exception e) {
                LogDinnamus.Log(e, true);                
        }
    }
    private void btEditarFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarFormaActionPerformed
        // TODO add your handling code here:
        try {
            if(dbgFormasPagto.getjTable().getRowCount()==0){
               return; 
            }
            bloq.Tela_Bloquear(this, 0.5f, Color.BLACK);
            EditarPagto();
            bloq.Tela_DesBloquear();
            
        } catch (Exception e) {
                LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btEditarFormaActionPerformed
    private boolean CadastroDeCheques(Dadosorc d, Float nValor,String Controle){
        boolean bRet=false;
        //String cContole="";
        try {
            
            new frmCadCheques(null,true,d,Controle,pdvgerenciar.CodigoPDV(),nValor );

            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private boolean FormEdicaoParcelas(String IdUnicoPagto, String GrupoForma, Float Valor){
        try {
            frmPDV_SimplesFPagtoParcelas frmParcelasFormas = new frmPDV_SimplesFPagtoParcelas(null, true);
            frmParcelasFormas.setDadosorc(getDadosorc());
            frmParcelasFormas.setControleFormaPagto(IdUnicoPagto);
            frmParcelasFormas.setDescricaoFormaPagto(GrupoForma);
            frmParcelasFormas.setValorForma(Valor);
            frmParcelasFormas.setVisible(true);
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void txtDescEAcrescValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescEAcrescValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescEAcrescValActionPerformed

    private void txtDescEAcrescValPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDescEAcrescValPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescEAcrescValPropertyChange

    private void lblValorFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblValorFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblValorFinalActionPerformed

    private void txtAcrescimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAcrescimoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoActionPerformed

    private void txtAcrescimoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAcrescimoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoPropertyChange

    private void btGravarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGravarVendaActionPerformed
        // TODO add your handling code here:
        System.out.println("EXECUTANDO EVENTO : btGravarVendaActionPerformed " + DataHora.getDataHoraAtual());
        final int momentovenda = this.Pagto_MomentoDaVendaTEFInterrompida_PDV;
        //btListaForma.setEnabled(false);                          
        btEfetivar(momentovenda);                                               //}
                    
        
    }//GEN-LAST:event_btGravarVendaActionPerformed

    private void txtValorVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorVendaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        try {
            if(!IniciarUI){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL INICIAR O FECHAMENTO DA VENDA", lblTitulo.getText());
                dispose();
            }else{
                if(ValorFaltaDistribuir()==0f){
                    if(Venda.PDV_TEF_Habilitado()){
                        if(!TEF_VendaNoCartao_NaoAutorizada()){
                           btGravarVenda.requestFocus();
                        }
                    }
               } 
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
       
    }//GEN-LAST:event_formWindowOpened
     private boolean Bloq_DesBloq_TelaNFCeEnviada(boolean Status){
        try {            
            MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelFormasPagtoVenda, Status);
            //MetodosUI_Auxiliares.BloquearDesbloquearComponentes(PainelRodapePagto, Status);
            //MetodosUI_Auxiliares.BloquearDesbloquearComponentes(PainelCorpoPagto, Status);
            dbgFormasPagto.getjTable().setEnabled(Status);
            //InterrompeLeituraTecla = !Status;
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean Bloq_DesBloq_TelaDuranteProcessamento(boolean Status){
        try {            
            MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelFormasPagtoVenda, Status);
            //MetodosUI_Auxiliares.BloquearDesbloquearComponentes(PainelRodapePagto, Status);
            //MetodosUI_Auxiliares.BloquearDesbloquearComponentes(PainelCorpoPagto, Status);
            dbgFormasPagto.getjTable().setEnabled(Status);
            InterrompeLeituraTecla = !Status;
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void btListaFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btListaFormaActionPerformed
        // TODO add your handling cCode here:
        if(btListaForma.isEnabled()){
            Double ValorFaltaReceber =ValorFaltaDistribuir();

            if(ValorFaltaReceber.floatValue()<=0d){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL ABRIR AS OPÇÕES DE PAGTO COM O SALDO A RECEBER MENOR OU IGUAR A 0(ZERO)", lblTitulo.getText());
               return ;
            }
            //MetodosUI_Auxiliares.BloquearDesbloquearComponentes(this, Pagto_OK);
            //bloq.Tela_Bloquear(this, 0.5f);  
            if(Pagto_TipoComprovante.equalsIgnoreCase("fiscal")){
                if(CodigoPreVenda>0l){
                  boolean RetPendencias =  PreVenda.ExibirPendenciasEmissaoDocFiscal( PreVenda.VerificarPendenciasParaCupomFiscal(CodigoPreVenda, Sistema.getLojaAtual()));
                  if(!RetPendencias){
                      return;
                  }
                }
            }
            Bloq_DesBloq_TelaDuranteProcessamento(false);
            btListaForma.setEnabled(false);            
            new Thread("frmPDV_SimplesFechamento_btListaFormaActionPerformed") {
                public void run(){                    
                    ProcessarFormaDePagamento();                                                            //}
                    Bloq_DesBloq_TelaDuranteProcessamento(true);
                    Bloquear_Liberar_CamposEditaveis(VerificarPossibilidadeEdicaoCampos());
                    btListaForma.setEnabled(true);
                    SwingUtilidade.RequestFocus(btListaForma);
                }
            }.start();
        }
        
        
    }//GEN-LAST:event_btListaFormaActionPerformed

    private void btRemoverFormaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btRemoverFormaKeyPressed
        // TODO add your handling code here:
        
            //if(evt.getKeyCode()==KeyEvent.VK_ENTER){
               if(!btGravarVenda.isEnabled()){ 
                  evt.consume();
                }
            //}
    }//GEN-LAST:event_btRemoverFormaKeyPressed

    private void txtDescontoAvulsoVLRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoAvulsoVLRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoAvulsoVLRActionPerformed

    private void txtDescontoAvulsoPercActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoAvulsoPercActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoAvulsoPercActionPerformed

    private void txtDescontoAtacadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoAtacadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoAtacadoActionPerformed

    private void txtDescontoAtacadoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDescontoAtacadoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoAtacadoPropertyChange

    private void txtDescontoAvulsoPercFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoAvulsoPercFocusGained
        // TODO add your handling code here:
        SwingUtilidade.SelectAll(txtDescontoAvulsoPerc);
    }//GEN-LAST:event_txtDescontoAvulsoPercFocusGained

    private void txtDescontoAvulsoVLRFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoAvulsoVLRFocusGained
        // TODO add your handling code here:
        SwingUtilidade.SelectAll(txtDescontoAvulsoVLR);
    }//GEN-LAST:event_txtDescontoAvulsoVLRFocusGained

    private void txtDescontoAvulsoPercFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoAvulsoPercFocusLost
        // TODO add your handling code here:        
        try {
            
          if(VerificarNFCeEmDigitacao())  { return;}
          Double PercentualSolicitado = CalcularDescontoPercentual();  
          if(PercentualSolicitado==0){
              //txtDescontoAvulsoPerc.requestFocus();
          }
          SomarTotalDesconto();            
          AtualizaValorFinalVenda();
          AtualizarValorFaltaReceber();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        //
    }//GEN-LAST:event_txtDescontoAvulsoPercFocusLost
    public boolean AtualizarCampoDescontoTotal(){
        try {
            Double ValorDescontoTotal=0d;            
            Double DescontoAvulso = 0d, DescontoAtacado =0d;            
            DescontoAvulso = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAvulsoVLR.getValue(),0d).toString());
            DescontoAtacado = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAtacado.getValue(),0d).toString());
            ValorDescontoTotal = DescontoAtacado + DescontoAvulso;
            txtDescEAcrescVal.setValue(ValorDescontoTotal);            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean AtualizarCampoAcrescimoTotal(){
        try {
            Double ValorAcrescimoTotal=0d;            
            Double AcrescimoAvulso = 0d, AcrescimoPagto =0d;            
            AcrescimoAvulso = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoAvulsoVLR.getValue(),0d).toString());
            AcrescimoPagto = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoPagto.getValue(),0d).toString());
            ValorAcrescimoTotal = AcrescimoAvulso + AcrescimoPagto;
            txtAcrescimo.setValue(ValorAcrescimoTotal);            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void txtDescontoAvulsoVLRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoAvulsoVLRFocusLost
        // TODO add your handling code here:
        try {     

            ArrayList<Double> Retorno =CalcularDescontoValor();
            Double ValorSolicitado = Retorno.get(0);
            Double PercSolicitado = Retorno.get(1);
                
            if(PercSolicitado>0f){
                Float DescontaJaAutorizado = VlrDescontoJaAutorizado();
                if(DescontaJaAutorizado.floatValue() != ValorSolicitado.floatValue()){
                    if(AutorizarDesconto(PercSolicitado)){
                        VincularDescontoAVenda(PercSolicitado,ValorSolicitado);    
                    }
                }                            
            }
            
            Atalhos("F6");
            AtualizarCampoDescontoTotal();
            SomarTotalDesconto();            
            AtualizaValorFinalVenda();
            AtualizarValorFaltaReceber();

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        //CalcularDescontoPercentual_Valor("Valor",true); 
        //PainelDescontos.setVisible(!PainelDescontos.isVisible());
    }//GEN-LAST:event_txtDescontoAvulsoVLRFocusLost

    private void txtAcrescimoAvulsoVLRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoVLRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoAvulsoVLRActionPerformed

    private void txtAcrescimoAvulsoVLRPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoVLRPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoAvulsoVLRPropertyChange

    private void txtAcrescimoPagtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAcrescimoPagtoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoPagtoActionPerformed

    private void txtAcrescimoPagtoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAcrescimoPagtoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoPagtoPropertyChange

    private void txtAcrescimoAvulsoPercActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoPercActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoAvulsoPercActionPerformed

    private void txtAcrescimoAvulsoPercPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoPercPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoAvulsoPercPropertyChange

    private void txtAcrescimoAvulsoPercFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoPercFocusGained
        // TODO add your handling code here:
        SwingUtilidade.SelectAll(txtAcrescimoAvulsoPerc);
    }//GEN-LAST:event_txtAcrescimoAvulsoPercFocusGained

    private void txtAcrescimoAvulsoVLRFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoVLRFocusGained
        // TODO add your handling code here:
        SwingUtilidade.SelectAll(txtAcrescimoAvulsoVLR);
    }//GEN-LAST:event_txtAcrescimoAvulsoVLRFocusGained

    private void txtAcrescimoAvulsoPercFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoPercFocusLost
        // TODO add your handling code here:
       try {
            
          Double PercentualSolicitado = CalcularAcrescimoPercentual();
          if(PercentualSolicitado==0){
              //txtDescontoAvulsoPerc.requestFocus();
          }
          SomarTotalAcrescimo();            
          AtualizaValorFinalVenda();
          AtualizarValorFaltaReceber();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_txtAcrescimoAvulsoPercFocusLost

    private void txtAcrescimoAvulsoVLRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAcrescimoAvulsoVLRFocusLost
        // TODO add your handling code here:
       try {     
           
            if(VerificarNFCeEmDigitacao())  { return;}
           
            ArrayList<Double> Retorno =CalcularAcrescimoValor();
            Double ValorSolicitado = Retorno.get(0);
            Double PercSolicitado = Retorno.get(1);
                
            if(PercSolicitado>0f){
                Float AcrescimoJaAutorizado = VlrAcrescimoJaAutorizado();
                if(AcrescimoJaAutorizado.floatValue() != ValorSolicitado.floatValue()){
                    if(AutorizarAcrescimo(PercSolicitado)){
                        VincularAcrescimoAVenda(PercSolicitado,ValorSolicitado);    
                    }
                }                            
            }
            
            Atalhos("F5");
            AtualizarCampoAcrescimoTotal();
            SomarTotalAcrescimo();
            AtualizaValorFinalVenda();
            AtualizarValorFaltaReceber();

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }//GEN-LAST:event_txtAcrescimoAvulsoVLRFocusLost

    private void txtDescontoPreVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoPreVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoPreVendaActionPerformed

    private void txtDescontoPreVendaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDescontoPreVendaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoPreVendaPropertyChange
    public Float VlrDescontoJaAutorizado(){
        Float Desconto=0f;
        try {
            ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
            if(rs.next()){
                Desconto =  TratamentoNulos.getTratarFloat().Tratar(rs.getFloat("desconto"),0f);
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Desconto;
    }

    public Double PercDescontoJaAutorizado(){
        Double Desconto=0d;
        try {
            ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
            if(rs.next()){
                Desconto =  TratamentoNulos.getTratarDouble().Tratar(rs.getDouble("descontoperc"),0d);
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Desconto;
    }
    public Float VlrAcrescimoJaAutorizado(){
        Float Acrescimo=0f;
        try {
            ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
            if(rs.next()){
                Acrescimo =  TratamentoNulos.getTratarFloat().Tratar(rs.getFloat("acrescimo_avulso_vlr"),0f);
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Acrescimo;
    }
    public Double PercAcrescimoJaAutorizado(){
        Double Acrescimo=0d;
        try {
            ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
            if(rs.next()){
                Acrescimo =  TratamentoNulos.getTratarDouble().Tratar(rs.getDouble("acrescimo_avulso_perc"),0d);
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Acrescimo;
    }
    public boolean AtualizarDesconto(Double Perc, Double Valor){
        try {
            if(VincularDescontoAVenda(Perc,Valor)){                
                txtDescontoAvulsoPerc.setValue(Perc);
                txtDescontoAvulsoVLR.setValue(Valor);
                AtualizarCampoDescontoTotal();
                return true;
            }                        
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
    public boolean AtualizarAcrescimo(Double Perc, Double Valor){
        try {
            if(VincularAcrescimoAVenda(Perc,Valor)){                
                txtAcrescimoAvulsoPerc.setValue(Perc);
                txtAcrescimoAvulsoVLR.setValue(Valor);                  
                AtualizarCampoAcrescimoTotal();
                return true;
            }                        
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
    public boolean VincularAcrescimoAVenda(Double PercAcrescimoAvulso, Double VlrAcrescimoAvulso){
        try {
            
            return VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(getDadosorc().getCodigo(),null,null,null,null,null,null,null,null,PercAcrescimoAvulso, VlrAcrescimoAvulso,null,null,true);
            
            //return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean VincularDescontoAVenda(Double PercDescontoAvulso, Double VlrDescontoAvulso){
        try {
            
            return VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(getDadosorc().getCodigo(),null,VlrDescontoAvulso,null,null,null,null,null,PercDescontoAvulso,null,null,null,null,true);
            
            //return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public Float DescontoMaximoAoOperador(){
        return DescontoMaximoAoOperador(0);
    }
    public Float DescontoMaximoAoOperador(Integer CodigoUsuario){
        Float Retorno =0f;
        try {
            Float PercentualLoja =0f, PercentualMaximoOperador=0f;
            Boolean AtivarLimite = false;
            ResultSet rsLoja = Sistema.getDadosLojaAtualSistema();
            ResultSet rsDadosOp= UsuarioSistema.getDadosUsuario(CodigoUsuario);
            PercentualLoja = rsLoja.getFloat("descontomaximo");
            PercentualMaximoOperador = rsDadosOp.getFloat("descontomaximo");
            AtivarLimite = rsDadosOp.getBoolean("ativarlimitedesc");
            if(AtivarLimite){
               Retorno =  PercentualMaximoOperador;
            }else{
               Retorno =  PercentualLoja; 
            }
                    
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Retorno;
    }
    private boolean SomarTotalDesconto(){
        try {
            
            Double TotalDesconto = 0d;
            Double TotalDescontoAvulso = 0d;
            Double TotalDescontoAtacado = 0d;
            Double TotalDescontoPreVenda = 0d;
            
            
            TotalDescontoAvulso = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAvulsoVLR.getValue(),0d).toString());
            TotalDescontoAtacado = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAtacado.getValue(),0d).toString());
            TotalDescontoPreVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoPreVenda.getValue(),0d).toString());
            TotalDesconto = TotalDescontoAtacado+TotalDescontoAvulso +TotalDescontoPreVenda;
            txtDescEAcrescVal.setValue(TotalDesconto);
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean SomarTotalAcrescimo(){
        try {
            
            Double TotalAcrescimo = 0d;
            Double TotalAcrescimoAvulso = 0d;
            Double TotalAcrescimoPagto = 0d;
            
            TotalAcrescimoAvulso = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoAvulsoVLR.getValue(),0d).toString());
            TotalAcrescimoPagto = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoPagto.getValue(),0d).toString());
            TotalAcrescimo = TotalAcrescimoAvulso+TotalAcrescimoPagto;
            txtAcrescimo.setValue(TotalAcrescimo);
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    //private bo
    private boolean AutorizarDesconto(Double PercDescontoSolicitado){
        boolean Retorno = false;
        try {
            if(PercDescontoSolicitado.floatValue()>0f){
                  boolean Autorizado = false;
                  boolean ExecutarVerificacao=true;
                  Integer IdUsuario = UsuarioSistema.getIdUsuarioLogado();
                  String TipoPermissao ="";
                  Float DescontoMaximoAoOperador =0f;
                  while(ExecutarVerificacao){
                    DescontoMaximoAoOperador = DescontoMaximoAoOperador(IdUsuario).floatValue();
                    if(PercDescontoSolicitado.floatValue() > DescontoMaximoAoOperador.floatValue()){
                        TipoPermissao ="LiberarDesconto";
                    }else if(PercDescontoSolicitado.floatValue() < DescontoMaximoAoOperador.floatValue()){
                        TipoPermissao ="ReduzirDesconto";
                    }else{
                        Retorno=true;
                        break;
                    }
                    Autorizado = UsuarioPermissoes.Verificar(IdUsuario, Sistema.getLojaAtual(), TipoPermissao);
                    int Resposta =0;
                    if(!Autorizado){
                        String Pergunta ="";
                        if(TipoPermissao.equalsIgnoreCase("LiberarDesconto")){
                            Pergunta = "O LIMITE DO DESCONTO PARA O USUÁRIO (" + FormatarNumeros.FormatarParaMoeda( DescontoMaximoAoOperador) + "%) FOI ULTRAPASSADO\n\nDESEJA AUTORIZAR ESTE DESCONTO COM OUTRO USUARIO?";
                        }else{
                            Pergunta = "DESCONTO AUTORIZADO PARA O USUÁRIO (" + FormatarNumeros.FormatarParaMoeda( DescontoMaximoAoOperador) + "%) NÃO FOI ALCANÇADO\n\nDESEJA AUTORIZAR ESTE DESCONTO COM OUTRO USUARIO?";
                        }
                        Resposta = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null,Pergunta , "AUTORIZAR "+ (TipoPermissao.equalsIgnoreCase("LiberarDesconto") ?  "AUMENTAR" : "REDUZIR") +" DESCONTO");
                    }else{
                        Retorno=true;
                        UsuarioAuditar.Auditar(IdUsuario, "PDV", "VENDA ["+ Pagto_dadosorc.getCodigo() +"] DESCONTO DE " + FormatarNumeros.FormatarParaMoeda(PercDescontoSolicitado) + " %");
                        break;
                    }
                    if(Resposta!=MetodosUI_Auxiliares_1.Sim()){
                       AtualizarDesconto(0d, 0d);
                       break;                       
                    }else{
                       IdUsuario = IdentificarUsuario.Identificar(null,"AUTORIZAR DESCONTO DE [" + FormatarNumeros.FormatarParaMoeda( PercDescontoSolicitado)   + "%]").intValue(); 
                       if(IdUsuario==0){
                          MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "OPERAÇÃO NÃO AUTORIZADA", "DESCONTO");
                          AtualizarDesconto(0d, 0d);
                          break;
                       }
                    }
                  }                
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }        
        return Retorno;
    }
    private boolean AutorizarAcrescimo(Double PercAcrescimoSolicitado){
        boolean Retorno = false;
        try {
            if(PercAcrescimoSolicitado.floatValue()>0f){
                boolean Autorizado = false;                
                Integer IdUsuario = UsuarioSistema.getIdUsuarioLogado();
                Integer Resposta = 0;
                while(true){
                    Autorizado = UsuarioPermissoes.Verificar(IdUsuario, Sistema.getLojaAtual(), "CHKAcres");
                    if(!Autorizado){
                       Resposta = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "USUÁRIO NÃO AUTORIZADO A REALIZAR ACRÉSCIMO\n\nDESEJA AUTORIZAR COM OUTRO USUÁRIO?", "ACRÉSCIMO");
                       if(Resposta!=MetodosUI_Auxiliares_1.Sim()){
                          AtualizarAcrescimo(0d, 0d);
                          break; 
                       }
                    }else{
                        Retorno=true;
                        UsuarioAuditar.Auditar(IdUsuario, "PDV", "VENDA ["+ Pagto_dadosorc.getCodigo() +"] ACRESCIMO DE " + FormatarNumeros.FormatarParaMoeda(PercAcrescimoSolicitado) + " %");
                        break;
                    }
                    IdUsuario = IdentificarUsuario.Identificar(null,"AUTORIZAR ACRÉSCIMO DE [" + FormatarNumeros.FormatarParaMoeda(PercAcrescimoSolicitado)   + "%]").intValue(); 
                    if(IdUsuario==0){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "OPERAÇÃO NÃO AUTORIZADA", "ACRÉSCIMO");
                       AtualizarAcrescimo(0d, 0d);
                       break;
                    }
                }
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }        
        return Retorno;
    }
    private Double CalcularAcrescimoPercentual(){
        Double Acrescimo =0d;
        try {
            Double PercAcrescimo=0d;            
            if(FormatarNumero.FormatarNumero(txtAcrescimoAvulsoPerc.getText()).isNaN()){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "INFORMAÇÃO INVÁLIDA", "% ACRÉSCIMO");
               AtualizarAcrescimo(0d, 0d);
               txtAcrescimoAvulsoPerc.requestFocus();
               return 0d;
            }
            txtAcrescimoAvulsoPerc.commitEdit();
            PercAcrescimo = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoAvulsoPerc.getValue(),0d).toString());
            if(PercAcrescimo<0 || PercAcrescimo>100){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O PERCENTUAL INFORMADO É INVÁLIDO. [1-100%]", "% ACRÉSCIMO");
                AtualizarAcrescimo(0d, 0d);
                return 0d;
            }
            Double ValorAcrescimo=0d;
            Double ValorVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue(),0d).toString());
            Double PercAcrescimoJaAutorizado = PercAcrescimoJaAutorizado();
            if(PercAcrescimoJaAutorizado.doubleValue()!=PercAcrescimo.doubleValue()){         
                ValorAcrescimo = NumeroArredondar.Arredondar_Double((ValorVenda * PercAcrescimo/100d),2);
                txtAcrescimoAvulsoVLR.setValue(ValorAcrescimo);                
            }
            Acrescimo=PercAcrescimo;
               
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return Acrescimo;
    }
    private Double CalcularDescontoPercentual(){
        Double Desconto =0d;
        try {
            Double PercDesconto=0d;            
            if(FormatarNumero.FormatarNumero(txtDescontoAvulsoPerc.getText()).isNaN()){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "INFORMAÇÃO INVÁLIDA", "% DESCONTO");
               AtualizarDesconto(0d, 0d);
               txtDescontoAvulsoPerc.requestFocus();
               return 0d;
            }
            txtDescontoAvulsoPerc.commitEdit();
            PercDesconto = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAvulsoPerc.getValue(),0d).toString());
            if(PercDesconto<0 || PercDesconto>100){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O PERCENTUAL INFORMADO É INVÁLIDO. [1-100%]", "%DESCONTO");
                AtualizarDesconto(0d, 0d);
                return 0d;
            }
            Double ValorDesconto=0d;
            Double ValorVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue(),0d).toString());
            Double PercDescontoJaAutorizado = PercDescontoJaAutorizado();
            if(PercDescontoJaAutorizado.floatValue()!=PercDesconto.floatValue()){         
                ValorDesconto = NumeroArredondar.Arredondar_Double((ValorVenda * PercDesconto/100f),2);
                txtDescontoAvulsoVLR.setValue(ValorDesconto);                
            }
            Desconto=PercDesconto;
               
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return Desconto;
    }
    private Double CalcularValorDoPercentual(Double Percentual, Double Total){
        Double Valor =0d;
        try {
            
            Valor = NumeroArredondar.Arredondar_Double((Total * Percentual/100f),2);
            Valor = NumeroArredondar.truncar(Valor, 2);
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Valor;
    }
    private ArrayList<Double> CalcularDescontoValor(){
        ArrayList<Double> Desconto  = new ArrayList<Double>();
        Desconto.add(0d);
        Desconto.add(0d);
        try {
            Double PercDesconto=0d;            
            if(FormatarNumero.FormatarNumero(txtDescontoAvulsoVLR.getText()).isNaN()){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "INFORMAÇÃO INVÁLIDA", "$ DESCONTO");
               AtualizarDesconto(0d, 0d);
               
               txtDescontoAvulsoVLR.requestFocus();              
               return Desconto;
            }
            //Float ValorAnterior = Float.valueOf(txtDescontoAvulsoVLR.getValue().toString());                
            txtDescontoAvulsoVLR.commitEdit();
            Double ValorDesconto = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAvulsoVLR.getValue(),0d).toString());
            Double ValorVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue(),0d).toString());
            if(ValorDesconto>ValorVenda){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O VALOR INFORMADO NÃO PODE SER MAIOR QUE O VALOR DA VENDA.", "$DESCONTO");
               AtualizarDesconto(0d, 0d);   
               txtDescontoAvulsoVLR.requestFocus();
               return Desconto;
            }       
            
            Double PercentualDescontoAtual = 0d;            
            Double ValorDescontoAtual = 0d;
            
            PercentualDescontoAtual = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAvulsoPerc.getValue(),0d).toString());
            ValorVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue(),0d).toString());
            ValorDescontoAtual = CalcularValorDoPercentual(PercentualDescontoAtual, ValorVenda);
            if(ValorDesconto.floatValue()!=ValorDescontoAtual){
                PercDesconto = NumeroArredondar.Arredondar_Double(ValorDesconto/ValorVenda,2)*100;
                PercDesconto = NumeroArredondar.truncar(PercDesconto,2);
                txtDescontoAvulsoPerc.setValue(PercDesconto);                 
            }else{
               PercDesconto = PercentualDescontoAtual; 
            }
            Desconto.set(0,ValorDesconto);
            Desconto.set(1,PercDesconto);
                        
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return Desconto;
    }
    private ArrayList<Double> CalcularAcrescimoValor(){
        ArrayList<Double> Acrescimo  = new ArrayList<Double>();
        Acrescimo.add(0d);
        Acrescimo.add(0d);
        try {
            Double PercAcrescimo=0d;            
            if(FormatarNumero.FormatarNumero(txtAcrescimoAvulsoVLR.getText()).isNaN()){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "INFORMAÇÃO INVÁLIDA", "$ ACRÉSCIMO");
               AtualizarAcrescimo(0d, 0d);               
               txtAcrescimoAvulsoVLR.requestFocus();              
               return Acrescimo;
            }
            //Float ValorAnterior = Float.valueOf(txtDescontoAvulsoVLR.getValue().toString());                
            txtAcrescimoAvulsoVLR.commitEdit();
            Double ValorAcrescimo = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoAvulsoVLR.getValue(),0d).toString());
            Double ValorVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue(),0d).toString());
            if(ValorAcrescimo>=ValorVenda){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O VALOR INFORMADO NÃO PODE SER MAIOR QUE O VALOR DA VENDA.", "$ ACRÉSCIMO");
               AtualizarAcrescimo(0d, 0d);               
               txtAcrescimoAvulsoVLR.requestFocus();
               return Acrescimo;
            }       
            
            Double PercentualDescontoAtual = 0d;            
            Double ValorDescontoAtual = 0d;
            
            PercentualDescontoAtual = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoAvulsoPerc.getValue(),0d).toString());
            ValorVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue(),0d).toString());
            ValorDescontoAtual = CalcularValorDoPercentual(PercentualDescontoAtual, ValorVenda);
            if(ValorAcrescimo.floatValue()!=ValorDescontoAtual){
                PercAcrescimo = NumeroArredondar.Arredondar_Double(ValorAcrescimo/ValorVenda,2)*100;
                PercAcrescimo = NumeroArredondar.truncar(PercAcrescimo,2);
                txtAcrescimoAvulsoPerc.setValue(PercAcrescimo);                 
            }else{
               PercAcrescimo = PercentualDescontoAtual; 
            }
            Acrescimo.set(0,ValorAcrescimo);
            Acrescimo.set(1,PercAcrescimo);
                        
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return Acrescimo;
    }
    /**
     * @param args the command line arguments
     */
    private boolean IncluirFormaPagamento(Dadosorc d,Long nCodigoVenda, Integer nCodigoForma, int nLoja, Double nValorForma, int nQtParcelas, int nCodigoPDV){
    return IncluirFormaPagamento( d, nCodigoVenda,  nCodigoForma,  nLoja,  nValorForma,  nQtParcelas,  nCodigoPDV, 0d );

}

private boolean IncluirFormaPagamento(Dadosorc d,Long nCodigoVenda, Integer nCodigoForma, int nLoja, Double nValorForma, int nQtParcelas, int nCodigoPDV, Double nValorAcrescimo )
{
    boolean bRet=false;
    try {
        if(PagtoorcRN.Pagtoorc_GerarParcelasFormas(d,nCodigoVenda ,nCodigoForma, nLoja,nValorForma+nValorAcrescimo,nQtParcelas,nCodigoPDV,nValorAcrescimo)) {
            /*ResultSet rs = TiposdePagamentos.Listar(nCodigoForma, nLoja);
            if(rs.next()){
                if(rs.getString("destino").equalsIgnoreCase("Cheques Recebidos")){
                  CadastroDeCheques(d,nValorForma);
                }
            }*/
            bRet=true;
      }else{
         MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível incluir a forma de pagamento", "Operação não realizada", "AVISO");
      }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    return bRet;
}

    private Double ValorFaltaDistribuir()
    {
        Double nValorRetorno=0d;
        Double nValorFinal =0d;
        Double nValalorDistribuido=0d;
        try {

            nValorFinal = FormatarNumero.FormatarNumero_Double(lblValorFinal.getText());
            nValalorDistribuido = PagtoorcRN.Pagtorc_SomarValores(getDadosorc().getCodigo());
            nValorRetorno = NumeroArredondar.Arredondar_Double(nValorFinal - nValalorDistribuido,2);

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nValorRetorno;
    }

    public boolean AtualizarValorFaltaReceber(){
    try {
        
        txtValorFaltaReceber.setValue(ValorFaltaDistribuir());
        return true;
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        return false;
    }
    }
    public boolean IniciarGridPagtos_Atualizar(){
        try {
            int TotalRegistros=0;
            ResultSet rs = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo(),0l,"codforma,grupoforma,valor,tefstatus,idunico");
            if(rs.last()){
                TotalRegistros =rs.getRow();
                rs.first();
            }
            
            //PainelFormasPagtoVenda.setVisible(TotalRegistros==0 ? false : true);            
            //dbgFormasPagto.setVisible(TotalRegistros==0 ? false : true);
            dbgFormasPagto.setRsDados(rs);
            AtualizaValorFinalVenda();
            AtualizarValorFaltaReceber();
            if(TotalRegistros>0){
                dbgFormasPagto.getjTable().setRowSelectionInterval(TotalRegistros-1, TotalRegistros-1);
            } 
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean IniciarGridPagtos(){
        try {
            dbgFormasPagto.getTbDinnamuS().setModeloUsandoColecao(true);
            dbgFormasPagto.addClColunas("codforma", "COD", 50, true);
            dbgFormasPagto.addClColunas("grupoforma", "NOME", 200, true);
            dbgFormasPagto.addClColunas("valor", "VALOR", 100, true,false,dbgFormasPagto.Alinhamento_Direita);            
            dbgFormasPagto.addClColunas("tefstatus", "TEF", 150, true);
            dbgFormasPagto.addNumberFormatMoeda("valor");            
            //dbgFormasPagto.getjTable().getTableHeader().setVisible(false);
            //dbgFormasPagto.getjTable().setBackground(Color.WHITE);
            //dbgFormasPagto.getjTable().setForeground(Color.BLACK);
            dbgFormasPagto.getjTable().setFont(new Font("Tahoma", Font.BOLD, 12));
            /*dbgFormasPagto.getjTable().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {  
                public Component getTableCellRendererComponent(JTable table, Object value,  
                        boolean isSelected, boolean hasFocus, int row, int column) {  
                    super.getTableCellRendererComponent(table, value, isSelected,  
                            hasFocus, row, column);  
                    try {
                        setBackground(Color.WHITE);  
                        setForeground(Color.BLACK);                        
                    } catch (Exception e) {
                        LogDinnamus.Log(e, true);
                    }
                    return this;  
                }  
            }); */
             
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean ExibirDadosAcrescimo(Long CodigoVenda){
        try {
            Double ValorAcrescimo = PagtoorcRN.Pagtorc_SomarValores_Acrescimo(CodigoVenda);
            Double ValorAcrescimoAvulso =  Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtAcrescimoAvulsoVLR.getValue(),0d).toString());
            txtAcrescimo.setValue(ValorAcrescimo+ValorAcrescimoAvulso);
            txtAcrescimoPagto.setValue(ValorAcrescimo);
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean AtualizaValorFinalVenda(){
        try {
            Double nValorDesconto = Double.valueOf(  TratamentoNulos.getTratarObject().Tratar(txtDescEAcrescVal.getValue() ,0d).toString());
            Double nValorAcrescimo =Double.valueOf(TratamentoNulos.getTratarObject().Tratar(txtAcrescimo.getValue(),0d).toString());
            Double nValorVenda  = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(txtValorVenda.getValue(),0d).toString());
            Double nTroca  = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(txtValorCreditoTroca.getValue(),0d).toString());
            Double nValorFinal =  NumeroArredondar.Arredondar_Double(
                                nValorVenda 
                                + (nValorAcrescimo==null ? 0d : nValorAcrescimo) 
                                - (nValorDesconto==null ? 0d : nValorDesconto) 
                                - (nTroca==null ? 0d : nTroca)
                                ,2);
            lblValorFinal.setValue(nValorFinal);
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
            return false;
        }
    }
    private void Troca(){
        try {          
            //int nOpcoesPagto = PagtoorcRN.PagtoOrc_Contar(getDadosorc().getCodigo());
            if(!PainelTroca.isVisible()) {return ;}
            
                if(Venda.VendaEmFechamento()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("CAMPO TROCA BLOQUEADO", "VENDA EM FECHAMENTO");
                    return ;
                }
            if(txtValorCreditoTroca.isEnabled()){
                
                Double ValorVenda =  Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue().toString(),0d).toString());
                Double Desconto = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescEAcrescVal.getValue(),0d).toString());
                Double SaldoFinal =  ValorVenda-Desconto;
                Double ValorCredito =0d;//(Float)txtValorCreditoTroca.getValue();
                ResultSet rsDadosVenda = VendaEmEdicao.VerificarVendaInterrompida();
                Long CodigoTroca = 0l;
                if(rsDadosVenda.next()){
                    CodigoTroca = rsDadosVenda.getLong("codigotroca");
                    ValorCredito = rsDadosVenda.getDouble("creditotroca");
                }
                boolean PreVenda = false;
                if(PreVendasSelecionadas.getNotasSelecionadas()!=null && PreVendasSelecionadas.getNotasSelecionadas().size()>0){
                    PreVenda=true;
                }
                getFrmTroca().CarregarInterface(this.Pagto_TipoComprovante,getDadosorc().getCodigo(),UsuarioSistema.getIdUsuarioLogado().longValue(),SaldoFinal,ValorCredito,CodigoTroca,PreVenda );
                getFrmTroca().setVisible(true);
                if(getFrmTroca().TrocaOK){                   
                   ProcessarTroca();
                   AtualizarValorFaltaReceber();
                }
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null,  "TROCA NÃO DISPONÍVEL\n\nPAGAMENTO(S) JÁ FOI(RAM) INCLUIDO(S) NESTA VENDA","FECHAMENTO DA VENDA");
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);          
        }
    }    
    private void Desconto_Acrescimo(String TipoOperacao ){
        try {  
            
            
            if(!txtDescEAcrescVal.isVisible()){ return;}
            
            if(Venda.VendaEmFechamento()){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("CAMPO DESCONTO BLOQUEADO", "VENDA EM FECHAMENTO");
                return ;
            }
            
            
            
            PainelDescontos.setVisible(!PainelDescontos.isVisible());
            
          
            if(PainelDescontos.isVisible()){
               if(!txtDescontoAvulsoVLR.isEnabled()){
                  MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "DESCONTO NÃO DISPONÍVEL\n\nPAGAMENTO(S) JÁ FOI(RAM) INCLUIDO(S) NESTA VENDA","FECHAMENTO DA VENDA");
                  btListaForma.requestFocus();
               }else{                 
                  txtDescontoAvulsoPerc.requestFocus();
               }
            }else{
                btListaForma.requestFocus();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);          
        }
    }    
    public boolean VerificarPossibilidadeEdicaoCampos(){
        try {
            int nOpcoesPagto = PagtoorcRN.PagtoOrc_Contar(getDadosorc().getCodigo());
            if(nOpcoesPagto==0){
                return true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return false;
    }
    public void Bloquear_Liberar_CamposEditaveis(boolean status){
        try {
            
            MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelAcrescimo, status);
            MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelDescontos, status);
            lblTroca.setEnabled(status);
            txtValorCreditoTroca.setEnabled(status);
            lblDesconto.setEnabled(status);
            //txtDescEAcrescVal.setEditable(status);
            lblAcrescimoTotal.setEnabled(status);
            txtAcrescimo.setEnabled(status);            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private void Acrescimo(){
        try {          
            
            if(!txtAcrescimo.isVisible()){return ;}
            
             if(Venda.VendaEmFechamento()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("CAMPO ACRÉSCIMO BLOQUEADO", "VENDA EM FECHAMENTO");
                    return ;
                } 
            
            PainelAcrescimo.setVisible(!PainelAcrescimo.isVisible());
            
            if(PainelAcrescimo.isVisible()){
               
                
               if(!txtAcrescimoAvulsoVLR.isEnabled()){
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ACRÉSCIMO NÃO DISPONÍVEL\n\nPAGAMENTO(S) JÁ FOI(RAM) INCLUIDO(S) NESTA VENDA","FECHAMENTO DA VENDA");
                   btListaForma.requestFocus();
               }else{ 
                  txtAcrescimoAvulsoPerc.requestFocus();
               }
            }else{
                btListaForma.requestFocus();
            }
            

            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);          
        }
    }    
    private boolean ProcessarDesconto(){
        try {
            /*
               if( AlterarDescontos(getFrmDesconto().nDescontoLiberado_Perc, getFrmDesconto().nDescontoLiberado,getFrmDesconto().DescontoAtacadoLiberado,getFrmDesconto().ValorVendaPDV,getFrmDesconto().CreditoTroca,getFrmDesconto().CodigoTroca)){
                   txtDescEAcrescVal.setValue(getFrmDesconto().nDescontoLiberado+getFrmDesconto().DescontoAtacadoLiberado - getFrmDesconto().AcrescimoLiberado);
                   txtValorCreditoTroca.setValue(getFrmDesconto().CreditoTroca);
                   //txtValorFinal.setValue(getFrmDesconto().ValorFinalPosDesconto);
                  
               }*/
             AtualizaValorFinalVenda();
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        return false;
    }
    private boolean ProcessarTroca(){
        try {
               if( AlterarTroca(getFrmTroca().CreditoTroca,getFrmTroca().CodigoTroca)){
                   //txtDescEAcrescVal.setValue(frmDesconto.nDescontoLiberado+frmDesconto.DescontoAtacadoLiberado - frmDesconto.AcrescimoLiberado);
                   Double ValorVenda = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorVenda.getValue(),0d).toString());
                   Double Desconto = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescEAcrescVal.getValue(),0d).toString());
                   Double ValorFinal = NumeroArredondar.Arredondar_Double(ValorVenda - Desconto - getFrmTroca().CreditoTroca,2);
                   txtValorCreditoTroca.setValue(getFrmTroca().CreditoTroca);
                   AtualizaValorFinalVenda();
               }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        return false;
    }    
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
private boolean AlterarDescontosEAcrescimo(Double nPercDesconto, Double nValorDesconto, Double nValorDescontoAtacado ,Double nValorVenda, Double ValorCreditoTroca, Long CodigoCreditoTroca,Double AcrescimoPercVLR,Double AcrescimoPercPRC, Double AcrescimoPagto, Double DescontoPreVenda)
{
    try {
        
        Double PercTotalDesconto =0d,ValorTotalDesconto =0d;
        
        ValorTotalDesconto  = nValorDesconto + nValorDescontoAtacado  + DescontoPreVenda;
      
        if(ValorTotalDesconto>0){
            PercTotalDesconto = NumeroArredondar.Arredondar_Double(ValorTotalDesconto/nValorVenda,2);
        }
        getDadosorc().setDesconto(ValorTotalDesconto);
        getDadosorc().setPercdesc(BigDecimal.valueOf(PercTotalDesconto));
        
        VendaEmEdicao.RegistrarVendaEmEdicao_AtualizarDados(getDadosorc().getCodigo(), null, nValorDesconto,nValorDescontoAtacado,null,null, ValorCreditoTroca, CodigoCreditoTroca,nPercDesconto,AcrescimoPercPRC,AcrescimoPercVLR,AcrescimoPagto);

        return true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
        return false;
        
    }
}

    public boolean TEF_PDV_Venda(Long nIdUnico){
        try {
            if(!Venda.PDV_TEF_Habilitado()){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Pdv não esta habilitado para operar com TEF", "TEF", "AVISO");      
               return false;
            }
            if((Pagto_TipoComprovante.equalsIgnoreCase("nfce") || Pagto_TipoComprovante.equalsIgnoreCase("fiscal")) && Pagto_ImprimirComprovante){                                       
                    ResultSet rsPagtoOrc = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo(), nIdUnico);                
                    if(rsPagtoOrc.next()){     
                        if(rsPagtoOrc.getString("TEF").equalsIgnoreCase("S"))
                        {
                            String StatusTEF =TratamentoNulos.getTratarString().Tratar(rsPagtoOrc.getString("TEFSTATUS"),"");
                            if(!StatusTEF.equalsIgnoreCase("") && !StatusTEF.equalsIgnoreCase("[ F9 ]- REPETIR OPERAÇÃO")){
                               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "A Forma de Pagamento já esta Aprovada pela Administradora", "TEF - Forma Pagto", "AVISO");      
                               return false;
                            }else{
                                if(!TefPadrao.VerificarGP()){                     
                                    InteracaoDuranteProcessamento.Mensagem("TEF DinnamuS", "O TEF NÃO ESTA ATIVADO", 5000);
                                    return false;
                                }
                                if(!TEFVenda.TEF_PDV_ConfirmarVendaPendente(getDadosorc().getCodigo(),0l)){
                                    return false;
                                }
                                String COO ="";
                                if(Pagto_TipoComprovante.equalsIgnoreCase("fiscal")){
                                 COO = getEcf().UltimoCupom();
                                }
                                Double ValorVendaTEF = rsPagtoOrc.getDouble("valor");
                                String ControleTEF = rsPagtoOrc.getString("controle");
                                HashMap<String,String> TEF_Retorno_Venda = TefPadrao.IniciarVendaTEF(COO, ValorVendaTEF, getDadosorc().getCodigo(),false,ControleTEF );                                
                                String SituacaoVenda = TEF_Retorno_Venda.get("situacaovenda");
                                String MsgTEF = TEF_Retorno_Venda.get("msg");
                                //SituacaoVenda = TratamentoNulos.getTratarString().Tratar(SituacaoVenda, "");
                                if(TEF_Retorno_Venda.size()>0){
                                    if(SituacaoVenda.equalsIgnoreCase("aprovada")){                                    
                                        TEF_Retorno_Venda.put("coo", COO);
                                        String Rede = TEF_Retorno_Venda.get("rede");
                                        String NSU = TEF_Retorno_Venda.get("nsu");
                                        String Finalizacao = TEF_Retorno_Venda.get("finalizacao");              
                                        String Identificacao = TEF_Retorno_Venda.get("identificacao");   
                                        String Parcelas = TEF_Retorno_Venda.get("parcelas");  
                                        String InfoTef =  TransformacaoDados.TransformarColecaoEmString(TEF_Retorno_Venda,"-dti-","<=>");//TEF_Retorno_Venda.toString(); //Rede +":"+ NSU +":"+ Finalizacao +":"+ COO + ":" + DataOp + ":" + HoraOP + ":" + ValorOP ;
                                        PagtoorcRN.Pagtoorc_AtualizarInfoTEF(nIdUnico,MsgTEF + " : " + Rede , InfoTef,"",Identificacao);                                    
                                        Long CodigoOperacao = getDadosorc().getCodigo();
                                        Integer TotParcelas = new Integer(Parcelas);
                                        Integer Loja = getDadosorc().getLoja();
                                         Integer CodigoPDV = getDadosorc().getPdv();
                                                
                                        PagtoorcRN.Pagtoorc_ReGerarParcorc(CodigoOperacao,nIdUnico,TotParcelas,Loja,CodigoPDV);

                                        InteracaoDuranteProcessamento.Mensagem("TEF DinnamuS", MsgTEF, 5000);
                                        //FormasDePagto_AtualizarGrid((getDadosorc().getCodigo()==null ? 0 :getDadosorc().getCodigo()));                                          
                                        //Evento_F7();
                                    }else if(!SituacaoVenda.equalsIgnoreCase("inativo")){                                        
                                        InteracaoDuranteProcessamento.Mensagem("TEF DinnamuS", MsgTEF, 5000);                                        
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
     public boolean AcionarVendaTEF_Acao(final Long CodigoVenda, final Long PKPagtoOrc ){
        //MetodosUI_Auxiliares.Tela_Bloquear(0.5f);
        try {                        

                TEF_PDV_Venda(PKPagtoOrc);                                                      
                ResultSet rsPagtoorc = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo(),PKPagtoOrc);
                try {
                    if(rsPagtoorc.next()){
                       String TEfStatus =  rsPagtoorc.getString("tefstatus");
                       if(TEfStatus!=null){
                         if(ValorFaltaDistribuir()==0f){
                            IniciarGridPagtos_Atualizar();
                            btGravarVendaActionPerformed(null);
                            return false;
                         }
                       }else{                                            
                             if(!PagtoorcRN.PagtoOrc_Excluir(getDadosorc().getCodigo(),PKPagtoOrc,true)){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível excluir a forma de pagto", "Exclusão de Forma Pagto", "AVISO");                                                
                             }
                       }
                    }
                    IniciarGridPagtos_Atualizar();
                } catch (SQLException ex) {
                    LogDinnamus.Log(ex, true);
                }                       

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return false;
       // MetodosUI_Auxiliares.Tela_DesBloquear();
   }    
      public boolean AcionarVendaTEF(final Long CodigoVenda, final Long PKPagtoOrc ){
        boolean Ret = false;
        
        try {
            //Bloq_DesBloq_TelaDuranteProcessamento(false);
            Ret =AcionarVendaTEF_Acao(CodigoVenda, PKPagtoOrc);            
            //Bloq_DesBloq_TelaDuranteProcessamento(true);
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
        return Ret;
            
    }
    public boolean RepetirOperacaoTEF_Desfeita(){
        try {
            Integer Linha = dbgFormasPagto.getjTable().getSelectedRow();
            if(Linha>=0){
                ResultSet rsPagtoOrc  = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo());
                String TEFInfo ="";
                Long PKPagtoOrc  =0l;
                if(rsPagtoOrc.absolute(Linha+1)){
                   PKPagtoOrc = rsPagtoOrc.getLong("idunico");
                   TEFInfo = TratamentoNulos.getTratarString().Tratar(rsPagtoOrc.getString("tefstatus"),"");
                }
                
                if(Pagto_TEF_Ativo){                
                    if(PKPagtoOrc>0l && TEFInfo.equalsIgnoreCase("[ F9 ]- REPETIR OPERAÇÃO")){                                                       
                        AcionarVendaTEF(getDadosorc().getCodigo(), PKPagtoOrc);                    
                    }
                }            
            }
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
        
    public boolean ProcessarFormaDePagamento(){
    try {
        frmPDV_SimplesPagtos frmOpcoesPagto=new frmPDV_SimplesPagtos(null, true,getDadosorc().getCodcliente(),getDadosorc().getCodigo(), Double.valueOf(lblValorFinal.getValue().toString()),Pagto_TEF_Ativo);      
        frmOpcoesPagto.setVisible(true);
        if(frmOpcoesPagto.OK){
            Double nValorForma = frmOpcoesPagto.FormaPagtoValor;
            int nQtParcelas =frmOpcoesPagto.FormaPagtoParcela;
            Integer CodigoForma = frmOpcoesPagto.FormaPagtoCodigo;            

            Boolean RetInclusao= IncluirFormaPagamento(getDadosorc(), getDadosorc().getCodigo(),CodigoForma.intValue(), Sistema.getCodigoLojaMatriz(),nValorForma,nQtParcelas,pdvgerenciar.CodigoPDV(),frmOpcoesPagto.FormaPagtoAcrescimo );
            if(!RetInclusao) { 
                return false ;            
            }else{
                
                ExibirDadosAcrescimo(getDadosorc().getCodigo());
            }            
            getDadosorc().setDinheiro(new BigDecimal(frmOpcoesPagto.FormaPagtoDinheiro));
            getDadosorc().setTroco(new BigDecimal(frmOpcoesPagto.FormaPagtoTroco));
            ResultSet rsPagtoOrc  = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo());
            Long PKPagtoOrc  =0l;
            if(rsPagtoOrc.last()){
               PKPagtoOrc = rsPagtoOrc.getLong("idunico");                            
            }
            if(Pagto_TEF_Ativo && frmOpcoesPagto.FormaPagtoAcionarTEF){                
                if(PKPagtoOrc>0l){    
                    
                    AcionarVendaTEF(getDadosorc().getCodigo(), PKPagtoOrc);                    
                }
            }else{
                Boolean PermiteEdicao = false;
                String Destino = rsPagtoOrc.getString("destino");
                if(Destino.equalsIgnoreCase("Cheques Recebidos") || Destino.equalsIgnoreCase("A Receber & Crediario")){
                    if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "A FORMA DA PAGAMENTO ESCOLHIDA POSSIBILITA A DIGITAÇÃO DE DADOS COMPLEMENTARES\n\nDESEJA FINALIZAR A VENDA SEM ALTERAR ESSES DADOS?", lblTitulo.getText())!=MetodosUI_Auxiliares_1.Sim()){
                      PermiteEdicao=true;  
                    }
                }
                //AtualizaValorFinalVenda();
                IniciarGridPagtos_Atualizar();
                if(!PermiteEdicao){
                    if(ValorFaltaDistribuir()==0){                        
                        btEfetivar(this.Pagto_MomentoDaVendaTEFInterrompida_PDV);     
                    }
                }else{
                    int Linha = dbgFormasPagto.getjTable().getRowCount();
                    dbgFormasPagto.getjTable().setRowSelectionInterval(Linha-1,Linha-1);
                    btEditarFormaActionPerformed(null);
                }
            }
        }
        return true;
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        return false;
    }
}
    private boolean PrepararFechamento(){
        return PrepararFechamento(1);
}
private boolean VerificarNFCeEmDigitacao(){
    return VerificarNFCeEmDigitacao(false);
}
private boolean DesfazerNFCE(Long CodigoVenda, int Loja , int PDV ){
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
            String versao = NFCe_ConfiguracaoAmbiente.getVersaoNFCe(PDV);
            boolean RetDefazer = nfce_desfazer.Desfazer(CodigoVenda, ChaveDeAcesso, XML, NFCe_ConfiguracaoAmbiente.getConfig().getCUF(), Loja, PDV,versao);
            if (!RetDefazer) {
                int resp =MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Não foi possível desfazer a nfce\n\nDESEJA TENTAR DESFAZER A NFC-E NOVAMENTE ?", "NFCe - aberta");
                if(resp==MetodosUI_Auxiliares_1.Sim()){
                    return DesfazerNFCE(CodigoVenda, Loja, PDV);
                }else{
                    nfce_desfazer.PularSequencia(ChaveDeAcesso, Loja, PDV, XML, CodigoVenda,versao);
                    return true;
                }
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
private boolean VerificarNFCeEmDigitacao( boolean  Silencioso){
    boolean Ret = false;
    try {
        if (Pagto_TipoComprovante.equalsIgnoreCase("nfce")) {
            Long CodigoVenda = getDadosorc().getCodigo();
            ResultSet rs = NFCE_Configurar.NotaEnviada(CodigoVenda, pdvgerenciar.CodigoPDV());
            if (rs.next()) {
                if (!Silencioso) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Existe uma NFCe enviada.\n\nFinalize a venda", "NFCe - Enviada");
                    return true;                    
                }else{
                     return true;
                }
            }
            rs = VendaEmEdicao.VerificarVendaInterrompida();
            if (rs != null) {
                if (rs.next()) {
                    String ChaveDeAcesso = TratamentoNulos.getTratarString().Tratar(rs.getString("nfce_chavedeacesso"), "");
                    if (ChaveDeAcesso.length() > 0) {
                        if (!Silencioso) {
                            int Ret2 = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Existe uma NFCe em processo de envio.\n\nCONFIRMA O CANCELAMENTO DA NFCE ?", "NFCe - aberta");
                            if (Ret2 == MetodosUI_Auxiliares_1.Sim()) {
                                return !DesfazerNFCE(CodigoVenda, Sistema.getLojaAtual(), pdvgerenciar.CodigoPDV());
                            } else {
                                return true;
                            }
                        }else{
                             return true;
                        }
                    }
                }
            }
        }
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
private boolean PreparaParaSairDoFechamento(){
    try {
            
            if(VerificarNFCeEmDigitacao()){return false;}
            
            if(PagtoorcRN.Pagtoorc_VendaTEF(getDadosorc().getCodigo())){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "Existem venda(s) aprovada(s) no cartão de crédito\nFAÇA O CANCELAMENTO DA(S) TRANSAÇÕES ANTES DE SAIR", "VENDA TEF APROVADA");
                return false;                    
            }else{
                
                VendaEmEdicao.LimparCamposTemporarios(getDadosorc().getCodigo());
                
                boolean Efetivada = DAO_RepositorioLocal.GerarResultSet("select 1 from dadosorc where recebido='S' and codigo=" + Pagto_dadosorc.getCodigo()).next();
                if(!Efetivada){
                    PagtoorcRN.PagtoOrc_Excluir(Pagto_dadosorc.getCodigo(),0l, true);
                }    
                if(PreVendasSelecionadas.getNotasSelecionadas().size()==0){                    
                    AlterarDescontosEAcrescimo(0d,0d,0d,0d,0d,0l,0d,0d,0d,0d);
                }
            }
        return true;
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        return false;
    }
}
    private boolean PrepararFechamentoCrediario(int MomentoVenda,Double ValorAReceber)
{
    boolean bRet=false;
    
    this.Pagto_MomentoDaVendaTEFInterrompida_PDV= MomentoVenda;
    
    try {
        if(MomentoVenda<=1){
            if(!PagtoorcRN.PagtoOrc_Excluir(getDadosorc().getCodigo(),Long.valueOf(0),true)){
               return false;
            }
        }
        Double DescontoAtacado =0d;
        Double DescontoRestaurado =0d;        
        Double DescontoPreVenda =0d;
        Double AcrescimoVLR =0d,AcrescimoPRC=0d,AcrescimoPagto =0d;
        Long CodigoCreditoTroca=0l;
        Double ValorCreditoTroca =0d;
        PrepararInterface_Recebimento();
        Double ValorTotalRecebimento =  ValorAReceber ; //ItensorcRN.Itensorc_Somar( getDadosorc().getCodigo());
        if(MomentoVenda>1){
            ResultSet rsVendaInterrompida = VendaEmEdicao.VerificarVendaInterrompida();
            if(rsVendaInterrompida.next()){
                DescontoRestaurado = br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("Desconto"),0d);                
                AcrescimoVLR = br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("acrescimo_avulso_vlr"),0d);
                AcrescimoPRC = br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("acrescimo_avulso_perc"),0d);
                AcrescimoPagto = 0d;// br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("acrescimo_pagto_vlr"),0d);
                DescontoPreVenda = 0d;// br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("Descontoprevenda"),0d);
                DescontoAtacado = 0d;//SomarDescontoAtacado(getDadosorc().getCodigo());               
            }        
        }
        ResultSet rsVendaAtual = VendaEmEdicao.VerificarVendaInterrompida();
        
        
        if(!Sistema.isOnline()){            
          
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "RECEBIMENTO NÃO PODE SER FINALIZADO EM MODO OFF-LINE", "FECHAMENTO DO RECEBIMENTO");
           return false;
           
        }
        Double DescontoTotal =0d,DescontoTotalPerc=0d;                    
        DescontoTotal = DescontoAtacado+DescontoRestaurado+DescontoPreVenda;
        if(DescontoTotal>0d){
           DescontoTotalPerc = NumeroArredondar.Arredondar_Double(DescontoTotal/ValorTotalRecebimento,2);
           txtDescEAcrescVal.setValue(DescontoTotal);
           txtDescontoAtacado.setValue(DescontoAtacado);
           txtDescontoAvulsoVLR.setValue(DescontoRestaurado);  
           Double PercDesconto = NumeroArredondar.Arredondar_Double(DescontoRestaurado/ValorTotalRecebimento,2)*100;
           //if(DescontoAtacado>0f){
           txtDescontoAvulsoPerc.setValue(PercDesconto);
           //CalcularDescontoPercentual_Acao("Valor");
           txtAcrescimoPagto.setValue(AcrescimoPagto);
           txtAcrescimoAvulsoPerc.setValue(AcrescimoPRC);
           txtAcrescimoAvulsoVLR.setValue(AcrescimoVLR);      
           txtDescontoPreVenda.setValue(DescontoPreVenda);
        }else{
           txtDescEAcrescVal.setValue(0d);
           txtDescontoAtacado.setValue(0d);
           txtDescontoAvulsoVLR.setValue(0d);           
           txtDescontoAvulsoPerc.setValue(0d);
           txtAcrescimoAvulsoPerc.setValue(0d);
           txtAcrescimoAvulsoVLR.setValue(0d);
           txtAcrescimoPagto.setValue(0d);
        }
        txtValorCreditoTroca.setValue(0d);
        //Float ValorFinal =   ValorTotalItens - DescontoTotal - ValorCreditoTroca + AcrescimoPagto + AcrescimoVLR - DescontoPreVenda;
        
        AlterarDescontosEAcrescimo(DescontoTotalPerc, DescontoRestaurado,DescontoAtacado, ValorTotalRecebimento, ValorCreditoTroca, CodigoCreditoTroca,AcrescimoVLR,AcrescimoPRC,AcrescimoPagto,DescontoPreVenda);

        //getFrmDesconto().txtAcrecVal.setValue(0);
        //getFrmDesconto().txtAcrescPerc.setValue(0);
        txtValorVenda.setValue(ValorTotalRecebimento);
        //txtValorFinal.setValue(ValorFinal);
        AtualizaValorFinalVenda();
        
        if(!IniciarGridPagtos()){ return false;}
            
        if(!IniciarGridPagtos_Atualizar()){ return false;}

            
        
        btListaForma.requestFocus();
        
        bRet=true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    return bRet;
}    
private void PrepararInterface_Recebimento(){
   
    try {
        lblTitulo.setText("RECEBIMENTO");
        
        lblSubTitulo.setText("VLR DO RECEBIMENTO");
        
        PainelTroca.setVisible(false);
        PainelDescontos.setVisible(false);
        PainelAcrescimo.setVisible(false);
        lblDesconto.setVisible(false);        
        txtDescEAcrescVal.setVisible(false);
        lblSinalMenosDesconto.setVisible(false);
        lblAcrescimoTotal.setVisible(false);        
        txtAcrescimo.setVisible(false);
        lblSinalMenosAcrescimo.setVisible(false);
        lblTituloCampoValorFinal.setVisible(false);
        lblValorFinal.setVisible(false);
        lblSinalIgual.setVisible(false);
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
   
}   
private void PrepararInterface_Venda(){
   
    try {
        lblTitulo.setText("FECHAMENTO DA VENDA");
        lblSubTitulo.setText("VLR DA VENDA");
        PainelTroca.setVisible(true);
        PainelDescontos.setVisible(false);
        PainelAcrescimo.setVisible(false);
        lblDesconto.setVisible(true);        
        txtDescEAcrescVal.setVisible(true);
        lblSinalMenosDesconto.setVisible(true);
        lblAcrescimoTotal.setVisible(true);        
        txtAcrescimo.setVisible(true);
        lblSinalMenosAcrescimo.setVisible(true);
        lblTituloCampoValorFinal.setVisible(true);
        lblValorFinal.setVisible(true);
        lblSinalIgual.setVisible(true);
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
   
}  
private boolean PrepararFechamento(int MomentoVenda)
{
    boolean bRet=false;
    
    this.Pagto_MomentoDaVendaTEFInterrompida_PDV= MomentoVenda;
    
    try {
        if(MomentoVenda<=1){
            if(!PagtoorcRN.PagtoOrc_Excluir(getDadosorc().getCodigo(),Long.valueOf(0),true)){
               return false;
            }
        }
        Double DescontoAtacado =0d;
        Double DescontoRestaurado =0d;
        Double DescontoAtacadoRestaurado =0d;
        Double DescontoPreVenda =0d;
        Double AcrescimoVLR =0d,AcrescimoPRC=0d,AcrescimoPagto =0d;
        Long CodigoCreditoTroca=0l;
        Double ValorCreditoTroca =0d;

        PrepararInterface_Venda();
        
        Double ValorTotalItens = ItensorcRN.Itensorc_Somar( getDadosorc().getCodigo());
        if(MomentoVenda>1){
            ResultSet rsVendaInterrompida = VendaEmEdicao.VerificarVendaInterrompida();
            if(rsVendaInterrompida.next()){
                DescontoRestaurado = br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("Desconto"),0d);
                DescontoAtacadoRestaurado = br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("DescontoAtacado"),0d);
                AcrescimoVLR = br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("acrescimo_avulso_vlr"),0d);
                AcrescimoPRC = br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("acrescimo_avulso_perc"),0d);
                AcrescimoPagto =  br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("acrescimo_pagto_vlr"),0d);
                DescontoPreVenda =  br.TratamentoNulo.TratamentoNulos.getTratarDouble().Tratar( rsVendaInterrompida.getDouble("Descontoprevenda"),0d);
                DescontoAtacado =SomarDescontoAtacado(getDadosorc().getCodigo(), Sistema.isOnline());               
            }
        }else{
            boolean descontoAtacadoAtivado =false;
            ResultSet dadosLojaAtualSistema = Sistema.getDadosLojaAtualSistema();
            if(dadosLojaAtualSistema!=null){
                descontoAtacadoAtivado = dadosLojaAtualSistema.getBoolean("descatacado");
            }
            if (descontoAtacadoAtivado) {
                
                    DescontoAtacado = SomarDescontoAtacado(getDadosorc().getCodigo(), Sistema.isOnline());
                                            
                
                    rsDescontoAtacado = null;
               
            } else {
                rsDescontoAtacado = null;
            }
        }
        ResultSet rsVendaAtual = VendaEmEdicao.VerificarVendaInterrompida();
        if(!rsVendaAtual.next()) { return false; }
        String PreCodigoPreVenda =TratamentoNulos.getTratarString().Tratar(rsVendaAtual.getString("prevenda"),"");
        if(PreCodigoPreVenda.trim().length()>0){
           CodigoPreVenda = new Long(PreCodigoPreVenda.split("-")[0]);
        }
        if(Sistema.isOnline()){                        
            CodigoCreditoTroca = rsVendaAtual.getLong("codigotroca");
            ValorCreditoTroca =Troca.ValorTotalCreditoTroca_PorID(CodigoCreditoTroca);
            this.CreditoTroca = ValorCreditoTroca;
            if(ValorCreditoTroca>0f){
                //CodigoCreditoTroca = Troca.RetornaTrocas_PorVenda(true, getDadosorc().getCodigo());
                this.CodigoTroca=CodigoCreditoTroca;
            }else{
                this.CodigoTroca =0l;
            }            
            DescontoPreVenda = PreVenda.SomarDescontoPreVenda(PreVendasSelecionadas.getNotasSelecionadas());
            
        }
        else{
           if(CodigoPreVenda>0l){
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "PRÉ-VENDA NÃO PODE SER FINALIZADA EM MODO OFF-LINE", "PRÉ-VENDA");
              return false;
           } 
           this.CreditoTroca = 0d;
           this.CodigoTroca =0l;
           
        }
        Double DescontoTotal =0d,DescontoTotalPerc=0d;                    
        DescontoTotal = DescontoAtacado+DescontoRestaurado+DescontoPreVenda;
        if(DescontoTotal>0d){
           DescontoTotalPerc = NumeroArredondar.Arredondar_Double(DescontoTotal/ValorTotalItens,2);
           txtDescEAcrescVal.setValue(DescontoTotal);
           txtDescontoAtacado.setValue(DescontoAtacado);
           txtDescontoAvulsoVLR.setValue(DescontoRestaurado);  
           Double PercDesconto = NumeroArredondar.Arredondar_Double(DescontoRestaurado/ValorTotalItens,2)*100;
           //if(DescontoAtacado>0f){
           txtDescontoAvulsoPerc.setValue(PercDesconto);
           //CalcularDescontoPercentual_Acao("Valor");
           txtAcrescimoPagto.setValue(AcrescimoPagto);
           txtAcrescimoAvulsoPerc.setValue(AcrescimoPRC);
           txtAcrescimoAvulsoVLR.setValue(AcrescimoVLR);      
           txtDescontoPreVenda.setValue(DescontoPreVenda);
        }else{
           txtDescEAcrescVal.setValue(0d);
           txtDescontoAtacado.setValue(0d);
           txtDescontoAvulsoVLR.setValue(0d);           
           txtDescontoAvulsoPerc.setValue(0d);
           txtAcrescimoAvulsoPerc.setValue(0d);
           txtAcrescimoAvulsoVLR.setValue(0d);
           txtAcrescimoPagto.setValue(0d);
        }
        txtValorCreditoTroca.setValue(ValorCreditoTroca);
        //Float ValorFinal =   ValorTotalItens - DescontoTotal - ValorCreditoTroca + AcrescimoPagto + AcrescimoVLR - DescontoPreVenda;
        
        AlterarDescontosEAcrescimo(DescontoTotalPerc, DescontoRestaurado,DescontoAtacado, ValorTotalItens, ValorCreditoTroca, CodigoCreditoTroca,AcrescimoVLR,AcrescimoPRC,AcrescimoPagto,DescontoPreVenda);

        //getFrmDesconto().txtAcrecVal.setValue(0);
        //getFrmDesconto().txtAcrescPerc.setValue(0);
        txtValorVenda.setValue(ValorTotalItens);
        //txtValorFinal.setValue(ValorFinal);
        AtualizaValorFinalVenda();
        
        if(!IniciarGridPagtos()){ return false;}
            
        if(!IniciarGridPagtos_Atualizar()){ return false;}

        if(VerificarNFCeEmDigitacao(true)){            
            Bloq_DesBloq_TelaNFCeEnviada(false);        
            btGravarVenda.setEnabled(true);
            //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Existe uma NFCe finalizada.\n\nFinalize a venda", "NFCe - Finalizada");
        }else{
           Bloq_DesBloq_TelaNFCeEnviada(true);        
        }
        
        btListaForma.requestFocus();
        
        bRet=true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    return bRet;
}
private Double SomarDescontoAtacado(Long nCodigoVenda, boolean sistema){
    Double  Desconto =0d;
    try {
        
        Desconto = Venda.DescontoAtacado_ListarItem_Total(nCodigoVenda,sistema);
        
        return NumeroArredondar.Arredondar_Double(Desconto,2);
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        return Desconto;
    }

}/*
private boolean EfetivarVenda(int MomentoVenda) {
      
        //bloq.Tela_DesBloquear();
        
        //bloq.Tela_Bloquear(this,0.4f);        
        //setVisible(false);
        boolean bRetorno = EfetivarVenda_Acao(MomentoVenda);        
         
        //bloq.Tela_DesBloquear();
        return bRetorno;
}*/
private String SolicitarComanda(){
    String Ret = "";
    try {
        DefaultFormatterFactory mascara = new DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#########")));
    
        Ret  = MetodosUI_Auxiliares_1.CapturarTexto("COMANDA DE VENDA", "Digite o número da comanda",mascara );

    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
private boolean EfetivarVenda_Acao(int MomentoVenda) {
    boolean bRet=false;

    try {
        Double ValorFinal = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  lblValorFinal.getValue(),0d).toString());
        
        ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
        Long CodigoTroca =0l;
        if(rs.next()){
           CodigoTroca = TratamentoNulos.getTratarLong().Tratar(rs.getLong("codigotroca"),0l);
        }
        if(ValorFinal<0d){
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL FECHAR UMA VENDA COM VALOR NEGATIVO", "VALOR FINAL NEGATIVO"); 
           return false;   
        }
        if(ValorFinal==0d && CodigoTroca == 0d){
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "VENDA COM VALOR 0(ZERO) SERÃO PERMITIDAS SOMENTE EM OPERAÇÕES DE TROCA", "VALOR FINAL ZERO"); 
           return false;   
        }        
        if(ValorFinal==0d && this.Pagto_TipoComprovante.equalsIgnoreCase("fiscal")){
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL EMITIR O CUPOM FISCAL COM VALOR 0(ZERO)", "VALOR FINAL 0(ZERO)"); 
           return false;   
        }
        Double nFaltaDistribuir = ValorFaltaDistribuir();        
        if(nFaltaDistribuir>0d){
           String ValorFormatado = FormatarNumeros.FormatarParaMoeda(nFaltaDistribuir);
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "Falta Receber o valor R$ " + ValorFormatado, "Valor Pendente");
           return false;
        }
        
        if(ParametrosGlobais.getPreVenda_Codigo().size()>0 && !Sistema.isOnline()){
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL EFETIVAR PRE-VENDA OU MESCLAGEM NO MODO OFFLINE", "SISTEMA OFF-LINE");  
            return false;
        }
        if(Venda.PDV_TEF_Habilitado()){
             if(TEF_VendaNoCartao_NaoAutorizada()){
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O TEF está ativo e existem vendas no cartão que não foram autorizadas\n\nFAVOR RETORNAR A VENDA PARA CONCLUIR A(S) TRANSAÇÃO(S)" , "VENDAS CARTÃO PENDENTE");                 
                 return false;
             }else{
                 if(TEF_VendaNoCartao_VendaCancelada()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EXISTE(M) VENDA(S) TEF CANCELADA(S)\n\nFINALIZE O PROCESSO DE CANCELAMENTO TEF" , "FINALIZAÇÃO INTERROMPIDA");    
                    return false;
                 }
             }            
        }       
        if (SetarValoresDadosOrc()) {            
            
            Boolean vendaCrediario = Venda.VerificaSeECrediario(getDadosorc().getCodigo());
            
            if( vendaCrediario){
                Long nCodigoCliente=0l;
                try {
                    nCodigoCliente =Long.parseLong(getDadosorc().getCodcliente());
                } catch (Exception e) {
                }
                if(nCodigoCliente>0){
                   if(!Crediario.VerificarLimite(Sistema.getLojaAtual(),  nCodigoCliente, getDadosorc().getCodigo())){
                      return false; 
                   }                        
                }else{
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "NÃO FOI IDENTIFICADO O CLIENTE", "VENDA CREDIARIO", "AVISO");
                    return false;
                }       
            }
            boolean bAlterarVerificacao =false;
            
            
            Long CodigoVenda = getDadosorc().getCodigo();
            boolean bImprimiuOK=true;
            
            String CPF_CNPJ_Armazendado  = ""; //TratamentoNulos.getTratarString().Tratar(getDadosorc().getNotaNome(), "");
            ResultSet rsVendaEmEdicao = VendaEmEdicao.VerificarVendaInterrompida();
            if(rsVendaEmEdicao!=null){
                if(!rsVendaEmEdicao.next()){
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi foi localizado o registro da venda em edição", "Fechamento");
                   return false;
                }
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível obter os dados da venda em edição", "Fechamento");
                return false;
            }
            if(Venda.VerificaObrigatoriedadeComanda()){
                String ComandaExistente = TratamentoNulos.getTratarString().Tratar(rsVendaEmEdicao.getString("cliente_comanda"),"");
                if(ComandaExistente.equalsIgnoreCase("")){
                    String Comanda =SolicitarComanda();
                    if(Comanda.trim().equalsIgnoreCase("")){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("PARA PROSSERGUIR COM ESTA OPERAÇÃO INFORME A COMANDA", "COMANDA OBRIGATÓRIA");                   
                       return false; 
                    }else{
                        if(Venda.VerificarExistenciaComanda(Comanda,Sistema.getLojaAtual())){
                            return false;
                        }else{
                            if(!VendaEmEdicao.RegistrarComanda(CodigoVenda, Comanda)){
                                  MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível registrar a comanda desta venda", "COMANDA");
                                  return false;
                             }
                            getDadosorc().setNumerocomanda(Comanda);
                        }
                    } 
                }else{
                    getDadosorc().setNumerocomanda(ComandaExistente);
                }
            }           
            CPF_CNPJ_Armazendado =TratamentoNulos.getTratarString().Tratar(rsVendaEmEdicao.getString("cliente_cpf_cnpj"),"");
                
            String cCNPJ_Ou_CPF = "";
          
            if(CPF_CNPJ_Armazendado.equalsIgnoreCase("")){              
                if (this.Pagto_TipoComprovante.equalsIgnoreCase("nfce")) {                                    
                    String CodCliente = TratamentoNulos.getTratarString().Tratar(getDadosorc().getCodcliente(), "");
                    if (CodCliente.equalsIgnoreCase("") || String.valueOf(Sistema.ClientePadrao()).equalsIgnoreCase(CodCliente)) {
                        cCNPJ_Ou_CPF = PDVComprovante.capturarCPF_Ou_CNPJ();
                        if (cCNPJ_Ou_CPF.equalsIgnoreCase("ignorar") || cCNPJ_Ou_CPF.equalsIgnoreCase("invalido")) {
                            return false;
                        }else if(cCNPJ_Ou_CPF.equalsIgnoreCase("prosseguir")){
                            cCNPJ_Ou_CPF="";
                        }else{
                            if(!VendaEmEdicao.RegistrarCPF_CNPJ(CodigoVenda, cCNPJ_Ou_CPF)){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível registrar o cpf/cnpj desta venda", "CPF/CNPJ");
                                return false;
                            }
                        }
                    }else{
                        if (!CodCliente.equalsIgnoreCase("") && !String.valueOf(Sistema.ClientePadrao()).equalsIgnoreCase(CodCliente)) {
                            ResultSet rsCliente = clientes.Listar(Sistema.getLojaAtual(), CodCliente);
                            if (rsCliente.next()) {
                                cCNPJ_Ou_CPF = ManipulacaoString.DeixarSomenteNumeros(TratamentoNulos.getTratarString().Tratar(rsCliente.getString("cpf"), ""));
                            }
                        }
                    }                    
                    getDadosorc().setNotaNome(cCNPJ_Ou_CPF);
                }else{
                    getDadosorc().setNotaNome("");
                }
            }else{
                cCNPJ_Ou_CPF=CPF_CNPJ_Armazendado;
            }

          
            if(this.Pagto_ImprimirComprovante || this.Pagto_TipoComprovante.equalsIgnoreCase("nfce")){
                String QRCode ="";
                if(this.Pagto_TipoComprovante.equalsIgnoreCase("nfce")){
                    
                    QRCode = TratamentoNulos.getTratarString().Tratar(EnviarNFCe(getDadosorc(), cCNPJ_Ou_CPF),"");
                   
                    if(QRCode.equalsIgnoreCase("")){
                        return false;
                    }
                }  
                
                bRet=PDVComprovante.FecharCupomVenda(getDadosorc(),MomentoVenda,this.Pagto_TipoComprovante,Pagto_ecf,Pagto_ECFDisponivel,CodigoTroca,false, ParametrosGlobais.getPreVenda_Codigo(),QRCode,Pagto_ImpressoraComprovantes,jasperNFce);
                
                if(vendaCrediario && Pagto_ViasCompVDaCrediario>1){
                    for (int i = 0; i < Pagto_ViasCompVDaCrediario-1; i++) {
                          bRet=PDVComprovante.FecharCupomVenda(getDadosorc(),MomentoVenda,this.Pagto_TipoComprovante,Pagto_ecf,Pagto_ECFDisponivel,CodigoTroca,false, ParametrosGlobais.getPreVenda_Codigo(),QRCode,Pagto_ImpressoraComprovantes,jasperNFce);
                    }
                }
                if(this.Pagto_TipoComprovante.equalsIgnoreCase("nfce")){
                    if(NFCE_Contingencia.Contingencia(pdvgerenciar.CodigoPDV())){
                       VendaEmEdicao.RegistrarItemImpresso(CodigoVenda, 0);     
                       if(bRet){
                         bRet=PDVComprovante.FecharCupomVenda(getDadosorc(),MomentoVenda,this.Pagto_TipoComprovante,Pagto_ecf,Pagto_ECFDisponivel,CodigoTroca,false, ParametrosGlobais.getPreVenda_Codigo(),QRCode,Pagto_ImpressoraComprovantes,jasperNFce); 
                       }
                    }
                }
                bImprimiuOK=true;
            }
            if(bImprimiuOK){
                System.out.println("INICIANDO EFETIVAÇÃO VENDA " + getDadosorc().getCodigo() + " - "+  DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                //
                Pagto_CodigoTrocaVenda=0l;
              
                if (Venda.Venda_Efetivar(getDadosorc(), Pagto_Itensorc, Venda.CodigoTipoMovEstoqueSaidaPorVenda, CodigoTroca,pdvgerenciar.CodigoPDV(),SincronizarMovimento.EnviandoMovimento,ParametrosGlobais.getPreVenda_Codigo() )) {                
                    Pagto_CodigoTrocaVenda=CodigoTroca;
                    if(getDadosorc().getTroco()!=null){
                        Pagto_Troco = new Double(getDadosorc().getTroco().toString());
                    }
                    bRet=true;                    
                    VendaEmEdicao.FinalizarNota(CodigoVenda, true,false); 
                    boolean ModoOperacional = Dao_Jdbc_1.getConexao().TestarConexao();
                    if(ModoOperacional){            
                        EnviarVendaServidor(CodigoVenda, CodigoTroca);
                    }
                    AcionarGaveta();
                    
                    System.out.println("TERMINADO EFETIVAÇÃO " + getDadosorc().getCodigo() + " - "+  DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));                               
                }else{
                    
                    System.out.println("FALHA TERMINADO EFETIVAÇÃO " + getDadosorc().getCodigo() + " - "+  DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));                               
                    bRet=false;
                }
            }else{
                bRet=false;
            }            
            
            //if(bAlterarVerificacao)
            //{
            //}
        }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    return bRet;
}
public Thread TarefaFechamentoVenda;
public Thread getTarefaFechamentoVenda(){
        return TarefaFechamentoVenda;
}
/*
private boolean EnviarNFCe_VerificarSefaz_RegistrarNFCe(
        byte[] _Xml, 
        String ChaveDeAcesso,
        String Protocolo , 
        NFNotaInfoIdentificacao ide, 
        int Loja,int PDV,
        Long CodigoNota,
        java.util.Date DataHoraRecebimento, 
        String QRCode){
    boolean Ret = false;
    try {
        
        String Xml = new String(_Xml);
        
        Ret = NFCE_Configurar.ProcessarNotaEnviada_Acao(Protocolo,
                                            ChaveDeAcesso,
                                            new java.sql.Date(DataHoraRecebimento.getTime()),                                            
                                            ide, 
                                            QRCode, Loja, PDV, Xml,CodigoNota);
        if(!Ret){
           MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível registrar a nfce interrompida", "NFCe Enviar");            
        }

            
      
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
private String EnviarNFCe_VerificarSefaz(byte[] Xml, String ChaveDeAcesso,Long CodigoVenda, int PDV, int Loja){
    String Ret = "";
    try {      
        ResultSet rsNotaEnviada = NFCE_Configurar.NotaEnviada(CodigoVenda, pdvgerenciar.CodigoPDV());
        if (rsNotaEnviada != null) {
            if (rsNotaEnviada.next()) {
                //processo interrompido depois do envio do lote
            } else {
                //processo interrompido antes do envio do lote. CONSULTAR CHAVE DE ACESSO
                String _QRCode ="";
                Document docXml = Xml_Util.getDocXml(Xml);
                if (docXml != null) {
                    _QRCode = Xml_Util.ProcurarTagEmXML(docXml, "qrCode");
                    if (_QRCode.equalsIgnoreCase("")) {                        
                        MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível obter do xml o QRCode da nfce interrompida", "NFCe Enviar");
                        return "";
                    }
                } else {
                    MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível obter o xml da nfce interrompida", "NFCe Enviar");
                    return "";
                }
                NFCE_ConsultarNota consultar = new NFCE_ConsultarNota();
                if (consultar.Consultar_Acao(ChaveDeAcesso, NFCe_ConfiguracaoAmbiente.getConfig().getCUF())) {
                    NFProtocolo protocolo = consultar.getRetorno().getProtocolo();
                    String StatusCons = consultar.getRetorno().getStatus();
                    boolean EnviarNFCe = false;
                    
                    if (StatusCons.equalsIgnoreCase("103") || StatusCons.equalsIgnoreCase("217")) {    
                        EnviarNFCe=true;                  
                    }
                    else{
                        StatusCons = protocolo.getProtocoloInfo().getStatus();
                        if(!StatusCons.equalsIgnoreCase("100")){
                            EnviarNFCe=true;
                        }else{
                            // NFCe ja esta na sefaz. Registrar banco
                            //DataHoraRecebimento
                            String _Xml = new String(Xml);
                            
                            NFNota loteenv =(NFNota)XStream_Api.ConverterXmlParaObjeto(_Xml,new NFNota());
                           
                            if(loteenv==null){                                
                              MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível obter a nfe do xml da nfce interrompida", "NFCe Enviar");
                              return "";
                            }
                            LocalDateTime DataHoraRecebimento = protocolo.getProtocoloInfo().getDataRecebimento();
                            String Protocolo =  protocolo.getProtocoloInfo().getNumeroProtocolo();
                            if(EnviarNFCe_VerificarSefaz_RegistrarNFCe(
                                        Xml,  
                                        ChaveDeAcesso, 
                                        Protocolo ,  
                                        loteenv.getInfo().getIdentificacao(),                                        
                                        Loja, 
                                        PDV, 
                                        CodigoVenda, 
                                        DataHoraRecebimento.toDate(), _QRCode)){
                                Ret = _QRCode;
                            }
                        }
                    }                    
                    //Nao foi Enviada. Envia XML
                    if(EnviarNFCe){
                        EnviarNFCe _enviarNFCe = new EnviarNFCe();
                        boolean RetEnvio = _enviarNFCe.EnviarNFE_ViaXML_Acao_1(CodigoVenda, ChaveDeAcesso, Xml, PDV);
                        if(RetEnvio){
                            if(_enviarNFCe.getProtocolo()!=null){
                                List<NFProtocolo> protocolos = _enviarNFCe.getProtocolo();
                                if(protocolos.size()>0){
                                   NFProtocolo _protocolo  = protocolos.get(0);
                                   String Status = _protocolo.getProtocoloInfo().getStatus();
                                   if(Status.equalsIgnoreCase("100")){
                                      Ret = _QRCode; 
                                   }
                                }
                            }else{
                                MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível obter o protocolo da nfce interrompida", "NFCe Enviar");                   
                                return "";
                            }
                            
                        }else{
                            MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível enviar a nfce interrompida\n\n" + _enviarNFCe.getMsgErro(), "NFCe Enviar");                   
                            return "";
                        }
                    }                    
                } else {
                    MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível consultar o nfce interrompida", "NFCe Enviar");                   
                    return "";
                }
            }
        }
       
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}*/
private ActionListener getInterromperEnvio_ActionListerner(){
    ActionListener Ret = null;
    try {

        Ret = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TarefaFechamentoVenda.stop();
                btListaForma.setEnabled(true);         
                Bloq_DesBloq_TelaDuranteProcessamento(true);
                IniciarGridPagtos_Atualizar();
               
            }
        };

    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
private String EnviarNFCe(Dadosorc d,String cCNPJ_Ou_CPF) {
    String Ret = "";
    try {
       bloq.Tela_Bloquear(this, 0.5f, Color.BLACK);
       Ret = EnviarNFCe_1(d, cCNPJ_Ou_CPF);
       bloq.Tela_DesBloquear();
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;    
    
}
private String EnviarNFCe_1(Dadosorc d,String cCNPJ_Ou_CPF) {
    String Ret = "";
    try {
        boolean ContingenciaAtivada =  NFCE_Contingencia.Contingencia(d.getPdv());
        EnviarNFE_Processar envionfce = new EnviarNFE_Processar("Enviando NFC-e - Codigo : " + d.getCodigo(),ContingenciaAtivada);
        envionfce.setDadosorc(d);
        envionfce.setcCNPJ_Ou_CPF(cCNPJ_Ou_CPF);
        frmEnviarNFCE_PDV enviandonfce = new frmEnviarNFCE_PDV(null, true, envionfce, d.getCodigo());
        enviandonfce.lblMensagem.setText("Enviando NFC-e..... Aguarde");
        enviandonfce.lblTitulo.setText("Cupom Eletrônico");
        Point p = this.getLocation();
        Float Y = new Float(p.y) * 2.5f;
        Float X = new Float(p.x) * 1.1f;
        p.setLocation(X.intValue(), Y.intValue());
        enviandonfce.setLocation(p);
        enviandonfce.setVisible(true);
        Ret = enviandonfce.getRetornoEnvio();
           
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
           
}
public class EnviarNFE_Processar extends Thread  {
        private EnviarNFCe _enviarNFCe;
        private Dadosorc d;
        private String cCNPJ_Ou_CPF;
        private ActionListener EncerrouEnvio;
        private String RetornoEnvio;
        private boolean interrompido=false;
        private boolean EmitirEmContingencia=false;
        private boolean ExecutarTarefa=false;
        public boolean getEmitirEmContingencia(){
            return EmitirEmContingencia;
        }
        public void setEmitirEmContingencia(boolean  EmitirEmContingencia){
            this.EmitirEmContingencia=EmitirEmContingencia;
        }
        public boolean getInterrompido(){return interrompido;}
        public void setInterrompido( boolean interrompido){
             this.interrompido=interrompido;
        }
        public EnviarNFE_Processar(String Nome, boolean ContingenciaAtivada){
            this.setName(Nome);
            EmitirEmContingencia =ContingenciaAtivada;
        }
        private boolean InterromperEnvio=false;
        
        public EnviarNFCe getEnviarNFCe(){
            return _enviarNFCe;
        }
        
        @Override
        public void run() {
            try {        
                EmitirEmContingencia=false;
                InterromperEnvio=false;
                ExecutarTarefa=true;
                while(isExecutarTarefa()){
                    if(!isInterromperEnvio()){
                        RetornoEnvio = EnviarNFCe(getEmitirEmContingencia());
                        setEmitirEmContingencia(false);
                        if(getInterrompido()){
                           EncerrouEnvio.actionPerformed(null);
                           return;
                        }else{
                            if(RetornoEnvio.equalsIgnoreCase("")){
                               setInterromperEnvio(true);
                               if(_enviarNFCe.isErroXML()){
                                  EncerrouEnvio.actionPerformed(new ActionEvent(this,0,"Nao Enviou-ErroXml")); 
                               }else{
                                   EncerrouEnvio.actionPerformed(new ActionEvent(this,0,"Nao Enviou"));
                               }
                            }else{
                                 EncerrouEnvio.actionPerformed(null);
                                 return;
                            }
                        }
                    }
                    sleep(1000);
                }
                
         }catch (Exception e) {
                LogDinnamus.Log(e);
            }
          
        }
        public String getRetornoEnvio(){
            return RetornoEnvio;
        }
        public void setEncerrarEnvioListener(ActionListener EncerrouEnvio){
            this.EncerrouEnvio =EncerrouEnvio;
        }
        public void setDadosorc(Dadosorc d){
            this.d=d;
        }
        public void setcCNPJ_Ou_CPF(String cCNPJ_Ou_CPF){
            this.cCNPJ_Ou_CPF=cCNPJ_Ou_CPF;
        }
         
        
        private String EnviarNFCe(boolean EmitirEmContigencia) {
            String Ret = "";
            try {
                String QRCode = "";

                String CodCliente = TratamentoNulos.getTratarString().Tratar(d.getCodcliente(), "");
                Long CodigoCliente = 0l, CodigoVenda = 0l;
                if (CodCliente.trim().length() != 0) {
                    CodigoCliente = Long.valueOf(CodigoCliente);
                }

                CodigoVenda = d.getCodigo();

                ResultSet rsVendaEmEdicao = VendaEmEdicao.VerificarVendaInterrompida(); //NFCE_Configurar.NotaEnviada(CodigoVenda, pdvgerenciar.CodigoPDV());
                List<NFProtocolo> protocolos = null;
                boolean RetEnvioNFce = false;

                if (rsVendaEmEdicao == null) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível obter os dados da NFCe", "NFCe Enviar");
                    return "";
                }
                byte[] Xml = null;
                String _Xml = "";
                NFLoteEnvio loteenv = null;

                Double _ImpostoNaNota = ImpostoNaNota.CalcularImposto(CodigoVenda);

                 _enviarNFCe = new EnviarNFCe();

                if (!rsVendaEmEdicao.next()) {
                    return "";
                }

                String ChaveDeAcesso = TratamentoNulos.getTratarString().Tratar(rsVendaEmEdicao.getString("nfce_chavedeacesso"), "");

                 if(!ChaveDeAcesso.equalsIgnoreCase("")){
                    ResultSet rsNFCeEmContigencia = DAO_RepositorioLocal.GerarResultSet("select  dadosqrcode from off_nfce_pdv_notas where codigovenda="+ - Math.abs(CodigoVenda));
                    if(rsNFCeEmContigencia.next()){
                        String QRCdode = rsNFCeEmContigencia.getString("dadosqrcode");
                        return QRCdode;
                    }
                }
                
                Xml = rsVendaEmEdicao.getBytes("nfce_xml");
                if (Xml != null) {
                    if (Xml.length > 0) {
                        _Xml = new String(Xml);
                        loteenv = new NotaParser().LoteParaObjeto(_Xml);
                    }
                }
                 String versao = NFCe_ConfiguracaoAmbiente.getVersaoNFCe( d.getPdv());
                 if (EmitirEmContigencia) {
                    //verificar se esta em contigencia
                    //if (NFCE_Contingencia.Contingencia(d.getPdv())) {
                        //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Existe uma NFCe interrompida mas o sistema está em contingência.\n\nEsta NFCe será Emitida em Contingencia", "NFCe Contingência");
                       
                        boolean RetEnvioContingencia = _enviarNFCe.Nfce_Processar_Contingencia(
                                CodigoVenda,
                                d.getPdv(),
                                d.getLoja(),
                                ChaveDeAcesso,
                                loteenv.getNotas().get(0).getInfo().getIdentificacao(),
                                CodigoCliente,
                                cCNPJ_Ou_CPF,
                                d.getDesconto(),
                                _Xml, _ImpostoNaNota,versao, d.getAcrescimo());
                        if (!RetEnvioContingencia) {
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível emitir a NFCe em Contingencia", "NFCe Contingência");
                            return "";
                        } else {
                            return _enviarNFCe.getQRCode();
                        }
                   // }
                 }
                
                if (!ChaveDeAcesso.equalsIgnoreCase("")) {
                    //verificar se esta em contigencia
                    
                    
                    if (NFCE_Contingencia.Contingencia(d.getPdv())) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Existe uma NFCe interrompida mas o sistema está em contingência.\n\nEsta NFCe será Emitida em Contingencia", "NFCe Contingência");
                        boolean RetEnvioContingencia = _enviarNFCe.Nfce_Processar_Contingencia(
                                CodigoVenda,
                                d.getPdv(),
                                d.getLoja(),
                                ChaveDeAcesso,
                                loteenv.getNotas().get(0).getInfo().getIdentificacao(),
                                CodigoCliente,
                                cCNPJ_Ou_CPF,
                                d.getDesconto(),
                                _Xml, _ImpostoNaNota,versao, d.getAcrescimo());
                        if (!RetEnvioContingencia) {
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível emitir a NFCe em Contingencia", "NFCe Contingência");
                            return "";
                        } else {
                            return _enviarNFCe.getQRCode();
                        }
                    }

                    //Venda NFCe Interrompida
                    HashMap<String, Object> RetVerificacao = _enviarNFCe.Verificar_NFCE_Sefaz(ChaveDeAcesso, NFCe_ConfiguracaoAmbiente.getConfig().getCUF());
                    
                    //if(getInterrompido()){ return "";}
                    
                    if (RetVerificacao != null) {
                        //REGISTRA A NFCE NO SISTEMA
                        String statusverificacao = RetVerificacao.get("status").toString();
                        if (statusverificacao.equalsIgnoreCase("ja_enviada")) {
                            NFNotaConsultaRetorno retornosefaz = (NFNotaConsultaRetorno) RetVerificacao.get("retorno");
                            String Protocolo = retornosefaz.getProtocolo().getProtocoloInfo().getNumeroProtocolo();
                            java.util.Date DataHoraRecebimento = retornosefaz.getProtocolo().getProtocoloInfo().getDataRecebimento().toDate();

                            QRCode = loteenv.getNotas().get(0).getInfosupl().getQrCode();
                            if (_enviarNFCe.EnviarNFCe_VerificarSefaz_RegistrarNFCe(
                                    Xml,
                                    ChaveDeAcesso,
                                    Protocolo,
                                    loteenv.getNotas().get(0).getInfo().getIdentificacao(),
                                    d.getLoja(),
                                    d.getPdv(),
                                    CodigoVenda,
                                    DataHoraRecebimento, QRCode,versao)) {
                                return QRCode;
                            } else {
                                return "";
                            }
                        }
                    } else {
                       /* MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível consultar o nfce interrompida\n\nESTA NOTA SERÁ EMITIDA EM CONTIGENCIA", "NFCe Enviar");
                        boolean RetEnvioContingencia = _enviarNFCe.Nfce_Processar_Contingencia(
                                CodigoVenda,
                                d.getPdv(),
                                d.getLoja(),
                                ChaveDeAcesso,
                                loteenv.getNotas().get(0).getInfo().getIdentificacao(),
                                CodigoCliente,
                                cCNPJ_Ou_CPF,
                                d.getDesconto(),
                                _Xml, _ImpostoNaNota);
                        if (!RetEnvioContingencia) {
                            MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível emitir a NFCe em Contingencia", "NFCe Contingência");
                            return "";
                        } else {
                            return _enviarNFCe.getQRCode();
                        }*/
                        return "";
                    }
                }
                //if(getInterrompido()){ return "";}
                


                 RetEnvioNFce = _enviarNFCe.EnviarNFE(CodigoVenda, pdvgerenciar.CodigoPDV(), CodigoCliente, cCNPJ_Ou_CPF, d.getDesconto(), _ImpostoNaNota,d.getAcrescimo());
                
                if(getInterrompido()){ return "";}
                
                protocolos = _enviarNFCe.getProtocolo();
                if (!RetEnvioNFce) {
                    //VendaEmEdicao.RegistrarVendaEmEdicao_NFCe(CodigoVenda, "");
                    if (protocolos != null) {
                        if (protocolos.size() > 0) {
                            for (Iterator<NFProtocolo> it = protocolos.iterator(); it.hasNext();) {
                                NFProtocolo protocolo = it.next();
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Status : " + protocolo.getProtocoloInfo().getStatus() + "\nMotivo: " + protocolo.getProtocoloInfo().getMotivo(), "NFCe Não Enviada - Retorno Sefaz");
                            }
                        }
                    }
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("Não foi possível enviar a NFCe","NFCe Não Enviada");
                    return "";
                } else {
                    if (protocolos != null) {
                        if (protocolos.size() > 0) {
                            for (Iterator<NFProtocolo> it = protocolos.iterator(); it.hasNext();) {
                                NFProtocolo protocolo = it.next();
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("Mensagem : " + protocolo.getProtocoloInfo().getMotivo() + "\nProtocolo : " + protocolo.getProtocoloInfo().getNumeroProtocolo() + "\nData/Hora:" + protocolo.getProtocoloInfo().getDataRecebimento().toString(), "NFCe Enviada com Sucesso");
                            }
                        }
                    } else {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("NFCe Emitida em CONTINGÊNCIA", "NFCe OK");
                    }
                    QRCode = _enviarNFCe.getQRCode();
                    Ret = QRCode;
                }

            } catch (Exception e) {
                LogDinnamus.Log(e, true);
            }
            return Ret;
        }

        /**
         * @return the InterromperEnvio
         */
        public boolean isInterromperEnvio() {
            return InterromperEnvio;
        }

        /**
         * @param InterromperEnvio the InterromperEnvio to set
         */
        public void setInterromperEnvio(boolean InterromperEnvio) {
            this.InterromperEnvio = InterromperEnvio;
        }

        /**
         * @return the TerminarTarefa
         */
        public boolean isExecutarTarefa() {
            return ExecutarTarefa;
        }

        /**
         * @param TerminarTarefa the TerminarTarefa to set
         */
        public void setExecutarTarefa(boolean ExecutarTarefa) {
            this.ExecutarTarefa = ExecutarTarefa;
        }
    };

        
private void EnviarVendaServidor(  Long CodigoVenda ,   Long CodigoTroca){
        try {                                 
            int nTotalVendasPendentes =  SincronizarMovimento.VerificarVendasPendentesDeSincronismo();
            if(nTotalVendasPendentes>1){
               System.out.println("VENDAS PENDENTES: " + nTotalVendasPendentes);
               SincronizarMovimento.Iniciar(false, false);   
            }else{
               System.out.println("INICIANDO SINCRONISMO DA VENDA : " + CodigoVenda);
               SincronizarMovimento.Iniciar(false, false,true,CodigoVenda,CodigoTroca);                                                                                          
               System.out.println("TERMINANDO SINCRONISMO DA VENDA : " + CodigoVenda);                           
            }                  
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
    }
private boolean EfetivarCrediario_Acao(int MomentoVenda, BaixarConta b, Date DataPagamento) {
    boolean bRet=false;

    try {
        Double ValorFinal = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  lblValorFinal.getValue(),0d).toString());
        
        ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
        
        if(ValorFinal<=0d){
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSÍVEL FECHAR UM RECEBIMENTO COM VALOR ZERO(0) OU NEGATIVO", "RECEBIMENTO: VALOR FINAL INVÁLIDO"); 
           return false;   
        }        
        
        Double nFaltaDistribuir = ValorFaltaDistribuir();        
        if(nFaltaDistribuir>0d){
           String ValorFormatado = FormatarNumeros.FormatarParaMoeda(nFaltaDistribuir);
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "Falta Receber o valor R$ " + ValorFormatado, "Valor Pendente");
           return false;
        }
     
        if(Venda.PDV_TEF_Habilitado()){
             if(TEF_VendaNoCartao_NaoAutorizada()){
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "O TEF está ativo e existem RECEBIMENTO no cartão que não foram autorizadas\n\nFAVOR RETORNAR AO RECEBIMENTO PARA CONCLUIR A(S) TRANSAÇÃO(S)" , "RECEBIMENTO DE CARTÃO PENDENTE");                 
                 return false;
             }else{
                 if(TEF_VendaNoCartao_VendaCancelada()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "EXISTE(M) RECEBIMENTO(S) TEF CANCELADOS(S)\n\nFINALIZE O PROCESSO DE CANCELAMENTO TEF" , "FINALIZAÇÃO INTERROMPIDA");    
                    return false;
                 }
             }            
        }       
        if (SetarValoresDadosOrc()) {   
            boolean bAlterarVerificacao =false;
            if(VerificarStatusServidor.isVerificarServidor()){
                VerificarStatusServidor.setVerificarServidor(false);
                bAlterarVerificacao=true;
            }            
            
            boolean bImprimiuOK=true;          
            
            if(this.Pagto_ImprimirComprovante){
                //progressovenda.AtualizarProgresso("GRAVANDO VENDA...FINALIZANDO COMPROVANTE", 1);
                bRet=PDVComprovante.FecharCupomRecebimento(getDadosorc(),rsDescontoAtacado,MomentoVenda,this.Pagto_TipoComprovante,Pagto_ecf,Pagto_ECFDisponivel,CodigoTroca,false, b,Pagto_ImpressoraComprovantes);
                
                if(!bRet){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível finalizar o comprovante ", "Recebimento sem Impressão", "Aviso");                    
                    bImprimiuOK=false;
                }else{
                    bRet=PDVComprovante.FecharCupomRecebimento(getDadosorc(),rsDescontoAtacado,MomentoVenda,this.Pagto_TipoComprovante,Pagto_ecf,Pagto_ECFDisponivel,CodigoTroca,false, b,Pagto_ImpressoraComprovantes);
                     if(!bRet){
                         MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível finalizar o comprovante ", "Recebimento sem Impressão", "Aviso");                     
                         bImprimiuOK=false;
                     }
                }
                
            }
            if(bImprimiuOK){
                System.out.println("INICIANDO EFETIVAÇÃO RECEBIMENTO " + getDadosorc().getCodigo() + " - "+  DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));
                //
                Pagto_CodigoTrocaVenda=0l;
                if (Venda.Recebimento_Efetivar(getDadosorc(),pdvgerenciar.CodigoPDV() ,b,DataPagamento)) {                
                    Pagto_CodigoTrocaVenda=CodigoTroca;
                    bRet=true;                
                    Long CodigoVenda = getDadosorc().getCodigo();
                    VendaEmEdicao.FinalizarNota(getDadosorc().getCodigo(), true,false);                
                    if(Sistema.isOnline()){            
                        EnviarVendaServidor(CodigoVenda, 0l);
                    }
                    AcionarGaveta();
                    
                    System.out.println("TERMINADO EFETIVAÇÃO RECEBIMENTO : " + getDadosorc().getCodigo() + " - "+  DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));                               
                }else{                    
                    System.out.println("FALHA TERMINADO EFETIVAÇÃO RECEBIMENTO : " + getDadosorc().getCodigo() + " - "+  DataHora.getHora(DataHora.FormatHoraPadrao, ManipularData.DataAtual()));                               
                    bRet=false;
                }
                //InteracaoDuranteProcessamento.Mensagem_Terminar();
                
            }else{
                bRet=false;
            }            
            
            //if(bAlterarVerificacao)
            //{
                VerificarStatusServidor.setVerificarServidor(true);
            //}
        }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    return bRet;
}


private boolean AcionarGaveta(){
    boolean Ret = false;
    try { 
        
        ResultSet rsCaixa = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(), 0, 0, pdvgerenciar.CodigoPDV(), false);
        if(rsCaixa.next()){
            boolean AcionarGaveta =  rsCaixa.getBoolean("gaveta");
            if(AcionarGaveta){
                //if( btGaveta.get("Gaveta - On");)
                boolean ignorarGaveta = false;
                if(Pagto_TipoComprovante.equalsIgnoreCase("fiscal") &&  !Pagto_ECFDisponivel){
                   ignorarGaveta=true;
                }
                //AtivarModoNaoFiscal(bImprimiuOK);
                if(!ignorarGaveta){
                    PDVComprovante.AbrirGaveta( Pagto_TipoComprovante, PDVComprovante.getcNomeImpNaoFiscal());
                }
            }
        }
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
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
               if(TefStatus.equalsIgnoreCase("") || TefStatus.equalsIgnoreCase("[ F9 ]- REPETIR OPERAÇÃO") ){
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
private void btEfetivar(int MomentoVenda){
    try {
       // if(!btGravarVenda.isEnabled()){
      //     return ;
       // }
        btGravarVenda.setEnabled(false);
        if(Pagto_Recebimento_Crediario){
            btEfetivar_Crediario_Acao(MomentoVenda,ParametrosGlobais.getBaixarConta(),new Date(ManipularData.DataAtual().getTime()));    
        }else{
            btEfetivar_Acao(MomentoVenda);    
        }
          btGravarVenda.setEnabled(true);
      
        
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    //btEfetivar_Acao(MomentoVenda);
}
    private void btEfetivar_Acao(int MomentoVenda){    
    try {
        
        if(ParametrosGlobais.getPreVenda_Codigo().size()>0){
              setPreVenda(true);
        }else{
              setPreVenda(false);
        }
        if(!EfetivarVenda_Acao(MomentoVenda)){
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível efetivar a venda.", "Problema na efetivação da Venda", "Aviso"); 
           //btGravarVenda.requestFocus();
           IniciarGridPagtos_Atualizar();
        }else{
        
            Pagto_OK=true;
            dispose();
        }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    
    
    //btGravarVenda.setEnabled(true);

}
private void btEfetivar_Crediario_Acao(int MomentoVenda, BaixarConta b, Date DataPagamento){    
    try {
        
         
        if(!EfetivarCrediario_Acao(MomentoVenda,b,DataPagamento)){
           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível efetivar o recebimento.", "Problema na efetivação do RECEBIMENTO", "Aviso RECEBIMENTO"); 
           //btGravarVenda.requestFocus();
           IniciarGridPagtos_Atualizar();
        }else{
        
            Pagto_OK=true;
            dispose();
        }
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    
    
    //btGravarVenda.setEnabled(true);

}
private boolean SetarValoresDadosOrc(){
   
    boolean bRet=false;
    try {
        //ResultSet rs = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(), 0);
        //if(rs.next()){
            ItemLista i ;
            if(getDadosorc().getCodigocotacao()==null){
                getDadosorc().setCodigocotacao(0l);
            }
            if(getDadosorc().getCodigoorcamento()==null){
                getDadosorc().setCodigoorcamento(0l);
            }
            getDadosorc().setControleCx(this.Pagto_ControleCX);
            getDadosorc().setCodcaixa(this.Pagto_nCodigoOperadorCX);
            getDadosorc().setObjetoCaixa(this.Pagto_nCodigoObjetoCaixa);
            getDadosorc().setValor(BigDecimal.valueOf( Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  lblValorFinal.getValue(),0d).toString())));
            //getDadosorc().setCodcliente(txtCodigoCliente.getText());
            //getDadosorc().setCliente(txtNomeCliente.getText());
            Double DescontoTotal=0d;
            Double DescontoNota = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescEAcrescVal.getValue(),0d).toString());
            Double percDescontoTotal = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtDescontoAvulsoPerc.getValue(),0d).toString());            
            Double CredTroca = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorCreditoTroca.getValue(),0d).toString());            
            DescontoTotal = NumeroArredondar.Arredondar_Double(DescontoNota+CredTroca,2);
            getDadosorc().setDesconto(DescontoTotal);
            getDadosorc().setPercdesc(new BigDecimal( percDescontoTotal.doubleValue()));
            //getDadosorc().setAcrescimopercentual(BigDecimal.valueOf(Float.valueOf(getFrmDesconto().txtAcrescPerc.getValue().toString())));
            Double Acrescimo = Double.valueOf( txtAcrescimo.getValue()==null ? "0" : txtAcrescimo.getValue().toString() );
            getDadosorc().setAcrescimo(Acrescimo );           
            //txtValorVenda.commitEdit();
            Double ValorVendaBruta = ItensorcRN.Itensorc_Somar(getDadosorc().getCodigo());
            getDadosorc().setLoja(Sistema.getLojaAtual());
            BigDecimal nTotalBruto = new BigDecimal( ValorVendaBruta );
            nTotalBruto.setScale(2, RoundingMode.HALF_EVEN );
            getDadosorc().setTotalbruto(nTotalBruto);
            getDadosorc().setFilial(this.Pagto_nCodigoFilial);
            getDadosorc().setVendaCondicional(false);
            if(Pagto_TipoComprovante.equalsIgnoreCase("fiscal")){
                getDadosorc().setCoo(Pagto_ecf.UltimoCupom());
            }else{
                getDadosorc().setCoo("");
            }
            
            getDadosorc().setFracao(BigDecimal.ZERO);
            if(getDadosorc().getDinheiro()==null){
                getDadosorc().setDinheiro(BigDecimal.valueOf(0d));
            }
            if(getDadosorc().getTroco()==null){
                getDadosorc().setTroco(BigDecimal.valueOf(0d));
            }
            
            //getDadosorc().setTroco(BigDecimal.valueOf(Float.parseFloat(txtTroco.getValue().toString())));
            getDadosorc().setPontuacaoatual(BigDecimal.ZERO);
            getDadosorc().setPontuacaoresgate(BigDecimal.ZERO);
            getDadosorc().setPontuacaovenda(BigDecimal.ZERO);
            getDadosorc().setPdv(pdvgerenciar.CodigoPDV());
        //}
        bRet=true;

    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    return bRet;
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelAcrescimo;
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelCorpoPagto;
    private javax.swing.JPanel PainelDescontos;
    private javax.swing.JPanel PainelFormasPagtoVenda;
    private javax.swing.JPanel PainelFormasPagtoVenda1;
    private javax.swing.JPanel PainelRodapePagto;
    private javax.swing.JPanel PainelTopo;
    private javax.swing.JPanel PainelTroca;
    private javax.swing.JButton btEditarForma;
    private javax.swing.JButton btFechar5;
    private javax.swing.JButton btGravarVenda;
    private javax.swing.JButton btListaForma;
    private javax.swing.JButton btRemoverForma;
    private br.com.ui.JTableDinnamuS dbgFormasPagto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblAcrescimoAvulso;
    private javax.swing.JLabel lblAcrescimoAvulso1;
    private javax.swing.JLabel lblAcrescimoPagto;
    private javax.swing.JLabel lblAcrescimoTotal;
    private javax.swing.JLabel lblDEscontoPreVenda;
    private javax.swing.JLabel lblDesconto;
    private javax.swing.JLabel lblDescontoAtacado;
    private javax.swing.JLabel lblDescontoNormal;
    private javax.swing.JLabel lblSinalIgual;
    private javax.swing.JLabel lblSinalMenosAcrescimo;
    private javax.swing.JLabel lblSinalMenosDesconto;
    private javax.swing.JLabel lblSubTitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloCampoValorFinal;
    private javax.swing.JLabel lblTroca;
    private javax.swing.JFormattedTextField lblValorFinal;
    private javax.swing.JFormattedTextField txtAcrescimo;
    private javax.swing.JFormattedTextField txtAcrescimoAvulsoPerc;
    private javax.swing.JFormattedTextField txtAcrescimoAvulsoVLR;
    private javax.swing.JFormattedTextField txtAcrescimoPagto;
    private javax.swing.JFormattedTextField txtDescEAcrescVal;
    private javax.swing.JFormattedTextField txtDescontoAtacado;
    private javax.swing.JFormattedTextField txtDescontoAvulsoPerc;
    private javax.swing.JFormattedTextField txtDescontoAvulsoVLR;
    private javax.swing.JFormattedTextField txtDescontoPreVenda;
    private javax.swing.JFormattedTextField txtValorCreditoTroca;
    private javax.swing.JFormattedTextField txtValorFaltaReceber;
    private javax.swing.JFormattedTextField txtValorVenda;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the dadosorc
     */
    public Dadosorc getDadosorc() {
        return Pagto_dadosorc;
    }

    /**
     * @param dadosorc the dadosorc to set
     */
    public void setDadosorc(Dadosorc dadosorc) {
        this.Pagto_dadosorc = dadosorc;
    }

    /**
     * @return the ecf
     */
    public ECFDinnamuS getEcf() {
        return Pagto_ecf;
    }

    /**
     * @param ecf the ecf to set
     */
    public void setEcf(ECFDinnamuS ecf) {
        this.Pagto_ecf = ecf;
    }

    /**
     * @return the frmDesconto
     */
   
    /**
     * @return the frmTroca
     */
    public frmPDV_SimplesSelecionarTroca getFrmTroca() {
        if(frmTroca==null){
            setFrmTroca(new frmPDV_SimplesSelecionarTroca(null,true));
        }
        return frmTroca;
    }

    /**
     * @param frmTroca the frmTroca to set
     */
    public void setFrmTroca(frmPDV_SimplesSelecionarTroca frmTroca) {
        this.frmTroca = frmTroca;
    }
    
    private boolean AtivarModoFiscal(){
        try {
            if(Pagto_ECFDisponivel){
                lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO VERDE.png")));            
                //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO(null, "ECF NÃO DISPONÍVEL", "Fechamento");
                Pagto_TipoComprovante="fiscal";  
                Pagto_ImprimirComprovante=true;
               return true;
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
    
     private boolean AtivarModoSemImpressao_NFCe(){
        try {
            if(Pagto_NFCe_OK){                               
                Pagto_TipoComprovante="nfce";            
                Pagto_ImprimirComprovante=true;
                lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO BRANCO.png")));
                return true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
           
        }
         return false;
    }
    private boolean AtivarModoNaoFiscal(boolean  ModoSilencioso){
        boolean Ret = false;
                
        try {
            if(!PDVComprovante.getImpressoraCompravante().isOK()){
                if(PDVComprovante.ImpressoraDeComprovante_Iniciar()){
                   Ret=true; 
                }                    
            }else{
               Ret=true; 
            }
            if(Ret){
                Pagto_TipoComprovante="nfiscal";            
                Pagto_ImprimirComprovante=true;
                lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO AZUL.png")));
                return true;
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
        }
        return false;
    }
    private boolean AtivarModoSemImpressao(){
        try {
            
            lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/CARRINHO VERMELHO.png")));
            Pagto_ImprimirComprovante=false;
            Pagto_TipoComprovante="nimp";                                
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
   private void DefinirTipoComprovante(String TipoComprovanteAtual ){
        try { 
            
            boolean Proximo=false;
            if(TipoComprovanteAtual.equalsIgnoreCase("")){
                Proximo=true;
                boolean isPrevenda_Concomitante=false;            
                ResultSet rs = VendaEmEdicao.VerificarVendaInterrompida();
                if(rs.next()){                
                    isPrevenda_Concomitante = rs.getBoolean("prevenda_concomitante");
                }
                TipoComprovanteAtual = Pagto_TipoComprovante;
                if(!TipoComprovanteAtual.equalsIgnoreCase("nimp") && isPrevenda_Concomitante ){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ESTA FUNÇÃO NÃO ESTA DISPONÍVEL\nQUANDO EXISTEM ITENS JÁ IMPRESSOS", "MODO OPERACIONAL(F10)");
                    return;
                }            
                if(PagtoorcRN.Pagtoorc_VendaTEF(Pagto_dadosorc.getCodigo())){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO É POSSIVEL ALTERAR O MODO OPERACIONAL\n\nExistem venda(s) aprovada(s) no cartão de crédito", "MODO OPERACIONAL(F10)");
                    return;                    
                }  
            }
            String ProximoModo = "";
            if(Proximo){
                int Indice  =ModosTrabalhoDisponiveis.indexOf(TipoComprovanteAtual);
                if(Indice<ModosTrabalhoDisponiveis.size()-1){
                    ProximoModo=ModosTrabalhoDisponiveis.get(Indice+1);
                }else{
                    ProximoModo=ModosTrabalhoDisponiveis.get(0);
                }
            }else{
                ProximoModo=TipoComprovanteAtual;
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
   private void Atalhos(String Fonte){
            if(InterrompeLeituraTecla){
                return;
            }
            if(VerificarNFCeEmDigitacao(true) && !Fonte.equalsIgnoreCase("F3") && !Fonte.equalsIgnoreCase("ESCAPE")){return ;}
            
            if(Fonte.equalsIgnoreCase("ESCAPE")){
                btFechar5ActionPerformed(null);
            }else if(Fonte.equalsIgnoreCase("F7")){
                btListaFormaActionPerformed(null);                 
            }else if(Fonte.equalsIgnoreCase("F6")){
                Desconto_Acrescimo("Desconto");               
            }else if(Fonte.equalsIgnoreCase("F5")){    
                Acrescimo();
            }else if(Fonte.equalsIgnoreCase("F4")){
                Troca();
            }else if(Fonte.equalsIgnoreCase("F3")){
               btGravarVendaActionPerformed(null);
            }else if(Fonte.equalsIgnoreCase("F9")){
                RepetirOperacaoTEF_Desfeita();
            }else if(Fonte.equalsIgnoreCase("F2")){
                btEditarFormaActionPerformed(null);
            }else if(Fonte.equalsIgnoreCase("DELETE")){
                btRemoverFormaActionPerformed(null);
            }else if(Fonte.equalsIgnoreCase("F10")){
                DefinirTipoComprovante("");
            }else if(Fonte.equalsIgnoreCase("F8")){
                if(dbgFormasPagto.getjTable().getRowCount()>0){
                   dbgFormasPagto.getjTable().setRowSelectionInterval(0, 0);
                   dbgFormasPagto.getjTable().requestFocus();
                }                       
            }
   }
   private boolean TeclasAtalhos_UI(){
        try {  
             AbstractAction TeclaAtalhos  = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                       String Fonte = e.getSource().toString();                
                       Atalhos(Fonte);
                }
            };            
            DefinirAtalhos.Definir(PainelFormasPagtoVenda, TeclaAtalhos);
            return true;
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
           return false;
       }
   }
   private ArrayList<String> PreVendaPendenciasParaECF = null;
   private boolean VerificarPendenciasPreVenda(Long CodigoPreVenda, int Loja ){
       try {
            if(Pagto_ECFDisponivel){               
                PreVendaPendenciasParaECF = PreVenda.VerificarPendenciasParaCupomFiscal(CodigoPreVenda,Loja);
                if(PreVendaPendenciasParaECF.size()>0){                            
                    boolean RetPergunta = PreVenda.ExibirPendenciasEmissaoDocFiscal(PreVendaPendenciasParaECF);                    
                    return RetPergunta;
                }
            }           
           return true;
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
           return false;
       }
   }

    /**
     * @return the Mesclagem
     */
    public boolean isPreVenda() {
        return Mesclagem;
    }

    /**
     * @param Mesclagem the Mesclagem to set
     */
    public void setPreVenda(boolean Mesclagem) {
        this.Mesclagem = Mesclagem;
    }

}

