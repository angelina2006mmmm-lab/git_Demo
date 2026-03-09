package box;

public class Box {
    private double wight;
    private double lenght;
    private double height;
    private int n = 2;

    public Box(double wight, double lenght, double height) {
        this.wight = wight;
        this.lenght = lenght;
        this.height = height;
    }

    public Box(double size){
        this(size,size,size);
    }

    private void setDimenes(double lenght, double wight, double height){
        this.lenght = lenght;
        this.wight = wight;
        this.height = height;

    }


    public int compare(Box another){
        double currentVolume = getVolume();
        double anotherVolume = another.getVolume();
        int result;

        if (currentVolume > anotherVolume){
            result = 1;
        } else if (currentVolume < anotherVolume) {
            result = -1;
        } else {
            result = 0;
        }
        return result;
    }

    public Box copy(){
        Box box = new Box(this.lenght, this.wight,this.height);
        return box;
    }

    public Box increase(){
        Box box = new Box(this.lenght * n, this.wight * n,this.height * n);
        return box;
    }

    public double getVolume(){
        return lenght*wight*height;
    }

    public void showVolume(){
        System.out.println(getVolume());
    }

    public double getWight() {
        return wight;
    }

    public double getLenght() {
        return lenght;
    }

    public double getHeight() {
        return height;
    }


    protected void showInfo(){
        System.out.println((
                "Length: " + lenght +
                        ", Width: " + wight +
                        ", Height: " + height +
                        ", Volume: " + getVolume()
        ));
    }
}