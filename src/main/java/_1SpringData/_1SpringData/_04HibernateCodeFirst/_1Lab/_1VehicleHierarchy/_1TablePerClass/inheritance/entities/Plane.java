package _1SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1TablePerClass.inheritance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle {
    private static final String PLANE_TYPE = "PLANE";

    @Column(name = "airline")
    private String airline;
    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    public Plane() {
        super(PLANE_TYPE);
    }
}
