package nju.java.logic.element;

public class UnitPosition extends Position{

    private Boolean occupied = false;

    public UnitPosition(int x, int y){
        super(x, y);
    }

    public Boolean getOccupied(){return occupied;}

    public synchronized Boolean tryOccupy(){
        if(!occupied){
            occupied = true;
            return true;
        }else{
            return false;
        }
    }

    public void release(){
        occupied = false;
    }

}
