/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * frmPreVenda.java
 *
 * Created on 29/05/2013, 03:44:08
 */
package dinnamus.ui.InteracaoUsuario.nfe;

import br.com.FormatarNumeros;
import br.com.ecf.ECFDinnamuS;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.com.ui.ValidarCelula;
import br.com.ui.jtabledinnamus_ModelListener;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensorc;
import br.data.DataHora;
import br.data.ManipularData;
import br.arredondar.NumeroArredondar;
import MetodosDeNegocio.Fachada.cadproduto;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.pdvgerenciar;
import dinnamus.ui.InteracaoUsuario.Estoque.frmPesquisarProduto;
import dinnamus.ui.componentes.tabela.EditorTabela;
import dinnamus.ui.componentes.tabela.Tabela_RecursosAdicionais;
import MetodosDeNegocio.Venda.DadosorcRN;
import MetodosDeNegocio.Venda.GerenciarCaixa;
import MetodosDeNegocio.Venda.ItensorcRN;
import br.TratamentoNulo.TratamentoNulos;
import br.com.generica.Dao_Generica;
import br.com.ui.BloquearTela;
import br.com.ui.ComboModelObject;
import br.com.ui.ConverterListHashMap;
import br.com.ui.InteracaoDuranteProcessamento;
import br.com.ui.ItemLista;
import br.com.ui.TbDinnamuSObject;
import br.digitoverificador.DigitoVerificadores;
import br.ui.formatar.factory.FormatacaoFac;
import br.ui.validacoes.ValidarIdentificacao;
import com.fincatto.nfe310.classes.NFAmbiente;
import com.fincatto.nfe310.validadores.StringValidador;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import jpa.entidades.CepMunicipio;
import jpa.entidades.CepUf;
import jpa.entidades.NfePais;
import jpa.entidades.OffNfceConfig;
import jpa.entidades.OffNfcePdvNotas;
import jpa.entidades.OffNfeDest;
import jpa.entidades.OffNfeProd;
import net.java.balloontip.BalloonTip;
import org.jdesktop.beansbinding.Binding;

/**
 *
 * @author Fernando
 */
public class frmNFe extends javax.swing.JDialog {

    /**
     * Creates new form frmPreVenda
     */
    private static String ControleCX = "";
    private static Dadosorc dadosorc = null;
    private static Itensorc itensorc = null;
    private static ArrayList<Itensorc> arItensorc = new ArrayList<Itensorc>();
    private static Integer nCodigoFilial = 0;
    private static Integer nCodigoObjetoCaixa = 0;
    private static ECFDinnamuS EcfDinnmus = new ECFDinnamuS();
    private static String NomeImpressoraComprovante = "";
    private static boolean bParaThread = false;
    private static boolean bAtualizacaoPedente = false;
    private static String ColunasGrid = "";
    private static boolean GridIniciado = false;
    private NFeFachada nFeFachada = new NFeFachada();
    private OffNfceConfig nfeconfig;

    /**
     * @return the dadosorc
     */
    public static Dadosorc getDadosorc() {
        return dadosorc;
    }

    /**
     * @param aDadosorc the dadosorc to set
     */
    public static void setDadosorc(Dadosorc aDadosorc) {
        dadosorc = aDadosorc;
    }

    /**
     * @return the itensorc
     */
    public static Itensorc getItensorc() {
        return itensorc;
    }

    /**
     * @param aItensorc the itensorc to set
     */
    public static void setItensorc(Itensorc aItensorc) {
        itensorc = aItensorc;
    }

    /**
     * @return the arItensorc
     */
    public static ArrayList<Itensorc> getArItensorc() {
        return arItensorc;
    }

    /**
     * @param aArItensorc the arItensorc to set
     */
    public static void setArItensorc(ArrayList<Itensorc> aArItensorc) {
        arItensorc = aArItensorc;
    }
    private boolean carregandoNFe = false;

    public frmNFe(java.awt.Frame parent, boolean modal) {
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        super(parent, modal);
        initComponents();
        new Thread("carregandoNFeDinnamuS") {
            public void run() {

                if (!UI_Carregar()) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível carrega a interface do usuário", "Pré-Venda", "AVISO");

                }
            }
        }.start();

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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        PainelPrincipal = new javax.swing.JPanel();
        PainelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btFechar2 = new javax.swing.JButton();
        PainelCorpo = new javax.swing.JPanel();
        PainelGeral = new javax.swing.JPanel();
        PainelConsulta = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PainelPesquisaNFE = new javax.swing.JPanel();
        dbgNFeListagem = new br.com.ui.JTableDinnamuS();
        PainelBotoesConsultaNFE = new javax.swing.JPanel();
        btConsultarNFE = new javax.swing.JButton();
        btCancelamento = new javax.swing.JButton();
        btCartaCorrecao = new javax.swing.JButton();
        btDanfe = new javax.swing.JButton();
        PainelVendas = new javax.swing.JPanel();
        dbgDetalhesVenda = new br.com.ui.JTableDinnamuS();
        PainelDevolucoes = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        PainelNFE = new javax.swing.JPanel();
        PainelTrabalho = new javax.swing.JPanel();
        PainelProdutos = new javax.swing.JPanel();
        dbgProdutosNFE = new br.com.ui.JTableDinnamuS();
        PainelTotal = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        PainelDestinatario = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        PainelDest = new javax.swing.JPanel();
        dest_txtBairro = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        dest_cbPais = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        dest_Inscricao = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        dest_cbUF = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        dest_chkIsento = new javax.swing.JCheckBox();
        dest_cbMunicipio = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        dest_txtInscSuframa = new javax.swing.JTextField();
        dest_txtEmail = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        dest_txtCEp = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        dest_txtEndereco = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        dest_txtNum = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        dest_txtComplemento = new javax.swing.JTextField();
        dest_Razao = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        dest_txtIdentificacao = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        dest_cbTipoID = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        PainelIdentificacao = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtVersao = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAmbiente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOperacaoOrigem = new javax.swing.JTextField();
        txtCodOpOrigem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtChave = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtProtocolo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDataHoraAutorizacao = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtDataHoraEmissao = new javax.swing.JTextField();
        txtNFE = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        PainelInfoAdic = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        PainelTransporte = new javax.swing.JPanel();
        MenuNFE = new javax.swing.JPanel();
        btIdentificacao = new javax.swing.JButton();
        btDestinatario = new javax.swing.JButton();
        btProdutos = new javax.swing.JButton();
        btInfoAdicionais = new javax.swing.JButton();
        btTransporte = new javax.swing.JButton();
        MenuNFE1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btCancelarNFe = new javax.swing.JButton();
        btGravarNFe = new javax.swing.JButton();
        MenuPrincipal = new javax.swing.JPanel();
        jXTPMenu1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jxBotoes1 = new org.jdesktop.swingx.JXTaskPane();
        PainelBotoes1 = new javax.swing.JPanel();
        btIdentificacao1 = new javax.swing.JButton();
        btDestinatario1 = new javax.swing.JButton();
        btProdutos1 = new javax.swing.JButton();
        btProdutos3 = new javax.swing.JButton();
        btProdutos2 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Nota fiscal eletrônica - V2");
        lblTitulo.setToolTipText("");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.1;
        PainelTitulo.add(lblTitulo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        PainelTitulo.add(jLabel5, gridBagConstraints);

        jLabel4.setBackground(new java.awt.Color(255, 255, 204));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.4;
        PainelTitulo.add(jLabel4, gridBagConstraints);

        btFechar2.setBackground(new java.awt.Color(0, 0, 0));
        btFechar2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btFechar2.setForeground(new java.awt.Color(255, 255, 255));
        btFechar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btFechar2.setMnemonic('F');
        btFechar2.setText("FECHAR");
        btFechar2.setBorderPainted(false);
        btFechar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFechar2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelTitulo.add(btFechar2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelPrincipal.add(PainelTitulo, gridBagConstraints);

        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        PainelGeral.setBackground(java.awt.Color.white);
        PainelGeral.setLayout(new java.awt.CardLayout());

        PainelConsulta.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Consulta de NFe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        PainelConsulta.add(jLabel1, gridBagConstraints);

        PainelPesquisaNFE.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout PainelPesquisaNFELayout = new javax.swing.GroupLayout(PainelPesquisaNFE);
        PainelPesquisaNFE.setLayout(PainelPesquisaNFELayout);
        PainelPesquisaNFELayout.setHorizontalGroup(
            PainelPesquisaNFELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 907, Short.MAX_VALUE)
        );
        PainelPesquisaNFELayout.setVerticalGroup(
            PainelPesquisaNFELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelConsulta.add(PainelPesquisaNFE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.5;
        PainelConsulta.add(dbgNFeListagem, gridBagConstraints);

        btConsultarNFE.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btConsultarNFE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-pesquisar-24.png"))); // NOI18N
        btConsultarNFE.setText("Consultar NFE");
        btConsultarNFE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConsultarNFEActionPerformed(evt);
            }
        });

        btCancelamento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCancelamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-cancelar-assinatura-24.png"))); // NOI18N
        btCancelamento.setText("Cancelamento");

        btCartaCorrecao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCartaCorrecao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-editar-arquivo-24.png"))); // NOI18N
        btCartaCorrecao.setText("Carta de Correção");

        btDanfe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btDanfe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-notícias-24.png"))); // NOI18N
        btDanfe.setText("Danfe");

