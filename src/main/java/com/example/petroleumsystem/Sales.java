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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class Sales implements Initializable {

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
    private TextField txtCustomerPhone;

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

    ObservableList<salesClass> list = FXCollections.observableArrayList();
    @FXML
    private TableView<salesClass> TableViewInfo;

    @FXML
    private TableColumn<salesClass, Integer> id;

    @FXML
    private TableColumn<salesClass, String> colCustomerName;

    @FXML
    private TableColumn<salesClass, Integer> colCustomerPhone;

    @FXML
    private TableColumn<salesClass, String> colFuelType;

    @FXML
    private TableColumn<salesClass, Integer> colTunkNumber;

    @FXML
    private TableColumn<salesClass, Integer> colLitters;

    @FXML
    private TableColumn<salesClass, Integer> colPerLitters;

    @FXML
    private TableColumn<salesClass, Integer> colTotalPrice;

    @FXML
    private TableColumn<salesClass, Date> colDate;

    @FXML
    private TableColumn<salesClass, String> colStatus;

    String status = "";

    @FXML
    void onTotal(KeyEvent event) {
        double liters = Double.parseDouble(txtLitters.getText());
        double pricePerLiter = Double.parseDouble(txtPerLitters.getText());
        double totalCost = liters * pricePerLiter;
        txtTotalPrice.setText(String.format("%.2f", totalCost));
    }


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
            db con = new db("select * from customer");
            while (db.resultSet.next()){
                if(!cmbSupplier.contains(db.resultSet.getString("name"))){
                    cmbSupplier.addAll(db.resultSet.getString("name"));
                }
            }
            cmbSupplierName.setItems(cmbSupplier);
            db cons = new db("select * from customer where name = '"+cmbSupplierName.getSelectionModel().getSelectedItem()+"' ");
            if(db.resultSet.next()){
                txtCustomerPhone.setText(String.valueOf(db.resultSet.getInt("phone")));
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
            PreparedStatement ps = db.connection.prepareStatement("delete from sales where id = ? ");
            ps.setInt(1,id);
            ps.executeUpdate();
            FetchData();
            ClearData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE");
            alert.setContentText("successfully Sales deleted ...");
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
    int enteredLiters;
    int newLiters;

    int currentLiters = 0;

    @FXML
    void OnSave(ActionEvent event) {

        try {
            if (cmbSupplierName.equals("") || cmbFuelType.equals("") || txtLitters.getText().equals("") || txtTotalPrice.getText().equals("") || txtCustomerPhone.getText().equals("") || txtTunkNumber.getText().equals("") || txtPerLitters.getText().equals("") || txtDate.getValue().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("fields can not empty ...");
                alert.show();
            } else {
                String combo_supp_name = cmbSupplierName.getSelectionModel().getSelectedItem();
                String fuel = cmbFuelType.getSelectionModel().getSelectedItem();
                String litters = txtLitters.getText();
                String totalPrice = txtTotalPrice.getText();
                String suppPhone = txtCustomerPhone.getText();
                int tunk_number = Integer.parseInt(txtTunkNumber.getText());
                int pricePerLitter = Integer.parseInt(txtPerLitters.getText());
                String date = String.valueOf(txtDate.getValue());

                // Insert the sales data into the database
                PreparedStatement ps3 = db.connection.prepareStatement("insert into sales values(default , ? , ? , ?, ?, ?, ?, ?, ?, ?)");
                ps3.setString(1, combo_supp_name);
                ps3.setString(2, suppPhone);
                ps3.setString(3, fuel);
                ps3.setInt(4, tunk_number);
                ps3.setString(5, litters);
                ps3.setInt(6, pricePerLitter);
                ps3.setString(7, totalPrice);
                ps3.setString(8, date);
                ps3.setString(9, status);
                ps3.executeUpdate();


                db con = new db("select tunk_capacity from fuel where fuel_type = '"+fuel+"'");

                while (db.resultSet.next()){
                    int testValue = db.resultSet.getInt("tunk_capacity");
                    currentLiters = testValue;
                }

                enteredLiters = Integer.parseInt(litters);
                newLiters = currentLiters - enteredLiters;
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void OnSearchPress(KeyEvent event) {

    }

    @FXML
    void getTableOfData(MouseEvent event) throws SQLException {
        try{
            salesClass salesClass = TableViewInfo.getSelectionModel().getSelectedItem();

            ObservableList<String> supplier_name = FXCollections.observableArrayList(salesClass.getCustomer_name());
            txtCustomerPhone.setText(String.valueOf(salesClass.getCustomer_phone()));
            ObservableList<String> fuel_type = FXCollections.observableArrayList(salesClass.getFuel_type());
            txtLitters.setText(String.valueOf(salesClass.getLitters()));
            txtTotalPrice.setText(String.valueOf(salesClass.getTotal_price()));
            txtTunkNumber.setText(String.valueOf(salesClass.getTunk_number()));
            txtPerLitters.setText(String.valueOf(salesClass.getPer_litters()));
            txtDate.setValue(LocalDate.parse(String.valueOf(salesClass.getDate())));

            cmbSupplierName.getItems().add(String.valueOf(supplier_name));
            cmbFuelType.getItems().add(String.valueOf(fuel_type));

            if(salesClass.getStatus().equals("Approved")){
                radioBtnPending.setSelected(false);
                radioBtnApproved.setSelected(true);
            } else if (salesClass.getStatus().equals("Pending")) {
                radioBtnApproved.setSelected(false);
                radioBtnPending.setSelected(true);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        FetchCmboSupplier();
        FetchCmboFuel();
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
                String customer_name = cmbSupplierName.getSelectionModel().getSelectedItem();
                int customer_phone = Integer.parseInt(txtCustomerPhone.getText());
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
                PreparedStatement ps = db.connection.prepareStatement("update sales set customer_name = ? , customer_phone = ?, fuel_type = ?, tunk_number = ?, litters = ?, per_litters = ?, total_price = ?, date = ?, status = ?  where id = ?");

                ps.setString(1,customer_name);
                ps.setInt(2, customer_phone);
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
            id.setCellValueFactory(new PropertyValueFactory<salesClass , Integer>("id"));
            colCustomerName.setCellValueFactory(new PropertyValueFactory<salesClass , String>("customer_name"));
            colCustomerPhone.setCellValueFactory(new PropertyValueFactory<salesClass , Integer>("customer_phone"));
            colFuelType.setCellValueFactory(new PropertyValueFactory<salesClass , String>("fuel_type"));
            colTunkNumber.setCellValueFactory(new PropertyValueFactory<salesClass , Integer>("tunk_number"));
            colLitters.setCellValueFactory(new PropertyValueFactory<salesClass , Integer>("litters"));
            colPerLitters.setCellValueFactory(new PropertyValueFactory<salesClass , Integer>("per_litters"));
            colTotalPrice.setCellValueFactory(new PropertyValueFactory<salesClass , Integer>("total_price"));
            colDate.setCellValueFactory(new PropertyValueFactory<salesClass , Date>("date"));
            colStatus.setCellValueFactory(new PropertyValueFactory<salesClass , String>("status"));

            db con = new db("select * from sales");
            while (db.resultSet.next()){
                list.addAll(new salesClass(
                        db.resultSet.getInt("id"),
                        db.resultSet.getString("customer_name"),
                        db.resultSet.getInt("customer_phone"),
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
        txtCustomerPhone.clear();
        txtPerLitters.clear();
        txtLitters.clear();
        txtDate.setValue(LocalDate.parse(""));
        txtTunkNumber.clear();
        txtTotalPrice.clear();
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
