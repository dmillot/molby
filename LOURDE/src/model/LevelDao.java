package model;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import entity.*;

public class LevelDao {

    public static ArrayList<Level> getLevels()
    {

        Connection connection = Database.connectDatabase();
        try
        {
            ArrayList<entity.Level> levels = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM level");

            while (resultSet.next())
            {
                levels.add(new entity.Level(
                        resultSet.getInt("id"),
                        resultSet.getString("label"),
                        resultSet.getString("description"),
                        resultSet.getInt("cost_xp"),
                        resultSet.getInt("required_xp"),
                        resultSet.getString("skin")
                ));
            }
            statement.close();

            return levels;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public static Integer create(entity.Level level)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO level (label, description, cost_xp, required_xp) VALUES (?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            // Set parameters for query
            preparedStatement.setString(1, level.getLabel());
            preparedStatement.setString(2, level.getDescription());
            preparedStatement.setInt(3, level.getCostXp());
            preparedStatement.setInt(4, level.getRequiredXp());

            // Execute the query
            preparedStatement.executeUpdate();
            ResultSet idLevel = preparedStatement.getGeneratedKeys();
            idLevel.next();
            Integer idLevelLong = idLevel.getInt(1);
            String url = "images/levels/"+idLevelLong+".jpg";
            preparedStatement.close();


            PreparedStatement preparedStatementTwo = connection.prepareStatement("UPDATE level SET skin = ? WHERE id = ?");
            preparedStatementTwo.setString(1, url);
            preparedStatementTwo.setInt(2, idLevelLong);
            preparedStatementTwo.executeUpdate();
            return idLevelLong;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(entity.Level level)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE level SET label = ?, description = ?, cost_xp = ?, required_xp = ?, skin = ? WHERE id = ?");

            // Set parameters for query
            preparedStatement.setString(1, level.getLabel());
            preparedStatement.setString(2, level.getDescription());
            preparedStatement.setInt(3, level.getCostXp());
            preparedStatement.setInt(4, level.getRequiredXp());
            preparedStatement.setString(5, level.getSkin());
            preparedStatement.setInt(6, level.getId());

            // Execute the query
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void remove(entity.Level level)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM level WHERE id = ?");
            preparedStatement.setInt(1, level.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
