package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IAtualizarPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IBuscarPessoaPorCpf;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IAtivacaoPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IExcluirPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IMostrarIdadeService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IRegistrarPessoaService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PessoaService implements IPessoaService {

    IBuscarPessoaPorCpf buscarPessoaPorCpf;
    IRegistrarPessoaService registrarPessoaService;
    IAtualizarPessoaService atualizarPessoaService;
    IExcluirPessoaService excluirPessoaService;
    IMostrarIdadeService mostrarIdadeService;
    IAtivacaoPessoaService ativacaoPessoaService;

    @Override
    public Pessoa registrar(Pessoa pessoa, Pessoa editor) {
        return registrarPessoaService.registrar(pessoa, editor);
    }

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa, Pessoa editor) {
        return atualizarPessoaService.atualizar(cpf, novaPessoa, editor);
    }

    @Override
    public Pessoa excluir(String cpf) {
        return excluirPessoaService.excluir(cpf);
    }

    @Override
    public Integer mostrarIdade(String cpf) {
        return mostrarIdadeService.mostrarIdade(cpf);
    }

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        return buscarPessoaPorCpf.buscarPorCpf(cpf);
    }

    @Override
    public Pessoa desativar(String cpf, Pessoa editor) {
        return ativacaoPessoaService.desativar(cpf, editor);
    }

    @Override
    public Pessoa ativar(String cpf, Pessoa editor) {
        return ativacaoPessoaService.ativar(cpf, editor);
    }
    
}
