import javax.swing.JFrame;

import logic.system.Rsystem;


public class Main {
    public static void main(String args[]){
        Rsystem sys = new Rsystem(100, 100);
        sys.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sys.setVisible(true);
    }
}
