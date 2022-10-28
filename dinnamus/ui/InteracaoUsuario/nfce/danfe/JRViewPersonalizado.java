/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfce.danfe;

import java.util.Locale;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;

/**
 *
 * @author Fernando
 */
public class JRViewPersonalizado  extends JRViewer{
  
    //define the constructor that you use
    public JRViewPersonalizado(JasperPrint jasperPrint) {
        super(jasperPrint);
    }

    @Override
    protected JRViewerToolbar createToolbar() {
        JRViewerToolbar toolbar = super.createToolbar();

        Locale locale = viewerContext.getLocale();
        ResourceBundle resBundle = viewerContext.getResourceBundle();
        JRPdfSaveContributor pdf = new JRPdfSaveContributor(locale, resBundle);
        //JRRtfSaveContributor rtf = new JRRtfSaveContributor(locale, resBundle);
        //JRSingleSheetXlsSaveContributor xls = new JRSingleSheetXlsSaveContributor(locale, resBundle);
        //JRDocxSaveContributor docx = new JRDocxSaveContributor(locale, resBundle);
        toolbar.setSaveContributors(new JRSaveContributor[] {pdf});

        return toolbar;
    }   
}

