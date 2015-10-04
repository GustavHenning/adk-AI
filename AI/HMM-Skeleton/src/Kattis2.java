
public class Kattis2 {
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);

		double[][] transM = HMM.getMatrix(io), emisM = HMM.getMatrix(io), initM = HMM.getMatrix(io);

		double[] seq = HMM.getVector(io);

		// HMM.printMatrix(transM, "transM");
		// HMM.printMatrix(emisM, "emisM");
		// HMM.printMatrix(initM, "initM");
		HMM hmm = new HMM(transM, emisM, initM);
		
		/* Note: We disable scaling here */
		hmm.forward(seq);
//		HMM.printMatrix(hmm.a, "alpha");

		double p = 0;
		for (int i = 0; i < hmm.lenRows(transM); i++) {
			p += hmm.a[seq.length - 1][i];
		}
		System.out.println(p);

		io.close();
	}
}
