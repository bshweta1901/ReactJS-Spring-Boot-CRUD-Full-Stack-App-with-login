package com.springboot.assignment.business.controllers;


import com.springboot.assignment.auth.repository.RoleRepository;
import com.springboot.assignment.business.service.UserService;
import com.springboot.assignment.model_structure.entity.Role;
import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.payload.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    /**
     * Controller for Creating or Registering User
     *
     * @param userDTO User Json String
     * @return User Id
     * @throws Exception Runtime Exception if any
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(HttpServletRequest httpServletRequest, @RequestBody UserDTO userDTO) throws Exception {


        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = modelMapper.map(userDTO, User.class);
        for (String role : userDTO.getRole()) {
            Role roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                user.addRole(roleEntity);
            }
        }
        user = userService.registerUser(user);
        Map<String, Long> data = new HashMap<>();
        data.put("userId", user.getId());
        return ResponseEntity.ok(data);
    }

    /**
     * Get User List
     *
     * @param userDTO User Object
     * @return User List
     * @throws Exception Runtime Exception if any occurs
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest httpServletRequest, @RequestBody UserDTO userDTO) throws Exception {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = modelMapper.map(userDTO, User.class);
        userService.update(user);
        return ResponseEntity.ok(Map.of("message", "update Sucessfully"));
    }

    /**
     * Get User List
     *
     * @param user User Object
     * @return User List
     * @throws Exception Runtime Exception if any occurs
     */
    @PostMapping("/list")
    public ResponseEntity<?> list(HttpServletRequest httpServletRequest, @RequestBody User user) throws Exception {
        List<User> userList = userService.getUserList(user);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<UserDTO> list =
                userList
                        .stream()
                        .map(users -> modelMapper.map(users, UserDTO.class)).toList();

        return ResponseEntity.ok(list);
    }

    /**
     * Gets Count of Users
     *
     * @return Count of Users
     * @throws Exception Runtime Exception if any occurs
     */
    @PostMapping("/count")
    public ResponseEntity<?> getUserCount(HttpServletRequest httpServletRequest, @RequestBody User user) throws Exception {
        return ResponseEntity.ok(userService.getUserCount(user));
    }


    /**
     * Controller for getting User by Id
     *
     * @param id User Id
     * @return User Object
     * @throws Exception Runtime Exception if any occurs
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) throws Exception {
        User user = userService.getUserById(id);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO UserDTO = modelMapper.map(user, UserDTO.class);

        return ResponseEntity.ok(UserDTO);
    }


    @PostMapping("/change-state")
    public ResponseEntity<?> changeStatus(@RequestBody User user) throws Exception {
        userService.changeStatus(user.getId());
        return ResponseEntity.ok(Map.of("message", "Status Change Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "Delete User Successfully"));
    }

    





}
