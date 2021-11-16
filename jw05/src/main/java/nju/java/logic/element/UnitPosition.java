package nju.java.logic.element;

public class UnitPosition extends Position{

    private Unit owner = null;

    public UnitPosition(int x, int y){
        super(x, y);
    }

    public Boolean getOccupied(){return owner != null;}

    public synchronized Boolean tryOccupy(Unit owner){
        if(this.owner == null || this.owner == owner){
            this.owner = owner;
            return true;
        }else{
            return false;
        }
    }

    public synchronized void release(Unit owner){
        if(this.owner!=null && this.owner==owner){
            this.owner = null;
        }
    }

}
