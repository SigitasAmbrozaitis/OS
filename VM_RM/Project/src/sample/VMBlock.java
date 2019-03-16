package sample;

import sample.Memory.CBlock;

public class VMBlock {

    String virtualAddress;
    String realAddress;
    CBlock cBlock;

    VMBlock(String vAddress, String rAddress){
        this.virtualAddress = vAddress;
        this.realAddress = rAddress;
        cBlock = new CBlock();

    }

}
