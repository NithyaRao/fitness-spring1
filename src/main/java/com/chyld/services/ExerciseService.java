package com.chyld.services;

import com.chyld.entities.Exercise;

import com.chyld.repositories.IExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class ExerciseService  {
    private IExerciseRepository repository;

    @Autowired
    public void setRepository(IExerciseRepository repository) {
        this.repository = repository;
    }

    public Exercise saveExercise(Exercise exercise) {
        return repository.save(exercise);
    }

    public Exercise findExerciseById(Integer id) {
        return repository.findOne(id);
    }


    public Exercise updateExercise( int uid, int id, Exercise exercise) {
        Exercise exercise1  = this.repository.findOne(id);
        if (exercise1.getUser().getId() == uid) {
            exercise1.setCalories(exercise.getCalories());
            exercise1.setDuration(exercise.getDuration());
            exercise1.setExercise(exercise.getExercise());
            exercise1.setQuantity(exercise.getQuantity());
            return this.repository.save(exercise1);
        }
        else {
            return null;
        }
    }

    public Exercise deleteExercise(int uid, int id) {
        Exercise exercise = this.repository.findOne(id);
        if (exercise.getUser().getId() == uid){
            this.repository.delete(id);
            return null;
        } else {
            return exercise;
        }
    }
}
