package forum.board.service;

import forum.board.controller.DTO.prodSaveForm;
import forum.board.domain.Cart;
import forum.board.domain.Products;
import forum.board.domain.UploadProdFile;
import forum.board.global.FileStore;
import forum.board.repository.MybatisProdFileRepository;
import forum.board.repository.MybatisProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final MybatisProductsRepository productsRepository;
    private final MybatisProdFileRepository prodFileRepository;
    private final FileStore fileStore;

   public List<Products> getProdAll()
   {
       return productsRepository.findAll();
   }

    public void prodAddProcess(prodSaveForm form) throws IOException {

            Products products = new Products();
            products.setProdCategory(form.getProdCategory());
            products.setProdName(form.getProdName());
            products.setProdStock(form.getProdStock());
            products.setProdPrice(form.getProdPrice());
            products.setProdDiscountPrice(form.getProdDiscountPrice());

            productsRepository.saveProd(products);

        Long prodId = products.getProdId();
        if(prodId != null) {
            if(!form.getProdImg().isEmpty()) {
                UploadProdFile uploadProdFile = prodImgProcess(prodId, form.getProdImg());
                prodFileRepository.saveProdFile(uploadProdFile);

            }
        }
    }

    //상품 썸네일 이미지 URL 을 불러오기 위한 메서드
    public String getProdImg(String fileName)
    {
        return fileStore.getProdImgDir(fileName);
    }


    public UploadProdFile prodImgProcess(Long prodId,MultipartFile multipartFile) throws IOException {

        return fileStore.storeProdImgFile(prodId, multipartFile);
    }

    public Products findProdById(Long prodId)
    {
        return productsRepository.findById(prodId);
    }

    public List<Products> findProdByCategory(String category)
    {
        return productsRepository.findByCategory(category);
    }

    public void updateProdProcess(Long prodId,prodSaveForm form) throws IOException {
        productsRepository.updateProd(prodId,form);
        if(!form.getProdImg().isEmpty()) {
            UploadProdFile changeUploadFile = prodImgProcess(prodId, form.getProdImg());
            prodFileRepository.updateImgFile(changeUploadFile,prodId);
        }
    }


    public void deleteProdProcess(Long prodId)
    {
        productsRepository.deleteProd(prodId);
    }

    // 상품상세 - 관련된상품 가져오기 메서드
    public List<Products> getRelatedProd(String category)
    {
        return productsRepository.findRelatedProdByCategory(category);
    }


    public void updateStock(List<Cart> cartList)
    {
        productsRepository.updateStock(cartList);
    }


}
