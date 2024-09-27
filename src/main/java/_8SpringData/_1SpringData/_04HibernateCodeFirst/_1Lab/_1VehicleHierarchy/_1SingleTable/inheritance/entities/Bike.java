package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1SingleTable.inheritance.entities;

import jakarta.persistence.Entity;

@Entity
public class Bike extends Vehicle {
    private static final String BIKE_TYPE = "BIKE";

    public Bike() {
        super(BIKE_TYPE);
    }
}
