package com.compay.course.entity;

import com.compay.course.DbUtility;
import com.compay.course.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users(email,username,password) VALUES(?,?,?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE" +
            " id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users;";


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public User create(User user) {
        try (Connection conn = DbUtility.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            System.out.println("User created! ");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public User read(int userId) {
        try (Connection conn = DbUtility.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(READ_USER_QUERY);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("email"));
                user.setEmail(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void update(User user) {
        try (Connection conn = DbUtility.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            ResultSet rs = statement.executeQuery();
            System.out.println("User with id: " + user.getId() + " updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public User delete(int userId) {
        try (Connection conn = DbUtility.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(DELETE_USER_QUERY);
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            System.out.println("User width id: " + userId + " deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public User[] addToArray(User u, User[] users) {
        User[] tempUsers = Arrays.copyOf(users, users.length + 1);
        tempUsers[users.length] = u;

        try (Connection conn = DbUtility.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_USERS_QUERY);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                tempUsers = new User[]{user};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempUsers;
    }




}
