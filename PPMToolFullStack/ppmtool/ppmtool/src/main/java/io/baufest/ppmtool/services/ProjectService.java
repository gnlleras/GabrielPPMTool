package io.baufest.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.baufest.ppmtool.domain.Project;
import io.baufest.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project){
		
		return projectRepository.save(project);
		
	}

}
 