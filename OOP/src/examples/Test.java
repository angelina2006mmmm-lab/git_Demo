package examples;

public class Test {

    int length;
    int width;


    Test(int length, int width){
        this.length = length;
        this.width = width;
    }

    void sqare(int length, int width) {
        this.length = length;
        this.width = width;
    }

    double getV(){
        return length*width;
    }

    void getR(){
        System.out.println(getV());

    }
}