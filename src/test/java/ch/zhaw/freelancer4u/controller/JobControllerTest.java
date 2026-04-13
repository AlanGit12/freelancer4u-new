package ch.zhaw.freelancer4u.controller;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import ch.zhaw.freelancer4u.repository.CompanyRepository;
import ch.zhaw.freelancer4u.security.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class JobControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CompanyRepository companyRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String job_id = "";
    private static String company_id = "";

    private static final String TEST_TITLE = "Test Job";
    private static final String TEST_DESCRIPTION = "Test Beschreibung";
    private static final String TEST_JOBTYPE = "IMPLEMENT";
    private static final double TEST_EARNINGS = 123.0;

    private String getCompanyId() {
        return companyRepository.findAll().get(0).getId();
    }

    @Test
    @Order(1)
    public void testCreateJob() throws Exception {
        company_id = getCompanyId();

        String jsonBody = """
            {
              "title": "%s",
              "description": "%s",
              "jobType": "%s",
              "earnings": %s,
              "companyId": "%s"
            }
            """.formatted(TEST_TITLE, TEST_DESCRIPTION, TEST_JOBTYPE, TEST_EARNINGS, company_id);

        var result = mvc.perform(post("/api/job")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, TestSecurityConfig.ADMIN)
                .content(jsonBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        job_id = jsonNode.get("id").asString();
    }

    @Test
    @Order(2)
    public void testGetCreatedJob() throws Exception {
        mvc.perform(get("/api/job/" + job_id)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, TestSecurityConfig.ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TEST_TITLE))
                .andExpect(jsonPath("$.description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("$.companyId").value(company_id));
    }

    @Test
    @Order(3)
    public void testDeleteCreatedJob() throws Exception {
        mvc.perform(delete("/api/job/" + job_id)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, TestSecurityConfig.ADMIN))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void testDeletedJobNotFound() throws Exception {
        mvc.perform(get("/api/job/" + job_id)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, TestSecurityConfig.ADMIN))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}