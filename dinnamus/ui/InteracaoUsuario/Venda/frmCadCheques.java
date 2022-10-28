/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmCadCheques.java
 *
 * Created on 11/09/2010, 19:42:18
 */

package dinnamus.ui.InteracaoUsuario.Venda;

import br.com.log.LogDinnamus;
import MetodosDeNegocio.Entidades.Chequesorcamento;
import MetodosDeNegocio.Entidades.Dadosorc;
import br.valor.formatar.FormatarNumero;
import br.TratamentoNulo.TratamentoNulos;
import br.ui.teclas.controleteclas;
import MetodosDeNegocio.Fachada.ChequesOrcamentoFachada;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.ui.teclas.TeclasDeAtalho;
import java.awt.event.ActionEvent;
//import br.com.ui.controleteclas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dti
 */
public class frmCadCheques extends javax.swing.JDialog {

    /** Creates new form frmCadCheques */
    private Dadosorc d;
    private String Controle;
    private Integer CodigoPDV;
    private String Orc;
    private Float ValorFormaPagamento=0f;
    //private List<Chequesorcamento> cheques=new ArrayList<>();
    public frmCadCheques(java.awt.Frame parent, boolean modal,Dadosorc d, String cControle,Integer nCodigoPDV, Float nValorFormaPagamento) {
        super(parent, modal);
        initComponents();
        setD(d);
        setControle(cControle);
        setCodigoPDV(nCodigoPDV);
        setValorFormaPagamento(nValorFormaPagamento);
        if(!InicializarUI(d, cControle, nCodigoPDV)){            
            this.dispose();
        }else{
            dbgCheques.getjTable().setRowSelectionInterval(0, 0);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
           
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

        PainelPrincipal = new javax.swing.JPanel();
        PainelTitulo = new javax.swing.JPanel();
        btFechar = new javax.swing.JButton();
        lblNomeForma1 = new javax.swing.JLabel();
        PainelCorpo = new javax.swing.JPanel();
        painelDados = new javax.swing.JPanel();
        txtVencimento = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        txtValor = new br.com.ui.JMoneyFieldDinnamus();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAgencia = new javax.swing.JTextField();
        txtConta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtBanco = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCPFTitular = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTitularCheque = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtChaveID = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNumeroCheque = new javax.swing.JTextField();
        btNovo = new javax.swing.JButton();
        dbgCheques = new br.com.ui.JTableDinnamuS();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cadastro de Cheques");
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(480, 480));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setBackground(new java.awt.Color(255, 255, 204));
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTitulo.setToolTipText("");

        btFechar.setBackground(new java.awt.Color(0, 0, 0));
        btFechar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btFechar.setForeground(new java.awt.Color(255, 255, 255));
        btFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btFechar.setMnemonic('x');
        btFechar.setText("Fechar");
        btFechar.setToolTipText("");
        btFechar.setBorderPainted(false);
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });
        btFechar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btFecharKeyPressed(evt);
            }
        });

        lblNomeForma1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblNomeForma1.setForeground(new java.awt.Color(255, 255, 255));
        lblNomeForma1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNomeForma1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/cheque-icon-9059.png"))); // NOI18N
        lblNomeForma1.setText("PDV - EDITAR CHEQUES");

        javax.swing.GroupLayout PainelTituloLayout = new javax.swing.GroupLayout(PainelTitulo);
        PainelTitulo.setLayout(PainelTituloLayout);
        PainelTituloLayout.setHorizontalGroup(
            PainelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelTituloLayout.createSequentialGroup()
                .addContainerGap(388, Short.MAX_VALUE)
                .addComponent(btFechar)
                .addContainerGap())
            .addGroup(PainelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelTituloLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblNomeForma1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE)))
        );
        PainelTituloLayout.setVerticalGroup(
            PainelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btFechar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(PainelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelTituloLayout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(lblNomeForma1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelTitulo, gridBagConstraints);

        PainelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        painelDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        painelDados.setOpaque(false);
        painelDados.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelDados.add(txtVencimento, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Vencimento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelDados.add(txtValor, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Agencia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelDados.add(txtAgencia, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelDados.add(txtConta, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Conta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelDados.add(txtBanco, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Banco");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelDados.add(txtCPFTitular, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("CPF Titular");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Titular");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        painelDados.add(txtTitularCheque, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("ChaveID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel8, gridBagConstraints);

        txtChaveID.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(txtChaveID, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Nro Cheque");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelDados.add(jLabel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        painelDados.add(txtNumeroCheque, gridBagConstraints);

        btNovo.setBackground(new java.awt.Color(0, 0, 0));
        btNovo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btNovo.setForeground(new java.awt.Color(255, 255, 255));
        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png"))); // NOI18N
        btNovo.setMnemonic('G');
        btNovo.setText("Gravar");
        btNovo.setBorderPainted(false);
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 26;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        painelDados.add(btNovo, gridBagConstraints);

        dbgCheques.setExibirBarra(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        painelDados.add(dbgCheques, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Dados do Cheques");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        painelDados.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelCorpo.add(painelDados, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        PainelPrincipal.add(PainelCorpo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelPrincipal, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        // TODO add your handling code here:
        try {
            
            if(!DataHora.IsDateValid("dd/MM/yyyy", txtVencimento.getDate())){
              MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Data de vencimento inválida", "Cadastro de Cheques");
              return ;
            }
            
            Chequesorcamento chequesorcamento = new Chequesorcamento();
            chequesorcamento.setChaveid( Integer.valueOf(txtChaveID.getText()));
            chequesorcamento.setDepositar(txtVencimento.getDate());
            chequesorcamento.setValor(txtValor.getValue().doubleValue());
            chequesorcamento.setBanco(txtBanco.getText());
            chequesorcamento.setNumerocheque(txtNumeroCheque.getText());
            chequesorcamento.setAgencia1(txtAgencia.getText());
            chequesorcamento.setTitular(txtTitularCheque.getText());
            chequesorcamento.setConta(txtConta.getText());
            chequesorcamento.setCpftitular(txtCPFTitular.getText());
            
           
            
            if(!ChequesOrcamentoFachada.AlterarCheques(chequesorcamento)){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível alterar o cheque", "Cheque não Alterado", "AVISO");
            }else{
               int nLinhaAtual =dbgCheques.getjTable().getSelectedRow();
               int nTotalLinhas = dbgCheques.getjTable().getRowCount();
               Atualizar_UI_dbgCheques(getD(),getControle(),getCodigoPDV());               
               
               if(nLinhaAtual+1<nTotalLinhas){
                  dbgCheques.getjTable().setRowSelectionInterval(nLinhaAtual+1, nLinhaAtual+1);
                  dbgCheques.getjTable().requestFocus();
               }else{
                   btFechar.requestFocus();
               }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
}//GEN-LAST:event_btNovoActionPerformed

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        if(VerificarCheques()){
           this.dispose();
        
        }

    }//GEN-LAST:event_btFecharActionPerformed

    private void btFecharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFecharKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyChar()=='\n'){
            btFecharActionPerformed(null);
        }
    }//GEN-LAST:event_btFecharKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
         dbgCheques.getjTable().requestFocus();
    }//GEN-LAST:event_formWindowOpened
    private boolean InicializarUI(Dadosorc d, String cControle,Integer nCodigoPDV){
        boolean bRet=false;
        try {
            controleteclas.UsarTeclaNaTrocaDeCampos(painelDados, KeyEvent.VK_ENTER);
            //controleteclas.SetarTodosOsBotoes(painelBotao);
            IniciarUI_TeclaAtalho();
            if(IniciarUI_DbgCadCheque()){
              if(Atualizar_UI_dbgCheques(d,cControle,nCodigoPDV)){
                 bRet=true;
              }
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private boolean VerificarCheques(){
        boolean bRet=false;
        try {
            Float nValorChequesInformados=ChequesOrcamentoFachada.TotalCheques(getD(), getCodigoPDV(), getControle());

            if(nValorChequesInformados.floatValue()!= getValorFormaPagamento().floatValue()){
              
                if (MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("O(s) valor(es) do(s) cheque(s) ["+ nValorChequesInformados.toString() +"] não confere com o valor da forma de pagto ["+ getValorFormaPagamento().toString() +"]\n\nDESEJA CORRIGIR ISSO ?", "Cadastro de Cheques") != MetodosUI_Auxiliares_1.Sim()) {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Todas as modificações feitas nos cheques serão desfeitas", "Cadastro de Cheques");
                    DAO_RepositorioLocal.RollBack_Statment();
                    dispose();
                }                 
            }else{
                bRet=ChequesOrcamentoFachada.AtualizarValorDasParcelas(getD(), getCodigoPDV(), getControle());                

            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private boolean Atualizar_UI_dbgCheques(Dadosorc d,String cControle, Integer nCodigoPDV){
        boolean bRet=false;
        try {

            dbgCheques.setRsDados(ChequesOrcamentoFachada.Listar(d,nCodigoPDV,cControle));
            dbgCheques.update(dbgCheques.getGraphics());
            bRet=true;

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;

    }
    private void LimparCampos(){
        try {
            txtAgencia.setText("");
            txtBanco.setText("");
            txtAgencia.setText("");
            txtConta.setText("");
            txtCPFTitular.setText("");
            txtTitularCheque.setText("");

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
    }
    private boolean PreencherCamposComResultSet(Integer nChaveID){
        boolean bRet=false;
        try {
            if(dbgCheques.getjTable().getRowCount()>0){
                //
                ResultSet rs = ChequesOrcamentoFachada.Listar(nChaveID);
                
                if(rs.next()){
                    txtChaveID.setText(String.valueOf(rs.getInt("chaveid")));
                    txtVencimento.setDate(rs.getDate("depositar"));
                    txtValor.setValue(rs.getBigDecimal("valor"));
                    txtBanco.setText(TratamentoNulos.getTratarString().Tratar(rs.getString("banco"),""));
                    txtAgencia.setText(TratamentoNulos.getTratarString().Tratar(rs.getString("agencia1"),""));
                    txtConta.setText(TratamentoNulos.getTratarString().Tratar(rs.getString("banco"),""));
                    txtCPFTitular.setText(TratamentoNulos.getTratarString().Tratar(rs.getString("cpftitular"),""));
                    txtTitularCheque.setText(TratamentoNulos.getTratarString().Tratar(rs.getString("titular"),""));
                }else{
                    LimparCampos();
                }

           }
        } catch (SQLException ex) {
           LogDinnamus.Log(ex);
        }
        return bRet;
    }
    public boolean IniciarUI_DbgCadCheque(){
    boolean bRet=false;
    try {

        dbgCheques.FormatoDataAdicionar("DATA", new SimpleDateFormat("dd/MM/yyyy"));
        dbgCheques.FormatoNumericoAdicionar("VALOR", FormatarNumero.getNf().getCurrencyInstance());
        dbgCheques.AlinhamentosAdicionar("VALOR", SwingConstants.RIGHT);        
        dbgCheques.addClColunas("chaveid" , "ID", 80, true);
        dbgCheques.addClColunas("data" , "DATA", 80, true);
        dbgCheques.addClColunas("valor" , "VALOR", 80, true);
        dbgCheques.addClColunas("cpf" , "CPF", 120, true);
        dbgCheques.addClColunas("titular" , "TITULAR", 200, true);
        dbgCheques.getjTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            int[] nLinha = dbgCheques.getjTable().getSelectedRows();
            if(nLinha.length>0){                
                Integer nChaveID = Integer.valueOf( dbgCheques.getjTable().getModel().getValueAt(nLinha[0], 0).toString());
                PreencherCamposComResultSet(nChaveID);
            }
        }
        });
        dbgCheques.getjTable().addKeyListener(new KeyListener() {

                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    e.consume();
                }

                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    if(e.getKeyCode()==e.VK_ENTER){
                        txtVencimento.getDateEditor().getUiComponent().requestFocus();
                        e.consume();
                    }
                }

                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    e.consume();
                }
            });
        bRet=true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }
    return bRet;
    }
    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btNovo;
    private br.com.ui.JTableDinnamuS dbgCheques;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblNomeForma1;
    private javax.swing.JPanel painelDados;
    private javax.swing.JTextField txtAgencia;
    private javax.swing.JTextField txtBanco;
    private javax.swing.JTextField txtCPFTitular;
    private javax.swing.JTextField txtChaveID;
    private javax.swing.JTextField txtConta;
    private javax.swing.JTextField txtNumeroCheque;
    private javax.swing.JTextField txtTitularCheque;
    private br.com.ui.JMoneyFieldDinnamus txtValor;
    private com.toedter.calendar.JDateChooser txtVencimento;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the Controle
     */
    public String getControle() {
        return Controle;
    }

    /**
     * @param Controle the Controle to set
     */
    public void setControle(String Controle) {
        this.Controle = Controle;
    }

    /**
     * @return the CodigoPDV
     */
    public Integer getCodigoPDV() {
        return CodigoPDV;
    }

    /**
     * @return the d
     */
    public Dadosorc getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(Dadosorc d) {
        this.d = d;
    }

    /**
     * @param CodigoPDV the CodigoPDV to set
     */
    public void setCodigoPDV(Integer CodigoPDV) {
        this.CodigoPDV = CodigoPDV;
    }

    /**
     * @return the ValorFormaPagamento
     */
    public Float getValorFormaPagamento() {
        return ValorFormaPagamento;
    }

    /**
     * @param ValorFormaPagamento the ValorFormaPagamento to set
     */
    public void setValorFormaPagamento(Float ValorFormaPagamento) {
        this.ValorFormaPagamento = ValorFormaPagamento;
    }
     private boolean IniciarUI_TeclaAtalho(){
        try {
            Action[] acoes = new Action[1];   
            acoes[0]= new  AbstractAction() {
                @Override  
                public void actionPerformed(ActionEvent e) {
                        btFecharActionPerformed(null);
                }
            };                        
            String[] Teclas = new String[1];            
            Teclas[0] = "ESCAPE";            
            TeclasDeAtalho.DefinirFuncao_A_Tecla(PainelPrincipal, acoes, Teclas);        
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

}
