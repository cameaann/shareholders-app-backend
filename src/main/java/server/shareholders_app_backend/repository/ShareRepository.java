package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share, Long> {
}
