package ntiteam.test.planetOverlord.ApiTest.controllers;

import com.google.gson.Gson;
import ntiteam.test.planetOverlord.models.Overlord;
import ntiteam.test.planetOverlord.models.Planet;
import ntiteam.test.planetOverlord.repository.OverlordRepo;
import ntiteam.test.planetOverlord.repository.PlanetRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GalacticApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GalacticApiController galacticApiController;

    @Autowired
    private PlanetRepo planetRepo;

    @Autowired
    private OverlordRepo overlordRepo;

    @Test
    void createNewOverlord() throws Exception {
        Overlord overlord = new Overlord("Palpatin", 100);

        given(galacticApiController.createNewOverlord(overlord)).willReturn("Palpatin");

        Gson gson = new Gson();
        String json = gson.toJson(overlord);

        mvc.perform(post("/API/newOverlord")
                .content(json)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createNewPlanet() throws Exception {
        Planet planet = new Planet("Nabula");

        given(galacticApiController.createNewPlanet(planet)).willReturn("Nabula");

        Gson gson = new Gson();
        String json = gson.toJson(planet);

        mvc.perform(post("/API/newPlanet")
                .content(json)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void setPlanetForOverlord() {
        Overlord overlord = new Overlord("Palpatin", 100);
        Planet planet = new Planet("Nabula");

        given(galacticApiController.setPlanetForOverlord(planet.getPlanetID(), overlord.getOverlordID()))
                .willReturn("Palpatin took charge over Nabula");
    }

    @Test
    void destroyPlanet() throws Exception {
        Planet planet = new Planet("Nabula");

        mvc.perform(delete("/API/destroyPlanet/{planetID}", planet.getPlanetID()))
                .andExpect(status().isOk());

        planetRepo.save(planet);

        given(galacticApiController.destroyPlanet(planet.getPlanetID())).willReturn("Planet has been destroyed");
    }

    @Test
    void showSlackers() {
        Overlord overlordFirst = new Overlord("Fain", 100);
        Overlord overlordSecond = new Overlord("Ainz", 200);
        Overlord overlordThird = new Overlord("Geralt", 54);

        Planet planet = new Planet("Nabula", overlordFirst);

        overlordRepo.save(overlordFirst);
        overlordRepo.save(overlordSecond);
        overlordRepo.save(overlordThird);

        given(galacticApiController.showSlackers()).willReturn("Palpatin, Proton, Zerkin, Arfon, Palpatin, Fain, Ainz, Geralt");

        overlordRepo.delete(overlordFirst);
        overlordRepo.delete(overlordSecond);
        overlordRepo.delete(overlordThird);
    }

    @Test
    void showYoungOverlords() {
        Overlord overlordFirst = new Overlord("Fain", 100);
        Overlord overlordSecond = new Overlord("Ainz", 200);
        Overlord overlordThird = new Overlord("Geralt", 54);

        Planet planet = new Planet("Nabula", overlordFirst);

        overlordRepo.save(overlordFirst);
        overlordRepo.save(overlordSecond);
        overlordRepo.save(overlordThird);

        given(galacticApiController.showSlackers()).willReturn("Geralt, Ainz, Fain, Arfon, Zerkin, Proton, Keron, Ainz, Palpatin");

        overlordRepo.delete(overlordFirst);
        overlordRepo.delete(overlordSecond);
        overlordRepo.delete(overlordThird);
    }
}