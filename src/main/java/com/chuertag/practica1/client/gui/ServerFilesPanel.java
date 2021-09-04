package com.chuertag.practica1.client.gui;

import com.chuertag.practica1.RemoteFilesProperties;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author root
 */
public class ServerFilesPanel extends JPanel implements ActionListener{
    private JButton receive;
    private JTree serverFilesTree;
    private JScrollPane treeScrollPane;

    public ServerFilesPanel(String ipAddress, int port){
        serverFilesTree = new JTree(receiveRemoteFilesInfo(ipAddress, port));
        treeScrollPane = new JScrollPane(serverFilesTree);
        receive = new JButton("Receive file(s)");
        receive.addActionListener(this);
        setLayout(new BorderLayout());
        add(treeScrollPane, BorderLayout.CENTER);
        add(receive, BorderLayout.SOUTH);
    }
    
    private DefaultMutableTreeNode receiveRemoteFilesInfo(String ipAddress,
            int port) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("MyFiles");
        try {
            Socket client = new Socket(ipAddress, port + 1);
            ObjectInputStream ois = new ObjectInputStream(
                    client.getInputStream());
            treeNode = (DefaultMutableTreeNode)ois.readObject();
        } catch (ClassNotFoundException ex) {
            System.out.println("Oops! an error occured when receiving object");
            JOptionPane.showMessageDialog(null, 
                    "Cannot connect to server, please try again later");
        } catch (IOException ex) {
            System.out.println("Oops! An error occured with server connection");
        } finally {
            return treeNode;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}