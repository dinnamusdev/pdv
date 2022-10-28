/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmResumoCaixa.java
 *
 * Created on 20/09/2010, 21:24:27
 */

package dinnamus.ui.InteracaoUsuario.Venda;

import br.com.FormatarNumeros;
import br.com.ecf.ECFDinnamuS;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import com.toedter.calendar.JDateChooser;
import dinnamus.entidades.recursos.Recursos;
import br.data.DataHora;
import br.valor.formatar.FormatarNumero;
import br.String.ManipulacaoString;
import br.data.ManipularData;
import br.TratamentoNulo.TratamentoNulos;
import br.com.ui.ItemLista;
import br.com.ui.JTableDinnamuS;
import br.com.ui.JTableDinnamuS_SubTabela;
import br.ui.teclas.DefinirAtalhos2;
import MetodosDeNegocio.Seguranca.Login;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import dinnamus.metodosnegocio.venda.caixa.ComprovanteNaoFiscal;
import MetodosDeNegocio.Venda.GerenciarCaixa;
import dinnamus.metodosnegocio.venda.caixa.PDVComprovanteFiscal;
import MetodosDeNegocio.Venda.pdvgerenciar;
import UI.Seguranca.ValidarAcessoAoProcesso;
import br.com.ui.BloquearTela;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.impressao.EscPos;
import br.impressao.Perifericos;
import dinnamus.metodosnegocio.venda.caixa.PDVComprovante;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author dti
 */
public class frmResumoCaixa extends javax.swing.JDialog {

    /** Creates new form frmResumoCaixa */
            //ChamarFormEntradaSaidaCaixa(0);/
    private static boolean  ConsultaGeralCaixa=false;
    private static Integer CodigoCaixa=0;
    private static String ControleCX="";
    private static Integer CodigoOperadorCaixa=0;
    private static Integer CodigoLoja=0;
    private  boolean bJaCarregou=false;
    private String ImpressoraComprovante_Modelo;
    /**
     * @return the bJaCarregou
     */
    public boolean isbJaCarregou() {
        return bJaCarregou;
    }

    /**
     * @param abJaCarregou the bJaCarregou to set
     */
    public void setbJaCarregou(boolean abJaCarregou) {
        this.bJaCarregou = abJaCarregou;
    }
    private Float SaldoFinal=0f;
    /**
     * @return the CodigoCaixa
     */
    public static Integer getCodigoCaixa() {
        return CodigoCaixa;
    }

