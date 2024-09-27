package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._2SalesDatabase.inheritance.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "sales")
    @OneToMany(mappedBy = "product")
    private Set<Sale> sales;
}
