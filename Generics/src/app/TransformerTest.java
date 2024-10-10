package app;

import inout.Out;
import map.ArrayMap;
import map.Map;
import map.MapUtils;
import map.Transformer;
import students.Person;
import students.Student;

public class TransformerTest {
    public static void main(String[] args) {
        Map<Integer, String> studentNames = new ArrayMap<>();
        studentNames.put(12342352, "Fred");
        studentNames.put(74829982, "Alex");
        studentNames.put(41923091, "Quentin");
        studentNames.put(23452113, "Hannah");

        Out.println("Student names:");
        AppUtils.printMap(studentNames);
        Out.println();

        // Test correct contra-variance on input parameter
        Transformer<Object, Person> toPersonTransformer = new Transformer<>() {
            @Override
            public Student transform(Object source) {
                return new Student(source.toString());
            }
        };
        Map<Integer, Person> persons = MapUtils.mapValues(studentNames, toPersonTransformer);

        Out.println("As persons:");
        AppUtils.printMap(persons);
        Out.println();

        Map<Integer, Integer> nCharacters = MapUtils.mapValues(studentNames, new Transformer<>() {
            @Override
            public Integer transform(String s) {
                return s.length();
            }
        });

        Out.println("Number of characters:");
        AppUtils.printMap(nCharacters);
        Out.println();

        // Test correct co-variance on output parameter
        Transformer<Object, String> toTypeName = new Transformer<>() {
            @Override
            public String transform(Object o) {
                return o.getClass().toString();
            }
        };

        Map<Integer, Object> types = MapUtils.mapValues(persons, toTypeName);

        Out.println("Types:");
        AppUtils.printMap(types);
    }
}
