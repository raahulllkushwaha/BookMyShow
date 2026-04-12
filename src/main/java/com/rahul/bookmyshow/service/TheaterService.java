package com.rahul.bookmyshow.service;

import com.rahul.bookmyshow.dto.MovieDto;
import com.rahul.bookmyshow.dto.TheaterDto;
import com.rahul.bookmyshow.exception.ResourceNotFoundException;
import com.rahul.bookmyshow.model.Movie;
import com.rahul.bookmyshow.model.Theater;
import com.rahul.bookmyshow.repo.TheaterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepo theaterRepo;

    public TheaterDto createTheater(TheaterDto theaterDto){
        Theater theater = mapToEntity(theaterDto);
        Theater savedTheater = theaterRepo.save(theater);
        return mapToDto(savedTheater);
    }

    public TheaterDto getTheaterById(Long id){
        Theater theater = theaterRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + id));
        return mapToDto(theater);
    }

    private List<TheaterDto> getAllTheater(){
        List<Theater> theaters = theaterRepo.findAll();
        return theaters.stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }
    private List<TheaterDto> getByCity(String city){
        List<Theater> theaters = theaterRepo.findByCity(city);
        return theaters.stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }
//update
    public TheaterDto updateTheater(Long id, TheaterDto theaterDto){
        Theater theater = theaterRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id."));
        theater.setId(theaterDto.getId());
        theater.setName(theaterDto.getName());
        theater.setAddress(theaterDto.getAddress());
        theater.setCity(theaterDto.getCity());
        theater.setTotalScreen(theaterDto.getTotalScreens());

       Theater updateTheater = theaterRepo.save(theater);
        return mapToDto(updateTheater);
    }
//end
    private TheaterDto mapToDto(Theater theater) {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(theater.getId());
        theaterDto.setName(theater.getName());
        theaterDto.setAddress(theater.getAddress());
        theaterDto.setCity(theater.getCity());
        theaterDto.setTotalScreens(theater.getTotalScreen());
        return theaterDto;
    }

    private Theater mapToEntity(TheaterDto theaterDto){
        Theater theater = new Theater();
        theater.setId(theaterDto.getId());
        theater.setName(theaterDto.getName());
        theater.setAddress(theaterDto.getAddress());
        theater.setCity(theaterDto.getCity());
        theater.setTotalScreen(theaterDto.getTotalScreens());
        return theater;
    }
}
