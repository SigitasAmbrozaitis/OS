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
    @FXML private TextField commandInput;
    @FXML private Button button_execute;
    @FXML public Label label;
    @FXML private Label mod;
    @FXML private Label r;
    @FXML private Label ptr;
    @FXML private Label ic;
    @FXML private Label _int;
    @FXML private Label sp;
    @FXML private Label c;
    @FXML public Label pi;
    @FXML private Label si;
    @FXML private Label ti;
    @FXML private Label ct;
    @FXML private Label sb;
    @FXML private Label db;
    @FXML private Label st;
    @FXML private Label dt;
    @FXML private Label sz;
    static String output = "";
    static String Routput = "";
    static String PTRoutput = "";
    static String ICoutput = "";
    static String SPoutput = "";
    static String INToutput = "";
    static String CToutput = "";
    static String Coutput = "";
    static String PIoutput = "";
    static String SIoutput = "";
    static String TIoutput = "";
    static String MODoutput = "";
    static String SBoutput = "";
    static String DBoutput = "";
    static String SToutput = "";
    static String DToutput = "";
    static String SZoutput = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crm1 = new CRM();
//        crm1.executeCommand("PI101");
//        crm1.executeCommand("TI401");
//        crm1.executeCommand("SP111");
//        crm1.executeCommand("IN100");
//        crm1.executeCommand("PTR19");
        crm1.getCpu().printCPURegisters();
        crm1.getCpu().updateRegistersCCPUController();
        crm1.getCCD().updateRegisterCCDController();
        updateRegistersGUI();

        button_execute.setOnAction(event -> execute());
    }

    private void execute(){
        String command = commandInput.getText();
        System.out.println(command);
        crm1.executeCommand(command);
        commandInput.clear();
        label.setText(output);
        crm1.getCpu().updateRegistersCCPUController();
        crm1.getCCD().updateRegisterCCDController();
        updateRegistersGUI();
        crm1.getCpu().printCPURegisters();
    }

    void setLabel(String text){
        label.setText(text);
    }

    void clearLabel(){
        label.setText("");
    }

    private void updateRegistersGUI(){
        pi.setText(PIoutput);
        r.setText(Routput);
        ptr.setText(PTRoutput);
        ic.setText(ICoutput);
        sp.setText(SPoutput);
        _int.setText(INToutput);
        ct.setText(CToutput);
        c.setText(Coutput);
        sp.setText(SPoutput);
        ti.setText(TIoutput);
        si.setText(SIoutput);
        mod.setText(MODoutput);
        sb.setText(SBoutput);
        db.setText(DBoutput);
        st.setText(SToutput);
        dt.setText(DToutput);
        sz.setText(SZoutput);
    }

    public void Tick()
    {
        crm1.Tick();
    }
}
