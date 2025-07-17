/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemmanajemenmieayam;
import javax.swing.UIManager;
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
            // Atur Look and Feel ke Metal (cross-platform)
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            // Atur warna tab
            UIManager.put("TabbedPane.selected", new java.awt.Color(255,204,153));
            UIManager.put("TabbedPane.unselectedBackground", new java.awt.Color(255, 255, 255));
            UIManager.put("TabbedPane.contentAreaColor", java.awt.Color.WHITE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Jalankan form utama
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new splashscreen().setVisible(true);
            }
        });
    }
        
    }
