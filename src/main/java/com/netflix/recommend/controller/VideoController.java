package com.netflix.recommend.controller;

import com.netflix.recommend.dto.res.VideoDetailResDto;
import com.netflix.recommend.dto.res.VideoElementResDto;
import com.netflix.recommend.dto.res.VideoPageResDto;
import com.netflix.recommend.enums.Genre;
import com.netflix.recommend.enums.Rate;
import com.netflix.recommend.enums.Type;
import com.netflix.recommend.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "영상")
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/{video-id}/histories")
    @Operation(
            summary = "시청 기록 등록 API",
            description = "특정 영상에 대한 시청 기록을 등록할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<String> postHistory(@Parameter(hidden = true) Authentication authentication, @PathVariable("video-id") Long videoId) {
        videoService.postHistory(Long.valueOf(authentication.getName()), videoId);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/{video-id}")
    @Operation(
            summary = "영상 정보 조회 API (인증 X)",
            description = "특정 영상에 대한 세부 정보를 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<VideoDetailResDto> getVideoDetail(@PathVariable("video-id") Long videoId) {
        return ResponseEntity.ok(videoService.getVideoDetail(videoId));
    }

    @GetMapping
    @Operation(
            summary = "영상 리스트 필터링 API (인증 X)",
            description = "영상을 장르, 연령, 타입, 검색어에 따라 필터링하여 조회할 수 있다. 결과는 페이징되어 반환한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<VideoPageResDto> getVideoListWithFiltering(@RequestParam(required = false) Genre genre,
                                                                     @RequestParam(required = false) Rate rate,
                                                                     @RequestParam(required = false) Type type,
                                                                     @RequestParam(required = false) String keyword,
                                                                     @RequestParam Integer page, @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(videoService.getVideoListWithFiltering(genre, rate, type, keyword, pageRequest));
    }
}
