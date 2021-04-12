package com.paging.pagingTest.controller;

import com.paging.pagingTest.domain.Board;
import com.paging.pagingTest.domain.Pagination;
import com.paging.pagingTest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "1") int page) {

        // 총 게시물 수
        List<Board> boardList = boardRepository.findAll();
        int size = boardList.size();
        log.info("리스트의 개수");
        log.info(String.valueOf(size));
        int totalListCnt = size;

        // 생성인자로  총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(totalListCnt, page);

        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        int pageCheck = startIndex + pageSize;
        List<Board> boardList2 = new ArrayList<>();
        for (int i = startIndex; i < pageCheck; i++){
            Board board = boardList.get(i);

            boardList2.add(board);

            if(i == (totalListCnt-1)){
                pageCheck = totalListCnt-1;
            }

        }

        log.info(String.valueOf(startIndex));
        model.addAttribute("boardList", boardList2);
        model.addAttribute("pagination", pagination);

        return "/view/index";
    }

    @GetMapping("/test")
    public String testView(){
        return "/view/test";
    }
}
