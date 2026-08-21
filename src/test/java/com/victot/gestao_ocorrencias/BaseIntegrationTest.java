package com.victot.gestao_ocorrencias;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victot.gestao_ocorrencias.config.security.TokenService;
import com.victot.gestao_ocorrencias.entity.Pessoa;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@Transactional
public abstract class BaseIntegrationTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Autowired
    protected TokenService tokenService;

    @Autowired
    protected PessoaRepository pessoaRepository;

    public static final String ADMIN_ID = "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab";
    public static final String ADMIN_CPF = "83908221087";

    public static final String GESTOR_ID = "b2c3d4e5-f6a1-4b2c-9d0e-123456789abc";
    public static final String GESTOR_CPF = "31478207038";

    public static final String OPERADOR_ID = "c3d4e5f6-a1b2-4c3d-a1f2-23456789abcd";
    public static final String OPERADOR_CPF = "65324508085";

    public static final String OCORRENCIA_1_ID = "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde";
    public static final String OCORRENCIA_2_ID = "e5f6a1b2-c3d4-4e5f-c3b4-456789abcdef";
    public static final String TRATATIVA_1_ID = "f6a1b2c3-d4e5-4f6a-d4c5-56789abcdef0";

    @BeforeEach
    void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    protected String getAdminToken() {
        Pessoa admin = pessoaRepository.findById(ADMIN_ID).orElseThrow();
        return "Bearer " + tokenService.generateToken(admin);
    }

    protected String getGestorToken() {
        Pessoa gestor = pessoaRepository.findById(GESTOR_ID).orElseThrow();
        return "Bearer " + tokenService.generateToken(gestor);
    }

    protected String getOperadorToken() {
        Pessoa operador = pessoaRepository.findById(OPERADOR_ID).orElseThrow();
        return "Bearer " + tokenService.generateToken(operador);
    }
}
