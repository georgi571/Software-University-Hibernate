package _8SpringData._1SpringData._02ORMFundamentals._2Exercise.entities;

import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Column;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Entity;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Id;

import java.time.LocalDate;

@Entity(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "registration")
    private LocalDate registration;

    @Column(name = "email")
    private String email;

    public User() {

    }

    public User(String username, int age, LocalDate registration) {
        this.username = username;
        this.age = age;
        this.registration = registration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("ID:%d -> Username: %s -> Age: %d -> RegistrationDate: %s -> Email: %s%n",this.id, this.username, this.age, this.registration, this.email);
    }
}
