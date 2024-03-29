package br.com.candidatesfollowup.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TipoRetornoDTO {
    @NotBlank(message = "Descrição é obrigatório!")
    @Size(min = 2, max = 60, message = "Descrição deve conter entre 2 e 60 caracteres!")
    private String descricao;
}
