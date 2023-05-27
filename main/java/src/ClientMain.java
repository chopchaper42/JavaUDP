import java.net.DatagramSocket;

public class ClientMain {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(12346)) {
            Client client = new Client(
                    socket,
                    new byte[1024]
            );

//            client.listen();
            client.send();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
