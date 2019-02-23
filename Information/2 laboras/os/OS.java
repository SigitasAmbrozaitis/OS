/*
TODO:
    1 apsaugoti, kad atpazinus komanda butu validus argumentas
    
 */
package os;


import static java.lang.String.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Algirdas
 */
public class OS { 

    /**
     * @param args the command line arguments
     */  
    public static final int RM_MEMORY_SIZE = 1000;
    public static final int VM_MEMORY_SIZE = 100;
    public static final int EXTERNAL_MEMORY_SIZE = 4000;
    
    //agregatai
    public static RealMachine realMachine = new RealMachine();
    public static VirtualMachine virtualMachine = new VirtualMachine();
    public static Paging paging = new Paging();
    
    // REALIOS MAŠINOS ATMINTS
    public static Memory[] rmMemory = new Memory[RM_MEMORY_SIZE];
    // IŠORINĖ ATMINTIS
    public static Memory[] externalMemory = new Memory[EXTERNAL_MEMORY_SIZE];
    
    //galimos komandos
    public static String[] COMMANDS = {
        // Bendros komandos
        "CHNGR", "LR", "SR", "LO", "AD", "SB", "MP", "DI", "CR", "RL", "RG", "CZ",
        "JC", "JP", "CA", "PU", "PO", "RETRN", "SY", "LP", 
        // Tik realios masinos komandos
        "CHNGE", "PI", "TI", "PTR", "SP", "IN", "START", "CALLI", "IRETN",
        "SB", "DB", "ST", "DT", "SZ", "XCHGN"
    };
    
    
    public static void memoryInit()
    {
        for( int i = 0 ; i < RM_MEMORY_SIZE ; i++ )
        {
            rmMemory[i] = new Memory();
        }
        for( int i = 0 ; i < EXTERNAL_MEMORY_SIZE ; i++ )
        {
            externalMemory[i] = new Memory();
        }
    }
    
    // ATMINTIES OUTPUT
    public static void memoryMonitoring(){
        for( int i = 0 ; i < RM_MEMORY_SIZE ; i++ ){   
            System.out.println(i + " " + rmMemory[i]);
        }
    }
    
    //darbas su komandomis
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
    public static int findCommand( String command ){
        
        for(char i = 0 ; i < COMMANDS.length; i++ ){
            if ( command.contains(COMMANDS[i]) ){
                return i;
            }
        }
        return -1;
    }
    public static String getValue(int commandNumber, String command)
    {
        if( commandNumber >= 0 )
        {
            String commandBegin = COMMANDS[commandNumber];
            return command.replace(commandBegin, "");
        } else 
        { 
            OS.realMachine.setRegisterPI(2);
            return "COMMAND NOT FOUND";
        }
    }
    public static void executeCommand()
    {        
        String command = getCommand();
        int commandNumber = findCommand(command);
        String value = getValue(commandNumber, command);
        
        realMachine.setRegisterIC(realMachine.getRegisterIC() + 1);
        realMachine.setRegisterTI(realMachine.getRegisterTI() - 1);
        
        if( !value.equals("COMMAND NOT FOUND") )
        {
            if( realMachine.isRegisterMOD() )
            {
                // MOD = 1, paskiriame komandos vykdyma virtualiai masinai
                    virtualMachine.doCommand(commandNumber, value );
            } 
            else
            {
                // MOD = 0, paskiriame komandos vykdyma realiai masinai
                    realMachine.doCommand(commandNumber, value);
            }
        }
        else 
        {
            // PI = 2, pertraukimo reikšmė dėl neleistino operacijos kodo
            OS.realMachine.setRegisterPI(2);
        }
        
    }
    public static void checkInterupts()
    {
        if (realMachine.getRegisterSI() != 0 || 
            realMachine.getRegisterPI() != 0 ||
            realMachine.getRegisterTI() == 0)
        {
            opperateInterupts();
        }
    }
    public static void opperateInterupts()
    {
        if (realMachine.getRegisterSI() != 0)
        {
            realMachine.setRegisterSI(0);
        }
        else if (realMachine.getRegisterPI() != 0)
        {
            realMachine.setRegisterPI(0);
        }else if (realMachine.getRegisterTI() == 0)
        {
            realMachine.setRegisterTI(50);
        }
    }
    public static void cpu()
    {
        executeCommand();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkInterupts();      
    }
    public static void main(String[] args) {
        // TODO code application logic here
        String a = "2";
        String b = "1";
        String c = String.valueOf(Integer.valueOf(a) - Integer.valueOf(b));
        boolean d = false;
        String f = "false";
        System.out.println(String.valueOf(d));
        memoryInit();
        //virtualMachine.memoryInit();
        //printMemory();
        rmMemory[0].setCell("AD050");
        String command = getCommand();
        int commandNumber = findCommand(command);
        String value = getValue(commandNumber, command);
        System.out.println(Boolean.valueOf(f));
        /*while(true)
        {
            if(rm.mod == 0)
            command = memory[rm.ic]
            else 
            {
            command = memory[vm.ic]
            }
            atpazystam komnda 
        }*/
    }
    
}
