package sample;

/*imports*/


import java.util.Arrays;
import java.util.Vector;

import sample.Enumerators.EError;
import sample.Enumerators.ERCommand;

import sample.Memory.CBlock;
import sample.Memory.CMemory;
import sample.Memory.CCell;
import sample.Memory.CPaging;


/*Real Machine implementation*/
public class CRM
{
    private class CCommand
    {
        public String cmd;
        public String param;
        public boolean bNumber;
    }

    private CCPU cpu;
    private CCD cd;
    private CMemory memory;
    private CPaging page;

    //getters for GUI
    public CCPU getCpu()
    {
        return cpu;
    }
    public CCD getCCD(){return cd;}
    public CMemory getMemory()
    {
        return memory;
    }
    public Vector<CVM> VMs;

    /*commands that can be executed by rm*/
    private String[] cmd2 = { "PI","TI","SP","BS","DB","ST","DT","SZ","IN","AD","SB","MP","DI","LR","SR","LO","CR","RL","RG","CZ","JC","JP","CA","PU","PO","SY","LP"};
    private String[] cmd3 = {"PTR"};
    private String[] cmd5 = {"XCHGN", "START", "IRETN", "CALLI", "CHNGM","CHNGR","RETRN"};
    private Vector<String> commands = new Vector<String>();

    CRM(){
        cpu = new CCPU();
        cd = new CCD();
        memory = new CMemory();

        VMs = new Vector<CVM>();

        commands.addAll( Arrays.asList(cmd2));
        commands.addAll( Arrays.asList(cmd3));
        commands.addAll( Arrays.asList(cmd5));
    }

    public void  ReadCommandInput(String input)
    {
        //split string to commands
        String[] commands = input.split(" ");
        Vector<String> commandsVec = new Vector<>(Arrays.asList(commands));

        short preIndex = (short)(cpu.getRegTI()>0?cpu.getRegIC():0);
        short startIndex = (short)(cpu.getRegTI()>0?cpu.getRegIC()+cpu.getRegTI():0);
        cpu.setRegIC(preIndex);
        cpu.setRegTI((short)(commandsVec.size() + cpu.getRegTI()));

        //keep in mind that three might be unexecuted commands
        for(int i=0; i<commandsVec.size(); ++i)
        {
            memory.GetAt((short)( startIndex  + i)).cell = commandsVec.elementAt(i);
        }
    }

    public void Tick()
    {
        short errorCode = EError.VALIDATION_SUCCESS;
        if(cpu.getRegMod())
        {
            if(cpu.getRegTI()>0 && cpu.getRegPI()==EError.VALIDATION_SUCCESS)
            {

                    errorCode = executeCommand(memory.GetAt(cpu.getRegIC()));
                    cpu.setRegIC((short)(cpu.getRegIC()+1));
                    cpu.setRegTI((short)(cpu.getRegTI()-1));
            } else {
                    /**
                     * switch that handles PI interruptions (executes command that is at INT)
                     */
                    switch (cpu.getRegPI()) {
                        case EError.ACCESS_VIOLATION:
                            errorCode = executeCommand(memory.GetAt(cpu.getRegINT()));
                            break;
                        case EError.COMMAND_VIOLATION:
                            errorCode = executeCommand(memory.GetAt(cpu.getRegINT()));
                            break;
                        case EError.MEMORY_VIOLATION:
                            errorCode = executeCommand(memory.GetAt(cpu.getRegINT()));
                            break;
                        case EError.ASSIGMENT_VIOLATION:
                            errorCode = executeCommand(memory.GetAt(cpu.getRegINT()));
                            break;
                    }
                }
            }
        else
        {
            for(int i=0; i<VMs.size(); ++i)
                VMs.elementAt(i).Tick();

        }
    }

    public void Run()
    {
        while(cpu.getRegTI()>0)
        {
            Tick();
        }
    }

