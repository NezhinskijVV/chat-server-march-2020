import services.MyServer;
import utils.Props;

import java.io.IOException;
import java.util.Properties;

public class MyApp {

    public static void main(String[] args) {
        new MyServer().start();

//        Properties props = new Properties();
//
//        try {
//            props.load(MyApp.class.getResourceAsStream("application.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        System.out.println("props.getProperty(\"user\") = " + Props.getValue("db.user"));

    }
}
