package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.Shareholder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareholderRepository extends JpaRepository<Shareholder, Long> {
}
