package no.accelerate.lagalt_backend.repositories;

import no.accelerate.lagalt_backend.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
