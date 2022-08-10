package br.com.candidatesfollowup.services;

import br.com.candidatesfollowup.domain.CandidatoRetorno;
import br.com.candidatesfollowup.domain.dtos.CandidatoRetornoDTO;
import br.com.candidatesfollowup.exceptions.ObjectNotFoundException;
import br.com.candidatesfollowup.repositories.CandidatoRetornoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CandidatoRetornoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CandidatoRetornoService.class);

    @Autowired
    private CandidatoRetornoRepository candidatoRetornoRepository;

    @Transactional
    public CandidatoRetorno salvarCandidatoRetorno(CandidatoRetorno candidatoRetorno) {
        return candidatoRetornoRepository.save(candidatoRetorno);
    }

    @Transactional
    public void desabilitarTodosRetornosCandidatosHabilitados() {
        candidatoRetornoRepository.disableAllCandidatoRetornoByIsAtivoTrue();
        LOGGER.info("desabilitarTodosRetornosCandidatosHabilitados(): Todos os Retornos de Candidatos foram desabilitados!");
    }

    @Transactional
    public void desabilitarRetornoCandidato(Integer idRetornoCandidato) {
        buscarCandidatoRetornoPorId(idRetornoCandidato);
        candidatoRetornoRepository.disableCandidatoRetorno(idRetornoCandidato);
        LOGGER.info("desabilitarRetornoCandidato(): Retorno do Candidato "+idRetornoCandidato+" desabilitado!");
    }

    @Transactional
    public void habilitarTodosRetornosCandidatosDesabilitados() {
        candidatoRetornoRepository.enableAllCandidatoRetornoByIsAtivoFalse();
        LOGGER.info("habilitarTodosRetornosCandidatosDesabilitados(): Todos os Retornos de Candidatos foram habilitados!");
    }

    @Transactional
    public void habilitarRetornoCandidato(Integer idRetornoCandidato) {
        buscarCandidatoRetornoPorId(idRetornoCandidato);
        candidatoRetornoRepository.enableCandidatoRetorno(idRetornoCandidato);
        LOGGER.info("habilitarRetornoCandidato(): Retorno do Candidato "+idRetornoCandidato+" habilitado!");
    }

    @Transactional
    public void marcarRetornoCandidatoComoContatado(Integer idRetornoCandidato) {
        candidatoRetornoRepository.candidatoContatado(idRetornoCandidato);
        LOGGER.info("Candidato com id "+idRetornoCandidato+" marcado como contatado!");
    }

    @Transactional
    public void marcarRetornoCandidatoComoNaoContatado(Integer idRetornoCandidato) {
        candidatoRetornoRepository.candidatoNaoContatado(idRetornoCandidato);
        LOGGER.info("Candidato com id "+idRetornoCandidato+" marcado como não contatado!");
    }

    @Transactional
    public void deletarRetornoCandidato(Integer idRetornoCandidato) {
        candidatoRetornoRepository.delete(buscarCandidatoRetornoPorId(idRetornoCandidato));
        LOGGER.info("deletarRetornoCandidato(): Retorno do candidato com id "+idRetornoCandidato+" foi excluído.");
    }

    @Transactional
    public void deletarRetornoCandidatoDesabilitados() {
        candidatoRetornoRepository.deleteAllByIsAtivoFalse();
    }

    public CandidatoRetorno buscarCandidatoRetornoPorId(Integer idCandidatoRetorno){
        LOGGER.info("Buscando retorno do candidato com id: "+idCandidatoRetorno);
        Optional<CandidatoRetorno> candidatoRetorno = candidatoRetornoRepository.findById(idCandidatoRetorno);
        return candidatoRetorno.orElseThrow(() -> new ObjectNotFoundException("Retorno do candidato não encontrado!"));
    }

    public CandidatoRetorno fromDTO(CandidatoRetornoDTO candidatoRetornoDTO){
        return new CandidatoRetorno(null, candidatoRetornoDTO.getNomeCandidato().trim(), candidatoRetornoDTO.getDataRetorno(),
                true, false, candidatoRetornoDTO.getMotivoRetorno(), candidatoRetornoDTO.getTipoRetorno());
    }

}
