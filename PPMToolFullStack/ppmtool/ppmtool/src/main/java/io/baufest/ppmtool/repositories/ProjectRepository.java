package io.baufest.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.baufest.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	@Override
	default Iterable<Project> findAllById(Iterable<Long> ids) {
		return null;
	}
	
	Project findByProjectIdentifier(String projectId);
	
	@Override
	Iterable<Project> findAll();

}
