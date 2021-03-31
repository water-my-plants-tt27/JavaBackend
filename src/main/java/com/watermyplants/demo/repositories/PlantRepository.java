package com.watermyplants.demo.repositories;

import com.watermyplants.demo.models.Plant;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PlantRepository extends PagingAndSortingRepository<Plant, Long>
{
    List<Plant> findAllByUser_Userid(long userid);
}