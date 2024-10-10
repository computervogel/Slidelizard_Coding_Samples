package ssw.exam.actions;

public enum Action {
    SHOW("Show a list of all students"),

    CREATE_STUDENT("Add a new student"),
    DELETE_STUDENT("Delete an existing student"),
    UPDATE_POINTS("Update points"),
    EXIT("Exit");

    final String description;

    Action(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
