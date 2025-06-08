package service;

import model.LendingRecord;

import java.time.LocalDate;
import java.util.List;

public class OverdueMonitor implements Runnable {
    private final List<LendingRecord> lendingRecords;

    public OverdueMonitor(List<LendingRecord> lendingRecords) {
        this.lendingRecords = lendingRecords;
    }

    @Override
    public void run() {
        while (true) {
            lendingRecords.stream()
                    .filter(r -> r.getReturnDate() == null && LocalDate.now().isAfter(r.getDueDate()))
                    .forEach(r -> {
                        System.out.println("Overdue: " + r.getBook().getTitle() +
                                " (Member: " + r.getMember().getName() + ", Due: " + r.getDueDate() + ")");
                    });

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
