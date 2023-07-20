package com.example.petroleumsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Customer implements Initializable {

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
    private TableView<CustomerClass> TableViewInfo;

    @FXML
    private TableColumn<CustomerClass, Integer> id;

    @FXML
    private TableColumn<CustomerClass, String> name;

    @FXML
    private TableColumn<CustomerClass, Integer> phone;

    @FXML
    private TableColumn<CustomerClass, String> address;
    ObservableList<CustomerClass> list = FXCollections.observableArrayList();

    @FXML
    void OnDelete(ActionEvent event) {
        try{
            int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
            int id = Integer.valueOf(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
            PreparedStatement ps = db.connection.prepareStatement("delete from customer where id = ? ");
            ps.setInt(1,id);
            ps.executeUpdate();
            FetData();
            CleatData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE");
            alert.setContentText("successfully Customer deleted ...");
            alert.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void OnEdit(ActionEvent event) {

        try{
            if(txtName.getText().equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                String name = txtName.getText();
                int phone = Integer.valueOf(txtPhone.getText());
                String address = txtAddress.getText();
                int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
                int id = Integer.valueOf(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
                PreparedStatement ps = db.connection.prepareStatement("update customer set name = ? , phone = ? , address = ?  where id = ? ");
                ps.setString(1,name);
                ps.setInt(2, phone);
                ps.setString(3,address);
                ps.setInt(4,id);
                ps.executeUpdate();
                FetData();
                CleatData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("UPDATED");
                alert.setContentText("Successfully Customer Updated ...");
                alert.show();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void OnRefresh(ActionEvent event) {
        try{
            FetData();
            CleatData();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void OnSave(ActionEvent event) {

        try{
            if(txtName.getText().equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                String name = txtName.getText();
                int phone = Integer.valueOf(txtPhone.getText());
                String address = txtAddress.getText();
                PreparedStatement ps = db.connection.prepareStatement("insert into customer (id, name, phone, address) values( default , ? , ? , ?)");
                ps.setString(1,name);
                ps.setInt(2, phone);
                ps.setString(3,address);
                ps.executeUpdate();
                FetData();
                CleatData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SAVE");
                alert.setContentText("Successfully Customer Saved ...");
                alert.show();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void OnSearchPress(KeyEvent event) {

        try{
            if(event.getCode().equals(KeyCode.ENTER)){
                TableViewInfo.getItems().clear();
                TableViewInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                id.setCellValueFactory(new PropertyValueFactory<CustomerClass, Integer>("id"));
                name.setCellValueFactory(new PropertyValueFactory<CustomerClass , String>("name"));
                phone.setCellValueFactory(new PropertyValueFactory<CustomerClass , Integer>("phone"));
                address.setCellValueFactory(new PropertyValueFactory<CustomerClass , String>("address"));
                int Search = Integer.valueOf(txtSearch.getText());
                db con = new db("select * from customer where id = '"+Search+"'");
                while (db.resultSet.next()){
                    list.addAll((new CustomerClass(
                            db.resultSet.getInt("id"),
                            db.resultSet.getString("name"),
                            db.resultSet.getInt("phone"),
                            db.resultSet.getString("address")
                    )));
                }
                TableViewInfo.setItems(list);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void getTableOfData(MouseEvent event) {
        CustomerClass customerClass = TableViewInfo.getSelectionModel().getSelectedItem();
        txtName.setText(customerClass.getName());
        txtPhone.setText(String.valueOf(customerClass.getPhone()));
        txtAddress.setText(customerClass.getAddress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            FetData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void FetData()throws SQLException,IOException {
        try{
            TableViewInfo.getItems().clear();
            TableViewInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            id.setCellValueFactory(new PropertyValueFactory<CustomerClass, Integer>("id"));
            name.setCellValueFactory(new PropertyValueFactory<CustomerClass , String>("name"));
            phone.setCellValueFactory(new PropertyValueFactory<CustomerClass , Integer>("phone"));
            address.setCellValueFactory(new PropertyValueFactory<CustomerClass , String>("address"));
            db con = new db("select * from customer");
            while (db.resultSet.next()){
                list.addAll((new CustomerClass(
                        db.resultSet.getInt("id"),
                        db.resultSet.getString("name"),
                        db.resultSet.getInt("phone"),
                        db.resultSet.getString("address")
                )));
            }
            TableViewInfo.setItems(list);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void CleatData(){
        txtSearch.clear();
        txtName.clear();
        txtPhone.clear();
        txtAddress.clear();
    }
}

