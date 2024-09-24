package _1SpringData._1SpringData._04HibernateCodeFirst._1Lab._2Relations.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle {
    private static final String TRUCK_TYPE = "TRUCK";

    @Column(name = "load_capacity")
    private double loadCapacity;

    @ManyToMany(mappedBy = "trucks")
    private Set<Driver> drivers;

    public Truck() {
        super(TRUCK_TYPE);
    }
}
