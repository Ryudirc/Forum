package forum.board.service;

import forum.board.domain.Item;
import forum.board.global.pagination;
import forum.board.repository.MybatisPaginationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class paginationService {


    private final MybatisPaginationRepository paginationRepository;
    private final ItemService itemService;

    public pagination getPagination(double btnNum)
    {
        //한 페이지에서 보여주는 게시글은 15개
        int pageSize = 15;
        // 하나의 블럭에서 보여줄 버튼은 5개
        double btnCnt = 5;

        //첫 시작페이지는 무조건 1페이지


        //버튼의 총 개수를 구한다.
        int totalBtnCnt = (int)getTotalBtnCnt();
        //버튼블럭의 개수를 구한다.
        int btnBlockCnt = (int)calcBtnCnt(totalBtnCnt);

        //한 페이지당 받아야 하는 게시글의 개수를 구한다.
        int start;
        if(btnNum == 1) {
            start = 0;
        }else{
            start = (int)btnNum * 15;
        }
            List<Item> items = BtnPerPageSize(start, pageSize);

        //페이지 블럭(prev,next) 구현
        int nowBlock =  (int)Math.ceil((btnNum / btnCnt));
        int nextBlock =(int)((nowBlock * btnCnt) + 1);
        int prevBlock =(int)((nowBlock * btnCnt) - btnCnt);
        if(prevBlock < 1)
        {
            prevBlock = 1;
        }
        if(nextBlock > totalBtnCnt)
        {
            nextBlock = totalBtnCnt;
        }
        // 시작 페이지
        int startPage = (int)((nowBlock - 1) * btnCnt + 1);

        // 끝 페이지
        int endPage = (int)(startPage + btnCnt - 1);

        // 최종적으로 넘길 페이지네이션 클래스를 생성하여 넘긴다.
        return new pagination(startPage, endPage, pageSize, btnCnt, btnBlockCnt, totalBtnCnt, items, nowBlock, prevBlock, nextBlock);
    }

    public pagination searchedPagination(int type,String keyword,int btnNum)
    {
        //한 페이지에서 보여주는 게시글은 15개
        int pageSize = 15;
        // 하나의 블럭에서 보여줄 버튼은 5개
        double btnCnt = 5;

        //첫 시작페이지는 무조건 1페이지


        //버튼의 총 개수를 구한다.
        int totalBtnCnt = (int)getTotalBtnCnt();
        //버튼블럭의 개수를 구한다.
        int btnBlockCnt = (int)calcBtnCnt(totalBtnCnt);

        //한 페이지당 받아야 하는 게시글의 개수를 구한다.
        int start;
        if(btnNum == 1) {
            start = 0;
        }else{
            start = (int)btnNum * 15;
        }

        List<Item> searchedItems = itemService.searchProcess(type, keyword,start,pageSize);

        //페이지 블럭(prev,next) 구현
        int nowBlock =  (int)Math.ceil((btnNum / btnCnt));
        int nextBlock =(int)((nowBlock * btnCnt) + 1);
        int prevBlock =(int)((nowBlock * btnCnt) - btnCnt);
        if(prevBlock < 1)
        {
            prevBlock = 1;
        }
        if(nextBlock > totalBtnCnt)
        {
            nextBlock = totalBtnCnt;
        }
        // 시작 페이지
        int startPage = (int)((nowBlock - 1) * btnCnt + 1);

        // 끝 페이지
        int endPage = (int)(startPage + btnCnt - 1);

        // 최종적으로 넘길 페이지네이션 클래스를 생성하여 넘긴다.
        return new pagination(startPage, endPage, pageSize, btnCnt, btnBlockCnt, totalBtnCnt, searchedItems, nowBlock, prevBlock, nextBlock);

    }




    //버튼의 총 개수를 구한다.
    public double getTotalBtnCnt()
    {
        int totalRows = paginationRepository.findAllRows().size();
        return Math.ceil((double)totalRows / 15.0);
    }


    // 버튼을 누를때 마다 페이지에서 게시글의 개수를 보여주기 위한 메서드(인자를 입력받아서 DB 로부터 15개씩 뽑아낸다)
    public List<Item> BtnPerPageSize(int start, int pageSize)
    {
        return paginationRepository.findPageSize(start, pageSize);
    }

    // prev, next 버튼의 활성화/비활성화 여부를 알려주는 메서드
    public Boolean checkPrevNextBtn(int startPage,int nowPage,int endPage)
    {
        if(startPage == endPage && endPage == 1)
        {
            return false;
        }
        else if (endPage > 1 && nowPage == startPage)
        {
            return true;
        } else if (endPage > 1 && nowPage == endPage)
        {
            return true;
        }
        else return true;

    }
    // totalBtn 으로 버튼의 단위개수(5개 기준) 설정 (총 버튼개수가 15개면 버튼블럭의 수는 3)
    public double calcBtnCnt(double totalBtnCnt)
    {
        return Math.ceil((double)totalBtnCnt / 5);
    }

    public int calcEndPage(int btnCnt)
    {
        return btnCnt*5;
    }



}
