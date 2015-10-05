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
		double erLimit = 1.0E-5;
		int i = 0;
		hmm.train(seq);
		while(i < MAX_TRAIN) {
			double before = logScaleSum(hmm.scale);
			hmm.train(seq);
			System.out.println(Math.abs(logScaleSum(hmm.scale) - before));
			if(Math.abs(logScaleSum(hmm.scale) - before) < erLimit){
				System.err.println(i + " iterations");
				break;
			}
			
			
//			double p = 0;
//			for (int j = 0; j < seq.length; j++) {
//				p += Math.log(hmm.scale[i]) / Math.log(10);
//			}
//			p *= -1;
//			if (p > pDeclineLimit) {
//				pDeclineLimit = p;
//			} else {
//				System.err.println(i + " times trained");
//				break;
//			}
			i++;
		}
		System.err.println("trans");
		prettyPrint(hmm.trans);
		System.err.println("emis");
		prettyPrint(hmm.emis);

	}

	public static double logScaleSum(double[] scale){
		double sum = 0;
		for(int i = 0; i < scale.length; i++){
			sum += Math.log10(scale[i]);
		}
		return sum;
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
