import LumenSerial.LumenBitInterface;
import LumenSerial.Model.Response;

public class Tests {
    public static void main(String[] args) {
        LumenBitInterface lumenBit = new LumenBitInterface();

        if (lumenBit.connected) {
            testTime(lumenBit);
            testUUID(lumenBit);
            testStatus(lumenBit);
            testActivations(lumenBit);
            testActivators(lumenBit);
            testReads(lumenBit);
        } else {
            System.out.println("Failed to connect to device");
        }
    }

    public static boolean testReads(LumenBitInterface lumenBit) {
        Response response = null;

        System.out.println("\n\nTesting reads\n\n");

        response = lumenBit.activations.read();
        System.out.println(response);
        response = lumenBit.status.read();
        System.out.println(response);

        return true;
    }

    public static boolean testTime(LumenBitInterface lumenBit) {
        Response response = null;

        System.out.println("\n\nTesting Time\n\n");
        response = lumenBit.time.read();
        System.out.println(response.toString());
        response = lumenBit.time.set();
        System.out.println(response.toString());
        response = lumenBit.time.read();
        System.out.println(response.toString());

        return true;
    }

    public static boolean testUUID(LumenBitInterface lumenBit) {
        Response response = null;

        System.out.println("\n\nTest UUID\n\n");
        response = lumenBit.uuid.read();
        System.out.println(response.toString());
        response = lumenBit.uuid.set("NOTRED");
        System.out.println(response.toString());
        response = lumenBit.uuid.read();
        System.out.println(response.toString());
        response = lumenBit.uuid.set("RED");
        System.out.println(response.toString());
        response = lumenBit.uuid.read();
        System.out.println(response.toString());

        return true;
    }

    public static boolean testStatus(LumenBitInterface lumenBit) {
        Response response = null;

        System.out.println("\n\nTest Status\n\n");
        response = lumenBit.status.read();
        System.out.println(response.toString());

        return true;
    }

    public static boolean testActivations(LumenBitInterface lumenBit) {
        Response response = null;

        System.out.println("\n\nTest Activations\n\n");
        response = lumenBit.activations.read();
        System.out.println(response.toString());

        return true;
    }

    public static boolean testActivators(LumenBitInterface lumenBit) {
        Response response = null;

        System.out.println("\n\nTest Activators\n\n");
        response = lumenBit.activators.read();
        System.out.println(response.toString());
        response = lumenBit.activators.set(new String[]{"GREEN", "BLACK"});
        System.out.println(response.toString());
        response = lumenBit.activators.read();
        System.out.println(response.toString());
        response = lumenBit.activators.set(new String[]{"YELLOW", "WHITE"});
        System.out.println(response.toString());
        response = lumenBit.activators.read();
        System.out.println(response.toString());

        return true;
    }
}
