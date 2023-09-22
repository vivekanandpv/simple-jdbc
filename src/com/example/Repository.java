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

        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            results = statement.executeQuery(query);

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
        } finally {
            //  Resource finalization
            try {
                if (results != null) {
                    //  every close() in turn can throw an exception!
                    results.close();
                }
            } catch (Exception e) {
                //  this is provisional, replace with proper strategy
                e.printStackTrace();
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Person getPerson(int id) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM public.persons WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            results = preparedStatement.executeQuery();

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
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }

    public static void createPerson(Person person) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO public.persons(person_id, first_name, last_name, email, city) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, person.getPersonId());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setString(5, person.getCity());

            preparedStatement.execute();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updatePerson(Person person) throws ClassNotFoundException, SQLException {

        String query = "UPDATE public.persons SET first_name=?, last_name=?, email=?, city=? WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setString(4, person.getCity());
            preparedStatement.setInt(5, person.getPersonId());

            preparedStatement.execute();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deletePerson(int id) throws ClassNotFoundException, SQLException {
        String query = "DELETE FROM public.persons WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
