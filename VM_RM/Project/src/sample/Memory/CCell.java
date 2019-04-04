package sample.Memory;
import sample.Enumerators.EError;

public class CCell {
    public String cell;
    //boolean free;
    private static int num = 0;
    public CCell()
    {
        cell = "00000";

        cell = String.valueOf(num++); //TODO remove this

        if((cell.equals("999")))
            num = 0;

        //free = true;
    }

    public CCell(String cell)
    {
        this.cell = cell;
    }

    //These will be called by RM/VM probably regC should be of type CCell too
    public short Add(CCell value)
    {
        try
        {
            this.cell = Integer.toString(Integer.parseInt(this.cell)  + Integer.parseInt(value.cell));
        }catch(NumberFormatException e)
        {
            return EError.COMMAND_VIOLATION;
        }
        return EError.VALIDATION_SUCCESS;
    }

    public short Sub(CCell value)
    {
        try
        {
            this.cell = Integer.toString(Integer.parseInt(this.cell)  - Integer.parseInt(value.cell));
        }catch(NumberFormatException e)
        {
            return EError.COMMAND_VIOLATION;
        }
        return EError.VALIDATION_SUCCESS;
    }

    public short Mul(CCell value)
    {
        try
        {
            this.cell = Integer.toString(Integer.parseInt(this.cell)  * Integer.parseInt(value.cell));
        }catch(NumberFormatException e)
        {
            return EError.COMMAND_VIOLATION;
        }
        return EError.VALIDATION_SUCCESS;
    }

    public short Div(CCell value)
    {
        try
        {
            this.cell = Integer.toString(Integer.parseInt(this.cell)  / Integer.parseInt(value.cell));
        }catch(NumberFormatException e)
        {
            return EError.COMMAND_VIOLATION;
        }
        return EError.VALIDATION_SUCCESS;
    }

    public boolean CmpString(CCell value)
    {
        return this.cell.compareTo(value.cell) == 0;
    }

    //return 1 if this is greater.
    //return -1 if this is less
    //return 0 if equal
    //return -2 on fail
    public short CmpNumber(CCell value)
    {
        short ret = 0;
        try
        {
            ret = (short)(Short.parseShort(this.cell) > Short.parseShort(value.cell) ? 1:-1);
            ret = (short)(Short.parseShort(this.cell) == Short.parseShort(value.cell) ? 0:ret);
        }catch(Exception e)
        {
            return -2;
        }
        return ret;
    }

    public CCell Copy()
    {
        return new CCell(this.cell);
    }




}
