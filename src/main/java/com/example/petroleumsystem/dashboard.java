package com.example.petroleumsystem;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class dashboard implements Initializable{

    public static String roll_type;
    public static String user_name;

    private volatile boolean stop = false;


    @FXML
    private ImageView img_close;

    @FXML
    public Label lblPrintDate;

    @FXML
    private Label lblRollType;

    @FXML
    private Label lblUserName;

    @FXML
    private Button btn_customer;

    @FXML
    private Button btn_dashboard;

    @FXML
    private Button btn_employee;

    @FXML
    private Button btn_fuel;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_purchase;

    @FXML
    private Button btn_reports;

    @FXML
    private Button btn_sales;

    @FXML
    private Button btn_supplier;

    @FXML
    private Button btn_users;



    @FXML
    private BorderPane fxborderPane;

    @FXML
    void OnCustomer(ActionEvent event) {

        System.out.println("you Clicked me! ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Customer");
        fxborderPane.setCenter(view);

        setActiveButton(btn_customer);

    }

    @FXML
    void OnEmployee(ActionEvent event) {

        System.out.println("you Clicked me! ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Employee");
        fxborderPane.setCenter(view);

        setActiveButton(btn_employee);
    }

    @FXML
    void OnFuel(ActionEvent event) {
        System.out.println("you Clicked me! ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Fuel");
        fxborderPane.setCenter(view);

        setActiveButton(btn_fuel);
    }

    @FXML
    void OnReport(ActionEvent event) {
        setActiveButton(btn_reports);
    }

    @FXML
    void OnSale(ActionEvent event) {
        setActiveButton(btn_sales);
    }

    @FXML
    void OnSupplier(ActionEvent event) {
        System.out.println("you Clicked me! ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Supplier");
        fxborderPane.setCenter(view);

        setActiveButton(btn_supplier);

    }

    @FXML
    void OnUser(ActionEvent event) {
        System.out.println("you Clicked me! ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Users");
        fxborderPane.setCenter(view);

        setActiveButton(btn_users);

    }

    @FXML
    void onHome() {
        System.out.println("you Clicked me! ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Home");
        fxborderPane.setCenter(view);

        setActiveButton(btn_dashboard);
    }
    @FXML
    void onPurchase(ActionEvent event) {
        System.out.println("you Clicked me! ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Purchase");
        fxborderPane.setCenter(view);

        setActiveButton(btn_purchase);
    }

    private void setActiveButton(Button activeButton) {
        btn_dashboard.getStyleClass().remove("active");
        btn_employee.getStyleClass().remove("active");
        btn_supplier.getStyleClass().remove("active");
        btn_purchase.getStyleClass().remove("active");
        btn_fuel.getStyleClass().remove("active");
        btn_users.getStyleClass().remove("active");
        btn_sales.getStyleClass().remove("active");
        btn_customer.getStyleClass().remove("active");
        btn_reports.getStyleClass().remove("active");
        activeButton.getStyleClass().add("active");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onHome();
        lblUserName.setText(user_name.substring(0, 1).toUpperCase() + user_name.substring(1));
        lblRollType.setText(roll_type.toUpperCase());
        TimeNow();
    }
    private void TimeNow(){
        Thread thread = new Thread(()->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            while (!stop){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                final  String timeNow = simpleDateFormat.format(new Date());
                Platform.runLater(()->{
                    lblPrintDate.setText(timeNow);
                });
            }
        });
        thread.start();
    }

    @FXML
    void OnLogOut(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Login Page ");
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void closeWindow(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(1);
        stop = true;
    }
}
