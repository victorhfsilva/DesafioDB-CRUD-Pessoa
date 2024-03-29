package com.db.crudpessoabackend.controller.usuario.pessoa;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.AtualizarPessoaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.RespostaAtualizarPessoaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/usuario")
@AllArgsConstructor
public class PessoaUsuarioController {
    
    private ITokenService tokenService;
    private IPessoaService pessoaService;
    private PasswordEncoder passwordEncoder;
    private TokenUtils tokenUtils;

    @GetMapping("/dados")
    public ResponseEntity<PessoaRespostaDTO> getDados(@RequestHeader("Authorization") String headerAutorizacao) {
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpf = tokenService.obterSujeito(token);
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PatchMapping("/desativar")
    public ResponseEntity<PessoaRespostaDTO> desativar(@RequestHeader("Authorization") String headerAutorizacao){
        String token = tokenUtils.validarToken(headerAutorizacao);        
        String cpf = tokenService.obterSujeito(token);
        Pessoa pessoaSalva = pessoaService.buscarPorCpf(cpf);
        Pessoa pessoa = pessoaService.desativar(cpf, pessoaSalva);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @PatchMapping("/ativar")
    public ResponseEntity<PessoaRespostaDTO> ativar(@RequestHeader("Authorization") String headerAutorizacao){
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpf = tokenService.obterSujeito(token);
        Pessoa pessoaSalva = pessoaService.buscarPorCpf(cpf);
        Pessoa pessoa = pessoaService.ativar(cpf, pessoaSalva);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<RespostaAtualizarPessoaDTO> atualizar(@RequestHeader("Authorization") String headerAutorizacao, 
                                                        @RequestBody @Valid AtualizarPessoaDTO pessoaDTO) {
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpf = tokenService.obterSujeito(token);
        Pessoa antigaPessoa = pessoaService.buscarPorCpf(cpf);
        Pessoa novaPessoa = pessoaDTO.converterParaEntidade(passwordEncoder, antigaPessoa.getPapel());
        Pessoa pessoa = pessoaService.atualizar(cpf, novaPessoa, novaPessoa);
        RespostaAtualizarPessoaDTO resposta = new RespostaAtualizarPessoaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
