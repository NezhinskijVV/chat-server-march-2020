package services;

import dao.UserDao;
import dao.UserDaoImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@RequiredArgsConstructor
public class ClientRunnable implements Runnable, Observer {
    private final Socket clientSocket;
    private final MyServer server;
    private User client;
    private final UserDao dao = new UserDaoImpl();

    @SneakyThrows
    @Override
    public void run() {
        server.addObserver(this);
        BufferedReader readerFromUser = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String messageFromUser;
        while ((messageFromUser = readerFromUser.readLine()) != null) {
//            if (messageFromUser.contains("Registration ")) {
//                System.out.println("Rega");
//                client = new User(messageFromUser.split(" ")[1],
//                        messageFromUser.split(" ")[2]);
//                System.out.println("Rega for " + client.getName() + " success");
//                notifyObserver("Registration successfully");
//            }
            if (messageFromUser.contains("Authorization ")) {
                String loginFromClient = messageFromUser.split(" ")[1];
                String passwordFromClient = messageFromUser.split(" ")[2];

                User userFromDao = dao.findByName(loginFromClient);

                if (userFromDao.getPassword().equals(passwordFromClient)) {
                    client = userFromDao;
                    notifyObserver("Authorization: successfully");
                } else {
                    notifyObserver("Authorization: wrong password");
                }
            } else {
                System.out.println(messageFromUser);
                server.notifyObservers(messageFromUser);
            }
        }
    }

    @SneakyThrows
    @Override
    public void notifyObserver(String message) {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
//        if (client != null) {
            printWriter.println(message);
            printWriter.flush();
//        }
    }

}

//HW:
//1. Регистрацию выделить в два цикла
//2. Optional Java 8
//3. Service с BufferedReader(InputStreamReader) аналог MessageReceiver