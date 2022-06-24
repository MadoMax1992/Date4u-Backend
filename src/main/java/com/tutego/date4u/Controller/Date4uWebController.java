package com.tutego.date4u.Controller;

import com.tutego.date4u.Photo;
import com.tutego.date4u.Profile;
import com.tutego.date4u.ProfileFormData;
import com.tutego.date4u.Repository.PhotoRepository;
import com.tutego.date4u.Repository.ProfileRepository;
import com.tutego.date4u.Repository.UnicornRepository;
import com.tutego.date4u.Unicorn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class Date4uWebController {
    private final Logger log = LoggerFactory.getLogger( getClass() );


    private final PhotoRepository photoRepository;
    private final ProfileRepository profileRepository;
    private  final UnicornRepository unicornRepository;

    @Autowired
    public Date4uWebController(PhotoRepository photoRepository, ProfileRepository profileRepository, UnicornRepository unicornRepository) {
        this.photoRepository = photoRepository;
        this.profileRepository = profileRepository;
        this.unicornRepository = unicornRepository;
    }



    @RequestMapping( "/*" )
    public String indexPage(Model model) {
        long counter = profileRepository.count();

        model.addAttribute("numberOfProfiles", counter);

        return "index"; }

    @RequestMapping( "/profile" )
    public String profilePage() { return "profile"; }

    @RequestMapping( "/search" )
    public String searchPage(Model model) {
        model.addAttribute("profiles", profileRepository.findAll());
        return "search";
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Profile>getProfile(@PathVariable long id) {
        Optional<Profile> maybeProfile = profileRepository.findById( id );
        if (maybeProfile.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(maybeProfile.get());
    }

    @PostMapping("/profileUpdate")
    ResponseEntity<Profile> updateProfile(@RequestBody Map<String, Object> payload) {

//        Profile updatedProfile = new Profile(
//                (String) payload.get("nickname"),
//                LocalDate.parse((String) payload.get("birthdate")),
//                (Integer) payload.get("hornlength"),
//                (Integer) payload.get("gender"),
//                (Integer) payload.get("attractedToGender"),
//                (String) payload.get("description"),
//                LocalDateTime.parse((String) payload.get("lastseen"))
//                );

        String stringToConvert = String.valueOf(payload.get("id"));
        //TODO vermutlich einfahcer möglich

        Optional<Profile> maybeProfile = profileRepository.findById(Long.parseLong(stringToConvert));
        if (maybeProfile.isEmpty())
            return ResponseEntity.notFound().build();

        //TODO Copy Costructor erstellen
        maybeProfile.get().setNickname((String) payload.get("nickname"));
        maybeProfile.get().setBirthdate(LocalDate.parse((String) payload.get("birthdate")));
        maybeProfile.get().setHornlength((Integer) payload.get("hornlength"));
        maybeProfile.get().setGender((Integer) payload.get("gender"));
        maybeProfile.get().setAttractedToGender((Integer) payload.get("attractedToGender"));
        maybeProfile.get().setDescription((String) payload.get("description"));


        profileRepository.save(maybeProfile.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(maybeProfile.get());
    }


//    @PostMapping(value = "/profileUpdate")
//    public ResponseEntity updateProfile(@RequestParam
//                                        Profile profile) {
//        profileRepository.save(profile);
//        return (ResponseEntity) ResponseEntity.ok();
//
//    }



    //TODO not working
    @GetMapping(value= "/simple")
    public ResponseEntity<Map<String, Object>> getSimpleProfile (@RequestParam long id) {
        Map<String, Object> maybeProfile = profileRepository.findSimpleProfile( id );
        if (maybeProfile.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(maybeProfile);

    }

    @GetMapping("/unicorn")
        public ResponseEntity<Unicorn>getUnicorn(@RequestParam String mail, String password) {
        List<Unicorn> maybeUnicorn = unicornRepository.findUnicornByEmail(mail);
        if (maybeUnicorn.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Unicorn unicorn = maybeUnicorn.get(0);

        if (!unicorn.getPassword().substring(6).equals(password)) {
            log.info("bad req");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(unicorn);
    }
    @GetMapping(value = "/profiles")
    public ResponseEntity<List<Profile>>getProfiles(@RequestParam int minAge, int maxAge,
                                                    short minHornLength, short maxHornLength, byte gender){
        LocalDate currentDate = LocalDate.now();
        List<Profile> profiles = profileRepository.findProfileSearchView(currentDate.minusYears(minAge), currentDate.minusYears(maxAge), minHornLength, maxHornLength, gender);

        if (profiles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profiles);

    }





    @GetMapping(value = "/img/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable long id) throws IOException {
        Optional<Photo> maybePhoto = photoRepository.findById(id);

        if (maybePhoto.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var imgFile = new ClassPathResource("img/"+maybePhoto.get().getName()+".jpg");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }


    @PostMapping( "/save" )
    public String saveProfile( @ModelAttribute ProfileFormData profile ) {
        log.info( profile.toString() );
        Profile realProfile = new Profile(
                profile.getNickname(),
                profile.getBirthdate(),
                profile.getHornlength(),
                profile.getGender(),
                profile.getAttractedToGender(),
                profile.getDescription(),
                profile.getLastseen()
        );
        profileRepository.save(realProfile);
        return "redirect:/profile/" + realProfile.getId();
    }
}
