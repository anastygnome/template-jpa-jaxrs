package fr.univtln.tdomenge293.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(staticName = "of")
@Entity
@Table(name = "engineers")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Engineer")
public class Engineer extends Employee {
    @NonNull
    private String engineeringField;
}
