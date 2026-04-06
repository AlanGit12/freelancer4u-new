package ch.zhaw.freelancer4u.Voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;
import ch.zhaw.freelancer4u.model.voucher.TwoForOneVoucher;
import ch.zhaw.freelancer4u.model.voucher.Voucher;

public class TwoForOneVoucherTest {

    @Test
    public void testTwoJobsDifferentTypes() {
        Voucher voucher = new TwoForOneVoucher(JobType.TEST);
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung 1",
                JobType.IMPLEMENT,
                77.0,
                "company1"
        ));

        jobs.add(new Job(
                "Job 2",
                "Beschreibung 2",
                JobType.REVIEW,
                33.0,
                "company2"
        ));

        double result = voucher.getDiscount(jobs);

        assertEquals(0.0, result);
    }

    @Test
    public void testTwoJobsSameTypeTest() {
        Voucher voucher = new TwoForOneVoucher(JobType.TEST);
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung 1",
                JobType.TEST,
                77.0,
                "company1"
        ));

        jobs.add(new Job(
                "Job 2",
                "Beschreibung 2",
                JobType.TEST,
                33.0,
                "company2"
        ));

        double result = voucher.getDiscount(jobs);

        assertEquals(55.0, result);
    }

    @Test
    public void testThreeJobsSameTypeReview() {
        Voucher voucher = new TwoForOneVoucher(JobType.REVIEW);
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung 1",
                JobType.REVIEW,
                77.0,
                "company1"
        ));

        jobs.add(new Job(
                "Job 2",
                "Beschreibung 2",
                JobType.REVIEW,
                33.0,
                "company2"
        ));

        jobs.add(new Job(
                "Job 3",
                "Beschreibung 3",
                JobType.REVIEW,
                99.0,
                "company3"
        ));

        double result = voucher.getDiscount(jobs);

        assertEquals(104.5, result);
    }

    @Test
    public void testOnlyFirstTwoJobsCount() {
        Voucher voucher = new TwoForOneVoucher(JobType.REVIEW);
        List<Job> jobs = new ArrayList<>();

        jobs.add(new Job(
                "Job 1",
                "Beschreibung 1",
                JobType.REVIEW,
                77.0,
                "company1"
        ));

        jobs.add(new Job(
                "Job 2",
                "Beschreibung 2",
                JobType.REVIEW,
                33.0,
                "company2"
        ));

        jobs.add(new Job(
                "Job 3",
                "Beschreibung 3",
                JobType.TEST,
                99.0,
                "company3"
        ));

        double result = voucher.getDiscount(jobs);

        assertEquals(55.0, result);
    }
}


