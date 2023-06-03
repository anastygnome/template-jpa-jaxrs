package fr.univtln.tdomenge293.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(force = true)
@Entity
@DiscriminatorValue("1")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Engineer")
public class Engineer extends Employee {
    @NonNull
    private String engineeringField;

    private Engineer ( @NonNull String engineeringField ) {
        this.engineeringField = engineeringField;
    }


    public static Engineer of ( String name, Date birthDate, String engineeringField , Department department) {
        Engineer e = new Engineer(engineeringField);
        e.setDepartment(department);
        e.setName(name);
        e.setBirthDate(birthDate);
        return  e;
    }
}
