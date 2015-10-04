/* 
 * In this task you should show that you know how 
 * to estimate the model parameters for an HMM. 
 * You will be given a starting guess of a HMM (transition matrix, 
 * emission matrix and initial state probability distribution) and 
 * a sequence of emissions and you should train the HMM to maximize the 
 * probability of observing the given sequence of emissions.
 * */
public class Kattis4 {
	private static final int MAX_TRAIN = 1000;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		double[][] transM = HMM.getMatrix(io), emisM = HMM.getMatrix(io), initM = HMM.getMatrix(io);

		double[] seq = HMM.getVector(io);

		HMM hmm = new HMM(transM, emisM, initM);

		/**
		 * We train the HMM until the sum of the scale converges.
		 * 
		 * https://hips.seas.harvard.edu/blog/2013/01/09/computing-log-sum-exp/
		 */
		double pDeclineLimit = Integer.MIN_VALUE;
		for (int i = 0; i < MAX_TRAIN; i++) {
			hmm.train(seq);
			
			double p = 0;
			for (int j = 0; j < seq.length; j++) {
				p += Math.log10(hmm.scale[i]);
			}
			p *= -1;
			if (p > pDeclineLimit) {
				pDeclineLimit = p;
			} else {
				System.err.println(i + " times trained");
				break;
			}
		}
		HMM.printVector(hmm.scale);
		prettyPrint(hmm.trans);
		prettyPrint(hmm.emis);

	}

	public static void prettyPrint(double[][] mat) {
		int r = HMM.lenRows(mat);
		int c = HMM.lenCols(mat);
		System.out.print(r + " " + c);
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				System.out.printf(" %f", mat[i][j]);
			}
		}
		System.out.println();
	}
}
