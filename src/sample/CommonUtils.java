package sample;

import javax.swing.*;
import java.net.*;
import java.util.Enumeration;

public class CommonUtils {


    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(CommonUtils.getIPV4Address());
    }

    public static InetAddress getBroadcastAddress(){

        byte[] address=getIPV4Address().getAddress();
        //将IP的最后一字节转为255（IPV4字节）
        address[3]=(byte)255;
        //hostAddress.
        //返回该IP对象
        try {
            return InetAddress.getByAddress(address);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static InetAddress getIPV4Address(){
        try {
            //获取所有网络接口
            Enumeration<NetworkInterface> allNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            //遍历所有网络接口
            for(;allNetworkInterfaces.hasMoreElements();){
                NetworkInterface networkInterface=allNetworkInterfaces.nextElement();
                //如果此网络接口为 回环接口 或者 虚拟接口(子接口) 或者 未启用 或者 描述中包含VM
                if(networkInterface.isLoopback()||networkInterface.isVirtual()||!networkInterface.isUp()||networkInterface.getDisplayName().contains("VM")){
                    //继续下次循环
                    continue;
                }
                //如果不是Intel与Realtek的网卡
//                if(!(networkInterface.getDisplayName().contains("Intel"))&&!(networkInterface.getDisplayName().contains("Realtek"))){
//                         //继续下次循环
//                            continue;
//                }
                //遍历此接口下的所有IP（因为包括子网掩码各种信息）
                for(Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses(); inetAddressEnumeration.hasMoreElements();){
                    InetAddress inetAddress=inetAddressEnumeration.nextElement();
                    //如果此IP不为空
                    if(inetAddress!=null){
                        //如果此IP为IPV4 则返回
                        if(inetAddress instanceof Inet4Address){

                            return inetAddress;
                        }
                        /*
                       // -------这样判断IPV4更快----------
                        if(inetAddress.getAddress().length==4){
                            return inetAddress;
                        }
                         */

                    }
                }


            }
            JOptionPane.showMessageDialog(null,"d");
            System.exit(0);
            return null;

        }catch(SocketException e){
            e.printStackTrace();
            return null;
        }
    }
}
