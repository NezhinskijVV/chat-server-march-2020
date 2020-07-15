package dao;

import model.User;

public interface UserDao {
    User findByName(String name);
    void createUser(User user);

//    void setUserName(User user, String name);
}