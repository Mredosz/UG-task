package pl.mateusz.redosz.nbp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mateusz.redosz.nbp.model.entity.Computer;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    @Query("""
                SELECT c FROM Computer c
                WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
                  AND (:date IS NULL OR c.accountingDate = :date)
            """)
    List<Computer> search(
            @Param("name") String name,
            @Param("date") LocalDate date,
            Sort sort
    );
}
