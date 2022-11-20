package com.sku.TravelF.controller;

import com.sku.TravelF.domain.*;
import com.sku.TravelF.domain.enums.BoardType;
import com.sku.TravelF.domain.enums.JournalType;
import com.sku.TravelF.service.ApiService;
import com.sku.TravelF.service.BoardService;
import com.sku.TravelF.service.CommentService;
import com.sku.TravelF.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/travelf/*")
@RequiredArgsConstructor
public class TravelFController {

    private final ApiService apiService;
    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService memberService;
    private String board_message = "";
    private String comment_message = "";

    @GetMapping("region")
    public ModelAndView region(HttpSession session, ModelAndView mav) {
        mav.setViewName("travelf/region"); // 뷰의 이름
        mav.addObject("login_message", session.getAttribute ("name"));
        return mav;
    }

    @GetMapping({"attractions", "attractions/"})
    public ModelAndView attractions(@RequestParam(value = "areaCode", defaultValue = "0") String areaCode, @RequestParam(value = "sigunguCode", defaultValue = "0") String sigunguCode,
                                    @RequestParam(value = "Search", defaultValue = "0") String search, @PageableDefault Pageable pageable, HttpSession session, ModelAndView mav) {
        mav.setViewName("travelf/attractions"); // 뷰의 이름
        mav.addObject("areaCode", areaCode);
        mav.addObject("sigunguCode", sigunguCode);
        mav.addObject("Search", search);
        mav.addObject("result",  apiService.findTour (areaCode, sigunguCode, search, pageable));
        mav.addObject("login_message", session.getAttribute ("name"));
        return mav;
    }

    @GetMapping({"view", "view/"})
    public ModelAndView viewItem(@RequestParam(value = "contentId", defaultValue = "0") String contentId, HttpSession session, ModelAndView mav) {
        mav.setViewName("travelf/view"); // 뷰의 이름
        Tour result = apiService.CallDetail (contentId);

        mav.addObject ("result", result);
        mav.addObject("login_message", session.getAttribute ("name"));
        return mav;
    }

    @GetMapping({"board", "board/"})
    public ModelAndView board(@RequestParam(value = "boardType", defaultValue = "0") String boardType, @RequestParam(value = "Search", defaultValue = "0") String search,
                              @PageableDefault Pageable pageable, HttpSession session, ModelAndView mav){
        if(boardType.equals ("0") || boardType.equals ("전체")){
            mav.addObject ("boardList", boardService.findBoardList(search, pageable));
        }
        else {
            mav.addObject ("boardList", boardService.findBoardType (boardType, search, pageable));
        }
        mav.setViewName("travelf/board"); // 뷰의 이름
        mav.addObject("boardType", boardType);
        mav.addObject("Search", search);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.addObject("board_message", board_message);
        board_message = "";
        return mav;
    }

    @RequestMapping(value = "saveBoard", method = RequestMethod.POST)
    public ModelAndView saveBoard(@RequestParam(value = "Search", defaultValue = "0") String search, @ModelAttribute Board board, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        if(user != null) {
            board.setUser (user);
            boardService.save (board);
            board_message = "save";
        }
        else {
            board_message = "fail";
        }

        if(board.getBoardType () == BoardType.notice){
            mav.setViewName("redirect:board?boardType="+ URLEncoder.encode ("공지사항", StandardCharsets.UTF_8) + "&Search=" + search); // 뷰의 이름
        }
        else {
            mav.setViewName("redirect:board?boardType=" + URLEncoder.encode ("자유게시판", StandardCharsets.UTF_8) + "&Search=" + search); // 뷰의 이름
        }
        return mav;
    }

    @RequestMapping(value = "updateBoard", method = RequestMethod.POST)
    public ModelAndView updateBoard(@RequestParam(value = "Search", defaultValue = "0") String search, @ModelAttribute Board board, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Board findBoard = boardService.findBoardById (board.getId ());

        if(user != null) {
            if(findBoard.getUser () == user) {
                Board persistBoard = boardService.findBoardById (board.getId ());
                persistBoard.update (board);
                boardService.save (persistBoard);
                board_message = "update";
            }
            else {
                board_message = "user";
            }
        }
        else {
            board_message = "fail";
        }
        if(findBoard.getBoardType () == BoardType.notice){
            mav.setViewName("redirect:board?boardType="+ URLEncoder.encode ("공지사항", StandardCharsets.UTF_8) + "&Search=" + search); // 뷰의 이름
        }
        else {
            mav.setViewName("redirect:board?boardType=" + URLEncoder.encode ("자유게시판", StandardCharsets.UTF_8) + "&Search=" + search); // 뷰의 이름
        }
        return mav;
    }

    //ajax
    //@ResponseBody
    //@PostMapping
    //@PutMapping
    //@DeleteMapping
    //ResponseEntity<?>
    //@PathVariable = board/{id}/name/
    //@RequestParam = board+?id=&name=
    @RequestMapping(value = "deleteBoard", method = RequestMethod.POST)
    public ModelAndView deleteBoard(@RequestParam(value = "Search", defaultValue = "0") String search, @ModelAttribute Board board, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Board findBoard = boardService.findBoardById (board.getId ());

        if(user != null) {
            if(findBoard.getUser () == user) {
                boardService.deleteById (board.getId ());
                board_message = "delete";
            }
            else{
                board_message = "user";
            }
        }
        else {
            board_message = "fail";
        }
        if(findBoard.getBoardType () == BoardType.notice){
            mav.setViewName("redirect:board?boardType="+ URLEncoder.encode ("공지사항", StandardCharsets.UTF_8) + "&Search=" + search); // 뷰의 이름
        }
        else {
            mav.setViewName("redirect:board?boardType=" + URLEncoder.encode ("자유게시판", StandardCharsets.UTF_8) + "&Search=" + search); // 뷰의 이름
        }
        return mav;
        //return new ResponseEntity<> ("{}", HttpStatus.OK);
    }

    @GetMapping({"form", "form/"})
    public ModelAndView form(@RequestParam(value = "id", defaultValue = "0") Long id, @RequestParam(value = "boardType", defaultValue = "0") String boardType, @RequestParam(value = "Search", defaultValue = "0") String search, HttpSession session, ModelAndView mav){
        mav.setViewName("travelf/form"); // 뷰의 이름
        mav.addObject ("board", boardService.findBoardById (id));
        mav.addObject("boardType", boardType);
        mav.addObject("Search", search);
        mav.addObject("login_message", session.getAttribute ("name"));
        return mav;
    }

    @GetMapping({"read", "read/"})
    public ModelAndView read(@RequestParam(value = "id", defaultValue = "0") Long id, @RequestParam(value = "boardType", defaultValue = "0") String boardType, @RequestParam(value = "Search", defaultValue = "0") String search, HttpSession session, ModelAndView mav){
        mav.setViewName("travelf/read"); // 뷰의 이름
        ArrayList<Comment> commentList = new ArrayList<> ();
        commentList = commentService.findBCommentList (id);
        mav.addObject ("board", boardService.findBoardById (id));
        mav.addObject ("commentList", commentList);
        mav.addObject("boardType", boardType);
        mav.addObject("Search", search);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.addObject("userid", session.getAttribute ("userid"));
        mav.addObject("comment_message", comment_message);
        comment_message = "";
        return mav;
    }

    @RequestMapping(value = "saveComment", method = RequestMethod.POST)
    public ModelAndView saveComment(@ModelAttribute Comment comment, @RequestParam(value = "id2", defaultValue = "0") Long id, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Board board = boardService.findBoardById (id);
        if(user != null) {
            comment.setUser (user);
            comment.setBoard (board);
            commentService.save (comment);
            comment_message = "save";
        }
        else {
            comment_message = "fail";
        }
        mav.setViewName("redirect:read?id=" + id); // 뷰의 이름
        return mav;
    }

    @GetMapping({"modify", "modify/"})
    public ModelAndView modify(@RequestParam Long boardID, @RequestParam Long commentID, HttpSession session, ModelAndView mav) {
        Comment findComment = commentService.findCommentById (commentID);

        mav.addObject("boardID", boardID);
        mav.addObject("comment", findComment);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.addObject("userid", session.getAttribute ("userid"));

        mav.setViewName("travelf/modify"); // 뷰의 이름
        return mav;
    }

    @RequestMapping("updateComment")
    public ModelAndView updateComment(@RequestParam Long boardID, @RequestParam Long commentID, @ModelAttribute Comment comment, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Comment findComment = commentService.findCommentById (commentID);

        if(user != null) {
            if(findComment.getUser () == user) {
                Comment persistComment = commentService.findCommentById (commentID);
                persistComment.update (comment);
                commentService.save (persistComment);
                comment_message = "update";
            }
            else {
                comment_message = "fail";
            }
        }
        mav.setViewName("redirect:read?id=" + boardID); // 뷰의 이름
        return mav;
    }

    @GetMapping("deleteComment")
    public ModelAndView deleteComment(@RequestParam Long boardID, @RequestParam Long commentID, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Comment findComment = commentService.findCommentById (commentID);

        if(user != null) {
            if(findComment.getUser () == user) {
                commentService.deleteById (commentID);
                comment_message = "delete";
            }
            else {
                comment_message = "fail";
            }
        }
        mav.setViewName("redirect:read?id=" + boardID); // 뷰의 이름
        return mav;
    }
}
