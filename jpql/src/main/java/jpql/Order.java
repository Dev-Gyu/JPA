package jpql;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
public class Order {
    @Id @GeneratedValue
    private Long id;
    private int orderMount;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderMount() {
        return orderMount;
    }

    public void setOrderMount(int orderMount) {
        this.orderMount = orderMount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
