package _1SpringData._1SpringData._04HibernateCodeFirst._1Lab._1VehicleHierarchy._1TableJoined.inheritance.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "fuel_type")
    private String fuelType;

    protected Vehicle() {

    }

    protected Vehicle(String type) {
        this.type = type;
    }
}
