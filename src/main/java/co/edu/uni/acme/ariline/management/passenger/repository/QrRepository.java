package co.edu.uni.acme.ariline.management.passenger.repository;

import co.edu.uni.acme.aerolinea.commons.entity.QrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrRepository extends JpaRepository<QrEntity, String> {
}
