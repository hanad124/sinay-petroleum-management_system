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
import java.util.jar.JarEntry;

public class Supplier implements Initializable {

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
    private TableView<SupplierClass> TableViewInfo;

    @FXML
    private TableColumn<SupplierClass, Integer> id;

    @FXML
    private TableColumn<SupplierClass, String> name;

    @FXML
    private TableColumn<SupplierClass, Integer> phone;

    @FXML
    private TableColumn<SupplierClass, String> address;

    ObservableList<SupplierClass> list = FXCollections.observableArrayList();

    @FXML
    void OnDelete(ActionEvent event) throws SQLException , IOException {
       try{
           int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
           int id = Integer.valueOf(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
           PreparedStatement ps = db.connection.prepareStatement("delete from supplier where id = ? ");
           ps.setInt(1,id);
           ps.executeUpdate();
           FetchData();
           ClearData();
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("DELETE");
           alert.setContentText("successfully Supplier deleted ...");
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
                PreparedStatement ps = db.connection.prepareStatement("update supplier set name = ? , phone = ? , address = ?  where id = ? ");
                ps.setString(1,name);
                ps.setInt(2, phone);
                ps.setString(3,address);
                ps.setInt(4,id);
                ps.executeUpdate();
                FetchData();
                ClearData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("UPDATED");
                alert.setContentText("Successfully Supplier Updated ...");
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
               PreparedStatement ps = db.connection.prepareStatement("insert into supplier values( default , ? , ? , ?)");
               ps.setString(1,name);
               ps.setInt(2, phone);
               ps.setString(3,address);
               ps.executeUpdate();
               FetchData();
               ClearData();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("SAVE");
               alert.setContentText("Successfully Supplier Saved ...");
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
                id.setCellValueFactory(new PropertyValueFactory<SupplierClass , Integer>("id"));
                name.setCellValueFactory(new PropertyValueFactory<SupplierClass , String>("name"));
                phone.setCellValueFactory(new PropertyValueFactory<SupplierClass , Integer>("phone"));
                address.setCellValueFactory(new PropertyValueFactory<SupplierClass , String>("address"));
                int Search = Integer.parseInt(txtSearch.getText());
                db con = new db("select * from supplier where id = '"+Search+"' ");
                while (db.resultSet.next()){
                    list.addAll(new SupplierClass(
                            db.resultSet.getInt("id"),
                            db.resultSet.getString("name"),
                            db.resultSet.getInt("phone"),
                            db.resultSet.getString("address")
                    ));
                }
                TableViewInfo.setItems(list);

            }

                    }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void getTableOfData(MouseEvent event) {
        SupplierClass supplierClass = TableViewInfo.getSelectionModel().getSelectedItem();
        txtName.setText(supplierClass.getName());
        txtPhone.setText(String.valueOf(supplierClass.getPhone()));
        txtAddress.setText(supplierClass.getAddress());
    }

    void FetchData() throws SQLException {
        try{

            TableViewInfo.getItems().clear();
            TableViewInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            id.setCellValueFactory(new PropertyValueFactory<SupplierClass , Integer>("id"));
            name.setCellValueFactory(new PropertyValueFactory<SupplierClass , String>("name"));
            phone.setCellValueFactory(new PropertyValueFactory<SupplierClass , Integer>("phone"));
            address.setCellValueFactory(new PropertyValueFactory<SupplierClass , String>("address"));
            db con = new db("select * from supplier");
            while (db.resultSet.next()){
                list.addAll(new SupplierClass(
                        db.resultSet.getInt("id"),
                        db.resultSet.getString("name"),
                        db.resultSet.getInt("phone"),
                        db.resultSet.getString("address")
                ));
            }
            TableViewInfo.setItems(list);


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

