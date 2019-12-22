package sample;

import sample.dialogs.ConfirmDialog;
import sample.dialogs.WarningDialog;

import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAccept implements Runnable {
    private ServerSocket serverSocket;
    public ServerAccept(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }
    @Override
    public void run(){
        try {
            while (true) {
                Socket socket=serverSocket.accept();
                //状态信息流
                DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
                int state = new ConfirmDialog(socket.getInetAddress().getHostAddress()).show();
                if(state==1) {
                    dataOutputStream.writeInt(1);
                    //启动接收线程
                    new Thread(new AcceptFile(socket)).start();
                }
                else
                    dataOutputStream.writeInt(0);
            }
        }catch(Exception e){
            e.printStackTrace();
            new WarningDialog("失败");
            this.run();
        }

    }
}
