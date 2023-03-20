package com.is208n21.is208.Controller.Admin;



import com.is208n21.is208.Entity.Model.Role;
import com.is208n21.is208.Entity.Model.User;
import com.is208n21.is208.Service.RoleService;
import com.is208n21.is208.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Map;

@RestController
@Transactional
public class AdminUser {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @PostMapping(value = {"/admin/{userId}"})
    public ResponseEntity<?> editeRole(@PathVariable("userId") String userId, @RequestBody Map<String, Object> roleName) {
        User user = userService.findUserByUserId(userId);

        Role role = roleService.findRoleByName(roleName.get("roleName").toString());
        user.setRole(role);
        userService.saveUser(user);
        return new ResponseEntity<>("successful", HttpStatus.OK);
    }
}
