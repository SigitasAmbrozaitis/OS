package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Memory.CBlock;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("OS");
        primaryStage.setScene(new Scene(root, 877, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        CRM crm1 = new CRM();
        CVM cvm = new CVM(crm1);
        CVM cvm2 = new CVM(crm1);
        CVM cvm3= new CVM(crm1);
        System.out.println("Dabar: " + cvm.currentAddress + "VM nr: " + cvm.VMcounter);
        System.out.println(crm1.getMemory().memory.get(20).getBlockNumber());
        for(VMBlock x : cvm2.vm1.vmMemory)
            System.out.println("Real Address: " + x.realAddress + " Virtual Address: " + x.virtualAddress);

        launch(args);
    }
}
