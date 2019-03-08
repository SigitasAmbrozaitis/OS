package sample;

/*imports*/


import java.util.Arrays;
import java.util.Vector;

import sample.Enumerators.EError;
import sample.Enumerators.ERCommand;

import sample.Memory.CMemory;

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


    /*commands that can be executed by rm*/
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

        if(errorCode!=EError.VALIDATION_SUCCESS)
        {
            cmdPI((short)errorCode);
            return;
        }

        boolean bNum = cmd.bNumber;

        switch(cmd.cmd)
        {
            case ERCommand.E_AD:
                if(cmd.bNumber)
                {
                    errorCode = cmdAD((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_BS:
                if(cmd.bNumber)
                {
                    errorCode = cmdBS((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_CA:
                if(cmd.bNumber)
                {
                    errorCode = cmdCA((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_CALLI:
                errorCode = cmdCALLI();
                break;
            case ERCommand.E_CHNGM:
                errorCode = cmdCHNGM();
                break;
            case ERCommand.E_CHNGR:
                errorCode = cmdCHNGR();
                break;
            case ERCommand.E_CR:
                if(cmd.bNumber)
                {
                    errorCode = cmdCR((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_CZ:
                if(!cmd.bNumber)
                {
                    errorCode = cmdCZ(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_DB:
                if(cmd.bNumber)
                {
                    errorCode = cmdDB((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_DI:
                if(cmd.bNumber)
                {
                    errorCode = cmdDI((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_DT:
                if(cmd.bNumber)
                {
                    errorCode = cmdDT((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_IN:
                if(cmd.bNumber)
                {
                    errorCode = cmdIN((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_IRETN:
                errorCode = cmdRETN();
                break;
            case ERCommand.E_JC:
                if(cmd.bNumber)
                {
                    errorCode = cmdJC((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_JP:
                if(cmd.bNumber)
                {
                    errorCode = cmdJP((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_LO:
                if(!cmd.bNumber)
                {
                    errorCode = cmdLO(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_LR:
                if(cmd.bNumber)
                {
                    errorCode = cmdLR((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_MP:
                if(cmd.bNumber)
                {
                    errorCode = cmdMP((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PI:
                if(cmd.bNumber)
                {
                    errorCode = cmdPI((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PO:
                if(!cmd.bNumber)
                {
                    errorCode = cmdPO(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PTR:
                if(cmd.bNumber)
                {
                    errorCode = cmdPTR((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_PU:
                if(!cmd.bNumber)
                {
                    errorCode = cmdPU(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_RG:
                if(cmd.bNumber)
                {
                    errorCode = cmdRG((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_RL:
                if(cmd.bNumber)
                {
                    errorCode = cmdRL((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_SB:
                if(cmd.bNumber)
                {
                    errorCode = cmdSB((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_SP:
                if(cmd.bNumber)
                {
                    errorCode = cmdSP((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_SR:
                if(cmd.bNumber)
                {
                    errorCode = cmdSR((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_ST:
                if(cmd.bNumber)
                {
                    errorCode = cmdST((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_START:
                errorCode = cmdSTART();
                break;
            case ERCommand.E_SZ:
                if(cmd.bNumber)
                {
                    errorCode = cmdSZ((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_TI:
                if(cmd.bNumber)
                {
                    errorCode = cmdTI((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_XCHNG:
                errorCode = cmdXCHGN();
                break;
            case ERCommand.E_RETRN:
                errorCode = cmdRETRN();
                break;
            case ERCommand.E_SY:
                errorCode = cmdSY((short)Integer.parseInt(cmd.param));
                break;
            case ERCommand.E_LP:
                errorCode = cmdLP((short)Integer.parseInt(cmd.param));
                break;
            default: errorCode = EError.COMMAND_VIOLATION;

        }

        if(errorCode!=EError.VALIDATION_SUCCESS)
        {
            cmdPI((short)errorCode);
        }
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

        command.cmd = cmd;
        command.param = param;
        command.bNumber = bNum;
        return EError.VALIDATION_SUCCESS;
    }
//    private void START(){
//        cpu.setRegIC((short)0);
//    }

    private short cmdPI(short input){cpu.setRegPI((short)(input/100)); return EError.VALIDATION_SUCCESS;}
    private short cmdTI(short input){cpu.setRegTI((short)(input/100)); return EError.VALIDATION_SUCCESS;}
    private short cmdCHNGM()
    {
        cpu.setRegMOD(!cpu.getRegMod());
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdSP(short input){cpu.setRegSP(input); return EError.VALIDATION_SUCCESS;}
    private short cmdIN(short input){cpu.setRegINT(input); return EError.VALIDATION_SUCCESS;}
    private short cmdPTR(short input){cpu.setRegPTR(input); return EError.VALIDATION_SUCCESS;}
    private short cmdCALLI(){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdRETN(){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdSTART(){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdBS(short input)
    {
        if(input > 99) return EError.ACCESS_VIOLATION;//memory block cant be >99
        cd.setRegSB(input);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdDB(short input)
    {
        if(input > 99) return EError.ACCESS_VIOLATION;//memory block cant be >99
        cd.setRegDB(input);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdST(short input)
    {
        if(input > 99) return EError.ACCESS_VIOLATION;//memory block cant be >99
        cd.setRegST(input);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdDT(short input)
    {
        if(input > 99) return EError.ACCESS_VIOLATION;//memory block cant be >99
        cd.setRegDT(input);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdSZ(short input)
    {
        if(input > 99) return EError.ACCESS_VIOLATION;//memory block cant be >99
        cd.setRegSZ(input);
        return EError.VALIDATION_SUCCESS;
    }

    private short cmdXCHGN(){ return EError.VALIDATION_SUCCESS;}//TODO implement

    private short cmdAD(short input){ cpu.getRegR().Add(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdSB(short input){ cpu.getRegR().Sub(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdMP(short input){ cpu.getRegR().Mul(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdDI(short input){ cpu.getRegR().Div(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdCHNGR(){ cpu.setRegR( memory.GetAt((short)(cpu.getRegIC()+1))); return EError.VALIDATION_SUCCESS;}
    private short cmdLR(short input){  cpu.setRegR( memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdSR(short input){ memory.GetAt(input).cell = cpu.getRegR().cell; return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdLO(String reg){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdCR(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdRL(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdRG(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdCZ(String reg){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdJC(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdJP(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdCA(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdPU(String reg){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdPO(String reg){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdRETRN(){ return EError.VALIDATION_SUCCESS;}//TODO implement
    private short cmdSY(short input){cpu.setRegSI(input); return EError.VALIDATION_SUCCESS;}
    private short cmdLP(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement

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