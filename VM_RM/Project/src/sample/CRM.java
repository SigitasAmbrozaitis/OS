package sample;

/*imports*/

/*Real Machine implementation*/
public class CRM
{
    /*private variables: registers: all registers will start with prefix reg*/

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

    /*constructor, used to set RM starting state*/

    /*commands that can only be executed by RM */

    private String[] Commands = { "PI","TI","SP","IN","BS","DB","ST","DT","SZ","PTR","CHNGM","CALLI","IRETN","START","XCHGN" };

    /*commands that can be executed by RM and VM */






}
