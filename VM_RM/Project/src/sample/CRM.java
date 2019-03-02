package sample;

/*imports*/

import java.util.Arrays;

/*Real Machine implementation*/
public class CRM
{
    private CCPU cpu ;
    private CMemory memory;

    //getters for GUI
    public CCPU getCpu()
    {
        return cpu;
    }

    public CMemory getMemory()
    {
        return memory;
    }

    /*private variables: registers: all registers will start with prefix reg*/

    /*constructor, used to set RM starting state*/

    /*commands that can be executed by RM*/

    //TODO JURGIS implements this
    //TODO implement commands string
    //TODO implement commands
    //TODO tip: when implementing RM commands implement first only RM commands then RM&VM commands. For easier copy paste to VM

    //TODO ANYONE should be done after commands are implemented
    //TODO implement command validation
    //TODO implement command and its parameters recognition (register recognition too)


    private String[] cmdR = { "PI","TI","SP","IN","BS","DB","ST","DT","SZ","PTR","CHNGM","CALLI","IRETN","START","XCHGN" };

    CRM(){
        cpu = new CCPU();
    }

    /*commands that can be executed by RM and VM */
    public void executeCommand(String command){
        int commandIndex;
        //get command name
        String cmd = command.replaceAll("[^A-Za-z]+", "");
        if(Arrays.asList(cmdR).indexOf(cmd) == -1){
            System.out.println("No command found");
            return;
        }
        //get passed value
        String value = command.replaceAll("\\D+","");
        //get command index in cmdR
        commandIndex = Arrays.asList(cmdR).indexOf(cmd);
        switch(commandIndex){
            case 0:
                cpu.setRegPI(value.charAt(0));  //PIxyz PI = x x=0..9
                break;
            case 1:
                cpu.setRegTI(value.charAt(0));  //TIxyz TI = x x=0..9
                break;
            case 2:
                cpu.setRegSP((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
                        (Character.getNumericValue(value.charAt(1)) * 10)
                        + (Character.getNumericValue(value.charAt(2)))));
                break;
            case 3:
                cpu.setRegINT((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
                        (Character.getNumericValue(value.charAt(1)) * 10)
                        + (Character.getNumericValue(value.charAt(2)))));
                break;
                //kanalu irenginys BS DB ST DT SZ
            case 9:
                cpu.setRegPTR((short) ((Character.getNumericValue(value.charAt(0)) * 10) +
                        (Character.getNumericValue(value.charAt(1)))));
                break;

            default:
                System.out.println("Error finding command");
        }
        System.out.println("Execute command: " + cmdR[commandIndex] + " value: " + value);
    }




}
