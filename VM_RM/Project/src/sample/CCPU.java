package sample;

class CCPU {

    private String regR;

    private short regPTR;
    private short regIC;
    private short regSP;
    private short regINT;
    private short regCT;

    private boolean regC;
    private boolean regPI;
    private boolean regSI;
    private boolean regTI;
    private boolean regMOD;

    CCPU(String R, short PTR, short IC, short SP, short INT, short CT, boolean C, boolean PI, boolean SI, boolean TI, boolean MOD )
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

    public String getRegR() {
        return regR;
    }

    public void setRegR(String regR) {
        this.regR = regR;
    }

    public short getRegPTR() {
        return regPTR;
    }

    public void setRegPTR(short regPTR) {
        this.regPTR = regPTR;
    }

    public short getRegIC() {
        return regIC;
    }

    public void setRegIC(short regIC) {
        this.regIC = regIC;
    }

    public short getRegSP() {
        return regSP;
    }

    public void setRegSP(short regSP) {
        this.regSP = regSP;
    }

    public short getRegINT() {
        return regINT;
    }

    public void setRegINT(short regINT) {
        this.regINT = regINT;
    }

    public short getRegCT() {
        return regCT;
    }

    public void setRegCT(short regCT) {
        this.regCT = regCT;
    }

    public boolean isRegC() {
        return regC;
    }

    public void setRegC(boolean regC) {
        this.regC = regC;
    }

    public boolean isRegPI() {
        return regPI;
    }

    public void setRegPI(boolean regPI) {
        this.regPI = regPI;
    }

    public boolean isRegSI() {
        return regSI;
    }

    public void setRegSI(boolean regSI) {
        this.regSI = regSI;
    }

    public boolean isRegTI() {
        return regTI;
    }

    public void setRegTI(boolean regTI) {
        this.regTI = regTI;
    }

    public boolean isRegMOD() {
        return regMOD;
    }

    public void setRegMOD(boolean regMOD) {
        this.regMOD = regMOD;
    }
}
