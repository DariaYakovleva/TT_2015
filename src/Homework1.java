import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Homework1 {
	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("tests/HW1/task1.in"));
		System.setOut(new PrintStream("tests/HW1/task1.out"));
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		String res = LambdaParser.parse(s).printExp();
		System.err.println(res);
		System.out.println(res);
		in.close();
	}
}
