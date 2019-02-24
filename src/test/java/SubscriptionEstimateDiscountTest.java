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
        estimate1.setSubscriptionFrequency(Frequency.MONTHLY);
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

    @Test
    public void testZeroPctDiscount() {
        discount0_pct.setDiscount(0.0f);
        discount0_pct.setFlat(false);
        discount0_pct.setFrequency(Frequency.MONTHLY);

        discountList.add(discount0_pct);

        assertEquals(0.0f, estimate1.getDiscount(discountList));
    }

    // Testing matching and nonmatching subscription frequencies
    @Test
    public void testMatchingSubscriptionFrequency() {
        discount1.setDiscount(10.0f);
        discount1.setFlat(true);
        discount1.setFrequency(Frequency.MONTHLY);

        discountList.add(discount1);

        assertEquals(10.0f, estimate1.getDiscount(discountList));
    }

    @Test
    public void testNonMatchingSubscriptionFrequency() {
        discount2.setDiscount(10.0f);
        discount2.setFlat(true);
        discount2.setFrequency(Frequency.DAILY);

        discountList.add(discount2);

        assertEquals(0.0f, estimate1.getDiscount(discountList));
    }

}
