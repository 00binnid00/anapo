package com.example.anapo.admin.application.service;

import com.example.anapo.admin.application.dto.RankingDto;
import com.example.anapo.admin.domain.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository repo;


    // 예약이 많은 순
    public List<RankingDto> getRankByReservation() {
        return repo.findHospitalsByReservationCount().stream()
                .map(r -> new RankingDto(
                        Long.valueOf(r[0].toString()),
                        r[1].toString(),
                        Long.valueOf(r[2].toString()),
                        null
                ))
                .toList();
    }


    // 즐겨찾기가 많은 순
    public List<RankingDto> getRankByBookmark() {
        return repo.findHospitalsByBookmarkCount().stream()
                .map(r -> new RankingDto(
                        Long.valueOf(r[0].toString()),
                        r[1].toString(),
                        Long.valueOf(r[2].toString()),
                        null
                ))
                .toList();
    }

    // 재방문율이 높은 순
    public List<RankingDto> getRankByRevisitRate() {
        return repo.findHospitalsByRevisitRate().stream()
                .map(r -> {
                    long revisitors = Long.parseLong(r[2].toString());
                    long totalUsers = Long.parseLong(r[3].toString());
                    double rate = (totalUsers == 0 ? 0 : (double) revisitors / totalUsers * 100);

                    return new RankingDto(
                            Long.valueOf(r[0].toString()),
                            r[1].toString(),
                            revisitors,
                            rate
                    );
                })
                .toList();
    }
}
