USE ana;

SELECT * FROM account;

CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,              -- PK

    user_id VARCHAR(255) NOT NULL UNIQUE,              -- 아이디 (중복 불가)
    user_name VARCHAR(255) NOT NULL,                   -- 이름
    user_number VARCHAR(255) NOT NULL,                 -- 전화번호
    user_password VARCHAR(255) NOT NULL,               -- 비밀번호
    birth VARCHAR(255) NOT NULL,                       -- 생년월일
    sex VARCHAR(255) NOT NULL                          -- 성별
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE hospital (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,             -- PK

    hos_name VARCHAR(255) NOT NULL,                   -- 병원명
    hos_address VARCHAR(255) NOT NULL,                -- 병원 주소
    hos_number VARCHAR(255) NOT NULL,                 -- 병원 전화번호
    hos_time VARCHAR(255) NOT NULL                    -- 병원 운영시간

    hos_lat DOUBLE NOT NULL,                         -- 병원 위도
    hos_lng DOUBLE NOT NULL                          -- 병원 경도
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,        -- 예약 고유 번호 (PK)

    reser_date DATETIME NOT NULL,                -- 예약 날짜 및 시간
    department VARCHAR(255) NOT NULL,            -- 진료 과목
    reser_yes_no BOOLEAN NOT NULL,               -- 예약 여부 (true/false)

    acc BIGINT NOT NULL,                         -- 예약자 (Account 외래키)
    hos BIGINT NOT NULL,                         -- 병원 (Hospital 외래키)

    CONSTRAINT fk_reservation_account
        FOREIGN KEY (acc) REFERENCES account(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_reservation_hospital
        FOREIGN KEY (hos) REFERENCES hospital(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE clinic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,           -- PK

    clinic_date DATETIME NOT NULL,                  -- 진료 일자
    diagnosis VARCHAR(255) NOT NULL,                -- 진단명
    treatment TEXT NOT NULL,                        -- 진료 내용
    doctor_name VARCHAR(255) NOT NULL,              -- 담당 의사 이름

    reservation_id BIGINT NOT NULL,                 -- 예약 정보 FK
    acc BIGINT NOT NULL,                            -- 회원 (Account FK)
    hos BIGINT NOT NULL,                            -- 병원 (Hospital FK)

    CONSTRAINT fk_medical_record_reservation
        FOREIGN KEY (reservation_id) REFERENCES reservation(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_medical_record_account
        FOREIGN KEY (acc) REFERENCES account(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_medical_record_hospital
        FOREIGN KEY (hos) REFERENCES hospital(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,                  -- PK

    title VARCHAR(255) NOT NULL,                           -- 제목
    content TEXT NOT NULL,                                 -- 내용
    writer VARCHAR(100),                                   -- 작성자
    hospital_id BIGINT NULL,                               -- 병원별 공지일 때 병원 ID 참조
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,         -- 등록일
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,       -- 수정일

    CONSTRAINT fk_notice_hospital                          -- 병원 FK 제약조건 이름
        FOREIGN KEY (hospital_id) REFERENCES hospital(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE chat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,            -- 메시지 PK
    
    sender_id BIGINT NOT NULL,                       -- 보낸 사람 (사용자 or 상담자)
    receiver_id BIGINT NOT NULL,                     -- 받는 사람
    message TEXT NOT NULL,                           -- 메시지 내용
    sent_at DATETIME DEFAULT CURRENT_TIMESTAMP,      -- 보낸 시각

    CONSTRAINT fk_chat_sender
        FOREIGN KEY (sender_id) REFERENCES account(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_chat_receiver
        FOREIGN KEY (receiver_id) REFERENCES account(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
