package examples;

public class Dog {
    String name;
    String breed;
    double weight;

    String getDog() {
        String a = "Name : " + name + " Breed : " + breed + " Weight : " + weight;
        System.out.println(a);
        return a;
    }
}
