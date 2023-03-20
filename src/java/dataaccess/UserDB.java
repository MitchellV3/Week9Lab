/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import models.Role;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mitchell
 */
public class UserDB {
    public static List<User> getAll() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        try {
            String query = "SELECT * FROM user";
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();

            while (rs.next()) {
                String email = rs.getString(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String password = rs.getString(4);
                int roleID = rs.getInt(5);
                Role role = new Role(roleID);
                User user = new User(email, firstName, lastName, password, role);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static User get(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        try {
            String query = "SELECT * FROM user WHERE email = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String password = rs.getString(4);
                int roleID = rs.getInt(5);
                Role role = new Role(roleID);
                user = new User(email, firstName, lastName, password, role);
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insert(User user) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO user (email, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getRoleID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new Exception("Error updating user " + user.toString() + " in database");
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void update(User user) throws Exception{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE user SET first_name = ?, last_name = ?, password = ?, role = ? WHERE email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleID());
            ps.setString(5, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new Exception("Error updating user " + user.toString() + " in database: " + user.getRole().getRoleID());
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void delete(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM user WHERE email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
