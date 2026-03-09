package inheritance;

public class Lion extends CatFamily{

    public Lion() {
        super(4,2,true);
    }

    public void eat(){
        super.eat();
        System.out.println("еду");
    }
}
