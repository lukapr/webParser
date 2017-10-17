package application.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restcount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restcount_seq_gen")
    @SequenceGenerator(name = "restcount_seq_gen", sequenceName = "restcount_id_seq")
    private long id;
    private String size;
    private String count;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="productid")
    private Product product;

    public Restcount(String size, String count) {
        this.size = size;
        this.count = count;
    }
}
