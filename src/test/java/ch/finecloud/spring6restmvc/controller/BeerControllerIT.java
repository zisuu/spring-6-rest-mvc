package ch.finecloud.spring6restmvc.controller;

import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.mappers.BeerMapper;
import ch.finecloud.spring6restmvc.model.BeerDTO;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import ch.finecloud.spring6restmvc.repositories.BeerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Disabled // just for demo
    @Test
    void testUpdateBeerBadVersion() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);

        beerDTO.setBeerName("Updated Name");

        MvcResult result = mockMvc.perform(put(BeerController.BASE_URL_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isNoContent())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        beerDTO.setBeerName("Updated Name 2");

        MvcResult result2 = mockMvc.perform(put(BeerController.BASE_URL_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isNoContent())
                .andReturn();

        System.out.println(result2.getResponse().getStatus());
    }



    @Test
    void tesListBeersByStyleAndNameShowInventoryTruePage2() throws Exception {
        mockMvc.perform(get(BeerController.BASE_URL)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "true")
                        .queryParam("pageNumber", "2")
                        .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(50)))
                .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryTrue() throws Exception {
        mockMvc.perform(get(BeerController.BASE_URL)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "true")
                        .queryParam("pageSize", "800"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(310)))
                .andExpect(jsonPath("$.content.[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryFalse() throws Exception {
        mockMvc.perform(get(BeerController.BASE_URL)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "false")
                        .queryParam("pageSize", "800"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(310)))
                .andExpect(jsonPath("$.content.[0].quantityOnHand").value(IsNull.nullValue()));
    }

    @Test
    void tesListBeersByStyleAndName() throws Exception {
        mockMvc.perform(get(BeerController.BASE_URL)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("pageSize", "800"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(310)));
    }

    @Test
    void testListBeerByBeerStyle() throws Exception {
        mockMvc.perform(get(BeerController.BASE_URL)
                        .queryParam("beerStyle", BeerStyle.LAGER.name())
                        .queryParam("pageSize", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(39)));
    }

    @Test
    void testListBeerByName() throws Exception {
        mockMvc.perform(get(BeerController.BASE_URL)
                        .queryParam("beerName", "IPA")
                        .queryParam("pageSize", "400"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(336)));
    }

    @Test
    void testPatchBeerBadName() throws Exception {
        Beer testBeer = beerRepository.findAll().get(0);
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name12312312312312312322342342234234234234342341123");
        MvcResult mvcResult = mockMvc.perform(patch(BeerController.BASE_URL_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.deleteById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Beer testBeer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(testBeer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(testBeer.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        Beer testBeer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(testBeer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        beerDTO.setBeerName("Updated Beer");
        ResponseEntity responseEntity = beerController.updateById(testBeer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Beer updatedBeer = beerRepository.findById(testBeer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer");
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New TestBeer")
                .build();
        ResponseEntity responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        Beer testBeer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerById(testBeer.getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testListBeers() {
        Page<BeerDTO> beerDTOList = beerController.listBeers(null, null, false, 1, 25);
        assertThat(beerDTOList.getContent().size()).isEqualTo(25);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        Page<BeerDTO> beerDTOList = beerController.listBeers(null, null, false, 1, 25);
        assertThat(beerDTOList.getContent().size()).isEqualTo(0);
    }
}