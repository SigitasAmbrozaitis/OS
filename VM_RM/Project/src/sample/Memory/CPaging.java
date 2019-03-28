package sample.Memory;

public class CPaging {

    CMemory memory;
    short adress;
    CBlock pagingTable;

    public CPaging( CMemory mem, short adress )
    {
        this.memory = mem;
        pagingTable = memory.GetBlockAt(adress);
    }

    public CCell GetAt(short index)
    {
        short a = Short.parseShort(pagingTable.block.elementAt(index/10).cell);
        short b = (short)(index%10);
        CCell cell = memory.GetAt((short)(a*10+b));
        return cell;
        //return memory.GetAt((short)(Short.parseShort(pagingTable.block.elementAt(index/10).cell)*10+ index%10));
    }

    public CCell GetAtCopy(short index)
    {
        return new CCell(GetAt(index).cell);
    }

}
