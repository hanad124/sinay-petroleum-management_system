package com.example.petroleumsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML
    private Label lblEmployee;
    @FXML
    private Label lblCustomer;

    @FXML
    private BarChart<String, String> barchart_totalsales;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            totalEmployee();
            totalCustomer();
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
