package account;

import java.io.IOException;
import java.util.ArrayList;

import account.model.Account;
import account.view.AccountEditDialogController;
import account.view.AccountOverviewController;
import account.view.LoginDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Encryption crypt = new Encryption();
    private FileManager fm = new FileManager();
    private Hashing h = new Hashing();
    private String key = "Bar12345Bar12345"; // 128 bit key
    private String initVector = "RandomInitVector"; // 16 bytes IV
    private String username;
    private String password;
    
    private ObservableList<Account> accountData = FXCollections.observableArrayList();

    public MainApp() {
        // Add some sample data
//        accountData.add(new Account("Reddit", "User", "Pass", "http://www.reddit.com", ""));
//        accountData.add(new Account("Facebook", "User", "Pass", "http://www.facebook.com", ""));
//        accountData.add(new Account("Twitter", "User", "Pass", "http://www.twitter.com", ""));
    }

    public ObservableList<Account> getAccountData() {
        return accountData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Password Manager");
        
        if (showLoginDialog()) {
        	initRootLayout();
            showAccountOverview();
        }
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAccountOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonalOverview.fxml"));
            AnchorPane accountOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(accountOverview);

            AccountOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showAccountEditDialog(Account account) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AccountEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Account");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AccountEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAccount(account);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showLoginDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LoginDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            this.username = controller.getUsername();
            this.password = controller.getPassword();
            
            if (controller.isLoggedIn()) {
            	ArrayList<String> inputLines = new ArrayList<>();
            	String passHash = h.hash(this.username + this.password);
            	inputLines = fm.Read("data/" + passHash);
            	
            	//XOR THE KEY AND PASSHASH FOR ENCRYPTION
            	return true;
            } else if (controller.isRegistering()) {
            	return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop() {
    	//Save file before the program closes
    	ArrayList<String> encryptedLines = new ArrayList<>();
    	for (Account a : accountData) {
    		encryptedLines.add(crypt.encrypt(key, initVector, a.toString()));
    	}
    	fm.Write(encryptedLines, "data/" + h.hash(this.username + this.password));
    }
}