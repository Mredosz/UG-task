package pl.mateusz.redosz.nbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.redosz.nbp.model.entity.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
}
