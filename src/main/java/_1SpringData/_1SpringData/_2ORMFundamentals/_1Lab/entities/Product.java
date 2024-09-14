package _1SpringData._1SpringData._2ORMFundamentals._1Lab.entities;

import _1SpringData._1SpringData._2ORMFundamentals._1Lab.orm.anotations.Column;
import _1SpringData._1SpringData._2ORMFundamentals._1Lab.orm.anotations.Entity;
import _1SpringData._1SpringData._2ORMFundamentals._1Lab.orm.anotations.Id;

@Entity(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
