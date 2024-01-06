package com.issyhome.JavaMestra.FileChooser;

import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * HelloWorldSwing.java requires no other files. 
 */
import javax.swing.*;        


public class HelloWorldSwing {
	
	final static Logger logger = Logger.getLogger(HelloWorldSwing.class.getName()); 

    
    private static void setLookAndFeel(JFrame frame) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) { 
            logger.log(Level.SEVERE , "MainGui: Exception while setting look and feel");
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        TestFileChooser testFileChooser = new TestFileChooser();
        frame.getContentPane().add(testFileChooser);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        setLookAndFeel(frame);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

