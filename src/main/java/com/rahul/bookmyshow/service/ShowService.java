package com.rahul.bookmyshow.service;

import com.rahul.bookmyshow.dto.MovieDto;
import com.rahul.bookmyshow.dto.ShowDto;
import com.rahul.bookmyshow.dto.ShowSeatDto;
import com.rahul.bookmyshow.dto.TheaterDto;
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

import java.util.List;

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
        return
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
    }
}
