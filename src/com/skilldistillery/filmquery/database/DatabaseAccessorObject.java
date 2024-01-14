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
			conn = DriverManager.getConnection(URL, user, pass);
			//System.out.println("Connected to the database");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		String sql = "SELECT * FROM film WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, filmId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Film film = extractFilmFromResultSet(rs);
					//System.out.println("Success! ");
					return film;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("Film not found with ID: " + filmId);
		return null;
	}

	@Override
	public Actor findActorById(int actorId) {
	    String sql = "SELECT * FROM actor WHERE id = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, actorId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                Actor actor = extractActorFromResultSet(rs);
	                List<Film> films = findFilmsByActorId(actorId);
	                actor.setFilms(films);
	                //System.out.println("Films for Actor ID " + actorId + ": " + films); // Debugging line
	                return actor;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    System.out.println("Actor not found with ID: " + actorId);
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
	                Actor actor = extractActorFromResultSet(rs);
	                actors.add(actor);
	                //System.out.println("Found Actor for Film ID " + filmId + ": " + actor);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    if (actors.isEmpty()) {
	        System.out.println("No actors found for Film ID: " + filmId);
	    }
	    return actors;
	}
	
	@Override
	public List<Film> findFilmsByKeyword(String keyword) {
	    List<Film> films = new ArrayList<>();
	    String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, "%" + keyword + "%");
	        stmt.setString(2, "%" + keyword + "%");
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Film film = extractFilmFromResultSet(rs);
	                films.add(film);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return films;
	}


	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
	    String sql = "SELECT f.* FROM film f JOIN film_actor fa ON f.id = fa.film_id WHERE fa.actor_id = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, actorId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Film film = extractFilmFromResultSet(rs);
	                films.add(film);
	            }
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
		Integer releaseYear = resultSet.getInt("release_year");
		int languageId = resultSet.getInt("language_id");
		int rentalDuration = resultSet.getInt("rental_duration");
		double rentalRate = resultSet.getDouble("rental_rate");
		Integer length = resultSet.getInt("length");
		double replacementCost = resultSet.getDouble("replacement_cost");
		String rating = resultSet.getString("rating");
		String specialFeatures = resultSet.getString("special_features");
		String language = findLanguageById(languageId);
		List<Actor> actors = findActorsByFilmId(filmId);
		//System.out.println("Success Extracted Film " + title);
		return new Film(filmId, title, description, releaseYear, language, rentalDuration, rentalRate, length,
	            replacementCost, rating, extractSpecialFeatures(specialFeatures), actors);
	}

	private String findLanguageById(int languageId) {
		String sql = "SELECT name FROM language WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, languageId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("name");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String[] extractSpecialFeatures(String specialFeatures) {
		return (specialFeatures != null) ? specialFeatures.split(",") : null;
	}

	private Actor extractActorFromResultSet(ResultSet resultSet) throws SQLException {
	    int actorId = resultSet.getInt("id");
	    String firstName = resultSet.getString("first_name");
	    String lastName = resultSet.getString("last_name");
	    //List<Film> films = findFilmsByActorId(actorId);
	    List<Film> films = new ArrayList<>();

	    //System.out.println("Extracted Actor: " + actorId + ", " + firstName + ", " + lastName + ", ...");
	    return new Actor(actorId, firstName, lastName, films);
	}


	public void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
