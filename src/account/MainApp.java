package account;

import java.io.IOException;

import account.model.Account;
import account.view.AccountOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Account> accountData = FXCollections.observableArrayList();

    public MainApp() {
        // Add some sample data
        accountData.add(new Account("Reddit", "User", "Pass", "http://www.reddit.com", ""));
        accountData.add(new Account("Facebook", "User", "Pass", "http://www.facebook.com", ""));
        accountData.add(new Account("Twitter", "User", "Pass", "http://www.twitter.com", ""));
    }

    public ObservableList<Account> getAccountData() {
        return accountData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Password Manager");

        initRootLayout();

        showAccountOverview();
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}