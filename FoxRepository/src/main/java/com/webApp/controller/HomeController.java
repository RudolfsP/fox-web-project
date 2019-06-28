package com.webApp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webApp.Fox;
import com.webApp.FoxService;

@Controller
public class HomeController {
	@Autowired
	private FoxService service;
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Fox> listFoxes = service.listAll();
		
		model.addAttribute("listFoxes", listFoxes);
		
		Collections.shuffle(listFoxes);
		
		model.addAttribute("showFox1", listFoxes.get(0));
		model.addAttribute("showFox2", listFoxes.get(1));
		model.addAttribute("showFox3", listFoxes.get(2));
		
		return "index";
	}
	
	@RequestMapping("/foxTable")
	public String foxTable(Model model) {
		List<Fox> listFoxes = service.listAll();
		//System.out.println(listFoxes);
		model.addAttribute("listFoxes", listFoxes);
		return "foxTable";
	}
	
	@RequestMapping("/new")
	public String showNewFoxForm(Model model) {
		
		Fox fox = new Fox();
		model.addAttribute("fox", fox);
		
		return "new_fox";
	}
	
	@RequestMapping("/home")
	public String openIndexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("fox") Fox fox) {
		service.save(fox);
		
		return "redirect:/foxTable";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditFoxPage(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_fox");
		Fox fox = service.get(id);
		
		mav.addObject("fox", fox);
		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteFox(@PathVariable(name = "id") Long id) {
		service.delete(id);
		
		return "redirect:/foxTable";
	}
	
	@RequestMapping("/find/{id}")
	public String findFox(@PathVariable(name = "id") String id, Model model) {
		
		String toReturn = "find_fox";
		
		 List<Fox> foxList = service.listAllFoxesByID(id);
	        model.addAttribute("foxList", foxList);
	        
	        if (foxList.size()==0) {
	            toReturn="redirect:/";
	        }        
	        
	        return toReturn;
	}
	
	@RequestMapping("/randomFox")
	public String randomFox(Model model) {
		List<Fox> listFoxes = service.listAll();
		List<String> urlList =  new ArrayList<>();
		
		//populate url list
		for(Fox f : listFoxes) {
			urlList.add(f.getUrl());
		}
		
		//generate a random url, if there is a duplicate, generate again
		String url = "";
		int timeout = 0;
		while(timeout <= 122) {
			url = generateUrl();
			
			if(!urlList.contains(url))
				break;
	
			else {
				timeout++;
				url = "https://picsum.photos/id/237/200/300";
			}
				
		}
		
		//add to model when the image url is generated
		model.addAttribute("randomUrl", url);
		
		return "randomFox";
	}
	
	//this method returns a list with randomly shuffled numbers
	public List<Fox> shuffleList(List<Fox> shuffleList) {
		Collections.shuffle(shuffleList);
		return shuffleList;
	}
	
	public String generateUrl() {
		//String url = "http://randomfox.ca/images/7.jpg";
		String url = "http://randomfox.ca/images/";
		//generate number from 1 to 122, because thats how many fox images are in the api
		Random rand = new Random();
		int randomNum = rand.nextInt((122 - 1) + 1) + 1;
		
		//return the modified url
		return url + randomNum + ".jpg";
	}
		
}
