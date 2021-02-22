package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class workerController {

    @FXML
    private TableView<Drink> orderTable;

    @FXML
    private TableColumn<Drink, String> drinkColumn;

    @FXML
    private TableColumn<Drink, String> milkColumn;

    @FXML
    private TableColumn<Drink, String> expressoColumn;

    @FXML
    private TableColumn<Drink, String> sugarColumn;

    @FXML
    private TableColumn<Drink, String> flavorColumn;

    @FXML
    private TableColumn<Drink, String> priceColumn;

    @FXML
    private TableColumn<Drink, String> pickUpColumn;


    private String price, flavor, sugar, expresso, milkChoice, drink = "", time;

    @FXML
    public void deleteOrderButtonPressed(ActionEvent e) {
        orderTable.getItems().removeAll(orderTable.getSelectionModel().getSelectedItem());
        ObservableList<Drink> workerOrders = FXCollections.observableArrayList();
        workerOrders = orderTable.getItems();
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("order.txt")))) {
            for (Object x : workerOrders) {
                out.println(x.toString());
            }
            out.close();
            File file = new File("order.txt");
        } catch(IOException exception) {

        }

    }

    @FXML
    public void logoutButtonPressed(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FrenchLogin.fxml"));
        Scene workerView = new Scene(root);

        Stage workerWindow = (Stage) ((Node) e.getSource()).getScene().getWindow();
        workerWindow.setTitle("French Press Coffee");
        workerWindow.setScene(workerView);
        workerWindow.show();
    }

    public void initialize(){
        drinkColumn.setCellValueFactory(new PropertyValueFactory<Drink, String>("drink"));
        milkColumn.setCellValueFactory(new PropertyValueFactory<Drink, String>("milk"));
        expressoColumn.setCellValueFactory(new PropertyValueFactory<Drink, String>("expresso"));
        sugarColumn.setCellValueFactory(new PropertyValueFactory<Drink, String>("sugar"));
        flavorColumn.setCellValueFactory(new PropertyValueFactory<Drink, String>("flavor"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Drink, String>("price"));
        pickUpColumn.setCellValueFactory(new PropertyValueFactory<Drink, String>("time"));

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("order.txt")))) {
            ObservableList<Drink> orderList = FXCollections.observableArrayList();
            String line;
            String [] lineArray;
            int orders = 0;

            while ((line = reader.readLine()) != null) {
                lineArray = line.split(" ");
                int length = lineArray.length;

                time = (String) lineArray[length-1];
                price = (String) lineArray[length - 2];
                flavor = (String) lineArray[length - 3];
                sugar = (String) lineArray[length - 4];
                expresso = (String) lineArray[length - 5];
                milkChoice = (String) lineArray[length - 6];
                length -= 7;
                for (int i = 0; i <= length; i++) {
                    String input = lineArray[i];
                    drink += input + " ";
                }
                Drink displayDrink = new Drink(drink, milkChoice, expresso, sugar, flavor, price, time);
                orderList.add(displayDrink);
                drink = "";

                orderTable.setItems(orderList);

            }

        } catch (IOException event) {
            event.printStackTrace();
        }




    }


}