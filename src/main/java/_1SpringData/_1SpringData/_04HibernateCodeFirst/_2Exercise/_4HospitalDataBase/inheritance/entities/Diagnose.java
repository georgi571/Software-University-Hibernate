package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._4HospitalDataBase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "comments")
    private String comment;

    @OneToMany(mappedBy = "diagnose")
    private Set<Visitation> visitations;

    @ManyToMany
    @JoinTable(name = "diagnoses_medicaments",
    joinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    private Set<Medicament> medicaments;
}
