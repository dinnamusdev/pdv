/*
 * frmSelecionarLojaAtual.java
 *
 * Created on 1 de Outubro de 2008, 12:00
 */

package dinnamus.ui.infraestrutura;

import br.PesquisarDiretorios.PesquisarDiretorios;
import br.String.ManipulacaoString;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import MetodosDeNegocio.Entidades.Caixas;
import MetodosDeNegocio.Entidades.ConfiguracaoEstacao;
import MetodosDeNegocio.RepositorioLocal.DAO_RepositorioLocal_Inicializar;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import br.infraestrurura.inicializacao.VerificarArquivoCFG;
import br.TratamentoNulo.TratamentoNulos;
import br.com.ecf.ECFSuportados;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.servidor.Dao_Jdbc_1;
import br.com.ui.BloquearTela;
import br.com.ui.ItemLista;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.valor.formatar.FormatarNumero;
import br.visualizararquivo.VisualizarArquivo;
import MetodosDeNegocio.Sincronismo.SincronizarMovimento;
import MetodosDeNegocio.Venda.GerenciarCaixa;
import MetodosDeNegocio.ecf.ECFAtual;
import MetodosDeNegocio.Venda.pdvgerenciar;
import UI.Seguranca.ValidarAcessoAoProcesso;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.net.UnknownHostException;
//import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author  desenvolvedor
 */
public class frmSelecionarLojaAtual extends javax.swing.JDialog {
    
    /** Creates new form frmSelecionarLojaAtual */

    private ResultSet rsDadosPDV=null;
    private java.awt.Frame frmPai;
    //private ECFDinnamuS ecfdinnamus=null;
    private boolean UI_OK =false;
    public frmSelecionarLojaAtual(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        frmPai=parent;
        initComponents();
        UI_OK= InicializarUI();
        
    }
    public boolean InicializarUI()
    {
        
        boolean bRetorno=true;
        //ecfdinnamus = new ECFDinnamuS();
        btAtualizarDadosPDV.setVisible(false);
        InicializarDbgAliquotas();
        InicializarDbgForma();
        txtNumeroPortaCom.setVisible(false);
        if(InicializarUI_ComboLoja())
        {
            if(VerificarArquivoCFG.getHmServidores().get("Servidor0").get("TipoInstalacao").equalsIgnoreCase("PDV"))
            {
                rsDadosPDV = pdvgerenciar.DadosPdv();
                PainelPDVStatus.setEnabled(true);
                if(rsDadosPDV!=null)
                {
                    /*
                    Integer nCodigoUsuario = ValidarAcessoAoProcesso.Verificar(null, UsuarioSistema.getIdUsuarioLogado(), "PDV_CFGPDV", Sistema.getLojaAtual(), true, "Configurar PDV");
                    if (nCodigoUsuario == 0) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Usuário não possui permissão de acesso a este módulo", "Acesso não autorizado");
                        return false;
                    }*/
                    
                    lblStatus.setText("CONFIGURADO");
                    PainelPDVStatus.setEnabled(false);
                    btMontarEDesmontar.setText("Desmontar PDV");
                    cbLojas_AtualizarCbDependentes(null);
                    btCarregarAliquotas.setEnabled(true);
                    btCarregarFormas.setEnabled(true);
                    btAtualizarDadosPDV.setVisible(true);
                }
                else{                  
                    DAO_RepositorioLocal_Inicializar.AtualizarOnline("codigo", "caixas", true, null, null);
                    lblStatus.setText("PENDENTE CFG");
                    btCarregarAliquotas.setEnabled(false);
                    btCarregarFormas.setEnabled(false);
                    BloquearComponentesECF();
                }


                if(!InicializarCampos())
                {
                   JOptionPane.showMessageDialog(this, "Não foi possível inicializar a UI.");
                   this.dispose();
                }
            }
            else
            {
                //jTabbedPane1.setEnabledAt(0, false);
                PainelPDVStatus.setEnabled(false);

                for(Component cp : PainelPDVStatus.getComponents())
                {
                    cp.setEnabled(false);
                }
                btMontarEDesmontar.setText("Gravar");
            }
            this.cbLojas.requestFocus();
        }
        setLocationRelativeTo(null);

        return bRetorno;
    }

