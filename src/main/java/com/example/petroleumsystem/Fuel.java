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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Fuel implements Initializable {

    ObservableList<FuelClass> list = FXCollections.observableArrayList();
    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtTunkCapacity;

    @FXML
    private ComboBox<Integer> cmbTunkNumber;

    @FXML
    private ComboBox<String> cmbFuelType;

    @FXML
    private TableView<FuelClass> TableViewInfo;

    @FXML
    private TableColumn<FuelClass, Integer> id;

    @FXML
    private TableColumn<FuelClass, Integer> tunkNumber;

    @FXML
    private TableColumn<FuelClass, String> fuelType;

    @FXML
    private TableColumn<FuelClass, String> tunkCapacity;
    ObservableList<String> comboFuel = FXCollections.observableArrayList();
    ObservableList<Integer> comboTunk = FXCollections.observableArrayList();

    @FXML
    void OnDelete(ActionEvent event) {
        try{
            int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
            int id = Integer.valueOf(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
            PreparedStatement ps = db.connection.prepareStatement("delete from fuel where id = ? ");
            ps.setInt(1,id);
            ps.executeUpdate();
            FetchData();
            ClearData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE");
            alert.setContentText("successfully Fuel Deleted ...");
            alert.show();
            ClearData();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

//    @FXML
//    void OnEdit(ActionEvent event) {
//        try{
//            if(cmbTunkNumber.equals("") || cmbFuelType.equals("") || txtTunkCapacity.getText().equals("")){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("ERROR");
//                alert.setContentText("fields can not empty ...");
//                alert.show();
//            }
//            else {
//                int tunk_number = cmbTunkNumber.getSelectionModel().getSelectedItem();
//                String fuel = cmbFuelType.getSelectionModel().getSelectedItem();
//                String tunk_capacity = txtTunkCapacity.getText();
//                int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
//                int id = Integer.valueOf(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
//                PreparedStatement ps = db.connection.prepareStatement("update fuel set tunk_number= ? , fuel_type = ? , tunk_capacity = ?  where id = ? ");
//                ps.setInt(1,tunk_number);
//                ps.setString(2,fuel);
//                ps.setString(3,tunk_capacity);
//                ps.setInt(4,id);
//                ps.executeUpdate();
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Updated");
//                alert.setContentText("Successfully Fuel Updated ...");
//                alert.show();
//                FetchData();
//                ClearData();
//                fetComboTunk();
//                fetchComboFuel();
//            }
//
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//    }

    @FXML
    void OnRefresh(ActionEvent event) throws SQLException, IOException {
      try{
          FetchData();
          ClearData();
      }catch (Exception e){
          System.out.println(e.getMessage());
      }

    }

    @FXML
    void OnSave(ActionEvent event) {
        try{
            if(cmbTunkNumber.equals("") || cmbFuelType.equals("") || txtTunkCapacity.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                int tunk_number = cmbTunkNumber.getSelectionModel().getSelectedItem();
                String fuel = cmbFuelType.getSelectionModel().getSelectedItem();
                String tunk_capacity = txtTunkCapacity.getText();
                PreparedStatement ps = db.connection.prepareStatement("insert into fuel values(default , ? , ? , ?)");
                ps.setInt(1,tunk_number);
                ps.setString(2,fuel);
                ps.setString(3,tunk_capacity);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SAVE");
                alert.setContentText("Successfully Fuel saved ...");
                alert.show();
                FetchData();
                ClearData();
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
               id.setCellValueFactory(new PropertyValueFactory<FuelClass , Integer>("id"));
               tunkNumber.setCellValueFactory(new PropertyValueFactory<FuelClass , Integer>("tunk_number"));
               fuelType.setCellValueFactory(new PropertyValueFactory<FuelClass , String>("fuel_type"));
               tunkCapacity.setCellValueFactory(new PropertyValueFactory<FuelClass , String>("tunk_capacity"));
               int search =Integer.parseInt(txtSearch.getText());
               db con = new db("select * from fuel where id = '"+search+"'");
               while (db.resultSet.next()){
                   list.addAll(new FuelClass(
                           db.resultSet.getInt("id"),
                           db.resultSet.getInt("tunk_number"),
                           db.resultSet.getString("fuel_type"),
                           db.resultSet.getString("tunk_capacity")
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
        try{
            FuelClass fuelClass = TableViewInfo.getSelectionModel().getSelectedItem();
            ObservableList<Integer> t_number = FXCollections.observableArrayList(fuelClass.getTunk_number());
            ObservableList<String> fuel = FXCollections.observableArrayList(fuelClass.getFuel_type());
            cmbTunkNumber.setItems(t_number);
            cmbFuelType.setItems(fuel);
            txtTunkCapacity.setText(fuelClass.getTunk_capacity());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            FetchData();
            fetchComboFuel();
            fetchCombo();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    void FetchData() throws SQLException , IOException {
        try{
            TableViewInfo.getItems().clear();
            TableViewInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            id.setCellValueFactory(new PropertyValueFactory<FuelClass , Integer>("id"));
            tunkNumber.setCellValueFactory(new PropertyValueFactory<FuelClass , Integer>("tunk_number"));
            fuelType.setCellValueFactory(new PropertyValueFactory<FuelClass , String>("fuel_type"));
            tunkCapacity.setCellValueFactory(new PropertyValueFactory<FuelClass , String>("tunk_capacity"));
            db con = new db("select * from fuel");
            while (db.resultSet.next()){
                list.addAll(new FuelClass(
                   db.resultSet.getInt("id"),
                   db.resultSet.getInt("tunk_number"),
                   db.resultSet.getString("fuel_type"),
                   db.resultSet.getString("tunk_capacity")
                ));
            }
            TableViewInfo.setItems(list);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void ClearData(){
        txtSearch.clear();
        cmbTunkNumber.getItems().clear();
        cmbFuelType.getItems().clear();
        txtTunkCapacity.clear();
    }

    @FXML
    void OnAddFuelType(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FuelType.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

    }

    @FXML
    void OnAddTunkNumber(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TunkNumber.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void fetchComboFuel() throws SQLException {
        db con = new db("select * from fuelType");
        while (db.resultSet.next()){
            if(!comboFuel.contains(db.resultSet.getString("fuel_type"))){
                comboFuel.addAll(db.resultSet.getString("fuel_type"));
            }
        }
        cmbFuelType.setItems(comboFuel);

    }
    @FXML
    void fetchCombo() throws SQLException {
        db con = new db("select * from tunk_number");
        while (db.resultSet.next()){
            if(!comboTunk.contains(db.resultSet.getInt("tunk_number"))){
                comboTunk.addAll(db.resultSet.getInt("tunk_number"));
            }
        }
        cmbTunkNumber.setItems(comboTunk);
        db cons = new db("select * from tunk_number where tunk_number = '"+cmbTunkNumber.getSelectionModel().getSelectedItem()+"' ");
        if(db.resultSet.next()){
            txtTunkCapacity.setText(db.resultSet.getString("tunk_capacity"));
        }
    }

}


