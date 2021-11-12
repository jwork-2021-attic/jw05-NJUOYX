package nju.java.logic.Controler;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import nju.java.logic.element.Brother;
import nju.java.logic.system.SysHandler;

public class User extends Brother {

    public User(SysHandler handler, int identity) {
        super(handler, identity);
    }

    public void run() {
        handler.setVisibleOfMe(this, true);
        while (true) {
            String move = handler.getMoveInput();
            System.out.println(move);
            int nx = x, ny = y;
            if (move != null) {
                if (move == "up") {
                    ny--;
                } else if (move == "down") {
                    ny++;
                } else if (move == "left") {
                    nx--;
                } else if (move == "right") {
                    nx++;
                } else {
                    continue;
                }
                handler.moveTo(this, nx, ny);
                handler.setVisibleOfMe(this, false);
                x = nx;
                y = ny;
                handler.setVisibleOfMe(this, true);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ir) {
                System.out.println(ir);
            }
        }
    }

}
