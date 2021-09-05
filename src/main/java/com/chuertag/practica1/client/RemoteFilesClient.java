package com.chuertag.practica1.client;

import com.chuertag.practica1.client.gui.RemoteFilesClientPanel;
import com.chuertag.practica1.RemoteFilesProperties;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author chuertag
 */
public class RemoteFilesClient {

    public static void main(String[] args) {
        if ((System.getProperty("os.name")).contains("Windows")) {
            RemoteFilesProperties.windows();
        }
        JFrame jFrame = new JFrame("Remote Files");
        jFrame.add(new RemoteFilesClientPanel(1280, 720));
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(1280, 720);
        jFrame.setVisible(true);
    }
}
