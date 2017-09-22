package springboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_seq_gen")
    @SequenceGenerator(name = "config_seq_gen", sequenceName = "config_id_seq")
    private long id;
    private String name;
    private String description;
    private String link;
}
