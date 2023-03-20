/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import models.Role;

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
public class RoleDB {
    public static List<Role> getAll() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        List<Role> roles;
        try {
            String query = "SELECT * FROM role";
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            roles = new ArrayList<>();
            while (rs.next()) {
                int roleID = rs.getInt(1);
                String roleName = rs.getString(2);
                Role role = new Role(roleID, roleName);
                roles.add(role);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return roles;
    }

    public static Role get(int roleID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        try {
            String query = "SELECT * FROM role WHERE role_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();
            Role role = null;
            while (rs.next()) {
                String roleName = rs.getString(2);
                role = new Role(roleID, roleName);
            }
            return role;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
}
