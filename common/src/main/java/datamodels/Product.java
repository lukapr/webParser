package datamodels;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_id_seq")
    private long id;
    private String name;
    private String article;
    private String href;
    private String img;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "productid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categoryid", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonManagedReference
    private Set<Statistics> statistics;


    public void addCategory(Category category) {
        if (categories == null) {
            categories = new HashSet<>();
        }
        categories.add(category);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        Product product = (Product) o;
//
//        if (id != product.id) return false;
//        if (name != null ? !name.equals(product.name) : product.name != null) return false;
//        if (article != null ? !article.equals(product.article) : product.article != null) return false;
//        if (href != null ? !href.equals(product.href) : product.href != null) return false;
//        return img != null ? img.equals(product.img) : product.img == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (int) (id ^ (id >>> 32));
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (article != null ? article.hashCode() : 0);
//        result = 31 * result + (href != null ? href.hashCode() : 0);
//        result = 31 * result + (img != null ? img.hashCode() : 0);
//        return result;
//    }

//    @Override
//    public String toString() {
//        return "Product{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", article='" + article + '\'' +
//                ", href='" + href + '\'' +
//                ", img='" + img + '\'' +
//                '}';
//    }
}
