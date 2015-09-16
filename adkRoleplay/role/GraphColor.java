package role;

import java.util.ArrayList;

public class GraphColor {
	int[] C;
	int V;

	public GraphColor() {

	}

	boolean solvableGraph(int V, ArrayList<Edge>[] edges, int m) {
		C = new int[V + 1];
		int next = -1;

		while ((next = notColored()) != -1) {
			int nextColor = 1;
			boolean colorUnique = false;

			while (!colorUnique) {
				if (next < edges.length && edges[next] != null) {
					boolean same = false;
					for (Edge e : edges[next]) {
						if (C[e.y] == nextColor) {
							nextColor++;
							if (nextColor > m) {
								return false;
							}
							same = true;
						}
					}
					colorUnique = !same;
				} else {
					break; /* No edges: just assign a color */
				}
			}
			C[next] = nextColor;
		}
		System.err.println("terminated");
		for(int i = 0; i < C.length; i++){
			System.err.println("c " + C[i]);
		}
		return true;
	}

	/* Should choose by number of edges */
	private int notColored() {
		for (int i = 1; i < C.length; i++) {
			if (C[i] == 0) {
				return i;
			}
		}
		return -1;
	}
}