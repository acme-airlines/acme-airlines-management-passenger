package co.edu.uni.acme.ariline.management.passenger.repository;

import co.edu.uni.acme.aerolinea.commons.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerUserRepository extends JpaRepository<PassengerEntity,String> {

}
