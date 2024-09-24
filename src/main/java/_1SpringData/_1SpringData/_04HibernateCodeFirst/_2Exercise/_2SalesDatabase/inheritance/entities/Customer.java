package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._2SalesDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Column(name = "sales")
    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;
}
