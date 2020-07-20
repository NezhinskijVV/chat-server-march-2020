package dao;

import lombok.SneakyThrows;
import model.User;
import utils.Props;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    private final static String DB_URL = Props.getValue("db.url");
    private final static String DB_USER = Props.getValue("db.user");
    private final static String DB_PASSWORD = Props.getValue("db.password");

    @SneakyThrows
    @Override
    public User findByName(String name) {
        ResultSet resultSet = getStatement().executeQuery("SELECT login, password from USER");
        while (resultSet.next()) {
            if (name.equalsIgnoreCase(resultSet.getString("login"))) {
                return new User(name, resultSet.getString("password"));
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void createUser(User user) {
        getStatement().executeUpdate("INSERT INTO USER(login, password) VALUES ('"
                + user.getName() + "','" + user.getPassword() + "') ");
    }

    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement();
    }
}

//HW:
//1.USER CRUD
//  UserDAO
//  findByName (сделали)
//  setName setPassword
//  createUser
//  deleteUser

//3. Создать таблицу: Message (id, text, idUser)
//4. В пакете модель создать класс Message()

//5. Создать Dao для сообщения (запись и чтение сообщения в базу)

//6* Unit test к UserDaoImpl
