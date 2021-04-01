package com.watermyplants.demo.repositories;

import com.watermyplants.demo.models.Useremail;
import org.springframework.data.repository.CrudRepository;

/**
 * The CRUD Repository connecting Useremail to the rest of the application
 */
public interface UseremailRepository
    extends CrudRepository<Useremail, Long>
{
}

