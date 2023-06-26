package com.example.petroleumsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class HelloController {

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtShowPass;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox btnCheckPass;

    @FXML
    private PasswordField txtHidePass;

    @FXML
    private Hyperlink ForgetpassLink;

    @FXML
    private Label lblResult;

    @FXML
    void OnLogin(ActionEvent event) throws SQLException {

        try{
            if(txtUser.getText().equals("") || txtHidePass.getText().equals("")){
                lblResult.setText("fill the black field ...");
                ClearFields();
            }
            else {
                String user = txtUser.getText();
                String pass = txtHidePass.getText();
                db con = new db("select * from users where user_name = '"+user+"' and user_password = '"+pass+"' ");
                if(db.resultSet.next()){
                    dashboard.user_name = db.resultSet.getString("user_name");
                    dashboard.roll_type = db.resultSet.getString("roll_type");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.resizableProperty().setValue(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
//                    stage.initStyle(StageStyle.UTILITY);
                    stage.setTitle("dashboard page ");
                    stage.setScene(scene);
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                else {
                    lblResult.setText("Invalid username or password ...");
                    ClearFields();
                }

            }


        }catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void onCheckPassword(ActionEvent event) {

        if(btnCheckPass.isSelected()){
            txtShowPass.setText(String.valueOf(txtHidePass.getText()));
            txtShowPass.setVisible(true);
            txtHidePass.setVisible(false);
        }
        else {
            txtHidePass.setText(String.valueOf(txtShowPass.getText()));
            txtHidePass.setVisible(true);
            txtShowPass.setVisible(false);
        }

    }

    @FXML
    void onLinkForgetPass(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ForgetPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.resizableProperty().setValue(false);
        stage.setTitle("Forget Password ");
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    void ClearFields (){
        txtUser.clear();
        txtHidePass.clear();
        txtShowPass.clear();
    }

}
