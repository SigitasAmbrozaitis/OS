package sample.Memory;
import sample.Enumerators.EError;

public class CCell {
    public String cell;
    //boolean free;

    public CCell()
    {
        cell = "";
        //free = true;
    }

    //These will be called by RM/VM probably regC should be of type CCell too
    public short Add(CCell value)
    {
        //TODO implement
        return EError.VALIDATION_SUCCESS;
    }

    public short Sub(CCell value)
    {
        //TODO implement
        return EError.VALIDATION_SUCCESS;
    }

    public short Mul(CCell value)
    {
        //TODO implement
        return EError.VALIDATION_SUCCESS;
    }

    public short Div(CCell value)
    {
        //TODO implement
        return EError.VALIDATION_SUCCESS;
    }


}
