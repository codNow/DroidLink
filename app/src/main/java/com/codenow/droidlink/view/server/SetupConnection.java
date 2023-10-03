package com.codenow.droidlink.view.server;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class SetupConnection {
    public static String getDeviceIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddressInt = wifiInfo.getIpAddress();

        // Convert IP address integer to a formatted string
        String ipAddress = ((ipAddressInt >> 0) & 0xFF) + "." +
                ((ipAddressInt >> 8) & 0xFF) + "." +
                ((ipAddressInt >> 16) & 0xFF) + "." +
                ((ipAddressInt >> 24) & 0xFF);

        return ipAddress;
    }

    public static void connectToServer(Context context, String serverAddress) {// Replace with the server's IP address
        int serverPort = 44345; // Replace with the server's port number

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            Toast.makeText(context, "Connected to server: " + socket.getInetAddress(), Toast.LENGTH_SHORT).show();

            // Implement your client-side logic here
            // You can exchange data with the server

            socket.close();
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
