/*
 * Copyright 2015 Alexandr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pharmacy.controller.pricecheck;

import com.pharmacy.article.Article;
import com.pharmacy.controller.abstraction.AbstractController;
import com.pharmacy.service.api.ArticleService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Alexandr
 */
@Controller
public class PriceCheckController extends AbstractController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/preisvergleich/{articelNumber}/{name}", method = RequestMethod.GET)
    public ModelAndView loadAllPharmacyForPriceCheck(@PathVariable String articelNumber, @PathVariable String name, @RequestParam(required = false) String page, HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("priceCheck");
        Article article = articleService.findArticleByArticelNumber(articelNumber);
        int currentpage;
        if (page == null) {
            currentpage = 1;
        } else {
            currentpage = Integer.valueOf(page);
        }
        getFilterOptions().setCurrentPage(currentpage);
        setPage(page, modelAndView, (long)article.getPrices().size());

        modelAndView.addObject("article", article);
        return modelAndView;
    }

}
