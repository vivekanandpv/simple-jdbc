package com.example;

import java.sql.SQLException;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        try {
            Person p = new Person();
            p.setPersonId(50);
            p.setFirstName("Monica");
            p.setLastName("Larsen");
            p.setEmail("monica@gmail.com");
            p.setCity("Singapore");

            Repository.createPerson(p);

            List<Person> persons = Repository.getAllPersons();
            for (Person person : persons) {
                System.out.printf("%d :: %s :: %s :: %s :: %s\n", person.getPersonId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getCity());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
