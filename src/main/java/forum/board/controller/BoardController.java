package forum.board.controller;

import forum.board.controller.DTO.ItemForm;
import forum.board.domain.Item;
import forum.board.service.ItemService;
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

    private final ItemService itemService;

    @GetMapping
    public String getFreeBoard(Model model)
    {
        List<Item> getItemList = itemService.getFreeBoard();
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

       // 파일처리 및 아이템저장 로직은 itemService 클래스에 위임.
       Item item = itemService.formFileProcess(form);

        redirectAttributes.addAttribute("itemId",item.getItemId());
        return "redirect:/forum/freeBoard/viewForm/{itemId}";
    }

   /* @PostMapping(value = "uploadSummernoteImageFile",produces = "application/json")
    @ResponseBody
    public JSONObject uploadSummernoteImage(@RequestParam("file") MultipartFile multipartFile)
    {
        // return fileHandleProcessor.summernoteImageProcess(MultipartFile multipartFile);
    }*/

    @GetMapping("viewForm/{itemId}") // 게시글 상세보기
    public String viewForm(@PathVariable Long itemId, Model model)
    {
        Item item = itemService.findById(itemId);
        model.addAttribute("item",item);

        return "viewForm";
    }


    @GetMapping("editForm/{itemId}") // 게시글 상세보기 페이지의 "게시글 수정" 버튼을 누르면 이 링크로 요청이 온다.
    public String editForm(@PathVariable Long itemId, Model model)
    {
        Item findItem = itemService.findById(itemId);
        model.addAttribute("item",findItem);

        return "editForm"; //editForm 에서 "수정완료" 버튼을 누르면 아래의 url 로 이동한다.
    }

    @PostMapping("editForm/{itemId}") //게시글이 수정되면, 수정된 게시글 상세보기 페이지로 이동
    public String saveEditForm(@PathVariable Long itemId, @ModelAttribute ItemForm form)
    {
        itemService.updateItem(itemId,form);
        // 첨부파일 바꾸기는 추후에 생각해봅시다.

        return "redirect:/forum/freeBoard/viewForm/{itemId}";
    }

    @GetMapping("viewForm/delete/{itemId}") // 게시글 상세보기 페이지의 "게시글 삭제" 버튼을 누르면 이 링크로 요청이 온다.
    public String removeItem(@PathVariable Long itemId)
    {
        itemService.deleteItem(itemId);

        return "redirect:/forum/freeBoard";
    }

    @GetMapping("search")
    public String searchItem(int type,String keyword,Model model)
    {
        model.addAttribute("type",type);
        model.addAttribute("keyword",keyword);
        List<Item> searchResult = itemService.searchProcess(type, keyword);
        model.addAttribute("items",searchResult);

        return "searchResult";
    }




}
