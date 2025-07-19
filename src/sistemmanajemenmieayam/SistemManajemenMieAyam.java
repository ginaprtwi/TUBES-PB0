/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemmanajemenmieayam;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.UIDefaults;




/**
 *
 * @author HP
 */
public class SistemManajemenMieAyam {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
    try {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());

                break;
            }
        }
            
                UIManager.put("ComboBox.background", Color.WHITE);
                UIManager.put("ComboBox.foreground", Color.BLACK);
                UIManager.put("ComboBox.selectionBackground", new Color(255,204,153));
                UIManager.put("ComboBox.selectionForeground", Color.BLACK);
                UIManager.put("ComboBox.buttonBackground", Color.WHITE);
                
                UIManager.put("nimbusSelectionBackground", new Color(255,204,153)); 
                UIManager.put("nimbusSelectedText", Color.BLACK);
                
                UIManager.put("Table.background", Color.WHITE);
                UIManager.put("Table.foreground", Color.BLACK);
                UIManager.put("Table.selectionBackground", new Color(255,204,153));
                UIManager.put("Table.selectionForeground", Color.BLACK);
                
                UIManager.put("OptionPane.background", Color.WHITE);
                UIManager.put("Panel.background", Color.WHITE);
                UIManager.put("OptionPane.messageForeground", Color.BLACK);
                UIManager.put("OptionPane.foreground", Color.BLACK);
                UIManager.put("Button.background", new Color(255,204,153));
                UIManager.put("Button.foreground", Color.BLACK);
                
                UIDefaults def = UIManager.getLookAndFeelDefaults();
                def.put("ComboBox.borderPainter", new AbstractRegionPainter() {
                protected void doPaint(Graphics2D g, JComponent c, int w, int h, Object[] keys) {
                    g.setColor(new Color(240, 160, 100));
                    g.drawRect(0, 0, w - 1, h - 1);
                }
                protected AbstractRegionPainter.PaintContext getPaintContext() {
                    return null;
                }
                
            });
                    
                
                
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new splashscreen().setVisible(true);
        }
    });
    }

}
        
    
