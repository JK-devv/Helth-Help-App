package com.example.healthhelpapp.controller;

import com.example.healthhelpapp.dto.request.ItemRequestDto;
import com.example.healthhelpapp.dto.request.PatientRequestDto;
import com.example.healthhelpapp.dto.response.ItemResponseDto;
import com.example.healthhelpapp.dto.response.PatientResponseDto;
import com.example.healthhelpapp.security.JwtAuthenticatedFilter;
import com.example.healthhelpapp.security.JwtService;
import com.example.healthhelpapp.security.SecurityConfig;
import com.example.healthhelpapp.service.ItemService;
import com.example.healthhelpapp.service.PatientService;
import com.example.healthhelpapp.service.impl.LinkHandlerServiceImpl;
import com.example.healthhelpapp.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.example.healthhelpapp.TestConstant.*;
import static com.example.healthhelpapp.util.AppConstant.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({LinkHandlerServiceImpl.class, SecurityConfig.class, JwtAuthenticatedFilter.class})
@WebMvcTest(value = HealthHelpAppController.class)
class HealthHelpAppControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LinkHandlerServiceImpl linkHandlerService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemService itemService;
    @MockBean
    private PatientService patientService;

    @Test
    @SneakyThrows
    void handleStartWhitOutTokenAccess() {
        String response = "{\"message\":\"Hello, please login first\"," +
                "\"_links\":{\"authentication\":{\"href\":\"http://localhost/login\"}}}";

        mockMvc.perform(MockMvcRequestBuilders.get("/start")
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.message").value(LOGIN_MESSAGE))
                .andExpect(jsonPath("$._links.authentication.href").value("http://localhost/login"));
    }

    @Test
    @SneakyThrows
    void handleStartWhitTokenAndDoctorRole() {
        String response = "{\"message\":\"Hello, choose link\"," +
                "\"_links\":{\"stock\":{\"href\":\"http://localhost/stock\"}," +
                "\"list_of_patients\":{\"href\":\"http://localhost/list-of-patients\"}," +
                "\"create_patient\":{\"href\":\"http://localhost/patient\"}}}";

        mockMvc.perform(get("/start")
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("doctor").roles(DOCTOR_ROLE)))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.message").value(CHOOSE_LINK_MESSAGE))
                .andExpect(jsonPath("$._links.stock.href").value("http://localhost/stock"))
                .andExpect(jsonPath("$._links.list_of_patients.href").value("http://localhost/list-of-patients"))
                .andExpect(jsonPath("$._links.create_patient.href").value("http://localhost/patient"));
    }

    @Test
    @SneakyThrows
    void handleStartWhitTokenAndManagerRole() {
        String response = "{\"message\":\"Hello, choose link\"," +
                "\"_links\":{\"stock\":{\"href\":\"http://localhost/stock\"}," +
                "\"list_of_food\":{\"href\":\"http://localhost/list-of-food\"}," +
                "\"create_food\":{\"href\":\"http://localhost/food\"}}}";

        mockMvc.perform(get("/start")
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("manager").roles(MANAGER_ROLE)))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.message").value(CHOOSE_LINK_MESSAGE))
                .andExpect(jsonPath("$._links.stock.href").value("http://localhost/stock"))
                .andExpect(jsonPath("$._links.list_of_food.href").value("http://localhost/list-of-food"))
                .andExpect(jsonPath("$._links.create_food.href").value("http://localhost/food"));
    }

    @Test
    @SneakyThrows
    void shouldReturnStock() {
        String response = "[{\"id\":1,\"name\":\"test name\",\"count\":1,\"categoryName\":\"medical\"," +
                "\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/item/1\"}," +
                "{\"rel\":\"start_page\",\"href\":\"http://localhost/start\"}]}]";

        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .id(1)
                .name(TEST_NAME)
                .categoryName(MEDICAL_CATEGORY)
                .count(1)
                .build();
        List<ItemResponseDto> responseDtos = List.of(itemResponseDto);
        when(itemService.getItemsByCategory(MEDICAL_CATEGORY))
                .thenReturn(responseDtos);

        mockMvc.perform(get("/stock")
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("doctor").authorities(new SimpleGrantedAuthority(DOCTOR_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$[0].id").value(itemResponseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(itemResponseDto.getName()))
                .andExpect(jsonPath("$[0].count").value(itemResponseDto.getCount()))
                .andExpect(jsonPath("$[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$[0].links[0].href").value("http://localhost/item/1"))
                .andExpect(jsonPath("$[0].links[1].rel").value("start_page"))
                .andExpect(jsonPath("$[0].links[1].href").value("http://localhost/start"));

        verify(itemService, times(1)).getItemsByCategory(MEDICAL_CATEGORY);
    }

    @Test
    @SneakyThrows
    void shouldReturnListOfFood() {
        String response = "[{\"id\":1,\"name\":\"test name\",\"count\":1," +
                "\"categoryName\":\"food\",\"links\":[{\"rel\":\"self\"," +
                "\"href\":\"http://localhost/food/1\"},{\"rel\":\"start_page\"," +
                "\"href\":\"http://localhost/start\"}]}]";

        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .id(1)
                .name(TEST_NAME)
                .categoryName(FOOD_CATEGORY)
                .count(1)
                .build();
        List<ItemResponseDto> responseDtos = List.of(itemResponseDto);

        when(itemService.getItemsByCategory(FOOD_CATEGORY))
                .thenReturn(responseDtos);

        mockMvc.perform(get("/list-of-food")
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("manager").authorities(new SimpleGrantedAuthority(MANAGER_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$[0].id").value(itemResponseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(itemResponseDto.getName()))
                .andExpect(jsonPath("$[0].count").value(itemResponseDto.getCount()))
                .andExpect(jsonPath("$[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$[0].links[0].href").value("http://localhost/food/1"))
                .andExpect(jsonPath("$[0].links[1].rel").value("start_page"))
                .andExpect(jsonPath("$[0].links[1].href").value("http://localhost/start"));

        verify(itemService, times(1)).getItemsByCategory(FOOD_CATEGORY);
    }

    @Test
    @SneakyThrows
    void shouldSaveFood() {
        String response = "{\"id\":1,\"name\":\"test name\",\"count\":1," +
                "\"categoryName\":\"food\",\"_links\":{\"start_page\":" +
                "{\"href\":\"http://localhost/start\"},\"self\":" +
                "{\"href\":\"http://localhost/food/1\"},\"list_of_food\":" +
                "{\"href\":\"http://localhost/list-of-food\"}}}";
        ItemRequestDto request = ItemRequestDto.builder()
                .count(1)
                .name(TEST_NAME)
                .build();
        ItemResponseDto responseDto = ItemResponseDto.builder()
                .categoryName(FOOD_CATEGORY)
                .name(TEST_NAME)
                .count(1)
                .id(1)
                .build();
        when(itemService.create(request)).thenReturn(responseDto);

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .with(user("manager")
                                .authorities(new SimpleGrantedAuthority(MANAGER_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().string(response))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.count").value(responseDto.getCount()))
                .andExpect(jsonPath("$.categoryName").value("food"))
                .andExpect(jsonPath("$._links.start_page.href").value("http://localhost/start"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/food/1"))
                .andExpect(jsonPath("$._links.list_of_food.href").value("http://localhost/list-of-food"));

        verify(itemService, times(1)).create(request);
    }

    @Test
    @SneakyThrows
    void shouldReturnListOfPatients() {
        String response = "[{\"id\":1,\"name\":\"test name\"," +
                "\"phone\":\"1111\",\"links\":[{\"rel\":\"self\"," +
                "\"href\":\"http://localhost/patient/1\"},{\"rel\"" +
                ":\"start_page\",\"href\":\"http://localhost/start\"}]}]";

        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .id(1)
                .name(TEST_NAME)
                .phone("1111")
                .build();
        List<PatientResponseDto> responseDtos = List.of(patientResponseDto);

        when(patientService.getListOfPatient())
                .thenReturn(responseDtos);

        mockMvc.perform(get("/list-of-patients")
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("doctor")
                                .authorities(new SimpleGrantedAuthority(DOCTOR_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.size()").value(responseDtos.size()))
                .andExpect(jsonPath("$[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$[0].links[0].href").value("http://localhost/patient/1"))
                .andExpect(jsonPath("$[0].links[1].rel").value("start_page"))
                .andExpect(jsonPath("$[0].links[1].href").value("http://localhost/start"));

        verify(patientService, times(1)).getListOfPatient();
    }

    @Test
    @SneakyThrows
    void shouldReturnFoodById() {
        String response = "{\"id\":1,\"name\":\"test name\"," +
                "\"count\":1,\"categoryName\":\"food\",\"_links\"" +
                ":{\"start_page\":{\"href\":\"http://localhost/start\"}}}";

        ItemResponseDto responseDto = ItemResponseDto.builder()
                .id(1)
                .count(1)
                .name(TEST_NAME)
                .categoryName(FOOD_CATEGORY)
                .build();

        when(itemService.getById(responseDto.getId()))
                .thenReturn(responseDto);

        mockMvc.perform(get("/food/{food-id}", 1)
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("manager")
                                .authorities(new SimpleGrantedAuthority(MANAGER_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.count").value(responseDto.getCount()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.categoryName").value(responseDto.getCategoryName()))
                .andExpect(jsonPath("$._links.start_page.href").value("http://localhost/start"));

        verify(itemService, times(1)).getById(responseDto.getId());
    }


    @Test
    @SneakyThrows
    void shouldSavePatient() {
        String response = "{\"id\":1,\"name\":\"test name\"," +
                "\"phone\":\"11111\",\"_links\":{\"start_page\":" +
                "{\"href\":\"http://localhost/start\"},\"self\":" +
                "{\"href\":\"http://localhost/patient/1\"},\"list_of_patients\":" +
                "{\"href\":\"http://localhost/list-of-patients\"}}}";

        PatientRequestDto request = PatientRequestDto.builder()
                .phone("11111")
                .name(TEST_NAME)
                .build();

        PatientResponseDto responseDto = PatientResponseDto.builder()
                .id(1)
                .phone("11111")
                .name(TEST_NAME)
                .build();

        when(patientService.create(request)).thenReturn(responseDto);

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .with(user("doctor")
                                .authorities(new SimpleGrantedAuthority(DOCTOR_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().string(response))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.phone").value(responseDto.getPhone()))
                .andExpect(jsonPath("$._links.start_page.href").value("http://localhost/start"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/patient/1"))
                .andExpect(jsonPath("$._links.list_of_patients.href").value("http://localhost/list-of-patients"));

        verify(patientService, times(1)).create(request);
    }

    @Test
    @SneakyThrows
    void getPatientById() {
        String response = "{\"id\":1,\"name\":\"test name\"," +
                "\"phone\":\"11111\",\"_links\":{\"start_page\"" +
                ":{\"href\":\"http://localhost/start\"}}}";

        PatientResponseDto responseDto = PatientResponseDto.builder()
                .id(1)
                .phone("11111")
                .name(TEST_NAME)
                .build();

        when(patientService.getById(responseDto.getId()))
                .thenReturn(responseDto);

        mockMvc.perform(get("/patient/{patient-id}", 1)
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("doctor")
                                .authorities(new SimpleGrantedAuthority(DOCTOR_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.phone").value(responseDto.getPhone()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$._links.start_page.href").value("http://localhost/start"));

        verify(patientService, times(1)).getById(responseDto.getId());
    }

    @Test
    @SneakyThrows
    void getItemById() {
        String response = "{\"id\":1,\"name\":\"test name\"," +
                "\"count\":1,\"categoryName\":\"medical\",\"_links\"" +
                ":{\"start_page\":{\"href\":\"http://localhost/start\"}}}";

        ItemResponseDto responseDto = ItemResponseDto.builder()
                .id(1)
                .categoryName(MEDICAL_CATEGORY)
                .count(1)
                .name(TEST_NAME)
                .build();

        when(itemService.getById(responseDto.getId()))
                .thenReturn(responseDto);

        mockMvc.perform(get("/item/{item-id}", 1)
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("doctor")
                                .authorities(new SimpleGrantedAuthority(DOCTOR_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.count").value(responseDto.getCount()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.categoryName").value(responseDto.getCategoryName()))
                .andExpect(jsonPath("$._links.start_page.href").value("http://localhost/start"));

        verify(itemService, times(1)).getById(responseDto.getId());
    }

    @Test
    @SneakyThrows
    void getFoodById() {
        String response = "{\"id\":1,\"name\":\"test name\"," +
                "\"count\":1,\"categoryName\":\"food\",\"_links\"" +
                ":{\"start_page\":{\"href\":\"http://localhost/start\"}}}";

        ItemResponseDto responseDto = ItemResponseDto.builder()
                .id(1)
                .categoryName(FOOD_CATEGORY)
                .count(1)
                .name(TEST_NAME)
                .build();

        when(itemService.getById(responseDto.getId()))
                .thenReturn(responseDto);

        mockMvc.perform(get("/food/{food-id}", 1)
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("manager")
                                .authorities(new SimpleGrantedAuthority(MANAGER_ROLE))))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.count").value(responseDto.getCount()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.categoryName").value(responseDto.getCategoryName()))
                .andExpect(jsonPath("$._links.start_page.href").value("http://localhost/start"));

        verify(itemService, times(1)).getById(responseDto.getId());
    }

    @Test
    @SneakyThrows
    void shoulNotProcessPageForDoctorRole() {
        mockMvc.perform(get("/list-of-food")
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("doctor").roles(DOCTOR_ROLE)))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void shoulNotProcessPageForManagerRole() {
        mockMvc.perform(get("/list-of-patients")
                        .accept(MediaTypes.HAL_JSON)
                        .header(AUTHORIZATION_HEADER, BEARER_HEADER_PART + TEST_TOKEN)
                        .with(user("manager").roles(MANAGER_ROLE)))
                .andExpect(status().isForbidden());
    }
}