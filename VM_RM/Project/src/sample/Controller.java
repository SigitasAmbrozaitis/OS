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
    //@FXML private Button button_reset;
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
//    @FXML private Label cvm_R;
//    @FXML private Label cvm_C;
//    @FXML private Label cvm_IC;
//    @FXML private Label cvm_CT;
//    @FXML private Label cvm_SP;
//    static String cvm_R_output;
//    static String cvm_C_output;
//    static String cvm_IC_output;
//    static String cvm_CT_output;
//    static String cvm_SP_output;
    //channel device
    @FXML private TextField cd_input;
    @FXML private TextField cd_output;
    static String cd_output_output;
    @FXML private Button button_cd_enter;

    public static String channelDeviceInput;

    @FXML private Label vm_count;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crm1 = new CRM();

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

        //crm1.getCpu().printCPURegisters();
        crm1.getCpu().updateRegistersCCPUController();
        crm1.getCCD().updateRegisterCCDController();
        updateRegistersGUI();
        updateMemoryTableGUI();
        updateVMcount();
        //checkVMs();

        button_execute.setOnAction(event -> execute());
        button_tick.setOnAction(event -> Tick());
        //button_reset.setOnAction(event -> initialize(location, resources));
        button_cd_enter.setOnAction(event -> cd_enter());
    }

    private void execute(){
        String command = commandInput.getText();
        if(command.isEmpty()){
            label.setText("No command to write");
        }else{
            System.out.println(command);
            crm1.ReadCommandInput(command);
            commandInput.clear();
            label.setText(output);
            crm1.getCpu().updateRegistersCCPUController();
            crm1.getCCD().updateRegisterCCDController();
            updateRegistersGUI();
            updateMemoryTableGUI();
          //  crm1.getCpu().printCPURegisters();
            updateVMcount();
            //checkVMs();
        }
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
        cd_output.setText(cd_output_output);
    }

    private void updateMemoryTableGUI(){
        column_0.setVisible(false);
        column_1.setVisible(false);
        column_2.setVisible(false);
        column_3.setVisible(false);
        column_4.setVisible(false);
        column_5.setVisible(false);
        column_4.setVisible(false);
        column_7.setVisible(false);
        column_8.setVisible(false);
        column_9.setVisible(false);

        memoryTable.setItems(getCBlock());

        column_0.setVisible(true);
        column_1.setVisible(true);
        column_2.setVisible(true);
        column_3.setVisible(true);
        column_4.setVisible(true);
        column_5.setVisible(true);
        column_4.setVisible(true);
        column_7.setVisible(true);
        column_8.setVisible(true);
        column_9.setVisible(true);
    }

    private ObservableList<CBlock> getCBlock(){
        ObservableList<CBlock> memory = FXCollections.observableArrayList();
        for(int i = 0; i < 100; i++){
            memory.add(crm1.getMemory().memory.get(i));
        }
        return memory;
    }

    private void updateVMcount(){
        //TODO enable this when we will have multiple VM
        //vm_count.setText(""+crm1.VMs.size());
    }

    private void Tick()
    {
        crm1.Tick();
        crm1.getCpu().updateRegistersCCPUController();
        crm1.getCCD().updateRegisterCCDController();
        updateRegistersGUI();
        updateMemoryTableGUI();
      //  crm1.getCpu().printCPURegisters();
        updateVMcount();
        //checkVMs();
    }
/**
 * probably will delete later
 */
//    private void checkVMs(){
//        //TODO enable this when we will have multiple VM
////        if(crm1.VMs.size() > 0){
////            crm1.VMs.elementAt(0).updateRegistersVMCPU();
////            cvm_R.setText(cvm_R_output);
////            cvm_SP.setText(cvm_SP_output);
////            cvm_C.setText(cvm_C_output);
////            cvm_CT.setText(cvm_CT_output);
////            cvm_IC.setText(cvm_IC_output);
////        }
//        if(crm1.VM!=null)
//        {
//            crm1.VM.updateRegistersVMCPU();
//            cvm_R.setText(cvm_R_output);
//            cvm_SP.setText(cvm_SP_output);
//            cvm_C.setText(cvm_C_output);
//            cvm_CT.setText(cvm_CT_output);
//            cvm_IC.setText(cvm_IC_output);
//        }
//
//
//    }

    /**
     * method for channel device input handling
     */
    private void cd_enter(){
        channelDeviceInput = cd_input.getText(); //gets input
        cd_input.clear();
        //probably will call a method here from channel device class and give the input as a parameter
    }
    public static String getChannelDeviceInput(){
        return channelDeviceInput;
    }
}
