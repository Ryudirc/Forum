package forum.board.controller;

import forum.board.controller.DTO.ItemSaveForm;
import forum.board.controller.DTO.ItemUpdateForm;
import forum.board.domain.Item;
import forum.board.domain.UploadFile;
import forum.board.domain.loginMember;
import forum.board.global.CategoryType;
import forum.board.global.SessionConst;
import forum.board.global.pagination;
import forum.board.service.ItemService;
import forum.board.service.paginationService;
import forum.board.validation.ItemSaveValidator;
import forum.board.validation.ItemUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
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

    private final ItemSaveValidator itemSaveValidator;
    private final ItemUpdateValidator itemUpdateValidator;

    @GetMapping("/forum/freeBoard")
    public String getFreeBoard(Model model, @RequestParam(value = "page",defaultValue = "1")int page, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) loginMember loginMember)
    {
        pagination pagination = paginationService.getPagination(page);
        model.addAttribute("pagination",pagination);
        model.addAttribute("member",loginMember);
        model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
        return "freeBoard";
    }


    @GetMapping("/forum/freeBoard/addForm")
    public String addItem(@ModelAttribute ItemSaveForm form, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)loginMember loginMember, Model model)
    {
        model.addAttribute("form",form);
        model.addAttribute("member",loginMember);
        model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
        return "addForm";
    }

    @PostMapping("/forum/freeBoard/addForm") //글 등록이 완료되면, 작성한 게시글로 redirect 한다.
    public String saveItem(@Valid @ModelAttribute("form") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)loginMember loginMember,Model model) throws IOException {

       if(itemSaveValidator.supports(form.getClass()))
        {
            itemSaveValidator.validate(form,bindingResult);
        }


       if(bindingResult.hasErrors())
       {
           model.addAttribute("member",loginMember);
           for (FieldError fieldError : bindingResult.getFieldErrors()) {
               System.out.println("fieldError = " + fieldError);
           }
           return "addForm";
       }


        // 파일처리 및 아이템저장 로직은 itemService 클래스에 위임.
       Item item = itemService.formFileProcess(form,loginMember);
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
    public String viewForm(@PathVariable Long itemId, Model model,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)loginMember loginMember)
    {
        Item item = itemService.findById(itemId);


        if(loginMember.getRole().equals("USER")) {
            if (!(loginMember.getMemberName().equals(item.getWriter()))) {
                model.addAttribute("member", loginMember);
                model.addAttribute("item", item);
                model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
                return "viewFormOther";
            }
        }

        itemService.updateViewCount(item.getItemId());
        model.addAttribute("item",item);
        model.addAttribute("member",loginMember);
        model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);

        return "viewForm";
    }


    @GetMapping("/forum/freeBoard/editForm/{itemId}") // 게시글 상세보기 페이지의 "게시글 수정" 버튼을 누르면 이 링크로 요청이 온다.
    public String editForm(@PathVariable Long itemId,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) loginMember loginMember,Model model)
    {
        Item item = itemService.findById(itemId);
        model.addAttribute("form",new ItemUpdateForm(item.getItemId(),item.getTitle(),item.getContent()));
        model.addAttribute("member",loginMember);
        model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);


        return "editForm"; //editForm 에서 "수정완료" 버튼을 누르면 아래의 url 로 이동한다.
    }

    @PostMapping("/forum/freeBoard/editForm/{itemId}") //게시글이 수정되면, 수정된 게시글 상세보기 페이지로 이동
    public String saveEditForm(@PathVariable Long itemId,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) loginMember loginMember,@Valid @ModelAttribute("form") ItemUpdateForm form,BindingResult bindingResult,Model model)
    {
        if(itemUpdateValidator.supports(form.getClass()))
        {
            itemUpdateValidator.validate(form,bindingResult);
        }
        if(bindingResult.hasErrors()){

            model.addAttribute("member",loginMember);
            model.addAttribute("form",form);
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                System.out.println("fieldError = " + fieldError);
            }
            return "editForm";
        }

        itemService.updateItem(itemId,form);
        return "redirect:/forum/freeBoard/viewForm/{itemId}";
    }

    @GetMapping("/forum/freeBoard/viewForm/delete/{itemId}") // 게시글 상세보기 페이지의 "게시글 삭제" 버튼을 누르면 이 링크로 요청이 온다.
    public String removeItem(@PathVariable Long itemId)
    {
        itemService.deleteItem(itemId);

        return "redirect:/forum/freeBoard";
    }

    @GetMapping("/forum/freeBoard/search")
    public String searchItem(@RequestParam("type") int type,@RequestParam("keyword") String keyword,@RequestParam(value = "page",defaultValue = "1")int page,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) loginMember loginMember ,Model model)
    {
        model.addAttribute("type",type);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        pagination pagination = paginationService.searchedPagination(type, keyword, page);
        model.addAttribute("pagination",pagination);
        model.addAttribute("member",loginMember);
        model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);

        return "searchResult";
    }




}
