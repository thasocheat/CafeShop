package cafe.cafeshop;

import java.sql.Date;

public class itemsData {

    private Integer id;

    private String item_id;
    private String item_name;
    private String type;
    private Integer stock;
    private Double price;
    private String status;
    private String image;
    private Date date;
    private Integer quantity;


    public itemsData(Integer id, String item_id,
                       String item_name, String type, Integer stock,
                       Double price, String status, String image, Date date) {
        this.id = id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }

    public itemsData(Integer id, String item_id,
                     String item_name, String type, Integer quantity,
                     Double price, String image, Date date){
        this.id = id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.type = type;
        this.price = price;
        this.image = image;
        this.date = date;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getType(){
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Integer getQuantity(){
        return quantity;
    }
}
