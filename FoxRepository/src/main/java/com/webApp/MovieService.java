package com.webApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	@Autowired
	private MovieRepository repo;
	
	public List<Movies> listAll() {
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
        //empty list where all the found foxes will be put
		List<Movies> returnList = new ArrayList<Movies>();
        //list of all foxes
        List<Movies> movieList = listAll();
        
        for(int i = 0; i < movieList.size(); i++) {
        	if(movieList.get(i).getTitle().equals(userSearch)) {
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
		
		String toReturn ="";
		
		while ((inputLine = in.readLine()) != null) {
			if(counter == 114) {
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
		
		movieList.add(getMovieData(usedValue));
		
		String url = "http://127.0.0.1:7071/getMovie?movieTitle=" + usedValue;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		
		int counter = 0;
		int smCounter = 0;
		
		while ((inputLine = in.readLine()) != null) {
			
			if(inputLine.contains("\"Name\"")) {
				
				//later change ************* counter < 13
				if(counter != 0 && counter >= 1 && counter < 3 + smCounter) {
					inputLine = inputLine.substring(16);
					inputLine = inputLine.replace("\"", "");
					inputLine = inputLine.replace(" ", "-");
					inputLine = inputLine.replace(",", "");
					Movies similarMovie = getMovieData(inputLine);
					
					if((similarMovie.getType().contentEquals("movie") || similarMovie.getType().contentEquals("series"))
							&& similarMovie.getPoster().contains("http")) {
						movieList.add(similarMovie);
					}
					
					else {
						smCounter++;
					}
					
				}
				
				counter++;
			}
		}
		
		in.close();
		return movieList;
	}
	
	private Movies getMovieData(String searchValue) throws Exception {
		List<String> movieDataList = new ArrayList<>();
		Movies movie = new Movies();
		
		String usedValue = searchValue.replace(" ", "-");
		String url = "http://127.0.0.1:7071/getMovieData?movieTitle=" + usedValue;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		if(responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				String convertedData = fixStrings(inputLine);
				movieDataList.add(convertedData);
			}
			
			
			movieDataList.removeIf(n -> (n.equals("")));
	
			
			movie.setTitle(movieDataList.get(0));
			movie.setYear(movieDataList.get(1));
			movie.setRuntime(movieDataList.get(2));
			movie.setGenre(movieDataList.get(3));
			movie.setDirector(movieDataList.get(4));
			movie.setWriter(movieDataList.get(5));
			movie.setActors(movieDataList.get(6));
			movie.setPlot(movieDataList.get(7));
			movie.setLang(movieDataList.get(8));
			movie.setCountry(movieDataList.get(9));
			movie.setAwards(movieDataList.get(10));
			movie.setPoster(movieDataList.get(11));
			movie.setMetascore(movieDataList.get(12));
			movie.setImdbRating(movieDataList.get(13));
			movie.setVotes(movieDataList.get(14));
			movie.setType(movieDataList.get(15));
			
			in.close();
			return movie;
		}
		//will return an empty object
		return movie;
	}
			
	private String fixStrings(String inputLine) {
	
		if(inputLine.contains("\"Title\": ")) {
			inputLine = inputLine.replace("  \"Title\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Year\": ")) {
			inputLine = inputLine.replace("  \"Year\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Runtime\": ")) {
			inputLine = inputLine.replace("  \"Runtime\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Genre\": ")) {
			inputLine = inputLine.replace("  \"Genre\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Director\": ")) {
			inputLine = inputLine.replace("  \"Director\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Writer\": ")) {
			inputLine = inputLine.replace("  \"Writer\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Actors\": ")) {
			inputLine = inputLine.replace("  \"Actors\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Plot\": ")) {
			inputLine = inputLine.replace("  \"Plot\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Language\": ")) {
			inputLine = inputLine.replace("  \"Language\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Country\": ")) {
			inputLine = inputLine.replace("  \"Country\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Awards\": ")) {
			inputLine = inputLine.replace("  \"Awards\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Poster\": ")) {
			inputLine = inputLine.replace("  \"Poster\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Metascore\": ")) {
			inputLine = inputLine.replace("  \"Metascore\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"imdbRating\": ")) {
			inputLine = inputLine.replace("  \"imdbRating\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"imdbVotes\": ")) {
			inputLine = inputLine.replace("  \"imdbVotes\": \"", "");
			inputLine = inputLine.replace("\",", "");
			return inputLine;
		}
		
		if(inputLine.contains("\"Type\": ")) {
			inputLine = inputLine.replace("  \"Type\": \"", "");
			inputLine = inputLine.replace("\"", "");
			return inputLine;
		}
		return "";
	}
}
