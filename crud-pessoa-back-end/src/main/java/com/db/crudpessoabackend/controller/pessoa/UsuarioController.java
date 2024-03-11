package com.db.crudpessoabackend.controller.pessoa;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.PessoaRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.infra.excecoes.ErroDeAutenticacaoException;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value = "/usuario")
@AllArgsConstructor
public class UsuarioController {
    
    private ITokenService tokenService;
    private IPessoaService pessoaService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/dados")
    public ResponseEntity<PessoaRespostaDTO> getDados(@RequestHeader("Authorization") String headerAutorizacao) {
        String token = TokenUtils.extractToken(headerAutorizacao);
        
        if (!tokenService.isTokenValido(token)){
            throw new ErroDeAutenticacaoException("Token Inválido.");
        }
        
        String cpf = tokenService.getSubject(token);
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @DeleteMapping("/desativar")
    public ResponseEntity<PessoaRespostaDTO> desativar(@RequestHeader("Authorization") String headerAutorizacao){
        String token = TokenUtils.extractToken(headerAutorizacao);
        
        if (!tokenService.isTokenValido(token)){
            throw new ErroDeAutenticacaoException("Token Inválido.");
        }

        String cpf = tokenService.getSubject(token);
        Pessoa pessoa = pessoaService.desativar(cpf);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
    
    @PostMapping("/ativar")
    public ResponseEntity<PessoaRespostaDTO> ativar(@RequestHeader("Authorization") String headerAutorizacao){
        String token = TokenUtils.extractToken(headerAutorizacao);
        
        if (!tokenService.isTokenValido(token)){
            throw new ErroDeAutenticacaoException("Token Inválido.");
        }

        String cpf = tokenService.getSubject(token);
        Pessoa pessoa = pessoaService.ativar(cpf);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<PessoaRespostaDTO> putMethodName(@RequestHeader("Authorization") String headerAutorizacao, @RequestBody PessoaDTO pessoaDTO) {
        String token = TokenUtils.extractToken(headerAutorizacao);
        
        if (!tokenService.isTokenValido(token)){
            throw new ErroDeAutenticacaoException("Token Inválido.");
        }
        String cpf = tokenService.getSubject(token);
        Pessoa antigaPessoa = pessoaService.buscarPorCpf(cpf);
        Pessoa novaPessoa = pessoaDTO.converterParaEntidade(passwordEncoder, antigaPessoa.getPapel());
        Pessoa pessoa = pessoaService.atualizar(cpf, novaPessoa);
        PessoaRespostaDTO resposta = new PessoaRespostaDTO(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
