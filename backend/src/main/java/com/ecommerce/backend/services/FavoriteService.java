package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.FavoriteMapper;
import com.ecommerce.backend.models.Favorite;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.payload.FavoriteRequestBody;
import com.ecommerce.backend.repository.FavoriteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final UserService userService;
    private final FavoriteMapper favoriteMapper;

    public List<Favorite> getFavorites() {
        return favoriteRepository.findAll();
    }

    public Favorite getFavoriteById(Integer id) {
        return favoriteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Favorito não encontrado"));
    }

    public Favorite createFavorite(FavoriteRequestBody favoriteRequestBody) {
        Favorite favorite = favoriteMapper.mapToFavorite(favoriteRequestBody);
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Integer id) {
        Favorite favorite = this.getFavoriteById(id);
        favoriteRepository.delete(favorite);
    }
}
