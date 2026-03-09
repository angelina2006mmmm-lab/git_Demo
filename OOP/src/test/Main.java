package test;


import box.Box;
import box.BoxWeight;

public class Main {
    public static void main(String[] args) {
        Box box = new Box(10);


        BoxWeight weight = new BoxWeight(10,10,10,15);
        weight.showInfo();
    }
}
