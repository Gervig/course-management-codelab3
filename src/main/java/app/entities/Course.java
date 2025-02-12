package app.entities;

import app.enums.CourseName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate

public class Course
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name="course_name")
    private CourseName courseName;
    private String description;
    private LocalDate endDate;
    private LocalDate startDate;

    public Course(CourseName courseName, String description, LocalDate endDate, LocalDate startDate)
    {
        this.courseName = courseName;
        this.description = description;
        this.endDate = endDate;
        this.startDate = startDate;
    }
}
