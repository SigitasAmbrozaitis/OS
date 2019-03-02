package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private CRM crm1;

    @FXML
    private TextField commandInput;

    @FXML
    private Button button_execute;

    @FXML
    public Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label = new Label();
        crm1 = new CRM();
//        crm1.executeCommand("PI101");
//        crm1.executeCommand("TI401");
//        crm1.executeCommand("SP111");
//        crm1.executeCommand("IN100");
//        crm1.executeCommand("PTR19");
//        crm1.getCpu().printCPURegisters();

        button_execute.setOnAction(event -> execute());
    }

    private void execute(){
        String command = commandInput.getText();
        System.out.println(command);
        crm1.executeCommand(command);
        commandInput.clear();
    }

    void setLabel(String text){
        label.setText(text);
    }

    void clearLabel(){
        label.setText("");
    }
}
