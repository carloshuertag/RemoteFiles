package com.chuertag.practica1.client.gui;

import com.chuertag.practica1.RemoteFilesProperties;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author root
 */
public class ClientFilesPanel extends JPanel implements ActionListener{
    JTextField jtf;
    JTree tree;
    JButton refresh, send;
    JPanel navbar;
    JScrollPane jsp;
    String currDirectory = null;

    public ClientFilesPanel() {
        File temp = new File(RemoteFilesProperties.CURRENT_ABSOLUTE_PATH
                + RemoteFilesProperties.CLIENT_DIRECTORY);
        DefaultMutableTreeNode top = createTree(temp);

        navbar = new JPanel(new BorderLayout());
        jtf = new JTextField(RemoteFilesProperties.CURRENT_ABSOLUTE_PATH
                + RemoteFilesProperties.CLIENT_DIRECTORY);
        refresh = new JButton("Go to the entered path");
        refresh.addActionListener(this);
        navbar.add(jtf, BorderLayout.CENTER);
        navbar.add(refresh, BorderLayout.EAST);
        tree = new JTree(top);
        jsp = new JScrollPane(tree);
        
        setLayout(new BorderLayout());
        send = new JButton("Send file(s)");
        add(navbar, BorderLayout.NORTH);
        add(jsp, BorderLayout.CENTER);
        add(send, BorderLayout.SOUTH);
        tree.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                        doMouseClicked(me);
                }
        });
        jtf.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        File temp = new File(jtf.getText());
        DefaultMutableTreeNode newtop = createTree(temp);
        if (newtop != null)
            tree = new JTree(newtop);
        remove(navbar);
        remove(jsp);
        remove(refresh);
        jsp = new JScrollPane(tree);
        setVisible(false);
        add(navbar, BorderLayout.NORTH);
        add(jsp, BorderLayout.CENTER);
        add(send, BorderLayout.SOUTH);
        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                doMouseClicked(me);
            }
        });
        setVisible(true);
    }

    DefaultMutableTreeNode createTree(File temp) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(temp.getPath());
        fillTree(top, temp.getPath());

        return top;
    }

    void fillTree(DefaultMutableTreeNode root, String filename) {
            File temp = new File(filename);

            if (!(temp.exists() && temp.isDirectory()))
                return;
            File[] filelist = temp.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                final DefaultMutableTreeNode tempDmtn = new DefaultMutableTreeNode(
                        filelist[i].getName());
                root.add(tempDmtn);
                final String newfilename = new String(filename +
                        RemoteFilesProperties.SLASH + filelist[i].getName());
                Thread t = new Thread() {
                    public void run() {
                        fillTree(tempDmtn, newfilename);
                    }
                };
                if (t == null) {
                    System.out.println("no more thread allowed " + newfilename);
                    return;
                }
                t.start();
            }
    }

    void doMouseClicked(MouseEvent me) {
        TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
        if (tp == null)
            return;
        String s = tp.toString();
        s = s.replace("[", "");
        s = s.replace("]", "");
        s = s.replace(", ", RemoteFilesProperties.SLASH);
        jtf.setText(s);
    }
}
