package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._2Relations.inheritance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car extends Vehicle {
    private static final String CAR_TYPE = "CAR";

    @Column(name = "seats")
    private int seats;

    @OneToOne
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    public Car() {
        super(CAR_TYPE);
    }
}
