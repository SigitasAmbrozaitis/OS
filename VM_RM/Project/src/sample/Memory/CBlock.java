package sample.Memory;

import java.util.Vector;

public class CBlock {
    public Vector<CCell> block;

    public CBlock()
    {

        block = new Vector<CCell>(10);
        for(int i=0; i<10;++i)
        {
            block.add(new CCell());
        }
    }
}
