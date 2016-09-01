package com.chyld.controllers;

import com.chyld.entities.Exercise;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(value = {"/prepare-db.sql"})
public class ExerciseControllerTest {
    @Before
    public void setUp() throws Exception {
        RestAssured.port = 8001;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldCreateAnExerciseAndAddToUser() throws Exception {
        Map<String, Object>exerciseJson = new HashMap<>();
        exerciseJson.put("exercise", "KICKBOXING");
        exerciseJson.put("quantity", "50");
        exerciseJson.put("calories", "2000");
        exerciseJson.put("duration", "60");

        given().log().all().and().given().
                contentType(ContentType.JSON).
                header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJib2JAYW9sLmNvbSIsInVzZXJfaWQiOjEsInJvbGVzIjpbeyJpZCI6MSwidmVyc2lvbiI6MCwicm9sZSI6IlVTRVIiLCJjcmVhdGVkIjoxNDcyNzY2ODc0MDAwLCJtb2RpZmllZCI6MTQ3Mjc2Njg3NDAwMCwiYXV0aG9yaXR5IjoiVVNFUiJ9LHsiaWQiOjIsInZlcnNpb24iOjAsInJvbGUiOiJBRE1JTiIsImNyZWF0ZWQiOjE0NzI3NjY4NzQwMDAsIm1vZGlmaWVkIjoxNDcyNzY2ODc0MDAwLCJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiZXhwIjoxNDczNTcwMDAwfQ.QAWB8mffvSVPcIPSqTWhFybt_n5IjbU5mRdg3a_xgjMNdGtGBT0VK232nvJS4nhy_KyNvoAl-6Lm9E6mEx7lNQ").
                body(exerciseJson).
                put("/api/exercises/addexercise")
                .then().log().all()
                .statusCode(201)
                .body(
                        "username", equalTo("bob@aol.com"),
                        "exercises[1].exercise", equalTo("KICKBOXING"),
                        "exercises[1].quantity", equalTo(50),
                        "exercises[1].calories", equalTo(2000),
                        "exercises[1].duration", equalTo(60)

                );

    }

    @Test
    public void shouldUpdateAnExerciseForTheUser() throws Exception {
        Map<String, Object>exerciseJson = new HashMap<>();
        exerciseJson.put("exercise", "KICKBOXING");
        exerciseJson.put("quantity", "50");
        exerciseJson.put("calories", "1000");
        exerciseJson.put("duration", "40");
        given().log().all().and().given().
                contentType(ContentType.JSON).
                header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJib2JAYW9sLmNvbSIsInVzZXJfaWQiOjEsInJvbGVzIjpbeyJpZCI6MSwidmVyc2lvbiI6MCwicm9sZSI6IlVTRVIiLCJjcmVhdGVkIjoxNDcyNzY2ODc0MDAwLCJtb2RpZmllZCI6MTQ3Mjc2Njg3NDAwMCwiYXV0aG9yaXR5IjoiVVNFUiJ9LHsiaWQiOjIsInZlcnNpb24iOjAsInJvbGUiOiJBRE1JTiIsImNyZWF0ZWQiOjE0NzI3NjY4NzQwMDAsIm1vZGlmaWVkIjoxNDcyNzY2ODc0MDAwLCJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiZXhwIjoxNDczNTcwMDAwfQ.QAWB8mffvSVPcIPSqTWhFybt_n5IjbU5mRdg3a_xgjMNdGtGBT0VK232nvJS4nhy_KyNvoAl-6Lm9E6mEx7lNQ").
                body(exerciseJson).
                put("/api/exercises/1")
                .then().log().all()
                .statusCode(201)
                .body(
                        "exercise", equalTo("KICKBOXING"),
                        "quantity", equalTo(50),
                        "calories", equalTo(1000),
                        "duration", equalTo(40)

                );


    }

    @Test
    public void shouldDeleteAnExerciseForTheUser() throws Exception {
        given().log().all().and().given().
                header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJib2JAYW9sLmNvbSIsInVzZXJfaWQiOjEsInJvbGVzIjpbeyJpZCI6MSwidmVyc2lvbiI6MCwicm9sZSI6IlVTRVIiLCJjcmVhdGVkIjoxNDcyNzY2ODc0MDAwLCJtb2RpZmllZCI6MTQ3Mjc2Njg3NDAwMCwiYXV0aG9yaXR5IjoiVVNFUiJ9LHsiaWQiOjIsInZlcnNpb24iOjAsInJvbGUiOiJBRE1JTiIsImNyZWF0ZWQiOjE0NzI3NjY4NzQwMDAsIm1vZGlmaWVkIjoxNDcyNzY2ODc0MDAwLCJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiZXhwIjoxNDczNTcwMDAwfQ.QAWB8mffvSVPcIPSqTWhFybt_n5IjbU5mRdg3a_xgjMNdGtGBT0VK232nvJS4nhy_KyNvoAl-6Lm9E6mEx7lNQ").
                delete("/api/exercises/1")
                .then().log().all()
                .statusCode(201);
    }

}