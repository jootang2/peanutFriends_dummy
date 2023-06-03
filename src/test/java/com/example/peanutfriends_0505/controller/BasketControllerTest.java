package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.WithMockCustomUser;
import com.example.peanutfriends_0505.dto.AddBasketDto;
import com.example.peanutfriends_0505.dto.MemberLoginDto;
import com.example.peanutfriends_0505.dto.MemberSignUpDto;
import com.example.peanutfriends_0505.dto.RefreshTokenDto;
import com.example.peanutfriends_0505.service.BasketService;
import com.example.peanutfriends_0505.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BasketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BasketService basketService;

    @Autowired
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @AfterEach
    public void clear() {
        basketService.deleteAll();
        memberService.deleteAll();
    }

    @Test
    @DisplayName("바스켓 생성")
    @WithMockUser()
    void addBasket() throws Exception{
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("signUpTest@test.com", "ABCabc123456!@#" , "송주환");

        mockMvc.perform(post("/api/members/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(memberSignUpDto))
        );

        MemberLoginDto memberLoginDto = new MemberLoginDto("signUpTest@test.com", "ABCabc123456!@#");

        MvcResult mvcResult = mockMvc.perform(post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberLoginDto))
                )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String accessToken = JsonPath.parse(response).read("$.accessToken");

        AddBasketDto addBasketDto = new AddBasketDto("TESTBASKET", "2021-07-07", "2022-07-07");

        mockMvc.perform(post("/api/basket/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(addBasketDto))
                        .header("Authorization", "Bearer "+ accessToken)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void getBaskets() {
    }

    @Test
    void signUpBasket() {
    }
}