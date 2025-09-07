package fateczl.TrabalhoLabEngSw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.service.ArquivoService;

@RestController
@RequestMapping("/api")
public class ApiController {    
    @Autowired
    private ArquivoService arqService;
    
    @GetMapping("/get_commit")
    public ResponseEntity<List<Arquivo>> getCommit(Long rep_id, Long commit_id) {
        try {
            List<Arquivo> arquivos = arqService.findByCommit(commit_id, rep_id);
            return ResponseEntity.ok(arquivos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
