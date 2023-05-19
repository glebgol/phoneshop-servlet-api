package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.services.RecentlyViewedProductService;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    protected RecentlyViewedProductService recentlyViewedProductService;
    private static final String QUERY_PARAMETER_NAME = "query";
    private static final String SORT_PARAMETER_NAME = "sort";
    private static final String ORDER_PARAMETER_NAME = "order";
    private static final String PRODUCTS_ATTRIBUTE_NAME = "products";
    private static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME = "recentlyViewedProducts";
    private static final String PRODUCT_LIST_JSP = "/WEB-INF/pages/productList.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedProductService = DefaultRecentlyViewedProductService.getInstance();
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
}
