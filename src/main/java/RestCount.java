import com.sun.xml.internal.txw2.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pryaly on 1/25/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "restCount")
@Data
@AllArgsConstructor
public class RestCount {
    private String size;
    private String count;
}
