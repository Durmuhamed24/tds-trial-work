package com.tds_trial_work.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tds_trial_work.model.Esim;

@Repository
public interface EsimRepository extends JpaRepository<Esim, Long> {
}