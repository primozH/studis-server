CREATE DATABASE `studis` COLLATE `utf8_slovenian_ci`;

CREATE USER 'studis'@'localhost' IDENTIFIED BY 'studisIS';
GRANT EXECUTE, SELECT, ALTER, CREATE, DELETE, INSERT, REFERENCES, UPDATE  ON *.* TO 'studis'@'localhost';