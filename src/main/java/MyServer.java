import lombok.SneakyThrows;

import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static final int PORT = 8080;


    @SneakyThrows
    public void start() {
        ServerSocket serverSocket = new ServerSocket(PORT);

        while(true){
            Socket socket = serverSocket.accept();

            if (socket != null){
                new Thread(new ClientRunnable(socket)).start();
            }
        }
    }
}
