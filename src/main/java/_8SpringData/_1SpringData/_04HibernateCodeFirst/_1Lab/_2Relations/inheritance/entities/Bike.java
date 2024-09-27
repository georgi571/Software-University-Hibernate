package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._2Relations.inheritance.entities;

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
