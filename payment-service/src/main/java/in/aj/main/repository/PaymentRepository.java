package in.aj.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.aj.main.entity.Payment;

public interface PaymentRepository
        extends JpaRepository<
        Payment,
        Long> {
}