import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;

;

public class Main {
	static int[] P;
	static int[] M;

	/**
	 * Example: 2 3 4 X = [1,2] Y = [3,4,5]
	 * 
	 * 
	 */
	public static BipGraph readMatchingProblem(Kattio io) {
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
		EdgeList E = new EdgeList(X.length + Y.length);
		int numEdges = io.getInt();
		for (int i = 0; i < numEdges; i++) {
			/* */
			int x = io.getInt();
			int y = io.getInt();
			E.add(x, y, 1);
		}
		return new BipGraph(X, Y, E);
	}

	/**
	 * Reduces a matching problem to a flow problem
	 * 
	 * @param X
	 * @param Y
	 */
	public static FlowGraph matchingToFlow(BipGraph bg) {
		int[] fX = new int[bg.X.length + 1];
		int[] fY = new int[bg.Y.length + 1];

		/* translate vertices */
		for (int i = 0; i < fX.length; i++) {
			fX[i] = i + 1;
		}
		for (int i = 0; i < fY.length; i++) {
			fY[i] = fX.length + i + 1;
		}

		/* translate edges */
		EdgeList fE = new EdgeList(fX.length + fY.length);
		/* add e(x,s) to fE */
		for (int i = 1; i < fX.length; i++) {
			fE.add(fX[0], fX[i], 1);
		}
		/* add E to fE */
		for (int x = 0; x < bg.E.edges.length; x++) {
			for (Edge y : bg.E.listByX(x)) {
				fE.add(x + 1, y.y + 1, 1);
			}
		}
		/* add e(y,t) to S */
		for (int i = 0; i < fY.length - 1; i++) {
			fE.add(fY[i], fY[fY.length - 1], 1);
		}
		/* create capacity */

		return new FlowGraph(fX, fY, fE, 1, fY[fY.length - 1]);
	}

	/**
	 * Translates a flow solution to a matching solution.
	 * 
	 * @param fg
	 * @return
	 */
	public static BipGraph flowToMatching(FlowGraph fg) {

		return new BipGraph(null, null, null);
	}

	public static EdKarpRes edKarp(FlowGraph fg) {
		P = new int[fg.V.length + 1];
		M = new int[fg.V.length + 1];
		long f = 0;
		int numE = 0;
		int[][] F = new int[fg.V.length + 1][fg.V.length + 1];

		BFSResult bfsr = null;
		while (true) {
			try {
				bfsr = BFS(fg, F);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (bfsr.m == 0) {
				break;
			}
			f = f + bfsr.m;

			int v = fg.t;
			while (v != fg.s) {
				int u = bfsr.p[v];
				 System.err.println("Path " + u + " -> " + v + " : " +
				 bfsr.m);
				F[u][v] = F[u][v] + bfsr.m;
				F[v][u] = F[v][u] - bfsr.m;
				v = u;
			}
		}
		for (int i = 0; i < F.length; i++) {
			for (int j = 0; j < F[i].length; j++) {
//				 System.err.print(" " + F[i][j]);
				if (F[i][j] > 0) {
					for (Edge e : fg.E.listByX(i)) {
						if (e.y == j && !e.counted && !e.inverse) {
							numE++;
							e.counted = true;
						}
					}
				}
			}
//			 System.err.println();
		}
		return new EdKarpRes(fg.V.length, fg.s, fg.t, f, numE, F);
	}

	public static BFSResult BFS(FlowGraph fg, int[][] F)
			throws InterruptedException {
		Arrays.fill(P, -1);
		P[fg.s] = -2;
		M[fg.s] = Integer.MAX_VALUE;

		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		q.offer(fg.s);
		while (!q.isEmpty()) {
			int x = q.poll();
			for (Edge e : fg.E.listByX(x)) {
				int c = e.inverse ? (F[x][e.y] - e.capacity) : (e.capacity
						- F[x][e.y]);
				 System.err.println("Edge from " + x + " to " + e.y + " : "
				 + c);
				if ((c > 0) && P[e.y] == -1) {
					P[e.y] = x;
					M[e.y] = Math.min(M[x], c);
					if (e.y != fg.t) {
						q.offer(e.y);
					} else {
						return new BFSResult(M[fg.t], P);
					}
				}

			}
		}
		return new BFSResult(0, P);
	}

	public static FlowGraph readFlowProblem(Kattio io) {
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int numE = io.getInt();

		int[] V = new int[v];
		for (int i = 0; i < v; i++) {
			V[i] = i + 1;
		}

		EdgeList E = new EdgeList(v + 1);

		for (int i = 0; i < numE; i++) {
			E.add(io.getInt(), io.getInt(), io.getInt());
		}
		return new FlowGraph(V, E, s, t);
	}

	public static void writeFlowProblem(Kattio io, FlowGraph fg) {
		io.println(fg.X.length + fg.Y.length);
		io.println(fg.s + " " + fg.t);
		io.println(fg.E.numEdges);
		for (int x = 0; x < fg.E.edges.length; x++) {
			for (Edge y : fg.E.listByX(x)) {
				io.println(x + " " + y.y + " " + 1);
			}
		}
		io.flush();
	}

	public static BipGraph readFlowToMatching(Kattio io, FlowGraph fg) {
		int t = io.getInt();
		io.getInt();
		io.getInt();
		io.getInt();
		EdgeList bE = new EdgeList(fg.X.length + fg.Y.length);
		int numE = io.getInt();
		for (int i = 0; i < numE; i++) {
			int x = io.getInt();
			int y = io.getInt();
			io.getInt();
			if (x == 1 || y == t) {
				continue;
			}
			bE.add(x - 1, y - 1, 1);
		}
		int[] bX = new int[fg.X.length - 1];
		int[] bY = new int[fg.Y.length - 1];
		for (int i = 0; i < bX.length; i++) {
			bX[i] = fg.X[i];
		}
		for (int i = 0; i < bY.length; i++) {
			bY[i] = fg.Y[i] - 1;
		}
		BipGraph bg = new BipGraph(bX, bY, bE);
		return bg;
	}

	public static void writeMatchingSolution(Kattio io, BipGraph bg) {
		// io.println(Xl + " " + Yl);
		//
		// int numSolutions = 0;
		// for (int i = 1; i < solution.length; i++) {
		// if(!(solution[i] == 0))
		// numSolutions++;
		// }
		// io.println(numSolutions);
		//
		// for (int i = 1; i < solution.length; i++) {
		// if(!(solution[i] == 0))
		// io.println(i + " " + solution[i]);
		// }
		//
		// /* flush out */
		// io.flush();
	}

}
