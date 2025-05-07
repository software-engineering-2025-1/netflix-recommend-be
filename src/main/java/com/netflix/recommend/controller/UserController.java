package com.netflix.recommend.controller;

import com.netflix.recommend.dto.req.UserDetailReqDto;
import com.netflix.recommend.dto.res.UserDetailResDto;
import com.netflix.recommend.dto.res.UserElementResDto;
import com.netflix.recommend.dto.res.VideoElementResDto;
import com.netflix.recommend.service.FollowService;
import com.netflix.recommend.service.RecommendService;
import com.netflix.recommend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FollowService followService;
    private final RecommendService recommendService;

    @PostMapping("/me")
    @Operation(
            summary = "내 정보 등록 API",
            description = "이름, 나이, 국가, 선호 장르를 초기 설정할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<String> postMyDetail(@Parameter(hidden = true) Authentication authentication, @RequestBody UserDetailReqDto userDetailReqDto) {
        userService.postUserDetail(Long.valueOf(authentication.getName()), userDetailReqDto);
        return ResponseEntity.ok("성공");
    }

    @PutMapping("/me")
    @Operation(
            summary = "내 정보 수정 API",
            description = "이름, 나이, 국가, 선호 장르를 수정할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<String> putMyDetail(@Parameter(hidden = true) Authentication authentication, @RequestBody UserDetailReqDto userDetailReqDto) {
        userService.updateUserDetail(Long.valueOf(authentication.getName()), userDetailReqDto);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회 API",
            description = "id, 이름, 나이, 국가, 선호 장르, 시청 기록을 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<UserDetailResDto> getMyDetail(@Parameter(hidden = true) Authentication authentication) {
        return ResponseEntity.ok(userService.getUserDetail(Long.valueOf(authentication.getName())));
    }

    @GetMapping("/{user-id}")
    @Operation(
            summary = "사용자 정보 조회 API (인증 X)",
            description = "id, 이름, 나이, 국가, 선호 장르, 시청 기록을 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<UserDetailResDto> getUserDetail(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(userService.getUserDetail(userId));
    }

    @PostMapping("/{user-id}/follows")
    @Operation(
            summary = "팔로우 등록 API",
            description = "특정 사용자를 팔로우할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<String> postFollow(@Parameter(hidden = true) Authentication authentication, @PathVariable("user-id") Long userId) {
        followService.postFollow(Long.valueOf(authentication.getName()), userId);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/{user-id}/followers")
    @Operation(
            summary = "팔로워 조회 API",
            description = "특정 사용자의 팔로워 리스트를 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<List<UserElementResDto>> getFollowers(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(followService.getFollowerList(userId));
    }

    @GetMapping("/{user-id}/followings")
    @Operation(
            summary = "팔로잉 조회 API",
            description = "특정 사용자의 팔로잉 리스트를 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<List<UserElementResDto>> getFollowings(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(followService.getFollowingList(userId));
    }

    @GetMapping("/me/recommend")
    @Operation(
            summary = "개인 추천 영상 상위 10개 리스트 조회 API",
            description = "개인 선호에 기반한 추천 영상의 상위 10개 리스트를 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<List<VideoElementResDto>> getRecommendedVideosForIndividual(@Parameter(hidden = true) Authentication authentication) {
        return ResponseEntity.ok(recommendService.recommendForIndividual(Long.valueOf(authentication.getName())));
    }
}
