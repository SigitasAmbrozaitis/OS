package os;

/**
 *
 * @author eimantas
 */
public class RealMachine {
    
    private String registerR;
    private int registerPTR,
                registerIC,
                registerSP,
                registerINT,
                registerCT,
                registerPI,
                registerSI,
                registerTI;
    
    private int SB,
                DB,
                ST,
                DT,
                SZ;
    
    private boolean registerC,
                    registerMOD;

    public RealMachine() {
        
        this.registerR = "";
        this.registerPTR = 0;
        this.registerIC = 0;
        this.registerSP = 999;
        this.registerINT = 0;
        this.registerCT = 0;
        this.registerPI = 0;
        this.registerSI = 0;
        this.registerTI = 50;
        this.SB = 0;
        this.DB = 0;
        this.ST = 0;
        this.DT = 0;
        this.SZ = 0;
        this.registerC = false;
        this.registerMOD = false;
        
    }
    
    public void doCommand( int number, String value )
    {
        
    }

    @Override
    public String toString() {
        return "RealMachine{" + "registerR=" + registerR + ", registerPTR=" + registerPTR + ", registerIC=" + registerIC + ", registerSP=" + registerSP + ", registerINT=" + registerINT + ", registerCT=" + registerCT + ", registerPI=" + registerPI + ", registerSI=" + registerSI + ", registerTI=" + registerTI + ", SB=" + SB + ", DB=" + DB + ", ST=" + ST + ", DT=" + DT + ", SZ=" + SZ + ", registerC=" + registerC + ", registerMOD=" + registerMOD + '}';
    }

    public String getRegisterR() {
        return registerR;
    }

    public int getRegisterPTR() {
        return registerPTR;
    }

    public int getRegisterIC() {
        return registerIC;
    }

    public int getRegisterSP() {
        return registerSP;
    }

    public int getRegisterINT() {
        return registerINT;
    }

    public int getRegisterCT() {
        return registerCT;
    }

    public int getRegisterPI() {
        return registerPI;
    }

    public int getRegisterSI() {
        return registerSI;
    }

    public int getRegisterTI() {
        return registerTI;
    }

    public int getSB() {
        return SB;
    }

    public int getDB() {
        return DB;
    }

    public int getST() {
        return ST;
    }

    public int getDT() {
        return DT;
    }

    public int getSZ() {
        return SZ;
    }

    public boolean isRegisterC() {
        return registerC;
    }

    public boolean isRegisterMOD() {
        return registerMOD;
    }

    public void setRegisterR(String registerR) {
        this.registerR = registerR;
    }

    public void setRegisterPTR(int registerPTR) {
        this.registerPTR = registerPTR;
    }

    public void setRegisterIC(int registerIC) {
        this.registerIC = registerIC;
    }

    public void setRegisterSP(int registerSP) {
        this.registerSP = registerSP;
    }

    public void setRegisterINT(int registerINT) {
        this.registerINT = registerINT;
    }

    public void setRegisterCT(int registerCT) {
        this.registerCT = registerCT;
    }

    public void setRegisterPI(int registerPI) {
        this.registerPI = registerPI;
    }

    public void setRegisterSI(int registerSI) {
        this.registerSI = registerSI;
    }

    public void setRegisterTI(int registerTI) {
        this.registerTI = registerTI;
    }

    public void setSB(int SB) {
        this.SB = SB;
    }

    public void setDB(int DB) {
        this.DB = DB;
    }

    public void setST(int ST) {
        this.ST = ST;
    }

    public void setDT(int DT) {
        this.DT = DT;
    }

    public void setSZ(int SZ) {
        this.SZ = SZ;
    }

    public void setRegisterC(boolean registerC) {
        this.registerC = registerC;
    }

    public void setRegisterMOD(boolean registerMOD) {
        this.registerMOD = registerMOD;
    }
    
    
    
    
    
}
