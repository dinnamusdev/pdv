/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario;

import MetodosDeNegocio.Venda.pdvgerenciar;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import br.ui.teclas.DefinirAtalhos2;
import com.nfce.config.NFCE_Configurar;
import com.nfce.config.NFCE_Contingencia;
import dinnamus.ui.InteracaoUsuario.nfce.frmNFCE_Listagem;
import dinnamus.ui.InteracaoUsuario.nfce.frmNFCe_Config_Pdv_Historico_Contingencia;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 *
 * @author Fernando
 */
public class frmNFCe_Avisos extends javax.swing.JDialog {

    /**
     * Creates new form frmNFCe_Avisos
     */
    private boolean CarregouOK=false;
    public frmNFCe_Avisos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        CarregouOK=IniciarUI();
        
               
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
        btBotao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblMSG = new javax.swing.JTextPane();
        PainelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btFechar1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        chkDesativar = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 320));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(500, 320));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setBackground(new java.awt.Color(0, 0, 0));
        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        btBotao.setBackground(new java.awt.Color(0, 0, 0));
        btBotao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btBotao.setForeground(new java.awt.Color(255, 255, 255));
        btBotao.setText("jButton1");
        btBotao.setBorderPainted(false);
        btBotao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBotaoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        PainelPrincipal.add(btBotao, gridBagConstraints);

        lblMSG.setEditable(false);
        lblMSG.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMSG.setText("MSG NFCE");
        jScrollPane1.setViewportView(lblMSG);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        PainelPrincipal.add(jScrollPane1, gridBagConstraints);

        PainelTitulo.setBackground(new java.awt.Color(255, 255, 204));
        PainelTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logo-nfce-xs.png"))); // NOI18N
        lblTitulo.setText("AVISOS");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        PainelTitulo.add(lblTitulo, gridBagConstraints);

        btFechar1.setBackground(new java.awt.Color(255, 255, 204));
        btFechar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelTitulo, gridBagConstraints);

        chkDesativar.setForeground(new java.awt.Color(255, 255, 255));
        chkDesativar.setText("Desativar Alerta");
        chkDesativar.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        PainelPrincipal.add(chkDesativar, gridBagConstraints);

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

    private void btBotaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBotaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btBotaoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if(!CarregouOK){
            this.dispose();
        }
    }//GEN-LAST:event_formWindowOpened
    private void  Funcoes(String Tecla){
        
        try {
            if(Tecla.equalsIgnoreCase("ESCAPE")){
                btFechar1ActionPerformed(null);
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
            String Teclas[] ={"ESCAPE"};            
            DefinirAtalhos2.Definir(PainelPrincipal, Teclas, TeclaAtalhos);            
           
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    } 
    private void btFechar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar1ActionPerformed
        // TODO add your handling code here:

        if(!btFechar1.isEnabled()){ return;}
        this.dispose();
    }//GEN-LAST:event_btFechar1ActionPerformed
    private boolean IniciarUI(){
        boolean Ret = false;
        try {
            if(!VerificarNFCe()){ return false;}
            IniciarUI_TeclaAtalho();
            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    
    private boolean VerificarNFCe(){
        try {
            
           
            int PDV = pdvgerenciar.CodigoPDV();
            int Loja = Sistema.getLojaAtual();
            
            
            boolean Ret = NFCE_Contingencia.VerificarNotasEmContigencia(PDV, Sistema.getLojaAtual());
            String cMSG = NFCE_Contingencia.getMSGNotasEmContingencias();
            if (cMSG.length() > 0) {
                final boolean ContingenciaOK = NFCE_Contingencia.Contingencia(PDV);
                //if (!(ContingenciaOK ? "CONTING??NCIA ATIVADA" : "ENVIO ATIVADO").equalsIgnoreCase(lblICone.getText())) {
                   // MetodosUI_Auxiliares.Centralizar_InternalFrame(PainelMsg);
                    //PainelMsg.setLocation(WIDTH, WIDTH);
                    //PainelMsg.setVisible(true);
                    Icon ico = null;
                    if (ContingenciaOK) {
                        ico = new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logo-nfc-offline.png"));
                        lblTitulo.setText("CONTING??NCIA ATIVADA");
                    } else {
                        ico = new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logo-nfce-xs.png"));
                        lblTitulo.setText("ENVIO ATIVADO");
                    }
                    lblTitulo.setIcon(ico);

                //}
                lblMSG.setText(NFCE_Contingencia.getMSGNotasEmContingencias());
                lblTitulo.setText("NFCE - Aviso de CONTING??NCIA");
               
                ActionListener act = null; 
                if (ContingenciaOK) {
                    
                    btBotao.setText("Desativar Conting??ncia");
                    act = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {                             
                                 new frmNFCe_Config_Pdv_Historico_Contingencia(null, true).setVisible(true);                             
                                 dispose();
                        }
                    };
                }else{
                         btBotao.setText("Resolver Agora");          
                        act= new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {                          
                                 new frmNFCE_Listagem(null, true).setVisible(true);
                                 dispose();
                        }
                    };
                }
                
                int tam = btBotao.getActionListeners().length ;
                if (tam > 0) {                
                    btBotao.removeActionListener( btBotao.getActionListeners()[0]);                    
                }
                 btBotao.addActionListener(act);
            } else {
                //PainelMsg.setVisible(false);
            }
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
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JButton btBotao;
    private javax.swing.JButton btFechar1;
    public javax.swing.JCheckBox chkDesativar;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane lblMSG;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
