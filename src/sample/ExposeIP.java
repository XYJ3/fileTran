package sample;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ExposeIP implements Runnable {
    private DatagramSocket socket;
    private InetAddress broadcastAddress;
    //暴露停止标记
    private boolean sign=true;
    public ExposeIP(DatagramSocket socket){
        this.socket=socket;
        broadcastAddress= CommonUtils.getBroadcastAddress();
        System.out.println(broadcastAddress.getHostAddress());
    }
    @Override
    public void run(){
        String tempData="expose";
        byte[] data= new byte[0];
        try {
            data = tempData.getBytes("UTF-8");
            while(true){
                if(sign) {
                    //重新获取广播地址
                    broadcastAddress = CommonUtils.getBroadcastAddress();
                    //重新打包
                    DatagramPacket packet = new DatagramPacket(data, 0, data.length, broadcastAddress, 5555);
//                    System.out.println(packet.getAddress().getHostAddress());
                    socket.send(packet);
                    //1s一次
                    Thread.sleep(1000);
                }else{
                    //1s一次
                    Thread.sleep(1000);
                    continue;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"网络变化");
            //重新获取广播地址
            broadcastAddress=CommonUtils.getBroadcastAddress();
            //--test
            System.out.println(broadcastAddress.getHostAddress());
            //--test
            if(broadcastAddress!=null)
                this.run();
            else {
                JOptionPane.showConfirmDialog(null,"没有网络");
                System.exit(0);
            }
        }
    }
    /*
    线程开关
     */
    public void setSwitch(boolean sign){
        this.sign=sign;

    }

}
