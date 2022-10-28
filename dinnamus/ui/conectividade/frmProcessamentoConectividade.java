/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.conectividade;

import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author DSWM
 */
public class frmProcessamentoConectividade extends javax.swing.JDialog {

    /**
     * Creates new form frmApresentacao
     */
    
    public frmProcessamentoConectividade(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        btOK.setVisible(false);
    }
     public frmProcessamentoConectividade(java.awt.Dialog parent, boolean modal, boolean moda2l) {
        super(parent, modal);
        initComponents();
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

        PainelTitulo = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        PainelCorpo = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblMensagem = new javax.swing.JTextArea();
        PainelRodape = new javax.swing.JPanel();
        btOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        PainelTitulo.add(jLabel5, gridBagConstraints);

        jLabel4.setBackground(new java.awt.Color(255, 255, 204));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/conectividade/barra logo dinnamus.JPG"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.4;
        PainelTitulo.add(jLabel4, gridBagConstraints);

        lblTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Conectividade WEB");
        lblTitulo.setToolTipText("");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelTitulo.add(lblTitulo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelTitulo, gridBagConstraints);

        PainelCorpo.setBackground(new java.awt.Color(255, 255, 204));
        PainelCorpo.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        lblLogo.setBackground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/conectividade/loading.gif"))); // NOI18N
        lblLogo.setName(""); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        PainelCorpo.add(lblLogo, gridBagConstraints);

        jScrollPane1.setBorder(null);

        lblMensagem.setEditable(false);
        lblMensagem.setBackground(new java.awt.Color(255, 255, 204));
        lblMensagem.setColumns(20);
        lblMensagem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMensagem.setLineWrap(true);
        lblMensagem.setRows(4);
        lblMensagem.setToolTipText("");
        jScrollPane1.setViewportView(lblMensagem);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 11, 11);
        PainelCorpo.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.4;
        getContentPane().add(PainelCorpo, gridBagConstraints);

        PainelRodape.setBackground(new java.awt.Color(0, 0, 0));

        btOK.setBackground(new java.awt.Color(0, 0, 0));
        btOK.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btOK.setForeground(new java.awt.Color(255, 255, 255));
        btOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png"))); // NOI18N
        btOK.setMnemonic('G');
        btOK.setBorderPainted(false);
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });
        PainelRodape.add(btOK);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(PainelRodape, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKActionPerformed
        // TODO add your handling code here:
         dispose();
    
    }//GEN-LAST:event_btOKActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public void AtualizarMensagem(String msg){
        AtualizarMensagem(msg, false,false);
    }
    public void ocultarAnimacao(){
        lblLogo.setVisible(false);
    }
    public void AtualizarMensagem(String msg, boolean erro, boolean exibirBtn){
        lblMensagem.setText(msg);
        lblMensagem.getLineCount();
        if(erro){
            lblMensagem.setForeground(Color.RED);
        }else{
            lblMensagem.setForeground(Color.BLACK); 
        }
        if(exibirBtn){
            btOK.setVisible(true);
        }
     
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelRodape;
    private javax.swing.JPanel PainelTitulo;
    public javax.swing.JButton btOK;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblLogo;
    private javax.swing.JTextArea lblMensagem;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
