package com.example.food.controller.product;

import com.example.food.entity.Product;
import com.example.food.model.ProductModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

public class productDetailServiet extends HttpServlet {
    private ProductModel model = new ProductModel();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product obj = model.findById(id);
        if (obj==null){
            resp.setStatus(404);
            resp.getWriter().println("Not found");
        }else {
            HttpSession session = req.getSession();
            HashSet<Product> recentView = (HashSet<Product>) session.getAttribute("recentView");
            if (recentView==null){
                recentView = new HashSet<>();
            }
            recentView.add(obj);
            session.setAttribute("recentView",recentView);
            req.setAttribute("obj",obj);
            req.getRequestDispatcher("/admin/products/detail.jsp").forward(req,resp);
        }
    }
}
