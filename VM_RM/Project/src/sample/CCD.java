/**
 * Kanalu irenginio klase
 */
package sample;

public class CCD {
    CCD(){}
    private short regSB;
    private short regDB;
    private short regST;
    private short regDT;
    private short regSZ;

    public void updateRegisterCCDController(){
        Controller.SBoutput = ""+regSB;
        Controller.DBoutput = ""+regDB;
        Controller.SToutput = ""+regST;
        Controller.DToutput = ""+regDT;
        Controller.SZoutput = ""+regSZ;
    }


    public void setRegSB(short regSB){this.regSB = regSB;}
    public short getRegSB(){ return regSB;}

    public void setRegDB(short regDB){this.regDB = regDB;}
    public short getRegDB(){ return regDB;}

    public void setRegST(short regST){this.regST = regST;}
    public short getRegST(){ return regST;}

    public void setRegDT(short regDT){this.regDT = regDT;}
    public short getRegDT(){ return regDT;}

    public void setRegSZ(short regSZ){this.regSZ = regSZ;}
    public short getRegSZ(){ return regSZ;}
}
