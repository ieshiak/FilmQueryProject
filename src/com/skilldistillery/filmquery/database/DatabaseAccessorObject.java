package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
private static final String user = "student";
private static final String pass = "student";


  @Override
  public Film findFilmById(int filmId) {
	  String sql = "SELECT * FROM film WHERE film_id = ?";
	  
    return null;
  }

@Override
public Actor findActorById(int actorId) {
	 Actor actor = null;
	 Connection conn = DriverManager.getConnection(URL, user, pass);

	  String sql = "SELECT * FROM actor WHERE actor_id = ?";
	  PreparedStatement stmt = conn.prepareStatement(sql);
	  stmt.setInt(1,actorId);
	  ResultSet actorResult = stmt.executeQuery();
	  if (actorResult.next()) {
	    actor = new Actor(); // Create the object
	    // Here is our mapping of query columns to our object fields:
	    actor.setId(actorResult.getInt(1));
	    actor.setFirstName(actorResult.getString(2));
	    actor.setLastName(actorResult.getString(3));
	  }
	return actor;
}

@Override
public List<Actor> findActorsByFilmId(int filmId) {
	String sql = "SELECT actor. * FROM actor" +
"JOIN film_actor ON actor.actor_id = film_actor.actor_id" +
			"WHERE film_actor.film_id = ?";
	return null;
}

}
