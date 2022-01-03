import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Feature {
    public static void main(String[] args) {
        featuresJava11();
    }

    public static void featuresJava11() {
        System.out.println("-Features Java 11");

        java11StringApi();
    }

    private static void java11StringApi() {
        System.out.println("--String API");

        System.out.println("Trim");
        String s = " dev aaa \t\t ";
        System.out.println("***" + s + "***");

        String s1 = s.trim();
        System.out.println("***" + s1 + "***");
        System.out.println("---------------");

        System.out.println("Repeat");
        String s2 = "Abc";
        System.out.println(s2.repeat(3));
        System.out.println("---------------");

        System.out.println("Strip");
        String s3 = " Lorem Ipsum ";
        System.out.println(s3);
        System.out.println(s3.strip());
        System.out.println(s3.stripLeading());
        System.out.println(s3.stripTrailing());
        System.out.println("---------------");

        System.out.println("Is blank");
        String s4 = "";
        String s5 = "Loren";
        System.out.println(s4.isBlank());
        System.out.println(s5.isBlank());
        System.out.println("---------------");

        System.out.println("Lines");
        String s6 = "Lorem\nIpsum\nis simply\ndummy text";
        s6.lines().forEach(System.out::println);
        System.out.println("---------------");

        System.out.println("To array");
        List<String> stringList = new ArrayList<>();
        stringList.add("Lorem");
        stringList.add("Ipsum");
        stringList.add("Is simply");
        String[] stringArray1 = stringList.toArray(new String[stringList.size()]); //Before
        String[] stringArray2 = stringList.toArray(String[]::new);//Java 11
        System.out.println(Arrays.toString(stringArray1));
        System.out.println(Arrays.toString(stringArray2));
        System.out.println("---------------");
    }
}
