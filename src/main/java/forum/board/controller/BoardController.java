package forum.board.controller;

import forum.board.controller.DTO.ItemForm;
import forum.board.domain.Item;
import forum.board.domain.UploadFile;
import forum.board.global.pagination;
import forum.board.service.ItemService;
import forum.board.service.paginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 게시판 글 목록을 뿌려주는 컨트롤러
 * - 게시글 목록
 * - 게시글 상세
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class BoardController {

    private final ItemService itemService;
    private final paginationService paginationService;

    @GetMapping("/forum/freeBoard")
    public String getFreeBoard(Model model,@RequestParam(value = "page",defaultValue = "1")int page)
    {
        pagination pagination = paginationService.getPagination(page);
        model.addAttribute("pagination",pagination);

        return "freeBoard";
    }


    @GetMapping("/forum/freeBoard/addForm")
    public String addItem(@ModelAttribute ItemForm form)
    {
        return "addForm";
    }

    @PostMapping("/forum/freeBoard/addForm") //글 등록이 완료되면, 작성한 게시글로 redirect 한다.
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {

       // 파일처리 및 아이템저장 로직은 itemService 클래스에 위임.
       Item item = itemService.formFileProcess(form);

        redirectAttributes.addAttribute("itemId",item.getItemId());
        return "redirect:/forum/freeBoard/viewForm/{itemId}";
    }

    @PostMapping(value = "/forum/freeBoard/uploadSummernoteImageFile")
    @ResponseBody
    public ResponseEntity uploadSummernoteImage(@RequestParam("file") MultipartFile file) throws IOException {
        return itemService.summernoteImageProcess(file);

    }

    @GetMapping("summernoteImage/{filename}")
    @ResponseBody
    public Resource showSummernoteImg(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + itemService.getSummernoteImgPath(filename));
    }

    @GetMapping("attach/{id}")
    public ResponseEntity<Resource> downloadAttachFile(@PathVariable("id") Long itemId, @RequestParam("filename") String filename) throws MalformedURLException {

        Item item = itemService.findById(itemId);
        String originalFileName = null;
        String fullPath = null;
        for (UploadFile attachFile : item.getAttachFiles()) {
            if(filename.equals(attachFile.getUploadFileName()))
            {
                originalFileName = attachFile.getUploadFileName();
                fullPath = itemService.getAttachFilePath(attachFile.getStoreFileName());
            }
        }
        UrlResource urlResource = new UrlResource("file:" + fullPath);
        String contentDisposition = "attachment; filename=\"" + originalFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }


    @GetMapping("/forum/freeBoard/viewForm/{itemId}") // 게시글 상세보기
    public String viewForm(@PathVariable Long itemId, Model model)
    {
        Item item = itemService.findById(itemId);
        itemService.updateViewCount(item.getItemId());
        model.addAttribute("item",item);

        return "viewForm";
    }


    @GetMapping("/forum/freeBoard/editForm/{itemId}") // 게시글 상세보기 페이지의 "게시글 수정" 버튼을 누르면 이 링크로 요청이 온다.
    public String editForm(@PathVariable Long itemId, Model model)
    {
        Item findItem = itemService.findById(itemId);
        model.addAttribute("item",findItem);

        return "editForm"; //editForm 에서 "수정완료" 버튼을 누르면 아래의 url 로 이동한다.
    }

    @PostMapping("/forum/freeBoard/editForm/{itemId}") //게시글이 수정되면, 수정된 게시글 상세보기 페이지로 이동
    public String saveEditForm(@PathVariable Long itemId, @ModelAttribute ItemForm form)
    {
        itemService.updateItem(itemId,form);
        // 첨부파일 바꾸기는 추후에 생각해봅시다.

        return "redirect:/forum/freeBoard/viewForm/{itemId}";
    }

    @GetMapping("/forum/freeBoard/viewForm/delete/{itemId}") // 게시글 상세보기 페이지의 "게시글 삭제" 버튼을 누르면 이 링크로 요청이 온다.
    public String removeItem(@PathVariable Long itemId)
    {
        itemService.deleteItem(itemId);

        return "redirect:/forum/freeBoard";
    }

    @GetMapping("/forum/freeBoard/search")
    public String searchItem(@RequestParam("type") int type,@RequestParam("keyword") String keyword,@RequestParam(value = "page",defaultValue = "1")int page, Model model)
    {
        model.addAttribute("type",type);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        pagination pagination = paginationService.searchedPagination(type, keyword, page);
        model.addAttribute("pagination",pagination);

        return "searchResult";
    }




}
