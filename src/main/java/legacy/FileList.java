package legacy;

import java.awt.Component;
import java.io.File;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author chuertag
 */
public class FileList {
    public Component getGui(File[] all, boolean vertical, ListSelectionListener listener) {
        // put File objects in the list..
        JList fileList = new JList(all);
        fileList.addListSelectionListener(listener);
        // then use a renderer
        fileList.setCellRenderer(new FileRenderer(!vertical));
        if (!vertical) {
            fileList.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
            fileList.setVisibleRowCount(-1);
        } else {
            fileList.setVisibleRowCount(9);
        }
        
        return new JScrollPane(fileList);
    }
}
