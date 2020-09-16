package com.untitle.web;

import com.untitle.config.auth.LoginUser;
import com.untitle.config.auth.dto.SessionUser;
import com.untitle.service.PostsService;
import com.untitle.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // @LoginUser 어노테이션 이용해서 로그인세션 이용 . 위방법은 계속 반복하여 복사하는 것이기 때문에 비효율
        // 이제 어디든 @LoginUser만 사용하면 세션 정보를 가져올수 있게 됨
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
