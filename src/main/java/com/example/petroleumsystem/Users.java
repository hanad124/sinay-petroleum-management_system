package com.example.petroleumsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Users implements Initializable {

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
    private Button btnSave;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<UserClass> userTable;

    @FXML
    private TableColumn<UserClass, Integer> userId;

    @FXML
    private TableColumn<UserClass, String> userName;

    @FXML
    private TableColumn<UserClass, String> userEmail;

    @FXML
    private TableColumn<UserClass, String> userRollType;

    @FXML
    private TableColumn<UserClass, String> userQuestion;

    @FXML
    private TableColumn<UserClass, String> userAnswer;

    @FXML
    private TableColumn<UserClass, String> userPassword;

    ObservableList<UserClass> list = FXCollections.observableArrayList();


    @FXML
    void getTableOfData(MouseEvent event) {

       UserClass userClass =  userTable.getSelectionModel().getSelectedItem();
       txtUserName.setText(userClass.getUser_name());
       txtUserEmail.setText(userClass.getEmail());
       txtUserRollType.setText(userClass.getRoll_type());
       txtUserQuestion.setText(userClass.getUser_question());
       txtUserAnswer.setText(userClass.getUser_answer());
       txtUserPassword.setText(userClass.getUser_password());
       btnEdit.setVisible(true);
       btnDelete.setVisible(true);

    }

    @FXML
    void OnRefresh(ActionEvent event) throws SQLException {
        BinddataView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BinddataView();
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

   public void BinddataView () throws SQLException {
        userTable.getItems().clear();
        userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        userId.setCellValueFactory(new PropertyValueFactory<UserClass , Integer>("id"));
        userName.setCellValueFactory(new PropertyValueFactory<UserClass , String>("user_name"));
        userEmail.setCellValueFactory(new PropertyValueFactory<UserClass , String>("email"));
        userRollType.setCellValueFactory(new PropertyValueFactory<UserClass , String>("roll_type"));
        userQuestion.setCellValueFactory(new PropertyValueFactory<UserClass , String>("user_question"));
        userAnswer.setCellValueFactory(new PropertyValueFactory<UserClass ,String>("user_answer"));
        userPassword.setCellValueFactory(new PropertyValueFactory<UserClass , String>("user_password"));
        db data = new db("select * from users");
        while (db.resultSet.next()){
            list.addAll(new UserClass(db.resultSet.getInt("id"),
                    db.resultSet.getString("user_name"),
                    db.resultSet.getString("email"),
                    db.resultSet.getString("roll_type"),
                    db.resultSet.getString("user_Question"),
                    db.resultSet.getString("user_answer"),
                    db.resultSet.getString("user_password")
            ));
        }
        userTable.setItems(list);

    }

    @FXML
    void OnSearchPress(KeyEvent event) throws SQLException {
        if(event.getCode()== KeyCode.ENTER){
            userTable.getItems().clear();
            userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            userId.setCellValueFactory(new PropertyValueFactory<UserClass , Integer>("id"));
            userName.setCellValueFactory(new PropertyValueFactory<UserClass , String>("user_name"));
            userEmail.setCellValueFactory(new PropertyValueFactory<UserClass , String>("email"));
            userRollType.setCellValueFactory(new PropertyValueFactory<UserClass , String>("roll_type"));
            userQuestion.setCellValueFactory(new PropertyValueFactory<UserClass , String>("user_question"));
            userAnswer.setCellValueFactory(new PropertyValueFactory<UserClass ,String>("user_answer"));
            userPassword.setCellValueFactory(new PropertyValueFactory<UserClass , String>("user_password"));
            int searchById = Integer.parseInt(txtSearch.getText());
            db data = new db("select * from users where id = '"+searchById+"'");
            while (db.resultSet.next()){
                try {
                    list.addAll(new UserClass(db.resultSet.getInt("id"),
                            db.resultSet.getString("user_name"),
                            db.resultSet.getString("email"),
                            db.resultSet.getString("roll_type"),
                            db.resultSet.getString("user_Question"),
                            db.resultSet.getString("user_answer"),
                            db.resultSet.getString("user_password")
                    ));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            userTable.setItems(list);

        }

    }
    @FXML
    void OnSaveUser(ActionEvent event) throws SQLException , IOException {

        try{
            if(txtUserName.getText().equals("") || txtUserEmail.getText().equals("") || txtUserRollType.getText().equals("") || txtUserQuestion.getText().equals("") || txtUserAnswer.getText().equals("") || txtUserPassword.getText().equals("")
            ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("error occur empty fields ..");
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
                ClearData();
                BinddataView();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save...");
                alert.setContentText("successfully Saved user...");
                alert.show();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void OnEditUser(ActionEvent event) throws SQLException , IOException {

        try{
            if(txtUserName.getText().equals("") || txtUserEmail.getText().equals("") || txtUserRollType.getText().equals("") || txtUserQuestion.getText().equals("") || txtUserAnswer.getText().equals("") || txtUserPassword.getText().equals("")
            ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error occur empty fields ..");
                alert.show();
            }
            else {
                int myIndex =userTable.getSelectionModel().getSelectedIndex();
                int id = Integer.parseInt(String.valueOf(userTable.getItems().get(myIndex).getId()));
                String user_name = txtUserName.getText();
                String user_email = txtUserEmail.getText();
                String roll_type = txtUserRollType.getText();
                String user_question = txtUserQuestion.getText();
                String user_answer = txtUserAnswer.getText();
                String user_password = txtUserPassword.getText();

                PreparedStatement ps =db.connection.prepareStatement(" UPDATE users SET user_name = ? , email = ? , roll_type = ? , user_question = ? , user_answer = ? , user_password = ?  where id = ? ");
                ps.setString(1, user_name);
                ps.setString(2,user_email);
                ps.setString(3,roll_type);
                ps.setString(4,user_question);
                ps.setString(5,user_answer);
                ps.setString(6,user_password);
                ps.setInt(7,id);
                ps.executeUpdate();
                ClearData();
                BinddataView();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit...");
                alert.setContentText("successfully updated user...");
                alert.show();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void OnDeleteUser(ActionEvent event) throws SQLException {

      try{
          int myIndex =userTable.getSelectionModel().getSelectedIndex();
          int id = Integer.parseInt(String.valueOf(userTable.getItems().get(myIndex).getId()));
          PreparedStatement ps =db.connection.prepareStatement(" delete from users where id = ? ");
          ps.setInt(1,id);
          ps.executeUpdate();
          ClearData();
          BinddataView();
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Delete...");
          alert.setContentText("successfully Deleted user...");
          alert.show();
      }catch (Exception e){
          System.out.println(e.getMessage());
      }

    }

    void ClearData(){
        txtUserName.clear();
        txtUserEmail.clear();
        txtUserRollType.clear();
        txtUserQuestion.clear();
        txtUserAnswer.clear();
        txtUserPassword.clear();
    }

}

