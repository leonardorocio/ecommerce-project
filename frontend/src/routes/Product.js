import React, { useEffect } from "react";
import NavbarComponent from "./Navbar";
import { useLocation } from "react-router-dom";

function Product(props) {
    const { state } = useLocation();

    return (
        <div>
            <NavbarComponent></NavbarComponent>

            <div className="showProduct">
                {state.product.price}
                {state.product.name}
                {state.product.description}
            </div>
        </div>
    )
}

export default Product;