package datamodels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistics_seq_gen")
    @SequenceGenerator(name = "statistics_seq_gen", sequenceName = "statistics_id_seq")
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="productid")
    private Product product;
    private Integer orderscount;
    private Double cost;
    private Double originalcost;
    private Double discount;
    @CreationTimestamp
    private Date createdon;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="statisticsid")
    private Set<Restcount> restcount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Statistics that = (Statistics) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderscount != null ? !orderscount.equals(that.orderscount) : that.orderscount != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (originalcost != null ? !originalcost.equals(that.originalcost) : that.originalcost != null) return false;
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) return false;
        return createdon != null ? createdon.equals(that.createdon) : that.createdon == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (orderscount != null ? orderscount.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (originalcost != null ? originalcost.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (createdon != null ? createdon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", product=" + product +
                ", orderscount=" + orderscount +
                ", cost=" + cost +
                ", originalcost=" + originalcost +
                ", discount=" + discount +
                ", createdon=" + createdon +
                '}';
    }
}
