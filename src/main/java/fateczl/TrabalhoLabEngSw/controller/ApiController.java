package fateczl.TrabalhoLabEngSw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.model.UsuarioRepositorio;
import fateczl.TrabalhoLabEngSw.persistence.UsuarioRepositorioRepository;
import fateczl.TrabalhoLabEngSw.service.ArquivoService;
import fateczl.TrabalhoLabEngSw.service.CommitService;
import fateczl.TrabalhoLabEngSw.service.UsuarioRepositorioService;
import fateczl.TrabalhoLabEngSw.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class ApiController {    
    @Autowired
    private ArquivoService arqService;
    @Autowired
    private CommitService comService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepositorioService usuarioRepositorioService;
    @Autowired
    private UsuarioRepositorioRepository usuarioRepositorioRep;

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
    // Requisição para buscar o colaborador pelo email
    @GetMapping("/buscarColaboradorByEmail")
    public ResponseEntity<Map<String, Object>> buscarColaboradorByEmail(String email) {
        Map<String, Object> response= new HashMap<>();
        try {
            // Busca o usuário pelo email
            Usuario usuario = usuarioService.buscarUsuarioByEmail(email);
            // Adiciona as informações do usuário ao response
            response.put("id", usuario.getId());
            response.put("nickname", usuario.getNickname());
            response.put("email", usuario.getEmail());
            response.put("status", true);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // Requisição para adicionar o colaborador ao repositório
    @PostMapping("/adicionarColaborador")
    public ResponseEntity<Map<String, Object>> adicionarColaborador(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response= new HashMap<>();
        try {
            // Pega os valores do requestBody
            Long rep_id = Long.valueOf(requestBody.get("rep_id").toString());
            Long user_id = Long.valueOf(requestBody.get("user_id").toString());
            // Adiciona o colaborador ao repositório
            usuarioRepositorioService.adicionarColaborador(rep_id, user_id);
            // Busca a lista de colaboradores atualizada
            List<UsuarioRepositorio> colaboradores = usuarioRepositorioRep.findByRepositorioId(rep_id);	
            // Retorna a lista de colaboradores atualizada
            response.put("colaboradores", colaboradores);
            response.put("status", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", false);
            response.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
