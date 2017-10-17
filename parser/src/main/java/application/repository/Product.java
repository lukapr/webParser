package application.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_id_seq")
    private long id;
    private String name;
    private String article;
    private String href;
    private String orderscount;
    private String cost;
    private String originalcost;
    private String discount;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="product")
    private List<Restcount> restcounts;
    @ManyToMany
    private List<Category> categories;


}
