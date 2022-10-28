/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.Venda;

import br.String.ManipulacaoString;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.data.ManipularData;
import br.valor.formatar.FormatarNumero;
import com.toedter.calendar.JDateChooser;
import MetodosDeNegocio.Seguranca.UsuarioAuditar;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.Troca;
import MetodosDeNegocio.Venda.pdvgerenciar;
import UI.Seguranca.ValidarAcessoAoProcesso;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Fernando
 */
public class frmPDV_SimplesTrocaListagem extends javax.swing.JDialog {

    /**
     * Creates new form frmPDV_SimplesTrocaListagem
     */
    private boolean IniciarUI =false;
    public frmPDV_SimplesTrocaListagem(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();        
        this.IniciarUI=IniciarUI();
    }
    private boolean IniciarDBG_Troca(){
        try {
            
            dbgTrocasRelizadas.setRsDados(Troca.TrocasRealizadasPorPeriodo(txtDataInicial.getDate(),txtDataFinal.getDate()));
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private boolean IniciarUI(){
        try {
           MetodosUI_Auxiliares_1.MaximizarJanelaDeDialogo(this);
           setLocationRelativeTo(null);            
           
           txtDataInicial.setDate(ManipularData.DataAtual());
           txtDataFinal.setDate(ManipularData.DataAtual());
           
            /*if(!Sistema.isOnline()){
                  MetodosUI_Auxiliares.MensagemAoUsuarioSimplesAVISO(null, "SISTEMA OFFLINE - TROCA NÃO DISPONIVEL", lblNomeModulo.getText());
                  return false;
           }*/
           dbgTrocasRelizadas.addClColunas("id", "ID",10,true);
           dbgTrocasRelizadas.addClColunas("data", "DATA",20,true);
           dbgTrocasRelizadas.addClColunas("idcliente", "IDCLIENTE",20,true);           
           dbgTrocasRelizadas.addClColunas("nomecliente", "NOME",100,true);
           dbgTrocasRelizadas.addClColunas("codigovendacreditada", "COD.VDA.CRED",20,true);
           dbgTrocasRelizadas.addClColunas("status", "STATUS",20,true);
           dbgTrocasRelizadas.addClColunas("estoque", "ESTOQUE",20,true);
           dbgTrocasRelizadas.addDateFormat("data");
           dbgTrocasRelizadas.getjTable().setColumnSelectionAllowed(false);
           dbgTrocasRelizadas.getjTable().setRowSelectionAllowed(true);
           //dbgTrocasRelizadas.getjTable().setDefaultRenderer(Object.class, new frmPDV_SimplesTrocaListagemRender());
           dbgTrocasRelizadas.getjTable().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {  
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,  
                        boolean isSelected, boolean hasFocus, int row, int column) {                      
                    Component c= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                      
                    try {     
                        if(!isSelected){                            
                          c.setBackground(null);                            
                          c.setForeground(null);
                        }else{                  
                            c.setForeground(Color.WHITE);
                            c.setBackground(Color.BLACK);
                        }
                        String coluna = table.getModel().getColumnName(column);                        
                        if(coluna.equalsIgnoreCase("status") ){
                            String Situacao = value.toString();//TratamentoNulos.getTratarString().Tratar(c.getString("status"),"");
                            if (Situacao.equalsIgnoreCase("CANCELADO")) {                                                                                                                            
                                c.setBackground(Color.RED);  
                                c.setForeground(Color.WHITE);                                                      
                            }
                        }  
                    } catch (Exception e) {
                        LogDinnamus.Log(e, true);
                    }
                    setValue(value);
                    return this;  
                }  
            });
           IniciarDBG_Troca();            
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
        PainelTitulo = new javax.swing.JPanel();
        btFechar2 = new javax.swing.JButton();
        lblNomeModulo = new javax.swing.JLabel();
        PainelPesquisa = new javax.swing.JPanel();
        txtDataInicial = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        txtDataFinal = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        btPesquisar = new javax.swing.JButton();
        cbCampoBusca = new javax.swing.JComboBox();
        txtCampoLocalizar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PainelCorpo = new javax.swing.JPanel();
        dbgTrocasRelizadas = new br.com.ui.JTableDinnamuS();
        PainelBotoes = new javax.swing.JPanel();
        btCancelarTroca = new javax.swing.JButton();
        btDigitarTroca1 = new javax.swing.JButton();
        btConsultarTRroca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setBackground(new java.awt.Color(255, 255, 204));
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelTitulo.setBackground(new java.awt.Color(51, 51, 51));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());

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
        PainelTitulo.add(btFechar2, gridBagConstraints);

        lblNomeModulo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblNomeModulo.setForeground(new java.awt.Color(255, 255, 255));
        lblNomeModulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNomeModulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/TROCA.png"))); // NOI18N
        lblNomeModulo.setText("PDV - TROCAS E DEVOLUÇÕES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        PainelTitulo.add(lblNomeModulo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelTitulo, gridBagConstraints);

        PainelPesquisa.setBackground(new java.awt.Color(255, 255, 255));
        PainelPesquisa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelPesquisa.setLayout(new java.awt.GridBagLayout());

        txtDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        txtDataInicial.getDateEditor().getUiComponent().addKeyListener(

            new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        //txtDataFinal.requestFocus();
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}

            }
        );
        txtDataInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataInicialFocusGained(evt);
            }
        });
        txtDataInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataInicialKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisa.add(txtDataInicial, gridBagConstraints);

        txtDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        txtDataInicial.getDateEditor().getUiComponent().addKeyListener(

            new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        //SwingUtilidade.RequestFocus(txtValorParcela);
                        // btPesquisar.requestFocus();
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}

            }
        );
        txtDataFinal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataFinalFocusGained(evt);
            }
        });
        txtDataFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataFinalKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisa.add(txtDataFinal, gridBagConstraints);

        btPesquisar.setBackground(new java.awt.Color(255, 255, 255));
        btPesquisar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btPesquisar.setText("Filtrar");
        btPesquisar.setToolTipText("");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        PainelPesquisa.add(btPesquisar, gridBagConstraints);

        cbCampoBusca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbCampoBusca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID_CLIENTE", "NOME_CLIENTE", "VENDA_CREDITADA" }));
        cbCampoBusca.setBorder(null);
        cbCampoBusca.setMinimumSize(new java.awt.Dimension(131, 15));
        cbCampoBusca.setPreferredSize(new java.awt.Dimension(131, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelPesquisa.add(cbCampoBusca, gridBagConstraints);

        txtCampoLocalizar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCampoLocalizar.setOpaque(false);
        txtCampoLocalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCampoLocalizarActionPerformed(evt);
            }
        });
        txtCampoLocalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCampoLocalizarKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPesquisa.add(txtCampoLocalizar, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("LOCALIZAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        PainelPesquisa.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        PainelPesquisa.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("FILTRAR DE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        PainelPesquisa.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("ATÉ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        PainelPesquisa.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelPesquisa, gridBagConstraints);

        PainelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        PainelCorpo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TROCAS REALIZADAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        dbgTrocasRelizadas.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 851;
        gridBagConstraints.ipady = 136;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(14, 14, 0, 14);
        PainelCorpo.add(dbgTrocasRelizadas, gridBagConstraints);

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));
        PainelBotoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        btCancelarTroca.setBackground(new java.awt.Color(0, 0, 0));
        btCancelarTroca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btCancelarTroca.setForeground(new java.awt.Color(255, 255, 255));
        btCancelarTroca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/delete.png"))); // NOI18N
        btCancelarTroca.setText("Cancelar Troca");
        btCancelarTroca.setBorderPainted(false);
        btCancelarTroca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarTrocaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelBotoes.add(btCancelarTroca, gridBagConstraints);

        btDigitarTroca1.setBackground(new java.awt.Color(0, 0, 0));
        btDigitarTroca1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btDigitarTroca1.setForeground(new java.awt.Color(255, 255, 255));
        btDigitarTroca1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/calculator_edit.png"))); // NOI18N
        btDigitarTroca1.setText("Digitar Troca");
        btDigitarTroca1.setBorderPainted(false);
        btDigitarTroca1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDigitarTroca1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelBotoes.add(btDigitarTroca1, gridBagConstraints);

        btConsultarTRroca.setBackground(new java.awt.Color(0, 0, 0));
        btConsultarTRroca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btConsultarTRroca.setForeground(new java.awt.Color(255, 255, 255));
        btConsultarTRroca.setText("Consultar Troca");
        btConsultarTRroca.setBorderPainted(false);
        btConsultarTRroca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConsultarTRrocaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelBotoes.add(btConsultarTRroca, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(11, 16, 17, 16);
        PainelCorpo.add(PainelBotoes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        PainelPrincipal.add(PainelCorpo, gridBagConstraints);

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

    private void btFechar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar2ActionPerformed
        try {
            //ParametrosGlobais.setPreVenda_Codigo(0l);
            dispose();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btFechar2ActionPerformed

    private void btFechar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFechar2KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_btFechar2KeyPressed

    private void txtDataInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataInicialFocusGained

    private void txtDataInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataInicialKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDataInicialKeyPressed

    private void txtDataFinalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataFinalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataFinalFocusGained

    private void txtDataFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataFinalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataFinalKeyPressed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        // TODO add your handling code here:
        try {

            if(txtDataInicial.getDate()==null){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "DATA INICIAL INVÁLIDA", "PESQUISAR PRÉ-VENDA");
                return;
            }

            if(txtDataFinal.getDate()==null){
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "DATA FINAL INVÁLIDA", "PESQUISAR PRÉ-VENDA");
                return;
            }

            //IniciarUI_DbgPrevenda_Atualizar();
             dbgTrocasRelizadas.setRsDados(Troca.TrocasRealizadasPorPeriodo(txtDataInicial.getDate(),txtDataFinal.getDate()));
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btPesquisarActionPerformed
    private Long TrocaSelecionada(){
        Long CodigoTrocaAtual =0l;
        try {
            ResultSet rs = dbgTrocasRelizadas.getTbDinnamuS().getRs();
            if(rs.getRow()>0){                
               int LinhaAtual = dbgTrocasRelizadas.getjTable().getSelectedRow();
               if(LinhaAtual>=0){
                  rs.absolute(LinhaAtual+1);
                  CodigoTrocaAtual= rs.getLong("id");               
               }
            }            
        } catch (Exception e) {
            LogDinnamus.Log(e, true); 
        }
        return CodigoTrocaAtual;
    }
    private void btCancelarTrocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarTrocaActionPerformed
            // TODO add your handling code here:
        try {            
               Long CodigoTroca = TrocaSelecionada();
               if(CodigoTroca>0l){
                    String Status = dbgTrocasRelizadas.getTbDinnamuS().getValorCelulaString("status");
                    //String Status = dbgTrocasRelizadas.getTbDinnamuS().getValorCelulaString("status");
                    Long CodigoVendaCreditada = dbgTrocasRelizadas.getTbDinnamuS().getValorCelulaLong("codigovendacreditada");
                    
                    if(Status.equalsIgnoreCase("cancelado")){
                       MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "TROCA JÁ ESTA CANCELADA", lblNomeModulo.getText());
                       return;
                    }   
                    if(CodigoVendaCreditada>0l){
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "JÁ EXIISTE UMA VENDA VINCULADA A ESTA TROCA\n\nREALIZE O EXTORNO DA VENDA E A TROCA SERA CANCELADA", "CANCELAR TROCA");
                        return ;
                    }   
                    if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "CONFIRMA O CANCELAMENTO DA TROCA ?", lblNomeModulo.getText())==MetodosUI_Auxiliares_1.Sim()){
                       int Usuario =ValidarAcessoAoProcesso.Verificar( null , UsuarioSistema.getIdUsuarioLogado(), "CancelarTroca", Sistema.getLojaAtual(), true,"CANCELAMENTO DE TROCA");
                       if(Usuario>0){
                            if(Troca.CancelarTroca(Sistema.getLojaAtual(), CodigoTroca,Sistema.CodigoDaFilial_LojaAtual(), pdvgerenciar.CodigoPDV())) {
                               UsuarioAuditar.Auditar(Usuario, "TROCA", "CANCELAMENTO DA TROCA COD: " + CodigoTroca);
                               IniciarDBG_Troca();
                               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "CANCELAMENTO OK", lblNomeModulo.getText());
                            }else{
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "NÃO FOI POSSIVEL REALIZAR O CANCELAMENTO", lblNomeModulo.getText());
                            }
                       }else{
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "CANCELAMENTO NÃO AUTORIZADO", lblNomeModulo.getText());
                       }
                    }
               }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true); 
        }
        
    }//GEN-LAST:event_btCancelarTrocaActionPerformed

    private void btDigitarTroca1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDigitarTroca1ActionPerformed
        // TODO add your handling code here:
        try {
            new frmPDV_SimplesTroca(null, true, Sistema.getFilial(),0l,0l,false,0d,false,false).setVisible(true);
            IniciarDBG_Troca();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }//GEN-LAST:event_btDigitarTroca1ActionPerformed

    private void btConsultarTRrocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConsultarTRrocaActionPerformed
        // TODO add your handling code here:
       Long CodigoTroca = TrocaSelecionada();
       if(CodigoTroca>0l){
         new frmPDV_SimplesTroca(null, true, Sistema.getFilial(),CodigoTroca,0l,true,0d,false,false).setVisible(true);
         IniciarDBG_Troca();
         
       }
    }//GEN-LAST:event_btConsultarTRrocaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if(!IniciarUI){
           dispose();
        }
    }//GEN-LAST:event_formWindowOpened

    private void txtCampoLocalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoLocalizarKeyPressed
        // TODO add your handling code here:
        try {
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
               if(txtCampoLocalizar.getText().length() >0){
                   String TextoLocalizar=txtCampoLocalizar.getText();
                   if(cbCampoBusca.getSelectedItem().toString().equalsIgnoreCase("VENDA_CREDITADA")){
                       if(FormatarNumero.FormatarNumeroLong(TextoLocalizar)==null){
                           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "INFORMAÇÃO DIGITADA INVÁLIDA", "LOCALIZAR TROCA");
                           return;
                       }
                   }    
                   dbgTrocasRelizadas.setRsDados(Troca.TrocasRealizadasPorPeriodo(null, null, cbCampoBusca.getSelectedItem().toString(), TextoLocalizar));
               } 
            }
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_txtCampoLocalizarKeyPressed

    private void txtCampoLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCampoLocalizarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCampoLocalizarActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelPesquisa;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JButton btCancelarTroca;
    private javax.swing.JButton btConsultarTRroca;
    private javax.swing.JButton btDigitarTroca1;
    private javax.swing.JButton btFechar2;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JComboBox cbCampoBusca;
    private br.com.ui.JTableDinnamuS dbgTrocasRelizadas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblNomeModulo;
    private javax.swing.JTextField txtCampoLocalizar;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDataInicial;
    // End of variables declaration//GEN-END:variables
}
