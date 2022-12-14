/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.Venda;

import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.ui.teclas.TeclasDeAtalho;
import dinnamus.metodosnegocio.publicidade.Publicidade;
import dinnamus.ui.InteracaoUsuario.publicidade.frmPublicidade;
import br.com.ui.ImagemTratamento;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Fernando
 */
public class frmPDVSimples_StatusPDV extends javax.swing.JDialog {

    /**
     * Creates new form frmPDVSimples_Efetivacao
     */
    private boolean Executa = true;    
    public String TeclasLidas="";
    private Long HostEmAtividade = 0l;
    private Thread TarefaPublicidade =null;
    public frmPDVSimples_StatusPDV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();           
        Dimension screenSize = tk.getScreenSize();  
        this.setSize(screenSize.width, screenSize.height); 
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblMsgStatusTitulo = new javax.swing.JLabel();
        lblMsgStatus = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblEmpresa = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblMsgRodape = new javax.swing.JLabel();
        PainelLogoApp = new javax.swing.JPanel();
        logoApp = new javax.swing.JLabel();
        btFechar2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel2MouseMoved(evt);
            }
        });
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel5.setOpaque(false);

        lblMsgStatusTitulo.setBackground(new java.awt.Color(255, 255, 204));
        lblMsgStatusTitulo.setFont(new java.awt.Font("Arial", 1, 72)); // NOI18N
        lblMsgStatusTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMsgStatusTitulo.setText("CAIXA LIVRE");

        lblMsgStatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMsgStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMsgStatus.setText("OBRIGADO PELA PREFER??NCIA");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lblMsgStatusTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lblMsgStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lblMsgStatusTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMsgStatus)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 434;
        gridBagConstraints.ipady = 53;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblEmpresa.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpresa.setText("OBRIGADO PELA PREFER??NCIA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblEmpresa)
                .addGap(45, 45, 45))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 107;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblMsgRodape.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblMsgRodape.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMsgRodape.setText(" ");

        PainelLogoApp.setBackground(new java.awt.Color(255, 255, 255));

        logoApp.setBackground(new java.awt.Color(255, 255, 255));
        logoApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/DinnamuS LOGO 3.jpg"))); // NOI18N

        btFechar2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btFechar2.setMnemonic('x');
        btFechar2.setText("x");
        btFechar2.setToolTipText("");
        btFechar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFechar2ActionPerformed(evt);
            }
        });
        btFechar2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btFechar2FocusLost(evt);
            }
        });
        btFechar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btFechar2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PainelLogoAppLayout = new javax.swing.GroupLayout(PainelLogoApp);
        PainelLogoApp.setLayout(PainelLogoAppLayout);
        PainelLogoAppLayout.setHorizontalGroup(
            PainelLogoAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelLogoAppLayout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(logoApp, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btFechar2))
        );
        PainelLogoAppLayout.setVerticalGroup(
            PainelLogoAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelLogoAppLayout.createSequentialGroup()
                .addGroup(PainelLogoAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelLogoAppLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btFechar2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelLogoAppLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logoApp, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMsgRodape, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(PainelLogoApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PainelLogoApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblMsgRodape, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 489;
        gridBagConstraints.ipady = -65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 7, 12);
        jPanel1.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private boolean TerminarThreadPublicidade(){
        try {
            
            Executa=false; 
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void btFechar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar2ActionPerformed
        TerminarThreadPublicidade();
        dispose();
    }//GEN-LAST:event_btFechar2ActionPerformed

    private void btFechar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFechar2KeyPressed
        // TODO add your handling code here:
        try {
            
            if(evt.getKeyChar()=='\n'){
                //MetodosUI_Auxiliares.MensagemAoUsuarioSimplesINFO(null, TeclasLidas,  "TeclaLidas");
                btFechar2ActionPerformed(null);
            }else{
                //Character Letra = new Character(evt.getKeyChar());
               HostEmAtividade = System.currentTimeMillis();
               if(Character.isLetterOrDigit(evt.getKeyChar())){                   
                 TeclasLidas+= new String( ((Character)evt.getKeyChar()).toString() );                 
               }                
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }

    }//GEN-LAST:event_btFechar2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if(!IniciarUI()){
            dispose();
        }
        
    }//GEN-LAST:event_formWindowOpened
    private boolean AcionarPublicidade(){
        try {
            TeclasLidas="";
            frmPublicidade publicidade = new frmPublicidade(null, true);
            publicidade.setVisible(true);
            TeclasLidas = frmPublicidade.TeclasLidas;
            btFechar2ActionPerformed(null);
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void btFechar2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btFechar2FocusLost
           // TODO add your handling code here:
        btFechar2.requestFocus();
    }//GEN-LAST:event_btFechar2FocusLost

    private void jPanel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseMoved
        // TODO add your handling code here:
        
           // btFechar2ActionPerformed(null);
    }//GEN-LAST:event_jPanel2MouseMoved
    private boolean IniciarUI_TeclaAtalho(){
        try {
            Action[] acoes = new Action[1];   
            acoes[0]= new  AbstractAction() {
                @Override  
                public void actionPerformed(ActionEvent e) {
                        TeclasLidas="";
                        btFechar2ActionPerformed(null);
                }
            };                        
            String[] Teclas = new String[2];            
            Teclas[0] = "ESCAPE";            
           
            TeclasDeAtalho.DefinirFuncao_A_Tecla(jPanel1, acoes, Teclas);        
            
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean IniciarUI(){
        try {
            
            
            /*if (!IniciarLogoLoja()){
                return false;
            }*/
            if(!IniciarUI_TeclaAtalho()) {
                return false;
            }
            
            lblEmpresa.setText(Sistema.getDadosLojaAtualSistema(false).getString("nome"));
            
            TeclasLidas="";
            btFechar2.requestFocus();
            HostEmAtividade = System.currentTimeMillis();
            
            //IniciarThreadPublicidade();
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    public boolean IniciarThreadPublicidade(){
        try {
            
            TarefaPublicidade = new Thread("IniciarThreadPublicidade"){
                        public void run() {
                                //System.out.println("INICIANDO: IniciarThreadPublicidade");
              
                                while(Executa){                                    
                                    //System.out.println("EXECUTANDO: IniciarThreadPublicidade");
                                    if(System.currentTimeMillis()-HostEmAtividade>10000){
                                        try {
                                            if(Publicidade.ListarDeImagens().next()){
                                               AcionarPublicidade();
                                            }
                                        } catch (SQLException ex) {
                                            LogDinnamus.Log(ex, true);
                                        }
                                       Executa=false;
                                    }else{
                                        try {
                                            sleep(100);
                                        } catch (InterruptedException ex) {
                                            LogDinnamus.Log(ex, false);
                                        }
                                    }
                                }
                                //System.out.println("TERMINANDO: IniciarThreadPublicidade");
                        
                        }
            };
            TarefaPublicidade.start();
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
    private javax.swing.JPanel PainelLogoApp;
    private javax.swing.JButton btFechar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JLabel lblEmpresa;
    public javax.swing.JLabel lblMsgRodape;
    public javax.swing.JLabel lblMsgStatus;
    public javax.swing.JLabel lblMsgStatusTitulo;
    private javax.swing.JLabel logoApp;
    // End of variables declaration//GEN-END:variables
}
