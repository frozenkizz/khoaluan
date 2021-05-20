/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.OrderDetailEntity;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

	/**
	 * @param orderId
	 * @return
	 */
	List<OrderDetailEntity> findAllByOrderOrderId(Long orderId);

}