    /**
     * @param aCodigoCaixa the CodigoCaixa to set
     */
    public static void setCodigoCaixa(Integer aCodigoCaixa) {
        CodigoCaixa = aCodigoCaixa;
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
     * @return the CodigoOperadorCaixa
     */
    public static Integer getCodigoOperadorCaixa() {
        return CodigoOperadorCaixa;
    }

    /**
     * @param aCodigoOperadorCaixa the CodigoOperadorCaixa to set
     */
    public static void setCodigoOperadorCaixa(Integer aCodigoOperadorCaixa) {
        CodigoOperadorCaixa = aCodigoOperadorCaixa;
    }

    /**
     * @return the CodigoLoja
     */
    public static Integer getCodigoLoja() {
        return CodigoLoja;
    }

    /**
     * @param aCodigoLoja the CodigoLoja to set
     */
    public static void setCodigoLoja(Integer aCodigoLoja) {
        CodigoLoja = aCodigoLoja;
    }
    private boolean IniciouOK = false;
    public frmResumoCaixa(java.awt.Frame parent, boolean modal,Integer nCodigoLoja, Integer nCodigoCaixa, String cControleCX, int nCodigoOperadorCaixa, boolean bConsultaGeral) {
            super(parent, modal);
            initComponents();
            setbJaCarregou(false);
            this.ConsultaGeralCaixa = bConsultaGeral;
            setCodigoCaixa(nCodigoCaixa);
            setControleCX(cControleCX);
            setCodigoLoja(nCodigoLoja);
            setCodigoOperadorCaixa(nCodigoOperadorCaixa);
            Integer nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "ConsultaGeralCaixa", Sistema.getLojaAtual(), true, "Resumo de Caixa");
            if(nCodigoUsuario>0){
                IniciouOK =InicializarUI(getCodigoCaixa(),getCodigoLoja());
                if(IniciouOK){                    
                    this.setSize(getToolkit().getScreenSize());                    
                    this.setLocationRelativeTo(null);                     
                    Funcoes("F9");
                    bJaCarregou=true;
                   // System.out.println(  "w :" + dbgConsolidado.getSize().getWidth() + " h " + dbgConsolidado.getSize());
                }else{
                    this.dispose();
                }
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, Recursos.getMensagens().getString("acesso.negado"), "Resumo de Caixa", "AVISO");
                this.dispose();
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

        GrupoOpcaoTipoVisualizacao = new javax.swing.ButtonGroup();
        opTipoRelatorio = new javax.swing.ButtonGroup();
        PainelPrincipal = new javax.swing.JPanel();
        PainelPesquisar = new javax.swing.JPanel();
        lblTextoAbertura = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbLojas = new javax.swing.JComboBox();
        cbCaixa = new javax.swing.JComboBox();
        cbAberturas = new javax.swing.JComboBox();
        lblTextoAbertura1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtData = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        PainelBotoes = new javax.swing.JPanel();
        btImprimir = new javax.swing.JButton();
        btMovimentar = new javax.swing.JButton();
        btConsultar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        PainelCorpo = new javax.swing.JPanel();
        PainelSituacaoCaixa = new javax.swing.JPanel();
        txtDataAbertura = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtOperadorAbertura = new javax.swing.JTextField();
        lblTextoAbertura5 = new javax.swing.JLabel();
        lblTextoAbertura9 = new javax.swing.JLabel();
        txtDataFechamento = new javax.swing.JTextField();
        lblTextoAbertura6 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtOperadorFechamento = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        PainelMovimentacao = new javax.swing.JPanel();
        painelEntrada1 = new javax.swing.JPanel();
        dbgEntradas = new br.com.ui.JTableDinnamuS();
        lblTextoAbertura7 = new javax.swing.JLabel();
        painelSaida1 = new javax.swing.JPanel();
        dbgSaidas = new br.com.ui.JTableDinnamuS();
        lblTextoAbertura8 = new javax.swing.JLabel();
        lblTextoAbertura12 = new javax.swing.JLabel();
        PainelFechamento = new javax.swing.JPanel();
        PainelSaldoFinal = new javax.swing.JPanel();
        txtSaldoFinal = new javax.swing.JFormattedTextField();
        lblTextoAbertura13 = new javax.swing.JLabel();
        lblTextoAbertura2 = new javax.swing.JLabel();
        txtSaidas = new javax.swing.JFormattedTextField();
        txtEntradas = new javax.swing.JFormattedTextField();
        lblTextoAbertura3 = new javax.swing.JLabel();
        lblTextoAbertura10 = new javax.swing.JLabel();
        lblTextoAbertura14 = new javax.swing.JLabel();
        lblTextoAbertura15 = new javax.swing.JLabel();
        txtVendasEmDinheiro = new javax.swing.JFormattedTextField();
        txtFundoDeCaixa = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblTextoAbertura16 = new javax.swing.JLabel();
        txtRecebimentosEmDinheiro = new javax.swing.JFormattedTextField();
        PainelVendasRealizadas = new javax.swing.JPanel();
        tbPrincipal = new javax.swing.JTabbedPane();
        PainelCaixaResumido = new javax.swing.JPanel();
        dbgConsolidado = new br.com.ui.JTableDinnamuS();
        PainelVendasEmDetalhe = new javax.swing.JPanel();
        dbgDetalhesVenda = new br.com.ui.JTableDinnamuS();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        PainelResumoPorVendedor = new javax.swing.JPanel();
        dbgVendedores = new br.com.ui.JTableDinnamuS();
        PainelRecebimentosEmDetalhe = new javax.swing.JPanel();
        dbgDetalhesRecebimento = new br.com.ui.JTableDinnamuS();
        lblTextoAbertura11 = new javax.swing.JLabel();
        txtTotalGeral = new javax.swing.JFormattedTextField();
        lblTextoAbertura4 = new javax.swing.JLabel();
        PainelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btFechar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Resumo de Caixa");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setBackground(new java.awt.Color(0, 0, 0));
        PainelPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelPesquisar.setBackground(new java.awt.Color(0, 0, 0));
        PainelPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        PainelPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        PainelPesquisar.setLayout(new java.awt.GridBagLayout());

        lblTextoAbertura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTextoAbertura.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura.setText("Caixa  :");
        lblTextoAbertura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisar.add(lblTextoAbertura, gridBagConstraints);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText(" PARAMETROS DA CONSULTA - [F5]");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jLabel1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelPesquisar.add(jLabel1, gridBagConstraints);

        cbLojas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbLojasItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPesquisar.add(cbLojas, gridBagConstraints);

        cbCaixa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCaixaItemStateChanged(evt);
            }
        });
        cbCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCaixaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPesquisar.add(cbCaixa, gridBagConstraints);

        cbAberturas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbAberturasItemStateChanged(evt);
            }
        });
        cbAberturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAberturasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPesquisar.add(cbAberturas, gridBagConstraints);

        lblTextoAbertura1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTextoAbertura1.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura1.setText("Abertura  :");
        lblTextoAbertura1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisar.add(lblTextoAbertura1, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Dia  :");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisar.add(jLabel3, gridBagConstraints);

        txtData.setOpaque(false);
        txtData.getDateEditor().getUiComponent().addKeyListener(
            new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        //Integer nLoja = Integer.valueOf(((ItemLista)cbLojas.getSelectedItem()).getIndice().toString());
                        //MetodosUI_Auxiliares.PreencherCombo(cbCaixa, GerenciarCaixa.ListarCaixas_QueTenhanMovimento(nLoja , UsuarioSistema.getIdUsuarioLogadoCaixa(), 0, txtData.getDate() ), "nome", "codigo", true);
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}

            }
        );
        txtData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPesquisar.add(txtData, gridBagConstraints);

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));
        PainelBotoes.setOpaque(false);
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        btImprimir.setBackground(new java.awt.Color(0, 0, 0));
        btImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/printer.png"))); // NOI18N
        btImprimir.setMnemonic('I');
        btImprimir.setText("Imprimir - F8");
        btImprimir.setBorderPainted(false);
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelBotoes.add(btImprimir, gridBagConstraints);

        btMovimentar.setBackground(new java.awt.Color(0, 0, 0));
        btMovimentar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btMovimentar.setForeground(new java.awt.Color(255, 255, 255));
        btMovimentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/cart_put.png"))); // NOI18N
        btMovimentar.setText("Movimentar Caixa - [F7]");
        btMovimentar.setBorderPainted(false);
        btMovimentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMovimentarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelBotoes.add(btMovimentar, gridBagConstraints);

        btConsultar.setBackground(new java.awt.Color(0, 0, 0));
        btConsultar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btConsultar.setForeground(new java.awt.Color(255, 255, 255));
        btConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Refresh.png"))); // NOI18N
        btConsultar.setText("Atualizar - [F6]");
        btConsultar.setBorderPainted(false);
        btConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConsultarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelBotoes.add(btConsultar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 6);
        PainelPesquisar.add(PainelBotoes, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Loja  :");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisar.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 5, 5);
        PainelPrincipal.add(PainelPesquisar, gridBagConstraints);

        PainelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        PainelCorpo.setOpaque(false);
        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        PainelSituacaoCaixa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PainelSituacaoCaixa.setLayout(new java.awt.GridBagLayout());

        txtDataAbertura.setEditable(false);
        txtDataAbertura.setBackground(new java.awt.Color(255, 255, 255));
        txtDataAbertura.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        txtDataAbertura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSituacaoCaixa.add(txtDataAbertura, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        jLabel4.setText("OP:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelSituacaoCaixa.add(jLabel4, gridBagConstraints);

        txtOperadorAbertura.setEditable(false);
        txtOperadorAbertura.setBackground(new java.awt.Color(255, 255, 255));
        txtOperadorAbertura.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        txtOperadorAbertura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSituacaoCaixa.add(txtOperadorAbertura, gridBagConstraints);

        lblTextoAbertura5.setBackground(new java.awt.Color(0, 0, 0));
        lblTextoAbertura5.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblTextoAbertura5.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoAbertura5.setText("SITUAÇÃO DO CAIXA");
        lblTextoAbertura5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblTextoAbertura5.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelSituacaoCaixa.add(lblTextoAbertura5, gridBagConstraints);

        lblTextoAbertura9.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        lblTextoAbertura9.setText("FECHAMENTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelSituacaoCaixa.add(lblTextoAbertura9, gridBagConstraints);

        txtDataFechamento.setEditable(false);
        txtDataFechamento.setBackground(new java.awt.Color(255, 255, 255));
        txtDataFechamento.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        txtDataFechamento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSituacaoCaixa.add(txtDataFechamento, gridBagConstraints);

        lblTextoAbertura6.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura6.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        lblTextoAbertura6.setText("ABERTURA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelSituacaoCaixa.add(lblTextoAbertura6, gridBagConstraints);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        jLabel6.setText("OP:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelSituacaoCaixa.add(jLabel6, gridBagConstraints);

        txtOperadorFechamento.setEditable(false);
        txtOperadorFechamento.setBackground(new java.awt.Color(255, 255, 255));
        txtOperadorFechamento.setFont(new java.awt.Font("Cordia New", 1, 20)); // NOI18N
        txtOperadorFechamento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSituacaoCaixa.add(txtOperadorFechamento, gridBagConstraints);

        txtStatus.setEditable(false);
        txtStatus.setBackground(new java.awt.Color(255, 255, 255));
        txtStatus.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStatus.setText("ABERTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSituacaoCaixa.add(txtStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 8, 6);
        PainelCorpo.add(PainelSituacaoCaixa, gridBagConstraints);

        PainelMovimentacao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        PainelMovimentacao.setOpaque(false);
        PainelMovimentacao.setLayout(new java.awt.GridBagLayout());

        painelEntrada1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        painelEntrada1.setOpaque(false);
        painelEntrada1.setLayout(new java.awt.GridBagLayout());

        dbgEntradas.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        painelEntrada1.add(dbgEntradas, gridBagConstraints);

        lblTextoAbertura7.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTextoAbertura7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoAbertura7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money_add.png"))); // NOI18N
        lblTextoAbertura7.setText("ENTRADAS NO CAIXA");
        lblTextoAbertura7.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelEntrada1.add(lblTextoAbertura7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 9, 8);
        PainelMovimentacao.add(painelEntrada1, gridBagConstraints);

        painelSaida1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        painelSaida1.setOpaque(false);
        painelSaida1.setLayout(new java.awt.GridBagLayout());

        dbgSaidas.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        painelSaida1.add(dbgSaidas, gridBagConstraints);

        lblTextoAbertura8.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTextoAbertura8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoAbertura8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money_delete.png"))); // NOI18N
        lblTextoAbertura8.setText(" SAIDAS NO CAIXA");
        lblTextoAbertura8.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelSaida1.add(lblTextoAbertura8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 9, 8);
        PainelMovimentacao.add(painelSaida1, gridBagConstraints);

        lblTextoAbertura12.setBackground(new java.awt.Color(0, 0, 0));
        lblTextoAbertura12.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        lblTextoAbertura12.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoAbertura12.setText(" MOVIMENTAÇÕES DO CAIXA");
        lblTextoAbertura12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblTextoAbertura12.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelMovimentacao.add(lblTextoAbertura12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 7);
        PainelCorpo.add(PainelMovimentacao, gridBagConstraints);

        PainelFechamento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PainelFechamento.setOpaque(false);
        PainelFechamento.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpo.add(PainelFechamento, gridBagConstraints);

        PainelSaldoFinal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PainelSaldoFinal.setLayout(new java.awt.GridBagLayout());

        txtSaldoFinal.setEditable(false);
        txtSaldoFinal.setBackground(new java.awt.Color(255, 255, 255));
        txtSaldoFinal.setBorder(null);
        txtSaldoFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtSaldoFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSaldoFinal.setText("0,00");
        txtSaldoFinal.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelSaldoFinal.add(txtSaldoFinal, gridBagConstraints);

        lblTextoAbertura13.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura13.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        lblTextoAbertura13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTextoAbertura13.setText("(=)SALDO FINAL");
        lblTextoAbertura13.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(lblTextoAbertura13, gridBagConstraints);

        lblTextoAbertura2.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura2.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        lblTextoAbertura2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTextoAbertura2.setText("(-)SAIDAS DO CAIXA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(lblTextoAbertura2, gridBagConstraints);

        txtSaidas.setEditable(false);
        txtSaidas.setBorder(null);
        txtSaidas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtSaidas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSaidas.setText("0,00");
        txtSaidas.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(txtSaidas, gridBagConstraints);

        txtEntradas.setEditable(false);
        txtEntradas.setBackground(new java.awt.Color(255, 255, 255));
        txtEntradas.setBorder(null);
        txtEntradas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtEntradas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEntradas.setText("0,00");
        txtEntradas.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(txtEntradas, gridBagConstraints);

        lblTextoAbertura3.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura3.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        lblTextoAbertura3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTextoAbertura3.setText("(+)VALOR VENDIDO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(lblTextoAbertura3, gridBagConstraints);

        lblTextoAbertura10.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura10.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        lblTextoAbertura10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTextoAbertura10.setText("(+)ENTRADAS NO CAIXA");
        lblTextoAbertura10.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(lblTextoAbertura10, gridBagConstraints);

        lblTextoAbertura14.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura14.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        lblTextoAbertura14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTextoAbertura14.setText("(+)SALDO ANTERIOR");
        lblTextoAbertura14.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(lblTextoAbertura14, gridBagConstraints);

        lblTextoAbertura15.setBackground(new java.awt.Color(0, 0, 0));
        lblTextoAbertura15.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblTextoAbertura15.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoAbertura15.setText("SALDO DO CAIXA (Dinheiro)");
        lblTextoAbertura15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblTextoAbertura15.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(lblTextoAbertura15, gridBagConstraints);

        txtVendasEmDinheiro.setEditable(false);
        txtVendasEmDinheiro.setBorder(null);
        txtVendasEmDinheiro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtVendasEmDinheiro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtVendasEmDinheiro.setText("0,00");
        txtVendasEmDinheiro.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(txtVendasEmDinheiro, gridBagConstraints);

        txtFundoDeCaixa.setEditable(false);
        txtFundoDeCaixa.setBackground(new java.awt.Color(255, 255, 255));
        txtFundoDeCaixa.setBorder(null);
        txtFundoDeCaixa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtFundoDeCaixa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFundoDeCaixa.setText("0,00");
        txtFundoDeCaixa.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(txtFundoDeCaixa, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelSaldoFinal.add(jSeparator1, gridBagConstraints);

        lblTextoAbertura16.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura16.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        lblTextoAbertura16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTextoAbertura16.setText("(+)VALOR RECEBIMENTOS");
        lblTextoAbertura16.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(lblTextoAbertura16, gridBagConstraints);

        txtRecebimentosEmDinheiro.setEditable(false);
        txtRecebimentosEmDinheiro.setBackground(new java.awt.Color(255, 255, 255));
        txtRecebimentosEmDinheiro.setBorder(null);
        txtRecebimentosEmDinheiro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtRecebimentosEmDinheiro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRecebimentosEmDinheiro.setText("0,00");
        txtRecebimentosEmDinheiro.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelSaldoFinal.add(txtRecebimentosEmDinheiro, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 0, 0, 7);
        PainelCorpo.add(PainelSaldoFinal, gridBagConstraints);

        PainelVendasRealizadas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        PainelVendasRealizadas.setOpaque(false);
        PainelVendasRealizadas.setLayout(new java.awt.GridBagLayout());

        tbPrincipal.setFont(new java.awt.Font("Cordia New", 0, 18)); // NOI18N

        PainelCaixaResumido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelCaixaResumido.setOpaque(false);
        PainelCaixaResumido.setLayout(new java.awt.GridBagLayout());

        dbgConsolidado.setExibirBarra(false);
        dbgConsolidado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelCaixaResumido.add(dbgConsolidado, gridBagConstraints);

        tbPrincipal.addTab("Caixa Resumido - [F9]", new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money.png")), PainelCaixaResumido); // NOI18N

        PainelVendasEmDetalhe.setLayout(new java.awt.GridBagLayout());

        dbgDetalhesVenda.setExibirBarra(false);
        dbgDetalhesVenda.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelVendasEmDetalhe.add(dbgDetalhesVenda, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png"))); // NOI18N
        jLabel7.setText("Efetivada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Erase.png"))); // NOI18N
        jLabel8.setText("Cancelada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        PainelVendasEmDetalhe.add(jPanel1, gridBagConstraints);

        tbPrincipal.addTab("Vendas em Detalhe - [F10]", new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/calculator_edit.png")), PainelVendasEmDetalhe); // NOI18N

        PainelResumoPorVendedor.setLayout(new java.awt.GridBagLayout());

        dbgVendedores.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelResumoPorVendedor.add(dbgVendedores, gridBagConstraints);

        tbPrincipal.addTab("Resumo por Vendedor - [F11]", new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/user.png")), PainelResumoPorVendedor); // NOI18N

        PainelRecebimentosEmDetalhe.setLayout(new java.awt.GridBagLayout());

        dbgDetalhesRecebimento.setExibirBarra(false);
        dbgDetalhesRecebimento.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelRecebimentosEmDetalhe.add(dbgDetalhesRecebimento, gridBagConstraints);

        tbPrincipal.addTab("Recebimentos em Detalhe - [F12]", new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Notes.png")), PainelRecebimentosEmDetalhe); // NOI18N

        tbPrincipal.setSelectedIndex(1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        PainelVendasRealizadas.add(tbPrincipal, gridBagConstraints);

        lblTextoAbertura11.setBackground(new java.awt.Color(0, 0, 0));
        lblTextoAbertura11.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        lblTextoAbertura11.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoAbertura11.setText("  VENDAS REALIZADAS");
        lblTextoAbertura11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblTextoAbertura11.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelVendasRealizadas.add(lblTextoAbertura11, gridBagConstraints);

        txtTotalGeral.setEditable(false);
        txtTotalGeral.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalGeral.setBorder(null);
        txtTotalGeral.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtTotalGeral.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalGeral.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelVendasRealizadas.add(txtTotalGeral, gridBagConstraints);

        lblTextoAbertura4.setBackground(new java.awt.Color(255, 255, 255));
        lblTextoAbertura4.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        lblTextoAbertura4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoAbertura4.setText("TOTAL DE OP. REALIZADAS >>");
        lblTextoAbertura4.setToolTipText("");
        lblTextoAbertura4.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelVendasRealizadas.add(lblTextoAbertura4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 6, 10);
        PainelCorpo.add(PainelVendasRealizadas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelPrincipal.add(PainelCorpo, gridBagConstraints);

        PainelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Sum.png"))); // NOI18N
        lblTitulo.setText("RESUMO DE CAIXA");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelTitulo.add(lblTitulo, gridBagConstraints);

        btFechar.setBackground(new java.awt.Color(0, 0, 0));
        btFechar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btFechar.setForeground(new java.awt.Color(255, 255, 255));
        btFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btFechar.setMnemonic('F');
        btFechar.setText("FECHAR");
        btFechar.setBorderPainted(false);
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 10);
        PainelTitulo.add(btFechar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        PainelTitulo.add(jLabel2, gridBagConstraints);

        lblLogo.setBackground(new java.awt.Color(255, 255, 204));
        lblLogo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/barra logo dinnamus.JPG"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.6;
        PainelTitulo.add(lblLogo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelTitulo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelPrincipal, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER) {
            

        }
}//GEN-LAST:event_txtDataKeyPressed

    private void cbCaixaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCaixaItemStateChanged
        // TODO add your handling code here:
        try {
            if(cbCaixa.getSelectedItem()!=null){
                Integer nCodigoCaixa =0;
                nCodigoCaixa = Integer.valueOf(
                                ((ItemLista)cbCaixa.getSelectedItem()).getIndice().toString()
                                );
               InicalizarUI_CbAberturas(nCodigoCaixa,getControleCX());
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_cbCaixaItemStateChanged

    private void cbAberturasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbAberturasItemStateChanged
        // TODO add your handling code here:
        try {
            //if(getControleCX().length()==0){
              btConsultarActionPerformed(null);
            //}
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        
    }//GEN-LAST:event_cbAberturasItemStateChanged

    private void cbLojasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbLojasItemStateChanged

        try {
            Integer nCodigoLoja=0;
            if(cbLojas.getItemCount()>0){
                nCodigoLoja = Integer.parseInt(((ItemLista) cbLojas.getSelectedItem()).getIndice().toString());
                Inicializar_UIcbCaixa(getCodigoCaixa(), nCodigoLoja,getCodigoOperadorCaixa());
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

    }//GEN-LAST:event_cbLojasItemStateChanged
    private void LimparCampos(){
        try {
            txtDataAbertura.setText("");
            txtOperadorAbertura.setText("");
            txtOperadorFechamento.setText("");
            txtDataFechamento.setText("");
            txtStatus.setText("");
            dbgConsolidado.setRsDados(GerenciarCaixa.Caixa_Saldo(""));
            //dbgConsolidado.update(dbgConsolidado.getGraphics());
//            tbPrincipal.update(tbPrincipal.getGraphics());
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

    }
    
    private String PegarControleCXAtual(){
        String cRet="";
        try {
         
            if(cbAberturas.getSelectedItem()!=null && isbJaCarregou()){
                ItemLista i = (ItemLista)cbAberturas.getSelectedItem();
                cRet = i.getIndice().toString(); 
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cRet;
    }
    private void btConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConsultarActionPerformed
        // TODO add your handling code here:
        try {
            if(!IniciouOK) { return ;}
            if(cbAberturas.getSelectedItem()!=null && bJaCarregou){
                ItemLista i = (ItemLista)cbAberturas.getSelectedItem();
                String cControleCx = i.getIndice().toString();
                if(cControleCx.length()>0){
                    ResultSet rs = GerenciarCaixa.Caixas_ConsultarAberturas(cControleCx);
                
                    if(rs.next()){
                        setControleCX(rs.getString("controlecx"));
                        setCodigoCaixa(rs.getInt("objetocaixa"));
                        setCodigoOperadorCaixa(rs.getInt("codcaixa"));
                        txtDataAbertura.setText( DataHora.getData(DataHora.FormatDataPadrao, rs.getDate("DataAbertura")) + " - " + rs.getTime("HoraAbertura"));
                        txtOperadorAbertura.setText(rs.getString("OperadorAbertura"));
                        if(rs.next()){
                            txtDataFechamento.setText( DataHora.getData(DataHora.FormatDataPadrao, rs.getDate("DataAbertura")) + " - " + rs.getTime("HoraAbertura"));
                            txtOperadorFechamento.setText(rs.getString("OperadorAbertura"));
                            txtStatus.setText("FECHADO");
                        }else{
                            txtStatus.setText("ABERTO");
                            txtOperadorFechamento.setText("");
                            txtDataFechamento.setText("");
                        }
                        AtualizarGrids(cControleCx);
                        
                        //painelConsolidado.update(painelConsolidado.getGraphics());
                    }else{
                        LimparCampos();
                    }
                }
            }else{
                LimparCampos();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_btConsultarActionPerformed
    private void AtualizarGrids(String cControleCx){
            Atualizar_DbgConsolidado(cControleCx);
            Atualizar_DbgVendedor(cControleCx);
            Atualizar_DbgEntradasESaidas(cControleCx,"E");
            Atualizar_DbgEntradasESaidas(cControleCx,"S");
            Atualizar_dbgDetalhesVenda(cControleCx);
            Atualizar_dbgDetalhesRecebimento(cControleCx);
            CalcularSaldoFinal(cControleCx);
            
    }
private void cbCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCaixaActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_cbCaixaActionPerformed

private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
// TODO add your handling code here:

    Object [] opcoes = {"Fiscal","N-Fiscal"};

    String cRet =MetodosUI_Auxiliares_1.InputBox(null, "Escolha o tipo de relatorio", "Relatorio de Fechamento de Caixa", "AVISO", opcoes,""); 
    if(cRet!=null){
        int nOp = (cRet.equalsIgnoreCase("Fiscal")  ? 2 : 1);
        if(Imprimir2(nOp, PegarControleCXAtual(), Sistema.getLojaAtual())){
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Resumo do Caixa Impresso com Sucesso", "Resumo de Caixa", "INFO");
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível imprimir Resumo do Caixa", "Resumo de Caixa", "AVISO"); 
        }
    }
}//GEN-LAST:event_btImprimirActionPerformed

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btFecharActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        btConsultarActionPerformed(null);
        
    }//GEN-LAST:event_formWindowOpened

    private void btMovimentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMovimentarActionPerformed
        // TODO add your handling code here:
        ChamarFormEntradaSaidaCaixa(0);
    }//GEN-LAST:event_btMovimentarActionPerformed

    private void cbAberturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAberturasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAberturasActionPerformed

    /**
    * @param args the command line arguments
    */
    private BloquearTela bloq = new BloquearTela();
    
      public BloquearTela getBloq() {
        if(bloq==null){
          bloq = new BloquearTela();
        }
        return bloq;
    }
    private void ChamarFormEntradaSaidaCaixa(int nCodigoMov){
        getBloq().Tela_Bloquear(this, 0.5f, Color.BLACK);
          ChamarFormEntradaSaidaCaixa_Acao(nCodigoMov);
          getBloq().Tela_DesBloquear();
    }
    private void ChamarFormEntradaSaidaCaixa_Acao(int nCodigoMov){
        try {
            
            getBloq().Tela_Bloquear(this, 0.5f, Color.BLACK);
            if(txtStatus.getText().equalsIgnoreCase("fechado")){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O caixa selecionado esta fechado. Não é possivel realizar alterações", "Resumo de Caixa", "AVISO");
                return;
            }
            
            new frmMovimentacaoCaixa(null, true, PegarControleCXAtual(),getCodigoOperadorCaixa(),getCodigoCaixa(),nCodigoMov);
            Atualizar_DbgConsolidado(getControleCX());
            Atualizar_DbgEntradasESaidas(getControleCX(), "E");
            Atualizar_DbgEntradasESaidas(getControleCX(), "S");
            CalcularSaldoFinal(getControleCX());
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

    }
    private boolean InicalizarUI_CbAberturas(Integer nCodigoCaixa, String cControleCxAtual){
        boolean bRet=false;

        try {
            ResultSet rs = GerenciarCaixa.Caixas_ConsultarAberturas(nCodigoCaixa);
            String cControleCxAbertura="";            
            ItemLista i=null ;
            cbAberturas.removeAllItems();
            while(rs.next()){
                 if(TratamentoNulos.getTratarString().Tratar(rs.getString("tipooperacao"), "").equalsIgnoreCase("A")){
                     cControleCxAbertura=TratamentoNulos.getTratarString().Tratar(rs.getString("controlecx"),"");
                     i=new ItemLista();
                     i.setIndice(cControleCxAbertura);
                     i.setDescricao( DataHora.getData(DataHora.FormatDataPadrao, rs.getDate("DataAbertura")) + " - " + rs.getTime("HoraAbertura").toString() );
                     cbAberturas.addItem(i);
                 }
            }
            if(cbAberturas.getItemCount()>0 && cControleCxAtual.length()>0){
               MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbAberturas, cControleCxAtual);
               if(ConsultaGeralCaixa){
                   cbAberturas.setEnabled(true);
               }else{
                  cbAberturas.setEnabled(false);
               }
            }else{
                cbAberturas.setEnabled(true);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrupoOpcaoTipoVisualizacao;
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelCaixaResumido;
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelFechamento;
    private javax.swing.JPanel PainelMovimentacao;
    private javax.swing.JPanel PainelPesquisar;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelRecebimentosEmDetalhe;
    private javax.swing.JPanel PainelResumoPorVendedor;
    private javax.swing.JPanel PainelSaldoFinal;
    private javax.swing.JPanel PainelSituacaoCaixa;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JPanel PainelVendasEmDetalhe;
    private javax.swing.JPanel PainelVendasRealizadas;
    private javax.swing.JButton btConsultar;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btMovimentar;
    private javax.swing.JComboBox cbAberturas;
    private javax.swing.JComboBox cbCaixa;
    private javax.swing.JComboBox cbLojas;
    private br.com.ui.JTableDinnamuS dbgConsolidado;
    private br.com.ui.JTableDinnamuS dbgDetalhesRecebimento;
    private br.com.ui.JTableDinnamuS dbgDetalhesVenda;
    private br.com.ui.JTableDinnamuS dbgEntradas;
    private br.com.ui.JTableDinnamuS dbgSaidas;
    private br.com.ui.JTableDinnamuS dbgVendedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTextoAbertura;
    private javax.swing.JLabel lblTextoAbertura1;
    private javax.swing.JLabel lblTextoAbertura10;
    private javax.swing.JLabel lblTextoAbertura11;
    private javax.swing.JLabel lblTextoAbertura12;
    private javax.swing.JLabel lblTextoAbertura13;
    private javax.swing.JLabel lblTextoAbertura14;
    private javax.swing.JLabel lblTextoAbertura15;
    private javax.swing.JLabel lblTextoAbertura16;
    private javax.swing.JLabel lblTextoAbertura2;
    private javax.swing.JLabel lblTextoAbertura3;
    private javax.swing.JLabel lblTextoAbertura4;
    private javax.swing.JLabel lblTextoAbertura5;
    private javax.swing.JLabel lblTextoAbertura6;
    private javax.swing.JLabel lblTextoAbertura7;
    private javax.swing.JLabel lblTextoAbertura8;
    private javax.swing.JLabel lblTextoAbertura9;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.ButtonGroup opTipoRelatorio;
    private javax.swing.JPanel painelEntrada1;
    private javax.swing.JPanel painelSaida1;
    private javax.swing.JTabbedPane tbPrincipal;
    private com.toedter.calendar.JDateChooser txtData;
    private javax.swing.JTextField txtDataAbertura;
    private javax.swing.JTextField txtDataFechamento;
    private javax.swing.JFormattedTextField txtEntradas;
    private javax.swing.JFormattedTextField txtFundoDeCaixa;
    private javax.swing.JTextField txtOperadorAbertura;
    private javax.swing.JTextField txtOperadorFechamento;
    private javax.swing.JFormattedTextField txtRecebimentosEmDinheiro;
    private javax.swing.JFormattedTextField txtSaidas;
    private javax.swing.JFormattedTextField txtSaldoFinal;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JFormattedTextField txtTotalGeral;
    private javax.swing.JFormattedTextField txtVendasEmDinheiro;
    // End of variables declaration//GEN-END:variables
    private boolean InicializarUI(int nCodigoCaixa, int nCodigoLoja){
        boolean bRet=false;
        try {

            bRet= MetodosUI_Auxiliares_1.PreencherCombo(cbLojas, Sistema.getRsLojasDaRede(Sistema.getCodigoLojaMatriz()), "Nome", "Codigo", true);
            /*if(nCodigoLoja==0){
               cbLojas.setEnabled(true);
            }else{
               cbLojas.setEnabled(false);
            }*/
            boolean iniciarimp=false;
            if(PDVComprovante.getImpressoraCompravante()==null){            
                iniciarimp=true;
            }else{
                if(!PDVComprovante.getImpressoraCompravante().isOK()){
                    iniciarimp=true;
                }
            }
            if(iniciarimp){
                PDVComprovante.ImpressoraDeComprovante_Iniciar();
            }
            MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbLojas, nCodigoLoja);
            txtData.setDate(ManipularData.DataAtual());
            Inicializar_UI_DbgConsolidado();
            Inicializar_UI_DbgVendedor();
            Inicializar_UI_dbgDetalhesVenda();
            Inicializar_UI_dbgDetalhesRecebimento();
            
            
            ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixasPorPDV( pdvgerenciar.CodigoPDV(), nCodigoLoja, Sistema.isOnline());
            
            if (rsDadosCaixa.next()) {
                nCodigoCaixa = rsDadosCaixa.getInt(("codigo"));
            }
            //cbLojasItemStateChanged(null);
            Inicializar_UIcbCaixa(nCodigoCaixa, nCodigoLoja, getCodigoOperadorCaixa());            
            MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbCaixa,nCodigoCaixa);
            cbCaixaItemStateChanged(null);
            IniciarUI_TeclaAtalho();
            
            //InicalizarUI_CbAberturas(nCodigoCaixa,getControleCX());
            
            //bJaCarregou=true;
            
           
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private void Funcoes(String Tecla){
        try {
            if(Tecla.equalsIgnoreCase("ESCAPE")){
                btFecharActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("F5")){
                cbLojas.requestFocus();
            }else if(Tecla.equalsIgnoreCase("F6")){
                btConsultarActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("F7")){
                btMovimentarActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("F8")){
                btImprimirActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("F9")){
                tbPrincipal.setSelectedIndex(0);
                dbgConsolidado.getjTable().requestFocus();
            }else if(Tecla.equalsIgnoreCase("F10")){
                tbPrincipal.setSelectedIndex(1);
                dbgDetalhesVenda.getjTable().requestFocus();
            }else if(Tecla.equalsIgnoreCase("F11")){
                tbPrincipal.setSelectedIndex(2);
                dbgVendedores.getjTable().requestFocus();
            }else if(Tecla.equalsIgnoreCase("F12")){
                tbPrincipal.setSelectedIndex(3);
                dbgDetalhesRecebimento.getjTable().requestFocus();
            }
            
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    
     private boolean IniciarUI_TeclaAtalho(){
        try {
         
            AbstractAction TeclaAtalhos  = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                        String Tecla = e.getSource().toString();                
                        Funcoes(Tecla);
                }
            };      
            String Teclas[] ={"ESCAPE" ,"F5", "F6","F7","F8","F9","F10","F11","F12"};            
            DefinirAtalhos2.Definir(PainelPrincipal, Teclas, TeclaAtalhos);            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }   
    private boolean Inicializar_UIcbCaixa(int nCodigoCaixa, int nCodigoLoja,  int nCodigoOperadorCaixa){
        boolean bRet=false;
        try {
            bRet= MetodosUI_Auxiliares_1.PreencherCombo(cbCaixa, GerenciarCaixa.ListarCaixas(nCodigoLoja, 0,0,  pdvgerenciar.CodigoPDV(),false), "Nome", "Codigo", true);
            
            /*if(nCodigoCaixa>0){
                MetodosUI_Auxiliares.SetarOpcaoCombo(cbCaixa, nCodigoCaixa);
                cbCaixa.setEnabled(false);
            }else{
                cbCaixa.setEnabled(true);
            }*/
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private boolean Inicializar_UI_DbgConsolidado(){
        boolean bret=false;
        try {
            //dbgConsolidado.TamanhoColunas();
            //''
            Dimension d= PainelCaixaResumido.getSize();
            Dimension d1= PainelCaixaResumido.getPreferredSize();
            
            HashMap<String, NumberFormat> hashMapData = new HashMap<String, NumberFormat>();
            hashMapData.put("ABERTURA", (FormatarNumero.getNf().getCurrencyInstance()));
            hashMapData.put("VALOR", (FormatarNumero.getNf().getCurrencyInstance()));
            dbgConsolidado.setNumberFormat(hashMapData);
            dbgConsolidado.setTamColunas(new int[]{20,40,20,20});
            dbgConsolidado.addAlinhamentos("ABERTURA", SwingConstants.RIGHT);
            dbgConsolidado.addAlinhamentos("VALOR", SwingConstants.RIGHT);
            dbgConsolidado.setColunaComTamanhosEmPercentual(true);
            dbgConsolidado.getjTable().setFont(new Font("Courier New", Font.BOLD, 20));
            dbgConsolidado.getjTable().setRowHeight(dbgConsolidado.getjTable().getRowHeight()+20);

            
            bret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bret;
    }
    private int PegarCodigoJtable(JTable tb){
        Integer nCodigoMov=0;
        try {
            int nLinhaSelecionada = tb.getSelectedRow();
            if(nLinhaSelecionada>=0){
               nCodigoMov = Integer.valueOf(tb.getModel().getValueAt(nLinhaSelecionada, 0).toString());               
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nCodigoMov;
    }
        private boolean Inicializar_UI_DbgVendedor(){
        boolean bret=false;
        try {
            //dbgConsolidado.TamanhoColunas();
            //''
            HashMap<String, NumberFormat> hashMapData = new HashMap<String, NumberFormat>();
            hashMapData.put("VALOR", (FormatarNumero.getNf().getCurrencyInstance()));
            dbgVendedores.setNumberFormat(hashMapData);
            dbgVendedores.setTamColunas(new int[]{10,70,20});
            
            dbgVendedores.addAlinhamentos("VALOR", SwingConstants.RIGHT);
            dbgVendedores.setColunaComTamanhosEmPercentual(true);
            dbgVendedores.getjTable().setFont(new Font("Courier New", Font.BOLD, 20));
            MouseListener m =new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                            if(e.getClickCount()==2){
                                
                                ChamarFormEntradaSaidaCaixa(PegarCodigoJtable((JTable) e.getComponent()));

                            }
                }
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e){}
                public void mouseEntered(MouseEvent e){}
                public void mouseExited(MouseEvent e) {}
            };
            KeyListener k = new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        ChamarFormEntradaSaidaCaixa(PegarCodigoJtable((JTable) e.getComponent()));
                    }
                }
                public void keyReleased(KeyEvent e) {}
            };
            dbgEntradas.setNumberFormat(hashMapData);
            dbgEntradas.setTamColunas(new int[]{60,210,100});            
            dbgEntradas.getjTable().addKeyListener(k);
            dbgEntradas.getjTable().addMouseListener(m);
            
            

            dbgSaidas.setNumberFormat(hashMapData);
            dbgSaidas.setTamColunas(new int[]{60,210,100});            
            dbgSaidas.getjTable().addKeyListener(k);
            dbgSaidas.getjTable().addMouseListener(m);




            bret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bret;
    }
    private boolean Atualizar_DbgConsolidado(String cControleCX){
        boolean bret=false;
        try {

            if(cControleCX!=null){
                ResultSet rs =GerenciarCaixa.Caixa_Saldo(cControleCX);
                if(rs!=null){
                    dbgConsolidado.setRsDados(rs);                
                }
                rs.beforeFirst();
                Double nTotalGeral=0d;
                setSaldoFinal(0f);
                while(rs.next()){
                    if(rs.getString("conta").equalsIgnoreCase("")){
                        nTotalGeral += rs.getDouble("Valor");
                   // }else if(rs.getString("conta").equalsIgnoreCase("1.1")){
                   //         setSaldoFinal( getSaldoFinal() + rs.getFloat("Abertura") + rs.getFloat("Valor") );
                    }
                }
                txtTotalGeral.setValue(nTotalGeral);
            }
            bret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bret;
    }
    private boolean Atualizar_DbgEntradasESaidas(String cControleCX, String cTipo){
        boolean bret=false;
        try {

            if(cControleCX!=null){
                
                ResultSet rs =GerenciarCaixa.Caixa_Entradas_E_Saidas(cControleCX, cTipo);
                if(rs!=null){
                    if(cTipo.equalsIgnoreCase("S")){
                        dbgSaidas.setRsDados(rs);
                    }else{
                        dbgEntradas.setRsDados(rs);
                    }
                }               
            }
            bret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bret;
    }
    private void CalcularSaldoFinal(String cControleCxSaldoFinal){
        try {
            TreeMap <String,Double> ValoresCaixa = GerenciarCaixa.Caixa_Saldo_Conta(cControleCxSaldoFinal, "1.1");
                    
            txtSaldoFinal.setValue(ValoresCaixa.get("total"));
            txtSaidas.setValue(ValoresCaixa.get("saidas"));
            txtEntradas.setValue(ValoresCaixa.get("entradas"));
            txtFundoDeCaixa.setValue(ValoresCaixa.get("abertura"));
            txtVendasEmDinheiro.setValue(ValoresCaixa.get("vendas"));
            txtRecebimentosEmDinheiro.setValue(ValoresCaixa.get("recebimentos"));

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }
    private boolean Atualizar_DbgVendedor(String cControleCX){
        boolean bret=false;
        try {

            if(cControleCX!=null){
                ResultSet rs =GerenciarCaixa.Caixa_Saldo_PorVendedor(cControleCX);
                if(rs!=null){
                    dbgVendedores.setRsDados(rs);
                }
            }
            bret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bret;
    }

    /**
     * @return the SaldoFinal
     */
    public Float getSaldoFinal() {
        return SaldoFinal;
    }

    /**
     * @param SaldoFinal the SaldoFinal to set
     */
    public void setSaldoFinal(Float SaldoFinal) {
        this.SaldoFinal = SaldoFinal;
    }
    
    private boolean  Inicializar_UI_dbgDetalhesVenda(){
    
        try {

            //HashMap<String, NumberFormat> hashMapData = new HashMap<String, NumberFormat>();
            //hashMapData.put("VALOR", (FormatarNumero.getNf().getCurrencyInstance()));
            //dbgVendedores.setNumberFormat(hashMapData);
            //Codigo,sequencia,id,codvendedor,Vendedor,Data,Cliente,Valor
            //dbgDetalhesVenda.setTamColunas(new int[]{80,80,80,80,50,130,150,80});
            dbgDetalhesVenda.getTbDinnamuS().setModeloUsandoColecao(true);
            TreeMap<String, ImageIcon> imagens = new TreeMap<String, ImageIcon>();
            imagens.put("S", new ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png")));
            imagens.put("C", new ImageIcon(getClass().getResource("/dinnamus/ui/img/delete.png")));
            
            dbgDetalhesVenda.addClColunas("recebido", "STATUS", 80,true,false,dbgConsolidado.Alinhamento_Centro, imagens,"" );         
            dbgDetalhesVenda.addClColunas("codigo", "Codigo", 100);
            dbgDetalhesVenda.addClColunas("seq", "Seq", 80);
            dbgDetalhesVenda.addClColunas("mesclagem", "Mesclagem", 80);
            dbgDetalhesVenda.addClColunas("numerocomanda", "Comanda", 80);
            dbgDetalhesVenda.addClColunas("valor", "Valor", 80, true,false,SwingConstants.RIGHT);
            dbgDetalhesVenda.addClColunas("data", "Data", 100);
            dbgDetalhesVenda.addClColunas("hora", "Hora", 80);            
            dbgDetalhesVenda.addClColunas("cod_vend", "Cod.Vend", 50);
            dbgDetalhesVenda.addClColunas("vendedor", "Vend", 130);
            dbgDetalhesVenda.addClColunas("cliente", "Cliente", 150);                      
            dbgDetalhesVenda.getjTable().setFont(new Font("Courier New", Font.BOLD, 14));
            //dbgDetalhesVenda.sett
            dbgDetalhesVenda.FormatoDataAdicionar("data", new SimpleDateFormat("dd/MM/yyyy"));
            dbgDetalhesVenda.FormatoDataAdicionar("hora", new SimpleDateFormat("HH:mm:ss"));
            dbgDetalhesVenda.AumentaAlturaLinhas(2f);
            
            final JTableDinnamuS subtabela =new JTableDinnamuS();                        
            subtabela.getTbDinnamuS().setModeloUsandoColecao(true);                        
            subtabela.addClColunas("grupoforma", "F.Pagto", 150);
            subtabela.addClColunas("valor", "Valor", 100, true,false,SwingConstants.RIGHT);
            
                        
            JTableDinnamuS_SubTabela iPagto = new JTableDinnamuS_SubTabela() {
                ResultSet rsModel = null;                
                public ResultSet getModel(Long PK){                   
                       rsModel =GerenciarCaixa.Caixa_MovimentoDetalhado_Pagto(PK);                   
                       return rsModel;
                }
                public JTable getUI() {                        
                        subtabela.setRsDados(rsModel);
                        return subtabela.getjTable();
                }
            };
            dbgDetalhesVenda.addSubTabela("pagto","Pagto", 250, iPagto, "codigo", "codigo");
            dbgDetalhesVenda.addClColunas("sinc", "SINC", 80);  
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, e.getMessage(), "Grid de Detalhes", "AVISO");
            return false;   
        }
    }
    
    private boolean  Inicializar_UI_dbgDetalhesRecebimento(){
    
        try {

            //HashMap<String, NumberFormat> hashMapData = new HashMap<String, NumberFormat>();
            //hashMapData.put("VALOR", (FormatarNumero.getNf().getCurrencyInstance()));
            //dbgVendedores.setNumberFormat(hashMapData);
            //Codigo,sequencia,id,codvendedor,Vendedor,Data,Cliente,Valor
            //dbgDetalhesVenda.setTamColunas(new int[]{80,80,80,80,50,130,150,80});
                        
            TreeMap<String, ImageIcon> imagens = new TreeMap<String, ImageIcon>();
            imagens.put("R", new ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png")));
            imagens.put("K", new ImageIcon(getClass().getResource("/dinnamus/ui/img/delete.png")));
            
            dbgDetalhesRecebimento.addClColunas("recebido", "STATUS", 80,true,false,dbgConsolidado.Alinhamento_Centro, imagens,"" );         
            dbgDetalhesRecebimento.addClColunas("codigo", "Codigo", 80);            
            dbgDetalhesRecebimento.addClColunas("valor", "Valor", 80, true,false,SwingConstants.RIGHT);
            dbgDetalhesRecebimento.addClColunas("data", "Data", 100);
            dbgDetalhesRecebimento.addClColunas("hora", "Hora", 80);                        
            dbgDetalhesRecebimento.addClColunas("cliente", "Cliente", 150);                      
            dbgDetalhesRecebimento.getjTable().setFont(new Font("Courier New", Font.BOLD, 14));
            //dbgDetalhesVenda.sett
            dbgDetalhesRecebimento.FormatoDataAdicionar("data", new SimpleDateFormat("dd/MM/yyyy"));
            dbgDetalhesRecebimento.FormatoDataAdicionar("hora", new SimpleDateFormat("HH:mm:ss"));
            dbgDetalhesRecebimento.AumentaAlturaLinhas(2f);
            
            final JTableDinnamuS subtabela =new JTableDinnamuS();                        
            subtabela.getTbDinnamuS().setModeloUsandoColecao(true);                        
            subtabela.addClColunas("grupoforma", "F.Pagto", 150);
            subtabela.addClColunas("valor", "Valor", 100, true,false,SwingConstants.RIGHT);
            
                        
            JTableDinnamuS_SubTabela iPagto = new JTableDinnamuS_SubTabela() {
                ResultSet rsModel = null;                
                public ResultSet getModel(Long PK){                   
                       rsModel =GerenciarCaixa.Caixa_MovimentoDetalhado_Pagto(PK);                   
                       return rsModel;
                }
                public JTable getUI() {                        
                        subtabela.setRsDados(rsModel);
                        return subtabela.getjTable();
                }
            };
            dbgDetalhesRecebimento.addSubTabela("pagto","Pagto", 250, iPagto, "codigo", "codigo");
            dbgDetalhesRecebimento.addClColunas("sinc", "SINC", 80);  
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, e.getMessage(), "Grid de Detalhes", "AVISO");
            return false;   
        }
    }
    
    private boolean  Atualizar_dbgDetalhesVenda(String cControleCX){
    
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
            ResultSet rs =GerenciarCaixa.Caixa_MovimentoDetalhado(cControleCX);
            //ResultSet rsPagtos =GerenciarCaixa.Caixa_MovimentoDetalhado_Pagto(cControleCX);            
            //
            dbgDetalhesVenda.setRsDados(rs);
          
            
            //dbgDetalhesVenda.getjTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            
            
            return true;
        } catch (Exception e) {
            
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean  Atualizar_dbgDetalhesRecebimento(String cControleCX){
    
        try {
            ResultSet rs =GerenciarCaixa.Caixa_MovimentoDetalhado_Recebimento(cControleCX);     
            dbgDetalhesRecebimento.setRsDados(rs);         
            return true;
        } catch (Exception e) {
            
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    

    private boolean Imprimir2(int nTipoComprovante, String cControleCX, int nCodigoLoja ){
        try {
            
            
            String cTextoRelatorio = ComprovanteNaoFiscal.GerarRelatorio(cControleCX, nCodigoLoja);
            if (nTipoComprovante==1) {                
                //GerenciarPorta porta = getPorta();
                //PDVComprovante.getImpressoraCompravante().Imprimir_Texto(EscPos.CodigoTextoCompactado());
                if(PDVComprovante.getImpressoraCompravante().isOK()){
                    
                    PDVComprovante.getImpressoraCompravante().Imprimir_Texto(EscPos.CodigoTextoCompactado(),EscPos.CodigoAlinhar_Esquerda(), cTextoRelatorio.getBytes());
                    PDVComprovante.getImpressoraCompravante().Imprimir_Texto(Perifericos.BuscarCodigoGaveta());
                    PDVComprovante.getImpressoraCompravante().Imprimir_Texto(Perifericos.BuscarCodigoGuilhotina());
                    
                }
                //porta.Fechar();
            }else{
                ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(),0,0,false);
                if(rsDadosCaixa.next()){
                    ECFDinnamuS ecfcomprovante = new ECFDinnamuS();
                    String Porta = TratamentoNulos.getTratarString().Tratar( rsDadosCaixa.getString("porta"),"COM1");
                    ecfcomprovante.setTipoECF(rsDadosCaixa.getString("IMPRESSORAFISCAL"),Porta);
                    PDVComprovanteFiscal.setEcfDinnmus(ecfcomprovante);
                    PDVComprovanteFiscal.ImprimirCNFV(cTextoRelatorio);
                }
            }
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    
    private boolean Imprimir(int nTipoComprovante, String cControleCX, int nCodigoLoja )
    {
         
        try {
            
            if(PDVComprovante.getImpressoraCompravante().isOK()){    
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar a porta do PDV", "Imprimir Caixa", "AVISO");
                return false;
            }
            
            ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixasPorPDV( pdvgerenciar.CodigoPDV(), nCodigoLoja, Sistema.isOnline());
            ResultSet rsAbrirCaixa = GerenciarCaixa.AbrirCaixa_Consultar(cControleCX, "A");
            ResultSet rsFecharCaixa = GerenciarCaixa.AbrirCaixa_Consultar(cControleCX, "F");
            
            if(!rsDadosCaixa.next()){
                
                return false;
            }
            
            if(!rsAbrirCaixa.next()){
                return false;
            }
            
            ResultSet rsDadosOpcx = Login.DadosUsuario(rsDadosCaixa.getInt("operadorcx"), false);
            if(!rsDadosOpcx.next()){
                return false;
            }
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(EscPos.CodigoTextoCompactado());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("DEMONSTRATIVO DE CAIXA");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("CAIXA :" + rsDadosCaixa.getString("nome"));
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("DADOS DA ABERTURA");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("  DATA/HORA   :" +  DataHora.getData("dd/MM/yyyy", rsAbrirCaixa.getDate("data"))+  " - "+  DataHora.getData(DataHora.FormatHoraPadrao, rsAbrirCaixa.getTimestamp("hora")) );
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("  OPERADOR    :" +  rsDadosOpcx.getString("nome") );            
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("ENCERRAMENTO DO DIA");
            if(rsFecharCaixa.next()){
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto("  DATA/HORA   :" + DataHora.getData("dd/MM/yyyy", rsFecharCaixa.getDate("data"))+  " - "+  DataHora.getData(DataHora.FormatHoraPadrao, rsFecharCaixa.getTimestamp("hora")) );                ResultSet rsDadosOpcxFechou = Login.DadosUsuario(rsFecharCaixa.getInt("codcaixa"), false);
                if(!rsDadosOpcxFechou.next()){
                    return false;
                }
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto("  OPERADOR    :"+ rsDadosOpcxFechou.getString("NOME")); 
            }else{
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto("  DATA/HORA   :");
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto("  OPERADOR    :");
            }
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("ABERTURA");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            
            
            rsAbrirCaixa.absolute(0); 
            Float nValorAberturaDinheiro=0f;
            while(rsAbrirCaixa.next()){
                if(rsAbrirCaixa.getString("conta").trim().equalsIgnoreCase("1.1")){
                   nValorAberturaDinheiro=rsAbrirCaixa.getFloat("valor");                    
                }
            }            
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("   ABERTURA DO CAIXA R$ " + FormatarNumeros.FormatarParaMoeda(nValorAberturaDinheiro));
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("FATURAMENTO");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            ResultSet rsDadosMovimento  =GerenciarCaixa.Caixa_Saldo(cControleCX);
            String cDescricao="", cConta="",cLinha="",strValor="";
            Float nRecebimento=0f;
            Float nTotalFaturamento =0f;
            Float nValorDinheiro=0f;
            while(rsDadosMovimento.next()){
                 cConta=rsDadosMovimento.getString("conta");
                 cDescricao=rsDadosMovimento.getString("descricao");
                 nRecebimento = rsDadosMovimento.getFloat("valor");
                 if(cConta.equalsIgnoreCase("1.1")){
                     nValorDinheiro += nRecebimento;
                 }
                 cLinha = "   " + cConta + (cConta.length()==0 ? "  " : "");
                 
                 cLinha= ManipulacaoString.FormataPADL(3,cLinha," ") + " " + ManipulacaoString.FormataPADR(20, cDescricao, " ");
                 if(cConta.trim().equalsIgnoreCase("")){
                    nTotalFaturamento  += nRecebimento;
                 }
                 
                 //ManipulacaoString.Replicate(cLinha, WIDTH)
               strValor =  "R$ " + FormatarNumeros.FormatarParaMoeda( nRecebimento);
               cLinha= cLinha + ManipulacaoString.FormataPADL(48 -  cLinha.length(), strValor, " ");
               
               PDVComprovante.getImpressoraCompravante().Imprimir_Texto(cLinha);
            }
            
            // ENTRADAS DE CAIXA
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("TOTAL DO FATURAMENTO" + ManipulacaoString.FormataPADL(48-"TOTAL DO FATURAMENTO".length() ,"R$ " +  FormatarNumeros.FormatarParaMoeda(nTotalFaturamento), " "));            
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("(+)REFORÇO DE CAIXA");    
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(" ");
            ResultSet rsEntradas = GerenciarCaixa.Caixa_Entradas_E_Saidas(cControleCX, "E");
            Float nTotalEntradas =0f;
            while(rsEntradas.next()){
                cLinha = rsEntradas.getInt("cod") + "  " + rsEntradas.getString("conta");
                nTotalEntradas  +=rsEntradas.getFloat("valor");
                strValor = FormatarNumeros.FormatarParaMoeda(rsEntradas.getFloat("valor"));
                cLinha  = cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," ");
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto(cLinha);
                //SaldoCaixa.AddItem Padr(IIf(IsNull(!Codigo), " ", !Codigo), 3, " ") & "  " & cDescricao & Padl(IIf(IsNull(!valor), 0, "R$ " & Format(!valor, "###,##0.00")), 15)
            }
            if(nTotalEntradas==0f){
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto("****** SEM REGISTRO DE ENTRADAS ******");
            }
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cLinha="TOTAL DE ENTRADAS";
            strValor = " R$ "+ FormatarNumeros.FormatarParaMoeda(nTotalEntradas);
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," "));
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            
            // SAIDAS DE CAIXA
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("(-)SAIDAS DE CAIXA");    
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(" ");
            ResultSet rsSaidas = GerenciarCaixa.Caixa_Entradas_E_Saidas(cControleCX, "S");
            Float nTotalSaidas =0f;
            while(rsSaidas.next()){
                cLinha = rsSaidas.getInt("cod") + "  " + rsSaidas.getString("conta");
                nTotalSaidas  +=rsSaidas.getFloat("valor");
                strValor = FormatarNumeros.FormatarParaMoeda(rsSaidas.getFloat("valor"));
                cLinha  = cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," ");
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto(cLinha);
                //SaldoCaixa.AddItem Padr(IIf(IsNull(!Codigo), " ", !Codigo), 3, " ") & "  " & cDescricao & Padl(IIf(IsNull(!valor), 0, "R$ " & Format(!valor, "###,##0.00")), 15)
            }
            if(nTotalSaidas==0f){
               PDVComprovante.getImpressoraCompravante().Imprimir_Texto("****** SEM REGISTRO DE SAIDAS ******");
            }
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cLinha="TOTAL DE SAIDAS";
            strValor = " R$ " + FormatarNumeros.FormatarParaMoeda(nTotalSaidas);
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," "));
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());    
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("SALDO ATUAL=(DINH.)+(REFORCO)-(SAIDA)");
            Float nSaldoCaixa = nValorDinheiro + nTotalEntradas - nTotalSaidas;
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa));
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("TOTAL EM CX.=(DINH.)+(REFORCO)-(SAIDA)+(ABERTURA)");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa+nValorAberturaDinheiro));
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("RESUMO POR VENDEDORES");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            ResultSet rsMovimentoVendedor = GerenciarCaixa.Caixa_Saldo_PorVendedor(cControleCX);
            Float nValorTotalVendedor=0f;
            while(rsMovimentoVendedor.next()){
                
                nValorTotalVendedor += rsMovimentoVendedor.getFloat("valor");
                cLinha = ManipulacaoString.FormataPADL(5, String.valueOf(rsMovimentoVendedor.getInt("cod")) , " ") + "  "+  rsMovimentoVendedor.getString("vendedor");
                strValor =  FormatarNumeros.FormatarParaMoeda(rsMovimentoVendedor.getFloat("valor"));
                PDVComprovante.getImpressoraCompravante().Imprimir_Texto(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor, " "));
                        
            }
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto("TOTAIS DOS VENDEDORES R$ " + FormatarNumeros.FormatarParaMoeda(nValorTotalVendedor));
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(" ");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(" ");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(" ");
            PDVComprovante.getImpressoraCompravante().Imprimir_Texto(" ");
            PDVComprovante.getImpressoraCompravante().Fechar();
            //PDVComprovanteNaoFiscal.AcionarGuilhotina(strValor)
            //porta.Fechar();
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
            return false;
        }finally{
            //porta.Fechar();
            return true;
        }
    }
    private String GerarRelatorio(int nTipoComprovante, String cControleCX, int nCodigoLoja )
    {
        //GerenciarPorta porta = PDVComprovanteNaoFiscal.getPorta(false);
        try {
            
                      
            ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixasPorPDV( pdvgerenciar.CodigoPDV(), nCodigoLoja, Sistema.isOnline());
            ResultSet rsAbrirCaixa = GerenciarCaixa.AbrirCaixa_Consultar(cControleCX, "A");
            ResultSet rsFecharCaixa = GerenciarCaixa.AbrirCaixa_Consultar(cControleCX, "F");
            
            if(!rsDadosCaixa.next()){
                return "";
            }
            
            if(!rsAbrirCaixa.next()){
                return "";
            }
            
            ResultSet rsDadosOpcx = Login.DadosUsuario(rsDadosCaixa.getInt("operadorcx"), false);
            if(!rsDadosOpcx.next()){
                return "";
            }
            String cTexto = "";
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("DEMONSTRATIVO DE CAIXA");
            cTexto=cTexto+("CAIXA :" + rsDadosCaixa.getString("nome"));
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("DADOS DA ABERTURA");
            cTexto=cTexto+("  DATA/HORA   :" +  DataHora.getData("dd/MM/yyyy", rsAbrirCaixa.getDate("data"))+  " - "+  DataHora.getData(DataHora.FormatHoraPadrao, rsAbrirCaixa.getTimestamp("hora")) );
            cTexto=cTexto+("  OPERADOR    :" +  rsDadosOpcx.getString("nome") );            
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("ENCERRAMENTO DO DIA");
            if(rsFecharCaixa.next()){
                cTexto=cTexto+("  DATA/HORA   :" + DataHora.getData("dd/MM/yyyy", rsFecharCaixa.getDate("data"))+  " - "+  DataHora.getData(DataHora.FormatHoraPadrao, rsFecharCaixa.getTimestamp("hora")) );                ResultSet rsDadosOpcxFechou = Login.DadosUsuario(rsFecharCaixa.getInt("codcaixa"), false);
                if(!rsDadosOpcxFechou.next()){
                    return "";
                }
                cTexto=cTexto+("  OPERADOR    :"+ rsDadosOpcxFechou.getString("NOME")); 
            }else{
                cTexto=cTexto+("  DATA/HORA   :");
                cTexto=cTexto+("  OPERADOR    :");
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("ABERTURA");
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            
            
            rsAbrirCaixa.absolute(0); 
            Float nValorAberturaDinheiro=0f;
            while(rsAbrirCaixa.next()){
                if(rsAbrirCaixa.getString("conta").trim().equalsIgnoreCase("1.1")){
                   nValorAberturaDinheiro=rsAbrirCaixa.getFloat("valor");                    
                }
            }            
            cTexto=cTexto+("   ABERTURA DO CAIXA R$ " + FormatarNumeros.FormatarParaMoeda(nValorAberturaDinheiro));
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("FATURAMENTO");
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            ResultSet rsDadosMovimento  =GerenciarCaixa.Caixa_Saldo(cControleCX);
            String cDescricao="", cConta="",cLinha="",strValor="";
            Float nRecebimento=0f;
            Float nTotalFaturamento =0f;
            Float nValorDinheiro=0f;
            while(rsDadosMovimento.next()){
                 cConta=rsDadosMovimento.getString("conta");
                 cDescricao=rsDadosMovimento.getString("descricao");
                 nRecebimento = rsDadosMovimento.getFloat("valor");
                 if(cConta.equalsIgnoreCase("1.1")){
                     nValorDinheiro += nRecebimento;
                 }
                 cLinha = "   " + cConta + (cConta.length()==0 ? "  " : "");
                 
                 cLinha= ManipulacaoString.FormataPADL(3,cLinha," ") + " " + ManipulacaoString.FormataPADR(20, cDescricao, " ");
                 if(cConta.trim().equalsIgnoreCase("")){
                    nTotalFaturamento  += nRecebimento;
                 }
                 
                 //ManipulacaoString.Replicate(cLinha, WIDTH)
               strValor =  "R$ " + FormatarNumeros.FormatarParaMoeda( nRecebimento);
               cLinha= cLinha + ManipulacaoString.FormataPADL(48 -  cLinha.length(), strValor, " ");
               
               cTexto=cTexto+(cLinha);
            }
            
            // ENTRADAS DE CAIXA
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("TOTAL DO FATURAMENTO" + ManipulacaoString.FormataPADL(48-"TOTAL DO FATURAMENTO".length() ,"R$ " +  FormatarNumeros.FormatarParaMoeda(nTotalFaturamento), " "));            
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("(+)REFORÇO DE CAIXA");    
            cTexto=cTexto+(" ");
            ResultSet rsEntradas = GerenciarCaixa.Caixa_Entradas_E_Saidas(cControleCX, "E");
            Float nTotalEntradas =0f;
            while(rsEntradas.next()){
                cLinha = rsEntradas.getInt("cod") + "  " + rsEntradas.getString("conta");
                nTotalEntradas  +=rsEntradas.getFloat("valor");
                strValor = FormatarNumeros.FormatarParaMoeda(rsEntradas.getFloat("valor"));
                cLinha  = cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," ");
                cTexto=cTexto+(cLinha);
                //SaldoCaixa.AddItem Padr(IIf(IsNull(!Codigo), " ", !Codigo), 3, " ") & "  " & cDescricao & Padl(IIf(IsNull(!valor), 0, "R$ " & Format(!valor, "###,##0.00")), 15)
            }
            if(nTotalEntradas==0f){
                cTexto=cTexto+("****** SEM REGISTRO DE ENTRADAS ******");
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cLinha="TOTAL DE ENTRADAS";
            strValor = " R$ "+ FormatarNumeros.FormatarParaMoeda(nTotalEntradas);
            cTexto=cTexto+(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," "));
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            
            // SAIDAS DE CAIXA
            cTexto=cTexto+("(-)SAIDAS DE CAIXA");    
            cTexto=cTexto+(" ");
            ResultSet rsSaidas = GerenciarCaixa.Caixa_Entradas_E_Saidas(cControleCX, "S");
            Float nTotalSaidas =0f;
            while(rsSaidas.next()){
                cLinha = rsSaidas.getInt("cod") + "  " + rsSaidas.getString("conta");
                nTotalSaidas  +=rsSaidas.getFloat("valor");
                strValor = FormatarNumeros.FormatarParaMoeda(rsSaidas.getFloat("valor"));
                cLinha  = cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," ");
                cTexto=cTexto+(cLinha);
                //SaldoCaixa.AddItem Padr(IIf(IsNull(!Codigo), " ", !Codigo), 3, " ") & "  " & cDescricao & Padl(IIf(IsNull(!valor), 0, "R$ " & Format(!valor, "###,##0.00")), 15)
            }
            if(nTotalSaidas==0f){
               cTexto=cTexto+("****** SEM REGISTRO DE SAIDAS ******");
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cLinha="TOTAL DE SAIDAS";
            strValor = " R$ " + FormatarNumeros.FormatarParaMoeda(nTotalSaidas);
            cTexto=cTexto+(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," "));
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());    
            cTexto=cTexto+("SALDO ATUAL=(DINH.)+(REFORCO)-(SAIDA)");
            Float nSaldoCaixa = nValorDinheiro + nTotalEntradas - nTotalSaidas;
            cTexto=cTexto+("R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa));
            cTexto=cTexto+("TOTAL EM CX.=(DINH.)+(REFORCO)-(SAIDA)+(ABERTURA)");
            cTexto=cTexto+("R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa+nValorAberturaDinheiro));
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("RESUMO POR VENDEDORES");
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            ResultSet rsMovimentoVendedor = GerenciarCaixa.Caixa_Saldo_PorVendedor(cControleCX);
            Float nValorTotalVendedor=0f;
            while(rsMovimentoVendedor.next()){
                
                nValorTotalVendedor += rsMovimentoVendedor.getFloat("valor");
                cLinha = ManipulacaoString.FormataPADL(5, String.valueOf(rsMovimentoVendedor.getInt("cod")) , " ") + "  "+  rsMovimentoVendedor.getString("vendedor");
                strValor =  FormatarNumeros.FormatarParaMoeda(rsMovimentoVendedor.getFloat("valor"));
                cTexto=cTexto+(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor, " "));
                        
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+("TOTAIS DOS VENDEDORES R$ " + FormatarNumeros.FormatarParaMoeda(nValorTotalVendedor));
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora());
            cTexto=cTexto+(" ");
            cTexto=cTexto+(" ");
            cTexto=cTexto+(" ");
            cTexto=cTexto+(" ");
            //PDVComprovanteNaoFiscal.AcionarGuilhotina(strValor)
            //porta.Fechar();
            return cTexto;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
            return "";
        }
    }
}

