package sample;

class CCPU {

    //registers
    private String regR;

    private short regPTR;
    private short regIC;
    private short regSP;
    private short regINT;
    private short regCT;

    private boolean regC;

    private char regPI;
    private boolean regSI;

    private char regTI;
    private boolean regMOD;

    public String[] registers = {"R", "PTR", "IC", "SP", "INT", "CT", "C", "PI", "SI", "TI", "MOD"};

    public void printCPURegisters(){
        System.out.println("PI: " + regPI + "\nTI:" + regTI +
                "\nSP: " + regSP+ "\nINT: " + regINT +"\nPTR: " + regPTR);
    }


    CCPU(){

    }

    CCPU(String R, short PTR, short IC, short SP, short INT, short CT, boolean C, char PI, boolean SI, char TI, boolean MOD )
    {
        this.regR = R;
        this.regPTR = PTR;
        this.regIC = IC;
        this.regSP = SP;
        this.regINT = INT;
        this.regCT = CT;
        this.regC = C;
        this.regPI = PI;
        this.regSI = SI;
        this.regTI = TI;
        this.regMOD = MOD;
    }
    public void setRegPI(char regPI) {
        this.regPI = regPI;
    }

    public char getRegPI() {
        return regPI;
    }
    public void setRegTI(char regTI) {
        this.regTI = regTI;
    }

    public char getRegTI() {
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

    public short getRegINT() {
        return regINT;
    }

    public void setRegPTR(short regPTR) {
        this.regPTR = regPTR;
    }

    public short getRegPTR() {
        return regPTR;
    }
}
