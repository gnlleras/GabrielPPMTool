package io.baufest.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.baufest.ppmtool.domain.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long>{
	
	Backlog findByProjectIdentifier(String Identifier);

}
