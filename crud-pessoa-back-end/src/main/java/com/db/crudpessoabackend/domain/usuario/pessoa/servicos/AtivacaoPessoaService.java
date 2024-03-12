package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IAtivacaoPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AtivacaoPessoaService implements IAtivacaoPessoaService {
    
    private PessoaRepository pessoaRepository;
    private BuscarPessoaPorCpf buscarPessoaPorCpf;

    public Pessoa desativar(String cpf, Pessoa editor) {
        Pessoa pessoa = buscarPessoaPorCpf.buscarPorCpf(cpf);
        pessoa.setActive(false);
        pessoa.setDeactivatedAt(LocalDateTime.now());
        pessoa.setDeactivatedBy(editor.getContato().getEmail());
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa ativar(String cpf, Pessoa editor) {
        Pessoa pessoa = buscarPessoaPorCpf.buscarPorCpf(cpf);
        pessoa.setActive(true);
        pessoa.setUpdatedAt(LocalDateTime.now());
        pessoa.setUpdatedBy(editor.getContato().getEmail());
        pessoa.setDeactivatedAt(null);
        pessoa.setDeactivatedBy(null);
        return pessoaRepository.save(pessoa);
    }
}
