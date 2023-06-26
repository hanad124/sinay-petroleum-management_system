package com.example.petroleumsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Employee implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnRefresh;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtAddress;

    @FXML
    private TableView<EmployeeClass> TableViewInfo;
    ObservableList<EmployeeClass> list = FXCollections.observableArrayList();

    @FXML
    private TableColumn<EmployeeClass, Integer> id;

    @FXML
    private TableColumn<EmployeeClass, String> name;

    @FXML
    private TableColumn<EmployeeClass, Integer> phone;

    @FXML
    private TableColumn<EmployeeClass, String> address;


    @FXML
    void newEmployee(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createEmployee.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.resizableProperty().setValue(false);
        stage.setTitle("Create New Employee ");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void OnDelete(ActionEvent event) {

        try{
            int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
            int id = Integer.parseInt(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
//            db con = new db("delete from employee where id = '"+id+"' ");
            PreparedStatement ps = db.connection.prepareStatement("delete from employee where id = ? ");
            ps.setInt(1,id);
            ps.executeUpdate();
            FetchData();
            ClearData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE");
            alert.setContentText("successfully deleted ...");
            alert.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void OnEdit(ActionEvent event) {
        try{
            if(txtName.getText().equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Inputs fields should not is a empty ? ...");
                alert.show();
            }
            else {
                String name = txtName.getText();
                int phone = Integer.parseInt(txtPhone.getText());
                String address = txtAddress.getText();
                int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
                int id = Integer.parseInt(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
                PreparedStatement ps = db.connection.prepareStatement("update employee set name = ? , phone = ? , address = ?  where id = ? ");
                ps.setString(1,name);
                ps.setInt(2,phone);
                ps.setString(3,address);
                ps.setInt(4,id);
                ps.executeUpdate();
                FetchData();
                ClearData();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("EDIT");
                alert.setContentText("successfully Updated ...");
                alert.show();

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void OnRefresh(ActionEvent event) throws SQLException {
        try{
            FetchData();
            ClearData();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onSave(ActionEvent event) throws SQLException {
        if(txtName.getText().equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Input fields is a empty or one of them ...");
            alert.show();
        }else {
            String name = txtName.getText();
            int phone = Integer.parseInt(txtPhone.getText());
            String address = txtAddress.getText();
            PreparedStatement ps = db.connection.prepareStatement("insert into employee values(default , ? , ? , ?)");
            ps.setString(1,name);
            ps.setInt(2,phone);
            ps.setString(3,address);
            ps.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SAVE");
            alert.setContentText("successfully saved ...");
            alert.show();
            FetchData();
            ClearData();

        }
    }

    @FXML
    void OnSearchPress(KeyEvent event) throws SQLException {
        if(event.getCode()==KeyCode.ENTER){
            TableViewInfo.getItems().clear();
            TableViewInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            id.setCellValueFactory(new PropertyValueFactory<EmployeeClass , Integer>("id"));
            name.setCellValueFactory(new PropertyValueFactory<EmployeeClass , String>("name"));
            phone.setCellValueFactory(new PropertyValueFactory<EmployeeClass , Integer>("phone"));
            address.setCellValueFactory(new PropertyValueFactory<EmployeeClass , String>("address"));
            int Search = Integer.parseInt(txtSearch.getText());
            db con = new db("select * from employee where id = '"+Search+"' ");
            while (db.resultSet.next()){
                list.addAll(new EmployeeClass(
                        db.resultSet.getInt("id"),
                        db.resultSet.getString("name"),
                        db.resultSet.getInt("phone"),
                        db.resultSet.getString("address")
                ));
                TableViewInfo.setItems(list);
            }
        }

    }

    @FXML
    void getTableOfData(MouseEvent event) {
        EmployeeClass employeeClass = TableViewInfo.getSelectionModel().getSelectedItem();
        txtName.setText(employeeClass.getName());
        txtPhone.setText(String.valueOf(employeeClass.getPhone()));
        txtAddress.setText(employeeClass.getAddress());

    }

    void FetchData() throws SQLException {
        try{
            TableViewInfo.getItems().clear();
            TableViewInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            id.setCellValueFactory(new PropertyValueFactory<EmployeeClass , Integer>("id"));
            name.setCellValueFactory(new PropertyValueFactory<EmployeeClass, String>("name"));
            phone.setCellValueFactory(new PropertyValueFactory<EmployeeClass , Integer>("phone"));
            address.setCellValueFactory(new PropertyValueFactory<EmployeeClass , String>("address"));
            db con = new db("select * from employee");
            while (db.resultSet.next()){
                list.addAll(new EmployeeClass(
                        db.resultSet.getInt("id"),
                        db.resultSet.getString("name"),
                        db.resultSet.getInt("phone"),
                        db.resultSet.getString("address")
                ));
                TableViewInfo.setItems(list);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FetchData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    void ClearData(){
        txtSearch.clear();
        txtName.clear();
        txtPhone.clear();
        txtAddress.clear();
    }
}
