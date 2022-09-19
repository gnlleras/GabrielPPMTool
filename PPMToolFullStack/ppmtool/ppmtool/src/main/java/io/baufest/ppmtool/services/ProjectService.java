package io.baufest.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.baufest.ppmtool.domain.Backlog;
import io.baufest.ppmtool.domain.Project;
import io.baufest.ppmtool.domain.User;
import io.baufest.ppmtool.exceptions.ProjectIdException;
import io.baufest.ppmtool.repositories.BacklogRepository;
import io.baufest.ppmtool.repositories.ProjectRepository;
import io.baufest.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdateProject(Project project, String username){
		
		User user = userRepository.findByUsername(username);
		project.setProjectLeader(user.getUsername());		
		project.setUser(user);
		
		try{
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			//Evita que el backlog se muestre como null
			if(project.getId() != null) {
				Backlog backlog  = backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()); 
				project.setBacklog(backlog);
			}
			
			return projectRepository.save(project);
		}catch (Exception e) {
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists ");
		}			
	}
	
	
	public Project findProjectByIdentifier(String projectId){
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null){
			throw new ProjectIdException("Project ID '"+projectId+"' does not exists");
		}
		
		return project;
	}
	
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	
	public void deleteProjectByIdentifier(String projectId) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null) {
			throw new ProjectIdException("Cannot Project with ID '"+projectId+"'. This does not exists");
		}
		
		projectRepository.delete(project);
	}

}
 