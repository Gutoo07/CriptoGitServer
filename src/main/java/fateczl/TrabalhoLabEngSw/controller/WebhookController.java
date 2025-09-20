package fateczl.TrabalhoLabEngSw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    @GetMapping("/")
    public ResponseEntity<String> handleWebhook() {
        // Processar o payload recebido
        System.out.println("Request recebido.");
        return ResponseEntity.ok("Webhook recebido com sucesso");
    }
}
