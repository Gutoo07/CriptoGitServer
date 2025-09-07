package fateczl.TrabalhoLabEngSw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/")
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
	public ModelAndView login(@Valid Usuario usuario, BindingResult br, HttpSession session, HttpServletResponse response) throws Exception {
	    ModelAndView mv = new ModelAndView();
	    mv.addObject("usuario", new Usuario());

	    if (br.hasErrors()) {
	        mv.setViewName("login/login");
	        return mv;
	    }

	    Usuario userLogin = service.loginUser(usuario.getEmail(), usuario.getSenha());

	    if (userLogin == null) {
	        mv.setViewName("login/login");
	        mv.addObject("msg", "Usuario nao encontrado");
	    } else {
	        session.setAttribute("usuarioLogado", userLogin);

	        Cookie cookie = new Cookie("user_nickname", userLogin.getNickname());
	        cookie.setPath("/");
	        cookie.setMaxAge(60 * 60 * 24);
	        response.addCookie(cookie);
	        cookie = new Cookie("user_id", userLogin.getId().toString());
	        cookie.setPath("/");
	        cookie.setMaxAge(60 * 60 * 24);
	        response.addCookie(cookie);

	        return index();
	    }

	    return mv;
	}

	
}
