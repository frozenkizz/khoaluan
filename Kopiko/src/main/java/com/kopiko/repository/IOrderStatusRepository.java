/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.OrderStatusEntity;

@Repository
public interface IOrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {

}
