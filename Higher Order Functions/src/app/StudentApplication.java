package app;

import inout.Out;
import map.ArrayMap;
import map.KeyValuePair;
import map.Map;
import students.Student;

import java.util.Optional;

import static students.Student.Degree.*;

public class StudentApplication {
    public static void main(String[] args) {
        Out.println("===========================================");
        Out.println("=== JKU KUSSS Backend Super System 3000 ===");
        Out.println("===========================================");

        // 8-digit student IDs (all starting with 1) mapped to students
        Map<Integer, Student> students = new ArrayMap<>();
        students.put(16804242, new Student("Herbert Posthofer",
                "Computer Science",
                PHD));
        students.put(11255667, new Student("Markus Mehringer",
                "History of Memes",
                PHD));
        students.put(11255668, new Student("Andreas Schörgenhummer",
                "Artificial Intelligence",
                PHD));
        students.put(11266779, new Student("Gabe Doginger",
                "History of Memes",
                MSC));
        students.put(11355555, new Student("Itis Wednesday",
                "Contemporary Music",
                BSC));
        students.put(11410001, new Student("Raffael Moossahner",
                "Artificial Intelligence",
                PHD));
        students.put(11410002, new Student("Sebastian Hofkloiber",
                "Digital Transformation in Industry 4.0",
                MSC));
        students.put(11598765, new Student("Lucas Macor",
                "Computer Science",
                MSC));
        students.put(11898765, new Student("Katrin Korn",
                "Digital Art",
                BSC));
        students.put(12001447, new Student("Gandalf der Gelbe",
                "Digital Art",
                BSC));
        students.put(12001448, new Student("Mike",
                "The Influence of Funny Names on the Funnyness of Homeworks",
                BSC));
        students.put(21110001, new Student("Maggus Sus", "Medicine", null));              //test student for null-degree
        Out.println("All students:");
        print(students);

        boolean putSuccess = students.putIfAbsent(11234567, () -> new Student("Randy Random", "Game Design", Student.Degree.BSC))
                /* DONE: putIfAbsent for student ID 11234567 and Student("Randy Random", "Game Design", Student.Degree.BSC) */;
        System.out.printf("1st putIfAbsent successful for student ID 11234567: %b%n", putSuccess);

        putSuccess = students.putIfAbsent(11234567, () -> new Student("Rudolphine Ruby", "Game Design", Student.Degree.BSC))
        /* DONE: putIfAbsent for student ID 11234567 and Student("Rudolphine Ruby", "Game Design", Student.Degree.BSC)) */;
        System.out.printf("2nd putIfAbsent successful for student ID 11234567: %b%n%n", putSuccess);

        Map<Integer, Student> bscs = students.filter((k, v) -> (v.degree == BSC))
                /* DONE: Filter all students that have degree == Student.Degree.BSC */;
        System.out.println("BSCs:");
        print(bscs);

        Map<Integer, Student> lastTwoDigitsGE50 = students.filter((k) -> (k % 100) >= 50)
                /* DONE: Filter all students whose last two student ID digits are >= 50 (use key-filter, not key-value-filter) */;
        System.out.println("Last two student ID digits >= 50:");
        print(lastTwoDigitsGE50);

        Map<Integer, String> initials = students.mapValues((pair) -> {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < pair.getValue().name.length(); i++){
                if(i < 1) {
                    if(pair.getValue().name.charAt(i) != ' '){
                        sb.append(pair.getValue().name.charAt(i));
                    }
                } else if(pair.getValue().name.charAt(i-1) == ' ' && pair.getValue().name.charAt(i) != ' '){
                    sb.append(pair.getValue().name.charAt(i));
                }
            }
            return sb.toString();
        });
        /* DONE: Map all students to their initials. Student "Gabe Doginger" should be mapped to the String "GD", student "Gandalf der Gelbe" to "GdG" */
        System.out.println("Initials:");
        print(initials);

