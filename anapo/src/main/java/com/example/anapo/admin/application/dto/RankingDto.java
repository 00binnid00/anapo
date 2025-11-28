package com.example.anapo.admin.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingDto {
    private Long hospitalId;
    private String hospitalName;
    private Long count;       // 예약수, 즐겨찾기수, 재방문자 수
    private Double revisitRate;  // 재방문율(%) → 예약/즐겨찾기 랭킹에서는 null
}