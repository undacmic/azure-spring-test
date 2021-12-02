package com.proiect.repositories;

import com.proiect.entities.Lending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendingRepository extends JpaRepository<Lending, Long> {

}
