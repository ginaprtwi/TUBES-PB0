/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemmanajemenmieayam;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.NumberFormat;
import java.util.Locale; // For things like $ or Rp

/**
 *
 * @author Malik
 */
public class CurrencyCellRenderer extends DefaultTableCellRenderer {

    private NumberFormat currencyFormat;

    public CurrencyCellRenderer()
    {
        // Create a Locale object for Indonesian (Indonesia)
        Locale indonesiaLocale = new Locale("in", "ID");

        // Get the currency instance for this specific locale
        currencyFormat = NumberFormat.getCurrencyInstance(indonesiaLocale);
        
//        setHorizontalAlignment(JLabel.RIGHT);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof Number) { // If it's a number...
            setText(currencyFormat.format(value)); // Format it!
        } else {
            super.setValue(value); // Otherwise, just show it normally
        }
    }
}
