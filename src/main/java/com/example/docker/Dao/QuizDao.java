package com.example.docker.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.docker.model.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

	@Query("SELECT q FROM Quiz q JOIN FETCH q.questions WHERE q.id = :id")
	Optional<Quiz> findById(Integer id);

}
