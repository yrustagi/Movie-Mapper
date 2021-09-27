import java.util.List;
import java.util.Scanner;

/**
 * Frontend class that talks to the backend and displays the user interface
 */
public class Frontend {
	// static variable for accessing backend and scanner accross methods
	private static MovieMapperBackend backend;
	private static Scanner in;

	/**
	 * main method that runs all other methods
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		run(new MovieMapperBackend(args));
	}

	/**
	 * method that handles all functionality for the base mode screen
	 * 
	 * @param args
	 */
	public static void run(MovieMapperBackend back) {
		backend = back;
		// instantiating Backend and Scanner
		in = new Scanner(System.in);
		System.out.println("\n===============================");
		System.out.println(" WELCOME TO CS400 MOVIE MAPPER");
		System.out.println("===============================");
		// initializing variables
		boolean running = true;
		int startIndex = 0;
		int pageNumber = 1;
		while (running) {
			// fetching 3 movies from backend that match criteria
			List<MovieInterface> movieResults = backend.getThreeMovies(startIndex);
			// fetching number of total movies and pages
			int pages = (int) Math.ceil((float) backend.getNumberOfMovies() / 3);
			pages++;
			// displaying movies in pages with 3 movies in each
			System.out.println(String.format("\nMovies with Selected Genres and Ratings [Page %d]:-", pageNumber));
			if (movieResults.size() == 0) {
				System.out.println("==No Movies to Display==");
			} else {
				for (int i = 0; i < movieResults.size(); i++) {
					System.out.println(String.format("%d. ", startIndex + i + 1) + movieResults.get(i).toString());
				}
			}
			System.out.print("\n[G]enre Selection Mode | [R]ating Selection Mode | [");
			System.out.print(String.format("%d-%d", 1, pages));
			System.out.println("] Scroll Pages | E[X]it");
			System.out.print("Enter a command: ");
			// fetching and executing command
			String input = in.nextLine();
			if (input.length() < 1) {
				// invalid input length
				System.out.println("\n==Invalid input! Please try again!==\n");
			} else {
				if (Character.isDigit(input.charAt(0))) {
					int newPage = Integer.parseInt(input);
					if (newPage < 1 || newPage > pages) {
						System.out.println("\n==Invalid input! Please try again!==\n");
					} else {
						// updating page number
						pageNumber = Integer.parseInt(input);
						startIndex = (pageNumber - 1) * 3;
						System.out.println(String.format("\n==Changed to Page %d!==", pageNumber));
					}
				} else {
					if (input.length() != 1) {
						// invalid input length
						System.out.println("\n==Invalid input! Please try again!==\n");
					} else {
						switch (input) {
							case "x":
							case "X":
								// exiting the program
								running = false;
								break;

							case "g":
							case "G":
								// changing to genre selection mode
								handleGenreSelection();
								startIndex = 0;
								break;

							case "r":
							case "R":
								// changing to rating selection mode
								handleRatingSelection();
								startIndex = 0;
								break;

							default:
								// if no matches input is invalid
								System.out.println("Invalid Command! Please try again!");
						}
					}
				}
			}
		}
		System.out.println("\n=========================================");
		System.out.println(" THANKS FOR CHECKING OUT MOVIE MAPPER !!");
		System.out.println("=========================================");

	}

	/**
	 * method that handles all functionality for the genre selection screen
	 */
	public static void handleGenreSelection() {
		System.out.println("\n======================");
		System.out.println(" Genre Selection Mode");
		System.out.println("======================\n");
		boolean inMode = true;
		while (inMode) {
			// fetching selected genres and all genres from backend
			List<String> selectedGenres = backend.getGenres();
			List<String> allGenres = backend.getAllGenres();
			// displaying all genre options with selections
			for (int i = 0; i < allGenres.size(); i++) {
				System.out.print("[" + (selectedGenres.contains(allGenres.get(i)) ? "*" : ""));
				System.out.println(String.format("%d] %s", i + 1, allGenres.get(i)));
			}
			System.out.println("\n* - Selected");
			System.out.print("Enter genre number to toggle selection or 'x' to go back: ");
			// fetching and executing command
			String input = in.nextLine();
			if (input.length() < 1) {
				// invalid input
				System.out.println("\n==Invalid input! Please try again!==\n");
			} else {
				if (Character.isDigit(input.charAt(0))) {
					int genreNumber = Integer.parseInt(input);
					if (genreNumber < 1 || genreNumber > allGenres.size()) {
						// invalid input
						System.out.println("\n==Invalid input! Please try again!==\n");
					} else {
						// toggling selection of particular genre
						if (selectedGenres.contains(allGenres.get(genreNumber - 1))) {
							backend.removeGenre(allGenres.get(genreNumber - 1));
						} else {
							backend.addGenre(allGenres.get(genreNumber - 1));
						}
						System.out.println("\n==Selection Success!==\n");
					}
				} else {
					if (input.equals("x") || input.equals("X")) {
						inMode = false;
					} else {
						// invalid input
						System.out.println("\n==Invalid input! Please try again!==\n");
					}
				}
			}
		}

	}

	/**
	 * method that handles all functionality for the rating selection screen
	 */
	public static void handleRatingSelection() {
		System.out.println("\n=======================");
		System.out.println(" Rating Selection Mode");
		System.out.println("=======================\n");
		boolean inMode = true;
		while (inMode) {
			// fetching selected ratings from backend
			List<String> selectedRatings = backend.getAvgRatings();
			// displaying all ratings with selections
			for (int i = 0; i < 10; i++) {
				System.out.print("[" + (selectedRatings.contains(Integer.toString(i + 1)) ? "*" : ""));
				System.out.println(String.format("%d]", i + 1));
			}
			System.out.println("\n* - Selected");
			System.out.print("Enter rating to toggle selection or 'x' to go back: ");
			// fetching and executing command
			String input = in.nextLine();
			if (input.length() < 1 || input.length() > 2) {
				System.out.println("\n==Invalid input! Please try again!==");
			} else {
				if (Character.isDigit(input.charAt(0))) {
					int ratingNumber = Integer.parseInt(input);
					if (ratingNumber < 1 || ratingNumber > 10) {
						// invalid input
						System.out.println("\n==Invalid input! Please try again!==\n");
					} else {
						// toggling the particular rating
						if (selectedRatings.contains(Integer.toString(ratingNumber))) {
							backend.removeAvgRating(Integer.toString(ratingNumber));
						} else {
							backend.addAvgRating(Integer.toString(ratingNumber));
						}
						System.out.println("\n==Selection Success!==\n");
					}
				} else {
					if (input.equals("x") || input.equals("X")) {
						inMode = false;
					} else {
						// invalid input
						System.out.println("\n==Invalid input! Please try again!==\n");
					}
				}
			}
		}
	}

}
