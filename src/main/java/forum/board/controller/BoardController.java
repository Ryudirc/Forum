package forum.board.controller;

import forum.board.domain.Item;
import forum.board.repository.MemoryBoardRepository;
import forum.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시판 글 목록을 뿌려주는 컨트롤러
 * - 게시글 목록
 * - 게시글 상세
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/forum/freeBoard")
public class BoardController {

    private final MemoryBoardRepository memoryBoardRepository;
    private final BoardService boardService;

    /*@ModelAttribute("items")
    public List<Item> items()
    {
        List<Item> items = memoryBoardRepository.findAll();

        return items;
    }*/

    @GetMapping
    public String getFreeBoard(Model model)
    {
        List<Item> getItemList = boardService.getFreeBoard();
        model.addAttribute("items",getItemList);
        return "freeBoard";
    }

    @GetMapping("addItem")
    public String getAddItem(Model model)
    {
        Item item = new Item(); //global 영역에 static 으로 빼서 활용하는게 비용을 아낄수있을듯 하다.
        model.addAttribute("item",item) ;
        return "addItem";
    }

    @PostMapping("addItem") //글 등록이 완료되면, 작성한 게시글로 redirect 한다.
    public String addItem(@ModelAttribute Item item,Model model)
    {
        // ItemService.saveItem(item);
        // model.addAttribute();
        return "리다이렉트하자."
    }
}
