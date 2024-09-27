package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1SingleTable.inheritance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Car extends Vehicle {
    private static final String CAR_TYPE = "CAR";

    @Column(name = "seats")
    private int seats;

    public Car() {
        super(CAR_TYPE);
    }
}
