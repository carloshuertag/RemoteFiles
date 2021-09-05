package com.chuertag.practica1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JProgressBar;

/**
 *
 * @author chuertag
 */
public class RemoteFiles {
    
    /**
     * @param ipAddress Server IP Addressss to receive files from.
     * @param port Application port to receive the files from.
     * @param flag Whether client is going to send or receive.
     * @throws java.io.IOException If it cannot stablish connection using the given info.
     */
    public static void notifyServer(String ipAddress, int port, boolean flag)
            throws IOException{
        Socket client = new Socket(ipAddress, port + 1);
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeBoolean(flag);
        dos.flush();
        dos.close();
        client.close();
    }

    /**
     * @param filename Filename to be received from Server.
     * @param path Directory path for the file received to be saved.
     * @param who Whether its a server who is sending the files or not.
     * @param jpb JProgressBar to display receiving status.
     * @param ipAddress Server IP Addressss to receive files from.
     * @param port Application port to receive the files from.
     * @throws java.io.IOException If it cannot stablish connection using the given info.
     */
    public static void receiveFiles(String filename, String path, boolean who,
            JProgressBar jpb, String ipAddress, int port) throws IOException {
        for (;;) {
            Socket s;
            int l = 0, progress = 0, received = 0;
            if (who) {
                ServerSocket ss = new ServerSocket(port);
                ss.setReuseAddress(true);
                s = ss.accept();
            } else {
                s = new Socket(ipAddress, port);
                DataOutputStream infoDos = new DataOutputStream(
                        new Socket(ipAddress, port + 1).getOutputStream());
                infoDos.writeUTF(filename);
                infoDos.flush();
                infoDos.close();
            }
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String name = dis.readUTF();
            long size = dis.readLong();
            boolean d = dis.readBoolean();
            File file = new File(path + name);
            if (d) {
                File newDir = file.getParentFile();
                while (newDir.getPath().contains(path)) {
                    newDir.mkdirs();
                    newDir.setWritable(true);
                    newDir = newDir.getParentFile();
                }
                file.createNewFile();
            }
            System.out.println("File download starting: " + name + " with "
                    + size + " bytes");
            DataOutputStream dos = new DataOutputStream(
                    new FileOutputStream(file));
            while (received < size) {
                byte[] b = new byte[1500];
                l = dis.read(b);
                jpb.setString("Received: " + l + " bytes");
                dos.write(b, 0, l);
                dos.flush();
                received = received + l;
                progress = (int) ((received * 100) / size);
                jpb.setValue(progress);
            }
            jpb.setString("File(s) received");
            dos.close();
            dis.close();
            s.close();
        }
    }

    /**
     * @param files Files to send.
     * @param jpb JProgressBar to display sending status.
     * @param dir Whether its a dir the file is sent or not
     * @param dirChain File parent directories path.
     * @param who Whether its a server who is sending the files or not.
     * @param ipAddress Server IP Addressss to send files to.
     * @param port Application port to send the files to.
     * @throws java.io.IOException If it cannot stablish connection using the given info.
     */
    public static void sendFiles(File[] files, JProgressBar jpb, boolean dir,
            String dirChain, boolean who, String ipAddress, int port)
            throws IOException {
        for (File file : files) {
            if (file.isDirectory()) {
                dirChain += (dir) ? "/" : "";
                sendFiles(file.listFiles(), jpb, true, dirChain
                        + file.getName(), who, ipAddress, port);
            } else {
                int l = 0, progress = 0, sent = 0;
                byte[] buff;
                Socket s;
                if (who) {
                    ServerSocket ss = new ServerSocket(port);
                    ss.setReuseAddress(true);
                    s = ss.accept();
                } else {
                    s = new Socket(ipAddress, port);
                }
                DataOutputStream dos = new DataOutputStream(
                        s.getOutputStream());
                String name = (dir) ? dirChain + "/" + file.getName()
                        : file.getName();
                long size = file.length();
                DataInputStream dis = new DataInputStream(new FileInputStream(
                        file.getAbsolutePath()));
                System.out.println("Preparing to send file " + name + " of " + size
                        + " bytes to " + s.getRemoteSocketAddress());
                dos.writeUTF(name);
                dos.flush();
                dos.writeLong(size);
                dos.flush();
                dos.writeBoolean(dir);
                dos.flush();
                while (sent < size) {
                    buff = new byte[1500];
                    l = dis.read(buff);
                    jpb.setString("Sent: " + l + " bytes");
                    dos.write(buff, 0, l);
                    dos.flush();
                    sent += l;
                    progress = (int) ((sent * 100) / size);
                    jpb.setValue(progress);
                }
                jpb.setString("File(s) sent");
                dis.close();
                dos.close();
            }
        }
    }
    
    public static void setJProgressBar(JProgressBar jpb, String text){
        jpb = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        jpb.setValue(0);
        jpb.setString(text);
        jpb.setStringPainted(true);
        jpb.setSize(100, 240);
    }
}
