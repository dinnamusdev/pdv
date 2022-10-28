/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfce.danfe;

import dinnamus.rel.RelatorioJasperXML;
import br.TratamentoNulo.TratamentoNulos;
import br.com.log.LogDinnamus;
import br.com.repositorio.DAO_RepositorioLocal;
import br.com.servidor.Dao_Jdbc_1;
import br.com.ui.ImagemTratamento;
import br.com.ui.InteracaoDuranteProcessamento;

import br.com.ui.MetodosUI_Auxiliares_1;
import br.data.DataHora;
import br.impressao.ESCPOSApi;
import br.impressao.Perifericos;
import br.manipulararquivos.ManipulacaoArquivo;
import br.manipulararquivos.ManipulacaoArquivo2;
import java.awt.Desktop;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.swing.Icon;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageable;

/**
 *
 * @author Fernando
 */
public class GerarDanfeNFCE {
    private String gerar_NfceDanfe(Long CodigoVenda, RelatorioJasperXML jasperNFce,  boolean sistema) {
        String Ret = "";
        try {
            ResultSet rsTmp = null;
            String sql =  "select l.logo, n1.dadosqrcode, n1.numeroprotocolo,n1.datahoraautorizacao,n2.xmlnfe,"
                    + "n_cfg.url_consulta_homologacao, n_cfg.url_consulta_producao,n1.ambiente "
                    + "from  off_nfce_pdv_notas n1, off_nfce_pdv_notas_cpl n2, off_nfce_pdv np, off_configuracaoestacao cfg, off_nfce_config n_cfg, "
                    + " lojas l "
                    + "where n1.id=n2.id_nfce_pdv_notas and n1.codigovenda=" + CodigoVenda + " and "
                    + " n1.id_nfce_pdv=np.id and np.pdv = cfg.codigopdv and cfg.lojaatual=l.codigo and "
                    + " n_cfg.id =np.id_nfce_config " ;
            if(sistema){
                 rsTmp = Dao_Jdbc_1.getConexao().GerarResultSet(sql);
            }else{
                rsTmp = DAO_RepositorioLocal.GerarResultSet(sql);
            }


            if (rsTmp.next()) {

                byte[] bytesLogo = rsTmp.getBytes("logo");
                
                InputStream p_logotipo = null;
                if(bytesLogo!=null){
                   p_logotipo =   new ByteArrayInputStream(rsTmp.getBytes("logo"));
                }else{
                    
                  Icon ico = new javax.swing.ImageIcon(getClass().getResource("/com/nfce/config/logo-nfce-xs.png"));

                   bytesLogo = ImagemTratamento.getByteData(ico,"png");
                    
                    p_logotipo =   new ByteArrayInputStream (bytesLogo);
                    
                            
                }
                int Ambiente = rsTmp.getInt("ambiente");

                String UrlConsultaHomologacao = TratamentoNulos.getTratarString().Tratar(rsTmp.getString("url_consulta_homologacao"), "");

                String UrlConsultaProducao = TratamentoNulos.getTratarString().Tratar(rsTmp.getString("url_consulta_producao"), "");

                String p_nprot = rsTmp.getString("numeroprotocolo");

                String dadosqrcode = rsTmp.getString("dadosqrcode");

                String cLocalImgQrcode = ManipulacaoArquivo2.DiretorioDeTrabalho() + ManipulacaoArquivo2.getSeparadorDiretorio() + "temp" + ManipulacaoArquivo2.getSeparadorDiretorio();

                String imgQrCode = cLocalImgQrcode + "qrcode.bmp";

                ESCPOSApi.GerarQRCode(dadosqrcode, imgQrCode);

                Timestamp p_dhRecbto = rsTmp.getTimestamp("datahoraautorizacao");

                InputStream p_qrcode = new FileInputStream(new File(imgQrCode));

                Map parametros = new HashMap();

                parametros.put("qrcode", p_qrcode);
                parametros.put("logotipo", p_logotipo);
                parametros.put("nProt", p_nprot);
                parametros.put("dhRecbto", p_dhRecbto);

                if (Ambiente == 1) {
                    parametros.put("urlconsulta", UrlConsultaHomologacao);
                } else {
                    parametros.put("urlconsulta", UrlConsultaProducao);
                }

                byte[] xmlnfe = rsTmp.getBytes("xmlnfe");
                
                InputStream xmlNFE = new ByteArrayInputStream(xmlnfe);
               
                if (jasperNFce.IsIniciado()) {
                    byte[] gerarRelatorioFonteXML = jasperNFce.gerarRelatorioFonteXML(xmlNFE, parametros);
                    StringBuilder nomeArquivoPDF =new StringBuilder();
                    nomeArquivoPDF
                    .append(ManipulacaoArquivo.DiretorioDeTrabalho())
                    .append(ManipulacaoArquivo.getSeparadorDiretorio())
                    .append("temp")    
                    .append(ManipulacaoArquivo.getSeparadorDiretorio())        
                    .append("nfce_")
                    .append(CodigoVenda.toString())
                    .append("_")
                    .append(DataHora.getData("ddMMyyyy_hhmmss", new Date()))
                    .append(".pdf");
                 
                    OutputStream  pdf = new FileOutputStream(nomeArquivoPDF.toString());
                    pdf.write(gerarRelatorioFonteXML);
                    pdf.close();
                    pdf.flush();
                    
                    //JasperPrint gerarRelatorioFonteXMLJasperPrint = jasperNFce.gerarRelatorioFonteXMLJasperPrint(xmlNFE, parametros);
                    
                    Ret = nomeArquivoPDF.toString();
                    
                } else {
                    MetodosUI_Auxiliares_1.MensagemAoUsuarioSimplesAVISO("O comprovante da nfce ainda não esta disponível. \nAguarde alguns instantes e tente novamente", "Comprovante NFCe não disponível");
                }

            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }
    
public boolean printPDF(String pdf,PrintService printService)
{
    try {
        //String filename = "C:/Users/Prodoxon/Documents/test.pdf"; 
        PDDocument document = PDDocument.load(new File (pdf));
        int numberOfPages = document.getNumberOfPages();
        //takes standard printer defined by OS
        //PrintService myPrintService = PrintServiceLookup.lookupDefaultPrintService();
        
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDPageable(document));
        job.setPrintService(printService);
        job.print();

        return true;
    } catch (Exception e) {
        LogDinnamus.Log(e, true);
        return false;
    }
  
}
    public boolean Exibir_NfceDanfe(Long CodigoVenda, RelatorioJasperXML jasperNFce, String titulo, boolean sistema) {
        boolean Ret = false;
        
        try {
            Icon ico = new javax.swing.ImageIcon(getClass().getResource("/com/nfce/config/logo-nfce-xs.png"));

            InteracaoDuranteProcessamento.Mensagem_Iniciar( "Danfe NFCe","Gerando danfe NFC-e .... aguarde", false, ico);
            boolean ret = false;
            
            String gerar_NfceDanfePDF = gerar_NfceDanfe(CodigoVenda, jasperNFce, sistema);
            
            InteracaoDuranteProcessamento.Mensagem_Terminar();
             
            if (gerar_NfceDanfePDF!=null) {
                //InputStream input = new FileInputStream(gerar_NfceDanfePDF );
                //Ret = jasperNFce.EnviarParaTela(titulo,input);
                
                 Desktop.getDesktop().open(new File(gerar_NfceDanfePDF));
            }
            
                
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Ret;
    }

    public boolean Imprimir_NfceDanfe(Long CodigoVenda, RelatorioJasperXML jasperNFce, String NomeImpressora, boolean sistema, String impressoraDanfe) {

        boolean ret = false;


        try {

           String gerar_NfceDanfePDF = gerar_NfceDanfe(CodigoVenda, jasperNFce, sistema);
            if(gerar_NfceDanfePDF!=null){
                 PrintService serviceFound = Perifericos.getImpressoraAlvo(impressoraDanfe);        
                 ret= printPDF(gerar_NfceDanfePDF,serviceFound);
                 //ManipulacaoArquivo.ExcluirArquivo(gerar_NfceDanfePDF);
                 //  ret = jasperNFce.EnviarParaImpressora(NomeImpressora,gerar_NfceDanfe);
            }
               
        } catch (Exception e) {

            LogDinnamus.Log(e, true);

        }
        return ret;
    }
    
    public boolean Imprimir_NfceDanfe_Avulso(Long CodigoVenda, RelatorioJasperXML jasperNFce, boolean sistema, PrintService serviceFound) {

        boolean ret = false;
        try {

           String gerar_NfceDanfePDF = gerar_NfceDanfe(CodigoVenda, jasperNFce, sistema);
            if(gerar_NfceDanfePDF!=null){
                 //PrintService serviceFound = Perifericos.getImpressoraAlvo(impressoraDanfe);        
                 ret= printPDF(gerar_NfceDanfePDF,serviceFound);
                 //ManipulacaoArquivo.ExcluirArquivo(gerar_NfceDanfePDF);
                 //  ret = jasperNFce.EnviarParaImpressora(NomeImpressora,gerar_NfceDanfe);
            }
               
        } catch (Exception e) {

            LogDinnamus.Log(e, true);

        }
        return ret;
    }
//    public boolean Imprimir_ComDialogo_NfceDanfe(Long CodigoVenda, RelatorioJasperXML jasperNFce, boolean sistema) {
//
//        boolean ret = false;
//
//
//        try {
//
//            if(gerar_NfceDanfe(CodigoVenda, jasperNFce, sistema)){
//
//                   ret = jasperNFce.EnviarParaDialogoImpressora();
//            }
//               
//        } catch (Exception e) {
//
//            LogDinnamus.Log(e, true);
//
//        }
//        return ret;
//    }
}
