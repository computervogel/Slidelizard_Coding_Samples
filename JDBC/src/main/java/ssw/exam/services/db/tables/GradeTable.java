package ssw.exam.services.db.tables;

public class GradeTable {
    public static final String TABLE_NAME = "grade";
    public static final String ID = "studentId";
    public static final String POINTS = "points";

    public static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS %s (
                %s TEXT PRIMARY KEY,
                %s INTEGER NOT NULL
            )
            """.formatted(TABLE_NAME, ID, POINTS);

    public static final String DROP_TABLE = """
            DROP TABLE IF EXISTS %s
            """.formatted(TABLE_NAME);

    public static final int INSERT_STUDENT_INDEX = 1;
    public static final int INSERT_POINT_INDEX = 2;
    public static final String INSERT = """
            INSERT INTO %s (%s, %s) VALUES (?, ?)
            """.formatted(TABLE_NAME, ID, POINTS);

    public static final int UPDATE_POINT_INDEX = 1;
    public static final int UPDATE_STUDENT_INDEX = 2;
    public static final String UPDATE_POINTS = """
            UPDATE %s SET %s = ? WHERE %s = ?
            """.formatted(TABLE_NAME, POINTS, ID);

    public static final int DELETE_GRADE_ID_INDEX = 1;
    public static final String DELETE = """
            DELETE FROM %s WHERE %s = ?
            """.formatted(TABLE_NAME, ID);
}