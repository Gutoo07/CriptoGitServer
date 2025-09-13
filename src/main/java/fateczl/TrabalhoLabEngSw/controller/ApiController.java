package fateczl.TrabalhoLabEngSw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.service.ArquivoService;
import fateczl.TrabalhoLabEngSw.service.CommitService;

@RestController
@RequestMapping("/api")
public class ApiController {    
    @Autowired
    private ArquivoService arqService;
    @Autowired
    private CommitService comService;
    
    // Requisição para buscar os arquivos do commit selecionado
    @GetMapping("/get_commit")
    public ResponseEntity<List<Arquivo>> getCommit(Long rep_id, Long commit_id) {
        try {
            List<Arquivo> arquivos = arqService.findByCommit(commit_id, rep_id);
            return ResponseEntity.ok(arquivos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Requisição para buscar as informações do commit selecionado
    @GetMapping("/get_commit_info")
    public ResponseEntity<Commite> getCommitInfo(Long commit_id) {
        try {
            Optional<Commite> commit = comService.findById(commit_id);
            if (!commit.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(commit.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
