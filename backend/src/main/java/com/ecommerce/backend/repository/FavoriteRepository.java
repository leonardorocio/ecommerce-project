package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Favorite;
import com.ecommerce.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {


    List<Favorite> findByUser(User user);
}
