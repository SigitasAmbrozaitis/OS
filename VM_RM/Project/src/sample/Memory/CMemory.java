package sample.Memory;

import java.util.Vector;

public class CMemory {

    public Vector<CBlock> memory;
    public CMemory()
    {
        memory = new Vector<CBlock>(100);
    }

    public CCell GetAt(short index)
    {
        return memory.elementAt(index/100).block.elementAt(index%100);
    }
}
