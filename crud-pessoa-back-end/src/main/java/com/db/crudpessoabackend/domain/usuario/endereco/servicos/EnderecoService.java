package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IEnderecoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnderecoService implements IEnderecoService {

    @Override
    public Endereco adicionar(Endereco endereco) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'adicionar'");
    }

    @Override
    public Endereco atualizar(int index, Endereco endereco) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    @Override
    public Endereco remover(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }
    
}
