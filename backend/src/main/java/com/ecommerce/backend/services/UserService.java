package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.mapper.PatchMapper;
import com.ecommerce.backend.mapper.UserMapper;
import com.ecommerce.backend.models.*;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.payload.PasswordRequestBody;
import com.ecommerce.backend.payload.UserPostRequestBody;

import com.ecommerce.backend.payload.UserPatchRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final PatchMapper patchMapper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserPostRequestBody userPostRequestBody) {
        User newUser = userMapper.mapToUser(userPostRequestBody);
        return userRepository.save(newUser);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("ID de usuário não encontrado"));
    }

    public User updateUser(UserPatchRequestBody userPatchRequestBody, Integer id) {
        User user = getUserById(id);
        User patchUser = userMapper.mapToUserPatch(userPatchRequestBody);
        patchMapper.mapToPatchRequest(user, patchUser);
        return userRepository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public void updateUserRefreshToken(User user, RefreshToken token) {
        user.setToken(token);
        userRepository.save(user);
    }

    public List<Favorite> getUsersFavorite(Integer id) {
        User user = this.getUserById(id);
        return user.getFavoriteList();
    }

    public List<Orders> getUsersOrders(int userId) {
        User user = this.getUserById(userId);
        return user.getUserOrders();
    }

    public List<Comment> getCommentsByUser(Integer id) {
        User user = this.getUserById(id);
        return user.getComments();
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateUserPassword(PasswordRequestBody passwordRequestBody, Integer id) {
        User user = getUserById(id);
        if (passwordRequestBody.getEmail().equals(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(passwordRequestBody.getPassword()));
        } else {
            throw new BadRequestException("Email não corresponde ao ID do usuário");
        }
        userRepository.save(user);
    }
}
