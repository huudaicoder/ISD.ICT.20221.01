package test;

import controllers.RentBikeController;
import entities.NormalBike;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Configs;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(value = Parameterized.class)
public class CheckHaveToPayTest {
    private controllers.RentBikeController rentBikeServiceController;

    @Before
    public void setUp() throws Exception {
        rentBikeServiceController = new RentBikeController();
    }

    @Parameterized.Parameter(value = 0)
    public boolean expected;

    @Parameterized.Parameter(value = 1)
    public entities.NormalBike bike;

    @Parameterized.Parameters(name = "{index}: the user of bike{index} has to pay is {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {false, new NormalBike(0, 100000, Configs.BikeType.STANDARDBIKE)},
                {false, new NormalBike(0, 150000, Configs.BikeType.STANDARDEBIKE)},
                {false, new NormalBike(0, 150000, Configs.BikeType.TWINBIKE)},
                {true, new NormalBike(11, 100000, Configs.BikeType.STANDARDBIKE)},
                {false, new NormalBike(10, 150000, Configs.BikeType.STANDARDEBIKE)},
                {true, new NormalBike(30, 150000, Configs.BikeType.TWINBIKE)},
        });
    }

    @Test
    public void test() {
        assertEquals(expected, rentBikeServiceController.checkHaveToPayOrNot(bike));
    }
}
