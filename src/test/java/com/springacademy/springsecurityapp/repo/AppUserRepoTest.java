package com.springacademy.springsecurityapp.repo;

import com.springacademy.springsecurityapp.model.AppUser;
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
public class AppUserRepoTest {

    @Autowired
    private AppUserRepo appUserRepo;

    @Test
    public void testAppUserRepoSave(){
        //Given
        Visit visit2 = new Visit(2L,LocalDate.now(), LocalTime.now(), "John", 20, "Scorpio");
        Visit visit3 = new Visit(3L,LocalDate.now(), LocalTime.now(), "Tom", 40, "Cancer");
        List<Visit> visitList = new ArrayList<>();
        visitList.add(visit2);
        visitList.add(visit3);
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        AppUser appUser = new AppUser(1L,"username@gmail.com", "Password",
                true, visitList, roles);
        visit2.setAppUser(appUser);
        visit3.setAppUser(appUser);

        //When
        appUserRepo.save(appUser);

        //Then
        long id = appUser.getId();
        Optional<AppUser> appUserById = appUserRepo.findById(id);

        Assert.assertTrue(appUserById.isPresent());
        Assert.assertEquals("username@gmail.com", appUserById.get().getUsername());
        Assert.assertEquals("Password", appUserById.get().getPassword());
        Assert.assertEquals("John", appUserById.get().getVisitList().get(0).getUserFirstName());
        Assert.assertEquals(20, appUserById.get().getVisitList().get(0).getUserAge().intValue());
        Assert.assertEquals("Scorpio", appUserById.get().getVisitList().get(0).getUserZodiacSign());
        Assert.assertEquals("Tom", appUserById.get().getVisitList().get(1).getUserFirstName());
        Assert.assertEquals(40, appUserById.get().getVisitList().get(1).getUserAge().intValue());
        Assert.assertEquals("Cancer", appUserById.get().getVisitList().get(1).getUserZodiacSign());
        Assert.assertEquals(1, appUserById.get().getRoles().size());
    }
}