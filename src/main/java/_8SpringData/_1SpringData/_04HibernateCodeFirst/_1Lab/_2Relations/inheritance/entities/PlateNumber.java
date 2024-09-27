package _8SpringData._1SpringData._04HibernateCodeFirst._1Lab._2Relations.inheritance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private String number;

    @OneToOne(mappedBy = "plateNumber")
    private Car car;
}
