package exception;


public abstract class Mexception extends Exception{
    protected int ex;
    
    public Mexception(int ex){
        this.ex = ex;
    }
}
