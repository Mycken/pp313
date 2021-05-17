package com.example.pp313.service;

import com.example.pp313.model.Role;
import com.example.pp313.model.User;
import com.example.pp313.model.UserDTO;

import java.util.List;


public interface UserService {
    void add(User user);
    void save(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    UserDTO getDTOById(long id);
    void deleteById(long id);
    UserDTO getUserByUserName(String username);
    List<Role> getAllRoles();
    List<Role> getRolesByUserId (Long id);
    List<UserDTO> getAllDTO();
    Role getRoleByDTOrole(String r);
    void saveUserfromDTO(UserDTO userDTO);

}
