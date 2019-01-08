package com.example.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Todo;
import com.example.service.TodoService;


@Controller
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping("/list-todos")
	public String showTodo(ModelMap modelMap) {
		String name = getLoggedInUserName(modelMap);
		modelMap.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}

	private String getLoggedInUserName(ModelMap modelMap) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			return ((UserDetails)principal).getUsername();
		}
		return principal.toString();
	}
	
	@GetMapping("/add-todos")
	public String showTodoAddPage(ModelMap modelMap) {
		modelMap.addAttribute("todo", new Todo(0, getLoggedInUserName(modelMap), "Default Desc", new Date(), false));
		return "todos";
	}
	
	@PostMapping("/add-todos")
	public String addTodo(ModelMap modelMap,@Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			return "todos";
		}
		service.addTodo(getLoggedInUserName(modelMap), todo.getDesc(), todo.getTargetDate(), false);
		return "redirect:/list-todos";
	}
	
	@GetMapping("/delete-todo")
	public String deleteTodo(@RequestParam int id) {
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	@GetMapping("/update-todo")
	public String showUpdateTodoPage(@RequestParam int id, ModelMap modelMap) {
		Todo todo = service.retrieveTodo(id);
		modelMap.put("todo", todo);
		return "todos";
	}
	
	@PostMapping("/update-todo")
	public String updateTodo(ModelMap modelMap, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todos";
		}
		todo.setUser(getLoggedInUserName(modelMap));
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}
	
}
