package com.example.petroleumsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FuelType implements Initializable {
    @FXML
    private TextField txtfuelType;

    ObservableList<FuelTypeClass> list = FXCollections.observableArrayList();

    @FXML
    private TableColumn<FuelTypeClass, String> fuelType;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<FuelTypeClass> tableView;

    @FXML
    private TableColumn<FuelTypeClass, Integer> id;
    @FXML
    void getData(MouseEvent event) {
        FuelTypeClass fuelTypeClass = tableView.getSelectionModel().getSelectedItem();
        txtfuelType.setText(fuelTypeClass.getFuelType());
    }

    @FXML
    void onDelete(ActionEvent event) {
        try{
            if(txtfuelType.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                int Myindex = tableView.getSelectionModel().getSelectedIndex();
                int id = Integer.parseInt(String.valueOf(tableView.getItems().get(Myindex).getId()));
                PreparedStatement ps = db.connection.prepareStatement("delete from fuelType  where id = ? ");
                ps.setInt(1,id);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete");
                alert.setContentText("Successfully Fuel Type Deleted ...");
                alert.show();
                FetchData();
                ClearData();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onEdit(ActionEvent event) {
        try {
            if(txtfuelType.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                int Myindex = tableView.getSelectionModel().getSelectedIndex();
                int id = Integer.parseInt(String.valueOf(tableView.getItems().get(Myindex).getId()));
                String fuel_type = txtfuelType.getText();
                PreparedStatement ps = db.connection.prepareStatement("update fuelType set fuel_type = ? where id = ?");
                ps.setString(1,fuel_type);
                ps.setInt(2,id);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Updated");
                alert.setContentText("Successfully Fuel Type Updated ...");
                alert.show();
                FetchData();
                ClearData();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void onSave(ActionEvent event) {

        try {
            if(txtfuelType.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                String fuel_type = txtfuelType.getText();
                PreparedStatement ps = db.connection.prepareStatement("insert into fuelType values(default , ? )");
                ps.setString(1,fuel_type);
                ps.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SAVE");
                alert.setContentText("Successfully Fuel Type saved ...");
                alert.show();
                FetchData();
                ClearData();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    void FetchData() throws SQLException {
        tableView.getItems().clear();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        id.setCellValueFactory(new PropertyValueFactory<FuelTypeClass , Integer>("id"));
        fuelType.setCellValueFactory(new PropertyValueFactory<FuelTypeClass , String>("fuelType"));
        db con = new db("select * from fuelType");
        while (db.resultSet.next()){
            list.addAll(new FuelTypeClass(
               db.resultSet.getInt("id"),
               db.resultSet.getString("fuel_type")
            ));
        }
        tableView.setItems(list);
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
        txtfuelType.clear();
    }
}

