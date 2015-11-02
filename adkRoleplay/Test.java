


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class Test {
	public Test() {
		test("2 1 2" +
				" 1 2");
	}

	public void test(String t) {
		ByteArrayInputStream stream = new ByteArrayInputStream(
				t.getBytes(StandardCharsets.UTF_8));
		System.setIn(stream);
		new Roleplay();
	}

	public static void main(String[] args) {
		new Test();
	}
}
