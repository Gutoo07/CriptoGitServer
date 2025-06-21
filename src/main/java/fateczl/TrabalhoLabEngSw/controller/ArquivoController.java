package fateczl.TrabalhoLabEngSw.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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
	private ArquivoRepository arqRep;
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
	

	@PostMapping("/arquivo/uploadArquivo")
	public ModelAndView upload(@RequestParam("arquivo") MultipartFile[] arquivos, @RequestParam("msg") String msg,
			@CookieValue(name = "user_id", defaultValue = "") String user_id,
			@RequestParam(name = "rep_id", required = true) Long repId) throws IOException, NoSuchAlgorithmException {


        Optional<Usuario> autorOpt = userRep.findById(Long.valueOf(user_id));
        Usuario autor = autorOpt.get();

        //Optional<Repositorio> repositorio = repRep.findById(rep_Id);
        Optional<Repositorio> repositorioOpt = repRep.findById(repId);
        Repositorio repositorio = repositorioOpt.get();
        
        Commite commitAnterior = commitRep.findLastCommit();
        Commite commit = new Commite();
        commit.setAutor(autor);
        commit.setMsg(msg);
        commit.setOrigem(repositorio);
		//Buscar o commit anterior para criar a sequencia temporal de versionamento        

        if (commitAnterior != null) {
        	commit.setAnterior(commitAnterior);
        }
        commitRep.save(commit);
        
		Diretorio diretorio = new Diretorio();
		diretorio.setNome("Tree node");		
		diretorio.setCommit(commit);
		dirRep.save(diretorio);
        
        
	    for (MultipartFile arquivo : arquivos) {
	        Blob blob = new Blob();
	        byte[] bytes = arquivo.getBytes();
	        blob.setConteudo(bytes);

	        MessageDigest md = MessageDigest.getInstance("SHA1");
	        byte[] sha1bytes = md.digest(bytes);
	        BigInteger bigInt = new BigInteger(1, sha1bytes);
	        String sha1 = bigInt.toString(16);
	        blob.setSha1(sha1);
	        
	        Arquivo novo = new Arquivo();
	        /*Testar se o arquivo upado ja existe ou foi alterado, para salvar ele ou nao*/
	        if (blobRep.findBySha1(sha1) == null) {
	        	/*Arquivo novo/diferente: salva no bd*/
		        blobRep.save(blob);	        
		        
		        novo.setNome(arquivo.getOriginalFilename());
		        novo.setBlob(blob);
		        novo.setDiretorioPai(diretorio);
	        } else {
	        	/*Arquivo existente: salva apenas a referencia do ultimo*/
	        	Blob antigo = blobRep.findBySha1(sha1);
	        	/*Pega o nome do arquivo antigo*/
		        novo.setNome(arquivo.getOriginalFilename());
		        novo.setBlob(antigo);
	        	novo.setDiretorioPai(diretorio);
	        }   
	        arqRep.save(novo);
	    }	 
	    return index(autor);
	}
	
	@GetMapping("/upload")
 	public ModelAndView carregaPaginaUpload(@RequestParam(name = "rep_id", required = true) Long repId) {
		ModelAndView mv = new ModelAndView();
		System.out.println("Repositorio ID:"+repId);
		
		Optional<Repositorio> repositorioOpt = repRep.findById(repId);
	    
	    if (repositorioOpt.isPresent()) {
	        System.out.println("Repositorio de ID:"+repositorioOpt.get().getId()+" "+repositorioOpt.get().getNome());
	        mv.addObject("repositorio", repositorioOpt.get());
	    } else {
	        mv.addObject("erro", "Repositório não encontrado");
	    }

		mv.setViewName("arquivo/upload");
 		return mv;
 	}
	
	public List<Arquivo> findLastByRepositorio(Long repId) {
		Commite ultimoCommit = commitRep.findLastCommitByRepositorio(repId);
		if (ultimoCommit != null) {
			return arqRep.findByCommiteAndRepositorio(ultimoCommit.getId(), repId);
		} else {
			return null;
		}
	}
	public List<Arquivo> findByCommit(Long commitId, Long repId) {
		return arqRep.findByCommiteAndRepositorio(commitId, repId);
	}
	public List<Arquivo> findAllByDiretorio(Diretorio diretorio) {
		return arqRep.findByDiretorioPai(diretorio);
	}
	public ModelAndView index(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/index");
		mv.addObject("usuario", usuario);
		return mv;
	}
	public void download(Arquivo arquivo) throws IOException {
		Blob blob = blobRep.findById(arquivo.getBlob().getId());
		Path currentDirectoryPath = FileSystems.getDefault().getPath("");
		String currentDirectoryName = currentDirectoryPath.toAbsolutePath().toString();
		String downloadPath = currentDirectoryName+"/downloads";
		
		String arquivoNome = arquivo.getNome();
		String[] caminhos = arquivoNome.split("/");
		File file;
		if (caminhos.length > 1) {
			int i;
			for (i = 0; i < (caminhos.length - 1); i++) {
				downloadPath = downloadPath + "/" + caminhos[i];
			}
			new File(downloadPath).mkdirs();
			file = new File(downloadPath+"/"+caminhos[i]);
		} else {
			new File(downloadPath).mkdirs();
			file = new File(downloadPath+"/"+arquivoNome);
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			fileOutputStream.write(blob.getConteudo());
			fileOutputStream.close();
		}
	}
	public void excluir(Arquivo arquivo) {
		arqRep.delete(arquivo);
	}

}
//