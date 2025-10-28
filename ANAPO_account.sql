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