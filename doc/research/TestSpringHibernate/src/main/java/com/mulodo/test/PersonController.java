package com.mulodo.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mulodo.test.constraints.Constraints;
import com.mulodo.test.exception.DAOException;
import com.mulodo.test.model.Person;
import com.mulodo.test.service.PersonService;

@RestController
public class PersonController {
	
	private PersonService personService;
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	@RequestMapping(value = "/person", method = RequestMethod.GET, headers="Accept=application/json")
	public String listPerson(@RequestParam("id") String idTemp,@RequestParam("name") String name
			,@RequestParam("country") String country,@RequestParam("pageNum") String pageNumTemp) throws JSONException{
		
		JSONObject result = new JSONObject();
		try {
			
			int id = 0;
			int pageNum = 0;
			try{
				id = Integer.parseInt(idTemp);
			}catch (Exception ex){
				id = 0;
			}
			
			try{
				pageNum = Integer.parseInt(pageNumTemp);
			}catch(Exception ex){
				pageNum = 0;
			}
			
			if(name.equals("")) name = null;
			if(country.equals("")) country = null;
			
			
			List<Person> listPersons = this.personService.listPersons(id, name, country, pageNum);
			int totalPersons = this.personService.totalPersons(id, name, country);
			
			result.put(Constraints.PERSON, "");
			result.put(Constraints.LIST_PERSONS, listPersons);
			result.put(Constraints.PAGE_NUM, pageNum);
			result.put(Constraints.LIMIT_ROW_STRING, Constraints.LIMIT_ROW);
			int totalPage = (int) Math.round(totalPersons/Constraints.LIMIT_ROW + 0.5);
			result.put(Constraints.TOTAL_PAGE, totalPage);
		} catch (Exception e) {
			
			e.printStackTrace();
			result.put(Constraints.MESSAGE, Constraints.ERROR);
		}
		
		return result.toString();
	}
	
	@RequestMapping(value = "/person/saveOrUpdate", method = RequestMethod.POST, headers="Accept=application/json")
	public String addPerson(@RequestParam("id") String idTemp,@RequestParam("name") String name, @RequestParam("country") String country){
		
		JSONObject result = new JSONObject();
//		if(p.getId() != 0){
		
			System.out.println("id C= "+ idTemp);
			System.out.println("name C= "+ name);
			System.out.println("Country C= "+ country);
		if(name == null || name.equals("")){
			result.put(Constraints.MESSAGE, Constraints.NAME_ERROR);
			return result.toString();
		}
		int id = 0;
		try{
			id = Integer.parseInt(idTemp); 
		}catch(Exception ex){
			id = 0;
		}
		
		if(id != 0){
			System.out.println("load edit");

			try {
				Person personTemp = this.personService.getPersonById(id);
				if(personTemp == null){
					result.put(Constraints.MESSAGE, Constraints.NOT_FOUND_PERSON);
					return result.toString();
				}
				Person person = new Person();
				person.setId(id);
				person.setName(name);
				person.setCountry(country);
				this.personService.updatePerson(person);
				result.put(Constraints.MESSAGE, Constraints.EDIT_SUCCESS);
			} catch (DAOException e) {
				e.printStackTrace();
				result.put(Constraints.MESSAGE, Constraints.ERROR);
			}
		}else{
			try {
				Person person = new Person();
				person.setName(name);
				person.setCountry(country);
				this.personService.addPerson(person);
				result.put(Constraints.MESSAGE, Constraints.ADD_SUCCESS);
			} catch (DAOException e) {			
				e.printStackTrace();
				result.put(Constraints.MESSAGE, Constraints.ERROR);
			}
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/person/remove/{id}", method = RequestMethod.DELETE, headers="Accept=application/json")
	public String removePerson(@PathVariable("id") String idTemp){
		JSONObject result = new JSONObject();
		try {
			int id = 0;
			id = Integer.parseInt(idTemp);
			Person person = this.personService.getPersonById(id);
			if(person == null){
				result.put(Constraints.MESSAGE, Constraints.NOT_FOUND_PERSON);
				return result.toString();
			}
			this.personService.removePerson(id);
			result.put(Constraints.MESSAGE, Constraints.DELETE_SUCCESS);
		} catch (DAOException e) {
		
			e.printStackTrace();
			result.put(Constraints.MESSAGE, Constraints.ERROR);
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/person/edit/{id}", method = RequestMethod.GET, headers="Accept=application/json")
	public String editPerson(@PathVariable("id") String idTemp){
		
		JSONObject result = new JSONObject();
		
		try {
			int id = 0;
			id = Integer.parseInt(idTemp);
			Person person = this.personService.getPersonById(id);
			JSONObject personJSON = new JSONObject(person);
			result.put(Constraints.PERSON, personJSON);
//			model.addAttribute("listPersons", this.personService.listPersons());
		} catch (DAOException e) {		
			e.printStackTrace();
			result.put(Constraints.MESSAGE, Constraints.ERROR);
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/person/findByName/{name}", method = RequestMethod.GET)
	public String findByName(@PathVariable("name") String name, Model model){
		try {
			model.addAttribute("person", this.personService.getPersonByName(name));
//			model.addAttribute("listPersons", this.personService.listPersons());
		} catch (DAOException e) {
		
			e.printStackTrace();
		}
		return "person";		
	}
}
