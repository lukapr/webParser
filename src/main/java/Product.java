import lombok.Data;
import lombok.ToString;

/**
 * Created by pryaly on 12/28/2016.
 */
@Data
public class Product {

    private String href;
    private String name;
    private String count;
    private String article;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", article='" + article + '\'' +
                '}';
    }
}
