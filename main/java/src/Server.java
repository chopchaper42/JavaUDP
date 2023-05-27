import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(12345)) {
            Client client = new Client(
                    socket,
                    new byte[1024]
            );

            client.listen();
//            client.send();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
