package com.db.crudpessoabackend.controller.pessoa;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.LoginDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.infra.excecoes.ErroDeAutenticacaoException;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/login")
@AllArgsConstructor
public class LoginController {
    
    private IPessoaService pessoaService;
    private AuthenticationManager authenticationManager;
    private ITokenService tokenService;

    @PostMapping("")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {
        try {
            pessoaService.buscarPorCpf(login.getCpf());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(login.getCpf(), login.getSenha());
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String token = tokenService.gerarToken(login.getCpf());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (Exception ex){
            throw new ErroDeAutenticacaoException("Não foi possível autenticar este usuário.");
        }
    }
    

}
