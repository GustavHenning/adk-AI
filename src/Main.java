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
				fE.add(x + 1, y.a + 1, 1);
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
//		M = new int[fg.V.length + 1];
		long f = 0;
		int[][] F = new int[fg.V.length + 1][fg.V.length + 1];

		BFSResult bfsr = null;
//		while (true) {
			try {
				bfsr = BFS(fg, F);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			if (bfsr.m == 0) {
//				break;
//			}
//			f = f + bfsr.m;
//
//			int v = fg.t;
//			while (v != fg.s) {
//				int u = bfsr.p[v];
//				if (F[u][v] == 0) {
//					numE++;
//				}
//				F[u][v] = F[u][v] + bfsr.m;
//				F[v][u] = F[v][u] - bfsr.m;
//				v = u;
//			}
			int numE = 0;
			for(int i = 0; i < F.length; i++){
				for(int j = 0; j < F[i].length; j++){
					if(F[i][j] > 0){
						numE++;
					}
				}
			}
//		}
//			Testing.printIntArray(bfsr.p);
		return new EdKarpRes(fg.V.length, fg.s, fg.t, bfsr.m, numE, F);
	}

	public static BFSResult BFS(FlowGraph fg, int[][] F)
			throws InterruptedException {
		// Arrays.fill(P, -1);
		// P[fg.s] = -2;
		// M[fg.s] = Integer.MAX_VALUE;
		int flow = 0;
		while (true) {
			Arrays.fill(P, -1);
			ArrayDeque<Integer> q = new ArrayDeque<Integer>();
			P[fg.s] = -2;
//			M[fg.s] = Integer.MAX_VALUE;

			q.push(fg.s);
			while (!q.isEmpty() && P[fg.t] == -1) {
				int u = q.pop();
				// System.out.println(q.size() + " " + P[fg.t] + " " + fg.t);
				for (Edge v : fg.E.listByX(u)) {
					if (P[v.a] == -1) {
						if (F[u][v.a] < v.b || F[v.a][u] > 0) {
							// System.out.println(F[u][v.a] +" " + F[v.a][u] +
							// " " + v.b );
							P[v.a] = u;
							q.push(v.a);
						}
					}
				}
				// Testing.printIntArray(P);
			}

			if (P[fg.t] == -1) {
				break;
			}
			int bot = Integer.MAX_VALUE;
			int u = Integer.MAX_VALUE;
//			System.out.println("bot " + bot);
			for (int v = fg.t; u >= 0; v = u) {
				u = P[v];
				if (u == -2)
					break;
				if (F[v][u] > 0) {
//					System.out.println(F[v][u]);
					bot = Math.min(bot, F[v][u]);
				} else {
					for (Edge e : fg.E.listByX(u)) {
						if (e.a == v) {
							bot = Math.min(bot, e.b - F[u][v]);
							break;
						}
					}
				}
				
			}
			u = 2;
			for (int v = fg.t; u >= 0; v = u) {
				u = P[v];
				if (u == -2)
					break;
				if (F[v][u] > 0) {
					F[v][u] -= bot;
				} else {
					F[u][v] += bot;
				}
			}
			flow += bot;
		}
		return new BFSResult(flow, P);

	}

	// ArrayDeque<Integer> q = new ArrayDeque<Integer>();
	// q.offer(fg.s);
	// while (!q.isEmpty()) {
	// int u = (Integer) q.poll();
	// for (Edge v : fg.E.listByX(u)) {
	// if ((v.b - F[u][v.a] > 0) && P[v.a] == -1) {
	// P[v.a] = u;
	// M[v.a] = Math.min(M[u], v.b - F[u][v.a]);
	// if (v.a != fg.t) {
	// q.offer(v.a);
	// } else {
	// return new BFSResult(M[fg.t], P);
	// }
	// }
	//
	// }
	// }

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
				io.println(x + " " + y.a + " " + 1);
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
