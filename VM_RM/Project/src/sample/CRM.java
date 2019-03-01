package sample;

/*imports*/

/*Real Machine implementation*/
public class CRM
{
    private CCPU cpu;
    private CMemory memory;

    //getters for GUI
    public CCPU getCpu()
    {
        return cpu;
    }

    public CMemory getMemory()
    {
        return memory;
    }

    /*private variables: registers: all registers will start with prefix reg*/

    /*constructor, used to set RM starting state*/

    /*commands that can be executed by RM*/

    //TODO JURGIS implements this
    //TODO implement commands string
    //TODO implement commands
    //TODO tip: when implementing RM commands implement first only RM commands then RM&VM commands. For easier copy paste to VM

    //TODO ANYONE should be done after commands are implemented
    //TODO implement command validation
    //TODO implement command and its parameters recognition (register recognition too)


    private String[] cmdR = { "PI","TI","SP","IN","BS","DB","ST","DT","SZ","PTR","CHNGM","CALLI","IRETN","START","XCHGN" };

    /*commands that can be executed by RM and VM */





}
