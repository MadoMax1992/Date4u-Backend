package com.tutego.date4u.Commands;

import com.tutego.date4u.Photo;
import com.tutego.date4u.Profile;
import com.tutego.date4u.Unicorn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.Character.UnicodeBlock;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.UnaryOperator;


@ShellComponent
public class EntityManagerCommands {

    @PersistenceContext
    private EntityManager em;

    @ShellMethod("find profile")
    public void findProfile( long id) {

        Profile fillmoreFat = em.find( Profile.class, id );
        System.out.println( fillmoreFat );


    }
    // alle nicknacmes von unicorns zwischen übergebenen min und max per shell comando
    @ShellMethod("find uni horns")
    public void findUnicornsHorns (short min, short max) {
        TypedQuery<Profile> query =
                em.createQuery( "SELECT p FROM Profile p WHERE p.hornlength > :min AND p.hornlength < :max", Profile.class )
                        .setParameter("min", min)
                        .setParameter("max", max);
        List<Profile> profiles = query.getResultList();
        for ( Profile p : profiles ) {
            System.out.println( p.getNickname() );
        }
    }

    @ShellMethod("find photos of unicorn")
    public void findPhotosOfUnicorn (long id){
        Unicorn unicorn = em.find(Unicorn.class, id);
        Profile profile = unicorn.getProfile();
        List <Photo> photos = profile.getPhotos();

        for (Photo photo : photos) {
            System.out.println(photo.getName());
        }


    }

}
