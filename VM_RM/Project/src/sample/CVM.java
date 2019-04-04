package sample;

import sample.Enumerators.EError;
import sample.Enumerators.ERCommand;
import sample.Memory.CBlock;
import sample.Memory.CCell;
import sample.Memory.CMemory;
import sample.Memory.CPaging;
import sample.Enumerators.EVCommand;

import java.util.Arrays;
import java.util.Vector;

public class CVM {

    private String[] cmd2 = { "AD","SB","MP","DI","LR","SR","LO","CR","RL","RG","CZ","JC","JP","CA","PU","PO","SY","LP"};
    private String[] cmd5 = { "CHNGR","RETRN"};
    private CCPU cpu ;

    short memSize = 100;
    private class CCommand
    {
        public String cmd;
        public String param;
        public boolean bNumber;
    }

    //Available memory for VMS 200-800 ?
  //  static int currentAddress = 200;
  //  static int VMcounter=0;
    //VMMemory vm1;
    public void  ReadCommandInput(String input)
    {
        //split string to commands
        String[] commands = input.split(" ");
        Vector<String> commandsVec = new Vector<>(Arrays.asList(commands));
        cpu.setRegIC((short)0);
        //keep in mind that three might be unexecuted commands
        for(int i=0; i<commandsVec.size(); ++i)
        {
            CPaging.GetAt((short)(i)).cell = commandsVec.elementAt(i);
        }
    }

    public void Tick()
    {
        if(cpu.getRegTI()>0)
        {
            executeCommand(CPaging.GetAt(cpu.getRegIC()));
            cpu.setRegIC((short)(cpu.getRegIC()+1));
            cpu.setRegTI((short)(cpu.getRegTI()-1));
        }else
        {
            cpu.setRegSI((short)4);
        }
    }

    CVM(CPaging page)
    {
        CPaging.SetPageBlock(cpu.getRegPTR());
        Vector<String> test = new Vector<String>();
        for(short i=0; i<100; ++i)
        {
            test.add(page.GetAt(i).cell);
            System.out.println(i+":"+page.GetAt(i).cell);
        }
        int a = 0;
        // page.GetAt((short)2).cell = "69";
        System.out.println("cell2: " + page.GetAt((short)2).cell);

    }

    CVM(CCPU cpu)
    {
        this.cpu = cpu;
        CPaging.SetPageBlock(cpu.getRegPTR());
        Vector<String> test = new Vector<String>();
        cpu.setRegTI((short)11);
    }


