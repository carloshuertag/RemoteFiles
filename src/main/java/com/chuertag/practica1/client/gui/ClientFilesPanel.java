package com.chuertag.practica1.client.gui;

import com.chuertag.practica1.RemoteFilesProperties;
import com.chuertag.practica1.client.RemoteFilesClient;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author root
 */
public class ClientFilesPanel extends JPanel implements ActionListener {

    private JTextField jtf;
    private File jfcroot;
    private JFileChooser jfc;
    private JButton refresh;
    private JPanel navbar;
    private JProgressBar jpb;
    private String serverIP;
    private int port;

    public ClientFilesPanel(String ipAddress, int port) {
        UIManager.put("FileChooser.chooseButtonText", "Send file(s)");
        UIManager.put("FileChooser.cancelButtonText", "Choose");
        serverIP = ipAddress;
        this.port = port;
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
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource().equals(refresh)) {
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
        }
        if (ev.getActionCommand().equals("CancelSelection")) {
            try {
                jtf.setText(jfc.getSelectedFile().getCanonicalPath());
                if (jfc.getSelectedFile().isDirectory()) {
                    jfc.setCurrentDirectory(jfc.getSelectedFile());
                } else {
                    jfc.setCurrentDirectory(jfc.getSelectedFile().getParentFile());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Invalid selection");
            }
        }
        if (ev.getActionCommand().equals("ApproveSelection")) {
            jpb = RemoteFilesClient.setJProgressBar("Sending files");
            JOptionPane.showMessageDialog(null, jpb);
            try {
                RemoteFilesClient.notifyServer(serverIP, port, true);
                RemoteFilesClient.sendFiles(jfc.getSelectedFiles(), jpb, false,
                        "", serverIP, port);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "Oops! An error occured when sending file(s)");
            }
        }
    }

    private void setFileChooser() {
        jfc = new JFileChooser(jfcroot);
        jfc.setMultiSelectionEnabled(true);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.addActionListener(this);
    }
    
}
