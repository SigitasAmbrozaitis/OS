package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("OS");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
        //TODO run RM
        //TODO display GUI


    public static void main(String[] args) {
        CRM crm1 = new CRM();
        System.out.println("TEST");
        crm1.executeCommand("PI101");
        crm1.executeCommand("TI401");
        crm1.executeCommand("SP111");
        crm1.executeCommand("IN100");
        crm1.executeCommand("PTR19");
        crm1.getCpu().printCPURegisters();
        launch(args);
    }
}
