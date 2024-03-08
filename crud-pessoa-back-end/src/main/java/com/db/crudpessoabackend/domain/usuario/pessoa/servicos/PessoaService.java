package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IAtualizarPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IRegistrarPessoaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PessoaService implements IPessoaService {

    IRegistrarPessoaService registrarPessoaService;
    IAtualizarPessoaService atualizarPessoaService;

    @Override
    public Pessoa registrar(Pessoa pessoa) {
        return registrarPessoaService.registrar(pessoa);
    }

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa) {
        return atualizarPessoaService.atualizar(cpf, novaPessoa);
    }

    @Override
    public Pessoa excluir(String cpf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluir'");
    }

    @Override
    public Integer mostrarIdade(String cpf) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarIdade'");
    }
    
}
