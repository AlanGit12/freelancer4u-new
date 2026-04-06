package ch.zhaw.freelancer4u.Voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;
import ch.zhaw.freelancer4u.model.voucher.PercentageVoucher;
import ch.zhaw.freelancer4u.model.voucher.Voucher;

public class PercentageVoucherTest {
    
@Test
    public void testEmpty() {
        Voucher voucher = new PercentageVoucher(20);
        List<Job> jobs = new ArrayList<>();

        double result = voucher.getDiscount(jobs);

        assertEquals(0.0, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 20, 49, 50})
    public void testOneJobWithDifferentPercentages(int percentage) {
        Voucher voucher = new PercentageVoucher(percentage);
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung",
                JobType.IMPLEMENT,
                50.0,
                "company1"
        ));

        double result = voucher.getDiscount(jobs);
        double expected = 50.0 * percentage / 100.0;

        assertEquals(expected, result);
    }

    @Test
    public void testTwoJobsWithFortyTwoPercent() {
        Voucher voucher = new PercentageVoucher(42);
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung 1",
                JobType.IMPLEMENT,
                42.0,
                "company1"
        ));

        jobs.add(new Job(
                "Job 2",
                "Beschreibung 2",
                JobType.REVIEW,
                77.0,
                "company2"
        ));

        double result = voucher.getDiscount(jobs);

        assertEquals(49.98, result, 0.0001);
    }
}
