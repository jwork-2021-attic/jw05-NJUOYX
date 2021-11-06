package logic.system;


public class System implements SysHandler{
    
    private int rangeX;
    private int rangeY;

    public System(int rangeX, int rangeY){
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }

    @Override
    public int getRangeX(){
        return this.rangeX;
    }

    @Override
    public int getRangeY(){
        return this.rangeY;
    }

}
