package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Memory.CBlock;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private CRM crm1;
    @FXML private TextField commandInput;
    @FXML private Button button_execute;
    @FXML private Button button_tick;
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
    @FXML private TableView<CBlock> memoryTable;
    @FXML private TableColumn<CBlock, Integer> column_0;
    @FXML private TableColumn<CBlock, String> column_1;
    @FXML private TableColumn<CBlock, String> column_2;
    @FXML private TableColumn<CBlock, String> column_3;
    @FXML private TableColumn<CBlock, String> column_4;
    @FXML private TableColumn<CBlock, String> column_5;
    @FXML private TableColumn<CBlock, String> column_6;
    @FXML private TableColumn<CBlock, String> column_7;
    @FXML private TableColumn<CBlock, String> column_8;
    @FXML private TableColumn<CBlock, String> column_9;
    @FXML private TableColumn<CBlock, String> column_10;

    @FXML
    private Label vm_count;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crm1 = new CRM();
        //for testing purposes add values to memory cells
//        crm1.getMemory().memory.get(0).block.get(0).cell = "a";
//        crm1.getMemory().memory.get(0).block.get(1).cell = "b";
//        crm1.getMemory().memory.get(0).block.get(2).cell = "b";
//        crm1.getMemory().memory.get(0).block.get(3).cell = "b";
//        crm1.getMemory().memory.get(0).block.get(4).cell = "b";
//        crm1.getMemory().memory.get(2).block.get(1).cell = "basd";
//        crm1.getMemory().memory.get(0).block.get(4).cell = "basd";

        System.out.println(crm1.getMemory().memory.get(0).block.get(4).cell);


        column_0.setCellValueFactory(new PropertyValueFactory<>("blockNumber"));
        column_1.setCellValueFactory(new PropertyValueFactory<>("Cell0"));
        column_2.setCellValueFactory(new PropertyValueFactory<>("Cell1"));
        column_3.setCellValueFactory(new PropertyValueFactory<>("Cell2"));
        column_4.setCellValueFactory(new PropertyValueFactory<>("Cell3"));
        column_5.setCellValueFactory(new PropertyValueFactory<>("Cell4"));
        column_6.setCellValueFactory(new PropertyValueFactory<>("Cell5"));
        column_7.setCellValueFactory(new PropertyValueFactory<>("Cell6"));
        column_8.setCellValueFactory(new PropertyValueFactory<>("Cell7"));
        column_9.setCellValueFactory(new PropertyValueFactory<>("Cell8"));
        column_10.setCellValueFactory(new PropertyValueFactory<>("Cell9"));

        memoryTable.setItems(getCBlock());

//        crm1.executeCommand("PI101");
//        crm1.executeCommand("TI401");
//        crm1.executeCommand("SP111");
//        crm1.executeCommand("IN100");
//        crm1.executeCommand("PTR19");
        crm1.getCpu().printCPURegisters();
        crm1.getCpu().updateRegistersCCPUController();
        crm1.getCCD().updateRegisterCCDController();
        updateRegistersGUI();
        updateMemoryTableGUI();
       // updateVMcount();

        button_execute.setOnAction(event -> execute());
        button_tick.setOnAction(event -> Tick());
    }

    private void execute(){
        String command = commandInput.getText();
        System.out.println(command);
        crm1.ReadCommandInput(command);
        commandInput.clear();
        label.setText(output);
        crm1.getCpu().updateRegistersCCPUController();
        crm1.getCCD().updateRegisterCCDController();
        updateRegistersGUI();
        updateMemoryTableGUI();
        crm1.getCpu().printCPURegisters();
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
        ti.setText(TIoutput);
        si.setText(SIoutput);
        mod.setText(MODoutput);
        sb.setText(SBoutput);
        db.setText(DBoutput);
        st.setText(SToutput);
        dt.setText(DToutput);
        sz.setText(SZoutput);
    }

    private void updateMemoryTableGUI(){ memoryTable.setItems(getCBlock()); }

    private ObservableList<CBlock> getCBlock(){
        ObservableList<CBlock> memory = FXCollections.observableArrayList();
        for(int i = 0; i < 100; i++){
            memory.add(crm1.getMemory().memory.get(i));
        }
        return memory;
    }

   /* private void updateVMcount(){
        vm_count.setText(""+CVM.VMcounter);
    }*/

    private void Tick()
    {
        crm1.Tick();
        crm1.getCpu().updateRegistersCCPUController();
        crm1.getCCD().updateRegisterCCDController();
        updateRegistersGUI();
        updateMemoryTableGUI();
        crm1.getCpu().printCPURegisters();
    }
}
