import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pryaly on 12/28/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Data
public class Product {

    @XmlElement
    private String href;
    @XmlElement
    private String name;
    @XmlElement
    private String ordersCount;
    @XmlAttribute
    private String article;
    @XmlElement
    private String cost;
    @XmlElement
    private String originalCost;
    @XmlElement
    private String discount;
    @XmlElement(name = "restCount")
    List<RestCount> restCount = new ArrayList<>();
//    Map<String, String> restCount = new HashMap<>();

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

//    public String getHref() {
//        return href;
//    }
//
//    @XmlElement
//    public void setHref(String href) {
//        this.href = href;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    @XmlElement
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getOrdersCount() {
//        return ordersCount;
//    }
//
//    @XmlElement
//    public void setOrdersCount(String ordersCount) {
//        this.ordersCount = ordersCount;
//    }
//
//    public String getArticle() {
//        return article;
//    }
//
//    @XmlAttribute
//    public void setArticle(String article) {
//        this.article = article;
//    }
//
//    public String getCost() {
//        return cost;
//    }
//
//    @XmlElement
//    public void setCost(String cost) {
//        this.cost = cost;
//    }
//
//    public String getOriginalCost() {
//        return originalCost;
//    }
//
//    @XmlElement
//    public void setOriginalCost(String originalCost) {
//        this.originalCost = originalCost;
//    }
//
//    public String getDiscount() {
//        return discount;
//    }
//
//    @XmlElement
//    public void setDiscount(String discount) {
//        this.discount = discount;
//    }
//
//    public List<RestCount> getRestCount() {
//        return restCount;
//    }
//
//    @XmlElement(name = "restCount")
//    public void setRestCount(List<RestCount> restCount) {
//        this.restCount = restCount;
//    }
}
