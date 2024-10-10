package app;

import inout.Out;
import map.ArrayMap;
import map.Map;
import students.Student;

import java.util.Iterator;

public class MapTest {
    private static final String CS = "Computer Science";
    private static final String MATH = "Mathematics";
    private static final String MECH = "Mechatronics";
    private static final String ELIT = "Electronics and Information Technology";

    public static void main(String[] args) {
        Student anton = new Student("Anton");
        Student berta = new Student("Berta");
        Student caesar = new Student("Cäsar");
        Student dora = new Student("Dora");
        Student emil = new Student("Emil");

        ArrayMap<Student, String> studentsToSubjects = new ArrayMap<>();

        addStudent(studentsToSubjects, dora, ELIT);
        addStudent(studentsToSubjects, berta, CS);
        addStudent(studentsToSubjects, anton, MATH);
        addStudent(studentsToSubjects, caesar, MECH);
        addStudent(studentsToSubjects, emil, MECH);

        printMap(studentsToSubjects);

        Student friedrich = new Student("Friedrich");
        Out.println("Subject of " + caesar + ": " + studentsToSubjects.get(caesar));
        Out.println("Subject of " + berta + ": " + studentsToSubjects.get(berta));
        Out.println("Subject of " + friedrich + ": " + studentsToSubjects.get(friedrich));
        Out.println();

        Out.println("Map contains " + dora + "? " + studentsToSubjects.contains(dora));
        Out.println("Map contains " + friedrich + "? " + studentsToSubjects.contains(friedrich));
        Out.println();

        studentsToSubjects.put(anton, CS);
        studentsToSubjects.put(caesar, MATH);

        Out.println("Set new subjects for " + anton + " and " + caesar);
        printEntries(studentsToSubjects);
        Out.println();


        Out.println("Removed " + emil + ". Student found? " + studentsToSubjects.remove(emil));

        Out.println("Removed " + caesar + ". Student found? " + studentsToSubjects.remove(caesar));

        Out.println("Removed " + friedrich + ". Student found? " + studentsToSubjects.remove(friedrich));

        printMap(studentsToSubjects);
        Out.println();
    }

    private static void addStudent(Map<Student, String> studentsToSubjects, Student student, String subject) {
        studentsToSubjects.put(student, subject);
        Out.println("Set subject " + subject + " for " + student.toString());
        Out.println("New map size: " + studentsToSubjects.size());
        Out.println();
    }

    private static void printMap(Map<Student, String> studentsToSubjects) {
        Out.println("=====");
        printEntries(studentsToSubjects);
        Out.println("=====");
        printKeys(studentsToSubjects);
        Out.println("=====");
        printValues(studentsToSubjects);
        Out.println("=====");
        Out.println();
    }

    private static void printKeys(Map<Student, String> studentsToSubjects) {
        Out.println("Keys:");
        Out.println("-----");
        Iterator<Student> keys = studentsToSubjects.keyIterator();
        while (keys.hasNext()) {
            Out.println(keys.next().toString());
        }
    }

    private static void printValues(Map<Student, String> studentsToSubjects) {
        Out.println("Values:");
        Out.println("-----");
        Iterator<String> values = studentsToSubjects.valueIterator();
        while (values.hasNext()) {
            Out.println(values.next());
        }
    }

    private static void printEntries(Map<Student, String> studentsToSubjects) {
        Out.println("Entries:");
        Out.println("-----");
        AppUtils.printMap(studentsToSubjects);
    }
}
