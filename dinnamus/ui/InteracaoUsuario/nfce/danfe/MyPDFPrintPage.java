/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.InteracaoUsuario.nfce.danfe;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

class MyPDFPrintPage implements Printable {

    private PDFFile file;

    MyPDFPrintPage(PDFFile file) {
        this.file = file;
    }

    public int print(Graphics g, PageFormat format, int index) throws PrinterException {
        int pagenum = index + 1;
        // don't bother if the page number is out of range.  
        if ((pagenum >= 1) && (pagenum <= file.getNumPages())) {
            // fit the PDFPage into the printing area  
            Graphics2D g2 = (Graphics2D) g;

            PDFPage page = file.getPage(pagenum);
            double pwidth = format.getImageableWidth();
            double pheight = format.getImageableHeight();

            double aspect = page.getAspectRatio();
            double paperaspect = pwidth / pheight;

            Rectangle imgbounds;

            if (aspect > paperaspect) {
                // paper is too tall / pdfpage is too wide  
                int height = (int) (pwidth / aspect);

                imgbounds = new Rectangle((int) format.getImageableX(), (int) (format.getImageableY() + ((pheight - height) / 2)), (int) pwidth, height);
            } else {
                // paper is too wide / pdfpage is too tall  
                int width = (int) (pheight * aspect);
                imgbounds = new Rectangle((int) (format.getImageableX() + ((pwidth - width) / 2)), (int) format.getImageableY(), width, (int) pheight);
            }

            // render the page  
            PDFRenderer pgs = new PDFRenderer(page, g2, imgbounds, null, null);
            try {
                page.waitForFinish();
                pgs.run();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
