/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.prevenda;

import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensorc;
import MetodosDeNegocio.Fachada.cadproduto;
import MetodosDeNegocio.Fachada.vendedor;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.DadosorcRN;
import MetodosDeNegocio.Venda.ItensorcRN;
import MetodosDeNegocio.Venda.pdvgerenciar;
import MetodosDeNegocio.prevenda.Prevenda;
import br.TratamentoNulo.TratamentoNulos;
import br.arredondar.NumeroArredondar;
import br.com.FormatarNumeros;
import br.com.ecf.ECFDinnamuS;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.ui.BloquearTela;
import br.com.ui.ItemLista;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.com.ui.SwingUtilidade;
import br.com.ui.ValidarCelula;
import br.com.ui.jtabledinnamus_ModelListener;
import br.data.DataHora;
import br.data.ManipularData;
import br.ui.teclas.DefinirAtalhos2;
import br.valor.formatar.FormatarNumero;
import com.toedter.calendar.JDateChooser;
import dinnamus.ui.InteracaoUsuario.Estoque.frmPesquisarProduto;
import dinnamus.ui.InteracaoUsuario.Venda.frmClienteListagem;
import dinnamus.ui.componentes.tabela.EditorTabela;
import dinnamus.ui.componentes.tabela.Tabela_RecursosAdicionais;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author Fernando
 */
public class frmPDV_PreVenda extends javax.swing.JDialog {

    /**
     * Creates new form frmPDV_PreVenda
     */
    
    private static String ControleCX="";
    private static Dadosorc dadosorc=null;
    private static Itensorc itensorc=null;
    private static ArrayList<Itensorc> arItensorc=new ArrayList<Itensorc>();
    private static Integer nCodigoFilial=0;
    private static Integer nCodigoObjetoCaixa=0;
    private static ECFDinnamuS EcfDinnmus = new ECFDinnamuS();
    private static String NomeImpressoraComprovante="";
    private static boolean bParaThread =false;
    private static boolean bAtualizacaoPedente =false;
    private static String ColunasGrid ="";
    private static boolean GridIniciado =false;
            

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
    public frmPDV_PreVenda(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         this.GridIniciado =false;  
        //this.dbgPrevenda_Itens.setjTable(new JTable());
        this.dbgPrevenda_Itens.getjTable().update(this.dbgPrevenda_Itens.getjTable().getGraphics());
        MetodosUI_Auxiliares_1.MaximizarJanelaDeDialogo(this);
    }
    private boolean UI_Carregar(){
        try {
            
            //if (!UI_CarregarLogo()) {return false;}
            
            if (!UI_CarregarDadosLoja()) {return false;}
            
            //if(!InicializarRelogio()) {return false;}
            
            //if (!UI_CarregarDadosUsuario()) {return false;}
            
            if (!UI_CarregarCombo()){return false;}
                                   
            if (!IniciarVenda()){
                
                return false;            
            }
            //controleteclas.UsarTeclaNaTrocaDeCampos(PainelPrincipal,KeyEvent.VK_ENTER);                
            
            //controleteclas.SetarTodosOsBotoes(this.getContentPane());
            
            TeclasAtalhos_UI();

            IniciarTarefa();
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
            return false;
        }
     
    }
 private void TeclaAtalho_Acoes_2(String Fonte){
     
     try {
         if(Fonte.equalsIgnoreCase("F3")){
           btGravarPrevendaActionPerformed(null);
         }else if(Fonte.equalsIgnoreCase("F2")){
             btNotaPrevendaActionPerformed(null);
         }else if(Fonte.equalsIgnoreCase("F6")){
             txtDescontoPercentual.requestFocus();
          }else if(Fonte.equalsIgnoreCase("ESCAPE")){
              btFechar1ActionPerformed(null);
          }else if(Fonte.equalsIgnoreCase("F4")){
            btPrevenda_LocalizarClienteActionPerformed(null);
          }else if(Fonte.equalsIgnoreCase("F9")){
              btPesquisarActionPerformed(null);
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
                       TeclaAtalho_Acoes_2(Fonte);
                       //txtProcurar.requestFocus();
                }
            };            
             
            String Teclas[] ={"F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","ESCAPE"};  
            DefinirAtalhos2.Definir(PainelCorpo, Teclas, TeclaAtalhos);
            //DefinirAtalhos.Definir(PainelCorpo, TeclaAtalhos);
            return true;
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
           return false;
       }
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

        PainelPrincipal = new javax.swing.JPanel();
        PainelBotoes = new javax.swing.JPanel();
        btDeletarItem = new javax.swing.JButton();
        btNotaPrevenda = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();
        btGravarPrevenda = new javax.swing.JButton();
        PainelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btFechar1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblRelogio = new javax.swing.JLabel();
        PainelCorpo = new javax.swing.JPanel();
        PainelData = new javax.swing.JPanel();
        txtPrevenda_Data = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        txtOperador8 = new javax.swing.JTextField();
        PainelVendedor = new javax.swing.JPanel();
        cbPrevenda_Vendedor = new javax.swing.JComboBox();
        txtPrevenda_CodigoVendedor = new javax.swing.JTextField();
        PainelCliente = new javax.swing.JPanel();
        txtPrevenda_NomeCliente = new javax.swing.JTextField();
        btPrevenda_LocalizarCliente = new javax.swing.JButton();
        PainelGrid = new javax.swing.JPanel();
        dbgPrevenda_Itens = new br.com.ui.JTableDinnamuS();
        PainelTotal = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtDescontoValor = new javax.swing.JTextField();
        lblTotal1 = new javax.swing.JLabel();
        txtDescontoPercentual = new javax.swing.JTextField();
        lblTotal2 = new javax.swing.JLabel();
        txtTotalLiquido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTotal3 = new javax.swing.JLabel();
        txtQTTotal = new javax.swing.JTextField();
        PainelCodigoPrevenda = new javax.swing.JPanel();
        txtOperador6 = new javax.swing.JTextField();
        txtPrevenda_Codigo = new javax.swing.JTextField();
        SEQUENCIA = new javax.swing.JTextField();
        txtSequencia = new javax.swing.JTextField();
        txtIndice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setBackground(new java.awt.Color(0, 0, 0));
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelBotoes.setOpaque(false);
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        btDeletarItem.setBackground(new java.awt.Color(0, 0, 0));
        btDeletarItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btDeletarItem.setForeground(new java.awt.Color(255, 255, 255));
        btDeletarItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Erase.png"))); // NOI18N
        btDeletarItem.setMnemonic('n');
        btDeletarItem.setText("[DEL] Deletar Item");
        btDeletarItem.setBorderPainted(false);
        btDeletarItem.setFocusable(false);
        btDeletarItem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btDeletarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletarItemActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        PainelBotoes.add(btDeletarItem, gridBagConstraints);

        btNotaPrevenda.setBackground(new java.awt.Color(0, 0, 0));
        btNotaPrevenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btNotaPrevenda.setForeground(new java.awt.Color(255, 255, 255));
        btNotaPrevenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money_add.png"))); // NOI18N
        btNotaPrevenda.setMnemonic('n');
        btNotaPrevenda.setText("Nova [ F2 ]");
        btNotaPrevenda.setBorderPainted(false);
        btNotaPrevenda.setFocusable(false);
        btNotaPrevenda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btNotaPrevenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNotaPrevendaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        PainelBotoes.add(btNotaPrevenda, gridBagConstraints);

        btPesquisar.setBackground(new java.awt.Color(0, 0, 0));
        btPesquisar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        btPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/calculator_edit.png"))); // NOI18N
        btPesquisar.setText("Pesquisar [ F9 ]");
        btPesquisar.setBorderPainted(false);
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        PainelBotoes.add(btPesquisar, gridBagConstraints);

