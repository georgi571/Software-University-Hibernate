CREATE DATABASE mini_orm;

CREATE TABLE mini_orm.users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(45),
    age INT,
    registration DATE
);

CREATE TABLE mini_orm.orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(55),
    date DATE
)
