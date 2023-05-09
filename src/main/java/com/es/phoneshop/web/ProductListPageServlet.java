package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String query = request.getParameter("query");
        final String sort = request.getParameter("sort");
        final String order = request.getParameter("order");

        if (order == null || sort == null) {
            request.setAttribute("products", productDao.findProducts(query));
        }
        else {
            ProductSortingField sortField = ProductSortingField.valueOf(sort.toUpperCase());
            SortingOrder sortingOrder = SortingOrder.valueOf(order.toUpperCase());

            request.setAttribute("products", productDao.findProducts(query, sortField, sortingOrder));
        }

        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
