package application.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
    @SequenceGenerator(name = "category_seq_gen", sequenceName = "category_id_seq")
    private long id;
    private String name;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name="category_product",
            joinColumns=@JoinColumn(name="categoryid", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="productid", referencedColumnName="id"))
    private List<Product> products;

    public void addProducts(List<Product> products) {
        if(this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.addAll(products);
    }
}
