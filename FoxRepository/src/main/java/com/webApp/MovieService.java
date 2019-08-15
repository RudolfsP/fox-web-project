package com.webApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	@Autowired
	private MovieRepository repo;

	public List<Movies> listAllFromDB() {
		return repo.findAll();
	}

	public void save(Movies fox) {
		repo.save(fox);
	}

	public Movies get(Long id) {
		return repo.findById(id).get();
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}

	public List<Movies> listAllMoviesByIDFromDB(String userSearch) {
		List<Movies> returnList = new ArrayList<Movies>();
		List<Movies> movieList = listAllFromDB();

		for (int i = 0; i < movieList.size(); i++) {
			if (movieList.get(i).getTitle().equals(userSearch)) {
				returnList.add(movieList.get(i));
			}
		}

		return returnList;
	}

	public String getRandomMovieName() throws IOException {
		String url = "https://www.suggestmemovie.com";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		int counter = 0;
		String toReturn = "";

		while ((inputLine = in.readLine()) != null && counter < 116) {
			if (counter == 114) {
				toReturn = inputLine;
			}
			counter++;
		}
		in.close();

		toReturn = toReturn.replace("        <h1>", "");
		toReturn = toReturn.substring(0, toReturn.indexOf("<s") - 1);

		return toReturn;
	}

	public List<Movies> listAllMoviesByIDFromAPI(String searchValue) throws Exception {
		List<Movies> movieList = new ArrayList<>();
		String usedValue = searchValue.replace(" ", "-");

		String url = "http://127.0.0.1:7071/getMovie?movieTitle=" + usedValue;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		//this is where JSON stuff begins
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		
		try {
			jsonObject = (JSONObject) parser.parse(in);
		} catch (ParseException e) {}
				
		Movies movie = makeMovieObject(jsonObject);
//		if(movie.getTitle().contentEquals("fake movie")) {
//			return movieList;
//		}
		
		List<String> similarMovies = putSimilarInList(jsonObject);
		movie.setSimilarMovies(similarMovies);
		if(movie.getTitle() == null) {
			return movieList;
		}
		
		
		movieList.add(movie);
		
	
		//get data about the first 12 similar movies
		int listSize = similarMovies.size();
		if(listSize > 12) 
			listSize = 12;
		
		//get movie data about the similar movies	
		for(int i = 0; i < listSize; i++) {
			
			Movies recommendedMovie = getMovieData(similarMovies.get(i));
			
			if(recommendedMovie.getTitle() == null) {
				listSize++;
				continue;
			}
			
			if(recommendedMovie.getPoster().contains("http")) {
				movieList.add(recommendedMovie);
			}
			
			else {
				listSize++;
			}	
		}		
	
		in.close();
		return movieList;
	}

	public Movies getMovieData(String searchValue) throws Exception {
		Movies movie = new Movies();

		String usedValue = searchValue.replace(" ", "-");
		String url = "http://127.0.0.1:7071/getMovieData?movieTitle=" + usedValue;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			// jsonStuff begins here
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = null;

			try {
				jsonObject = (JSONObject) parser.parse(in);
			} catch (ParseException e) {
			}
			
		
			movie = makeMovieObject(jsonObject);
	
			in.close();
			return movie;
		}
		// will return an empty object
		return movie;
	}
	
	private List<String> putSimilarInList(JSONObject jsonObject) {
		List<String> similarMovies = new ArrayList<>();
		
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("Similar");
		JSONArray resultArray = (JSONArray)jsonObject2.get("Results");
		
		for(int i = 0; i < resultArray.size(); i++) {
			JSONObject jsonObject3 = (JSONObject) resultArray.get(i);
			
			if(jsonObject3.get("Type").equals("movie") || jsonObject3.get("Type").equals("show")) {
				similarMovies.add((String) jsonObject3.get("Name"));
			}
		}
		
		return similarMovies;
	}
	
	private Movies makeMovieObject(JSONObject jsonObject) {
		Movies movie = new Movies();
		movie.setTitle((String) jsonObject.get("Title"));
		movie.setYear((String) jsonObject.get("Year"));
		movie.setRuntime((String) jsonObject.get("Runtime"));
		movie.setGenre((String) jsonObject.get("Genre"));
		movie.setDirector((String) jsonObject.get("Director"));
		movie.setWriter((String) jsonObject.get("Writer"));
		movie.setActors((String) jsonObject.get("Actors"));
		movie.setPlot((String) jsonObject.get("Plot"));
		movie.setLang((String) jsonObject.get("Language"));
		movie.setCountry((String) jsonObject.get("Country"));
		movie.setAwards((String) jsonObject.get("Awards"));
		movie.setPoster((String) jsonObject.get("Poster"));
		movie.setMetascore((String) jsonObject.get("Metascore"));
		movie.setImdbRating((String) jsonObject.get("imdbRating"));
		movie.setVotes((String) jsonObject.get("imdbVotes"));
		movie.setType((String) jsonObject.get("Type"));
		return movie;
	}

}