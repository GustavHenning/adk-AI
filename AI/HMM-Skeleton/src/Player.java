import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

class Player {
	private final int actThreshhold = 50, MAX_TRAIN = 100, MOVE_UNCERTAIN = -1;
	private final double erLimit = 1.0E-10, SHOOT_THRESHOLD = 0.1;
	private int steps = 0, numBirds, round, shotsHit, shotsFired, guessesCorrect, guessesFalse;
	private LinkedList<HMM>[] modelsBySpecies;
	int[] lastGuesses;

	public class PredictMove{
		public int bird;
		public int move;
		public double pMax;

		public PredictMove(int bird, int move, double pMax){
			this.bird = bird;
			this.move = move;
			this.pMax = pMax;
		}
	}

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
		LinkedList<PredictMove> probableMoves = new LinkedList<PredictMove>();
		for (int i = 0; i < numBirds; i++) {
			b = pState.getBird(i);
			if (b.isAlive()) {
				obs = getObs(b);
				species = identify(obs);
				pMax = 0; /* Threshold? */
				moveToMake = MOVE_UNCERTAIN;
				if (species != Constants.SPECIES_UNKNOWN && species != Constants.SPECIES_BLACK_STORK) {
					it = modelsBySpecies[species].iterator();
					birdMoves = new ArrayList<Integer>();
					while (it.hasNext()) {
						/* TODO train on obs? */
						pObs = it.next().nextEmissionProbabilities();
						for(int j = 0; j < pObs.length; j++){
							if(pObs[j] > pMax){
								pMax = pObs[j];
								moveToMake = j;
							}
						}
						birdMoves.add(moveToMake);
					}
					boolean modelsAgree = true;
					for(int j = 1; j < birdMoves.size(); j++){
						if(birdMoves.get(i-1) != birdMoves.get(i)){
							modelsAgree = false;
							break;
						}
					}
					if(modelsAgree && moveToMake != MOVE_UNCERTAIN){
						probableMoves.add(new PredictMove(i, moveToMake, pMax));
					}
				}
			}
			Iterator<PredictMove> iter = probableMoves.iterator();
			pMax = 0;
			moveToMake = MOVE_UNCERTAIN;
			int bird = -1;
			PredictMove pm = new PredictMove(-1, -1, -1.0);
			while(iter.hasNext()){
				pm = iter.next();
				if(pm.pMax > pMax){
					pMax = pm.pMax;
					moveToMake = pm.move;
					bird = pm.bird;
				}
			}
			if(pMax > SHOOT_THRESHOLD && moveToMake != MOVE_UNCERTAIN && bird != -1){
				shotsFired++;
				steps++;
				return new Action(bird, moveToMake);
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
		double pMax = 0;
		int speciesGuess = Constants.SPECIES_UNKNOWN;
		Iterator<HMM> iter;
		for(int i = 0; i < modelsBySpecies.length; i++){
			iter = modelsBySpecies[i].iterator();
			while(iter.hasNext()){
				double[] pEmis = iter.next().nextEmissionProbabilities();
				for(int j = 0; j < pEmis.length; j++){
					if(pEmis[j] > pMax){
						pMax = pEmis[j];
						speciesGuess = i; /* j? */
					}
				}
			}
		}
		
		return speciesGuess;
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
	 */
	public void hit(GameState pState, int pBird, Deadline pDue) {
		System.err.println("HIT BIRD!!!");
		shotsHit++;
	}

	/**
	 * If you made any guesses, you will find out the true species of those
	 * birds through this function.
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
				modelsBySpecies[pSpecies[i]]
						.addLast(new HMM(Constants.COUNT_MOVE, Constants.COUNT_SPECIES)); /* TODO fix hmm */
				modelsBySpecies[pSpecies[i]].getLast().estimateMatrices(erLimit, MAX_TRAIN, toDoubleArray(obs));
			}
		}
		if(guessesCorrect + guessesFalse > 0)
			System.err.println("Guess rate: " + guessesCorrect + "/" + (guessesCorrect + guessesFalse) + " : "
				+ new Double(guessesCorrect / guessesCorrect + guessesFalse) + "%");
		if(shotsHit + shotsFired > 0)
		System.err.println("Hit rate: " + shotsHit + "/" + (shotsHit + shotsFired) + " : "
				+ new Double(shotsHit / (shotsHit + shotsFired)) + "%");
	}

	public double[] toDoubleArray(int[] ar){
		double[] dAr = new double[ar.length];
		for(int i = 0; i < ar.length; i++){
			dAr[i] = ar[i];
		}
		return dAr;
	}

	public static final Action cDontShoot = new Action(-1, -1);
}
