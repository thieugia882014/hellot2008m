package com.example.food.controller.product;

import com.example.food.entity.Product;
import com.example.food.model.ProductModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProductServiet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/products/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String Thumbnail = req.getParameter("Thumbnail");
        double price = Double.parseDouble(req.getParameter("price"));
        ProductModel model = new ProductModel();
        Product obj = new Product(name, Thumbnail, price);
        if (!obj.isvalid()) {
            req.setAttribute("errors", obj.getErrors());
            req.getRequestDispatcher("/admin/products/form.jsp").forward(req, resp);
            return;
        }
        model.save(obj);

        resp.sendRedirect("/products/list");
    }
}
