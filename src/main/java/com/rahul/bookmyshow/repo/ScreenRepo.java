package com.rahul.bookmyshow.repo;

import com.rahul.bookmyshow.model.Movie;
import com.rahul.bookmyshow.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepo extends JpaRepository<Screen, Long> {
    List<Screen> findByTheaterId(Long theaterId);
}