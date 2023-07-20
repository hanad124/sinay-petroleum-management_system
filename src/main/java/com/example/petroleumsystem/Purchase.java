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

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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

    String status = "";

    int enteredLiters;
    int newLiters;

    int currentLiters = 0;


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
        try{
            int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
            int id = Integer.valueOf(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
            PreparedStatement ps = db.connection.prepareStatement("delete from purchase where id = ? ");
            ps.setInt(1,id);
            ps.executeUpdate();
            FetchData();
            ClearData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE");
            alert.setContentText("successfully Purchase deleted ...");
            alert.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void OnRefresh(ActionEvent event) {

    }

    @FXML
    void toggleApproved(ActionEvent event) {
        status = "Approved";
    }

    @FXML
    void togglePending(ActionEvent event) {
        status = "Pending";
    }

    @FXML
    void OnSave(ActionEvent event) {
        try{
            if(cmbSupplierName.equals("") || cmbFuelType.equals("") || txtLitters.getText().equals("") || txtTotalPrice.getText().equals("") || txtSupplierPhone.getText().equals("") || txtTunkNumber.getText().equals("") || txtPerLitters.getText().equals("") || txtDate.getValue().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                String combo_supp_name = cmbSupplierName.getSelectionModel().getSelectedItem();
                String fuel = cmbFuelType.getSelectionModel().getSelectedItem();
                String litters = txtLitters.getText();
                String totalPrice = txtTotalPrice.getText();
                String suppPhone = txtSupplierPhone.getText();
                int tunk_number = Integer.parseInt(txtTunkNumber.getText());
                int pricePerLitter = Integer.parseInt(txtPerLitters.getText());
                String date = String.valueOf(txtDate.getValue());

                PreparedStatement ps = db.connection.prepareStatement("insert into purchase values(default , ? , ? , ?, ?, ?, ?, ?, ?, ?)");

                ps.setString(1,combo_supp_name);
                ps.setString(2,suppPhone);
                ps.setString(3,fuel);
                ps.setInt(4,tunk_number);
                ps.setString(5,litters);
                ps.setInt(6,pricePerLitter);
                ps.setString(7,totalPrice);
                ps.setString(8,date);
                ps.setString(9,status);
                ps.executeUpdate();

                db con = new db("select tunk_capacity from fuel where fuel_type = '"+fuel+"'");

                while (db.resultSet.next()){
                    int testValue = db.resultSet.getInt("tunk_capacity");
                    currentLiters = testValue;
                }

                enteredLiters = Integer.parseInt(litters);
                newLiters = currentLiters + enteredLiters;
                System.out.println("SUBSTRACTED VALUE "+newLiters);
                System.out.println("CURRENT VALUE "+currentLiters);

                // Update the liters value in the database
                PreparedStatement ps2 = db.connection.prepareStatement("update fuel set tunk_capacity = ? where fuel_type = ?");
                ps2.setInt(1, newLiters);
                ps2.setString(2, fuel);
                ps2.executeUpdate();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SAVE");
                alert.setContentText("Successfully Purchase saved ...");
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

    }

    @FXML
    void getTableOfData(MouseEvent event) {
        try{
            purchaseClass purchaseClass = TableViewInfo.getSelectionModel().getSelectedItem();

            ObservableList<String> supplier_name = FXCollections.observableArrayList(purchaseClass.getSupplier_name());
            txtSupplierPhone.setText(String.valueOf(purchaseClass.getSupplier_phone()));
            ObservableList<String> fuel_type = FXCollections.observableArrayList(purchaseClass.getFuel_type());
            txtLitters.setText(String.valueOf(purchaseClass.getLitters()));
            txtTotalPrice.setText(String.valueOf(purchaseClass.getTotal_price()));
            txtTunkNumber.setText(String.valueOf(purchaseClass.getTunk_number()));
            txtPerLitters.setText(String.valueOf(purchaseClass.getPer_litters()));
            txtDate.setValue(LocalDate.parse(String.valueOf(purchaseClass.getDate())));

            cmbSupplierName.getItems().add(String.valueOf(supplier_name));
            cmbFuelType.getItems().add(String.valueOf(fuel_type));

            if(purchaseClass.getStatus().equals("Approved")){
                radioBtnPending.setSelected(false);
                radioBtnApproved.setSelected(true);
            } else if (purchaseClass.getStatus().equals("Pending")) {
                radioBtnApproved.setSelected(false);
                radioBtnPending.setSelected(true);
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onEdit(ActionEvent event) {
        try{
            if(cmbSupplierName.getValue().equals("") || cmbFuelType.getValue().equals("") || txtLitters.getText().equals("") || txtTotalPrice.getText().equals("") || txtPerLitters.getText().equals("") || txtDate.getValue().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            }
            else {
                String supplierr_name = cmbSupplierName.getSelectionModel().getSelectedItem();
                int supplier_phone = Integer.parseInt(txtSupplierPhone.getText());
                String fuel_type = cmbFuelType.getSelectionModel().getSelectedItem();
                int tunk_number = Integer.parseInt(txtTunkNumber.getText());
                int litters = Integer.parseInt(txtLitters.getText());
                int per_litters = Integer.parseInt(txtPerLitters.getText());
                int total_price = Integer.parseInt(txtTotalPrice.getText());
                LocalDate date = txtDate.getValue();
                String radioStatus = "";

                if (radioBtnApproved.isSelected()){
                    radioStatus = "Approved";
                } else if (radioBtnPending.isSelected()) {
                    radioStatus = "Pending";
                }
                int myIndex = TableViewInfo.getSelectionModel().getSelectedIndex();
                int id = Integer.parseInt(String.valueOf(TableViewInfo.getItems().get(myIndex).getId()));
                PreparedStatement ps = db.connection.prepareStatement("update purchase set supplier_name = ? , supplier_phone = ?, fuel_type = ?, tunk_number = ?, litters = ?, per_litters = ?, total_price = ?, date = ?, status = ?  where id = ?");

                ps.setString(1,supplierr_name);
                ps.setInt(2, supplier_phone);
                ps.setString(3,fuel_type);
                ps.setInt(4, tunk_number);
                ps.setInt(5, litters);
                ps.setInt(6, per_litters);
                ps.setInt(7, total_price);
                ps.setString(8, String.valueOf(date));
                ps.setString(9,radioStatus);

                System.out.println("Salected status: "+radioStatus);

                ps.setInt(10,id);

                ps.executeUpdate();
                FetchData();
                ClearData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("UPDATED");
                alert.setContentText("Sales Successfully Updated ...");
                alert.show();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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

    void ClearData(){
        txtSearch.clear();
        txtSupplierPhone.clear();
        txtPerLitters.clear();
        txtLitters.clear();
        txtDate.setValue(LocalDate.parse(""));
        txtTunkNumber.clear();
        txtTotalPrice.clear();
    }


    @FXML
    void onTutal(KeyEvent event) {
        double liters = Double.parseDouble(txtLitters.getText());
        double pricePerLiter = Double.parseDouble(txtPerLitters.getText());
        double totalCost = liters * pricePerLiter;
        txtTotalPrice.setText(String.format("%.2f", totalCost));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FetchCmboSupplier();
            FetchCmboFuel();
            FetchData();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
