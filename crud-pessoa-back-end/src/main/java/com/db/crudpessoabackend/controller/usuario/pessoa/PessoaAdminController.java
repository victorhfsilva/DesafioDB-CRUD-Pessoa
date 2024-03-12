package com.db.crudpessoabackend.controller.usuario.pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.papel.Papel;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/admin")
@AllArgsConstructor
public class PessoaAdminController {

    private IPessoaService pessoaService;
    private PasswordEncoder passwordEncoder;
    private ITokenService tokenService;
    private TokenUtils tokenUtils;

    @PatchMapping("/desativar/{cpf}")
    public ResponseEntity<PessoaRespostaDTO> desativar(@PathVariable("cpf") String cpf,
                                                        @RequestHeader("Authorization") String headerAutorizacao){
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpfEditor = tokenService.obterSujeito(token);
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Pessoa pessoa = pessoaService.desativar(cpf, editor);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @DeleteMapping("/excluir/{cpf}")
    public ResponseEntity<PessoaRespostaDTO> excluir(@PathVariable("cpf") String cpf){
        Pessoa pessoa = pessoaService.excluir(cpf);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/ativar/{cpf}")
    public ResponseEntity<PessoaRespostaDTO> ativar(@PathVariable("cpf") String cpf,
                                                    @RequestHeader("Authorization") String headerAutorizacao){
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpfEditor = tokenService.obterSujeito(token);
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Pessoa pessoa = pessoaService.ativar(cpf, editor);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar/{cpf}")
    public ResponseEntity<PessoaRespostaDTO> atualizar(@PathVariable("cpf") String cpf,
                                                        @RequestParam(name = "papel", defaultValue = "USUARIO") Papel papel, 
                                                        @RequestBody @Valid PessoaDTO pessoaDTO,
                                                        @RequestHeader("Authorization") String headerAutorizacao) {
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpfEditor = tokenService.obterSujeito(token);
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Pessoa novaPessoa = pessoaDTO.converterParaEntidadeSemEndereco(passwordEncoder, papel);
        Pessoa pessoa = pessoaService.atualizar(cpf, novaPessoa, editor);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
