package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection conn = null;

        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sample_db", "postgres", "postgres");
        if (conn != null) {
            return conn;
        } else {
            throw new RuntimeException("Connection error");
        }
    }

    public static List<Person> getAllPersons() throws ClassNotFoundException, SQLException {
        List<Person> persons = new ArrayList<>();

        String query = "SELECT * FROM public.persons";

        Statement stmt = getConnection().createStatement();
        ResultSet results = stmt.executeQuery(query);

        while (results.next()) {
            Person person = new Person();

            person.setPersonId(results.getInt("person_id"));
            person.setFirstName(results.getString("first_name"));
            person.setLastName(results.getString("last_name"));
            person.setEmail(results.getString("email"));
            person.setCity(results.getString("city"));

            persons.add(person);
        }

        return persons;
    }

    public static Person getPerson(int id) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM public.persons WHERE id = ?";

        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet results = pstmt.executeQuery();

        while (results.next()) {
            Person person = new Person();

            person.setPersonId(results.getInt("person_id"));
            person.setFirstName(results.getString("first_name"));
            person.setLastName(results.getString("last_name"));
            person.setEmail(results.getString("email"));
            person.setCity(results.getString("city"));

            return person;
        }

        throw new RuntimeException("Person doesn't exist"); // customer doesn't exist

    }

    public static void createPerson(Person person) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.persons(person_id, first_name, last_name, email, city) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = getConnection().prepareStatement(sql);

        // replace these ?
        pstmt.setInt(1, person.getPersonId());
        pstmt.setString(2, person.getFirstName());
        pstmt.setString(3, person.getLastName());
        pstmt.setString(4, person.getEmail());
        pstmt.setString(5, person.getCity());

        pstmt.execute();
    }

    public static void updatePerson(Person person) throws ClassNotFoundException, SQLException {

        String query = "UPDATE public.persons SET first_name=?, last_name=?, email=?, city=? WHERE id = ?";

        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setString(1, person.getFirstName());
        pstmt.setString(2, person.getLastName());
        pstmt.setString(3, person.getEmail());
        pstmt.setString(4, person.getCity());
        pstmt.setInt(5, person.getPersonId());

        pstmt.execute();
    }

    public static void deletePerson(int id) throws ClassNotFoundException, SQLException {
        String query = "DELETE FROM public.persons WHERE id = ?";

        PreparedStatement pstmt = getConnection().prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.execute();
    }
}
