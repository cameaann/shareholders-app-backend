package server.shareholders_app_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.shareholders_app_backend.model.Shareholder;

public interface ShareholderRepository extends JpaRepository<Shareholder, Long> {
}
