import java.util.Stack;

import sun.misc.Queue;

public class Matching {

	static int[] maximum;
	static boolean[] matched;

	public static int[] findMaximum(int[] X, int[] Y) {
		maximum = new int[X.length + 1];
		matched = new boolean[X.length + Y.length + 1];

		calc(X,Y);
		return maximum;
	}

	public static void calc(int[] X, int[] Y) {
		Queue queue = new Queue();

		for (int x : X) {
			int nEx = EdgeList.numE[x];

			if (nEx > 0) {
				if (nEx == 1) {
					mark(x, EdgeList.edgesFromX(x)[0], true);
				} else {
					visit(x, queue);
				}
			}
		}
		while(!queue.isEmpty()){
			try {
				int next = (Integer) queue.dequeue();
				} catch (InterruptedException e) {e.printStackTrace();}
			
		}
		
	}

	private static void visit(int v, Queue queue) {
		// Alla kanter från v
		int[] edgesToV;
		if (v <= maximum.length) {
			edgesToV = EdgeList.edgesFromX(v);
		} else {
			edgesToV = EdgeList.edgesFromY(v);
		}
		// Tar den kant med minst antal kanter
		int smallestEdge = 0;
		for (int e : edgesToV) {
			if (!matched[e]) {
				int numE = EdgeList.numE[e];
				if (numE < EdgeList.numE[smallestEdge] || smallestEdge == 0) {
					smallestEdge = e;
				}
			}
		}
		mark(v, smallestEdge, true);
		// Köar resten
		for (int e : edgesToV) {
			if (e != smallestEdge && !matched[e]) {
				queue.enqueue(e);
			}
		}

	}

	/**
	 * Marks a pair of x,y as matched. Can be used to do the opposite
	 * 
	 * @param x
	 * @param y
	 */
	public static void mark(int x, int y, boolean match) {
		maximum[x] = match ? y : 0;
		matched[x] = match;
		matched[y] = match;

	}
}
