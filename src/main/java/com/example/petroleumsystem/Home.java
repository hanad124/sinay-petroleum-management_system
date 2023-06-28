package com.example.petroleumsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML
    private Label lblEmployee;
    @FXML
    private Label lblCustomer;
    @FXML
    private Label lbl_purchaseTotal;
    @FXML
    private Label lbl_salesTotal;

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


    @FXML
    private BarChart<String, String> barchart_totalsales;

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

    void totalEmployee() throws SQLException {
        db con = new db("select count(*) from employee ");
        while (db.resultSet.next()){
            lblEmployee.setText(String.valueOf(db.resultSet.getInt("count(*)")));
        }
    }

    void totalCustomer() throws SQLException {
        try{
            db con = new db("select count(*) from customer");
            while (db.resultSet.next()){
                lblCustomer.setText(String.valueOf(db.resultSet.getInt("count(*)")));
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void totalSales() {
        try{
            db con = new db("SELECT SUM(total_price) AS total_price FROM `sales`");
            while (db.resultSet.next()){
                lbl_salesTotal.setText("$"+String.valueOf(db.resultSet.getInt("total_price")));
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    void totalPurchase() {
        try{
            db con = new db("SELECT SUM(total_price) AS total_price FROM `purchase`");
            while (db.resultSet.next()){
                lbl_purchaseTotal.setText("$"+String.valueOf(db.resultSet.getInt("total_price")));
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FetchData();
        try {
            totalEmployee();
            totalCustomer();
            totalSales();
            totalPurchase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2022");

        series1.getData().add(new XYChart.Data("Hanad", 2323));
        series1.getData().add(new XYChart.Data("Mohamed", 4333));
        series1.getData().add(new XYChart.Data("Abdi Kafi", 10000));
        series1.getData().add(new XYChart.Data("Usama", 35000));
        series1.getData().add(new XYChart.Data("Cumar", 1200));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2023");

        series2.getData().add(new XYChart.Data("Hanad", 2323));
        series2.getData().add(new XYChart.Data("Mohamed", 4333));
        series2.getData().add(new XYChart.Data("Abdi Kafi", 10000));
        series2.getData().add(new XYChart.Data("Usama", 35000));
        series2.getData().add(new XYChart.Data("Cumar", 1200));

        barchart_totalsales.getData().addAll(series1, series2);
    }
}
