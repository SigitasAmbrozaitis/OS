package sample;

/*imports*/


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import sample.Enumerators.EError;
import sample.Enumerators.ERCommand;
import sample.Memory.CBlock;
import sample.Memory.CCell;
import sample.Memory.CMemory;
import sample.Memory.CPaging;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


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
//    public Vector<CVM> VMs;
    CVM VM;

    /*commands that can be executed by rm*/
    private String[] cmd2 = { "PI","TI","SP","BS","DB","ST","DT","SZ","IN","AD","SB","MP","DI","LR","SR","LO","CR","RL","RG","CZ","JC","JP","CA","PU","PO","SY","LP"};
    private String[] cmd3 = {"PTR"};
    private String[] cmd5 = {"XCHGN", "START", "IRETN", "CALLI", "CHNGM","CHNGR","RETRN"};
    private Vector<String> commands = new Vector<String>();

    CRM(){
        cpu = new CCPU();
        cd = new CCD();
        memory = new CMemory();
        CPaging.SetPageMemory(memory);
        memory.GetAt(cpu.getRegINT()).cell = "JP000";
//        VMs = new Vector<CVM>();


        commands.addAll( Arrays.asList(cmd2));
        commands.addAll( Arrays.asList(cmd3));
        commands.addAll( Arrays.asList(cmd5));
    }

    public void  ReadCommandInput(String input)
    {
        if(!cpu.getRegMod())
        {
            //split string to commands
            String[] commands = input.split(" ");
            Vector<String> commandsVec = new Vector<>(Arrays.asList(commands));
            cpu.setRegIC((short)0);
            cpu.setRegPI(EError.VALIDATION_SUCCESS);
            cpu.setRegSI((short)0);

            for(int i=0; i<commandsVec.size(); ++i)
            {
                memory.GetAt((short)(i)).cell = commandsVec.elementAt(i);
            }
        }else
        {
            VM.ReadCommandInput(input);
        }
    }

    public void Tick()
    {
        short errorCode = EError.VALIDATION_SUCCESS;

        //execute command in RM or VM

        if(!cpu.getRegMod())
        {
            //supervisor
            executeCommand(memory.GetAt(cpu.getRegIC()));
            cpu.setRegIC((short)(cpu.getRegIC()+1));
            cpu.setRegTI((short)(cpu.getRegTI()-1));
        }else
        {
            //user
//            for(int i=0; i<VMs.size(); ++i)
//                VMs.elementAt(i).Tick();
            VM.Tick();
        }

        //check for interupts
        if(cpu.getRegTI()==0 || (short)(cpu.getRegPI()+cpu.getRegSI())!=EError.VALIDATION_SUCCESS)
        {
                /**
                 * switch that handles PI interruptions (executes command that is at INT)
                 */
                //SA:
                //should be called CALLI, i suggest in adress INT place commands IRETN
                //Is the switch really needed since we dont have separate registers for separate interupts
                //and all interupts are handled by 1 command?
            /**
             *was thinking that there might be different variations of interruption handling in the future,
             *that's why I used switch
             *
             */
                switch (cpu.getRegPI()) {
                    case EError.VALIDATION_SUCCESS:
                        //everything fine
                        break;
                    case EError.ACCESS_VIOLATION:
                        errorCode = cmdCALLI();
                        System.out.println("ACCESS VIOLATION INTERRUPTION");
                        errorCode = cmdRETN();
                        break;
                    case EError.COMMAND_VIOLATION:
                        errorCode = cmdCALLI();
                        System.out.println("COMMAND VIOLATION INTERRUPTION");
                        errorCode = cmdRETN();
                        break;
                    case EError.MEMORY_VIOLATION:
                        errorCode = cmdCALLI();
                        System.out.println("MEMORY VIOLATION INTERRUPTION");
                        errorCode = cmdRETN();
                        break;
                    case EError.ASSIGMENT_VIOLATION:
                        errorCode = cmdCALLI();
                        System.out.println("ASSIGMENT VIOLATION INTERRUPTION");
                        errorCode = cmdRETN();
                        break;
                    default:
                        System.out.println("Unknown Error");
                        break;
                }

                //handles SI interupt if si != 0
                switch (cpu.getRegSI()) {
                    case 0:
                        break;
                    case 1:
                        //TODO implement "waiting for R" (just like in c++ cin waits for input)
//                        String regR = cpu.getRegR().cell;
//                        while (true){
//                            cpu.setRegIC();
//                            if(!regR.equals(cpu.getRegR().cell))
//                                break;
//                        }
                        cmdSZ((short)(cpu.getRegR().cell.charAt(0) + cpu.getRegR().cell.charAt(1)));//first two bytes indicate how many words will be recorded
                        cmdDB((short)(cpu.getRegR().cell.charAt(2) + cpu.getRegR().cell.charAt(3)));//next two bytes indicate the address in the VM's memory
                        //cmdBS((short)1);
                        cmdST((short)3);
                        cmdDT((short)1);
                        cmdXCHGN();
                        break;
                    case 2:
                        /**
                         * VM's request to send data to output device.
                         * The value in Registry R is treated as: the first two bytes indicate how many words will be entered,
                         * the next two bytes indicate the address in the virtual machine's memory.
                         *
                         * Master:Consult with GrandMaster Jurgis. He is responsible for data exchange.
                         */
                        //TODO implement "waiting for R" (just like in c++ cin waits for input)
                        cmdDT((short)3);
                        cmdST((short)1);
                        cmdSZ((short)(cpu.getRegR().cell.charAt(0)+cpu.getRegR().cell.charAt(1)));
                        cmdBS((short)(cpu.getRegR().cell.charAt(2)+cpu.getRegR().cell.charAt(3)));
                        cmdXCHGN();
                        break;
                    case 3:
                        /**
                         * the request of the VM's to extract additional memory according to register R,
                         * not more than 10 blocks.
                         *
                         * Master:Consult with GrandMaster Jurgis. He is responsible for data exchange.
                         */
                        //what command allocates VM's memory?

                        //TODO implement "waiting for R" (just like in c++ cin waits for input)
                        //TODO remove sigis hardcoded VM memory
                        short pageAdress = 10;
                        int regR = Integer.parseInt(cpu.getRegR().cell);
                        if(regR > 10){
                            System.out.println("More that 10 blocks. Change R value");
                        } else {

                            CBlock temp = memory.GetBlockAt((short)(pageAdress+regR));
                            temp.block.elementAt(0).cell = "00111";
                            temp.block.elementAt(1).cell = "00220";
                            temp.block.elementAt(2).cell = "00330";
                            temp.block.elementAt(3).cell = "00440";
                            temp.block.elementAt(4).cell = "00550";
                            temp.block.elementAt(5).cell = "00660";
                            temp.block.elementAt(6).cell = "00770";
                            temp.block.elementAt(7).cell = "00880";
                            temp.block.elementAt(8).cell = "00990";
                            temp.block.elementAt(9).cell = "00999";
                        }
                        break;
                    case 4:
                        cmdCHNGM();
                        cpu.setRegSI((short)0);
                        loadRMState();
                        break;
                    default:
                        System.out.println("Unknown Error");
                        break;
                }

                //nterupt if ti == 0
                if(cpu.getRegTI() == 0)
                {
                    if(cpu.getRegMod())
                    {
                        loadRMState();
                        //cmdCHNGM();
                    }
                    //for now just reset ti to 10
                    cpu.setRegTI((short)10);
                    //errorCode = cmdCALLI();
                    //TODO in future calls OS interrupts processor
                }
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
                  //  System.out.println("SZ: " + cd.getRegSZ());
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_TI:
                if(cmd.bNumber)
                {
                    errorCode = cmdTI((short)Integer.parseInt(cmd.param));
                }else errorCode = EError.COMMAND_VIOLATION;
                break;
            case ERCommand.E_XCHNG:
                //System.out.println("AAAAAAAAAAAAAAasilas");
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
    private short loadRMState()
    {
        cmdPO("_CT");
        cmdPO("_SP");
        cmdPO("_IC");
        cmdPO("__R");
        cmdPO("__C");
        cmdPO("PTR");
        cmdPO("MOD");
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

        //TODO remove:HardCoded PTR setting
        short pageAdress = 10;
        cpu.setRegPTR(pageAdress);

        //TODO remove: HardCoded memory setting
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

        //TODO no need to give TI
//        FillExampleCommands();
        VM = new CVM(cpu);

        //change mod
        cmdCHNGM();

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

    private short cmdXCHGN(){

      //  int inputNumber=0, outputNumber=0; //inputNumber - block or object number to be copied
                                           //outputNumber - block or object number to be pasted into
      //  int objectOrBlockInput = -1, objectOrBlockOutput=-1; // defines locations
                                                             // 0 - block, 1 - object
        int inputMode=0; //1 - copy from VM
                         //2 - copy from hdd
                         //3 - copy from input field
        int blockToBeCopied = -1;
        int wordsNumber = -1; //how many words to copy
        ArrayList<String> hdd = new ArrayList<String>();
        hdd = populateHDD(hdd,"hdd.txt");

        if( cd.getRegSZ() == 0){ //by default 0
            //interrupt
            System.out.println("Please set register SZ to the number of words you wish to copy");
            //return EError.COMMAND_VIOLATION;
        }
        else {
            wordsNumber = cd.getRegSZ();
        }
        //Validate input
        if(cd.getRegST() == 0){
            System.out.println("Missing input data");
            //return EError.COMMAND_VIOLATION;
        }
        if(cd.getRegST() != 0 && cd.getRegSB()!=0){
            if(cd.getRegST() == 1){
                inputMode = cd.getRegST();
                blockToBeCopied = cd.getRegSB();
            }
            if(cd.getRegST() == 2){
                inputMode = cd.getRegST();
                blockToBeCopied = cd.getRegSB();
            }
            if(cd.getRegST() == 3){
                inputMode = cd.getRegST();
                //from input field, no block number needed
            }
        }
        else{
            System.out.println("Missing input data");
            //return EError.COMMAND_VIOLATION;
        }
        System.out.println("Input mode: " + inputMode +" num of words: "+ wordsNumber +" block: " + blockToBeCopied);


/*
        else {
            if (cd.getRegST() != 0){
                objectOrBlockInput = 1;
                inputNumber = cd.getRegST();
            }
            else{
                objectOrBlockInput = 0;
                inputNumber = cd.getRegSB();
            }
            if (cd.getRegDB() != 0){
                objectOrBlockOutput = 0;
                outputNumber = cd.getRegDB();
            }
            else{
                objectOrBlockOutput = 1;
                outputNumber = cd.getRegDT();
            }
        }
        if(objectOrBlockInput == 0) { //copying from block in memory

            if(wordsNumber > memory.GetBlockAt((short) inputNumber).block.size()){
                System.out.println("Duheli, perdaug prasai, gausi interupta");
                //interrupt and return
            }
            if(objectOrBlockOutput == 0){
                System.out.println("Kopinam is takelio nr: " + inputNumber + " i takeli nr: " + outputNumber);
                for(int i=0; i < wordsNumber; i++){
                    memory.GetBlockAt((short)outputNumber).block.get(i).cell = memory.GetBlockAt((short) inputNumber).block.get(i).cell;
                }
            }
            if(objectOrBlockOutput == 1){
                String words[] = new String[wordsNumber];
                for(int i=0; i < wordsNumber; i++){
                    words[i]= memory.GetBlockAt((short) inputNumber).block.get(i).cell;
                }
                outputCD(words, wordsNumber);

            }
        }
        else if(objectOrBlockInput == 1){
            String words[] = new String[wordsNumber];
            if(inputNumber == 1){
                //PALAUKT KOL SIGIS PADARYS PAGING NORMALIAI
                // GAUT IS SIGIO INPUT
            }
            else if(inputNumber == 2){
                //read how many needed words
                int count = wordsNumber;
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader("hdd.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    String line;
                    int i=0;
                    while ((line = br.readLine()) != null && count > 0) {
                        // process the line
                        words[i] = line;
                        i++;
                        count--;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Arrays.toString(words));
            }
            else if(inputNumber == 3){
                //Gauti is Pofkes input lauko reiksmes
                if(Controller.getChannelDeviceInput().equals(" ")){
                    System.out.println("INTERRUPT PER GALVA BLIA");
                }
                else{
                    words = Controller.getChannelDeviceInput().split(" ");
                }

            }
            if(objectOrBlockOutput == 0){
                for(int i=0; i < wordsNumber; i++){
                    memory.GetBlockAt((short)outputNumber).block.get(i).cell = words[i];
                }
            }
            else if(objectOrBlockOutput == 1){
                outputCD(words, outputNumber);
            }
        }
*/
       // System.out.println("Valio inputNumber: " + +inputNumber + " objectOrBlockInput: " + objectOrBlockInput +
         //       " objectOrBlockOutput: " + objectOrBlockOutput + " wordsNumber: " + wordsNumber);
        return EError.VALIDATION_SUCCESS;
    }
    //TODO implement, maybe it should have different input in GUI?
private void outputCD(String[] data, int outputNumber){

        switch(outputNumber){
            case 1:
                //Laukiam Sigio, vartotojo atmintis, VM blablabla
                //DUOT SIGIUI REIKSMES
                break;
            case 2:
                String output = "";
                for(int i=0; i < data.length; i++){
                    output += data[i] + "\r\n"; //add each word in new line
                }
                writeToHdd("hdd.txt", output);
                break;
            case 3:
                //String[] data idet i Pofkes langa
                //for testing purposes
                //Jurgi, replace "hello" with what you want to output. Should be a String
                String result = Arrays.toString(data);
                getCCD().updateCdOutputOuput(result);
                break;
            default:
                System.out.println("Error");
                break;
        }
}
private void writeToHdd(String filename, String output){
    try {
        // Open given file in append mode.
        BufferedWriter out = new BufferedWriter(
                new FileWriter(filename, true));
        System.out.println("irasinesiu: " + output);
        out.write(output);
        out.close();
    }
    catch (IOException e) {
        System.out.println("exception occoured" + e);
    }
}
private ArrayList<String> populateHDD(ArrayList<String> alist, String filename){
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader(filename));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    try {
        String line;
        int i=0;
        while ((line = br.readLine()) != null) {
            // process the line
            alist.add(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return alist;
}


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
    private short FillExampleCommands()
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
