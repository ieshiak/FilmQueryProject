package com.skilldistillery.filmquery.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Film {
    private int id;
    private String title;
    private String description;
    private Integer releaseYear;
    private int languageId;
    private String language;
    private int rentalDuration;
    private double rentalRate;
    private Integer length;
    private double replacementCost;
    private String rating;
    private String[] specialFeatures;
    private List<Actor> actors;
    
    
    public Film(int id, String title, String description, Integer releaseYear, String language, int rentalDuration,
            double rentalRate, Integer length, double replacementCost, String rating, String[] specialFeatures, List<Actor> actors) {
    super();
    this.id = id;
    this.title = title;
    this.description = description;
    this.releaseYear = releaseYear;
    this.language = language;
    this.rentalDuration = rentalDuration;
    this.rentalRate = rentalRate;
    this.length = length;
    this.replacementCost = replacementCost;
    this.rating = rating;
    this.specialFeatures = specialFeatures;
    this.setActors(actors);
}


	public int getId() {
		return id;
	}
	

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	
	public String getLanguage() {
	    return language;
	    }

	public void setLanguage(String language) {
	    this.language = language;
	    }

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public double getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(double replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String[] getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String[] specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(specialFeatures);
		result = prime * result + Objects.hash(actors, description, id, language, languageId, length, rating,
				releaseYear, rentalDuration, rentalRate, replacementCost, title);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(actors, other.actors) && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(language, other.language) && languageId == other.languageId
				&& Objects.equals(length, other.length) && Objects.equals(rating, other.rating)
				&& Objects.equals(releaseYear, other.releaseYear) && rentalDuration == other.rentalDuration
				&& Double.doubleToLongBits(rentalRate) == Double.doubleToLongBits(other.rentalRate)
				&& Double.doubleToLongBits(replacementCost) == Double.doubleToLongBits(other.replacementCost)
				&& Arrays.equals(specialFeatures, other.specialFeatures) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Film ").append('\n');
	    builder.append(" - ID: ").append(id).append('\n');
	    builder.append(" - Title: ").append(title).append('\n');
	    builder.append(" - Description: ").append(wrapText(description, 40, "\t")).append('\n');
	    builder.append(" - Release Year: ").append(releaseYear).append('\n');
	    builder.append(" - Language: ").append(language).append('\n');
	    builder.append(" - Rental Duration: ").append(rentalDuration).append(" days").append('\n');
	    builder.append(" - Rental Rate: $").append(rentalRate).append('\n');
	    builder.append(" - Length: ").append(length).append(" mins").append('\n');
	    builder.append(" - Replacement Cost: $").append(replacementCost).append('\n');
	    builder.append(" - Rating: ").append(rating).append('\n');
	    builder.append(" - Special Features: ").append(Arrays.toString(specialFeatures)).append('\n');
	    Collections.sort(actors, (a1, a2) -> a1.getLastName().compareToIgnoreCase(a2.getLastName()));
	    if (actors != null) {
	        builder.append("Actors").append('\n');
	    for (Actor actor : actors) {
	        builder.append(" - Id: ").append(actor.getActorId())
	                .append(" \t - Name: ").append(actor.getFirstName())
	                .append(" ").append(actor.getLastName()).append('\n');
	    }
	}
	    return builder.toString();
	}

	private String wrapText(String text, int lineWidth, String prefix) {
	    StringBuilder result = new StringBuilder();
	    String[] words = text.split("\\s+");

	    int currentLineLength = 0;
	    for (String word : words) {
	        if (currentLineLength + word.length() > lineWidth) {
	            result.append(System.lineSeparator()).append(prefix);
	            currentLineLength = 0;
	        }

	        result.append(word).append(" ");
	        currentLineLength += word.length() + 1; // 1 for the space
	    }

	    return result.toString();
	}
}
