package LumenSerial.Connector;

import LumenSerial.Constants.Messages;
import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Serial {
    private SerialPort port;
    private final Listener listener = new Listener();
    private Queue<String> buffer = new LinkedList<>();


    public void sendMessage(String message) {
        this.port.writeBytes(message.getBytes(), message.length());
    }

    public Queue<String> readBuffer() {
        String[] array = this.listener.getMessages();
        this.buffer.addAll(Arrays.asList(array));

        return this.buffer;
    }

    public boolean connect() {
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Available ports:");
        for (SerialPort port : ports) {
            System.out.printf("- %-5s = %s%n", port.getSystemPortName(), port.getDescriptivePortName());
        }
        for (SerialPort port: ports) {
            String portDesc = port.getDescriptivePortName();
            if (portDesc.contains("USB Serial Device") || portDesc.contains("Serieel USB-apparaat")) {
                return this.attemptConnection(port);
            }
        }

        return false;
    }

    public boolean disconnect(){
        try {
            this.port.closePort();
            this.port = null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean openPort(SerialPort serialPort){
        try {
            serialPort.openPort();
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 1000);
            serialPort.setBaudRate(115200);
            serialPort.setNumDataBits(255);
            serialPort.setParity(SerialPort.ODD_PARITY);
            serialPort.addDataListener(this.listener);
            this.port = serialPort;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean attemptConnection(SerialPort serialPort){
        if (this.openPort(serialPort)) {
            System.out.println("Connected to " + serialPort.getDescriptivePortName());
            this.sendMessage(Messages.GetTestConnection());
            System.out.println("Sent test message");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[] messages = this.listener.getMessages();
            System.out.println("Received messages:");
            for (String message : messages) {
                System.out.println(message);
                if (message.contains("ACK")) {
                    System.out.println("Microbit found on port: " + serialPort.getSystemPortName());
                    this.port = serialPort;
                    return true;
                }
            }
        }

        System.out.println("Microbit not found on port: " + serialPort.getSystemPortName());
        return false;
    }
}
