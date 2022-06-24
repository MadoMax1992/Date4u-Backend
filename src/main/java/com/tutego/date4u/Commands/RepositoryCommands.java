package com.tutego.date4u.Commands;

import com.tutego.date4u.Profile;
import com.tutego.date4u.Repository.ProfileRepository;
import com.tutego.date4u.Repository.UnicornRepository;
import com.tutego.date4u.Unicorn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ShellComponent
public class RepositoryCommands {

    private final ProfileRepository profileRepository;
    private final UnicornRepository unicornRepository;

    @Autowired
    public RepositoryCommands(ProfileRepository profileRepository, UnicornRepository unicornRepository) {
        this.profileRepository = profileRepository;
        this.unicornRepository = unicornRepository;
    }
    @ShellMethod("find All Profiles")
    public void listAll() {
        System.out.println(profileRepository.findAll().toString());
    }

    @ShellMethod("find Unicorn Mail")
    public void findUnicornByMail(String mail) {
        List<Unicorn> unicornList = unicornRepository.findUnicornByEmail(mail);

        System.out.println("Found " + unicornList.size()+ " Unicorn");

    }

    @ShellMethod("save Unicorn")
    public void saveExampleUnicorn() {
        Profile profile = new Profile("nick", LocalDate.now(), 12, 0,1, "Cool Description", LocalDateTime.now());
        profileRepository.save(profile);
    }

//    find-unicorn-by-mail woody.forrest@medhurst.io

}

//unicorn repo machen
// abfrage ob eine mail adresse exestiert