    private boolean InicializarCampos()
    {
        boolean bRet=true;
        
        try {
            if(rsDadosPDV!=null) {

                  
                  MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbLojas, rsDadosPDV.getObject("lojaatual"));                  
    
                  
                  ItemLista i = (ItemLista) cbLojas.getSelectedItem();

                  txtCodigoPDV.setText( String.valueOf(rsDadosPDV.getInt("CodigoPdv")));

                  cbLojas_AtualizarCbDependentes(i);
                  
                  //MetodosUI_Auxiliares.SetarOpcaoCombo(cbCaixas,  0);

                  //MetodosUI_Auxiliares.SetarOpcaoCombo(cbOperadores, rsDadosPDV.getObject("OperadorVinculadoAoPdv"));

                  //cbModoTrabalho.setSelectedIndex(Integer.parseInt(rsDadosPDV.getObject("ModoDeConexao").toString()));

            }

        } catch (SQLException ex) {
            Logger.getLogger(frmSelecionarLojaAtual.class.getName()).log(Level.SEVERE, null, ex);
            bRet=false;
        }
        return bRet;

    }
    public boolean InicializarUI_ComboLoja()
    {
        try {
        
            MetodosUI_Auxiliares_1.PreencherCombo(cbImpressoraNFiscal, DAO_RepositorioLocal.GerarResultSet("select id,ModeloImp from off_modeloimpressoranaofiscal order by id", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), "modeloimp", "modeloimp",true);
            
            if(MetodosUI_Auxiliares_1.PreencherCombo(cbLojas, Sistema.getDadosLoja(0), "Nome", "Codigo",true)){
                ItemLista i = (ItemLista) cbLojas.getSelectedItem();
                cbLojas_AtualizarCbDependentes(i);          
            }
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
            return false;
        }
     
        
        

    
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        opPorta = new javax.swing.ButtonGroup();
        txtPorta = new javax.swing.JTextField();
        btLocalArquivos = new javax.swing.JButton();
        TabECF = new javax.swing.JTabbedPane();
        PainelDefinirECF = new javax.swing.JPanel();
        cbImpressorasFiscal = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        txtLocalArquivosDLL = new javax.swing.JTextField();
        PainelPorta = new javax.swing.JPanel();
        opUsb = new javax.swing.JRadioButton();
        opSerial = new javax.swing.JRadioButton();
        txtNumeroPortaCom = new javax.swing.JSpinner();
        btLerArquivoCFG = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        chkECF_Ativo = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        PainelValidarECF = new javax.swing.JPanel();
        btValidarECF = new javax.swing.JButton();
        chkECFValido = new javax.swing.JCheckBox();
        lblCNPJ = new javax.swing.JLabel();
        lblMFD = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        btTrocarECF = new javax.swing.JButton();
        PainelConfigurarECF = new javax.swing.JPanel();
        PainelDBG_FormaPgto = new javax.swing.JPanel();
        dbgFormasPagto = new br.com.ui.JTableDinnamuS();
        btCarregarFormas = new javax.swing.JButton();
        btCadastrarPagto = new javax.swing.JButton();
        btApagarForma = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        PainelDBG_Aliquota = new javax.swing.JPanel();
        dbgAliquotas = new br.com.ui.JTableDinnamuS();
        btCarregarAliquotas = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btCarregarAliquotas1 = new javax.swing.JButton();
        btApagarAliquota = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtCodSangria = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCodSuprimentoFiscal = new javax.swing.JTextField();
        PainelPrincipalCFG = new javax.swing.JPanel();
        PainelFundo = new javax.swing.JPanel();
        PainelPrincipal = new javax.swing.JPanel();
        PainelLoja = new javax.swing.JPanel();
        cbLojas = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        PainelBotoes = new javax.swing.JPanel();
        btMontarEDesmontar = new javax.swing.JButton();
        btAtualizarDadosPDV = new javax.swing.JButton();
        PainelPrincipalDadosPDV = new javax.swing.JPanel();
        PainelPDVStatus = new javax.swing.JPanel();
        cbCaixas = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoPDV = new javax.swing.JTextField();
        lblStatus = new javax.swing.JTextField();
        PainelPDV_Digitacao = new javax.swing.JPanel();
        PainelCaixa = new javax.swing.JPanel();
        cbImpressoraNFiscal = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtCodCaixa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNomeCaixa = new javax.swing.JTextField();
        btCFGNFiscal = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtViasCompVendaCrediario = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        chkGaveta = new javax.swing.JCheckBox();
        chkComprovantesIreprt = new javax.swing.JCheckBox();
        chkConectividade = new javax.swing.JCheckBox();
        PainelTransmissao = new javax.swing.JPanel();
        lblTransm = new javax.swing.JLabel();
        chkEnviarMovimento = new javax.swing.JCheckBox();
        chkReceberCadastro = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        cbLocalEstoque = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        PainelTEF = new javax.swing.JPanel();
        chkTEFAtivado = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        txtPastaTEF_Resp = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtPastaTEF_Req = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btLocalizarResp = new javax.swing.JButton();
        btLocalizarReq = new javax.swing.JButton();
        txtViasCompTEF = new javax.swing.JSpinner();
        chkSITEF = new javax.swing.JCheckBox();
        PainelTopo = new javax.swing.JPanel();
        LBLTITULO = new javax.swing.JLabel();
        btFechar2 = new javax.swing.JButton();

        btLocalArquivos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Folder.png"))); // NOI18N
        btLocalArquivos.setText("PASTA DLL");
        btLocalArquivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLocalArquivosActionPerformed(evt);
            }
        });

        TabECF.setBackground(new java.awt.Color(255, 255, 255));
        TabECF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        PainelDefinirECF.setOpaque(false);
        PainelDefinirECF.setLayout(new java.awt.GridBagLayout());

        cbImpressorasFiscal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bematech MP20 FII", "SWEDA MFD STxxxx", "EPSON TM-T81 FBIII", "Daruma Framework" }));
        cbImpressorasFiscal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbImpressorasFiscalItemStateChanged(evt);
            }
        });
        cbImpressorasFiscal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbImpressorasFiscalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        PainelDefinirECF.add(cbImpressorasFiscal, gridBagConstraints);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("PORTA DE COMUNICAÇÃO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelDefinirECF.add(jLabel22, gridBagConstraints);

        txtLocalArquivosDLL.setEditable(false);
        txtLocalArquivosDLL.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        PainelDefinirECF.add(txtLocalArquivosDLL, gridBagConstraints);

        PainelPorta.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        PainelPorta.setOpaque(false);
        PainelPorta.setLayout(new java.awt.GridBagLayout());

        opPorta.add(opUsb);
        opUsb.setText("USB");
        opUsb.setOpaque(false);
        opUsb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                opUsbItemStateChanged(evt);
            }
        });
        opUsb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opUsbActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        PainelPorta.add(opUsb, gridBagConstraints);

        opPorta.add(opSerial);
        opSerial.setText("COM");
        opSerial.setOpaque(false);
        opSerial.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                opSerialItemStateChanged(evt);
            }
        });
        opSerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opSerialActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        PainelPorta.add(opSerial, gridBagConstraints);

        txtNumeroPortaCom.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));
        txtNumeroPortaCom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtNumeroPortaComStateChanged(evt);
            }
        });
        txtNumeroPortaCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumeroPortaComFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        PainelPorta.add(txtNumeroPortaCom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelDefinirECF.add(PainelPorta, gridBagConstraints);

        btLerArquivoCFG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/View.png"))); // NOI18N
        btLerArquivoCFG.setText("LER CFG");
        btLerArquivoCFG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLerArquivoCFGActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelDefinirECF.add(btLerArquivoCFG, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("MARCA DO ECF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelDefinirECF.add(jLabel23, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.1;
        PainelDefinirECF.add(jLabel24, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("STATUS ECF  ");
        jLabel11.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jLabel11, gridBagConstraints);

        chkECF_Ativo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chkECF_Ativo.setText("ECF Ativado");
        chkECF_Ativo.setEnabled(false);
        chkECF_Ativo.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(chkECF_Ativo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        PainelDefinirECF.add(jPanel1, gridBagConstraints);

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Folder.png"))); // NOI18N
        jLabel25.setText("PASTA DLL ECF");
        jLabel25.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelDefinirECF.add(jLabel25, gridBagConstraints);

        TabECF.addTab("1 - Definir", PainelDefinirECF);

        PainelValidarECF.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Carregadas do ECF", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N
        PainelValidarECF.setOpaque(false);
        PainelValidarECF.setLayout(new java.awt.GridBagLayout());

        btValidarECF.setText("Validar ECF");
        btValidarECF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btValidarECFActionPerformed(evt);
            }
        });
        PainelValidarECF.add(btValidarECF, new java.awt.GridBagConstraints());

        chkECFValido.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        chkECFValido.setText("ECF NÃO VALIDADO");
        chkECFValido.setEnabled(false);
        chkECFValido.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        PainelValidarECF.add(chkECFValido, gridBagConstraints);

        lblCNPJ.setBorder(javax.swing.BorderFactory.createTitledBorder("CNPJ ECF"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelValidarECF.add(lblCNPJ, gridBagConstraints);

        lblMFD.setBorder(javax.swing.BorderFactory.createTitledBorder("Num.Serie MFD"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelValidarECF.add(lblMFD, gridBagConstraints);

        lblMarca.setBorder(javax.swing.BorderFactory.createTitledBorder("Marca"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelValidarECF.add(lblMarca, gridBagConstraints);

        lblModelo.setToolTipText("");
        lblModelo.setBorder(javax.swing.BorderFactory.createTitledBorder("Modelo"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelValidarECF.add(lblModelo, gridBagConstraints);

        lblTipo.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelValidarECF.add(lblTipo, gridBagConstraints);

        btTrocarECF.setText("Trocar ECF");
        btTrocarECF.setEnabled(false);
        btTrocarECF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTrocarECFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        PainelValidarECF.add(btTrocarECF, gridBagConstraints);

        TabECF.addTab("2 - Validar", PainelValidarECF);

        PainelConfigurarECF.setBackground(new java.awt.Color(255, 255, 255));
        PainelConfigurarECF.setLayout(new java.awt.GridBagLayout());

        PainelDBG_FormaPgto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelDBG_FormaPgto.setOpaque(false);
        PainelDBG_FormaPgto.setLayout(new java.awt.GridBagLayout());

        dbgFormasPagto.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelDBG_FormaPgto.add(dbgFormasPagto, gridBagConstraints);

        btCarregarFormas.setText("Carregar");
        btCarregarFormas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCarregarFormasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDBG_FormaPgto.add(btCarregarFormas, gridBagConstraints);

        btCadastrarPagto.setText("Cadastrar");
        btCadastrarPagto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarPagtoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDBG_FormaPgto.add(btCadastrarPagto, gridBagConstraints);

        btApagarForma.setText("Apagar");
        btApagarForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btApagarFormaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDBG_FormaPgto.add(btApagarForma, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MEIOS DE PAGAMENTO - ECF");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelDBG_FormaPgto.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 6, 5);
        PainelConfigurarECF.add(PainelDBG_FormaPgto, gridBagConstraints);

        PainelDBG_Aliquota.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelDBG_Aliquota.setOpaque(false);
        PainelDBG_Aliquota.setLayout(new java.awt.GridBagLayout());

        dbgAliquotas.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelDBG_Aliquota.add(dbgAliquotas, gridBagConstraints);

        btCarregarAliquotas.setText("Carregar");
        btCarregarAliquotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCarregarAliquotasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDBG_Aliquota.add(btCarregarAliquotas, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("ALIQUOTAS - ECF");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelDBG_Aliquota.add(jLabel4, gridBagConstraints);

        btCarregarAliquotas1.setText("Cadastrar");
        btCarregarAliquotas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCarregarAliquotas1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDBG_Aliquota.add(btCarregarAliquotas1, gridBagConstraints);

        btApagarAliquota.setText("Apagar");
        btApagarAliquota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btApagarAliquotaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelDBG_Aliquota.add(btApagarAliquota, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(6, 7, 6, 7);
        PainelConfigurarECF.add(PainelDBG_Aliquota, gridBagConstraints);

        jLabel13.setText("Cod Sangria ECF");
        PainelConfigurarECF.add(jLabel13, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelConfigurarECF.add(txtCodSangria, gridBagConstraints);

        jLabel12.setText("Cod Suprimento ECF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        PainelConfigurarECF.add(jLabel12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelConfigurarECF.add(txtCodSuprimentoFiscal, gridBagConstraints);

        TabECF.addTab("3 - Configurar", PainelConfigurarECF);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Configurar PDV");
        setMinimumSize(new java.awt.Dimension(600, 440));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(600, 440));
        setResizable(false);
        setSize(new java.awt.Dimension(600, 440));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipalCFG.setMaximumSize(new java.awt.Dimension(610, 700));
        PainelPrincipalCFG.setMinimumSize(new java.awt.Dimension(610, 700));
        PainelPrincipalCFG.setPreferredSize(new java.awt.Dimension(610, 700));
        PainelPrincipalCFG.setRequestFocusEnabled(false);
        PainelPrincipalCFG.setLayout(new java.awt.GridBagLayout());

        PainelFundo.setBackground(new java.awt.Color(255, 255, 204));
        PainelFundo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelFundo.setPreferredSize(new java.awt.Dimension(574, 472));
        PainelFundo.setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelLoja.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelLoja.setOpaque(false);
        PainelLoja.setLayout(new java.awt.GridBagLayout());

        cbLojas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLojasActionPerformed(evt);
            }
        });
        cbLojas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbLojasFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelLoja.add(cbLojas, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Loja Atual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 3);
        PainelLoja.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelLoja, gridBagConstraints);

        PainelBotoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelBotoes.setOpaque(false);
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        btMontarEDesmontar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btMontarEDesmontar.setMnemonic('G');
        btMontarEDesmontar.setText("Montar PDV");
        btMontarEDesmontar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMontarEDesmontarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 31;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 12, 13, 0);
        PainelBotoes.add(btMontarEDesmontar, gridBagConstraints);

        btAtualizarDadosPDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAtualizarDadosPDV.setMnemonic('G');
        btAtualizarDadosPDV.setText("Atualizar Dados PDV");
        btAtualizarDadosPDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizarDadosPDVActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 73, 13, 0);
        PainelBotoes.add(btAtualizarDadosPDV, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelBotoes, gridBagConstraints);

        PainelPrincipalDadosPDV.setOpaque(false);
        PainelPrincipalDadosPDV.setLayout(new java.awt.GridBagLayout());

        PainelPDVStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelPDVStatus.setOpaque(false);
        PainelPDVStatus.setLayout(new java.awt.GridBagLayout());

        cbCaixas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCaixasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPDVStatus.add(cbCaixas, gridBagConstraints);

        jLabel2.setText("Caixa Vinculado ao PDV");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelPDVStatus.add(jLabel2, gridBagConstraints);

        jLabel5.setText("Status");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelPDVStatus.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Codigo PDV");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelPDVStatus.add(jLabel6, gridBagConstraints);

        txtCodigoPDV.setEditable(false);
        txtCodigoPDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPDVStatus.add(txtCodigoPDV, gridBagConstraints);

        lblStatus.setEditable(false);
        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 115;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPDVStatus.add(lblStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPrincipalDadosPDV.add(PainelPDVStatus, gridBagConstraints);

        PainelPDV_Digitacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelPDV_Digitacao.setOpaque(false);
        PainelPDV_Digitacao.setLayout(new java.awt.GridBagLayout());

        PainelCaixa.setOpaque(false);
        PainelCaixa.setLayout(new java.awt.GridBagLayout());

        cbImpressoraNFiscal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bematech", "Daruma", "Sweda", "DataRegis", "Bematech MP4200-TH" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelCaixa.add(cbImpressoraNFiscal, gridBagConstraints);

        jLabel9.setText("Impressora Comprov.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        PainelCaixa.add(jLabel9, gridBagConstraints);

        txtCodCaixa.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelCaixa.add(txtCodCaixa, gridBagConstraints);

        jLabel7.setText("Codigo Caixa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        PainelCaixa.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Nome Caixa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        PainelCaixa.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelCaixa.add(txtNomeCaixa, gridBagConstraints);

        btCFGNFiscal.setText("Cfg.");
        btCFGNFiscal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCFGNFiscalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        PainelCaixa.add(btCFGNFiscal, gridBagConstraints);

        jLabel26.setText(" Vias Venda Crediário");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelCaixa.add(jLabel26, gridBagConstraints);

        txtViasCompVendaCrediario.setModel(new javax.swing.SpinnerNumberModel(1, 1, 2, 1));
        txtViasCompVendaCrediario.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtViasCompVendaCrediarioStateChanged(evt);
            }
        });
        txtViasCompVendaCrediario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtViasCompVendaCrediarioFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelCaixa.add(txtViasCompVendaCrediario, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        chkGaveta.setText(" Gaveta Dinheiro");
        chkGaveta.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(chkGaveta, gridBagConstraints);

        chkComprovantesIreprt.setText("Usar comprovantes iReport");
        chkComprovantesIreprt.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 14);
        jPanel2.add(chkComprovantesIreprt, gridBagConstraints);

        chkConectividade.setText("Conectividade");
        chkConectividade.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(chkConectividade, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelCaixa.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPDV_Digitacao.add(PainelCaixa, gridBagConstraints);

        PainelTransmissao.setBackground(new java.awt.Color(255, 255, 255));
        PainelTransmissao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PainelTransmissao.setLayout(new java.awt.GridBagLayout());

        lblTransm.setBackground(new java.awt.Color(255, 255, 255));
        lblTransm.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTransm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTransm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/dinnamus.png"))); // NOI18N
        lblTransm.setText("RETAGUARDA");
        lblTransm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblTransm.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        PainelTransmissao.add(lblTransm, gridBagConstraints);

        chkEnviarMovimento.setText("Enviar Movimento");
        chkEnviarMovimento.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        PainelTransmissao.add(chkEnviarMovimento, gridBagConstraints);

        chkReceberCadastro.setText("Receber Cadastros");
        chkReceberCadastro.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        PainelTransmissao.add(chkReceberCadastro, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weighty = 0.2;
        PainelTransmissao.add(jLabel10, gridBagConstraints);

        cbLocalEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLocalEstoqueActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        PainelTransmissao.add(cbLocalEstoque, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Local Baixar Estoque");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        PainelTransmissao.add(jLabel14, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weighty = 0.1;
        PainelTransmissao.add(jLabel21, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 6, 7);
        PainelPDV_Digitacao.add(PainelTransmissao, gridBagConstraints);

        PainelTEF.setBackground(new java.awt.Color(255, 255, 255));
        PainelTEF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PainelTEF.setLayout(new java.awt.GridBagLayout());

        chkTEFAtivado.setText("TEF Ativado");
        chkTEFAtivado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chkTEFAtivado.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelTEF.add(chkTEFAtivado, gridBagConstraints);

        jLabel15.setText(" Vias Comprovante TEF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelTEF.add(jLabel15, gridBagConstraints);

        txtPastaTEF_Resp.setEditable(false);
        txtPastaTEF_Resp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelTEF.add(txtPastaTEF_Resp, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.2;
        PainelTEF.add(jLabel16, gridBagConstraints);

        txtPastaTEF_Req.setEditable(false);
        txtPastaTEF_Req.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelTEF.add(txtPastaTEF_Req, gridBagConstraints);

        jLabel17.setText(" Pasta TEF Req");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelTEF.add(jLabel17, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/TEFONLINE.png"))); // NOI18N
        jLabel18.setText("CONFIGURAÇÕES DO TEF");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelTEF.add(jLabel18, gridBagConstraints);

        jLabel20.setText(" Pasta TEF Resp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelTEF.add(jLabel20, gridBagConstraints);

        btLocalizarResp.setText("...");
        btLocalizarResp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLocalizarRespActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        PainelTEF.add(btLocalizarResp, gridBagConstraints);

        btLocalizarReq.setText("...");
        btLocalizarReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLocalizarReqActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        PainelTEF.add(btLocalizarReq, gridBagConstraints);

        txtViasCompTEF.setModel(new javax.swing.SpinnerNumberModel(2, 1, 2, 1));
        txtViasCompTEF.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtViasCompTEFStateChanged(evt);
            }
        });
        txtViasCompTEF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtViasCompTEFFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        PainelTEF.add(txtViasCompTEF, gridBagConstraints);

        chkSITEF.setText("SITEF");
        chkSITEF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chkSITEF.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        PainelTEF.add(chkSITEF, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 9, 7, 8);
        PainelPDV_Digitacao.add(PainelTEF, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelPrincipalDadosPDV.add(PainelPDV_Digitacao, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelPrincipal.add(PainelPrincipalDadosPDV, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 7, 8);
        PainelFundo.add(PainelPrincipal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        PainelPrincipalCFG.add(PainelFundo, gridBagConstraints);

        PainelTopo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTopo.setLayout(new java.awt.GridBagLayout());

        LBLTITULO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LBLTITULO.setForeground(new java.awt.Color(255, 255, 255));
        LBLTITULO.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LBLTITULO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/gear.png"))); // NOI18N
        LBLTITULO.setText(" CONFIGURAÇÕES DO PDV");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelTopo.add(LBLTITULO, gridBagConstraints);

        btFechar2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btFechar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btFechar2.setMnemonic('x');
        btFechar2.setToolTipText("");
        btFechar2.setBorderPainted(false);
        btFechar2.setOpaque(false);
        btFechar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFechar2ActionPerformed(evt);
            }
        });
        btFechar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btFechar2KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelTopo.add(btFechar2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipalCFG.add(PainelTopo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelPrincipalCFG, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
     private boolean VerificarCaixaEmOperacao(String NomeHostAtual,   String NomeHostPDV){
        boolean Ret = false;
        try {
       
            ResultSet rs = Dao_Jdbc_1.getConexao().GerarResultSet("sp_who2");
            String Host = "";
            String APP ="";
            String Banco ="";
            while(rs.next()){
                  Host = TratamentoNulos.getTratarString().Tratar(rs.getString("hostname"),"").trim();
                  APP =TratamentoNulos.getTratarString().Tratar(rs.getString("programname"),"").trim();
                  Banco = TratamentoNulos.getTratarString().Tratar(rs.getString("dbname"),"").trim();
                  String BancoAtual = Dao_Jdbc_1.getConexao().getCNX().getCatalog();
                  if( !NomeHostAtual.equalsIgnoreCase(NomeHostPDV)  && Host.equalsIgnoreCase(NomeHostAtual) && APP.equalsIgnoreCase("jtds") && Banco.equalsIgnoreCase(BancoAtual)){
                     Ret=true; 
                     break;
                  }
            }                
         
            rs.close();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean ValidarDados()
    {
        boolean bRet=false;
        if(btMontarEDesmontar.getText().equalsIgnoreCase("Montar PDV")){        
            
            if(cbLojas.getSelectedIndex()<0)
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples((JFrame) frmPai, "Informe uma loja", "Config. PDV", "AVISO");
            else
            {
                if(txtNomeCaixa.getText().trim().length()==0){
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("NÃO FOI ESPECIFICADO UM NOME PARA O CAIXA", "MONTAR PDV");
                   return false;
                }
                
                ItemLista i =(ItemLista)  cbLocalEstoque.getSelectedItem();
                if(i==null){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("NÃO FOI ESPECIFICADO O LOCAL DO ESTOQUE", "MONTAR PDV");
                    return false;
                }
                
                if(VerificarArquivoCFG.getHmServidores().get("Servidor0").get("TipoInstalacao").equalsIgnoreCase("PDV"))
                {
                    if(cbCaixas.getSelectedIndex()<0)
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples((JFrame) frmPai, "Informe um caixa", "Config. PDV", "AVISO");                
                    else{
                        // Verificar se a estacao e as mesma vinculada ao pdv anterior
                        int CodigoCaixa = txtCodCaixa.getText().length()==0 ? 0 : new Integer(txtCodCaixa.getText());
                        if(CodigoCaixa>0){
                            String HostVinculadoAoPDV = pdvgerenciar.HostVinculadaAoPDV(CodigoCaixa);
                            String NomeHostAtual = "";
                            try {
                                 NomeHostAtual= java.net.InetAddress.getLocalHost().getHostName();   
                            } catch (Exception e) {
                                LogDinnamus.Log(e, false);
                                NomeHostAtual="127.0.0.1";
                            }

                            if(VerificarCaixaEmOperacao(HostVinculadoAoPDV,NomeHostAtual)){                              
                               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("FOI IDENTIFICADO QUE OUTRO COMPUTADOR [ " + HostVinculadoAoPDV +  " ] ESTÁ OPERANDO O CAIXA SELECIONADO\n\nOPERAÇÃO CANCELADA", "MONTAR PDV");                                    
                               return false;
                            }                            
                            if(!NomeHostAtual.equalsIgnoreCase(HostVinculadoAoPDV)){
                               int RetPerg = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null,"FOI IDENTIFICADO QUE OUTRO COMPUTADOR [ " + HostVinculadoAoPDV + " ] \nESTA CADASTRADO PARA OPERAR O CAIXA SELECIONADO.\n\nDESEJA REALMENTE MONTAR PDV USANDO ESTE CAIXA", "MONTAR PDV");
                               if(RetPerg!=MetodosUI_Auxiliares_1.Sim()){
                                   return false;
                               }
                            }
                            bRet= true;
                        }else{
                            bRet= true;
                        }
                    }
                }
                else
                    bRet=true;
            }
        }else{
            try {
                ResultSet  rs = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(),0,0,pdvgerenciar.CodigoPDV(),false);
                if(rs.next()){
                    String Status =  TratamentoNulos.getTratarString().Tratar(rs.getString("status"),"F");
                    if(!Status.equalsIgnoreCase("F")){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O PDV NÃO PODE SER DESMONTADO COM O CAIXA ABERTO. FECHE O CAIXA PRIMEIRO", "CAIXA ABERTO");
                        bRet=false;
                    }else{
                        bRet=true;
                    }                    
                }    
            } catch (Exception e) {
                LogDinnamus.Log(e, true);
            }
            
        }
        return bRet;

    }
    
    private boolean Confirma(){
        try {
           if(btMontarEDesmontar.getText().equalsIgnoreCase("Montar PDV")){                       
                if (MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Confirma a montagem do PDV?", "Montagem do PDV")==JOptionPane.YES_OPTION)
                {
                   return true;     
                }
           }else{
              if (MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao( "Deseja desmontar o PDV?", "Desmontar PDV")==JOptionPane.YES_OPTION)
              {
                 return true; 
              }
            }                        
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        return false;
    
    }
    private void ProcessarMontagem(boolean ReutilizarPDV){ 
            
            ConfiguracaoEstacao pdv = new ConfiguracaoEstacao();
            pdv.setLojaAtual( Integer.parseInt(((ItemLista) cbLojas.getSelectedItem()).getIndice().toString()));
            if(VerificarArquivoCFG.getHmServidores().get("Servidor0").get("TipoInstalacao").equalsIgnoreCase("PDV"))
            {
                pdv.setCaixaVinculadoAoPDV(Integer.parseInt(((ItemLista) cbCaixas.getSelectedItem()).getIndice().toString()));
            }
            try {
                pdv.setHost(java.net.InetAddress.getLocalHost().getHostName());
            } catch (Exception ex) {
                LogDinnamus.Log(ex,false);
                pdv.setHost("127.0.0.1");
            }
            
            if(pdvgerenciar.MontarPdv(pdv,SetarCaixa(),ReutilizarPDV))
            {
                ResultSet RsDadosPDV = pdvgerenciar.DadosPdv();
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "PDV montado com sucesso!!", "Configuração do PDV");
                try {
                    if(RsDadosPDV!=null){
                        Integer nCodigo = RsDadosPDV.getInt("CaixaVinculadoAoPDV");
                        txtCodCaixa.setText(nCodigo.toString());
                    }    
                } catch (Exception e) {
                    LogDinnamus.Log(e);
                }
               
                this.dispose();
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "Não foi possível montar o PDV ", "Configuração do PDV");
            }
              
    }
    
    private void Desmotagem(){
            try {

                ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixas(getLojaAtual(),0,getCaixaAtual());
                if(rsDadosCaixa.next()){
                    String cStatus=TratamentoNulos.getTratarString().Tratar( rsDadosCaixa.getString("status"),"F");
                    if(cStatus.equalsIgnoreCase("A")){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O pdv está com um caixa aberto. Para desmontar o pdv é necessário encerrar o caixa", "Caixa Aberto", "AVISO");
                        return;
                    }
                }

            } catch (Exception e) {
                LogDinnamus.Log(e);
            }

            if(pdvgerenciar.DesMontarPdv(Integer.parseInt(txtCodigoPDV.getText())))
            {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "PDV desmontado com sucesso. O sistema será encerrado", "PDV Desmontado");                    
                System.exit(0);
            }else{
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "Não foi possível desmontar o PDV", "Desmontar PDV");                    
            } 
            
    }
    
    private void btMontarEDesmontarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMontarEDesmontarActionPerformed
        // TODO add your handling code here:
        
      if(!ValidarDados()){ return ;}
      
      if(!Confirma()) { return ; } 
      final BloquearTela  bloq = new BloquearTela();      
      bloq.Tela_Bloquear(this, 0.2f);
      MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelFundo, false);
      MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelTopo, false);
      
      new Thread("frmSelecionarLojaAtual_btMontarEDesmontarActionPerformed"){
            @Override
            public void run(){
                    if(btMontarEDesmontar.getText().equalsIgnoreCase("Montar PDV")){
                       boolean PDVNovo = false; 
                       if(txtCodCaixa.getText().length()>0){ 
                           int Ret = MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("DESEJA USAR O MESMO PDV ?", "MONTAR PDV");
                           PDVNovo =  (Ret == MetodosUI_Auxiliares_1.Sim() ? true : false);
                       }
                       ProcessarMontagem(PDVNovo);
                    }else{
                        Desmotagem();
                    }   
                    bloq.Tela_DesBloquear();
                    MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelFundo, true);
                    MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelTopo, true);
            }
            
      
      }.start();

    }//GEN-LAST:event_btMontarEDesmontarActionPerformed
    public Caixas SetarCaixa(){
        Caixas c=null;
        try {
            c = new Caixas();
            String nCodigoCaixa = txtCodCaixa.getText().trim();
            c.setCodigo(nCodigoCaixa.equalsIgnoreCase("") ? 0 : Integer.valueOf(nCodigoCaixa));
            c.setNome(txtNomeCaixa.getText());
            c.setImpressoraNaoFiscal(cbImpressoraNFiscal.getSelectedItem().toString());
            c.setImpressorafiscal(cbImpressorasFiscal.getSelectedItem().toString());
            c.setPorta(getPorta());
            c.setTotSuprimento(txtCodSuprimentoFiscal.getText());
            c.setTotSangria(txtCodSangria.getText());
            ItemLista i =(ItemLista)  cbLocalEstoque.getSelectedItem();
            c.setFilial( Integer.parseInt(i.getIndice().toString()));
            c.setAbilitarTEF(chkTEFAtivado.isSelected());
            c.setGaveta(chkGaveta.isSelected());
            Integer nCodigoLoja =  Integer.valueOf(((ItemLista) cbLojas.getSelectedItem()).getIndice().toString());
            c.setLoja(nCodigoLoja);
            c.setEcf_ativo(chkECF_Ativo.isSelected());
            c.setEnviar(chkEnviarMovimento.isSelected());
            c.setReceber(chkReceberCadastro.isSelected());
            c.setViascomptef(new Integer(txtViasCompTEF.getValue().toString()));
            c.setViascompvdacrediario(new Integer(txtViasCompVendaCrediario.getValue().toString()));
            c.setSitef(chkSITEF.isSelected());
            c.setPasta_tef_req(txtPastaTEF_Req.getText());
            c.setPasta_tef_resp(txtPastaTEF_Resp.getText());
            c.setEcf_marca(lblMarca.getText());
            c.setEcf_cnpj(lblCNPJ.getText());
            c.setEcf_modelo(lblModelo.getText());
            c.setEcf_tipo(lblTipo.getText());
            c.setEcf_serie_mfd(lblMFD.getText());
            c.setEcf_valido(chkECFValido.isSelected());
            c.setEcf_pasta_dll(txtLocalArquivosDLL.getText());
            c.setComprovanteIReport(chkComprovantesIreprt.isSelected());
            c.setConectividade(chkConectividade.isSelected());
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return c;
    }
    public String getPorta(){
        String Ret ="";
        try {
             
             if( opSerial.isSelected()){
               Ret ="COM" + txtNumeroPortaCom.getValue().toString();
            }else if(opUsb.isSelected()){
               Ret="USB";
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public void setPorta(String Porta){
        try {
            
             if(Porta.toUpperCase().trim().contains("COM")){
               String NumPorta =  Porta.substring(3);
               txtNumeroPortaCom.setValue(new Integer(NumPorta));
               opSerial.setSelected(true);
            }else if(Porta.toUpperCase().trim().contains("USB")){
               txtNumeroPortaCom.setValue(1);
               opUsb.setSelected(true);
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private boolean CarregarDadosCaixa_UI(int nCodigoLoja, int nCodigoCaixa){
        boolean bRet=false; 
        try {

            if(nCodigoCaixa>0){
                ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixas(nCodigoLoja,0, nCodigoCaixa,0,false,false);
                if(rsDadosCaixa.next()){
                    String Porta = TratamentoNulos.getTratarString().Tratar( rsDadosCaixa.getString("porta"),"");
                    txtCodCaixa.setText(String.valueOf(rsDadosCaixa.getInt("codigo")));
                    txtNomeCaixa.setText(rsDadosCaixa.getString("nome"));
                    MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbImpressoraNFiscal, TratamentoNulos.getTratarString().Tratar( rsDadosCaixa.getString("impressoranaofiscal"),""));
                    //cbImpressoraNFiscal.setSelectedItem( );
                    cbImpressorasFiscal.setSelectedItem(TratamentoNulos.getTratarString().Tratar( rsDadosCaixa.getString("impressorafiscal"),""));
                    txtPorta.setText(Porta);
                    txtCodSuprimentoFiscal.setText(TratamentoNulos.getTratarString().Tratar( rsDadosCaixa.getString("tot_suprimento"),""));
                    txtCodSangria.setText(TratamentoNulos.getTratarString().Tratar( rsDadosCaixa.getString("tot_sangria"),""));
                    MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbLocalEstoque,  rsDadosCaixa.getInt("filial"));
                    
                    chkConectividade.setSelected( rsDadosCaixa.getBoolean("conectividade"));
                    chkComprovantesIreprt.setSelected( rsDadosCaixa.getBoolean("comprovantesireport"));
                    chkGaveta.setSelected( rsDadosCaixa.getBoolean("gaveta"));
                    chkTEFAtivado.setSelected( rsDadosCaixa.getBoolean("abilitartef"));
                    
                    
                    chkEnviarMovimento.setSelected(rsDadosCaixa.getBoolean("enviar"));
                    chkReceberCadastro.setSelected(rsDadosCaixa.getBoolean("receber"));                    
                    chkSITEF.setSelected(rsDadosCaixa.getBoolean("sitef"));   
                    int viascomp = rsDadosCaixa.getInt("viascomptef");
                    if(viascomp==0){
                        viascomp=2;
                    }
                    
                    int viascompvdacrediario =  TratamentoNulos.getTratarInt().Tratar( rsDadosCaixa.getInt("viascompvdacrediario"),1);
                    if(viascompvdacrediario==0){
                        viascompvdacrediario=1;
                    }
                    txtViasCompTEF.setValue(viascomp);
                    txtViasCompVendaCrediario.setValue(viascompvdacrediario);
                    txtPastaTEF_Req.setText(rsDadosCaixa.getString("pasta_tef_req"));
                    txtPastaTEF_Resp.setText(rsDadosCaixa.getString("pasta_tef_resp"));
                    
                    setPorta(Porta);
                    if(!rsDadosCaixa.getBoolean("ecf_valido")){
                        chkECFValido.setText("ECF NÃO VALIDADO");                        
                        chkECF_Ativo.setEnabled(!rsDadosCaixa.getBoolean("ecf_valido"));                                                
                        chkECF_Ativo.setEnabled(false);
                        btValidarECF.setEnabled(true);
                        btTrocarECF.setEnabled(false);
                        cbImpressorasFiscal.setEnabled(true);
                    }else{
                        chkECFValido.setText("ECF VALIDADO COM SUCESSO");
                        chkECF_Ativo.setSelected(rsDadosCaixa.getBoolean("ecf_ativo"));                        
                        chkECF_Ativo.setEnabled(true);
                        btTrocarECF.setEnabled(true);
                        btValidarECF.setEnabled(false);
                        cbImpressorasFiscal.setEnabled(false);
                    }
                    chkECFValido.setSelected(rsDadosCaixa.getBoolean("ecf_valido"));

                    MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelConfigurarECF, chkECFValido.isSelected());
                    dbgAliquotas.getjTable().setEnabled(chkECFValido.isSelected());
                    dbgFormasPagto.getjTable().setEnabled(chkECFValido.isSelected());
                    lblCNPJ.setText(TratamentoNulos.getTratarString().Tratar(rsDadosCaixa.getString("ecf_cnpj"),""));
                    lblMFD.setText(TratamentoNulos.getTratarString().Tratar(rsDadosCaixa.getString("ecf_serie_mfd"),""));
                    lblMarca.setText(TratamentoNulos.getTratarString().Tratar(rsDadosCaixa.getString("ecf_marca"),""));
                    lblModelo.setText(TratamentoNulos.getTratarString().Tratar(rsDadosCaixa.getString("ecf_modelo"),""));
                    lblTipo.setText(TratamentoNulos.getTratarString().Tratar(rsDadosCaixa.getString("ecf_tipo"),""));                    
                    txtLocalArquivosDLL.setText(TratamentoNulos.getTratarString().Tratar(rsDadosCaixa.getString("ecf_pasta_dll"),""));                    
                }
            }else{
                txtCodCaixa.setText("");
                txtNomeCaixa.setText("");
                chkComprovantesIreprt.setSelected( false);
                cbImpressoraNFiscal.setSelectedItem( "");
                cbImpressorasFiscal.setSelectedItem("");
                txtPorta.setText("");
                txtCodSuprimentoFiscal.setText("");
                txtCodSangria.setText("");
                MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbLocalEstoque,  -1);
                chkGaveta.setSelected(false);
                chkTEFAtivado.setSelected(false);
                chkEnviarMovimento.setSelected(true);
                chkReceberCadastro.setSelected(true);    
                chkConectividade.setSelected(false); 
                chkSITEF.setSelected(false);
                txtViasCompTEF.setValue(2);
                txtViasCompVendaCrediario.setValue(1);
                txtPastaTEF_Req.setText("c:\\tef_dial\\req");
                txtPastaTEF_Resp.setText("c:\\tef_dial\\resp");
                txtLocalArquivosDLL.setText(ManipulacaoArquivo2.DiretorioDeTrabalho());
            }
            bRet=true;
        } catch (Exception e) {
            
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private JComboBox GerarComboLetra(){
        JComboBox cx = new JComboBox();
        try {
            cx.setModel(new DefaultComboBoxModel(new String[] {"A","B","C","D","E","F"}));
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return cx;
    }
    
    private boolean AtualizarDbgForma(Integer nCodigoCaixa){
        try {
            dbgFormasPagto.setRsDados(GerenciarCaixa.ListarFormasPagamentoCaixa(nCodigoCaixa));
            dbgFormasPagto.getjTable().getColumn(1).setCellEditor(new DefaultCellEditor(GerarComboLetra()));              
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean InicializarDbgForma(){
        boolean bRet=false;
        try {
              
              dbgFormasPagto.addClColunas("descricao","NOME".toUpperCase() , 200) ;
              dbgFormasPagto.addClColunas("codigoformpg","LETRA".toUpperCase() , 50,true,true, SwingConstants.CENTER) ;                            
              
              
              
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
            
    }
    private boolean AtualizarDbgAliquotas(Integer nCodigoCaixa){
        try 
        {
            dbgAliquotas.setRsDados(GerenciarCaixa.ListarAliquotas(nCodigoCaixa));
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean InicializarDbgAliquotas(){
        boolean bRet=false;
        try {
              dbgAliquotas.addClColunas("codigo", "CODIGO", 100, true);
              dbgAliquotas.addClColunas("icms", "ALIQUOTA", 100, true);
              
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    /*private boolean DefinirECF(){
        try {
            ecfdinnamus = new ECFDinnamuS();           
            ecfdinnamus.setTipoECF(cbImpressorasFiscal.getSelectedItem().toString(),txtPorta.getText());    
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }*/
    
    private boolean CarregarFormasPagamento(){
        try {
            Integer nCodigoLoja = Integer.valueOf(((ItemLista) cbLojas.getSelectedItem()).getIndice().toString());
            Integer nCodigoCaixa = Integer.valueOf(txtCodCaixa.getText().length()==0 ? "0" : txtCodCaixa.getText());               
            
            return CarregarFormasPagamento(nCodigoCaixa, nCodigoLoja, pdvgerenciar.CodigoPDV(),cbImpressorasFiscal.getSelectedItem().toString(),txtPorta.getText());
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        return false;
        
        
    }
    private boolean CarregarFormasPagamento(Integer nCodigoCaixa, Integer nCodigoLoja, Integer nCodigoPDV,String Modelo,String Porta){
        boolean bRet=false;
        try {            
            //ECFAtual.   
            if(!ValidarECF(true,true,true)){ return false;}
                
           String cFormas = ECFAtual.getECF().VerificarFormasPagamento();           
           String[] cRetorno = cFormas.split(",");
           if(cRetorno.length>0){
              
              bRet = GerenciarCaixa.IncluirFormasPagamentoCaixa(nCodigoCaixa, cRetorno, nCodigoLoja, nCodigoPDV,true);
              if(bRet){
                  DAO_RepositorioLocal.Commitar_Statment();
              }           

           }else{
               GerenciarCaixa.ExcluirTodasFormasPagamentoCaixa(nCodigoCaixa);
               bRet=true;
           }
           AtualizarDbgForma(nCodigoCaixa);

           
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
 
    }
    
    private boolean CarregarAliquotas(){
        try {
             Integer nCodigoLoja = Integer.valueOf(((ItemLista) cbLojas.getSelectedItem()).getIndice().toString());
             Integer nCodigoCaixa =  Integer.valueOf(txtCodCaixa.getText().length()==0 ? "0" : txtCodCaixa.getText());               
             return CarregarAliquotas(nCodigoCaixa, nCodigoLoja, pdvgerenciar.CodigoPDV());
        } catch (Exception e) {
            LogDinnamus.Log(e, true);         
        }
        return false;
    }
            
    private boolean CarregarAliquotas(Integer nCodigoCaixa, Integer nCodigoLoja, Integer nCodigoPDV){
        boolean bRet=false;
        try {
            if(!ValidarECF(true,true,true)){ return false;}      
            
            TreeMap<String,Float> cFormas = ECFAtual.getECF().AliquotasProgramadas();
           //String[] cRetorno = cFormas.split(",");
           if(cFormas.size()>0){
              bRet = GerenciarCaixa.IncluirAliquotas(nCodigoCaixa, cFormas, nCodigoLoja, nCodigoPDV,true);              
           }else{
              GerenciarCaixa.ExcluirAliquotas(nCodigoCaixa);
              bRet=true;
           }
           AtualizarDbgAliquotas(nCodigoCaixa);
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    private void cbLojas_AtualizarCbDependentes(ItemLista i) {
        
        //ItemLista i  = (ItemLista) cbLojas.getSelectedItem();
          try {
               int nCodigoLoja = Integer.valueOf(((ItemLista) cbLojas.getSelectedItem()).getIndice().toString());
               ResultSet rsDadosCaixa=null;
               String status = lblStatus.getText();
               if(!status.equalsIgnoreCase("") )
               {
                    if(status.equalsIgnoreCase("CONFIGURADO") ){
                         int nCodigoCaixa = pdvgerenciar.CaixaVinculado();
                         rsDadosCaixa = GerenciarCaixa.ListarCaixas(nCodigoLoja,0,0, pdvgerenciar.CodigoPDV(), false,false);
                         AtualizarDbgForma(nCodigoCaixa);
                         AtualizarDbgAliquotas(nCodigoCaixa);                         
                    }else{
                        ItemLista iNovoCaixa= new ItemLista();
                        iNovoCaixa.setDescricao("<<NOVO CAIXA>>");
                        iNovoCaixa.setIndice(0);           
                        cbCaixas.removeAllItems();
                        cbCaixas.addItem(iNovoCaixa);                       
                        rsDadosCaixa= GerenciarCaixa.ListarCaixas( nCodigoLoja,0,0,0,false,false);
                        AtualizarDbgForma(0);
                        AtualizarDbgAliquotas(0);
                    }
                    MetodosUI_Auxiliares_1.PreencherCombo(cbLocalEstoque, Sistema.getDadosFilialLoja(nCodigoLoja), "NomeFilial", "CodigoFilial",true);
                    cbLocalEstoque.setSelectedIndex(-1);

                    MetodosUI_Auxiliares_1.PreencherCombo(cbCaixas, rsDadosCaixa ,"Nome","Codigo" ,(lblStatus.getText().equalsIgnoreCase("CONFIGURADO") ? true : false ));
                    //cbCaixas.update(cbCaixas.getGraphics());
                    String Status = lblStatus.getText();
                    if(Status.equalsIgnoreCase("CONFIGURADO")){
                        cbCaixasActionPerformed(null);
                    }
               }
        } catch (Exception ex) {
                LogDinnamus.Log(ex);
        }
        
    }
    private void cbLojasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbLojasFocusLost
        // TODO add your handling code here:
        if(cbLojas.getItemCount()>0)
        {
            ItemLista i = (ItemLista) cbLojas.getSelectedItem();
            
            cbLojas_AtualizarCbDependentes(i);
        }
        else
        {
            cbCaixas.removeAllItems();
         //   cbOperadores.removeAllItems();
        }
    }//GEN-LAST:event_cbLojasFocusLost
    private Integer getLojaAtual(){
        Integer nCodigoLoja=0;
        try {
            nCodigoLoja =  Integer.valueOf(((ItemLista) cbLojas.getSelectedItem()).getIndice().toString());
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nCodigoLoja;

    }private Integer getCaixaAtual(){
        Integer nCodigoCaixa=0;
        try {
            nCodigoCaixa =  Integer.valueOf(((ItemLista) cbCaixas.getSelectedItem()).getIndice().toString());
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nCodigoCaixa;

    }
    private void cbCaixasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCaixasActionPerformed
        // TODO add your handling code here:
        if(cbCaixas.getSelectedIndex()>=0){
           ItemLista i = (ItemLista)cbCaixas.getSelectedItem();
           if(i!=null){
                Integer nCodigoCaixa =  new Integer(i.getIndice().toString());
                Integer nCodigoLoja =  getLojaAtual();
                CarregarDadosCaixa_UI(nCodigoLoja, nCodigoCaixa);
           }
        }
    }//GEN-LAST:event_cbCaixasActionPerformed

    private void btCarregarAliquotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCarregarAliquotasActionPerformed
        // TODO add your handling code here:
        // if(!txtCodCaixa.getText().equalsIgnoreCase("")){           
           if(CarregarAliquotas()){
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Aliquotas carregadas com sucesso", "Cadastro de Caixas", "INFO");
           }else{
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível carregar as Aliqoutas", "Cadastro de Caixas", "AVISO");
           }
       //}

    }//GEN-LAST:event_btCarregarAliquotasActionPerformed

    private void btCarregarFormasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCarregarFormasActionPerformed
        // TODO add your handling code here:
       //if(!txtCodCaixa.getText().equalsIgnoreCase("")){           
           if(CarregarFormasPagamento()){
              if(evt!=null){
                  MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Meios de pagto carregadas com sucesso", "Cadastro de Caixas", "INFO");
              }
           }else{
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível carregar os Meios de pagto", "Cadastro de Caixas", "AVISO");
           }
       //}
    }//GEN-LAST:event_btCarregarFormasActionPerformed
    private boolean AtualizarDadosPDV(int nCodigoPdv, Caixas  c){
        try {
            if(AtualizarDadosPDV_Acao(nCodigoPdv, c)) {
                DAO_RepositorioLocal.Commitar_Statment();
                return true;
            }else{
                DAO_RepositorioLocal.RollBack_Statment();
            }
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            
        }
        return false;
    }
    private boolean AtualizarDadosPDV_Acao(int nCodigoPdv, Caixas  c){
        boolean ret = false;
        try {
            ret = GerenciarCaixa.AlterarCaixa(nCodigoPdv, c,DAO_RepositorioLocal.getCnRepLocal());
        
            if(ret){
                if(Sistema.isOnline()){
                    Dao_Jdbc_1.getConexao().getCNX().setSavepoint("alterarcaixa");
                    ret =SincronizarMovimento.ProcessarEnvioTabelasDoCaixa(false,true);
                    if(ret){
                        Dao_Jdbc_1.getConexao().Commitar_Statment();
                    }else{
                        Dao_Jdbc_1.getConexao().RollBack_Statment();
                    }
                }                
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        
        }
        return ret;
    }
    private void btAtualizarDadosPDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizarDadosPDVActionPerformed
        // TODO add your handling code here:
        try {
            Caixas  c = SetarCaixa();
            if(AtualizarDadosPDV(pdvgerenciar.CodigoPDV(), c)){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "DADOS DO PDV ATUALIZADO COM SUCESSO", "ATUALIZAR DADOS PDV");
               //dispose();
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL ATUALIZAR OS DADOS DO PDV", "ATUALIZAR DADOS PDV"); 
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btAtualizarDadosPDVActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        try {
            
            if(!UI_OK){
                dispose();
            }else{
                 if( lblStatus.getText().equalsIgnoreCase("PENDENTE CFG")){
                      CarregarDadosCaixa_UI(Sistema.getLojaAtual(), 0);
                 }   
                
            }
            
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_formWindowOpened

    private void btFechar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar2ActionPerformed
        try {
            //--ValorFinalPosDesconto=  ValorVendaPDV - nDescontoLiberado - DescontoAtacadoLiberado + AcrescimoLiberado;  //FormatarNumero.FormatarNumero(txtValorFinal.getValue().toString());
          DAO_RepositorioLocal.RollBack_Statment();
            dispose();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }//GEN-LAST:event_btFechar2ActionPerformed

    private void btFechar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFechar2KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_btFechar2KeyPressed

    private void btCadastrarPagtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarPagtoActionPerformed
        // TODO add your handling code here:
        
        if(!ValidarECF(false,false,false)){ return ;}
        
        if(ECFAtual.getECF().ProgramarFormaPagto()){
            btCarregarFormasActionPerformed(null);
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "FORMA DA PAGAMENTO CADASTRADA COM SUCESSO", "CADASTRAR PAGTO NO ECF");            
        }
    }//GEN-LAST:event_btCadastrarPagtoActionPerformed

    private void btApagarFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btApagarFormaActionPerformed
        // TODO add your handling code here:
        try {
            int Linha = dbgFormasPagto.getjTable().getSelectedRow();
            if(Linha>=0){               
               Long IdForma =dbgFormasPagto.getTbDinnamuS().getValorCelulaLong("id",Linha);
               //String Forma =dbgFormasPagto.getTbDinnamuS().getValorCelulaString("descricao",Linha);
               if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "ESTA OPERAÇÃO NÃO REMOVERÁ DO ECF O MEIO DE PAGAMENTO\n\n CONFIRMA A EXCLUSÃO DO MEIO DE PAGTO", "EXCLUIR M.PAGTO DO SISTEMA")==MetodosUI_Auxiliares_1.Sim()){
                    if(GerenciarCaixa.ExcluirFormasPagamentoCaixa(IdForma)){
                        Integer nCodigoCaixa = Integer.valueOf(txtCodCaixa.getText().length()==0 ? "0" : txtCodCaixa.getText());
                        AtualizarDbgForma(nCodigoCaixa);
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "MEIO DE PAGTO EXCLUIDA COM SUCESSO", "EXCLUIR MEIO PAGTO");
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL EXCLUIR MEIO DE PAGTO", "EXCLUIR MEIO PAGTO");
                    }
               }
            }
                
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btApagarFormaActionPerformed
    public boolean ValidarECF(boolean IgnorarZPendente,boolean IgnorarZJaEmitida,boolean IgnorarDiaFechado){
        boolean Ret = false;
        try {
            String Modelo =cbImpressorasFiscal.getSelectedItem().toString();
            String Porta = txtPorta.getText();
            Ret =ECFAtual.isECFValido(false);
            if(!Ret){ 
               Ret = ECFAtual.ValidarECF(pdvgerenciar.CodigoPDV(), Modelo, Porta, IgnorarZPendente, false, IgnorarZJaEmitida, IgnorarDiaFechado,false);
            }                       
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private void btCarregarAliquotas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCarregarAliquotas1ActionPerformed
        // TODO add your handling code here:
        try {
           // DefinirECF();
            if(!ValidarECF(false,false,false)){ return ;}
            
            ECFAtual.getECF().ProgramarAliquota();
            
            CarregarAliquotas();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btCarregarAliquotas1ActionPerformed

    private void btApagarAliquotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btApagarAliquotaActionPerformed
        // TODO add your handling code here:
         try {
            int Linha = dbgAliquotas.getjTable().getSelectedRow();
            if(Linha>=0){               
               Long IdForma =dbgAliquotas.getTbDinnamuS().getValorCelulaLong("chaveprimaria",Linha);
               //String Forma =dbgFormasPagto.getTbDinnamuS().getValorCelulaString("descricao",Linha);
               if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "ESTA OPERAÇÃO NÃO REMOVERÁ DO ECF A ALIQUOTA \n\n CONFIRMA A EXCLUSÃO DA ALÍQUOTA DO SISTEMA", "EXCLUIR ALÍQUOTA DO SISTEMA")==MetodosUI_Auxiliares_1.Sim()){
                    if(GerenciarCaixa.ExcluirAliquota(IdForma)){
                        Integer nCodigoCaixa = Integer.valueOf(txtCodCaixa.getText().length()==0 ? "0" :txtCodCaixa.getText()  );
                        AtualizarDbgAliquotas(nCodigoCaixa);
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "ALÍQUOTA EXCLUIDA COM SUCESSO", "EXCLUIR ALÍQUOTA");
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL EXCLUIR ALÍQUOTA", "EXCLUIR ALÍQUOTA");
                    }
               }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btApagarAliquotaActionPerformed

    private void cbLojasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLojasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLojasActionPerformed

    private void cbLocalEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLocalEstoqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLocalEstoqueActionPerformed

    private void btLocalizarReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLocalizarReqActionPerformed
        // TODO add your handling code here:
        String Dir =PesquisarDiretorios.Pesquisar("LOCALIZAR PASTA REQ");
        if(Dir.length()>0){
           txtPastaTEF_Req.setText(Dir);
        }
    }//GEN-LAST:event_btLocalizarReqActionPerformed

    private void btLocalizarRespActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLocalizarRespActionPerformed
        // TODO add your handling code here:
        String Dir =PesquisarDiretorios.Pesquisar("LOCALIZAR PASTA RESP");
        if(Dir.length()>0){
           txtPastaTEF_Resp.setText(Dir);
        }
    }//GEN-LAST:event_btLocalizarRespActionPerformed

    private void txtViasCompTEFStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtViasCompTEFStateChanged
            // TODO add your handling code here:
        try {
          
            String Valor = txtViasCompTEF.getModel().getValue().toString();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesINFO(null, );
    }//GEN-LAST:event_txtViasCompTEFStateChanged

    private void txtViasCompTEFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtViasCompTEFFocusLost
        // TODO add your handling code here:
        try {
        
            if(FormatarNumero.FormatarNumero(txtViasCompTEF.getValue().toString())==Float.NaN){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("DIGITE UMA VALOR VALIDO [1-2]", "VIAS COMPROVANTE TEF");
               txtViasCompTEF.setValue("2");
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }//GEN-LAST:event_txtViasCompTEFFocusLost

    private void btValidarECFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btValidarECFActionPerformed
        // TODO add your handling code here:
       // Validar_ECF_PDV();
        Inicializar_ECF_PDV();
    }//GEN-LAST:event_btValidarECFActionPerformed

    private void btTrocarECFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTrocarECFActionPerformed
        // TODO add your handling code here:
        TrocarECF();
    }//GEN-LAST:event_btTrocarECFActionPerformed

    private void btLocalArquivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLocalArquivosActionPerformed
        // TODO add your handling code here:
        try {
            
            String ModeloECf = cbImpressorasFiscal.getSelectedItem().toString() ;
            String Retorno = PesquisarDiretorios.Pesquisar("LOCAL DAS DLL - ECF [ "+ ModeloECf +" ]");
            if(Retorno.length()>0){
                if(ValidarPasta(Retorno,ModeloECf)){
                    AtualizarArquivoCFG(ModeloECf);
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("ARQUIVO(S) DO ECF [ "+ ModeloECf +" ] OK"  , "ARQUIVOS OK");                    
                }else{
                    String DLLs = ManipulacaoString.TransformarListEmStringDelimitada(ECFSuportados.getDLLs_ECF(ModeloECf), false," ");
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O ECF [ "+ ModeloECf +" ] Precisa dos arquivos abaixo:\n\n" + DLLs +"\n\nVerifique a pasta escolhida" , "ARQUIVOS NÃO LOCALIZADOS");
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btLocalArquivosActionPerformed

    private void btLerArquivoCFGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLerArquivoCFGActionPerformed
        // TODO add your handling code here:
        CarregarArquivo();
    }//GEN-LAST:event_btLerArquivoCFGActionPerformed

    private void txtNumeroPortaComStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtNumeroPortaComStateChanged
        // TODO add your handling code here:
            if(opSerial.isSelected()){
                String Modelo = cbImpressorasFiscal.getSelectedItem().toString();
                txtPorta.setText("COM" + txtNumeroPortaCom.getValue().toString());
                AtualizarArquivoCFG(Modelo);
            }
    }//GEN-LAST:event_txtNumeroPortaComStateChanged

    private void txtNumeroPortaComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroPortaComFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroPortaComFocusLost

    private void opSerialItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_opSerialItemStateChanged
        // TODO add your handling code here:
        //if(opSerial.isSelected()){
           txtNumeroPortaCom.setVisible(opSerial.isSelected());
        //}else
        try {
            if(opSerial.isSelected()){
                String Modelo = cbImpressorasFiscal.getSelectedItem().toString();
                txtPorta.setText("COM" + txtNumeroPortaCom.getValue().toString());
                AtualizarArquivoCFG(Modelo);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_opSerialItemStateChanged
    private String getModeloECFAtaul(){
        String Modelo ="";
        try {
            
            Modelo = cbImpressorasFiscal.getSelectedItem().toString();
        
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Modelo;
    }
    private void opUsbItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_opUsbItemStateChanged
        // TODO add your handling code here:
        try {
            
            String Modelo = getModeloECFAtaul();
            if(opUsb.isSelected() && !Modelo.equalsIgnoreCase(ECFSuportados.getNomeECFEpson())){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("SOMENTE OS ECF [" + ECFSuportados.getNomeECFEpson() +"] SUPORTAM PORTA USB", "ECF NÃO SUPORTA USB");
                opSerial.setSelected(true);
                return;
            }
            txtNumeroPortaCom.setVisible(opSerial.isSelected());
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }//GEN-LAST:event_opUsbItemStateChanged

    private void opSerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opSerialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_opSerialActionPerformed

    private void opUsbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opUsbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_opUsbActionPerformed

    private void cbImpressorasFiscalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbImpressorasFiscalItemStateChanged
        // TODO add your handling code here:
        if(UI_OK){
            if(evt.getStateChange()==ItemEvent.SELECTED){
              //CarregarPastaDLL_ECF(cbImpressorasFiscal.getSelectedItem().toString());
            }
        }
        
    }//GEN-LAST:event_cbImpressorasFiscalItemStateChanged

    private void cbImpressorasFiscalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbImpressorasFiscalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbImpressorasFiscalActionPerformed

    private void btCFGNFiscalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCFGNFiscalActionPerformed
        // TODO add your handling code here:
        (new frmConfigImNaoFiscal(null, true)).setVisible(true);
             
    }//GEN-LAST:event_btCFGNFiscalActionPerformed

    private void txtViasCompVendaCrediarioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtViasCompVendaCrediarioStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtViasCompVendaCrediarioStateChanged

    private void txtViasCompVendaCrediarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtViasCompVendaCrediarioFocusLost
        // TODO add your handling code here:
        
         try {
        
            if(FormatarNumero.FormatarNumero(txtViasCompVendaCrediario.getValue().toString())==Float.NaN){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("DIGITE UMA VALOR VALIDO [1-2]", "VIAS COMPROVANTE VENDA CREDIÁRIO");
               txtViasCompVendaCrediario.setValue("1");
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_txtViasCompVendaCrediarioFocusLost
    private boolean CarregarArquivo(){
        boolean Ret = false;
        try {
            if(!ValidarDLL_ECF()) {return false;}
            
            String ModeloECf = cbImpressorasFiscal.getSelectedItem().toString() ;
            String Arquivo = ECFSuportados.getDLLs_ECF_ArquivoCFG(ModeloECf);
            String Pasta =  ECFSuportados.PastaDLL_ECF(ModeloECf);
            if(Arquivo.length()==0){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("A DLL não possui arquivo de configuração", "Carregar Arquivo DLL");
            }else{
                VisualizarArquivo.Visualizar(Pasta + ManipulacaoArquivo2.getSeparadorDiretorio() + Arquivo);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean ValidarPasta(String Pasta,String ModeloECf){
        boolean Ret = false;
        try {
            ArrayList<String> dlLs_ECF = ECFSuportados.getDLLs_ECF(ModeloECf);
            String Arquivo ="";
            for (int i = 0; i < dlLs_ECF.size(); i++) {
                Arquivo = Pasta +"\\" +dlLs_ECF.get(i);
                if(!ManipulacaoArquivo2.ArquivoExiste(Arquivo, false)){
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO("O ECF [ "+ ModeloECf +" ] NECESSITA DO ARQUIVO ABAIXO PARA FUNCIONAR\n\n" + Arquivo , "ARQUIVO NÃO LOCALIZADO");
                    return false;
                }
            }
            Ret=true;
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean AtualizarArquivoCFG(String ModeloECF){
        boolean Ret = false;
        try {
            
            String ArquivoCFG = ECFSuportados.getDLLs_ECF_ArquivoCFG(ModeloECF);
             if(ArquivoCFG.length()>0){
                String Porta = txtPorta.getText();
                if(Porta.length()>0){
                    if(!ECFSuportados.EditarCFG(ModeloECF, Porta)){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível atualizar o arquivo [ "+ ArquivoCFG +" ]", "Arquivo CFG Atualizado");
                    }else{
                        Ret=true;
                    }
                        
                }
             }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean ValidarDLL_ECF(){
        boolean Ret = false;
        try {
            String ModeloECF  = cbImpressorasFiscal.getSelectedItem().toString();
            
            if(ValidarPasta(txtLocalArquivosDLL.getText(),ModeloECF)){
                    Ret=true;
                }else{
                    String DLLs = ManipulacaoString.TransformarListEmStringDelimitada(ECFSuportados.getDLLs_ECF(ModeloECF), false," ");
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O ECF [ "+ ModeloECF +" ] Precisa dos arquivos abaixo:\n\n" + DLLs +"\n\nVerifique a pasta escolhida" , "ARQUIVOS NÃO LOCALIZADOS");
                }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private void CarregarPastaDLL_ECF(String ModeloECF){
        try {
                txtLocalArquivosDLL.setText(ECFSuportados.PastaDLL_ECF(ModeloECF));
             
              //if(Retorno.length()>0){
                if(ValidarDLL_ECF()){
                    AtualizarArquivoCFG(ModeloECF);                
                }
            //}
             
             //AtualizarArquivoCFG(ModeloECF);
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private void BloquearComponentesECF(){
        
        try {
            
              chkECF_Ativo.setSelected(false);
              chkECF_Ativo.setEnabled(false);
              chkECFValido.setText("ECF NÃO VALIDADO");
              chkECFValido.setSelected(false);
              cbImpressorasFiscal.setEnabled(true);
              btTrocarECF.setEnabled(false);
              btValidarECF.setEnabled(true);
              MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelConfigurarECF, false);
              dbgAliquotas.getjTable().setEnabled(false);
              dbgFormasPagto.getjTable().setEnabled(false);
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        //return Ret;    
    }
    private boolean TrocarECF(){
        boolean Ret = false;
        try {
            if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("ESTE PROCEDIMENTO BLOQUEARÁ A IMPRESSORA ATUAL\nLIBERANDO A TROCA POR OUTRO ECF\nSERÁ NECESSÁRIO A INICIALIZAÇÃO DO NOVO ECF\n\nCONFIRMA ESTA OPERAÇÃO ?", "TROCAR ECF")==MetodosUI_Auxiliares_1.Sim()){
              lblCNPJ.setText("");
              lblMFD.setText("");
              lblMarca.setText("");
              lblTipo.setText("");
              lblModelo.setText("");
              if(ECFAtual.getECF()!=null){
                  ECFAtual.getECF().FecharPorta();
              }
              BloquearComponentesECF();
              Caixas  c = SetarCaixa();              
              if(AtualizarDadosPDV(pdvgerenciar.CodigoPDV(), c)){
                  TabECF.setSelectedIndex(0);
                  MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("ECF DISPONÍVEL PARA TROCA", "PROCEDIMENTO OK");
              }else{
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("NÃO FOI POSSIVEL TROCAR O ECF", "PROCEDIMENTO NÃO REALIZADO"); 
                 this.dispose();
                  
              }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret; 
    }
    private void DesbloquearComponentesECF(){
        
        try {
            chkECF_Ativo.setEnabled(true);
            chkECF_Ativo.setSelected(true);
            chkECFValido.setSelected(true);
            btTrocarECF.setEnabled(true);    
            btValidarECF.setEnabled(false);
            MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelConfigurarECF, true);
            dbgAliquotas.getjTable().setEnabled(true);
            dbgFormasPagto.getjTable().setEnabled(true);
            cbImpressorasFiscal.setEnabled(false);    
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }
    private boolean Inicializar_ECF_PDV(){
        boolean Ret = false;
        try {
            if(txtPorta.getText().trim().length()==0){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("PORTA DE COMUNICAÇÃO NÃO INFORMADA", "VALIDAÇÃO DO ECF");
                return false;
            }
            String Porta = txtPorta.getText();
            
            String Modelo =cbImpressorasFiscal.getSelectedItem().toString();
                        
            if(!ValidarDLL_ECF()) {return false;}            
            
            if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("CONFIRMA A INICIALIZAÇÃO DO ECF ["+ Modelo +"] NA PORTA ["+ Porta +"] ?", "INICIALIZAR ECF")!=MetodosUI_Auxiliares_1.Sim())
            {
               return false;
            }
            
            if(ECFAtual.Inicializar_ECF_PDV(Modelo, Porta)){
                HashMap<String, String> DadosDoECF = ECFAtual.getECF().DadosDoECF();
                if(DadosDoECF.size()==5){                  
                   lblCNPJ.setText(ManipulacaoString.DeixarSomenteNumeros(DadosDoECF.get("cnpj")));
                   lblMFD.setText( DadosDoECF.get("mfd").trim());
                   lblMarca.setText(DadosDoECF.get("marca").trim());
                   lblModelo.setText(DadosDoECF.get("modelo").trim());
                   lblTipo.setText(DadosDoECF.get("tipo").trim());
                   chkECFValido.setText("ECF VALIDADO COM SUCESSO"); 
                   DesbloquearComponentesECF();
                   MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("ECF INICIALIZADO COM SUCESSO", "PROCEDIMENTO OK");
                   //GerenciarCaixa.Caixa_Ativar_ECF(WIDTH, Porta, Porta, Porta, Modelo, Porta);
                }else{
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("NÃO FOI POSSÍVEL INICIALIZAR O ECF", "PROCEDIMENTO NÃO REALIZADO");
                }
            }

            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
  
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LBLTITULO;
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelCaixa;
    private javax.swing.JPanel PainelConfigurarECF;
    private javax.swing.JPanel PainelDBG_Aliquota;
    private javax.swing.JPanel PainelDBG_FormaPgto;
    private javax.swing.JPanel PainelDefinirECF;
    private javax.swing.JPanel PainelFundo;
    private javax.swing.JPanel PainelLoja;
    private javax.swing.JPanel PainelPDVStatus;
    private javax.swing.JPanel PainelPDV_Digitacao;
    private javax.swing.JPanel PainelPorta;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelPrincipalCFG;
    private javax.swing.JPanel PainelPrincipalDadosPDV;
    private javax.swing.JPanel PainelTEF;
    private javax.swing.JPanel PainelTopo;
    private javax.swing.JPanel PainelTransmissao;
    private javax.swing.JPanel PainelValidarECF;
    private javax.swing.JTabbedPane TabECF;
    private javax.swing.JButton btApagarAliquota;
    private javax.swing.JButton btApagarForma;
    private javax.swing.JButton btAtualizarDadosPDV;
    private javax.swing.JButton btCFGNFiscal;
    private javax.swing.JButton btCadastrarPagto;
    private javax.swing.JButton btCarregarAliquotas;
    private javax.swing.JButton btCarregarAliquotas1;
    private javax.swing.JButton btCarregarFormas;
    private javax.swing.JButton btFechar2;
    private javax.swing.JButton btLerArquivoCFG;
    private javax.swing.JButton btLocalArquivos;
    private javax.swing.JButton btLocalizarReq;
    private javax.swing.JButton btLocalizarResp;
    private javax.swing.JButton btMontarEDesmontar;
    private javax.swing.JButton btTrocarECF;
    private javax.swing.JButton btValidarECF;
    private javax.swing.JComboBox cbCaixas;
    private javax.swing.JComboBox cbImpressoraNFiscal;
    private javax.swing.JComboBox cbImpressorasFiscal;
    private javax.swing.JComboBox cbLocalEstoque;
    private javax.swing.JComboBox cbLojas;
    private javax.swing.JCheckBox chkComprovantesIreprt;
    private javax.swing.JCheckBox chkConectividade;
    private javax.swing.JCheckBox chkECFValido;
    private javax.swing.JCheckBox chkECF_Ativo;
    private javax.swing.JCheckBox chkEnviarMovimento;
    private javax.swing.JCheckBox chkGaveta;
    private javax.swing.JCheckBox chkReceberCadastro;
    private javax.swing.JCheckBox chkSITEF;
    private javax.swing.JCheckBox chkTEFAtivado;
    private br.com.ui.JTableDinnamuS dbgAliquotas;
    private br.com.ui.JTableDinnamuS dbgFormasPagto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCNPJ;
    private javax.swing.JLabel lblMFD;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JTextField lblStatus;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTransm;
    private javax.swing.ButtonGroup opPorta;
    private javax.swing.JRadioButton opSerial;
    private javax.swing.JRadioButton opUsb;
    private javax.swing.JTextField txtCodCaixa;
    private javax.swing.JTextField txtCodSangria;
    private javax.swing.JTextField txtCodSuprimentoFiscal;
    private javax.swing.JTextField txtCodigoPDV;
    private javax.swing.JTextField txtLocalArquivosDLL;
    private javax.swing.JTextField txtNomeCaixa;
    private javax.swing.JSpinner txtNumeroPortaCom;
    private javax.swing.JTextField txtPastaTEF_Req;
    private javax.swing.JTextField txtPastaTEF_Resp;
    private javax.swing.JTextField txtPorta;
    private javax.swing.JSpinner txtViasCompTEF;
    private javax.swing.JSpinner txtViasCompVendaCrediario;
    // End of variables declaration//GEN-END:variables

    
    
}
