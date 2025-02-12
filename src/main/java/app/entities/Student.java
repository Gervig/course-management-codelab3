package app.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate

public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="created_at")
    private LocalDateTime createdAt;
    private String email;
    private String name;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public Student(LocalDateTime createdAt, String email, String name, LocalDateTime updatedAt)
    {
        this.createdAt = createdAt;
        this.email = email;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    //"Stempler" eleven med localDateTime
    @PrePersist
    private void beforePersist(){
        addCreated();
        addUpdated();
    }

    private void addCreated(){
        createdAt = LocalDateTime.now();
    }

    private void addUpdated(){
        updatedAt = LocalDateTime.now();
    }

    //Relation til course - 1 student kan have 1 course
    @ManyToOne
    private Course course;

}