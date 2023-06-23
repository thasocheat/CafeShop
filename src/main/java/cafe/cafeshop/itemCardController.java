package cafe.cafeshop;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class itemCardController implements Initializable {

    @FXML
    private AnchorPane item_cardForm;

    @FXML
    private Label item_name;

    @FXML
    private Label item_price;

    @FXML
    private ImageView item_imageView;

    @FXML
    private Spinner<Integer> item_spinner;

    @FXML
    private Button btn_addItem;

    private itemsData itemsData;
    private Image image;

    private String itemID;
    private String id;
    private String type;
    private String item_date;
    private String item_image;

    private SpinnerValueFactory<Integer> spin;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    private double totalP;
    private double pr;


    public void setData(itemsData itemsData) {
        this.itemsData = itemsData;

        item_image = itemsData.getImage();
        item_date = String.valueOf(itemsData.getDate());
        type = itemsData.getType();
        itemID = itemsData.getItem_id();
        item_name.setText(itemsData.getItem_name());
        item_price.setText("$" + String.valueOf(itemsData.getPrice()));
        String path = "File:" + itemsData.getImage();
        image = new Image(path, 190, 94, false, true);
        item_imageView.setImage(image);
        pr = itemsData.getPrice();

    }
    private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        item_spinner.setValueFactory(spin);
    }

    public void addBtn() {

        MainDashboardController mForm = new MainDashboardController();
        mForm.userID();

        qty = item_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM items WHERE item_id = '"
                + itemID + "'";

        connect = database.connectDB();

        try {
            int checkStck = 0;
            String checkStock = "SELECT stock FROM items WHERE item_id = '"
                    + itemID + "'";

            prepare = connect.prepareStatement(checkStock);
            result = prepare.executeQuery();

            if (result.next()) {
                checkStck = result.getInt("stock");
            }

            if(checkStck == 0){

                String updateStock = "UPDATE items SET item_name = '"
                        + item_name.getText() + "', type = '"
                        + type + "', stock = 0, price = " + pr
                        + ", status = 'Unavailable', image = '"
                        + item_image + "', date = '"
                        + item_date + "' WHERE item_id = '"
                        + itemID + "'";
                prepare = connect.prepareStatement(updateStock);
                prepare.executeUpdate();

            }

            prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if (result.next()) {
                check = result.getString("status");
            }

            if (!check.equals("Available") || qty == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong :3");
                alert.showAndWait();
            } else {

                if (checkStck < qty) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid. This product is Out of stock");
                    alert.showAndWait();
                } else {
                    item_image = item_image.replace("\\", "\\\\");

                    String insertData = "INSERT INTO customers "
                            + "(userId, item_id, item_name, type, quantity, price, date, image, Usname) "
                            + "VALUES(?,?,?,?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, String.valueOf(data.cID));
                    prepare.setString(2, itemID);
                    prepare.setString(3, item_name.getText());
                    prepare.setString(4, type);
                    prepare.setString(5, String.valueOf(qty));

                    totalP = (qty * pr);
                    prepare.setString(6, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(7, String.valueOf(sqlDate));

                    prepare.setString(8, item_image);
                    prepare.setString(9, data.username);

                    prepare.executeUpdate();

                    int upStock = checkStck - qty;



                    System.out.println("Date: " + item_date);
                    System.out.println("Image: " + item_image);

                    String updateStock = "UPDATE items SET item_name = '"
                            + item_name.getText() + "', type = '"
                            + type + "', stock = " + upStock + ", price = " + pr
                            + ", status = '"
                            + check + "', image = '"
                            + item_image + "', date = '"
                            + item_date + "' WHERE item_id = '"
                            + itemID + "'";

                    prepare = connect.prepareStatement(updateStock);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    mForm.menuGetTotal();
                    mForm.menuShowOrderData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Use spinner to get value
        setQuantity();
    }
}