        Map<Integer, Integer> startYears = students.mapValues((pair) -> {
            int digits = pair.getKey();
            digits = digits/100000 % 100;
            return digits;
        })
                /* DONE: Map all students to their study start year (digit 2 and digit 3 of the key). For example, 11598765 started in year 15, 11266779 in year 12. */;
        System.out.println("Start years:");
        print(startYears);

        Optional<Student> studentWithThreePartName = students.find(integerStudentKeyValuePair -> {
            int wordCount = 0;
            for(int i = 0; i < integerStudentKeyValuePair.getValue().name.length(); i++){
                if(i < 1){
                    if(integerStudentKeyValuePair.getValue().name.charAt(i) != ' '){
                        wordCount++;
                    }
                } else if(integerStudentKeyValuePair.getValue().name.charAt(i - 1) == ' ' && integerStudentKeyValuePair.getValue().name.charAt(i) != ' '){
                    wordCount++;
                }
            }
            return wordCount == 3;
        })
                /* DONE: Use find() to potentially find a student that has three words in his/her name */;
        System.out.println("Student with three-part name: "
                + studentWithThreePartName.map(Student::toString).orElse("no such student"));


        Optional<Student> studentWithNullDegree = students.find((integerStudentKeyValuePair -> integerStudentKeyValuePair.getValue().degree == null))
                /* DONE: Use find() to potentially find a student that has null-degree */;
        System.out.println("Student with null degree: " + studentWithNullDegree.map(Student::toString).orElse("no such student"));

        Out.println();

        boolean allStudentIdsStartWithOne = students.all(((integer, student) -> {
            int digits = integer;
            digits = digits/10000000;
            return digits == 1;
        }))
                /* DONE: Use all() to find out if all student IDs start with the digit 1 */;
        System.out.println("All student IDs start with 1: " + allStudentIdsStartWithOne);

        boolean allStudentHaveSpaceInName = students.all((integer, student) -> student.name.contains(" ")
        );
                /* DONE: Use all() to find out if all students have a space in their name */
        System.out.println("All students have a space in their name: " + allStudentHaveSpaceInName);

        Out.println();

        boolean noStudentNameStartsWithX = students.none((integer, student) -> student.name.charAt(0)=='X');
                /* DONE: Use none() to find out if no student name starts with the letter 'X' */
        System.out.println("No student name starts with X: " + noStudentNameStartsWithX);

        boolean noStudentStudiesTwoWordSubject = students.none((integer, student) -> {
            int wordCount = 0;
            for(int i = 0; i < student.name.length(); i++){
                if(i < 1){
                    if(student.name.charAt(i) == ' '){
                        wordCount++;
                    }
                } else if(student.name.charAt(i-1) == ' ' && student.name.charAt(i) != ' '){
                    wordCount++;
                }
            }
            return wordCount == 3;
        });
                /* DONE: Use none() to find out if no student studies a subject that consists of two words */
        System.out.println("No student studies a subject with two words: " + noStudentStudiesTwoWordSubject);

        Out.println();

        boolean someStudentHasFiveAtEndAtStudiesBSC = students.some((integer, student) -> {
            int digits = integer;
            digits = digits % 10;
            return digits == 5 && student.degree == BSC;
        })
                /* DONE: Use some() to find out if some student has a student ID that ends with 5 AND has a degree value of BSC */;
        System.out.println("At least on student has ID that ends with 5 and studies BSC degree: " + someStudentHasFiveAtEndAtStudiesBSC);

        boolean someStudentStudiesMedicine = students.some((integer, student) -> student.subject.equals("Medicine"))
                /* DONE: Use some() to find out if some student studies the subject "Medicine" */;
        System.out.println("At least one student studies medicine: " + someStudentStudiesMedicine);
    }

    static <K, V> void print(Map<K, V> map) {
        if (map == null) {
            Out.println("TODO! Map still null!");
        } else {
            for (KeyValuePair<K, V> entry : map) {
                System.out.printf("%s\t->\t%s%n", entry.getKey().toString(), entry.getValue().toString());
            }
        }
        Out.println();
    }
}