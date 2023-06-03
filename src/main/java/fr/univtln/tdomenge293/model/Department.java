package fr.univtln.tdomenge293.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "departments")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@BatchSize(size = 10)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Department ( @NotNull String name ) {
        this.name = name;
    }

    @NotNull
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL,orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    @XmlElement(name = "employee")
    private Set<Employee> employees = new HashSet<>();

    public static Department of (String name ) {
        return new Department(name);
    }
}
