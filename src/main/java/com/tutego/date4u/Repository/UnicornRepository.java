package com.tutego.date4u.Repository;

import com.tutego.date4u.Unicorn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface UnicornRepository extends CrudRepository<Unicorn, Long>{

    @Query("SELECT u FROM Unicorn u WHERE u.email=:mail")
    List<Unicorn> findUnicornByEmail(String mail);






}

