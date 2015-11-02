public class Roleplay {
	final boolean DEBUG = true;

	/*
	 * In: vertices: (v>=1) edges: (e>=0) goal: (m=>1)
	 * 
	 * for(e) e.x e.y Out: roles (n=>1) scenes: (s=>1) actors: (k=>2) for(roles)
	 * k1, k2 ... etc for(scenes) n1, n2 ... etc
	 */

	public Roleplay() {
		Kattio io = new Kattio(System.in, System.out);
		int v = io.getInt();
		int e = io.getInt();
		int m = io.getInt();

		int n = v + 1;
		int s = e;
		int k = m + 1;

		if (k < 3)
			k = 3;
		if (DEBUG)
			System.out.println("n s k");
		System.out.println(n + " " + 1 + " " + k);
		/* Let all roles be played by any actor (except divas) */
		StringBuilder sb = new StringBuilder("" + (k - 2));
		for (int i = 1; i <= k - 2; i++) {
			sb.append(" " + (i + 2));
		}
		/* roles */
		if (DEBUG)
			System.out.println("roles");
		for (int i = 1; i < n; i++) {
			System.out.println(sb.toString());
		}
		System.out.println("2 1 2"); // last fictional node have divas 1 & 2
		if (DEBUG)
			System.out.println("scenes");

		int[] val = new int[n];
		for (int i = 0; i < s; i++) {
			val[io.getInt()]++;
			val[io.getInt()]++;
		}
		int maxVal = 1;
		for (int i = 1; i < val.length; i++) {
			maxVal = (val[i] > maxVal) ? val[i] : maxVal;
		}
		sb = new StringBuilder((val[maxVal] + 1) + " ");
		for (int j = 1; j < (val[maxVal] + 1); j++) {
			sb.append(j + " ");
		}
		sb.append(n);
		System.out.println(sb.toString());
		// for (int i = 0; i < s; i++) {
		// int n1 = io.getInt();
		// int n2 = io.getInt();
		// System.out.println("3 " + n1 + " " + n2 + " " + n);
		// }
		if (!DEBUG)
			io.close();
	}

	public static void main(String[] args) {
		new Roleplay();
	}
}