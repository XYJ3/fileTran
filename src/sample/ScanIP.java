package sample;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ScanIP implements Runnable {
    private DatagramSocket socket;
    //启动标记---默认启动
    private boolean sign = true;

    public ScanIP(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (sign) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 0, 1024);
                socket.receive(packet);
                String temp = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                //如果检测到暴露线程
                if (temp.equals("expose")) {
                    String strIP = packet.getAddress().getHostAddress();
                    //如果集合内不包含此ip，则添加至数据集合
                    if (!IpList.ipList.contains(strIP) && !strIP.equals(CommonUtils.getIPV4Address().getHostAddress()))
                        IpList.ipList.add(strIP);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
