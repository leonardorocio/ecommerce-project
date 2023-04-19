import React, { useEffect } from "react";
import NavbarComponent from "./Navbar";
import { useLocation, useParams } from "react-router-dom";

function Product(props) {
    const { productIndex } = useParams();
    const product = JSON.parse(localStorage.getItem("products"))[productIndex];
    return (
        <div>
            <NavbarComponent></NavbarComponent>

            <div className="showProduct">
                {product.price}
                {product.name}
                {product.description}
            </div>
        </div>
    )
}

export default Product;