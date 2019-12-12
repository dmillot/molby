package model;

import entity.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserDao {
    public static ArrayList<User> getUsers()
    {
        Connection connection = Database.connectDatabase();

        try
        {
            ArrayList<User> users = new ArrayList<User>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user as u INNER JOIN link_user_level as lul ON u.id = lul.id_user INNER JOIN level as l ON lul.id_level = l.id LEFT JOIN link_user_group as lug ON u.id = lug.id_user LEFT JOIN `group` as g ON (lug.id_group = g.id AND g.date_start <= CURRENT_DATE AND g.date_end >= CURRENT_DATE)LEFT JOIN year_group as yg ON yg.id = u.id_year_group GROUP BY u.id");
            while (resultSet.next())
            {
                users.add(new User(
                        resultSet.getInt("u.id"),
                        resultSet.getString("u.picture"),
                        resultSet.getString("u.name"),
                        resultSet.getString("u.firstname"),
                        resultSet.getString("u.mail"),
                        resultSet.getInt("u.xp"),
                        resultSet.getInt("l.id"),
                        resultSet.getString("l.label"),
                        resultSet.getInt("g.id"),
                        resultSet.getString("g.name"),
                        resultSet.getInt("yg.id"),
                        resultSet.getString("yg.name")
                ));
            }

            statement.close();
            return users;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<User> getAvailableUser(LocalDate dateStart, LocalDate dateEnd){
        Connection connection = Database.connectDatabase();
        try
        {
            ArrayList<User> users = new ArrayList<User>();
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user u inner join link_user_group lg ON lg.id_user = u.id INNER JOIN `group` g ON lg.id_group = g.id WHERE g.date_start NOT BETWEEN ? AND ? AND g.date_end NOT BETWEEN ? AND ? AND ? NOT BETWEEN g.date_start AND g.date_end AND ? NOT BETWEEN g.date_start AND g.date_end");
            Statement statementTwo = connection.createStatement();

            preparedStatement.setDate(1, Date.valueOf(dateStart));
            preparedStatement.setDate(2, Date.valueOf(dateEnd));
            preparedStatement.setDate(3, Date.valueOf(dateStart));
            preparedStatement.setDate(4, Date.valueOf(dateEnd));
            preparedStatement.setDate(5, Date.valueOf(dateStart));
            preparedStatement.setDate(6, Date.valueOf(dateEnd));

            ResultSet resultSet = preparedStatement.executeQuery();;
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("u.id"),
                        resultSet.getString("u.name"),
                        resultSet.getString("u.firstname")
                ));
            }
            preparedStatement.close();
            ResultSet resultSetTwo = statementTwo.executeQuery("SELECT * from user U LEFT JOIN link_user_group lg ON lg.id_user = u.id WHERE id_user IS NULL");
            while (resultSetTwo.next()) {
                users.add(new User(
                        resultSetTwo.getInt("u.id"),
                        resultSetTwo.getString("u.name"),
                        resultSetTwo.getString("u.firstname")
                ));
            }
            statementTwo.close();
            return users;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }




    public static ArrayList<User> getWithoutYearGroup(LocalDate dateStart, LocalDate dateEnd){
        Connection connection = Database.connectDatabase();
        try
        {
            ArrayList<User> users = new ArrayList<User>();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user u inner join year_group yg ON yg.id = u.id_year_group WHERE yg.date_start NOT BETWEEN ? AND ? AND yg.date_end NOT BETWEEN ? AND ? AND ? NOT BETWEEN yg.date_start AND yg.date_end AND ? NOT BETWEEN yg.date_start AND yg.date_end");
            preparedStatement.setDate(1, Date.valueOf(dateStart));
            preparedStatement.setDate(2, Date.valueOf(dateEnd));
            preparedStatement.setDate(3, Date.valueOf(dateStart));
            preparedStatement.setDate(4, Date.valueOf(dateEnd));
            preparedStatement.setDate(5, Date.valueOf(dateStart));
            preparedStatement.setDate(6, Date.valueOf(dateEnd));

            ResultSet resultSet = preparedStatement.executeQuery();;
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("u.id"),
                        resultSet.getString("u.name"),
                        resultSet.getString("u.firstname")
                ));
            }

            preparedStatement.close();
            // Prepare an INSERT query
            Statement statement = connection.createStatement();
            ResultSet resultSetTwo = statement.executeQuery("SELECT * FROM user AS U WHERE id_year_group IS NULL");
            while (resultSetTwo.next()) {
                users.add(new User(
                        resultSetTwo.getInt("u.id"),
                        resultSetTwo.getString("u.name"),
                        resultSetTwo.getString("u.firstname")
                ));
            }
            statement.close();
            return users;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(User user)
    {
        Connection connection = Database.connectDatabase();

        try
        {

            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET xp = ? WHERE id = ?");

            // Set parameters for query
            preparedStatement.setInt(1, user.getExperience());
            preparedStatement.setInt(2, user.getId());

            // Execute the query
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
