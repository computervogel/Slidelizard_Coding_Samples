package ssw.exam;

import ssw.exam.actions.Action;
import ssw.exam.actions.PointActions;
import ssw.exam.model.Grade;
import ssw.exam.model.Student;
import ssw.exam.services.GradeService;
import ssw.exam.services.db.DbService;
import ssw.exam.util.ConsoleUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("---- Exam Management System ----");
        final boolean dropTable = ConsoleUtil.yesNo("Recreate the database:");
        try (GradeService service = new DbService(dropTable)) {
            loop:
            for (; ; ) {
                final Action action = ConsoleUtil.select("Select an action:", Action.values());
                switch (action) {
                    case SHOW -> showGradesList(service);
                    case CREATE_STUDENT -> createStudent(service);
                    case DELETE_STUDENT -> deleteStudent(service);
                    case UPDATE_POINTS -> pointsView(service);
                    case EXIT -> {
                        break loop;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void showGradesList(GradeService service) throws Exception {
        System.out.println("Grade List:");
        ConsoleUtil.list(service.getGrades(), Grade::toString);
    }

    private static void createStudent(GradeService service) throws Exception {
        final String studentId = ConsoleUtil.readString("StudentID: ", s -> !s.isEmpty(), "StudentID cannot be empty");
        final String name = ConsoleUtil.readString("Name: ", s -> !s.isEmpty(), "Name cannot be empty");
        service.createGrade(new Student(studentId, name), 0);
    }

    private static void deleteStudent(GradeService service) throws Exception {
        final List<Grade> grades = service.getGrades();
        if (grades.isEmpty()) {
            System.out.println("No students");
            return;
        }
        final Grade selected = ConsoleUtil.select("Select a student", grades, g -> g.getStudent().toString());
        service.deleteGrade(selected);
    }

    private static void pointsView(GradeService service) throws Exception {
        if (service.getGrades().isEmpty()) {
            System.out.println("You first have to create students");
            return;
        }
        service.startPointUpdate();
        loop:
        for (; ; ) {
            final PointActions action = ConsoleUtil.select("Select an action:", PointActions.values());
            switch (action) {
                case SHOW -> showTemporaryGradesList(service);
                case UPDATE -> updatePoints(service);
                case ABORT -> {
                    service.abortPointUpdate();
                    break loop;
                }
                case FINISH -> {
                    service.finishPointUpdate();
                    break loop;
                }
            }
        }
    }

    private static void showTemporaryGradesList(GradeService service) throws Exception {
        System.out.println("Temporary Grades List:");
        ConsoleUtil.list(service.getGrades(), Grade::toString);
    }

    private static void updatePoints(GradeService service) throws Exception {
        final List<Grade> grades = service.getGrades();
        if (grades.isEmpty()) {
            System.out.println("No students");
            return;
        }
        final Grade selected = ConsoleUtil.select("Select a student", grades, Grade::toString);
        System.out.printf("Current points: %d%n", selected.getPoints());
        final int points = ConsoleUtil.readInt("New points: ", i -> 0 <= i && i <= 24, "Invalid number of points");
        selected.setPoints(points);
        service.updateGrade(selected);
    }
}