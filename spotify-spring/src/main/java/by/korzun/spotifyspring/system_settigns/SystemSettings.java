package by.korzun.spotifyspring.system_settigns;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "system_settings")
@Accessors(chain = true)
@Setter
@Getter
public class SystemSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String value;
    private String type;
}
