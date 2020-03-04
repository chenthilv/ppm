package com.ppm.boot.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppm.boot.domain.Project;
import com.ppm.boot.error.ValidationErrors;
import com.ppm.boot.services.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){
		if(result.hasErrors()) {		
			
			List<ValidationErrors> validationErrorsList = new ArrayList<>();
			for(FieldError fieldError : result.getFieldErrors()) {
				ValidationErrors validationErrors = new ValidationErrors();
				validationErrors.setKey(fieldError.getField());
				validationErrors.setMessage(fieldError.getDefaultMessage());
				validationErrorsList.add(validationErrors);
				System.out.println(validationErrors);
				
			}
			return new ResponseEntity<List<ValidationErrors>>(validationErrorsList,HttpStatus.BAD_REQUEST);
		}
		Project response = projectService.saveOrUpdate(project, principal.getName());
		return new ResponseEntity<Project>(response, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{projectId}") 
	public ResponseEntity<?> getProjectById(@PathVariable String projectId){
		Project project = projectService.findProjectByIdentifier(projectId);
		return new ResponseEntity<Project>(project, HttpStatus.FOUND);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllProjects(Principal principal){
		Iterable<Project> projects = projectService.findAllProjects(principal.getName());
		return new ResponseEntity<Iterable<Project>>(projects, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{projectId}")
	public ResponseEntity<?> deleteById(@PathVariable String projectId){
		projectService.deleteProjectByIdentifier(projectId);
		return new ResponseEntity<String>(projectId+" deleted successfully.", HttpStatus.OK);
	}
	
}
