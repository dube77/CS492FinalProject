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
    private String key; // 128 bit key
    private String initVector = "RandomInitVector"; // 16 bytes IV
    private String username;
    private String password;
    
    private ObservableList<Account> accountData = FXCollections.observableArrayList();

    public MainApp() {
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
            	//Read data from file
            	ArrayList<String> inputLines = new ArrayList<>();
            	String passHash = h.hash(this.username + this.password);
            	inputLines = fm.Read("data/" + passHash);
            	
            	//Compute decryption key from password and username
            	this.key = (h.hash(this.password + this.username)).substring(0, 16);
            	ArrayList<String> decryptedLines = new ArrayList<>();
            	for (String inputLine : inputLines) {
            		decryptedLines.add(crypt.decrypt(this.key, initVector, inputLine));
            	}
            	
            	//Setup accounts with decrypted lines
            	for (String decryptedLine : decryptedLines) {
            		String[] data = decryptedLine.split("`");
            		accountData.add(new Account(data[0], data[1], data[2], data[3], data[4]));
            	}
            	
            	return true;
            } else if (controller.isRegistering()) {
            	this.key = (h.hash(this.password + this.username)).substring(0, 16);
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
    		encryptedLines.add(crypt.encrypt(this.key, initVector, a.toString()));
    	}
    	fm.Write(encryptedLines, "data/" + h.hash(this.username + this.password));
    }
}