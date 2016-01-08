package appprofiler.appprofilerv1;

/**
 * Created by soory_000 on 11/30/2015.
 */

        import android.os.Environment;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import org.jnetpcap.Pcap;
        import org.jnetpcap.nio.JMemory;
        import org.jnetpcap.packet.JFlow;
        import org.jnetpcap.packet.JFlowKey;
        import org.jnetpcap.packet.JFlowMap;
        import org.jnetpcap.packet.JPacket;
        import org.jnetpcap.packet.JPacketHandler;
        import org.jnetpcap.packet.JScanner;
        import org.jnetpcap.packet.PcapPacket;
        import org.jnetpcap.protocol.network.Ip4;
        import org.jnetpcap.protocol.tcpip.Http;
        import org.jnetpcap.protocol.tcpip.Tcp;

public class IPExtract {
    final static List<String> ipaddress = new ArrayList<String>();
    private static String FILENAME;

    public IPExtract(String Filename) {
        this.FILENAME = Filename;//FIle Path : /storage/emulated/0/Android/data/jp.co.taosoftware.android.packetcapture/files/
    }

    public static void main(String[] args) {
     /*  ipaddress.add("139.130.4.5");
        ipaddress.add("74.125.226.22");
        ipaddress.add("69.171.230.68");
*/


      final StringBuilder errbuf = new StringBuilder();
        final Pcap pcap = Pcap.openOffline(FILENAME, errbuf);
        if (pcap == null) {
            System.err.println(errbuf);
            return;
        }
        pcap.loop(Pcap.LOOP_INFINITE, new JPacketHandler<StringBuilder>() {


            final Tcp tcp = new Tcp();

            final Ip4 ip4 = new Ip4();
            final Http http = new Http();
            public void nextPacket(JPacket packet, StringBuilder errbuf) {


                if (packet.hasHeader(Tcp.ID)) {
                    if (packet.hasHeader(tcp) && packet.hasHeader(http)) {

                        packet.getHeader(ip4);
                        byte[] sip = new byte[4];
                        sip = packet.getHeader(ip4).source();
                        byte[] dip = new byte[4];
                        dip = packet.getHeader(ip4).destination();
                        String destinationip = org.jnetpcap.packet.format.FormatUtils.ip(dip);
                        if (!(ipaddress.contains(destinationip))) {
                            ipaddress.add(destinationip);
                        }

                    }
                }
            }

        }, errbuf);
        ipaddresscollection();
     //   pcap.close();

    }

    public static List<String> ipaddresscollection() {
        return ipaddress;
    }

}
