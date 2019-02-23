package os;

/**
 *
 * @author eimantas
 */
public class Memory {
    
    String cell;
    boolean state;

    Memory(){
        setCell("");
        setState(false);
    }
    
    public void setCell( String cell ){
        if( this.isState() ){
            OS.realMachine.setRegisterPI(1);
        } else{
            this.cell = cell;
        }
    }
    
    public String getCell(){
        return cell;    
    }
    
    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public String toString() {
        return "Memory{" + "cell=" + cell + ", state=" + state + '}';
    }
    
    
    
    
}
