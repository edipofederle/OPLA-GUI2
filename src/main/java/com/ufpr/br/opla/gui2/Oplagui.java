package com.ufpr.br.opla.gui2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elf
 */
public class Oplagui {

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                com.ufpr.br.opla.gui2.main gui;
                try {
                    gui = new main();
                    gui.setResizable(false);
                    gui.setVisible(true);
                    
                } catch (Exception ex) {
                    Logger.getLogger(Oplagui.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
}
