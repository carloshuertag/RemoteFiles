package com.chuertag.practica1.client.gui;

import com.chuertag.practica1.RemoteFilesProperties;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author root
 */
public class ClientFilesPanel extends JPanel implements ActionListener {

    JTextField jtf;
    File jfcroot;
    JFileChooser jfc;
    JButton refresh;
    JPanel navbar;
    String currDirectory = null;

    public ClientFilesPanel() {
        UIManager.put("FileChooser.chooseButtonText", "Send file(s)");
        UIManager.put("FileChooser.cancelButtonText", "Choose");
        jfcroot = new File(RemoteFilesProperties.CURRENT_ABSOLUTE_PATH
                + RemoteFilesProperties.CLIENT_DIRECTORY);
        navbar = new JPanel(new BorderLayout());
        jtf = new JTextField(RemoteFilesProperties.CURRENT_ABSOLUTE_PATH
                + RemoteFilesProperties.CLIENT_DIRECTORY);
        refresh = new JButton("Go to the entered path");
        refresh.addActionListener(this);
        navbar.add(jtf, BorderLayout.CENTER);
        navbar.add(refresh, BorderLayout.EAST);
        setFileChooser();
        setLayout(new BorderLayout());
        add(navbar, BorderLayout.NORTH);
        add(jfc, BorderLayout.CENTER);
        jtf.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        jfcroot = new File(jtf.getText());
        if (jfcroot != null && jfcroot.exists()) {
            remove(navbar);
            remove(jfc);
            setVisible(false);
            setFileChooser();
            add(navbar, BorderLayout.NORTH);
            add(jfc, BorderLayout.CENTER);
            setVisible(true);
        }
        if (ev.getActionCommand().equals("CancelSelection")) {
            System.out.printf("CancelSelection\n");
        }
        if (ev.getActionCommand().equals("ApproveSelection")) {
            System.out.printf("ApproveSelection\n");
        }
    }

    public void setFileChooser() {
        jfc = new JFileChooser(jfcroot);
        jfc.setMultiSelectionEnabled(true);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }
}
