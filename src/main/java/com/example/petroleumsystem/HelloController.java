package com.example.petroleumsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class HelloController {

    @FXML
    private TextField txtUser;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox btnCheckPass;

    @FXML
    private TextField txtPassword;

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
                lblResult.setText("fill the blank field ...");
                ClearFields();
            }
            else {
                String user = txtUser.getText();
                String pass = txtHidePass.getText();
                db con = new db("select * from users where user_name = '"+user+"' and user_password = '"+pass+"' ");
                if(db.resultSet.next()){
                    dashboard.user_name = db.resultSet.getString("user_name");
                    dashboard.roll_type = db.resultSet.getString("roll_type");

                    String roll = db.resultSet.getString("roll_type");

                    if(roll.equals("user".toLowerCase())){
                        dashboard dash = new dashboard();
//                        dash.btn_dashboard.setVisible(false);

//                            dashboard.btn_users.setVisible(false);
//                            dashboard.btn_users.setVisible(false);
//                            dashboard dashboard = new dashboard();
//                            dashboard.hidenSomeButtons();
                    }
                    else {

                    }

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.resizableProperty().setValue(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
//                    stage.initStyle(StageStyle.UTILITY);
                    stage.setTitle("dashboard page ");
                    stage.setScene(scene);
                    stage.show();

//
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
    void OnLoginWithEnter(KeyEvent event) throws SQLException  {
        if(event.getCode() == KeyCode.ENTER){
            try{
                if(txtUser.getText().equals("") || txtHidePass.getText().equals("")){
                    lblResult.setText("fill the blank field ...");
                    ClearFields();
                }
                else {
                    String user = txtUser.getText();
                    String pass = txtHidePass.getText();
                    db con = new db("select * from users where user_name = '"+user+"' and user_password = '"+pass+"' ");
                    if(db.resultSet.next()){
                        dashboard.user_name = db.resultSet.getString("user_name");
                        dashboard.roll_type = db.resultSet.getString("roll_type");

                        String roll = db.resultSet.getString("roll_type");

                        if(roll.equals("user".toLowerCase())){
                            dashboard dash = new dashboard();
//                            dash.btn_dashboard.setVisible(false);

//                            dashboard.btn_users.setVisible(false);
//                            dashboard.btn_users.setVisible(false);
//                            dashboard dashboard = new dashboard();
//                            dashboard.hidenSomeButtons();
                        }
                        else {

                        }

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.resizableProperty().setValue(false);
                        stage.initStyle(StageStyle.TRANSPARENT);
//                    stage.initStyle(StageStyle.UTILITY);
                        stage.setTitle("dashboard page ");
                        stage.setScene(scene);
                        stage.show();

//
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


    @FXML
    void OnHidePassword(ActionEvent event) {
        if(btnCheckPass.isSelected()){
            txtPassword.setText(String.valueOf(txtHidePass.getText()));
            txtPassword.setVisible(true);
            txtHidePass.setVisible(false);
        }
        else {
            txtHidePass.setText(String.valueOf(txtPassword.getText()));
            txtHidePass.setVisible(true);
            txtPassword.setVisible(false);
        }
    }

    void ClearFields (){
        txtUser.clear();
        txtPassword.clear();
    }

}
