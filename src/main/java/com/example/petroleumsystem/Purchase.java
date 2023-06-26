package com.example.petroleumsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class Purchase implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtTunkNumber;

    @FXML
    private ComboBox<String> cmbSupplierName;

    @FXML
    private ComboBox<String> cmbFuelType;
    ObservableList<String> cmbFeul = FXCollections.observableArrayList();
    ObservableList<String> cmbSupplier = FXCollections.observableArrayList();

    @FXML
    private TextField txtSupplierPhone;

    @FXML
    private TextField txtLitters;

    @FXML
    private TextField txtPerLitters;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private DatePicker txtDate;

    @FXML
    private RadioButton radioBtnPending;

    @FXML
    private ToggleGroup btnStatus;

    @FXML
    private RadioButton radioBtnApproved;

    @FXML
    private Button btnEdit;

    ObservableList<purchaseClass> list = FXCollections.observableArrayList();
    @FXML
    private TableView<purchaseClass> TableViewInfo;

    @FXML
    private TableColumn<purchaseClass, Integer> id;

    @FXML
    private TableColumn<purchaseClass, String> colSupplierName;

    @FXML
    private TableColumn<purchaseClass, Integer> colSupplierPhone;

    @FXML
    private TableColumn<purchaseClass, String> colFuelType;

    @FXML
    private TableColumn<purchaseClass, Integer> colTunkNumber;

    @FXML
    private TableColumn<purchaseClass, Integer> colLitters;

    @FXML
    private TableColumn<purchaseClass, Integer> colPerLitters;

    @FXML
    private TableColumn<purchaseClass, Integer> colTotalPrice;

    @FXML
    private TableColumn<purchaseClass, Date> colDate;

    @FXML
    private TableColumn<purchaseClass, String> colStatus;

    @FXML
    void FetchCmboFuel() throws SQLException {

        db con = new db("select * from fuelType");
        while (db.resultSet.next()){
            if(!cmbFeul.contains(db.resultSet.getString("fuel_type"))){
                cmbFeul.addAll(db.resultSet.getString("fuel_type"));
            }
        }
        cmbFuelType.setItems(cmbFeul);

    }

    @FXML
    void FetchCmboSupplier() {
        try{
            db con = new db("select * from supplier");
            while (db.resultSet.next()){
                if(!cmbSupplier.contains(db.resultSet.getString("name"))){
                    cmbSupplier.addAll(db.resultSet.getString("name"));
                }
            }
            cmbSupplierName.setItems(cmbSupplier);
            db cons = new db("select * from supplier where name = '"+cmbSupplierName.getSelectionModel().getSelectedItem()+"' ");
            if(db.resultSet.next()){
                txtSupplierPhone.setText(String.valueOf(db.resultSet.getInt("phone")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void OnDelete(ActionEvent event) {

    }

    @FXML
    void OnRefresh(ActionEvent event) {

    }

    @FXML
    void OnSave(ActionEvent event) {


    }

    @FXML
    void OnSearchPress(KeyEvent event) {

    }

    @FXML
    void getTableOfData(MouseEvent event) {


    }

    @FXML
    void onEdit(ActionEvent event) {

    }


    void FetchData(){
        try{

            TableViewInfo.getItems().clear();
            TableViewInfo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            id.setCellValueFactory(new PropertyValueFactory<purchaseClass , Integer>("id"));
            colSupplierName.setCellValueFactory(new PropertyValueFactory<purchaseClass , String>("supplier_name"));
            colSupplierPhone.setCellValueFactory(new PropertyValueFactory<purchaseClass , Integer>("supplier_phone"));
            colFuelType.setCellValueFactory(new PropertyValueFactory<purchaseClass , String>("fuel_type"));
            colTunkNumber.setCellValueFactory(new PropertyValueFactory<purchaseClass , Integer>("tunk_number"));
            colLitters.setCellValueFactory(new PropertyValueFactory<purchaseClass , Integer>("litters"));
            colPerLitters.setCellValueFactory(new PropertyValueFactory<purchaseClass , Integer>("per_litters"));
            colTotalPrice.setCellValueFactory(new PropertyValueFactory<purchaseClass , Integer>("total_price"));
            colDate.setCellValueFactory(new PropertyValueFactory<purchaseClass , Date>("date"));
            colStatus.setCellValueFactory(new PropertyValueFactory<purchaseClass , String>("status"));

            db con = new db("select * from purchase");
            while (db.resultSet.next()){
                list.addAll(new purchaseClass(
                        db.resultSet.getInt("id"),
                        db.resultSet.getString("supplier_name"),
                        db.resultSet.getInt("supplier_phone"),
                        db.resultSet.getString("fuel_type"),
                        db.resultSet.getInt("tunk_number"),
                        db.resultSet.getInt("litters"),
                        db.resultSet.getInt("per_litters"),
                        db.resultSet.getInt("total_price"),
                        db.resultSet.getDate("date"),
                        db.resultSet.getString("status")
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
            FetchCmboSupplier();
            FetchCmboFuel();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
