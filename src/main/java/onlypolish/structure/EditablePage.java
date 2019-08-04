package onlypolish.structure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="EDITABLE_PAGES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditablePage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String url;

    @NotNull
    private String textFileName;
}
