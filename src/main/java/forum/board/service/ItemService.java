package forum.board.service;

import forum.board.controller.DTO.ItemForm;
import forum.board.domain.Item;
import forum.board.domain.UploadFile;
import forum.board.global.FileStore;
import forum.board.repository.MybatisFileRepository;
import forum.board.repository.MybatisItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return itemRepository.findById(itemId);
    }

    public void deleteItem(Long itemId)
    {
        itemRepository.delete(itemId);
    }

    public Item formFileProcess(ItemForm form) throws IOException {

        //데이터베이스에 저장
        Item item = new Item();
        item.setTitle(form.getTitle());
        item.setContent(form.getContent());
        item.setWriter("RMA전문가");
        item.setViewCnt(0);
        item.setGoodCnt(0);
        itemRepository.save(item);


        //multipartFile 로 넘어온 데이터 처리 로직 작성(만약 첨부파일이 넘어오지 않으면, DB에 데이터를 넣지 않음)
        Long itemId = item.getItemId();
        if(!(form.getAttachFile().isEmpty())) {
            UploadFile attachFile = fileStore.storeFile(itemId, form.getAttachFile());
            fileRepository.saveFile(attachFile);
        }
        if(form.getAttachFiles().stream().count()>1){
            List<UploadFile> imageFiles = fileStore.storeFiles(itemId, form.getAttachFiles());
            fileRepository.saveFiles(imageFiles);
        }

        return item;
    }

    public void updateItem(Long itemId, ItemForm updateItem)
    {
        Item findItem = itemRepository.findById(itemId);
        findItem.setTitle(updateItem.getTitle());
        findItem.setContent(updateItem.getContent());

        itemRepository.update(itemId,findItem);
    }


    public List<Item> searchProcess(int type, String keyword)
    {
        switch (type) {
            case 1:
               return searchByTitle(keyword);
            case 2:
                return searchByWriter(keyword);
            case 3:
                return searchByKeyword(keyword);
        }
        return null;
    }



    public List<Item> searchByTitle(String keyword)
    {
        return itemRepository.findByTitle(keyword);
    }

    public List<Item> searchByWriter(String keyword)
    {
        return itemRepository.findByWriter(keyword);
    }

    public List<Item> searchByKeyword(String keyword)
    {
        return itemRepository.findByKeyword(keyword);
    }

    /*public JSONObject summernoteImageProcess(MultipartFile multipartFile)
    {
        // 컨트롤러로부터 multipartFile 을 주입받아서 FileStore 클래스의 메서드를 사용하여 summernote API 의 이미지를 처리
    }*/

}
