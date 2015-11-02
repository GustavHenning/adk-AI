public class Roleplay {
	final boolean DEBUG = false;
	final String WS = " ";
	Kattio io = new Kattio(System.in, System.out);

	/*
	 * In: vertices: (v>=1) edges: (e>=0) goal: (m=>1)
	 * 
	 * for(e) e.x e.y Out: roles (n=>1) scenes: (s=>1) actors: (k=>2) for(roles)
	 * k1, k2 ... etc for(scenes) n1, n2 ... etc
	 */

	public Roleplay() {
		int v = io.getInt();
		int e = io.getInt();
		int m = io.getInt();

		int n = v + 3;
		int s = e + 2;
		int k = m + 3;

		if (DEBUG)
			io.println("n s k");
		io.println(n + WS + s + WS + k);

		if (DEBUG)
			io.println("roles");
		/* roles base case */
		io.println(1 + WS + 1);
		io.println(1 + WS + 2);
		io.println(1 + WS + 3);

		StringBuilder sb = new StringBuilder();
		sb.append((k - 3) + WS);

		for (int i = 4; i < k; i++) {
			sb.append(i + WS);
		}
		sb.append(k);
		/* rest of roles by all actors */
		for (int i = 4; i <= n; i++) {
			io.println(sb.toString());
		}

		if (DEBUG)
			io.println("scenes");
		/* scenes base case */
		io.println(2 + WS + 1 + WS + 3);
		io.println(2 + WS + 2 + WS + 3);
		/* rest of scenes = edges shifted by divas */
		for (int i = 3; i <= s; i++) {
			io.println("2 " + (io.getInt() + 3) + WS + (io.getInt() + 3));
		}


		if (!DEBUG)
			io.close();
	}

	public static void main(String[] args) {
		new Roleplay();
	}
}