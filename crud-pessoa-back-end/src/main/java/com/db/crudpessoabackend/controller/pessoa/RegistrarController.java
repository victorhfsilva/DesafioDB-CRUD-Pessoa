package com.db.crudpessoabackend.controller.pessoa;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.RespostaRegistrarDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping(value = "/registrar")
@AllArgsConstructor
public class RegistrarController {
    
    private PasswordEncoder passwordEncoder;
    private IPessoaService pessoaService;
    private ITokenService tokenService;
    private TokenUtils tokenUtils;

    @PostMapping("/usuario")
    public ResponseEntity<RespostaRegistrarDTO> registrarUsuario(@RequestBody PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.USUARIO);
        Pessoa pessoaSalva = pessoaService.registrar(pessoa, pessoa);
        String token = tokenService.gerarToken(pessoa.getCpf());
        PessoaRespostaDTO pessoaRespostaDTO = new PessoaRespostaDTO(pessoaSalva);
        RespostaRegistrarDTO resposta = new RespostaRegistrarDTO(token, pessoaRespostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/admin")
    public ResponseEntity<RespostaRegistrarDTO> registrarAdmin(@RequestBody PessoaDTO pessoaDTO,
                                                                @RequestHeader("Authorization") String headerAutorizacao) {
        String tokenEditor = tokenUtils.validarToken(headerAutorizacao);
        String cpfEditor = tokenService.obterSujeito(tokenEditor);
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Pessoa pessoa = pessoaDTO.converterParaEntidade(passwordEncoder, Papel.ADMIN);
        Pessoa pessoaSalva = pessoaService.registrar(pessoa, editor);
        String tokenGerado = tokenService.gerarToken(pessoa.getCpf());
        PessoaRespostaDTO pessoaRespostaDTO = new PessoaRespostaDTO(pessoaSalva);
        RespostaRegistrarDTO resposta = new RespostaRegistrarDTO(tokenGerado, pessoaRespostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
    
}
