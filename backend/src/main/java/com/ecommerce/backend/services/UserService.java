package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.mapper.PatchMapper;
import com.ecommerce.backend.mapper.UserMapper;
import com.ecommerce.backend.models.Favorite;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.models.RefreshToken;
import com.ecommerce.backend.models.User;
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

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
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
