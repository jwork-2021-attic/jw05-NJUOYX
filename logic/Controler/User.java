package logic.Controler;

import logic.element.Brother;
import logic.system.SysHandler;

public class User extends Brother{

    private SysHandler handler;

    public User(SysHandler handler, int identity){
        super(handler, identity);
    }

    public void run(){
        while(true){
            String move = handler.getMoveInput();
            if(move != null){
                if(move == "up"){
                    handler.moveTo(this, x, y-1);
                }else if(move == "down"){
                    handler.moveTo(this, x, y+1);
                }else if(move == "left"){
                    handler.moveTo(this, x-1, y);
                }else if(move == "right"){
                    handler.moveTo(this, x+1, y);
                }else{
                    
                }
            }
        }
    }

}
