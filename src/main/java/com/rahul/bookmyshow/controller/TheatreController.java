package com.rahul.bookmyshow.controller;

import com.rahul.bookmyshow.dto.TheaterDto;
import com.rahul.bookmyshow.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {
    @Autowired
    private TheaterService theaterService;

    @PostMapping
    public ResponseEntity<TheaterDto> addTheatre(@Valid @RequestBody TheaterDto theaterDto) {
        return new ResponseEntity<>(theaterService.addTheater(theaterDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterDto> getTheatreById(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @GetMapping
    public ResponseEntity<List<TheaterDto>> getAllTheater() {
        return ResponseEntity.ok(theaterService.getAllTheater());
    }

}
