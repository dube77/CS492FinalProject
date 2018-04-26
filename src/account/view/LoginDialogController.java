package account.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

import account.FileManager;
import account.Hashing;

public class LoginDialogController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private Stage dialogStage;
    private boolean isLoggedIn = false;
    private boolean isRegistering = false;
    private Hashing h = new Hashing();
    private FileManager fm = new FileManager();
    private String username;
    private String password;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public String getUsername() {
    	return this.username;
    }
    
    public String getPassword() {
    	return this.password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public boolean isRegistering() {
    	return isRegistering;
    }
    
    

    @FXML
    private void handleLogin() {
        if (isLoginValid()) {
            username = usernameField.getText();
            password = passwordField.getText();

            isLoggedIn = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleRegister() {
    	if (isRegisterValid()) {
    		username = usernameField.getText();
            password = passwordField.getText();
    		
    		isRegistering = true;
            dialogStage.close();
    	}
    }

    private boolean isLoginValid() {
        String errorMessage = "";

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n"; 
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n"; 
        }
        String hash = h.hash(usernameField.getText() + passwordField.getText());
        if (!fm.isFile(hash, new File("data/"))) {
        	errorMessage += "User account not found!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    private boolean isRegisterValid() {
        String errorMessage = "";

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n"; 
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n"; 
        }
        String hash = h.hash(usernameField.getText() + passwordField.getText());
        if (fm.isFile(hash, new File("data/"))) {
        	errorMessage += "User account already exists!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}