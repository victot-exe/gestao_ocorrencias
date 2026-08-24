package com.victot.gestao_ocorrencias.service.storage;

import com.victot.gestao_ocorrencias.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

class LimpezaStorageSchedulerTest extends BaseIntegrationTest {

    @Autowired
    private LimpezaStorageScheduler scheduler;

    @Value("${app.storage.upload-dir:uploads/ocorrencias}")
    private String uploadDir;

    @Test
    @DisplayName("Deve remover arquivo do disco quando ele for órfão (sem registro no banco)")
    void deveRemoverArquivoOrfao() throws Exception {
        Path pasta = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(pasta);

        // 1. Cria um arquivo órfão no disco
        String nomeArquivoOrfao = "orfao-teste-" + System.currentTimeMillis() + ".png";
        Path caminhoOrfao = pasta.resolve(nomeArquivoOrfao);
        Files.writeString(caminhoOrfao, "arquivo sem vinculo no banco");

        // 2. Executa diretamente o metodu agendado
        scheduler.limparArquivosOrfaos();

        // 3. O arquivo órfão deve ter sido deletado
        assertFalse(Files.exists(caminhoOrfao), "O arquivo órfão deveria ter sido removido pelo scheduler");
    }
}
