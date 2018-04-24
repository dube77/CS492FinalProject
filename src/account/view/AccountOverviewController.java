package account.view;

import javafx.fxml.FXML;
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
        // Initialize the person table with the two columns.
        websiteNameColumn.setCellValueFactory(cellData -> cellData.getValue().websiteNameProperty());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        accountTable.setItems(mainApp.getAccountData());
    }
}