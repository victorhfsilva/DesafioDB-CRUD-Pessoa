package com.db.crudpessoabackend.controller.usuario.endereco;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IEnderecoService;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/admin/endereco")
@AllArgsConstructor
public class EnderecoAdminController {
    
    private IPessoaService pessoaService;
    private IEnderecoService enderecoService;
    private ITokenService tokenService;
    private TokenUtils tokenUtils;

    @PostMapping("/adicionar/{cpf}")
    public ResponseEntity<EnderecoRespostaDTO> adicionarEndereco(
                @PathVariable("cpf") String cpf,
                @RequestBody @Valid EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        pessoa.setAtualizadoAs(LocalDateTime.now());
        pessoa.setAtualizadoPor(pessoa.getContato().getEmail());
        Endereco endereco = enderecoDTO.converterParaEntidadeComDono(pessoa);
        Endereco enderecoSalvo = enderecoService.adicionar(endereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<EnderecoRespostaDTO> excluirEndereco(
                @PathVariable("id") Long id,
                @RequestHeader("Authorization") String headerAutorizacao){
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpfEditor = tokenService.obterSujeito(token);
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);            
        Pessoa dono = enderecoService.buscarEnderecoPorId(id).getPessoa();
        pessoaService.atualizar(dono.getCpf(), dono, editor);
        Endereco endereco = enderecoService.excluir(id);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(endereco);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EnderecoRespostaDTO> atualizarEndereco(
                @PathVariable("id") Long id, 
                @RequestBody @Valid EnderecoDTO enderecoDTO,
                @RequestHeader("Authorization") String headerAutorizacao){
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpfEditor = tokenService.obterSujeito(token);
        Pessoa editor = pessoaService.buscarPorCpf(cpfEditor);
        Pessoa dono = enderecoService.buscarEnderecoPorId(id).getPessoa();
        pessoaService.atualizar(dono.getCpf(), dono, editor);
        Endereco novoEndereco = enderecoDTO.converterParaEntidadeComDono(dono);
        Endereco enderecoAtualizado = enderecoService.atualizar(id, novoEndereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

}
