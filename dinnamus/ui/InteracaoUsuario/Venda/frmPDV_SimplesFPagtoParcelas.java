/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.Venda;

import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.ui.teclas.TeclasDeAtalho;
import com.toedter.calendar.JDateChooser;
import MetodosDeNegocio.Entidades.Dadosorc;
import br.data.DataHora;
import br.valor.formatar.FormatarNumero;
import br.arredondar.NumeroArredondar;
import MetodosDeNegocio.Venda.PagtoorcRN;
import MetodosDeNegocio.Venda.ParcorcRN;
import br.com.FormatarNumeros;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.com.ui.SwingUtilidade;
import br.ui.teclas.controleteclas;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.Savepoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;


/**
 *
 * @author Fernando
 */
public class frmPDV_SimplesFPagtoParcelas extends javax.swing.JDialog {

    /**
     * Creates new form frmPDV_SimplesFPagtoParcelas
     */
    private Savepoint  svt = null;
    private Dadosorc dadosorc = new Dadosorc();
    private String ControleFormaPagto ="";
    private String DescricaoFormaPagto ="";
    private Float ValorForma =0f;
    public frmPDV_SimplesFPagtoParcelas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        PainelDadosParcela = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtValorParcela = new javax.swing.JFormattedTextField();
        txtVencimentoParcela = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
        txtParcela = new javax.swing.JTextField();
        btGravarParcela = new javax.swing.JButton();
        dbgParcelas = new br.com.ui.JTableDinnamuS();
        txtValorForma = new javax.swing.JFormattedTextField();
        lblNomeForma = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btFechar = new javax.swing.JButton();
        lblNomeForma1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        PainelDadosParcela.setBackground(new java.awt.Color(255, 255, 255));
        PainelDadosParcela.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da Parcela", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel7.setText("Parcela");

        jLabel8.setText("Data");

        jLabel9.setText("Valor");

