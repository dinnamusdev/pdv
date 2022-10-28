/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfce;

import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.pdvgerenciar;
import UI.Seguranca.ValidarAcessoAoProcesso;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;
import com.nfce.config.NFCE_Configurar;
import com.nfce.config.NFCE_Contingencia;
import com.nfce.consultar.NFCE_ConsultarStatus;
import com.nfce.envio.NFCe_ConfiguracaoAmbiente;
import dinnamus.metodosnegocio.licencas.Licenca;
import java.sql.ResultSet;
import javax.swing.ImageIcon;

/**
 *
 * @author Fernando
 */
public class frmNFCe_Config_Pdv_Historico_Contingencia extends javax.swing.JDialog {

    /**
     * Creates new form frmNFCe_Config_Pdv_Historico_Contingencia
     */
    
    private boolean CarregouOK=false;
    public frmNFCe_Config_Pdv_Historico_Contingencia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        CarregouOK=IniciarUI();
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

        PainelPrincipal = new javax.swing.JPanel();
        PainelTitulo = new javax.swing.JPanel();
        btFechar1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        PainelInternoGe = new javax.swing.JLabel();
        PainelCorpo = new javax.swing.JPanel();
        PainelBotoes = new javax.swing.JPanel();
        btAtualizarDadosPDV = new javax.swing.JButton();
        PainelInterno = new javax.swing.JPanel();
        dbgHistorico = new br.com.ui.JTableDinnamuS();
        PainelContingencia = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chkContigencia = new javax.swing.JCheckBox();
        lblIcone = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelPrincipal.setLayout(new java.awt.GridBagLayout());

