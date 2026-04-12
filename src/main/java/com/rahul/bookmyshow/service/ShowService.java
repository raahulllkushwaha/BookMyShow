package com.rahul.bookmyshow.service;

import com.rahul.bookmyshow.dto.*;
import com.rahul.bookmyshow.exception.ResourceNotFoundException;
import com.rahul.bookmyshow.model.Movie;
import com.rahul.bookmyshow.model.Screen;
import com.rahul.bookmyshow.model.Show;
import com.rahul.bookmyshow.model.ShowSeat;
import com.rahul.bookmyshow.repo.MovieRepo;
import com.rahul.bookmyshow.repo.ScreenRepo;
import com.rahul.bookmyshow.repo.ShowRepo;
import com.rahul.bookmyshow.repo.ShowSeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {
    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private ScreenRepo screenRepo;

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private ShowSeatRepo showSeatRepo;


    public ShowDto createShow(ShowDto showDto){
        Show show = new Show();
        Movie movie = movieRepo.findById(showDto.getMovie().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found: "));

        Screen screen = screenRepo.findById(showDto.getScreen().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found: "));

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());

        Show savedShow = showRepo.save(show);

        List<ShowSeat> availableSeats =
                showSeatRepo.findByShowIdAndStatus(savedShow.getId(), ("AVAILABLE"));
        return mapToDto(savedShow, availableSeats);
    }

    public ShowDto getShowById(Long id){
        Show show = showRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: "+ id));
        List<ShowSeat> availableSeats =
                showSeatRepo.findByShowIdAndStatus(show.getId(), ("AVAILABLE"));
        return mapToDto(show, availableSeats);
    }

    public List<ShowDto> getAllShow(){
        List<Show> shows = showRepo.findAll();
        return shows.stream()
                .map(show -> {
                    List<ShowSeat> availableSeats = showSeatRepo.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show, availableSeats);
                })
                .collect(Collectors.toList());
    }
    public List<ShowDto> getShowByMovie(Long movieId){
        List<Show> shows = showRepo.findByMovieId(movieId);
        return shows.stream()
                .map(show -> {
                    List<ShowSeat> availableSeats = showSeatRepo.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show, availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowByMovieAndCity(Long movieId, String city){
        List<Show> shows = showRepo.findByMovie_IdAndScreen_Theater_City(movieId, city);
        return shows.stream()
                .map(show -> {
                    List<ShowSeat> availableSeats = showSeatRepo.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show, availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowByDateRange(LocalDateTime startTime, LocalDateTime endTime){
        List<Show> shows = showRepo.findByStartTimeBetween(startTime, endTime);
        return shows.stream()
                .map(show -> {
                    List<ShowSeat> availableSeats = showSeatRepo.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show, availableSeats);
                })
                .collect(Collectors.toList());
    }
    private ShowDto mapToDto(Show show, List<ShowSeat> availableSeats){
        ShowDto showDto = new ShowDto();
        showDto.setId(show.getId());
        showDto.setStartTime(show.getStartTime());
        showDto.setEndTime(show.getEndTime());

        showDto.setMovie(new MovieDto(
                show.getMovie().getId(),
                show.getMovie().getTitle(),
                show.getMovie().getDescription(),
                show.getMovie().getLanguage(),
                show.getMovie().getGenre(),
                show.getMovie().getDurationMins(),
                show.getMovie().getReleaseDate(),
                show.getMovie().getPosterUrl()
        ));

        TheaterDto theaterDto = new TheaterDto(
                show.getScreen().getTheater().getId(),
                show.getScreen().getTheater().getName(),
                show.getScreen().getTheater().getAddress(),
                show.getScreen().getTheater().getCity(),
                show.getScreen().getTheater().getTotalScreen()
        );

        showDto.setScreen(new ScreenDto(
                show.getScreen().getId(),
                show.getScreen().getName(),
                show.getScreen().getTotalSeats(),
                theaterDto
        ));

       List<ShowSeatDto> seatDtos =  availableSeats.stream()
                .map(seat -> {
                    ShowSeatDto seatDto = new ShowSeatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setStatus(seat.getStatus());
                    seatDto.setPrice(seat.getPrice());

                    SeatDto baseSeatDto = new SeatDto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBasePrice());

                    seatDto.setSeat(baseSeatDto);
                    return seatDto;
                }).collect(Collectors.toList());
       showDto.setAvailableSeats(seatDtos);
       return showDto;
    }
}
