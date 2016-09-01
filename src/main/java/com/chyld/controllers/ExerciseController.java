package com.chyld.controllers;


import com.chyld.entities.Exercise;
import com.chyld.entities.Profile;
import com.chyld.entities.User;
import com.chyld.security.JwtToken;
import com.chyld.services.ExerciseService;
import com.chyld.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    private static final String EMAIL_EXISTS_MESSAGE = "Not yours";
    private final UserService userService;
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(final UserService userService, final ExerciseService exerciseService) {
        this.userService = userService;
        this.exerciseService = exerciseService;
    }

    @RequestMapping(value = "/addexercise", method = RequestMethod.PUT)
    public ResponseEntity<?> AddExcercise(@Valid @RequestBody Exercise exercise, Principal user) throws JsonProcessingException {
        int uid = ((JwtToken)user).getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.addExercise(uid, exercise));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> UpdateExcercise(@PathVariable int id, @Valid @RequestBody Exercise exercise, Principal user) throws JsonProcessingException {
        int uid = ((JwtToken)user).getUserId();
        Exercise exe = this.exerciseService.updateExercise(uid, id, exercise);
        if ( exe == null) {
            return ResponseEntity.badRequest().body(EMAIL_EXISTS_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(exe);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> DeleteExcercise(@PathVariable int id, Principal user) throws JsonProcessingException {
        int uid = ((JwtToken)user).getUserId();
        Exercise exerciseToBeDeleted = this.exerciseService.deleteExercise(uid,id);
        if (exerciseToBeDeleted != null) {
            return ResponseEntity.badRequest().body(EMAIL_EXISTS_MESSAGE);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(" ");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Exercise> getExercise(Principal user) {
        int uid = ((JwtToken)user).getUserId();
        User u = userService.findUserById(uid);
        return u.getExercises();
    }
}