        PainelTitulo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        PainelTitulo.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelTitulo.add(btFechar1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        PainelTitulo.add(jLabel5, gridBagConstraints);

        PainelInternoGe.setBackground(new java.awt.Color(0, 0, 0));
        PainelInternoGe.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        PainelInternoGe.setForeground(new java.awt.Color(255, 255, 255));
        PainelInternoGe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/InteracaoUsuario/nfce/logo-nfce-xs.png"))); // NOI18N
        PainelInternoGe.setText("Contingência NFCe");
        PainelInternoGe.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 4, 0, 0);
        PainelTitulo.add(PainelInternoGe, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelPrincipal.add(PainelTitulo, gridBagConstraints);

        PainelCorpo.setLayout(new java.awt.GridBagLayout());

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));
        PainelBotoes.setLayout(new java.awt.GridBagLayout());

        btAtualizarDadosPDV.setBackground(new java.awt.Color(0, 0, 0));
        btAtualizarDadosPDV.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAtualizarDadosPDV.setForeground(new java.awt.Color(255, 255, 255));
        btAtualizarDadosPDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Yes.png"))); // NOI18N
        btAtualizarDadosPDV.setMnemonic('G');
        btAtualizarDadosPDV.setText("Gravar");
        btAtualizarDadosPDV.setBorderPainted(false);
        btAtualizarDadosPDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizarDadosPDVActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        PainelBotoes.add(btAtualizarDadosPDV, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        PainelCorpo.add(PainelBotoes, gridBagConstraints);

        PainelInterno.setBorder(javax.swing.BorderFactory.createTitledBorder("Histórico de Contingências"));
        PainelInterno.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.4;
        PainelInterno.add(dbgHistorico, gridBagConstraints);

        PainelContingencia.setBackground(new java.awt.Color(255, 255, 204));
        PainelContingencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PainelContingencia.setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("STATUS DA CONTIGÊNCIA");
        jLabel1.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        PainelContingencia.add(jLabel1, gridBagConstraints);

        chkContigencia.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chkContigencia.setText("contigência desativada");
        chkContigencia.setOpaque(false);
        chkContigencia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkContigenciaItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        PainelContingencia.add(chkContigencia, gridBagConstraints);

        lblIcone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.2;
        PainelContingencia.add(lblIcone, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        PainelInterno.add(PainelContingencia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.2;
        PainelCorpo.add(PainelInterno, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.2;
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

    private void btFechar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar1ActionPerformed
        // TODO add your handling code here:
         
                
        this.dispose();
    }//GEN-LAST:event_btFechar1ActionPerformed

    private void btAtualizarDadosPDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizarDadosPDVActionPerformed
        // TODO add your handling code here:
        try {
            GravarContingencia();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btAtualizarDadosPDVActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if(!CarregouOK){this.dispose();}
    }//GEN-LAST:event_formWindowOpened

    private void chkContigenciaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkContigenciaItemStateChanged
        // TODO add your handling code here:
        AtualizarStatus();
    }//GEN-LAST:event_chkContigenciaItemStateChanged
    private boolean GravarContingencia_Acao(){
        boolean Ret = false;
        try {
            
            boolean Contingencia=false;

            ResultSet rs = NFCE_Configurar.ListarConfiguracaoPDV_PorPDV(pdvgerenciar.CodigoPDV(),NFCE_Configurar.NFCE_MODELO);
            if(rs.next()){
               Contingencia = rs.getBoolean("contigencia");
            }
            if(Contingencia!=chkContigencia.isSelected()){
               if(chkContigencia.isSelected()){
                   if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Confirma a entrada da emissão em contingência ?", "Entrar em Contingência")==MetodosUI_Auxiliares_1.Sim()){
                       if(NFCE_Contingencia.EntrarEmContingencia("Manual", Sistema.getLojaAtual(),pdvgerenciar.CodigoPDV())){
                           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("O NFCe está com a contingência ativada", "NFCe em ContingÊncia");
                           AtualizarUI_Dbg();
                           Ret = true;
                       }else{
                           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível ativar a contingência", "Falha na ativação da ContingÊncia");
                       }
                   }
               }else{
                 if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao("Confirma a saída da emissão em contingência ?", "Saída de Contingência")==MetodosUI_Auxiliares_1.Sim()){
                     
                      if(NFCe_ConfiguracaoAmbiente.getFachadaNFe()==null){
                         boolean RetConfigNFCe = NFCe_ConfiguracaoAmbiente.Configurar(Sistema.getLojaAtual(), pdvgerenciar.CodigoPDV(), Licenca.DataServidor());
                      }
                      String MSG_Retorno = (new NFCE_ConsultarStatus()).Consultar_NFCe(true, pdvgerenciar.CodigoPDV());
                        if(MSG_Retorno.length()==0){
                             MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("Não foi possível VERIFICAR OS SERVIDORES DA SEFAZ\n\nO SISTEMA NÃO PODERÁ SAIR DA CONTINGÊNCIA", "NFC-e sem comunicação");
                             return false;
                        }
                       if(NFCE_Contingencia.SaidaDaContingencia("Manual", Sistema.getLojaAtual(),pdvgerenciar.CodigoPDV())){
                           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO("A contingência da NFCe está desativada", "ContingÊncia NFCe DESATIVADA");
                           AtualizarUI_Dbg();
                           Ret = true;
                       }else{
                           MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Não foi possível desativar a contingência", "Falha na desativação da ContingÊncia");
                       }
                   }
               }
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private void GravarContingencia(){
        boolean Ret = false;
        try {
            
            dbgHistorico.getjTable().setEnabled(false);
            MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal, false);
            
            
            new Thread(){
                public void run(){
                    GravarContingencia_Acao(); 
                    MetodosUI_Auxiliares_1.BloquearDesbloquearComponentes(PainelPrincipal, true);                
                    dbgHistorico.getjTable().setEnabled(true);
                }
            }.start();
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
    }
    private void AtualizarStatus(){
        
        try {
            if(chkContigencia.isSelected()){
                chkContigencia.setText("CONTIGÊNCIA ATIVADA");
                ImageIcon img =  new javax.swing.ImageIcon(getClass().getResource("/com/nfce/config/logo-nfc-offline.png"));//  (ImageIcon)lblLogoCarregando.getIcon(); 
                lblIcone.setIcon(img);
            }else{
                chkContigencia.setText("CONTIGÊNCIA DESATIVADA".toLowerCase());
                lblIcone.setIcon(null);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
         
    }
    private boolean IniciarUI_Dbg(){
        boolean Ret = false;
        try {
            
            ResultSet rs = NFCE_Contingencia.HistoricoContigencia(pdvgerenciar.CodigoPDV());
            dbgHistorico.addClColunas("entrada", "Hora da Entrada", 130);
            dbgHistorico.addClColunas("tipodeentrada", "Tipo de Entrada", 100);
            dbgHistorico.addClColunas("saida", "Hora da Saída", 130);
            dbgHistorico.addClColunas("tipodesaida", "Tipo de Saída", 100);
            dbgHistorico.getTbDinnamuS().setModeloUsandoColecao(true);
            dbgHistorico.addDateFormat("entrada",dbgHistorico.DateFormat_ddmmyy_hhmmss);
            dbgHistorico.addDateFormat("saida",dbgHistorico.DateFormat_ddmmyy_hhmmss);
            Ret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean AtualizarUI_Dbg(){
        boolean Ret = false;
        try {
            
            ResultSet rs = NFCE_Contingencia.HistoricoContigencia(pdvgerenciar.CodigoPDV());
            dbgHistorico.setRsDados(rs);
            
            Ret=true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    private boolean IniciarUI(){
        boolean Ret = false;
        try {
            /*
            Integer nCodigoUsuario = ValidarAcessoAoProcesso.Verificar(null, UsuarioSistema.getIdUsuarioLogado(), "PDV_HISTCONT", Sistema.getLojaAtual(), true, "Histórico de contingência");
            if (nCodigoUsuario == 0) {
                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("Usuário não possui permissão de acesso a este módulo", "Acesso não autorizado");
                return false;
            }*/
            if(!IniciarUI_Dbg()) {return false;} 
            
            AtualizarUI_Dbg();
            
            boolean Contingencia=false;

            ResultSet rs = NFCE_Configurar.ListarConfiguracaoPDV_PorPDV(pdvgerenciar.CodigoPDV(),NFCE_Configurar.NFCE_MODELO);
            if(rs.next()){
               Contingencia = rs.getBoolean("contigencia");
            }
            chkContigencia.setSelected(Contingencia);
            
            AtualizarStatus();
            
            Ret = true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    
    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelContingencia;
    private javax.swing.JPanel PainelCorpo;
    private javax.swing.JPanel PainelInterno;
    private javax.swing.JLabel PainelInternoGe;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel PainelTitulo;
    private javax.swing.JButton btAtualizarDadosPDV;
    private javax.swing.JButton btFechar1;
    private javax.swing.JCheckBox chkContigencia;
    private br.com.ui.JTableDinnamuS dbgHistorico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblIcone;
    // End of variables declaration//GEN-END:variables
}
