package _1SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1TablePerClass.inheritance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle {
    private static final String TRUCK_TYPE = "TRUCK";

    @Column(name = "load_capacity")
    private double loadCapacity;

    public Truck() {
        super(TRUCK_TYPE);
    }
}
