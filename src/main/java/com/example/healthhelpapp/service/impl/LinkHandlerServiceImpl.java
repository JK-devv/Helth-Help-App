package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.controller.AuthenticationController;
import com.example.healthhelpapp.controller.HealthHelpAppController;
import com.example.healthhelpapp.service.LinkHandlerService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static com.example.healthhelpapp.util.AppConstant.REL_AUTHENTICATION;
import static com.example.healthhelpapp.util.AppConstant.REL_CREATE_FOOD;
import static com.example.healthhelpapp.util.AppConstant.REL_CREATE_PATIENT;
import static com.example.healthhelpapp.util.AppConstant.REL_LIST_OF_FOOD;
import static com.example.healthhelpapp.util.AppConstant.REL_LIST_OF_PATIENT;
import static com.example.healthhelpapp.util.AppConstant.REL_START_PAGE;
import static com.example.healthhelpapp.util.AppConstant.REL_STOCK;

@Component
public class LinkHandlerServiceImpl implements LinkHandlerService {

    public Link addRelLinkToStartPage() {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(HealthHelpAppController.class)
                                .handleStart())
                .withRel(REL_START_PAGE);
    }

    public Link addRelLinkToItemById(Integer itemId) {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(HealthHelpAppController.class)
                                .getItemById(itemId))
                .withSelfRel();
    }

    public Link addRelLinkToStock() {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(HealthHelpAppController.class)
                                .getListOfMedicalItems()
                )
                .withRel(REL_STOCK);
    }

    public Link addRelLinkToFoodList() {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(HealthHelpAppController.class)
                                .getListOfFood()
                )
                .withRel(REL_LIST_OF_FOOD);
    }

    public Link addRelLinkToPatientList() {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(HealthHelpAppController.class)
                                .getListOfPatients()
                )
                .withRel(REL_LIST_OF_PATIENT);
    }

    public Link addRelLinkToPatientById(Integer patientId) {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(HealthHelpAppController.class)
                                .getPatientById(patientId))
                .withSelfRel();
    }

    public Link addRelLinkToCreatePatient() {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn
                                (HealthHelpAppController.class)
                        .savePatient(null))
                .withRel(REL_CREATE_PATIENT);
    }

    public Link addRelLinkToCreateItem() {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn
                                (HealthHelpAppController.class)
                        .saveFood(null))
                .withRel(REL_CREATE_FOOD);
    }

    public Link addRelLinkToLogin() {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                AuthenticationController.class).authentication(null))
                .withRel(REL_AUTHENTICATION);
    }

    @Override
    public Link addRelLinkToFoodById(Integer foodId) {
        return WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(HealthHelpAppController.class)
                                .getFoodById(foodId))
                .withSelfRel();
    }
}
