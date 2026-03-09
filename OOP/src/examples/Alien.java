package examples;

public class Alien {

    int antennas;
    int eyes;
    int tentacles;

    public Alien(int antennas, int eyes, int tentacles) {
        this.antennas = antennas;
        this.eyes = eyes;
        this.tentacles = tentacles;
    }

    Alien(int count){
        this(count,count,count);
    }

    Alien(){
        this(2);
    }

    void speak(int count, String word){
        for (int i = 0; i < count; i++)
            System.out.println(word);
    }

    void speak(int count){
        for (int i = 0; i < count; i++)
            System.out.println("Blublublu.....");
    }

    void speak(){
        speak(1);
    }
}
