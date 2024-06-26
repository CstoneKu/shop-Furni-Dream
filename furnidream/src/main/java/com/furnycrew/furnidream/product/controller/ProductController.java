package com.furnycrew.furnidream.product.controller;

import com.furnycrew.furnidream.common.paging.PageCriteria;
import com.furnycrew.furnidream.common.search.SearchCriteria;
import com.furnycrew.furnidream.product.model.dto.ProductDto;
import com.furnycrew.furnidream.product.model.dto.ProductRegistDto;
import com.furnycrew.furnidream.product.model.dto.ProductUpdateDto;
import com.furnycrew.furnidream.product.model.service.ProductCommandService;
import com.furnycrew.furnidream.product.model.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    @GetMapping("/list")
    public void list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit,
            Model model){
        log.info("GET /product/list?page{}&limit={}", page, limit);
        // 1. 컨텐츠 영역 (limit 쿼리)
        int offset = (page - 1) * limit;
        List<ProductDto> products = productQueryService.findAll(offset, limit);
        log.debug("products = {}", products);
        model.addAttribute("products", products);

        // 2. 페이지바 영역(html)
        int totalCount = productQueryService.countProducts();
        String url = "list";
        model.addAttribute("pageCriteria", new PageCriteria(page, limit, totalCount, url));
    }

    @GetMapping("/regist")
    public void regist(Model model){
        log.info("GET /product/regist");
        List<ProductDto> categories = productQueryService.findAllCategory();
        model.addAttribute("categories", categories);
    }

    @PostMapping("/regist")
    public String regist(@ModelAttribute ProductRegistDto productRegistDto, RedirectAttributes redirectAttributes){
        log.info("POST /product/regist");
        ProductDto productDto = productRegistDto.toProductDto();
        int result = productCommandService.insertProduct(productDto);
        redirectAttributes.addFlashAttribute("message", "✅상품이 성공적으로 등록되었습니다");
        return "redirect:/product/list";
    }

    @GetMapping("/update/{productId}")
    public String update(@PathVariable("productId") Long productId, Model model){
        log.info("GET /product/update/{}", productId);
     ProductDto productDto = productQueryService.findByProductId(productId);
        model.addAttribute("productDto", productDto);
        return "product/update";
    }



    @PostMapping("/update")
    public String update(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes){
        log.info("POST /product/update");
        int result = productCommandService.updateProduct(productDto);

        redirectAttributes.addFlashAttribute("message", "🔄️상품이 성공적으로 수정되었습니다.");
        log.info("result = {}", result);
        return "redirect:/product/list";
    }

    @GetMapping("/detail/{productId}")
    public String detail(Model model, @PathVariable("productId") Long productId){
        log.info("GET /product/detail/{}", productId);
        ProductDto product = productQueryService.findByProductId(productId);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchType,
                         @RequestParam String keyword,
                         Model model) {
        log.info("GET search?searchType={}&keyword={}", searchType, keyword);
        log.debug("searchType/keyword = {}/{}", searchType, keyword);
        List<ProductDto> products = productQueryService.searchProduct(searchType, keyword);
        model.addAttribute("products", products);
        return "product/search";
    }
}
