package LumenSerial;

import LumenSerial.Connector.Serial;

import LumenSerial.Processor.Time;
import LumenSerial.Processor.UUID;
import LumenSerial.Processor.Status;
import LumenSerial.Processor.Activations;
import LumenSerial.Processor.Activators;


public class LumenBitInterface {
    public Time time;
    public UUID uuid;
    public Status status;
    public Activations activations;
    public Activators activators;
    public boolean connected = false;

    public LumenBitInterface() {
        Serial serial = new Serial();

        if (serial.connect()) {
            System.out.println("Connected to device");
            this.setProcessors(serial);
            this.connected = true;
        } else {
            System.out.println("Failed to connect to device");
        }
    }

    public void setProcessors(Serial serial) {
        this.time = new Time(serial);
        this.uuid = new UUID(serial);
        this.status = new Status(serial);
        this.activations = new Activations(serial);
        this.activators = new Activators(serial);
    }
}
