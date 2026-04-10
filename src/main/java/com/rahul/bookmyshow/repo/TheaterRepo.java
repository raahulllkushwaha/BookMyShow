package com.rahul.bookmyshow.repo;

import com.rahul.bookmyshow.model.ShowSeat;
import com.rahul.bookmyshow.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepo extends JpaRepository<Theater, Long> {
    List<Theater> findByCity(String city);

}