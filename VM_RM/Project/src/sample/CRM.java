package sample;

/*imports*/


import java.util.Arrays;

/*Real Machine implementation*/
public class CRM
{
    private class CCommand
    {
        public String cmd;
        public String param;
    }

    private class EError
    {
        public final static short VALIDATION_SUCCESS = 0;
        public final static short ACCESS_VIOLATION = 1;
        public final static short COMMAND_VIOLATION = 2;
        public final static short MEMORY_VIOLATION = 3;
        public final static short ASSIGMENT_VIOLATION = 4;
    }

    private CCPU cpu ;
    private CCD cd;
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

    /*constructor, used to set rm starting state*/

    /*commands that can be executed by rm*/

    //todo jurgis implements this
    //todo implement commands string
    //todo implement commands
    //todo tip: when implementing rm commands implement first only rm commands then rm&vm commands. for easier copy paste to vm

    //todo anyone should be done after commands are implemented
    //todo implement command validation
    //todo implement command and its parameters recognition (register recognition too)


    private String[] cmdR = { "PI","TI","SP","IN","BS","DB","ST","DT","SZ","PTR","CHNGM","CALLI","IRETN","START","XCHGN" };

    CRM(){
        cpu = new CCPU();
        cd = new CCD();
        memory = new CMemory();
    }

    /*commands that can be executed by RM and VM */
    public void executeCommand(String command){

        CCommand cmdD = new CCommand();
        if(ValidateCommand(command, cmdD) > 0) return;

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
            case 4:
                cd.setRegSB((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
                        (Character.getNumericValue(value.charAt(1)) * 10)
                        + (Character.getNumericValue(value.charAt(2)))));
                break;
            case 5:
                cd.setRegDB((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
                        (Character.getNumericValue(value.charAt(1)) * 10)
                        + (Character.getNumericValue(value.charAt(2)))));
                break;
            case 6:
                cd.setRegST((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
                        (Character.getNumericValue(value.charAt(1)) * 10)
                        + (Character.getNumericValue(value.charAt(2)))));
                break;
            case 7:
                cd.setRegDT((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
                        (Character.getNumericValue(value.charAt(1)) * 10)
                        + (Character.getNumericValue(value.charAt(2)))));
                break;
            case 8:
                cd.setRegSZ((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
                        (Character.getNumericValue(value.charAt(1)) * 10)
                        + (Character.getNumericValue(value.charAt(2)))));
                break;

                //kanalu irenginys BS DB ST DT SZ
            case 9:
                cpu.setRegPTR((short) ((Character.getNumericValue(value.charAt(0)) * 10) +
                        (Character.getNumericValue(value.charAt(1)))));
                break;

            case 10:
                cpu.setRegMOD();
                break;
            case 11:
                memory.CALLI();
                break;
            case 12:
                memory.IRETN();
                break;
            case 13:
                START();
                break;
            case 14:
                cd.XCHGN();
                break;
            default:
                System.out.println("Error finding command");
        }
        Controller.output = "Executed command: " + cmdR[commandIndex] + " value: " + value;
        System.out.println(Controller.output);
    }

    short ValidateCommand(String strCommand, CCommand command)
    {
        //TODO get command
        //TODO check command
        //TODO get param
        //TODO check param




        return EError.VALIDATION_SUCCESS;
    }

    private void START(){
        cpu.setRegIC((short)0);
    }




}
