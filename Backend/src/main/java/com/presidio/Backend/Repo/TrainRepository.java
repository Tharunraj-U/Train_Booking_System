package com.presidio.Backend.Repo;

import com.presidio.Backend.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train,Long>
{

}
