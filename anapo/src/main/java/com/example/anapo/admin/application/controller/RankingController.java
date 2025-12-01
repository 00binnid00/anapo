package com.example.anapo.admin.application.controller;

import com.example.anapo.admin.application.dto.RankingDto;
import com.example.anapo.admin.application.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/hospital-rank")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    
    // 예약 많은 순
    @GetMapping("/reservation")
    public List<RankingDto> getRankByReservation() {
        return rankingService.getRankByReservation();
    }

    // 즐겨찾기 많은 순
    @GetMapping("/bookmark")
    public List<RankingDto> getRankByBookmark() {
        return rankingService.getRankByBookmark();
    }

    // 재방문율이 높은 순
    @GetMapping("/revisit")
    public List<RankingDto> getRankByRevisit() {
        return rankingService.getRankByRevisitRate();
    }
}
