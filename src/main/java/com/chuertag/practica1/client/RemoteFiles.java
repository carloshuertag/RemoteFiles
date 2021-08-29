package com.chuertag.practica1.client;

import com.chuertag.practica1.RemoteFilesProperties;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author chuertag
 */
public class RemoteFiles {

    public static void main(String[] args) {
        if (((String)(System.getProperty("os.name"))).contains("Windows"))
            RemoteFilesProperties.windows();
        JFrame jFrame = new JFrame("Remote Files");
        jFrame.add(new RemoteFilesClient(720, 480));
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(720, 480);
        jFrame.setVisible(true);
    }
}
