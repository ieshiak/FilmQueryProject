package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private Connection conn;
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String user = "student";
	private static final String pass = "student";
	
	public DatabaseAccessorObject() {
        try {
            // Ensure that conn is properly initialized based on your application's structure
            conn = DriverManager.getConnection(URL, user, pass);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

	@Override
	public Film findFilmById(int filmId) {
		String sql = "SELECT * FROM film WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, filmId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return extractFilmFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Actor findActorById(int actorId) {
		String sql = "SELECT * FROM actor WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, actorId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return extractActorFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle or log the exception appropriately
		}
		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		String sql = "SELECT a.* FROM actor a " + "JOIN film_actor fa ON a.id = fa.actor_id " + "WHERE fa.film_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, filmId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					actors.add(extractActorFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle or log the exception appropriately
		}
		return actors;
	}

	private List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		String sql = "SELECT film.* " + "FROM film " + "JOIN film_actor ON film.id = film_actor.film_id "
				+ "WHERE film_actor.actor_id = ?";
		System.out.println("SQL Query: " + sql);

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, actorId);
			System.out.println("Parameters set for findFilmsByActorId: actorId = " + actorId);

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				films.add(extractFilmFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}


	private Film extractFilmFromResultSet(ResultSet resultSet) throws SQLException {
	    int filmId = resultSet.getInt("id");
	    String title = resultSet.getString("title");
	    String description = resultSet.getString("description");
	    Integer releaseYear = resultSet.getInt("release_year"); // Use Integer for potential null values
	    int languageId = resultSet.getInt("language_id");
	    int rentalDuration = resultSet.getInt("rental_duration");
	    double rentalRate = resultSet.getDouble("rental_rate");
	    Integer length = resultSet.getInt("length"); // Use Integer for potential null values
	    double replacementCost = resultSet.getDouble("replacement_cost");
	    String rating = resultSet.getString("rating");
	    String specialFeatures = resultSet.getString("special_features");

//	    System.out.println("Extracted Film:\n " +
//	            "ID: " + filmId + ",\n "  +
//	            "Title: " + title + ",\n "  +
//	            "Description: " + description + ",\n " +
//	            "Release Year: " + releaseYear + ",\n " +
//	            "Language ID: " + languageId + ",\n " +
//	            "Rental Duration: " + rentalDuration + ",\n " +
//	            "Rental Rate: " + rentalRate + ",\n "  +
//	            "Length: " + length + ",\n "  +
//	            "Replacement Cost: " + replacementCost + ",\n "  +
//	            "Rating: " + rating + ",\n "  +
//	            "Special Features: " + Arrays.toString(extractSpecialFeatures(specialFeatures)));
	    
	    return new Film(filmId, title, description, releaseYear, languageId, rentalDuration, rentalRate, length,
                replacementCost, rating, extractSpecialFeatures(specialFeatures));
	}

	private String[] extractSpecialFeatures(String specialFeatures) {
	    return (specialFeatures != null) ? specialFeatures.split(",") : null;
	}

	private Actor extractActorFromResultSet(ResultSet resultSet) throws SQLException {
		int actorId = resultSet.getInt("id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		List<Film> films = findFilmsByActorId(actorId);
		films = (films != null) ? films : new ArrayList<>();

		System.out.println("Extracted Actor: " + actorId + ", " + firstName + ", " + lastName + ", ...");
		return new Actor(actorId, firstName, lastName, films);
	}
	 public void closeConnection() {
	        try {
	            if (conn != null && !conn.isClosed()) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	    }

}
