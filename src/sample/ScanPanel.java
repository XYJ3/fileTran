package sample;

import java.net.DatagramSocket;
import java.net.SocketException;

public class ScanPanel {
    public ScanPanel() throws SocketException {
        DatagramSocket socket = new DatagramSocket(5555);
        Thread expose= new Thread(new ExposeIP(socket),"Expose");
        Thread scan= new Thread(new ScanIP(socket),"scan");
        expose.start();
        scan.start();
    }
}
