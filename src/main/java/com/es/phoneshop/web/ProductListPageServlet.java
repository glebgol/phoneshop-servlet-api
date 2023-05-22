package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.services.NumberParser;
import com.es.phoneshop.services.RecentlyViewedProductService;
import com.es.phoneshop.services.impl.DefaultNumberParser;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductService;
import com.es.phoneshop.web.utils.RequestParser;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class ProductListPageServlet extends AbstractServlet {
    private ProductDao productDao;
    protected RecentlyViewedProductService recentlyViewedProductService;
    protected NumberParser numberParser;
    private static final String PRODUCT_QUANTITY_ERROR_ID_ATTRIBUTE = "quantityErrorId";
    private static final String PRODUCT_LIST_JSP = "/WEB-INF/pages/productList.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedProductService = DefaultRecentlyViewedProductService.getInstance();
        numberParser = DefaultNumberParser.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String query = request.getParameter(QUERY_PARAMETER_NAME);
        final String sort = request.getParameter(SORT_PARAMETER_NAME);
        final String order = request.getParameter(ORDER_PARAMETER_NAME);

        if (order == null || sort == null) {
            request.setAttribute(PRODUCTS_ATTRIBUTE_NAME, productDao.findProducts(query));
        }
        else {
            ProductSortingField sortField = ProductSortingField.valueOf(sort.toUpperCase());
            SortingOrder sortingOrder = SortingOrder.valueOf(order.toUpperCase());

            request.setAttribute(PRODUCTS_ATTRIBUTE_NAME, productDao.findProducts(query, sortField, sortingOrder));
        }
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME, recentlyViewedProductService.getRecentlyViewedProducts(request));
        request.getRequestDispatcher(PRODUCT_LIST_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = RequestParser.getProductIdParameter(request);
        String quantityString = request.getParameter(QUANTITY_ATTRIBUTE_NAME);
        try {
            int quantity = numberParser.parseNumber(request.getLocale(), quantityString);
            cartService.add(cartService.getCart(request.getSession()), productId, quantity);
        } catch (OutOfStockException | ParseException | NumberFormatException e) {
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, getQuantityErrorMessage(e));
            request.setAttribute(PRODUCT_QUANTITY_ERROR_ID_ATTRIBUTE, productId);
            doGet(request, response);
            return;
        }
        response.sendRedirect(getRedirectUrl(request));
    }

    private String getRedirectUrl(HttpServletRequest request) {
        return request.getContextPath() + "/products" + "?" + SUCCESS_MESSAGE_ATTRIBUTE_NAME + "=" + SUCCESS_MESSAGE;
    }
}
