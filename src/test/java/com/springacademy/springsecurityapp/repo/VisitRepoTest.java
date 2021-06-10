package com.springacademy.springsecurityapp.repo;

import com.springacademy.springsecurityapp.model.Visit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VisitRepoTest {

    @Autowired
    private VisitRepo visitRepo;

    @Test
    public void testVisitRepoSave(){
        //Given
        Visit visit1 = new Visit(1L,LocalDate.now(), LocalTime.now(), "Jan", 30, "Leo");

        //When
        visitRepo.save(visit1);

        //Then
        long id = visit1.getVisitId();
        Optional<Visit> visitById = visitRepo.findById(id);

        Assert.assertTrue(visitById.isPresent());
        Assert.assertEquals("Jan", visitById.get().getUserFirstName());
        Assert.assertEquals(30, visitById.get().getUserAge().intValue());
        Assert.assertEquals("Leo", visitById.get().getUserZodiacSign());
    }
}