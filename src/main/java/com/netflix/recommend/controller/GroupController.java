package com.netflix.recommend.controller;

import com.netflix.recommend.dto.res.GroupDetailResDto;
import com.netflix.recommend.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}
