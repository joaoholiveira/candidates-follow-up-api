package br.com.candidatesfollowup.resources;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.TipoRetorno;
import br.com.candidatesfollowup.domain.dtos.CandidatoRetornoDTO;
import br.com.candidatesfollowup.services.CandidatoRetornoService;
import br.com.candidatesfollowup.services.TipoRetornoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/candidato-retorno")
public class CandidatoRetornoResource {

    @Autowired
    private CandidatoRetornoService candidatoRetornoService;

    @Autowired
    private TipoRetornoService tipoRetornoService;

    @PostMapping
    public ResponseEntity<?> inserirRetornoCandidato(@Valid @RequestBody CandidatoRetornoDTO candidatoRetornoDTO){
        CandidatoRetorno candidatoRetorno = candidatoRetornoService.fromDTO(candidatoRetornoDTO);
        candidatoRetorno = candidatoRetornoService.salvarCandidatoRetorno(candidatoRetorno);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(candidatoRetorno.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{idRetornoCandidato}")
    public ResponseEntity<?> atualizarRetornoCandidato(@PathVariable Integer idRetornoCandidato,
                                                       @Valid @RequestBody CandidatoRetornoDTO candidatoRetornoDTO){
        CandidatoRetorno candidatoRetorno = candidatoRetornoService.fromDTO(candidatoRetornoDTO);
        candidatoRetorno.setId(idRetornoCandidato);
        candidatoRetornoService.salvarCandidatoRetorno(candidatoRetorno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("desabilitar")
    public ResponseEntity<?> desabilitarTodosRetornosCandidatosHabilitados(){
        candidatoRetornoService.desabilitarTodosRetornosCandidatosHabilitados();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("desabilitar/{idRetornoCandidato}")
    public ResponseEntity<?> desabilitarRetornoCandidato(@PathVariable Integer idRetornoCandidato){
        candidatoRetornoService.desabilitarRetornoCandidato(idRetornoCandidato);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("habilitar")
    public ResponseEntity<?> habilitarTodosRetornosCandidatosDesabilitados(){
        candidatoRetornoService.habilitarTodosRetornosCandidatosDesabilitados();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("habilitar/{idRetornoCandidato}")
    public ResponseEntity<?> habilitarRetornoCandidato(@PathVariable Integer idRetornoCandidato){
        candidatoRetornoService.habilitarRetornoCandidato(idRetornoCandidato);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("contatado/{idRetornoCandidato}")
    public ResponseEntity<?> marcarRetornoCandidatoComoContatado(@PathVariable Integer idRetornoCandidato){
        candidatoRetornoService.marcarRetornoCandidatoComoContatado(idRetornoCandidato);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("descontatado/{idRetornoCandidato}")
    public ResponseEntity<?> marcarRetornoCandidatoComoNaoContatado(@PathVariable Integer idRetornoCandidato){
        candidatoRetornoService.marcarRetornoCandidatoComoNaoContatado(idRetornoCandidato);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idRetornoCandidato}")
    public ResponseEntity<?> deletarRetornoCandidato(@PathVariable Integer idRetornoCandidato){
        candidatoRetornoService.deletarRetornoCandidato(idRetornoCandidato);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/desabilitados")
    public ResponseEntity<?> deletarRetornoCandidatoDesabilitados(){
        candidatoRetornoService.deletarRetornoCandidatoDesabilitados();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> buscarTodosRetornoCandidatoHabilitados(){
        List<CandidatoRetorno> candidatoRetornos = candidatoRetornoService.buscarTodosRetornoCandidatoHabilitados();
        return ResponseEntity.ok(candidatoRetornos);
    }

    @GetMapping("/desabilitados")
    public ResponseEntity<?> buscarTodosRetornoCandidatoDesabilitados(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "linesPerPage", defaultValue = "10")  Integer linesPerPage){
        List<CandidatoRetorno> candidatoRetornos = candidatoRetornoService.buscarTodosRetornoCandidatoDesabilitados();
        return ResponseEntity.ok(candidatoRetornos);
    }

    @GetMapping("/{idRetornoCandidato}")
    public ResponseEntity<?> buscarRetornoCandidatoPorId(@PathVariable Integer idRetornoCandidato){
        return ResponseEntity.ok(candidatoRetornoService.buscarCandidatoRetornoPorId(idRetornoCandidato));
    }

    @GetMapping("/data-atual-retorno")
    public ResponseEntity<?> buscarCandidatoRetornoHabilitadosPorDataAtual(){
        return ResponseEntity.ok(candidatoRetornoService.buscarCandidatoRetornoHabilitadosPorData(LocalDate.now()));
    }

    @GetMapping("/nome-candidato-data-retorno")
    public ResponseEntity<?> buscarTodosRetornoCandidatoPorDescricaoEDataDeRetorno(@RequestParam(value = "nomeCandidato", defaultValue = "") String nomeCandidato,
                                                                      @RequestParam(value = "dataRetorno")  String data) throws ParseException {

        return ResponseEntity.ok(candidatoRetornoService.buscarCandidatoRetornoHabilitadosPorNomeCandidatoEDataRetorno(nomeCandidato, data));
    }

    @GetMapping("/tipo-retorno/{idTipoRetorno}")
    public ResponseEntity<?> buscarCandidatoRetornoPorTipoDeRetorno(@PathVariable Integer idTipoRetorno) throws ParseException {
        TipoRetorno tipoRetorno = tipoRetornoService.buscarTipoDeRetornoPorId(idTipoRetorno);
        List<CandidatoRetorno> candidatoRetornoList = candidatoRetornoService.buscarPorTipoDeRetorno(tipoRetorno);

        return ResponseEntity.ok(candidatoRetornoList);
    }


}
