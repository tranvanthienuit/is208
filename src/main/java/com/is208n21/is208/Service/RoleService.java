package com.is208n21.is208.Service;



import com.is208n21.is208.Entity.Model.Role;
import com.is208n21.is208.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

}
