package legacy;

import com.chuertag.practica1.RemoteFilesProperties;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author root
 */
public class Explorer {
    public static void main(String[] args) {
        if (((String)(System.getProperty("os.name"))).contains("Windows"))
            RemoteFilesProperties.windows();
        JFrame jFrame = new JFrame("Remote Files");
        jFrame.add(new FileExplorer(RemoteFilesProperties.CURRENT_ABSOLUTE_PATH
                + RemoteFilesProperties.CLIENT_DIRECTORY), "Center");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(1080, 720);
        jFrame.setVisible(true);
    }
}
