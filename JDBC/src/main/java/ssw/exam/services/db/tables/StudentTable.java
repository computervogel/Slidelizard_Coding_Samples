package ssw.exam.services.db.tables;

public class StudentTable {
    public static final String TABLE_NAME = "student";
    public static final String ID = "studentId";
    public static final String NAME = "name";

    public static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS %s (
                %s TEXT PRIMARY KEY,
                %s TEXT NOT NULL
            )
            """.formatted(TABLE_NAME, ID, NAME);

    public static final String DROP_TABLE = """
            DROP TABLE IF EXISTS %s
            """.formatted(TABLE_NAME);

    public static final int INSERT_ID_INDEX = 1;
    public static final int INSERT_NAME_INDEX = 2;
    public static final String INSERT = """
            INSERT INTO %s (%s, %s) VALUES (?, ?)
            """.formatted(TABLE_NAME, ID, NAME);

    public static final int DELETE_STUDENT_INDEX = 1;
    public static final String DELETE = """
            DELETE FROM %s WHERE %s = ?
            """.formatted(TABLE_NAME, ID);

    public static final String SELECT = """
            SELECT s.%s, %s, %s FROM %s s
            JOIN %s USING (%s)
            ORDER BY %s
            """.formatted(ID, NAME, GradeTable.POINTS, TABLE_NAME, GradeTable.TABLE_NAME, ID, ID);
}
