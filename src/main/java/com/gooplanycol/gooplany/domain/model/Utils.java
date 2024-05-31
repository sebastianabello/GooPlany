package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.*;

public class Utils {

    protected EventCategory findEventCategory(String eventCategory) {
        return switch (eventCategory) {
            case "music" -> EventCategory.MUSIC;
            case "theater" -> EventCategory.THEATER;
            case "sports" -> EventCategory.SPORTS;
            case "exhibition" -> EventCategory.EXHIBITION;
            case "conference" -> EventCategory.CONFERENCE;
            case "workshop" -> EventCategory.WORKSHOP;
            case "party" -> EventCategory.PARTY;
            case "festival" -> EventCategory.FESTIVAL;
            case "cultural" -> EventCategory.CULTURAL;
            case "religious" -> EventCategory.RELIGIOUS;
            case "charity" -> EventCategory.CHARITY;
            case "other" -> EventCategory.OTHER;
            default -> EventCategory.NOT_SPECIFIED;
        };
    }

    protected TypeOfAudience findTypeOfAudience(String typeOfAudience) {
        return switch (typeOfAudience) {
            case "children" -> TypeOfAudience.CHILDREN;
            case "teenagers" -> TypeOfAudience.TEENAGERS;
            case "adults" -> TypeOfAudience.ADULTS;
            case "elderly" -> TypeOfAudience.ELDERLY;
            case "family" -> TypeOfAudience.FAMILY;
            case "students" -> TypeOfAudience.STUDENTS;
            case "professionals" -> TypeOfAudience.PROFESSIONALS;
            case "tourists" -> TypeOfAudience.TOURISTS;
            case "locals" -> TypeOfAudience.LOCALS;
            case "foreigners" -> TypeOfAudience.FOREIGNERS;
            default -> TypeOfAudience.NOT_SPECIFIED;
        };
    }

    protected TypeOfPlace findTypeOfPlace(String typeOfPlace) {
        return switch (typeOfPlace) {
            case "park" -> TypeOfPlace.PARK;
            case "bar" -> TypeOfPlace.BAR;
            case "shopping_center" -> TypeOfPlace.SHOPPING_CENTER;
            case "restaurant" -> TypeOfPlace.RESTAURANT;
            case "cafe" -> TypeOfPlace.CAFE;
            case "museum" -> TypeOfPlace.MUSEUM;
            case "library" -> TypeOfPlace.LIBRARY;
            case "gym" -> TypeOfPlace.GYM;
            case "theater" -> TypeOfPlace.THEATER;
            case "concert_hall" -> TypeOfPlace.CONCERT_HALL;
            case "beach" -> TypeOfPlace.BEACH;
            case "hotel" -> TypeOfPlace.HOTEL;
            case "stadium" -> TypeOfPlace.STADIUM;
            case "university" -> TypeOfPlace.UNIVERSITY;
            case "school" -> TypeOfPlace.SCHOOL;
            case "church" -> TypeOfPlace.CHURCH;
            default -> TypeOfPlace.NOT_SPECIFIED;
        };
    }

    protected StatusEventPost findStatusEventPost(String statusEventPost) {
        return switch (statusEventPost.toLowerCase()) {
            case "approved" -> StatusEventPost.APPROVED;
            case "rejected" -> StatusEventPost.REJECTED;
            case "finished" -> StatusEventPost.FINISHED;
            default -> StatusEventPost.PENDING;
        };
    }

    protected StatusEventParticipant StatusRegistration(String status) {
        return switch (status.toLowerCase()) {
            case "registered" -> StatusEventParticipant.REGISTERED;
            case "canceled" -> StatusEventParticipant.CANCELED;
            default -> StatusEventParticipant.UNREGISTERED;
        };
    }

}
