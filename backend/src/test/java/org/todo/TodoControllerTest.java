package org.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = Main.class)
public class TodoControllerTest {
  @Autowired
  WebApplicationContext webApplicationContext;

  private MockMvc mvc;
  private ObjectMapper objectMapper;
  String json = "{\"title\": \"Test Todo\", \"completed\": false}";

  @BeforeEach
  public void setup() {
    mvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void postTodo() throws Exception {
    mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/todos")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  public void getById() throws Exception {
    MvcResult createResult = mvc
        .perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/todos")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
        .andReturn();

    Long todoId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

    mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/todos/{id}", todoId))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.id").value(todoId));
  }

  @Test
  public void testGetAll() throws Exception {
    mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/todos"))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testUpdate() throws Exception {
    String updateJson = "{\"title\": \"Updated Todo\", \"completed\": true}";
    MvcResult createResult = mvc
        .perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/todos")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
        .andReturn();

    Long todoId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

    mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/todos/{id}", todoId)
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .content(updateJson))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.title").value("Updated Todo"))
        .andExpect(jsonPath("$.completed").value(true));
  }

  @Test
  public void testDelete() throws Exception {
    MvcResult createResult = mvc
        .perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/todos")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
        .andReturn();

    Long todoId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

    mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/todos/{id}", todoId))
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isNoContent());
  }
}
