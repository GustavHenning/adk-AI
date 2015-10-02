
public class HMM {
	public double[] scale;
	/* emissions, initial, transitions, alpha, beta, gamma */
	private double[][] emis, init, trans, a, b, g;
	private double[][][] xi;

	/**
	 * Baum-Welcher Forward/Backward Hidden Markov Model. Assumes that all
	 * columns have same length.
	 * 
	 * @param emissions
	 * @param transitions
	 * @param initial
	 */
	public HMM(double[][] emissions, double[][] transitions, double[][] initial) {
		emis = emissions;
		trans = transitions;
		init = initial;
	}

	/**
	 * Reestimates transitions and emissions based on the sequence omitted.
	 * 
	 * @param seq:
	 *            The sequence to train on
	 */
	public void train(double[] seq) {
		if (g == null || xi == null) {
			System.err.println("HMM previously untrained");
		}
		forward(seq);
		backward(seq);
		gamma(seq);
		setEmissions(seq);
	}

	/* Calculates forward probabilities alpha(a) */
	private void forward(double[] seq) {
		a = new double[seq.length][lenRows(trans)];
		scale = new double[seq.length];

		/* init a */
		int s = (int) seq[0];
		for (int i = 0; i < lenRows(trans); i++) {
			a[0][i] = init[0][i] * emis[i][s];
			scale[0] += a[0][i];
		}
		/* apply scale */
		scale[0] = 1 / scale[0];
		for (int i = 0; i < lenRows(trans); i++) {
			a[0][i] *= scale[0];
		}
		/* set alpha(a) */
		for (int i = 1; i < seq.length; i++) {
			scale[i] = 0;
			for (int j = 0; j < lenRows(trans); j++) {
				double p = 0;
				for (int k = 0; k < lenRows(trans); k++) {
					p += a[i - 1][k] * trans[k][j];
				}
				s = (int) seq[i];
				a[i][j] = p * emis[j][s];
				scale[i] += a[i][j];
			}
			/* scale alpha(a) */
			scale[i] = 1 / scale[i];
			for (int j = 0; j < lenRows(trans); j++) {
				a[i][j] *= scale[i];
			}
		}
	}

	/* Calculates backwards probabilities beta(b) */
	private void backward(double[] seq) {
		int r = seq.length;
		int c = lenRows(trans);

		/* init beta(b) */
		b = new double[r][c];
		for (int i = 0; i < c; i++) {
			b[r - 1][i] = scale[r - 1];
		}
		/* set beta(b) */
		for (int i = r - 2; i >= 0; i--) {
			int s = (int) seq[i + 1]; /* cause for er? */
			for (int j = 0; j < c; j++) {
				b[i][j] = 0;

				for (int k = 0; k < c; k++) {
					b[i][j] += trans[j][k] * emis[k][s] * b[i + 1][k];
				}
				/* scale b */
				b[i][j] *= scale[i];
			}
		}
	}

	/* sets gamma(g) by calculating xi */
	private void gamma(double[] seq) {
		int r = seq.length;
		int c = lenRows(trans);

		g = new double[r][c];
		xi = new double[r][c][c];

		for (int i = 0; i < r - 1; i++) {
			/* get d */
			double d = 0;
			for (int j = 0; j < c; j++) {
				for (int k = 0; k < c; k++) {
					d += (a[i][j]) * (trans[j][k]) * (emis[k][(int) seq[i + 1]]) * (b[i + 1][k]);
				}
			}
			/* set xi */
			for (int j = 0; j < c; j++) {
				g[i][j] = 0;
				for (int k = 0; k < c; k++) {
					xi[i][j][k] = ((a[i][j]) * (trans[j][k]) * (emis[k][(int) seq[i + 1]] * b[i + 1][k])) / d;
					g[i][j] = xi[i][j][k];
				}
			}
		}

	}

	/**
	 * Assuming forward, backward and gamma are set, updates transitions and
	 * emissions.
	 * 
	 * @param seq
	 */
	private void setEmissions(double[] seq) {
		/* set init values */
		for (int i = 0; i < lenCols(init); i++) {
			init[0][i] = g[0][i];
		}
		/* update transitions */
		for (int i = 0; i < lenRows(trans); i++) {
			for (int j = 0; j < lenCols(trans); j++) {
				double t = 0, d = 0;
				for (int k = 0; k < seq.length; k++) {
					t += xi[k][i][j];
					d += g[k][i];
				}
				trans[i][j] = t / d;
			}
		}
		/* update emissions */
		for (int i = 0; i < lenRows(trans); i++) { /* emis? */
			for (int j = 0; j < lenCols(emis); j++) {
				double t = 0, d = 0;
				for (int k = 0; k < seq.length; k++) {
					if (seq[k] == j) {
						t += g[k][i];
					}
					d += g[k][i];
				}
				emis[i][j] = t / d;
			}
		}
	}

	/**
	 * Returns the emission probability distribution of state (i+1) assuming
	 * current state i
	 * 
	 * @return
	 */
	public double[][] nextEmissionProbabilities() {
		double[] next = new double[lenCols(init)];

		for (int i = 0; i < lenRows(trans); i++) {
			double state = init[0][i];
			for (int j = 0; j < lenCols(trans); j++) {
				next[j] += state * trans[i][j];
			}
		}
		
		double[][] dist = new double[1][lenCols(emis)];

		for (int j = 0; j < lenCols(emis); j++) {
			for (int i = 0; i < lenRows(emis); i++) {
				dist[0][j] += next[i] * trans[i][j];
			}
		}
		return dist;
	}

	/**
	 * @return The emissions of the current state
	 */
	public double[][] getEmissions() {
		return emis;
	}

	/**
	 * 
	 * @return The transitions of the current state
	 */
	public double[][] getTransitions() {
		return trans;
	}

	public double[][] getMatrix(Kattio io){
		return null;		
	}
	
	public void printMatrix(double[][] m, String name) {
		if (name != null)
			System.err.println(name);
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.printf("%d ", m[i][j]);
			}
			System.out.println();
		}
	}

	/* Helping methods */
	private int lenRows(double[][] ar) {
		if (ar.length <= 0) {
			System.err.println("RowLengthError: Rows of an array is of length 0. ");
			System.exit(1);
		}
		return ar.length > 0 ? ar.length : -1;
	}

	private int lenCols(double[][] ar) {
		if (ar.length <= 0 || (ar.length > 0 && ar[0].length == 0)) {
			System.err.println("ColLengthError: Columns of an array is of length 0. ");
			System.exit(1);
		}
		return ar.length > 0 ? ar[0].length : -1;
	}
}
