//public class Matching {
//
//	static int[] maximum;
//	static boolean[] matched;
//
//	public static int[] findMaximum(BipGraph bg) {
//		bg.E.precalc();
//		maximum = new int[bg.X.length + 1];
//		matched = new boolean[bg.X.length + bg.Y.length + 1];
//
//		iterMatch(bg.X, bg.E);
//		return maximum;
//	}
//
//	public static void iterMatch(int[] X, EdgeList E) {
//		for (int x : X) {
//			int[] edgesToX = E.edges(x);
//			boolean found = false;
//			for (int y : edgesToX) {
////				System.out.println(x + " " + y);
//				if (!matched[y]) {
//					mark(x, y, true);
//					found = true;
//					break;
//				}
//				// Alla y upptagna
//			}
//			if (!found) {
//				for (int y : edgesToX) {
//					if (remap(y, E)) {
//						mark(x, y, true);
//						break;
//					}
//				}
//			}
//		}
//	}
//
//	private static boolean remap(int y, EdgeList E) {
//		int x = xFromY(y);
//		for (int yBis : E.edges(x)) {
//			if (!matched[yBis]) {
//				mark(x, y, false);
//				mark(x, yBis, true);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public static int xFromY(int y) {
//		for (int i = 0; i < maximum.length; i++) {
//			if (maximum[i] == y)
//				return i;
//		}
//		return -1;
//	}
//
//
//	/**
//	 * Marks a pair of x,y as matched. Can be used to do the opposite
//	 * 
//	 * @param x
//	 * @param y
//	 */
//	public static void mark(int x, int y, boolean match) {
//		maximum[x] = match ? y : 0;
//		matched[x] = match;
//		matched[y] = match;
////		System.out.println(x + " " + y);
//	}
//}
