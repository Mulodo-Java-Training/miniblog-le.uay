package com.mulodo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mulodo.test.model.Person;
import com.mulodo.test.service.PersonService;

@Controller
public class PersonController {
	
	private PersonService personService;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public String listPerson(Model model){
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		
		return "person";
	}
	
	@RequestMapping(value = "/person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Person p){
		
		if(p.getId() != 0){
//			System.out.println("id = "+ p.getId());
//			System.out.println("name = "+ p.getName());
//			System.out.println("Country = "+ p.getCountry());
			this.personService.updatePerson(p);
		}else{
			this.personService.addPerson(p);
		}
		return "redirect:/person";
	}
	
	@RequestMapping(value = "/person/remove/{id}", method = RequestMethod.GET)
	public String removePerson(@PathVariable("id") int id){
		
		this.personService.removePerson(id);
		return "redirect:/person";
	}
	
	@RequestMapping(value = "/person/edit/{id}", method = RequestMethod.GET)
	public String editPerson(@PathVariable("id") int id, Model model){
		model.addAttribute("person", this.personService.getPersonById(id));
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	
	@RequestMapping(value = "/person/findByName/{name}", method = RequestMethod.GET)
	public String findByName(@PathVariable("name") String name, Model model){
		model.addAttribute("person", this.personService.getPersonByName(name));
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";		
	}
}
