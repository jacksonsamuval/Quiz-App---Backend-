package com.example.docker.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

import com.example.docker.QuestionWrapper;
import com.example.docker.Dao.QuestionDao;
import com.example.docker.Dao.QuizDao;
import com.example.docker.model.Question;
import com.example.docker.model.Quiz;
import com.example.docker.model.Response;

@Service
public class QuizService 
{
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category);
		Quiz quiz = new Quiz();
		quiz.setQuestions(questions);
		quiz.setTitle(title);
		quizDao.save(quiz);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
	}


	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) 
	{
		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionsFromDb = quiz.get().getQuestions();
		List<QuestionWrapper> questionForUser = new ArrayList<>();
		for(Question q : questionsFromDb)
		{
			QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
			questionForUser.add(qw);
		}	
		return new ResponseEntity<>(questionForUser, HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculate(Integer id, List<Response> responses) {
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions= quiz.getQuestions();
		int right =0;
		int i=0;
		for(Response response: responses)
		{
			if(response.getResponse().equals(questions.get(i).getRightAnswer()))
				right++;
			i++;
		}		
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
}
