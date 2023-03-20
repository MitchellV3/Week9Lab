/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.Role;
import models.User;
import dataaccess.UserDB;

import java.util.List;

/**
 *
 * @author Mitchell
 */
public class UserService {

    public static void insert(String email, String firstName, String lastName, String password, Role role) throws Exception {
        User user = new User(email, firstName, lastName, password);
        user.setRole(role);
        UserDB.insert(user);
    }

    public static void update(String email, String firstName, String lastName, String password, Role role) throws Exception {
        User user = UserDB.get(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(role);
        UserDB.update(user);
    }

    public static void delete(String email) {
        User user = UserDB.get(email);
        UserDB.delete(user);
    }

    public static User get(String email) {
        return UserDB.get(email);
    }

    public static List<User> getAll() {
        return UserDB.getAll();
    }



}
