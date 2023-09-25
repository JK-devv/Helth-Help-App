package com.example.healthhelpapp.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {LinkHandlerServiceImpl.class})
class LinkHandlerServiceImplTest {
    @Autowired
    private LinkHandlerServiceImpl linkHandlerService;

    @Test
    void shouldReturnRelLinkToStartPage() {
        Link link = linkHandlerService.addRelLinkToStartPage();
        assertEquals("http://localhost/start", link.getHref());
        assertEquals("start_page", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToItemById() {
        Link link = linkHandlerService.addRelLinkToItemById(1);
        assertEquals("http://localhost/item/1", link.getHref());
        assertEquals("self", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToStock() {
        Link link = linkHandlerService.addRelLinkToStock();
        assertEquals("http://localhost/stock", link.getHref());
        assertEquals("stock", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToFoodList() {
        Link link = linkHandlerService.addRelLinkToFoodList();
        assertEquals("http://localhost/list-of-food", link.getHref());
        assertEquals("list_of_food", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToPatientList() {
        Link link = linkHandlerService.addRelLinkToPatientList();
        assertEquals("http://localhost/list-of-patients", link.getHref());
        assertEquals("list_of_patients", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToPatientById() {
        Link link = linkHandlerService.addRelLinkToPatientById(1);
        assertEquals("http://localhost/patient/1", link.getHref());
        assertEquals("self", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToCreatePatient() {
        Link link = linkHandlerService.addRelLinkToCreatePatient();
        assertEquals("http://localhost/patient", link.getHref());
        assertEquals("create_patient", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToCreateItem() {
        Link link = linkHandlerService.addRelLinkToCreateItem();
        assertEquals("http://localhost/food", link.getHref());
        assertEquals("create_food", link.getRel().value());
    }

    @Test
    void shouldReturnRelLinkToLogin() {
        Link link = linkHandlerService.addRelLinkToLogin();
        assertEquals("http://localhost/login", link.getHref());
        assertEquals("authentication", link.getRel().value());
    }

    @Test
    void addRelLinkToFoodById() {
        Link link = linkHandlerService.addRelLinkToFoodById(1);
        assertEquals("http://localhost/food/1", link.getHref());
        assertEquals("self", link.getRel().value());
    }
}