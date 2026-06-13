package FYP.CREDITRISK;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {
    List<Prediction> findByCompanyOrderByCreatedAtDesc(Company company);
    Optional<Prediction> findFirstByCompanyOrderByCreatedAtDesc(Company company);
}
