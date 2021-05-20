package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.Invoice;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
	Invoice findByInvoiceId(Long invoiceId);
	List<Invoice> findAllByOrderOrderId(Long OrderID);
}