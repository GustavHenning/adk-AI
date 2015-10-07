
public class HMM {
	public double[] scale;
	/* emissions, initial, transitions, alpha, beta, gamma */
	protected double[][] emis, init, trans, a, b, g;
	protected double[][][] xi;

	private final double UNDERFLOW_PREV = 1.0E-20;

	/**
	 * Baum-Welcher Forward/Backward Hidden Markov Model.
	 * 
	 * @param transitions
	 * @param emissions
	 * @param initial
	 */
	public HMM(double[][] transitions, double[][] emissions, double[][] initial) {
		trans = transitions;
		emis = emissions;
		init = initial;
	}

	/**
	 * Creates a HMM for the Bird shooting problem
	 * 
	 * @param numMoves
	 * @param numSpecies
	 */
	public HMM(int numMoves, int numSpecies) {
		int N = numSpecies;
		int T = numMoves;
		init = new double[1][N];
		trans = new double[N][N];
		emis = new double[T][N]; /* switch T, N ? */

		/* matrices start values */
		for (int i = 0; i < N; i++) {
			init[0][i] = (1.0 / N);
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				trans[i][j] = (1.0 / N);
			}
		}
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < N; j++) {
				emis[i][j] = (1.0 / T);
			}
		}

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
	protected void forward(double[] seq) {
		a = new double[seq.length][lenRows(trans)];
		scale = new double[seq.length];
		/* init a */
		int s = (int) seq[0];
		for (int i = 0; i < lenRows(trans); i++) {
			a[0][i] = init[0][i] * emis[i][s] + UNDERFLOW_PREV;
			scale[0] += a[0][i];
		}
		/* apply scale */
		scale[0] = 1.0 / scale[0];
		for (int i = 0; i < lenRows(trans); i++) {
			a[0][i] *= scale[0];
		}
		/* set alpha(a) */
		for (int i = 1; i < seq.length; i++) {
			for (int j = 0; j < lenRows(trans); j++) {
				double p = 0;
				for (int k = 0; k < lenRows(trans); k++) {
					p += a[i - 1][k] * trans[k][j];
				}
				s = (int) seq[i];
				a[i][j] = p * emis[j][s] + UNDERFLOW_PREV;
				scale[i] += a[i][j];
			}
			/* scale alpha(a) */
			scale[i] = 1.0 / scale[i];
			for (int j = 0; j < lenRows(trans); j++) {
				a[i][j] *= scale[i];
			}
		}
	}

	/* Calculates backwards probabilities beta(b) */
	protected void backward(double[] seq) {
		int r = seq.length;
		int c = lenRows(trans);

		/* init beta(b) */
		b = new double[r][c];
		for (int i = 0; i < c; i++) {
			b[r - 1][i] = scale[r - 1];
		}
		/* set beta(b) */
		for (int i = r - 2; i >= 0; i--) {
			int s = (int) seq[i + 1];
			for (int j = 0; j < c; j++) {
				b[i][j] = 0;

				for (int k = 0; k < c; k++) {
					b[i][j] += trans[j][k] * emis[k][s] * b[i + 1][k] + UNDERFLOW_PREV;
				}
				/* scale b */
				b[i][j] *= scale[i];
			}
		}
	}

	/* sets gamma(g) by calculating xi */
	protected void gamma(double[] seq) {
		int r = seq.length;
		int c = lenRows(trans);

		g = new double[r][c];
		xi = new double[r][c][c];

		for (int i = 0; i < r - 1; i++) {
			/* get d */
			double d = 0;
			for (int j = 0; j < c; j++) {
				for (int k = 0; k < c; k++) {
					d += (a[i][j]) * (trans[j][k]) * (emis[k][(int) seq[i + 1]]) * (b[i + 1][k]) + UNDERFLOW_PREV;
				}
			}
			/* set xi */
			for (int j = 0; j < c; j++) {
				g[i][j] = 0;
				for (int k = 0; k < c; k++) {
					xi[i][j][k] = (((a[i][j]) * (trans[j][k]) * (emis[k][(int) seq[i + 1]]) * (b[i + 1][k])) / d)
							+ UNDERFLOW_PREV;
					g[i][j] += xi[i][j][k];
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
	protected void setEmissions(double[] seq) {
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
				trans[i][j] = (t / d) + UNDERFLOW_PREV;
			}
		}
		/* update emissions */
		for (int i = 0; i < lenRows(trans); i++) { /* emis? */
			for (int j = 0; j < lenCols(emis); j++) {
				double t = 0, d = 0;
				for (int k = 0; k < seq.length - 1; k++) {
					if (seq[k] == j) {
						t += g[k][i];
					}
					d += g[k][i];
				}
				emis[i][j] = (t / d) + UNDERFLOW_PREV;
			}
		}
	}

	/**
	 * Returns the emission probability distribution of state (i+1) assuming
	 * current state i
	 * 
	 * @return Distribution dist from 1 to N where N is the emission length
	 */
	public double[] nextEmissionProbabilities() {
		double[] next = new double[lenCols(init)];

		for (int i = 0; i < lenRows(trans); i++) {
			double state = init[0][i];
			for (int j = 0; j < lenCols(trans); j++) {
				next[j] += state * trans[i][j];
			}
		}

		double[] dist = new double[lenCols(emis)];

		for (int j = 0; j < lenCols(emis); j++) {
			for (int i = 0; i < lenRows(emis); i++) {
				dist[j] += next[i] * emis[i][j];
			}
		}
		return dist;
	}
	/**
	 * For Bird shooting, made estimation of matrices into a separate method.
	 * @param erLimit
	 * @param maxTrain
	 * @param seq
	 */
	public void estimateMatrices(double erLimit, int maxTrain, double[] seq){
		int i = 0;
		train(seq);
		while (i < maxTrain) {
			double before = logScaleSum(scale);
			train(seq);
			// System.out.println(Math.abs(logScaleSum(hmm.scale) - before));
			if (Math.abs(logScaleSum(scale) - before) < erLimit) {
				System.err.println(i + " iterations");
				break;
			}
			i++;
		}
	}
	
	public static double logScaleSum(double[] scale) {
		double sum = 0;
		for (int i = 0; i < scale.length; i++) {
			sum += Math.log10(scale[i]);
		}
		return sum;
	}

	/**
	 * Inspired by the Viterbi algorithm
	 * https://en.wikipedia.org/wiki/Viterbi_algorithm
	 * 
	 * @param seq
	 * @return statePath: most likely transition of states given a sequence
	 */
	public int[] estimateStateFromSequence(double[] seq) {
		double[][] d = new double[seq.length][lenRows(trans)];
		/* most likely transition(of the path so far) */
		double[][] mlt = new double[seq.length][lenRows(trans)];

		int[] statePath = new int[seq.length];

		/* init d */
		int s = (int) seq[0];
		for (int i = 0; i < lenRows(trans); i++) {
			d[0][i] = init[0][i] * emis[i][s];
		}
		/* using d, trace mlt */
		for (int i = 1; i < seq.length; i++) {
			for (int j = 0; j < lenRows(trans); j++) {
				/* max prob */
				double mProb = 0;
				int prev = 0;
				for (int k = 0; k < lenRows(trans); k++) {
					/* reverse prob */
					double rProb = d[i - 1][k] * trans[k][j];
					mProb = Math.max(mProb, rProb);
					if (mProb == rProb)
						prev = k;
				}
				s = (int) seq[i];
				d[i][j] = mProb * emis[j][s];
				mlt[i][j] = prev;
			}
		}
		/* find mlt : start with last transition */
		double mProb = 0;
		int ind = 0;
		for (int i = 0; i < lenRows(trans); i++) {
			double rProb = d[seq.length - 1][i];
			mProb = Math.max(rProb, mProb);
			if (rProb == mProb)
				ind = i;
		}
		statePath[seq.length - 1] = ind;
		/*
		 * we use mlt to find the most likely path backwards given
		 * statePath[last]
		 */
		for (int i = seq.length - 1; i > 0; i--) {
			statePath[i - 1] = (int) mlt[i][(int) statePath[i]];
		}
		return statePath;
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

	public static double[] getVector(Kattio io) {
		double[] vec = new double[io.getInt()];
		for (int i = 0; i < vec.length; i++) {
			vec[i] = io.getDouble();
		}
		return vec;
	}

	public static void printVector(double[] vec) {
		System.out.print("1 " + vec.length);
		for (int i = 0; i < vec.length; i++) {
			System.out.print(" " + vec[i]);
		}
		System.out.println();
	}

	public static double[][] getMatrix(Kattio io) {
		double[][] mat = new double[io.getInt()][io.getInt()];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				mat[i][j] = io.getDouble();
			}
		}
		return mat;
	}

	public static void printMatrix(double[][] m, String name) {
		if (name != null)
			System.err.println(name);
		System.out.print(m.length + " " + m[0].length);
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.print(" " + m[i][j]);
			}
			System.out.println();
		}
	}

	/* Helping methods */
	protected static int lenRows(double[][] ar) {
		if (ar.length <= 0) {
			System.err.println("RowLengthError: Rows of an array is of length 0. ");
			System.exit(1);
		}
		return ar.length > 0 ? ar.length : -1;
	}

	protected static int lenCols(double[][] ar) {
		if (ar.length <= 0 || (ar.length > 0 && ar[0].length == 0)) {
			System.err.println("ColLengthError: Columns of an array is of length 0. ");
			System.exit(1);
		}
		return ar.length > 0 ? ar[0].length : -1;
	}
}
