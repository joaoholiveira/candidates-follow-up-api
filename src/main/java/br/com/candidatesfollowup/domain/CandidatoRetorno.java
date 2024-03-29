package br.com.candidatesfollowup.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "follow_up")
public class CandidatoRetorno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "INCREMENT", strategy = "INCREMENT")
    @Column(columnDefinition = "SERIAL")
    private Integer id;

    @Column(length = 80)
    private String nomeCandidato;

    @JsonFormat(pattern="MM/dd/yyyy")
    private LocalDate dataRetorno;

    private Boolean isAtivo;

    private Boolean isCandidatoContatado;

    @ManyToOne
    private TipoRetorno tipoRetorno;

    public CandidatoRetorno(Integer id, String nomeCandidato, LocalDate dataRetorno, Boolean isAtivo, Boolean isCandidatoContatado, TipoRetorno tipoRetorno) {
        this.id = id;
        this.nomeCandidato = nomeCandidato;
        this.dataRetorno = dataRetorno;
        this.isAtivo = isAtivo;
        this.isCandidatoContatado = isCandidatoContatado;
        this.tipoRetorno = tipoRetorno;
    }
}
