package com.rahul.bookmyshow.dto;

import com.rahul.bookmyshow.model.Screen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private List<Screen> totalScreens;

}
