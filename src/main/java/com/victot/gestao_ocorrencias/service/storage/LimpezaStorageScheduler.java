package com.victot.gestao_ocorrencias.service.storage;

import com.victot.gestao_ocorrencias.entity.MidiaOcorrencia;
import com.victot.gestao_ocorrencias.repository.MidiaOcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j//o que e isso?
public class LimpezaStorageScheduler {
    private final MidiaOcorrenciaRepository midiaRepository;
    private final StorageService storageService;

    @Value("${app.storage.upload-dir:uploads/ocorrencias}")
    private String uploadDir;

    // Roda every dia às 07:00 da madrugada (Cron: Seg Min Hora Dia Mes DiaSemana)
    @Scheduled(cron = "0 0 7 * * *")
    public void limparArquivosOrfaos() {
        log.info("Iniciando rotina de limpeza de arquivos órfãos no storage...");

        try {
            Path diretorio = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(diretorio)) return;

            Set<String> arquivosRegistradosNoBanco = midiaRepository.findAll()
                    .stream()
                    .map(MidiaOcorrencia::getNomeArmazenado)
                    .collect(Collectors.toSet());

            try (var stream = Files.list(diretorio)) {
                stream.filter(Files::isRegularFile).forEach(caminhoArquivo -> {
                    String nomeArquivoDisco = caminhoArquivo.getFileName().toString();

                    if (!arquivosRegistradosNoBanco.contains(nomeArquivoDisco)) {
                        log.warn("Arquivo órfão detectado e removido: {}", nomeArquivoDisco);
                        storageService.deletar(nomeArquivoDisco);
                    }
                });
            }
        } catch (IOException e) {
            log.error("Erro durante a limpeza de arquivos órfãos", e);
        }
    }
}
