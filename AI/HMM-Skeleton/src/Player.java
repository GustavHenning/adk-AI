
class Player {
	private HMM species;
	private HMM moves;
	// /constructor

	// /There is no data in the beginning, so not much should be done here.
	public Player() {
	}

	/**
	 * Shoot!
	 *
	 * This is the function where you start your work.
	 *
	 * You will receive a variable pState, which contains information about all
	 * birds, both dead and alive. Each birds contains all past actions.
	 *
	 * The state also contains the scores for all players and the number of time
	 * steps elapsed since the last time this function was called.
	 *
	 * @param pState
	 *            the GameState object with observations etc
	 * @param pDue
	 *            time before which we must have returned
	 * @return the prediction of a bird we want to shoot at, or cDontShoot to
	 *         pass
	 */
	public Action shoot(GameState pState, Deadline pDue) {
		//System.err.println("Shoot code reached ");
		if (moves == null)
			moves = new HMM(pState.getNumBirds(), Constants.COUNT_MOVE); /* one for moves or for each bird? */
		if (species == null)
			species = new HMM(pState.getNumBirds(), Constants.COUNT_SPECIES);/* one for each species or bird? */

		int[] birdStates = new int[pState.getNumBirds()];
		for (int i = 0; i < birdStates.length; i++) {
			birdStates[i] = pState.getBird(i).getLastObservation();
		}
		moves.train(birdStates, 1 /* should this increase? */);
		if (pState.getNumNewTurns() < 10) {
			// This line chooses not to shoot
			return cDontShoot;
		} else {
			/*
			 * Here, out of all alive birds, calculate the most predictable
			 * bird/move, then shoot
			 */
		}
		// This line choose not to shoot
		return cDontShoot;
		/*
		 * Here you should write your clever algorithms to get the best action.
		 * This skeleton never shoots.
		 */

		// This line would predict that bird 0 will move right and shoot at it
		// return Action(0, MOVE_RIGHT);
	}

	/**
	 * Guess the species! This function will be called at the end of each round,
	 * to give you a chance to identify the species of the birds for extra
	 * points.
	 *
	 * Fill the vector with guesses for the all birds. Use SPECIES_UNKNOWN to
	 * avoid guessing.
	 *
	 * @param pState
	 *            the GameState object with observations etc
	 * @param pDue
	 *            time before which we must have returned
	 * @return a vector with guesses for all the birds
	 */
	public int[] guess(GameState pState, Deadline pDue) {
		/*
		 * Here you should write your clever algorithms to guess the species of
		 * each bird. This skeleton makes no guesses, better safe than sorry!
		 */

		int[] lGuess = new int[pState.getNumBirds()];
		for (int i = 0; i < pState.getNumBirds(); ++i)
			lGuess[i] = Constants.SPECIES_UNKNOWN;
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
	}

	public static final Action cDontShoot = new Action(-1, -1);
}
