package sample;

/*imports*/


import java.util.Arrays;
import java.util.Vector;

import sample.Enumerators.EError;
import sample.Enumerators.ERCommand;

/*Real Machine implementation*/
public class CRM
{
    private class CCommand
    {
        public String cmd;
        public String param;
        public boolean bNumber;
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

    //TODO HIGH PRIORITY update todo list :D
    //todo jurgis implements this
    //todo implement commands string
    //todo implement commands
    //todo tip: when implementing rm commands implement first only rm commands then rm&vm commands. for easier copy paste to vm

    //todo anyone should be done after commands are implemented
    //todo implement command validation
    //todo implement command and its parameters recognition (register recognition too)


    private String[] cmd2 = { "PI","TI","SP","BS","DB","ST","DT","SZ",};
    private String[] cmd3 = {"PTR", "INT"};
    private String[] cmd5 = {"XCHGN", "START", "IRETN", "CALLI", "CHNGM"};
    private Vector<String> commands = new Vector<String>();

    CRM(){
        cpu = new CCPU();
        cd = new CCD();
        memory = new CMemory();

        commands.addAll( Arrays.asList(cmd2));
        commands.addAll( Arrays.asList(cmd3));
        commands.addAll( Arrays.asList(cmd5));
    }

    /*commands that can be executed by RM and VM */
    public void executeCommand(String command){

        CCommand cmd = new CCommand();
        int errorCode = ValidateCommand(command, cmd);

        if(errorCode > 0) return; //handle error

        boolean bNum = cmd.bNumber;

        //TODO add method calls
        //every command has different param type string/int/none
        //check is executed before every function call
        switch(cmd.cmd)
        {
            case ERCommand.E_AD:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_BS:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_CA:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_CALLI:
                //no param execute command
                break;
            case ERCommand.E_CHNGM:
                //no param excute command
                break;
            case ERCommand.E_CHNGR:
                //no param execute command
                break;
            case ERCommand.E_CR:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_CZ:
                if(!cmd.bNumber)
                {
                    //excute command with param cmd.param
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_DB:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_DI:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_DT:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_IN:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_IRETN:
                //no param excute command
                break;
            case ERCommand.E_JC:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_JP:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_LO:
                if(!cmd.bNumber)
                {
                    //excute command with param cmd.param
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_LR:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_MP:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PI:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PO:
                if(!cmd.bNumber)
                {
                    //excute command with param cmd.param
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PTR:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PU:
                if(!cmd.bNumber)
                {
                    //excute command with param cmd.param
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_RG:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_RL:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_SB:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_SP:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_SR:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_ST:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_START:
                //no param execute command
                break;
            case ERCommand.E_SZ:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_TI:
                if(cmd.bNumber)
                {
                    //excute command with param Integer.parseInt(cmd.param)
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_XCHNG:
                //no param execute command
                break;

        }
        //TODO check whats needed, remove/keep after.

//        int commandIndex;
//        //get command name
//        String cmd = command.replaceAll("[^A-Za-z]+", "");
//        if(Arrays.asList(commands).indexOf(cmd) == -1){
//            System.out.println("No command found");
//            return;
//        }
//        //get passed value
//        String value = command.replaceAll("\\D+","");
        //get command index in cmdR
//        int commandIndex = commands.indexOf(cmd.cmd);
//        switch(commandIndex){
//            case 0:
//                cpu.setRegPI(value.charAt(0));  //PIxyz PI = x x=0..9
//                break;
//            case 1:
//                cpu.setRegTI(value.charAt(0));  //TIxyz TI = x x=0..9
//                break;
//            case 2:
//                cpu.setRegSP((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
//                        (Character.getNumericValue(value.charAt(1)) * 10)
//                        + (Character.getNumericValue(value.charAt(2)))));
//                break;
//            case 3:
//                cpu.setRegINT((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
//                        (Character.getNumericValue(value.charAt(1)) * 10)
//                        + (Character.getNumericValue(value.charAt(2)))));
//                break;
//            case 4:
//                cd.setRegSB((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
//                        (Character.getNumericValue(value.charAt(1)) * 10)
//                        + (Character.getNumericValue(value.charAt(2)))));
//                break;
//            case 5:
//                cd.setRegDB((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
//                        (Character.getNumericValue(value.charAt(1)) * 10)
//                        + (Character.getNumericValue(value.charAt(2)))));
//                break;
//            case 6:
//                cd.setRegST((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
//                        (Character.getNumericValue(value.charAt(1)) * 10)
//                        + (Character.getNumericValue(value.charAt(2)))));
//                break;
//            case 7:
//                cd.setRegDT((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
//                        (Character.getNumericValue(value.charAt(1)) * 10)
//                        + (Character.getNumericValue(value.charAt(2)))));
//                break;
//            case 8:
//                cd.setRegSZ((short)( (Character.getNumericValue(value.charAt(0)) * 100) +
//                        (Character.getNumericValue(value.charAt(1)) * 10)
//                        + (Character.getNumericValue(value.charAt(2)))));
//                break;
//
//                //kanalu irenginys BS DB ST DT SZ
//            case 9:
//                cpu.setRegPTR((short) ((Character.getNumericValue(value.charAt(0)) * 10) +
//                        (Character.getNumericValue(value.charAt(1)))));
//                break;
//
//            case 10:
//                cpu.setRegMOD();
//                break;
//            case 11:
//                memory.CALLI();
//                break;
//            case 12:
//                memory.IRETN();
//                break;
//            case 13:
//                START();
//                break;
//            case 14:
//                cd.XCHGN();
//                break;
//            default:
//                System.out.println("Error finding command");
//        }
//        Controller.output = "Executed command: " + cmdR[commandIndex] + " value: " + value;
//        System.out.println(Controller.output);
    }

    short ValidateCommand(String strCommand, CCommand command)
    {
        //TODO check if validation is correct, had no time for that :D
        if(strCommand.length() != 5) return EError.COMMAND_VIOLATION;

        //Get command name
        String cmd = "";
        if(CUtils.StringIsInArray(strCommand, cmd5, 5))
            cmd = strCommand;
        else if(CUtils.StringIsInArray(strCommand,cmd3, 3))
            cmd = strCommand.substring(0,3);
        else if(CUtils.StringIsInArray(strCommand,cmd2,2))
            cmd = strCommand.substring(0,2);

        if(cmd == "") return EError.COMMAND_VIOLATION;



        //Get command param
        String param = strCommand.substring(cmd.length());
        //check if param is register
        boolean bReg = CUtils.StringIsInArray(param, cpu.registers, 3);
        boolean bNum = true;
        int nParam = 0;
        //check if param is number
        try {
            nParam = Integer.parseInt(param);
        }
        catch (NumberFormatException e)
        {
            bNum = false;
        }

        if(!bReg && !bNum) return EError.COMMAND_VIOLATION;
        if(nParam < 0 || nParam > 999) return EError.ACCESS_VIOLATION;

        //TODO check for other violations
        command.cmd = cmd;
        command.param = param;
        return EError.VALIDATION_SUCCESS;
    }




    private void START(){
        cpu.setRegIC((short)0);
    }




}
