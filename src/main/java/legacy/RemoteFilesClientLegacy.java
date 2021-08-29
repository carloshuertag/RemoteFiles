/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legacy;

import com.chuertag.practica1.RemoteFilesProperties;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author root
 */
public class RemoteFilesClientLegacy {
    public static void main(String[] args){
    SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                File f = new File(RemoteFilesProperties.CURRENT_ABSOLUTE_PATH +
                    RemoteFilesProperties.CLIENT_DIRECTORY);
                FileList fl = new FileList();
                Component c1 = fl.getGui(f.listFiles(), true,
                        new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        JList list = (JList) e.getSource();
                        if(!e.getValueIsAdjusting()){
                            System.out.println(list.getSelectedValuesList());
                        }
                    }
                });
                JFrame frame = new JFrame("Remote Files Client");
                JPanel gui = new JPanel(new BorderLayout());
                gui.add(new JLabel("Local"), BorderLayout.NORTH);
                gui.add(c1,BorderLayout.WEST);
                //gui.add(c2,BorderLayout.CENTER);
                //c2.setPreferredSize(new Dimension(375,100));
                gui.setBorder(new EmptyBorder(3,3,3,3));
                frame.setContentPane(gui);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
