package fateczl.TrabalhoLabEngSw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/login");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/index");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	@GetMapping("/cadastro")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/cadastro");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	@PostMapping("/salvarUsuario")
	public ModelAndView cadastrar(Usuario usuario) throws Exception {
		ModelAndView mv = new ModelAndView();
		service.salvarUsuario(usuario);
		mv.setViewName("redirect:/");
		return mv;
	}
	@PostMapping("/login")
	public ModelAndView login(@Valid Usuario usuario, BindingResult br,
			HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		if (br.hasErrors()) {
			mv.setViewName("login/login");
		}
		
		Usuario userLogin = service.loginUser(usuario.getEmail(), usuario.getSenha());
		if (userLogin == null) {
			mv.addObject("msg", "Usuario nao encontrado");
		} else {
			session.setAttribute("usuarioLogado", userLogin);
			return index();
		}
		
		return mv;
	}
	
}
