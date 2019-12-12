package model;

import entity.Group;
import entity.User;
import entity.YearGroup;

import java.sql.*;
import java.util.ArrayList;

public class YearGroupDao {

    public static ArrayList<YearGroup> getYearGroups()
    {
        Connection connection = Database.connectDatabase();

        try
        {
            ArrayList<YearGroup> yearGroups = new ArrayList<>();

            Statement statement = connection.createStatement();
            Statement statementTwo = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM year_group as g");

            while (resultSet.next())
            {
                ArrayList<User> users = new ArrayList<>();
                ResultSet resultSetTwo = statementTwo.executeQuery("SELECT u.id, u.name, u.firstname FROM  user as u WHERE u.id_year_group = " + resultSet.getInt("g.id"));
                while (resultSetTwo.next())
                {
                    users.add(new User(
                            resultSetTwo.getInt("u.id"),
                            resultSetTwo.getString("u.name"),
                            resultSetTwo.getString("u.firstname")
                    ));
                }
                yearGroups.add(new YearGroup(
                        resultSet.getInt("g.id"),
                        resultSet.getString("g.name"),
                        users,
                        resultSet.getDate("g.date_start"),
                        resultSet.getDate("g.date_end")
                ));
            }
            return yearGroups;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void create(YearGroup yearGroup)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO year_group (name, date_start, date_end) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            // Set parameters for query
            preparedStatement.setString(1, yearGroup.getLabel());
            preparedStatement.setDate(2, (Date) yearGroup.getDateStart());
            preparedStatement.setDate(3, (Date) yearGroup.getDateEnd());

            // Execute the query
            preparedStatement.executeUpdate();
            ResultSet idYearGroup = preparedStatement.getGeneratedKeys();
            idYearGroup.next();
            int idYearGroupLong = idYearGroup.getInt(1);
            preparedStatement.close();
            for (User user : yearGroup.getUsers()) {
                PreparedStatement preparedStatementTwo = connection.prepareStatement("UPDATE user SET id_year_group = ? WHERE id = ?");
                preparedStatementTwo.setInt(1, idYearGroupLong);
                preparedStatementTwo.setInt(2, user.getId());
                preparedStatementTwo.executeUpdate();
                preparedStatementTwo.close();
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void update(YearGroup yearGroup)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE year_group SET name = ?, date_start = ?, date_end = ? WHERE id = ?");

            // Set parameters for query
            preparedStatement.setString(1, yearGroup.getLabel());
            preparedStatement.setDate(2, (Date) yearGroup.getDateStart());
            preparedStatement.setDate(3, (Date) yearGroup.getDateEnd());
            preparedStatement.setInt(4, yearGroup.getId());

            // Execute the query
            preparedStatement.executeUpdate();
            preparedStatement.close();

            PreparedStatement preparedStatementTwo = connection.prepareStatement("UPDATE user SET id_year_group = ? WHERE id_year_group = ?");

            // Set parameters for query
            preparedStatementTwo.setString(1, null);
            preparedStatementTwo.setInt(2, yearGroup.getId());

            // Execute the query
            preparedStatementTwo.executeUpdate();
            preparedStatementTwo.close();



            for (User user : yearGroup.getUsers()) {
                PreparedStatement preparedStatementThree = connection.prepareStatement("UPDATE user SET id_year_group = ? WHERE id = ?");
                preparedStatementThree.setInt(1, yearGroup.getId());
                preparedStatementThree.setInt(2, user.getId());
                preparedStatementThree.executeUpdate();
                preparedStatementThree.close();
            }


        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void remove(YearGroup yearGroup)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            PreparedStatement preparedStatementTwo = connection.prepareStatement("UPDATE user SET id_year_group = ? WHERE id_year_group = ?");

            // Set parameters for query
            preparedStatementTwo.setString(1, null);
            preparedStatementTwo.setInt(2, yearGroup.getId());

            // Execute the query
            preparedStatementTwo.executeUpdate();
            preparedStatementTwo.close();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM year_group WHERE id = ?");
            preparedStatement.setInt(1, yearGroup.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
