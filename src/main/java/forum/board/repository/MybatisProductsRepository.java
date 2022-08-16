package forum.board.repository;

import forum.board.controller.DTO.prodSaveForm;
import forum.board.domain.Products;
import forum.board.repository.mybatisMapper.productsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisProductsRepository {

    private final productsMapper productsMapper;

    public void saveProd(Products prod)
    {
        productsMapper.saveProd(prod);
    }

    public void updateProd(Long prodId, prodSaveForm form)
    {
        productsMapper.updateProd(prodId,form);
    }

    public void deleteProd(Long prodId)
    {
        productsMapper.deleteProd(prodId);
    }

    public Products findById(Long prodId)
    {
        return productsMapper.findById(prodId);
    }

    public Products findByProdName(String prodName)
    {
        return productsMapper.findByProdName(prodName);
    }

    public List<Products> findByCategory(String prodCategory)
    {
        return productsMapper.findByCategory(prodCategory);
    }

    public List<Products> findRelatedProdByCategory(String prodCategory) {
        return productsMapper.findRelatedProdByCategory(prodCategory);
    }

    public List<Products> findAll()
    {
        return productsMapper.findAll();
    }

}
