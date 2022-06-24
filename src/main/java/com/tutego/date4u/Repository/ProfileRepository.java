package com.tutego.date4u.Repository;

import com.tutego.date4u.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    //Optional ?
//    Optional<Profile> findFirstByOrderByHornlengthDesc();
//
//    List<Profile> findProfilesByOrderByHornlength();
//
//    List<Profile> findByHornlengthGreaterThan(short min);
//
//    List<Profile> findFirst10ByOrOrderByLastseenDesc();

    @Query("SELECT p FROM Profile p WHERE p.hornlength>=:minHornLength AND p.hornlength<=:maxHornLength AND p.gender=:gender AND p.birthdate<=:minAge AND p.birthdate>=:maxAge")
    List<Profile> findProfileSearchView(LocalDate minAge, LocalDate maxAge, short minHornLength, short maxHornLength, byte gender);

    @Query("SELECT p.nickname FROM Profile p WHERE p.id=:id")
    Map<String, Object> findSimpleProfile(long id);



}

