package com.webApp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoxService {
	@Autowired
	private FoxRepository repo;
	
	public List<Fox> listAll() {
		return repo.findAll();
	}
	
	public void save(Fox fox) {
		repo.save(fox);
	}
	
	public Fox get(Long id) {
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public List<Fox> listAllFoxesByID(String userSearch) {
        //empty list where all the found foxes will be put
		List<Fox> returnList = new ArrayList<Fox>();
        //list of all foxes
        List<Fox> foxList = listAll();
        
        for(int i = 0; i < foxList.size(); i++) {
        	
        	if(foxList.get(i).getName().equals(userSearch) ||
        			foxList.get(i).getSpecies().equals(userSearch)) {
        	
        		returnList.add(foxList.get(i));
        	}
        	
        }
 
        return returnList;
    }
	
}
