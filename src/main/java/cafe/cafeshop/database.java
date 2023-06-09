package cafe.cafeshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class database {

//    Connect to MYSQL database
    public static Connection connectDB() {

        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "1234";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url,user,password);
            System.out.println("Connection is Successful to the database"+url);
            return connect;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }catch (Exception e) {
                 e.printStackTrace();

        }

        return connectDB();

        // try {
        //
        //     Class.forName("com.mysql.jdbc.Driver");
        //
        //     // LINK YOUR DATABASE
        //     Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        //     return connect;
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // return null;

    }


}
