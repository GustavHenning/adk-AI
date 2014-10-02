public class EdgeList {
	public static boolean[][] edges;
	public static int[] numE;

	public EdgeList(int sizeX, int sizeY) {
		edges = new boolean[sizeX + 1][sizeY + 1];
		numE = new int[sizeX + sizeY + 1];
	}

	public static void add(int x, int y) {
		edges[x][y - (edges.length-1)] = true;
		numE[x]++;
		numE[y]++;
	}

	public static int[] edgesFromX(int x) {
		int[] E = new int[numE[x]];
		int eAdded = 0;
		for (int j = 1; j < edges[x].length; j++) {
			if (edges[x][j]) {
				E[eAdded] = j + (edges.length-1);
				eAdded++;
			}
		}
		Testing.printIntArray(E);
		return E;
	}

	public static int[] edgesFromY(int y) {
		int[] E = new int[numE[y]];
		int eAdded = 0;
		for (int i = 1; i < edges.length; i++) {
			if (edges[i][y - (edges.length-1)]) {
				E[eAdded] = i;
				eAdded++;
			}
		}
		return E;
	}
}
