package com.rahul.bookmyshow.repo;

import com.rahul.bookmyshow.model.ShowSeat;
import com.rahul.bookmyshow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existingByEmail(String email);

}