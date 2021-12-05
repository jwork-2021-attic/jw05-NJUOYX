package nju.java.logic.element.utils;

import nju.java.logic.element.GAPI;

public class UtilsMaker {
    public static void utilStart(String util, int x, int y , String direction, GAPI gapi){
        switch (util){
            case "Gun":gunStart(x, y, direction, gapi);break;
            case "Shotgun":shotgunStart(x, y, direction, gapi);break;
            default:make(util, x, y, direction, gapi).start();break;
        }
    }

    private static void gunStart(int x, int y , String direction, GAPI gapi){
        new Bullet(x, y, direction, gapi).start();
    }

    private static void shotgunStart(int x , int y, String direction, GAPI gapi){
        String left = null;
        String right = null;
        switch (direction){
            case"up":left = "left-up";right = "right-up";break;
            case"down":left = "left-down";right = "right-down";break;
            case"left":left = "left-down";right = "left-up";break;
            case"right":left = "right-up";right = "right-down";break;
            default:assert false;break;
        }
        new Bullet(x, y, direction, gapi).start();
        new Bullet(x, y, left, gapi).start();
        new Bullet(x, y, right, gapi).start();
    }


    private static Util make(String util, int x, int y , String direction, GAPI gapi){
        String packageName = UtilsMaker.class.getPackageName();
        try {
            Class<?> utilClass = Class.forName(packageName + "." + util);
            Util element = (Util) utilClass.getDeclaredConstructor(int.class, int.class, String.class, GAPI.class)
                    .newInstance(x,y,direction, gapi);
            return element;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
