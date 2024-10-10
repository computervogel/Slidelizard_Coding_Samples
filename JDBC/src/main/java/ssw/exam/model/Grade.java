package ssw.exam.model;

public class Grade {
    private final Student student;
    private int points;

    public Grade(Student student, int points) {
        this.student = student;
        this.points = points;
    }

    public Student getStudent() {
        return student;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getGrade() {
        if (points < 12) {
            return "Nicht genügend";
        }
        if (points < 15) {
            return "Genügend";
        }
        if (points < 18) {
            return "Befriedigend";
        }
        if (points < 21) {
            return "Gut";
        }
        return "Sehr Gut";
    }

    @Override
    public String toString() {
        return student + " has a " + getGrade() + " with " + getPoints();
    }
}
