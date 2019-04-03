package sample.Memory;

public class CPaging {

    static CMemory memory;

    static CBlock pagingTable;




    public static void SetPageMemory(CMemory mem)
    {
        memory = mem;

    }

    public static void SetPageBlock(short blockIndex)
    {
        pagingTable = memory.GetBlockAt(blockIndex);
    }

//    public static void SetPage(CMemory mem, short blockIndex)
//    {
//        SetPageMemory(mem);
//        SetPageBlock(blockIndex);
//    }


    public static CCell GetAt(short index)
    {
        short a = Short.parseShort(pagingTable.block.elementAt(index/10).cell);
        short b = (short)(index%10);
        CCell cell = memory.GetAt((short)(a*10+b));
        return cell;
        //return memory.GetAt((short)(Short.parseShort(pagingTable.block.elementAt(index/10).cell)*10+ index%10));
    }

    public static CBlock GetBlockAt(short index)
    {
        return memory.GetBlockAt(Short.parseShort(pagingTable.block.elementAt(index).cell));
    }

    public static CCell GetAtCopy(short index)
    {
        return new CCell(GetAt(index).cell);
    }

}
