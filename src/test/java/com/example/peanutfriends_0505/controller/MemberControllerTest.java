package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.dto.MemberLoginDto;
import com.example.peanutfriends_0505.dto.MemberLoginResponseDto;
import com.example.peanutfriends_0505.dto.MemberSignUpDto;
import com.example.peanutfriends_0505.dto.RefreshTokenDto;
import com.example.peanutfriends_0505.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

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
        memberService.deleteAll();
    }

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

        Member member = memberService.findByEmail(memberSignUpDto.getEmail());
        memberService.removeMemberAccount(member);
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
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("토큰 갱신")
    void refreshToken() throws Exception{
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
        String refreshToken = JsonPath.parse(response).read("$.refreshToken");

        RefreshTokenDto refreshTokenDto = new RefreshTokenDto(refreshToken);

        mockMvc.perform(post("/api/members/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(refreshTokenDto))
                        .header("Authorization", "Bearer "+ accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃")
    void logout() throws Exception {
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
        String refreshToken = JsonPath.parse(response).read("$.refreshToken");

        RefreshTokenDto refreshTokenDto = new RefreshTokenDto(refreshToken);

        mockMvc.perform(post("/api/members/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(refreshTokenDto))
                        .header("Authorization", "Bearer "+ accessToken)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 체크")
    void loginCheck() throws Exception{
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
        mockMvc.perform(get("/api/members/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("atk", accessToken))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

}