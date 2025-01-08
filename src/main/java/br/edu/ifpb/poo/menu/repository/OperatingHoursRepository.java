package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperatingHoursRepository extends JpaRepository<OperatingHours, Long> {
    List<OperatingHours> findByUserId(Long userId);
}