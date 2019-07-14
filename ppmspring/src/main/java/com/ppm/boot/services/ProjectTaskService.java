package com.ppm.boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppm.boot.domain.Backlog;
import com.ppm.boot.domain.ProjectTask;
import com.ppm.boot.repositories.BacklogRepository;
import com.ppm.boot.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		projectTask.setBacklog(backlog);
		
		Integer backlogSequence = backlog.getPTSequence();
		backlogSequence++;
		
		projectTask.setProjectIdentifier(projectIdentifier);
		projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
		
		if(projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		}
		if(projectTask.getStatus() == null) {
			projectTask.setStatus("Open");
		}
		
		return projectTaskRepository.save(projectTask);
	}
	
	
	
}
