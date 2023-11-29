import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String content = "/user";

        String pattern = "/user.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
    }
}
