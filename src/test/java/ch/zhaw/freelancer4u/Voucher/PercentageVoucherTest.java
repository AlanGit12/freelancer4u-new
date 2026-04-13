package ch.zhaw.freelancer4u.Voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    @ValueSource(ints = { 1, 2, 5, 20, 49, 50 })
    public void testOneJobWithDifferentPercentages(int percentage) {
        Voucher voucher = new PercentageVoucher(percentage);
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung",
                JobType.IMPLEMENT,
                50.0,
                "company1"));

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
                "company1"));

        jobs.add(new Job(
                "Job 2",
                "Beschreibung 2",
                JobType.REVIEW,
                77.0,
                "company2"));

        double result = voucher.getDiscount(jobs);

        assertEquals(49.98, result, 0.0001);
    }

    @Test
    public void testTwoJobsDifferentTypesMock() {
        Voucher voucher = new PercentageVoucher(42);

        List<Job> jobs = new ArrayList<>();

        Job job1 = mock(Job.class);
        Job job2 = mock(Job.class);

        when(job1.getEarnings()).thenReturn(42.0);
        when(job2.getEarnings()).thenReturn(77.0);

        jobs.add(job1);
        jobs.add(job2);

        double result = voucher.getDiscount(jobs);

        assertEquals(49.98, result, 0.0001);

    }

    @Test
    public void TestAboveFiftyP() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(51);
        });
        assertEquals("Error: Discount value must less or equal 50.", exception.getMessage());
    }

    @Test
    void TestUnderZeroP() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(-1);
        });
        assertEquals("Error: Discount value must be greater zero", exception.getMessage());
    }

    @Test
    void TestZeroP() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(0);
        });
        assertEquals("Error: Discount value must be greater zero", exception.getMessage());
    }

}
