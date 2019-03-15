package sample.Memory;
import sample.Enumerators.EError;

public class CCell {
    public String cell;
    //boolean free;

    public CCell()
    {
        cell = "00000";
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

    public short Div(CCell value) //TODO test
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

       public boolean Cmp(CCell value)
    {
        return this.cell.compareTo(value.cell) == 0;
    }


}
