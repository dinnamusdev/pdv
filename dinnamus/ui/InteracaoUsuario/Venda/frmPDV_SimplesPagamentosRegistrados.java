/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.Venda;

import br.com.ecf.ECFDinnamuS;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.MetodosUI_Auxiliares_1;

import br.ui.teclas.TeclasDeAtalho;
import br.ui.teclas.controleteclas;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.PagtoorcRN;
import MetodosDeNegocio.Venda.Venda;
import MetodosDeNegocio.Venda.pdvgerenciar;
import MetodosDeNegocio.TEF.TEFVenda;
import UI.Seguranca.ValidarAcessoAoProcesso;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Fernando
 */
public class frmPDV_SimplesPagamentosRegistrados extends javax.swing.JDialog {

    /**
     * Creates new form frmPDV_SimplesPagamentosRegistrados
     */
    private boolean IniciarUI= false;
    private Dadosorc dadosorc =null;
    private ECFDinnamuS ecf = null;
    public frmPDV_SimplesPagamentosRegistrados(java.awt.Frame parent, boolean modal,Dadosorc dadosorc,ECFDinnamuS ecf) {
        super(parent, modal);
        initComponents();
        this.dadosorc = dadosorc;
        this.ecf=ecf;
        IniciarUI=IniciarUI();
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

        PainelFormasPagtoVenda = new javax.swing.JPanel();
        dbgFormasPagto = new br.com.ui.JTableDinnamuS();
        PainelTopo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btFechar5 = new javax.swing.JButton();
        PainelBotoes = new javax.swing.JPanel();
        btRemoverForma = new javax.swing.JButton();
        btEditarForma = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 300));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(420, 350));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        PainelFormasPagtoVenda.setBackground(new java.awt.Color(255, 255, 255));
        PainelFormasPagtoVenda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PainelFormasPagtoVenda.setLayout(new java.awt.GridBagLayout());

        dbgFormasPagto.setBackground(new java.awt.Color(255, 255, 255));
        dbgFormasPagto.setExibirBarra(false);
        dbgFormasPagto.setPreferredSize(new java.awt.Dimension(700, 251));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(13, 12, 13, 12);
        PainelFormasPagtoVenda.add(dbgFormasPagto, gridBagConstraints);

        PainelTopo.setBackground(new java.awt.Color(0, 0, 0));
        PainelTopo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setBackground(new java.awt.Color(0, 0, 0));
        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("PAGAMENTOS REGISTRADOS");
        lblTitulo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        PainelTopo.add(lblTitulo, gridBagConstraints);

        btFechar5.setBackground(new java.awt.Color(0, 0, 0));
        btFechar5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btFechar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/Shut down_16x16.png"))); // NOI18N
        btFechar5.setMnemonic('x');
        btFechar5.setToolTipText("");
        btFechar5.setBorderPainted(false);
        btFechar5.setOpaque(false);
        btFechar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFechar5ActionPerformed(evt);
            }
        });
        btFechar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btFechar5KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        PainelTopo.add(btFechar5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelFormasPagtoVenda.add(PainelTopo, gridBagConstraints);

        PainelBotoes.setBackground(new java.awt.Color(0, 0, 0));

        btRemoverForma.setBackground(new java.awt.Color(0, 0, 0));
        btRemoverForma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btRemoverForma.setForeground(new java.awt.Color(255, 255, 255));
        btRemoverForma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/money_delete.png"))); // NOI18N
        btRemoverForma.setMnemonic('r');
        btRemoverForma.setText("[DEL] Remover");
        btRemoverForma.setBorderPainted(false);
        btRemoverForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverFormaActionPerformed(evt);
            }
        });

        btEditarForma.setBackground(new java.awt.Color(0, 0, 0));
        btEditarForma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btEditarForma.setForeground(new java.awt.Color(255, 255, 255));
        btEditarForma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinnamus/ui/img/calculator_edit.png"))); // NOI18N
        btEditarForma.setMnemonic('e');
        btEditarForma.setText("[F2] Editar");
        btEditarForma.setBorderPainted(false);
        btEditarForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarFormaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelBotoesLayout = new javax.swing.GroupLayout(PainelBotoes);
        PainelBotoes.setLayout(PainelBotoesLayout);
        PainelBotoesLayout.setHorizontalGroup(
            PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
            .addGroup(PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelBotoesLayout.createSequentialGroup()
                    .addGap(0, 82, Short.MAX_VALUE)
                    .addComponent(btRemoverForma)
                    .addGap(67, 67, 67)
                    .addComponent(btEditarForma, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 82, Short.MAX_VALUE)))
        );
        PainelBotoesLayout.setVerticalGroup(
            PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
            .addGroup(PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PainelBotoesLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(PainelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btRemoverForma)
                        .addComponent(btEditarForma))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        PainelFormasPagtoVenda.add(PainelBotoes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(PainelFormasPagtoVenda, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private boolean IniciarUI(){
        try {
            
            if(!IniciarGridPagtos()) {return false;}
            
            if(!IniciarGridPagtos_Atualizar()) { return false;}

            if(!IniciarUI_TeclaAtalho()){return false;}
            
            dbgFormasPagto.getjTable().setRowSelectionInterval(0, 0);
            
            dbgFormasPagto.getjTable().requestFocus();
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
        
    private boolean IniciarGridPagtos_Atualizar(){
        try {
            int TotalRegistros=0;
            ResultSet rs = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo(),0l,"codforma,grupoforma,valor,tefstatus,idunico");
            if(rs.last()){
                TotalRegistros =rs.getRow();
                rs.first();
            }
            
            PainelFormasPagtoVenda.setVisible(TotalRegistros==0 ? false : true);            
            dbgFormasPagto.setVisible(TotalRegistros==0 ? false : true);
            dbgFormasPagto.setRsDados(rs);
            dbgFormasPagto.getjTable().addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {}
                public void keyPressed(KeyEvent e) {
                    try {
                        if(e.getKeyCode()==KeyEvent.VK_F2){
                            btEditarFormaActionPerformed(null);
                        }
                    } catch (Exception ex) {
                        LogDinnamus.Log(ex, true);
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
    private boolean IniciarGridPagtos(){
        try {
            
            dbgFormasPagto.addClColunas("codforma", "COD", 5, true);
            dbgFormasPagto.addClColunas("grupoforma", "NOME", 20, true);
            dbgFormasPagto.addClColunas("valor", "VALOR", 20, true,false,dbgFormasPagto.Alinhamento_Direita);            
            dbgFormasPagto.addClColunas("tefstatus", "TEF", 20, true);
            dbgFormasPagto.addNumberFormatMoeda("valor");            
            //dbgFormasPagto.getjTable().getTableHeader().setVisible(false);
            //dbgFormasPagto.getjTable().setBackground(Color.WHITE);
            //dbgFormasPagto.getjTable().setForeground(Color.BLACK);
            dbgFormasPagto.getjTable().setFont(new Font("Tahoma", Font.BOLD, 16));
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

        
    private void btFechar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFechar5ActionPerformed
        try {
          dispose();
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btFechar5ActionPerformed

    private void btFechar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btFechar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btFechar5KeyPressed
    private boolean RemoverPagto(){
        try {

            Object[] obj = dbgFormasPagto.TratarLinhaSelecionada(dbgFormasPagto.getjTable());
            if(obj!=null)
            {
                if(MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "CONFIRMA A EXCLUSÃO DA FORMA DE PAGAMENTO", null)!=MetodosUI_Auxiliares_1.Sim()){
                    return false;
                }
                Long nIdUnico = Long.parseLong(obj[4].toString());

                if(Venda.VendaEmFechamento()){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "A VENDA ESTÁ COM O CUPOM FISCAL FECHADO. FAVOR ENCERRAR A NOTA", "EXCLUSÃO NÃO PERMITIDA");
                    return false;
                }

                boolean bPermiteCancelar =true;
                ResultSet rsPagtoOrc = PagtoorcRN.PagtoOrc_Listar(getDadosorc().getCodigo(), nIdUnico);
                if(rsPagtoOrc.next()){
                    if(rsPagtoOrc.getString("tefstatus")!=null){
                        bPermiteCancelar =false;
                        int nRetorno= MetodosUI_Auxiliares_1.MensagemAoUsuarioOpcoes_Sim_e_Nao(null, "A Forma de Pagamento já esta Aprovada pela Administradora\n\nCONFIRMA ESTA OPERAÇÃO ?", "Exclusão da Forma Pagto");
                        if(nRetorno==MetodosUI_Auxiliares_1.Sim()){
                            int nCodigoUsuario =ValidarAcessoAoProcesso.Verificar(null , UsuarioSistema.getIdUsuarioLogado(), "ExcluirCotacao", Sistema.getLojaAtual(), true, "Exclusão de Forma Pagto");
                            if(nCodigoUsuario>0){
                               bPermiteCancelar = TEFVenda.DesfazerVendaTEF(nCodigoUsuario,nIdUnico,rsPagtoOrc.getString("tefcomandoatual"),getDadosorc().getCodigo(),getEcf());
                            }else{
                                MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesINFO(null, "OPERADOR NÃO AUTORIZADO", "Exclusão de Forma Pagto");
                            }
                        }
                    }
                }
                if(!bPermiteCancelar){
                    return false;
                }
                if(!PagtoorcRN.PagtoOrc_Excluir(getDadosorc().getCodigo(),nIdUnico,true)){
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimples(null, "Não foi possível excluir a forma de pagto", "Exclusão de Forma Pagto", "AVISO");
                }               
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return false;
    }
    private void btRemoverFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverFormaActionPerformed
        // TODO add your handling code here:
        
        new Thread("frmPDV_SimplesPagtoRegistrados_btRemoverFormaActionPerformed"){
            @Override
            public void run(){
                    RemoverPagto();
                
            }
        }.start();

    }//GEN-LAST:event_btRemoverFormaActionPerformed

    private void btEditarFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarFormaActionPerformed
        // TODO add your handling code here:
        try {
            int nPosGridFormasPagto = dbgFormasPagto.LinhaAtualModel();
            //int nPosGridOpcoesPagto = dbgOpcoesPagto.LinhaAtualModel();
            Object[] obj = dbgFormasPagto.TratarLinhaSelecionada(dbgFormasPagto.getjTable());
            if(obj!=null)
            {
                if(obj[0]==null){
                    return;
                }
                Long nIdUnico = Long.parseLong(obj[4].toString());
                ResultSet rsForma = PagtoorcRN.PagtoOrc_Listar( getDadosorc().getCodigo(), nIdUnico);
                if(rsForma.next()){
                    String Destino = rsForma.getString("destino");
                    Float Valor=rsForma.getFloat("valor");
                    if(Destino.equalsIgnoreCase("A Receber & Crediario")){
                       String IdUnicoPagto=rsForma.getString("idunico"),  GrupoForma=rsForma.getString("grupoforma");                      
                       FormEdicaoParcelas(IdUnicoPagto, GrupoForma, Valor);                               
                    }else  if(Destino.equalsIgnoreCase("Cheques Recebidos")){
                        String Controle = rsForma.getString("controle");
                        CadastroDeCheques(getDadosorc(),Valor,Controle);
                    }else{
                        MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "ESTE PAGAMENTO NÃO PERMITE EDIÇÃO", "EDITAR PAGTO");
                    }                    
                }
                dbgFormasPagto.getjTable().setRowSelectionInterval(nPosGridFormasPagto, nPosGridFormasPagto);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }//GEN-LAST:event_btEditarFormaActionPerformed
    private boolean CadastroDeCheques(Dadosorc d, Float nValor,String Controle){
        boolean bRet=false;
        //String cContole="";
        try {
            
            new frmCadCheques(null,true,d,Controle,pdvgerenciar.CodigoPDV(),nValor );

            bRet=true;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }
    private boolean FormEdicaoParcelas(String IdUnicoPagto, String GrupoForma, Float Valor){
        try {
            frmPDV_SimplesFPagtoParcelas frmParcelasFormas = new frmPDV_SimplesFPagtoParcelas(null, true);
            frmParcelasFormas.setDadosorc(getDadosorc());
            frmParcelasFormas.setControleFormaPagto(IdUnicoPagto);
            frmParcelasFormas.setDescricaoFormaPagto(GrupoForma);
            frmParcelasFormas.setValorForma(Valor);
            frmParcelasFormas.setVisible(true);
            
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if(!IniciarUI){
            MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO(null, "NÃO FOI POSSÍVEL INICIAR A INTERFACE COM O USUARIO", lblTitulo.getText());
            dispose();
        }
    }//GEN-LAST:event_formWindowOpened
   private Action ESCAPE=null,DELETE =null, F2 =null; 
   private boolean IniciarUI_TeclaAtalho(){            
         try {
            ESCAPE = new  AbstractAction() {
            @Override  
            public void actionPerformed(ActionEvent e) {TeclaAtalho_Acoes_2(KeyEvent.VK_ESCAPE);}};       
            DELETE = new  AbstractAction() {
            @Override  
            public void actionPerformed(ActionEvent e) {TeclaAtalho_Acoes_2(KeyEvent.VK_F1);}};                   
            F2 = new  AbstractAction() {
            @Override  
            public void actionPerformed(ActionEvent e) {TeclaAtalho_Acoes_2(KeyEvent.VK_F2);}};                   
            TeclasDeAtalho.DefinirFuncao_A_Tecla(PainelFormasPagtoVenda, ESCAPE, "ESCAPE"); 
            TeclasDeAtalho.DefinirFuncao_A_Tecla(PainelFormasPagtoVenda, DELETE, "DELETE"); 
            TeclasDeAtalho.DefinirFuncao_A_Tecla(PainelFormasPagtoVenda, F2, "F2");             
            controleteclas.SetarTodosOsBotoes(this.getContentPane());
            return true;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
         
    }
   public void TeclaAtalho_Acoes_2(int e){
       
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBotoes;
    private javax.swing.JPanel PainelFormasPagtoVenda;
    private javax.swing.JPanel PainelTopo;
    private javax.swing.JButton btEditarForma;
    private javax.swing.JButton btFechar5;
    private javax.swing.JButton btRemoverForma;
    private br.com.ui.JTableDinnamuS dbgFormasPagto;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

    

    /**
     * @return the ecf
     */
    public ECFDinnamuS getEcf() {
        return ecf;
    }

    /**
     * @param ecf the ecf to set
     */
    public void setEcf(ECFDinnamuS ecf) {
        this.ecf = ecf;
    }

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
}