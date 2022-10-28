/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmMovimentacaoCaixa.java
 *
 * Created on 24/09/2010, 09:21:15
 */

package dinnamus.ui.InteracaoUsuario.Venda;

import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import com.toedter.calendar.JDateChooser;
import MetodosDeNegocio.Entidades.Caixa;
import dinnamus.entidades.recursos.Recursos;
import br.data.DataHora;
import br.valor.formatar.FormatarNumero;
import br.data.ManipularData;
import br.TratamentoNulo.TratamentoNulos;
import br.arredondar.NumeroArredondar;
import br.com.FormatarNumeros;
import br.com.ui.ItemLista;
import br.ui.teclas.controleteclas;
import MetodosDeNegocio.Fachada.planodecontas;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Sincronismo.SincronizarMovimento;
import MetodosDeNegocio.Venda.GerenciarCaixa;
import MetodosDeNegocio.Venda.MovCaixa;
import MetodosDeNegocio.Venda.pdvgerenciar;

import UI.Seguranca.ValidarAcessoAoProcesso;
import br.com.ecf.ECFDinnamuS;
import br.com.ui.MetodosUI_Auxiliares_1;
//import br.com.ui.controleteclas;
import br.com.ui.SwingUtilidade;
import br.impressao.EscPos;
import br.impressao.Perifericos;
import br.ui.teclas.DefinirAtalhos2;
import dinnamus.metodosnegocio.venda.caixa.ComprovanteNaoFiscal;
import dinnamus.metodosnegocio.venda.caixa.PDVComprovante;
import dinnamus.metodosnegocio.venda.caixa.PDVComprovanteFiscal;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 *
 * @author dti
 */
public class frmMovimentacaoCaixa extends javax.swing.JDialog {

    /** Creates new form frmMovimentacaoCaixa */
    private String ControleCx="";
    private Integer CodigoCaixa=0, ObjetoCaixa=0, CodigoMovCaixa=0;
    private Thread th=null;
    private boolean  bPodeExecutarThread=false;

