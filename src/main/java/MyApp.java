import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import services.MyServer;

public class MyApp {

    public static void main(String[] args) {
        new MyServer().start();

//        UserDao dao = new UserDaoImpl();
//
//        dao.createUser(new User("1","2"));

    }
}
