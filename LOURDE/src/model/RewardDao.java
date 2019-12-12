package model;

import entity.Reward;
import model.Database;

import java.sql.*;
import java.util.ArrayList;

public class RewardDao {

    public static ArrayList<entity.Reward> getRewards()
    {
        Connection connection = Database.connectDatabase();

        try
        {
            ArrayList<entity.Reward> rewards = new ArrayList<Reward>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reward INNER JOIN level ON reward.id_level = level.id");

            while (resultSet.next())
            {
                rewards.add(new entity.Reward(
                        resultSet.getInt("reward.id"),
                        resultSet.getString("reward.label"),
                        resultSet.getInt("cost_xp"),
                        resultSet.getInt("nb_available"),
                        resultSet.getString("description"),
                        resultSet.getInt("id_level"),
                        resultSet.getString("level.label"),
                        resultSet.getString("skin")
                ));
            }

            statement.close();

            return rewards;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer create(Reward reward)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reward (label, cost_xp, nb_available, description, id_level) VALUES (?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            // Set parameters for query
            preparedStatement.setString(1, reward.getLabel());
            preparedStatement.setInt(2, reward.getCostXp());
            preparedStatement.setInt(3, reward.getNbAvailable());
            preparedStatement.setString(4, reward.getDescription());
            preparedStatement.setInt(5, reward.getIdLevel());

            // Execute the query
            preparedStatement.executeUpdate();
            ResultSet idReward = preparedStatement.getGeneratedKeys();
            idReward.next();
            Integer idRewardLong = idReward.getInt(1);
            String url = "images/rewards/"+idRewardLong+".jpg";
            preparedStatement.close();


            PreparedStatement preparedStatementTwo = connection.prepareStatement("UPDATE reward SET skin = ? WHERE id = ?");
            preparedStatementTwo.setString(1, url);
            preparedStatementTwo.setInt(2, idRewardLong);
            preparedStatementTwo.executeUpdate();
            return idRewardLong;

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(Reward reward)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reward SET label = ?, cost_xp = ?, nb_available = ?, description = ?, id_level = ?, skin = ?  WHERE id = ?");

            // Set parameters for query
            preparedStatement.setString(1, reward.getLabel());
            preparedStatement.setInt(2, reward.getCostXp());
            preparedStatement.setInt(3, reward.getNbAvailable());
            preparedStatement.setString(4, reward.getDescription());
            preparedStatement.setInt(5, reward.getIdLevel());
            preparedStatement.setString(6, reward.getSkin());
            preparedStatement.setInt(7, reward.getId());

            // Execute the query
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void remove(Reward reward)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reward WHERE id = ?");
            preparedStatement.setInt(1, reward.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
