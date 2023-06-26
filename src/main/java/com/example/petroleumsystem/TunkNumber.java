package com.example.petroleumsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class TunkNumber implements Initializable {

    @FXML
    private TableColumn<TunkNumberClass, Integer> tunkNumber;

    @FXML
    private TableColumn<TunkNumberClass, String> tunkCapacity;
    ObservableList<TunkNumberClass> list = FXCollections.observableArrayList();

    @FXML
    private Button btnSave;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<TunkNumberClass> tableView;

    @FXML
    private TableColumn<TunkNumberClass, Integer> id;

    @FXML
    private TextField txttunkNumber;

    @FXML
    private TextField txttunkCapacity;


    @FXML
    void getData(MouseEvent event) {
        TunkNumberClass tunkNumberClass = tableView.getSelectionModel().getSelectedItem();
        txttunkNumber.setText(String.valueOf(tunkNumberClass.getTunkNumber()));
        txttunkCapacity.setText(tunkNumberClass.getTunkCapacity());
    }

    @FXML
    void onDelete(ActionEvent event) {
        try{
            if(txttunkCapacity.getText().isEmpty() || txttunkNumber.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                int Myindex = tableView.getSelectionModel().getSelectedIndex();
                int id = Integer.parseInt(String.valueOf(tableView.getItems().get(Myindex).getId()));
                PreparedStatement ps = db.connection.prepareStatement("delete from tunk_number  where id = ? ");
                ps.setInt(1,id);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete");
                alert.setContentText("Successfully Tunk_number Deleted ...");
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

        try{
            if(txttunkCapacity.getText().isEmpty() || txttunkNumber.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                int tunk_number = Integer.parseInt(txttunkNumber.getText());
                String tunk_capacity = txttunkCapacity.getText();
                int Myindex = tableView.getSelectionModel().getSelectedIndex();
                int id = Integer.parseInt(String.valueOf(tableView.getItems().get(Myindex).getId()));
                PreparedStatement ps = db.connection.prepareStatement("update tunk_number set tunk_number = ? , tunk_capacity = ? where id = ? ");
                ps.setInt(1,tunk_number);
                ps.setString(2,tunk_capacity);
                ps.setInt(3,id);
                ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update");
                alert.setContentText("Successfully Tunk_number Updated ...");
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
      try{
          if(txttunkCapacity.getText().isEmpty() || txttunkNumber.getText().isEmpty()){
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("ERROR");
              alert.setContentText("fields can not empty ...");
              alert.show();
          }
          else {
              int tunk_number = Integer.parseInt(txttunkNumber.getText());
              String tunk_capacity = txttunkCapacity.getText();
              PreparedStatement ps = db.connection.prepareStatement("insert into tunk_number values (default , ? , ?)");
              ps.setInt(1,tunk_number);
              ps.setString(2,tunk_capacity);
              ps.executeUpdate();
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("SAVE");
              alert.setContentText("Successfully Tunk_number saved ...");
              alert.show();
              FetchData();
              ClearData();
          }
      }catch (Exception e){
          System.out.println(e.getMessage());
      }


    }

    public  void FetchData(){
        try{
            tableView.getItems().clear();
            tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            id.setCellValueFactory(new PropertyValueFactory<TunkNumberClass , Integer>("id"));
            tunkNumber.setCellValueFactory(new PropertyValueFactory<TunkNumberClass , Integer>("tunkNumber"));
            tunkCapacity.setCellValueFactory(new PropertyValueFactory<TunkNumberClass , String>("tunkCapacity"));
            db con = new db("select * from tunk_number ");
            while (db.resultSet.next()){
                list.addAll(new TunkNumberClass(
                   db.resultSet.getInt("id"),
                   db.resultSet.getInt("tunk_number"),
                   db.resultSet.getString("tunk_capacity")
                ));
            }
            tableView.setItems(list);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            FetchData();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    void ClearData(){
        txttunkNumber.clear();
        txttunkCapacity.clear();
    }
}
