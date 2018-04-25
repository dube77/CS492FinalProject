package account.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import account.MainApp;
import account.model.Account;

public class AccountOverviewController {
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Account, String> websiteNameColumn;
    @FXML
    private TableColumn<Account, String> usernameColumn;
    @FXML
    private TableColumn<Account, String> passwordColumn;

    @FXML
    private Label websiteNameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label websiteUrlLabel;
    @FXML
    private Label notesLabel;

    // Reference to the main application.
    private MainApp mainApp;

    public AccountOverviewController() {
    }

    @FXML
    private void initialize() {
        websiteNameColumn.setCellValueFactory(cellData -> cellData.getValue().websiteNameProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        
        showAccountDetails(null);
        
        accountTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> showAccountDetails(newValue));
    }
    
    @FXML
    private void handleDeleteAccount() {
    	int selectedIndex = accountTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            accountTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Account Selected");
            alert.setContentText("Please select an account in the table.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewAccount() {
        Account tempAccount = new Account();
        boolean okClicked = mainApp.showAccountEditDialog(tempAccount);
        if (okClicked) {
            mainApp.getAccountData().add(tempAccount);
        }
    }

    @FXML
    private void handleEditAccount() {
        Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            boolean okClicked = mainApp.showAccountEditDialog(selectedAccount);
            if (okClicked) {
                showAccountDetails(selectedAccount);
            }

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Account Selected");
            alert.setContentText("Please select an account in the table.");

            alert.showAndWait();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        accountTable.setItems(mainApp.getAccountData());
    }
    
    private void showAccountDetails(Account account) {
        if (account != null) {
            websiteNameLabel.setText(account.getWebsiteName());
            usernameLabel.setText(account.getUsername());
            passwordLabel.setText(account.getPassword());
            websiteUrlLabel.setText(account.getWebsiteUrl());
            notesLabel.setText(account.getNotes());
        } else {
            websiteNameLabel.setText("");
            usernameLabel.setText("");
            passwordLabel.setText("");
            websiteUrlLabel.setText("");
            notesLabel.setText("");
        }
    }
}