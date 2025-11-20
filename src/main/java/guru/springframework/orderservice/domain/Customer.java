package guru.springframework.orderservice.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AttributeOverrides({
        @AttributeOverride(
                name = "address.address",
                column = @Column(name = "address")
        ),
        @AttributeOverride(
                name = "address.city",
                column = @Column(name = "city")
        ),
        @AttributeOverride(
                name = "address.state",
                column = @Column(name = "state")
        ),
        @AttributeOverride(
                name = "address.zipCode",
                column = @Column(name = "zipCode")
        )
})
public class Customer extends BaseEntity {

    @Length(max = 50)
    private String customerName;
    @Valid
    @Embedded
    private Address address;
    @Length(max = 20)
    private String phone;
    private String email;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Set<OrderHeader> orders;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<OrderHeader> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderHeader> orders) {
        this.orders = orders;
    }

    public void addOrderHeader(OrderHeader order) {
        if (orders == null) {
            orders = new HashSet<>();
        }

        orders.add(order);
        order.setCustomer(this);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;
        return Objects.equals(customerName, customer.customerName) && Objects.equals(address, customer.address) && Objects.equals(phone, customer.phone) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(customerName);
        result = 31 * result + Objects.hashCode(address);
        result = 31 * result + Objects.hashCode(phone);
        result = 31 * result + Objects.hashCode(email);
        return result;
    }
}
