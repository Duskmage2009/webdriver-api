package executor.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyCredentialsDTO;
import executor.api.model.ProxyNetworkConfigDTO;

import executor.api.service.impl.ProxySourceQueueHandler;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class ProxySourceControllerTest {
    private final ProxyCredentialsDTO proxyCredentialsDTO = new ProxyCredentialsDTO("username", "password");
    private final String hostName = "hostname";
    private final int port = 8080;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String proxyUrl = "/api/v1/proxies";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProxySourceQueueHandler proxySourceQueueHandler;

    @Test
    public void testAddValidPostStatusIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString(new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO(hostName, port),
                                proxyCredentialsDTO)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testValidGetStatusIsOk() throws Exception {


        Optional<ProxyConfigHolderDTO> optional = Optional.of(new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO(hostName, port),
                proxyCredentialsDTO));
        Mockito.when(proxySourceQueueHandler.poll()).thenReturn(optional);
        mockMvc.perform(MockMvcRequestBuilders.get(proxyUrl)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testWithNullOptionForGet() throws Exception {
        Optional<ProxyConfigHolderDTO> optional = Optional.empty();
        Mockito.when(proxySourceQueueHandler.poll()).thenReturn(optional);
        mockMvc.perform(MockMvcRequestBuilders.get(proxyUrl)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testWrongJsonFormatForPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString("Not a Json format"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testWrongJsonFormatWithEmptyString() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString(""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testAddHostnameWithEmptyField() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString(new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO("", port),
                                proxyCredentialsDTO)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void testAddHostnameWithEmptySpaces() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString(new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO("          ", port),
                                proxyCredentialsDTO)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void testAddPortWithNullField() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString(new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO(hostName, null),
                                proxyCredentialsDTO)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testAddPortWithInvalidValueMoreThenMaximum() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString(new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO(hostName, 77777),
                                proxyCredentialsDTO)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testAddPortWithInvalidValueMoreThenMinimum() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(proxyUrl)
                        .content(objectMapper.writeValueAsString(new ProxyConfigHolderDTO(new ProxyNetworkConfigDTO(hostName, -33333),
                                proxyCredentialsDTO)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
