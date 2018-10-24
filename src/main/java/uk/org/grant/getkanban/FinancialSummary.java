package uk.org.grant.getkanban;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class FinancialSummary {
    private AtomicIntegerArray newSubscribers = new AtomicIntegerArray(22);
    private boolean f1 = true;
    private boolean e1 = false;
    private final Column deployed;

    public FinancialSummary(Column deployed) {
        this.deployed = deployed;
        init();
    }

    private void init() {
        for (Card card : deployed.getCards()) {
            if (card.getName().equals("F1")) {
                if (card.getDayDeployed() <= 15) {
                    this.f1 = false;
                }
            }
            if (card.getName().equals("E1")) {
                if (card.getDayDeployed() <= 18) {
                    this.e1 = true;
                }
            }
            if (card.getName().equals("F2")) {
                newSubscribers.getAndAdd(21, 30);
            }
            if (card.getDayDeployed() <= 9) {
                newSubscribers.getAndAdd(9, card.getSubscribers());
            } else if (card.getDayDeployed() <= 12) {
                newSubscribers.getAndAdd(12, card.getSubscribers());
            } else if (card.getDayDeployed() <= 15) {
                newSubscribers.getAndAdd(15, card.getSubscribers());
            } else if (card.getDayDeployed() <= 18) {
                newSubscribers.getAndAdd(18, card.getSubscribers());
            } else {
                newSubscribers.getAndAdd(21, card.getSubscribers());
            }
        }
    }

    public int getNewSubscribers(int billingCycle) {
        return newSubscribers.get(billingCycle);
    }

    public int getTotalSubscribersToDate(int billingCycle) {
        if (billingCycle == 9) {
            return newSubscribers.get(billingCycle);
        } else {
            return newSubscribers.get(billingCycle) + getTotalSubscribersToDate(billingCycle - 3);
        }
    }

    public int getBillingCycleRevenue(int billingCycle) {
        int multiplier = 10 + ((billingCycle - 9) / 3) * 5;
        return getTotalSubscribersToDate(billingCycle) * multiplier;
    }

    public int getBillingCycleGrossProfit(int billingCycle) {
        return getBillingCycleRevenue(billingCycle) + getFinesOrPayments(billingCycle);
    }

    public int getTotalGrossProfitToDate(int billingCycle) {
        if (billingCycle == 9) {
            return getBillingCycleGrossProfit(billingCycle);
        } else {
            return getTotalGrossProfitToDate(billingCycle - 3) + getBillingCycleGrossProfit(billingCycle);
        }
    }

    public int getFinesOrPayments(int billingCycle) {
        if (billingCycle == 15 && this.f1) {
            return -1500;
        } else if (billingCycle == 18 && this.e1) {
            return 4000;
        } else {
            return 0;
        }
    }
}