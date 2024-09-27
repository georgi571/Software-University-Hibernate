package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._4HospitalDataBase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "medicaments")
public class Medicament {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "medicaments")
    private Set<Diagnose> diagnoses;
}
