import java.util.Scanner;

public class Main {
	static double[][] P_trans, P_emission;
	static double[] P_init;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int matricesRecieved = 0;

		while (sc.hasNext() || matricesRecieved < 3) {
			fillMatrix(sc, matricesRecieved);
			matricesRecieved++;
		}
	}

	
	public static void fillMatrix(Scanner sc, int matRec) {
		int x = sc.nextInt(), y = sc.nextInt();
		switch (matRec) {
		case 0: // P_trans
			P_trans = new double[x][y];
			System.err.println("P_Trans");
			for (int i = 0; i < P_trans.length; i++) {
				for (int j = 0; j < P_trans[i].length; j++) {
					P_trans[i][j] = sc.nextDouble();
					System.err.print(P_trans[i][j] + " ");
				}
				System.err.println();
			}
			break;
		case 1: // P_emiss
			P_emission = new double[x][y];
			System.err.println("P_Emiss");
			for (int i = 0; i < P_emission.length; i++) {
				for (int j = 0; j < P_emission[i].length; j++) {
					P_emission[i][j] = sc.nextDouble();
					System.err.print(P_emission[i][j] + " ");
				}
				System.err.println();
			}
			break;
		case 2: // P_init
			P_init = new double[y];
			System.err.println("P_init");
			for (int i = 0; i < P_init.length; i++) {
				P_init[i] = sc.nextDouble();
				System.err.print(P_init[i] + " ");
			}
			System.err.println();
			break;
		}
	}

}