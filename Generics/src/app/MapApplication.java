package app;

import inout.Out;
import map.ArrayMap;
import map.Map;
import map.MapUtils;
import map.Transformer;
import students.Student;

public class MapApplication {

    public static void main(String[] args) {
        // Map from MatNr to student object
        Map<Integer, Student> studentsByMatNr = new ArrayMap<>();
        Student matthias = new Student("Matthias");
        Student gerda = new Student("Gerda");
        Student ginny = new Student("Ginny");

        Out.println("MatNr -> Student:");
        studentsByMatNr.put(12018660, matthias);
        studentsByMatNr.put(12113652, gerda);
        studentsByMatNr.put(12025639, ginny);
        AppUtils.printMap(studentsByMatNr);
        Out.println();

        // Map from student to his/her monthly spending in EUR
        Map<Student, Integer> monthlySpendingsByStudent = new ArrayMap<>();
        monthlySpendingsByStudent.put(matthias, 90);
        monthlySpendingsByStudent.put(gerda, 1400);
        monthlySpendingsByStudent.put(ginny, 900);
        Out.println("Student -> Monthly Spending:");
        AppUtils.printMap(monthlySpendingsByStudent);
        Out.println();

        Transformer<Student, String> nameTransformer = new Transformer<Student, String>() {
            @Override
            public String transform(Student student) {
                return student.name;
            }
        };

        // Map from MatNr to student name
        ArrayMap<Integer, String> namesByMatNr = MapUtils.mapValues(studentsByMatNr, nameTransformer);
        Out.println("MatNr -> Name:");
        AppUtils.printMap(namesByMatNr);
        Out.println();

        Transformer<Integer, Integer> numberLengthTransformer = new Transformer<Integer, Integer>() {
            @Override
            public Integer transform(Integer number) {
                int i = 0;
                while(number >= 1){
                    number /= 10;
                    i++;
                }
                return i;
            }
        };

        // Map from student object to number of digits of his/her monthly spending
        ArrayMap<Student, Integer> monthlySpendingDigits = MapUtils.mapValues(monthlySpendingsByStudent, numberLengthTransformer);
        Out.println("Student -> Digits of monthly spending:");
        AppUtils.printMap(monthlySpendingDigits);
        Out.println();

        Transformer<String, Character> stringInitialTransformer = new Transformer<String, Character>() {
            @Override
            public Character transform(String string) {
                return string.charAt(0);
            }
        };

        // Map from MatNr to the student name initial
        ArrayMap<Integer, Character> nameInitialsByMatNr = MapUtils.mapValues(namesByMatNr, stringInitialTransformer);
        Out.println("MatNr -> Name initial:");
        AppUtils.printMap(nameInitialsByMatNr);
        Out.println();
    }
}
