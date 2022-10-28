/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.infraestrutura.inicializacao;

import br.com.log.LogDinnamus;
import br.com.servidor.Dao_Jdbc_1;
import MetodosDeNegocio.RepositorioLocal.DAO_RepositorioLocal_Inicializar;
import br.String.ManipulacaoString;
import br.com.ManipularString;
import br.data.DataHora;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.manipulararquivos.ManipulacaoArquivoGrava;
import dinnamus.ui.InteracaoUsuario.Retaguarda.frmGerarCargaPDV;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;

/**
 *
 * @author dti
 */
public class ArquivoCargaPDV {

    //private static String SeparadorDeCampos="!@!";
    //private static String DelimitadorCampoAlfa="@!@";
    private static String SeparadorDeCampos = "¨";
    private static String DelimitadorCampoAlfa = "°";

    private static String cNomeBaseArquivos = "";
    private static String cNomeBaseArquivosColunas = "";
    private static frmGerarCargaPDV cargaPDV = null;
    private static ManipulacaoArquivoGrava nIO = null;
    private static File fArquivoCargar = null;
    private static ArrayList<String> ArquivosCarga = new ArrayList<String>();

    static public void Iniciar(String cLocal, String data, String hora) {
        if (getArquivosCarga() != null) {
            ArquivoCargaPDV.getArquivosCarga().clear();
        }
        Iniciar(cLocal, "", data, hora);
    }

    static public void Iniciar(String cLocal, String NomeArquivo, String data, String hora) {
        cNomeBaseArquivos = cLocal + ManipulacaoArquivo2.getSeparadorDiretorio() + ("".equalsIgnoreCase(NomeArquivo) ? "CargaPDV" + data + hora + "#" : NomeArquivo);
        cNomeBaseArquivosColunas = cLocal + ManipulacaoArquivo2.getSeparadorDiretorio() + ("".equalsIgnoreCase(NomeArquivo) ? "CargaPDV" + data + hora + "#" : NomeArquivo);
    }

    static private void MontarArquivo_Fechar() {
        nIO.FecharCanalGravacao();
        fArquivoCargar = null;
    }

    static private boolean MontarArquivo_Gravar(String cLinha) {
        return nIO.GravarLinha(cLinha);
    }

