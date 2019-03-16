package sample.Memory;

import sample.VMBlock;

import java.util.Vector;

public class CBlock{
    public Vector<CCell> block;
    int blockNumber;

    public CBlock()
    {

        block = new Vector<CCell>(10);
        for(int i=0; i<10;++i)
        {
            block.add(new CCell());
        }
    }


    /**
     *Getters for populating memory table on GUI
     */
    public String getCell0(){ return block.get(0).cell; }
    public String getCell1(){ return block.get(1).cell; }
    public String getCell2(){ return block.get(2).cell; }
    public String getCell3(){ return block.get(3).cell; }
    public String getCell4(){ return block.get(4).cell; }
    public String getCell5(){ return block.get(5).cell; }
    public String getCell6(){ return block.get(6).cell; }
    public String getCell7(){ return block.get(7).cell; }
    public String getCell8(){ return block.get(8).cell; }
    public String getCell9(){ return block.get(9).cell; }

    public int getBlockNumber() {
        return blockNumber;
    }
}
