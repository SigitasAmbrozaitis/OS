package sample;

import sample.Memory.CBlock;

public class CVM {

    //Available memory for VMS 200-800 ?
    static int currentAddress = 200;
    static int VMcounter=0;
    VMMemory vm1;
    //TODO JURGIS implements this
    //TODO copy paste VM commands from RM

    public void Tick()
    {

    }

    CVM(CRM crm){

       // System.out.println("mmry address: " + crm.getMemory().GetAt((short) 2));
        vm1 = new VMMemory(currentAddress);
        currentAddress +=100;
        VMcounter++;
        //write to CBlock



    }

}
