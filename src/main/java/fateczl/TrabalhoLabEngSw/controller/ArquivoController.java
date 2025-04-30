package fateczl.TrabalhoLabEngSw.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fateczl.TrabalhoLabEngSw.model.Arquivo;
import fateczl.TrabalhoLabEngSw.model.Blob;
import fateczl.TrabalhoLabEngSw.model.Commite;
import fateczl.TrabalhoLabEngSw.model.Diretorio;
import fateczl.TrabalhoLabEngSw.model.Repositorio;
import fateczl.TrabalhoLabEngSw.model.Usuario;
import fateczl.TrabalhoLabEngSw.persistence.ArquivoRepository;
import fateczl.TrabalhoLabEngSw.persistence.BlobRepository;
import fateczl.TrabalhoLabEngSw.persistence.CommitRepository;
import fateczl.TrabalhoLabEngSw.persistence.DiretorioRepository;
import fateczl.TrabalhoLabEngSw.persistence.RepositorioRepository;
import fateczl.TrabalhoLabEngSw.persistence.UsuarioRepository;

@Controller
public class ArquivoController {
	@Autowired
	private ArquivoRepository rep;
	@Autowired
	private BlobRepository blobRep;
	@Autowired
	private UsuarioRepository userRep;
	@Autowired
	private CommitRepository commitRep;
	@Autowired
	private DiretorioRepository dirRep;
	@Autowired
	private RepositorioRepository repRep;
	
	/*
	@PostMapping("/arquivo/uploadArquivo")
	public String upload(@RequestParam("arquivo") MultipartFile arquivo) throws IOException, NoSuchAlgorithmException {
		System.out.println(arquivo.getOriginalFilename());
		System.out.println(arquivo.getContentType());
		
		Usuario autor = userRep.findByEmail("guto@email.com");
		
		Repositorio repositorio = new Repositorio();
		repositorio.setNome("Repositorio teste");
		repositorio.setUsuario(autor);
		
		byte[] chaveSimetrica = {0,1};
		repositorio.setChaveSimetrica(chaveSimetrica);
		
		repRep.save(repositorio);
		
		Blob blob = new Blob();
		byte[] bytes = arquivo.getBytes();		
		System.out.println(bytes);
		blob.setConteudo(bytes);		
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] sha1bytes = md.digest(bytes);
        BigInteger bigInt = new BigInteger(1, sha1bytes);
        String sha1 = bigInt.toString(16);
        System.out.println(sha1);
        blob.setSha1(sha1);
        blobRep.save(blob);
        
        Commite commit = new Commite();
		commit.setAutor(autor);
		commit.setMsg("Primeiro commit teste");
		commitRep.save(commit);
		
		Diretorio diretorio = new Diretorio();
		diretorio.setNome("Pasta");		
		diretorio.setCommit(commit);
		diretorio.setRepositorio(repositorio);
		dirRep.save(diretorio);		
		
		Arquivo novo = new Arquivo();
		novo.setNome(arquivo.getOriginalFilename());
		novo.setBlob(blob);		
		novo.setDiretorioPai(diretorio);	
		
		rep.save(novo);
		
		
		return "/home/index";
	}*/
	@PostMapping("/arquivo/uploadArquivo")
	public String upload(@RequestParam("arquivo") MultipartFile[] arquivos, @RequestParam("msg") String msg) throws IOException, NoSuchAlgorithmException {


        Usuario autor = userRep.findByEmail("guto@email.com");

        Repositorio repositorio = new Repositorio();


        autor.setId(1L);
        repositorio.setId(1L);
        
        System.out.println("COMMIT");
        System.out.println("Autor_id:"+autor.getId());
        System.out.println("Repositorio_id:"+repositorio.getId());
        Commite commit = new Commite();
        commit.setAutor(autor);
        commit.setMsg(msg);
        commit.setOrigem(repositorio);
        commitRep.save(commit);
        
	    for (MultipartFile arquivo : arquivos) {
	        System.out.println(arquivo.getOriginalFilename());
	        System.out.println(arquivo.getContentType());
     

	        System.out.println("BLOB");
	        Blob blob = new Blob();
	        byte[] bytes = arquivo.getBytes();
	        blob.setConteudo(bytes);

	        MessageDigest md = MessageDigest.getInstance("SHA1");
	        byte[] sha1bytes = md.digest(bytes);
	        BigInteger bigInt = new BigInteger(1, sha1bytes);
	        String sha1 = bigInt.toString(16);
	        blob.setSha1(sha1);
	        blobRep.save(blob);

	
	        
	        
	        System.out.println("ARQUIVO");
	        Arquivo novo = new Arquivo();
	        novo.setNome(arquivo.getOriginalFilename());
	        novo.setBlob(blob);
	        novo.setCommite(commit);
	        rep.save(novo);
	    }

	 
	    return "redirect:/home/index";
	}


	
	@GetMapping("/upload")
 	public ModelAndView carregaPaginaUpload(@RequestParam(name = "rep_id", required = true) Long repId) {
		ModelAndView mv = new ModelAndView();
		
		Optional<Repositorio> repositorioOpt = repRep.findById(repId);
	    
	    if (repositorioOpt.isPresent()) {
	        System.out.println(repositorioOpt.get().getNome());
	        mv.addObject("repositorio", repositorioOpt.get());
	    } else {
	        mv.addObject("erro", "Repositório não encontrado");
	    }

		mv.setViewName("arquivo/upload");
 		return mv;
 	}
	

	
	

}
