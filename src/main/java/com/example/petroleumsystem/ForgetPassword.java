package com.example.petroleumsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ForgetPassword {

    @FXML
    private TextField txtUser;

    @FXML
    private TextField ReadQuestion;

    @FXML
    private Button btnGetPassword;

    @FXML
    private Hyperlink goToLoginLink;

    @FXML
    private Label lblPrintPass;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button userQuestion;

    @FXML
    void OnGetPassword(ActionEvent event) throws SQLException {
        if(txtPassword.getText().equals("")){
            lblPrintPass.setText("fill the empty fields ....");
            lblPrintPass.setTextFill(Paint.valueOf("red"));
        }
        else {
            String user_question = ReadQuestion.getText();
            db con = new db("select user_answer , user_password from users where user_question = '"+user_question+"' ");
            if(db.resultSet.next()){
                String user_answer = db.resultSet.getString("user_answer");
                System.out.println(user_answer);
                if(user_answer.contains(txtPassword.getText())){
                    lblPrintPass.setText(db.resultSet.getString("user_password"));
                    ClearData ();
                }
                else {
                    lblPrintPass.setText("invalid answer ");
                    lblPrintPass.setTextFill(Paint.valueOf("red"));
                }

            }

        }

    }

    @FXML
    void getUserQuestion(ActionEvent event) throws SQLException {
        if(txtUser.getText().equals("")){
            lblPrintPass.setText("fill the empty fields ....");
            lblPrintPass.setTextFill(Paint.valueOf("red"));
        }
        else {
            String user = txtUser.getText();
            String pass = txtPassword.getText();
            db con = new db("select user_question , user_answer , user_password from users where user_name = '"+user+"' ");
            if(db.resultSet.next()){
                ReadQuestion.setText(db.resultSet.getString("user_question"));
            }

        }
    }

    @FXML
    void onGoToLogin(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Login Page ");
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    void ClearData (){
        txtUser.clear();
        txtPassword.clear();
        ReadQuestion.clear();
    }

}

