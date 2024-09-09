package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.ShareTransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareTransferHistoryRepository extends JpaRepository<ShareTransferHistory, Long> {
}
