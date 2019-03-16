package sample;

import sample.Memory.CBlock;
import sample.Memory.CMemory;

import java.util.Vector;

public class VMMemory {
    public Vector<VMBlock> vmMemory;
    VMMemory(int startingAddress){
        vmMemory = new Vector<VMBlock>(10);
        for(int i=0; i < 10; i++){
            vmMemory.add(new VMBlock(Integer.toString(i),Integer.toString(startingAddress + i*10)));
        }
    }


}
