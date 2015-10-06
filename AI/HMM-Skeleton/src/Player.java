import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

class Player {
	private final int actThreshhold = 50, NUM_TRAIN = 100;
	private int steps = 0, numBirds, round, shotsHit, shotsFired, guessesCorrect, guessesFalse;
	private LinkedList<HMM>[] modelsBySpecies;
	int[] lastGuesses;

	public Player() {
		shotsHit = 0;
		shotsFired = 0;
		guessesCorrect = 0;
		guessesFalse = 0;
		modelsBySpecies = new LinkedList[Constants.COUNT_SPECIES];
		for (int i = 0; i < modelsBySpecies.length; i++) {
			modelsBySpecies[i] = new LinkedList<HMM>();
		}
	}

	public Action shoot(GameState pState, Deadline pDue) {
		// System.err.println("Shoot code reached ");
		numBirds = pState.getNumBirds();
		round = pState.getRound();

		if (steps <= actThreshhold || round == 0) {
			if (steps == 0)
				System.err.println("Round: " + round);
		} else {
			return act(pState);
		}
		steps++;
		return cDontShoot;

	}

	public Action act(GameState pState) {
		Bird b;
		int[] obs;
		double[] pObs;
		int species, moveToMake;
		double pMax;
		Iterator<HMM> it;
		ArrayList<Integer> birdMoves;
		for (int i = 0; i < numBirds; i++) {
			b = pState.getBird(i);
			if (b.isAlive()) {
				obs = getObs(b);
				species = identify(obs);
				pMax = 0;
				moveToMake = 0;
				if (species != Constants.SPECIES_UNKNOWN && species != Constants.SPECIES_BLACK_STORK) {
					it = modelsBySpecies[species].iterator();
					birdMoves = new ArrayList<Integer>();
					while (it.hasNext()) {
						/* hmm */
					}
				}
			}
		}
		return cDontShoot;
	}

	private int[] getObs(Bird b) {
		int[] obs = new int[b.getSeqLength()];
		for (int i = 0; i < obs.length; i++) {
			obs[i] = b.getObservation(i);
		}
		return obs;
	}

	private int identify(int[] obs) {
		return Constants.SPECIES_UNKNOWN;
	}

	/**
	 * Fill the vector with guesses for the all birds. Use SPECIES_UNKNOWN to
	 * avoid guessing.
	 */
	public int[] guess(GameState pState, Deadline pDue) {
		steps = 0;
		int[] lGuess = new int[pState.getNumBirds()];
		Random r = new Random();
		if (round == 0) {
			/* First round, make random guesses */
			System.err.println("First guess");
			for (int i = 0; i < lGuess.length; i++) {
				lGuess[i] = r.nextInt(Constants.COUNT_SPECIES - 1);
			}
		} else {
			/* Ordinary round: make qualified guess else random */
			for (int i = 0; i < numBirds; i++) {
				Bird b = pState.getBird(i);
				int[] bObs = getObs(b);
				int guess = identify(bObs);
				lGuess[i] = guess != Constants.SPECIES_UNKNOWN ? guess : r.nextInt(Constants.COUNT_SPECIES - 1);
				System.err.println("For bird #" + i + " my guess was " + guess
						+ ((guess == Constants.SPECIES_UNKNOWN) ? ": random" : ""));
			}
		}
		lastGuesses = lGuess;
		return lGuess;
	}

	/**
	 * If you hit the bird you were trying to shoot, you will be notified
	 * through this function.
	 *
	 * @param pState
	 *            the GameState object with observations etc
	 * @param pBird
	 *            the bird you hit
	 * @param pDue
	 *            time before which we must have returned
	 */
	public void hit(GameState pState, int pBird, Deadline pDue) {
		System.err.println("HIT BIRD!!!");
		shotsHit++;
	}

	/**
	 * If you made any guesses, you will find out the true species of those
	 * birds through this function.
	 *
	 * @param pState
	 *            the GameState object with observations etc
	 * @param pSpecies
	 *            the vector with species
	 * @param pDue
	 *            time before which we must have returned
	 */
	public void reveal(GameState pState, int[] pSpecies, Deadline pDue) {
		System.err.println("Reveal phase");
		for (int i = 0; i < pSpecies.length; i++) {
			if (pSpecies[i] == lastGuesses[i]) {
				guessesCorrect++;
			} else {
				guessesFalse++;
			}
		}
		/* init hmms */
		int[] obs;
		for (int i = 0; i < pSpecies.length; i++) {
			if (pSpecies[i] != Constants.SPECIES_UNKNOWN || round == 0) {
				obs = getObs(pState.getBird(i));
				modelsBySpecies[i]
						.addLast(new HMM(null, null, null)); /* TODO fix hmm */
				/* TODO use hmm */
			}
		}

		System.err.println("Guess rate: " + guessesCorrect + "/" + (guessesCorrect + guessesFalse) + " : "
				+ new Double(guessesCorrect / guessesCorrect + guessesFalse) + "%");
		System.err.println("Hit rate: " + shotsHit + "/" + (shotsHit + shotsFired) + " : "
				+ new Double(shotsHit / (shotsHit + shotsFired)) + "%");
	}

	public static final Action cDontShoot = new Action(-1, -1);
}
