package com.is208n21.is208.Controller.Admin_Seller;



import com.is208n21.is208.Entity.UserList;
import com.is208n21.is208.Service.RoleService;
import com.is208n21.is208.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
public class User {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @DeleteMapping(value = {"/admin/xoa-user/{userId}", "/admin/xoa-user", "/seller/xoa-user/{userId}", "/seller/xoa-user"})
    public ResponseEntity<String> removeUser(@PathVariable(value = "userId", required = false) String userId) throws Exception {
        if (userService.findUserByUserId(userId) != null) {
            if (!userService.countUser().equals(1)) {
                userService.removeUserByUserId(userId);
                return new ResponseEntity<>("successful", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/admin/xem-tat-ca-user/{page}", "/admin/xem-tat-ca-user", "/seller/xem-tat-ca-user/{page}", "/seller/xem-tat-ca-user"})
    public ResponseEntity<UserList> getAllUser(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        UserList userList = new UserList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 12);
        Page<com.is208n21.is208.Entity.Model.User> userPage = userService.getAllUser(pageable);
        List<com.is208n21.is208.Entity.Model.User> userPageContent = userPage.getContent();
        if (userPageContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            userList.setUserList(userPageContent);
            userList.setCount(userService.getAllUsers().size());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

    @PostMapping(value = {"/admin/tim-user", "/seller/tim/user"})
    public ResponseEntity<?> findUser(@RequestBody Map<String, Object> keyword) {
        if (keyword != null)
            return new ResponseEntity<>(userService.findUser(keyword.get("keyword").toString()), HttpStatus.OK);
        return new ResponseEntity<>("Không có người dùng nào cả", HttpStatus.OK);
    }
}
