package roles;

public class Test {
	
	public Test() {
		String[] t1 = {"5 5 3",
				"3 1 2 3",
				"2 2 3",
				"2 1 3",
				"1 2",
				"3 1 2 3",
				"2 1 2",
				"2 1 2",
				"3 1 3 4",
				"2 3 5",
				"3 2 3 5"};
		System.out.println("Test 1: false : " + new Roleplay(t1).solvable());
		
		String[] t2 = {
				"6 5 4",
				"3 1 3 4",
				"2 2 3",
				"2 1 3",
				"1 2",
				"4 1 2 3 4",
				"2 1 4",
				"3 1 2 6",
				"3 2 3 5",
				"3 2 4 6",
				"3 2 3 6",
				"2 1 6"
		};
		System.out.println("Test 2: true : " + new Roleplay(t2).solvable());
	}
	
	public static void main(String[] args){
		new Test();
	}
}