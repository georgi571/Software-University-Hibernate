package _8SpringData._1SpringData._04HibernateCodeFirst._2Exercise._5BillsPaymentSystem.inheritance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetails{
    @Column(name = "name")
    private String name;

    @Column(name = "swift_code")
    private String swiftCode;
}
