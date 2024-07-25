package com.springboot.assignment.business.service.impl;

import com.springboot.assignment.auth.exception.APIException;
import com.springboot.assignment.auth.repository.RoleRepository;
import com.springboot.assignment.business.service.UserService;
import com.springboot.assignment.model_structure.entity.Role;
import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.auth.repository.UserRepository;
import com.springboot.assignment.utility.dao.GenericDao;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private  GenericDao genericDao;
    private PasswordEncoder encoder;
    public UserServiceImpl(GenericDao genericDao,PasswordEncoder encode) {
        this.genericDao = genericDao;
        this.encoder=encode;
    }


    /**
     * Will fetch the user by id
     *
     * @param userId The I'd of the user that needs to be fetched
     * @return User Object
     * @throws Exception Not found exception if the Id provided is not present
     */
    @Override
    public User getUserById(Long userId) throws Exception {
        String query="from User where id='" + userId + "' WHERE isDelete='N' ";
        List<User> userList = genericDao.findCustomList( query);
        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return  null;
//        throw new NotFoundUserExceptions.UserNotFound("User not found");
    }



    /**
     * Will create a user, and it's access with respect to the role
     * in the database after checking validations from the validation config
     *
     * @param user - User object that needs to be saved
     * @return I'd of the user created
       * @see private void saveAccessControlMasterByUser(User user)
     */
    @Override
    public User registerUser(User user) throws Exception {

         if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(encoder.encode(user.getPassword()));
            }
        validateUsername(user);


        user.setRoles(user.getRoles());
        user.setIsDeactivate("N");
        user.setIsDelete("N");
        user = (User) genericDao.save(user);

        return user;
    }

    private Long getUserCountByField(String fieldName, String fieldValue) throws Exception {
        String countQuery = "SELECT COUNT(id) from User where " + fieldName + "='" + fieldValue + "' and isDelete='N'";
        return genericDao.countCustomList(countQuery);
    }

    private void validateUsername(User user) throws Exception {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST,"username is mandatory");
        }
        if (getUserCountByField("username", user.getUsername()) > 0) {
            throw new APIException(HttpStatus.BAD_REQUEST,"User with this username already exists");
        }
    }



    @Override
    public void update(User user) throws Exception {
        if (user.getId() == null || user.getId().equals(0L)) {
            throw new APIException(HttpStatus.BAD_REQUEST,"Bad Request");
        }
        updateObject(user);
    }

    private User updateObject( User user) throws Exception {
        User userOld = getUserById(user.getId());
        if (user.getName() != null && !user.getName().isEmpty() && !user.getName().equalsIgnoreCase(userOld.getName())) {
            userOld.setName(user.getName());

        }
        if (user.getEmail() != null && !user.getEmail().isEmpty() && !user.getEmail().equalsIgnoreCase(userOld.getEmail())) {
            userOld.setEmail(user.getEmail());
        }

        if (user.getUsername() != null && !user.getUsername().isEmpty() && !user.getUsername().equalsIgnoreCase(userOld.getUsername())) {
            userOld.setUsername(user.getUsername());
        }

        genericDao.update(userOld);
        return userOld;
    }




    /**
     * Changes password by comparing the existing password. If the existing password matches
     * then the new password in encoded and updated
     *
     * @param user User which has existing, new password and id
     * @return SUCCESS if password is changes else ERROR
     */
    @Override
    public String changePassword(User user) throws Exception {
        String newPassword = "";
        String changeStatus = "";
        String currentPassword = "";

        User exisitingUser = getUserByUsername(user.getUsername());

        newPassword = encoder.encode(user.getNewPassword());
        currentPassword = user.getCurrentPassword();
        if (exisitingUser != null && newPassword != null
                && encoder.matches(currentPassword, exisitingUser.getPassword())) {
            exisitingUser.setPassword(newPassword);
            genericDao.save(exisitingUser);
            changeStatus = "SUCCESS";
        } else {
            changeStatus = "ERROR";
            throw new APIException(HttpStatus.BAD_REQUEST,"MISMATCH! Current Password wrong!!! ");
        }
        return changeStatus;
    }

    /**
     * Gets user by username
     *
     * @param username username of the user
     * @return User object
     */
    @Override
    public User getUserByUsername(String username) throws Exception {
        List<User> userList = genericDao.findCustomList( "from User where username='" + username + "' ");
//        if (userList.isEmpty()) {
//            throw new UsernameNotFoundException("No user exists with this username!");
//        }
        return userList.get(0);
    }

    /**
     * Fetches all users except those with the role of admin
     *
     * @param user user with fields which you want to filter
     * @return list of users
     * @throws Exception Runtime exception if any occurs
     */
    @Override
    public List<User> getUserList(User user) throws Exception {
        String query = " FROM User u";
        query = queryCrieteria(query, user);
        query += " GROUP BY u.id Order by u.id Desc";
        return (List<User>) genericDao.findCustomList(query);
    }

    private String queryCrieteria(String query, User user) {
        query += "WHERE u.isDelete='N' ";

        if (user.getIsDeactivate() != null && !user.getIsDeactivate().isEmpty()) {
            query += " AND  u.isDeactivate='" + user.getIsDeactivate() + "'";
        }

        if (user.getSearchValue() != null && !user.getSearchValue().isEmpty()) {
            query += " and (" +
                    " u.id LIKE '%" + user.getSearchValue() + "%' " +
                    " OR u.name LIKE '%" + user.getSearchValue() + "%' " +
                    " OR u.email LIKE '%" + user.getSearchValue() + "%' " +
                    ")  ";
        }


        return query;
    }

    /**
     * Fetches all user count except those with the role of admin
     *
     * @return Count of users who are not admin
     * @throws Exception Runtime exception if any occurs
     */
    @Override
    public Long getUserCount(User user) throws Exception {
        String query = " SELECT COUNT(DISTINCT(u.id)) FROM User u";
        query = queryCrieteria(query, user);
        return genericDao.countCustomList(query);
    }

    @Override
    public void changeStatus(Long id) throws Exception {
        String query = "UPDATE User SET isDeactivate = CASE WHEN isDeactivate = 'Y' THEN 'N' ELSE 'Y' END WHERE id = " + id;
        genericDao.customUpdate(query);
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        String query = "SELECT isDeactivate FROM User WHERE id=" + id;
        String activeState = genericDao.findSingleColumnValue(query);

        if (activeState != null && !activeState.isEmpty()) {
            if (activeState.equals("N")) {
                throw new APIException(HttpStatus.BAD_REQUEST,"Can't delete Active User");
            }
        }
        query = "UPDATE User SET isDelete = 'Y', isDeactivate='Y',username=null WHERE id = " + id;
        genericDao.customUpdate(query);
    }




}
