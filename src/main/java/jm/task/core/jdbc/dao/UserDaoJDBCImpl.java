package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getMySQLConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE user ( " +
                    "id int , " +
                    "name varchar(255), " +
                    "lastName varchar(255), " +
                    "age int )";
            statement.executeUpdate(sql);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void dropUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getMySQLConnection();
            Statement statement = connection.createStatement();
            String sql = "drop table if exists user ";
            statement.execute(sql);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO user(name, lastName, age) VALUES(?,?,?)";
        Connection connection = null;
        try {
            connection = Util.getMySQLConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с " + name + " добавлен в базу данных.");

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {}

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "select name, lastname, age from user";
        Connection connection = null;

        try {
            connection = Util.getMySQLConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));

                list.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        Connection connection = null;

        try {
            connection = Util.getMySQLConnection();
            String sql = "truncate table user";
            Statement statement = connection.createStatement();
            statement.execute(sql);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
