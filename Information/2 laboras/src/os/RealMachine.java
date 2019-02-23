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
    
    String registers[] = {"CT", "IC", "SP", "C", "R", "MOD", "PTR", "TI","SI","PI",
                          "INT"}; 

    public RealMachine() {
        
        this.registerR = "0";
        this.registerPTR = 0;
        this.registerIC = 0;
        this.registerSP = 999;
        this.registerINT = 0;
        this.registerCT = 0;
        this.registerPI = 0;
        this.registerSI = 0;
        this.registerTI = 0;
        this.SB = 0;
        this.DB = 0;
        this.ST = 0;
        this.DT = 0;
        this.SZ = 0;
        this.registerC = false;
        this.registerMOD = false;
        
    }
    
    public void changeR()
    {
        this.registerR = OS.rmMemory[this.registerIC].getCell();
    }
    public void loadR(int memCell)
    {
        this.registerR = OS.rmMemory[memCell].getCell();
    }
    public void saveR(int memCell)
    {
        OS.rmMemory[memCell].setCell(this.registerR);
    }
     
    public void loadRR(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: this.registerR = String.valueOf(this.registerCT);
                            break;
                    case 1: this.registerR = String.valueOf(this.registerIC);
                            break;
                    case 2: this.registerR = String.valueOf(this.registerSP);
                            break;
                    case 3: this.registerR = String.valueOf(this.registerC);
                            break;
                    case 4: this.registerR = String.valueOf(this.registerR);
                            break;
                    case 5: this.registerR = String.valueOf(this.registerMOD);
                            break;
                    case 6: this.registerR = String.valueOf(this.registerPTR);
                            break;
                    case 7: this.registerR = String.valueOf(this.registerTI);
                            break;
                    case 8: this.registerR = String.valueOf(this.registerSI);
                            break;
                    case 9: this.registerR = String.valueOf(this.registerPI);
                            break;
                    case 10: this.registerR = String.valueOf(this.registerINT);
                            break;
                }
            }
        }
    }
    
    public void addR(int memCell)
    {
        this.registerR = String.valueOf(Integer.valueOf(this.registerR) + Integer.valueOf(OS.rmMemory[memCell].getCell()));
    }
    
    public void subR(int memCell)
    {
        this.registerR = String.valueOf(Integer.valueOf(this.registerR) - Integer.valueOf(OS.rmMemory[memCell].getCell()));
    }
    
    public void mulR(int memCell)
    {
                this.registerR = String.valueOf(Integer.valueOf(this.registerR) * Integer.valueOf(OS.rmMemory[memCell].getCell()));
    }
    
    public void divR(int memCell)
    {
                this.registerR = String.valueOf(Integer.valueOf(this.registerR) / Integer.valueOf(OS.rmMemory[memCell].getCell()));
    }
    
    public void cmpR(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) == Integer.valueOf(OS.rmMemory[memCell].getCell()));
    }
    
    public void cmpRL(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) < Integer.valueOf(OS.rmMemory[memCell].getCell()));
    }
    
    public void cmpRG(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) > Integer.valueOf(OS.rmMemory[memCell].getCell()));
    } 
    
    public void cmpZ(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: this.registerC = (this.registerCT == 0 );
                            return;
                    case 1: this.registerC = (this.registerIC == 0 );
                            return;
                    case 2: this.registerC = (this.registerSP == 0 );
                            return;
                    case 3: this.registerC = (this.registerC == false );
                            return;
                    case 4: this.registerC = (Integer.valueOf(this.registerR) == 0 );
                            return;
                    case 5: this.registerC = (this.registerMOD == false );
                            return;
                    case 6: this.registerC = (this.registerPTR == 0 );
                            return;
                    case 7: this.registerC = (this.registerTI == 0 );
                            return;
                    case 8: this.registerC = (this.registerSI == 0 );
                            return;
                    case 9: this.registerC = (this.registerPI == 0 );
                            return;
                    case 10: this.registerC = (this.registerINT == 0 );
                            return;
                }
            }
        }
        //this.registerC = (Integer.valueOf(this.registerR) == 0 );
    }
    
    public void jumpIf(int memCell)
    {
        if(this.registerC)
        {
            this.registerIC = memCell;
        }
    }
    
    public void jump(int memCell)
    {
        this.registerIC = memCell;
    }
    
    public void call(int memCell)
    {
        push("MOD");
        push("PTR");
        push("C");
        push("R");
        push("IC");
        push("SP");
        push("CT");
        this.registerIC = Integer.valueOf(OS.rmMemory[memCell].getCell());
        jump(memCell);
    }
    
    public void push(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: OS.rmMemory[this.registerSP].setCell(String.valueOf(this.registerCT));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 1: OS.rmMemory[this.registerSP].setCell(String.valueOf(this.registerIC));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 2: OS.rmMemory[this.registerSP].setCell(String.valueOf(this.registerSP));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 3: OS.rmMemory[this.registerSP].setCell(String.valueOf(this.registerC));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 4: OS.rmMemory[this.registerSP].setCell(this.registerR);
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 5: OS.rmMemory[this.registerSP].setCell(Boolean.toString(this.registerMOD));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 6: OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerPTR));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 7: OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerTI));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 8: OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerSI));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 9: OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerPI));
                            this.registerSP = this.registerSP - 1;
                            return;
                    case 10: OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerINT));
                            this.registerSP = this.registerSP - 1;
                            return;
                }
            }
        }
    }
    // {"CT", "IC", "SP", "C", "R", "MOD", "PTR", "TI","SI","PI", "INT"}; 
    public void pop(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: this.registerSP = this.registerSP + 1;
                            this.registerCT = Integer.valueOf(OS.rmMemory[this.registerSP].getCell()); 
                            return;
                    case 1: this.registerSP = this.registerSP + 1;
                            this.registerIC = Integer.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 2: this.registerSP = this.registerSP + 1;
                            this.registerSP = Integer.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 3: this.registerSP = this.registerSP + 1;
                            this.registerC = Boolean.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 4: this.registerSP = this.registerSP + 1;
                            this.registerR = OS.rmMemory[this.registerSP].getCell();
                            return;
                    case 5: this.registerSP = this.registerSP + 1;
                            this.registerMOD = Boolean.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 6: this.registerSP = this.registerSP + 1;
                            this.registerPTR = Integer.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 7: this.registerSP = this.registerSP + 1;
                            this.registerTI = Integer.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 8: this.registerSP = this.registerSP + 1;
                            this.registerSI = Integer.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 9: this.registerSP = this.registerSP + 1;
                            this.registerPI = Integer.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                    case 10: this.registerSP = this.registerSP + 1;
                            this.registerINT = Integer.valueOf(OS.rmMemory[this.registerSP].getCell());
                            return;
                }
            }
        }
    }
    public void returnC ()
    {          
        pop("CT");
        pop("SP");
        pop("IC");
        pop("R");
        pop("C");
        pop("PTR");
        pop("MOD");
    }
    
    public void systemCall(int number)
    {
        OS.realMachine.setRegisterSI(number);
    }
    
    public void loop(int memCell)
    {
        if(this.registerCT != 0)
        {
            this.registerCT = this.registerCT - 1;
            jump(memCell);
        }
    }
    
    public void doCommand(int number, String value)
    {
        switch(number)
         { 
            case 0 : changeR();
                     break;
            case 1 : loadR(Integer.valueOf(value));
                     break;
            case 2 : saveR(Integer.valueOf(value));
                     break;
            case 3 : loadRR(value);
                     break;
            case 4 : addR(Integer.valueOf(value));
                     break;
            case 5 : subR(Integer.valueOf(value));
                     break;
            case 6 : mulR(Integer.valueOf(value));
                     break;
            case 7 : divR(Integer.valueOf(value));
                     break;
            case 8 : cmpR(Integer.valueOf(value));
                     break;
            case 9 : cmpRL(Integer.valueOf(value));
                     break;
            case 10: cmpRG(Integer.valueOf(value));
                     break;
            case 11: cmpZ(value);
                     break;
            case 12: jumpIf(Integer.valueOf(value));
                     break;
            case 13: jump(Integer.valueOf(value));
                     break;
            case 14: call(Integer.valueOf(value));
                     break;
            case 15: push(value);
                     break;
            case 16: pop(value);
                     break;
            case 17: returnC();
                     break;
            case 18: systemCall(Integer.valueOf(value));
                     break;
            case 19: loop(Integer.valueOf(value));
                     break;
            case 20: chnge();
                     break;
            case 21: sepi( Integer.parseInt(value) );
                     break;
            case 22: seti( Integer.parseInt(value) );
                     break;
            case 23: ptr( Integer.parseInt(value) );
                     break;
            case 24: sp( Integer.parseInt(value) );
                     break;
            case 25: in( Integer.parseInt(value) );
                     break;
            case 26: start();
                     break;
            case 27: calli();
                     break;
            case 28: iretn();
                     break;
            case 29: sb( Integer.parseInt(value) );
                     break;
            case 30: db( Integer.parseInt(value) );
                     break;
            case 31: st( Integer.parseInt(value) );
                     break;
            case 32: dt( Integer.parseInt(value) );
                     break;
            case 33: sz( Integer.parseInt(value) );
                     break;
            case 34: xchgn();   
            default: // throw exception
         }
    }
    
    public void chnge(){
        this.registerMOD = !this.registerMOD;
    } 
    
    public void sepi( int x ){
        if( x >= 0 && x <= 999 ){
            this.registerPI = x;
        } 
    }
    
    public void seti( int x ){ 
        if( x >= 0 && x <= 999 ){
            this.registerTI = x;
        } else; // throw exception   
    }
    
    public void ptr( int block_number ){
        if( block_number >= 0 && block_number <= 99 ){
            this.registerPTR = block_number;
        } else; // throw exception
    }
    
    public void sp( int cell_adress ){
        if( cell_adress >= 0 && cell_adress <= 999 ){
            this.registerSP = cell_adress;
        } else; // throw exception
        
    }
    
    public void in( int cell_adress ){
        if( cell_adress >= 0 && cell_adress <= 999 ){
            this.registerINT = cell_adress;
        } else; // throw exception 
    }
    
    public void start(){
       OS.rmMemory[this.registerSP].setCell(Boolean.toString(this.registerMOD));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerPTR));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Boolean.toString(this.registerC));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(this.registerR);
       this.registerSP--;
       this.registerIC = 0;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerCT));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerINT));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerSP));
       this.registerSP--;
    }
    
    public void calli(){
       OS.rmMemory[this.registerSP].setCell(Boolean.toString(this.registerMOD));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerPTR));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Boolean.toString(this.registerC));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(this.registerR);
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerIC));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerSP));
       this.registerSP--;
       OS.rmMemory[this.registerSP].setCell(Integer.toString(this.registerCT));
       this.registerSP--;
       this.registerIC = this.registerINT;    
    }

    public void iretn(){
       this.registerCT = Integer.parseInt(OS.rmMemory[this.registerSP].getCell());
       this.registerSP++;
       this.registerSP = Integer.parseInt(OS.rmMemory[this.registerSP].getCell());
       this.registerSP++;
       this.registerIC = Integer.parseInt(OS.rmMemory[this.registerSP].getCell());
       this.registerSP++;
       this.registerR = OS.rmMemory[this.registerSP].getCell();
       this.registerSP++;
       this.registerC = Boolean.parseBoolean(OS.rmMemory[this.registerSP].getCell());
       this.registerSP++;
       this.registerPTR = Integer.parseInt(OS.rmMemory[this.registerSP].getCell());
       this.registerSP++;
       this.registerMOD = Boolean.parseBoolean(OS.rmMemory[this.registerSP].getCell());
       this.registerSP++;
    }

    public void sb( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.SB = object_number;
        } else ; // throw exception
    }

    public void db( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.DB = object_number;
        } else ; // throw exception
    }
    
    public void st( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.ST = object_number;
        } else ; // throw exception
    }
    
    public void dt( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.DT = object_number;
        } else ; // throw exception
    }
    
    public void sz( int object_number ){
        if( object_number >= 0 && object_number <= 999 ){
        this.SZ = object_number;
        } else ; // throw exception
    }
  
    public void xchgn(){
        /*
        dafuq
        */  
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
    
    @Override
    public String toString() {
        return "RealMachine{" + "registerR=" + registerR + ", registerPTR=" + registerPTR + ", registerIC=" + registerIC + ", registerSP=" + registerSP + ", registerINT=" + registerINT + ", registerCT=" + registerCT + ", registerPI=" + registerPI + ", registerSI=" + registerSI + ", registerTI=" + registerTI + ", SB=" + SB + ", DB=" + DB + ", ST=" + ST + ", DT=" + DT + ", SZ=" + SZ + ", registerC=" + registerC + ", registerMOD=" + registerMOD + '}';
    }
    
    
}
