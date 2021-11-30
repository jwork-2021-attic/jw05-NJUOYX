package nju.java.logic.element;

public class Brother extends ActiveElement {

    private void resolveMove(String str) {
        int nx = x;
        int ny = y;

        switch (str) {
        case "up":
            ny--;
            break;
        case "down":
            ny++;
            break;
        case "left":
            nx--;
            break;
        case "right":
            nx++;
            break;
        default:assert(false);
            break;
        }
        direction = str;
        moveTo(nx, ny);
    }

    protected void attack() {
        new Bullet(x,y,direction, GAPI).start();
    }

    private void resolveInput(String str) {
        if (str != null) {
            switch (str) {
            case "up":
            case "down":
            case "left":
            case "right":
                resolveMove(str);
                break;

            case "attack":
                attack();
                break;
            default:
                break;
            }
        }
    }

    @Override
    public void activeProcessor() {
        String str = GAPI.getInput();
        resolveInput(str);
    }


    @Override
    public void frameSleep(){
        eSleep(25);
    }

}
