package dao;

import model.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    @Override
    public User findByName(String name) {
        try (Connection connection = DriverManager.getConnection("jdbc:MySQL://localhost:3306/my_schema?serverTimezone=UTC",
                "root", "12345678");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT login, password from USER");
        ) {
            while (resultSet.next()) {
//                System.out.println(resultSet.getString("login") + " ");
                if (name.equalsIgnoreCase(resultSet.getString("login"))) {
                    return new User(name, resultSet.getString("password"));
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;
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
