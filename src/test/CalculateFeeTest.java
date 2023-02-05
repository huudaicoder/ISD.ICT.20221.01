package test;

import controllers.RentBikeServiceController;
import entities.Bike;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Configs;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(value = Parameterized.class)
public class CalculateFeeTest {
    private controllers.RentBikeServiceController rentBikeServiceController;

    @Before
    public void setUp() throws Exception {
        rentBikeServiceController = new RentBikeServiceController();
    }

    @Parameterized.Parameter(value = 0)
    public int expected;

    @Parameterized.Parameter(value = 1)
    public entities.Bike bike;

    @Parameterized.Parameters(name = "{index}: the fee of bike{index} is {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {100000, new Bike(0, 100000, Configs.BikeType.STANDARDBIKE)},
                {150000, new Bike(0, 150000, Configs.BikeType.STANDARDEBIKE)},
                {150000, new Bike(0, 150000, Configs.BikeType.TWINBIKE)},
                {110000, new Bike(11, 100000, Configs.BikeType.STANDARDBIKE)},
                {150000, new Bike(10, 150000, Configs.BikeType.STANDARDEBIKE)},
                {174000, new Bike(55, 150000, Configs.BikeType.TWINBIKE)},
                {174000, new Bike(60, 150000, Configs.BikeType.TWINBIKE)},
        });
    }

    @Test
    public void test() {
        assertEquals(expected, rentBikeServiceController.calculateFee(bike));
    }
}
