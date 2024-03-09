package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IExcluirEnderecoService;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExcluirEnderecoService implements IExcluirEnderecoService{

    private EnderecoRepository enderecoRepository;

    @Override
    public Endereco excluir(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                                .orElseThrow(() -> 
                                new EntidadeNaoEncontradaException(
                                    "Não foi possível encontrar o endereco com index " + id));
        
        enderecoRepository.delete(endereco);
        return endereco;
    }
    
}
