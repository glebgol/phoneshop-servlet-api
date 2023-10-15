package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.services.RecentlyViewedProductService;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductService;
import com.es.phoneshop.strategies.SearchMethod;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.codehaus.plexus.util.StringUtils.isBlank;

public class AdvancedSearchPageServlet extends AbstractServlet {
    private ProductDao productDao;
    private RecentlyViewedProductService recentlyViewedProductService;
    private static final String ADVANCED_SEARCH_JSP = "/WEB-INF/pages/advancedSearch.jsp";
    private static final String MIN_PRICE_PARAMETER_NAME = "minPrice";
    private static final String MAX_PRICE_PARAMETER_NAME = "maxPrice";
    private static final String QUERY_PARAMETER_NAME = "query";
    private static final String SEARCH_METHOD_PARAMETER_NAME = "searchMethod";
    private static final String PRODUCTS_ATTRIBUTE_NAME = "products";
    private static final String ERRORS_ATTRIBUTE_NAME = "errors";
    private static final String SEARCH_METHODS_ATTRIBUTE_NAME = "searchMethods";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedProductService = DefaultRecentlyViewedProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();

        String search = request.getParameter(QUERY_PARAMETER_NAME);
        SearchMethod searchMethod = getSearchMethod(request);
        BigDecimal minPrice = getBigDecimalParameter(request, MIN_PRICE_PARAMETER_NAME, errors);
        BigDecimal maxPrice = getBigDecimalParameter(request, MAX_PRICE_PARAMETER_NAME, errors);

        if (search != null && errors.isEmpty()) {
            request.setAttribute(PRODUCTS_ATTRIBUTE_NAME,
                    productDao.advancedProductSearch(search, searchMethod, minPrice, maxPrice));
        } else {
            request.setAttribute(ERRORS_ATTRIBUTE_NAME, errors);
        }

        request.setAttribute(SEARCH_METHODS_ATTRIBUTE_NAME, getSearchMethods());
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME,
                recentlyViewedProductService.getRecentlyViewedProducts(request));
        request.getRequestDispatcher(ADVANCED_SEARCH_JSP).forward(request, response);
    }

    private List<SearchMethod> getSearchMethods() {
        return Arrays.asList(SearchMethod.values());
    }

    private SearchMethod getSearchMethod(HttpServletRequest request) {
        String searchMethodString = request.getParameter(SEARCH_METHOD_PARAMETER_NAME);
        if (searchMethodString == null) {
            return SearchMethod.ANY_WORD;
        } else {
            return SearchMethod.valueOf(searchMethodString);
        }
    }

    private BigDecimal getBigDecimalParameter(HttpServletRequest request, String name, Map<String, String> errors) {
        String bigDecimalString = request.getParameter(name);
        if (isBlank(bigDecimalString)) {
            return null;
        }
        try {
            return new BigDecimal(bigDecimalString);
        } catch (NumberFormatException e) {
            errors.put(name, "Not a number");
            return null;
        }
    }
}
