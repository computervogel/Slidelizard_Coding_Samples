package ssw.exam.services.db;

import ssw.exam.model.Grade;
import ssw.exam.model.Student;
import ssw.exam.services.GradeService;
import ssw.exam.services.db.tables.GradeTable;
import ssw.exam.services.db.tables.StudentTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbService implements GradeService {
    private static final String connectionString = "jdbc:sqlite:students.db";
    public final Connection conn;

    public DbService(boolean createNewDb) throws Exception {
        // DONE: connect to database
        try {
            this.conn = DriverManager.getConnection(connectionString);

            if(createNewDb){
                dropTables();
            }

            createTables();
        } catch (SQLException e) {
            throw new Exception("Database connection could not be established", e);
        }
    }

    private void dropTables() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(StudentTable.DROP_TABLE);
            stmt.execute(GradeTable.DROP_TABLE);
        } catch (SQLException e){
            throw new Exception("Could not drop database tables", e);
        }
    }

    private void createTables() throws Exception {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(StudentTable.CREATE_TABLE);
            stmt.execute(GradeTable.CREATE_TABLE);
        } catch (SQLException e) {
            throw new Exception("Could not create database tables", e);
        }
    }

    /**
     * Adds a new grade to the database.
     *
     * @param student the student for the grade
     * @param points  the points
     * @throws Exception if a database error occurs
     */
    public void createGrade(Student student, int points) throws Exception {
        //DONE
        try (PreparedStatement p = conn.prepareStatement(StudentTable.INSERT); PreparedStatement ps = conn.prepareStatement(GradeTable.INSERT)){
            p.setString(StudentTable.INSERT_ID_INDEX, student.studentId());
            p.setString(StudentTable.INSERT_NAME_INDEX, student.name());

            final int rows = p.executeUpdate();
            if(rows != 1){
                throw new Exception("Failed to insert rows into database");
            }

            ps.setString(GradeTable.INSERT_STUDENT_INDEX, student.studentId());
            ps.setInt(GradeTable.INSERT_POINT_INDEX, points);

            final int rows2 = ps.executeUpdate();
            if(rows2 != 1){
                throw new Exception("Failed to insert rows into database");
            }
        }
    }

    /**
     * Updates the given grade in the database.
     *
     * @param grade The grade to update
     * @throws Exception if a database error occurs
     */
    @Override
    public void updateGrade(Grade grade) throws Exception {
        //DONE
        try (PreparedStatement p = conn.prepareStatement(GradeTable.UPDATE_POINTS)){
            p.setInt(GradeTable.UPDATE_POINT_INDEX, grade.getPoints());
            p.setString(GradeTable.UPDATE_STUDENT_INDEX, grade.getStudent().studentId());

            final int rows = p.executeUpdate();
            if(rows != 1){
                throw new Exception("Failed to update rows into database");
            }
        } catch (SQLException e) {
            throw new Exception("Could not update grade", e);
        }
    }

    /**
     * Deletes the given grade from the database
     *
     * @param grade The grade to delete
     * @throws Exception if a database error occurs
     */
    @Override
    public void deleteGrade(Grade grade) throws Exception {
        //DONE
        try (PreparedStatement p = conn.prepareStatement(GradeTable.DELETE); PreparedStatement ps = conn.prepareStatement(StudentTable.DELETE)){
            p.setString(GradeTable.DELETE_GRADE_ID_INDEX, grade.getStudent().studentId());

            final int rows = p.executeUpdate();
            if(rows != 1){
                throw new Exception("Failed to delete rows into database");
            }

            ps.setString(StudentTable.DELETE_STUDENT_INDEX, grade.getStudent().studentId());

            final int rows2 = ps.executeUpdate();
            if(rows2 != 1){
                throw new Exception("Failed to delete rows into database");
            }
        } catch (SQLException e) {
            throw new Exception("Could not delete grade", e);
        }
    }

    /**
     * @return A list of the grades in the database
     * @throws Exception if a database error occurs
     */
    @Override
    public List<Grade> getGrades() throws Exception {
        //TODO
        try (Statement s = conn.createStatement()){
            final ResultSet results = s.executeQuery(StudentTable.SELECT);
            final List<Grade> grades = new ArrayList<>();

            while (results.next()){
                final String id = results.getString(StudentTable.ID);
                final String name = results.getString(StudentTable.NAME);
                final int points = results.getInt(GradeTable.POINTS);
                grades.add(new Grade(new Student(id, name), points));
            }

            return grades;
        } catch (SQLException e){
            throw new Exception("Could not read grades", e);
        }
    }

    /**
     * Starts a new transaction.
     *
     * @throws Exception if a database error occurs
     */
    @Override
    public void startPointUpdate() throws Exception {
        //DONE
        conn.setAutoCommit(false);
    }

    /**
     * Aborts the current transaction.
     *
     * @throws Exception if a database error occurs
     */
    @Override
    public void abortPointUpdate() throws Exception {
        //DONE
        conn.rollback();
        conn.setAutoCommit(true);
    }

    /**
     * Finishes the current transaction.
     *
     * @throws Exception if a database error occurs
     */
    @Override
    public void finishPointUpdate() throws Exception {
        //DONE
        conn.rollback();
        conn.setAutoCommit(true);
    }

    /**
     * Close the connection to the database.
     *
     * @throws Exception if a database error occurs
     */
    @Override
    public void close() throws Exception {
        //DONE
        conn.close();
    }
}
