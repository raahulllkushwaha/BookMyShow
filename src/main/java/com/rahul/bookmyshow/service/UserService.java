package com.rahul.bookmyshow.service;

import com.rahul.bookmyshow.dto.UserDto;
import com.rahul.bookmyshow.exception.ResourceNotFoundException;
import com.rahul.bookmyshow.model.User;
import com.rahul.bookmyshow.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserDto createUser(UserDto userDto){
        User user = mapToEntity(userDto);
        User savedUser = userRepo.save(user);
        return mapToDto(savedUser);
    }
    public UserDto getUserById(Long id){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not find with this id: " + id));
        return mapToDto(user);
    }

    public List<UserDto> getAllUsers(){
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return user;
    }

    private UserDto mapToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }
}
