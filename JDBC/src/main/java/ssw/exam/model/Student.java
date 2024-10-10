package ssw.exam.model;

public record Student(String studentId, String name) {
    @Override
    public String toString() {
        return name + "(" + studentId + ")";
    }
}
