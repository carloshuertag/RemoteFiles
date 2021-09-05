package com.chuertag.practica1.client.gui;

import com.chuertag.practica1.RemoteFiles;
import com.chuertag.practica1.RemoteFilesProperties;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author root
 */
public class ServerFilesPanel extends JPanel implements ActionListener {

    private JButton receive;
    private JTree serverFilesTree;
    private JScrollPane treeScrollPane;
    private JProgressBar jpb;
    private String serverIPAddr;
    private int port;

    public ServerFilesPanel(String ipAddress, int port) {
        this.serverIPAddr = ipAddress;
        this.port = port;
        serverFilesTree = new JTree(receiveRemoteFilesInfo());
        serverFilesTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeScrollPane = new JScrollPane(serverFilesTree);
        receive = new JButton("Receive file(s)");
        receive.addActionListener(this);
        setLayout(new BorderLayout());
        add(treeScrollPane, BorderLayout.CENTER);
        add(receive, BorderLayout.SOUTH);
    }

    private DefaultMutableTreeNode receiveRemoteFilesInfo() {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("MyFiles");
        try {
            Socket client = new Socket(serverIPAddr, port + 1);
            ObjectInputStream ois = new ObjectInputStream(
                    client.getInputStream());
            treeNode = (DefaultMutableTreeNode) ois.readObject();
            ois.close();
            client.close();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Oops! On error occured when receiving server data, please try again");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Oops! Cannot connect to server, please try again later");
        } finally {
            return treeNode;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> temp = new ArrayList<>();
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)
                serverFilesTree.getLastSelectedPathComponent();
        int i;
        String filename = "";
        temp.add(treeNode.toString());
        for (i = 0; i < treeNode.getLevel(); i++) {
            temp.add(treeNode.getParent().toString());
        }
        for (i = i; i >= 0; i--) {
            filename += temp.get(i);
            if (i != 0) {
                filename += RemoteFilesProperties.SLASH;
            }
        }
        jpb = RemoteFiles.setJProgressBar("Receiving files");
        JOptionPane.showMessageDialog(null, jpb);
        try {
            RemoteFiles.notifyServer(serverIPAddr, port, false);
            RemoteFiles.receiveFiles(filename, System.getProperty("user.dir")
                    + RemoteFilesProperties.SLASH
                    + RemoteFilesProperties.CLIENT_DIRECTORY
                    + RemoteFilesProperties.SLASH, false, null, jpb,
                    serverIPAddr, port);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Oops! An error occured when receiving file(s)");
        }
    }
}
