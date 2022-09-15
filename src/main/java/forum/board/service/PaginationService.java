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
public class PaginationService {

    //btnNum = 버튼번호로써 현재 페이지 번호를 말한다. 페이지 번호는 1 ~ 5 / 6 ~ 10 / 11 ~ 15 ... 이다.
    //pageSize = 한 페이지당 노출되어야 하는 게시글의 수를 말한다. 나는 15개로 계획했다.
    //btnCnt = 한 블럭에 몇개의 버튼을 보여줄지 갯수를 나타낼 변수다. 나는 한 블럭당 5개의 번호(1~5) 단위로 보여주기위해 5로 계획했다.
    //start = DB 에서 게시글을 조회해 올때 몇번부터 가져올지를 정하는 변수다. btnNum에 따라 다르게 가져오도록 설계했다.
    //prevBlock, nowBlock, nextBlock = 현재 블럭과 이전,다음 블럭을 정해준다. 이는 btnNum 과 btnCnt 에 따라 변하도록 설계했다.
    // startPage, endPage = 각 블럭의 시작페이지와 끝페이지를 정의하는 변수다. 1블럭에서 startPage는 1이고 endPage는 5 이다. 2블럭에서 startPage는 6이고, endPage는 10이다.


    private final MybatisPaginationRepository paginationRepository;
    private final ItemService itemService;


    //한 페이지에서 보여주는 게시글은 15개
    int pageSize =  15;
    // 하나의 블럭에서 보여줄 버튼은 5개
    double btnCnt = 5;

    public pagination getPagination(double btnNum)
    {

        //버튼의 총 개수를 구한다.
        int totalBtnCnt = (int)getTotalBtnCnt();
        //버튼블럭의 개수를 구한다.
        int btnBlockCnt = (int)calcBtnCnt(totalBtnCnt);

        //한 페이지당 받아야 하는 게시글의 개수를 구한다.
        int start;
        if(btnNum == 1) {
            start = 0;
        }else{
            start = (int)btnNum * pageSize;
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
        // 끝 페이지의 위치를 게시글의 개수와 맞게 조절해주기 위해 모든 게시글을 조회해오는 리스트를 선언한다.
        List<Item> freeBoardItems = itemService.getFreeBoard();

        // next 버튼이 게시글의 끝 페이지와 동일한 URL 로 이동할 수 있도록 validation 해주어야 한다.
        if(nextBlock > (freeBoardItems.size())/15)
        {
            nextBlock = freeBoardItems.size()/15;
        }
        // 시작 페이지
        int startPage = (int)((nowBlock - 1) * btnCnt + 1);

        // 끝 페이지
        int endPage = (int)(startPage + btnCnt - 1);

        //조회를 통해 가져온 모든 아이템의 개수가 끝페이지보다 적을때(17페이지에 마지막게시글이 있는데 페이지는 20페이지 까지 가는경우) 끝 게시글에 맞춰 페이지가 끝날수 있도록 validation 해야 한다.
        if(endPage > (freeBoardItems.size()/15))
        {
            endPage = freeBoardItems.size()/15;
            if(endPage <=0)
            {
                nextBlock = 1;
                endPage = 1;
            }
        }


        // 최종적으로 넘길 페이지네이션 클래스를 생성하여 넘긴다.
        return new pagination(startPage, endPage, pageSize, btnCnt, btnBlockCnt, totalBtnCnt, items, nowBlock, prevBlock, nextBlock);
    }

    public pagination searchedPagination(int type,String keyword,int btnNum)
    {
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

        //끝 페이지의 위치를 게시글의 개수와 맞게 조절해주기 위해 검색결과에 해당하는 모든 게시물을 조회하는 리스트를 선언한다.
        List<Item> itemsAll = itemService.searchAll(type, keyword);

        //페이지 블럭(prev,next) 구현
        int nowBlock =  (int)Math.ceil((btnNum / btnCnt));
        int nextBlock =(int)((nowBlock * btnCnt) + 1);
        int prevBlock =(int)((nowBlock * btnCnt) - btnCnt);
        if(prevBlock < 1)
        {
            prevBlock = 1;
        }
        if(nextBlock > (itemsAll.size()/15))
        {
            nextBlock = itemsAll.size()/15;
        }
        // 시작 페이지
        int startPage = (int)((nowBlock * btnCnt) - 4);
        // 끝 페이지
        int endPage = (int)(nowBlock * btnCnt);

        if(endPage > (itemsAll.size()/15))
        {
            endPage = itemsAll.size()/15;
            if(endPage <=0)
            {
                nextBlock = 1;
                endPage = 1;
            }
        }

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

    // totalBtn 으로 버튼의 단위개수(5개 기준) 설정 (총 버튼개수가 15개면 버튼블럭의 수는 3)
    public double calcBtnCnt(double totalBtnCnt)
    {
        return Math.ceil((double)totalBtnCnt / 5);
    }




}
