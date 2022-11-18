package com.sku.TravelF.domain.repository;

import com.sku.TravelF.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserid(String userid);
    User findByUseridAndPassword(String userid, String password);
}
