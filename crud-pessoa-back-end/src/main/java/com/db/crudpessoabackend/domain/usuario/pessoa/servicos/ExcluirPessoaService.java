package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IExcluirPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExcluirPessoaService implements IExcluirPessoaService {

    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa excluir(String cpf) {
        Pessoa pessoa = pessoaRepository.findByCpf(cpf)
                                        .orElseThrow(() -> 
                                        new EntidadeNaoEncontradaException(
                                            "Não foi possível encontrar a pessoa com CPF " + cpf));
        pessoaRepository.delete(pessoa);
        return pessoa;
    }
    
}
