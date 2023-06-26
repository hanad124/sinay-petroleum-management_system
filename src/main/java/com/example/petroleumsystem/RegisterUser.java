package com.example.petroleumsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterUser {

    @FXML
    private Button btnAddUser;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserEmail;

    @FXML
    private TextField txtUserRollType;

    @FXML
    private TextField txtUserQuestion;

    @FXML
    private TextField txtUserAnswer;

    @FXML
    private TextField txtUserPassword;

    @FXML
    void OnAddUser(ActionEvent event) throws SQLException {

        try{
            if(txtUserName.getText().equals("") || txtUserEmail.getText().equals("") || txtUserRollType.getText().equals("") || txtUserQuestion.getText().equals("") || txtUserAnswer.getText().equals("") || txtUserPassword.getText().equals("")
            ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error occur empty fields ..");
                alert.show();
            }
            else {
                String user_name = txtUserName.getText();
                String user_email = txtUserEmail.getText();
                String roll_type = txtUserRollType.getText();
                String user_question = txtUserQuestion.getText();
                String user_answer = txtUserAnswer.getText();
                String user_password = txtUserPassword.getText();

                PreparedStatement ps =db.connection.prepareStatement("insert into users values( default ,? , ? , ? , ? , ? , ? )");
                ps.setString(1, user_name);
                ps.setString(2,user_email);
                ps.setString(3,roll_type);
                ps.setString(4,user_question);
                ps.setString(5,user_answer);
                ps.setString(6,user_password);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successfully added ....");
                alert.show();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


}