        txtValorParcela.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.00"))));
        txtValorParcela.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorParcela.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValorParcela.setValue(new Float(0f));
        txtValorParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorParcelaActionPerformed(evt);
            }
        });
        txtValorParcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorParcelaFocusGained(evt);
            }
        });
        txtValorParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorParcelaKeyPressed(evt);
            }
        });

        txtVencimentoParcela.getDateEditor().getUiComponent().addKeyListener(

            new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        SwingUtilidade.RequestFocus(txtValorParcela);
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}

            }
        );
        txtVencimentoParcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVencimentoParcelaFocusGained(evt);
            }
        });
        txtVencimentoParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtVencimentoParcelaKeyPressed(evt);
            }
        });

        txtParcela.setEditable(false);
        txtParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtParcelaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtParcelaKeyReleased(evt);
            }
        });

        btGravarParcela.setText("OK");
        btGravarParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGravarParcelaActionPerformed(evt);
            }
        });

        dbgParcelas.setExibirBarra(false);
        dbgParcelas.getjTable().addKeyListener(

            new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        e.consume();
                        txtVencimentoParcela.getDateEditor().getUiComponent().requestFocus();
                    }else if(e.getKeyCode()==KeyEvent.VK_F2){
                        //Evento_F2();
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        e.consume();
                    }
                }

            }
        );

        javax.swing.GroupLayout PainelDadosParcelaLayout = new javax.swing.GroupLayout(PainelDadosParcela);
        PainelDadosParcela.setLayout(PainelDadosParcelaLayout);
        PainelDadosParcelaLayout.setHorizontalGroup(
            PainelDadosParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDadosParcelaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDadosParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDadosParcelaLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelDadosParcelaLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVencimentoParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelDadosParcelaLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btGravarParcela)))
                .addGap(18, 18, 18)
                .addComponent(dbgParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        PainelDadosParcelaLayout.setVerticalGroup(
            PainelDadosParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDadosParcelaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDadosParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PainelDadosParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtVencimentoParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PainelDadosParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValorParcela, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btGravarParcela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(PainelDadosParcelaLayout.createSequentialGroup()
                .addComponent(dbgParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtValorForma.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.00"))));
        txtValorForma.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorForma.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValorForma.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtValorForma.setValue(new Float(0f));
        txtValorForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorFormaActionPerformed(evt);
            }
        });
        txtValorForma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorFormaFocusGained(evt);
            }
        });
        txtValorForma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorFormaKeyPressed(evt);
            }
        });

        lblNomeForma.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblNomeForma.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNomeForma.setText("PARCELAS DA FORMA DE PAGTO");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PainelDadosParcela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(lblNomeForma, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorForma, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNomeForma))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PainelDadosParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

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
        lblNomeForma1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money.png"))); // NOI18N
        lblNomeForma1.setText("PDV - EDITAR PARCELAS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btFechar)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblNomeForma1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(76, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btFechar, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(lblNomeForma1)
                    .addContainerGap(14, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        if(FinalizarEdicao()){
            dispose();
        }
        
        
    }//GEN-LAST:event_btFecharActionPerformed

    private void btFecharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFecharKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyChar()=='\n'){
            btFecharActionPerformed(null);
        }
    }//GEN-LAST:event_btFecharKeyPressed

    private void txtValorParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorParcelaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorParcelaActionPerformed
    private boolean IniciarUI_FormasDePagto_Parcelas()
{
    boolean bRet=false;
    try {

        dbgParcelas.TamanhoColunas(new int[]{40,100,100,0});
        dbgParcelas.getjTable().getTableHeader().setResizingAllowed(false);
        dbgParcelas.getjTable().getTableHeader().setReorderingAllowed(false);
        HashMap<String, DateFormat> hashMapData = new HashMap<String, DateFormat>();
        hashMapData.put("DATA", (new SimpleDateFormat("dd/MM/yyyy")));
        dbgParcelas.setDateFormat(hashMapData);        
        FormasDePagto_Parcelas_AtualizarGrid((getDadosorc().getCodigo()==null ?  0 : getDadosorc().getCodigo()), getControleFormaPagto());
        bRet=true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
    }

    return bRet;
}
    private boolean FormasDePagto_Parcelas_AtualizarGrid(Long nCodigo, String Controle)
{
   
    try {
         //ResultSet rs = DAO_RepositorioLocal.
         ResultSet rs = DAO_RepositorioLocal.GerarResultSet("SELECT parcela AS Prc,Data,VALOR,IDUNICO FROM PARCORC WHERE CONTROLE='"+ Controle +"' AND CODIGO="+ nCodigo, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
         dbgParcelas.setRsDados(rs);
         dbgParcelas.getjTable().addFocusListener(new FocusListener() {

             public void focusGained(FocusEvent e) {
                 PreencheDadosForm();
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             public void focusLost(FocusEvent e) {
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }
         });
         dbgParcelas.getjTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                          PreencheDadosForm();
                }
            });
         //dbgParcelas.update(dbgParcelas.getGraphics());
         
         return true;
    } catch (Exception e) {
        LogDinnamus.Log(e);
        return false;
    }
   

}
     private boolean PreencheDadosForm(){
        boolean Ret = false;
        try {

            int nLinha = dbgParcelas.getjTable().getSelectedRow();
            TableModel tm = dbgParcelas.getjTable().getModel();
            if (nLinha >= 0) {
                Date Vencimento = dbgParcelas.getTbDinnamuS().getValorCelulaDate("data", nLinha);
                txtParcela.setText(dbgParcelas.getTbDinnamuS().getValorCelulaString("prc", nLinha));
                //String Data = tm.getValueAt(nLinha[0], 1).toString();
                txtVencimentoParcela.setDate(Vencimento);
                Double Valor = dbgParcelas.getTbDinnamuS().getValorCelulaDouble("valor", nLinha);
                txtValorParcela.setText(FormatarNumeros.FormatarParaMoeda(Valor));

            }

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private void txtValorParcelaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorParcelaFocusGained
        // TODO add your handling code here:
        SwingUtilidade.SelectAll(txtValorParcela);
    }//GEN-LAST:event_txtValorParcelaFocusGained

    private void txtValorParcelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorParcelaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btGravarParcela.requestFocus();
        }
    }//GEN-LAST:event_txtValorParcelaKeyPressed

    private void txtVencimentoParcelaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVencimentoParcelaFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txtVencimentoParcelaFocusGained

    private void txtVencimentoParcelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVencimentoParcelaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            SwingUtilidade.RequestFocus(txtValorParcela);
        }
    }//GEN-LAST:event_txtVencimentoParcelaKeyPressed

    private void txtParcelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParcelaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtParcelaKeyPressed

    private void txtParcelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParcelaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtParcelaKeyReleased

    private void btGravarParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGravarParcelaActionPerformed
        // TODO add your handling code here:
        try {
            Integer nParc =0;
            Date dDataVencimento =null;
            Float nValor =0f;

            if(txtParcela.getText().length()>0 && txtValorParcela.getText().length()>0){
                if(FormatarNumero.FormatarNumero(txtValorParcela.getText())!=Float.NaN){
                    if(DataHora.IsDateValid("dd/MM/yyyy", txtVencimentoParcela.getDate())){
                        nParc = Integer.valueOf(txtParcela.getText());
                        dDataVencimento = txtVencimentoParcela.getDate();
                        nValor = FormatarNumero.FormatarNumero(txtValorParcela.getText());
                        
                        if(!ParcorcRN.Parcorc_AlterarParcelas(getDadosorc().getCodigo(), nParc, dDataVencimento, nValor)){
                            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível alterar a parcela", "Alteração da Parcela", "AVISO");
                        }else{
                            FormasDePagto_Parcelas_AtualizarGrid((getDadosorc().getCodigo()==null ?  0 : getDadosorc().getCodigo()),getControleFormaPagto());
                            Integer nTotalParcelaFormas = ParcorcRN.Parcorc_Contar(getDadosorc().getCodigo());
                            dbgParcelas.getjTable().requestFocus();
                            if(nParc< nTotalParcelaFormas)
                            dbgParcelas.getjTable().setRowSelectionInterval(nParc,nParc);
                            else{
                                if(PagtoorcRN.Pagtorc_SomarValoresDinheiros(getDadosorc().getCodigo())==0f){
                                   // btEfetivar.requestFocus();
                                }else{
                                    //SwingUtilidade.RequestFocus(txtDinheiro);
                                }
                            }
                        }
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Data inválida", "Alterar Parcela", "AVISO");
                    }
                }else{
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Valor inválido", "Alterar Parcela", "AVISO");
                }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

    }//GEN-LAST:event_btGravarParcelaActionPerformed
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
            TeclasDeAtalho.DefinirFuncao_A_Tecla(jPanel2, acoes, Teclas);        
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    
    private boolean IniciarUI(){
        try {
            if(!IniciarUI_FormasDePagto_Parcelas()){
               return false;                 
            }            
            IniciarUI_TeclaAtalho();
            lblNomeForma.setText("FORMA DE PAGTO: [ "+ getDescricaoFormaPagto() + " ]");
            txtValorForma.setValue(getValorForma());
            dbgParcelas.getjTable().requestFocus();
            dbgParcelas.getjTable().setRowSelectionInterval(0, 0);
            controleteclas.SetarTodosOsBotoes(PainelDadosParcela);
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        try {
            
            if(!IniciarUI()){
               MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível iniciar a UI de parcelas", "PDV Parcelas", "AVISO");
               dispose();
            }
            //DAO_RepositorioLocal.getCnRepLocal().setAutoCommit(false);
            svt = DAO_RepositorioLocal.CriarPontoDeSalvamento("PDVFormasParcelas");
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_formWindowOpened

    private void txtValorFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorFormaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorFormaActionPerformed

    private void txtValorFormaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFormaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorFormaFocusGained

    private void txtValorFormaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorFormaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorFormaKeyPressed

    private boolean FinalizarEdicao(){
    boolean bRet =false;
        try {
            
                    
            Float ValorParcelas = NumeroArredondar.Arredondar2(ParcorcRN.Parcorc_Somar(getDadosorc().getCodigo(), 0, getControleFormaPagto()),2);
            //NumeroArredondar.Arredondar2(TOP_ALIGNMENT, ERROR);
            if( ValorParcelas.floatValue() != getValorForma().floatValue()){ 
               int nRet= br.com.ui.MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null,"O VALOR INFORMADO ("+  FormatarNumero.FormatarNumeroMoeda(ValorParcelas.toString())  +") NÃO CONFERE COM O VALOR DA FORMA DE PAGTO (" + FormatarNumero.FormatarNumeroMoeda(getValorForma().toString())  +")\n\nDESEJA CORRIGIR ISSO ?" , "PDV EDITAR PARCELAS");
               if(nRet==br.com.ui.MetodosUI_Auxiliares_1.Sim()){                   
                   bRet = false;
               }else{                 
                   DAO_RepositorioLocal.RollBack_Statment(svt);
                   bRet = true;
               }
            }else{
                DAO_RepositorioLocal.Commitar_Statment(svt);
                bRet = true;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            bRet = true;
        }
        //svt=null;
        return bRet;
    }
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelDadosParcela;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btGravarParcela;
    private br.com.ui.JTableDinnamuS dbgParcelas;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblNomeForma;
    private javax.swing.JLabel lblNomeForma1;
    private javax.swing.JTextField txtParcela;
    private javax.swing.JFormattedTextField txtValorForma;
    private javax.swing.JFormattedTextField txtValorParcela;
    private com.toedter.calendar.JDateChooser txtVencimentoParcela;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the dadosorc
     */
    public Dadosorc getDadosorc() {
        return dadosorc;
    }

    /**
     * @param dadosorc the dadosorc to set
     */
    public void setDadosorc(Dadosorc dadosorc) {
        this.dadosorc = dadosorc;
    }

    /**
     * @return the CodigoFormaPagto
     */
    public String getControleFormaPagto() {
        return ControleFormaPagto;
    }

    /**
     * @param CodigoFormaPagto the CodigoFormaPagto to set
     */
    public void setControleFormaPagto(String CodigoFormaPagto) {
        this.ControleFormaPagto = CodigoFormaPagto;
    }

    /**
     * @return the DescricaoFormaPagto
     */
    public String getDescricaoFormaPagto() {
        return DescricaoFormaPagto;
    }

    /**
     * @param DescricaoFormaPagto the DescricaoFormaPagto to set
     */
    public void setDescricaoFormaPagto(String DescricaoFormaPagto) {
        this.DescricaoFormaPagto = DescricaoFormaPagto;
    }

    /**
     * @return the ValorForma
     */
    public Float getValorForma() {
        return ValorForma;
    }

    /**
     * @param ValorForma the ValorForma to set
     */
    public void setValorForma(Float ValorForma) {
        this.ValorForma = ValorForma;
    }
}