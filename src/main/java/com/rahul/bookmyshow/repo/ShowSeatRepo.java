package com.rahul.bookmyshow.repo;

import com.rahul.bookmyshow.model.Show;
import com.rahul.bookmyshow.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowSeatRepo extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowId(Long showId);
    List<ShowSeat> findByShowIdAndStatus(Long showId, String status);
}