package account.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import account.model.Account;

public class AccountEditDialogController {

    @FXML
    private TextField websiteNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField websiteUrlField;
    @FXML
    private TextArea notesField;

    private Stage dialogStage;
    private Account account;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setAccount(Account account) {
        this.account = account;

        websiteNameField.setText(account.getWebsiteName());
        usernameField.setText(account.getUsername());
        passwordField.setText(account.getPassword());
        websiteUrlField.setText(account.getWebsiteUrl());
        notesField.setText(account.getNotes());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            account.setWebsiteName(websiteNameField.getText());
            account.setUsername(usernameField.getText());
            account.setPassword(passwordField.getText());
            account.setWebsiteUrl(websiteUrlField.getText());
            account.setNotes(notesField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (websiteNameField.getText() == null || websiteNameField.getText().length() == 0) {
            errorMessage += "No valid website name!\n"; 
        }
        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n"; 
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n"; 
        }
        if (websiteUrlField.getText() == null || websiteUrlField.getText().length() == 0) {
            errorMessage += "No valid website url!\n"; 
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