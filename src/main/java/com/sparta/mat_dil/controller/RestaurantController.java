package com.sparta.mat_dil.controller;

import com.sparta.mat_dil.dto.*;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.security.UserDetailsImpl;
import com.sparta.mat_dil.service.CommentService;
import com.sparta.mat_dil.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final CommentService commentService;

    /**
     * 음식점 등록
     * @param requestDto 요청 객체
     * @param userDetails 회원 정보
     * @return status.code , message
     **/
    @PostMapping
    public ResponseEntity<ResponseDataDto<RestaurantResponseDto>> createRestaurant(@Valid @RequestBody RestaurantRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RestaurantResponseDto responseDto = restaurantService.createRestaurant(requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.RESTAURANT_CREATE_SUCCESS, responseDto));
    }


    /**
     * 전체 음식점 조회
     * @param page 요청 객체
     * @return status.code, message
     **/
    @GetMapping
    public Page<RestaurantResponseDto> getRestaurantList(@RequestParam(value = "page") int page) {
        return restaurantService.getRestaurantList(page - 1);
    }
    /**
    * 특정 음식점 조회
    * @param id 음식점 id
    * @return status.code, message
    **/


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataDto<RestaurantResponseDto>> getRestaurant(@PathVariable Long id) {
        RestaurantResponseDto responseDto = restaurantService.getRestaurant(id);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.RESTAURANT_CHECK_SUCCESS, responseDto));
    }


     /**
     * 특정 음식점 수정
     * @param id 음식점 id
     * @param userDetails 회원 정보
     * @return status.code, message
     **/
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataDto<RestaurantResponseDto>> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RestaurantResponseDto responseDto = restaurantService.updateRestaurant(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.RESTAURANT_UPDATE_SUCCESS, responseDto));
    }


    /**
     * 특정 음식점 삭제
     * @param id 음식점 id
     * @param userDetails 회원 정보
     * @return status.code, message
     **/
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteRestaurant(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        restaurantService.deleteRestaurant(id, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.RESTAURANT_DELETE_SUCCESS));
    }



    //댓글 등록
    @PostMapping("/{restaurants_id}/comments")
    public ResponseEntity<ResponseDataDto<CommentResponseDto>> createComment(@PathVariable Long restaurants_id, @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(restaurants_id, requestDto, userDetails.getUser());

        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.COMMENTS_CHECK_SUCCESS, responseDto));
    }

    //전체 댓글 조회
    @GetMapping("/{restaurantId}/comments")
    public ResponseEntity<ResponseDataDto<List<CommentResponseDto>>> getAllComments(@PathVariable Long restaurantId){
        List<CommentResponseDto> responseDtoList = commentService.getAllComments(restaurantId);

        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.COMMENTS_CHECK_SUCCESS, responseDtoList));
    }

    //단일 댓글 조회
    @GetMapping("/{restaurantId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long restaurantId, @PathVariable Long commentId){
        CommentResponseDto responseDto = commentService.getComment(restaurantId, commentId);

        return ResponseEntity.ok(responseDto);
    }

//    //댓글 수정
    @PutMapping("/{restaurantId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long restaurantId, @PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        CommentResponseDto responseDto = commentService.updateComment(restaurantId, commentId, userDetails.getUser(), requestDto);

        return ResponseEntity.ok(responseDto);
    }

    //댓글 삭제
    @DeleteMapping("/{restaurantId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long restaurantId, @PathVariable Long commentId,
                                                @RequestBody PasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        commentService.deleteComment(restaurantId, commentId, userDetails.getUser(), requestDto);

        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

    @PostMapping("/{restaurants_id}/foods")
    public ResponseEntity<ResponseDataDto<FoodResponseDto>> saleFood(@PathVariable Long restaurants_id, @RequestBody FoodRequestDto foodRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        FoodResponseDto responseDto= restaurantService.saleFood(restaurants_id, foodRequestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.FOOD_CREATE_SUCCESS, responseDto));
    }

}
