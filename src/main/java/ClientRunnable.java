import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@AllArgsConstructor
public class ClientRunnable implements Runnable {
    private final Socket clientSocket;

    @SneakyThrows
    @Override
    public void run() {
        BufferedReader readerFromUser = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

        String messageFromUser;
        while ((messageFromUser = readerFromUser.readLine()) != null) {
            System.out.println(messageFromUser);

            printWriter.println(messageFromUser);
            printWriter.flush();
        }
    }
}
