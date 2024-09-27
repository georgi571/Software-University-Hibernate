package _8SpringData._1SpringData._02ORMFundamentals._2Exercise.entities;

import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Column;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Entity;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Id;

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
