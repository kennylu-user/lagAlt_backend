package no.accelerate.lagalt_backend.repositories;

import no.accelerate.lagalt_backend.models.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Integer> {
}
