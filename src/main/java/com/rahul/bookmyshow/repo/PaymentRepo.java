package com.rahul.bookmyshow.repo;

import com.rahul.bookmyshow.model.Movie;
import com.rahul.bookmyshow.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);


}
