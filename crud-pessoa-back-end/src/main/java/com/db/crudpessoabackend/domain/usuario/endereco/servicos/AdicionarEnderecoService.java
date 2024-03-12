package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IAdicionarEnderecoService;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdicionarEnderecoService implements IAdicionarEnderecoService {

    private EnderecoRepository enderecoRepository;

    @Override
    public Endereco adicionarEndereco(Endereco endereco) {
       return enderecoRepository.save(endereco);
    }
    
}
