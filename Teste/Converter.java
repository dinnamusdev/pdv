/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;

/**
 *
 * @author Fernando
 */
public class Converter {
    public static void main(String[] args) {
        try {
            // Nome e diretório do Arquivo
            String strArquivoIn = "C:\\Users\\Fernando\\Downloads\\notasdejunho.jrprint";
            JasperExportManager.exportReportToPdfFile(strArquivoIn,
                    "c:\\notasdejunho.pdf");
            System.out.println("Arquivo Convertido com Sucesso.");
 
        } catch (JRException e) {
            System.out.println("Erro ao Converter o Arquivo.");
            e.printStackTrace();
        }
    }
}
