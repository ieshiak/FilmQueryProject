package com.skilldistillery.filmquery.app;

import java.util.List;
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
				input.nextLine(); // Clear the invalid input
			}

		} while (choice != 3);
	}

	private void lookUpFilmById(Scanner input) {
		System.out.print("Enter the film ID: ");
		try {
			int filmId = input.nextInt();
			Film film = db.findFilmById(filmId);
			if (film != null) {
				System.out.println("Film details:\n" + film);
			} else {
				System.out.println("Film not found with ID: " + filmId);
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println("Invalid input. Please enter a valid number.");
			input.nextLine(); // Clear the invalid input
		}
	}

	private void lookUpFilmByKeyword(Scanner input) {
		System.out.print("Enter the search keyword: ");
		String keyword = input.nextLine();
		List<Film> films = db.findFilmsByKeyword(keyword);
		if (!films.isEmpty()) {
			System.out.println("Films found with keyword '" + keyword + "':");
			for (Film film : films) {
				System.out.println(film);
			}
		} else {
			System.out.println("No films found with keyword: " + keyword);
		}
	}

}
