package com.netflix.recommend.controller;

import com.netflix.recommend.dto.req.ReviewDetailReqDto;
import com.netflix.recommend.dto.res.GroupDetailResDto;
import com.netflix.recommend.dto.res.GroupElementResDto;
import com.netflix.recommend.dto.res.ReviewPageResDto;
import com.netflix.recommend.dto.res.VideoElementResDto;
import com.netflix.recommend.service.GroupService;
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

@Tag(name = "그룹")
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @Operation(
            summary = "그룹 생성 API",
            description = "그룹 이름을 query param으로 입력하면 그룹이 생성되고, 자동 참여된다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<String> postGroup(@Parameter(hidden = true) Authentication authentication, @RequestParam String name) {
        groupService.postGroup(Long.valueOf(authentication.getName()), name);
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/{group-id}")
    @Operation(
            summary = "그룹 참여 API",
            description = "이미 참여한 그룹이 아닐 시, 그룹에 참여한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<String> joinGroup(@Parameter(hidden = true) Authentication authentication, @PathVariable("group-id") Long groupId) {
        groupService.joinGroup(Long.valueOf(authentication.getName()), groupId);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/{group-id}")
    @Operation(
            summary = "그룹 정보 조회 API (인증 X)",
            description = "그룹 id, 이름, 멤버를 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<GroupDetailResDto> getGroupDetail(@PathVariable("group-id") Long groupId) {
        return ResponseEntity.ok(groupService.getGroupDetail(groupId));
    }

    @GetMapping
    @Operation(
            summary = "그룹 검색 API (인증 X)",
            description = "그룹을 키워드로 검색한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<List<GroupElementResDto>> searchGroupList(@RequestParam String keyword) {
        return ResponseEntity.ok(groupService.searchGroupList(keyword));
    }

    @GetMapping("/{group-id}/reviews")
    @Operation(
            summary = "그룹 리뷰 조회 API",
            description = "그룹 리뷰를 페이지네이션을 통해 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<ReviewPageResDto> getGroupReview(@Parameter(hidden = true) Authentication authentication,
                                                           @PathVariable("group-id") Long groupId,
                                                           @RequestParam Integer page, @RequestParam Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(groupService.getReviewWithPaging(Long.valueOf(authentication.getName()), groupId, pageRequest));
    }

    @PostMapping("/{group-id}/reviews")
    @Operation(
            summary = "그룹 리뷰 등록 API",
            description = "특정 그룹의 리뷰를 등록한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<String> postGroupReview(@Parameter(hidden = true) Authentication authentication,
                                                  @PathVariable("group-id") Long groupId,
                                                  @RequestBody ReviewDetailReqDto reviewDetailReqDto) {
        groupService.postReview(Long.valueOf(authentication.getName()), groupId, reviewDetailReqDto);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 그룹 리스트 조회 API",
            description = "내가 참여한 그룹의 리스트를 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<List<GroupElementResDto>> getMyGroupList(@Parameter(hidden = true) Authentication authentication) {
        return ResponseEntity.ok(groupService.getMyGroupList(Long.valueOf(authentication.getName())));
    }

    @GetMapping("{group-id}/histories")
    @Operation(
            summary = "그룹 시청 기록 조회 API",
            description = "특정 그룹의 시청 기록을 조회할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content())
            }
    )
    public ResponseEntity<List<VideoElementResDto>> getMyGroupList(@PathVariable("group-id") Long groupId) {
        return ResponseEntity.ok(groupService.getGroupHistory(groupId));
    }
}
