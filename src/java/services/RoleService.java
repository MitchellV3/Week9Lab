/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import models.Role;
import dataaccess.RoleDB;

/**
 *
 * @author Mitchell
 */
public class RoleService {
    public List<Role> getAll() {
        return RoleDB.getAll();
    }

    public Role get(int roleID) {
        return RoleDB.get(roleID);
    }

    
}
