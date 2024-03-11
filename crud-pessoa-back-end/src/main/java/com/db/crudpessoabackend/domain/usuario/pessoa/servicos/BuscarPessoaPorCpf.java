package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IBuscarPessoaPorCpf;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscarPessoaPorCpf implements IBuscarPessoaPorCpf {

    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf)
                                        .orElseThrow(() -> 
                                        new EntidadeNaoEncontradaException(
                                            "Não foi possível encontrar a pessoa com CPF " + cpf ));
    }
    
}
