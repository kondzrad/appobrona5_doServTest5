package pl.kawka.appobrona.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import pl.kawka.appobrona.model.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class EmployeeWindowController {

    private MainWindowController mainWindowController;

    ///////////
    @FXML
    private TextField idFieldId, idFieldFirstName, idFieldLastName, idFieldTown, idFieldStreet, idFieldPostcode,
            idFieldTelephoneNumber, idFieldNip, idFieldDateAdded;

    ///////////

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> id, firstName, lastName, town, street, postcode, telephoneNumber, nip, dateAdded;

    @FXML
    public void readDatabase() {

            System.out.println("Wczytanie bazy danych klientow");
            try {
                URL url = new URL("http://localhost:8080/api/customer");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");
                conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                System.out.println("poszłooooooooooooooooo");
                JSONParser parser = new JSONParser();
                JSONArray jSONArray = (JSONArray) parser.parse(in.readLine());

                ObservableList<Customer> masterData = FXCollections.observableArrayList();
                for (Object object : jSONArray) {
                    JSONObject jSONObject = (JSONObject) parser.parse(object.toString());
                    System.out.println("jSONObject : " + jSONObject.toJSONString());
                    masterData.add(new Customer(
                            Integer.parseInt(jSONObject.get("id").toString()),
                            jSONObject.get("firstName").toString(),
                            jSONObject.get("lastName").toString(),
                            jSONObject.get("town").toString(),
                            jSONObject.get("street").toString(),
                            jSONObject.get("postcode").toString(),
                            jSONObject.get("telephoneNumber").toString(),
                            jSONObject.get("nip").toString(),
                            jSONObject.get("dateAdded").toString()));

                    id.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
                    firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                    lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                    town.setCellValueFactory(new PropertyValueFactory<Customer, String>("town"));
                    street.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
                    postcode.setCellValueFactory(new PropertyValueFactory<Customer, String>("postcode"));
                    telephoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("telephoneNumber"));
                    nip.setCellValueFactory(new PropertyValueFactory<Customer, String>("nip"));
                    dateAdded.setCellValueFactory(new PropertyValueFactory<Customer, String>("dateAdded"));
                }
                customerTableView.setItems(masterData);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

    }

    @FXML
    public void actionOpenCreateCustomerWindow(){
        Parent root = null;
        Stage secondStage = new Stage();
        try{
            root = FXMLLoader.load(getClass().getResource("/fxml/CreateCustomerWindow.fxml"));
        } catch(IOException ex){

        }
        secondStage.setScene(new Scene(root, 400, 400));
        secondStage.setMinWidth(400);
        secondStage.setMinHeight(400);
        secondStage.setTitle("Stwórz klienta");
        secondStage.show();

    }


    @FXML//to juz usuniete
    public void actionOpenReadCustomerWindow(){
        Parent root = null;
        Stage secondStage = new Stage();
        try{
            root = FXMLLoader.load(getClass().getResource("/fxml/ReadCustomerWindow.fxml"));
        } catch(IOException ex){

        }
        secondStage.setScene(new Scene(root, 400, 400));
        secondStage.setMinWidth(400);
        secondStage.setMinHeight(400);
        secondStage.setTitle("Wczytaj klientów");
        secondStage.show();




    }

    /*@FXML
    public void xxxx(JSONArray jSONArray){
        try {

            System.out.println("Odczyt jsonArray jako wynik wyszukiwania: "+jSONArray);


            JSONParser parser = new JSONParser();
            ObservableList<Customer> masterData = FXCollections.observableArrayList();
            for (Object object : jSONArray) {
                JSONObject jSONObject = (JSONObject) parser.parse(object.toString());
                System.out.println("jSONObject : " + jSONObject.toJSONString());
                masterData.add(new Customer(
                        Integer.parseInt(jSONObject.get("id").toString()),
                        jSONObject.get("firstName").toString(),
                        jSONObject.get("lastName").toString(),
                        jSONObject.get("town").toString(),
                        jSONObject.get("street").toString(),
                        jSONObject.get("postcode").toString(),
                        jSONObject.get("telephoneNumber").toString(),
                        jSONObject.get("nip").toString(),
                        jSONObject.get("dateAdded").toString()));


                System.out.println("nic1");
                id.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
                System.out.println("nic2");
                firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                System.out.println("nic3");
                lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                town.setCellValueFactory(new PropertyValueFactory<Customer, String>("town"));
                street.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
                postcode.setCellValueFactory(new PropertyValueFactory<Customer, String>("postcode"));
                telephoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("telephoneNumber"));
                nip.setCellValueFactory(new PropertyValueFactory<Customer, String>("nip"));
                dateAdded.setCellValueFactory(new PropertyValueFactory<Customer, String>("dateAdded"));
            }
            customerTableView.setItems(masterData);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

    @FXML
    public void actionReadCustomers(){
        System.out.println("Wchodze do wczytania klientow z okna employee");

        JSONObject json = new JSONObject();
        try {
            json.put("id", Integer.parseInt(idFieldId.getText()));
        }catch (NumberFormatException e){
            json.put("id", 0);
        }
        json.put("firstName", idFieldFirstName.getText());
        json.put("lastName", idFieldLastName.getText());
        json.put("town", idFieldTown.getText());
        json.put("street", idFieldStreet.getText());
        json.put("postcode", idFieldPostcode.getText());
        json.put("telephoneNumber", idFieldTelephoneNumber.getText());
        json.put("nip", idFieldNip.getText());
        json.put("dateAdded", idFieldDateAdded.getText());

        System.out.println(json);

        try {
            URL url = new URL("http://localhost:8080/api/customer/read");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST"); //zeby wyslac jakies obiekt JSON chyba nie da sie z GET bo probowalem
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());  //wyslanie JSON
            wr.flush();
            wr.flush();
            wr.close();
            conn.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("poszłooooooooooooooooo");
            JSONParser parser = new JSONParser();
            JSONArray jSONArray = (JSONArray) parser.parse(in.readLine());

            ObservableList<Customer> masterData = FXCollections.observableArrayList();
            for (Object object : jSONArray) {
                JSONObject jSONObject = (JSONObject) parser.parse(object.toString());
                System.out.println("jSONObject : " + jSONObject.toJSONString());
                masterData.add(new Customer(
                        Integer.parseInt(jSONObject.get("id").toString()),
                        jSONObject.get("firstName").toString(),
                        jSONObject.get("lastName").toString(),
                        jSONObject.get("town").toString(),
                        jSONObject.get("street").toString(),
                        jSONObject.get("postcode").toString(),
                        jSONObject.get("telephoneNumber").toString(),
                        jSONObject.get("nip").toString(),
                        jSONObject.get("dateAdded").toString()));

                id.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
                firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                town.setCellValueFactory(new PropertyValueFactory<Customer, String>("town"));
                street.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
                postcode.setCellValueFactory(new PropertyValueFactory<Customer, String>("postcode"));
                telephoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("telephoneNumber"));
                nip.setCellValueFactory(new PropertyValueFactory<Customer, String>("nip"));
                dateAdded.setCellValueFactory(new PropertyValueFactory<Customer, String>("dateAdded"));
            }
            customerTableView.setItems(masterData);

        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }
    }


    @FXML
    public void actionDeleteCustomer(){

        Customer selectedPerson = null;
        /* customerTableView.requestFocus();
        customerTableView.getSelectionModel().select(2);
        System.out.println(customerTableView.getFocusModel().focus(2));*/
        if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            selectedPerson = customerTableView.getSelectionModel().getSelectedItem();
            //nameTextField.setText(selectedPerson.getName());
            //addressTextField.setText(selectedPerson.getAddress());
            System.out.println(selectedPerson.getId());
        }

        JSONObject json = new JSONObject();
        //json.put("login", fieldLogin.getText());
        json.put("id", selectedPerson.getId().toString());

        System.out.println(json);

        try {
        URL url = new URL("http://localhost:8080/api/customer/delete");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod("POST"); //zeby wyslac jakies obiekt JSON chyba nie da sie z GET bo probowalem
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());  //wyslanie JSON
        wr.flush();
        wr.flush();
        wr.close();
        conn.getInputStream();

        readDatabase();
        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("Loading Application Error.", ex);
        }

    }

    @FXML
    public void back() { //nazwa onAction przycisku
        mainWindowController.loadMenuScreen();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }


}
