package sample;

import sample.Enumerators.EError;
import sample.Enumerators.ERRegister;
import sample.Memory.CCell;

public class VCCPU {

    private CCell vregR;
    private short vregSP;
    private short vregIC;
    private boolean vregC;
    private short vregCT;

    VCCPU()
    {
        vregR = new CCell();
        vregSP = 99;
        vregIC = 0;
        vregC = false;
        vregCT = 0;
    }


    public String[] registers = {"__R", "__C", "_IC", "_SP", "_CT"};

    public CCell getVregR() {
        return vregR;
    }

    public void setVregR(CCell vregR) {
        this.vregR = vregR;
    }
    public short getVregSP() {
        return vregSP;
    }

    public void setVregSP(short vregSP) {
        this.vregSP = vregSP;
    }

    public short getVregIC() {
        return vregIC;
    }

    public void setVregIC(short vregIC) {
        this.vregIC = vregIC;
    }

    public boolean getVregC() {
        return vregC;
    }

    public void setVregC(boolean vregC) {
        this.vregC = vregC;
    }


    public short getVregCT() {
        return vregCT;
    }

    public void setVregCT(short vregCT) {
        this.vregCT = vregCT;
    }

    public CCell ConvertRegToCCell(String reg)
    {
        CCell returnValue = new CCell();
        switch(reg)
        {
            case ERRegister.E_C:
                returnValue.cell = Boolean.toString(this.vregC);
                break;
            case ERRegister.E_CT:
                returnValue.cell = Short.toString(this.vregCT);
                break;
            case ERRegister.E_IC:
                returnValue.cell = Short.toString(this.vregIC);
                break;
            case ERRegister.E_R:
                returnValue = this.vregR;
                break;
            case ERRegister.E_SP:
                returnValue.cell = Short.toString(this.vregSP);
                break;
        }
        return returnValue;
    }
    public short SetRegFromCCell(String reg, CCell cell)
    {
        short errorCode = EError.VALIDATION_SUCCESS;
        short tempErrorCode = EError.VALIDATION_SUCCESS;
        short val = 0;
        try
        {
            val = Short.parseShort(cell.cell);
        }catch(NumberFormatException e)
        {
            tempErrorCode = EError.ASSIGMENT_VIOLATION;
        }

        switch(reg)
        {
            case ERRegister.E_C:
                this.vregC = Boolean.valueOf(cell.cell);
                break;
            case ERRegister.E_CT:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.vregCT = val;
                break;
            case ERRegister.E_IC:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.vregIC = val;
                break;
            case ERRegister.E_R:
                this.vregR = cell;
                break;
            case ERRegister.E_SP:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.vregSP = val;
                break;
        }
        return errorCode;
    }

}
