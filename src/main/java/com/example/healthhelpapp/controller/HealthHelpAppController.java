package com.example.healthhelpapp.controller;

import com.example.healthhelpapp.dto.request.ItemRequestDto;
import com.example.healthhelpapp.dto.response.ItemResponseDto;
import com.example.healthhelpapp.dto.request.PatientRequestDto;
import com.example.healthhelpapp.dto.response.PatientResponseDto;
import com.example.healthhelpapp.dto.response.ResponseDto;
import com.example.healthhelpapp.service.ItemService;
import com.example.healthhelpapp.service.LinkHandlerService;
import com.example.healthhelpapp.service.PatientService;
import com.example.healthhelpapp.util.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.healthhelpapp.util.AppConstant.DOCTOR_ROLE;
import static com.example.healthhelpapp.util.AppConstant.FOOD_CATEGORY;
import static com.example.healthhelpapp.util.AppConstant.LOGIN_MESSAGE;
import static com.example.healthhelpapp.util.AppConstant.MANAGER_ROLE;
import static com.example.healthhelpapp.util.AppConstant.MEDICAL_CATEGORY;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthHelpAppController {
    private final ItemService itemService;
    private final PatientService patientService;
    private final LinkHandlerService linkHandlerService;

    @GetMapping("/start")
    public ResponseDto handleStart() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        ResponseDto response = new ResponseDto();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            response.setMessage(LOGIN_MESSAGE);
            response.add(linkHandlerService.addRelLinkToLogin());
            return response;
        }

        response.setMessage(AppConstant.CHOOSE_LINK_MESSAGE);
        User user = (User) authentication.getPrincipal();
        Optional<String> optionalRole = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(gA -> gA.contains(DOCTOR_ROLE) || gA.contains(MANAGER_ROLE))
                .findFirst();

        if (optionalRole.isPresent()) {
            String userRole = optionalRole.get();
            if (userRole.contains(DOCTOR_ROLE)) {
                response.add(linkHandlerService.addRelLinkToStock());
                response.add(linkHandlerService.addRelLinkToPatientList());
                response.add(linkHandlerService.addRelLinkToCreatePatient());
            }

            if (userRole.contains(AppConstant.MANAGER_ROLE)) {
                response.add(linkHandlerService.addRelLinkToStock());
                response.add(linkHandlerService.addRelLinkToFoodList());
                response.add(linkHandlerService.addRelLinkToCreateItem());
            }
        }

        return response;
    }

    @GetMapping("/stock")
    public List<ItemResponseDto> getListOfMedicalItems() {
        List<ItemResponseDto> itemsByCategory =
                itemService.getItemsByCategory(MEDICAL_CATEGORY);

        itemsByCategory.forEach(itemResource -> {
            itemResource.add(linkHandlerService.addRelLinkToItemById(itemResource.getId()));
            itemResource.add(linkHandlerService.addRelLinkToStartPage());
        });

        return itemsByCategory;
    }

    @GetMapping("/list-of-food")
    public List<ItemResponseDto> getListOfFood() {
        List<ItemResponseDto> itemsByCategory =
                itemService.getItemsByCategory(FOOD_CATEGORY);

        itemsByCategory.forEach(itemResource -> {
            itemResource.add(
                    linkHandlerService.addRelLinkToFoodById(itemResource.getId()));
            itemResource.add(linkHandlerService.addRelLinkToStartPage());
        });

        return itemsByCategory;
    }

    @PostMapping(value = "/food")
    public ItemResponseDto saveFood(@RequestBody ItemRequestDto itemRequestDto) {
        ItemResponseDto itemResponseDto = itemService.create(itemRequestDto);
        itemResponseDto.add(linkHandlerService.addRelLinkToStartPage());
        itemResponseDto.add(linkHandlerService.addRelLinkToFoodById(itemResponseDto.getId()));
        itemResponseDto.add(linkHandlerService.addRelLinkToFoodList());
        return itemResponseDto;
    }


    @GetMapping("/food/{food-id}")
    public ItemResponseDto getFoodById(@PathVariable("food-id") Integer id) {
        ItemResponseDto itemResponseDto = itemService.getById(id);
        itemResponseDto.add(linkHandlerService.addRelLinkToStartPage());
        return itemResponseDto;
    }

    @GetMapping("/list-of-patients")
    public List<PatientResponseDto> getListOfPatients() {
        List<PatientResponseDto> listOfPatient = patientService.getListOfPatient();
        listOfPatient.forEach(patientResponseDto -> {
            patientResponseDto.add(
                    linkHandlerService.addRelLinkToPatientById(patientResponseDto.getId()));
            patientResponseDto.add(linkHandlerService.addRelLinkToStartPage());
        });
        return listOfPatient;
    }

    @PostMapping("/patient")
    public PatientResponseDto savePatient(@RequestBody PatientRequestDto patientRequestDto) {
        PatientResponseDto patientResponseDto = patientService.create(patientRequestDto);
        patientResponseDto.add(linkHandlerService.addRelLinkToStartPage());
        patientResponseDto.add(linkHandlerService.addRelLinkToPatientById(patientResponseDto.getId()));
        patientResponseDto.add(linkHandlerService.addRelLinkToPatientList());
        return patientResponseDto;
    }

    @GetMapping("/patient/{patient-id}")
    public PatientResponseDto getPatientById(@PathVariable("patient-id") Integer id) {
        PatientResponseDto patientResponseDto = patientService.getById(id);
        patientResponseDto.add(linkHandlerService.addRelLinkToStartPage());
        return patientResponseDto;
    }

    @GetMapping("/item/{item-id}")
    public ItemResponseDto getItemById(@PathVariable("item-id") Integer id) {
        ItemResponseDto itemById = itemService.getById(id);
        itemById.add(linkHandlerService.addRelLinkToStartPage());
        return itemById;
    }
}
