package project.versatile.flexidsession;

import android.util.Log;

import java.io.*;
import java.net.*;

import project.versatile.flexid.FlexID;

public class FlexIDSocket {
    private Socket socket;

    private DataInputStream dIn;
    private DataOutputStream dOut;

    public FlexIDSocket(FlexID flexid) {
        try {
            // convert FlexID to IP address.
            String IP_addr = flexid.getLocator().getAddr();

            socket = new Socket(IP_addr, flexid.getLocator().getPort());

            if((dIn = new DataInputStream(socket.getInputStream())) == null)
                System.exit(0);
            if((dOut = new DataOutputStream(socket.getOutputStream())) == null)
                System.exit(0);
        } catch(Exception e) {
            Log.getStackTraceString(e);
        }
    }
    public FlexIDSocket(Socket sock) {
        try {
            socket = sock;
            dIn = new DataInputStream(socket.getInputStream());
            dOut = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    public byte[] read() {
        try {
            int length = dIn.readInt();
//			System.out.println("received message lengeth: " + length);
            if(length > 0) {
                byte[] msg = new byte[length];
                dIn.readFully(msg, 0, msg.length);
                return msg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    public void write(byte[] msg) {
        try {
            dOut.writeInt(msg.length);
            dOut.write(msg);
            dOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    public void bind(SocketAddress bindpoint) {
        try {
            socket.bind(bindpoint);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void connect(SocketAddress endpoint) {
        try {
            socket.connect(endpoint);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}