        btGravarPrevenda.setBackground(new java.awt.Color(0, 0, 0));
        btGravarPrevenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btGravarPrevenda.setForeground(new java.awt.Color(255, 255, 255));
        btGravarPrevenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png"))); // NOI18N
        btGravarPrevenda.setMnemonic('n');
        btGravarPrevenda.setText("Gravar [ F3 ]");
        btGravarPrevenda.setBorderPainted(false);
        btGravarPrevenda.setFocusable(false);
        btGravarPrevenda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btGravarPrevenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGravarPrevendaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 9, 8, 9);
        PainelBotoes.add(btGravarPrevenda, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 16, 16);
        PainelPrincipal.add(PainelBotoes, gridBagConstraints);

        PainelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/impressora.png"))); // NOI18N
        lblTitulo.setText("PRÉ-VENDA");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.9;
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
        PainelTitulo.add(jLabel5, gridBagConstraints);

        jLabel4.setBackground(new java.awt.Color(255, 255, 204));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/barra logo dinnamus.JPG"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.4;
        PainelTitulo.add(jLabel4, gridBagConstraints);

        lblRelogio.setBackground(new java.awt.Color(255, 255, 204));
        lblRelogio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblRelogio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/clock2.png"))); // NOI18N
        lblRelogio.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelTitulo.add(lblRelogio, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelTitulo, gridBagConstraints);

        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        PainelData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelData.setLayout(new java.awt.GridBagLayout());

        txtPrevenda_Data.getDateEditor().getUiComponent().addKeyListener(

            new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        // btIncluirForma.requestFocus();
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}

            }
        );
        txtPrevenda_Data.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrevenda_DataKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        PainelData.add(txtPrevenda_Data, gridBagConstraints);

        txtOperador8.setEditable(false);
        txtOperador8.setBackground(new java.awt.Color(240, 240, 240));
        txtOperador8.setText(" DATA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelData.add(txtOperador8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelCorpo.add(PainelData, gridBagConstraints);

        PainelVendedor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelVendedor.setLayout(new java.awt.GridBagLayout());

        cbPrevenda_Vendedor.setNextFocusableComponent(dbgPrevenda_Itens);
        cbPrevenda_Vendedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbPrevenda_VendedorFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelVendedor.add(cbPrevenda_Vendedor, gridBagConstraints);

        txtPrevenda_CodigoVendedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrevenda_CodigoVendedorFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelVendedor.add(txtPrevenda_CodigoVendedor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        PainelCorpo.add(PainelVendedor, gridBagConstraints);

        PainelCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelCliente.setLayout(new java.awt.GridBagLayout());

        txtPrevenda_NomeCliente.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelCliente.add(txtPrevenda_NomeCliente, gridBagConstraints);

        btPrevenda_LocalizarCliente.setBackground(new java.awt.Color(0, 0, 0));
        btPrevenda_LocalizarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btPrevenda_LocalizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/User group.png"))); // NOI18N
        btPrevenda_LocalizarCliente.setText("CLIENTE - [F4]");
        btPrevenda_LocalizarCliente.setBorderPainted(false);
        btPrevenda_LocalizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPrevenda_LocalizarClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCliente.add(btPrevenda_LocalizarCliente, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        PainelCorpo.add(PainelCliente, gridBagConstraints);

        PainelGrid.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelGrid.setLayout(new java.awt.GridBagLayout());

        dbgPrevenda_Itens.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        PainelGrid.add(dbgPrevenda_Itens, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.6;
        gridBagConstraints.weighty = 0.7;
        PainelCorpo.add(PainelGrid, gridBagConstraints);

        PainelTotal.setBackground(new java.awt.Color(255, 255, 255));
        PainelTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelTotal.setLayout(new java.awt.GridBagLayout());

        lblTotal.setBackground(new java.awt.Color(240, 240, 240));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("SUB-TOTAL");
        lblTotal.setToolTipText("");
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblTotal.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(lblTotal, gridBagConstraints);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 204));
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0,00");
        txtTotal.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(txtTotal, gridBagConstraints);

        txtDescontoValor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDescontoValor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescontoValor.setText("0,00");
        txtDescontoValor.setToolTipText("");
        txtDescontoValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoValorFocusLost(evt);
            }
        });
        txtDescontoValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescontoValorKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(txtDescontoValor, gridBagConstraints);

        lblTotal1.setBackground(new java.awt.Color(240, 240, 240));
        lblTotal1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal1.setText("DESCONTO [F6]");
        lblTotal1.setToolTipText("");
        lblTotal1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblTotal1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(lblTotal1, gridBagConstraints);

        txtDescontoPercentual.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDescontoPercentual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescontoPercentual.setText("00,00");
        txtDescontoPercentual.setToolTipText("");
        txtDescontoPercentual.setAutoscrolls(false);
        txtDescontoPercentual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoPercentualFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoPercentualFocusLost(evt);
            }
        });
        txtDescontoPercentual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescontoPercentualKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(txtDescontoPercentual, gridBagConstraints);

        lblTotal2.setBackground(new java.awt.Color(240, 240, 240));
        lblTotal2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal2.setText("TOTAL");
        lblTotal2.setToolTipText("");
        lblTotal2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblTotal2.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(lblTotal2, gridBagConstraints);

        txtTotalLiquido.setEditable(false);
        txtTotalLiquido.setBackground(new java.awt.Color(255, 255, 204));
        txtTotalLiquido.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTotalLiquido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalLiquido.setText("0,00");
        txtTotalLiquido.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(txtTotalLiquido, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        PainelTotal.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("$");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        PainelTotal.add(jLabel2, gridBagConstraints);

        lblTotal3.setBackground(new java.awt.Color(240, 240, 240));
        lblTotal3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal3.setText("QUANT");
        lblTotal3.setToolTipText("");
        lblTotal3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblTotal3.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(lblTotal3, gridBagConstraints);

        txtQTTotal.setEditable(false);
        txtQTTotal.setBackground(new java.awt.Color(255, 255, 204));
        txtQTTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtQTTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtQTTotal.setText("000");
        txtQTTotal.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        PainelTotal.add(txtQTTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpo.add(PainelTotal, gridBagConstraints);

        PainelCodigoPrevenda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelCodigoPrevenda.setLayout(new java.awt.GridBagLayout());

        txtOperador6.setEditable(false);
        txtOperador6.setBackground(new java.awt.Color(240, 240, 240));
        txtOperador6.setText("CODIGO VENDA PDV");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelCodigoPrevenda.add(txtOperador6, gridBagConstraints);

        txtPrevenda_Codigo.setEditable(false);
        txtPrevenda_Codigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelCodigoPrevenda.add(txtPrevenda_Codigo, gridBagConstraints);

        SEQUENCIA.setEditable(false);
        SEQUENCIA.setBackground(new java.awt.Color(240, 240, 240));
        SEQUENCIA.setText("CODIGO VENDA PDV");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelCodigoPrevenda.add(SEQUENCIA, gridBagConstraints);

        txtSequencia.setEditable(false);
        txtSequencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        PainelCodigoPrevenda.add(txtSequencia, gridBagConstraints);

        txtIndice.setEditable(false);
        txtIndice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCodigoPrevenda.add(txtIndice, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelCorpo.add(PainelCodigoPrevenda, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 1.4;
        PainelPrincipal.add(PainelCorpo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        getContentPane().add(PainelPrincipal, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btDeletarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletarItemActionPerformed
        // TODO add your handling code here:
        Prevenda_UI_Processar_Tecla_DELETE();
    }//GEN-LAST:event_btDeletarItemActionPerformed

    private void btNotaPrevendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNotaPrevendaActionPerformed
        // TODO add your handling code here:
        try {

            if(!IniciarVenda()){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar uma a pré-venda", "Pré-Venda", "AVISO");
            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btNotaPrevendaActionPerformed

    private void cbPrevenda_VendedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbPrevenda_VendedorFocusLost
        // TODO add your handling code here:
        try {

            ItemLista i = (ItemLista) cbPrevenda_Vendedor.getSelectedItem();
            if(i!=null){
                txtPrevenda_CodigoVendedor.setText(i.getIndice().toString());
            }else{
                txtPrevenda_CodigoVendedor.setText("");
            }

        } catch (Exception e) {

            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_cbPrevenda_VendedorFocusLost

    private void txtPrevenda_CodigoVendedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrevenda_CodigoVendedorFocusLost
        // TODO add your handling code here:

        try {

            if(txtPrevenda_CodigoVendedor.getText().length()>0){
                if(!MetodosUI_Auxiliares_1.SetarOpcaoCombo(cbPrevenda_Vendedor, txtPrevenda_CodigoVendedor.getText())){
                    cbPrevenda_Vendedor.setSelectedIndex(-1);
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Código do Vendedor não localizado", "Pré-Venda", "AVISO");
                    txtPrevenda_CodigoVendedor.requestFocus();
                }
            }

        } catch (Exception e) {

            LogDinnamus.Log(e, true);
        }

    }//GEN-LAST:event_txtPrevenda_CodigoVendedorFocusLost

    private void btPrevenda_LocalizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPrevenda_LocalizarClienteActionPerformed
        // TODO add your handling code here:

        try {
            frmClienteListagem formTabela =  new frmClienteListagem(null, true,false,false);

            formTabela.setVisible(true);

            Long CodigoCliente =formTabela.ClienteSelecionado;
            String NomeCliente=formTabela.ClienteSelecionadoNome;
            if(CodigoCliente>0l) {
                getDadosorc().setCodcliente(CodigoCliente.toString());
                getDadosorc().setCliente(NomeCliente);
                txtPrevenda_NomeCliente.setText(CodigoCliente + " - " + NomeCliente );
            }else{
                getDadosorc().setCodcliente("0");
                getDadosorc().setCliente("** Consumidor **");
            }

        } catch (Exception e) {
            LogDinnamus.Log(e,true);
        }

    }//GEN-LAST:event_btPrevenda_LocalizarClienteActionPerformed

    private void txtPrevenda_DataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrevenda_DataKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPrevenda_DataKeyPressed

    private void btFechar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar1ActionPerformed
        // TODO add your handling code here:
        if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Confirma a saída da pré-venda?", "Pré-Venda")==MetodosUI_Auxiliares_1.Sim()){
            this.dispose();
        }
    }//GEN-LAST:event_btFechar1ActionPerformed

    private void btGravarPrevendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGravarPrevendaActionPerformed
        // TODO add your handling code here:
        
            gravarPrevenda();
        
    }//GEN-LAST:event_btGravarPrevendaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
         if(!UI_Carregar()){            
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível carrega a interface do usuário", "Pré-Venda", "AVISO");
                this.dispose();            
        }
    }//GEN-LAST:event_formWindowOpened

    private BloquearTela bloqtelaPrevenda=new BloquearTela();
    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        // TODO add your handling code here:
        bloqtelaPrevenda.Tela_Bloquear(this, 0.5f, Color.BLACK);
        new frmPDV_ListarPrevenda(null, true).setVisible(true);
        bloqtelaPrevenda.Tela_DesBloquear();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void txtDescontoPercentualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoPercentualFocusLost
        // TODO add your handling code here:
        
        
        try {
           
            txtDescontoPercentual.setText( FormatarNumeros.FormatarParaMoeda(  TratamentoNulos.getTratarBigDecimal().Tratar( getDadosorc().getPercdesc(), BigDecimal.ZERO).doubleValue()));            
            txtDescontoValor.setText( FormatarNumeros.FormatarParaMoeda(  TratamentoNulos.getTratarDouble().Tratar( getDadosorc().getDesconto(), 0d)));            
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
         
        
    }//GEN-LAST:event_txtDescontoPercentualFocusLost

    private void txtDescontoPercentualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoPercentualFocusGained
        // TODO add your handling code here:.
        
        
        txtDescontoPercentual.setSelectionStart(0);
        txtDescontoPercentual.setSelectionEnd(txtDescontoPercentual.getText()==null ? 0 :txtDescontoPercentual.getText().length());
    }//GEN-LAST:event_txtDescontoPercentualFocusGained

    private void txtDescontoValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoValorFocusLost
        // TODO add your handling code here:
        
        try {
           
            txtDescontoPercentual.setText( FormatarNumeros.FormatarParaMoeda(  TratamentoNulos.getTratarBigDecimal().Tratar( getDadosorc().getPercdesc(), BigDecimal.ZERO).doubleValue()));            
            txtDescontoValor.setText( FormatarNumeros.FormatarParaMoeda(  TratamentoNulos.getTratarDouble().Tratar( getDadosorc().getDesconto(), 0d)));            
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_txtDescontoValorFocusLost

    private void txtDescontoValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoValorFocusGained
        // TODO add your handling code here:
         txtDescontoValor.setSelectionStart(0);
        txtDescontoValor.setSelectionEnd(txtDescontoValor.getText()==null ? 0 :txtDescontoValor.getText().length());
    }//GEN-LAST:event_txtDescontoValorFocusGained

    private void txtDescontoPercentualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescontoPercentualKeyPressed
        // TODO add your handling code here:
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            processarDescontoPercentual();
        }
    }//GEN-LAST:event_txtDescontoPercentualKeyPressed

    private void txtDescontoValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescontoValorKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            processarDescontoValor();
        }
    }//GEN-LAST:event_txtDescontoValorKeyPressed

    
    public boolean IniciarVenda(){
        try {
            
           setDadosorc(new Dadosorc());
            
           Long nCodigoVenda = RegistraItem_RegistrarEntidades_Dadosorc();
           
           if(nCodigoVenda==0){
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar uma nova prevenda", "Pré-Venda", "AVISO");    
              return false;           
           }
        
           getDadosorc().setCodigo(nCodigoVenda);
           
           if(!UI_CarregarDadosCliente()){return false;}
           
           txtPrevenda_Codigo.setText(nCodigoVenda.toString());
           
           txtPrevenda_Data.setDate(new Date());
           
           txtPrevenda_CodigoVendedor.setText("");
                      
          
           txtQTTotal.setText("0,00");
           txtDescontoPercentual.setText("0.00");
           txtDescontoValor.setText("0.00");           
           txtTotal.setText("0.00");           
           txtTotalLiquido.setText("0.00");
           if(!GridIniciado){
              UI_IniciarGridItens();            
              GridIniciado=true;
           }
           
           UI_IniciarGridItens_AtualizarGrid(true);
           
           
           if(!NovaLinha(nCodigoVenda)) {
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar o item da prevenda", "Pré-Venda", "AVISO");    
              return false;           
           }
           
           
           //dbgPrevenda_Itens.getjTable().update(dbgPrevenda_Itens.getjTable().getGraphics());
           
           SwingUtilidade.RequestFocus(txtPrevenda_CodigoVendedor);
           return true;
           
        } catch (Exception e) {
            LogDinnamus.Log(e);
            return false;
        }
     
    }
 private Integer NovoSeq(){
         Integer Ret=0;
         try {
             ResultSet c = dbgPrevenda_Itens.getRsDados();
             if(c!=null){
                if(c.last()){
                    Ret = c.getInt("seq")+1;
                }
             }
             
         } catch (Exception e) {
             LogDinnamus.Log(e, true);
         }
         return Ret;
    
     }
 private boolean NovaLinha(Long CodigoVenda){
        try {
           int LinhaAtual = dbgPrevenda_Itens.getjTable().getSelectedRow();
           int TotLinha =dbgPrevenda_Itens.getjTable().getRowCount();
           if(LinhaAtual==TotLinha -1){
              Integer NovoSeq = NovoSeq();
              NovoSeq = NovoSeq==0 ? 1 : NovoSeq;
              dbgPrevenda_Itens.getTbDinnamuS().addRow(true,CodigoVenda, Sistema.getLojaAtual(), pdvgerenciar.CodigoPDV(), false,false);                                                                            
              UI_IniciarGridItens_AtualizarGrid(true);      
              dbgPrevenda_Itens.getTbDinnamuS().getRs().last();
              dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("seq",NovoSeq );               
              TotLinha = dbgPrevenda_Itens.getjTable().getRowCount()-1;
              dbgPrevenda_Itens.getjTable().setRowSelectionInterval(TotLinha,TotLinha);              
              dbgPrevenda_Itens.getjTable().setColumnSelectionInterval(1, 1);
              GravarRegistro();
           }
            return true;
        } catch (Exception e) {
             LogDinnamus.Log(e, true);
             return false;
        }
    } 
   public boolean  UI_CarregarDadosCliente()
    {
        try {
        
            txtPrevenda_NomeCliente.setText("** Consumidor **");
            
            return true;
        } catch (Exception e) {
                LogDinnamus.Log(e, true);
        }
        return false;

    }
    /*private boolean UI_CarregarLogo(){
        try {
            
             Image img=null;
             String cCaminhoLogo = Sistema.getDadosLojaAtualSistema().getString("caminhologo");
             
             if ( cCaminhoLogo !=null) {
                  Blob b=  Sistema.getDadosLojaAtualSistema().getBlob("logo");
                  if(b!=null){
                      byte[] bytes = b.getBytes(1, (int)b.length());

                      img = (new ImageIcon(bytes)).getImage();
                  }else{
                    img = Toolkit.getDefaultToolkit().getImage(
                        getClass().getResource(
                        "/dinnamus/ui/InteracaoUsuario/DinnamuS.jpg")
                        );
                    return true;
                  }
                  
                  
             }else{
                     img = Toolkit.getDefaultToolkit().getImage(
                        getClass().getResource(
                        "/dinnamus/ui/InteracaoUsuario/DinnamuS.jpg")
                        );
                
             }
             
             
              img=img.getScaledInstance((int) PainelLogo.getPreferredSize().getWidth(),
                         ((int)PainelLogo.getPreferredSize().getHeight()),  
                         Image.SCALE_DEFAULT);

             lblLogo.setIcon((new ImageIcon(img)));
             return true;

        } catch (Exception e) {
            LogDinnamus.Log(e);
            return false;
        }
    }*/
    private boolean UI_CarregarCombo(){
        if (!UI_CarregarComboVendedor()){ return false;}
        
        
        return true;
    }
    
    private boolean UI_CarregarComboVendedor(){
          try {
            
            Boolean bRet= MetodosUI_Auxiliares_1.PreencherCombo(cbPrevenda_Vendedor,vendedor.Listar(Sistema.getLojaAtual()),"Nome","Codigo",true);
            //MetodosUI_Auxiliares.SetarOpcaoCombo(cbVendedor, UsuarioSistema.getCodVendedorVinculadoAoCaixa());
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e );
            return false;
        }
    }
    
   
    
    private boolean UI_IniciarGridItens_AtualizarGrid(boolean  bAjustaModelo ){
        try {
            Long nCodigoVenda =dadosorc.getCodigo();
            
            dbgPrevenda_Itens.setRsDados(ItensorcRN.Itensorc_Listar_3(nCodigoVenda));
            //dbgPrevenda_Itens.setEdicao_Query(ItensorcRN.Itensorc_Listar_2_Query(nCodigoVenda));
            
            //dbgPrevenda_Itens.getTbDinnamuS().fireTableStructureChanged();
            return true;
        } catch (Exception e) {
            
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean LimparLinha(){
        try {
             //ResultSet lista = dbgItensDevolvidos.getTbDinnamuS().getRs();
             //dbgItensDevolvidos.getTbDinnamuS().setValorCelular(PontoSalvamento, lista);
             dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("descricao", "");
             dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("codprod", 0l);
             dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("quantidade", 0D);
             dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("preco", 0D);
             dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("total", 0D);
             //dbgItensDevolvidos.getTbDinnamuS().fireTableDataChanged();
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private ArrayList<Double> TotalizarTroca(){
        ArrayList<Double> totais = new ArrayList<Double>();
        try {
           
            ResultSet c = dbgPrevenda_Itens.getRsDados();
            
            c.beforeFirst();
           
            Double Total = 0d;//ItensDevolvidosRN.ItensDevolvidos_Somar(CodigoVenda,false);
            Double Qt = 0d;//ItensDevolvidosRN.ItensDevolvidos_ContarItens(CodigoVenda,false);
            
            while(c.next()){
                Qt += c.getDouble("quantidade");
                Total+=c.getDouble("total");            
            }
            totais.add(Qt);
            totais.add(Total);
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
         
        }
        return totais;
    }
    private boolean AtualizarCamposTotais(){
        try {
           
            ResultSet c = dbgPrevenda_Itens.getRsDados();
            
            c.beforeFirst();
            ArrayList<Double> TotaisTroca = TotalizarTroca();
            Double Qt =  TotaisTroca.get(0);//ItensDevolvidosRN.ItensDevolvidos_ContarItens(CodigoVenda,false);
            Double Total = TotaisTroca.get(1);//ItensDevolvidosRN.ItensDevolvidos_Somar(CodigoVenda,false);
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean GravarRegistro(){
        boolean Ret = false;
        try {
            int l=0,c=0;
            l = dbgPrevenda_Itens.getjTable().getSelectedRow();
            c = dbgPrevenda_Itens.getjTable().getSelectedColumn();
            DAO_RepositorioLocal.Commitar_Statment();
            UI_IniciarGridItens_AtualizarGrid(true);  
            dbgPrevenda_Itens.getjTable().setRowSelectionInterval(l, l);
            dbgPrevenda_Itens.getjTable().setColumnSelectionInterval( c, c);
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
     private boolean DesfazerRegistro(){
        boolean Ret = false;
        try {
            int l=0,c=0;
            l = dbgPrevenda_Itens.getjTable().getSelectedRow();
            c = dbgPrevenda_Itens.getjTable().getSelectedColumn();
            DAO_RepositorioLocal.RollBack_Statment();
            UI_IniciarGridItens_AtualizarGrid(true);  
            dbgPrevenda_Itens.getjTable().setRowSelectionInterval(l, l);
            dbgPrevenda_Itens.getjTable().setColumnSelectionInterval( c, c);
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean UI_IniciarGridItens(){
        try {
            dbgPrevenda_Itens.addClColunas("seq", "Seq", 5,true,false,dbgPrevenda_Itens.Alinhamento_Esqueda);
            dbgPrevenda_Itens.addClColunas("descricao", "Descrição", 50,true,true,dbgPrevenda_Itens.Alinhamento_Esqueda);
            dbgPrevenda_Itens.addClColunas("quantidade", "QT", 10,true,true,dbgPrevenda_Itens.Alinhamento_Direita, Types.DOUBLE,3);
            dbgPrevenda_Itens.addClColunas("preco", "P.Unit", 10,true,true,dbgPrevenda_Itens.Alinhamento_Direita, Types.DOUBLE,2);
            dbgPrevenda_Itens.addClColunas("descv", "$Desc", 10,true,true,dbgPrevenda_Itens.Alinhamento_Direita ,Types.DOUBLE,2 );
            dbgPrevenda_Itens.addClColunas("descp", "%Desc", 10,true,true,dbgPrevenda_Itens.Alinhamento_Direita, Types.DOUBLE,2);
            dbgPrevenda_Itens.addClColunas("total", "TOTAL", 20,true,false,dbgPrevenda_Itens.Alinhamento_Direita, Types.DOUBLE,2);
            dbgPrevenda_Itens.addNumberFormat("total");
            dbgPrevenda_Itens.addNumberFormat("preco");
            dbgPrevenda_Itens.addNumberFormat("descv");
            dbgPrevenda_Itens.addNumberFormat("descp");            
            dbgPrevenda_Itens.addNumberFormat("quantidade");
            //dbgPrevenda_Itens.addNumberFormat("quantidade");   
            dbgPrevenda_Itens.setEdicao_ChavePrimaria("idunico");
            dbgPrevenda_Itens.setEdicao_ChaveEstrangeira("codigo");
            dbgPrevenda_Itens.setEdicao_Tabela("itensorc");                     
            dbgPrevenda_Itens.setAjustaColunaAoPainel(true);            
            dbgPrevenda_Itens.AumentaAlturaLinhas(1.5f);
            dbgPrevenda_Itens.DefinirFonte("Courier New", Font.PLAIN, 18);                        
            dbgPrevenda_Itens.getjTable().setRowSelectionAllowed(false);
            dbgPrevenda_Itens.getjTable().setCellSelectionEnabled(true);
            //dbgPrevenda_Itens.getjTable().setSelectionModel(new ListSelectionModel);
            dbgPrevenda_Itens.getjTable().addPropertyChangeListener("tableCellEditor",new PropertyChangeListener() {
                
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                        int nCol = dbgPrevenda_Itens.getjTable().getSelectedColumn(); 
                        boolean Editando = dbgPrevenda_Itens.getjTable().isEditing();
                        if (!Editando){                              
                            EditorTabela r = (EditorTabela) evt.getOldValue();
                            boolean EdicaoCancela =r.isEdicaoCancelada();
                            if(!EdicaoCancela){                                                      
                                if(nCol ==1){     
                                   int nRet = Prevenda_ProcessarPesquisaGridItens();                
                                   if(nRet==0){
                                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("CODIGO NÃO LOCALIZADO", "Pré-Venda");                                     
                                       try {
                                           LimparLinha();
                                           new Robot().keyPress(KeyEvent.VK_LEFT);
                                           new Robot().keyPress(KeyEvent.VK_RIGHT);
                                       } catch (AWTException ex) {
                                           LogDinnamus.Log(ex, true);
                                       }
                                   }else if(nRet==1){                                       
                                       //String NomeImpresso =  TratamentoNulos.getTratarString().Tratar(dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaString("nome_impresso"),"");
                                       //String Descricao =  TratamentoNulos.getTratarString().Tratar(dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaString("descricao"),"");
                                      
                                          //dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("descricao", NomeImpresso);
                                           try {
                                               new Robot().keyPress(KeyEvent.VK_RIGHT);
                                             
                                           } catch (AWTException ex) {
                                              LogDinnamus.Log(ex, true);
                                           }
                                       
                                   }                                
                                }else if(nCol==3){
                                    try {                                                    
                                        new Robot().keyPress(KeyEvent.VK_DOWN);
                                    } catch (AWTException ex) {
                                        LogDinnamus.Log(ex, true);
                                    }
                                }
                                String Coluna = dbgPrevenda_Itens.getjTable().getColumnName(nCol);
                                if(Coluna.equalsIgnoreCase("quantidade") ||  Coluna.equalsIgnoreCase("preco")){
                                    int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
                                    dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("descv", 0d,Linha);
                                    dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("descp", 0d,Linha);
                                    Grid_AtualizarItem();
                                }else if(Coluna.equalsIgnoreCase("descv")){
                                    Grid_AtualizarDescontoValor();
                                }else if(Coluna.equalsIgnoreCase("descp")){
                                    Grid_AtualizarDescontoPercentual();
                                }
                                Prevenda_UI_TotalizarVenda(getDadosorc().getCodigo());   
                                GravarRegistro();
                            }else{
                                DesfazerRegistro();
                            }
                            
                        }            
                }
            });        
            dbgPrevenda_Itens.getjTable().addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {}                         
                @Override
                public void keyPressed(KeyEvent e) {
                    //MetodosUI_Auxiliares.MensagemAoUsuarioSimples(null, "keypressed : " + String.valueOf(e.getKeyCode()), "Pré-Venda", "AVISO");    
                    if(!dbgPrevenda_Itens.getjTable().isEditing()){
                        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                            Prevenda_UI_ProcessarTeclaDOWN();
                        } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                            e.consume();
                            Prevenda_UI_Processar_Tecla_DELETE();
                        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            e.consume();
                            btFechar1ActionPerformed(null);
                        } else if (e.getKeyChar() == '\n') {
                            Tabela_RecursosAdicionais.TratarTeclaPressionada(e, dbgPrevenda_Itens);                           
                        }else  if (e.getKeyCode() == KeyEvent.VK_F6){
                            
                            e.consume();
                            
                            txtDescontoPercentual.requestFocus();
                         }else  if (e.getKeyCode() == KeyEvent.VK_F3){
                             e.consume();
                             gravarPrevenda();
                             
                         }else  if (e.getKeyCode() == KeyEvent.VK_F9){
                                e.consume();
                                TeclaAtalho_Acoes_2("F9");
                         }
                        
                        
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            });  
            UI_IniciarGridItens_AtualizarGrid(true);  
            dbgPrevenda_Itens.getjTable().setDefaultEditor(Object.class,  new EditorTabela(new Prevenda_Validacoes()));
            dbgPrevenda_Itens.getTbDinnamuS().addTableModelListener(new jtabledinnamus_ModelListener( dbgPrevenda_Itens.getjTable() ));                                   
            dbgPrevenda_Itens.getjTable().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"none"); 
            //KeyStroke[] ks = dbgPrevenda_Itens.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).allKeys();
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
            
    }
    private boolean Prevenda_UI_Processar_Tecla_DELETE(){
        try {
            
             int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if (Linha >= 0) {
                //Long nCodprod = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaLong("codprod", Linha);
                if (MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("CONFIRMA A EXCLUSÃO DO ITEM ?", "Pré-Venda") == MetodosUI_Auxiliares_1.Sim()) {

                    boolean UltimaLinha = false;
                    int LinhaAtual = dbgPrevenda_Itens.getTbDinnamuS().getLinhaAtual();
                    if (Linha + 1 == dbgPrevenda_Itens.getjTable().getRowCount()) {
                        UltimaLinha = true;
                    }
                    //Long nIdUnico = Long.parseLong(dbgPrevenda_Itens.getTbDinnamuS().getValorCelula("idunico").toString());
                    // int nTotalLinhas = dbgPrevenda_Itens.getjTable().getRowCount();
                    int nLinhaAtual = dbgPrevenda_Itens.getjTable().getSelectedRow();
                    //int nSEQ = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaInt("seq", Linha);
                    //; 
                    if (!dbgPrevenda_Itens.getTbDinnamuS().RemoveRow(nLinhaAtual)) {
                        DesfazerRegistro();
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O ITEM NÃO PODE SER EXCLUIDO", "Pré-Venda");
                        //DAO_RepositorioLocal.RollBack_Statment();
                        //NumeroArredondar.Arredondar_Double(Double.NaN, ERROR)
                    } else {
                         DAO_RepositorioLocal.Commitar_Statment();
                         UI_IniciarGridItens_AtualizarGrid(true);
                         if(dbgPrevenda_Itens.getTbDinnamuS().getRowCount()==0){
                            NovaLinha(getDadosorc().getCodigo());
                         }else{        
                             AtualizarSeq();
                             if (!UltimaLinha) {
                                 dbgPrevenda_Itens.getjTable().setRowSelectionInterval(LinhaAtual, LinhaAtual);
                             } else {
                                 int TotalLinha = dbgPrevenda_Itens.getTbDinnamuS().getRowCount();
                                 if (TotalLinha != 0) {
                                     dbgPrevenda_Itens.getjTable().setRowSelectionInterval(TotalLinha - 1, TotalLinha - 1);
                                 }
                             }
                             LinhaAtual = dbgPrevenda_Itens.getjTable().getSelectedRow();
                             dbgPrevenda_Itens.getjTable().setColumnSelectionInterval(2, 2);
                         }
                    }
                }
            }
            return false;
        } catch (Exception e) {

            dbgPrevenda_Itens.getTbDinnamuS().getValorCelula("codprod");
            LogDinnamus.Log(e, true);
            return false;
        }
    }
        public boolean AtualizarSeq(){
        try {
            int nSeq =1;
            
            //ResultSet lista = dbgItensDevolvidos.getTbDinnamuS().getRs();
            dbgPrevenda_Itens.getTbDinnamuS().getRs().beforeFirst();
            while(dbgPrevenda_Itens.getTbDinnamuS().getRs().next()){
                
                dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("seq", nSeq);
                
                nSeq++;
            }        
            //dbgItensDevolvidos.getTbDinnamuS().fireTableDataChanged();
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
  
    private void Prevenda_UI_ProcessarTeclaDOWN(){
        try {
            
            //Object[] ObjLinha =  dbgPrevenda_Itens.TratarLinhaSelecionada(dbgPrevenda_Itens.getjTable());
            int linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(linha>=0){
                //int nSEQ = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaInt("seq", linha);
                Long CodProd = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaLong("codprod", linha);                
                int nTotalLinha = dbgPrevenda_Itens.getjTable().getRowCount(); 
                int nLinhaAtual = dbgPrevenda_Itens.getjTable().getSelectedRow();
                if(CodProd>0l && nLinhaAtual+1 ==nTotalLinha){
                    NovaLinha(getDadosorc().getCodigo());                          
                    UI_IniciarGridItens_AtualizarGrid(true);
                    dbgPrevenda_Itens.getjTable().setRowSelectionInterval(nTotalLinha-1, nTotalLinha-1);
                    dbgPrevenda_Itens.getjTable().setColumnSelectionInterval(1, 1);                                      
                }
            }
            
        } catch (Exception e) {
             LogDinnamus.Log(e, true);
        }
    } 
    private Double Item_CalcularDescontoPercentual(){
        Double Ret = 0d;
        try {
            Double nQT = Item_Qt();
            Double nPreco = Item_Preco();
            Double PercDesconto = Item_DescontoPercentual();
            Ret = NumeroArredondar.Arredondar_Double((nQT * nPreco) * (PercDesconto/100),2);
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean Grid_AtualizarPreco(Double Preco){
        boolean Ret = false;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
                dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("preco", Preco,Linha);
                dbgPrevenda_Itens.getTbDinnamuS().fireTableRowsUpdated(Linha, Linha);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }        
    private boolean Grid_AtualizarQuantidade(Double Quantidade){
        boolean Ret = false;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
                dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("quantidade", Quantidade,Linha);
                dbgPrevenda_Itens.getTbDinnamuS().fireTableRowsUpdated(Linha, Linha);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }        
    private boolean Grid_AtualizarSubtotal(Double Subtotal){
        boolean Ret = false;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
                dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("total", Subtotal,Linha);
                dbgPrevenda_Itens.getTbDinnamuS().fireTableRowsUpdated(Linha, Linha);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
     private boolean Grid_AtualizarDescontoValor(){
        boolean Ret = false;
        try {
            
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
                
               Double ValorBruto = NumeroArredondar.Arredondar_Double(Item_Preco() * Item_Qt(),2);
               Double DescontoValor = Item_DescontoValor();
               Double PercDesconto = NumeroArredondar.Arredondar_Double((DescontoValor/ValorBruto)*100,2);
               //dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("descv", DescontoValor,Linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("descp", PercDesconto,Linha);
               Grid_AtualizarItem();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean Grid_AtualizarDescontoPercentual(){
        boolean Ret = false;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
               Double PercDesconto = Item_DescontoPercentual();
               Double ValorBruto = NumeroArredondar.Arredondar_Double(Item_Preco() * Item_Qt(),2);
               Double DescontoValor =  NumeroArredondar.Arredondar_Double(ValorBruto * (PercDesconto/100d),2);
               dbgPrevenda_Itens.getTbDinnamuS().setValorDouble("descv", DescontoValor,Linha);
               Grid_AtualizarItem();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean Grid_AtualizarItem(){
        boolean Ret = false;
        try {
            
            Double subtotal = Item_CalcularSubTotal();
            Grid_AtualizarSubtotal(subtotal);
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private Double Item_Qt(){
        Double Ret = 0d;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
               Ret = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaDouble("quantidade",Linha);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private Double Item_Preco(){
        Double Ret = 0d;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
               Ret = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaDouble("preco",Linha);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private Double Item_DescontoValor(){
        Double Ret = 0d;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
               Ret = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaDouble("descv",Linha);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private Double Item_DescontoPercentual(){
        Double Ret = 0d;
        try {
            int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
            if(Linha>=0){
               Ret = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaDouble("descp",Linha);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private Double Item_CalcularSubTotal(){
        Double Ret = 0d;
        try {
              int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
              if(Linha>=0){
                Double SubTotal =0d;                
                Double nQT = Item_Qt();
                Double nPreco = Item_Preco();
                Double Desconto = Item_DescontoValor();
                SubTotal =  NumeroArredondar.Arredondar_Double((nQT * nPreco)  - Desconto,2);
                Ret = SubTotal;
              }
             
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    public class Prevenda_Validacoes implements ValidarCelula {

        public boolean Validar(String cColuna, Object obj) {
            try {
                int Linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
                if(!"DESCRICAO".equalsIgnoreCase(cColuna)){
                    Long Codprod =  TratamentoNulos.getTratarLong().Tratar(dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaLong("codprod",Linha),0l);
                    if(Codprod==0l){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "INFORME UM PRODUTO PARA COMEÇAR", "Pré-Venda", "AVISO"); 
                        return false;
                    }
                }                
                if("DESCP".equalsIgnoreCase(cColuna)){
                    Double PercDesconto = Double.parseDouble(obj.toString().replaceAll(",", "."));
                    Double TotalBruto = NumeroArredondar.Arredondar_Double(Item_Preco() * Item_Qt(),2);
                    Double Desconto =  NumeroArredondar.Arredondar_Double(TotalBruto*(PercDesconto/100d),2);
                    Double SubTotal = NumeroArredondar.Arredondar_Double(TotalBruto - Desconto,2);
                    if(SubTotal<=0d){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O desconto informado é maior ou igual ao valor do item", "DESCONTO NÃO PERMITIDO", "AVISO"); 
                        return false;                    
                    }          
                    
                    
                    
                }else if("DESCV".equalsIgnoreCase(cColuna)){
                       Double ValorDesconto = Double.parseDouble(obj.toString().replaceAll(",", "."));
                       Double nQT =  dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaDouble("quantidade",Linha);
                       Double nPreco = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaDouble("preco",Linha);
                       Double nSubTotalItem = nQT * nPreco ; //Double.parseDouble( dbgPrevenda_Itens.getTbDinnamuS().getValorCelula("total").toString());
                       
                       if(ValorDesconto<0D){
                          MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O DESCONTO NÃO PODE SER NEGATIVO", "Pré-Venda", "AVISO"); 
                          return false;                    
                       }
                       
                       if(ValorDesconto>=nSubTotalItem){
                          MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "O DESCONTO NÃO PODE SER MAIOR OU IGUAL AO VALOR DO ITEM", "Pré-Venda", "AVISO"); 
                          return false;                    
                       }                         
                       
                        
                }else if("QUANTIDADE".equalsIgnoreCase(cColuna) || "PRECO".equalsIgnoreCase(cColuna) ){
                        Double nQuantidade =0D;
                        Double nPreco =0D;
                        if("QUANTIDADE".equalsIgnoreCase(cColuna)){
                            nQuantidade = Double.parseDouble(obj.toString().replaceAll(",", "."));
                                if(nQuantidade<=0d){
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Quantidade informada inválida", "QUANTIDADE INVÁLIDA"); 
                                return false;                    
                            }   
                            
                            boolean bfracionado = dbgPrevenda_Itens.getTbDinnamuS().getValorCelulaBoolean("fracionado",Linha);    
                            
                            double qtinteiro = NumeroArredondar.Arredondar_Double(nQuantidade, 0);
                            
                            if(!bfracionado){
                                 if(qtinteiro!=nQuantidade){
                                     MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O Produto não permite quantidade fracionada", "QUANTIDADE INVÁLIDA"); 
                                     return false;                    
                                 }
                             }
                                
                            Long nQtMaxima = Sistema.getDadosLoja(Sistema.getLojaAtual(),true).getLong("qtmaxima");
                            
                            nQtMaxima = (nQtMaxima==0 ? 99999l : nQtMaxima);
                            
                            if(nQuantidade>nQtMaxima){
                               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "A QUANTIDADE INFORMADA EXCEDEU O LIMITE PERMITIDO["+ nQtMaxima +"]", "Pré-Venda", "AVISO");  
                               return false;
                            }             
                            
                            
                        }else{
                            nPreco = Double.parseDouble(obj.toString().replaceAll(",", "."));
                            if(nPreco<=0d){
                               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Preço informado inválido", "PREÇO INVÁLIDO"); 
                               return false;                    
                            }   
                        }
                        //Grid_AtualizarItem();
                         
                }
               
               return true;
            } catch (Exception e) {
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "INFORMAÇÃO DIGITADA INVÁLIDA", "Pré-Venda", "AVISO");                
                 return false;
            }
        }
    
    }
           
    private boolean Prevenda_TotalizarItemPrevenda(Long nCodigoVenda, int nSEQ){
        try {
            Double nValorItem =0D, nQuantidade=0D, nDesconto=0D,nSubTotalItem=0D;
            
            ResultSet rsItens = ItensorcRN.Itensorc_Listar(nCodigoVenda);
            while(rsItens.next()){
                nSEQ = rsItens.getInt("seq");
                nValorItem = rsItens.getDouble("preco");
                nQuantidade = rsItens.getDouble("quantidade");
                nDesconto = rsItens.getDouble("descv");
                nSubTotalItem =  NumeroArredondar.Arredondar_Double(nValorItem*nQuantidade-nDesconto,2);
                
                if(!ItensorcRN.Itensorc_AtualizaSubtotal(nCodigoVenda, nSEQ, nSubTotalItem)){
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void Prevenda_UI_TotalizarVenda(Long nCodigoVenda){
        try {
            
            Double nSubTotal = ItensorcRN.Itensorc_Somar(nCodigoVenda);
            Double nQtTotal = ItensorcRN.Itensorc_SomarQuantidades(nCodigoVenda);
                        
            txtTotal.setText( FormatarNumeros.FormatarParaMoeda(nSubTotal));
            txtQTTotal.setText(FormatarNumeros.FormatarParaMoeda(nQtTotal));
            
            Double Desconto = TratamentoNulos.getTratarDouble().Tratar(getDadosorc().getDesconto(),0d);
            
            txtTotalLiquido.setText(FormatarNumeros.FormatarParaMoeda(nSubTotal-Desconto));
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    
    }
    private Double Prevenda_Totalizar(Long nCodigoVenda){
        try {
            Double nValorItem =0D, nQuantidade=0D, nDesconto=0D,nSubTotalItem=0D, nTotalVenda =0D;
            
            ResultSet rsItens = ItensorcRN.Itensorc_Listar(nCodigoVenda);
            while(rsItens.next()){
                nValorItem = rsItens.getDouble("preco");
                nQuantidade = rsItens.getDouble("quantidade");
                nDesconto = rsItens.getDouble("descv");
                nSubTotalItem =  NumeroArredondar.Arredondar_Double(nValorItem*nQuantidade-nDesconto,2);
                nTotalVenda+=nSubTotalItem;
              
            }
            return nTotalVenda;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return -1d;
        }
    }
    private boolean Prevenda_ProcessarDemaisCamposGrid(){
        try {
             Long nDadosOrc_Codigo =getDadosorc().getCodigo();             
             int nLinhaAtual = dbgPrevenda_Itens.LinhaAtualModel();
             Prevenda_UI_TotalizarVenda(nDadosOrc_Codigo);   
             dbgPrevenda_Itens.getTbDinnamuS().fireTableRowsUpdated(nLinhaAtual, nLinhaAtual);
                       
             return true;
        } catch (Exception e) {
             LogDinnamus.Log(e, true);
             return false;
        }
    }
   
    private int Prevenda_ProcessarPesquisaGridItens(){
        try {                                             
           int nColunaAtual = dbgPrevenda_Itens.getjTable().getSelectedColumn();
           int nLinhaAtual = dbgPrevenda_Itens.getjTable().getSelectedRow();
                                                   
           if (nLinhaAtual>=0){
              String cTextoLocalizar = dbgPrevenda_Itens.getValorCelula("descricao").toString();
              int nSeq = Integer.valueOf(dbgPrevenda_Itens.getValorCelula("seq").toString());
              Long nDadosOrc_Codigo =getDadosorc().getCodigo();
              Long nItensOrc_Idunico =  Long.valueOf(dbgPrevenda_Itens.getValorCelula("idunico").toString());//arItensorc.get(nLinhaAtual).getIdunico();
              if(cTextoLocalizar.trim().length()>0){
                 String cCodigoProduto ="";
                 cTextoLocalizar=cTextoLocalizar.trim();
                 ResultSet rsDadosProduto=null;
                 if(cTextoLocalizar.matches("[0-9]+")){
                      rsDadosProduto =  Prevenda_LocalizaProduto(cTextoLocalizar);
                      rsDadosProduto.beforeFirst();
                      if(!rsDadosProduto.next()){
                          return 0; // Codigo Não localizado
                      }
                 }else{                
                     
                     frmPesquisarProduto pesquisa = new frmPesquisarProduto(null, true, nCodigoFilial, cTextoLocalizar);
                     pesquisa.setVisible(true);
                     cCodigoProduto = pesquisa.getCodigoProduto();
                     
                     if(cCodigoProduto.trim().length()>0){                             
                        rsDadosProduto =  Prevenda_LocalizaProduto(cCodigoProduto);
                     }else{
                        return -1;
                     }
                }
                rsDadosProduto.beforeFirst();
                if(rsDadosProduto.next()){
                   if(InserirProduto(rsDadosProduto)){                              
                       return 1;
                   }                                       
                }else{
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
    private ResultSet Prevenda_LocalizaProduto(String  cCodigoProduto){
        try {
            
              ResultSet rs = cadproduto.Pesquisar(cCodigoProduto, Sistema.getCodigoLojaMatriz(), true, 0, 0, 0, 0f, true, true, Sistema.CodigoDaFilial_LojaAtual());
            
              return rs;
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return null;
        }
    }
    
    
     private boolean InserirProduto(ResultSet rsDadosProduto){
        try {
            rsDadosProduto.beforeFirst();
            if(rsDadosProduto.next()){
                Long CodProd = rsDadosProduto.getLong("ig_chaveunica");
                String Descricao = TratamentoNulos.getTratarString().Tratar(rsDadosProduto.getString("cp_nome"),"");
                Double Valor = rsDadosProduto.getDouble("itp_precovenda");
                Long CodigoSeq = rsDadosProduto.getLong("cp_codigo");
                String Cor = TratamentoNulos.getTratarString().Tratar(rsDadosProduto.getString("cor"), "");
                String Tamanho = TratamentoNulos.getTratarString().Tratar(rsDadosProduto.getString("tamanho"), "");
                //Double ITP_PrecoVenda = rsDadosProduto.getDouble("ITP_PrecoVenda");
                String cTributacaoIcms = (rsDadosProduto.getString("Tributaçãoicms") == null ? "1" : rsDadosProduto.getString("Tributaçãoicms"));
                String cAliquota = (rsDadosProduto.getString("Codaliquota") == null ? "01" : rsDadosProduto.getString("Codaliquota"));
                Double nPercentualDeIcms = (rsDadosProduto.getDouble("Percentualdeicms") == 0D ? 17d : rsDadosProduto.getDouble("Percentualdeicms"));
                String TP_Descricao = TratamentoNulos.getTratarString().Tratar(rsDadosProduto.getString("TP_Descricao"),"");
                String Unidade = TratamentoNulos.getTratarString().Tratar(rsDadosProduto.getString("unidade"),"");
                Boolean nFracionado = rsDadosProduto.getBoolean("francionado");

               int linha = dbgPrevenda_Itens.getjTable().getSelectedRow();
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("codprod", CodProd,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("descricao", Descricao,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("nome_impresso", Descricao,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("preco", Valor,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("quantidade", 1,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("Total", Valor,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("nomemov", "",linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("ref", CodigoSeq.toString(),linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("nomecor", Cor,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("codtam", Tamanho,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("fracionado", nFracionado,linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("unidade", Unidade.trim(),linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("tabela", TP_Descricao.trim(),linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("st", cTributacaoIcms.trim(),linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("codaliquota", cAliquota.trim(),linha);
               dbgPrevenda_Itens.getTbDinnamuS().setValorCelular("icms", nPercentualDeIcms,linha);
               GravarRegistro();
               
             
            }
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    
    private boolean UI_CarregarDadosLoja(){
        try {
            
            ResultSet rs = Sistema.getDadosLojaAtualSistema();
            //if(rs.next()){
                //lblDadosEmpresa.setText(rs.getString("nome"));
            //}  
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e );
            return false;
        }
     
    }
      public Long RegistraItem_RegistrarEntidades_Dadosorc()
    {
        Long nRet=Long.valueOf(0);
        Date dDataAbertura;
        Integer nCodigoVendedor, nCodigoCliente,  nCodigoOperador;
        String cControleCX="";
        String cNomeVendedor,cNomeCliente,cNomeOperador;
        try {

            //DataHora.getStringToData(cControleCX)
            dDataAbertura=  ManipularData.DataAtual();
            nCodigoVendedor= Integer.parseInt(((ItemLista) cbPrevenda_Vendedor.getSelectedItem()).getIndice().toString());
            cNomeVendedor=((ItemLista) cbPrevenda_Vendedor.getSelectedItem()).getDescricao();
            nCodigoOperador=UsuarioSistema.getIdUsuarioLogado();
            cNomeOperador="";
            nCodigoCliente= 0;//Integer.valueOf(getDadosorc().getCodcliente()) ;
            cNomeCliente="";
            
            nRet=RegistraItem_RegistrarEntidades_Dadosorc_Persistencia( dDataAbertura, nCodigoVendedor,cNomeVendedor, nCodigoCliente, cNomeCliente, nCodigoOperador, cNomeOperador,Sistema.getLojaAtual(), UsuarioSistema.getIdUsuarioLogadoCaixa(),0, "", pdvgerenciar.CodigoPDV());
            
          

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return nRet;
    }
 
      
    public static Itensorc SetarItensorc( Dadosorc dDadosorc,int nSeq , Long nItensorc_Idunico ,Long nIG_ChaveUnica, String cCodigoProduto,String cNomeProduto, Double nQuantidade, Double nPreco, Double nSubTotal, String cSituacaoTributaria, String cAliquota, Double nPercentualIcms, int nCodigoLoja, String cTabela, Long nCodigoReexibicao, int nCodigoPDV, boolean  bFracionado, String cCodMov, String Unidade, boolean bRegistroNovo )
    {
        Itensorc i=null;
        try {
            i=new Itensorc();
            //i.setIdunico(DAO_RepositorioLocal.NovoValorIdentidade("itensorc", Sistema.getLojaAtual(),nCodigoPDV));
            if(!bRegistroNovo){
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
            if(nCodigoReexibicao==0){
               i=ItensorcRN.RegistraItem_RegistrarEntidades_Itensorc_Persistencia(i,nCodigoReexibicao,bRegistroNovo);
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
    public Long RegistraItem_RegistrarEntidades_Dadosorc_Persistencia(Date dDataAbertura,Integer nCodigoVendedor,String cNomeVendedor, Integer nCodigoCliente, String cNomeCliente, Integer nCodigoOperador, String cNomeOperador, Integer nLoja, Integer nCodigoOpCaixa, Integer nCodigoObjetoCaixa, String cControleCX, int nCodigoPDV )
    {
        Long nRetorno=Long.valueOf(0);
        try {

            getDadosorc().setCodigo(DAO_RepositorioLocal.NovoValorIdentidade("dadosorc", Sistema.getLojaAtual(),nCodigoPDV));
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
            getDadosorc().setFeito("N");
            getDadosorc().setPdv(nCodigoPDV);
            getDadosorc().setNaosinc(true);
            nRetorno= DadosorcRN.Dadosorc_Incluir(DAO_RepositorioLocal.getCnRepLocal(), dadosorc, 0,true,true);

        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return nRetorno;
    }

   private Double calcularDescontoSubtotalValor_RetPerc(double valorDescontoInformado){
        Double Ret = 0d;
        try {
            Double Subtotal=ItensorcRN.Itensorc_Somar(getDadosorc().getCodigo());
            
            Ret = NumeroArredondar.Arredondar_Double( valorDescontoInformado/Subtotal,2) * 100d;
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;        
    }
    
    private Double calcularDescontoSubtotalPerc_RetValor(){
        Double Ret = 0d;
        try {            
            
            Double Subtotal=ItensorcRN.Itensorc_Somar(getDadosorc().getCodigo());
                        
            Double percentualInformado = FormatarNumero.FormatarNumero_Double(txtDescontoPercentual.getText());
            
            Ret = NumeroArredondar.Arredondar_Double( Subtotal * (percentualInformado/100d),2);
            
            if( Ret >=Subtotal){
               Ret=-1d;
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;        
    }
    
    private void limparCamposDesconto(){
    
        
        try {
                txtDescontoPercentual.setText("0,00");
                txtDescontoValor.setText("0,00");               
                getDadosorc().setPercdesc(new BigDecimal(0d));
                getDadosorc().setDesconto(0d);                    
                Prevenda_UI_TotalizarVenda(getDadosorc().getCodigo());    
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
        
    }
    private void processarDescontoValor(){

        try {
            Double Subtotal=ItensorcRN.Itensorc_Somar(getDadosorc().getCodigo());
            
            if(Subtotal>0d){
            
                Double valorDescontoInformado = NumeroArredondar.ValidarDouble(txtDescontoValor.getText(),0d,Subtotal - 0.01d,-1d,"($)Desconto");
                if(valorDescontoInformado==-1d){
                    limparCamposDesconto();  
                    txtDescontoValor.requestFocus();                
                }else{
                     Double percDesconto = calcularDescontoSubtotalValor_RetPerc(valorDescontoInformado);
                     getDadosorc().setPercdesc(new BigDecimal(percDesconto));
                     getDadosorc().setDesconto(valorDescontoInformado); 
                     txtDescontoPercentual.setText( FormatarNumeros.FormatarParaMoeda(percDesconto));                    
                     txtDescontoValor.setText( FormatarNumeros.FormatarParaMoeda(valorDescontoInformado));  
                     Prevenda_UI_TotalizarVenda(getDadosorc().getCodigo());    
                     txtDescontoValorFocusGained(null);
                }

            }else{
                  limparCamposDesconto();
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }
    
    private void processarDescontoPercentual(){
        try {
            Double Subtotal = ItensorcRN.Itensorc_Somar(getDadosorc().getCodigo());

            if (Subtotal > 0d) {

                if (NumeroArredondar.ValidarDouble(txtDescontoPercentual.getText(), 0d, 99.99d, -1d, "(%)Desconto") == -1d) {
                    limparCamposDesconto();
                    txtDescontoPercentual.requestFocus();
                } else {
                    Double valorDesconto = calcularDescontoSubtotalPerc_RetValor();
                    if (valorDesconto == -1d) {
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O percentual informado não pode zerar o TOTAL", "% Desconto inválido");
                        limparCamposDesconto();
                        txtDescontoPercentual.requestFocus();
                    } else {

                        Double percentualInformado = FormatarNumero.FormatarNumero_Double(txtDescontoPercentual.getText());
                        getDadosorc().setPercdesc(new BigDecimal(percentualInformado));
                        getDadosorc().setDesconto(valorDesconto);
                        txtDescontoPercentual.setText(FormatarNumeros.FormatarParaMoeda(percentualInformado));
                        txtDescontoValor.setText(FormatarNumeros.FormatarParaMoeda(valorDesconto));
                        txtDescontoValor.requestFocus();
                    }
                }
             }else{
                  limparCamposDesconto();
             }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        Prevenda_UI_TotalizarVenda(getDadosorc().getCodigo());
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
            
            getDadosorc().setControleCx("");
            getDadosorc().setCodcaixa(0);
            getDadosorc().setObjetoCaixa(0);
            
            if(getDadosorc().getCodcliente()==null){
                getDadosorc().setCodcliente("0");
                getDadosorc().setCliente("** Consumidor **");
            }
            //getDadosorc().setCliente(txtNomeCliente.getText());
            //Double DescontoTotal=0d;
            Double DescontoNota =  TratamentoNulos.getTratarDouble().Tratar(getDadosorc().getDesconto(),0d); //Double.valueOf(TratamentoNulos.getTratarString().Tratar(  txtDescontoValor.getText(),"0").toString());
            //Double percDescontoTotal = Double.valueOf(TratamentoNulos.getTratarString().Tratar(  txtDescontoPercentual.getText(),"0").toString());            
            //Double CredTroca = Double.valueOf(TratamentoNulos.getTratarObject().Tratar(  txtValorCreditoTroca.getValue(),0d).toString());            
            //DescontoTotal = NumeroArredondar.Arredondar_Double(DescontoNota,2);
            //getDadosorc().setDesconto(DescontoTotal);
            //getDadosorc().setPercdesc(new BigDecimal( percDescontoTotal.doubleValue()));
            getDadosorc().setAcrescimopercentual(0d);
            //Double Acrescimo = Double.valueOf( txtAcrescimo.getValue()==null ? "0" : txtAcrescimo.getValue().toString() );
            getDadosorc().setAcrescimo(0d );           
            //txtValorVenda.commitEdit();
            Double ValorVendaBruta = ItensorcRN.Itensorc_Somar(getDadosorc().getCodigo());
            getDadosorc().setLoja(Sistema.getLojaAtual());
            BigDecimal nTotalBruto = new BigDecimal( ValorVendaBruta );
            nTotalBruto.setScale(2, RoundingMode.HALF_EVEN );
            getDadosorc().setTotalbruto(nTotalBruto);
            
            Double vendaLiquida = NumeroArredondar.Arredondar_Double(ValorVendaBruta - DescontoNota,2);
            getDadosorc().setValor( new BigDecimal(vendaLiquida));
            
            
            getDadosorc().setFilial(Sistema.getFilial());
            getDadosorc().setVendaCondicional(false);
            
            
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

private boolean gravarPrevenda(){
    if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Confirma a gravação da pré-venda ?", "Finalizar Pré-venda")==MetodosUI_Auxiliares_1.Sim()){                        
       return gravarPrevenda_acao();
    }else{
       return false;     
    }    
}    
private boolean gravarPrevenda_acao(){
    boolean Ret = false;
    try {
        
        
        
        boolean SetarValoresDadosOrc = SetarValoresDadosOrc();
        
        if(SetarValoresDadosOrc){
           Ret = Prevenda.PreVenda_Efetivar(getDadosorc(), pdvgerenciar.CodigoPDV(), Sistema.isOnline());
           if(Ret){
             if(!IniciarVenda()){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar uma a pré-venda", "Pré-Venda", "AVISO");
             }else{
                 MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("Pré-venda" + (getDadosorc().getCodigocotacao()!=null ? " [ "+  getDadosorc().getCodigocotacao() + "-" + getDadosorc().getCodigoorcamento() + "]" : "")   + " gravada com sucesso", "Pré-venda OK");
             }
           }else{
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível gravar a pré-venda (pe)", "Operação não realizada");
           }
        }else{
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível gravar a pré-venda (svd)", "Operação não realizada");
        }
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
    return Ret;
}
  
//private Thread TarefaPrincipal;
private boolean IniciarTarefa(){
    boolean Ret = false;
    try {        
         new Thread(new Runnable() {
            @Override
            public void run() {
               while(true){                   
                   try {                        
                        atualizaRelogio();
                   
                        Thread.sleep(1000);
                   } catch (Exception e) {
                       LogDinnamus.Log(e, true);
                   }
                   
               }
            }
        }, "TarefaPrincipalPrevenda" ).start();        
        
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        return false;
    }
    return true;
}
private void atualizaRelogio(){
   
    try {
        
        lblRelogio.setText((new SimpleDateFormat("HH:mm:ss")).format(new Date()));
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
    }
     
    

}

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelCliente;
    private javax.swing.JPanel PainelCodigoPrevenda;
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelData;
    private javax.swing.JPanel PainelGrid;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JPanel PainelTotal;
    private javax.swing.JPanel PainelVendedor;
    private javax.swing.JTextField SEQUENCIA;
    private javax.swing.JButton btDeletarItem;
    private javax.swing.JButton btFechar1;
    private javax.swing.JButton btGravarPrevenda;
    private javax.swing.JButton btNotaPrevenda;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btPrevenda_LocalizarCliente;
    private javax.swing.JComboBox cbPrevenda_Vendedor;
    private br.com.ui.JTableDinnamuS dbgPrevenda_Itens;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblRelogio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotal1;
    private javax.swing.JLabel lblTotal2;
    private javax.swing.JLabel lblTotal3;
    private javax.swing.JTextField txtDescontoPercentual;
    private javax.swing.JTextField txtDescontoValor;
    private javax.swing.JTextField txtIndice;
    private javax.swing.JTextField txtOperador6;
    private javax.swing.JTextField txtOperador8;
    private javax.swing.JTextField txtPrevenda_Codigo;
    private javax.swing.JTextField txtPrevenda_CodigoVendedor;
    private com.toedter.calendar.JDateChooser txtPrevenda_Data;
    private javax.swing.JTextField txtPrevenda_NomeCliente;
    private javax.swing.JTextField txtQTTotal;
    private javax.swing.JTextField txtSequencia;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalLiquido;
    // End of variables declaration//GEN-END:variables
}
