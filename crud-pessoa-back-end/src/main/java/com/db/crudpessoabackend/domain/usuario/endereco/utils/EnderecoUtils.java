package com.db.crudpessoabackend.domain.usuario.endereco.utils;

import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;

import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IEnderecoService;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.servicos.PessoaService;
import com.db.crudpessoabackend.infra.excecoes.ErroDeAutenticacaoException;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;
import java.util.List;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EnderecoUtils {
    
    private TokenUtils tokenUtils;
    private ITokenService tokenService;
    private IPessoaService pessoaService;
    private IEnderecoService enderecoService;

    public Pessoa validarPermissaoDeAlterarEndereco(String headerAutorizacao, long id) {
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpf = tokenService.getSubject(token);
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        Endereco endereco = enderecoService.buscarEnderecoPorId(id);
        List<Endereco> enderecos = pessoa.getEnderecos();
        if (!enderecos.contains(endereco)){
            throw new ErroDeAutenticacaoException("Esta propriedade não pertence a este usuário.");
        }
        return pessoa;
    }
}
