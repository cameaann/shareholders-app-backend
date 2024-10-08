package server.shareholders_app_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.shareholders_app_backend.model.Shareholder;

public interface ShareholderRepository extends JpaRepository<Shareholder, Long> {

    // Kysely, joka tarkistaa, onko Shareholder olemassa annetulla henkilö- tai
    // yritystunnuksella
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Shareholder s WHERE s.personalIdOrCompanyId = :personalIdOrCompanyId")
    boolean existsByPersonalIdOrCompanyId(@Param("personalIdOrCompanyId") String personalIdOrCompanyId);
}
