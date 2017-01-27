
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by pryaly on 1/25/2017.
 */
@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @XmlElement(name = "category")
    private Collection<Category> products;

    @XmlAttribute
    private String href;

    @XmlAttribute
    private String date;
}
