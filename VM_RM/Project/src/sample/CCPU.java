package sample;

import sample.Enumerators.EError;
import sample.Memory.CCell;
import sample.Enumerators.ERRegister;

class CCPU {


    //registers
    private CCell regR; //Type CCell?

    private short regPTR;
    private short regIC;
    private short regSP;
    private short regINT;
    private short regCT;
    private short regPI;
    private short regTI;
    private short regSI;

    private boolean regMOD;
    private boolean regC;

    //Registers are use placeholders to have common lenght
    public String[] registers = {"__R", "__C", "_IC", "_SP", "_CT","_PI", "_SI", "_TI", "PTR", "INT", "MOD"};





    public void printCPURegisters(){
        System.out.println("PI: " + regPI + "\nTI:" + regTI +
                "\nSP: " + regSP+ "\nINT: " + regINT +"\nPTR: " + regPTR);
    }

    public void updateRegistersCCPUController(){
        Controller.PIoutput = ""+regPI;
        Controller.Routput = ""+regR.cell;
        Controller.PTRoutput = ""+regPTR;
        Controller.ICoutput = ""+regIC;
        Controller.SPoutput = ""+regSP;
        Controller.INToutput = ""+regINT;
        Controller.CToutput = ""+regCT;
        Controller.Coutput = ""+regC;
        Controller.PIoutput = ""+regPI;
        Controller.SIoutput = ""+regSI;
        Controller.TIoutput = ""+regTI;
        Controller.MODoutput = ""+regMOD;
    }


    CCPU(){
        this.regR = new CCell("00000");
        this.regPTR = 0;
        this.regIC = 0;
        this.regSP = 999;
        this.regINT = 200;
        this.regCT = 0;
        this.regC = false;
        this.regPI = 0;
        this.regSI = 0;
        this.regTI = 0;
        this.regMOD = true;
    }

    CCPU(CCell R, short PTR, short IC, short SP, short INT, short CT, boolean C, short PI, short SI, short TI, boolean MOD )
    {
        this.regR = R;
        this.regPTR = PTR;//
        this.regIC = IC;//
        this.regSP = SP;//
        this.regINT = INT;//
        this.regCT = CT;
        this.regC = C;
        this.regPI = PI;
        this.regSI = SI;//
        this.regTI = TI;//
        this.regMOD = MOD;//
    }
    public void setRegPI(short regPI) {
        this.regPI = regPI;
    }
    public short getRegPI() {
        return regPI;
    }

    public void setRegTI(short regTI) {
        this.regTI = regTI;
    }
    public short getRegTI() {
        return regTI;
    }

    public void setRegSP(short regSP) {
        this.regSP = regSP;
    }
    public short getRegSP() {
        return regSP;
    }

    public void setRegINT(short regINT) {
        this.regINT = regINT;
    }
    public short getRegINT() { return regINT; }

    public void setRegPTR(short regPTR) {
        this.regPTR = regPTR;
    }
    public short getRegPTR() {
        return regPTR;
    }

    public void setRegMOD(boolean regMOD){ this.regMOD = regMOD; }
    public boolean getRegMod(){return regMOD;}

    public void setRegIC(short regIC) {
        this.regIC = regIC;
    }
    public short getRegIC() {
        return regIC;
    }

    public CCell getRegR(){ return regR; }
    public void setRegR(CCell regR) { this.regR = regR;  }

    public short getRegCT() { return regCT; }
    public void setRegCT(short regCT) { this.regCT = regCT; }

    public short getRegSI() {return regSI; }
    public void setRegSI(short regSI) {this.regSI = regSI; }

    public boolean getRegC() { return regC;}
    public void setRegC(boolean regC) { this.regC = regC; }

    public CCell ConvertRegToCCell(String reg)
    {
        CCell returnValue = new CCell();
        switch(reg)
        {
            case ERRegister.E_C:
                returnValue.cell = Boolean.toString(this.regC);
                break;
            case ERRegister.E_CT:
                returnValue.cell = Short.toString(this.regCT);
                break;
            case ERRegister.E_IC:
                returnValue.cell = Short.toString(this.regIC);
                break;
            case ERRegister.E_INT:
                returnValue.cell = Short.toString(this.regINT);
                break;
            case ERRegister.E_MOD:
                returnValue.cell = Boolean.toString(this.regMOD);
                break;
            case ERRegister.E_PI:
                returnValue.cell = Short.toString(this.regPI);
                break;
            case ERRegister.E_PTR:
                returnValue.cell = Short.toString(this.regPTR);
                break;
            case ERRegister.E_R:
                returnValue = this.regR;
                break;
            case ERRegister.E_SI:
                returnValue.cell = Short.toString(this.regSI);
                break;
            case ERRegister.E_SP:
                returnValue.cell = Short.toString(this.regSP);
                break;
            case ERRegister.E_TI:
                returnValue.cell = Short.toString(this.regTI);
                break;
        }
        returnValue.cell = CUtils.NormalizeString(returnValue.cell);


        return  returnValue;
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
                this.regC = Boolean.valueOf(cell.cell);
                break;
            case ERRegister.E_CT:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regCT = val;
                break;
            case ERRegister.E_IC:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regIC = val;
                break;
            case ERRegister.E_INT:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regINT = val;
                break;
            case ERRegister.E_MOD:
                this.regMOD = Boolean.valueOf(cell.cell);
                break;
            case ERRegister.E_PI:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regPI = val;
                break;
            case ERRegister.E_PTR:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regPTR = val;
                break;
            case ERRegister.E_R:
                this.regR = cell;
                break;
            case ERRegister.E_SI:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regSI = val;
                break;
            case ERRegister.E_SP:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regSP = val;
                break;
            case ERRegister.E_TI:
                if(tempErrorCode!=EError.VALIDATION_SUCCESS) errorCode = tempErrorCode;
                else this.regTI = val;
                break;
        }
        return errorCode;
    }


}
