package examples;

public class Monstr {

    int eyes;
    int legs;
    int arms;

    public Monstr(int eyes, int arms, int legs) {
        this.eyes = eyes;
        this.arms = arms;
        this.legs = legs;
    }

    Monstr(int count){
        this(count,count,count);
    }

    Monstr(){
        this(2);
    }

    void voice(int count, String word){
        for (int i = 0; i < count; i++){
            System.out.println(word);
        }
    }

    void voice(int count){
        for (int i = 0; i < count; i++){
            System.out.println("Grrrrrrrr...");
        }
    }

    void voice(){
        voice(1);
    }
}
