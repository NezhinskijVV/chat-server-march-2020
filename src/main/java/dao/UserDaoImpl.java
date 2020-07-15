package dao;

import lombok.SneakyThrows;
import model.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    private final String DB_URL = "jdbc:MySQL://localhost:3306/my_schema?serverTimezone=UTC";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "12345678";

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
