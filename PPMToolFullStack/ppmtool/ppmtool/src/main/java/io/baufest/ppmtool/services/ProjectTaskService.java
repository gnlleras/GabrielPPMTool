package io.baufest.ppmtool.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.baufest.ppmtool.domain.Backlog;
import io.baufest.ppmtool.domain.Project;
import io.baufest.ppmtool.domain.ProjectTask;
import io.baufest.ppmtool.exceptions.ProjectNotFoundException;
import io.baufest.ppmtool.repositories.BacklogRepository;
import io.baufest.ppmtool.repositories.ProjectRepository;
import io.baufest.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository; 
	
	@Autowired
	private ProjectRepository projectRepository; 
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		try {
			//PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			//set the bl to pt
			projectTask.setBacklog(backlog);
			
			//we want our project sequence to be like this: IDPRO-1  IDPRO-2
			Integer BacklogSequence = backlog.getPTSequence();
			
			// Update the BL SEQUENCE
			BacklogSequence++;
			
			backlog.setPTSequence(BacklogSequence);
			
			//Add Sequence to Project Task
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//INITIAL priority when priority null
			if(projectTask.getPriority()==null) { //en el futuro si necesitamos == 0 para manejar el form
				projectTask.setPriority(3);
			}
	        //INITIAL status when status is null
			if(projectTask.getStatus()==""||projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}

			return projectTaskRepository.save(projectTask);
		}
		catch(Exception e){
			throw new ProjectNotFoundException("Project not found");
		}
		
	}

	public Iterable<ProjectTask> findBacklogById(String id) {
		Project project = projectRepository.findByProjectIdentifier(id);
		if(project == null) {
			throw new ProjectNotFoundException("Project whit ID: '"+id+"' does not exist");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){
		
		//Existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project whit ID: '"+backlog_id+"' does not exist");
		}
		
		//Existing task
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task: '"+pt_id+"' does not exist");
		}
		
		//Task belong to backlog
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);
		}
		
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		
		projectTask = updatedTask;
		
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		
		projectTaskRepository.delete(projectTask);
	}
	//Update project Task
	
	//find existing project task
	//replace it with update task
	//save update
}
