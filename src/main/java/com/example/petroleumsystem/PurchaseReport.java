package com.example.petroleumsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PurchaseReport {


    @FXML
    void printPurchaseReport(ActionEvent event) {
        JasperPrint jp;

        System.out.println();

        Map param = new HashMap();

        try {
            jp = JasperFillManager.fillReport("reports/PurchaseReport.jasper",param, ReportConnection.createConnection());

            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Purchase Report");
            viewer.setVisible(true);
        }
        catch (JRException e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void printSalesReport(ActionEvent event) {
        JasperPrint jp;

        System.out.println();

        Map param = new HashMap();

        try {
            jp = JasperFillManager.fillReport("reports/salesReport.jasper",param, ReportConnection.createConnection());

            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Sales Report");
            viewer.setVisible(true);
        }
        catch (JRException e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
