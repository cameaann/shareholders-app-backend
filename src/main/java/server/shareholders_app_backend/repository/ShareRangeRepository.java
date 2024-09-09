package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.ShareRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ShareRangeRepository extends JpaRepository<ShareRange, Long> {

    @Query("SELECT COALESCE(MAX(sr.endNumber), 0) FROM ShareRange sr")
    Integer findMaxEndNumber();

    @Query("SELECT sr FROM ShareRange sr ORDER BY sr.startNumber ASC")
    List<ShareRange> findAllOrderedByStartNumber();
}
