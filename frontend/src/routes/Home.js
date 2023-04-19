import React, { useEffect, useState } from "react";
import axios from "axios";
import NavbarComponent from "./Navbar";
import {
  Input,
  InputGroup,
  InputGroupText,
} from "reactstrap";
import Swal from "sweetalert2";
import { Link, Route, useNavigate } from "react-router-dom";
import ProductCard from "./ProductCard";
import Product from "./Product";

function Home() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();

  const jwt = localStorage.getItem("JWT");

  const fetchProducts = async () => {
    const productsData = await axios.get("http://localhost:8080/products", {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
    setProducts(productsData.data);
  };

  const fetchCategories = () => {
    axios
      .get("http://localhost:8080/category", {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      })
      .then((response) => {
        setCategories(response.data);
      })
      .catch((error) => {
        if (error.response.status === 401) {
          Swal.fire({
            title: "Usuário não autenticado. Indo para Login",
            showConfirmButton: true,
            timer: 3000,
            timerProgressBar: true
          })
          .then((result) => {
            navigate("/");
          })
        }
        
      });
  };

  useEffect(() => {
    fetchCategories()
    fetchProducts()
  }, []);

  const categoryList = categories.map((category) => {
    return (
      <li key={category.id}>
        <Input type="checkbox"></Input>
        {category.name}
      </li>
    );
  });

  const productList = products.map((product) => {
    /*global event*/
    /*eslint no-restricted-globals: 0*/
    const stateObj = { product: product };
    history.pushState(stateObj, "product", "/product");
    return (
      <li className="product" key={product.product_id}>
        <Link to="/product" state={{product: product}}>
          <ProductCard product={product}></ProductCard>
        </Link>
      </li>
    );
  });

  return (
    <div>
      <NavbarComponent></NavbarComponent>
      <div className="productsPage">
        <div className="sideBarFilters">
          <ul>{categoryList}</ul>
        </div>

        <div className="productsSection">
          <div className="searchBar">
            <InputGroup>
              <InputGroupText>Search</InputGroupText>
              <Input type="text"></Input>
            </InputGroup>
          </div>

          <div className="showProducts">
            <ul>{productList}</ul>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
