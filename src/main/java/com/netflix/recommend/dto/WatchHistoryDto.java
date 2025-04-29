package com.netflix.recommend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchHistoryDto {
    private Long userId;
    private Long contentId;
    private LocalDateTime watchedAt; // 클라이언트에서 지정하거나, 서버에서 자동 설정 가능
}
