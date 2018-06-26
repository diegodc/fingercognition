package rest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import rest.persistence.FingerprintRepositoryImpl;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(RestController.class)
public class RestServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FingerprintRepositoryImpl mockRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        when(mockRepository.countValidFingerprints()).thenReturn(10L);
        when(mockRepository.countInvalidFingerprints()).thenReturn(3L);
    }

    @Test
    public void verificationService() throws Exception {

        String validMatrix = "{\"matrix\" : [\"ACTGAC\", \"TAACTA\", \"ACAGAG\", \"TGAATG\", \"ACTGAG\", \"TGACTG\"]}";
        String invalidMatrix = "{\"matrix\" : [\"ACTGAC\", \"TGACTG\", \"ACTGAC\", \"TGACTG\", \"ACTGAC\", \"TGACTG\"]}";

        mockMvc.perform(post("/fingerPrint")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(validMatrix))
                .andExpect(status().isOk());

        mockMvc.perform(post("/fingerPrint")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(invalidMatrix))
                .andExpect(status().isForbidden());
    }

    @Test
    public void emptyBodyRequest() throws Exception {
        mockMvc.perform(post("/fingerPrint"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidRequest() throws Exception {
        mockMvc.perform(post("/fingerPrint")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("bad"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void statsService() throws Exception {

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count_valid_fingerPrint", is(10)))
                .andExpect(jsonPath("$.count_not_valid_fingerPrint", is(3)))
                .andExpect(jsonPath("$.ratio", is(3.3)));
    }

}