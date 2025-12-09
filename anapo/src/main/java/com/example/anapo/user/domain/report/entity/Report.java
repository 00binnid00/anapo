package com.example.anapo.user.domain.report.entity;

import com.example.anapo.admin.domain.entity.ReportAction;
import com.example.anapo.user.enums.ReportTargetType;
import com.example.anapo.user.enums.ReportType;
import com.example.anapo.user.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report")
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account reporter;  // 신고자

    @ManyToOne
    private Account reported;  // 피신고자 (없을 수 있음)

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Enumerated(EnumType.STRING)
    private ReportTargetType targetType;

    private Long targetId;      // 게시글/댓글/메시지의 PK

    private String description; // 신고 내용

    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL)
    private ReportAction action; // 관리자 처리 정보 연결

    private LocalDateTime createdAt;
}
