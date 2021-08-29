package legacy;

import com.chuertag.practica1.RemoteFilesProperties;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.tree.TreePath;

/**
 *
 * @author root
 */
public class FileExplorer extends JPanel implements ActionListener{
    JTextField jtf;
    JTree tree;
    JButton refresh;
    JLabel jtb;
    JScrollPane jsp, jspTable;
    String currDirectory = null;

    FileExplorer(String path) {
        File temp = new File(path);
        DefaultMutableTreeNode top = createTree(temp);

        jtf = new JTextField();
        refresh = new JButton("Refresh");
        // if(top!=null)
        tree = new JTree(top);
        jsp = new JScrollPane(tree);
        jtb = new JLabel("Remote Files");
        jspTable = new JScrollPane(jtb);
        setLayout(new BorderLayout());
        setLayout(new GridLayout(1,2,4,4));
        add(jtf, BorderLayout.NORTH);
        add(jsp, BorderLayout.WEST);
        add(jspTable, BorderLayout.CENTER);
        add(refresh, BorderLayout.SOUTH);
        tree.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                        doMouseClicked(me);
                }
        });
        jtf.addActionListener(this);
        refresh.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        File temp = new File(jtf.getText());
        DefaultMutableTreeNode newtop = createTree(temp);

        if (newtop != null)
            tree = new JTree(newtop);
        remove(jsp);
        jsp = new JScrollPane(tree);
        setVisible(false);
        add(jsp, BorderLayout.WEST);
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
