package com.victot.gestao_ocorrencias.service.storage.implementation;

import com.victot.gestao_ocorrencias.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {
    private final Path diretorioUpload;

    public LocalStorageService(@Value("${app.storage.upload-dir:uploads/ocorrencias}") String uploadDir) {
        this.diretorioUpload = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(diretorioUpload);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de upload.", e);
        }
    }

    @Override
    public String salvar(MultipartFile arquivo) {
        try {
            String extensao = StringUtils.getFilenameExtension(arquivo.getOriginalFilename());
            String nomeArmazenado = UUID.randomUUID() + (extensao != null ? "." + extensao : "");

            Path destino = this.diretorioUpload.resolve(nomeArmazenado).normalize();
            Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nomeArmazenado;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo no disco.", e);
        }
    }

    @Override
    public Resource carregar(String nomeArmazenado) {
        try {
            Path caminhoArquivo = this.diretorioUpload.resolve(nomeArmazenado).normalize();
            Resource resource = new UrlResource(caminhoArquivo.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            throw new RuntimeException("Arquivo não encontrado ou ilegível.");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao carregar o arquivo.", e);
        }
    }

    @Override
    public void deletar(String nomeArmazenado) {
        try {
            Path caminhoArquivo = this.diretorioUpload.resolve(nomeArmazenado).normalize();
            Files.deleteIfExists(caminhoArquivo);
        } catch (IOException ignored) { }
    }
}
