package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.ShareRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ShareRangeRepository extends JpaRepository<ShareRange, Long> {

    // Kysely, joka palauttaa suurimman endNumber-arvon ShareRange-entiteeteistä.
    // Jos arvoja ei ole, palauttaa 0.
    @Query("SELECT COALESCE(MAX(sr.endNumber), 0) FROM ShareRange sr")
    Integer findMaxEndNumber();

    // Kysely, joka palauttaa kaikki ShareRange-entiteetit järjestettyinä
    // startNumberin mukaan nousevassa järjestyksessä.
    @Query("SELECT sr FROM ShareRange sr ORDER BY sr.startNumber ASC")
    List<ShareRange> findAllOrderedByStartNumber();
}
