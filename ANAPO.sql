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
