package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IAdicionarEnderecoService;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IAtualizarEnderecoService;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IEnderecoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnderecoService implements IEnderecoService {

    private IAdicionarEnderecoService adicionarEnderecoService;
    private IAtualizarEnderecoService atualizarEnderecoService;
    
    @Override
    public Endereco adicionar(Endereco endereco) {
        return adicionarEnderecoService.adicionarEndereco(endereco);
    }

    @Override
    public Endereco atualizar(Long id, Endereco endereco) {
        return atualizarEnderecoService.atualizar(id, endereco);
    }

    @Override
    public Endereco remover(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }
    
}
