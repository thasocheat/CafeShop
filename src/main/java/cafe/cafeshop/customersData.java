/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafe.cafeshop;

import java.sql.Date;

/**
 *
 * @author WINDOWS 10
 */
public class customersData {

    private Integer id;
    private Integer userId;
    private Double total;
    private Date date;
    private String Usname;

    public customersData(Integer id, Integer userId, Double total,
             Date date, String Usname) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.date = date;
        this.Usname = Usname;
    }

    public Integer getId() {
        return id;
    }

    public Integer getuserId() {
        return userId;
    }

    public Double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public String getUsname() {
        return Usname;
    }

}
