package forum.board.service;


import forum.board.controller.DTO.ItemSaveForm;
import forum.board.controller.DTO.ItemUpdateForm;
import forum.board.domain.Item;
import forum.board.domain.UploadFile;
import forum.board.domain.loginMember;
import forum.board.global.FileStore;
import forum.board.repository.MybatisFileRepository;
import forum.board.repository.MybatisItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final MybatisItemRepository itemRepository;
    private final MybatisFileRepository fileRepository;
    private final FileStore fileStore;

    public List<Item> getFreeBoard()
    {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId)
    {
        Item item = itemRepository.findById(itemId);
        List<UploadFile> uploadFileList = fileRepository.findFileById(itemId);
        item.setAttachFiles(uploadFileList);

        return item;
    }

    public void deleteItem(Long itemId)
    {
        itemRepository.delete(itemId);
    }

    public Item formFileProcess(ItemSaveForm form, loginMember member) throws IOException {

        //데이터베이스에 저장
        Item item = new Item();
        item.setTitle(form.getTitle());
        item.setContent(form.getContent());
        item.setWriter(member.getMemberName());
        item.setViewCnt(0);
        item.setGoodCnt(0);
        itemRepository.save(item);


        //multipartFile 로 넘어온 데이터 처리 로직 작성(만약 첨부파일이 넘어오지 않으면, DB에 데이터를 넣지 않음)
        Long itemId = item.getItemId();
        if(form.getAttachFiles().stream().count()>= 1){
            if(!form.getAttachFiles().iterator().next().getOriginalFilename().isEmpty()) {
                List<UploadFile> attachFiles = fileStore.storeFiles(itemId, form.getAttachFiles());
                fileRepository.saveFiles(attachFiles);
                item.setAttachFiles(attachFiles);
            }
        }
        return item;
    }

    public String getAttachFilePath(String attachFileName)
    {
         return fileStore.getFullPath(attachFileName);
    }

    public ResponseEntity summernoteImageProcess(MultipartFile multipartFile) throws IOException{

        String imgPath = fileStore.storeImgFile(multipartFile);
        return ResponseEntity.ok("/summernoteImage/"+imgPath);
    }

    public String getSummernoteImgPath(String filename)
    {
        return fileStore.getImgFullPath(filename);
    }


    public void updateItem(Long itemId, ItemUpdateForm updateItem)
    {
        Item findItem = itemRepository.findById(itemId);
        findItem.setTitle(updateItem.getTitle());
        findItem.setContent(updateItem.getContent());

        itemRepository.update(itemId,findItem);
    }


    public List<Item> searchProcess(int type, String keyword,int start,int pageSize)
    {
        switch (type) {
            case 1:
               return searchByTitle(keyword,start,pageSize);
            case 2:
                return searchByWriter(keyword,start,pageSize);
            case 3:
                return searchByKeyword(keyword,start,pageSize);
        }
        return null;
    }



    public List<Item> searchByTitle(String keyword,int start,int pageSize)
    {
        return itemRepository.findByTitle(keyword,start,pageSize);
    }

    public List<Item> searchByWriter(String keyword,int start,int pageSize)
    {
        return itemRepository.findByWriter(keyword,start,pageSize);
    }

    public List<Item> searchByKeyword(String keyword,int start,int pageSize)
    {
        return itemRepository.findByKeyword(keyword,start,pageSize);
    }



    public List<Item> searchAll(int type, String keyword)
    {
        switch (type) {
            case 1:
                return searchByTitleAll(keyword);
            case 2:
                return searchByWriterAll(keyword);
            case 3:
                return searchByKeywordAll(keyword);
        }
        return null;
    }



    public List<Item> searchByTitleAll(String keyword)
    {
        return itemRepository.findByTitleAll(keyword);
    }

    public List<Item> searchByWriterAll(String keyword)
    {
        return itemRepository.findByWriterAll(keyword);
    }

    public List<Item> searchByKeywordAll(String keyword)
    {
        return itemRepository.findByKeywordAll(keyword);
    }



    public void updateViewCount(Long itemId)
    {
        itemRepository.updateViewCount(itemId);
    }


}
