import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pryaly on 12/28/2016.
 */
@Data
public class Product {

    private String href;
    private String name;
    private String ordersCount;
    private String article;
    private String cost;
    private String originalCost;
    private String discount;
    Map<String, String> restCount = new HashMap<>();

    @Override
    public String toString() {
        return "Product{" +
                "href='" + href + '\'' +
                ", name='" + name + '\'' +
                ", ordersCount='" + ordersCount + '\'' +
                ", article='" + article + '\'' +
                ", cost='" + cost + '\'' +
                ", originalCost='" + originalCost + '\'' +
                ", discount='" + discount + '\'' +
                ", restCount=" + restCount +
                '}';
    }
}
