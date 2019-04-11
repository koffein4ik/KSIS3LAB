import java.net.*;

import java.io.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

class TimeFromServer {

    private String host;

    private int port;

    TimeFromServer(String host, int port){

        this.host = host;

        this.port = port;

    }

    private void sendMessage(){

        try{

            byte data[] = new byte[48];
            data[0] = 0x1B;
            InetAddress addr = InetAddress.getByName("time.windows.com");

            DatagramSocket ds = new DatagramSocket();
            ds.connect(addr, 123);
            DatagramPacket pack = new DatagramPacket(data, data.length, addr, 123);
            ds.send(pack);
            DatagramPacket pack1 = new DatagramPacket(new byte[(int)48], 48);
            ds.receive(pack1);
            long a1, a2, a3, a4, a5;
            a1 = a2 = a3 = a4 = 0;
            int b = (int)(pack1.getData()[40] & 0xFF);
            a1 = b;
            a1 = a1 << 24;
            b = (int)(pack1.getData()[41] & 0xFF);
            a2 = b;
            a2 = a2 << 16;
            b = (int)(pack1.getData()[42] & 0xFF);
            a3 = b;
            a3 = a3 << 8;
            b = (int)(pack1.getData()[43] & 0xFF);
            a4 = b;
            a5 = a1 + a2 + a3 + a4;
            a5 += 10800;
            a5 *= 1000;
            Date date = new Date(a5);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateformatted =  formatter.format(date);
            System.out.println(dateformatted);

            ds.close();

        }catch(IOException e){

            System.err.println(e);

        }

    }

    public static void main(String[] args){

        TimeFromServer sndr = new TimeFromServer("timeserver.ru", 123);
        sndr.sendMessage();
    }

}