    static private boolean MontarArquivo_Abrir(String cNomeArquivo) {

        boolean bRet = false;
        try {

            fArquivoCargar = ManipulacaoArquivo2.novaInstanciaFile(cNomeArquivo);
            nIO = new ManipulacaoArquivoGrava("UTF-8");
            bRet = nIO.AbrirCanalGravar(fArquivoCargar);
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    static private boolean gravarArquivoColunas(String nomeArquivoColunas, String colunas) {
        boolean bRet = false;
        try {

            File novaInstanciaFile = ManipulacaoArquivo2.novaInstanciaFile(nomeArquivoColunas);
            ManipulacaoArquivoGrava arquivoColuna = new ManipulacaoArquivoGrava("UTF-8");
            if (arquivoColuna.AbrirCanalGravar(novaInstanciaFile)) {
                arquivoColuna.GravarLinha(colunas);
                arquivoColuna.FecharCanalGravacao();
                bRet = true;
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return bRet;
    }

    static public ArrayList<String> ProcessarArquivo_ListaTabelas() {
        return DAO_RepositorioLocal_Inicializar.TabelasParaSincronismo(1);
    }

    static public boolean ProcessarTabela(String cNomeTabela) {
        try {

            return ProcessarTabela(cNomeTabela, null, false);

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    static public boolean ProcessarTabela(String cNomeTabela, boolean incluircolunas) {
        try {

            return ProcessarTabela(cNomeTabela, null, incluircolunas);

        } catch (Exception e) {
            LogDinnamus.Log(e, true);
            return false;
        }
    }

    static public boolean ProcessarTabela(String cNomeTabela, ResultSet rsDadosTabela, boolean incluircolunas) {
        ResultSet rsDados = null;
        int nTotalRegistrosTabela = 0;
        String cQueryPesquisa = "";
        String cLinhaDeDados = "";
        String cDados = "";
        String cNomeArquivo = "";
        String cNomeArquivoColunas = "";

        boolean bRet = false;
        try {
            if (rsDadosTabela == null) {
                cQueryPesquisa = "select * from " + cNomeTabela;
                rsDados = Dao_Jdbc_1.getConexao().GerarResultSet(cQueryPesquisa);
            } else {
                rsDados = rsDadosTabela;
            }
            nTotalRegistrosTabela = Dao_Jdbc_1.getConexao().ContarRegistros(cNomeTabela);
            if (nTotalRegistrosTabela > 0) {
                cNomeArquivo = cNomeBaseArquivos + cNomeTabela + ".data";
                cNomeArquivoColunas = cNomeBaseArquivos + cNomeTabela + ".colunas";

                if (MontarArquivo_Abrir(cNomeArquivo)) {
                    String cNomeCampo = "";

                    if (incluircolunas) {
                        StringBuilder stringColunas = new StringBuilder();
                        for (int j = 1; j <= rsDados.getMetaData().getColumnCount(); j++) {
                             
                            stringColunas.append("%").append(rsDados.getMetaData().getColumnName(j)).append("%").append(j < rsDados.getMetaData().getColumnCount() ? ";" : "");
                        }
                        if (gravarArquivoColunas(cNomeArquivoColunas, stringColunas.toString())) {
                            getArquivosCarga().add(cNomeArquivoColunas);
                        }

                    }

                    while (rsDados.next()) {
                        cLinhaDeDados = "";
                        for (int j = 1; j <= rsDados.getMetaData().getColumnCount(); j++) {
                            cNomeCampo = rsDados.getMetaData().getColumnName(j);
                            if (rsDados.getMetaData().getColumnType(j) == Types.BLOB) {
                                cDados = "";

                                Blob blob = rsDados.getBlob(rsDados.getMetaData().getColumnName(j));
                                if (blob != null && blob.length() > 0) {
                                    byte[] b = blob.getBytes(1l, new Long(blob.length()).intValue());
                                    if (b != null) {
                                        cDados = "" ;//DelimitadorCampoBlob + Base64.getEncoder().encodeToString(b) + DelimitadorCampoBlob;
                                    }
                                }
                            } else if (rsDados.getMetaData().getColumnType(j) == Types.INTEGER || 
                                    rsDados.getMetaData().getColumnType(j) == Types.DECIMAL ||
                                    rsDados.getMetaData().getColumnType(j) == Types.NUMERIC ||
                                    rsDados.getMetaData().getColumnType(j) == Types.REAL
                                    ) {

                                Long l = rsDados.getLong(rsDados.getMetaData().getColumnName(j));
                                if(rsDados.wasNull()){
                                    cDados="0";
                                }else{
                                    if(l!=null){
                                        cDados = String.valueOf(l.toString());
                                    }
                                }
                                
                            } else if (rsDados.getMetaData().getColumnType(j) == Types.FLOAT ){
                                Float f = rsDados.getFloat(rsDados.getMetaData().getColumnName(j));
                                if(!rsDados.wasNull()){
                                   cDados = f.toString();
                                }else{
                                    cDados="null";
                                }
                                
                            } else if (rsDados.getMetaData().getColumnType(j) == Types.DOUBLE ){    
                                Double d = rsDados.getDouble(rsDados.getMetaData().getColumnName(j));
                                if(!rsDados.wasNull()){
                                   cDados = d.toString();
                                }else{
                                    cDados="null";
                                }
                            } else if (rsDados.getMetaData().getColumnType(j) == Types.BIGINT ){
                                
                                BigDecimal bigDecimal = rsDados.getBigDecimal(rsDados.getMetaData().getColumnName(j));
                                if(!rsDados.wasNull()){
                                   cDados = bigDecimal.toString();
                                }else{
                                    cDados="null";
                                }
                            }else if (rsDados.getMetaData().getColumnType(j) == Types.BOOLEAN) {
                                boolean aBoolean = rsDados.getBoolean(rsDados.getMetaData().getColumnName(j));

                                if (aBoolean) {
                                    cDados = "1";
                                } else {
                                    cDados = "0";
                                }
                            } else if (rsDados.getMetaData().getColumnType(j) == Types.BIT) {

                                cDados = (rsDados.getBoolean(rsDados.getMetaData().getColumnName(j)) ? "1" : "0");

                            } else if (rsDados.getMetaData().getColumnType(j)==Types.DATE ||
                                        rsDados.getMetaData().getColumnType(j)==Types.TIMESTAMP ||
                                     rsDados.getMetaData().getColumnType(j)==Types.TIMESTAMP_WITH_TIMEZONE ) {
                                
                                Timestamp timestamp = rsDados.getTimestamp(rsDados.getMetaData().getColumnName(j));
                                if(timestamp!=null){
                                    cDados = DataHora.getCampoFormatado("yyyy-MM-dd HH:mm:ss", rsDados.getTimestamp(rsDados.getMetaData().getColumnName(j)));
                                }else{
                                    cDados="null";
                                }
                            } else if (rsDados.getMetaData().getColumnType(j) == Types.VARCHAR
                                        || rsDados.getMetaData().getColumnType(j) == Types.CHAR
                                        || rsDados.getMetaData().getColumnType(j) == Types.NVARCHAR) {
                                 
                                    if (rsDados.getString(rsDados.getMetaData().getColumnName(j)) == null) {
                                        cDados = "null";
                                    } else {
                                        cDados = rsDados.getString(rsDados.getMetaData().getColumnName(j)).toString();
                                  
                                    
                                        cDados = DelimitadorCampoAlfa + cDados.replaceAll(DelimitadorCampoAlfa, "") + DelimitadorCampoAlfa;
                                    }
                            }else{
                                 if (rsDados.getObject(rsDados.getMetaData().getColumnName(j)) == null) {
                                    cDados = "null";
                                } else {
                                    cDados = rsDados.getObject(rsDados.getMetaData().getColumnName(j)).toString();
                                }
                            }

                            if(cDados.equalsIgnoreCase("null")){
                                cDados="";
                            }
                            cLinhaDeDados += (cLinhaDeDados.length() > 0 ? SeparadorDeCampos : "") + cDados;

                        }
                        cLinhaDeDados = cLinhaDeDados.replaceAll("\n", "").replace("\r", "").replaceAll("\t", "");
                       // System.out.println(cLinhaDeDados);
                        MontarArquivo_Gravar(cLinhaDeDados + "\n");
                    }
                }
                MontarArquivo_Fechar();
                getArquivosCarga().add(cNomeArquivo);
            }
            bRet = true;
        } catch (Exception ex) {
            LogDinnamus.Log(ex);
        }
        return bRet;
    }

    /**
     * @return the ArquivosCarga
     */
    public static ArrayList<String> getArquivosCarga() {
        return ArquivosCarga;
    }

    /**
     * @param aArquivosCarga the ArquivosCarga to set
     */
    public static void setArquivosCarga(ArrayList<String> aArquivosCarga) {
        ArquivosCarga = aArquivosCarga;
    }

}
