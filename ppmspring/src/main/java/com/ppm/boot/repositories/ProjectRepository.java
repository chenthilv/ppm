package com.ppm.boot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ppm.boot.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	public Project findByProjectIdentifier(String projectIdentifier);
	

	public Iterable<Project> findAll();
	
	public Iterable<Project> findAllByProjectLeader(String username);

}
