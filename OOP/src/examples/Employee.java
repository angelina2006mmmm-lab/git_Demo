package examples;

public class Employee {

    String name;
    String post;
    double salary;

    Employee(String name, String post, double salary){
        this.post = post;
        this.name = name;
        this.salary = salary;
    }

    void getСonclusion(){
        System.out.println(name + ", " + post + ", " + salary + " рублей");
    }
}
