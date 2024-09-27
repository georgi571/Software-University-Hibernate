package _8SpringData._1SpringData._02ORMFundamentals._2Exercise.entities;

import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Column;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Entity;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Id;

import java.time.LocalDate;

@Entity(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "number")
    private String number;

    @Column(name = "date")
    private LocalDate date;

    public Order() {

    }

    public Order(String number, LocalDate date) {
        this.number = number;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("ID:%d -> Order №: %s -> Date Of Order: %s%n",this.id, this.number, this.date);
    }
}
