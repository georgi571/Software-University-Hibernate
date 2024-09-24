package _1SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1SingleTable.inheritance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
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
