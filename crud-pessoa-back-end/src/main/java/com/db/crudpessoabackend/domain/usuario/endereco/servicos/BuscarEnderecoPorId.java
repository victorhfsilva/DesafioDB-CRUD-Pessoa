package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IBuscarEnderecoPorId;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscarEnderecoPorId implements IBuscarEnderecoPorId {

    private EnderecoRepository enderecoRepository;
    
    @Override
    public Endereco buscarPorId(Long id) {
       return enderecoRepository.findById(id)
                                        .orElseThrow(() -> 
                                        new EntidadeNaoEncontradaException(
                                            "Não foi possível encontrar o endereco de id " + id));
    }
    
}
