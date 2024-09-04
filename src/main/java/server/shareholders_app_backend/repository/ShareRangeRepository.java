package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.ShareRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShareRangeRepository extends JpaRepository<ShareRange, Long> {

    @Query("SELECT COALESCE(MAX(sr.endNumber), 0) FROM ShareRange sr")
    Integer findMaxEndNumber();
}
