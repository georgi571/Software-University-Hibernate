package _1SpringData._1SpringData._04HibernateCodeFirst._2Exercise._2SalesDatabase.inheritance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "store_locations")
public class StoreLocation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @OneToOne(mappedBy = "storeLocations")
    private Sale sale;
}
