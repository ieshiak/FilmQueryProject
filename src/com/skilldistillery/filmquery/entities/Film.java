package com.skilldistillery.filmquery.entities;

import java.util.Arrays;
import java.util.Objects;

public class Film {
    private int id;
    private String title;
    private String description;
    private Integer releaseYear; // Using Integer to allow null values
    private int languageId;
    private String language;
    private int rentalDuration;
    private double rentalRate;
    private Integer length; // Using Integer to allow null values
    private double replacementCost;
    private String rating;
    private String[] specialFeatures;
	
    public Film(int id, String title, String description, Integer releaseYear, String language, int rentalDuration,
            double rentalRate, Integer length, double replacementCost, String rating, String[] specialFeatures) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language; // Add this line to set the language field
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
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
	
	@Override
    public String toString() {
        return "Film " + '\n' +
                "ID: " + id + '\n' +
                "Title: " + title + '\n' +
                "Description: " + description + '\n' +
                "Release Year: " + releaseYear + '\n' +
                "Language: " + language + '\n' +
                "Rental Duration: " + rentalDuration + '\n' +
                "Rental Rate: $" + rentalRate + '\n' +
                "Length: " + length + " mins" + '\n' +
                "Replacement Cost: $" + replacementCost + '\n' +
                "Rating: " + rating + '\n' +
                "Special Features: " + Arrays.toString(specialFeatures) +
                '\n';
    }
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id &&
                languageId == film.languageId &&
                rentalDuration == film.rentalDuration &&
                Double.compare(film.rentalRate, rentalRate) == 0 &&
                Double.compare(film.replacementCost, replacementCost) == 0 &&
                Objects.equals(title, film.title) &&
                Objects.equals(description, film.description) &&
                Objects.equals(releaseYear, film.releaseYear) &&
                Objects.equals(length, film.length) &&
                Objects.equals(rating, film.rating) &&
                Arrays.equals(specialFeatures, film.specialFeatures);
    }
	 @Override
	    public int hashCode() {
	        int result = Objects.hash(id, title, description, releaseYear, languageId, rentalDuration, rentalRate, length, replacementCost, rating);
	        result = 31 * result + Arrays.hashCode(specialFeatures);
	        return result;
	    }
}