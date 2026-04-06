package ch.zhaw.freelancer4u.model.voucher;

import java.util.List;

import ch.zhaw.freelancer4u.model.Job;

public class PercentageVoucher implements Voucher{
    
    private int discount;

    public PercentageVoucher(int discount) {
        this.discount = discount;
    }

    @Override
    public double getDiscount(List<Job> jobs) {
        double sum = jobs.stream()
                .mapToDouble(Job::getEarnings)
                .sum();

        return sum * discount / 100.0;
    }
}
