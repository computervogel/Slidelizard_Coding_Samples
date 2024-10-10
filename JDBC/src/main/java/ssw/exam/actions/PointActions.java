package ssw.exam.actions;

public enum PointActions {
    SHOW("Show a list of the temporary points"),
    UPDATE("Update the points of one student"),
    ABORT("Abort without saving the changes"),
    FINISH("Finish saving the changes");

    final String description;

    PointActions(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
