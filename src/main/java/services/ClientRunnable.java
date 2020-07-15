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


        String messageFromUser = "";
        while (!clientSocket.isClosed() && (messageFromUser = readerFromUser.readLine()) != null) {
            if (messageFromUser.contains("Registration ")) {
                registration(messageFromUser);
            } else if (messageFromUser.contains("Authorization ")) {
                authorization(messageFromUser);
            } else {
                break;
            }
        }

        if (!clientSocket.isClosed()) {
            do {
                System.out.println(messageFromUser);
                server.notifyObservers(messageFromUser);
            } while ((messageFromUser = readerFromUser.readLine()) != null);
        }
    }

    @SneakyThrows
    private void authorization(String messageFromUser) {
        String loginFromClient = messageFromUser.split(" ")[1];
        String passwordFromClient = messageFromUser.split(" ")[2];

        User userFromDao;
        if ((userFromDao = dao.findByName(loginFromClient)) != null) {
            if (userFromDao.getPassword().equals(passwordFromClient)) {
                client = userFromDao;
                notifyObserver("Authorization: successfully");
            } else {
                notifyObserver("Authorization failed: wrong password");
                server.deleteObserver(this);
                clientSocket.close();
            }
        } else {
            notifyObserver("Authorization failed: wrong name");
            server.deleteObserver(this);
            clientSocket.close();
        }
    }

    private void registration(String messageFromUser) {
        System.out.println("Rega");
        client = new User(messageFromUser.split(" ")[1],
                messageFromUser.split(" ")[2]);
        dao.createUser(client);
        System.out.println("Rega for " + client.getName() + " success");
        notifyObserver("Registration successfully");
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


//HW3:
//1. Отловить Authorization failed и завершить приложение с помощью System.exit(0);
//2. Доделать оставшиеся


//1. Logger (@Log4j)
//2. Сохранение переписки в файл
//3. Подгрузка переписки


//4. Интернационализация приложения (java.util.Locale) в понедельник

//5. Личные сообщения
//6. Json Gson
//7. Security Encryptor
//8. Swing Java FX прикрутить интерфейс
