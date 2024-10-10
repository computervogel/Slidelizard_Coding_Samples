package ssw.exam.services;

import ssw.exam.model.Grade;
import ssw.exam.model.Student;

import java.util.List;

public interface GradeService extends AutoCloseable {
    void createGrade(Student student, int points) throws Exception;

    void updateGrade(Grade grade) throws Exception;

    void deleteGrade(Grade grade) throws Exception;

    List<Grade> getGrades() throws Exception;

    void startPointUpdate() throws Exception;

    void abortPointUpdate() throws Exception;

    void finishPointUpdate() throws Exception;
}
