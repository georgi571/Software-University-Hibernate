package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._4HospitalDataBase.inheritance.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "visitations")
public class Visitation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;
}
