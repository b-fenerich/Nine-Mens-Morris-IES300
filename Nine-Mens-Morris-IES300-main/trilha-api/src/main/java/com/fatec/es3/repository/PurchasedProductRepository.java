package com.fatec.es3.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.es3.model.PurchasedProduct;

@Repository
public interface PurchasedProductRepository extends JpaRepository<PurchasedProduct, Long> {

	@Query("SELECT u FROM PurchasedProduct u WHERE u.userId = :userId AND u.productId = :productId")
	public PurchasedProduct getPurchasedProductByUserAndProductId(@Param("userId") long userId,
			@Param("productId") long productId);

	@Query("SELECT u FROM PurchasedProduct u WHERE u.userId = :userId")
	public ArrayList<PurchasedProduct> getPurchasedProductByUser(@Param("userId") long userId);

	@Query("SELECT u FROM PurchasedProduct u WHERE u.userId = :userId AND u.active = true")
	public ArrayList<PurchasedProduct> getPurchasedProductActiveByUser(@Param("userId") long userId);
}
