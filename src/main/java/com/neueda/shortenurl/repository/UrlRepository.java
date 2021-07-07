package com.neueda.shortenurl.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.neueda.shortenurl.entity.Url;

@Repository
public interface UrlRepository extends CrudRepository<Url,String>{
	Optional<Url> findByAlias(String alias);
	
	Boolean existsByAlias(String alias);

}