package ntiteam.test.planetOverlord.ApiTest.controllers;

import ntiteam.test.planetOverlord.models.Overlord;
import ntiteam.test.planetOverlord.models.Planet;
import ntiteam.test.planetOverlord.repository.OverlordRepo;
import ntiteam.test.planetOverlord.repository.PlanetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class GalacticWebController {

    private OverlordRepo overlordRepo;
    private PlanetRepo planetRepo;

    @Autowired
    public GalacticWebController(OverlordRepo overlordRepo, PlanetRepo planetRepo) {
        this.overlordRepo = overlordRepo;
        this.planetRepo = planetRepo;
    }

    @GetMapping("/Overlords")
    public String overlords(Model model) {
        model.addAttribute("overlords", overlordRepo.findAll());
        return "newOverlord";
    }

    @PostMapping("/createNewOverlord")
    public String createNewOverlord(
            @RequestParam(name="overlordName",required=true,defaultValue="") String overlordName,
            @RequestParam(name="overlordAge",required=true,defaultValue="") int overlordAge
    ) {
        Overlord overlord = new Overlord(overlordName, overlordAge);

        overlordRepo.save(overlord);

        return "redirect:/Overlords";
    }

    @GetMapping("/Planets")
    public String planets(Model model) {
        model.addAttribute("planets", planetRepo.findAll());
        model.addAttribute("overlords", overlordRepo.findAll());
        return "newPlanet";
    }

    @PostMapping("/createNewPlanet")
    public String createNewPlanets(
            @RequestParam(name="planetName",required=true,defaultValue="") String planetName
    ) {
        Planet planet = new Planet(planetName);

        planetRepo.save(planet);

        return "redirect:/Planets";
    }

    @DeleteMapping("/destroyPlanet/{planetID}")
    public String destroyPlanets(@PathVariable("planetID") long planetID) {
        Overlord overlord;
        long overlordID;

        Planet planet;
        if (planetRepo.findById(planetID).isPresent()) {
            planet = planetRepo.findById(planetID).get();

            if(planet.getOverlord() != null) {
                if (overlordRepo.findById(planet.getOverlord().getOverlordID()).isPresent()) {
                    overlordID = planet.getOverlord().getOverlordID();

                    overlord = overlordRepo.findById(overlordID).get();

                    overlord.removePlanetForOverlord(planet);
                    overlordRepo.save(overlord);
                }
            }

            planetRepo.deleteById(planetID);
        }
        return "redirect:/Planets";
    }

    @GetMapping("/showSlackers")
    public String showSlacker(Model model) {
        model.addAttribute("overlords", overlordRepo.findSlackerOverlords());
        return "slackerOverlord";
    }

    @GetMapping("/showYoungOverlords")
    public String showYoungOverlords(Model model) {
        model.addAttribute("overlords", overlordRepo.findTopOfYoungOverlords());
        return "youngOverlord";
    }

    @PatchMapping("/planetForOverlord/planet/{planetID}")
    public String setPlanetsForOverlord(@PathVariable("planetID") long planetID, @RequestParam(name="overlordID",required=true,defaultValue="") long overlordID) {

        if(overlordID == -1){
            return "redirect:/Planets";
        }

        Overlord overlord;
        if (overlordRepo.findById(overlordID).isPresent())
            overlord = overlordRepo.findById(overlordID).get();
        else
            return "redirect:/Planets";

        Planet planet;
        if (planetRepo.findById(planetID).isPresent())
            planet = planetRepo.findById(planetID).get();
        else
            return "redirect:/Planets";

        Overlord overlordTemp = planet.getOverlord();

        if(overlordTemp != null){
            overlordTemp.removePlanetForOverlord(planet);
            overlordRepo.save(overlordTemp);
        }

        overlord.setPlanetForOverlord(planet);
        planet.setOverlord(overlord);

        overlordRepo.save(overlord);
        planetRepo.save(planet);

        return "redirect:/Planets";
    }

}
