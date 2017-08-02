package model;

import play.db.jpa.JPA;

import javax.persistence.*;

/**
 * Created by nfaure on 8/1/17.
 */
@Entity
@Table(name = "nfauretest")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class NfaureTest {

    @Column(name = "customer_id", nullable = false, columnDefinition = "NUMBER(10)")
    @Id
    private Long customerId;

    @Column(name = "customer_name", length = 256)
    private String customerName;

    @Column(name = "city", length = 256)
    private String customerCity;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customer_id) {
        this.customerId = customer_id;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }
}
