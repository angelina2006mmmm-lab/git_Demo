package box;

public class BoxWeight extends Box{
        private double weight;



    public BoxWeight(double wight, double lenght, double height, double weight) {
        super(wight, lenght, height);
        this.weight = weight;
    }

    public BoxWeight(double size, double weight) {
        super(size);
        this.weight = weight;
    }



    @Override
    public void showInfo() {
        System.out.println("Length: " + getLenght() + ", Width: " + getWight() + ", Height: " + getHeight() + ", Weight: " + weight + ", Volume: " + getVolume());
    }
}
