package os;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
    TO-DO:
    > istaisyti komandu ieskojima findcommand() - ???
*/
public class OS{
    
    // KONSTANTOS
    private static final int RM_MEMORY_SIZE = 1000;
    private static final int VM_MEMORY_SIZE = 100;
    private static final int EXTERNAL_MEMORY_SIZE = 4000;
    
    // REALIOS MAŠINOS ATMINTS
    public static Memory[] rmMemory = new Memory[RM_MEMORY_SIZE];
    // IŠORINĖ ATMINTIS
    public static Memory[] externalMemory = new Memory[EXTERNAL_MEMORY_SIZE];
    
    public static RealMachine realMachine = new RealMachine(); 
    public static VirtualMachine virtualMachine = new VirtualMachine();
    public static GUI OSgui = new GUI();
    public static ChannelDevice cd = new ChannelDevice();
    
    // ATMINTIES INCIAlIZAVIMAS
    public static void memoryInit(){
        for( int i = 0 ; i < RM_MEMORY_SIZE ; i++ ){
            rmMemory[i] = new Memory();
        }
        for( int i = 0 ; i < EXTERNAL_MEMORY_SIZE ; i++ ){
            externalMemory[i] = new Memory();
        }
    }

    // RM BEI VM MAŠINŲ KOMANDOS
    public static String[] COMMANDS = {
        // Bendros komandos
        "CHNGR", "LR", "SR", "LO", "AD", "SB", "MP", "DI", "CR", "RL", "RG", "CZ",
        "JC", "JP", "CA", "PU", "PO", "RETRN", "SY", "LP", 
        // Tik realios masinos komandos
        "CHNGE", "PI", "TI", "PTR", "SP", "IN", "START", "CALLI", "IRETN",
        "SB", "DB", "ST", "DT", "SZ", "XCHGN"
    };

    // ATMINTIES OUTPUT
    public static void memoryMonitoring(){
        for( int i = 0 ; i < RM_MEMORY_SIZE ; i++ ){   
            System.out.println(i + " " + rmMemory[i]);
        }
    }
    
    public static String getValue(int commandNumber, String command)
    {
        if( commandNumber >= 0 ){
            String commandBegin = COMMANDS[commandNumber];
            return command.replace(commandBegin, "");
        } else { 
            OS.realMachine.setRegisterPI(2);
            return "COMMAND NOT FOUND";
        }
    }
    
    // PAGAL MOD REIKŠMĘ PASKIRIAMAS KOMANDOS VYKDYMAS ATITINKAMAI MAŠINAI
    public static void executeCommand(){
        
        String command = getCommand();
        int commandNumber = findCommand(command);
        String value = getValue(commandNumber, command);
        
        realMachine.setRegisterIC(realMachine.getRegisterIC() + 1);
        realMachine.setRegisterTI(realMachine.getRegisterTI() - 1);
        
        if( !value.equals("COMMAND NOT FOUND") ){
            if( realMachine.isRegisterMOD() ){
                // MOD = 1, paskiriame komandos vykdyma virtualiai masinai
                    virtualMachine.doCommand(commandNumber, value );
            } else {
                // MOD = 0, paskiriame komandos vykdyma realiai masinai
                    realMachine.doCommand(commandNumber, value);
            }
        } else {
            // PI = 2, pertraukimo reikšmė dėl neleistino operacijos kodo
            OS.realMachine.setRegisterPI(2);
        }
        
    }
    
    // GAUNAMA KOMANADA IŠ NURODYTOS CELL PAGAL IC REGISTRĄ
    public static String getCommand()
    {
        if (realMachine.isRegisterMOD())
        {
            //turim ziureti pagal virtualia masina
            return rmMemory[virtualMachine.getRegisterIC()].getCell();
        }
        else 
        {
            //turim ziuret pagal realia masina
            return rmMemory[realMachine.getRegisterIC()].getCell();
        }
    }
    
    // IEŠKOMA KOMANDOS KOMANDŲ SĄRAŠE. SUTVARKYTI!
    public static int findCommand( String command ){
        for( char i = 0 ; i < COMMANDS.length; i++ ){
            if ( command.contains(COMMANDS[i]) ){
                return i;
            } 
        }
        return -1;
    }
    
    public static void main(String[] args) {
        
        memoryInit();
       
        OSgui.fillRMmemory();
        OSgui.fillExternalMemory();
        OSgui.fillVMmemory();
        
        rmMemory[0].setCell("AD050");
        rmMemory[0].setState(true);
        rmMemory[50].setCell("14");
        rmMemory[50].setState(true);
        
        OSgui.refreshRegisterFields();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String command = getCommand();
        int commandNumber = findCommand(command);
        String value = getValue(commandNumber,command);
        executeCommand();
        
        rmMemory[51].setCell("5");
        rmMemory[51].setState(true);
        rmMemory[1].setCell("DI051");
        rmMemory[1].setState(true);
        realMachine.setRegisterIC(1);
        
        String command1 = getCommand();
        int commandNumber1 = findCommand(command1);
        String value1 = getValue(commandNumber1,command1);
        executeCommand();
        
        OSgui.refreshRegisterFields();
        
        
        realMachine.setST(3);
        realMachine.setSB(0);
        realMachine.setSZ(2);
        realMachine.setDT(3);
        
        
        cd.DataExchange();
        System.out.println("SEKANTIS");
        realMachine.setST(3);
        realMachine.setSB(1);
        realMachine.setDB(2);
        realMachine.setSZ(2);
        realMachine.setDT(2);
        
        
        cd.DataExchange();
        
        realMachine.setST(3);
        realMachine.setSB(1);
        realMachine.setDB(0);
        realMachine.setSZ(2);
        realMachine.setDT(1);
        
        
        cd.DataExchange();
        
        
    }
    
    public static int getRM_MEMORY_SIZE() {
        return RM_MEMORY_SIZE;
    }

    public static int getEXTERNAL_MEMORY_SIZE() {
        return EXTERNAL_MEMORY_SIZE;
    }
    
    public static int getVM_MEMORY_SIZE(){
        return VM_MEMORY_SIZE;
    }
}
