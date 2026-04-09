package com.rahul.bookmyshow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    private String description;


    private String language;


    private String genre;

    private String durationMins;

    private LocalDateTime releaseDate;

    private String posterUrl;

    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Show> shows;


}
