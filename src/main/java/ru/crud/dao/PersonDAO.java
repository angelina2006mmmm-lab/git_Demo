package ru.crud.dao;

import org.springframework.stereotype.Component;
import ru.crud.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 25, "aaaaaaaaa@mail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Tomas", 48, "bbbbbbbbbb@mail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Jonn", 22, "cccccccc@mail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Finis", 36, "ddddddddd@mail.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updateRerson){
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updateRerson.getName());
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id );
    }
}
