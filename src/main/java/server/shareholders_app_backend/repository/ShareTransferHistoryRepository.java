package server.shareholders_app_backend.repository;

import server.shareholders_app_backend.model.ShareTransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

// Rajapinta, joka tarjoaa CRUD-toiminnot ShareTransferHistory-entiteetille
public interface ShareTransferHistoryRepository extends JpaRepository<ShareTransferHistory, Long> {
    // Tämä rajapinta perii kaikki JpaRepository:n metodit,
    // joten ylimääräisiä kyselyitä ei tarvita, ellei erikoistarpeita ole.
}
