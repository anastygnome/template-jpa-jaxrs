package fr.univtln.tdomenge293.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Engineer.class, name = "Engineer"), @JsonSubTypes.Type(value = Manager.class, name = "Manager")})
@XmlSeeAlso({Manager.class, Engineer.class})
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NonNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_id")
    @NonNull
    @ToString.Exclude
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    private Department department;

    @XmlID
    @JsonIgnore
    public String getXmlId () {
        return id.toString();
    }

    public void setDepartment ( Department department ) {
        this.department = department;
        department.getEmployees().add(this);
    }
}
