/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.componentes.tabela;

import br.com.log.LogDinnamus;
import br.com.ui.ValidarCelula;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Fernando
 */
public class EditorTabela extends  AbstractCellEditor  implements TableCellEditor  {
    
    private boolean EdicaoCancelada=true;
    private String Coluna ="";
    private Object obj =null;
    private JTextField componente = new JTextField(); 
    private ValidarCelula validacoes =null;    
    private Set<String> ColunasNumericas=new HashSet<String>();
    private int UltimaTecla=0;
    private boolean  Foco = false;
    public EditorTabela(ValidarCelula validacoes){
        this.validacoes = validacoes;                                                                                                                                  
        componente.setBorder(null); 
       /* if(componente.getKeyListeners().length==0){
            componente.addKeyListener(  new KeyListener() {
                public void keyTyped(KeyEvent e) {
                      UltimaTecla = e.getKeyCode();   
                      
                }
                public void keyPressed(KeyEvent e) {
                       UltimaTecla = e.getKeyCode(); 
                      
                }
                public void keyReleased(KeyEvent e) {
                    //UltimaTecla = e.getKeyCode();   
                    
                }
            });
        }*/
        
    }
    private void TratarTeclaEnter(JTable table){
        try {
            InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            if (im.size() == 0) {
                KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
                im.put(enter, im.get(KeyStroke.getKeyStroke(KeyEvent.VK_GREATER, 0)));
                Action enterAction = new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        UltimaTecla = 10;
                        stopCellEditing();
                    }
                };
                table.getActionMap().put(im.get(enter), enterAction);
                
                KeyStroke escape = KeyStroke.getKeyStroke("ESCAPE");
                im.put(escape, im.get(KeyStroke.getKeyStroke(KeyEvent.VK_GREATER, 0)));
                Action escapeAction = new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        UltimaTecla = 0;
                        stopCellEditing();
                    }
                };
                 table.getActionMap().put(im.get(escape), escapeAction);
            }
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
    }
    public EditorTabela(ValidarCelula validacoes, Set<String> ColunasNumericas){
        this.validacoes = validacoes;                                                                                                                          
        this.ColunasNumericas = ColunasNumericas;
        componente.setBorder(null); 
    }
    
   
    @Override
    public Object getCellEditorValue() {
       return componente.getText();  
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
           // if (isSelected){   
           try {
                this.Coluna = table.getModel().getColumnName(column);                      
                componente.setBorder(null);    
                componente.getCaret().setVisible(true);                
                if(value!=null){
                    componente.setText(value.toString());      
                    //UltimaTecla = value.toString().getBytes()[value.toString().getBytes().length-1];
                }else{
                    componente.setText("");
                }
               TratarTeclaEnter(table);
               componente.selectAll();      
               
            } catch (Exception e) {
                LogDinnamus.Log(e, true);
                return null;
            }
             
            //}      
            return componente;
    }
    
   @Override  
   public void cancelCellEditing(){
       try {
           setEdicaoCancelada(true);    
       } catch (Exception e) {
           LogDinnamus.Log(e, true);
       }
       super.cancelCellEditing();
   }
    @Override  
    public boolean shouldSelectCell(EventObject anEvent) {
        
        return true;
    }
   @Override  
   public boolean stopCellEditing(){  
       try {
            if(UltimaTecla==KeyEvent.VK_ENTER){
             UltimaTecla=0;
             setEdicaoCancelada(false);
             Object Value = getCellEditorValue();           
             if(validacoes!=null){
                 if( !validacoes.Validar(Coluna, Value)){
                     cancelCellEditing();
                     return false;             
                  }                 
             }           
           }else{          
                //UltimaTecla=0;
                cancelCellEditing();
                return false;
           }
       } catch (Exception e) {
            LogDinnamus.Log(e, true);
       }
       super.stopCellEditing();
       return true;
   } 
   @Override  
   public boolean isCellEditable(EventObject e) {          
       try {       
           
           if(e.getClass()==MouseEvent.class){
               MouseEvent m = (MouseEvent) e;  
               int click = m.getClickCount();
               if(click==1){
                   return false;
               }else{
                    return true;
                }
           }else{
               return true;
           }                     
       } catch (Exception ex) {
           LogDinnamus.Log(ex, true);
           return true;
       }
 
       
   }  

    /**
     * @return the EdicaoCancelada
     */
    public boolean isEdicaoCancelada() {
        return EdicaoCancelada;
    }

    /**
     * @param EdicaoCancelada the EdicaoCancelada to set
     */
    public void setEdicaoCancelada(boolean EdicaoCancelada) {
        this.EdicaoCancelada = EdicaoCancelada;
    }
   
   }
