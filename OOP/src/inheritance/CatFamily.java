package inheritance;

public class CatFamily {

    private int legs;
    private int eyes;
    private boolean canEatPerson;


    public CatFamily(int eyes, int legs, boolean canEatPerson) {
        this.eyes = eyes;
        this.legs = legs;
        this.canEatPerson = canEatPerson;
    }

    public void eat(){
        System.out.print("Кушаю ");
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setEyes(int eyes) {
        this.eyes = eyes;
    }

    public void setCanEatPerson(boolean canEatPerson) {
        this.canEatPerson = canEatPerson;
    }

    public int getLegs() {
        return legs;
    }

    public int getEyes() {
        return eyes;
    }

    public boolean isCanEatPerson() {
        return canEatPerson;
    }
}
