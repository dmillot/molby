package model;

import entity.Group;
import entity.Reward;
import entity.User;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;

public class GroupDao {

    public static ArrayList<Group> getGroups()
    {
        Connection connection = Database.connectDatabase();

        try
        {
            ArrayList<Group> groups = new ArrayList<>();

            Statement statement = connection.createStatement();
            Statement statementTwo = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `group` as g ORDER BY date_start DESC");

            while (resultSet.next())
            {
                ArrayList<User> users = new ArrayList<>();
                ResultSet resultSetTwo = statementTwo.executeQuery("SELECT u.id, u.name, u.firstname, lg.is_leader, lg.nb_points_awarded FROM link_user_group as lg INNER JOIN user as u ON u.id = lg.id_user WHERE lg.id_group = " + resultSet.getInt("g.id"));
                while (resultSetTwo.next())
                {
                    users.add(new User(
                            resultSetTwo.getInt("u.id"),
                            resultSetTwo.getString("u.name"),
                            resultSetTwo.getString("u.firstname"),
                            resultSetTwo.getInt("lg.is_leader") != 0,
                            resultSetTwo.getInt("lg.nb_points_awarded")
                    ));
                }
                groups.add(new Group(
                        resultSet.getInt("g.id"),
                        resultSet.getString("g.name"),
                        users,
                        resultSet.getInt("g.available_xp"),
                        resultSet.getInt("g.available_points_to_give"),
                        resultSet.getInt("g.total_xp"),
                        resultSet.getDate("g.date_start"),
                        resultSet.getDate("g.date_end")
                ));
            }
            return groups;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void create(Group group)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `group` (name, available_xp, available_points_to_give, total_xp, date_start, date_end) VALUES (?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            // Set parameters for query
            preparedStatement.setString(1, group.getLabel());
            preparedStatement.setInt(2, group.getAvailableXp());
            preparedStatement.setInt(3, group.getPointsToGive());
            preparedStatement.setInt(4, group.getTotalXp());
            preparedStatement.setDate(5, group.getDateStart());
            preparedStatement.setDate(6, group.getDateEnd());

            // Execute the query
            preparedStatement.executeUpdate();

            ResultSet idGroup = preparedStatement.getGeneratedKeys();
            idGroup.next();
            int idGroupLong = idGroup.getInt(1);
            preparedStatement.close();
            for (User user : group.getUsers()) {
                PreparedStatement preparedStatementTwo = connection.prepareStatement("INSERT INTO link_user_group (id_user, id_group, is_leader, nb_points_awarded) VALUES (?, ?, ?, ?)");

                preparedStatementTwo.setInt(1, user.getId());
                preparedStatementTwo.setInt(2, idGroupLong);
                preparedStatementTwo.setInt(3, (user.isLeader()) ? 1 : 0);
                preparedStatementTwo.setInt(4, user.getPointsAwarded());

                preparedStatementTwo.executeUpdate();
                preparedStatementTwo.close();
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void update(Group group)
    {
        Connection connection = Database.connectDatabase();

        try
        {
            // Prepare an INSERT query
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `group` SET name = ?, available_xp = ?, available_points_to_give = ?, total_xp = ?, date_start = ?, date_end = ? WHERE id = ?");

            // Set parameters for query
            preparedStatement.setString(1, group.getLabel());
            preparedStatement.setInt(2, group.getAvailableXp());
            preparedStatement.setInt(3, group.getPointsToGive());
            preparedStatement.setInt(4, group.getTotalXp());
            preparedStatement.setDate(5, group.getDateStart());
            preparedStatement.setDate(6, group.getDateEnd());
            preparedStatement.setInt(7, group.getId());

            // Execute the query
            preparedStatement.executeUpdate();
            preparedStatement.close();

            PreparedStatement preparedStatementTwo = connection.prepareStatement("DELETE FROM link_user_group WHERE id_group = ?");
            preparedStatementTwo.setInt(1, group.getId());
            preparedStatementTwo.executeUpdate();
            preparedStatementTwo.close();



            for (User user : group.getUsers()) {
                PreparedStatement preparedStatementThree = connection.prepareStatement("INSERT INTO link_user_group (id_user, id_group, is_leader, nb_points_awarded) VALUES (?, ?, ?, ?)");
                preparedStatementThree.setInt(1, user.getId());
                preparedStatementThree.setInt(2, group.getId());
                preparedStatementThree.setInt(3, (user.isLeader()) ? 1 : 0);
                preparedStatementThree.setInt(4, user.getPointsAwarded());
                preparedStatementThree.executeUpdate();
                preparedStatementThree.close();
            }



        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void remove(Group group)
    {
        Connection connection = Database.connectDatabase();

        try
        {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM link_user_group WHERE id_group = ?");
            preparedStatement.setInt(1, group.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            PreparedStatement preparedStatementTwo = connection.prepareStatement("DELETE FROM `group` WHERE id = ?");
            preparedStatementTwo.setInt(1, group.getId());

            preparedStatementTwo.executeUpdate();
            preparedStatementTwo.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
