import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import edu.neu.cs4500.models.*;

public class SubscriptionEstimateDiscountTest {

    private Estimate estimate1 = new Estimate();
    private List<SubscriptionDiscount> discountList = new ArrayList<SubscriptionDiscount>();

    private SubscriptionDiscount discount0_amt = new SubscriptionDiscount();
    private SubscriptionDiscount discount0_pct = new SubscriptionDiscount();
    private SubscriptionDiscount discount1 = new SubscriptionDiscount();
    private SubscriptionDiscount discount2 = new SubscriptionDiscount();
    private SubscriptionDiscount discount3 = new SubscriptionDiscount();

    @BeforeEach
    public void init() {
        estimate1.setBaseprice(200.00f);
        estimate1.setBaseFrequency(Frequency.MONTHLY);
        discountList.clear();
    }

    // Tests to verify 0-value discounts does nothing
    @Test
    public void testZeroAmtDiscount() {
        discount0_amt.setDiscount(0.0f);
        discount0_amt.setFlat(true);
        discount0_amt.setFrequency(Frequency.MONTHLY);

        discountList.add(discount0_amt);

        assertEquals(0.0f, estimate1.getDiscount(discountList));
    }
}
