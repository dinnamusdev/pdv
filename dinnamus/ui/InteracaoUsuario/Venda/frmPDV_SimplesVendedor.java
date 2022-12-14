/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.Venda;

import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.ui.teclas.DefinirAtalhos;
import br.ui.teclas.DefinirAtalhos2;
import MetodosDeNegocio.Venda.Venda;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;

/**
 *
 * @author Fernando
 */
public class frmPDV_SimplesVendedor extends javax.swing.JDialog {

    /**
     * Creates new form frmPDV_SimplesVendedor
     */
    public Long VendedorCodigo =0l;
    public frmPDV_SimplesVendedor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        IniciarUI();
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
        PainelTabela = new javax.swing.JPanel();
        dbgVendedor = new br.com.ui.JTableDinnamuS();
        PainelBotoes = new javax.swing.JPanel();
        btFechar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setBackground(new java.awt.Color(255, 255, 204));
        PainelPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelTabela.setBackground(new java.awt.Color(255, 255, 255));
        PainelTabela.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelTabela.setLayout(new java.awt.GridBagLayout());

        dbgVendedor.setExibirBarra(false);
        dbgVendedor.setMaximumSize(new java.awt.Dimension(200, 380));
        dbgVendedor.setMinimumSize(new java.awt.Dimension(200, 380));
        dbgVendedor.setPreferredSize(new java.awt.Dimension(200, 380));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 5, 3);
        PainelTabela.add(dbgVendedor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelPrincipal.add(PainelTabela, gridBagConstraints);

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));

        btFechar.setBackground(new java.awt.Color(0, 0, 0));
        btFechar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
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

        lblTitulo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Boss.png"))); // NOI18N
        lblTitulo.setText("VENDEDOR");

        javax.swing.GroupLayout PainelBotoesLayout = new javax.swing.GroupLayout(PainelBotoes);
        PainelBotoes.setLayout(PainelBotoesLayout);
        PainelBotoesLayout.setHorizontalGroup(
            PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelBotoesLayout.createSequentialGroup()
                .addContainerGap(253, Short.MAX_VALUE)
                .addComponent(btFechar)
                .addContainerGap())
            .addGroup(PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelBotoesLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        PainelBotoesLayout.setVerticalGroup(
            PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btFechar, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelBotoesLayout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(lblTitulo)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelBotoes, gridBagConstraints);

        btOK.setBackground(new java.awt.Color(0, 0, 0));
        btOK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btOK.setForeground(new java.awt.Color(255, 255, 255));
        btOK.setText("OK");
        btOK.setBorderPainted(false);
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 5, 0);
        PainelPrincipal.add(btOK, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelPrincipal, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        
        dispose();

    }//GEN-LAST:event_btFecharActionPerformed
    
    private void btFecharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFecharKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyChar()=='\n'){
            btFecharActionPerformed(null);
        }
    }//GEN-LAST:event_btFecharKeyPressed

    private void btOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKActionPerformed
        // TODO add your handling code here:
        try {
            if(dbgVendedor.getjTable().getSelectedRow()>=0){
                int Indice = dbgVendedor.getjTable().getSelectedRow();
                VendedorCodigo=dbgVendedor.getTbDinnamuS().getValorCelulaLong("codigo", Indice);
                btFecharActionPerformed(null);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btOKActionPerformed
    private boolean IniciarUI(){
        try {
            setLocationRelativeTo(null);
            if(!IniciarUI_DBG()){ return false;}
            
            if(!TeclasAtalhos_UI()){return false;}
                        
            dbgVendedor.getjTable().requestFocus();
           
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    } 
   private boolean TeclasAtalhos_UI(){
        try {  
             AbstractAction TeclaAtalhos  = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                        String Fonte = e.getSource().toString();                
                        if(Fonte.equalsIgnoreCase("ESCAPE")){
                            btFecharActionPerformed(e);
                        }
                }
            };            
            DefinirAtalhos2.Definir(PainelBotoes, new String[]{"ESCAPE"}, TeclaAtalhos);
            //DefinirAtalhos.Definir(PainelPrincipal, TeclaAtalhos);
            return true;
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
           return false;
       }
   }
  
   
   
    private boolean IniciarUI_DBG(){
        try {
            
            dbgVendedor.addClColunas("codigo", "CODIGO", 70,true);
            dbgVendedor.addClColunas("nome", "NOME", 220,true);
            dbgVendedor.setRsDados(Venda.Vendedor(0l, Sistema.getLojaAtual()));
            dbgVendedor.getjTable().addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        btOKActionPerformed(null);
                    }
                }
                public void keyReleased(KeyEvent e) {}
            });
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelTabela;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btOK;
    private br.com.ui.JTableDinnamuS dbgVendedor;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
