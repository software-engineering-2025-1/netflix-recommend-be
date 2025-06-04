package com.netflix.recommend.unit;

import com.netflix.recommend.dto.req.UserDetailReqDto;
import com.netflix.recommend.dto.res.UserDetailResDto;
import com.netflix.recommend.entity.User;
import com.netflix.recommend.enums.Country;
import com.netflix.recommend.enums.Genre;
import com.netflix.recommend.exception.CustomException;
import com.netflix.recommend.exception.ErrorCode;
import com.netflix.recommend.repository.PreferGenreRepository;
import com.netflix.recommend.repository.UserRepository;
import com.netflix.recommend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock private UserRepository userRepository;
    @Mock private PreferGenreRepository preferGenreRepository;

    @Test
    @DisplayName("사용자 정보 수정 - 정상 수정")
    void 사용자_아이디를_통해_정보를_수정할_수_있다() {
        // given
        Long userId = 1L;
        UserDetailReqDto userDetailReqDto = new UserDetailReqDto();
        ReflectionTestUtils.setField(userDetailReqDto, "name", "name1");
        ReflectionTestUtils.setField(userDetailReqDto, "age", 23);
        ReflectionTestUtils.setField(userDetailReqDto, "country", Country.SOUTH_KOREA);
        ReflectionTestUtils.setField(userDetailReqDto, "genres", Set.of(Genre.ACTION_ADVENTURE));

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        // when
        userService.updateUserDetail(userId, userDetailReqDto);

        // then
        verify(preferGenreRepository).deleteByUser(any(User.class));
        verify(preferGenreRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("사용자 정보 수정 - 존재하지 않는 사용자 예외")
    void 존재하지_않는_사용자의_정보를_수정할_경우_예외가_발생한다() {
        // given
        Long userId = 1L;
        UserDetailReqDto userDetailReqDto = new UserDetailReqDto();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            userService.updateUserDetail(userId, userDetailReqDto);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.CANNOT_FIND_USER);
    }

    @Test
    @DisplayName("사용자 정보 조회 - 정상 조회")
    void 사용자_아이디를_통해_정보를_조회할_수_있다() {
        // given
        Long userId = 1L;

        User user = new User();
        user.updateDetail("name1", 23, Country.SOUTH_KOREA);
        ReflectionTestUtils.setField(user, "genres", Set.of());
        ReflectionTestUtils.setField(user, "histories", Set.of());

        when(userRepository.findUserDetailByIdFetch(userId)).thenReturn(Optional.of(user));

        // when
        UserDetailResDto userDetail = userService.getUserDetail(userId);

        // then
        assertThat(userDetail.getName()).isEqualTo("name1");
        assertThat(userDetail.getAge()).isEqualTo(23);
        assertThat(userDetail.getCountry()).isEqualTo(Country.SOUTH_KOREA.getName());
    }

    @Test
    @DisplayName("사용자 정보 조회 - 존재하지 않는 사용자 예외")
    void 존재하지_않는_사용자의_정보를_조회할_경우_예외가_발생한다() {
        // given
        Long userId = 1L;

        when(userRepository.findUserDetailByIdFetch(userId)).thenReturn(Optional.empty());

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            userService.getUserDetail(userId);
        });

        assertThat(thrown.getErrorCode()).isEqualTo(ErrorCode.CANNOT_FIND_USER);
    }
}