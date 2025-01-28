package br.edu.ifpb.poo.menu.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    @GetMapping("/uploads/product/{id}/{imageName}")
    @ResponseBody
    public ResponseEntity<org.springframework.core.io.Resource> getProductImage(
            @PathVariable String id,
            @PathVariable String imageName) {
        return getImage("product", id, imageName);
    }

    @GetMapping("/uploads/user/{id}/{imageName}")
    @ResponseBody
    public ResponseEntity<org.springframework.core.io.Resource> getUserImage(
            @PathVariable String id,
            @PathVariable String imageName) {
        return getImage("user", id, imageName);
    }

    private ResponseEntity<org.springframework.core.io.Resource> getImage(
            String type,
            String id,
            String imageName) {
        try {
            // Define o caminho completo para o arquivo de imagem
            Path filePath = Paths.get(System.getProperty("user.dir"), "uploads", type, id, imageName);

            // Verifica se o arquivo existe
            File file = filePath.toFile();
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Cria um recurso a partir do arquivo
            org.springframework.core.io.Resource resource = new FileSystemResource(file);

            // Determina o tipo de mídia correto
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // tipo padrão
            }

            // Retorna o arquivo como resposta
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            // Se ocorrer algum erro, retornamos o erro 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
