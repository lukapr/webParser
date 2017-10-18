package datamodels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restcount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restcount_seq_gen")
    @SequenceGenerator(name = "restcount_seq_gen", sequenceName = "restcount_id_seq")
    private long id;
    private String size;
    private String count;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="statisticsid")
    @JsonBackReference
    private Statistics statistics;

    public Restcount(String size, String count) {
        this.size = size;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Restcount restcount = (Restcount) o;

        if (id != restcount.id) return false;
        if (size != null ? !size.equals(restcount.size) : restcount.size != null) return false;
        return count != null ? count.equals(restcount.count) : restcount.count == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Restcount{" +
                "id=" + id +
                ", size='" + size + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
