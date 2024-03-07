package com.db.crudpessoabackend.domain.usuario.pessoa;

import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IRegistrarPessoaService;

public class PessoaService implements IPessoaService {

    IRegistrarPessoaService registrarPessoaService;

    public PessoaService(IRegistrarPessoaService registrarPessoaService) {
        this.registrarPessoaService = registrarPessoaService;
    }

    @Override
    public Pessoa registrar(Pessoa pessoa) {
        return registrarPessoaService.registrar(pessoa);
    }

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    @Override
    public Pessoa atualizarEndereco(String cpf, Pessoa novoEndereco) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarEndereco'");
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
