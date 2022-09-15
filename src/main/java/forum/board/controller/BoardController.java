package forum.board.controller;

import forum.board.controller.DTO.ItemSaveForm;
import forum.board.controller.DTO.ItemUpdateForm;
import forum.board.domain.Item;
import forum.board.domain.UploadFile;
import forum.board.domain.LoginMember;
import forum.board.global.CategoryType;
import forum.board.global.SessionConst;
import forum.board.global.pagination;
import forum.board.service.ItemService;
import forum.board.service.PaginationService;
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


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.http.HttpResponse;

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
    private final PaginationService paginationService;

    private final ItemSaveValidator itemSaveValidator;
    private final ItemUpdateValidator itemUpdateValidator;

    @GetMapping("/forum/freeBoard")
    public String getFreeBoard(Model model, @RequestParam(value = "page",defaultValue = "1")int page, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember)
    {
        pagination pagination = paginationService.getPagination(page);
        model.addAttribute("pagination",pagination);
        model.addAttribute("member",loginMember);
        model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
        return "freeBoard";
    }


    @GetMapping("/forum/freeBoard/addForm")
    public String addItem(@ModelAttribute ItemSaveForm form, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember, Model model)
    {
        model.addAttribute("form",form);
        model.addAttribute("member",loginMember);
        model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
        return "addForm";
    }

    @PostMapping("/forum/freeBoard/addForm") //글 등록이 완료되면, 작성한 게시글로 redirect 한다.
    public String saveItem(@Valid @ModelAttribute("form") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember, Model model) throws IOException {

       if(itemSaveValidator.supports(form.getClass()))
        {
            itemSaveValidator.validate(form,bindingResult);
        }


       if(bindingResult.hasErrors())
       {
           model.addAttribute("member",loginMember);
           model.addAttribute("categoryType",CategoryType.CATEGORY_TYPE);
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

    @GetMapping(value = "summernoteImage/{filename}",produces = "image/jpeg")
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
    public String viewForm(@PathVariable Long itemId, Model model,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember)
    {
        Item item = itemService.findById(itemId);

        // 로그인한 멤버의 권한이 USER 이고 if 조건 분기에 따라 본인 게시글이면 viewForm 으로 이동하고 아니면 viewFomOther 로 이동하도록 설계했다.
        // 로그인한 멤버의 권한이 USER 가 아니라면 ADMIN 이므로, ADMIN 의 권한에 따라 모든 게시글의 CURD 가 가능하므로 바로 viewForm 으로 이동하도록 설계했다.

        if(loginMember.getRole().equals("USER")) {
            if (!(loginMember.getMemberId().equals(item.getWriterId()))) {
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
    public String editForm(@PathVariable Long itemId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember, Model model)
    {
        Item item = itemService.findById(itemId);

        // 본인의 게시글이 아닌 다른사람이 작성한 게시글을 수정하려고 url 을 변경하면 수정이 가능했기때문에, 이를 방지하고자 if 조건을 걸어서 본인의 게시글만 수정페이지로 넘어올 수 있도록 수정하였다.
        // 어드민은 모든 게시글에 대한 CRUD 권한을 가지므로, or 조건으로 권한체크 조건을 같이 걸어두었다.
        if(loginMember.getMemberId() == item.getWriterId() || loginMember.getRole().equals("ADMIN")) {
            model.addAttribute("form", new ItemUpdateForm(item.getItemId(), item.getTitle(), item.getContent()));
            model.addAttribute("member", loginMember);
            model.addAttribute("categoryType", CategoryType.CATEGORY_TYPE);
            return "editForm"; //editForm 에서 "수정완료" 버튼을 누르면 아래의 컨트롤러의 url 로 이동한다.
        }else
            return "redirect:/forum/freeBoard/viewForm/{itemId}";

    }

    @PostMapping("/forum/freeBoard/editForm/{itemId}") //게시글이 수정되면, 수정된 게시글 상세보기 페이지로 이동
    public String saveEditForm(@PathVariable Long itemId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember, @Valid @ModelAttribute("form") ItemUpdateForm form, BindingResult bindingResult, Model model)
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
    public String removeItem(@PathVariable Long itemId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)LoginMember loginMember)
    {
        // 본인 이외 남이 작성한 게시글을 삭제할 수 있는 현상이 있는데 이는 세션에 있는 회원의 정보와 게시글정보의 일치점을 찾는 체크로직이 없어서이다. 체크 로직을 넣겠다.
        // 본인이 작성한 게시글만 삭제가 가능하며, 어드민은 모든 게시글에대해 CRUD 권한을 가지므로 or 조건으로 권한체크 조건을 같이 걸어두었다.
        Item item = itemService.findById(itemId);
        if(loginMember.getMemberId() == item.getWriterId() || loginMember.getRole().equals("ADMIN")) {
            itemService.deleteItem(itemId);
            return "redirect:/forum/freeBoard";
        }else
            return "redirect:/forum/freeBoard/viewForm/{itemId}";
    }

    @GetMapping("/forum/freeBoard/search")
    public String searchItem(@RequestParam("type") int type, @RequestParam("keyword") String keyword, @RequestParam(value = "page",defaultValue = "1")int page, @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) LoginMember loginMember , Model model)
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
