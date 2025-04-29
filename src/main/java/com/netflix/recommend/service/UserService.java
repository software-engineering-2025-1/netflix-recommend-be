package com.netflix.recommend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.netflix.recommend.dto.UserDto;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDto(user);
    }

    public UserDto createUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPreferredGenres(dto.getPreferredGenres());
        user.setPreferredCountries(dto.getPreferredCountries());
        user.setAgeRatingPreference(dto.getAgeRatingPreference());
        return toDto(userRepository.save(user));
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getPreferredGenres(),
                user.getPreferredCountries(),
                user.getAgeRatingPreference());
    }
}
