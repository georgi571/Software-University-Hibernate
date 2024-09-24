package _1SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1SingleTable.inheritance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Truck extends Vehicle {
    private static final String TRUCK_TYPE = "TRUCK";

    @Column(name = "load_capacity")
    private double loadCapacity;

    public Truck() {
        super(TRUCK_TYPE);
    }
}
