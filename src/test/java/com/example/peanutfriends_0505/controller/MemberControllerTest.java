package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.dto.MemberLoginDto;
import com.example.peanutfriends_0505.dto.MemberLoginResponseDto;
import com.example.peanutfriends_0505.dto.MemberSignUpDto;
import com.example.peanutfriends_0505.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception{
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("signUpTest@test.com", "ABCabc123456!@#" , "송주환");

        mockMvc.perform(post("/api/members/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(memberSignUpDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.regDate").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception{
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("signUpTest@test.com", "ABCabc123456!@#" , "송주환");

        mockMvc.perform(post("/api/members/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(memberSignUpDto))
        );

        MemberLoginDto memberLoginDto = new MemberLoginDto("signUpTest@test.com", "ABCabc123456!@#");

        mockMvc.perform(post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberLoginDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andDo(print());
    }



}