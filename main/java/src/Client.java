import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Client extends Thread{

    private static final int LISTENING_PORT = 12345;
    private static final int SENDING_PORT = 12346;

    private boolean running = false;
    private DatagramSocket socket;
    private DatagramPacket packet;
    InetAddress friend;
    private Scanner scanner = new Scanner(System.in);

    public Client(InetAddress address, byte[] buffer) {
//        this.socket = new DatagramSocket(); TODO: create one socket for listening and one for sending
        this.packet = new DatagramPacket(buffer, buffer.length);
    }

    @Override
    public void run() {
        super.run();
        running = true;
        chooseCreateOrConnect();
        try {
            listen();
        } catch (IOException e) {
            throw new RuntimeException("Exception while listening on port. " + e.getMessage());
        }
    }

    public void connectToGame() {
        System.out.println("Enter the ip address: ");
        String ip = scanner.nextLine();
        try {
            InetAddress address = InetAddress.getByName(ip);
            connect(address);
        } catch (UnknownHostException e) {
            stopRunning();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void connect(InetAddress address) {

    }

    public void startGame() {
        System.out.println("Game started! Now wait for your friend.");
        try {
            listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send() {
        if (friend == null) {
            System.out.println("Enter recipient address:");
            String ip = scanner.nextLine();
            try {
                friend = InetAddress.getByName(ip);
                packet.setAddress(friend);
                packet.setPort(SENDING_PORT);
            } catch (UnknownHostException e) {
                System.out.println("Bad IP!");
                return;
            }
        }
        System.out.println("Enter your message:");
        String message = scanner.nextLine();
        packet.setData(message.getBytes(StandardCharsets.UTF_8));
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Unable to send message.\n" + e.getMessage());
        }
    }

    public void listen() throws IOException {
//        while (running) {
            socket.receive(packet);
            System.out.println("MESSAGE: " + Arrays.toString(packet.getData()));
            if (friend == null)
                friend = InetAddress.getByName(packet.getAddress().toString());
//        }
    }

    private void chooseCreateOrConnect() {
        boolean choose = true;

        System.out.println("1 - Create the game. 2 - Connect to the game.");

        while (choose) {
            switch (scanner.nextInt()) {
                case 1:
                    startGame();
                    choose = false;
                    break;
                case 2:
                    connectToGame();
                    choose = false;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private void stopRunning() {
        running = false;
    }

    private void startRunning() {
        running = true;
    }
}
