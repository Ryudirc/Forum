package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.ProdSaveForm;
import forum.board.domain.Cart;
import forum.board.domain.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductsMapper {

    void saveProd(Products prod);

    void updateProd(@Param("id") Long prodId, @Param("prodSaveForm") ProdSaveForm form);

    void updateStock(List<Cart> cartList);

    void deleteProd(Long prodId);

    Products findById(Long prodId);

    Products findByProdName(String prodName);

    List<Products> findByCategory(String prodCategory);

    List<Products> findRelatedProdByCategory(String prodCategory);

    List<Products> findAll();



}
