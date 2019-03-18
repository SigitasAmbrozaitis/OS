package sample.Memory;

import java.util.Vector;

public class CMemory {

    public Vector<CBlock> memory;
    public CMemory()
    {
        memory = new Vector<CBlock>(100);
        for(int i=0; i<100; ++i)
        {
            memory.add(new CBlock());
            memory.get(i).blockNumber = i;
        }
    }


    public CCell GetAt(short index)
    {
        return memory.elementAt(index/10).block.elementAt(index%10);
    }
    public CBlock GetBlockAt(short index) {return memory.elementAt(index);}
}
