
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class Test {
	Roleplay r;
	public Test() {
		System.out.println("y");
		test("2 1 2" + " 1 2");
		System.out.println("y");
		test("3 3 3" + " 1 2" + " 2 3" + " 3 1");
		System.out.println("y");
		test("4 3 2" + " 1 2 2 3 3 4");
		System.out.println("n");
		test("4 6 2" + " 1 2 2 3 3 4 1 4 2 4 1 3");
		System.out.println("n");
		test("4 6 3" + " 1 2 2 3 3 4 1 4 2 4 1 3");
		System.out.println("y");
		test("4 6 4" + " 1 2 2 3 3 4 1 4 2 4 1 3");
		r.io.close();
	}

	public void test(String t) {
		System.out.println("Test: " + t);
		System.out.println();
		ByteArrayInputStream stream = new ByteArrayInputStream(
				t.getBytes(StandardCharsets.UTF_8));
		System.setIn(stream);
		r = new Roleplay(); // set debug to true
		System.out.println();
	}

	public static void main(String[] args) {
		new Test();
	}
}
