package _1SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1TableJoined.inheritance.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bikes")
public class Bike extends Vehicle {
    private static final String BIKE_TYPE = "BIKE";

    public Bike() {
        super(BIKE_TYPE);
    }
}
