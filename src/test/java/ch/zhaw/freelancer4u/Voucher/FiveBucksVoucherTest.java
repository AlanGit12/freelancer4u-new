package ch.zhaw.freelancer4u.Voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;
import ch.zhaw.freelancer4u.model.voucher.FiveBucksVoucher;
import ch.zhaw.freelancer4u.model.voucher.Voucher;

public class FiveBucksVoucherTest {
    

    @Test
    public void testEmpty() {
        Voucher voucher = new FiveBucksVoucher();
        List<Job> jobs = new ArrayList<>();

        double discount = voucher.getDiscount(jobs);

        assertEquals(0.0, discount);
    }

    @Test
    public void testTen() {
        Voucher voucher = new FiveBucksVoucher();
        List<Job> jobs = new ArrayList<>();

        Job job = new Job(
                "Test Job",
                "Beschreibung",
                JobType.IMPLEMENT,
                10.0,
                "company1"
        );

        jobs.add(job);

        double discount = voucher.getDiscount(jobs);

        assertEquals(5.0, discount);
    }

    @Test
    public void testBelowTen() {
        Voucher voucher = new FiveBucksVoucher();
        List<Job> jobs = new ArrayList<>();

        Job job = new Job(
                "Kleiner Job",
                "Beschreibung",
                JobType.IMPLEMENT,
                9.5,
                "company1"
        );

        jobs.add(job);

        double discount = voucher.getDiscount(jobs);

        assertEquals(0.0, discount);
    }

    @Test
    public void testAboveTenWithTwoJobs() {
        Voucher voucher = new FiveBucksVoucher();
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung 1",
                JobType.IMPLEMENT,
                4.0,
                "company1"
        ));

        jobs.add(new Job(
                "Job 2",
                "Beschreibung 2",
                JobType.REVIEW,
                6.0,
                "company2"
        ));

        double discount = voucher.getDiscount(jobs);

        assertEquals(5.0, discount);
    }

    
}
