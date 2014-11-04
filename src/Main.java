public class Main {

	static Kattio io;

	public static void main(String[] args) {
		io = new Kattio(System.in, System.out);
		/* Read matching problem */
		maximumMatching();
		/* Read flow problem */
		readMaxFlow();
		writeBipartiteSolution();

	}

	/**
	 * Example: 2 3 4 X = [1,2] Y = [3,4,5]
	 * 
	 * 
	 */
	private static void maximumMatching() {
		
		int[] X = new int[io.getInt()];
		int[] Y = new int[io.getInt()];
		/* Fill X */
		for (int i = 1; i < X.length + 1; i++) {
			X[i - 1] = i;
		}
		/* Fill Y */
		for (int i = X.length; i < X.length + Y.length; i++) {
			Y[i - X.length] = i;
		}
		/* Read edges */
		new EdgeList(X.length, Y.length);
		int numEdges = io.getInt();
		for (int i = 0; i < numEdges; i++) {
			/* */
			int x = io.getInt();
			int y = io.getInt();
			EdgeList.add(x,y);
		}
		Testing.printIntArray(EdgeList.numE);
		writeFlow(X.length, Y.length, Matching.findMaximum(X, Y));
	}

	private static void writeFlow(int Xl, int Yl, int[] solution) {
		io.println(Xl + " " + Yl);
		
		int numSolutions = 0;
		for (int i = 1; i < solution.length; i++) {
			if(!(solution[i] == 0))
				numSolutions++;
		}
		io.println(numSolutions);
		
		for (int i = 1; i < solution.length; i++) {
			if(!(solution[i] == 0))
			io.println(i + " " + solution[i]);
		}

		/* flush out */
		io.flush();
	}

	private static void readMaxFlow() {

	}

	private static void writeBipartiteSolution() {
		/* flush out */
		io.flush();
	}

}