    /*commands that can be executed by VM */
    public short executeCommand(CCell command){

        CCommand cmd = new CCommand();
        short errorCode = ValidateCommand(command.cell, cmd);
        if(errorCode!=EError.VALIDATION_SUCCESS)
        {
            cpu.setRegPI(errorCode);
            return errorCode;
        }

        switch(cmd.cmd)
        {
            case EVCommand.E_AD:
                if(cmd.bNumber)
                {
                    errorCode = cmdAD((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_CA:
                if(cmd.bNumber)
                {
                    errorCode = cmdCA((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_CHNGR:
                errorCode = cmdCHNGR();
                break;
            case EVCommand.E_CR:
                if(cmd.bNumber)
                {
                    errorCode = cmdCR((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_CZ:
                if(!cmd.bNumber)
                {
                    errorCode = cmdCZ(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_DI:
                if(cmd.bNumber)
                {
                    errorCode = cmdDI((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_JC:
                if(cmd.bNumber)
                {
                    errorCode = cmdJC((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_JP:
                if(cmd.bNumber)
                {
                    errorCode = cmdJP((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_LO:
                if(!cmd.bNumber)
                {
                    errorCode = cmdLO(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_LR:
                if(cmd.bNumber)
                {
                    errorCode = cmdLR((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_MP:
                if(cmd.bNumber)
                {
                    errorCode = cmdMP((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_PO:
                if(!cmd.bNumber)
                {
                    errorCode = cmdPO(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_PU:
                if(!cmd.bNumber)
                {
                    errorCode = cmdPU(cmd.param);
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_RG:
                if(cmd.bNumber)
                {
                    errorCode = cmdRG((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_RL:
                if(cmd.bNumber)
                {
                    errorCode = cmdRL((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_SB:
                if(cmd.bNumber)
                {
                    errorCode = cmdSB((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_SR:
                if(cmd.bNumber)
                {
                    errorCode = cmdSR((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case EVCommand.E_RETRN:
                errorCode = cmdRETRN();
                break;
            case EVCommand.E_SY:
                errorCode = cmdSY((short)Integer.parseInt(cmd.param));
                break;
            case EVCommand.E_LP:
                errorCode = cmdLP((short)Integer.parseInt(cmd.param));
                break;
            default: errorCode = EError.COMMAND_VIOLATION;

        }
        cpu.setRegPI(errorCode);
        return errorCode;
    }

    short ValidateCommand(String strCommand, CCommand command) {
        if(strCommand.length() != 5) return EError.COMMAND_VIOLATION;

        //Get command name
        String cmd = "";
        if(CUtils.StringIsInArray(strCommand, cmd5, 5))
            cmd = strCommand;
        else if(CUtils.StringIsInArray(strCommand,cmd2,2))
            cmd = strCommand.substring(0,2);

        if(cmd == "") return EError.COMMAND_VIOLATION;



        //Get command param
        String param = strCommand.substring(cmd.length());
        //check if param is register
        boolean bReg = CUtils.StringIsInArray(param, cpu.registers, 5 - cmd.length());
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

        if(!bReg && !bNum && cmd.length()!=5) return EError.COMMAND_VIOLATION;
        if(nParam < 0 || nParam >= memSize) return EError.ACCESS_VIOLATION;

        command.cmd = cmd;
        command.param = param;
        command.bNumber = bNum;
        return EError.VALIDATION_SUCCESS;
    }

    private short cmdSP(short input){ cpu.setRegSP(input); return EError.VALIDATION_SUCCESS;}
    private short cmdAD(short input){ cpu.getRegR().Add(CPaging.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdSB(short input){ cpu.getRegR().Sub(CPaging.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdMP(short input){ cpu.getRegR().Mul(CPaging.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdDI(short input){ cpu.getRegR().Div(CPaging.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdCHNGR()
    {
        cpu.setRegR( CPaging.GetAt((short)(cpu.getRegIC()+1)));
        cpu.setRegIC((short)(cpu.getRegIC()+1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdLR(short input){  cpu.setRegR( CPaging.GetAtCopy(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdSR(short input){ CPaging.GetAt(input).cell = cpu.getRegR().cell; return EError.VALIDATION_SUCCESS;}
    private short cmdLO(String reg) {  cpu.setRegR(cpu.ConvertRegToCCell(reg));return EError.VALIDATION_SUCCESS;  }
    private short cmdCR(short input)
    {
        cpu.setRegC(cpu.getRegR().CmpString(CPaging.GetAt(input)));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdRL(short input)
    {
        cpu.setRegC(cpu.getRegR().CmpNumber(CPaging.GetAt(input)) < 0);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdRG(short input)
    {
        cpu.setRegC(cpu.getRegR().CmpNumber(CPaging.GetAt(input)) > 0);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdCZ(String reg)
    {
        cpu.setRegC(cpu.ConvertRegToCCell(reg).CmpString(new CCell("00000")));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdJC(short input)
    {
        if(cpu.getRegC()) cpu.setRegIC((short)(input-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdJP(short input)
    {
        cpu.setRegIC((short)(input-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdCA(short input)
    {
        //cmdPU("MOD");
        //cmdPU("PTR");
        cmdPU("C");
        cmdPU("R");
        cmdPU("IC");
        cmdPU("SP");
        cmdPU("CT");
        //cmdPU("TI");

        cpu.setRegIC((short)(input-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdPU(String reg)
    {
        CPaging.GetAt(cpu.getRegSP()).cell = cpu.ConvertRegToCCell(reg).cell;
        cmdSP((short)(cpu.getRegSP()-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdPO(String reg)
    {
        short errorCode;
        errorCode = cmdSP((short)(cpu.getRegSP()+1));
        if (errorCode!=EError.VALIDATION_SUCCESS) return errorCode;
        errorCode = cpu.SetRegFromCCell(reg, CPaging.GetAt(cpu.getRegSP()));
        return errorCode;
    }
    private short cmdRETRN()
    {
        //cmdPO("TI");
        cmdPO("CT");
        cmdPO("SP");
        cmdPO("IC");
        cmdPO("R");
        cmdPO("C");
        //cmdPO("PTR");
        //cmdPO("MOD");
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdSY(short input){cpu.setRegSI(input); return EError.VALIDATION_SUCCESS;}
    private short cmdLP(short input){
        if(input < 0 || input > memSize) return EError.COMMAND_VIOLATION;
        if(cpu.getRegCT() != 0) {
            cpu.setRegCT((short) (cpu.getRegIC() - 1));
        }else{
            cmdJP(input);
        }
        return EError.VALIDATION_SUCCESS;
    }

/**
 * probably will delete later
 */
//    void updateRegistersVMCPU(){
//        Controller.cvm_C_output = ""+cpu.getRegC();
//        Controller.cvm_R_output = ""+cpu.getRegR().cell;
//        Controller.cvm_IC_output = ""+cpu.getRegIC();
//        Controller.cvm_CT_output = ""+cpu.getRegCT();
//        Controller.cvm_SP_output = ""+cpu.getRegSP();
//    }

}
