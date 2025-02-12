package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate

public class Teacher
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String name;
    private String zoom;

    public Teacher(String email, String name, String zoom)
    {
        this.email = email;
        this.name = name;
        this.zoom = zoom;
    }


    //Relation til course
    @OneToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Course> courses = new HashSet<>();
}
