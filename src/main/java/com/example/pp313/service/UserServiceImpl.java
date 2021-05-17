package com.example.pp313.service;

import com.example.pp313.model.Role;
import com.example.pp313.model.User;
import com.example.pp313.model.UserDTO;
import com.example.pp313.repository.RoleRepository;
import com.example.pp313.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userDao, RoleRepository roleRepository) {
        this.userRepository = userDao;
        this.roleRepository = roleRepository;

    }

    @Transactional
    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void save(User user) { userRepository.save(user); }

    @Override
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles(){return  roleRepository.findAll();}

    @Override
    public List<Role> getRolesByUserId(Long id) {
        System.out.println(roleRepository.findAll());
        return null;
    }

    @Override
    public List<UserDTO> getAllDTO() {
        List <User> userList = userRepository.findAll();
        List <UserDTO> userDTOList = new ArrayList<>();
        for (User user: userList){
            UserDTO uDTO = new UserDTO(
                    user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), this.simpleRole(user.getId()));
            userDTOList.add(uDTO);
        }
        return userDTOList;
    }

    @Override
    public Role getRoleByDTOrole(String r) {
        return roleRepository.findAll().stream().filter(ro ->ro.getRole().equals(r)).findAny().get();
    }

    @Override
    public void saveUserfromDTO(UserDTO userDTO) {
        User user = userRepository.findById((userDTO.getId())).orElse(new User());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        List<Role> listRole = new ArrayList<>();
        for(String role: userDTO.getRoles()) {listRole.add(this.getRoleByDTOrole(role));}
        user.setRoles(listRole);
        userRepository.save(user);
    }

    public List<String> simpleRole(Long id){
        List<String> simpleRole = new ArrayList<>();
        User user = userRepository.findById(id).get();
        List <Role> userRoles = user.getRoles();
        for (Role r: userRoles){
            simpleRole.add(r.getRole());
        }
        return simpleRole;
    }

    @Override
    public User getUserById(long id) { return userRepository.findById(id).get();  }

    @Override
    public UserDTO getDTOById(long id) { return  fromUserToDTO(userRepository.findById(id).get()); }

    @Override
    public UserDTO getUserByUserName(String username) {
        List<User> users = userRepository.findAll();
        Optional<User> user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny();
        User userToDTO = user.get();
        return fromUserToDTO(userToDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findAll();
        Optional<User> user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny();
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return User.fromUser(user.get());
    }

    public UserDTO fromUserToDTO (User user){
        UserDTO uDTO = new UserDTO(
                user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getEmail(), this.simpleRole(user.getId()));
        return uDTO;
    }
}
