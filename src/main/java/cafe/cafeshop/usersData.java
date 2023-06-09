package cafe.cafeshop;

import java.sql.Date;

public class usersData {

    private Integer id;
    private String username;
    private String password;
    private String question;
    private String answer;
    private static Date date;


    public usersData(Integer id, String username, String password,
                     String question, String answer, Date date) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.date = date;
    }




    public usersData() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