    /*commands that can be executed by RM and VM */
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
            cpu.setRegPI(errorCode);
        }
        return errorCode;
    }

    short ValidateCommand(String strCommand, CCommand command)
    {
        //TODO check if validation is correct, had no time for that :D
        /**Everything seems OK*/
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
    private short cmdCALLI()
    {
        cmdPU("MOD");
        cmdPU("PTR");
        cmdPU("__C");
        cmdPU("__R");
        cmdPU("_IC");
        cmdPU("_SP");
        cmdPU("_CT");
        cmdPU("_TI");

        cpu.setRegIC(cpu.getRegINT());
        cpu.setRegTI((short)1);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdRETN()
    {
        cmdPO("_TI");
        cmdPO("_CT");
        cmdPO("_SP");
        cmdPO("_IC");
        cmdPO("__R");
        cmdPO("__C");
        cmdPO("PTR");
        cmdPO("MOD");
        return EError.VALIDATION_SUCCESS;
    }
    private short saveRMState(){
        cmdPU("MOD");
        cmdPU("PTR");
        cmdPU("__C");
        cmdPU("__R");
        cmdPU("_IC");
        cmdPU("_SP");
        cmdPU("_CT");


        return EError.VALIDATION_SUCCESS;
    }
    private short cmdSTART(){
        //save real machine state
        short errorCode = saveRMState();
        if(errorCode!=EError.VALIDATION_SUCCESS)
        {
            cpu.setRegPI(errorCode);
            return errorCode;
        }

        short pageAdress = 10;
        CBlock temp = memory.GetBlockAt(pageAdress);
        temp.block.elementAt(0).cell = "00011";
        temp.block.elementAt(1).cell = "00020";
        temp.block.elementAt(2).cell = "00030";
        temp.block.elementAt(3).cell = "00040";
        temp.block.elementAt(4).cell = "00050";
        temp.block.elementAt(5).cell = "00060";
        temp.block.elementAt(6).cell = "00070";
        temp.block.elementAt(7).cell = "00080";
        temp.block.elementAt(8).cell = "00090";
        temp.block.elementAt(9).cell = "00099";



        CPaging page = new CPaging(memory, pageAdress);

        short ic = FillExampleCommands(page);
        VMs.add(new CVM(page, ic, cpu));

        return EError.VALIDATION_SUCCESS;
    }//TODO implement, starts virtual machine

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

    private short cmdXCHGN(){ return EError.VALIDATION_SUCCESS;}//TODO implement, maybe it should have different input in GUI?

    private short cmdAD(short input){ cpu.getRegR().Add(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdSB(short input){ cpu.getRegR().Sub(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdMP(short input){ cpu.getRegR().Mul(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdDI(short input){ cpu.getRegR().Div(memory.GetAt(input)); return EError.VALIDATION_SUCCESS;}
    private short cmdCHNGR()
    {
        cpu.setRegR( memory.GetAt((short)(cpu.getRegIC()+1)).Copy());
        cpu.setRegIC((short)(cpu.getRegIC()+1));
        //cpu.setRegTI((short)(cpu.getRegTI()-1));
        return EError.VALIDATION_SUCCESS;}
    private short cmdLR(short input){  cpu.setRegR( memory.GetAt(input).Copy()); return EError.VALIDATION_SUCCESS;}
    private short cmdSR(short input){ memory.GetAt(input).cell = cpu.getRegR().Copy().cell; return EError.VALIDATION_SUCCESS;}
    private short cmdLO(String reg) {  cpu.setRegR(cpu.ConvertRegToCCell(reg)); return EError.VALIDATION_SUCCESS;  }
    private short cmdCR(short input)
    {
        cpu.setRegC(cpu.getRegR().CmpString(memory.GetAt(input)));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdRL(short input)
    {
        cpu.setRegC(cpu.getRegR().CmpNumber(memory.GetAt(input)) < 0);
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdRG(short input)
    {
        cpu.setRegC(cpu.getRegR().CmpNumber(memory.GetAt(input)) > 0);
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
        cmdPU("MOD");
        cmdPU("PTR");
        cmdPU("__C");
        cmdPU("__R");
        cmdPU("_IC");
        cmdPU("_SP");
        cmdPU("_CT");
        cmdPU("_TI");

        cpu.setRegIC((short)(input-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdPU(String reg)
    {
        memory.GetAt(cpu.getRegSP()).cell = cpu.ConvertRegToCCell(reg).cell;
        cmdSP((short)(cpu.getRegSP()-1));
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdPO(String reg)
    {
        short errorCode;
        errorCode = cmdSP((short)(cpu.getRegSP()+1));
        if (errorCode!=EError.VALIDATION_SUCCESS) return errorCode;
        errorCode = cpu.SetRegFromCCell(reg, memory.GetAt(cpu.getRegSP()).Copy());
        return errorCode;
    }
    private short cmdRETRN()
    {
        cmdPO("_TI");
        cmdPO("_CT");
        cmdPO("_SP");
        cmdPO("_IC");
        cmdPO("__R");
        cmdPO("__C");
        cmdPO("PTR");
        cmdPO("MOD");
        return EError.VALIDATION_SUCCESS;
    }
    private short cmdSY(short input){cpu.setRegSI(input); return EError.VALIDATION_SUCCESS;}
    private short cmdLP(short input){ return EError.VALIDATION_SUCCESS;}//TODO implement



    //TODO delete when not needed anymore
    private short FillExampleCommands(CPaging page)
    {
        short commandIndex = 0;
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";
        page.GetAt(commandIndex++).cell = "AD090";

        return commandIndex;//commands added
    }
}
