package sample.Memory;

import java.util.Vector;

public class CBlock {
    public Vector<CCell> block;

    public CBlock()
    {
        block = new Vector<CCell>(10);
    }
}
