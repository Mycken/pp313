package com.example.pp313.conroller;


import com.example.pp313.model.Role;
import com.example.pp313.model.User;
import com.example.pp313.model.UserDTO;
import com.example.pp313.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
//    private List <Map<String,String>> userv = new ArrayList<>(){{
//        add(new HashMap<String,String>() {{ put("id", "1"); put("firstName", "pier"); }});
//        add(new HashMap<String,String>() {{ put("id", "2"); put("firstName", "serg"); }});
//        add(new HashMap<String,String>() {{ put("id", "3"); put("firstName", "vano"); }});
//    }};
//    @GetMapping("userv")
//    public List<Map<String,String>> list(){return userv;}

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    public UserController() {}



    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping(path ="/api/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<UserDTO> getAll() {return userService.getAllDTO();}

    @GetMapping(path ="/api/admin/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserDTO getUserById(@PathVariable Long id) {
        return this.userService.getDTOById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserDTO call (Authentication auth) {
        return userService.getUserByUserName(auth.getName());
    }

    @GetMapping("/api/users/{username}")
    public @ResponseBody UserDTO viewUserProfile(@PathVariable String username, Authentication auth) {
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if(roles.contains("ROLE_ADMIN") || auth.getName().equals(username)) {
            return userService.getUserByUserName(username);
        }
        return null;
    }

    @RequestMapping(value = "/api/users/update", method=RequestMethod.POST)
    public void updateUserJsonResult(@RequestBody UserDTO userDTO){userService.saveUserfromDTO(userDTO);}

    @RequestMapping("/api/admin/user-delete/{id}")
    public void deleteUser(@PathVariable Long id){ userService.deleteById(id);}

    @PostMapping("/api/admin/user-create")
    public void createUser(@RequestBody UserDTO userDTO){userService.saveUserfromDTO(userDTO);}

}
