import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pryaly on 1/26/2017.
 */
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @XmlElement(name = "product")
    private Collection<Product> products;
    @XmlAttribute(name = "name")
    private String name;

    public void addProducts(List<Product> products) {
        if(this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.addAll(products);
    }
}
