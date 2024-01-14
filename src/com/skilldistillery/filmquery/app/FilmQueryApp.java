package com.skilldistillery.filmquery.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();
	}

	private void test() {
		// Test findFilmById
		Film film = db.findFilmById(2);
		System.out.println(film);

		// Test findActorById
		// Actor actor = db.findActorById(5);
		// System.out.println("Actor Details:\n" + actor);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		int choice = 0;

		do {
			System.out.println("----------- Film Query Menu -----------");
			System.out.println("1. Look up a film by its id");
			System.out.println("2. Look up a film by a search keyword");
			System.out.println("3. Exit");
			System.out.println("---------------------------------------");
			System.out.print("Enter your choice: ");

			try {
				choice = input.nextInt();
				input.nextLine(); 
				switch (choice) {
				case 1:
					lookUpFilmById(input);
					break;
				case 2:
					lookUpFilmByKeyword(input);
					break;
				case 3:
					System.out.println("Exiting the application. Goodbye!");
					break;
				default:
					System.out.println("Invalid choice. Please enter a valid option.");
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				input.nextLine();
			}
		} while (choice != 3);
	}

	private void lookUpFilmById(Scanner input) {
		System.out.print("Enter the film ID: ");
		try {
			int filmId = input.nextInt();
			Film film = db.findFilmById(filmId);
			if (film != null) {
				System.out.println("\nFilm details:");
				System.out.println("---------------------------------------");
				System.out.println(film);

				int submenuChoice = 0; // Remove initialization

				do {
					System.out.println("--------- Film Details Submenu --------");
					System.out.println("1. Return to the main menu");
					System.out.println("2. View all film details");
					System.out.println("---------------------------------------");
					System.out.print("Enter your choice: ");

					try {
						submenuChoice = input.nextInt();
						input.nextLine();

						switch (submenuChoice) {
						case 1:
							System.out.println("Returning to the main menu.");
							break;
						case 2:
							viewAllFilmDetails(film);

							System.out.print("Enter 'M' to return to the main menu: ");
							String returnToMainMenu = input.nextLine().toUpperCase();

							if (!returnToMainMenu.equals("M")) {
								System.out.println("Invalid choice. Returning to the main menu.");
								return; 
							}
							break;
						default:
							System.out.println("Invalid choice. Please enter a valid option.");
						}
					} catch (java.util.InputMismatchException e) {
						System.out.println("Invalid input. Please enter a number.");
						input.nextLine();
					}

				} while (submenuChoice != 1);
			} else {
				System.out.println("Film not found with ID: " + filmId);
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println("Invalid input. Please enter a valid number.");
			input.nextLine();
		}
	}

	private void viewAllFilmDetails(Film film) {
		List<String> categories = db.findCategoriesByFilmId(film.getId());
		System.out.println("\nAll film details:");
		System.out.println("---------------------------------------");
		System.out.println(" - Film ID: " + film.getId());
		System.out.println(" - Title: " + film.getTitle());
		System.out.println(String.format(" - Description: %s", wrapText(film.getDescription())));

		System.out.println(" - Categories: " + String.join(", ", categories));
		System.out.println(" - Release Year: " + film.getReleaseYear());
		System.out.println(" - Language: " + film.getLanguage());
		System.out.println(" - Rental Duration: " + film.getRentalDuration());
		System.out.println(" - Rental Rate: " + film.getRentalRate());
		System.out.println(" - Length: " + film.getLength());
		System.out.println(" - Replacement Cost: " + film.getReplacementCost());
		System.out.println(" - Rating: " + film.getRating());
		String[] specialFeatures = film.getSpecialFeatures();
		System.out.println(
				" - Special Features: " + (specialFeatures != null ? Arrays.toString(specialFeatures) : "N/A"));

		System.out.println(" - Actors: ");
		List<Actor> actors = film.getActors();
		for (Actor actor : actors) {
			System.out.println("   - Id: " + actor.getActorId() + "\t - Name: " + actor.getFirstName() + " "
					+ actor.getLastName());
		}
		
		System.out.println(" - Copies in Inventory: ");
	    List<Map<String, Object>> copies = db.findCopiesWithConditionsByFilmId(film.getId());
	    for (Map<String, Object> copy : copies) {
	        System.out.println(" - Copy ID: " + copy.get("id") + "    - Condition: " + copy.get("media_condition"));
	    }
	}
	
	

	private String wrapText(String text) {
		int maxLineLength = 40;
		StringBuilder wrappedText = new StringBuilder();
		String[] words = text.split("\\s+");
		int currentLineLength = 0;
		String indentation = "\t";
		for (String word : words) {
			if (currentLineLength + indentation.length() + word.length() > maxLineLength) {
				wrappedText.append("\n").append(indentation);
				currentLineLength = 0; 
			}
			wrappedText.append(word).append(" ");
			currentLineLength += indentation.length() + word.length() + 1; 
		}
		return wrappedText.toString();
	}

	private void lookUpFilmByKeyword(Scanner input) {
		System.out.print("Enter the search keyword: ");
		String keyword = input.nextLine();
		List<Film> films = db.findFilmsByKeyword(keyword);
		if (!films.isEmpty()) {
			System.out.println("\nFilms found with keyword '" + keyword + "':");
			System.out.println("---------------------------------------");
			for (Film film : films) {
				System.out.println(film);
			}
		} else {
			System.out.println("No films found with keyword: " + keyword);
		}
	}
}
