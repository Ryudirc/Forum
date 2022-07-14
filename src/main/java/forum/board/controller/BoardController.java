package forum.board.controller;

import forum.board.controller.DTO.ItemForm;
import forum.board.domain.Item;
import forum.board.domain.UploadFile;
import forum.board.global.FileStore;
import forum.board.repository.MemoryBoardRepository;
import forum.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

/**
 * 게시판 글 목록을 뿌려주는 컨트롤러
 * - 게시글 목록
 * - 게시글 상세
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/forum/freeBoard")
public class BoardController {

    private final MemoryBoardRepository memoryBoardRepository;
    private final BoardService boardService;
    private final FileStore fileStore;

    @GetMapping
    public String getFreeBoard(Model model)
    {
        List<Item> getItemList = boardService.getFreeBoard();
        model.addAttribute("items",getItemList);
        return "freeBoard";
    }

    @GetMapping("addForm")
    public String addItem(@ModelAttribute ItemForm form)
    {
        return "addForm";
    }

    @PostMapping("addForm") //글 등록이 완료되면, 작성한 게시글로 redirect 한다.
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> imageFiles = fileStore.storeFiles(form.getAttachFiles());

        //데이터베이스에 저장
        Item item = new Item();
        item.setTitle(form.getTitle());
        item.setContent(form.getContent());
        item.setAttachFile(attachFile);
        item.setImageFiles(imageFiles);
        memoryBoardRepository.save(item);

        redirectAttributes.addAttribute("itemId",item.getItemId());
        return "redirect:/forum/freeBoard/viewForm/{itemId}";
    }

    @GetMapping("viewForm/{itemId}") // 게시글 상세보기
    public String viewForm(@PathVariable Long itemId, Model model)
    {
        Item item = memoryBoardRepository.findById(itemId);
        model.addAttribute("item",item);

        return "viewForm";
    }


    @GetMapping("editForm/{itemId}") // 게시글 상세보기 페이지의 "게시글 수정" 버튼을 누르면 이 링크로 요청이 온다.
    public String editForm(@PathVariable Long itemId, Model model)
    {
        Item findItem = memoryBoardRepository.findById(itemId);
        model.addAttribute("item",findItem);

        return "editForm"; //editForm 에서 "수정완료" 버튼을 누르면 아래의 url 로 이동한다.
    }

    @PostMapping("editForm/{itemId}") //게시글이 수정되면, 수정된 게시글 상세보기 페이지로 이동
    public String saveEditForm(@PathVariable Long itemId, @ModelAttribute ItemForm form)
    {
        Item oldItem = memoryBoardRepository.findById(itemId);
        oldItem.setTitle(form.getTitle());
        oldItem.setContent(form.getContent());
        // 첨부파일 바꾸기는 추후에 생각해봅시다.

        return "redirect:/viewForm/{itemId}";
    }

    @GetMapping("viewForm/delete/{itemId}") // 게시글 상세보기 페이지의 "게시글 삭제" 버튼을 누르면 이 링크로 요청이 온다.
    public String removeItem(@PathVariable Long itemId)
    {
        memoryBoardRepository.delete(itemId);

        return "redirect:/forum/freeBoard";
    }


}
