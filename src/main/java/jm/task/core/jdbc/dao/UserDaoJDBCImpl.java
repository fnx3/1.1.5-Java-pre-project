package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "lastName VARCHAR(50)," +
                "age INT" +
                ")";

        try (Statement statement = Util.getConnection().createStatement() ) {
            statement.execute(query);
            System.out.print("Таблица создана " + '\n');
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists users";

        try (Statement statement = Util.getConnection().createStatement() ) {
            statement.execute(query);
            System.out.println("Таблица удалена" + '\n');
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into users (name, lastName, age) values (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с имененем " + name + " успешно добавлен в таблицу");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id =" + id;

        try (Statement statement = Util.getConnection().createStatement() ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() {
        String query = "select * from users";
        List<User> userList = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement() ) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next() ) {
                userList.add(new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4) ) );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String query = "truncate table users";

        try (Statement statement = Util.getConnection().createStatement() ) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
