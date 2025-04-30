package fateczl.TrabalhoLabEngSw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;
import fateczl.TrabalhoLabEngSw.service.RepositorioService;

@Controller
public class RepositorioController {
	@Autowired
	private RepositorioRepository repRep;
	private RepositorioService repService;
	
	@GetMapping("/repositorios")
	public ModelAndView carregaRepositorios(@CookieValue(name = "user_id",defaultValue = "") String user_id) {
		ModelAndView mv = new ModelAndView();
		Usuario usuario = new Usuario();
		//System.out.println("Useroid >>>>"+Long.valueOf(user_id));
		usuario.setId(Long.valueOf(user_id));
		List<Repositorio> lista = repRep.findByUsuario(usuario);
		mv.setViewName("repositorio/listagem");
		System.out.println(lista.size());
		mv.addObject("listaRep", lista);
		return mv;
	}
	
	@GetMapping("/acessar")
	public ModelAndView acessarRepositorios(@RequestParam(name = "rep_id", required = true) Long repId,
	                                         @CookieValue(name = "user_id", defaultValue = "") String user_id) {
	    ModelAndView mv = new ModelAndView();

	    Usuario usuario = new Usuario();
	    usuario.setId(Long.valueOf(user_id));

	    Optional<Repositorio> repositorioOpt = repRep.findById(repId);
	    
	    if (repositorioOpt.isPresent()) {
	        System.out.println(repositorioOpt.get().getNome());
	        mv.addObject("repositorio", repositorioOpt.get());
	    } else {
	        mv.addObject("erro", "Repositório não encontrado");
	    }

	    mv.setViewName("repositorio/acessar");
	    return mv;
	}

	
	@PostMapping("/criarRepositorio")
	public ModelAndView criarRepositorio(Repositorio repositorio,
			@CookieValue(value = "user_id", defaultValue = "") String user_id) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		Usuario usuario = new Usuario();
		usuario.setId(Long.parseLong(user_id));
		repService = new RepositorioService();
		repositorio.setChaveSimetrica(repService.gerarChaveSimetrica().getEncoded());
		repositorio.setUsuario(usuario);
		repRep.save(repositorio);
		mv.setViewName("repositorio/listagem");
		return carregaRepositorios(user_id);
	}
	
}
