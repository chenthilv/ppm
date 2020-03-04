package com.ppm.boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppm.boot.domain.Backlog;
import com.ppm.boot.domain.Project;
import com.ppm.boot.domain.User;
import com.ppm.boot.exceptions.ProjectIdException;
import com.ppm.boot.repositories.ProjectRepository;
import com.ppm.boot.repositories.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdate(Project project, String username) {
		
		User user = userRepository.findByUsername(username);
		
		if(project.getId() == null) {
			Backlog backlog = new Backlog();
			backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			backlog.setProject(project);
			project.setBacklog(backlog);
			project.setUser(user);
			
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
	
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project = findProjectByIdentifier(projectId);
		
		if(project == null) {
			throw new ProjectIdException("Project Identifier"+ projectId+ " does not exist");
		}
		
		projectRepository.delete(project);
	}
}
 