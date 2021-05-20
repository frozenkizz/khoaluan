package com.kopiko.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.Product;
import com.kopiko.model.ITop12ProductSelling;
import com.kopiko.statistic.IProductStatistic;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByCategoryCategoryId(Long categoryId);

	
	List<Product> findAllByProductNameContaining(String keyword);

	@Query(value = "select * from product as p where p.brand_id = ?1", nativeQuery = true)
	List<Product> findAllByBrandBrandId(Long brandId);

	List<Product> findByProductNameIgnoreCaseContaining(String studentName);

	@Query(value = "select * from product as p where 100-(p.sale_price/p.price)*100 >= 40", nativeQuery = true)
	List<Product> findByProductSale();

	List<Product> findAllBySalePriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

	// Search product by category id!
	@Query(value = "select * from product as p where p.category_id = ?1", nativeQuery = true)
	List<Product> searchProductByCategoryId(Long id);

	// Search product of category id by product id
	@Query(value = "select   *\r\n" + "from product as pr\r\n"
			+ "where    pr.category_id = (select p.category_id from product as p where p.product_id = ?1)  ", nativeQuery = true)
	List<Product> searchProductOfCategoryByProductId(Long id);

	// Search list product by categoryid and brandid for views product offer
	@Query(value = "select  top(12) pr.product_id, pr.brand_id, pr.category_id,  pr.price, pr.product_name, pr.sale_price, pr.date_created, pr.description, pr.status\r\n"
			+ "from product as pr, (select p.brand_id, p.category_id from product as p where p.product_id = ?1) temp1 \r\n"
			+ "where   pr.brand_id = temp1.brand_id or pr.category_id = temp1.category_id  ", nativeQuery = true)
	List<Product> searchProductByCategoryIdOrBrandId(Long id);

//	@Query(value = "select * from product where category_id = ?1", nativeQuery = true)
//	List<Product> searchProductByCategoryId(Long id);

	@Query(value = "select * from product where category_id = ?1", nativeQuery = true)
	Page<Product> searchProductByCategoryIdWithPage(Long id, Pageable pageable);

	@Query(value = "select top 30 p.product_id as productId, p.product_name as productName, case when tb1.quantity_of_product is null then 0 else tb1.quantity_of_product end as quantityOfProduct \r\n"
			+ "from(\r\n" + "select pd.product_id, count(od.quantity) as quantity_of_product\r\n"
			+ "from order_detail as od\r\n" + "join [order] as o on od.order_id = o.order_id\r\n"
			+ "join product_detail as pd on od.product_detail_id = pd.product_detail_id\r\n"
			+ "where month(o.date_created) = ?1 and year(o.date_created) = ?2\r\n" + "group by pd.product_id\r\n"
			+ ") as tb1\r\n" + "right join product as p on p.product_id = tb1.product_id", nativeQuery = true)
	List<ITop12ProductSelling> Top12ProductSelling(int month, int year);

	@Query(value = "select *\r\n" + "from product as p\r\n"
			+ "where p.status = 1 and p.date_created between ?1 and ?2", nativeQuery = true)
	List<Product> HotNewMonth(Date dateStart, Date dateEnd);

	@Query(value = "select top 10 p.product_id as ProductId, p.product_name as ProductName, count(od.quantity) as QuantityOfProduct\r\n"
			+ "from order_detail as od\r\n" + "join [order] as o on od.order_id = o.order_id\r\n"
			+ "join product_detail as pd on od.product_detail_id = pd.product_detail_id\r\n"
			+ "right join product as p on p.product_id = pd.product_id\r\n"
			+ "group by p.product_id, p.product_name", nativeQuery = true)
	List<IProductStatistic> getTop10SellingAllTime();

	@Query(value = "select top 10 p.product_id as ProductId, p.product_name as ProductName, case when tb1.quantity_of_product is null then 0 else tb1.quantity_of_product end as QuantityOfProduct\r\n"
			+ "from(\r\n" + "	select pd.product_id, count(od.quantity) as quantity_of_product\r\n"
			+ "	from order_detail as od\r\n" + "	join [order] as o on od.order_id = o.order_id\r\n"
			+ "	join product_detail as pd on od.product_detail_id = pd.product_detail_id\r\n"
			+ "	where month(o.date_created) = ?1 and year(o.date_created) = ?2\r\n" + "group by pd.product_id\r\n"
			+ ") as tb1\r\n" + "right join product as p on p.product_id = tb1.product_id", nativeQuery = true)
	List<IProductStatistic> getTop10SellingByMonthAndYear(Integer month, Integer year);
}