/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.OrderEntity;
import com.kopiko.statistic.IOrderStatistic;
import com.kopiko.statistic.IRevenueStatistic;

@Repository
public interface IOrderRepository  extends JpaRepository<OrderEntity, Long>{
	List<OrderEntity> findAllByOrderStatusOrderStatusId(Long statusId);

	/**
	 * @param accountId
	 * @return
	 */
	List<OrderEntity> findAllByAccountAccountId(Long accountId);

	/**
	 * @param username
	 * @return
	 */
	
	@Query(value="select o.* from [order] as o join account as a on o.account_id = a.account_id where a.username = ?1", nativeQuery = true)
	List<OrderEntity> findAllByUsername(String username);

	/**
	 * @param statusId
	 * @return
	 */
	OrderEntity findByOrderIdAndAccountAccountId(Long orderId, Long accountId);
	
	@Query(value="select m.month as Month, count(o.order_id) as QuantityOfOrder \r\n"
			+ "from temp_month as m \r\n"
			+ "left join (\r\n"
			+ "	select * from [order] as o\r\n"
			+ "	where year(o.date_created) = ?1\r\n"
			+ ") as o on m.month = MONTH(o.date_created)\r\n"
			+ "group by m.month", nativeQuery = true)
	List<IOrderStatistic> getListQuantityOfOrderByYear(Integer year);
	
	@Query(value = "select m.month as Month, \r\n"
			+ "case when total_sale_price is null then 0 else total_sale_price end as TotalOfSalePrice\r\n"
			+ "from temp_month as m \r\n"
			+ "left join (\r\n"
			+ "	select MONTH(o.date_created) as month, sum(od.quantity*od.sale_price) as total_sale_price \r\n"
			+ "	from [order] as o\r\n"
			+ "	join order_detail as od on o.order_id = od.order_id\r\n"
			+ "	where year(o.date_created) = ?1 and o.order_status_id = 4\r\n"
			+ "	group by MONTH(o.date_created)\r\n"
			+ ") as o on m.month = o.month", nativeQuery = true)
	List<IRevenueStatistic> getListRevenueOfOrderByYear(Integer year);
}
