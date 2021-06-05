package ntiteam.test.planetOverlord.ApiTest.controllers;

import ntiteam.test.planetOverlord.models.Overlord;
import ntiteam.test.planetOverlord.models.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ntiteam.test.planetOverlord.repository.OverlordRepo;
import ntiteam.test.planetOverlord.repository.PlanetRepo;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/API")
public class GalacticApiController {

    private OverlordRepo overlordRepo;
    private PlanetRepo planetRepo;

    @Autowired
    public GalacticApiController(OverlordRepo overlordRepo, PlanetRepo planetRepo) {
        this.overlordRepo = overlordRepo;
        this.planetRepo = planetRepo;
    }

    @PostMapping("/newOverlord")
    public String createNewOverlord(@RequestBody Overlord overlord) {
        overlordRepo.save(overlord);
        return overlord.getOverlordName();
    }

    @PostMapping("/newPlanet")
    public String createNewPlanet(@RequestBody Planet planet) {
        planetRepo.save(planet);
        return planet.getPlanetName();
    }

    @PatchMapping("/planetForOverlord/overlord/{overlordID}/planet/{planetID}")
    public String setPlanetForOverlord(@PathVariable("planetID") long planetID, @PathVariable("overlordID") long overlordID) {
        Overlord overlord;
        if (overlordRepo.findById(overlordID).isPresent())
            overlord = overlordRepo.findById(overlordID).get();
        else
            return "Overlord not found";

        Planet planet;
        if (planetRepo.findById(planetID).isPresent())
            planet = planetRepo.findById(planetID).get();
        else
            return "Planet not found";

        Overlord overlordTemp = planet.getOverlord();

        if(overlordTemp != null){
            overlordTemp.removePlanetForOverlord(planet);
            overlordRepo.save(overlordTemp);
        }

        overlord.setPlanetForOverlord(planet);
        planet.setOverlord(overlord);

        overlordRepo.save(overlord);
        planetRepo.save(planet);

        return overlord.getOverlordName() + " took charge over " + planet.getPlanetName();
    }

    @DeleteMapping("/destroyPlanet/{planetID}")
    public String destroyPlanet(@PathVariable("planetID") long planetID) {
        Overlord overlord;
        long overlordID;

        Planet planet;
        if (planetRepo.findById(planetID).isPresent()) {
            planet = planetRepo.findById(planetID).get();

            overlordID = planet.getOverlord().getOverlordID();

            if (overlordRepo.findById(overlordID).isPresent())
                overlord = overlordRepo.findById(overlordID).get();
            else
                return "Overlord not found";

            overlord.removePlanetForOverlord(planet);
            overlordRepo.save(overlord);

            planetRepo.deleteById(planetID);
            return "Planet has been destroyed";
        }
        else
            return "Planet not found";
    }

    @GetMapping("/showSlackers")
    public String showSlackers() {
        return overlordRepo.findSlackerOverlords()
                .stream()
                .map(Overlord::getOverlordName)
                .collect(Collectors.joining(", "));
    }

    @GetMapping("/showYoungOverlords")
    public String showYoungOverlords() {
        return overlordRepo.findTopOfYoungOverlords()
                .stream()
                .map(Overlord::getOverlordName)
                .collect(Collectors.joining(", "));
    }
}
