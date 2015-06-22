package com.pharmacy.web.controller;

import com.pharmacy.article.Article;
import com.pharmacy.article.Pharmacy;
import com.pharmacy.article.Price;
import com.pharmacy.article.helper.ArticleHelper;
import com.pharmacy.evaluation.Evaluation;
import com.pharmacy.evaluation.helper.EvaluationHelper;
import com.pharmacy.exception.ControllerException;
import com.pharmacy.exception.ServiceException;
import com.pharmacy.exception.type.ExceptionType;
import com.pharmacy.payment.PaymentType;
import com.pharmacy.service.api.ArticleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Inject
    private ArticleService articleService;

    @RequestMapping(value = {"/", "/index", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        ModelAndView modelAndView = null;
        try {
            modelAndView = new ModelAndView("index");
            List<Article> articles = articleService.loadBestDiscountedArticles();
            modelAndView.addObject("articles", articles);
            List<Pharmacy> pharmacies = getSomePharmacies();
            modelAndView.addObject("pharmacies", pharmacies);
            modelAndView.addObject("evaluationHelper", new EvaluationHelper());
            modelAndView.addObject("articleHelper", new ArticleHelper());
            modelAndView.addObject("evaluations", getSomeEvaluations());

        } catch (ServiceException ex) {
            ex.writeLog(LOG);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security + Hibernate Example");
        model.addObject("message", "This page is for ROLE_ADMIN only!");
        model.setViewName("admin");

        return model;

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            try {
                getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION");
            } catch (ControllerException ex) {
                model.addObject("error", ex.getExceptionType().getResourceKey());
                ex.writeLog(LOG);
            }
        }
        if (logout != null) {
            model.setViewName("index");
            model.addObject("msg", "You've been logged out successfully.");
        } else {
            model.setViewName("login");
        }
        return model;

    }

    // customize the error message
    private void getErrorMessage(HttpServletRequest request, String key) throws ControllerException {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        if (exception != null) {
            throw new ControllerException(ExceptionType.LOGIN_0002, exception);
        } else {
            throw new ControllerException(ExceptionType.LOGIN_0003);
        }
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        // check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(userDetail);

            model.addObject("username", userDetail.getUsername());

        }

        model.setViewName("403");
        return model;

    }

    private List<Pharmacy> getSomePharmacies() {
        List<Pharmacy> pharmacys = new ArrayList<>();
        Pharmacy pharmacy;
        Evaluation evaluation;
        Price price;
        for (int i = 1; i <= 5; i++) {
            pharmacy = new Pharmacy();
            pharmacy.setName("Apotheke " + i);
            pharmacy.setPaymentTypes(new ArrayList<>(Arrays.asList(PaymentType.values())));
            pharmacy.setLogoURL("http://www.shop-apotheke.com/pix/shop-apotheke-online-apotheke.png");
            for (int j = 0; j < 10; j++) {
                evaluation = new Evaluation();
                evaluation.setPoints((float) (i + 0.5));
                pharmacy.getEvaluations().add(evaluation);
            }
            pharmacys.add(pharmacy);
        }
        return pharmacys;
    }

    private List<Evaluation> getSomeEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        Evaluation evaluation;
        for (int j = 1; j <= 5; j++) {
            evaluation = new Evaluation();
            evaluation.setName("Bewertung Bewertung Bewertung Bewertung Bewertung Bewertung Bewertung Bewertung akjsdhajksdha kjdak jdkajs dkajsd kajsnd kjand adjn alksjdn aksjdn kaljdn kajsnd kajnd kajsnd kajsnd kjbf lbgksdfsf nsdf s skjdf skjnfd fkj nsfkj nkjs ndfjks dnfksjfn skjdfgd" + j);
            evaluation.setDescription("Hier ist die Bewertung von der Bewertung Hier ist die Bewertung von der Bewertung Hier ist die Bewertung von der Bewertung Hier ist die Bewertung von der Bewertung Hier ist die Bewertung von der Bewertung Hier ist die Bewertung von der Bewertung Hier ist die Bewertung von der Bewertung " + j);
            evaluation.setPoints((float) (j + 0.5));
            evaluations.add(evaluation);
        }
        return evaluations;
    }
}