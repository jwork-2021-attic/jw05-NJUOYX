package exception;

public class Logic_e extends Mexception{
    public enum Ex_num{
        HANDLE_NULL,
    }
    public Logic_e(Ex_num ex){
        super(ex.ordinal());
    }
}
