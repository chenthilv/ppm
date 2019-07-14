package com.ppm.boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppm.boot.domain.Backlog;
import com.ppm.boot.domain.Project;
import com.ppm.boot.exceptions.ProjectIdException;
import com.ppm.boot.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdate(Project project) {
		
		if(project.getId() == null) {
			Backlog backlog = new Backlog();
			backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			backlog.setProject(project);
			project.setBacklog(backlog);
		}
		
		
		try {
			return projectRepository.save(project);
		}catch(Exception e) {
			throw new ProjectIdException("Project ID identifier : "+project.getProjectIdentifier().toUpperCase() +" is already exists.");
		}
		
	}
	
	public Project findProjectByIdentifier(String identifier) {
		Project project =  projectRepository.findByProjectIdentifier(identifier.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Project ID identifier : "+identifier.toUpperCase() +" does not exists.");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project = findProjectByIdentifier(projectId);
		
		if(project == null) {
			throw new ProjectIdException("Project Identifier"+ projectId+ " does not exist");
		}
		
		projectRepository.delete(project);
	}
}
 