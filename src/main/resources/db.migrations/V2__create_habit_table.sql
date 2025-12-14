CREATE TABLE tb_habit(

    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    priority VARCHAR(5) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status BOOLEAN,
    date DATE NOT NULL,
    user_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);