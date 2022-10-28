/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dinnamus.ui.componentes.tabela;

import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Fernando
 */
public class EditorNumerico extends DefaultCellEditor {
    protected JComponent editorComponent;
    protected EditorDelegate delegate;
    protected int clickCountToStart = 1;
    private NumberFormat currencyFormat;
  
    /**
     * Constructor for objects of class MyCurrencyCellEditor
     */
    public EditorNumerico( final JFormattedTextField tf,
                                 NumberFormat nf ) {
        super( tf );
        currencyFormat = nf;
        tf.setFocusLostBehavior( JFormattedTextField.COMMIT );
        tf.setHorizontalAlignment( SwingConstants.RIGHT );
        //tf.setBorder( null );
  
        delegate = new EditorDelegate() {
  
            public Object getCellEditorValue() {
                try {
                    String field = tf.getText();
                    Number number = currencyFormat.parse( field );
                    double parsed = number.doubleValue();
                    Double d = new Double( parsed );
                    return d;
                } catch ( ParseException e ) {
                    e.printStackTrace();
                    return new Double( 0.0 );
                }
            }
  
            public void setValue( Object param ) {
                Double value = ( Double ) param;
                if ( value == null ) {
                    tf.setValue( currencyFormat.format( 0.0 ) );
                } else {
                    double d = value.doubleValue();
                    String format = currencyFormat.format( d );
                    tf.setValue( format );
                }
            }
        };
    }
}
