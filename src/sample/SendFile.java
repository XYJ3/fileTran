package sample;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import sample.dialogs.WarningDialog;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SendFile implements Runnable {
    private File selectedFile;

    private Socket socket;
    private DataOutputStream dataOutputStream;

    private DataInputStream dataInputStream, stateInputStream;
    private InetSocketAddress address;
    private ProgressBar progressBar;
    private ProgressIndicator progressIndicator;
    //等待操作框
//    private Load loadDialog;
    public SendFile(File selectedFile, InetSocketAddress address) {
        super();
        this.selectedFile = selectedFile;
        this.address = address;
        socket = new Socket();
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[10240];
            //0-为无限等待
            socket.setSoTimeout(0);
            //TCP连接--设置了连接超时
            socket.connect(address, 3000);
            //接收目标状态信息流
            stateInputStream = new DataInputStream(socket.getInputStream());
//            loadDialog=new LoadDialog();
//            loadDialog.setVisible(true);
            int responseCode = stateInputStream.readInt();
//            loadDialog.dispose();
            //判断是否可发送
            if (responseCode == 1) {
                //设置进度条最大值--file.length--字节
                long size = selectedFile.length();
                System.out.println(size + ":" + (int) size);
                //本机文件流
                dataInputStream = new DataInputStream(new FileInputStream(selectedFile));
                //文件发送流
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                //发送文件名
                dataOutputStream.writeUTF(selectedFile.getName());
                //文件大小
                dataOutputStream.writeLong(size);
                int len = 0;
                MyProgress.progressBar.setVisible(true);
                while ((len = dataInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, len);
                    System.out.println(len / size * 100);
                    MyProgress.progressBar.setProgress(len / size * 100);
                }
                dataInputStream.close();
                dataOutputStream.close();
                JOptionPane.showMessageDialog(null, "发送成功");
                MyProgress.progressBar.setVisible(false);
            } else {
                //关闭状态输入流-----注意，此处会关闭socket
                stateInputStream.close();
                new WarningDialog("对方拒收");
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "发送失败");
            progressBar.setVisible(false);
        }
    }

}
