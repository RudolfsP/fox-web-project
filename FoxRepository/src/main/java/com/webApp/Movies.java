package com.webApp;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@EnableAutoConfiguration
public class Movies {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)		//column atrribute specs
	private Long id;
	private String title;
	private String year;
	private String runtime;
	private String genre;
	private String director;
	private String writer;
	private String actors;
	private String plot;
	private String lang;
	private String country;
	private String awards;
	private String poster;
	private String metascore;
	private String imdbRating;
	private String votes;
	private String type;
	
	@Transient
	private List<String> similarMovies;
	
	public Movies(String title, String year, String runtime, String genre, String director, String writer,
			String actors, String plot, String lang, String country, String awards, String poster, String metascore,
			String imdbRating, String votes, String type) {
		super();
		this.title = title;
		this.year = year;
		this.runtime = runtime;
		this.genre = genre;
		this.director = director;
		this.writer = writer;
		this.actors = actors;
		this.plot = plot;
		this.lang = lang;
		this.country = country;
		this.awards = awards;
		this.poster = poster;
		this.metascore = metascore;
		this.imdbRating = imdbRating;
		this.votes = votes;
		this.type = type;
	}


	public Movies() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getMetascore() {
		return metascore;
	}

	public void setMetascore(String metascore) {
		this.metascore = metascore;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getVotes() {
		return votes;
	}

	public void setVotes(String votes) {
		this.votes = votes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getSimilarMovies() {
		return similarMovies;
	}

	public void setSimilarMovies(List<String> similarMovies) {
		this.similarMovies = similarMovies;
	}

	@Override
	public String toString() {
		return this.title + "\n" + this.year + "\n" + this.runtime + "\n" + this.genre +
				"\n" + this.director + "\n" + this.writer + "\n" + this.actors + "\n" +
				this.plot + "\n" + this.lang + "\n" + this.country + "\n" + this.awards + "\n" +
				this.poster + "\n" + this.metascore + "\n" + this.imdbRating + "\n" +
				this.votes + "\n" + this.type;
	}

}