    public frmMovimentacaoCaixa(java.awt.Frame parent, boolean modal, String cControleCx, Integer nCodigoCaixa, Integer nObjetoCaixa, Integer nCodigoMov) {
        super(parent, modal);
        Integer nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "Caixa", Sistema.getLojaAtual(), true, "Resumo de Caixa");
        if(nCodigoUsuario>0){
            setControleCx(cControleCx);
            setCodigoCaixa(nCodigoCaixa);
            setObjetoCaixa(nObjetoCaixa);
            setCodigoMovCaixa(nCodigoMov);
            initComponents();
            //bPodeExecutarThread=true;
            if(Iniciar_UI(getCodigoMovCaixa())){
                this.setLocationRelativeTo(null);
                MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal,false);
                this.setVisible(true);
            }else{
                 this.dispose();
            }
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, Recursos.getMensagens().getString("acesso.negado"), "Controle de Caixa", "Aviso");
            this.dispose();
        }
    }

    
    private void Acao(String Tecla){
        
        try {
            if(Tecla.equalsIgnoreCase("F3")){
              btNovoActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("F4")){
                btEditarActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("F5")){
                btExcluirActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("F6")){
                btImprimirActionPerformed(null);
            }else if(Tecla.equalsIgnoreCase("ESCAPE")){
                btFechar1ActionPerformed(null);
                
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
         
    }
     private boolean TeclasAtalhos_UI(){
        try {  
             AbstractAction TeclaAtalhos  = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                       String Fonte = e.getSource().toString();                
                       Acao(Fonte);
                       //txtProcurar.requestFocus();
                }
            };            
             
            String Teclas[] ={"F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","ESCAPE"};  
            DefinirAtalhos2.Definir(PainelJanela, Teclas, TeclaAtalhos);
            //DefinirAtalhos.Definir(PainelCorpo, TeclaAtalhos);
            return true;
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
           return false;
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

        PainelJanela = new javax.swing.JPanel();
        PainelPesquisar = new javax.swing.JPanel();
        cbOpcaoBusca = new javax.swing.JComboBox();
        txtCampoBusca = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        txtDataInicial = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDataFinal = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        jLabel3 = new javax.swing.JLabel();
        PainelListagem = new javax.swing.JPanel();
        dbgMovimento = new br.com.ui.JTableDinnamuS();
        PainelPrincipal = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbContas = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtHistorico = new javax.swing.JTextField();
        txtDataMov = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        jLabel7 = new javax.swing.JLabel();
        cbTipo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        txtValor = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        PainelBotoes = new javax.swing.JPanel();
        btNovo = new javax.swing.JButton();
        btEditar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btImprimir = new javax.swing.JButton();
        PainelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btFechar1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Movimentação de Caixa");
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelJanela.setLayout(new java.awt.GridBagLayout());

        PainelPesquisar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar Movimento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        PainelPesquisar.setLayout(new java.awt.GridBagLayout());

        cbOpcaoBusca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Conta", "Descricao" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PainelPesquisar.add(cbOpcaoBusca, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        PainelPesquisar.add(txtCampoBusca, gridBagConstraints);

        btPesquisar.setBackground(new java.awt.Color(0, 0, 0));
        btPesquisar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        btPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/View.png"))); // NOI18N
        btPesquisar.setText("Pesquisar");
        btPesquisar.setBorderPainted(false);
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisar.add(btPesquisar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisar.add(txtDataInicial, gridBagConstraints);

        jLabel1.setText(" Data Inicial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        PainelPesquisar.add(jLabel1, gridBagConstraints);

        jLabel2.setText(" Data Final");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        PainelPesquisar.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        PainelPesquisar.add(txtDataFinal, gridBagConstraints);

        jLabel3.setText("Campos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        PainelPesquisar.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelJanela.add(PainelPesquisar, gridBagConstraints);

        PainelListagem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listagem de Movimentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        dbgMovimento.setExibirBarra(false);

        javax.swing.GroupLayout PainelListagemLayout = new javax.swing.GroupLayout(PainelListagem);
        PainelListagem.setLayout(PainelListagemLayout);
        PainelListagemLayout.setHorizontalGroup(
            PainelListagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dbgMovimento, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
        );
        PainelListagemLayout.setVerticalGroup(
            PainelListagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelListagemLayout.createSequentialGroup()
                .addComponent(dbgMovimento, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelJanela.add(PainelListagem, gridBagConstraints);

        PainelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de Movimentos de Caixas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        PainelPrincipal.setEnabled(false);
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Codigo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPrincipal.add(jLabel4, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(txtCodigo, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Conta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPrincipal.add(jLabel5, gridBagConstraints);

        cbContas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbContas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbContasFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelPrincipal.add(cbContas, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Histórico");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPrincipal.add(jLabel6, gridBagConstraints);

        txtHistorico.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelPrincipal.add(txtHistorico, gridBagConstraints);

        txtDataMov.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDataMov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataMovFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(txtDataMov, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Data");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPrincipal.add(jLabel7, gridBagConstraints);

        cbTipo.setEditable(true);
        cbTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada", "Saída" }));
        cbTipo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(cbTipo, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPrincipal.add(jLabel8, gridBagConstraints);

        txtValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtValor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValor.setText("11212");
        txtValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(txtValor, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPrincipal.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelJanela.add(PainelPrincipal, gridBagConstraints);

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));
        PainelBotoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        btNovo.setBackground(new java.awt.Color(0, 0, 0));
        btNovo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btNovo.setForeground(new java.awt.Color(255, 255, 255));
        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Create.png"))); // NOI18N
        btNovo.setMnemonic('n');
        btNovo.setText("Novo [F3]");
        btNovo.setBorderPainted(false);
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        PainelBotoes.add(btNovo, gridBagConstraints);

        btEditar.setBackground(new java.awt.Color(0, 0, 0));
        btEditar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btEditar.setForeground(new java.awt.Color(255, 255, 255));
        btEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Modify.png"))); // NOI18N
        btEditar.setMnemonic('e');
        btEditar.setText("Editar [F4]");
        btEditar.setBorderPainted(false);
        btEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        PainelBotoes.add(btEditar, gridBagConstraints);

        btExcluir.setBackground(new java.awt.Color(0, 0, 0));
        btExcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Erase.png"))); // NOI18N
        btExcluir.setMnemonic('x');
        btExcluir.setText("Excluir [F5]");
        btExcluir.setBorderPainted(false);
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        PainelBotoes.add(btExcluir, gridBagConstraints);

        btImprimir.setBackground(new java.awt.Color(0, 0, 0));
        btImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/printer.png"))); // NOI18N
        btImprimir.setMnemonic('p');
        btImprimir.setText("Imprimir [F6]");
        btImprimir.setBorderPainted(false);
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        PainelBotoes.add(btImprimir, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelJanela.add(PainelBotoes, gridBagConstraints);

        PainelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money_add.png"))); // NOI18N
        lblTitulo.setText("MOVIMENTAÇÕES DE CAIXA");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelTitulo.add(lblTitulo, gridBagConstraints);

        btFechar1.setBackground(new java.awt.Color(0, 0, 0));
        btFechar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btFechar1.setForeground(new java.awt.Color(255, 255, 255));
        btFechar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btFechar1.setMnemonic('F');
        btFechar1.setText("FECHAR");
        btFechar1.setBorderPainted(false);
        btFechar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFechar1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 10);
        PainelTitulo.add(btFechar1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        PainelTitulo.add(jLabel10, gridBagConstraints);

        jLabel11.setBackground(new java.awt.Color(255, 255, 204));
        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/barra logo dinnamus.JPG"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        PainelTitulo.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelJanela.add(PainelTitulo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelJanela, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Saida(){
    try {            
            if(Sistema.isOnline()){
               SincronizarMovimento.EnviarDadosCaixa(false,false);
            }
            this.dispose();
        
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    private boolean  ValidarEntradas(){
        boolean bRet=false;
        try {

            
            if(!DataHora.IsDateValid("dd/MM/yyyy", txtDataMov.getDate())){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Data Inválida", "Movimento de Caixa", "AVISO");
                txtDataMov.getDateEditor().getUiComponent().requestFocus();
                return false;
            }else if(FormatarNumero.FormatarNumero(txtValor.getValue().toString())==Float.NaN ){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Valor Inválido", "Movimento de Caixa", "AVISO");
                    SwingUtilidade.RequestFocus(txtValor);
                    return false;
            }else if(FormatarNumero.FormatarNumero(txtValor.getValue().toString()).floatValue()<=0f){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Valor Inválido", "Movimento de Caixa", "AVISO");
                    SwingUtilidade.RequestFocus(txtValor);
                    return false;
            }
            

            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private void PosicionarCursorGrid(){
        try {
            int nLinhas = dbgMovimento.getjTable().getRowCount();
            if(nLinhas>0){
                dbgMovimento.getjTable().setRowSelectionInterval(nLinhas-1, nLinhas-1);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

    }
    private Caixa SetarObjetoCaixa(){
        Caixa c = new Caixa();
        try {

            //"(:codigo, :codigoorc, :descricao, :conta, :tipo, :data, :valor,:inicio, :loja, :historico, :codcaixa, :objetocaixa, :controlecx)";
            ItemLista i = (ItemLista)cbContas.getSelectedItem();
            if(txtCodigo.getText().length()>0){
                c.setCodigo(Long.parseLong(txtCodigo.getText()));
            }
            c.setCodigoorc("");
            c.setConta(i.getIndice().toString());
            c.setDescricao(i.getDescricao());
            c.setTipo(cbTipo.getSelectedItem().toString());
            c.setData(txtDataMov.getDate());
            c.setValor( NumeroArredondar.Arredondar_Double(Double.valueOf(FormatarNumero.FormatarNumero(txtValor.getValue().toString())),2));
            c.setInicio('P');
            c.setLoja(Sistema.getLojaAtual());
            c.setHistorico(txtHistorico.getText());
            c.setCodcaixa(getCodigoCaixa());
            c.setObjetocaixa(getObjetoCaixa());
            c.setControleCx(getControleCx());
            c.setPdvoff(pdvgerenciar.CodigoPDV());

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return c;

    }
    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        // TODO add your handling code here:
        
        try {
            String Titulo = btNovo.getText();
            if(Titulo.startsWith("Novo")){
                LimparCampos();
                cbContas.requestFocus();
                btNovo.setText("Gravar [F3]");
                btEditar.setText("Cancelar [F4]");
                //btEditar.setEnabled(false);
                txtDataMov.setDate(ManipularData.DataAtual());
                btExcluir.setEnabled(false);
                MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal,true);

                //PainelPrincipal.update(PainelPrincipal.getGraphics());
            }else if(Titulo.startsWith("Gravar")){
                if(ValidarEntradas()){
                    Double nSaldoCaixa = NumeroArredondar.Arredondar_Double(GerenciarCaixa.Caixa_Saldo_Conta(getControleCx(), "1.1").get("total"),2);
                    Caixa c = SetarObjetoCaixa();
                    if(c.getTipo().charAt(0)=='S'){
                      if(nSaldoCaixa.floatValue()<c.getValor().floatValue()){
                          MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O Saldo em caixa [  R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa)  + " ] é insuficiente para realizar a movimentação", "Saida da Caixa", "AVISO");
                          return;
                      }
                    }

                    Long nCodigo=MovCaixa.MovCaixa_Incluir(c, true,true,pdvgerenciar.CodigoPDV());
                    if(nCodigo!=0){                        
                        btNovo.setText("Novo [F3]");
                        btEditar.setText("Editar [F4]");
                        btEditar.setEnabled(true);
                        btExcluir.setEnabled(true);
                        AtualizarGridMov(getControleCx(),getCodigoMovCaixa());
                        PosicionarCursorGrid();
                        MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal,false);
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possivel incluir o registro", "Caixa", "AVISO");
                    }
                }
            }else{
                
                btNovo.setText("Novo [F3]");
                btEditar.setText("Editar [F4]");
                btEditar.setEnabled(true);
                btExcluir.setEnabled(true);
                MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal,false);
                Integer nLinha=dbgMovimento.getjTable().getSelectedRow();
                if(nLinha>=0){
                    Long nCodigo=  Long.valueOf(dbgMovimento.getjTable().getModel().getValueAt(nLinha, 0).toString());
                    PreencherCamposComResultSet(nCodigo);
                }
                //PreencherCamposComResultSet(nCodigoAtual);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_btNovoActionPerformed

    private void btEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarActionPerformed
        // TODO add your handling code here:
        try {
            if(btEditar.getText().startsWith("Cancelar")){
                
                    btNovo.setText("Novo [F3]");
                    btEditar.setText("Editar [F4]");
                    btEditar.setEnabled(true);
                    btExcluir.setEnabled(true);
                    //PreencherCamposComResultSet(nCodigo);
                    Integer nLinha=dbgMovimento.getjTable().getSelectedRow();
                    if(nLinha>=0){
                        Long nCodigo=  Long.valueOf(dbgMovimento.getjTable().getModel().getValueAt(nLinha, 0).toString());
                        PreencherCamposComResultSet(nCodigo);
                    }
                    MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal,false);

            }else if(btEditar.getText().startsWith("Editar")){
                    String cSituacao="";
                    cSituacao=  MovCaixa.MovCaixa_ConsultarSituacaoRegistro(Integer.valueOf(txtCodigo.getText()), getControleCx(),true);
                    if(cSituacao.length()>0){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, cSituacao, "Situação do Registro", "AVISO");
                    }else{
                        btNovo.setText("Cancelar [F3]");
                        btEditar.setText("Gravar [F4]" );
                        btExcluir.setEnabled(false);
                        cbContas.requestFocus();
                        MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal,true);
                    }
            }else{
                if(ValidarEntradas()){                    
                    Caixa c = SetarObjetoCaixa();
                    if(c.getTipo().charAt(0)=='S'){
                        Double nSaldoCaixa = NumeroArredondar.Arredondar_Double(GerenciarCaixa.Caixa_Saldo_Conta(getControleCx(), "1.1").get("total"),2);
                        ResultSet rsRegistro =  MovCaixa.MovCaixa_Consultar(c.getCodigo(), true);                        
                        if(rsRegistro.next()){
                            nSaldoCaixa+= NumeroArredondar.Arredondar_Double(rsRegistro.getDouble("valor"),2);
                            if(nSaldoCaixa.doubleValue()<c.getValor().doubleValue()){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O Saldo em caixa [  R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa)  + " ] é insuficiente para realizar a movimentação", "Saida da Caixa", "AVISO");
                                return;
                            }
                        }
                    }
                    btNovo.setText("Novo [F3]");
                    btEditar.setText("Editar [F4]");
                    btExcluir.setEnabled(true);
                    if(MovCaixa.MovCaixa_Atualizar(c, true, true,pdvgerenciar.CodigoPDV())){
                       AtualizarGridMov(getControleCx(),getCodigoMovCaixa());
                       MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal,false);
                       //PosicionarCursorGrid();
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possivel atualizar o registro", "Caixa", "AVISO");
                    }
                }                
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_btEditarActionPerformed

    private void txtDataMovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataMovFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDataMovFocusGained

    private void txtValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusGained
            // TODO add your handling code here:
        SwingUtilidade.SelectAll(txtValor);
    }//GEN-LAST:event_txtValorFocusGained

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btNovo.requestFocus();
        }
    }//GEN-LAST:event_txtValorKeyPressed

    private void cbContasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbContasFocusLost
        // TODO add your handling code here:

        try {            
                ItemLista i = (ItemLista)cbContas.getSelectedItem();
                ResultSet rs = planodecontas.Listar(Sistema.getCodigoLojaMatriz(),i.getIndice().toString());
                if(rs.next()){
                    cbTipo.setSelectedItem(rs.getString("tipo"));
                }            
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_cbContasFocusLost

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        try {
            if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Confirma a exclusão do Movimento de Caixa","Excluir Mov." , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                if(txtCodigo.getText().length()>0){
                   Integer nCodigo =Integer.valueOf(txtCodigo.getText());
                   if(MovCaixa.MovCaixa_Excluir(nCodigo, DAO_RepositorioLocal.getCnRepLocal(),true)){
                       AtualizarGridMov(getControleCx(),getCodigoMovCaixa());
                       LimparCampos();
                       PosicionarCursorGrid();
                   }else{
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível excluir o registro", "Excluir", "AVISO");
                   }
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost
        // TODO add your handling code here:
       if(!btNovo.getText().startsWith("Gravar")){
          btEditar.requestFocus();
       }
    }//GEN-LAST:event_txtValorFocusLost

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        // TODO add your handling code here:
        try {
            Date dDataInicial=null, dDataFinal=null;
            dDataInicial = (DataHora.IsDateValid("dd/MM/yyyy", txtDataInicial.getDate()) ?  txtDataInicial.getDate() : null);
            dDataFinal = (DataHora.IsDateValid("dd/MM/yyyy", txtDataFinal.getDate()) ?  txtDataFinal.getDate() : null);
            ResultSet rs=MovCaixa.MovCaixa_Pesquisar_2(getControleCx(), dDataInicial, dDataFinal, cbOpcaoBusca.getSelectedItem().toString(), txtCampoBusca.getText(), true);
            if(rs.next()){
                dbgMovimento.setRsDados(rs);
                dbgMovimento.getjTable().setRowSelectionInterval(0, 0);
                dbgMovimento.requestFocus();
            }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Nenhum registro localizado", "Localizar Lancamento de Caixa", "AVISO");
            }


        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
       

    }//GEN-LAST:event_formWindowClosed

    private void btFechar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar1ActionPerformed
        // TODO add your handling code here:
        Saida();
        //this.dispose();
    }//GEN-LAST:event_btFechar1ActionPerformed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        // TODO add your handling code here:
         try {
            if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes(null, "Confirma a impressão do Movimento de Caixa","Imprimir Mov." , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                if(txtCodigo.getText().length()>0){
                   Long nCodigo =Long.valueOf(txtCodigo.getText());
                   ImprimirComprovante(nCodigo, Sistema.getLojaAtual(), pdvgerenciar.CodigoPDV(), getControleCx());                   
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }//GEN-LAST:event_btImprimirActionPerformed

    /**
    * @param args the command line arguments
    */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelJanela;
    private javax.swing.JPanel PainelListagem;
    private javax.swing.JPanel PainelPesquisar;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btFechar1;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JComboBox cbContas;
    private javax.swing.JComboBox cbOpcaoBusca;
    private javax.swing.JComboBox cbTipo;
    private br.com.ui.JTableDinnamuS dbgMovimento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCampoBusca;
    private javax.swing.JTextField txtCodigo;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDataInicial;
    private com.toedter.calendar.JDateChooser txtDataMov;
    private javax.swing.JTextField txtHistorico;
    private javax.swing.JFormattedTextField txtValor;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the ControleCx
     */
    public String getControleCx() {
        return ControleCx;
    }

    /**
     * @param ControleCx the ControleCx to set
     */
    public void setControleCx(String ControleCx) {
        this.ControleCx = ControleCx;
    }
    private boolean ImprimirComprovante(Long Codigo,int Loja,int PDv,String cControlecx){
        boolean Ret = false;
        try {
            Object[] opcoes = {"Fiscal", "N-Fiscal"};

            String cRet = MetodosUI_Auxiliares_1.InputBox(null, "Escolha o tipo de relatorio", "Relatorio de Fechamento de Caixa", "AVISO", opcoes, "");
            if (cRet != null) {
                int nTipoComprovante = (cRet.equalsIgnoreCase("Fiscal") ? 2 : 1);

                String cTextoRelatorio = ComprovanteNaoFiscal.MovimentacaoCaixa(Codigo, Loja, PDv, cControlecx);
                if (nTipoComprovante == 1) {                    
                    if (PDVComprovante.getImpressoraCompravante().isOK()) {
                       
                        PDVComprovante.getImpressoraCompravante().Imprimir_Texto(EscPos.CodigoTextoCompactado(), EscPos.CodigoAlinhar_Esquerda(), cTextoRelatorio.getBytes());
                        PDVComprovante.getImpressoraCompravante().Imprimir_Texto(Perifericos.BuscarCodigoGaveta());
                        PDVComprovante.getImpressoraCompravante().Imprimir_Texto(Perifericos.BuscarCodigoGuilhotina());

                    }
                    //porta.Fechar();
                } else {
                    ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixas(Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(), 0, 0, false);
                    if (rsDadosCaixa.next()) {
                        ECFDinnamuS ecfcomprovante = new ECFDinnamuS();
                        String Porta = TratamentoNulos.getTratarString().Tratar(rsDadosCaixa.getString("porta"), "COM1");
                        ecfcomprovante.setTipoECF(rsDadosCaixa.getString("IMPRESSORAFISCAL"), Porta);
                        PDVComprovanteFiscal.setEcfDinnmus(ecfcomprovante);
                        PDVComprovanteFiscal.ImprimirCNFV(cTextoRelatorio);
                    }
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public boolean Iniciar_UI(int nCodigoMovCaixa){
        boolean bRet=false;

          if(IniciarUI_GridMov()){
              if(InicializarCbContas()){
                 AtualizarGridMov(getControleCx(),nCodigoMovCaixa);
                 controleteclas.UsarTeclaNaTrocaDeCampos(this.getContentPane(),KeyEvent.VK_ENTER);
                 controleteclas.SetarTodosOsBotoes(this.getContentPane());                 
                 if(dbgMovimento.getjTable().getRowCount()>0){
                     dbgMovimento.getjTable().setRowSelectionInterval(0, 0);
                     dbgMovimento.getjTable().requestFocus();
                 }
                 TeclasAtalhos_UI();
                 bRet=true;
              }
          }
        return bRet;
    }
    private boolean PreencherCamposComResultSet(Long nCodigo){
        boolean bRet=false;
        try {
            if(dbgMovimento.getjTable().getRowCount()>0){
                //
                ResultSet rs = MovCaixa.MovCaixa_Consultar(nCodigo, true);
                if(rs.next()){
                    txtCodigo.setText(String.valueOf(rs.getInt("codigo")));
                    MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbContas,  TratamentoNulos.getTratarString().Tratar(rs.getString("conta"),""));
                    txtHistorico.setText(TratamentoNulos.getTratarString().Tratar(rs.getString("historico"),""));
                    txtValor.setValue(rs.getFloat("valor"));
                    txtDataMov.setDate(rs.getDate("data"));
                    cbTipo.setSelectedItem(TratamentoNulos.getTratarString().Tratar(rs.getString("tipo"),""));
                }else{
                    LimparCampos();
                }

           }

        } catch (SQLException ex) {
           LogDinnamus.Log(ex);
        }
        return bRet;
    }
    public boolean IniciarUI_GridMov(){
        boolean bRet=false;
        try {
            
            
            dbgMovimento.addClColunas("codigo","CODIGO",60,true);
            dbgMovimento.addClColunas("descricao","DESCRICAO",250,true);
            dbgMovimento.addClColunas("tipo","TIPO",80,true);
            dbgMovimento.addClColunas("valor","VALOR",60,true,false,SwingConstants.RIGHT);
            dbgMovimento.addDateFormat("DATA");
            dbgMovimento.addNumberFormatMoeda("VALOR");           
            
            dbgMovimento.getjTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int[] nLinha = dbgMovimento.getjTable().getSelectedRows();                
                if(nLinha.length>0){
                    TableModel tm = dbgMovimento.getjTable().getModel();
                    Long nCodigo= Long.valueOf( tm.getValueAt(nLinha[0], 0).toString());
                    PreencherCamposComResultSet(nCodigo);
                }
            }
            });
            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    public boolean InicializarCbContas(){
        try {
            MetodosUI_Auxiliares_1.PreencherCombo(cbContas, planodecontas.Listar(Sistema.getCodigoLojaMatriz()),"descricao", "conta", true,"string","");
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return false;
    }
    public void AtualizarGridMov(String cControleCx, Integer nCodigoMovCaixa){
            dbgMovimento.setRsDados(GerenciarCaixa.Caixa_Entradas_E_Saidas2(cControleCx,"",nCodigoMovCaixa));
    }
    private void LimparCampos(){
        try {
            txtCodigo.setText("");
            if(cbContas.getItemCount()>0){
                cbContas.setSelectedIndex(0);
            }
            txtHistorico.setText("");
            txtDataMov.setDate(null);
            cbTipo.setSelectedIndex(0);
            txtValor.setValue(0);
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }
    

    /**
     * @return the CodigoCaixa
     */
    public Integer getCodigoCaixa() {
        return CodigoCaixa;
    }

    /**
     * @param CodigoCaixa the CodigoCaixa to set
     */
    public void setCodigoCaixa(Integer CodigoCaixa) {
        this.CodigoCaixa = CodigoCaixa;
    }

    /**
     * @return the ObjetoCaixa
     */
    public Integer getObjetoCaixa() {
        return ObjetoCaixa;
    }

    /**
     * @param ObjetoCaixa the ObjetoCaixa to set
     */
    public void setObjetoCaixa(Integer ObjetoCaixa) {
        this.ObjetoCaixa = ObjetoCaixa;
    }

    /**
     * @return the CodigoMovCaixa
     */
    public Integer getCodigoMovCaixa() {
        return CodigoMovCaixa;
    }

    /**
     * @param CodigoMovCaixa the CodigoMovCaixa to set
     */
    public void setCodigoMovCaixa(Integer CodigoMovCaixa) {
        this.CodigoMovCaixa = CodigoMovCaixa;
    }
    public class AtualizarSaldoOnline implements Runnable
    {

        public void run() {
                    if(bPodeExecutarThread){
                        while(bPodeExecutarThread){
                            //txtSaldoCaixa.setValue(GerenciarCaixa.Caixa_Saldo_Conta(getControleCx(), "1.1"));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(frmMovimentacaoCaixa.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }else{
                         Thread.yield();
                    }


        }

    }


}
