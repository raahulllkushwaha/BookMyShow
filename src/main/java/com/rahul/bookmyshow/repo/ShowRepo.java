package com.rahul.bookmyshow.repo;

import com.rahul.bookmyshow.model.Screen;
import com.rahul.bookmyshow.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<Show, Long> {
    List<Show> findByMovieId(Long movieId);
    List<Show> findByScreenId(Long screenId);
    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
//    List<Show> findByMovie_IdAndScreen_Theater_City(Long movieId, String city);
}