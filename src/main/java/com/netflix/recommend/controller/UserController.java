package com.netflix.recommend.controller;

import com.netflix.recommend.dto.req.UserDetailReqDto;
import com.netflix.recommend.dto.res.UserDetailResDto;
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

@Tag(name = "사용자")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}
