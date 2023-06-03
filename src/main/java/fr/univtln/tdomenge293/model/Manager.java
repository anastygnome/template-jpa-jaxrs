package fr.univtln.tdomenge293.model;

import jakarta.persistence.DiscriminatorValue;
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
@DiscriminatorValue("2")
@XmlRootElement
@XmlType(name = "Manager")

@XmlAccessorType(XmlAccessType.FIELD)
public class Manager extends Employee {
    @NonNull
    private String managedDepartment;
}
