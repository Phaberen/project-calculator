DROP TABLE IF EXISTS project;

CREATE TABLE project (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         description VARCHAR(200),
                         deadline DATE
);
