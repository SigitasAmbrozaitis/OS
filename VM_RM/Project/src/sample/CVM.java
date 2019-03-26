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
    private Vector<String> commands = new Vector<String>();
    private VCCPU cpu ;
    //private CCPU rcpu;
    private CPaging page;
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
    //TODO JURGIS implements this
    //TODO copy paste VM commands from RM

    public void Tick()
    {

    }

  /*  CVM(CRM crm){

       // System.out.println("mmry address: " + crm.getMemory().GetAt((short) 2));
        vm1 = new VMMemory(currentAddress);
        currentAddress +=100;
        VMcounter++;
        //write to CBlock
        commands.addAll( Arrays.asList(cmd2));
        commands.addAll( Arrays.asList(cmd5));
    }*/

    CVM(CPaging page)
    {
        this.page = page;
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


    /*commands that can be executed by VM */
    public short executeCommand(CCell command){

        CCommand cmd = new CCommand();
        short errorCode = ValidateCommand(command.cell, cmd);

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
                //errorCode = cmdSY((short)Integer.parseInt(cmd.param));
                break;
            case EVCommand.E_LP:
                errorCode = cmdLP((short)Integer.parseInt(cmd.param));
                break;
            default: errorCode = EError.COMMAND_VIOLATION;

        }

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
        if(nParam < 0 || nParam > 999) return EError.ACCESS_VIOLATION;

        command.cmd = cmd;
        command.param = param;
        command.bNumber = bNum;
        return EError.VALIDATION_SUCCESS;
    }

    private short cmdSP(short input){ cpu.setVregSP(input); return EError.VALIDATION_SUCCESS;}
    private short cmdAD(short input){ cpu.getVregR().Add(page.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdSB(short input){ cpu.getVregR().Sub(page.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdMP(short input){ cpu.getVregR().Mul(page.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdDI(short input){ cpu.getVregR().Div(page.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdCHNGR()
    {
        cpu.setVregR( page.GetAt((short)(cpu.getVregIC()+1)));
        cpu.setVregIC((short)(cpu.getVregIC()+1));
       // cpu.setRegTI((short)(cpu.getRegTI()-1)); //No TI reg in VM
        return EError.VALIDATION_SUCCESS;}
    private short cmdLR(short input){  cpu.setVregR( page.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdSR(short input){ page.GetAt(input).cell = cpu.getVregR().cell; return EError.VALIDATION_SUCCESS;}
    private short cmdLO(String reg) {  return EError.VALIDATION_SUCCESS;  }
    private short cmdCR(short input)
    {
        cpu.setVregC(cpu.getVregR().CmpString(page.GetAt(input)));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdRL(short input)
    {
        cpu.setVregC(cpu.getVregR().CmpNumber(page.GetAt(input)) < 0);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdRG(short input)
    {
        cpu.setVregC(cpu.getVregR().CmpNumber(page.GetAt(input)) > 0);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdCZ(String reg)
    {
        cpu.setVregC(cpu.ConvertRegToCCell(reg).CmpString(new CCell("00000")));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdJC(short input)
    {
        if(cpu.getVregC()) cpu.setVregIC((short)(input-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdJP(short input)
    {
        cpu.setVregIC((short)(input-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdCA(short input)
    {
        cmdPU("MOD");
        cmdPU("PTR");
        cmdPU("C");
        cmdPU("R");
        cmdPU("IC");
        cmdPU("SP");
        cmdPU("CT");
        cmdPU("TI");

        cpu.setVregIC((short)(input-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdPU(String reg)
    {
        page.GetAt(cpu.getVregSP()).cell = cpu.ConvertRegToCCell(reg).cell;
        cmdSP((short)(cpu.getVregSP()-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdPO(String reg)
    {
        short errorCode;
        errorCode = cmdSP((short)(cpu.getVregSP()+1));
        if (errorCode!=EError.VALIDATION_SUCCESS) return errorCode;
        errorCode = cpu.SetRegFromCCell(reg, page.GetAt(cpu.getVregSP()));
        return errorCode;
    }
    private short cmdRETRN()
    {
        cmdPO("TI");
        cmdPO("CT");
        cmdPO("SP");
        cmdPO("IC");
        cmdPO("R");
        cmdPO("C");
        cmdPO("PTR");
        cmdPO("MOD");
        return EError.VALIDATION_SUCCESS;
    }
  //  private short cmdSY(short input){cpu.setRegSI(input); return EError.VALIDATION_SUCCESS;}
    private short cmdLP(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement




}
