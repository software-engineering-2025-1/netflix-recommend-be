package com.netflix.recommend.service.impl;

import com.netflix.recommend.dto.req.UserDetailReqDto;
import com.netflix.recommend.dto.res.UserDetailResDto;
import com.netflix.recommend.entity.PreferGenre;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.PreferGenreRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PreferGenreRepository preferGenreRepository;

    @Override
    @Transactional
    public void postUserDetail(Long userId, UserDetailReqDto userDetailReqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));

        user.updateDetail(
                userDetailReqDto.getName(),
                userDetailReqDto.getAge(),
                userDetailReqDto.getCountry()
        );

        try {
            preferGenreRepository.saveAll(
                    userDetailReqDto.getGenres().stream().map(
                            (genre -> PreferGenre.builder().genre(genre).user(user).build())
                    ).toList()
            );
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_DETAIL);
        }
    }

    @Override
    @Transactional
    public void updateUserDetail(Long userId, UserDetailReqDto userDetailReqDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));

        user.updateDetail(
                userDetailReqDto.getName(),
                userDetailReqDto.getAge(),
                userDetailReqDto.getCountry()
        );

        preferGenreRepository.deleteByUser(user);
        preferGenreRepository.flush();

        preferGenreRepository.saveAll(
                userDetailReqDto.getGenres().stream().map(
                        genre -> PreferGenre.builder().genre(genre).user(user).build()
                ).toList()
        );
    }

    @Override
    public UserDetailResDto getUserDetail(Long userId) {
        User user = userRepository.findUserDetailByIdFetch(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANNOT_FIND_USER));

        return UserDetailResDto.from(user);
    }
}