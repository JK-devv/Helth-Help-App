package com.example.healthhelpapp.service;

import org.springframework.hateoas.Link;

public interface LinkHandlerService {

    Link addRelLinkToStartPage();

    Link addRelLinkToItemById(Integer itemId);

    Link addRelLinkToStock();

    Link addRelLinkToFoodList();

    Link addRelLinkToPatientList();

    Link addRelLinkToPatientById(Integer patientId);

    Link addRelLinkToCreatePatient();

    Link addRelLinkToCreateItem();

    Link addRelLinkToLogin();

    Link addRelLinkToFoodById(Integer foodId);
}
