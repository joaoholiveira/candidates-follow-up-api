package br.com.candidatesfollowup.domain.dtos;

import br.com.candidatesfollowup.domain.TipoRetorno;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CandidatoRetornoDTO {
    @NotBlank(message = "Nome do candidato é obrigatório!")
    @Size(min = 2, max = 80, message = "Nome do candidato deve conter entre 2 e 80 caracteres!")
    private String nomeCandidato;

    @NotNull(message = "Data de retorno é obrigatório!")
    private LocalDate dataRetorno;

    @NotNull(message = "Canal de Retorno é obrigatório!")
    private TipoRetorno canalDeRetorno;
}
