package guru.springframwork.msscbeerservice.web.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("snake")
@JsonTest
public class BeerDtoSnakeTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testSnake() throws Exception {
        BeerDto beerDto = getBeerDto();
        String s = objectMapper.writeValueAsString(beerDto);
        System.out.println(">>>>>>>>>>>> beerDto" + s);
    }
}
