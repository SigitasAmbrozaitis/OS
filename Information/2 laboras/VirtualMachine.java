/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os2;

import static java.lang.String.*;

/**
 *
 * @author Algirdas
 */
public class VirtualMachine 
{

    public VirtualMachine(int memoryRequired) 
    {
        
    }
    //registers
    private String registerR;
    private int registerCT;
    private int registerIC;
    private int registerSP;
    private boolean registerC;
    
    //other Fields
    private final int memorySize = 100;
    private Memory[] memory = new Memory[this.memorySize];
    String registers[] = {"CT", "IC", "SP", "C", "R"};
    
    //constructor

    public VirtualMachine() {
        this.registerR = "";
        this.registerCT = 0;
        this.registerIC = 0;
        this.registerSP = 99;
        this.registerC = false;
        memoryInit();
    }
    
    //memory work
    private void memoryInit()
    {
        for(int i = 0; i < memorySize; i++)
        {
            this.memory[i] = new Memory(false, "");
        }
    }
    
    public void printMemory()
    {
        for(int i = 0; i < memorySize; i++)
        {
            System.out.println(i + " " + memory[i]);
        }
    }
    
    //setters
    public void setRegisterR(String registerR) {
        this.registerR = registerR;
    }

    public void setRegisterCT(int registerCT) {
        this.registerCT = registerCT;
    }

    public void setRegisterIC(int registerIC) {
        this.registerIC = registerIC;
    }

    public void setRegisterSP(int registerSP) {
        this.registerSP = registerSP;
    }

    public void setRegisterC(boolean registerC) {
        this.registerC = registerC;
    }
    
    //getters

    public String getRegisterR() {
        return registerR;
    }

    public int getRegisterCT() {
        return registerCT;
    }

    public int getRegisterIC() {
        return registerIC;
    }

    public int getRegisterSP() {
        return registerSP;
    }

    public boolean isRegisterC() {
        return registerC;
    }
    
    //toString

    @Override
    public String toString() {
        return "VirtualMachine{" + "registerR=" + registerR + ", registerCT=" + registerCT + ", registerIC=" + registerIC + ", registerSP=" + registerSP + ", registerC=" + registerC + '}';
    }
    
    // TODO 
    //other methods
    public void changeR()
    {
        this.registerR = memory[this.registerIC].getCell();
    }
    public void loadR(int memCell)
    {
        this.registerR = memory[memCell].getCell();
    }
    public void saveR(int memCell)
    {
        memory[memCell].setCell(this.registerR);
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
                    case 1: this.registerR = String.valueOf(this.registerIC);
                    case 2: this.registerR = String.valueOf(this.registerSP);
                    case 3: this.registerR = String.valueOf(this.registerC);
                    case 4: this.registerR = String.valueOf(this.registerR);
                }
            }
        }
    }
    public void addR(int memCell)
    {
        this.registerR = String.valueOf(Integer.valueOf(this.registerR) + Integer.valueOf(memory[memCell].getCell()));
    }
    public void subR(int memCell)
    {
        this.registerR = String.valueOf(Integer.valueOf(this.registerR) - Integer.valueOf(memory[memCell].getCell()));
    }
    public void mulR(int memCell)
    {
                this.registerR = String.valueOf(Integer.valueOf(this.registerR) * Integer.valueOf(memory[memCell].getCell()));
    }
    public void divR(int memCell)
    {
                this.registerR = String.valueOf(Integer.valueOf(this.registerR) / Integer.valueOf(memory[memCell].getCell()));
    }
    public void cmpR(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) == Integer.valueOf(memory[memCell].getCell()));
    }
    public void cmpRL(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) < Integer.valueOf(memory[memCell].getCell()));
    }
    public void cmpRG(int memCell)
    {
        this.registerC = (Integer.valueOf(this.registerR) > Integer.valueOf(memory[memCell].getCell()));
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
                    case 1: this.registerC = (this.registerIC == 0 );
                    case 2: this.registerC = (this.registerSP == 0 );
                    case 3: this.registerC = (this.registerC == false );
                    case 4: this.registerC = (Integer.valueOf(this.registerR) == 0 );
                }
            }
        }
        this.registerC = (Integer.valueOf(this.registerR) == 0 );
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
        push("C");
        push("R");
        push("IC");
        push("SP");
        push("CT");
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
                    case 0: memory[this.registerSP].setCell(String.valueOf(this.registerCT));
                            this.registerSP = this.registerSP - 1;
                    case 1: memory[this.registerSP].setCell(String.valueOf(this.registerIC));
                            this.registerSP = this.registerSP - 1;
                    case 2: memory[this.registerSP].setCell(String.valueOf(this.registerSP));
                            this.registerSP = this.registerSP - 1;
                    case 3: memory[this.registerSP].setCell(String.valueOf(this.registerC));
                            this.registerSP = this.registerSP - 1;
                    case 4: memory[this.registerSP].setCell(this.registerR);
                            this.registerSP = this.registerSP - 1;
                }
            }
        }
    }
    public void pop(String reg)
    {
        for(char i = 0 ; i < registers.length; i++ )
        {
            if ( reg.contains(registers[i]) )
            {
                switch(i)
                {
                    case 0: this.registerSP = this.registerSP + 1;
                            this.registerCT = Integer.valueOf(memory[this.registerSP].getCell());          
                    case 1: this.registerSP = this.registerSP + 1;
                            this.registerIC = Integer.valueOf(memory[this.registerSP].getCell());
                    case 2: this.registerSP = this.registerSP + 1;
                            this.registerSP = Integer.valueOf(memory[this.registerSP].getCell());
                    case 3: this.registerSP = this.registerSP + 1;
                            this.registerC = Boolean.valueOf(memory[this.registerSP].getCell());
                    case 4: this.registerSP = this.registerSP + 1;
                            this.registerR = memory[this.registerSP].getCell();
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
        push("C");
    }
    public void systemCall(int number)
    {
        OS2.realMachine.setRegisterSI(number);
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
            case 1 : loadR(Integer.valueOf(value));
            case 2 : saveR(Integer.valueOf(value));
            case 3 : loadRR(value);
            case 4 : addR(Integer.valueOf(value));
            case 5 : subR(Integer.valueOf(value));
            case 6 : mulR(Integer.valueOf(value));
            case 7 : divR(Integer.valueOf(value));
            case 8 : cmpR(Integer.valueOf(value));
            case 9 : cmpRL(Integer.valueOf(value));
            case 10: cmpRG(Integer.valueOf(value));
            case 11: cmpZ(value);
            case 12: jumpIf(Integer.valueOf(value));
            case 13: jump(Integer.valueOf(value));
            case 14: call(Integer.valueOf(value));
            case 15: push(value);
            case 16: pop(value);
            case 17: returnC();
            case 18: systemCall(Integer.valueOf(value));
            case 19: loop(Integer.valueOf(value));
         }
    }
}
