package com.ufpr.br.opla.gui2;

/**
 *
 * @author elf
 */
public class Oplagui {

    public static void main(String args[]) {
   
        java.awt.EventQueue.invokeLater(new Runnable() {
           
            @Override
            public void run() {
                com.ufpr.br.opla.gui2.main gui = new main();
                gui.setVisible(true);
                gui.setDefaultThings();
  
            }
        });
    }
    
}
