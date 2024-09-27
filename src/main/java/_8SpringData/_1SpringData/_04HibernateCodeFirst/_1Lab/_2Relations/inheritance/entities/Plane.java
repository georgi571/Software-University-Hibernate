package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._2Relations.inheritance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle {
    private static final String PLANE_TYPE = "PLANE";

    @Column(name = "airline")
    private String airline;
    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Plane() {
        super(PLANE_TYPE);
    }
}
