public class Matching {

	static int[] maximum;
	static boolean[] xMatched;
	static boolean[] yMatched;

	public static int[] findMaximum(int[] X, int[] Y) {
		maximum = new int[X.length + 1];
		xMatched = new boolean[X.length + 1];
		yMatched = new boolean[Y.length + 1]; 

		/*
		 * All edges that have vertices with one edge only are automatically in
		 * the matching
		 */
		markLoners();
		System.out.println("Loners marked");
		Testing.printIntArray(maximum);
		/*
		 * For each vertice Y not in maximum choose it's X with least edges that
		 * are also not in maximum
		 */
		markProbable(X, Y);
		System.out.println("Probables marked");
		Testing.printIntArray(maximum);

		/* While not maximum, trace back and re match */

		return maximum;
	}

	/**
	 * Marks a pair of x,y as matched. Can be used to do the opposite
	 * 
	 * @param x
	 * @param y
	 */
	public static void mark(int x, int y, boolean matched) {
		maximum[x] = matched ? y : 0;
		xMatched[x] = matched;
		yMatched[y + 1 - xMatched.length] = matched;
	}

	/**
	 * Matches all the X,Y vertices that have only one edge on either side.
	 */
	private static void markLoners() {
		/* Loop all values */
		for (int i = 1; i < EdgeList.numE.length; i++) {
			if (EdgeList.numE[i] == 1) {
				/* These are X */
				if (i < EdgeList.edges.length) {
					int y = EdgeList.edgesFromX(i)[0];
					if (!yMatched[y + 1 - xMatched.length])
						mark(i, y, true);
				} else {/* Y values */
					/* Solo pair may be reverted unless checked */
					if (!yMatched[i + 1 - xMatched.length]) {
						mark(EdgeList.edgesFromY(i)[0], i, true);
					}
				}
			}
		}

	}

	/*
	 * For each vertice Y not in maximum mark it's edge in X with least edges
	 * that are also not in maximum
	 */
	private static void markProbable(int[] X, int[] Y) {
		for (int i = 0; i < Y.length; i++) {
			int y = Y[i];
			if (!yMatched[i+1]) {
				int[] edgesFromY = EdgeList.edgesFromY(y);
				int xToMatch = 0;
				for (int j = 0; j < edgesFromY.length; j++) {
					int x = edgesFromY[j];
					if (!xMatched[x]) {
						/*
						 * Here there may be problems with them being equal and
						 * not choosing the right one
						 */
						if (xToMatch == 0
								|| EdgeList.edgesFromX(x).length < EdgeList
										.edgesFromX(xToMatch).length) {
							xToMatch = x;
						}
					}
				}
				if (xToMatch != 0) {
					mark(xToMatch, y, true);
				}
			}
		}
	}

}
