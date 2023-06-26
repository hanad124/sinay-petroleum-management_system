package com.example.petroleumsystem;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;

public class UserClass {

    private int id ;
    private String user_name;
    private String email;
    private String roll_type;
    private String user_question;
    private String user_answer;
    private String user_password;
    private Button button;

    public  UserClass(int id , String user_name , String email , String roll_type , String user_question , String user_answer , String user_password){

        this.id = id ;
        this.user_name = user_name;
        this.email = email;
        this.roll_type = roll_type;
        this.user_question = user_question;
        this.user_answer = user_answer ;
        this.user_password = user_password;
    }

    public int getId() {

        return id;
    }

    public String getUser_name() {

        return user_name;
    }

    public String getEmail() {
        return email;
    }

    public String getRoll_type() {

        return roll_type;
    }

    public String getUser_question() {

        return user_question;
    }

    public String getUser_answer() {

        return user_answer;
    }

    public String getUser_password() {

        return user_password;
    }

    public Button getButton() {
        return button;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_name(String user_name) {

        this.user_name = user_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoll_type(String roll_type) {

        this.roll_type = roll_type;
    }

    public void setUser_question(String user_question) {
        this.user_question = user_question;
    }

    public void setUser_answer(String user_answer) {

        this.user_answer = user_answer;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
