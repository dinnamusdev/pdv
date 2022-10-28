/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.ui.infraestrutura;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author dti
 */
public class JOptionPanelComEnter extends JOptionPane{
    private static final long serialVersionUID = 1L;

    public JOptionPanelComEnter() {
            JPanel b = (JPanel) this.getComponent(1);
            final JButton acheiVoce = (JButton) b.getComponent(0);
            acheiVoce.addKeyListener(new KeyAdapter(){
                @Override
                public void keyReleased(KeyEvent arg0) {
                    if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
                        acheiVoce.doClick();

                }
            });
    }

}
