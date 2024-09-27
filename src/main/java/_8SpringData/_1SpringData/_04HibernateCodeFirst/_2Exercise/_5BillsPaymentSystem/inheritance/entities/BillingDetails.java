package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._5BillsPaymentSystem.inheritance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BillingDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "number")
    private int number;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
