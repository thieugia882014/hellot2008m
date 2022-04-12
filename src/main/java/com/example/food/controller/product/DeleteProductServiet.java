package com.example.food.controller.product;

import com.example.food.entity.Product;
import com.example.food.model.ProductModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServiet extends HttpServlet {
    private ProductModel model = new ProductModel();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product obj = model.findById(id);
        if (obj == null) {
            resp.setStatus(404);
            resp.getWriter().println("Not found");
        } else {
           model.deleteVip(id);
            resp.sendRedirect("/products/list");
        }
    }
}
