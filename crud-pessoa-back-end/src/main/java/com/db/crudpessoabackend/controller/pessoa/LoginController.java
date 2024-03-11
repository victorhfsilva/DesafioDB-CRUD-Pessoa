package com.db.crudpessoabackend.controller.pessoa;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.dtos.LoginDTO;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.BuscarPessoaPorCpf;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.PessoaService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/login")
@AllArgsConstructor
public class LoginController {
    
    private PessoaService pessoaService;

    @PostMapping("")
    public String login(@RequestBody LoginDTO login) {
        Pessoa pessoa = pessoaService.buscarPorCpf(login.getCpf());

        return null;
    }
    

}
