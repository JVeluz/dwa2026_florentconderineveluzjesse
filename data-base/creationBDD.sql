#Création de la bdd
CREATE DATABASE dwa;

#Changement de bdd
use dwa;

### CREATION DES TABLES #############################################
create table user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    pseudo VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    age INT DEFAULT 0,
    country VARCHAR(20),
    credits INT DEFAULT 0
);
create table pixel(
    id INT AUTO_INCREMENT PRIMARY KEY,
    x INT NOT NULL,
    y INT NOT NULL,
    price INT DEFAULT 5,
    color_hexadecimal CHAR(6) DEFAULT 'FFFFFF',
    oldness INT DEFAULT 0,
    owner_id INT DEFAULT NULL,
    FOREIGN KEY(owner_id) REFERENCES user(id)
);
create table bonus(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25),
    description VARCHAR(255),
    price INT
);
create table user_has_bonus(
    user_id INT,
    bonus_id INT,
    PRIMARY KEY(user_id, bonus_id),
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(bonus_id) REFERENCES bonus(id)
);
CREATE TABLE skill_state (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bonus_id INT,
    current_tick INT,

    user_id INT,

    FOREIGN KEY (bonus_id) REFERENCES bonus(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

### PROCEDURE CREEE PAR IA ##########################################
DELIMITER $$

CREATE PROCEDURE init_grid()
BEGIN
    DECLARE x INT DEFAULT 0;
    DECLARE y INT DEFAULT 0;
    SET x = 0;
    WHILE x < 50 DO
        SET y = 0;

        WHILE y < 50 DO

            INSERT INTO pixel (x, y, price, color_hexadecimal, oldness, owner_id)
            VALUES (y, x, 5, 'FFFFFF', 0, NULL);

            SET y = y + 1;
        END WHILE;

        SET x = x + 1;
    END WHILE;
END$$

DELIMITER ;

#Initialisation de la grille
call init_grid();

### SUPPRESSION DES TABLES ##########################################

DROP TABLE user_has_bonus;
DROP TABLE skill_state;
DROP TABLE pixel;
DROP TABLE bonus;
DROP TABLE user;

### SUPPRESSION DE LA PROCEDURE #####################################

DROP PROCEDURE init_grid;

### INSERTION DES BONUS #############################################

INSERT INTO bonus (name, description, price) VALUES ('Bon pinceau', '3x3', 100);
INSERT INTO bonus (name, description, price) VALUES ('Grand pinceau', '5x5', 500);
INSERT INTO bonus (name, description, price) VALUES ('Bonus passif 1', '+1 crédits par tick', 100);
INSERT INTO bonus (name, description, price) VALUES ('Bonus passif 2', '+2 crédits par tick', 500);
INSERT INTO
    bonus (name, description, price)
VALUES (
        'Double click',
        'Multiplie les gain du click par deux',
        1000
    );

### TESTS ###########################################################

INSERT INTO user (pseudo, password, age, country, credits)
VALUES ('SYSTEM', '', 0, NULL, 0);

SELECT * from pixel LIMIT 2500;

select * from user;