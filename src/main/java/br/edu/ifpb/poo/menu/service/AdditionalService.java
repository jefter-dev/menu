package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.additional.AdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.AdditionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AdditionalService {
    @Autowired
    private AdditionalRepository additionalRepository;

    /**
     * Retorna um adicional pelo ID.
     */
    public Additional getAdditionalById(Long id) throws AdditionalNotFoundException {
        try {
            Optional<Additional> additionalFind = additionalRepository.findById(id);
            if (additionalFind.isEmpty()) {
                throw new AdditionalNotFoundException("Adicional não encontrado com o ID fornecido.");
            }
            return additionalFind.get();
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Retorna todos os adicionais.
     */
    public List<Additional> getAllAdditionals() {
        return additionalRepository.findAll();
    }

//    /**
//     * Retorna adicionais vinculados a um usuário.
//     */
//    public List<Additional> getAdditionalsByUser(User user) throws InvalidUserException {
//        if (user == null) {
//            throw new InvalidUserException("Usuário fornecido não encontrado.");
//        }
//        try {
//            return additionalRepository.getAdditionalsByUserId(user.getId());
//        } catch (DataIntegrityViolationException ex) {
//            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
//        }
//    }

    /**
     * Cria um novo adicional.
     */
    public Additional createAdditional(Additional additional) throws InvalidFieldException {
        validateAdditional(additional);
        try {
            return additionalRepository.save(additional);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Atualiza um adicional existente.
     */
    public Additional updateAdditional(Long id, Additional updatedAdditional) throws AdditionalNotFoundException, InvalidFieldException {
        Additional existingAdditional = getAdditionalById(id);
        validateAdditional(updatedAdditional);

        existingAdditional.setName(updatedAdditional.getName());
        existingAdditional.setDescription(updatedAdditional.getDescription());
        existingAdditional.setPrice(updatedAdditional.getPrice());
        existingAdditional.setUpdatedAt(updatedAdditional.getUpdatedAt());

        try {
            return additionalRepository.save(existingAdditional);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Deleta um adicional pelo ID.
     */
    public void deleteAdditional(Long id) throws AdditionalNotFoundException {
        Additional additional = getAdditionalById(id);
        try {
            additionalRepository.delete(additional);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    /**
     * Valida os campos do adicional.
     */
    private void validateAdditional(Additional additional) throws InvalidFieldException {
        if (additional.getName() == null || additional.getName().trim().isEmpty()) {
            throw new InvalidFieldException("O nome do adicional não pode ser nulo ou vazio.");
        }
        if (additional.getPrice() == null) {
            throw new InvalidFieldException("O preço do adicional não pode ser nulo ou vazio.");
        }
        if (additional.getDescription() == null || additional.getDescription().trim().isEmpty()) {
            throw new InvalidFieldException("A descrição do adicional não pode ser nula ou vazia.");
        }
    }
}
