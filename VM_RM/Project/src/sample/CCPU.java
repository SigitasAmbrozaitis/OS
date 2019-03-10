package sample;

import sample.Memory.CCell;

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
        Controller.Routput = ""+regR;
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

    public short isRegSI() {return regSI; }
    public void setRegSI(short regSI) {this.regSI = regSI; }

    public boolean isRegC() { return regC;}
    public void setRegC(boolean regC) { this.regC = regC; }



}
