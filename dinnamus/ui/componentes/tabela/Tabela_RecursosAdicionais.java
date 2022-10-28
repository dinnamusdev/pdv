/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.componentes.tabela;

import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.com.ui.JTableDinnamuS;
import br.TratamentoNulo.TratarNulos;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.table.TableColumn;

/**
 *
 * @author Fernando
 */
public class Tabela_RecursosAdicionais {
    
    
    public static int Grid_RetornaIndiceColuna(JTableDinnamuS dbgTable , String NomeColuna){
        try {
              int nRetorno =-1;
              int nFim=dbgTable.getjTable().getColumnModel().getColumnCount();
                            
              for (int i = 0; i < nFim; i++) {
                  if(dbgTable.getjTable().getModel().getColumnName(i).equalsIgnoreCase(NomeColuna)){                     
                     nRetorno=i;
                     break;
                  }
              }
            return nRetorno;
        } catch (Exception e) {
             LogDinnamus.Log(e, true);
             return -1;
        }
    }
    public static String Grid_UltimoCampoGridCotOrcVenda(){
        try {
            String cRetorno ="";
            ResultSet rs = Sistema.getDadosLoja(Sistema.getLojaAtual());
            if(rs.next()){
              cRetorno =rs.getString("UltimoCampoGridCotOrcVenda");
              cRetorno = (new TratarNulos<String>()).Tratar(cRetorno, "");
              if(cRetorno==""){
                  cRetorno="quantidade";
              }
            }
            return cRetorno;
        } catch (Exception e) {
             LogDinnamus.Log(e, true);
             return "";
        }
        
    }
    
     public static void Grid_PosicionarCursor(JTableDinnamuS dbgTable , String NomeColuna){
         try {
              int nFim=dbgTable.getjTable().getColumnModel().getColumnCount();
                            
              for (int i = 0; i < nFim; i++) {
                  if(dbgTable.getjTable().getModel().getColumnName(i).equalsIgnoreCase(NomeColuna)){
                     dbgTable.getjTable().setColumnSelectionInterval(i, i); 
                     return;
                  }
              }
             
         } catch (Exception e) {
             LogDinnamus.Log(e, true);
         }
     }
     private static int Grid_UltimaColunaVisivel(JTableDinnamuS dbgTable){
        try {
            int nUltimoColunaVisivel =0;
            for (int i = dbgTable.getjTable().getColumnCount()-1; i >=0 ; i--) {
                if(dbgTable.getjTable().getColumnModel().getColumn(i).getPreferredWidth()>0){
                nUltimoColunaVisivel =i;
                break;
                }                    
            }
            return nUltimoColunaVisivel;
        } catch (Exception e) {
             LogDinnamus.Log(e, true);
             return -1;
        }
    }
    public static void TratarTeclaPressionada(KeyEvent e, JTableDinnamuS dbgTable ){
        try {            
             if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT){
                        //e.consume();
                        int nCol =dbgTable.getjTable().getSelectedColumn();
                        int nInicio=0,nFim=0;
                        
                        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                            nInicio =nCol+1;
                            nFim=dbgTable.getjTable().getColumnModel().getColumnCount();
                            
                            for (int i = nInicio; i < nFim; i++) {
                                TableColumn tc= dbgTable.getjTable().getColumnModel().getColumn(i);
                                if(tc.getPreferredWidth()!=0){
                                      dbgTable.getjTable().setColumnSelectionInterval(i-1, i-1);
                                    return ;
                                }
                            }
                            int nUltimaColuna = Grid_UltimaColunaVisivel(dbgTable);
                            dbgTable.getjTable().setColumnSelectionInterval(nUltimaColuna-1, nUltimaColuna-1);
                        }else{
                            nInicio =nCol-1;
                            nFim=0;
                                                  
                            for (int i = nInicio; i >= nFim; i--) {
                                TableColumn tc= dbgTable.getjTable().getColumnModel().getColumn(i);
                                if(tc.getPreferredWidth()!=0){
                                    e.consume();
                                    dbgTable.getjTable().setColumnSelectionInterval(i, i);
                                    
                                    return;
                                }
                            }                             
                            dbgTable.getjTable().setColumnSelectionInterval(0,0);
                        }

             } else if(e.getKeyCode() == KeyEvent.VK_END){
                    int nUltimaColuna = Grid_UltimaColunaVisivel(dbgTable);
                    e.consume();
                    dbgTable.getjTable().setColumnSelectionInterval(nUltimaColuna, nUltimaColuna);
             }else if(e.getKeyCode() == KeyEvent.VK_HOME){
                 dbgTable.getjTable().setColumnSelectionInterval(0, 0);
             }
        } catch (Exception ex) {
            LogDinnamus.Log(ex);
            
        }
    }     
}
