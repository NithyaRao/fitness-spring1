package com.chyld.repositories;

import com.chyld.entities.Exercise;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface IExerciseRepository extends PagingAndSortingRepository<Exercise, Integer> {
}