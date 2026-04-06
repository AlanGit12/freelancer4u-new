package ch.zhaw.freelancer4u.model.voucher;

import java.util.List;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;
import lombok.RequiredArgsConstructor;

public class TwoForOneVoucher implements Voucher{
    
   private JobType jobType;

    public TwoForOneVoucher(JobType jobType) {
        this.jobType = jobType;
    }

    @Override
    public double getDiscount(List<Job> jobs) {
        List<Job> matchingJobs = jobs.stream()
                .filter(job -> job.getJobType() == jobType)
                .toList();

        if (matchingJobs.size() < 2) {
            return 0.0;
        }

        double sum = matchingJobs.stream()
                .mapToDouble(Job::getEarnings)
                .sum();

        return sum / 2.0;
    }
}

