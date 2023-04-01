package by.korzun.spotifyspring.system_settigns;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "system_settings")
@Data
@Accessors(chain = true)
public class SystemSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String value;
    private String type;
}