        javax.swing.GroupLayout PainelBotoesConsultaNFELayout = new javax.swing.GroupLayout(PainelBotoesConsultaNFE);
        PainelBotoesConsultaNFE.setLayout(PainelBotoesConsultaNFELayout);
        PainelBotoesConsultaNFELayout.setHorizontalGroup(
            PainelBotoesConsultaNFELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelBotoesConsultaNFELayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(btConsultarNFE)
                .addGap(18, 18, 18)
                .addComponent(btCancelamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btCartaCorrecao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btDanfe)
                .addContainerGap(245, Short.MAX_VALUE))
        );
        PainelBotoesConsultaNFELayout.setVerticalGroup(
            PainelBotoesConsultaNFELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelBotoesConsultaNFELayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(PainelBotoesConsultaNFELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btConsultarNFE)
                    .addComponent(btCancelamento)
                    .addComponent(btCartaCorrecao)
                    .addComponent(btDanfe))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        PainelConsulta.add(PainelBotoesConsultaNFE, gridBagConstraints);

        PainelGeral.add(PainelConsulta, "PainelConsulta");

        PainelVendas.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        PainelVendas.add(dbgDetalhesVenda, gridBagConstraints);

        PainelGeral.add(PainelVendas, "PainelVendas");

        jLabel18.setText("PainelDevolucoes");

        javax.swing.GroupLayout PainelDevolucoesLayout = new javax.swing.GroupLayout(PainelDevolucoes);
        PainelDevolucoes.setLayout(PainelDevolucoesLayout);
        PainelDevolucoesLayout.setHorizontalGroup(
            PainelDevolucoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 911, Short.MAX_VALUE)
            .addGroup(PainelDevolucoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelDevolucoesLayout.createSequentialGroup()
                    .addGap(0, 413, Short.MAX_VALUE)
                    .addComponent(jLabel18)
                    .addGap(0, 413, Short.MAX_VALUE)))
        );
        PainelDevolucoesLayout.setVerticalGroup(
            PainelDevolucoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(PainelDevolucoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelDevolucoesLayout.createSequentialGroup()
                    .addGap(0, 263, Short.MAX_VALUE)
                    .addComponent(jLabel18)
                    .addGap(0, 263, Short.MAX_VALUE)))
        );

        PainelGeral.add(PainelDevolucoes, "PainelDevolucao");

        PainelNFE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                PainelNFEFocusLost(evt);
            }
        });
        PainelNFE.setLayout(new java.awt.GridBagLayout());

        PainelTrabalho.setLayout(new java.awt.CardLayout());

        PainelProdutos.setLayout(new java.awt.GridBagLayout());

        dbgProdutosNFE.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelProdutos.add(dbgProdutosNFE, gridBagConstraints);

        PainelTotal.setBackground(new java.awt.Color(255, 255, 255));
        PainelTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("SUB-TOTAL");
        lblTotal.setToolTipText("");

        javax.swing.GroupLayout PainelTotalLayout = new javax.swing.GroupLayout(PainelTotal);
        PainelTotal.setLayout(PainelTotalLayout);
        PainelTotalLayout.setHorizontalGroup(
            PainelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelTotalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                .addContainerGap())
        );
        PainelTotalLayout.setVerticalGroup(
            PainelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelTotalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelProdutos.add(PainelTotal, gridBagConstraints);

        PainelTrabalho.add(PainelProdutos, "PainelProdutos");

        PainelDestinatario.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Destinatário");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
        PainelDestinatario.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
        PainelDestinatario.add(jSeparator6, gridBagConstraints);

        PainelDest.setLayout(new java.awt.GridBagLayout());

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.bairro}"), dest_txtBairro, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 202;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 126;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        PainelDest.add(dest_txtBairro, gridBagConstraints);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Bairro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 202;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        PainelDest.add(jLabel29, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.pais}"), dest_cbPais, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(new ConversoresBeansItemListaInteger(hmItemListaPais).converterItemLista());
        bindingGroup.addBinding(binding);

        dest_cbPais.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dest_cbPaisItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 11, 0);
        PainelDest.add(dest_cbPais, gridBagConstraints);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("País");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 4, 0, 0);
        PainelDest.add(jLabel30, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.ie}"), dest_Inscricao, org.jdesktop.beansbinding.BeanProperty.create("text"), "beansBinInscricao");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 108;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(9, 4, 0, 0);
        PainelDest.add(dest_Inscricao, gridBagConstraints);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("UF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        PainelDest.add(jLabel31, gridBagConstraints);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Inscrição Estadual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        PainelDest.add(jLabel22, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.uf}"), dest_cbUF, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(new ConversoresBeansItemLista<String>(hmItemListaUF).converterItemLista());
        bindingGroup.addBinding(binding);

        dest_cbUF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dest_cbUFItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 11, 0);
        PainelDest.add(dest_cbUF, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Municipio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 63;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 0, 0);
        PainelDest.add(jLabel32, gridBagConstraints);

        dest_chkIsento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dest_chkIsento.setText("Isento de Icms");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.isentoicms}"), dest_chkIsento, org.jdesktop.beansbinding.BeanProperty.create("selected"), "beansBindingISento");
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        binding.setConverter(new ConversoresBeansBind().converterShortBoolean());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(7, 2, 0, 0);
        PainelDest.add(dest_chkIsento, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.municipio}"), dest_cbMunicipio, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(new ConversoresBeansItemListaInteger(hmItemListaMunicipio).converterItemLista());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 63;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 143;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 313;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 11, 0);
        PainelDest.add(dest_cbMunicipio, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Inscrição Suframa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDest.add(jLabel23, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.isuframa}"), dest_txtInscSuframa, org.jdesktop.beansbinding.BeanProperty.create("text"), "beansbindIESuframa");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 68;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 108;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 0, 0);
        PainelDest.add(dest_txtInscSuframa, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.email}"), dest_txtEmail, org.jdesktop.beansbinding.BeanProperty.create("text"), "beansbindEmail");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 84;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 122;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 251;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        PainelDest.add(dest_txtEmail, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 84;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDest.add(jLabel24, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Cep");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 4, 0, 0);
        PainelDest.add(jLabel25, gridBagConstraints);

        try {
            dest_txtCEp.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.cep}"), dest_txtCEp, org.jdesktop.beansbinding.BeanProperty.create("value"), "beansbindingCep");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        PainelDest.add(dest_txtCEp, gridBagConstraints);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Endereço");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 15;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 3, 0, 0);
        PainelDest.add(jLabel26, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.logradouro}"), dest_txtEndereco, org.jdesktop.beansbinding.BeanProperty.create("text"), "beansbindEndereco");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 197;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 380;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 0, 0);
        PainelDest.add(dest_txtEndereco, gridBagConstraints);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Num");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 202;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 6, 0, 0);
        PainelDest.add(jLabel27, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.numero}"), dest_txtNum, org.jdesktop.beansbinding.BeanProperty.create("text"), "beansBindNumero");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 202;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 126;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        PainelDest.add(dest_txtNum, gridBagConstraints);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Complemento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        PainelDest.add(jLabel28, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.complemento}"), dest_txtComplemento, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 201;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 472;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        PainelDest.add(dest_txtComplemento, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.razaosocial}"), dest_Razao, org.jdesktop.beansbinding.BeanProperty.create("text"), "beansBindRazao");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 27;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 179;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        PainelDest.add(dest_Razao, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Razão Social / Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 27;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDest.add(jLabel21, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.identificacao}"), dest_txtIdentificacao, org.jdesktop.beansbinding.BeanProperty.create("value"), "bindBeanIdentificacao");
        bindingGroup.addBinding(binding);

        dest_txtIdentificacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dest_txtIdentificacaoFocusGained(evt);
            }
        });
        dest_txtIdentificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dest_txtIdentificacaoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 25;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        PainelDest.add(dest_txtIdentificacao, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Identificação");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDest.add(jLabel20, gridBagConstraints);

        dest_cbTipoID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CPF", "CNPJ", "PASS" }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${dest.tipodocid}"), dest_cbTipoID, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"), "beansBindTipoID");
        bindingGroup.addBinding(binding);

        dest_cbTipoID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dest_cbTipoIDItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 14, 0);
        PainelDest.add(dest_cbTipoID, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Tipo Id");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        PainelDest.add(jLabel19, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.4;
        PainelDestinatario.add(PainelDest, gridBagConstraints);

        PainelTrabalho.add(PainelDestinatario, "PainelDestinatario");

        PainelIdentificacao.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Série");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 6);
        PainelIdentificacao.add(txtSerie, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(txtNumero, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Numero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        PainelIdentificacao.add(jLabel7, gridBagConstraints);

        txtVersao.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        PainelIdentificacao.add(txtVersao, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Versão");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        PainelIdentificacao.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Ambiente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        PainelIdentificacao.add(jLabel9, gridBagConstraints);

        txtAmbiente.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        PainelIdentificacao.add(txtAmbiente, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Operação de Origem");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(jLabel10, gridBagConstraints);

        txtOperacaoOrigem.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        PainelIdentificacao.add(txtOperacaoOrigem, gridBagConstraints);

        txtCodOpOrigem.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        PainelIdentificacao.add(txtCodOpOrigem, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Cod.Op. Origem");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        PainelIdentificacao.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Chave de Acesso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(jLabel12, gridBagConstraints);

        txtChave.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        PainelIdentificacao.add(txtChave, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Protocolo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(jLabel13, gridBagConstraints);

        txtProtocolo.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        PainelIdentificacao.add(txtProtocolo, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Data/Hora Autorização");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        PainelIdentificacao.add(jLabel14, gridBagConstraints);

        txtDataHoraAutorizacao.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 7);
        PainelIdentificacao.add(txtDataHoraAutorizacao, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Data/Hora Emissão");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(jLabel15, gridBagConstraints);

        txtDataHoraEmissao.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(txtDataHoraEmissao, gridBagConstraints);

        txtNFE.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(txtNFE, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Tipo NFe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelIdentificacao.add(jLabel16, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 906, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelIdentificacao.add(jPanel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 10;
        PainelIdentificacao.add(jSeparator1, gridBagConstraints);
        PainelIdentificacao.add(jSeparator3, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 1, 10, 1);
        PainelIdentificacao.add(jSeparator4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 1, 10, 1);
        PainelIdentificacao.add(jSeparator5, gridBagConstraints);

        PainelTrabalho.add(PainelIdentificacao, "PainelIdentificacao");

        jLabel6.setText("infoadico");

        javax.swing.GroupLayout PainelInfoAdicLayout = new javax.swing.GroupLayout(PainelInfoAdic);
        PainelInfoAdic.setLayout(PainelInfoAdicLayout);
        PainelInfoAdicLayout.setHorizontalGroup(
            PainelInfoAdicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 911, Short.MAX_VALUE)
            .addGroup(PainelInfoAdicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelInfoAdicLayout.createSequentialGroup()
                    .addGap(0, 433, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 433, Short.MAX_VALUE)))
        );
        PainelInfoAdicLayout.setVerticalGroup(
            PainelInfoAdicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
            .addGroup(PainelInfoAdicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelInfoAdicLayout.createSequentialGroup()
                    .addGap(0, 218, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 218, Short.MAX_VALUE)))
        );

        PainelTrabalho.add(PainelInfoAdic, "PainelInfoAdic");

        PainelTransporte.setBackground(new java.awt.Color(255, 255, 255));
        PainelTransporte.setLayout(new java.awt.GridBagLayout());
        PainelTrabalho.add(PainelTransporte, "PainelTransporte");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.2;
        PainelNFE.add(PainelTrabalho, gridBagConstraints);

        MenuNFE.setLayout(new java.awt.GridBagLayout());

        btIdentificacao.setBackground(new java.awt.Color(204, 204, 204));
        btIdentificacao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btIdentificacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-código-de-barras-32.png"))); // NOI18N
        btIdentificacao.setText("Identificação  - [F2]");
        btIdentificacao.setBorderPainted(false);
        btIdentificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIdentificacaoActionPerformed(evt);
            }
        });
        MenuNFE.add(btIdentificacao, new java.awt.GridBagConstraints());

        btDestinatario.setBackground(new java.awt.Color(204, 204, 204));
        btDestinatario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btDestinatario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-conferência-30.png"))); // NOI18N
        btDestinatario.setText("Destinatário  - [F3]");
        btDestinatario.setBorderPainted(false);
        btDestinatario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDestinatarioActionPerformed(evt);
            }
        });
        MenuNFE.add(btDestinatario, new java.awt.GridBagConstraints());

        btProdutos.setBackground(new java.awt.Color(204, 204, 204));
        btProdutos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-etiqueta-de-preço-26.png"))); // NOI18N
        btProdutos.setText("Produtos - [F4]");
        btProdutos.setBorderPainted(false);
        btProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProdutosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        MenuNFE.add(btProdutos, gridBagConstraints);

        btInfoAdicionais.setBackground(new java.awt.Color(204, 204, 204));
        btInfoAdicionais.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btInfoAdicionais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-informações-30.png"))); // NOI18N
        btInfoAdicionais.setText("Info Adicionais  - [F5]");
        btInfoAdicionais.setBorderPainted(false);
        btInfoAdicionais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInfoAdicionaisActionPerformed(evt);
            }
        });
        MenuNFE.add(btInfoAdicionais, new java.awt.GridBagConstraints());

        btTransporte.setBackground(new java.awt.Color(204, 204, 204));
        btTransporte.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btTransporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-caminhão-30.png"))); // NOI18N
        btTransporte.setText("Transporte - [F6]");
        btTransporte.setBorderPainted(false);
        btTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTransporteActionPerformed(evt);
            }
        });
        MenuNFE.add(btTransporte, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelNFE.add(MenuNFE, gridBagConstraints);

        MenuNFE1.setLayout(new java.awt.GridBagLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        btCancelarNFe.setBackground(new java.awt.Color(204, 204, 204));
        btCancelarNFe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCancelarNFe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-desfazer-24.png"))); // NOI18N
        btCancelarNFe.setText("Voltar");
        btCancelarNFe.setToolTipText("");
        btCancelarNFe.setBorderPainted(false);
        btCancelarNFe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarNFeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 23, 8, 23);
        jPanel4.add(btCancelarNFe, gridBagConstraints);

        btGravarNFe.setBackground(new java.awt.Color(204, 204, 204));
        btGravarNFe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btGravarNFe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-crie-um-novo-24.png"))); // NOI18N
        btGravarNFe.setText("Gravar");
        btGravarNFe.setBorderPainted(false);
        btGravarNFe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGravarNFeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 23, 8, 23);
        jPanel4.add(btGravarNFe, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        MenuNFE1.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelNFE.add(MenuNFE1, gridBagConstraints);

        PainelGeral.add(PainelNFE, "PainelNFE");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        PainelCorpo.add(PainelGeral, gridBagConstraints);

        MenuPrincipal.setLayout(new java.awt.GridBagLayout());

        jXTPMenu1.setBackground(new java.awt.Color(0, 0, 0));

        jxBotoes1.setSpecial(true);
        jxBotoes1.setTitle("Funções principais");
        jxBotoes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jxBotoes1MouseClicked(evt);
            }
        });
        org.jdesktop.swingx.VerticalLayout verticalLayout5 = new org.jdesktop.swingx.VerticalLayout();
        verticalLayout5.setGap(10);
        jxBotoes1.getContentPane().setLayout(verticalLayout5);

        PainelBotoes1.setBackground(new java.awt.Color(0, 0, 0));
        PainelBotoes1.setOpaque(false);
        org.jdesktop.swingx.VerticalLayout verticalLayout4 = new org.jdesktop.swingx.VerticalLayout();
        verticalLayout4.setGap(5);
        PainelBotoes1.setLayout(verticalLayout4);

        btIdentificacao1.setBackground(new java.awt.Color(204, 204, 204));
        btIdentificacao1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btIdentificacao1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-código-de-barras-32.png"))); // NOI18N
        btIdentificacao1.setText("Consultar NFe´s");
        btIdentificacao1.setBorderPainted(false);
        btIdentificacao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIdentificacao1ActionPerformed(evt);
            }
        });
        PainelBotoes1.add(btIdentificacao1);

        btDestinatario1.setBackground(new java.awt.Color(204, 204, 204));
        btDestinatario1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btDestinatario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-pagamento-online-24.png"))); // NOI18N
        btDestinatario1.setText("NFe Venda");
        btDestinatario1.setBorderPainted(false);
        btDestinatario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDestinatario1ActionPerformed(evt);
            }
        });
        PainelBotoes1.add(btDestinatario1);

        btProdutos1.setBackground(new java.awt.Color(204, 204, 204));
        btProdutos1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btProdutos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-substituir-24.png"))); // NOI18N
        btProdutos1.setText("NFe Devolução");
        btProdutos1.setBorderPainted(false);
        btProdutos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProdutos1ActionPerformed(evt);
            }
        });
        PainelBotoes1.add(btProdutos1);

        jxBotoes1.getContentPane().add(PainelBotoes1);

        btProdutos3.setBackground(new java.awt.Color(204, 204, 204));
        btProdutos3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btProdutos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-enviar-arquivo-24.png"))); // NOI18N
        btProdutos3.setText("NFe Transferência");
        btProdutos3.setBorderPainted(false);
        btProdutos3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProdutos3ActionPerformed(evt);
            }
        });
        jxBotoes1.getContentPane().add(btProdutos3);

        btProdutos2.setBackground(new java.awt.Color(204, 204, 204));
        btProdutos2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btProdutos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/icons8-descarte-de-lixo-24.png"))); // NOI18N
        btProdutos2.setText("Inutilizar Seq");
        btProdutos2.setBorderPainted(false);
        btProdutos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProdutos2ActionPerformed(evt);
            }
        });
        jxBotoes1.getContentPane().add(btProdutos2);

        jXTPMenu1.add(jxBotoes1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        MenuPrincipal.add(jXTPMenu1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.1;
        PainelCorpo.add(MenuPrincipal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.5;
        PainelPrincipal.add(PainelCorpo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelPrincipal, gridBagConstraints);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
// TODO add your handling code here:

}//GEN-LAST:event_formWindowActivated
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmNFe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(frmNFe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(frmNFe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(frmNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DAO_RepositorioLocal.Iniciar();
                Map<String, String> hmRepositorioLocal = new HashMap<String, String>();
                hmRepositorioLocal.put("Banco", "dinnamus");
                hmRepositorioLocal.put("Usuario", "dinnamus");
                hmRepositorioLocal.put("Senha", "dti");
                DAO_RepositorioLocal.AbrirBanco(hmRepositorioLocal, false);
                new frmNFe(null, true).setVisible(true);
            }
        });
    }
private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:


}//GEN-LAST:event_formWindowOpened

    private void btIdentificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIdentificacaoActionPerformed
        //GerarRelatorio();
        // iniciarUILogs();

        CardLayout card = (CardLayout) PainelTrabalho.getLayout();
        card.show(PainelTrabalho, "PainelIdentificacao");

    }//GEN-LAST:event_btIdentificacaoActionPerformed

    private void btDestinatarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDestinatarioActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) PainelTrabalho.getLayout();
        card.show(PainelTrabalho, "PainelDestinatario");
    }//GEN-LAST:event_btDestinatarioActionPerformed

    private void btProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProdutosActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) PainelTrabalho.getLayout();
        card.show(PainelTrabalho, "PainelProdutos");
    }//GEN-LAST:event_btProdutosActionPerformed

    private void btInfoAdicionaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInfoAdicionaisActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) PainelTrabalho.getLayout();
        card.show(PainelTrabalho, "PainelInfoAdic");
    }//GEN-LAST:event_btInfoAdicionaisActionPerformed

    private void btTransporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTransporteActionPerformed
        CardLayout card = (CardLayout) PainelTrabalho.getLayout();
        card.show(PainelTrabalho, "PainelTransporte");
    }//GEN-LAST:event_btTransporteActionPerformed

    private void btFechar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btFechar2ActionPerformed

    private void btIdentificacao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIdentificacao1ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) PainelGeral.getLayout();
        card.show(PainelGeral, "PainelConsulta");

        UI_AtualizaNFEListagem();

    }//GEN-LAST:event_btIdentificacao1ActionPerformed

    private void btDestinatario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDestinatario1ActionPerformed
        // TODO add your handling code here:
        PainelConsultaVenda_UI_Iniciar();
        
        
    }//GEN-LAST:event_btDestinatario1ActionPerformed

    private void btProdutos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProdutos1ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) PainelGeral.getLayout();
        card.show(PainelGeral, "PainelDevolucao");
    }//GEN-LAST:event_btProdutos1ActionPerformed

    private void jxBotoes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jxBotoes1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jxBotoes1MouseClicked

    private void btProdutos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProdutos2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btProdutos2ActionPerformed

    private void btConsultarNFEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConsultarNFEActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) PainelGeral.getLayout();
        card.show(PainelGeral, "PainelNFE");
        selecionarVenda();

    }//GEN-LAST:event_btConsultarNFEActionPerformed

    private void btProdutos3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProdutos3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btProdutos3ActionPerformed

    private void btCancelarNFeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarNFeActionPerformed
        // TODO add your handling code here:
        fecharBaloes();
        btIdentificacao1ActionPerformed(null);
    }//GEN-LAST:event_btCancelarNFeActionPerformed

    private void btGravarNFeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGravarNFeActionPerformed
        // TODO add your handling code here:

        try {

            if (MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Confirma a gravação da NFe ?", "Gravar NFe") == MetodosUI_Auxiliares_1.Sim()) {

                if (validarGravarNFe()) {
                    boolean gravarNFe = new NFeFachada().gravarNFe(nfecompleta);
                    if (gravarNFe) {
                        btIdentificacao1ActionPerformed(null);
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("NFe gravada com sucesso!!", "NFe OK");
                    } else {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possvel gravar a NFe", "NFe - Operação não realizada");
                    }
                }
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }//GEN-LAST:event_btGravarNFeActionPerformed
    public boolean validarGravarNFe() {
        boolean Ret = false;
        try {
            if (!validarGravarNFe_destinatario()) {
                return false;
            }

            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private List<BalloonTip> baloes;

    private List<BalloonTip> getBaloes() {
        if (baloes == null) {
            baloes = new ArrayList<>();
        }
        return baloes;

    }

    private void novoBalao(JComponent c, String msg) {
        BalloonTip Ret = null;
        try {
            Ret = new BalloonTip(c, msg);

            getBaloes().add(Ret);

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }

    public void fecharBaloes() {

        try {

            MetodosUI_Auxiliares_1.DefinirBorda(PainelDest, txtSerie.getBorder());

            for (BalloonTip balloonTip : getBaloes()) {
                balloonTip.closeBalloon();
                balloonTip = null;
            }
            baloes = null;

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }

    public boolean validarGravarNFe_destinatario() {
        boolean Ret = true;
        try {

            fecharBaloes();

            Object value = dest_txtIdentificacao.getText();

            Object tipoid = dest_cbTipoID.getSelectedItem();

            if (tipoid != null && value != null) {
                if (tipoid.toString().equalsIgnoreCase("cpf")) {

                    String cpf = value.toString().replaceAll("[^0-9]", "");

                    boolean CPF = DigitoVerificadores.CPF(cpf.toString());
                    if (!CPF) {

                        dest_txtIdentificacao.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));

                        novoBalao(dest_txtIdentificacao, "CPF digitado inválido");

                        Ret = false;

                    }

                } else if (tipoid.toString().equalsIgnoreCase("cnpj")) {

                    String cnpj = value.toString().replaceAll("[^0-9]", "");

                    boolean CNPJ = DigitoVerificadores.isCnpjValido(cnpj.toString());
                    if (!CNPJ) {
                        dest_txtIdentificacao.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                        novoBalao(dest_txtIdentificacao, "CNPJ digitado inválido");
                        Ret = false;
                    }
                }
            }
            String textdest_Razao = TratamentoNulos.getTratarString().Tratar(dest_Razao.getText(), "").trim();

            if (textdest_Razao.length() > 60) {
                dest_Razao.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_Razao, "Razão social inválida [60 caracteres]");
                Ret = false;
            }

            String textdest_IE = TratamentoNulos.getTratarString().Tratar(dest_Inscricao.getText(), "").trim();

            try {
                StringValidador.inscricaoEstadual(textdest_IE);
            } catch (Exception e) {
                dest_Inscricao.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_Inscricao, e.getMessage());
                Ret = false;
            }

            String textdest_ISuframa = TratamentoNulos.getTratarString().Tratar(dest_txtInscSuframa.getText(), "").trim();

            try {
                if (textdest_ISuframa.trim().length() > 0) {
                    StringValidador.tamanho8a9N(textdest_ISuframa);
                }
            } catch (Exception e) {
                dest_txtInscSuframa.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_txtInscSuframa, e.getMessage());
                Ret = false;
            }

            String textdest_Email = TratamentoNulos.getTratarString().Tratar(dest_txtEmail.getText(), "").trim();

            try {
                if (textdest_Email.trim().length() > 0) {
                    StringValidador.tamanho60(textdest_Email);
                }
            } catch (Exception e) {
                dest_txtEmail.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_txtEmail, e.getMessage());
                Ret = false;
            }

            String textdest_Cep = TratamentoNulos.getTratarString().Tratar(dest_txtCEp.getText(), "").trim().replaceAll("[^0-9]", "");

            try {
                StringValidador.exatamente8(textdest_Cep);
            } catch (Exception e) {
                dest_txtCEp.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_txtCEp, e.getMessage());
                Ret = false;
            }

            String textdest_Endereco = TratamentoNulos.getTratarString().Tratar(dest_txtEndereco.getText(), "").trim();

            try {
                // if(textdest_Endereco.trim().length()>0){
                StringValidador.tamanho60(textdest_Endereco);
                //  }
            } catch (Exception e) {
                dest_txtEndereco.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_txtEndereco, e.getMessage());
                Ret = false;
            }

            String textdest_num = TratamentoNulos.getTratarString().Tratar(dest_txtNum.getText(), "").trim();

            try {
                StringValidador.tamanho20(textdest_num);
            } catch (Exception e) {
                dest_txtNum.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_txtNum, e.getMessage());
                Ret = false;
            }

            String textdest_complemento = TratamentoNulos.getTratarString().Tratar(dest_txtComplemento.getText(), "").trim();

            try {
                if (textdest_complemento.trim().length() > 0) {
                    StringValidador.tamanho60(textdest_complemento);
                }
            } catch (Exception e) {
                dest_txtComplemento.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_txtComplemento, e.getMessage());
                Ret = false;
            }

            String textdest_Bairro = TratamentoNulos.getTratarString().Tratar(dest_txtBairro.getText(), "").trim();

            try {
                StringValidador.tamanho60(textdest_Bairro);
            } catch (Exception e) {
                dest_txtBairro.setBorder(MetodosUI_Auxiliares_1.PintarBorda(Color.RED));
                novoBalao(dest_txtBairro, e.getMessage());
                Ret = false;
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private void dest_cbUFItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dest_cbUFItemStateChanged
        // TODO add your handling code here:

        try {

            if (!carregandoNFe) {
                final ItemLista i = (ItemLista) dest_cbUF.getSelectedItem();

                if (i != null) {

                    new Thread() {
                        public void run() {
                            iniciarCBMunicipio(i.getIndice().toString());
                        }

                    }.start();

                }
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }//GEN-LAST:event_dest_cbUFItemStateChanged

    private void dest_cbPaisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dest_cbPaisItemStateChanged

        // TODO add your handling code here:
        try {

            if (!carregandoNFe) {
                final ItemLista i = (ItemLista) dest_cbPais.getSelectedItem();

                if (i != null) {

                    new Thread() {
                        public void run() {
                            iniciarCBUF(Integer.valueOf(i.getIndice().toString()));
                        }

                    }.start();

                }
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_dest_cbPaisItemStateChanged

    private void dest_cbTipoIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dest_cbTipoIDItemStateChanged

        // TODO add your handling code here:

    }//GEN-LAST:event_dest_cbTipoIDItemStateChanged

    private void dest_txtIdentificacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dest_txtIdentificacaoFocusGained
        // TODO add your handling code here:
        try {
            if (carregandoNFe) {
                return;
            }
            Object selectedItem = dest_cbTipoID.getSelectedItem();
            Object value = dest_txtIdentificacao.getValue();
            dest_txtIdentificacao.setValue(null);
            if (selectedItem != null) {
                DefaultFormatterFactory Formatar = null;
                if (selectedItem.toString().equalsIgnoreCase("cpf")) {

                    Formatar = new FormatacaoFac().Formatar("###.###.###-##");
                    if (value != null) {
                        if (value.toString().length() != 14) {
                            value = null;
                        }
                    }
                } else if (selectedItem.toString().equalsIgnoreCase("cnpj")) {
                    Formatar = new FormatacaoFac().Formatar("##.###.###/####-##");
                    if (value != null) {
                        if (value.toString().length() != 18) {
                            value = null;
                        }
                    }
                } else {
                    Formatar = null;

                }
                dest_txtIdentificacao.setFormatterFactory(Formatar);
            }
            dest_txtIdentificacao.setValue(value);

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_dest_txtIdentificacaoFocusGained


    private void dest_txtIdentificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dest_txtIdentificacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dest_txtIdentificacaoActionPerformed

    private void PainelNFEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PainelNFEFocusLost
        // TODO add your handling code here:

        fecharBaloes();
    }//GEN-LAST:event_PainelNFEFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MenuNFE;
    private javax.swing.JPanel MenuNFE1;
    private javax.swing.JPanel MenuPrincipal;
    private javax.swing.JPanel PainelBotoes1;
    private javax.swing.JPanel PainelBotoesConsultaNFE;
    private javax.swing.JPanel PainelConsulta;
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelDest;
    private javax.swing.JPanel PainelDestinatario;
    private javax.swing.JPanel PainelDevolucoes;
    private javax.swing.JPanel PainelGeral;
    private javax.swing.JPanel PainelIdentificacao;
    private javax.swing.JPanel PainelInfoAdic;
    private javax.swing.JPanel PainelNFE;
    private javax.swing.JPanel PainelPesquisaNFE;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelProdutos;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JPanel PainelTotal;
    private javax.swing.JPanel PainelTrabalho;
    private javax.swing.JPanel PainelTransporte;
    private javax.swing.JPanel PainelVendas;
    private javax.swing.JButton btCancelamento;
    private javax.swing.JButton btCancelarNFe;
    private javax.swing.JButton btCartaCorrecao;
    private javax.swing.JButton btConsultarNFE;
    private javax.swing.JButton btDanfe;
    private javax.swing.JButton btDestinatario;
    private javax.swing.JButton btDestinatario1;
    private javax.swing.JButton btFechar2;
    private javax.swing.JButton btGravarNFe;
    private javax.swing.JButton btIdentificacao;
    private javax.swing.JButton btIdentificacao1;
    private javax.swing.JButton btInfoAdicionais;
    private javax.swing.JButton btProdutos;
    private javax.swing.JButton btProdutos1;
    private javax.swing.JButton btProdutos2;
    private javax.swing.JButton btProdutos3;
    private javax.swing.JButton btTransporte;
    private br.com.ui.JTableDinnamuS dbgDetalhesVenda;
    private br.com.ui.JTableDinnamuS dbgNFeListagem;
    private br.com.ui.JTableDinnamuS dbgProdutosNFE;
    private javax.swing.JTextField dest_Inscricao;
    private javax.swing.JTextField dest_Razao;
    private javax.swing.JComboBox dest_cbMunicipio;
    private javax.swing.JComboBox dest_cbPais;
    private javax.swing.JComboBox dest_cbTipoID;
    private javax.swing.JComboBox dest_cbUF;
    private javax.swing.JCheckBox dest_chkIsento;
    private javax.swing.JTextField dest_txtBairro;
    private javax.swing.JFormattedTextField dest_txtCEp;
    private javax.swing.JTextField dest_txtComplemento;
    private javax.swing.JTextField dest_txtEmail;
    private javax.swing.JTextField dest_txtEndereco;
    private javax.swing.JFormattedTextField dest_txtIdentificacao;
    private javax.swing.JTextField dest_txtInscSuframa;
    private javax.swing.JTextField dest_txtNum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTPMenu1;
    private org.jdesktop.swingx.JXTaskPane jxBotoes1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtAmbiente;
    private javax.swing.JTextField txtChave;
    private javax.swing.JTextField txtCodOpOrigem;
    private javax.swing.JTextField txtDataHoraAutorizacao;
    private javax.swing.JTextField txtDataHoraEmissao;
    private javax.swing.JTextField txtNFE;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtOperacaoOrigem;
    private javax.swing.JTextField txtProtocolo;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtVersao;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private BloquearTela bloquearTela = new BloquearTela();

    private void carregandoNFE() {

        try {
            bloquearTela.Tela_Bloquear(this, 1f, Color.BLACK);

            Icon ico = new javax.swing.ImageIcon(getClass().getResource("/com/nfce/config/logo-nfce-xs.png"));

            InteracaoDuranteProcessamento.Mensagem_Iniciar("NFe DinnamuS PDV", "Preparando ambiente emissor...AGUARDE", false, ico);

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }

    private void liberarTela() {
        InteracaoDuranteProcessamento.Mensagem_Terminar();
        bloquearTela.Tela_DesBloquear();
    }

    private boolean UI_Carregar() {
        try {

            carregandoNFE();

            MetodosUI_Auxiliares_1.MaximizarJDialog(this, this.getToolkit());

            if (!UI_IniciarNFEListagem()) {
                return false;
            }

            if (!UI_IniciarGridItens()) {
                return false;
            }

            CardLayout card = (CardLayout) PainelTrabalho.getLayout();
            card.show(PainelTrabalho, "PainelIdentificacao");

            new NFeFachada().getPersistencia();

            liberarTela();

            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);

        }
        this.dispose();
        return false;

    }
    private OffNfeDest destUI;

    public boolean iniciarCombo() {

        return true;
    }
    private HashMap<String, ItemLista> hmItemListaUF = new HashMap<>();
    private HashMap<Integer, ItemLista> hmItemListaMunicipio = new HashMap<>();
    private HashMap<Integer, ItemLista> hmItemListaPais = new HashMap<>();

    public boolean iniciarCbPais() {
        boolean Ret = false;
        try {

            List<NfePais> listaPais = new NFeFachada().getNfePais();

            ComboModelObject<NfePais> modelo = new ComboModelObject<>();

            dest_cbPais.setModel(modelo.GerarComboModel(listaPais, "nome", "codigo", true, ""));

            hmItemListaPais = new ConverterListHashMap<ItemLista, Integer>().converter(modelo.getItemListas(), "indice", hmItemListaPais);

            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public boolean iniciarCBUF(Integer pais) {
        boolean Ret = false;
        try {
            List<CepUf> listacepUF = new NFeFachada().getCepUF(pais);

            ComboModelObject<CepUf> modelo = new ComboModelObject<>();

            dest_cbUF.setModel(modelo.GerarComboModel(listacepUF, "nome", "codigo", true, ""));

            hmItemListaUF = new ConverterListHashMap<ItemLista, String>().converter(modelo.getItemListas(), "indice", hmItemListaUF);

            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public void carregandoCombo(JComboBox jc) {

        try {

            ItemLista processando = new ItemLista();
            processando.setIndice(-1);
            processando.setDescricao("Carregando lista");
            List<ItemLista> lista = new ArrayList<>();
            lista.add(processando);
            new ComboModelObject<ItemLista>().GerarComboModel(lista, "descricao", "indice", true, "");

            jc.setModel(new ComboModelObject<ItemLista>().GerarComboModel(lista, "descricao", "indice", true, ""));

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }

    public boolean iniciarCBMunicipio(String uf) {
        boolean Ret = false;
        try {

            carregandoCombo(dest_cbMunicipio);

            List<CepMunicipio> listacepmunicipio = new NFeFachada().getCepMunicipio(uf);

            ComboModelObject<CepMunicipio> modeloCepMunicipio = new ComboModelObject<>();

            dest_cbMunicipio.setModel(modeloCepMunicipio.GerarComboModel(listacepmunicipio, "municipio", "codigo", true, ""));

            hmItemListaMunicipio = new ConverterListHashMap<ItemLista, Integer>().converter(modeloCepMunicipio.getItemListas(), "indice", hmItemListaMunicipio);

            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    private OffNfcePdvNotas nfecompleta;

    public boolean selecionarVenda() {
        boolean Ret = false;
        try {
            int nLinhaAtual = dbgNFeListagem.getjTable().getSelectedRow();
            if (nLinhaAtual >= 0) {
                OffNfcePdvNotas getNfeSelecionada = tbDinnamuSObject.getRs().get(nLinhaAtual);
                if (getNfeSelecionada != null) {

                    nfecompleta = nFeFachada.getNfe(getNfeSelecionada.getId());

                    carregandoNFe = carregarNFE(nfecompleta);

                    carregandoNFe = false;
                }
            } else {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi selecionada nenhuma NFe", "Consultar NFe");
            }
            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;

    }

    public boolean carregarNFE(OffNfcePdvNotas nota) {
        boolean Ret = false;
        try {
            carregandoNFe = true;
            OffNfeDest destinatario = nota.getOffNfeDestCollection().iterator().next();

            if (!iniciarCbPais()) {
                return false;
            }

            if (!iniciarCBUF(destinatario.getPais())) {
                return false;
            }

            if (nota.getOffNfeDestCollection() != null && !nota.getOffNfeDestCollection().isEmpty()) {

                if (!iniciarCBMunicipio(destinatario.getUf())) {
                    return false;
                }
            }
            if (!carregarNFE_Identificacao(nota)) {
                return false;
            }

            if (!carregarNFE_destinatario(nota.getOffNfeDestCollection())) {
                return false;
            }

            if (!UI_IniciarGridItens_AtualizarGrid(new ArrayList(nota.getOffNfeProdCollection()))) {
                return false;
            }

            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public boolean carregarNFE_destinatario(Collection<OffNfeDest> dest) {
        boolean Ret = false;
        try {

            if (dest != null && !dest.isEmpty()) {
                //  bindingGroup.unbind();

                destUI = (OffNfeDest) dest.toArray()[0];

                for (Binding b : bindingGroup.getBindings()) {

                    b.unbind();

                    b.bind();

                }

                // bindingGroup.bind();
                return true;

            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public boolean carregarNFE_Identificacao(OffNfcePdvNotas nota) {
        boolean Ret = false;
        try {
            NFAmbiente ambiente = NFAmbiente.valueOfCodigo(TratamentoNulos.getTratarInt().Tratar(nota.getAmbiente(), 0).toString());

            txtAmbiente.setText(ambiente.name());
            txtSerie.setText(nota.getSerie().toString());
            txtVersao.setText(nota.getVersao());
            txtChave.setText(nota.getChave());
            String datahoraAutorizacaoFormatada = (nota.getDatahoraautorizacao() != null ? DataHora.getData(DataHora.FormatDataHoraPadrao, nota.getDatahoraautorizacao()) : "");
            String datahoraEmissaoFormatada = (nota.getEmissao() != null ? DataHora.getData(DataHora.FormatDataHoraPadrao, nota.getEmissao()) : "");
            txtDataHoraEmissao.setText(datahoraEmissaoFormatada);
            txtDataHoraAutorizacao.setText(datahoraAutorizacaoFormatada);
            txtCodOpOrigem.setText(TratamentoNulos.getTratarLong().Tratar(nota.getOperacaoorigem(), 0l).toString());

            String tipoOperacaoOrigem = TratamentoNulos.getTratarString().Tratar(nota.getTipooperacaoorigem(), "VDA");
            if (tipoOperacaoOrigem.equalsIgnoreCase("vda")) {
                tipoOperacaoOrigem = "VENDA";
            } else if (tipoOperacaoOrigem.equalsIgnoreCase("dev")) {
                tipoOperacaoOrigem = "DEVOLUÇÃO";
            } else if (tipoOperacaoOrigem.equalsIgnoreCase("trx")) {
                tipoOperacaoOrigem = "TRANSFERÊNCIA";
            } else if (tipoOperacaoOrigem.equalsIgnoreCase("avulsa")) {
                tipoOperacaoOrigem = "AVULSA";
            }
            txtNFE.setText(tipoOperacaoOrigem);
            txtOperacaoOrigem.setText(nota.getTipooperacaoorigem());
            txtNumero.setText(nota.getNumero());

            Ret = true;

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    private TbDinnamuSObject<OffNfcePdvNotas> tbDinnamuSObject = null;

    public boolean UI_AtualizaNFEListagem() {
        boolean Ret = false;
        try {

            tbDinnamuSObject.setRs(nFeFachada.listarNFE());

            dbgNFeListagem.setModeloObjeto(tbDinnamuSObject);

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public boolean UI_IniciarNFEListagem() {
        boolean Ret = false;
        try {

            if (tbDinnamuSObject == null) {
                tbDinnamuSObject = new TbDinnamuSObject<>();
            }

            dbgNFeListagem.addClColunas("numero", "Número", 20, true, true, dbgNFeListagem.Alinhamento_Esqueda);
            dbgNFeListagem.addClColunas("serie", "Série", 10, true, true, dbgNFeListagem.Alinhamento_Esqueda);
            dbgNFeListagem.addClColunas("chave", "Chave", 30, true, true, dbgNFeListagem.Alinhamento_Esqueda);
            dbgNFeListagem.addClColunas("protocolo", "Protocolo", 20, true, true, dbgNFeListagem.Alinhamento_Esqueda);
            dbgNFeListagem.addClColunas("datahoraautorizacao", "Dt/Hr Autorização", 20, true, true, dbgNFeListagem.Alinhamento_Esqueda);
            dbgNFeListagem.setColunaComTamanhosEmPercentual(true);
            dbgNFeListagem.setAjustaColunaAoPainel(true);
            dbgNFeListagem.getjTable().addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, "keypressed : " + String.valueOf(e.getKeyCode()), "Pré-Venda", "AVISO");    
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                    } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                        e.consume();

                    } else if (e.getKeyChar() == '\n') {
                        //e.consume();
                    } else {

                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, "keyReleased : " + String.valueOf(e.getKeyCode()), "Pré-Venda", "AVISO");    

                    int nCol = dbgNFeListagem.getjTable().getSelectedColumn();

                    if (e.getKeyChar() == '\n') {

                    }
                }
            });

            /*
            dbgNFeListagem.getjTable().setDefaultEditor(Object.class,  new EditorTabela(new Prevenda_Validacoes()));
            dbgNFeListagem.getTbDinnamuS().addTableModelListener(new jtabledinnamus_ModelListener( dbgNFeListagem.getjTable() ));            
            dbgNFeListagem.getjTable().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"selectNextColumnCell"); 
             */
            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;

    }

    private static boolean NovoItensorc_Vazio() {
        return NovoItensorc_Vazio(0l, 1);
    }

    private static boolean NovoItensorc_Vazio(Long nItensorc_Idunico, int nSeq) {
        try {

            boolean bRegistroNovo = false;
            if (nItensorc_Idunico == 0) {
                nItensorc_Idunico = DAO_RepositorioLocal.NovoValorIdentidade("itensorc", Sistema.getLojaAtual(), pdvgerenciar.CodigoPDV());
                bRegistroNovo = true;
            }

            Itensorc i = SetarItensorc(getDadosorc(), nSeq, nItensorc_Idunico, 0l, "", "", 0f, 0f, 0f, "", "", 0f, Sistema.getLojaAtual(), "", 0l, pdvgerenciar.CodigoPDV(), false, "", "", bRegistroNovo);
            if (i != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }

    }

    private boolean UI_IniciarGridItens_AtualizarGrid(ArrayList<OffNfeProd> nfeProds) {
        try {

            if (nfeProds == null) {
                nfeProds = new ArrayList<OffNfeProd>();
            }

            tbOffNfeProd.setRs(nfeProds);

            dbgProdutosNFE.setModeloObjeto(tbOffNfeProd);

            return true;
        } catch (Exception e) {

            LogDinnamus.Log(e, true);
            return false;
        }
    }

    private TbDinnamuSObject<OffNfeProd> tbOffNfeProd = null;

    private boolean UI_IniciarGridItens() {
        try {

            if (tbOffNfeProd == null) {
                tbOffNfeProd = new TbDinnamuSObject<>();
            }
            dbgProdutosNFE.addClColunas("item", "item", 5, true, true, dbgProdutosNFE.Alinhamento_Esqueda);
            dbgProdutosNFE.addClColunas("codigoproduto", "Cod", 10, true, true, dbgProdutosNFE.Alinhamento_Esqueda);
            dbgProdutosNFE.addClColunas("nome", "Descrição", 30, true, true, dbgProdutosNFE.Alinhamento_Esqueda);
            dbgProdutosNFE.addClColunas("ncm", "NCM", 10, true, true, dbgProdutosNFE.Alinhamento_Esqueda);
            dbgProdutosNFE.addClColunas("cfop", "CFOP", 5, true, true, dbgProdutosNFE.Alinhamento_Esqueda);
            dbgProdutosNFE.addClColunas("qtdtributaval", "QT", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);
            dbgProdutosNFE.addClColunas("valorunittributario", "P.Unit", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);
            dbgProdutosNFE.addClColunas("desconto", "$Desc", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);
            dbgProdutosNFE.addClColunas("total", "TOTAL", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);
            dbgProdutosNFE.addClColunas("basecalcicms", "B.Calc.", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);
            dbgProdutosNFE.addClColunas("aliqoutaicms", "%Icms", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);
            dbgProdutosNFE.addClColunas("valoricms", "$Icms", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);
            dbgProdutosNFE.addClColunas("cst", "CST", 5, true, true, dbgProdutosNFE.Alinhamento_Direita);

            dbgProdutosNFE.addNumberFormat("valorunittributario");
            dbgProdutosNFE.addNumberFormat("desconto");
            dbgProdutosNFE.addNumberFormat("total");
            dbgProdutosNFE.addNumberFormat("basecalcicms");
            dbgProdutosNFE.addNumberFormat("aliqoutaicms");
            dbgProdutosNFE.addNumberFormat("valoricms");

            dbgProdutosNFE.setAjustaColunaAoPainel(true);
            dbgProdutosNFE.getjTable().addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, "keypressed : " + String.valueOf(e.getKeyCode()), "Pré-Venda", "AVISO");    
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        Prevenda_UI_ProcessarTeclaDOWN();

                    } else if (e.getKeyChar() == '\n') {
                        //e.consume();
                    } else {
                        Tabela_RecursosAdicionais.TratarTeclaPressionada(e, dbgProdutosNFE);
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, "keyReleased : " + String.valueOf(e.getKeyCode()), "Pré-Venda", "AVISO");    

                    int nCol = dbgProdutosNFE.getjTable().getSelectedColumn();

                    if (e.getKeyChar() == '\n') {

                        Prevenda_UI_ProcessarTeclaEnter(nCol, e);

                    }
                }
            });

            /* UI_IniciarGridItens_AtualizarGrid(null);  
            dbgProdutosNFE.getjTable().setDefaultEditor(Object.class,  new EditorTabela(new Prevenda_Validacoes()));
            dbgProdutosNFE.getTbDinnamuS().addTableModelListener(new jtabledinnamus_ModelListener( dbgProdutosNFE.getjTable() ));            
            dbgProdutosNFE.getjTable().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"selectNextColumnCell"); 
             */
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }

    }

    private void Prevenda_UI_ProcessarTeclaEnter(int nCol, KeyEvent e) {
        try {
            if (nCol == 2) {
                int nLinhaAtual = dbgProdutosNFE.getjTable().getSelectedRow();
                int nRet = Prevenda_ProcessarPesquisaGridItens(e);
                e.consume();
                if (nRet == 0) {
                    dbgProdutosNFE.getjTable().setRowSelectionInterval(nLinhaAtual, nLinhaAtual);
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "PRODUTO NÃO LOCALIZADO", "Pré-Venda", "AVISO");
                    Long nIdUnico = Long.parseLong(dbgProdutosNFE.getTbDinnamuS().getValorCelula("idunico").toString());
                    int nSEQ = Integer.parseInt(dbgProdutosNFE.getTbDinnamuS().getValorCelula("seq").toString());
                    NovoItensorc_Vazio(nIdUnico, nSEQ);
                    nLinhaAtual = dbgProdutosNFE.getjTable().getSelectedRow();
                    UI_IniciarGridItens_AtualizarGrid(null);
                    dbgProdutosNFE.getjTable().setRowSelectionInterval(nLinhaAtual, nLinhaAtual);
                    dbgProdutosNFE.getjTable().setColumnSelectionInterval(1, 1);
                } else if (nRet == -1) {
                    dbgProdutosNFE.getjTable().setColumnSelectionInterval(1, 1);
                }
            } else if (nCol > 0 && nCol < dbgProdutosNFE.getTbDinnamuS().getColumnCount() - 1) {

                String NomeColuna = dbgProdutosNFE.getTbDinnamuS().getColumnName(nCol - 1);
                String UltimoCampoGrid = Sistema.getDadosLoja(Sistema.getLojaAtual(), true).getString("UltimoCampoGridCotOrcVenda");
                Long nCodProd = Long.valueOf(dbgProdutosNFE.getTbDinnamuS().getValorCelula("codprod").toString());

                if (NomeColuna.equalsIgnoreCase(UltimoCampoGrid) && nCodProd > 0) {

                    int nTotalLinha = dbgProdutosNFE.getjTable().getRowCount();
                    int nLinhaAtual = dbgProdutosNFE.getjTable().getSelectedRow();
                    if (nTotalLinha == nLinhaAtual + 1) {
                        Prevenda_UI_ProcessarTeclaDOWN();
                    }
                    dbgProdutosNFE.getjTable().setRowSelectionInterval(nLinhaAtual + 1, nLinhaAtual + 1);
                    dbgProdutosNFE.getjTable().setColumnSelectionInterval(1, 1);

                } else {
                    Prevenda_ProcessarDemaisCamposGrid();
                }
            }

        } catch (Exception ex) {

            dbgProdutosNFE.getTbDinnamuS().getValorCelula("idunico");
            LogDinnamus.Log(ex, true);
        }

    }

    private void Prevenda_UI_ProcessarTeclaDOWN() {
        try {

            Object[] ObjLinha = dbgProdutosNFE.TratarLinhaSelecionada(dbgProdutosNFE.getjTable());
            if (ObjLinha != null) {
                int nSEQ = Integer.valueOf(ObjLinha[0].toString());
                ResultSet rsItens = ItensorcRN.Itensorc_Listar(getDadosorc().getCodigo(), nSEQ);
                try {
                    if (rsItens.next()) {
                        int nTotalLinha = dbgProdutosNFE.getjTable().getRowCount();
                        int nLinhaAtual = dbgProdutosNFE.getjTable().getSelectedRow();
                        if (rsItens.getLong("codprod") > 0 && nLinhaAtual + 1 == nTotalLinha) {
                            NovoItensorc_Vazio();
                            UI_IniciarGridItens_AtualizarGrid(null);
                            dbgProdutosNFE.getjTable().setRowSelectionInterval(nTotalLinha - 1, nTotalLinha - 1);
                            dbgProdutosNFE.getjTable().setColumnSelectionInterval(1, 1);
                        }
                    }
                } catch (SQLException ex) {
                    LogDinnamus.Log(ex, true);
                }
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }

    /**
     * @return the persistencia
     */
    /**
     * @return the nfeconfig
     */
    /**
     * @param nfeconfig the nfeconfig to set
     */
    public void setNfeconfig(OffNfceConfig nfeconfig) {
        this.nfeconfig = nfeconfig;
    }

    /**
     * @return the dest
     */
    public OffNfeDest getDest() {
        return destUI;
    }

    /**
     * @param dest the dest to set
     */
    public void setDest(OffNfeDest dest) {
        this.destUI = dest;
    }

    public class Prevenda_Validacoes implements ValidarCelula {

        public boolean Validar(String cColuna, Object obj) {
            try {
                if (!"DESCRICAO".equalsIgnoreCase(cColuna)) {
                    Long Codprod = Long.parseLong(dbgProdutosNFE.getTbDinnamuS().getValorCelula("codprod").toString());
                    if (Codprod == 0) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "INFORME UM PRODUTO PARA COMEÇAR", "Pré-Venda", "AVISO");
                        return false;
                    }
                }

                if ("DESCP".equalsIgnoreCase(cColuna)) {
                    Float PercDesconto = Float.parseFloat(obj.toString());

                    if (PercDesconto < 0f || PercDesconto >= 100f) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "DESCONTO DIGITADO ESTA FORA DA FAIXA PERMITIDA[1-99]%", "Pré-Venda", "AVISO");
                        return false;
                    }

                    Float nQT = Float.parseFloat(dbgProdutosNFE.getTbDinnamuS().getValorCelula("quantidade").toString());
                    Float nPreco = Float.parseFloat(dbgProdutosNFE.getTbDinnamuS().getValorCelula("preco").toString());
                    Float nDesconto = NumeroArredondar.Arredondar2((nQT * nPreco) * (PercDesconto / 100), 2);
                    Float nSubTotalItem = (nQT * nPreco) - nDesconto;
                    dbgProdutosNFE.getTbDinnamuS().setValorCelular("descv", nDesconto);

                } else if ("DESCV".equalsIgnoreCase(cColuna)) {
                    Float ValorDesconto = Float.parseFloat(obj.toString());
                    Float nQT = Float.parseFloat(dbgProdutosNFE.getTbDinnamuS().getValorCelula("quantidade").toString());
                    Float nPreco = Float.parseFloat(dbgProdutosNFE.getTbDinnamuS().getValorCelula("preco").toString());
                    Float nSubTotalItem = nQT * nPreco; //Float.parseFloat( dbgPrevenda_Itens.getTbDinnamuS().getValorCelula("total").toString());
                    Float nPercDesconto = 0f;

                    if (ValorDesconto < 0f) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O DESCONTO NÃO PODE SER NEGATIVO", "Pré-Venda", "AVISO");
                        return false;
                    }

                    if (ValorDesconto >= nSubTotalItem) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O DESCONTO NÃO PODE SER MAIOR OU IGUAL AO VALOR DO ITEM", "Pré-Venda", "AVISO");
                        return false;
                    }

                    nPercDesconto = NumeroArredondar.Arredondar2(ValorDesconto / nSubTotalItem, 2) * 100;
                    dbgProdutosNFE.getTbDinnamuS().setValorCelular("descp", nPercDesconto);

                } else if ("QUANTIDADE".equalsIgnoreCase(cColuna) || "PRECO".equalsIgnoreCase(cColuna)) {
                    Float nQuantidade = 0f;
                    Float nPreco = 0f;
                    if ("QUANTIDADE".equalsIgnoreCase(cColuna)) {
                        nQuantidade = Float.parseFloat(obj.toString());
                        Long nQtMaxima = Sistema.getDadosLoja(Sistema.getLojaAtual(), true).getLong("qtmaxima");

                        nQtMaxima = (nQtMaxima == 0 ? 99999l : nQtMaxima);

                        if (nQuantidade > nQtMaxima) {
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "A QUANTIDADE INFORMADA EXCEDEU O LIMITE PERMITIDO[" + nQtMaxima + "]", "Pré-Venda", "AVISO");
                            return false;
                        }

                        nPreco = Float.parseFloat(dbgProdutosNFE.getTbDinnamuS().getValorCelula("preco").toString());
                    } else {
                        nPreco = Float.parseFloat(obj.toString());
                        nQuantidade = Float.parseFloat(dbgProdutosNFE.getTbDinnamuS().getValorCelula("quantidade").toString());
                    }
                    Float PercDesconto = Float.parseFloat(dbgProdutosNFE.getTbDinnamuS().getValorCelula("descp").toString());
                    Float nDesconto = NumeroArredondar.Arredondar2((nQuantidade * nPreco) * (PercDesconto / 100), 2);
                    dbgProdutosNFE.getTbDinnamuS().setValorCelular("descv", nDesconto);
                }
                return true;
            } catch (Exception e) {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "INFORMAÇÃO DIGITADA INVÁLIDA", "Pré-Venda", "AVISO");

                //JOptionPane.showMessageDialog(null, "INFORMAÇÃO DIGITADA INVÁLIDA", "Pré-Venda", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

    }

    private boolean Prevenda_TotalizarItemPrevenda(Long nCodigoVenda, int nSEQ) {
        try {
            Float nValorItem = 0f, nQuantidade = 0f, nDesconto = 0f, nSubTotalItem = 0f;

            ResultSet rsItens = ItensorcRN.Itensorc_Listar(nCodigoVenda);
            while (rsItens.next()) {
                nSEQ = rsItens.getInt("seq");
                nValorItem = rsItens.getFloat("preco");
                nQuantidade = rsItens.getFloat("quantidade");
                nDesconto = rsItens.getFloat("descv");
                nSubTotalItem = NumeroArredondar.Arredondar2(nValorItem * nQuantidade - nDesconto, 2);

                // if(!ItensorcRN.Itensorc_AtualizaSubtotal(nCodigoVenda, nSEQ, nSubTotalItem)){
                //     return false;
                ///}
            }
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    private void Prevenda_UI_TotalizarVenda(Long nCodigoVenda) {
        try {

            Float nSubTotal = Prevenda_TotalizarPrevenda(nCodigoVenda);

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }

    private Float Prevenda_TotalizarPrevenda(Long nCodigoVenda) {
        try {
            Float nValorItem = 0f, nQuantidade = 0f, nDesconto = 0f, nSubTotalItem = 0f, nTotalVenda = 0f;

            ResultSet rsItens = ItensorcRN.Itensorc_Listar(nCodigoVenda);
            while (rsItens.next()) {
                nValorItem = rsItens.getFloat("preco");
                nQuantidade = rsItens.getFloat("quantidade");
                nDesconto = rsItens.getFloat("descv");
                nSubTotalItem = NumeroArredondar.Arredondar2(nValorItem * nQuantidade - nDesconto, 2);
                nTotalVenda += nSubTotalItem;

            }
            return nTotalVenda;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return -1f;
        }
    }

    private boolean Prevenda_ProcessarDemaisCamposGrid() {
        try {
            Long nDadosOrc_Codigo = getDadosorc().getCodigo();
            int nLinhaAtual = dbgProdutosNFE.LinhaAtualModel();
            Prevenda_UI_TotalizarVenda(nDadosOrc_Codigo);
            dbgProdutosNFE.getTbDinnamuS().fireTableRowsUpdated(nLinhaAtual, nLinhaAtual);

            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    private boolean Prevenda_ECodigoAlfaNumerico(String cCodigo) {
        try {

            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    private int Prevenda_ProcessarPesquisaGridItens(KeyEvent e) {
        try {

            int nColunaAtual = dbgProdutosNFE.getjTable().getSelectedColumn();
            int nLinhaAtual = dbgProdutosNFE.LinhaAtualModel();

            if (nLinhaAtual >= 0) {
                String cTextoLocalizar = dbgProdutosNFE.getValorCelula("descricao").toString();
                int nSeq = Integer.valueOf(dbgProdutosNFE.getValorCelula("seq").toString());
                Long nDadosOrc_Codigo = getDadosorc().getCodigo();
                Long nItensOrc_Idunico = Long.valueOf(dbgProdutosNFE.getValorCelula("idunico").toString());//arItensorc.get(nLinhaAtual).getIdunico();

                //int nTabelaPreco=0;
                if (cTextoLocalizar.trim().length() > 0) {
                    String cCodigoProduto = "";
                    cTextoLocalizar = cTextoLocalizar.trim();
                    ResultSet rsDadosProduto = null;
                    if (cTextoLocalizar.matches("[0-9]+")) {
                        rsDadosProduto = Prevenda_LocalizaProduto(cTextoLocalizar);
                        rsDadosProduto.beforeFirst();
                        if (!rsDadosProduto.next()) {
                            return 0; // Codigo Não localizado
                        }
                    } else {
                        cCodigoProduto = (new frmPesquisarProduto(null, true, nCodigoFilial, cTextoLocalizar)).getCodigoProduto();

                        if (cCodigoProduto.trim().length() > 0) {
                            rsDadosProduto = Prevenda_LocalizaProduto(cCodigoProduto);
                        } else {
                            return -1;
                        }
                    }
                    try {
                        rsDadosProduto.beforeFirst();
                        if (rsDadosProduto.next()) {
                            if (Prevenda_IncluirProdutoVenda(nSeq, cCodigoProduto, nDadosOrc_Codigo, nItensOrc_Idunico, rsDadosProduto, true)) {
                                Prevenda_TotalizarItemPrevenda(nDadosOrc_Codigo, nSeq);
                                Prevenda_UI_TotalizarVenda(nDadosOrc_Codigo);
                                dbgProdutosNFE.setRsDados(ItensorcRN.Itensorc_Listar_2(nDadosOrc_Codigo), true);
                                dbgProdutosNFE.getjTable().setRowSelectionInterval(nLinhaAtual, nLinhaAtual);
                                Tabela_RecursosAdicionais.Grid_PosicionarCursor(dbgProdutosNFE, Tabela_RecursosAdicionais.Grid_UltimoCampoGridCotOrcVenda());
                                return 1;
                            }
                        } else {
                            return -1;
                        }
                    } catch (SQLException ex) {
                        LogDinnamus.Log(ex, true);
                        return -1;
                    }
                }
            }

        } catch (Exception ex) {
            LogDinnamus.Log(ex, true);
            return -1;

        }
        return -1;
    }

    private ResultSet Prevenda_LocalizaProduto(String cCodigoProduto) {
        try {

            ResultSet rs = cadproduto.Pesquisar(cCodigoProduto, Sistema.getCodigoLojaMatriz(), true, 0, 0, 0, 0f, true, true, Sistema.CodigoDaFilial_LojaAtual());

            return rs;

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return null;
        }
    }

    private boolean Prevenda_IncluirProdutoVenda(int nSeq, String cCodigoProduto, Long Dadosorc_Codigo, Long nItensOrc_Idunico, ResultSet rsDadosProduto, boolean bRegistroNovo) {
        try {

            //Long nItensorc_Idunico = DAO_RepositorioLocal.NovoValorIdentidade("itensorc", Sistema.getLojaAtual(), pdvgerenciar.CodigoPDV());
            Long IG_ChaveUnica = rsDadosProduto.getLong("IG_ChaveUnica");
            String cNome = rsDadosProduto.getString("CP_Nome");
            Float ITP_PrecoVenda = rsDadosProduto.getFloat("ITP_PrecoVenda");
            String cTributacaoIcms = (rsDadosProduto.getString("Tributaçãoicms") == null ? "1" : rsDadosProduto.getString("Tributaçãoicms"));
            String cAliquota = (rsDadosProduto.getString("Codaliquota") == null ? "01" : rsDadosProduto.getString("Codaliquota"));
            Float nPercentualDeIcms = (rsDadosProduto.getFloat("Percentualdeicms") == 0f ? 17f : rsDadosProduto.getFloat("Percentualdeicms"));
            String TP_Descricao = rsDadosProduto.getString("TP_Descricao");
            String Unidade = rsDadosProduto.getString("unidade");
            Boolean nFracionado = rsDadosProduto.getBoolean("francionado");

            Itensorc i = SetarItensorc(getDadosorc(), nSeq, nItensOrc_Idunico, IG_ChaveUnica, cCodigoProduto, cNome, 1f, ITP_PrecoVenda, ITP_PrecoVenda,
                    cTributacaoIcms,
                    cAliquota,
                    nPercentualDeIcms, Sistema.getLojaAtual(),
                    TP_Descricao, -1l, pdvgerenciar.CodigoPDV(), nFracionado, "", Unidade, bRegistroNovo);

            i = ItensorcRN.Itensorc_Incluir(DAO_RepositorioLocal.getCnRepLocal(), i, Sistema.getLojaAtual(), 0, false, true);
            if (i.getIdunico() == 0l) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    private boolean UI_CarregarDadosLoja() {
        try {

            ResultSet rs = Sistema.getDadosLojaAtualSistema();
            //if(rs.next()){
            //lblDadosEmpresa.setText(rs.getString("nome"));
            //}  

            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
            return false;
        }

    }

    public Long RegistraItem_RegistrarEntidades_Dadosorc() {
        Long nRet = Long.valueOf(0);
        Date dDataAbertura;
        Integer nCodigoVendedor, nCodigoCliente, nCodigoOperador;
        String cControleCX = "";
        String cNomeVendedor, cNomeCliente, cNomeOperador;
        try {

            //DataHora.getStringToData(cControleCX)
            dDataAbertura = ManipularData.DataAtual();
            nCodigoOperador = UsuarioSistema.getIdUsuarioLogado();

            nCodigoCliente = 0;//Integer.valueOf(getDadosorc().getCodcliente()) ;
            cNomeCliente = "";

            // nRet=RegistraItem_RegistrarEntidades_Dadosorc_Persistencia( dDataAbertura, nCodigoVendedor,cNomeVendedor, nCodigoCliente, cNomeCliente, nCodigoOperador, cNomeOperador,Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(),0, "", pdvgerenciar.CodigoPDV());
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nRet;
    }

    public static Itensorc SetarItensorc(Dadosorc dDadosorc, int nSeq, Long nItensorc_Idunico, Long nIG_ChaveUnica, String cCodigoProduto, String cNomeProduto, Float nQuantidade, Float nPreco, Float nSubTotal, String cSituacaoTributaria, String cAliquota, Float nPercentualIcms, int nCodigoLoja, String cTabela, Long nCodigoReexibicao, int nCodigoPDV, boolean bFracionado, String cCodMov, String Unidade, boolean bRegistroNovo) {
        Itensorc i = null;
        try {
            i = new Itensorc();
            //i.setIdunico(DAO_RepositorioLocal.NovoValorIdentidade("itensorc", Sistema.getLojaAtual(),nCodigoPDV));
            if (!bRegistroNovo) {
                i.setIdunico(nItensorc_Idunico);
                i.setSeq(nSeq);
            }
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
            i.setTabela(cTabela);
            i.setSt(cSituacaoTributaria);
            i.setLoja(nCodigoLoja);
            i.setFracionado(bFracionado);
            i.setCodmov(cCodMov);
            i.setUnidade(Unidade);
            if (nCodigoReexibicao == 0) {
                i = ItensorcRN.RegistraItem_RegistrarEntidades_Itensorc_Persistencia(i, nCodigoReexibicao, bRegistroNovo);
            } else {
                i.setSeq(nSeq);
                i.setIdunico(nItensorc_Idunico);
            }

            if (i.getIdunico() == 0) {
                return null;
            }

        } catch (Exception e) {
            i = null;
            LogDinnamus.Log(e);
        }
        return i;
    }

    public Long RegistraItem_RegistrarEntidades_Dadosorc_Persistencia(Date dDataAbertura, Integer nCodigoVendedor, String cNomeVendedor, Integer nCodigoCliente, String cNomeCliente, Integer nCodigoOperador, String cNomeOperador, Integer nLoja, Integer nCodigoOpCaixa, Integer nCodigoObjetoCaixa, String cControleCX, int nCodigoPDV) {
        Long nRetorno = Long.valueOf(0);
        try {

            getDadosorc().setCodigo(DAO_RepositorioLocal.NovoValorIdentidade("dadosorc", Sistema.getLojaAtual(), nCodigoPDV));
            getDadosorc().setData(dDataAbertura);
            getDadosorc().setHora(Timestamp.valueOf(DataHora.getDataHoraAtual()));
            getDadosorc().setLoja(nLoja);
            getDadosorc().setFilial(nCodigoFilial);
            getDadosorc().setCodoperador(nCodigoOperador.toString());
            getDadosorc().setOperador(cNomeOperador);
            getDadosorc().setVendedor(nCodigoVendedor.toString());
            getDadosorc().setCodvendedor(nCodigoVendedor.toString());
            getDadosorc().setControleCx("");
            getDadosorc().setObjetoCaixa(0);
            getDadosorc().setCodcaixa(0);
            getDadosorc().setRecebido("N");
            getDadosorc().setFeito("S");
            getDadosorc().setPdv(nCodigoPDV);
            getDadosorc().setNaosinc(true);
            nRetorno = DadosorcRN.Dadosorc_Incluir(DAO_RepositorioLocal.getCnRepLocal(), dadosorc, 0, true, true);

        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return nRetorno;
    }

        private boolean  Inicializar_UI_dbgDetalhesVenda(){
    
        try {
            
           dbgDetalhesVenda.getTbDinnamuS().setModeloUsandoColecao(true);
            TreeMap<String, ImageIcon> imagens = new TreeMap<String, ImageIcon>();
            imagens.put("OK", new ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png")));
            imagens.put("PEND", new ImageIcon(getClass().getResource("/dinnamus/ui/img/No-entry.png")));
             dbgDetalhesVenda.setColunaComTamanhosEmPercentual(true);
            dbgDetalhesVenda.addClColunas("codigo", "Codigo", 10);
            dbgDetalhesVenda.addClColunas("seq", "Seq", 10);
            //dbgDetalhesVenda.addClColunas("mesclagem", "Mesclagem", 10);
            dbgDetalhesVenda.addClColunas("valor", "Valor", 10, true,false,SwingConstants.RIGHT);
            dbgDetalhesVenda.addClColunas("data", "Data", 8);
            dbgDetalhesVenda.addClColunas("hora", "Hora", 7);            
           // dbgDetalhesVenda.addClColunas("cod_vend", "Cod.Vend", 30);
            dbgDetalhesVenda.addClColunas("vendedor", "Vend", 20);
        
            dbgDetalhesVenda.addClColunas("cliente", "Cliente", 30);                      
            dbgDetalhesVenda.getjTable().setFont(new Font("Courier New", Font.BOLD, 14));
            //dbgDetalhesVenda.sett
            dbgDetalhesVenda.setExibirBarra(true);
            dbgDetalhesVenda.FormatoDataAdicionar("data", new SimpleDateFormat("dd/MM/yyyy"));
            dbgDetalhesVenda.FormatoDataAdicionar("hora", new SimpleDateFormat("HH:mm:ss"));
            dbgDetalhesVenda.getjTable().addKeyListener(new KeyListener() {
               @Override
               public void keyTyped(KeyEvent e) {
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               @Override
               public void keyPressed(KeyEvent e) {
                   if(e.getKeyChar()=='\n'){
                      
                   }
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               @Override
               public void keyReleased(KeyEvent e) {
                  // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }
           });
            
            return true;
        } catch (Exception e) {
            
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, e.getMessage(), "Grid de Detalhes", "AVISO");
            return false;
        }
    }
    private boolean  Atualizar_dbgDetalhesVenda(){
    
        try {

            //HashMap<String, NumberFormat> hashMapData = new HashMap<String, NumberFormat>();
            //hashMapData.put("VALOR", (FormatarNumero.getNf().getCurrencyInstance()));
            //dbgVendedores.setNumberFormat(hashMapData);
            //Codigo,sequencia,id,codvendedor,Vendedor,Data,Cliente,Valor
            //dbgVendedores.setTamColunas(new int[]{40,40,40,40,100,50,100,50});
            //HashMap<String, DefaultTableCellRenderer> hmalinhamento = new HashMap<String, DefaultTableCellRenderer>();
            //DefaultTableCellRenderer alinhamento=new DefaultTableCellRenderer();
            //alinhamento.setHorizontalAlignment(SwingConstants.RIGHT);
            //hmalinhamento.put("VALOR", alinhamento);
            //dbgVendedores.setAlinhamentos(hmalinhamento);
            ResultSet rs =GerenciarCaixa.Caixa_MovimentoDetalhado(15);
            
            dbgDetalhesVenda.setRsDados(rs);
            if(rs.getRow()>0){
                dbgDetalhesVenda.getjTable().setRowSelectionInterval(0, 0);
            }
            
            String query = rs.getStatement().'.toString();
            
            int totalRegistros = Dao_Generica.ContarRegistros(query, Sistema.isOnline());
            
            
            
            return true;
        } catch (Exception e) {
            
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, e.getMessage(), "Grid de Detalhes", "AVISO");
            return false;
        }
    }
    
    private void PainelConsultaVenda_UI_Iniciar(){
        CardLayout card = (CardLayout) PainelGeral.getLayout();
        card.show(PainelGeral, "PainelVendas");
        Inicializar_UI_dbgDetalhesVenda();
        Atualizar_dbgDetalhesVenda();
    }
}
