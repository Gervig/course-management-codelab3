package app.populators;

import app.dao.TeacherDAO;
import app.entities.Teacher;

public class TeacherPopulator
{
    public static Teacher[] populate()
    {
        Teacher t1 = Teacher.builder()
                .name("Jon")
                .email("cph-jon@cphbusiness.dk")
                .zoom("Jons Zoom rum")
                .build();
        Teacher t2 = Teacher.builder()
                .name("Signe")
                .email("cph-Signe@cphbusiness.dk")
                .zoom("Signes Zoom rum")
                .build();
        Teacher t3 = Teacher.builder()
                .name("Thomas")
                .email("cph-Thomas@cphbusiness.dk")
                .zoom("Thomas' Zoom rum")
                .build();
    }
}
