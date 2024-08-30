package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.ShareRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRangeRepository extends JpaRepository<ShareRange, Long> {
}
