package com.springacademy.springsecurityapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "VISITS")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long visitId;

    @NotNull
    private LocalDate visitDate;

    @NotNull
    private LocalTime visitHour;

    @NotNull
    private String userFirstName;

    @NotNull
    private Integer userAge;

    @NotNull
    private String userZodiacSign;

    @ManyToOne
    @JoinColumn(name = "FK_APP_USER_ID")
    private AppUser appUser;

    public Visit() {
    }

    public Visit(Long visitId, LocalDate visitDate, LocalTime visitHour, String userFirstName, Integer userAge, String userZodiacSign) {
        this.visitId = visitId;
        this.visitDate = visitDate;
        this.visitHour = visitHour;
        this.userFirstName = userFirstName;
        this.userAge = userAge;
        this.userZodiacSign = userZodiacSign;
    }


    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public LocalTime getVisitHour() {
        return visitHour;
    }

    public void setVisitHour(LocalTime visitHour) {
        this.visitHour = visitHour;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserZodiacSign() {
        return userZodiacSign;
    }

    public void setUserZodiacSign(String userZodiacSign) {
        this.userZodiacSign = userZodiacSign;
    }
}
