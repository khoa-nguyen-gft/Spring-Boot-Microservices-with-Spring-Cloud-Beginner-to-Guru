package guru.springframwork.msscbeerservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class BeerDtoTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;


    @SneakyThrows
    @Test
    void testSerializeDto() {
        BeerDto beerDto = this.getBeerDto();

        String value = objectMapper.writeValueAsString(beerDto);

        System.out.println(">>>>>>>>>>>beerDto"+ value);
    }

    @Test
    @SneakyThrows
    void testDeserialize() {
        String json = "{\"beerId\":\"639c00ff-9993-4ec1-af12-8277c85f93bb\"," +
                        "\"beerName\":\"BeerName\"," +
                        "\"beerStyle\":\"PALE_ALE\"," +
                        "\"upc\":123123123123," +
                        "\"price\":12.99," +
                        "\"createdDate\":\"2019-06-02T16:35:58.321001-04:00\"," +
                        "\"lastUpdatedDate\":\"2019-06-02T16:35:58.321872-04:00\"" +
                       "}";

        BeerDto dto = objectMapper.readValue(json, BeerDto.class);

        System.out.println(dto);

    }


}