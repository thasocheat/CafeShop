package cafe.cafeshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CafeShopManagement extends Application {

    private Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {




//        String url = "jdbc:mysql://localhost:3306/test";
//        String user = "root";
//        String password = "1234";
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connect = DriverManager.getConnection(url,user,password);
//            System.out.println("Connection is Successful to the database"+url);
////            return connect;
//        }catch (Exception e) {
//            e.printStackTrace();
//
//        }

        Parent root = FXMLLoader.load(getClass().getResource("login-form.fxml"));
////        Parent root = FXMLLoader.load(getClass().getResource("main_dashboard.fxml"));
//
//
//
        Scene scene = new Scene(root);
//
        // Set the application icon.
        stage.getIcons().add(new Image(CafeShopManagement.class.getResourceAsStream("icons8-cafe-100.png")));
        stage.setTitle("Cafe Shop Management System");
//
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
